package kg.aalyev.drivers.service.impl;

import kg.aalyev.drivers.dao.RoleDao;
import kg.aalyev.drivers.dao.impl.RoleDaoImpl;
import kg.aalyev.drivers.entity.Role;
import kg.aalyev.drivers.service.RoleService;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RoleServiceImpl extends GenericServiceImpl<Role, Integer, RoleDao> implements RoleService {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager em;
    private RoleDao dao;

    @PostConstruct
    public void initialize() {
        dao = new RoleDaoImpl(em);
    }

    @Override
    protected RoleDao getDao() {
        return dao;
    }

}
