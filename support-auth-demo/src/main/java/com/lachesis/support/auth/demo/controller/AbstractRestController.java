package com.lachesis.support.auth.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/${support.auth.demo.version}")
public abstract class AbstractRestController {
	
//	@RequestMapping(value="/", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public ResponseVO createNew(@RequestBody Book book){
//		return ResponseVO.ok(book);
//	}
}
