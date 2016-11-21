package com.lachesis.support.auth.context.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

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
		
		try {
			String token = wrapper.getAuthorizationToken();
			String terminalIpAddress = wrapper.getRemoteIpAddress();

			Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());

			LocalAuthenticationToken localToken = new LocalAuthenticationToken(token, terminalIpAddress, params);
			Subject subject = getSubject(request, response);
			subject.login(localToken);
		} catch (Exception e) {
			LOG.error("errors while login", e);
			onLoginFail(e, response);
			return false;
		}
		
		return true;

	}

	protected void onLoginFail(Exception ex, ServletResponse response) throws IOException {
		if(ex instanceof  AuthenticationException){
			if(response instanceof HttpServletResponse){
				((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}else{
			if(response instanceof HttpServletResponse){
				((HttpServletResponse)response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		
		if(response instanceof HttpServletResponse){
			response.setContentType("application/json;charset=utf-8");
			PrintWriter writer = ((HttpServletResponse)response).getWriter();
			writer.write(String.format("{\"msg\":\"%s\"}", ex.getMessage()));
//			writer.close();
		}
		
	}
}
