package com.lachesis.support.auth.context.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lachesis.support.auth.common.vo.AuthorizationResponseVO;
import com.lachesis.support.auth.context.comm.AuthCenterClient;

public class TokenAuthorizationRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorizationRealm.class);
	
	@Autowired
	private AuthCenterClient authCenterClient;
	
	public void setAuthCenterClient(AuthCenterClient authCenterClient) {
		this.authCenterClient = authCenterClient;
	}

	protected AuthCenterClient getAuthCenterClient() {
		return authCenterClient;
	}

	public boolean supports(AuthenticationToken token) {
		return token instanceof LocalAuthenticationToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AuthorizationResponseVO info = ThreadLocalAuthContext.get();
		if (info == null) {
			LOG.error("cannot find authorization info.");
			throw new RuntimeException("AuthThreadLocalContext error");
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("current thread:%s", Thread.currentThread().getName()));
			LOG.debug(String.format("found authorization info [%s]", info));
		}

		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		sai.addRoles(info.getRoles());
		sai.addStringPermissions(info.getPermissions());
		return sai;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		AuthorizationResponseVO resp = ThreadLocalAuthContext.get();
		
		if(resp != null){
			if(LOG.isDebugEnabled()){
				LOG.debug(String.format("found authentication info [%s]", resp));
			}
			
			return createAuthenticationInfo(token, resp);
		}
		
		try {
			resp = authCenterClient.authorize((String)token.getPrincipal(), (String)token.getCredentials());
			if (resp != null) {
				ThreadLocalAuthContext.set(resp);
				return createAuthenticationInfo(token, resp);
			}
		} catch (Exception e) {
			throw new AuthenticationException("authentication failed", e);
		}

		throw new AuthenticationException("authentication failed");
	}
	
	private AuthenticationInfo createAuthenticationInfo(AuthenticationToken token, AuthorizationResponseVO info){
		return new SimpleAuthenticationInfo(info.getUsername(), token.getCredentials(), getName());
	}

}
