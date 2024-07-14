package com.example.scalingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/memory")
public class MemoryIntensiveController {

    @Autowired
    private MemoryIntensiveService memoryIntensiveService;

    @GetMapping("/allocate")
    public CompletableFuture<String> allocateMemory() {
        return memoryIntensiveService.performMemoryIntensiveOperationAsync();
    }
}
