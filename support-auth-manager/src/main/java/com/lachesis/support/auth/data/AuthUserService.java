package com.lachesis.support.auth.data;

import com.lachesis.support.auth.model.AuthUser;

public interface AuthUserService {
	AuthUser findAuthUserByUserid(String userid);
	AuthUser findAuthorizationInfoByUserid(String userid);
}
