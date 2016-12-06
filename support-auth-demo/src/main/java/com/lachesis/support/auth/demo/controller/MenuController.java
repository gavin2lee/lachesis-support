package com.lachesis.support.auth.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.restful.context.controller.AbstractRestController;
import com.lachesis.support.restful.context.vo.RequestVO;

@RestController
@RequestMapping("menus")
public class MenuController extends AbstractRestController<RequestVO> {
	
}
