package com.b2c.prototype.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    // --------------------------- MEMBER VARIABLES ---------------------------

    /**
     * The format used to marshal to JSON
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");

    // ------------------------------------------------------------------------

    // ----------------------------- PUBLIC METHODS ---------------------------

    /**
     * Serialize the given ZonedDateTime object into a string.
     */
    @Override
    public void serialize(final ZonedDateTime value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        String s = this.timeToString(value);
        if (s != null) {
            gen.writeString(s);
        }
    }

    /**
     * Given a ZonedDateTime, return the String representation of the time using the defined formatter. Null is returned
     * if the ZonedDateTime is null.
     *
     * @param value
     *            The ZonedDateTime to convert.
     * @return the ZonedDate time String, or null.
     */
    public String timeToString(final ZonedDateTime value) {
        if (value == null) {
            return null;
        }

        if (value.getZone().equals(ZoneOffset.UTC)) {
            return value.format(formatter);
        }
        else {
            return ZonedDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC).format(formatter);
        }
    }

    // ------------------------------------------------------------------------
}