package com.example.scalingdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
public class HighLatencyController {

    @GetMapping("/high_latency")
    public String simulateHighLatency() {

            try {
                Thread.sleep(5000); // Simulates a high latency operation with 5 seconds delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "High latency operation completed";

    }

    @GetMapping("/simple")
    public Mono<String> simple() {
            System.out.println("Hello World");
            return Mono.just("High latency operation completed");

    }
}
