package kg.aalyev.drivers.util;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kbakytbekov on 09.09.2017.
 */
public class Util {
    public static void redirect(String path) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + path);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
