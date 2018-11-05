package com.doushi.library.thread;

public abstract class ZZSafeThread implements Runnable {

	public Object value;

	public ZZSafeThread() {

	}

	public ZZSafeThread(Object value) {
		this.value = value;
	}

	public abstract void deal();

	@Override
	public void run() {
		try {
			deal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
