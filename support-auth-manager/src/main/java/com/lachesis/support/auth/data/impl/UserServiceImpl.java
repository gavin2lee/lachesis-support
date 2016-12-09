package com.lachesis.support.auth.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.model.User;
import com.lachesis.support.auth.repository.UserRepository;
import com.lachesis.support.common.util.dal.BaseRepository;
import com.lachesis.support.common.util.service.AbstractCrudService;

@Service("userService")
public class UserServiceImpl extends AbstractCrudService<User> implements com.lachesis.support.auth.data.UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

	@Override
	protected BaseRepository<User> getBaseRepository() {
		return userRepo;
	}

	

}
