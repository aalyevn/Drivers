package kg.aalyev.drivers.service.impl;


import kg.aalyev.drivers.dao.PatientDao;
import kg.aalyev.drivers.dao.impl.PatientDaoImpl;
import kg.aalyev.drivers.entity.Patient;
import kg.aalyev.drivers.service.PatientService;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PatientServiceImpl extends GenericServiceImpl<Patient, Integer, PatientDao> implements PatientService {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager em;
    private PatientDao dao;

    @PostConstruct
    public void initialize() {
        dao = new PatientDaoImpl(em);
    }

    @Override
    protected PatientDao getDao() {
        return dao;
    }

}
