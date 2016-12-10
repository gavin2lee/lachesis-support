package com.lachesis.support.auth.cache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.lachesis.support.auth.model.Token;

public class SimpleTokenQueueBroker implements TokenQueueBroker{
	private BlockingQueue<Token> putTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> updateTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> removeTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> evictTokens = new ArrayBlockingQueue<Token>(1000);
	private BlockingQueue<Token> expireTokens = new ArrayBlockingQueue<Token>(1000);
	@Override
	public void addPutToken(Token t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addUpdateToken(Token t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addRemoveToken(Token t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addEvictToken(Token t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addExpireToken(Token t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Token takePutToken() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Token takeUpdateToken() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Token takeRemoveToken() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Token takeEvictToken() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Token takeExpireToken() {
		// TODO Auto-generated method stub
		return null;
	}
}
