package com.b2c.prototype.configuration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Configuration
public class BeanConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
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

    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Marketplace API")
                .description("Reactive API documentation")
                .version("v1"));
    }
}
