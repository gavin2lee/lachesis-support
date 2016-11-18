package com.lachesis.support.auth.demo.shiro;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpHeaders;

public class TokenAccessControlFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String token = determineToken((HttpServletRequest) request);
		String terminalIpAddress = determineIpAddress((HttpServletRequest) request);

		Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());

		StatelessToken sToken = new StatelessToken(token, terminalIpAddress, params);

		try {
			Subject subject = getSubject(request, response);
			subject.login(sToken);
		} catch (Exception e) {
			onLoginFail(response);
			return false;
		}
		return true;

	}

	private String determineToken(HttpServletRequest request) {
		String authrization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authrization == null) {
			return "";
		}
		String token = authrization.substring(6);

		return token;
	}

	private String determineIpAddress(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	private void onLoginFail(ServletResponse response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.getWriter().write("login error");
	}

}
