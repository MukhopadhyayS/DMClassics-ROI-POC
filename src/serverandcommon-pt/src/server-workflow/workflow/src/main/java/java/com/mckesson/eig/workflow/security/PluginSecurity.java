package com.mckesson.eig.workflow.security;

import com.mckesson.eig.workflow.security.api.SecurityProperties;

public interface PluginSecurity {

    SecurityProperties logon(String user, String password);
    void logoff();
}
