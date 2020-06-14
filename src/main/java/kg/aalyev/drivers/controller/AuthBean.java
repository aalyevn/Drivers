package kg.aalyev.drivers.controller;

import kg.aalyev.drivers.entity.Role;
import kg.aalyev.drivers.entity.User;
import kg.aalyev.drivers.service.RoleService;
import kg.aalyev.drivers.service.UserService;
import kg.aalyev.drivers.util.*;
import kg.aalyev.drivers.util.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kbakytbekov on 08.09.2017.
 */
@Named
@SessionScoped
public class AuthBean implements Serializable {
    @EJB
    private UserService userService;
    @EJB
    private RoleService roleService;

    @Inject
    private LoginUtil loginUtil;
    @Inject
    protected Resources resources;

    private String username;
    private String password;


    public String login() throws IOException {
        User user = userService.getByProperty("login", username);

        if (user != null && loginUtil.isPasswordsMatch(password, user.getPassword(), user.getSalt())) {
            return authorize(user);
        } else {
            Messages.showMessage("form", "invalidUsernameOrPassword", FacesMessage.SEVERITY_ERROR, false);
            return null;
        }

    }

    public String addDrivers() throws IOException {
        return navigate("form.xhtml");

    }

    public String authorize(User user) {
        if (loginUtil.userHasRole(user, "admin")) {
            loginUtil.setCurrentUser(user);
            return navigate("/admin/patient/list.xhtml");
        } else {
            return null;
        }
    }

    public void add() {
//        System.out.println("-------------------------------------------");
        Role role = new Role();
        role.setDescription("admin");
        role.setTitle("admin");
        roleService.persist(role);
//        Role role1 = new Role();
//        role1.setDescription("operator");
//        role1.setTitle("operator");
//        roleService.persist(role1);
//        Role role2 = new Role();
//        role2.setDescription("manager");
//        role2.setTitle("manager");
//        roleService.persist(role2);
        User user = new User();
        user.setSalt(PasswordEncryptionService.generateSalt());
        user.setPassword(PasswordEncryptionService.hashPassword("admin", user.getSalt()));
        user.setLogin("admin");
        user.setCreateDate(new Date());
        user.setRole(roleService.getByProperty("title", "admin"));
        user.setName("admin");
        user.setSurname("admin");
        userService.persist(user);
    }

    public void logout() {
        loginUtil.logout();
        Util.redirect("/login.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isAdmin() {
        boolean admin = loginUtil.getCurrentUser().getRole().getTitle().equals("admin");
        return admin;
    }

    public boolean isOperator() {
        boolean user = loginUtil.getCurrentUser().getRole().getTitle().equals("operator");
        return user;
    }

    public User checkUser() {
        return loginUtil.getCurrentUser();
    }

    public void redirectToIndex() {
        Util.redirect("/login.xhtml");
    }

    private String navigate(String url) {
        return url + "?faces-redirect=true";
    }
}
