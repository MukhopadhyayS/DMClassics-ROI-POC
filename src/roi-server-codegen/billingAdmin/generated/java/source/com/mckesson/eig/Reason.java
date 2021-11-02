
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Reason complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reason">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="displayText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tpo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nonTpo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reason", propOrder = {
    "id",
    "name",
    "displayText",
    "type",
    "status",
    "tpo",
    "nonTpo",
    "recordVersion"
})
public class Reason {

    protected long id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String displayText;
    @XmlElement(required = true)
    protected String type;
    protected int status;
    protected boolean tpo;
    protected boolean nonTpo;
    protected int recordVersion;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the displayText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayText() {
        return displayText;
    }

    /**
     * Sets the value of the displayText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayText(String value) {
        this.displayText = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the status property.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Gets the value of the tpo property.
     * 
     */
    public boolean isTpo() {
        return tpo;
    }

    /**
     * Sets the value of the tpo property.
     * 
     */
    public void setTpo(boolean value) {
        this.tpo = value;
    }

    /**
     * Gets the value of the nonTpo property.
     * 
     */
    public boolean isNonTpo() {
        return nonTpo;
    }

    /**
     * Sets the value of the nonTpo property.
     * 
     */
    public void setNonTpo(boolean value) {
        this.nonTpo = value;
    }

    /**
     * Gets the value of the recordVersion property.
     * 
     */
    public int getRecordVersion() {
        return recordVersion;
    }

    /**
     * Sets the value of the recordVersion property.
     * 
     */
    public void setRecordVersion(int value) {
        this.recordVersion = value;
    }

}
