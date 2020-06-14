package kg.aalyev.drivers.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Patient.class)
public abstract class Patient_ extends kg.aalyev.drivers.entity.AbstractEntity_ {

	public static volatile SingularAttribute<Patient, Date> birthday;
	public static volatile SingularAttribute<Patient, String> address;
	public static volatile SingularAttribute<Patient, String> phone;
	public static volatile SingularAttribute<Patient, String> name;
	public static volatile SingularAttribute<Patient, String> firstVisit;
	public static volatile SingularAttribute<Patient, Date> editDate;
	public static volatile SingularAttribute<Patient, Date> createDate;

}

