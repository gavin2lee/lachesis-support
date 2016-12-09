package com.lachesis.support.auth.verifier;

import com.lachesis.support.auth.model.Token;

public interface TokenVerifier {
	Token verify(String token, String terminalIpAddress);
}
