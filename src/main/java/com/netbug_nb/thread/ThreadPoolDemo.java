package com.netbug_nb.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
	public static class MyTask implements Runnable {
		volatile int count = 0;

		@Override
		public void run() {
			count++;
			System.out.println(System.currentTimeMillis() / 1000 + ":Thread Name:" + Thread.currentThread().getName()
					+ ":count:" + count);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	public static void main(String[] args) {
		Runnable task = new MyTask();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
			executorService.submit(task);
		}
		System.out.println();
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
		for (int i = 0; i < 10; i++) {
			scheduledExecutorService.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);

		}
	}
}
