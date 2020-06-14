package kg.aalyev.drivers.service;


import kg.aalyev.drivers.entity.Patient;

import javax.ejb.Local;

/**
 * Created by kbakytbekov on 07.09.2017.
 */
@Local
public interface PatientService extends GenericService<Patient,Integer>{
}
