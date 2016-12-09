package com.lachesis.support.auth.cache;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class TokenCacheEventListenerFactory extends CacheEventListenerFactory {
	private static final Logger LOG = LoggerFactory.getLogger(TokenCacheEventListenerFactory.class);

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		LOG.debug("create "+TokenCacheEventListener.class.getSimpleName());
		return new TokenCacheEventListener();
	}

}
