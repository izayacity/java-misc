package com.izayacity.concurrency.locks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/4/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
public class CasTest {

    private static int p = 0;

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(5);
        AtomicInteger i = new AtomicInteger(0);
        long time = System.currentTimeMillis();

        ExecutorService pool = Executors.newFixedThreadPool(5);
        for(int j = 0; j < 5; j++) {
            pool.execute(() -> {
                for(int k = 0; k < 100; k++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    p++;                // not atomic
                    i.getAndIncrement();// Call atom plus class 1
                }
                latch.countDown();
            });
        }
        latch.await();// to ensure that all sub-thread execution is complete
        System.out.println(System.currentTimeMillis() - time);
        System.out.println("p=" + p);
        System.out.println("i=" + i);
        pool.shutdown();
    }
}
