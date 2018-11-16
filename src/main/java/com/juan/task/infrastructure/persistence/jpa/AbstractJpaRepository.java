package com.juan.task.infrastructure.persistence.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.juan.task.domain.model.BaseEntity;

/**
 * @author juan
 *
 */
@Transactional
public class AbstractJpaRepository<T extends BaseEntity, Id extends Serializable> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractJpaRepository.class);
	protected final Class<T> persistentClass;
	protected final Class<Id> idClass;
	
	@Autowired
	protected EntityManager em;
	
	@SuppressWarnings("unchecked")
	protected AbstractJpaRepository() {
		this.persistentClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.idClass = (Class<Id>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	public T create(T entity) {
		LOG.debug("create persistentClass=" + persistentClass + ",entity="+entity);
		
		em.persist(entity);
		
		LOG.info("created "+ entity);
		
		return entity;
	}
	
	public T findById(Id id) {
		LOG.debug("findById persistentClass=" + persistentClass + ",Id="+id);
		
		T entity = em.find(persistentClass, id);
		
		LOG.info("findById persistentClass=" + persistentClass + ",returning="+entity);
		
		return entity;
	}
	
	public T update(T entity) {
		LOG.debug("update persistentClass=" + persistentClass + ",entity="+entity);
		
		entity = em.merge(entity);
		
		LOG.info("update persistentClass=" + persistentClass + ",returning="+entity);
		
		return entity;
	}
	
	public T deleteById(Id id) {
		LOG.debug("deleteById persistentClass=" + persistentClass + ",Id="+id);
		
		T entity = em.find(persistentClass, id);
		
		em.remove(entity);
		
		LOG.debug("deleteById persistentClass=" + persistentClass + ",removed="+entity);
		
		return entity;
	}
	
	public List<T> findAll() {
		LOG.debug("findAll persistentClass=" + persistentClass);
		
		javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder ( ).createQuery ( );
		cq.select ( cq.from ( persistentClass ) );
		
		List<T> entities = em.createQuery ( cq ).getResultList ( );
		
		LOG.info("findAll persistentClass=" + persistentClass + ",returning="+entities);
		
		return entities;
	}
}
