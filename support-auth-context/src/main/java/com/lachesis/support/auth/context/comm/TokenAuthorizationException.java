package com.lachesis.support.auth.context.comm;

public class TokenAuthorizationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2491534178994706190L;

	public TokenAuthorizationException() {
		super();
	}

	public TokenAuthorizationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TokenAuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenAuthorizationException(String message) {
		super(message);
	}

	public TokenAuthorizationException(Throwable cause) {
		super(cause);
	}

	
}
