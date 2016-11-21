package com.lachesis.support.auth.context.shiro;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.context.wrapper.HttpServletRequestWrapper;

public class TokenAuthorizationAccessControlFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorizationAccessControlFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request);
		String token = wrapper.getAuthorizationToken();
		String terminalIpAddress = wrapper.getRemoteIpAddress();

		Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());

		LocalAuthenticationToken localToken = new LocalAuthenticationToken(token, terminalIpAddress, params);

		try {
			Subject subject = getSubject(request, response);
			subject.login(localToken);
		} catch (Exception e) {
			LOG.error("errors while login", e);
			onLoginFail(response);
		}
		
		return true;

	}

	protected void onLoginFail(ServletResponse response) throws IOException {
		throw new AuthenticationException("authentication failed");
	}

}
