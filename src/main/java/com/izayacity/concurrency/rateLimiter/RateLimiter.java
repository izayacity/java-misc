package com.izayacity.concurrency.rateLimiter;

import java.time.LocalDateTime;

public class RateLimiter {

    private long capacity;

    private long lastRefillTimeStamp;

    private long refillCountPerSecond;

    private long availableTokens;

    public RateLimiter(long capacity, long windowTimeInSeconds) {
        this.capacity = capacity;
        lastRefillTimeStamp = System.currentTimeMillis();
        refillCountPerSecond = capacity / windowTimeInSeconds;
        availableTokens = 0;
    }

    public long getAvailableTokens() {
        return this.availableTokens;
    }

    public boolean tryConsume() {
        refill();

        if (availableTokens > 0) {
            --availableTokens;
            return true;
        } else {
            return false;
        }
    }

    private void refill() {
        long now = System.currentTimeMillis();
        if (now > lastRefillTimeStamp) {
            long elapsedTime = now - lastRefillTimeStamp;
            // refill tokens for this duration
            long tokensToBeAdded = (elapsedTime / 1000) * refillCountPerSecond;
            if (tokensToBeAdded > 0) {
                availableTokens = Math.min(capacity, availableTokens + tokensToBeAdded);
                lastRefillTimeStamp = now;
            }
        }
    }

    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter(2, 2);
        Runnable producer = () -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
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
                    Thread.sleep(1000);
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