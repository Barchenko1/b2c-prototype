package com.b2c.prototype.service.parallel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncProcessor implements IAsyncProcessor {

    private final Executor executor;

    public AsyncProcessor(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public Map<Class<?>, Object> process(Task... tasks) {
        Map<Class<?>, Object> resultMap = new HashMap<>();
        Arrays.stream(tasks)
                .map(task -> CompletableFuture.supplyAsync(task.getSupplier(), executor)
                        .thenAccept(result -> resultMap.put(task.getClazz(), result))
                )
                .forEach(CompletableFuture::join);

        return resultMap;
    }
}
