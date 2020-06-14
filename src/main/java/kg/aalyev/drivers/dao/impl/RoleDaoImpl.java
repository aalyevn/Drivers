package kg.aalyev.drivers.dao.impl;


import kg.aalyev.drivers.dao.RoleDao;
import kg.aalyev.drivers.entity.Role;

import javax.persistence.EntityManager;

/**
 * @author aziko
 */
public class RoleDaoImpl extends GenericDaoImpl<Role, Integer> implements RoleDao {

    public RoleDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
