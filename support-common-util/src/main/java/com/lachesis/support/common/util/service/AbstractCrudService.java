package com.lachesis.support.common.util.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.common.util.dal.BaseRepository;

public abstract class AbstractCrudService<E,K> implements CrudService<E,K> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractCrudService.class);

	@Transactional
	public void addEntity(E entity) {
		getBaseRepository().saveOne(entity, getEntityType());
	}

	@Override
	public void updateEntity(E entity) {
		getBaseRepository().updateOne(entity, getEntityType());
	}

	@Override
	public E retrieveEntityById(K id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> retrieveEntitiesByCriteria(E criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEntity(K id) {
		// TODO Auto-generated method stub

	}
	
	protected abstract Class<E> getEntityType();
	protected abstract BaseRepository<E,K> getBaseRepository();

}
