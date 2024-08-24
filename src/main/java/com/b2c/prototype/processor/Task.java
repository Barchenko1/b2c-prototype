package com.b2c.prototype.processor;

import lombok.Builder;
import lombok.Data;

import java.util.function.Supplier;

@Builder
@Data
public class Task {
    Supplier<?> supplier;
    Class<?> clazz;

    public Task(Supplier<?> supplier, Class<?> clazz) {
        this.supplier = supplier;
        this.clazz = clazz;
    }
}
