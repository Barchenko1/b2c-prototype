package com.b2c.prototype.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override public LocalDateTime deserialize(JsonParser p, DeserializationContext c) throws IOException {
        String s = p.getValueAsString();
        if (s == null || s.isBlank()) return null;
        try { return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME); }
        catch (Exception e) {
            try { return java.time.OffsetDateTime.parse(s).toLocalDateTime(); }
            catch (Exception e2) { return java.time.ZonedDateTime.parse(s).toLocalDateTime(); }
        }
    }
}