package com.lachesis.support.auth.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.data.UserDataService;
import com.lachesis.support.objects.entity.auth.User;
import com.lachesis.support.restful.context.vo.ResponseVO;

@RestController
@RequestMapping("users")
public class UserDataController {
	private static final Logger LOG = LoggerFactory.getLogger(UserDataController.class);
	
	@Autowired
	private UserDataService userDataService;
	
	public ResponseVO addUser(User u){
		return null;
	}
	
	public ResponseVO updateUser(User u){
		return null;
	}
	
	public ResponseVO deleteUser(long userId){
		return null;
	}
	
	public ResponseVO findUserById(long userId){
		return null;
	}
	
	public ResponseVO findUserByUsername(String username){
		return null;
	}
	
	public ResponseVO addRole(){
		return null;
	}
	
	public ResponseVO deleteRole(){
		return null;
	}
}
