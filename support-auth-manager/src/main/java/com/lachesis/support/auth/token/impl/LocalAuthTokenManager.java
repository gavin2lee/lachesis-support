package com.lachesis.support.auth.token.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.model.Token;
import com.lachesis.support.auth.token.AuthTokenManager;
import com.lachesis.support.auth.token.TokenStorageStrategy;

@Service("localAuthTokenManager")
public class LocalAuthTokenManager implements AuthTokenManager {
	private static final Logger LOG = LoggerFactory.getLogger(LocalAuthTokenManager.class);
	
	@Autowired
	private TokenStorageStrategy tokenStorageStrategy;
	
	public void setTokenStorageStrategy(TokenStorageStrategy tokenStorageStrategy) {
		this.tokenStorageStrategy = tokenStorageStrategy;
	}

	@Override
	public void store(Token authToken) {
		if(authToken == null){
			throw new RuntimeException("auth token cannot be null");
		}
		tokenStorageStrategy.save(authToken);
	}

	@Override
	public void updateLastModifiedTime(Token authToken) {
		if(authToken == null){
			throw new RuntimeException("auth token cannot be null");
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug("modify the last modified field to current system time");
		}
		
		authToken.setLastModified(new Date());
		tokenStorageStrategy.update(authToken);
	}

	@Override
	public Token retrieve(String tokenValue) {
		return tokenStorageStrategy.find(tokenValue);
	}

	@Override
	public Token dismiss(String tokenValue) {
		return tokenStorageStrategy.remove(tokenValue);
	}

}
