package com.mckesson.eig.wsfw;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class MockHttpSession implements HttpSession {

    private HashMap _map = new HashMap();
    
    public long getCreationTime() {
        return 0;
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return 0;
    }

    public ServletContext getServletContext() {
         return null;
    }

    public void setMaxInactiveInterval(int arg0) {
    }

    public int getMaxInactiveInterval() {
        return 0;
    }

	@Deprecated
    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(String arg0) {
         return _map.get(arg0);
    }

	@Deprecated
    public Object getValue(String arg0) {
         return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

	@Deprecated
    public String[] getValueNames() {
        return null;
    }

    public void setAttribute(String arg0, Object arg1) {
        _map.put(arg0, arg1);
    }

	@Deprecated
    public void putValue(String arg0, Object arg1) {
    }

    public void removeAttribute(String arg0) {
    }

	@Deprecated
    public void removeValue(String arg0) {
    }

    public void invalidate() {
    }

    public boolean isNew() {
        return false;
    }

}
