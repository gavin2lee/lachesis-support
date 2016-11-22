package com.lachesis.support.auth.demo.controller;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.demo.vo.Greeting;

@RestController
@RequestMapping("/")
public class GreetingController {
	private AtomicLong count = new AtomicLong();
	
	@RequestMapping("/greetings")
	public Greeting getGreeting(@RequestParam(name="toname") String toName, HttpServletRequest request){
		
//		String token = determineToken(request);
//		String ip = determineIpAddress(request);
//		String url = determineUrlToRequest(token, ip);
//		
//		Map<String,String> vars = new HashMap<String,String>();
//		//vars.put("token", token);
//		//vars.put("ip", ip);
//		
//		RestTemplate  restTemplate = new RestTemplate();
//		String result = "";
//		try{
//			result = restTemplate.getForObject(url, String.class, vars);
//		}catch(HttpClientErrorException ex){
//			System.out.println(ex.getMessage());
//			System.out.println(ex.getResponseBodyAsString());
//			System.out.println(ex.getStatusCode());
//			System.out.println(ex.getStatusText());
//		}
//		SimpleUserVo vo = convertToObject(result);
//		
//		String owner = "dummy";
//		if(vo != null){
//			owner = vo.getUsername();
//		}
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
