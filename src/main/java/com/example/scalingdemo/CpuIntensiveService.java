package com.example.scalingdemo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class CpuIntensiveService {

    private static final int NUM_THREADS = 4;

    @Async
    public void complexSqrtOperation() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(() -> {
                // Simulating heavy computation
                double result = 0;
                for (int j = 0; j < 1_000_000_000; j++) {
                    result += Math.sqrt(j);
                    System.out.println("square root of"+j+"  is  "+result);
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
