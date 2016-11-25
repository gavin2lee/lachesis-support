package com.lachesis.support.auth.common;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public final class SQLBuilder {
	public static final char UNDER_SCORE = '_';
	
	public String buildFindOneById(@Param("id") final Serializable id, @Param("clazz") final Class<?> clazz){
		return new SQL() {{
			Field[] fs = clazz.getDeclaredFields();
			
			for(Field f : fs){
				SELECT(build(f));
			}
			
		    FROM(buildTableName(clazz));
		    WHERE(buildWhere());
		  }}.toString();
	}
	
	private String buildWhere(){
		return "ID = #{id}";
	}
	
	private String buildTableName(Class<?> clazz){
		return "T_"+clazz.getSimpleName().toUpperCase();
	}
	
	private String build(Field f){
		return String.format("%s as %s", buildCamelColumnName(f.getName()), f.getName());
	}
	
	private String buildCamelColumnName(String fieldName){
		StringBuilder sb = new StringBuilder();
		int len = fieldName.length();
		
		for(int index=0; index < len; index++){
			char c = fieldName.charAt(index);
			if(Character.isUpperCase(c)){
				sb.append(UNDER_SCORE).append(Character.toUpperCase(c));
			}else{
				sb.append(Character.toUpperCase(c));
			}
		}
		
		return sb.toString();
	}
	
	public SQLBuilder(){}
}
