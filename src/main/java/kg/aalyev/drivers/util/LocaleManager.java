package kg.aalyev.drivers.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;

@ManagedBean
@SessionScoped
public class LocaleManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public void changeLocale(String languageCode) {
        locale = new Locale(languageCode);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public Locale getLocale() {
        return locale;
    }

}
