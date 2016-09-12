package com.intuit.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class BaseDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public <T> T save(T obj) {
		T savedObj = entityManager.merge(obj);
		return savedObj;
	}

	public <T> List<T> saveAll(List<T> obj) {
		List<T> list = new ArrayList<T>();
		for (T t : obj) {
			t = save(t);
			list.add(t);
		}
		return list;
	}

	public <T> List<T> saveBatch(List<T> obj) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < obj.size(); i++) {
			T t = save(obj.get(i));
			list.add(t);
			if (i % 500 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
		return list;
	}

	public <T> void batchInsert(List<T> obj) {
		for (int i = 0; i < obj.size(); i++) {
			entityManager.merge(obj.get(i));
			if (i % 100 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	public <T> void delete(T obj) {
		entityManager.remove(obj);
	}

	public <T> void deleteAll(List<T> obj) {
		for (T t : obj) {
			delete(t);
		}
	}

	public <T> void softDelete(T obj) {
		save(obj);
	}

	public <T> void softDeleteAll(List<T> obj) {
		for (T t : obj) {
			softDelete(t);
		}
	}

	public <T> List<T> find(final String queryString, final Object... values) {
		Query queryObject = entityManager.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i + 1, values[i]);
			}
		}
		return queryObject.getResultList();
	}

	/*
	 * public <T> List<T> findByParameterList(final String queryString, final
	 * Map<String, Object> params) { HibernateEntityManager hem =
	 * (HibernateEntityManager) entityManager; org.hibernate.Query queryObject =
	 * hem.getSession().createQuery(queryString); if (params != null) { for
	 * (Map.Entry<String, Object> entry : params.entrySet()) { if
	 * (entry.getValue() instanceof Collection<?>) {
	 * queryObject.setParameterList(entry.getKey(), (Collection<?>)
	 * entry.getValue()); } else { queryObject.setParameter(entry.getKey(),
	 * entry.getValue()); } } } return queryObject.list(); }
	 */

	public <T> List<T> findByParams(final String queryString,
			final Map<String, ?> params) {
		Query queryObject = entityManager.createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, ?> entry : params.entrySet()) {
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return queryObject.getResultList();
	}

	public int batchUpdate(final String updateQuery) {
		Query queryObject = entityManager.createQuery(updateQuery);
		return queryObject.executeUpdate();
	}

	public <T> List<T> find(final String queryString, Integer firstResult,
			Integer maxResults, final Object... values) {
		Query queryObject = entityManager.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i + 1, values[i]);
			}
		}
		queryObject.setFirstResult(firstResult);
		queryObject.setMaxResults(maxResults);
		return queryObject.getResultList();
	}

	public <T> T findUnique(final Class<T> clazz, final String queryString,
			final Object... values) {
		Query queryObject = entityManager.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i + 1, values[i]);
			}
		}
		try {
			return clazz.cast(queryObject.getSingleResult());
		} catch (NoResultException nre) {
			return null;
		}
	}

	public <T> List<T> findByNamedParams(final String queryString,
			final Map<String, ?> params) {

		Query queryObject = entityManager.createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, ?> entry : params.entrySet()) {
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return queryObject.getResultList();
	}

	public <T> List<T> findPaginatedResults(final String queryString,
			final Map<String, ?> params, int startIndex, int size) {
		Query queryObject = entityManager.createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, ?> entry : params.entrySet()) {
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		queryObject.setFirstResult(startIndex);
		queryObject.setMaxResults(size);
		return queryObject.getResultList();
	}

	public <T> T findUnique(final Class<T> clazz, final String queryString,
			final Map<String, ?> params) {
		Query queryObject = entityManager.createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, ?> entry : params.entrySet()) {
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		try {
			return clazz.cast(queryObject.getSingleResult());
		} catch (NoResultException nre) {
			return null;
		}
	}

	public int executeUpdate(final String queryString, final Object... values) {
		Query queryObject = entityManager.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i + 1, values[i]);
			}
		}
		return queryObject.executeUpdate();
	}

	public int executeUpdate(final String queryString,
			final Map<String, ?> params) {

		Query queryObject = entityManager.createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, ?> entry : params.entrySet()) {
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return queryObject.executeUpdate();
	}

	/*
	 * public int executeCountNativeQuery(String nativeQuery) {
	 * HibernateEntityManager hem = (HibernateEntityManager) entityManager;
	 * return ((Number) hem.createNativeQuery(nativeQuery).getResultList()
	 * .get(0)).intValue(); }
	 */

	/*
	 * public Long executeCountNativeQuery(final String nativeQuery, final
	 * Map<String, ?> params) { HibernateEntityManager hem =
	 * (HibernateEntityManager) entityManager; Query queryObject =
	 * hem.createNativeQuery(nativeQuery); if (params != null) { for
	 * (Map.Entry<String, ?> entry : params.entrySet()) {
	 * queryObject.setParameter(entry.getKey(), entry.getValue()); } } return
	 * ((Number) queryObject.getResultList().get(0)).longValue(); }
	 */

	/*
	 * public List executeSelectNativeQueryByPagination(final String
	 * nativeQuery, final Map<String, ?> params, int startIndex, int size) {
	 * HibernateEntityManager hem = (HibernateEntityManager) entityManager;
	 * Query queryObject = hem.createNativeQuery(nativeQuery); if (params !=
	 * null) { for (Map.Entry<String, ?> entry : params.entrySet()) {
	 * queryObject.setParameter(entry.getKey(), entry.getValue()); } }
	 * queryObject.setFirstResult(startIndex).setMaxResults(size); return
	 * queryObject.getResultList(); }
	 */

	/*
	 * public List<Object[]> executeSelectNativeQuery(final String nativeQuery,
	 * final Object... values) { HibernateEntityManager hem =
	 * (HibernateEntityManager) entityManager; Query queryObject =
	 * hem.createNativeQuery(nativeQuery); if (values != null) { for (int i = 0;
	 * i < values.length; i++) { queryObject.setParameter(i + 1, values[i]); } }
	 * return queryObject.getResultList(); }
	 */

	/*
	 * public int executeNativeQuery(String nativeQuery) {
	 * HibernateEntityManager hem = (HibernateEntityManager) entityManager;
	 * return hem.getSession().createSQLQuery(nativeQuery).executeUpdate(); }
	 */

	/*
	 * public List executeStoreProc(String nativeQuery, Class clazz, String
	 * resultSetMapping, String storedProcedureStmt, String procName, Object...
	 * values) {
	 * 
	 * HibernateEntityManager hem = (HibernateEntityManager) entityManager;
	 * 
	 * Query queryObject = hem.createNativeQuery(storedProcedureStmt);
	 * 
	 * if (clazz != null) { queryObject =
	 * hem.createNativeQuery(storedProcedureStmt, clazz); }
	 * 
	 * if (StringUtils.isNotBlank(resultSetMapping)) { queryObject =
	 * hem.createNativeQuery(storedProcedureStmt, resultSetMapping); }
	 * 
	 * if (values != null) { for (int i = 0; i < values.length; i++) {
	 * queryObject.setParameter(i + 1, values[i]); } } return
	 * queryObject.getResultList(); }
	 */

	/*
	 * public List executeNativeQuery(String nativeQuery, Object... values) {
	 * HibernateEntityManager hem = (HibernateEntityManager) entityManager;
	 * org.hibernate.Query queryObject =
	 * hem.getSession().getNamedQuery(nativeQuery);
	 * 
	 * if (values != null) { for (int i = 0; i < values.length; i++) {
	 * queryObject.setParameter(i , values[i]); } } return queryObject.list(); }
	 */

	public String isDatabaseServerUp() {
		try {
			find("FROM BasicDetails db", new Object[] {});
			return "PASSED";
		} catch (Exception e) {
			return "FAILED";
		}
	}

	/*
	 * public String getCurrentSchema() { HibernateEntityManager hem =
	 * (HibernateEntityManager) entityManager; SessionFactoryImplementor sfi =
	 * (SessionFactoryImplementor) hem.getSession().getSessionFactory(); return
	 * sfi.getSettings().getDefaultSchemaName(); }
	 */

	public <T> boolean contains(final Class<T> clazz, final Object entity) {
		return entityManager.contains(entity);
	}

	public <T> T merge(final T entity) {
		T savedObj = entityManager.merge(entity);
		return savedObj;
	}

	public <T> T findById(final Class<T> entityClass, final Object id) {
		return entityManager.find(entityClass, id);
	}

	public void flush() {
		entityManager.flush();
	}

}
