package kg.aalyev.drivers.validation;


import kg.aalyev.drivers.entity.User;
import kg.aalyev.drivers.service.UserService;
import kg.aalyev.drivers.util.Messages;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@FacesValidator
public class UsernameValidator implements Validator {

    @EJB
    private UserService userService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        Integer userId = (Integer) component.getAttributes().get("userId");

        if (userId != null) {
            if (!getUser(userId).getLogin().equals(value.toString())) {
                validateUsername(value.toString());
            }
        } else {
            validateUsername(value.toString());
        }
    }

    private void validateUsername(String username) {
        if (isUsernameExists(username)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.getErrorMessage("usernameRegistered"), null));
        }
    }

    private boolean isUsernameExists(String username) {
        return userService.getByProperty("login", username) != null;
    }

    private User getUser(int id) {
        return userService.findById(id, false);
    }
}
