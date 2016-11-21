package com.lachesis.support.auth.data.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.auth.data.AuthUserService;
import com.lachesis.support.auth.model.AuthUser;
import com.lachesis.support.auth.repository.AuthUserRepository;

@Service("databaseBasedAuthUserService")
public class DatabaseBasedAuthUserService implements AuthUserService {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseBasedAuthUserService.class);

	@Autowired
	private AuthUserRepository authUserRepo;

	@Transactional(readOnly=true)
	public AuthUser findAuthUserByUserid(String userid) {
		if(StringUtils.isBlank(userid)){
			LOG.error("userid should be specified");
			throw new IllegalArgumentException("userid is empty");
		}
		
		AuthUser user = authUserRepo.findByUserid(userid);
		return user;
	}

}
