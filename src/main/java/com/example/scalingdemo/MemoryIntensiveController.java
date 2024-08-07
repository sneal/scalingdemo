package com.example.scalingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * MemoryIntensiveController class to handle memory intensive operations.
 * This class demonstrates the use of a service to perform memory intensive operations asynchronously.
 */
@RestController
@RequestMapping("/memory")
public class MemoryIntensiveController {

    @Autowired
    private MemoryIntensiveService memoryIntensiveService;

    @GetMapping("/allocate")
    public Mono<String> allocateMemory() {
         memoryIntensiveService.performMemoryIntensiveOperationAsync();
        return Mono.just("Memory allocated");
    }
}
