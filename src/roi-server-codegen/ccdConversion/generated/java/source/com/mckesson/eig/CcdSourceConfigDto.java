
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CcdSourceConfigDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CcdSourceConfigDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="configValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="configLabel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcdSourceConfigDto", propOrder = {
    "configKey",
    "configValue",
    "configLabel"
})
public class CcdSourceConfigDto {

    @XmlElement(required = true)
    protected String configKey;
    @XmlElement(required = true)
    protected String configValue;
    @XmlElement(required = true)
    protected String configLabel;

    /**
     * Gets the value of the configKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * Sets the value of the configKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigKey(String value) {
        this.configKey = value;
    }

    /**
     * Gets the value of the configValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * Sets the value of the configValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigValue(String value) {
        this.configValue = value;
    }

    /**
     * Gets the value of the configLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigLabel() {
        return configLabel;
    }

    /**
     * Sets the value of the configLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigLabel(String value) {
        this.configLabel = value;
    }

}
