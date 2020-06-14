package kg.aalyev.drivers.dao.impl;


import kg.aalyev.drivers.dao.PatientDao;
import kg.aalyev.drivers.entity.Patient;

import javax.persistence.EntityManager;

/**
 * @author aziko
 */
public class PatientDaoImpl extends GenericDaoImpl<Patient, Integer> implements PatientDao{

    public PatientDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
