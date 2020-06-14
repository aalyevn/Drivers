package kg.aalyev.drivers.controller;

import kg.aalyev.drivers.util.LoginUtil;

import javax.inject.Inject;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseBean implements Serializable {

    protected String navigate(String url) {
        return url + "?faces-redirect=true";
    }

    public String getFormatString(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public Date getFormatDate(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Inject
    protected LoginUtil loginUtil;


}
