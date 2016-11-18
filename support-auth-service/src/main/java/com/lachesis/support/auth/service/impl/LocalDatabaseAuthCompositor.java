package com.lachesis.support.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.authentication.impl.AbstractAuthenticator;
import com.lachesis.support.auth.authorization.Authorizer;
import com.lachesis.support.auth.data.AuthUserService;
import com.lachesis.support.auth.model.AuthUser;
import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.AuthorizationResult;
import com.lachesis.support.auth.vo.SimpleAuthorizationResult;
import com.lachesis.support.auth.vo.UserDetails;

@Service("localDatabaseAuthCompositor")
public class LocalDatabaseAuthCompositor extends AbstractAuthenticator implements Authorizer {
	private static final Logger LOG = LoggerFactory.getLogger(LocalDatabaseAuthCompositor.class);

	@Autowired
	@Qualifier("databaseBasedAuthUserService")
	private AuthUserService authUserService;

	protected UserDetails doAuthenticate(String userid, String password) {
		AuthUser user = getAuthUser(userid);
		if (user == null) {
			LOG.debug("authenticating failed for:" + userid);
			return null;
		}

		if (!password.equals(user.getPassword())) {
			LOG.debug("password comparing failed for " + userid);
			return null;
		}

		UserDetails userDetails = new SimpleAuthorizationResult(String.valueOf(user.getId()), user.getUsername(),
				user.getPassword());
		return userDetails;
	}
	
	protected AuthUser getAuthUser(String userid){
		return authUserService.findAuthUserByUserid(userid);
	}

	@Override
	public AuthorizationResult authorize(AuthToken authToken) {
		AuthUser user = getAuthUser(authToken.getUserid());
		if (user == null) {
			LOG.debug("authorizing failed for:" + authToken.getUserid());
			return null;
		}
		//TODO getAuthorization
		return new SimpleAuthorizationResult(String.valueOf(user.getId()), user.getUsername(), user.getPassword());
	}

}
