package com.lachesis.support.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.lachesis.support.auth.common.SQLBuilder;

public interface BaseRepository<T> {
	@SelectProvider(type = SQLBuilder.class, method = "buildFindOne")
	T findOne(@Param("id") Object id, @Param("clazz") Class<T> type);

	@SelectProvider(type = SQLBuilder.class, method = "buildFindListByCriteria")
	List<T> findListByCriteria(@Param("t") T criteria, @Param("clazz") Class<T> type);

	@InsertProvider(type = SQLBuilder.class, method = "buildSaveOne")
	Integer saveOne(@Param("t") T entity, @Param("clazz") Class<T> type);

	@UpdateProvider(type = SQLBuilder.class, method = "buildUpdateOne")
	Integer updateOne(@Param("t") T entity, @Param("clazz") Class<T> type);

	@DeleteProvider(type = SQLBuilder.class, method = "buildDeleteOne")
	Integer deleteOne(@Param("id") Object id, @Param("clazz") Class<T> type);
}
