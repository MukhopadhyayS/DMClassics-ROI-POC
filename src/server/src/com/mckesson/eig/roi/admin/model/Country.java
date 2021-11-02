package com.mckesson.eig.roi.admin.model;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Country complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Country">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="defaultCountry" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="countrySeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Country", propOrder = {
    "countryCode",
    "countryName",
    "defaultCountry",
    "countrySeq"
})
public class Country {
    
    @XmlElement(required = true)
    private String countryCode;
    @XmlElement(required = true)
    private String countryName;
    private boolean defaultCountry;
    private long countrySeq;   
   
    
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
