package com.lachesis.support.auth.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.demo.service.NurseService;
import com.lachesis.support.auth.demo.vo.SimpleUserVo;

@RestController
@RequestMapping("/")
public class NurseController {
	@Autowired
	private NurseService nurseService;
	
	@RequestMapping(value="nurses",produces={MediaType.APPLICATION_JSON_VALUE})
	public List<SimpleUserVo> listNurses(@RequestParam("deptid") String deptid){
		
		return nurseService.listAllOfDepartment(deptid);
	}
}
