package com.itmo;

public class Main {

    public static void main(String[] args) {
	    LRUCache<Integer, Integer> lruCache= new LRUCache<>(2);
	    lruCache.put(1, 1);
	    lruCache.put(2, 2);

	    lruCache.get(1);
	    lruCache.put(3, 3);

        System.out.println(lruCache.get(1));
    }
}
