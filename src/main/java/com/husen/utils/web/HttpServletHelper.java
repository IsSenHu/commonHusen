package com.husen.utils.web;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpServlet帮助工具类
 * Created by HuSen on 2018/10/12 10:07.
 */
@SuppressWarnings("ALL")
public class HttpServletHelper {
    private static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";

    private static final String X_REAL_IP = "X-Real-IP";

    public static String getCookieValue(final HttpServletRequest request, final String key) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return null;
        } else {
            String value = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    value = cookie.getValue();
                    break;
                }
            }
            return value;
        }
    }

    public static void putSessionValue(final HttpServletRequest request, final String key, final Object value) {
        request.getSession().setAttribute(key, value);
    }

    public static <T> T getSessionValue(final HttpServletRequest request, final String key) {
        return (T)request.getSession().getAttribute(key);
    }

    public static Boolean getBooleanValueFromSession(final HttpServletRequest request, final String key) {
        return getSessionValue(request, key);
    }

    public static void setCookie(final HttpServletResponse response, final String key, final String value, final int expireTime, final String path) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expireTime);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public static String getClientIp(final HttpServletRequest request) {
        String ipAddress = request.getHeader(X_FORWARDED_FOR);
        if(ipAddress == null) {
            String xRealIp = request.getHeader(X_REAL_IP);
            return StringUtils.isBlank(xRealIp) ? request.getRemoteHost() : xRealIp;
        }
        return ipAddress;
    }

    public static String getRequestURIWithParameters(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String queryString  = request.getQueryString();
        return StringUtils.isBlank(queryString) ? requestURI : String.format("%s?%s", requestURI, queryString);
    }

    public static Map<String ,String> getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String nextElement = headerNames.nextElement();
            String nextValue = request.getHeader(nextElement);
            headers.put(nextElement, nextValue);
        }
        return headers;
    }
}
