package kg.aalyev.drivers.service.impl;


import kg.aalyev.drivers.beans.FilterExample;
import kg.aalyev.drivers.dao.GenericDao;
import kg.aalyev.drivers.entity.PersistentEntity;
import kg.aalyev.drivers.enums.SortEnum;
import kg.aalyev.drivers.service.GenericService;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/****
 * @param <T>
 * @param <ID>
 * @param <D>
 * @author aziko
 */

public abstract class GenericServiceImpl<T extends PersistentEntity<ID>, ID extends Serializable, D extends GenericDao<T, ID>>
        implements GenericService<T, ID> {

    private static Class<?>[] standards = {Byte.class, Character.class, Short.class, Integer.class, Long.class,
            Float.class, Double.class, Boolean.class, String.class, Date.class};
    @Inject
    protected EntityManager em;

    protected abstract D getDao();

    @Override
    public T persist(T entity) throws PersistenceException {
        return getDao().persist(entity);
    }

    @Override
    public List<T> persistList(List<T> entityList) throws PersistenceException {
        return getDao().persistList(entityList);
    }

    @Override
    public List<T> mergeList(List<T> entityList) throws PersistenceException {
        return getDao().mergeList(entityList);
    }

    @Override
    public List<T> saveList(List<T> entityList) throws PersistenceException {
        return getDao().saveList(entityList);
    }

    @Override
    public void removeList(List<T> entityList) throws PersistenceException {
        getDao().removeList(entityList);
    }

    @Override
    public T merge(T entity) throws PersistenceException {
        return getDao().merge(entity);
    }

    @Override
    public void remove(T entity) {
        getDao().remove(getDao().findById(entity.getId(), false));
    }

    @Override
    public void refresh(T entity) {
        getDao().refresh(entity);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public long countAll() {
        return getDao().countAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public T findById(ID id, boolean lock) {
        try {
            return getDao().findById(id, lock);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public T getByProperty(String property, Object value) {
        try {
            return getDao().getByProperty(property, value);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public T getByIdWithFields(ID id, String[] fields) {
        try {
            return getDao().getByIdWithFields(id, fields);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public T getByPropertyWithFields(String property, Object value, String[] fields) {
        try {
            return getDao().getByPropertyWithFields(property, value, fields);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Long countByProperty(String property, Object value) {
        try {
            return getDao().countByProperty(property, value);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Long countByExample(List<FilterExample> list) {
        return getDao().countByExample(list);
    }

    @Override
    public Double sumByExample(String property, List<FilterExample> list) {
        return getDao().sumByExample(property, list);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByProperty(String property, Object value) {
        try {
            return getDao().findByProperty(property, value);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<T> findByPropertyWithFields(String property, Object value, String[] fields) {
        try {
            return getDao().findByProperty(property, value, fields);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findAll() {
        return getDao().findAll();
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findEntries(int firstResult, int maxResults) {
        return getDao().findEntries(firstResult, maxResults);
    }

    @Override
    public List<T> findEntries(int firstResult, int maxResults, SortEnum sort) {
        return getDao().findEntries(firstResult, maxResults, sort);
    }

    @Override
    public List<T> findByExample(int from, int to, SortEnum sortEnum,
                                 List<FilterExample> list, String sortProperty) {
        return getDao().findByExample(from, to, sortEnum, list, sortProperty);
    }

    @Override
    public List<T> findByExample(int from, int to, SortEnum sortEnum,
                                 List<FilterExample> list, String sortProperty, String[] fields) {
        return getDao().findByExample(from, to, sortEnum, list, sortProperty, fields);
    }

    protected List<String> getModifiedFields(T entity, T modifiedEntity) {

        Field[] fields = modifiedEntity.getClass().getDeclaredFields();
        List<String> list = new ArrayList<String>();

        for (Field field : fields) {

            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) continue;

            try {
                if (field.getType().isPrimitive() || isStandard(field.getType()) || field.getType().isEnum()
                        || PersistentEntity.class.isAssignableFrom(field.getType())) {
                    String methodName = "get" + field.getName().substring(0, 1).toUpperCase(Locale.ENGLISH) + field.getName().substring(1);
                    Method method = modifiedEntity.getClass().getMethod(methodName);
                    Object value = method.invoke(entity, new Object[]{});
                    Object otherValue = method.invoke(modifiedEntity, new Object[]{});
                    if (value == null && otherValue != null) list.add(field.getName());
                    else if (value != null && !value.equals(otherValue)) list.add(field.getName());
                } else if (Collection.class.isAssignableFrom(field.getType())) {

                    PersistenceUnitUtil unitUtil = getDao().getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
                    if (!unitUtil.isLoaded(modifiedEntity, field.getName())) continue;
                    if (!unitUtil.isLoaded(entity, field.getName()))
                        entity = getDao().getByIdWithFields(entity.getId(), new String[]{field.getName()});
                    String methodName = "get" + field.getName().substring(0, 1).toUpperCase(Locale.ENGLISH) + field.getName().substring(1);

                    Method method = modifiedEntity.getClass().getMethod(methodName);
                    Collection<?> value = (Collection<?>) method.invoke(entity, new Object[]{});
                    Collection<?> otherValue = (Collection<?>) method.invoke(modifiedEntity, new Object[]{});
                    if (value != null && value.size() != otherValue.size()) list.add(field.getName());

                } else {
                    System.out.println("unknown field!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! = " + field.getName());
                }
            } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return list;

    }

    private boolean isStandard(Class<?> type) {
        return Arrays.asList(standards).contains(type);
    }

}
