package com.lachesis.support.restful.context.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lachesis.support.restful.context.vo.RequestVO;
import com.lachesis.support.restful.context.vo.ResponseVO;

public abstract class AbstractRestController< V  extends RequestVO> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRestController.class);
	
	@RequestMapping(method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVO createNew(@RequestBody V json){
		if(LOG.isDebugEnabled()){
			LOG.debug("RECV:"+json);
		}
		return ResponseVO.ok(json);
	}
}
