package com.lachesis.support.auth.context.comm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.lachesis.support.auth.common.vo.AuthorizationResponseVO;
import com.lachesis.support.auth.context.comm.AuthCenterClient;
import com.lachesis.support.auth.context.comm.TokenAuthorizationException;

@Component("restTemplateAuthCenterClient")
public class RestTemplateAuthCenterClient implements AuthCenterClient {
	private static final Logger LOG = LoggerFactory.getLogger(RestTemplateAuthCenterClient.class);
	@Autowired
	private RestTemplate restTemplate;

	@Value("${support.auth.context.authc.authorize.url}")
	private String authorizingUrl;

	@Override
	public AuthorizationResponseVO authorize(String token, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("authorization api:" + authorizingUrl);
			LOG.debug(String.format("authorize for [token:%s,ip:%s]", token, terminalIpAddress));
		}
		String url = String.format("%s/%s?ip=%s", authorizingUrl, token, terminalIpAddress);

		AuthorizationResponseVO resp = null;

		try {
			resp = restTemplate.getForObject(url, AuthorizationResponseVO.class);
		} catch (Exception e) {
			LOG.error("errors while communicating with auth center", e);
			throw new TokenAuthorizationException(e);
		}
		
		if(resp == null){
			throw new TokenAuthorizationException();
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("%s", resp.toString()));
		}
		return resp;
	}
}
