package com.mckesson.eig.workflow.security;

import com.mckesson.eig.workflow.security.api.SecurityProperties;
import java.util.List;
import java.util.ArrayList;
import com.mckesson.eig.workflow.security.api.SecurityRight;
import com.mckesson.eig.workflow.api.WorkflowPluginException;


public class MockPluginSecurityImpl implements PluginSecurity {

    public MockPluginSecurityImpl(String location) {
    }

    public SecurityProperties logon(String user, String password) {

        SecurityProperties securityProperties = new SecurityProperties();

        try {
            //dialog displayed for 30 seconds
            final int logoffTime = 30;
            securityProperties.setAutoLogOffTime(logoffTime);
            //autologoff event occurs after 15 minutes of idle time
            final int logoffWaitTime = 60 * 15;
            securityProperties.setAutoLogOffWaitTime(logoffWaitTime);
            
            List<SecurityRight> securityRights = new ArrayList<SecurityRight>();
            securityRights.add(SecurityRight.DeployProcessAccess);
            securityRights.add(SecurityRight.ReviewProcessAccess);
           
            securityProperties.setSecurityRights(securityRights);
            
        } catch (Exception ex) {
            throw new WorkflowPluginException(ex);
        }

        return securityProperties;    
    }
    
    public void logoff() {
        
    }
    
}
