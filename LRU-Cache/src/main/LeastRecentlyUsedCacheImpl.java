package main;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class LeastRecentlyUsedCacheImpl {

    LinkedHashSet<Integer> cache;  /** Data structure to implement cache */
    Integer MAX_CACHE_SIZE = 3;    /** Max size of the cache */
    static int faults;             /** Count of total no of page faults */ 

    /**
     * Cache constructor
     * 
     * @param cacheSize - size of the cache - Integer
     */
    public LeastRecentlyUsedCacheImpl(Integer cacheSize) {
        cache = new LinkedHashSet<Integer>();
        MAX_CACHE_SIZE = cacheSize;
    }

    public void refer(int page) {
        if(cache.contains(page)) { /** if the LinkedHashSet cache already contains the page */
            shuffle(page);
        } else {
            faultHandle(page);
        }
    }
    
    /**
     * This method is called when page doesn't already exist in the cache.
     * If cache size is equals to the maximum allowed cache size, then remove the first
     * element of the cache and add the new requested page in the cache.
     * 
     * If cache size if less than the maximum size, then add the page in the cache.
     * 
     * @param page - cache element - int
     */
    private void faultHandle(int page) {
        if (cache.size() == MAX_CACHE_SIZE) {
            int first = cache.iterator().next();
            cache.remove(first);
            cache.add(page);
        } else {
            cache.add(page);
        }
        faults++;
    }

    /**
     * This method is called when page already exists in the cache. In this case, the
     * priority of the requested page needs to be increased in the cache which means, the
     * requested page needs to be moved from existing index to the top index of the cache
     * as it has been recently added to it.
     * 
     * @param page - cache element 0 int
     */
    private void shuffle(int page) {
        cache.remove(page);
        cache.add(page);
    }

    public static void main(String[] args) {
        
        LeastRecentlyUsedCacheImpl cacheImpl = new LeastRecentlyUsedCacheImpl(3);
        Arrays.asList(3,2,1,3,4,1,6,2,4,3,4,2,1,4,5,2,1,3,4)
            .stream()
            .forEachOrdered(p -> cacheImpl.refer(p));
        
        System.out.println("Pages in cache: " + cacheImpl.cache);
        System.out.println("Number of page faults: " + faults);
    }
}
