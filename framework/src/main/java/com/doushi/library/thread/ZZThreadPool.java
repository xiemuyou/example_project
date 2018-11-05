package com.doushi.library.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yang
 * @date 2014-5-30 上午9:15:19 线程池
 */
public class ZZThreadPool {

	private static ExecutorService executorService;

	public synchronized static void execute(Runnable runnable) {
		if (executorService == null) {
			executorService = Executors.newScheduledThreadPool(5);
		}
		executorService.execute(runnable);
	}

	public static ExecutorService getExecutorService() {
		return executorService;
	}
}
