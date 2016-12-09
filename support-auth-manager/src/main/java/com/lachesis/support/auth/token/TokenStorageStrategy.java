package com.lachesis.support.auth.token;

import com.lachesis.support.auth.model.Token;

public interface TokenStorageStrategy {
	void save(Token authToken);
	void update(Token authToken);
	Token find(String tokenValue);
	Token remove(String tokenValue);
}
