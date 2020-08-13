package com.inbiznetcorp.acs.framework.websocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class UMSExecutorService
{
	static ExecutorService 		executorService 	= null;
	static ThreadPoolExecutor 	threadPoolExecutor 	= null;

	public static void addCallRunable(Runnable callRunnable)
	{
		if(executorService == null)
		{
			executorService 	= Executors.newFixedThreadPool(Integer.valueOf(100));
			threadPoolExecutor	= (ThreadPoolExecutor) executorService;

		}
		executorService.submit(callRunnable);
	}
	
	public static void executorShutdown()
	{
		executorService.shutdownNow();
	}

	public static int iActiveCountCount()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getActiveCount();
	}

	public static int iCorePoolSize()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getCorePoolSize();
	}

	public static int iMaximumPoolSize()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getMaximumPoolSize();
	}

	public static int iPoolSize()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getPoolSize();
	}

	public static long iTaskCount()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getTaskCount();
	}
}
