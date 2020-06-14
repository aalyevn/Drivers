package kg.aalyev.drivers.filter;


import kg.aalyev.drivers.entity.User;
import kg.aalyev.drivers.util.LoginUtil;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aziko
 */

public class SecurityFilter implements Filter {

    private Map<String, String[]> map;

    @Inject
    private LoginUtil loginUtil;

    @Override
    public void init(FilterConfig config) throws ServletException {
        map = new HashMap<>();
        parseParam(config, "admin");
        parseParam(config, "operator");
        parseParam(config, "manager");
        parseParam(config, "citizen");
    }

    private void parseParam(FilterConfig config, String param) {
        String url = config.getInitParameter(param);
        if (url != null && !"".equals(url)) {
            map.put(param, url.split("\\|"));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();

        httpServletResponse.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");

        if (url == null || url.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) httpServletRequest.getSession().getAttribute(LoginUtil.CURRENT_USER_SESSION_KEY);

        if (isSecuredUrl(url, contextPath)) {
            if (user == null) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else if (isAccessAllowed(user, url, contextPath)) {
                chain.doFilter(request, response);
                return;
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isSecuredUrl(String url, String contextPath) {
        for (String securedUrls[] : map.values()) {
            for (String securedUrl : securedUrls) {
                if (url.matches("^" + contextPath + securedUrl + "$")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAccessAllowed(User user, String url, String contextPath) {
        for (String securedUrl : map.get(loginUtil.getActiveRoleName(user))) {
            if (url.matches("^" + contextPath + securedUrl + "$")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        map = null;
    }
}