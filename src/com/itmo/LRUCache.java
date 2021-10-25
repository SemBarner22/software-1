package com.itmo;

import java.util.*;

import static org.junit.Assert.*;

public class LRUCache<T, R> {

    private final Map<T, Node> map = new HashMap<>();
    private final Node head = new Node();
    private final int capacity;

    private Node tail = null;
    private int listSize = 0;

    // If I remember correctly, HashMap has default capacity 16
    public LRUCache() {
        this.capacity = 16;
    }

    public LRUCache(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity should not be negative");
        }
        this.capacity = capacity;
    }

    public R get(Object key) {
        Node node = getNode(key);
        if (node != null) {
            return node.value;
        }
        throw new NoSuchElementException("No such element in cache");
    }

    public R put(T key, R value) {
        Node keyNode = getNode(key);
        if (keyNode != null) {
            R oldValue = keyNode.value;
            keyNode.value = value;
            return oldValue;
        } else {
            Node node = new Node(key, value);
            node.addFirst();
            removeIfNeeded();
            map.put(key, node);
            assertEquals(map.size(), listSize);
            return null;
        }
    }

    public R remove(Object key) {
        if (!map.containsKey(key)) {
            throw new NoSuchElementException("No such element in cache");
        }
        Node value = map.get(key);
        value.remove();
        map.remove(key);
        assertEquals(map.size(), listSize);
        return value.value;
    }

    public void clear() {
        map.clear();
        clearNodeList();
        assertEquals(map.size(), listSize);
    }

    private class Node {
        Node next = null;
        Node prev = null;
        T key = null;
        R value = null;

        Node() { }

        Node(T key, R value) {
            this.key = key;
            this.value = value;
        }

        public void addFirst() {
            listSize++;
            Node nextNode = head.next;
            if (nextNode == null) {
                tail = this;
            }
            prev = head;
            next = nextNode;
            head.next = this;
            if (next != null) {
                next.prev = this;
            }
            assertEquals(head.next, this);
            assertEquals(prev, head);
        }

        public void addLast() {
            listSize++;
            if (tail == null) {
                tail = this;
                this.prev = head;
                head.next = this;
            } else {
                tail.next = this;
                this.prev = tail;
            }
            assertNull(next);
        }

        public void remove() {
            listSize--;
            if (this == tail) {
                prev.next = null;
                if (prev == head) {
                    tail = null;
                } else {
                    tail = prev;
                }
                return;
            }
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }
            assertNotEquals(prev.next, this);
            assertNotEquals(next.prev, this);
            assertEquals(prev.next, next);
            assertEquals(next.prev, prev);
        }
    }

    public int size() {
        assertEquals(map.size(), listSize);
        return map.size();
    }

    public boolean isEmpty() {
        assertEquals(map.size(), listSize);
        return map.isEmpty();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    private Node getNode(Object key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.remove();
            node.addFirst();
            return node;
        }
        return null;
    }

    private void removeIfNeeded() {
        if (listSize > capacity) {
            map.remove(tail.key);
            tail.remove();
        }
        assert(listSize <= capacity);
    }

    private void clearNodeList() {
        head.next = null;
        tail = null;
        listSize = 0;
    }

}
