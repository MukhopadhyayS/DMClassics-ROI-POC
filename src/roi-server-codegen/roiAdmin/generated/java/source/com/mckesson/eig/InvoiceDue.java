
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="invoiceDueDays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="customDate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDue", propOrder = {
    "id",
    "recordVersion",
    "invoiceDueDays",
    "customDate"
})
public class InvoiceDue {

    protected long id;
    protected int recordVersion;
    protected int invoiceDueDays;
    protected boolean customDate;

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

    /**
     * Gets the value of the invoiceDueDays property.
     * 
     */
    public int getInvoiceDueDays() {
        return invoiceDueDays;
    }

    /**
     * Sets the value of the invoiceDueDays property.
     * 
     */
    public void setInvoiceDueDays(int value) {
        this.invoiceDueDays = value;
    }

    /**
     * Gets the value of the customDate property.
     * 
     */
    public boolean isCustomDate() {
        return customDate;
    }

    /**
     * Sets the value of the customDate property.
     * 
     */
    public void setCustomDate(boolean value) {
        this.customDate = value;
    }

}
