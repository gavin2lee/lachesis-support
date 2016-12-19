package com.lachesis.support.auth.repository;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.objects.entity.auth.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class PermissionRepositoryTest {
	
	@Autowired
	PermissionRepository permissionRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOne() {
		Permission p = permissionRepo.findOne(1L);
		
		Assert.assertThat(p, Matchers.notNullValue());
	}

	@Test
	public void testFindOneByName() {
		String name = "DEPT:LIST";
		Permission p = permissionRepo.findOneByName(name);
		Assert.assertThat(p, Matchers.notNullValue());
		Assert.assertThat(p.getName(), Matchers.equalTo(name));
	}

	@Test
	public void testFindAll() {
		List<Permission> permissions = permissionRepo.findAll();
		
		Assert.assertThat(permissions, Matchers.notNullValue());
	}

	@Test
	public void testFindByRoleId() {
		List<Permission> permissions = permissionRepo.findByRoleId(1L);
		
		Assert.assertThat(permissions, Matchers.notNullValue());
	}

	@Test
	public void testInsertOne() {
		Permission p = new Permission();
		p.setCode("P:READ");
		p.setDescription("描述");
		p.setLabel("读权限");
		p.setName("P:READ");
		p.setDeleted(false);
		
		Long ret = permissionRepo.insertOne(p);
		
		Assert.assertThat(ret, Matchers.greaterThan(0L));
		Assert.assertThat(p.getId(), Matchers.notNullValue());
		Assert.assertThat(p.getId(), Matchers.greaterThan(0L));
	}

	@Test
	public void testUpdateOne() {
		Permission p = permissionRepo.findOne(1L);
		Assert.assertThat(p, Matchers.notNullValue());
		
		String des = "descriptions";
		p.setDescription(des);
		permissionRepo.updateOne(p);
		
		p = permissionRepo.findOne(1L);
		Assert.assertThat(p, Matchers.notNullValue());
		Assert.assertThat(p.getDescription(), Matchers.equalTo(des));
	}

	@Test
	public void testDeleteOne() {
		Permission p = new Permission();
		p.setCode("P:READ");
		p.setDescription("描述");
		p.setLabel("读权限");
		p.setName("P:READ");
		p.setDeleted(false);
		
		permissionRepo.insertOne(p);
		
		Permission p2 = permissionRepo.findOne(p.getId());
		
		Assert.assertThat(p2, Matchers.notNullValue());
		permissionRepo.deleteOne(p2.getId());
		
		Permission p3 = permissionRepo.findOne(p2.getId());
		Assert.assertThat(p3, Matchers.nullValue());
	}

}
