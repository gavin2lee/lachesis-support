package com.lachesis.support.auth.repository;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.auth.model.User;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOne() {
		Assert.assertThat(userRepo, Matchers.notNullValue());
		
		User u = userRepo.findOne(1, User.class);
		
		Assert.assertThat(u, Matchers.notNullValue());
	}
	
	@Test
	public void testSaveOne(){
		User u = new User();
		u.setUsername("9999");
		userRepo.saveOne(u, User.class);
	}
	
	@Test
	public void testUpdateOne(){
		User u = new User();
		u.setId(1L);
		u.setMobilePhone("13966668888");
		userRepo.updateOne(u, User.class);
		
		u = userRepo.findOne(1, User.class);
		
		Assert.assertThat(u, Matchers.notNullValue());
		
	}
	
	@Test
	public void testDeleteOne(){
		userRepo.deleteOne(5L, User.class);
		
		User u = userRepo.findOne(5, User.class);
		Assert.assertThat(u, Matchers.nullValue());
	}
	
	@Test
	public void testFindListByCriteria(){
		User template = new User();
		template.setPassword("123");
		
		List<User> users = userRepo.findListByCriteria(template, User.class);
		
		Assert.assertThat(users, Matchers.notNullValue());
		Assert.assertThat(users.size(), Matchers.greaterThan(1));
	}

}
