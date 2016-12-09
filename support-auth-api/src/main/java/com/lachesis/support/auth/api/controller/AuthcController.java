package com.lachesis.support.auth.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.api.common.AuthBizErrorCodes;
import com.lachesis.support.auth.api.exception.AuthenticationException;
import com.lachesis.support.auth.api.vo.AuthResponse;
import com.lachesis.support.auth.api.vo.AuthenticationRequest;
import com.lachesis.support.auth.api.vo.AuthenticationResponse;
import com.lachesis.support.auth.service.CentralizedAuthSupporter;
import com.lachesis.support.auth.vo.UserDetails;

@RestController
public class AuthcController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthcController.class);

	@Autowired
	private CentralizedAuthSupporter authSupporter;

	@RequestMapping(value = "tokens", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.POST)
	public AuthResponse authenticate(@RequestBody AuthenticationRequest tokenRequest, HttpServletRequest request) {
		logAuthenticate(tokenRequest);
		validateAuthenticationRequest(tokenRequest);

		String ip = determineTerminalIpAddress(request);

		String token = internalAuthenticate(tokenRequest.getUsername(), tokenRequest.getPassword(), determineTerminalIpAddress(request));
		UserDetails userDetails = prepareUserDetails(token, ip);
		return convertAuthenticationResult(token, userDetails.getId());
	}
	
	private void logAuthenticate(AuthenticationRequest request){
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("authenticate for [username:%s]", request.getUsername()));
		}
	}
	
	private void validateAuthenticationRequest(AuthenticationRequest request){
		if (isBlank(request.getUsername()) || isBlank(request.getPassword())) {
			LOG.error(String.format("errors with [username:%s]", request.getUsername()));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_ARGS, "用户名或密码为空");
		}
	}
	
	private String internalAuthenticate(String userid, String psword,String ip){
		String token = authSupporter.authenticate(userid, psword, ip);
		if (isBlank(token)) {
			LOG.error(String.format("authenticating failed for [userid:%s, ip:%s]", userid, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED, "认证错误");
		}
		
		return token;
	}
	
	private UserDetails prepareUserDetails(String token, String ip){
		UserDetails userDetails = authSupporter.authorize(token, ip);
		if (userDetails == null) {
			LOG.error("cannot get userdetails with token:" + token);
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED, "认证失败");
		}
		
		return userDetails;
	}
	
	private AuthenticationResponse convertAuthenticationResult(String token, String userId){
		AuthenticationResponse tokenResp = new AuthenticationResponse();
		tokenResp.setToken(token);
		tokenResp.setUserId(userId);
		
		return tokenResp;
	}
	
	private String determineTerminalIpAddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		return ip;
	}
	
	private boolean isBlank(String s) {
		return StringUtils.isBlank(s);
	}
}
