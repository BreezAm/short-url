package com.breez.shorturl.util;

import java.util.concurrent.*;

public class ThreadPoolUtil {

    /**
     * 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
     */
    private static final int SIZE_CORE_POOL = 5;
    /**
     * 线程池维护线程的最大数量
     */
    private static final int SIZE_MAX_POOL = 10;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final long ALIVE_TIME = 2000;
    /**
     * 线程缓冲队列
     */
    private static final BlockingQueue<Runnable> BQUEUE = new ArrayBlockingQueue<Runnable>(100);
    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            SIZE_CORE_POOL,
            SIZE_MAX_POOL,
            ALIVE_TIME,
            TimeUnit.MILLISECONDS,
            BQUEUE,
            new ThreadPoolExecutor.CallerRunsPolicy());
    public static ThreadPoolExecutor threadPool;

    static {
        POOL.prestartAllCoreThreads();
    }

    public static void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }
    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
                    return threadPool;
                }
                return threadPool;
            }
        }
    }

    public static ThreadPoolExecutor getPool() {
        return POOL;
    }

}
