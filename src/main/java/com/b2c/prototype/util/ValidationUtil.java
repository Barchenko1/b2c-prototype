package com.b2c.prototype.util;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtil {
    public boolean scriptPresent(final String str) {
        String[] arr = {"%3C", "%3E", "<script>", "</script>", "alert(", "eval(", "%28", "%29", "IMG", "SRC",
                "javascript", "onmouseover", "onerror", "iframe", "ONLOAD", "<a>", "<div>", "%253C", "%253E", "HREF", "<body>",
                "<STYLE>", "<svg>", "&gt", "&lt", "&quot", "onblur", "onmousemove", "%22", "%27"};
        return StringUtils.containsAny(StringUtils.trim(str).toLowerCase(), arr);
    }
}
