package com.lachesis.support.common.util.service;

import java.util.List;

public interface CrudService <E,K>{
	void addEntity(E entity);
	void updateEntity(E entity);
	E retrieveEntityById(K id);
	List<E> retrieveEntitiesByCriteria(E criteria);
	void deleteEntity(K id);
}
