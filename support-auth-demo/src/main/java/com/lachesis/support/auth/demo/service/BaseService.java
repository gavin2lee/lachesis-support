package com.lachesis.support.auth.demo.service;

import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.auth.demo.repository.BaseRepository;

public abstract class BaseService <T>{
	@Transactional
	public Integer createEntity(T entity, Class<T> type){
		return getBaseRepository().saveOne(entity, type);
	}
	
	@Transactional(readOnly = true)
	public T retrieveEntity(Object id, Class<T> type){
		return getBaseRepository().findOne(id, type);
	}
	
	@Transactional
	public Integer updateEntity(T entity, Class<T> type){
		return getBaseRepository().updateOne(entity, type);
	}
	
	@Transactional
	public Integer deleteEntity(Object id, Class<T> type){
		return getBaseRepository().deleteOne(id, type);
	}
	
	protected abstract BaseRepository<T> getBaseRepository();
}
