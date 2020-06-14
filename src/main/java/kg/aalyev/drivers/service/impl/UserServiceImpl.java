package kg.aalyev.drivers.service.impl;

import kg.aalyev.drivers.dao.UserDao;
import kg.aalyev.drivers.dao.impl.UserDaoImpl;
import kg.aalyev.drivers.entity.User;
import kg.aalyev.drivers.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserServiceImpl extends GenericServiceImpl<User, Integer, UserDao> implements UserService {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager em;
    private UserDao dao;

    @PostConstruct
    public void initialize() {
        dao = new UserDaoImpl(em);
    }

    @Override
    protected UserDao getDao() {
        return dao;
    }

}
