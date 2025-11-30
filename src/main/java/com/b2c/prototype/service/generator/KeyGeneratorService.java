package com.b2c.prototype.service.generator;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class KeyGeneratorService implements IKeyGeneratorService {

    @Override
    public String generateKey(String table) {
        String base = table.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");

        // Completely random UUID → new suffix on every call
        String suffix = UUID.randomUUID()
                .toString()
                .replace("-", "")   // compact
                .substring(0, 8);   // shorten if you like

        return base + "-" + suffix;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
