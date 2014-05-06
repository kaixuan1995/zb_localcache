package com.zhangbin.common.local;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestLock {

	public static void main(String[] args) {
		ReadWriteLock lock = new ReentrantReadWriteLock();
		Lock readLock = lock.readLock();
		Lock writeLock = lock.writeLock();
		/*writeLock.lock();
		System.out.println("get write lock");
		readLock.lock();
		System.out.println("get read lock");
		readLock.unlock();
		System.out.println("release read lock");
		writeLock.unlock();
		System.out.println("release write lock");*/
		
		writeLock.lock();
		System.out.println("get write lock");
		writeLock.lock();
		System.out.println("get write lock");
		writeLock.unlock();
		System.out.println("release write lock");
		writeLock.unlock();
		System.out.println("release write lock");
		
		// readlock获取到之后不能在获取writelock
		/*readLock.lock();
		System.out.println("get read lock");
		writeLock.lock();
		System.out.println("get write lock");
		writeLock.unlock();
		System.out.println("release write lock");
		readLock.unlock();
		System.out.println("release read lock");
*/
	}
}
