package com.izayacity.algorithms.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomizedCollectionTest {

    @Test
    public void getRandomTest1() {
        // Init an empty collection.
        RandomizedCollection collection = new RandomizedCollection();
        // Inserts 1 to the collection. Returns true as the collection did not contain 1.
        boolean resBool = collection.insert(1);
        boolean expectedBool = true;
        assertEquals(resBool, expectedBool);

        // Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
        resBool = collection.insert(1);
        expectedBool = false;
        assertEquals(resBool, expectedBool);

        // Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
        resBool = collection.insert(2);
        expectedBool = true;
        assertEquals(resBool, expectedBool);

        // getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
        int res = collection.getRandom();
        System.out.println(res);

        // Removes 1 from the collection, returns true. Collection now contains [1,2].
        resBool = collection.remove(1);
        expectedBool = true;
        assertEquals(resBool, expectedBool);

        // getRandom should return 1 and 2 both equally likely.
        res = collection.getRandom();
        System.out.println(res);
    }

    @Test
    public void getRandomTest2() {
        RandomizedCollection collection = new RandomizedCollection();
        boolean res = collection.insert(0);
        boolean expected = true;
        assertEquals(res, expected);

        res = collection.insert(1);
        expected = true;
        assertEquals(res, expected);

        res = collection.insert(2);
        expected = true;
        assertEquals(res, expected);

        res = collection.insert(3);
        expected = true;
        assertEquals(res, expected);

        res = collection.insert(3);
        expected = false;
        assertEquals(res, expected);

        res = collection.remove(2);
        expected = true;
        assertEquals(res, expected);

        res = collection.remove(3);
        expected = true;
        assertEquals(res, expected);

        res = collection.remove(0);
        expected = true;
        assertEquals(res, expected);

        int result = collection.getRandom();
        System.out.println(result);

        result = collection.getRandom();
        System.out.println(result);

        result = collection.getRandom();
        System.out.println(result);

        result = collection.getRandom();
        System.out.println(result);
    }
}