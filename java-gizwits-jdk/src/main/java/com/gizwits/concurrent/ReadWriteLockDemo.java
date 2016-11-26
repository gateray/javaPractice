package com.gizwits.concurrent;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by feel on 2016/11/7.
 * <p>
 * 读读共享，写写互斥。
 * <p>
 * 读不互斥
 * 读写互斥
 * 写写互斥
 */
public class ReadWriteLockDemo implements Runnable {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();

    private static HashMap<String, Integer> map = new HashMap();

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getId() + "进入....");
            readLock.lock(); //首先开启读锁，从缓存中去取
            Integer count = map.get("count");
            System.out.println(count);
            readLock.unlock();
            writeLock.lock();
            try {
                map.put("count", map.get("count") + 1);
                System.out.println("进入写...");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                writeLock.unlock();
                //然后再上读锁
                readLock.lock();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {


        map.put("count", 100);
        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {

            service.submit(new Thread(new ReadWriteLockDemo()));
        }

        service.shutdown();
        service.awaitTermination(1000, TimeUnit.SECONDS);

        System.out.println("value....." + map.get("count"));


    }
}
