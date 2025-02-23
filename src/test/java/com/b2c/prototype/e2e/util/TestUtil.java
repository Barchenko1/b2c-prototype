package com.b2c.prototype.e2e.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestUtil {
    public static String readFile(final String fileName) throws IOException {
        StringBuilder buf = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/" + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            return buf.toString();
        }
    }
}
