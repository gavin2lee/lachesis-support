package com.lachesis.support.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;

public interface RoleRepository {
	Role findOne(@Param("id") Long id);
	
	Role findOneByName(@Param("name") String name);

	List<Role> findAll();

	List<Role> findByUserId(@Param("userId") Long userId);

	Integer deletePermissions(@Param("roleId") Long roleId, List<Permission> permissions);

	Integer deletePermission(@Param("roleId") Long roleId, Permission permission);

	Long insertOne(Role r);

	Integer updateOne(Role r);

	@Delete("delete from T_ROLE where id=#{id}")
	Integer deleteOne(@Param("id") Long id);
}
