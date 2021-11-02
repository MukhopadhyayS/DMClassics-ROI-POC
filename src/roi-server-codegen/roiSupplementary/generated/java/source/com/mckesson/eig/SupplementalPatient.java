
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementalPatient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementalPatient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="epn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="homephone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="workphone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newCountry" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasRequest" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="freeformFacility" type="{urn:eig.mckesson.com}FreeFormFacility"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalPatient", propOrder = {
    "id",
    "address1",
    "address2",
    "address3",
    "city",
    "dateOfBirth",
    "epn",
    "facility",
    "firstName",
    "gender",
    "homephone",
    "lastName",
    "mrn",
    "ssn",
    "state",
    "vip",
    "workphone",
    "zip",
    "countryCode",
    "countryName",
    "newCountry",
    "hasRequest",
    "freeformFacility"
})
public class SupplementalPatient {

    protected long id;
    @XmlElement(required = true)
    protected String address1;
    @XmlElement(required = true)
    protected String address2;
    @XmlElement(required = true)
    protected String address3;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String dateOfBirth;
    @XmlElement(required = true)
    protected String epn;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String gender;
    @XmlElement(required = true)
    protected String homephone;
    @XmlElement(required = true)
    protected String lastName;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String ssn;
    @XmlElement(required = true)
    protected String state;
    protected boolean vip;
    @XmlElement(required = true)
    protected String workphone;
    @XmlElement(required = true)
    protected String zip;
    @XmlElement(required = true)
    protected String countryCode;
    @XmlElement(required = true)
    protected String countryName;
    protected boolean newCountry;
    protected boolean hasRequest;
    @XmlElement(required = true)
    protected FreeFormFacility freeformFacility;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the address1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Gets the value of the address3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * Sets the value of the address3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress3(String value) {
        this.address3 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfBirth(String value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the epn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEpn() {
        return epn;
    }

    /**
     * Sets the value of the epn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEpn(String value) {
        this.epn = value;
    }

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacility(String value) {
        this.facility = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the homephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomephone() {
        return homephone;
    }

    /**
     * Sets the value of the homephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomephone(String value) {
        this.homephone = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the mrn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMrn() {
        return mrn;
    }

    /**
     * Sets the value of the mrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMrn(String value) {
        this.mrn = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsn(String value) {
        this.ssn = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the vip property.
     * 
     */
    public boolean isVip() {
        return vip;
    }

    /**
     * Sets the value of the vip property.
     * 
     */
    public void setVip(boolean value) {
        this.vip = value;
    }

    /**
     * Gets the value of the workphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkphone() {
        return workphone;
    }

    /**
     * Sets the value of the workphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkphone(String value) {
        this.workphone = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the newCountry property.
     * 
     */
    public boolean isNewCountry() {
        return newCountry;
    }

    /**
     * Sets the value of the newCountry property.
     * 
     */
    public void setNewCountry(boolean value) {
        this.newCountry = value;
    }

    /**
     * Gets the value of the hasRequest property.
     * 
     */
    public boolean isHasRequest() {
        return hasRequest;
    }

    /**
     * Sets the value of the hasRequest property.
     * 
     */
    public void setHasRequest(boolean value) {
        this.hasRequest = value;
    }

    /**
     * Gets the value of the freeformFacility property.
     * 
     * @return
     *     possible object is
     *     {@link FreeFormFacility }
     *     
     */
    public FreeFormFacility getFreeformFacility() {
        return freeformFacility;
    }

    /**
     * Sets the value of the freeformFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeFormFacility }
     *     
     */
    public void setFreeformFacility(FreeFormFacility value) {
        this.freeformFacility = value;
    }

}
