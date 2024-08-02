package com.b2c.prototype.service.gateway;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.internal.StringUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.PrematureCloseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RestClient implements IRestClient {

    private Long maxRetry;

    private Long backoffDelay;

    private WebClient buildWebClient(final StringBuilder logBuilder) {

        HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(30000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(30000, TimeUnit.MILLISECONDS));
                });

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024)).build();

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        return clientResponse.createException()
                                .flatMap(e ->
                                        Mono.error(new RuntimeException(e.getStatusCode().toString())));
                    } else {
                        return Mono.just(clientResponse);
                    }
                }))
                .filter(logRequest(logBuilder))
                .filter(logResponse(logBuilder))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private <T> T httpCall(final HttpMethod httpMethod, final Class<T> responseType, final String baseUrl, final String uri,
                           final Consumer<HttpHeaders> headersConsumer, final Object payload,
                           final MultiValueMap<String, String> multiValueQueryMap, final Object... pathParam) {
//        boolean isOAuthResponse = responseType.getSimpleName().equals(OAuthResponse.class.getSimpleName());
        boolean isOAuthResponse = false;
        StringBuilder logBuilder = new StringBuilder(StringUtil.EMPTY_STRING);
        Long StartTime = System.currentTimeMillis();
        logBuilder.append("Method: ").append("RestClient_httpCall");
        logBuilder.append(" StartTime: ").append(StartTime);
        logBuilder.append(" Payload: ").append(isOAuthResponse ? "***" : payload.toString().replaceAll("\\s+", " "));
        Mono<ResponseEntity<T>> mono = buildWebClient(logBuilder).method(httpMethod)
                .uri(baseUrl, uriBuilder -> (uriBuilder.path(uri).queryParams(multiValueQueryMap).build(pathParam)))
                .body(BodyInserters.fromValue(payload))
                //.header(HttpHeaders.CONTENT_TYPE, mediaType.toString())
                .headers(headersConsumer)
                //.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .exchange().flatMap(clientResponse -> clientResponse.toEntity(responseType))
                .retryWhen(Retry.backoff(maxRetry, Duration.ofMillis(backoffDelay)).filter(
                        throwable -> throwable instanceof PrematureCloseException || throwable instanceof ReadTimeoutException).doBeforeRetry(
                        retrySignal -> System.out.println("Retrying for exception: Total Retries: "
                                + retrySignal.totalRetries() + "; Exception: " + retrySignal.failure())));

        ResponseEntity<T> responseEntity = null;
        try {
            responseEntity = mono.block();
            logBuilder.append(" ResponseStatus: ").append(responseEntity != null ? responseEntity.getStatusCodeValue() : "Null Response");
            return responseEntity != null ? responseEntity.getBody() : null;
        } catch (Exception e) {
//            logBuilder.append(" GatewayException: ").append(e.getHttpStatusCode()).append(" " + e.getMessage());
            throw e;
        } finally {
            logBuilder.append(" EndTime: ").append(System.currentTimeMillis());
            logBuilder.append(" ProcessingTime: ").append(StartTime - System.currentTimeMillis());
//            loggerUtil.debug(logBuilder.toString(), isOAuthResponse ? "***" :
//                    (responseEntity != null && responseEntity.getBody() != null) ? responseEntity.getBody().toString() : StringUtil.EMPTY_STRING);
        }
    }

    private static ExchangeFilterFunction logRequest(final StringBuilder logBuilder) {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            logBuilder.append(" URL: ").append(clientRequest.url()).append(" Method: ").append(clientRequest.method()).append(" Param: ");
            clientRequest.attributes().forEach((s, o) -> logBuilder.append(s).append(" = ").append(o).append(" "));
            return Mono.just(clientRequest);
        });
    }

    private static ExchangeFilterFunction logResponse(final StringBuilder logBuilder) {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            logBuilder.append(" StatusCode: ").append(clientResponse.statusCode().value());
            return Mono.just(clientResponse);
        });
    }

    public <T> T get(final Class<T> responseType, final String baseUrl, final String uri, final MediaType mediaType,
                     final MultiValueMap<String, String> multiValueQueryMap, final Object... pathParam) {
        return httpCall(HttpMethod.GET, responseType, baseUrl, uri, httpHeaders -> httpHeaders.add(HttpHeaders.CONTENT_TYPE, mediaType.toString()),
                StringUtil.EMPTY_STRING, multiValueQueryMap, pathParam);
    }

    public <T> T post(final Class<T> responseType, final String baseUrl, final String uri, final MediaType mediaType, final Object payload,
                      final MultiValueMap<String, String> multiValueQueryMap, final Object... pathParam) {
        return httpCall(HttpMethod.POST, responseType, baseUrl, uri, httpHeaders -> httpHeaders.add(HttpHeaders.CONTENT_TYPE, mediaType.toString()),
                payload, multiValueQueryMap, pathParam);
    }
}
