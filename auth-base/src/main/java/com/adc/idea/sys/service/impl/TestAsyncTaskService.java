package com.adc.idea.sys.service.impl;

import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestAsyncTaskService {

	@Async
	public void sleep() {
		try {
			System.out.println(Thread.currentThread().getName() + ":+++++++++++++++我睡觉了+++++++++++++++ : " + new Date());
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ":+++++++++++++++我睡醒了+++++++++++++++ : " + new Date());
	}

	@Async
	public void play() {
		System.out.println(Thread.currentThread().getName() + ":I am playing... : " + new Date());
	}

	@Async
	public void task() {
		sleep();
		System.out.println("Now: " + new Date());
		play();
	}
}
