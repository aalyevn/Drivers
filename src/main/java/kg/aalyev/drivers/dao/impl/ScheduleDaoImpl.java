package kg.aalyev.drivers.dao.impl;


import kg.aalyev.drivers.dao.ScheduleDao;
import kg.aalyev.drivers.entity.Schedule;

import javax.persistence.EntityManager;

/**
 * @author aziko
 */
public class ScheduleDaoImpl extends GenericDaoImpl<Schedule, Integer> implements ScheduleDao {

    public ScheduleDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
