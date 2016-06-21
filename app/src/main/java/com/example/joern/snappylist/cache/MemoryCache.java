package com.example.joern.snappylist.cache;

import android.util.LruCache;

/**
 * Created by joern on 21.06.2016.
 */
public class MemoryCache {

    private static final int SHARE_OF_APP_MEMORY = 8;

    private static MemoryCache instance;
    private LruCache<Object, Object> lru;

    private MemoryCache() {

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / SHARE_OF_APP_MEMORY;

        lru = new LruCache<Object, Object>(cacheSize);
    }

    public static MemoryCache getInstance() {

        if (instance == null) {

            instance = new MemoryCache();
        }

        return instance;

    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }

}
