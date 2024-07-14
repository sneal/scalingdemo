package com.example.scalingdemo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MemoryIntensiveService {

    @Async
    public CompletableFuture<String> performMemoryIntensiveOperationAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                performMemoryIntensiveOperation();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Memory allocation task started.";
        });
    }

    public void performMemoryIntensiveOperation() throws InterruptedException {
        List<byte[]> memoryList = new ArrayList<>();
        int sizePerMinute = 1 * 1024 * 1024; // 1 MB in bytes
        int totalSize = 0;

        while (true) {
            byte[] memory = new byte[sizePerMinute];
            for (int i = 0; i < memory.length; i++) {
                memory[i] = 1;
            }
            memoryList.add(memory);
            totalSize += sizePerMinute;
            System.out.println("Allocated total memory: " + totalSize / (1024 * 1024) + " MB");
            Thread.sleep(60000);
        }
    }
}
