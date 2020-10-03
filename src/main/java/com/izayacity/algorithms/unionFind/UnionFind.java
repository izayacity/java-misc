package com.izayacity.algorithms.unionFind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        10/3/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 *
 * @author francis
 */
public class UnionFind<T> {

    private final Map<T, T> map = new HashMap<>();

    public UnionFind(List<T> list) {
        for (T item : list) {
            this.map.put(item, item);
        }
    }

    public void union(T a, T b) {
        T p = this.find(a);
        T q = this.find(b);
        this.map.put(p, this.map.get(q));
    }

    public T find(T x) {
        while (this.map.get(x) != x) {
            T rootX = this.map.get(this.map.get(x));
            this.map.put(x, rootX);
            x = rootX;
        }
        return x;
    }
}
