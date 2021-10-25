import com.itmo.LRUCache;
import org.junit.Test;
import org.junit.Assert;

import java.util.NoSuchElementException;

public class LRUTest {

    @Test
    public void empty() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(10);
        try {
            lruCache.get(1);
            Assert.fail();
        } catch (NoSuchElementException e) {
            Assert.assertEquals(e.getMessage(), "No such element in cache");
        }
    }

    @Test
    public void oneAdd() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(1);
        lruCache.put(1, 1);
        Assert.assertEquals(lruCache.get(1), Integer.valueOf(1));
    }

    @Test
    public void oneToForget() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(1);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        try {
            lruCache.get(1);
            Assert.fail();
        } catch (NoSuchElementException e) {
            Assert.assertEquals(e.getMessage(), "No such element in cache");
        }
    }

    @Test
    public void manyAddGet() {
        LRUCache<Integer, Integer> lruCache= new LRUCache<>(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);

        lruCache.get(1);
        lruCache.put(3, 3);

        Assert.assertEquals((Integer) 1, lruCache.get(1));
    }

    @Test
    public void emptyLRU() {
        try {
            LRUCache<Integer, Integer> lruCache = new LRUCache<>(-2);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Capacity should not be negative");
        }
    }

    @Test
    public void tryRemove() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(10);
        try {
            lruCache.remove(1);
            Assert.fail();
        } catch (NoSuchElementException e) {
            Assert.assertEquals(e.getMessage(), "No such element in cache");
        }
    }

    @Test
    public void clear() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(10);
        try {
            lruCache.put(1, 1);
            lruCache.clear();
            lruCache.get(1);
            Assert.fail();
        } catch (NoSuchElementException e) {
            Assert.assertEquals(e.getMessage(), "No such element in cache");
        }
    }

    @Test
    public void updateNode() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(1, 0);
        lruCache.put(3, 3);

        lruCache.get(1);

        Assert.assertEquals((Integer) 0, lruCache.get(1));
    }

    @Test
    public void correctRemovedValue() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(2);
        lruCache.put(1, 1);
        Assert.assertEquals((Integer) 1, lruCache.remove(1));
    }

    @Test
    public void correctPrevValue() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(2);
        lruCache.put(1, 1);
        Assert.assertEquals((Integer) 1, lruCache.put(1, 2));
    }


    @Test
    public void manyInserts() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(100);
        for (int i = 0; i < 150; i++) {
            lruCache.put(i, i);
        }
        for (int i = 50; i < 150; i++) {
            Assert.assertEquals((Integer) i, lruCache.get(i));
        }
        for (int i = 0; i < 50; i++) {
            try {
                lruCache.get(i);
                Assert.fail();
            } catch (NoSuchElementException e) {
                Assert.assertEquals(e.getMessage(), "No such element in cache");
            }
            try {
                lruCache.remove(i);
                Assert.fail();
            } catch (NoSuchElementException e) {
                Assert.assertEquals(e.getMessage(), "No such element in cache");
            }
        }
    }
}