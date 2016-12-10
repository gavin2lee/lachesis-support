package com.lachesis.support.auth.data.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lachesis.support.auth.data.TokenService;
import com.lachesis.support.auth.model.Token;
import com.lachesis.support.auth.repository.TokenRepository;

@Service("databaseBasedTokenService")
public class DatabaseBasedTokenService implements TokenService {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseBasedTokenService.class);
	
	@Autowired
	private TokenRepository tokenRepo;

	@Override
	public List<Token> findPagedTokens(int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		List<Token> tokens = tokenRepo.selectPage();
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("pageNum:%d,selected size:%d", pageNum, tokens.size()));
		}
		return tokens;
	}

	@Override
	public Token findByTokenValue(String tokenValue) {
		return tokenRepo.selectOne(tokenValue);
	}

	@Override
	@Transactional
	public void updateLastModifiedTokens(List<Token> tokensToUpdate) {
		if(tokensToUpdate.size() > MAX_SIZE_IN_BATCH){
			throw new IllegalArgumentException();
		}
		List<Token> strippedTokens = stripTokens(tokensToUpdate);
		
		for(Token t : strippedTokens){
			tokenRepo.updateOne(t);
		}

		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("%s tokens had been modified.", strippedTokens.size()));
		}
	}

	@Override
	@Transactional
	public void removeTokens(List<Token> tokensToRemove) {
		for(Token t : tokensToRemove){
			tokenRepo.deleteOne(t.getTokenValue());
		}

		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("%s tokens removed", tokensToRemove.size()));
		}
	}

	@Override
	@Transactional
	public void addTokens(List<Token> tokensToAdd) {
		int ret = tokenRepo.insertBatch(tokensToAdd);
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("%s tokens added", ret));
		}

	}
	
	protected List<Token> stripTokens(List<Token> richTokens){
		List<Token> tokens = new LinkedList<Token>();
		for(Token t : richTokens){
			Token tNew = new Token();
			tNew.setLastModified(t.getLastModified());
			tNew.setTokenValue(t.getTokenValue());
			
			tokens.add(tNew);
		}
		
		return tokens;
	}

	@Override
	@Transactional
	public void removeExpiredTokens() {
		// TODO Auto-generated method stub
		
	}

}