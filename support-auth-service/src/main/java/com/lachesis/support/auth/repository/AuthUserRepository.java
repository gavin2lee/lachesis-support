package com.lachesis.support.auth.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lachesis.support.auth.model.AuthUser;

public interface AuthUserRepository {
	@Select("SELECT SEQ_ID as id,LOGIN_NAME as username,USER_CODE as userCode,PASSWORD as password "
			+"FROM SYS_USER "
			+"WHERE LOGIN_NAME=#{userid}"
			+"AND STATUS='01'")
	AuthUser findByUserid(@Param("userid")String userid);
}
