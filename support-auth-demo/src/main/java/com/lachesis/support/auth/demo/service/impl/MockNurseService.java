package com.lachesis.support.auth.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.demo.service.NurseService;
import com.lachesis.support.auth.demo.vo.SimpleUserVo;

@Service("mockNurseService")
public class MockNurseService implements NurseService {
	public static final String ID_DEPT_SURGERY = "1";
	
	private Map<String,List<SimpleUserVo>> nurses = new HashMap<String,List<SimpleUserVo>>();
	public MockNurseService(){
		init();
	}

	@Override
	@RequiresRoles({"admin"})
	public List<SimpleUserVo> listAllOfDepartment(String deptId) {
		return nurses.get(deptId);
	}
	
	private void init(){
		List<SimpleUserVo> usersOfSurgery = new ArrayList<SimpleUserVo>();
		for(int i=1; i<6; i++){
			usersOfSurgery.add(new SimpleUserVo(String.valueOf(i), "s00"+i));
		}
		
		nurses.put(ID_DEPT_SURGERY, usersOfSurgery);
	}
	

}
