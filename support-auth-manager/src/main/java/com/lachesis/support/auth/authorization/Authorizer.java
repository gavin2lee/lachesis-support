package com.lachesis.support.auth.authorization;

import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.AuthorizationResult;

public interface Authorizer {
	AuthorizationResult authorize(AuthToken authToken);
}
