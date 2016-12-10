package com.lachesis.support.auth.cache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lachesis.support.auth.model.Token;

@Component
public class SimpleTokenQueueBroker implements TokenQueueBroker {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleTokenQueueBroker.class);
	private BlockingQueue<Token> putTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> updateTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> removeTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> evictTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> expireTokens = new ArrayBlockingQueue<Token>(1000);

	@Override
	public void addPutToken(Token t) {
		try {
			getPutTokens().put(t);
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
		}
	}

	@Override
	public void addUpdateToken(Token t) {
		try {
			getUpdateTokens().put(t);
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
		}

	}

	@Override
	public void addRemoveToken(Token t) {
		try {
			getRemoveTokens().put(t);
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
		}

	}

	@Override
	public void addEvictToken(Token t) {
		try {
			getEvictTokens().put(t);
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
		}

	}

	@Override
	public void addExpireToken(Token t) {
		try {
			getExpireTokens().put(t);
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
		}

	}

	@Override
	public Token takePutToken() {
		try {
			return getPutTokens().take();
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}

	@Override
	public Token takeUpdateToken() {
		try {
			return getUpdateTokens().take();
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}

	@Override
	public Token takeRemoveToken() {
		try {
			return getRemoveTokens().take();
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}

	@Override
	public Token takeEvictToken() {
		try {
			return getEvictTokens().take();
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}

	@Override
	public Token takeExpireToken() {
		try {
			return getEvictTokens().take();
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}

	protected BlockingQueue<Token> getPutTokens() {
		return putTokens;
	}

	protected BlockingQueue<Token> getUpdateTokens() {
		return updateTokens;
	}

	protected BlockingQueue<Token> getRemoveTokens() {
		return removeTokens;
	}

	protected BlockingQueue<Token> getEvictTokens() {
		return evictTokens;
	}

	protected BlockingQueue<Token> getExpireTokens() {
		return expireTokens;
	}

}
