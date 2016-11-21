package com.lachesis.support.auth.demo.shiro;

import com.lachesis.support.auth.common.vo.AuthorizationResponseVO;

public final class AuthThreadLocalContext {
	private AuthThreadLocalContext(){}
	
	private static final ThreadLocal<AuthorizationResponseVO> authorizations = new InheritableThreadLocal<AuthorizationResponseVO>();
	
	public static void set(AuthorizationResponseVO authorization){
		authorizations.set(authorization);
	}
	
	public static AuthorizationResponseVO get(){
		return authorizations.get();
	}
}
