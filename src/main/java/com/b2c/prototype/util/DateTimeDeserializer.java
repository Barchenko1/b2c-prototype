package com.b2c.prototype.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

@Slf4j
public class DateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    // --------------------------- MEMBER VARIABLES ---------------------------

    /**
     * Valid unmarshalling patterns
     */
    private static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            // yyyy-MM-ddTHH:mm:ss
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            // (Optional) Fraction of second
            .optionalStart()
            .appendPattern(".SSS")
            .optionalEnd()
            // (Optional) GMT Offset, including Z(ulu) and with/without
            // colon
            .optionalStart()
            .appendOffset("+HHMM", "Z")
            .optionalEnd()
            .optionalStart()
            .appendOffset("+HH:MM", "Z")
            .optionalEnd()
            // (Optional) Zone ID (in brackets)
            .optionalStart()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .optionalEnd()
            .toFormatter();

    // ------------------------------------------------------------------------

    // ----------------------------- PUBLIC METHODS ---------------------------

    /**
     * De-serialize the given string into a ZonedDateTime object.
     */
    @Override
    public ZonedDateTime deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {

        String v = p.getValueAsString();
        return this.stringToZonedDateTime(v);
    }

    /**
     * Given a String, return the ZonedDateTime the defined formatter. Null is returned if the String is blank.
     *
     * @param value
     *            A String representation of a ZonedDateTime.
     * @return the ZonedDateTime, or null.
     */
    public ZonedDateTime stringToZonedDateTime(final String value) {

        // Shortcut if null
        if (StringUtils.isBlank(value)) {
            return null;
        }

        try {
            // Parses from string as best as possible
            TemporalAccessor temporalAccessor = formatter.parse(value, ZonedDateTime::from);
            return (ZonedDateTime) temporalAccessor;
        }
        catch (Exception ex) {

            // Bad
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    // ------------------------------------------------------------------------
}
