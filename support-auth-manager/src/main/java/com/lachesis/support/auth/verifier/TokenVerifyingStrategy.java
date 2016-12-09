package com.lachesis.support.auth.verifier;

import com.lachesis.support.auth.model.Token;

public interface TokenVerifyingStrategy {
	Token verify(String token, String terminalIpAddress);
}
