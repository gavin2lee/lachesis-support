package com.lachesis.support.auth.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

@Ignore
public class AuthCenterClientTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String username = "283";
		String password = "123";
		
		
		String token="m9BICpac3bHIzAFhrbVyr1o95PtDOgsls+3YbS7AXPlZBhNAcNaDhQ==";
		String ip="0:0:0:0:0:0:0:1";
		String url = "http://127.0.0.1:9090/authcenter/api/v1/token?token="+token+"&&ip="+ip;
		Map<String, String> vars = new HashMap<String, String>();

		RestTemplate restTemplate = new RestTemplate();

		String result = restTemplate.getForObject(url,String.class,vars);

		System.out.println("RESULT:" + result);
	}

}
