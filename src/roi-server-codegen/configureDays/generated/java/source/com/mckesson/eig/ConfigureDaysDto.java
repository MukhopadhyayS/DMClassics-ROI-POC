
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigureDaysDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigureDaysDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dayName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dayStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigureDaysDto", propOrder = {
    "dayName",
    "dayStatus"
})
public class ConfigureDaysDto {

    @XmlElement(required = true)
    protected String dayName;
    @XmlElement(required = true)
    protected String dayStatus;

    /**
     * Gets the value of the dayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayName() {
        return dayName;
    }

    /**
     * Sets the value of the dayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayName(String value) {
        this.dayName = value;
    }

    /**
     * Gets the value of the dayStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayStatus() {
        return dayStatus;
    }

    /**
     * Sets the value of the dayStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayStatus(String value) {
        this.dayStatus = value;
    }

}
