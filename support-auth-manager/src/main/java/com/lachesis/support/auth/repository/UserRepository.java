package com.lachesis.support.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.User;

public interface UserRepository {
	User findOne(@Param("id") Long id);

	Integer addRoles(@Param("userId") Long userId, List<Role> roles);

	Integer deleteRoles(@Param("userId") Long userId, List<Role> roles);

	Integer deleteRole(@Param("userId") Long userId, Role role);

	Long insertOne(User u);

	Integer updateOne(User u);

	Integer deleteOne(@Param("id") Long id);
}
