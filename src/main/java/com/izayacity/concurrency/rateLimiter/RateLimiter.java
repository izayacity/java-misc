package com.izayacity.concurrency.rateLimiter;

import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimiter {

    private long capacity;
    private long refillCountPerSecond;
    private volatile long lastRefillTimeStamp;
    private volatile long availableTokens;
    private final Lock lock = new ReentrantLock();

    public RateLimiter(long capacity, long windowTimeInSeconds) {
        this.capacity = capacity;
        this.lastRefillTimeStamp = System.currentTimeMillis();
        this.refillCountPerSecond = capacity / windowTimeInSeconds;
        this.availableTokens = 0;
    }

    public long getAvailableTokens() {
        return this.availableTokens;
    }

    public boolean tryConsume() {
        refill();

        if (this.availableTokens <= 0) {
            return false;
        }
        synchronized (this.lock) {
            if (this.availableTokens <= 0) {
                return false;
            }
            --this.availableTokens;
            return true;
        }
    }

    private void refill() {
        long now = System.currentTimeMillis();
        if (now <= this.lastRefillTimeStamp) {
            return;
        }
        synchronized (this.lock) {
            if (now > this.lastRefillTimeStamp) {
                long elapsedTime = now - this.lastRefillTimeStamp;
                // refill tokens for this duration
                long tokensToBeAdded = (elapsedTime / 1000) * this.refillCountPerSecond;
                if (tokensToBeAdded > 0) {
                    this.availableTokens = Math.min(this.capacity, this.availableTokens + tokensToBeAdded);
                    this.lastRefillTimeStamp = now;
                }
            }
        }
    }

    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter(2, 2);
        int sleepMillis = 100;

        Runnable producer = () -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                limiter.refill();
                System.out.println(LocalDateTime.now().toString() + " producer " + i + " tokens: " + limiter.getAvailableTokens());
            }
        };
        Runnable consumer = () -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalDateTime.now().toString() + " consumer " + i + " tokens: " + limiter.getAvailableTokens());
                boolean success = limiter.tryConsume();
                System.out.println(LocalDateTime.now().toString() + " consumer " + i + " success: " + success);
            }
        };
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
    }
}