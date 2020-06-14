package kg.aalyev.drivers.controller;

import kg.aalyev.drivers.annotation.RolesAllowed;
import kg.aalyev.drivers.entity.User;
import kg.aalyev.drivers.util.LoginUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginManager implements Serializable {

    @Inject
    private LoginUtil loginUtil;

    @RolesAllowed(roles = {"admin"})
    public String logout() {
        loginUtil.logout();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean hasRole(String role) {
        User user = loginUtil.getCurrentUser();
        return user != null && loginUtil.userHasRole(user, role);
    }

    public User getCurrentUser() {
        return loginUtil.getCurrentUser();
    }

    public String getCurrentUserRole() {
        if (getCurrentUser() != null) {
            getCurrentUser().getRole().getTitle();
        }
        return null;
    }
}
