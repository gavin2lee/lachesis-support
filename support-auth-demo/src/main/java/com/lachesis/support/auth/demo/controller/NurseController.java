package com.lachesis.support.auth.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOG = LoggerFactory.getLogger(NurseController.class);
	@Autowired
	private NurseService nurseService;
	
	@RequestMapping(value="nurses",produces={MediaType.APPLICATION_JSON_VALUE})
	public List<SimpleUserVo> listNurses(@RequestParam("deptid") String deptid,HttpServletRequest request){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			String localIpAddress = addr.getHostAddress();
			LOG.debug("LOCAL IP ADDR:"+localIpAddress);
			
			String remoteIpAddress = request.getRemoteAddr();
			LOG.debug("REMOTE IP ADDR:"+remoteIpAddress);
			
			
			LOG.debug("THREAD:"+Thread.currentThread().getName());
		} catch (UnknownHostException e) {
			LOG.error("errors", e);
		}
		return nurseService.listAllOfDepartment(deptid);
	}
}
