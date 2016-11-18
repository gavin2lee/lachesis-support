package com.lachesis.support.auth.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lachesis.support.auth.demo.vo.Greeting;
import com.lachesis.support.auth.demo.vo.SimpleUserVo;

@RestController
@RequestMapping("/greetings")
public class PesudoController {
	private AtomicLong count = new AtomicLong();
	
	@RequestMapping("/greeting")
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
	
	private SimpleUserVo convertToObject(String json){
		ObjectMapper mapper = new ObjectMapper();
		try {
			SimpleUserVo vo = mapper.readValue(json, SimpleUserVo.class);
			return vo;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String determineUrlToRequest(String token, String ip){
		return String.format("http://localhost:9090/authcenter/v1/auth?token=%s&&ip=%s", token, ip);
	}
	
	private String determineToken(HttpServletRequest request){
		String authrization = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = authrization.substring(6);
		
		return token;
	}
	
	private String determineIpAddress(HttpServletRequest request){
		return request.getRemoteAddr();
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
