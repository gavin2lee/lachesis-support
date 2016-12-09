package com.lachesis.support.auth.authorization;

import com.lachesis.support.auth.model.Token;
import com.lachesis.support.auth.vo.AuthorizationResult;

public interface Authorizer {
	AuthorizationResult authorize(Token authToken);
}
