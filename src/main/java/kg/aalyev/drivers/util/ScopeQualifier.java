package kg.aalyev.drivers.util;



import kg.aalyev.drivers.enums.ScopeConstants;

import javax.servlet.http.HttpSession;

/**
 * @author aziko
 */

public interface ScopeQualifier {

    public <U> U getValue(String name, ScopeConstants scope);

    public <U> void setValue(String name, U u, ScopeConstants scope);

    public void remove(String name, ScopeConstants scope);

    public HttpSession getSession();

}
