package com.lachesis.support.auth.verifier.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.lachesis.support.auth.common.AuthConstants;
import com.lachesis.support.auth.token.AuthTokenManager;
import com.lachesis.support.auth.vo.AuthToken;

public abstract class AbstractMaxIdleTimeTokenVerifyingStrategy extends AbstractTokenVerifyingStrategy{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractMaxIdleTimeTokenVerifyingStrategy.class);
	
	@Value("${support.auth.verifier.max.idle.time.seconds}")
	private int tokenMaxIdleSeconds = AuthConstants.TOKEN_MAX_IDLE_SECONDS;

	@Autowired
	private AuthTokenManager tokenHolder;

	@Override
	protected AuthToken doVerify(String token, String terminalIpAddress) {
		AuthToken authToken = tokenHolder.retrieve(token);

		if (authToken == null) {
			LOG.debug(String.format("cannot retrieve [token:%s]", token));
			return null;
		}

		if (isExpired(authToken)) {
			LOG.debug(String.format("[token:%s] expired", token));
			return null;
		}

		tokenHolder.updateLastModifiedTime(authToken);
		return authToken;
	}
	
	protected boolean isExpired(AuthToken token){
		long lastModifiedTime = token.getLastModified().getTime();
		long currentTime = System.currentTimeMillis();
		
		long idleTime = (currentTime - lastModifiedTime);
		return (idleTime > (getTokenMaxIdleSeconds() * 1000) );
	}

	protected int getTokenMaxIdleSeconds() {
		return this.tokenMaxIdleSeconds;
	}
}
