package com.izayacity.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author:         Francis Xirui Yang
 * Date:            9/18/19
 * Time:            19:33 PM
 * Version:        1.0
 * Email:           izayacity@gmail.com
 * Description: Since these collections are backed by immutable arrays, a snapshot iterator can read the values in one
 * of these arrays (but never modify them) without danger of them being changed by another thread.
 */
public class CopyOnWriteTest {

    /**
     * The CopyOnWriteArrayList was created to allow for the possibility of safe iterating over elements even when the
     * underlying list gets modified. Because of the copying mechanism, the remove() operation on the returned Iterator
     * is not permitted – resulting with UnsupportedOperationException:
     */
    @Test
    public void copyOnWriteArrayListTest() {
        CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        // when we create an iterator for the CopyOnWriteArrayList,
        // we get an immutable snapshot of the data in the list at the time iterator() was called.
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);
        // Because of that, while iterating over it, we won't see the number 10 in the iteration
        List<Integer> result = new LinkedList<>();
        iterator.forEachRemaining(result::add);
        assertThat(result).containsOnly(1, 3, 5, 8);

        Iterator<Integer> iterator2 = numbers.iterator();
        List<Integer> result2 = new LinkedList<>();
        iterator2.forEachRemaining(result2::add);

        assertThat(result2).containsOnly(1, 3, 5, 8, 10);
    }
}
