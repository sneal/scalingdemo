package com.example.scalingdemo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * MemoryIntensiveService class to perform memory intensive operations.
 * This class demonstrates how to allocate memory continuously in an asynchronous manner.
 */
@Service
public class MemoryIntensiveService {

    /**
     * Asynchronously starts a memory intensive operation.
     * This method wraps the memory intensive operation in a CompletableFuture to run it asynchronously.
     *
     * @return a CompletableFuture containing a message indicating the status of the operation.
     */
    @Async
    public void performMemoryIntensiveOperationAsync() {


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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

    }


}
