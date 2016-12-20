package com.lachesis.support.auth.data.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.auth.data.PermissionService;
import com.lachesis.support.auth.repository.PermissionRepository;
import com.lachesis.support.objects.entity.auth.Permission;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	private PermissionRepository permRepo;

	@Override
	@Transactional
	public Permission savePermission(Permission p) {
		if(p == null){
			return null;
		}
		
		if(p.getId() > 0){
			return null;
		}
		
		return null;
	}

	@Override
	public Permission updatePermission(Permission p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission removePermission(Permission p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission findPermissionById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission findPermissionByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Permission> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
