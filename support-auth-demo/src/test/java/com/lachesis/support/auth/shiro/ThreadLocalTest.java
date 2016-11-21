package com.lachesis.support.auth.shiro;

import org.junit.Test;

import com.lachesis.support.auth.demo.vo.Greeting;

public class ThreadLocalTest {

	@Test
	public void testThreadLocal() {
		ThreadLocal<Greeting> greetings = new ThreadLocal<Greeting>();

		Greeting g = new Greeting();
		g.setId(String.valueOf(1));

		greetings.set(g);

		Greeting g1 = greetings.get();

		System.out.println(g1.getId());

		Greeting g2 = greetings.get();

		System.out.println(g2.getId());
	}

	@Test
	public void testMultiThreadLocal() {
		final InheritableThreadLocal<Greeting> greetings = new InheritableThreadLocal<Greeting>();

		Greeting g = new Greeting();
		g.setId(String.valueOf(1));
		
		greetings.set(g);

		System.out.println(Thread.currentThread().toString() + ":" + greetings.get().getId());
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Greeting g1 = greetings.get();
				if (g1 == null) {
					System.out.println(Thread.currentThread().toString());
				} else {
					System.out.println(Thread.currentThread().toString() + ":" + g1.getId());
				}
			}

		});
		
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		
	}
}
