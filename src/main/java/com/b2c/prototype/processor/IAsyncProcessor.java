package com.b2c.prototype.processor;

import java.util.Map;

public interface IAsyncProcessor {
    Map<Class<?>, Object> process(Task... tasks);
}
