package com.izayacity.concurrency;

import com.izayacity.concurrency.reentrantLock.Worker;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:         Francis Xirui Yang
 * Date:            1/13/19
 * Time:            5:35 PM
 * Version:        1.0
 * Email:           izayacity@gmail.com
 * Description: concurrency
 */
public class ReentrantLockTest {

    static final int MAX_T = 2;

    @Test
    public void worker() {
        ReentrantLock rel = new ReentrantLock();
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
        Runnable w1 = new Worker(rel, "Job1");
        Runnable w2 = new Worker(rel, "Job2");
        Runnable w3 = new Worker(rel, "Job3");
        Runnable w4 = new Worker(rel, "Job4");
        pool.execute(w1);
        pool.execute(w2);
        pool.execute(w3);
        pool.execute(w4);
        pool.shutdown();
    }
}
