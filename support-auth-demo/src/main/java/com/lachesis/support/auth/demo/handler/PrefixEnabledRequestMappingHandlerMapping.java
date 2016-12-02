package com.lachesis.support.auth.demo.handler;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class PrefixEnabledRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	public static final String URL_SLASH = "/";
	@Value("${support.auth.demo.version}")
	private String apiVersion = "v1";
	private String contextPrefix = "/api/" + apiVersion;

	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = super.getMappingForMethod(method, handlerType);

		RequestMappingInfo enrichedInfo = null;
		if (info != null) {
			PatternsRequestCondition patt = info.getPatternsCondition();
			String[] patterns = new String[patt.getPatterns().size()];

			String[] prefixedPatts = prefixPatterns(patt.getPatterns().toArray(patterns));

			PatternsRequestCondition patternsCondition = new PatternsRequestCondition(prefixedPatts,
					this.getUrlPathHelper(), this.getPathMatcher(), this.useSuffixPatternMatch(),
					this.useTrailingSlashMatch(), this.getFileExtensions());
			enrichedInfo = new RequestMappingInfo(info.getName(), patternsCondition, info.getMethodsCondition(),
					info.getParamsCondition(), info.getHeadersCondition(), info.getConsumesCondition(),
					info.getProducesCondition(), info.getCustomCondition());
		}
		return enrichedInfo;
	}

	public String getContextPrefix() {
		return contextPrefix;
	}

	public void setContextPrefix(String contextPrefix) {
		this.contextPrefix = contextPrefix;
	}

	protected String[] prefixPatterns(String[] patts) {
		if (patts == null) {
			return null;
		}
		String[] prefixedPatts = new String[patts.length];
		for (int i = 0; i < patts.length; i++) {
			if (patts[i].startsWith(URL_SLASH)) {
				prefixedPatts[i] = getContextPrefix() + patts[i];
			}else{
				prefixedPatts[i] = getContextPrefix() + URL_SLASH + patts[i];
			}
		}
		return prefixedPatts;
	}
}
