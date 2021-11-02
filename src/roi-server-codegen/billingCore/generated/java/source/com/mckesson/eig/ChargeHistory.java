
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ChargeHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargeHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalFeeCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalDocumentCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalShippingCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalSalesTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="unbillableAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeHistory", propOrder = {
    "totalFeeCharge",
    "totalDocumentCharge",
    "totalShippingCharge",
    "totalSalesTax",
    "releaseDate",
    "unbillableAmount"
})
public class ChargeHistory {

    protected double totalFeeCharge;
    protected double totalDocumentCharge;
    protected double totalShippingCharge;
    protected double totalSalesTax;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar releaseDate;
    protected double unbillableAmount;

    /**
     * Gets the value of the totalFeeCharge property.
     * 
     */
    public double getTotalFeeCharge() {
        return totalFeeCharge;
    }

    /**
     * Sets the value of the totalFeeCharge property.
     * 
     */
    public void setTotalFeeCharge(double value) {
        this.totalFeeCharge = value;
    }

    /**
     * Gets the value of the totalDocumentCharge property.
     * 
     */
    public double getTotalDocumentCharge() {
        return totalDocumentCharge;
    }

    /**
     * Sets the value of the totalDocumentCharge property.
     * 
     */
    public void setTotalDocumentCharge(double value) {
        this.totalDocumentCharge = value;
    }

    /**
     * Gets the value of the totalShippingCharge property.
     * 
     */
    public double getTotalShippingCharge() {
        return totalShippingCharge;
    }

    /**
     * Sets the value of the totalShippingCharge property.
     * 
     */
    public void setTotalShippingCharge(double value) {
        this.totalShippingCharge = value;
    }

    /**
     * Gets the value of the totalSalesTax property.
     * 
     */
    public double getTotalSalesTax() {
        return totalSalesTax;
    }

    /**
     * Sets the value of the totalSalesTax property.
     * 
     */
    public void setTotalSalesTax(double value) {
        this.totalSalesTax = value;
    }

    /**
     * Gets the value of the releaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReleaseDate(XMLGregorianCalendar value) {
        this.releaseDate = value;
    }

    /**
     * Gets the value of the unbillableAmount property.
     * 
     */
    public double getUnbillableAmount() {
        return unbillableAmount;
    }

    /**
     * Sets the value of the unbillableAmount property.
     * 
     */
    public void setUnbillableAmount(double value) {
        this.unbillableAmount = value;
    }

}
