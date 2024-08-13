package com.app.usingThreeThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainThreadClass {

    private static final BlockingDeque<Integer> queue = new LinkedBlockingDeque<>();

    private static final List<Integer> numbers =  new ArrayList<>();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private static final Object lock = new Object();

    private static final int MAX_COUNTS = 10000;
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(4);


        for(int i=0;i<4;i ++) {
            executorService.submit(new ProduceNumbersThread(queue, numbers, MAX_COUNTS, counter, lock));
        }



        List<Future<List<Integer>>> futureList = new ArrayList<>();
        for(int i=0;i<4;i++){
            futureList.add(executorService.submit(new ConsumeNumbersThread(queue, numbers,lock)));
        }



        executorService.shutdown();
        // Shutting down executor service

        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());

        List<Integer> randomNumbers = new ArrayList<>();
        for(Future<List<Integer>> future : futureList) {
            try {
                randomNumbers.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        for(Integer random: randomNumbers) {
            System.out.println("random = " + random);
        }


    }
}
