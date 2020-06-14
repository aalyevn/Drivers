package kg.aalyev.drivers.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends kg.aalyev.drivers.entity.AbstractEntity_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> salt;
	public static volatile SingularAttribute<User, Boolean> deleted;
	public static volatile SingularAttribute<User, Role> role;
	public static volatile SingularAttribute<User, String> post;
	public static volatile SingularAttribute<User, String> surname;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, Date> editDate;
	public static volatile SingularAttribute<User, Date> createDate;
	public static volatile SingularAttribute<User, Date> deleteDate;

}

