
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "invoiceId",
    "invoiceBalance"
})
@XmlRootElement(name = "updateInvoiceBalance")
public class UpdateInvoiceBalance {

    protected long invoiceId;
    protected double invoiceBalance;

    /**
     * Gets the value of the invoiceId property.
     * 
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the value of the invoiceId property.
     * 
     */
    public void setInvoiceId(long value) {
        this.invoiceId = value;
    }

    /**
     * Gets the value of the invoiceBalance property.
     * 
     */
    public double getInvoiceBalance() {
        return invoiceBalance;
    }

    /**
     * Sets the value of the invoiceBalance property.
     * 
     */
    public void setInvoiceBalance(double value) {
        this.invoiceBalance = value;
    }

}
