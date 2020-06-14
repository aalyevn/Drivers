package kg.aalyev.drivers.controller.admin;

import kg.aalyev.drivers.beans.FilterExample;
import kg.aalyev.drivers.controller.BaseBean;
import kg.aalyev.drivers.entity.Role;
import kg.aalyev.drivers.entity.User;
import kg.aalyev.drivers.enums.InequalityConstants;
import kg.aalyev.drivers.enums.SortEnum;
import kg.aalyev.drivers.service.RoleService;
import kg.aalyev.drivers.service.UserService;
import kg.aalyev.drivers.util.LoginUtil;
import kg.aalyev.drivers.util.Messages;
import kg.aalyev.drivers.util.PasswordEncryptionService;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class UserController extends BaseBean {
    @EJB
    private UserService userService;
    @EJB
    private RoleService roleService;
    @Inject
    private LoginUtil loginUtil;

    @Setter
    @Getter
    private List<User> userList;
    //    private UserModel userList;
    @Setter
    @Getter
    private User user;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String confpassword;


    public void init() {
        userList = userService.findByExample(0, 0, SortEnum.ASCENDING, getFilters(), "name");
//        userList=new UserModel(userService);
//        userList.setFilters(getFilters());
//        userList.setProperty("createdDate");
//        userList.setSortEnum(SortEnum.DESCENDING);
    }

    public List<FilterExample> getFilters() {
        List<FilterExample> filter = new ArrayList<>();
        filter.add(new FilterExample("login", InequalityConstants.IS_NOT_NULL));
        filter.add(new FilterExample("deleted", false, InequalityConstants.EQUAL));
        return filter;
    }

    public String add() {
        user = new User();
        return navigate("form.xhtml");
    }

    public String edit(User u) {
        user = u;
        return navigate("form.xhtml");
    }

    public String back() {
        user = null;
        return navigate("list.xhtml");
    }

    public String save() {
        if (password != null && !"".equals(password) && !password.equals(user.getPassword())) {
            user.setSalt(PasswordEncryptionService.generateSalt());
            user.setPassword(PasswordEncryptionService.hashPassword(password, user.getSalt()));
        }
        if (user.getId() == null) {
            user.setCreateDate(new Date());
            if (userService.getByProperty("login", user.getLogin()) == null) {
                userService.persist(user);
            } else {
                Messages.showMessage("form", "cantFindUser", FacesMessage.SEVERITY_ERROR, true);
                return null;
            }
        } else {
            user.setEditDate(new Date());
            userService.merge(user);
        }
        return navigate("list.xhtml");
    }

    public void delete(User user) {
        try {
            user.setDeleted(true);
            user.setDeleteDate(new Date());
            userService.merge(user);
        } catch (Exception e) {
            Messages.showMessage("form", "cantBeDeleted", FacesMessage.SEVERITY_ERROR, true);
        }
    }


    public List<Role> getRoles() {
        return roleService.findAll();
    }
}
