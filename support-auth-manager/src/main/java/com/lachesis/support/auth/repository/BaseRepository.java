package com.lachesis.support.auth.repository;

import java.io.Serializable;

public interface BaseRepository<T> {
	T findOneById(Serializable id);
	int saveOne(T t);
	int updateOne(T t);
	int deleteOne(T t);
}
