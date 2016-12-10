package com.lachesis.support.auth.cache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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

	private int waitIntervalSeconds = 5;

	private int waitIntervalMilliSecondsPut = 300;

	public void setWaitIntervalMilliSecondsPut(int waitIntervalMilliSecondsPut) {
		this.waitIntervalMilliSecondsPut = waitIntervalMilliSecondsPut;
	}

	public void setWaitIntervalSeconds(int waitIntervalSeconds) {
		this.waitIntervalSeconds = waitIntervalSeconds;
	}

	@Override
	public void addPutToken(Token t) {
		putIntoQueue(getPutTokens(), t);
	}

	@Override
	public void addUpdateToken(Token t) {
		putIntoQueue(getUpdateTokens(), t);
	}

	@Override
	public void addRemoveToken(Token t) {
		putIntoQueue(getRemoveTokens(), t);

	}

	@Override
	public void addEvictToken(Token t) {
		putIntoQueue(getEvictTokens(), t);

	}

	@Override
	public void addExpireToken(Token t) {
		putIntoQueue(getExpireTokens(), t);

	}

	@Override
	public Token takePutToken() {
		return getFromQueue(getPutTokens());
	}

	@Override
	public Token takeUpdateToken() {
		return getFromQueue(getUpdateTokens());
	}

	@Override
	public Token takeRemoveToken() {
		return getFromQueue(getRemoveTokens());
	}

	@Override
	public Token takeEvictToken() {
		return getFromQueue(getEvictTokens());
	}

	@Override
	public Token takeExpireToken() {
		return getFromQueue(getExpireTokens());
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

	protected void putIntoQueue(BlockingQueue<Token> q, Token t) {
		try {
			boolean result = q.offer(t,waitIntervalMilliSecondsPut, TimeUnit.MILLISECONDS);
			if(!result){
				LOG.warn("cannot write into queue.");
			}
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
		}
	}

	protected Token getFromQueue(BlockingQueue<Token> q) {
		try {
			return q.poll(waitIntervalSeconds, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			LOG.warn(e.getMessage());
		}

		return null;
	}

}
