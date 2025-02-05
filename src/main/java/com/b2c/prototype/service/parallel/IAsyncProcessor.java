package com.b2c.prototype.service.parallel;

import java.util.Map;

public interface IAsyncProcessor {
    Map<Class<?>, Object> process(Task... tasks);
}
