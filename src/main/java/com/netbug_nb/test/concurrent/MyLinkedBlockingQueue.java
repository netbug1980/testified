package com.netbug_nb.test.concurrent;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyLinkedBlockingQueue<E> {
	private LinkedList<E> list = new LinkedList<>();
	private int max = 10;
	private AtomicInteger size = new AtomicInteger();
	private ReentrantLock lock = new ReentrantLock();
	private Condition readCondition = lock.newCondition();
	private Condition writeCondition = lock.newCondition();

	public MyLinkedBlockingQueue() {
	}

	public MyLinkedBlockingQueue(int max) {
		this.max = max;
	}

	public void put(E e) {
		lock.lock();
		System.out.printf("%s执行中%n", Thread.currentThread().getName());
		if (size.get() >= max) {
			try {
				System.out.printf("%s进入等待%n", Thread.currentThread().getName());
				writeCondition.await();
				System.out.printf("%s继续执行%n", Thread.currentThread().getName());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		list.offer(e);
		size.incrementAndGet();
		readCondition.signal();
		System.out.printf("%s执行完毕：%s,队列大小%d%n", Thread.currentThread().getName(), e, this.size());
		lock.unlock();
	}

	public E take() {
		lock.lock();
		System.out.printf("%s执行中%n", Thread.currentThread().getName());
		if (size.get() <= 0) {
			try {
				System.out.printf("%s进入等待%n", Thread.currentThread().getName());
				readCondition.await();
				System.out.printf("%s继续执行%n", Thread.currentThread().getName());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		E e = list.poll();
		size.decrementAndGet();
		writeCondition.signal();
		System.out.printf("%s执行完毕：%s,队列大小%d%n", Thread.currentThread().getName(), e, this.size());
		lock.unlock();
		return e;
	}

	public int size() {
		return size.intValue();
	}
}
