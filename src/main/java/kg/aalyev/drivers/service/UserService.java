package kg.aalyev.drivers.service;

import kg.aalyev.drivers.entity.User;

import javax.ejb.Local;

@Local
public interface UserService extends GenericService<User,Integer>{
}
