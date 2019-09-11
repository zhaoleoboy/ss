package com.ying.cache;

import org.apache.curator.shaded.com.google.common.cache.CacheBuilder;
import org.apache.curator.shaded.com.google.common.cache.CacheLoader;
import org.apache.curator.shaded.com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class LoadingCacheTest {

    /**
     * 测试LoadingCache的get
     */
    @Test
    public void testGetKey() throws ExecutionException {
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().maximumSize(3).build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                System.out.println(key + " is loaded from a cacheLoader!");
                return key + "value";
            }
        });
        System.out.println(loadingCache.get("key1"));
        System.out.println(loadingCache.get("key2"));
        System.out.println(loadingCache.get("key3"));
    }
}
