
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for user complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="user">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="validateCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idle" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="instanceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="securities" type="{urn:eig.mckesson.com}securities" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
    "fullName",
    "loginId",
    "validateCode",
    "idle",
    "instanceId",
    "securities"
})
public class User {

    protected String fullName;
    @XmlElement(required = true)
    protected String loginId;
    protected int validateCode;
    protected Integer idle;
    protected Integer instanceId;
    protected Securities securities;

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullName(String value) {
        this.fullName = value;
    }

    /**
     * Gets the value of the loginId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Sets the value of the loginId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginId(String value) {
        this.loginId = value;
    }

    /**
     * Gets the value of the validateCode property.
     * 
     */
    public int getValidateCode() {
        return validateCode;
    }

    /**
     * Sets the value of the validateCode property.
     * 
     */
    public void setValidateCode(int value) {
        this.validateCode = value;
    }

    /**
     * Gets the value of the idle property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdle() {
        return idle;
    }

    /**
     * Sets the value of the idle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdle(Integer value) {
        this.idle = value;
    }

    /**
     * Gets the value of the instanceId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstanceId() {
        return instanceId;
    }

    /**
     * Sets the value of the instanceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstanceId(Integer value) {
        this.instanceId = value;
    }

    /**
     * Gets the value of the securities property.
     * 
     * @return
     *     possible object is
     *     {@link Securities }
     *     
     */
    public Securities getSecurities() {
        return securities;
    }

    /**
     * Sets the value of the securities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Securities }
     *     
     */
    public void setSecurities(Securities value) {
        this.securities = value;
    }

}
