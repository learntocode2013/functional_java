package org.learn.functional.java;

import com.google.common.flogger.FluentLogger;
import org.jooq.lambda.Unchecked;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.logging.Level;

public class TestCF {
    private final int POOL_SIZE = 2; //Runtime.getRuntime().availableProcessors();
    static private final FluentLogger logger = FluentLogger.forEnclosingClass();
    private final ArrayBlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(2, true);
    private final Executor executor = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE,
            1,
            TimeUnit.MINUTES,
            taskQueue,
            new ThreadPoolExecutor.AbortPolicy()
    );

    @Test
    @DisplayName("Verify that async CF task fails when task pool buffer is full")
    void demoCFFailure() throws ExecutionException, InterruptedException {
        Thread monitor = new Thread(() -> {
            while(true) {
                logger.at(Level.INFO).log(Thread.currentThread().getName() + " - task-queue-size: %s", taskQueue.size());
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "Queue-Monitor");
        monitor.start();

        new Thread(() -> {
            while(true) {
                CompletableFuture<String> future = submitRequest();
                if(future.isCompletedExceptionally()) {
                    logger.at(Level.SEVERE).log(Thread.currentThread().getName() + " - Failed attempt");
                    return;
                } else {
                    logger.at(Level.INFO).log(Thread.currentThread().getName() + " - Success attempt");
                }
            }
        },"Producer-1").start();

        new Thread(() -> {
            while(true) {
                CompletableFuture<String> future = submitRequest();
                if(future.isCompletedExceptionally()) {
                    logger.at(Level.SEVERE).log(Thread.currentThread().getName() + " - Failed attempt");
                    return;
                } else {
                    logger.at(Level.INFO).log(Thread.currentThread().getName() + " - Success attempt");
                }
            }
        },"Producer-2").start();

        for (int i = 0; i < 20; i++) {
            CompletableFuture<String> fut = submitRequest();
            logger.at(Level.INFO).log(Thread.currentThread().getName() + " - result: %s",fut.get());
        }
        monitor.join();
    }

    private CompletableFuture<String> submitRequest() {
        try {
            return CompletableFuture.supplyAsync(() -> fetchProductName(), executor);
        } catch (Exception e) {
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }

    }

    private String fetchProductName() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Mac-book";
    }
}
