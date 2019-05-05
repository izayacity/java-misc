package com.izayacity.algorithms.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LFUCacheTest {

    @Test
    public void get() {
        LFUCache cache = new LFUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        int res = cache.get(1);
        int expected = 1;
        assertEquals(res, expected);

        cache.put(3, 3);
        res = cache.get(2);
        expected = -1;
        assertEquals(res, expected);

        res = cache.get(3);
        expected = 3;
        assertEquals(res, expected);

        cache.put(4, 4);
        res = cache.get(1);
        expected = -1;
        assertEquals(res, expected);

        res = cache.get(3);
        expected = 3;
        assertEquals(res, expected);

        res = cache.get(4);
        expected = 4;
        assertEquals(res, expected);
    }
}