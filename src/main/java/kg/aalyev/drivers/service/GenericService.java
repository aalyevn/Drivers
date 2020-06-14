package kg.aalyev.drivers.service;


import kg.aalyev.drivers.beans.FilterExample;
import kg.aalyev.drivers.enums.SortEnum;
import kg.aalyev.drivers.beans.*;
import kg.aalyev.drivers.entity.PersistentEntity;
import kg.aalyev.drivers.enums.*;

import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.List;

/**
 * @author aziko
 */

public interface GenericService<T extends PersistentEntity<ID>, ID extends Serializable> {

    /**
     * @param entity
     * @return
     */
    T persist(T entity) throws PersistenceException;

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

    /***
     * @param property
     * @param value
     * @return
     */
    T getByProperty(String property, Object value);

    T findById(ID id, boolean lock);

    /**
     * @param id
     * @param fields fields that include
     * @return
     */
    T getByIdWithFields(ID id, String[] fields);

    T getByPropertyWithFields(String property, Object value, String[] fields);

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
    List<T> findByPropertyWithFields(String property, Object value, String[] fields);

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

    /**
     * @param from
     * @param to
     * @param sortEnum
     * @param list
     * @param sortProperty
     * @return
     */
    List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty);

    /**
     * @param from
     * @param to
     * @param sortEnum
     * @param list
     * @param sortProperty
     * @return
     */
    List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty, String[] fields);

}

