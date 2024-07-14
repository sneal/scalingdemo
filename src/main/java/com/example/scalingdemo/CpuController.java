package com.example.scalingdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class CpuController {

    private static final int NUM_THREADS = 4;

    @GetMapping("/cpu")
    public String performCpuIntensiveOperation() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(() -> {
                // Simulating heavy computation
                double result = 0;
                for (int j = 0; j < 1_000_000_000; j++) {
                    result += Math.sqrt(j);
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "CPU intensive operation completed";
    }
}
