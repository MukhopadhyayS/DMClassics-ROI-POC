package com.mckesson.eig.roi.admin.model;

public class Country {
    
    private long countrySeq;   
    private String countryCode;
    private String countryName;
    private boolean defaultCountry;
    
    public long getCountrySeq() {
        return countrySeq;
    }
    public void setCountrySeq(long countrySeq) {
        this.countrySeq = countrySeq;
    }
    public boolean isDefaultCountry() {
        return defaultCountry;
    }
    public void setDefaultCountry(boolean defaultCountry) {
        this.defaultCountry = defaultCountry;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
