package com.lachesis.support.auth.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.auth.model.Token;

@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class TokenRepositoryTest {
	@Autowired
	TokenRepository repo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindPagedTokens() {
	}

	@Test
	public void testFindOne() {
		String tokenValue = "1111";
		Token t = repo.findOne(tokenValue);
		
		Assert.assertThat(t, Matchers.notNullValue());
	}

	@Test
	public void testUpdateOne() {
	}

	@Test
	public void testUpdateBatch() {
	}

	@Test
	public void testDeleteOne() {
	}

	@Test
	public void testDeleteBatch() {
	}

}
