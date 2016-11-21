package com.lachesis.support.auth.demo.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.client.RestTemplate;

import com.lachesis.support.auth.common.vo.AuthorizationResponseVO;

public class TokenAuthorizingRealm extends AuthorizingRealm {

	private String requestUrl = "http://127.0.0.1:9090/authc/api/v1/authorization";

	public boolean supports(AuthenticationToken token) {
		return token instanceof StatelessToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AuthorizationResponseVO vo = AuthThreadLocalContext.get();
		if(vo == null){
			throw new RuntimeException("AuthThreadLocalContext error");
		}
		
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		sai.addRoles(vo.getRoles());
		return sai;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		RestTemplate restTemplate = new RestTemplate();
		String authorizeUrl = String.format("%s/%s?ip=%s", requestUrl, token.getPrincipal(), token.getCredentials());

		try {
			AuthorizationResponseVO authorizationResp = restTemplate.getForObject(authorizeUrl,
					AuthorizationResponseVO.class);
			if(authorizationResp != null){
				AuthThreadLocalContext.set(authorizationResp);
				return new SimpleAuthenticationInfo(authorizationResp.getUsername(), token.getCredentials(), getName());
			}
		} catch (Exception e) {
			throw new AuthenticationException("authentication failed", e);
		}

		throw new AuthenticationException("authentication failed");
	}

}
