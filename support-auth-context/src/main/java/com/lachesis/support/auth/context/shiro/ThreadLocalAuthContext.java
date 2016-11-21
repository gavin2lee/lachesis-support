package com.lachesis.support.auth.context.shiro;

import com.lachesis.support.auth.common.vo.AuthorizationResponseVO;

public final class ThreadLocalAuthContext {
	private ThreadLocalAuthContext(){}
	
	private static final ThreadLocal<AuthorizationResponseVO> authorizations = new ThreadLocal<AuthorizationResponseVO>();
	
	public static void set(AuthorizationResponseVO authorization){
		authorizations.set(authorization);
	}
	
	public static AuthorizationResponseVO get(){
		return authorizations.get();
	}
}
