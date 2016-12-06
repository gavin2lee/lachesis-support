package com.lachesis.support.common.util.dal;

import java.util.List;
import static com.lachesis.support.common.util.dal.SQLBuilder.*;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.lachesis.support.common.util.dal.SQLBuilder;

public interface BaseRepository<M,K> {
	@SelectProvider(type = SQLBuilder.class, method = FIND_ONE)
	M findOne(@Param(P_NAME_ID) K id, @Param(P_NAME_TYPE) Class<M> type);

	@SelectProvider(type = SQLBuilder.class, method = FIND_LIST_BY_CRITERIA)
	List<M> findListByCriteria(@Param(P_NAME_ENTITY) M criteria, @Param(P_NAME_TYPE) Class<M> type);

	@InsertProvider(type = SQLBuilder.class, method = SAVE_ONE)
	Integer saveOne(@Param(P_NAME_ENTITY) M entity, @Param(P_NAME_TYPE) Class<M> type);

	@UpdateProvider(type = SQLBuilder.class, method = UPDATE_ONE)
	Integer updateOne(@Param(P_NAME_ENTITY) M entity, @Param(P_NAME_TYPE) Class<M> type);

	@DeleteProvider(type = SQLBuilder.class, method = DELETE_ONE)
	Integer deleteOne(@Param(P_NAME_ID) K id, @Param(P_NAME_TYPE) Class<M> type);
}
