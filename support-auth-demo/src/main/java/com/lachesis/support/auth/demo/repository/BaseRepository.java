package com.lachesis.support.auth.demo.repository;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

public interface BaseRepository <T>{
	T findById(@Param("id") Serializable id);
	void updateOne(T t);
	
}
