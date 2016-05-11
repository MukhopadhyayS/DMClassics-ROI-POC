package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReleaseHistoryUser implements Serializable {
    
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
