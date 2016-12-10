package com.lachesis.support.auth.data;

import java.util.List;

import com.lachesis.support.auth.model.Token;

public interface TokenService {
	int PAGE_SIZE = 300;
	int MAX_SIZE_IN_BATCH = 300;
	List<Token> findPagedTokens(int pageNum);
	Token findByTokenValue(String tokenValue);
	void updateLastModifiedTokens(List<Token> tokensToUpdate);
	void removeTokens(List<Token> tokensToRemove);
	void removeExpiredTokens(int maxMinutesAllowed);
	void addTokens(List<Token> tokensToAdd);
}
