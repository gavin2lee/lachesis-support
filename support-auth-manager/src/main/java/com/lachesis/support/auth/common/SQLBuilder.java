package com.lachesis.support.auth.common;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public final class SQLBuilder {
	
	public String buildFindOneById(@Param("id") final Serializable id, @Param("clazz") final Class<?> clazz){
		return new SQL() {{
		    SELECT("ID as id");
		    SELECT("CODE as code");
		    SELECT("USERNAME as username");
		    FROM("T_"+clazz.getSimpleName().toUpperCase());
		    WHERE("ID = #{id}");
		  }}.toString();
	}
	
	public SQLBuilder(){}
}
