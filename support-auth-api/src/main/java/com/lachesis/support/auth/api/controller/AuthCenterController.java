package com.lachesis.support.auth.api.controller;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/")
public class AuthCenterController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthCenterController.class);

	@Autowired
	private CentralizedAuthSupporter authSupporter;

	@RequestMapping(value="tokens",produces={MediaType.APPLICATION_JSON_UTF8_VALUE},method=RequestMethod.POST)
	public AuthResponse authenticate(@RequestBody AuthenticationRequest tokenRequest, HttpServletRequest request) {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("authenticate for [username:%s]", tokenRequest.getUsername()));
		}

		if (isBlank(tokenRequest.getUsername()) || isBlank(tokenRequest.getPassword())) {
			LOG.error(String.format("errors with [username:%s]", tokenRequest.getUsername()));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_ARGS, "用户名或密码为空");
		}

		String userid = tokenRequest.getUsername();
		String psword = tokenRequest.getPassword();
		String ip = determineTerminalIpAddress(request);

		String token = authSupporter.authenticate(userid, psword, ip);
		if (isBlank(token)) {
			LOG.error(String.format("authenticating failed for [userid:%s, ip:%s]", userid, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED, "认证错误");
		}

		UserDetails userDetails = authSupporter.authorize(token, ip);
		if (userDetails == null) {
			LOG.error("cannot get userdetails with token:" + token);
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED, "认证失败");
		}

		String resourceId = userDetails.getId();

		AuthenticationResponse tokenResp = new AuthenticationResponse();
		tokenResp.setToken(token);
		tokenResp.setUserId(resourceId);
		return tokenResp;
	}

	@RequestMapping(value="authorizations/{tokenid}",produces={MediaType.APPLICATION_JSON_UTF8_VALUE},method=RequestMethod.GET)
	public AuthResponse authorize(@PathVariable("tokenid") String token, @RequestParam("ip") String ip, HttpServletRequest request) {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("authorize for [token:%s,ip:%s]", token, ip));
		}

		if (isBlank(token) || isBlank(ip)) {
			LOG.error(String.format("errors with [token:%s, ip:%s]", token, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_ARGS, "token或IP为空");
		}

		AuthorizationResult authResult = authSupporter.authorize(token, ip);
		if (authResult == null) {
			LOG.error(String.format("authentication failed with [token:%s, ip:%s]", token, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_TOKEN, "无效token");
		}

		AuthorizationResponse resp = new AuthorizationResponse();
		resp.setId(authResult.getId());
		resp.setUsername(authResult.getUserid());
		
		//TODO
		List<String> roles = new ArrayList<String>();
		roles.add("user");
		roles.add("admin");
		
		resp.setRoles(roles);
		return resp;
	}
	
	@RequestMapping(value="authorizations",produces={MediaType.APPLICATION_JSON_UTF8_VALUE},method=RequestMethod.GET)
	public AuthResponse authorizeWithUsername(@RequestParam("username") String username, HttpServletRequest request){
		//TODO
		return null;
	}
	
	@RequestMapping(value="tokens/{tokenid}",produces={MediaType.APPLICATION_JSON_UTF8_VALUE},method=RequestMethod.DELETE)
	public ResponseEntity<String> logout(@PathVariable("tokenid")String token, HttpServletRequest request){
		authSupporter.logout(token);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}

	private String determineTerminalIpAddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		return ip;
	}

	private boolean isBlank(String s) {
		return StringUtils.isBlank(s);
	}
}
