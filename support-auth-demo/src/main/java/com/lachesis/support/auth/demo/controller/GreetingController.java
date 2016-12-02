package com.lachesis.support.auth.demo.controller;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.demo.vo.Greeting;

@RestController
@RequestMapping("/greetings")
public class GreetingController extends AbstractRestController{
	private AtomicLong count = new AtomicLong();
	
	@RequestMapping
	public Greeting getGreeting(@RequestParam(name="toname") String toName, HttpServletRequest request){
		
		return createNewGreeting(toName, "dummy");
	}

	
	private Greeting createNewGreeting(String counterparty, String owner){
		Greeting g = new Greeting();
		g.setId(String.valueOf(count.getAndIncrement()));
		g.setGreeting(UUID.randomUUID().toString());
		g.setCounterparty(counterparty);
		g.setOwner(owner);
		
		return g;
	}
}
