package com.lachesis.support.auth.context.comm;

import com.lachesis.support.auth.common.vo.AuthorizationResponseVO;

public interface AuthCenterClient {
	AuthorizationResponseVO authorize(String token, String terminalIpAddress);
}
