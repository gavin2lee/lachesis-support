package com.lachesis.support.auth.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lachesis.support.auth.demo.vo.Book;
import com.lachesis.support.vo.ResponseVO;

public abstract class AbstractRestController {
	
	@RequestMapping(value="/", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVO createNew(@RequestBody Book book){
		return ResponseVO.ok(book);
	}
}
