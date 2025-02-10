package com.b2c.prototype.util;

import java.util.UUID;

public final class Util {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
