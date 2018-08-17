package com.zhide.app.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hasee on 2018/2/27.
 */

public class ThreadPoolManager {
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private int maximumPoolSize;//最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
    private int corePoolSize;
    private long keepAliveTime = 10;//存活时间
    private TimeUnit unit = TimeUnit.MINUTES;
    private ThreadPoolExecutor executor;
    private ExecutorService fixedThreadPool;
    private ExecutorService singleThreadPool;
    private ExecutorService cachedThreadPool;
    private ExecutorService scheduledThreadPool;
    private ScheduledExecutorService scheduledExecutorService;

    private ThreadPoolManager() {
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        maximumPoolSize = corePoolSize;
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        executor.allowCoreThreadTimeOut(true);
    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            instance = new ThreadPoolManager();
        }
        return instance;
    }

    /**
     * FixThreadPool只有核心线程，并且数量固定的，也不会被回收，
     * 所有线程都活动时，因为队列没有限制大小，新任务会等待执行。
     *
     * @param nThreads 核心线程数
     * @return
     */
    public ExecutorService getFixedThreadPool(int nThreads) {
        if (fixedThreadPool == null) {
            fixedThreadPool = Executors.newFixedThreadPool(nThreads);
        }
        return fixedThreadPool;
    }

    /**
     * SingleThreadPool只有一个核心线程，确保所有任务都在同一线程中按顺序完成。
     * 因此不需要处理线程同步的问题。
     *
     * @return
     */
    public ExecutorService getSingleThreadPool() {
        if (singleThreadPool == null) {
            singleThreadPool = Executors.newSingleThreadExecutor();
        }
        return singleThreadPool;
    }

    /**
     * CachedThreadPool只有非核心线程，最大线程数非常大，所有线程都活动时，
     * 会为新任务创建新线程，否则利用空闲线程（60s空闲时间，过了就会被回收，
     * 所以线程池中有0个线程的可能）处理任务。
     *
     * @return
     */
    public ExecutorService getCachedThreadPool() {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }

    /**
     * （4个里面唯一一个有延迟执行和周期重复执行的线程池）,ScheduledThreadPool主要用于执行定时任务以及有固定周期的重复任务。
     *
     * @param corePoolSize 核心线程数
     * @return
     */
    public ExecutorService getScheduledThreadPool(int corePoolSize) {
        if (scheduledThreadPool == null) {

            scheduledThreadPool = Executors.newScheduledThreadPool(corePoolSize);
        }

        return scheduledThreadPool;
    }

    /**
     * （4个里面唯一一个有延迟执行和周期重复执行的线程池）,ScheduledThreadPool主要用于执行定时任务以及有固定周期的重复任务。
     *
     * @param corePoolSize 核心线程数
     * @return
     */
    public ScheduledExecutorService getSingleScheduledThreadPool(int corePoolSize) {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        return scheduledExecutorService;
    }

    /**
     * 获取线程池中活动线程的数量
     *
     * @return
     */
    public int getActiveThreadCount() {

        return executor.getActiveCount();
    }

    /**
     * 获取当前线程池的线程数量
     *
     * @return
     */
    public int getCurrentThreadPoolSize() {
        return executor.getPoolSize();
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null)
            return;
        executor.execute(runnable);
    }

    /**
     * 从线程池中移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable == null) return;

        executor.remove(runnable);
    }
}
