package kg.aalyev.drivers.dao.impl;


import kg.aalyev.drivers.dao.UserDao;
import kg.aalyev.drivers.entity.User;

import javax.persistence.EntityManager;

/**
 * @author aziko
 */
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao{

    public UserDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
