package com.lachesis.support.auth.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.data.RoleService;
import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.restful.context.vo.ResponseVO;

@RestController
@RequestMapping("roles")
public class RoleController {
	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping(method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO addRole(@RequestBody Role r) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("add role:" + r);
		}

		Role retRole = roleService.saveRole(r);

		return ResponseVO.ok(retRole);
	}

	@RequestMapping(method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO getRoles() {
		return null;
	}

	@RequestMapping(path = "/{roleId}", method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO getRoleById(@PathVariable("roleId") long id) {
		return null;
	}
	
	@RequestMapping(path = "/{roleId}", method = { RequestMethod.DELETE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO deleteRoleById(@PathVariable("roleId") long id) {
		return null;
	}

	@RequestMapping(path = "/{roleId}/permissions", method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO addPermission(@PathVariable("roleId") long roleId, @RequestBody Permission p) {
		return null;
	}
	
	@RequestMapping(path = "/{roleId}/permissions/{permId}", method = { RequestMethod.DELETE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO deletePermission(@PathVariable("roleId") long roleId, @PathVariable("permId") long permId) {
		return null;
	}
}
