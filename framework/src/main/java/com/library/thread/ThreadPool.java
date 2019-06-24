package com.library.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yang
 * @date 2014-5-30 上午9:15:19 线程池
 * //executorService = Executors.newScheduledThreadPool(5, new TaskThreadFactory(""));
 */
public class ThreadPool {

    private static ExecutorService executorService;

    public synchronized static void execute(Runnable runnable) {
        if (executorService == null) {
            //线程数量
            int size = 5;
            executorService = new ThreadPoolExecutor(size, size, 0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    new TaskThreadFactory());
        }
        executorService.execute(runnable);
    }
}
