package com.izayacity.algorithms.collections;
import java.util.*;

public class BinaryHeap<T> {

    private List<T> heapList = new ArrayList<>();

    public BinaryHeap () {
    }

    private static int parentIndex(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("parentIndex invalid i");
        }
        return (i - 1) / 2;
    }

    private static int leftChildIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("leftChildIndex invalid i");
        }
        return 2 * i + 1;
    }
    private static int rightChildIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("rightChildIndex invalid i");
        }
        return 2 * i + 2;
    }

    public boolean lessThan(T o1, T o2) {
        // todo
        return (int)o1 < (int)o2;
    }

    public void add(T data) {
        this.heapList.add(data);

        for (int i = this.heapList.size() - 1;
             i > 0 && lessThan(this.heapList.get(parentIndex(i)), this.heapList.get(i)); ) {
            T temp = this.heapList.get(i);
            int p0 = parentIndex(i);
            heapList.set(i, this.heapList.get(p0));
            this.heapList.set(p0, temp);
            i = p0;
        }
    }

    public T peek() {
        if (this.heapList.isEmpty()) {
            return null;
        }
        return this.heapList.get(0);
    }

    public T poll() {
        if (this.heapList.isEmpty()) {
            return null;
        }
        T head = this.heapList.get(0);
        int N = this.heapList.size();
        this.heapList.set(0, this.heapList.get(N - 1));
        this.maxHeapify(0);
        return head;
    }

    private void maxHeapify(int index) {
        int l0 = leftChildIndex(index);
        int r0 = rightChildIndex(index);
        int largest = index;
        int size = this.heapList.size();

        if (l0 < size && lessThan(this.heapList.get(largest), this.heapList.get(l0))) {
            largest = l0;
        }
        if (r0 < size && lessThan(this.heapList.get(largest), this.heapList.get(r0))) {
            largest = r0;
        }
        if (largest == index) {
            return;
        }
        T temp = this.heapList.get(index);
        this.heapList.set(index, this.heapList.get(largest));
        this.heapList.set(largest, temp);
        this.maxHeapify(largest);
    }

    public List<T> getHeapList() {
        return this.heapList;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        List<Integer> inputList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            int val = rand.nextInt(100);
            inputList.add(val);
            heap.add(val);
        }
        System.out.println(inputList);
        System.out.println(heap.getHeapList());
    }
}
