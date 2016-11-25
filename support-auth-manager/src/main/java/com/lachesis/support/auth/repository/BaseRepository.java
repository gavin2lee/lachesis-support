package com.lachesis.support.auth.repository;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.lachesis.support.auth.common.SQLBuilder;


public interface BaseRepository<T> {
	@SelectProvider(type=SQLBuilder.class,method="buildFindOneById")
	T findOneById(@Param("id") Serializable id, @Param("clazz")Class<T> clazz);
	//int saveOne(T t);
	//int updateOne(T t);
	//int deleteOne(T t);
}
