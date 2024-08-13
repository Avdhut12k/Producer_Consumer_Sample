package com.app.usingThreeThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;

public class ConsumeNumbersThread implements Callable<List<Integer>> {

    private BlockingDeque<Integer> queue;

    private List<Integer> numbers;

    private final Object lock;

    public ConsumeNumbersThread(BlockingDeque<Integer> queue, List<Integer> numbers, Object lock) {
        this.queue = queue;
        this.numbers = numbers;
        this.lock = lock;
    }

    @Override
    public List<Integer> call() throws Exception {

        List<Integer> localRandom = new ArrayList<>();

        while (true) {
            synchronized (lock) {
                if (queue.isEmpty()) {
                    lock.wait();
                }
            }

                Integer poppedNumber = queue.poll();

                    if(poppedNumber == null)
                        continue;

                    if(poppedNumber == -1) {
                        break;
                    }

                    localRandom.add(poppedNumber);

                }

        synchronized (numbers) {
            numbers.addAll(localRandom);
        }

            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            return localRandom;
        }
}
