package com.mckesson.eig.roi.hpf.model;

public class UserTypeLOV implements java.io.Serializable{

    private static final long serialVersionUID = 14563L;
    
    private int userTypeLOVId;

    public int getUserTypeLOVId() {
        return userTypeLOVId;
    }

    public void setUserTypeLOVId(int userTypeLOVId) {
        this.userTypeLOVId = userTypeLOVId;
    }

    public UserTypeLOV(int userTypeLOVId) {
        super();
        this.userTypeLOVId = userTypeLOVId;
    }

    public UserTypeLOV() {
    }

}
