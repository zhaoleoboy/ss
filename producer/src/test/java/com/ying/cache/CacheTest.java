package com.ying.cache;

import org.apache.curator.shaded.com.google.common.cache.Cache;
import org.apache.curator.shaded.com.google.common.cache.CacheBuilder;
import org.apache.curator.shaded.com.google.common.cache.RemovalListener;
import org.apache.curator.shaded.com.google.common.cache.RemovalNotification;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheTest {

    /**
     * 测试通过getIfPresent从cache读取缓存
     */
    @Test
    public void testGetFromCache() {
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        cache.put("hello", "hello, guava cache");
        Assert.assertEquals("hello, guava cache", cache.getIfPresent("hello"));
    }

    /**
     * 测试guava cahe的最大容量
     */
    @Test
    public void testGuavaLimit(){
        Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(2).build();
        cache.put("key1","value1");
        cache.put("key2","value2");
        cache.put("key3","value3");
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
        System.out.println("第二个值：" + cache.getIfPresent("key2"));
        System.out.println("第三个值：" + cache.getIfPresent("key3"));
    }

    /**
     * 测试guava cache的write过期时间
     * @throws InterruptedException
     */
    @Test
    public void testExpireWriteTime() throws InterruptedException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
        cache.put("key1","value1");
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
        Thread.sleep(3000);
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
    }

    /**
     * 测试guava cache的access过期时间
     * @throws InterruptedException
     */
    @Test
    public void testExpireAccessTime() throws InterruptedException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS).build();
        cache.put("key1","value1");
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
        Thread.sleep(3000);
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
    }

    /**
     * 测试guava cache的invalidate删除缓存值
     */
    @Test
    public void testInvalidate(){
        Cache<String,String> cache = CacheBuilder.newBuilder().build();
        Object value = new Object();
        cache.put("key1","value1");
        cache.put("key2","value2");
        cache.put("key3","value3");

        List<String> list = new ArrayList<String>();
        list.add("key1");
        list.add("key2");

        cache.invalidateAll(list);//批量清除list中全部key对应的记录
        System.out.println(cache.getIfPresent("key1"));
        System.out.println(cache.getIfPresent("key2"));
        System.out.println(cache.getIfPresent("key3"));
    }

    /**
     * 测试guava cache删除缓存监听
     */
    @Test
    public void testRemoveListener(){
        Cache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(2).removalListener(new RemovalListener<String, Object>() {
            @Override
            public void onRemoval(RemovalNotification<String, Object> removalNotification) {
                System.out.println("删除cache:" + removalNotification.getKey() + "," + removalNotification.getValue());
            }
        }).build();
        cache.put("key1","value1");
        cache.put("key2","value2");
        cache.put("key3","value3");
        cache.put("key4","value3");
        cache.put("key5","value3");
        cache.put("key6","value3");
        cache.put("key7","value3");
        cache.put("key8","value3");
    }

    /**
     * 测试guava cache的数据统计功能
     */
    @Test
    public void testStatistics(){
        Cache<String,String> cache = CacheBuilder.newBuilder().recordStats().build();
        Object value = new Object();
        cache.put("key1","value1");
        cache.put("key2","value2");
        cache.put("key3","value3");

        List<String> list = new ArrayList<String>();
        list.add("key1");
        list.add("key2");

        cache.invalidateAll(list);//批量清除list中全部key对应的记录
        cache.getIfPresent("key1");
        cache.getIfPresent("key2");
        cache.getIfPresent("key3");
        cache.getIfPresent("key4");
        cache.getIfPresent("key5");
        cache.getIfPresent("key6");

        System.out.println(cache.stats());
    }


}
