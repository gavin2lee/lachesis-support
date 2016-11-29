package com.lachesis.support.auth.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.api.common.AuthBizErrorCodes;
import com.lachesis.support.auth.api.exception.AuthenticationException;
import com.lachesis.support.auth.api.vo.AuthResponse;
import com.lachesis.support.auth.api.vo.AuthenticationRequest;
import com.lachesis.support.auth.api.vo.AuthenticationResponse;
import com.lachesis.support.auth.api.vo.AuthorizationResponse;
import com.lachesis.support.auth.service.CentralizedAuthSupporter;
import com.lachesis.support.auth.vo.AuthorizationResult;
import com.lachesis.support.auth.vo.UserDetails;


@RestController
public class AuthCenterController extends AbstractRestController{
	private static final Logger LOG = LoggerFactory.getLogger(AuthCenterController.class);

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

	@RequestMapping(value = "authorizations/{tokenid}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.GET)
	public AuthResponse authorize(@PathVariable("tokenid") String token, @RequestParam("ip") String ip,
			HttpServletRequest request) {
		logAuthorize(token, ip);
		validateAuthorizeParameters(token, ip);
		
		return convertAuthorizationResult(internalAuthorize(token, ip));
	}
	
	@RequestMapping(value = "authorizations", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.GET)
	public AuthResponse authorizeWithUsername(@RequestParam("username") String username, HttpServletRequest request) {
		throw new UnsupportedOperationException();
	}

	@RequestMapping(value = "tokens/{tokenid}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.DELETE)
	public ResponseEntity<String> logout(@PathVariable("tokenid") String token, HttpServletRequest request) {
		authSupporter.logout(token);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	private AuthorizationResult internalAuthorize(String token, String ip){
		AuthorizationResult authzResult = authSupporter.authorize(token, ip);
		if (authzResult == null) {
			LOG.error(String.format("authentication failed with [token:%s, ip:%s]", token, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_TOKEN, "无效token");
		}
		
		return authzResult;
	}
	
	private void validateAuthorizeParameters(String token, String ip){
		if (isBlank(token) || isBlank(ip)) {
			LOG.error(String.format("errors with [token:%s, ip:%s]", token, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_ARGS, "token或IP为空");
		}
	}
	
	private void logAuthorize(String token, String ip){
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("authorize for [token:%s,ip:%s]", token, ip));
		}
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

	private AuthorizationResponse convertAuthorizationResult(AuthorizationResult result) {
		AuthorizationResponse resp = new AuthorizationResponse();
		resp.setId(result.getId());
		resp.setUsername(result.getUsername());
		resp.setRoles(result.getRoles());
		resp.setPermissions(result.getPermissions());

		return resp;
	}
	
	private String determineTerminalIpAddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		return ip;
	}

	private boolean isBlank(String s) {
		return StringUtils.isBlank(s);
	}
}
