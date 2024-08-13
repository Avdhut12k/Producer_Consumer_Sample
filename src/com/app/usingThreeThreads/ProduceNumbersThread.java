package com.app.usingThreeThreads;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ProduceNumbersThread implements Runnable {

    private BlockingDeque<Integer> queue;

    private List<Integer> numbers;

    private int max_counts;

    private AtomicInteger counter;

    private final Object lock;


    private static final Random random = new Random(10000);


    public ProduceNumbersThread(BlockingDeque<Integer> queue, List<Integer> numbers, int max_counts, AtomicInteger counter, Object lock) {
        this.queue = queue;
        this.numbers = numbers;
        this.max_counts = max_counts;
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run() {

        while (counter.get() < max_counts) {

            int currentCount = counter.incrementAndGet();
            if (currentCount >= max_counts) {
                break;
            }


            queue.offer(random.nextInt());
            System.out.println("counter = " + counter);
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());

            // Threads are executing parallely so once you produce the number u have to notifyAll() consumers.
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        // After all production of numbers done u have to again inform to consumer and offer -1 so that they know that it's end of production.

        synchronized (lock) {
            for (int i = 0; i < 4; i++) {
                queue.offer(-1);
            }
            lock.notifyAll();
        }
    }
}
