package com.izayacity.concurrency;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SolutionTest {

    @Test
    public void printFooBar1() {
        Semaphore s1 = new Semaphore(0);
        Semaphore s2 = new Semaphore(1);
        AtomicInteger count = new AtomicInteger();

        Runnable r1 = () -> {
            while (count.get() < 20) {
                try {
                    s2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1:" + count.getAndIncrement());
                s1.release();
            }
        };
        Runnable r2 = () -> {
            while (count.get() < 20) {
                try {
                    s1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2:" + count.getAndIncrement());
                s2.release();
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }

    @Test
    public void printFooBar2() {
        AtomicInteger count = new AtomicInteger();
        final Object lock = new Object();

        Runnable r1 = () -> {
            while (count.get() < 20) {
                synchronized (lock) {
                    if (count.get() % 2 == 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("r1:" + count.getAndIncrement());
                    lock.notifyAll();
                }
            }
        };
        Runnable r2 = () -> {
            while (count.get() < 20) {
                synchronized (lock) {
                    if (count.get() % 2 == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("r2:" + count.getAndIncrement());
                    lock.notifyAll();
                }
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }

    @Test
    public void dining() {
        for (int i = 0; i < 10; i++) {
            doDining();
        }
    }

    private void doDining() {
        int n = 5;
        Lock[] forks = new Lock[n];
        Semaphore semaphore = new Semaphore(4);

        for (int i = 0; i < n; i++) {
            forks[i] = new ReentrantLock();
            int finalI = i;

            Runnable runnable = () -> {
                tryEat(finalI, forks, semaphore);
            };
            Thread t = new Thread(runnable);
            t.start();
        }
    }

    private void tryEat(int id, Lock[] forks, Semaphore semaphore) {
        int n = forks.length;
        int right = (id + n - 1) % n;

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pickLeft(id, forks);
        pickRight(right, forks);
        System.out.println("person " + id + " can eat now");

        putRight(right, forks);
        putLeft(id, forks);
        semaphore.release();
    }

    private void pickLeft(int id, Lock[] forks) {
        forks[id].lock();
        System.out.println("person " + id + " picked the left fork");
    }

    private void pickRight(int id, Lock[] forks) {
        forks[id].lock();
        System.out.println("person " + id + " picked the right fork");
    }

    private void putLeft(int id, Lock[] forks) {
        System.out.println("person " + id + " put down the left fork");
        forks[id].unlock();
    }

    private void putRight(int id, Lock[] forks) {
        System.out.println("person " + id + " put down the right fork");
        forks[id].unlock();
    }
}
