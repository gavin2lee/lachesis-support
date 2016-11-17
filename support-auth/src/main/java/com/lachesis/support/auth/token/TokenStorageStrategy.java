package com.lachesis.support.auth.token;

import com.lachesis.support.auth.vo.AuthToken;

public interface TokenStorageStrategy {
	void save(AuthToken authToken);
	void update(AuthToken authToken);
	AuthToken find(String tokenValue);
	AuthToken remove(String tokenValue);
}
