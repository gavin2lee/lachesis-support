package com.lachesis.support.auth.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.RolePermission;

@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class RoleRepositoryTest {

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	PermissionRepository permissionRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOne() {
		Role r = roleRepo.findOne(1L);
		assertThat(r, notNullValue());
	}

	@Test
	public void testFindOneByName() {
		String name = "NURSE";
		Role r = roleRepo.findOneByName(name);

		assertThat(r, notNullValue());
		assertThat(r.getName(), equalTo(name));
	}

	@Test
	public void testFindAll() {
		List<Role> roles = roleRepo.findAll();

		assertThat(roles, notNullValue());
		assertThat(roles.size(), greaterThan(0));

	}

	@Test
	public void testFindByUserId() {
		List<Role> roles = roleRepo.findByUserId(1L);

		assertThat(roles, notNullValue());
		assertThat(roles.size(), greaterThan(0));
	}

	@Test
	public void testInsertOne() {
		Role r = mockRole();
		roleRepo.insertOne(r);

		assertThat(r.getId(), notNullValue());
	}

	private Role mockRole() {
		Role r = new Role();
		r.setCode("ROLE_TEST-");
		r.setCreateAt(new Date());
		r.setName("ROLE-TEST-"+System.nanoTime());

		r.setIsDeleted(false);

		return r;
	}

	@Test
	public void testUpdateOne() {
		Role r = mockRole();
		roleRepo.insertOne(r);

		assertThat(r.getId(), notNullValue());
		assertThat(r.getDescription(), nullValue());

		String description = "dec_test";

		r.setDescription(description);
		roleRepo.updateOne(r);

		r = roleRepo.findOne(r.getId());
		assertThat(r.getDescription(), notNullValue());
		assertThat(r.getDescription(), equalTo(description));
	}

	private Permission mockPermission() {
		Permission p = new Permission();
		p.setName("PERM-TEST-" + System.nanoTime());
		p.setLabel("LABEL-TEST");

		return p;
	}

	@Test
	public void testAddPermission() {
		Permission p = mockPermission();
		permissionRepo.insertOne(p);

		assertThat(p.getId(), notNullValue());

		Role r = mockRole();
		roleRepo.insertOne(r);

		assertThat(r.getId(), notNullValue());

		roleRepo.addPermission(mockRolePermission(r.getId(), p.getId()));
	}

	@Test
	public void testAddPermissions() {
		Permission p1 = mockPermission();
		Permission p2 = mockPermission();
		permissionRepo.insertOne(p1);
		permissionRepo.insertOne(p2);
		
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		List<RolePermission> rps = mockRolePermissions(r.getId(), p1.getId(), p2.getId());
		
		Integer ret = roleRepo.addPermissions(rps);
		assertThat(ret, equalTo(2));
		
		Role r1 = roleRepo.findOne(r.getId());
		assertThat(r1, notNullValue());
		assertThat(r1.getPermissions(), notNullValue());
		assertThat(r1.getPermissions().size(), equalTo(2));
	}

	private List<RolePermission> mockRolePermissions(Long roleId, Long... permissionIds) {
		List<RolePermission> rps = new ArrayList<RolePermission>();
		for (Long pid : permissionIds) {
			rps.add(mockRolePermission(roleId, pid));
		}

		return rps;
	}

	private RolePermission mockRolePermission(Long roleId, Long permissionId) {
		RolePermission rp = new RolePermission();
		rp.setCreateAt(new Date());
		rp.setRoleId(roleId);
		rp.setPermissionId(permissionId);
		rp.setIsDeleted(false);

		return rp;
	}

	@Test
	public void testDeleteOne() {
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		assertThat(r.getId(), notNullValue());
		
		Role r1 = roleRepo.findOne(r.getId());
		assertThat(r1, notNullValue());
		
		roleRepo.deleteOne(r.getId());
		r1 = roleRepo.findOne(r.getId());
		
		assertThat(r1, nullValue());
		
	}

	@Test
	public void testDeletePermissions() {
		Permission p1 = mockPermission();
		Permission p2 = mockPermission();
		permissionRepo.insertOne(p1);
		permissionRepo.insertOne(p2);
		
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		List<RolePermission> rps = mockRolePermissions(r.getId(), p1.getId(), p2.getId());
		
		Integer ret = roleRepo.addPermissions(rps);
		assertThat(ret, equalTo(2));
		
		Role r1 = roleRepo.findOne(r.getId());
		assertThat(r1, notNullValue());
		assertThat(r1.getPermissions(), notNullValue());
		assertThat(r1.getPermissions().size(), equalTo(2));
	}

	@Test
	public void testDeletePermission() {
		fail("Not yet implemented");
	}

}
