package kg.aalyev.drivers.dao.impl;


import kg.aalyev.drivers.dao.GenericDao;
import kg.aalyev.drivers.entity.PersistentEntity;
import kg.aalyev.drivers.beans.FilterExample;
import kg.aalyev.drivers.enums.SortEnum;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/***
 * @param <T>
 * @param <ID>
 * @author aziko
 */
@XmlTransient
public abstract class GenericDaoImpl<T extends PersistentEntity<ID>, ID extends Serializable> implements GenericDao<T, ID> {

    private Class<T> persistentClass;
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl(EntityManager entityManager) {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *
     */
    public void flush() {
        getEntityManager().flush();
    }

    /**
     *
     */
    public void clear() {
        getEntityManager().clear();
    }

    /**
     * @return
     */
    protected Class<T> getPersistentClass() {
        return persistentClass;
    }


    @Override
    public T persist(T entity) throws PersistenceException {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public List<T> persistList(List<T> entityList) throws PersistenceException {
        for (T entity : entityList) {
            getEntityManager().persist(entity);
        }

        return entityList;
    }

    @Override
    public List<T> mergeList(List<T> entityList) throws PersistenceException {
        for (T entity : entityList) {
            getEntityManager().merge(entity);
        }

        return entityList;
    }

    public List<T> saveList(List<T> entityList) throws PersistenceException {
        for (T entity : entityList) {
            if (entity.getId() == null) {
                persist(entity);
            } else {
                merge(entity);
            }
        }
        return entityList;
    }

    @Override
    public void removeList(List<T> entityList) throws PersistenceException {
        for (T entity : entityList) {
            getEntityManager().remove(entity);
        }
    }

    @Override
    public T merge(T entity) {
        return getEntityManager().merge(entity);
    }

    ;

    @Override
    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    @Override
    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    ;

    @Override
    public long countAll() {
        return getEntityManager().createQuery(new StringBuilder("select count(*) from ")
                .append(getPersistentClass().getName()).toString(), Long.class).getSingleResult();
    }

    @Override
    public T getByProperty(String property, Object value) {
        return getEntityManager().createQuery(new StringBuilder("select entity from ").append(getPersistentClass().getSimpleName())
                .append(" as entity where entity." + property + " =:value").toString(), getPersistentClass())
                .setParameter("value", value)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public T findById(ID id, boolean lock) {
        T entity;
        if (lock)
            entity = (T) getEntityManager().find(getPersistentClass(), id, LockModeType.WRITE);
        else
            entity = (T) getEntityManager().find(getPersistentClass(), id);

        return entity;
    }

    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery(new StringBuilder("select entity from ").append(getPersistentClass().getSimpleName())
                .append(" as entity Order by entity.id").toString(), getPersistentClass()).getResultList();

    }

    @Override
    public List<T> findEntries(int firstResult, int maxResults) {
        return findEntries(firstResult, maxResults, SortEnum.ASCENDING);
    }

    @Override
    public List<T> findEntries(int firstResult, int maxResults, SortEnum sort) {
        String order = SortEnum.ASCENDING.equals(sort) ? "ASC" : "DESC";
        return getEntityManager().createQuery(new StringBuilder("select entity from ").append(getPersistentClass().getSimpleName())
                .append(" as entity ORDER BY entity.id " + order).toString(), getPersistentClass()).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public void evict(ID id) {
        getEntityManager().getEntityManagerFactory().getCache().evict(getPersistentClass(), id);
    }

    @Override
    public void evictByEntity() {
        getEntityManager().getEntityManagerFactory().getCache().evict(getPersistentClass());
    }

    @Override
    public void evictAll() {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    @Override
    public Long countByProperty(String property, Object value) {
        String query = new StringBuilder("select count(entity) from ").append(getPersistentClass().getSimpleName())
                .append(" as entity where entity." + property + " = :property").toString();
        return getEntityManager().createQuery(query, Long.class).setParameter("property", value).getSingleResult();
    }

    @Override
    public List<T> findByProperty(String property, Object value) {
        String query = new StringBuilder("select entity from ").append(getPersistentClass().getSimpleName())
                .append(" as entity where entity." + property + " = :property").toString();
        return getEntityManager().createQuery(query, getPersistentClass()).setParameter("property", value).getResultList();
    }

    @Override
    public List<T> findByProperty(String property, Object value, String[] fields) {
        StringBuffer buffer = new StringBuffer("SELECT DISTINCT entity FROM ");
        buffer.append(getPersistentClass().getSimpleName());
        buffer.append(" AS entity");
        for (String string : fields) {
            buffer.append(" LEFT JOIN FETCH entity." + string);
        }
        buffer.append(" where entity." + property + " = :property");
        return getEntityManager().createQuery(buffer.toString(), getPersistentClass()).setParameter("property", value).getResultList();
    }

    @Override
    public T getByPropertyWithFields(String property, Object value, String[] fields) {
        StringBuilder builder = new StringBuilder("select entity FROM ").append(getPersistentClass().getSimpleName());
        builder.append(" AS entity");
        for (String string : fields) {
            builder.append(" LEFT JOIN FETCH entity.").append(string);
        }
        builder.append(" where entity.").append(property).append(" = :property");
        return getEntityManager().createQuery(builder.toString(), getPersistentClass())
                .setParameter("property", value)
                .getSingleResult();
    }

    @Override
    public T getByIdWithFields(ID id, String[] fields) {
        StringBuffer buffer = new StringBuffer("SELECT entity FROM ");
        buffer.append(getPersistentClass().getSimpleName());
        buffer.append(" AS entity");
        for (String string : fields) {
            buffer.append(" LEFT JOIN FETCH entity." + string);
        }
        buffer.append(" where entity.id = :id");
        return getEntityManager().createQuery(buffer.toString(), getPersistentClass()).setParameter("id", id).setMaxResults(1).getSingleResult();
    }

    @Override
    public Long countByExample(List<FilterExample> list) {

        StringBuffer buffer = new StringBuffer("SELECT COUNT(DISTINCT entity) FROM " + getPersistentClass().getCanonicalName() + " entity");
        if (!list.isEmpty()) buffer.append(" WHERE");

        @SuppressWarnings("unchecked")
        TypedQuery<Long> query = (TypedQuery<Long>) applyFilter(Long.class, list, buffer, "");

        return query.getSingleResult();
    }

    @Override
    public Double sumByExample(String property, List<FilterExample> list) {
        StringBuffer buffer = new StringBuffer("SELECT SUM(entity." + property + ") FROM " +
                getPersistentClass().getCanonicalName() + " entity");
        if (!list.isEmpty()) buffer.append(" WHERE");

        @SuppressWarnings("unchecked")
        TypedQuery<Double> query = (TypedQuery<Double>) applyFilter(Double.class, list, buffer, "");
        Double result = query.getSingleResult();
        if (result == null) {
            return 0d;
        }
        return result;
    }

    @Override
    public List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty) {

        try {
            StringBuffer buffer = new StringBuffer("SELECT DISTINCT entity FROM " + getPersistentClass().getCanonicalName() + " entity");
            if (!list.isEmpty()) buffer.append(" WHERE");

            return getByExample(from, to, sortEnum, list, sortProperty, buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

    @Override
    public List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty, String[] fields) {

        try {
            StringBuffer buffer = new StringBuffer("SELECT DISTINCT entity FROM " + getPersistentClass().getCanonicalName() + " entity");


            if (fields != null) {
                for (String string : fields) {
                    buffer.append(" LEFT JOIN FETCH entity." + string);
                }
            }
            if (!list.isEmpty()) buffer.append(" WHERE");


            return getByExample(from, to, sortEnum, list, sortProperty, buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

    private List<T> getByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty, StringBuffer buffer) throws Exception {
        String order = SortEnum.ASCENDING.equals(sortEnum) ? "ASC" : "DESC";
        @SuppressWarnings("unchecked")
        TypedQuery<T> query = (TypedQuery<T>) applyFilter(getPersistentClass(), list, buffer, " ORDER BY entity." + sortProperty + " " + order);

        return query.setFirstResult(from).setMaxResults(to).getResultList();
    }

    protected TypedQuery<?> applyFilter(Class<?> type, List<FilterExample> list, StringBuffer buffer, String orderBy) {
        int count = 1;
        boolean search = true;
        for (FilterExample filterExample : list) {
            if (filterExample.isSearch()) {
                if (search) {
                    buffer.append(" (");
                    search = false;
                } else buffer.append(" OR");
            } else if (!search) {
                buffer.append(" )");
                search = true;
                if (count <= list.size()) buffer.append(" AND");
            }
            buffer.append(filterExample.query("entity", count++));
            if (filterExample.isSearch()) {
                if (count > list.size()) buffer.append(" )");
            } else if (count <= list.size()) buffer.append(" AND");

        }

        buffer.append(orderBy);
//        System.out.println("query=" + buffer);

        TypedQuery<?> query = getEntityManager().createQuery(buffer.toString(), type);
        count = 1;

        for (FilterExample filterExample : list) {
            if (filterExample.getValue() != null) {
                query.setParameter("value" + count++, filterExample.getValue());
            } else count++;

        }

        return query;
    }

}
