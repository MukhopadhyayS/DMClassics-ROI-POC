
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userAccount">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invalidLogonCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="epnEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="epnPrefix" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facilities" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeFormFacilities" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="securityRightsMap" type="{urn:eig.mckesson.com}rightsMap" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="maskSSN" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="maskBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userAccount", propOrder = {
    "invalidLogonCount",
    "userId",
    "epnEnabled",
    "epnPrefix",
    "facilities",
    "freeFormFacilities",
    "securityRightsMap",
    "maskSSN",
    "maskBy"
})
public class UserAccount {

    protected int invalidLogonCount;
    protected int userId;
    protected boolean epnEnabled;
    @XmlElement(required = true)
    protected String epnPrefix;
    protected List<String> facilities;
    protected List<String> freeFormFacilities;
    protected List<RightsMap> securityRightsMap;
    protected boolean maskSSN;
    @XmlElement(required = true)
    protected String maskBy;

    /**
     * Gets the value of the invalidLogonCount property.
     * 
     */
    public int getInvalidLogonCount() {
        return invalidLogonCount;
    }

    /**
     * Sets the value of the invalidLogonCount property.
     * 
     */
    public void setInvalidLogonCount(int value) {
        this.invalidLogonCount = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     */
    public void setUserId(int value) {
        this.userId = value;
    }

    /**
     * Gets the value of the epnEnabled property.
     * 
     */
    public boolean isEpnEnabled() {
        return epnEnabled;
    }

    /**
     * Sets the value of the epnEnabled property.
     * 
     */
    public void setEpnEnabled(boolean value) {
        this.epnEnabled = value;
    }

    /**
     * Gets the value of the epnPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEpnPrefix() {
        return epnPrefix;
    }

    /**
     * Sets the value of the epnPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEpnPrefix(String value) {
        this.epnPrefix = value;
    }

    /**
     * Gets the value of the facilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFacilities() {
        if (facilities == null) {
            facilities = new ArrayList<String>();
        }
        return this.facilities;
    }

    /**
     * Gets the value of the freeFormFacilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeFormFacilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeFormFacilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFreeFormFacilities() {
        if (freeFormFacilities == null) {
            freeFormFacilities = new ArrayList<String>();
        }
        return this.freeFormFacilities;
    }

    /**
     * Gets the value of the securityRightsMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the securityRightsMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecurityRightsMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RightsMap }
     * 
     * 
     */
    public List<RightsMap> getSecurityRightsMap() {
        if (securityRightsMap == null) {
            securityRightsMap = new ArrayList<RightsMap>();
        }
        return this.securityRightsMap;
    }

    /**
     * Gets the value of the maskSSN property.
     * 
     */
    public boolean isMaskSSN() {
        return maskSSN;
    }

    /**
     * Sets the value of the maskSSN property.
     * 
     */
    public void setMaskSSN(boolean value) {
        this.maskSSN = value;
    }

    /**
     * Gets the value of the maskBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaskBy() {
        return maskBy;
    }

    /**
     * Sets the value of the maskBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaskBy(String value) {
        this.maskBy = value;
    }

}
