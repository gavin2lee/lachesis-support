package com.lachesis.support.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lachesis.support.auth.model.Token;

public interface TokenRepository {
	List<Token> findPagedTokens(@Param("start") Long start, @Param("size") int size);
	
	Token findOne(@Param("tokenValue") String tokenValue);

	Integer updateOne(@Param("token") Token token);

	Integer updateBatch(@Param("tokens") List<Token> tokens);

	Integer deleteOne(@Param("tokenValue") String tokenValue);

	Integer deleteBatch(@Param("tokens") List<Token> tokens);
}
