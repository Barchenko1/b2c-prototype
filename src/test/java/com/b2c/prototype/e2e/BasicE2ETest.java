package com.b2c.prototype.e2e;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

@DBRider
@DBUnit(schema = "public", caseSensitiveTableNames = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicE2ETest {

    protected WebTestClient webTestClient;
    protected final ObjectMapper objectMapper = getObjectMapper();

    private ObjectMapper getObjectMapper() {
        DateTimeFormatter ldtSerialize = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter ldtDeserialize = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        JavaTimeModule javaTime = new JavaTimeModule();
        javaTime.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ldtSerialize));
        javaTime.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ldtDeserialize));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(javaTime);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ReactiveWebApplicationContext reactiveWebApplicationContext;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(reactiveWebApplicationContext).build();
    }

    protected MultiValueMap<String, String> getMultiValueMap(Map<String, String> requestParams) {
        MultiValueMap<String, String> multiValueMap =  new LinkedMultiValueMap<>();
        requestParams.forEach(multiValueMap::add);
        return multiValueMap;
    }

    protected String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
