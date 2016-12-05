package com.lachesis.support.common.util.crypt;

import com.lachesis.support.common.util.coder.StringCoder;
import com.lachesis.support.common.util.digest.MessageDigester;
import com.lachesis.support.common.util.exception.CryptException;
import com.lachesis.support.common.util.text.TextUtils;

public class CryptUtils {
	public static final String INTERNAL_ENCODING = "UTF-8";
	public static final String DYNAMIC_PARAM_KEY_DIGESTER = "support.crypt.digester";
	public static final String DYNAMIC_PARAM_KEY_CODER = "support.crypt.coder";

	private static final String DEFAULT_DIGESTER = "com.lachesis.support.common.util.digest.Md5BasedMessageDigester";
	private static final String DEFAULT_CODER = "com.lachesis.support.common.util.coder.DesCryptStringCoder";
	private static MessageDigester digester;
	private static StringCoder coder;

	static {
		init();
		validate();
	}

	public static String encrypt(String plaintext) throws CryptException {
		return coder.encode(plaintext);
	}

	public static String decrypt(String ciphertext) throws CryptException {
		return coder.decode(ciphertext);
	}

	public static String digest(String content) throws CryptException {
		return digester.digestAndHex(content);
	}

	private static void init() {
		try {
			initializeDigester();
			initializeCoder();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void initializeDigester()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String concreteDigesterType = System.getProperty(DYNAMIC_PARAM_KEY_DIGESTER, DEFAULT_DIGESTER);
		if (TextUtils.isNotBlank(concreteDigesterType)) {
			Class<?> initDigester = Class.forName(concreteDigesterType);
			if (!MessageDigester.class.isAssignableFrom(initDigester)) {
				throw new RuntimeException("the digester type provided is not appropriate.");
			}
			digester = (MessageDigester) initDigester.newInstance();
		}
	}

	private static void initializeCoder()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String concreteCoder = System.getProperty(DYNAMIC_PARAM_KEY_CODER, DEFAULT_CODER);
		if (TextUtils.isNotBlank(concreteCoder)) {
			Class<?> initCoder = Class.forName(concreteCoder);
			if (!StringCoder.class.isAssignableFrom(initCoder)) {
				throw new RuntimeException("the coder type provided is not appropraite.");
			}
			coder = (StringCoder) initCoder.newInstance();
		}
	}

	private static void validate() {
		if (digester == null) {
			throw new IllegalStateException("digester must be specified");
		}
		if (coder == null) {
			throw new IllegalStateException("coder must be specified");
		}
	}
}
