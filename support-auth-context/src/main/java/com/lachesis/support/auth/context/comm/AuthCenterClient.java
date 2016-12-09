package com.lachesis.support.auth.context.comm;

import com.lachesis.support.auth.vo.AuthorizationResponseVO;

public interface AuthCenterClient {
	AuthorizationResponseVO authorize(String token, String terminalIpAddress) throws AuthorizationException;
}
