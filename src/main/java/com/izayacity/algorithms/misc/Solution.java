package com.izayacity.algorithms.misc;

import java.util.*;

class Solution {
    public Solution() {
    }

    long fibonacci(int n) {
        if (n == 0) {
            return 0;
        }
        long a = 0;
        long b = 1;

        for (int i = 2; i < n; i++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return a + b;
    }

//	public static void main(String[] args) {
//		Scanner in = new Scanner(System.in);
//		int n = in.nextInt();
//		List<Integer> theList = new ArrayList<>();
//
//		for(int i = 0; i < n; i++) {
//			Integer theValue = in.nextInt();
//			int lo = 0;
//			int hi = theList.size();
//
//			while(lo < hi) {
//				int median = (lo + hi) / 2;
//
//				if(theValue >= theList.get(median)) {
//					lo = median + 1;
//					// increase hi to get out of the while loop
//					if(median + 1 < theList.size() && theValue <= theList.get(median + 1)) {
//						hi = median + 1;
//					}
//				} else {
//					hi = median;
//					if(median > 0 && theValue >= theList.get(median - 1)) {
//						lo = median;
//					}
//				}
//			}
//
//			theList.add(lo, theValue);
//			System.out.println(String.format("%.1f", getMedianValue(theList)));
//		}
//	}

    public static float getMedianValue(List<Integer> list) {
        if (list.isEmpty()) {
            return 0;
        } else if (list.size() % 2 == 1) {
            return list.get((list.size()) / 2);
        } else {
            return (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2f;
        }
    }

    public List<Integer> deductSuppliesAny(List<Integer> list, int count) {
        int sum = listSum(list);

        if (sum < count) {
            return null;
        } else if (sum == count) {
            return Arrays.asList(0, 0, 0, 0);
        }

        LinkedHashMap<Integer, Integer> lMap = new LinkedHashMap<Integer, Integer>();

        for (int i = 0; i < list.size(); i++) {
            lMap.put(i, list.get(i));
        }

        return list;
    }

    public static int listSum(List<Integer> list) {
        int sum = 0;
        for (int val : list) {
            sum += val;
        }
        return sum;
    }
}
