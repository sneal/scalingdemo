package com.example.scalingdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class HighLatencyController {

    @GetMapping("/high_latency")
    public CompletableFuture<String> simulateHighLatency() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // Simulates a high latency operation with 5 seconds delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "High latency operation completed";
        });
    }

    @GetMapping("/simple")
    public CompletableFuture<String> simple() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello World");
            return "High latency operation completed";
        });
    }
}
