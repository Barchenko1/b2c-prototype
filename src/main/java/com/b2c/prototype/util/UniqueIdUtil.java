package com.b2c.prototype.util;

import java.util.UUID;

public final class UniqueIdUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
