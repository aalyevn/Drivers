package kg.aalyev.drivers.util;


import kg.aalyev.drivers.entity.User;
import kg.aalyev.drivers.enums.ScopeConstants;

import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * @author aziko
 */

@SessionScoped
public class LoginUtil implements Serializable {

    public static final String CURRENT_USER_SESSION_KEY = "com.ak.dst.current_user_session_key";

    public boolean isPasswordsMatch(String providedPassword, String storedPassword, String salt) {
        return storedPassword.equals(PasswordEncryptionService.hashPassword(providedPassword, salt));
    }

    public boolean userHasRole(User user, String roleName) {
        if (roleName == null || user == null) return false;


        if (user.getRole().getTitle().equals(roleName))
            return true;
//        for (Role role : user.getRoles()) {
//            if (roleName.equals(role.getTitle())) {
//                return true;
//            }
//        }
        return false;
    }

    public User getCurrentUser() {
        return new FacesScopeQualifier().getValue(CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
    }

    public void setCurrentUser(User user) {
        new FacesScopeQualifier().setValue(CURRENT_USER_SESSION_KEY, user, ScopeConstants.SESSION_SCOPE);
    }

    public boolean isLogged() {
        User user = new FacesScopeQualifier().getValue(CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
        return (user != null);
    }

    public String getActiveRoleName(User user) {
        return user.getRole().getTitle();
    }

    public void logout() {
        new FacesScopeQualifier().getSession().invalidate();
    }
}
