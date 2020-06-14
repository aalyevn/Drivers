package kg.aalyev.drivers.util;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Calendar;

/**
 * @author Azamat Turgunbaev
 */

@Named
@ApplicationScoped
public class UtilBean {

    private int year;

    @PostConstruct
    public void init() {
        year = Calendar.getInstance().get(Calendar.YEAR);
    }

    public int getYear() {
        return year;
    }
}
