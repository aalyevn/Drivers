package kg.aalyev.drivers.dao;


import kg.aalyev.drivers.beans.FilterExample;
import kg.aalyev.drivers.entity.PersistentEntity;
import kg.aalyev.drivers.enums.SortEnum;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * @param <T>  Entity
 * @param <ID> Identification for entity
 * @author aziko
 */
@XmlTransient
public interface GenericDao<T extends PersistentEntity<ID>, ID extends Serializable> {

    /***
     * @return
     */
    EntityManager getEntityManager();

    /**
     * @param entity
     * @return
     */
    T persist(T entity) throws PersistenceException;

    /**
     * @param
     * @return
     */
    List<T> persistList(List<T> entityList) throws PersistenceException;

    List<T> mergeList(List<T> entityList) throws PersistenceException;

    List<T> saveList(List<T> entityList) throws PersistenceException;

    void removeList(List<T> entityList) throws PersistenceException;

    /**
     * @param entity
     */
    void remove(T entity);

    /**
     * @param entity
     */
    T merge(T entity);

    /**
     * @param
     */
    void refresh(T entity);

    /**
     * @return
     */
    long countAll();

    /**
     * @param id
     * @param lock
     * @return
     */
    T findById(ID id, boolean lock);

    /**
     * @param property
     * @param value
     * @return
     */
    T getByProperty(String property, Object value);

    T getByPropertyWithFields(String property, Object value, String[] fields);

    /**
     * @param id
     * @param fields fields that include
     * @return
     */
    T getByIdWithFields(ID id, String[] fields);

    /***
     * @param property
     * @param value
     * @return
     */
    Long countByProperty(String property, Object value);

    /***
     * @param property
     * @param value
     * @return
     */
    List<T> findByProperty(String property, Object value);

    /***
     * @param property
     * @param value
     * @param fields
     * @return
     */
    List<T> findByProperty(String property, Object value, String[] fields);

    /**
     * @return
     */
    List<T> findAll();

    /**
     * @param firstResult
     * @param maxResults
     * @return
     */
    List<T> findEntries(int firstResult, int maxResults);

    /**
     * @param firstResult
     * @param maxResults
     * @param sort
     * @return
     */
    List<T> findEntries(int firstResult, int maxResults, SortEnum sort);

    /**
     * @param list
     * @return
     */
    Long countByExample(List<FilterExample> list);

    Double sumByExample(String property, List<FilterExample> list);

    List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty);

    List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty, String[] fields);

    /**
     * @return void
     */
    void evict(ID id);

    /**
     * @return void
     */
    void evictByEntity();

    /**
     * @return void
     */
    void evictAll();

}
