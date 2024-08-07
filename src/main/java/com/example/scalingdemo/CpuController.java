package com.example.scalingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CpuController class to handle CPU intensive operations.
 * This class demonstrates auto-scaling in Tanzu Application Service (TAS) by simulating
 * a CPU intensive operation.
 */
@RestController
public class CpuController {

    @Autowired
    CpuIntensiveService complexSqrtOperation;


    /**
     * Endpoint to perform a CPU intensive operation.
     * This method will create a fixed thread pool with a specified number of threads
     * and submit tasks to simulate heavy computation.
     *
     * @return a message indicating completion of the CPU intensive operation.
     */
    @GetMapping("/cpu")
    public Mono<String> performCpuIntensiveOperation() {
        complexSqrtOperation.complexSqrtOperation();
        return Mono.just("CPU intensive operation completed");
    }

}
