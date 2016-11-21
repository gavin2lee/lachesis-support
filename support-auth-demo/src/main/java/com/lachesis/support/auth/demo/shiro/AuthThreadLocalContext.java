package com.lachesis.support.auth.demo.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.common.vo.AuthorizationResponseVO;

public final class AuthThreadLocalContext {
	private static final Logger LOG = LoggerFactory.getLogger(AuthThreadLocalContext.class);
	private AuthThreadLocalContext(){}
	
	private static final ThreadLocal<AuthorizationResponseVO> authorizations = new ThreadLocal<AuthorizationResponseVO>();
	
	public static void set(AuthorizationResponseVO authorization){
		LOG.info("set CTX Thread:"+Thread.currentThread().getName());
		authorizations.set(authorization);
	}
	
	public static AuthorizationResponseVO get(){
		LOG.info("get CTX Thread:"+Thread.currentThread().getName());
		return authorizations.get();
	}
}
