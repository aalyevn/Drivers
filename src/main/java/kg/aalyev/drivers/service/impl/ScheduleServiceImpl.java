package kg.aalyev.drivers.service.impl;


import kg.aalyev.drivers.dao.ScheduleDao;
import kg.aalyev.drivers.dao.impl.ScheduleDaoImpl;
import kg.aalyev.drivers.entity.Schedule;
import kg.aalyev.drivers.service.ScheduleService;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule, Integer, ScheduleDao> implements ScheduleService {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager em;
    private ScheduleDao dao;

    @PostConstruct
    public void initialize() {
        dao = new ScheduleDaoImpl(em);
    }

    @Override
    protected ScheduleDao getDao() {
        return dao;
    }

}
