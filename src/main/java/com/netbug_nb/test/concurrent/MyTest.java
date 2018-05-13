package com.netbug_nb.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyTest {
	MyLinkedBlockingQueue<String> queue = new MyLinkedBlockingQueue<String>();

	class Read implements Runnable {

		@Override
		public void run() {
			queue.take();
		}

	}

	class Write implements Runnable {
		private String str = null;

		public Write(String str) {
			this.str = str;
		}

		@Override
		public void run() {
			queue.put(str);
		}

	}

	public static void main(String[] args) {
		MyTest myTest = new MyTest();
		
		ExecutorService pool = new ThreadPoolExecutor(10, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>() );
		ExecutorService pool2 = new ThreadPoolExecutor(10, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>() );
		
		for (int i = 0; i < 50; i++) {
			Write r = myTest.new Write("C" + i);
			pool.submit(r);
		}
		for (int i = 0; i < 51; i++) {
			Read r = myTest.new Read();
			pool2.submit(r);
		}
		try {
			Thread.currentThread().sleep(1000 * 60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
