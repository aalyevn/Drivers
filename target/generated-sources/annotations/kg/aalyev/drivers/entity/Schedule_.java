package kg.aalyev.drivers.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Schedule.class)
public abstract class Schedule_ extends kg.aalyev.drivers.entity.AbstractEntity_ {

	public static volatile SingularAttribute<Schedule, String> notes;
	public static volatile SingularAttribute<Schedule, Patient> patient;
	public static volatile SingularAttribute<Schedule, Date> eventTime;
	public static volatile SingularAttribute<Schedule, String> diagnosis;
	public static volatile SingularAttribute<Schedule, String> allergy;
	public static volatile SingularAttribute<Schedule, String> totalWork;
	public static volatile SingularAttribute<Schedule, Date> editDate;
	public static volatile SingularAttribute<Schedule, Date> eventDate;
	public static volatile SingularAttribute<Schedule, Date> createDate;
	public static volatile SingularAttribute<Schedule, Date> deleteDate;

}

