package kg.aalyev.drivers.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

/**
 * @author Kuttubek Aidaraliev
 */

@ApplicationScoped
public class Messages implements Serializable {

    private static final long serialVersionUID = -2065651128811043538L;

    private static Map<Locale, ResourceBundle> messageResources = new WeakHashMap<>();
    private static Map<Locale, ResourceBundle> errorResources = new WeakHashMap<>();

    public static String getMessage(String message) {
        return getResourceMessage("messages", message, messageResources);
    }

    public static String getErrorMessage(String message) {
        return getResourceMessage("errors", message, errorResources);
    }

    public static void showMessage(String componentId, String message, FacesMessage.Severity severity, boolean keep, Object... args) {
        FacesContext.getCurrentInstance().addMessage(componentId,
                new FacesMessage(severity, MessageFormat.format(
                        severity == FacesMessage.SEVERITY_ERROR ? getErrorMessage(message) : getMessage(message), args), null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(keep);
    }

    private static String getResourceMessage(String resourceFileName, String message, Map<Locale, ResourceBundle> resources) {
        try {
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            ResourceBundle bundle = resources.get(locale);
            if (bundle == null) {
                bundle = ResourceBundle.getBundle(resourceFileName, locale);
                resources.put(locale, bundle);
            }
            return bundle.getString(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}  
