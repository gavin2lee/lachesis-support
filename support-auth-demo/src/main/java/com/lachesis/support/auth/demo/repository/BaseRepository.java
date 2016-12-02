package com.lachesis.support.auth.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.lachesis.support.auth.demo.util.SQLBuilder;

import static com.lachesis.support.auth.demo.util.SQLBuilder.*;

public interface BaseRepository<T> {
	@SelectProvider(type = SQLBuilder.class, method = FIND_ONE)
	T findOne(@Param(P_NAME_ID) Object id, @Param(P_NAME_TYPE) Class<T> type);

	@SelectProvider(type = SQLBuilder.class, method = FIND_LIST_BY_CRITERIA)
	List<T> findListByCriteria(@Param(P_NAME_ENTITY) T criteria, @Param(P_NAME_TYPE) Class<T> type);

	@InsertProvider(type = SQLBuilder.class, method = SAVE_ONE)
	Integer saveOne(@Param(P_NAME_ENTITY) T entity, @Param(P_NAME_TYPE) Class<T> type);

	@UpdateProvider(type = SQLBuilder.class, method = UPDATE_ONE)
	Integer updateOne(@Param(P_NAME_ENTITY) T entity, @Param(P_NAME_TYPE) Class<T> type);

	@DeleteProvider(type = SQLBuilder.class, method = DELETE_ONE)
	Integer deleteOne(@Param(P_NAME_ID) Object id, @Param(P_NAME_TYPE) Class<T> type);
}
