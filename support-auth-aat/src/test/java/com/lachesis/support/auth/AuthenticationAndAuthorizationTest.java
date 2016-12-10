package com.lachesis.support.auth;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lachesis.support.objects.vo.auth.AuthenticationRequestVO;
import com.lachesis.support.objects.vo.auth.AuthenticationResponseVO;

//@Ignore
public class AuthenticationAndAuthorizationTest {
	static final Logger LOG = LoggerFactory.getLogger(AuthenticationAndAuthorizationTest.class);
	static final String AUTHC_HOST = "192.168.0.107";
	static final String DEMO_HOST = "192.168.0.107";
	String authcBaseUrl = String.format("http://%s:9090/authc/api/v1/tokens", AUTHC_HOST);
	String appServerBaseUrl = String.format("http://%s:9091/demo/api/v1/nurses",DEMO_HOST);

	RestTemplate restTemplate;
	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		restTemplate = new RestTemplate();
	}

	@Test
	public void testAuthcAndAuthzWithSufficientPermissions() throws Exception {
		String username = "283";
		String password = "123";

		LOG.debug("STEP 1: try to login...");
		LOG.debug(String.format("Login with %s", username));

		pause();

		AuthenticationResponseVO respVO = authenticate(username, password);
		LOG.debug(String.format("Token:%s", respVO.getToken()));

		pause();
		LOG.debug("STEP 2:try to submit business request with token headers...");
		LOG.debug(String.format("Authorization:token %s", respVO.getToken()));
		pause();

		ResponseEntity<String> result = listNurses(respVO);
		LOG.debug(result.getBody());

	}

	@Test
	public void testAuthcAndAuthzWithSufficientPermissionsInBatch() throws Exception {
		String username = "283";
		String password = "123";

		int maxRound = 10;

		for (int i = 0; i < maxRound; i++) {
			LOG.debug("STEP 1: try to login..." + i);
			LOG.debug(String.format("Login with %s", username));

			AuthenticationResponseVO respVO = authenticate(username, password);
			LOG.debug(String.format("Token:%s", respVO.getToken()));

			LOG.debug("STEP 2:try to submit business request with token headers...");
			LOG.debug(String.format("Authorization:token %s", respVO.getToken()));

			ResponseEntity<String> result = listNurses(respVO);
			LOG.debug(result.getBody());
		}

	}

	private ResponseEntity<String> listNurses(AuthenticationResponseVO respVO) {
		HttpEntity<String> requestEntity = prepareRequestEntityForListNurses(respVO);
		String url = appServerBaseUrl + "?deptid=1";
		ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		Assert.assertThat(respEntity, Matchers.notNullValue());
		return respEntity;
	}

	private HttpEntity<String> prepareRequestEntityForListNurses(AuthenticationResponseVO respVO) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		headers.add("Authorization", String.format("token %s", respVO.getToken()));

		HttpEntity<String> reqEntity = new HttpEntity<String>(headers);
		return reqEntity;
	}

	private void pause() throws IOException {
		// System.in.read();
	}

	private AuthenticationResponseVO authenticate(String username, String password)
			throws RestClientException, JsonProcessingException {
		AuthenticationRequestVO requestVO = new AuthenticationRequestVO(username, password);
		AuthenticationResponseVO respVO = restTemplate.postForObject(authcBaseUrl, prepareHttpEntity(requestVO),
				AuthenticationResponseVO.class);
		Assert.assertThat(respVO, Matchers.notNullValue());
		Assert.assertThat(respVO.getToken(), Matchers.notNullValue());

		return respVO;
	}

	private HttpEntity<String> prepareHttpEntity(Object requestParams) throws JsonProcessingException {
		String sJson = mapper.writeValueAsString(requestParams);
		HttpEntity<String> formEntity = new HttpEntity<String>(sJson, prepareHeaders());

		return formEntity;
	}

	private HttpHeaders prepareHeaders() {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		return headers;
	}
}
