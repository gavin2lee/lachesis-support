package com.lachesis.support.auth.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.auth.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOneById() {
		Assert.assertThat(userRepo, Matchers.notNullValue());
		
		User u = userRepo.findOneById(1, User.class);
		
		Assert.assertThat(u, Matchers.notNullValue());
	}
	
	@Test
	public void testSaveOne(){
		User u = new User();
		u.setUsername("9999");
		userRepo.saveOne(u, User.class);
	}

}
