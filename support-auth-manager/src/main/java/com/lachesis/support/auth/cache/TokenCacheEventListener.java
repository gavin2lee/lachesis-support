package com.lachesis.support.auth.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class TokenCacheEventListener implements CacheEventListener {
	private static final Logger LOG = LoggerFactory.getLogger(TokenCacheEventListener.class);

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		LOG.debug(String.format("remove:%s,%s,%s", cache.getName(),element.getObjectKey(),element.getObjectValue()));
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		
		LOG.debug(String.format("put:%s,%s,%s", cache.getName(),element.getObjectKey(),element.getObjectValue()));
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		LOG.debug(String.format("update:%s,%s,%s", cache.getName(),element.getObjectKey(),element.getObjectValue()));
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		LOG.debug(String.format("expire:%s,%s,%s", cache.getName(),element.getObjectKey(),element.getObjectValue()));
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		LOG.debug(String.format("evict:%s,%s,%s", cache.getName(),element.getObjectKey(),element.getObjectValue()));
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		LOG.debug(String.format("removeall:%s", cache.getName()));
	}

	@Override
	public void dispose() {

	}
	
	 public Object clone() throws CloneNotSupportedException{
		 return this.clone();
	 }

}
