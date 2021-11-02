
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreCharges complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreCharges">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="requestCoreSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="previouslyReleasedCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="releaseCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalRequestCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="balanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPagesReleased" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="salesTaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="salesTaxPercentage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="billingType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="originalBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="creditAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="debitAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="billingLocCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingLocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="applySalesTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="displayBillingPaymentInfo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceBaseCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceAutoAdjustment" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="released" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasUnReleasedInvoices" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoicesBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoicesSalesTaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="unbillable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasInvoices" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="totalUnappliedPaymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalUnappliedAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalUnappliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="salesTaxSummary" type="{urn:eig.mckesson.com}SalesTaxSummary"/>
 *         &lt;element name="requestCoreChargesBilling" type="{urn:eig.mckesson.com}RequestCoreChargesBilling"/>
 *         &lt;element name="requestCoreChargesShipping" type="{urn:eig.mckesson.com}RequestCoreChargesShipping"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreCharges", propOrder = {
    "id",
    "createdBy",
    "modifiedBy",
    "createdDt",
    "modifiedDt",
    "recordVersion",
    "requestCoreSeq",
    "previouslyReleasedCost",
    "releaseDate",
    "releaseCost",
    "totalPages",
    "totalRequestCost",
    "balanceDue",
    "totalPagesReleased",
    "salesTaxAmount",
    "salesTaxPercentage",
    "billingType",
    "originalBalance",
    "paymentAmount",
    "creditAdjustmentAmount",
    "debitAdjustmentAmount",
    "billingLocCode",
    "billingLocName",
    "applySalesTax",
    "displayBillingPaymentInfo",
    "invoiceBaseCharge",
    "invoiceAutoAdjustment",
    "released",
    "hasUnReleasedInvoices",
    "invoicesBalance",
    "invoicesSalesTaxAmount",
    "unbillable",
    "hasInvoices",
    "totalUnappliedPaymentAmount",
    "totalUnappliedAdjustmentAmount",
    "totalUnappliedAmount",
    "salesTaxSummary",
    "requestCoreChargesBilling",
    "requestCoreChargesShipping"
})
public class RequestCoreCharges {

    protected long id;
    protected long createdBy;
    protected long modifiedBy;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDt;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDt;
    protected int recordVersion;
    protected long requestCoreSeq;
    protected double previouslyReleasedCost;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar releaseDate;
    protected double releaseCost;
    protected int totalPages;
    protected double totalRequestCost;
    protected double balanceDue;
    protected int totalPagesReleased;
    protected double salesTaxAmount;
    protected double salesTaxPercentage;
    @XmlElement(required = true)
    protected String billingType;
    protected double originalBalance;
    protected double paymentAmount;
    protected double creditAdjustmentAmount;
    protected double debitAdjustmentAmount;
    @XmlElement(required = true)
    protected String billingLocCode;
    @XmlElement(required = true)
    protected String billingLocName;
    protected boolean applySalesTax;
    protected boolean displayBillingPaymentInfo;
    protected double invoiceBaseCharge;
    protected double invoiceAutoAdjustment;
    protected boolean released;
    protected boolean hasUnReleasedInvoices;
    protected double invoicesBalance;
    protected double invoicesSalesTaxAmount;
    protected boolean unbillable;
    protected boolean hasInvoices;
    protected double totalUnappliedPaymentAmount;
    protected double totalUnappliedAdjustmentAmount;
    protected double totalUnappliedAmount;
    @XmlElement(required = true)
    protected SalesTaxSummary salesTaxSummary;
    @XmlElement(required = true)
    protected RequestCoreChargesBilling requestCoreChargesBilling;
    @XmlElement(required = true)
    protected RequestCoreChargesShipping requestCoreChargesShipping;

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
     * Gets the value of the createdBy property.
     * 
     */
    public long getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     */
    public void setCreatedBy(long value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     */
    public long getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     */
    public void setModifiedBy(long value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the createdDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDt() {
        return createdDt;
    }

    /**
     * Sets the value of the createdDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDt(XMLGregorianCalendar value) {
        this.createdDt = value;
    }

    /**
     * Gets the value of the modifiedDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDt() {
        return modifiedDt;
    }

    /**
     * Sets the value of the modifiedDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDt(XMLGregorianCalendar value) {
        this.modifiedDt = value;
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
     * Gets the value of the requestCoreSeq property.
     * 
     */
    public long getRequestCoreSeq() {
        return requestCoreSeq;
    }

    /**
     * Sets the value of the requestCoreSeq property.
     * 
     */
    public void setRequestCoreSeq(long value) {
        this.requestCoreSeq = value;
    }

    /**
     * Gets the value of the previouslyReleasedCost property.
     * 
     */
    public double getPreviouslyReleasedCost() {
        return previouslyReleasedCost;
    }

    /**
     * Sets the value of the previouslyReleasedCost property.
     * 
     */
    public void setPreviouslyReleasedCost(double value) {
        this.previouslyReleasedCost = value;
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
     * Gets the value of the releaseCost property.
     * 
     */
    public double getReleaseCost() {
        return releaseCost;
    }

    /**
     * Sets the value of the releaseCost property.
     * 
     */
    public void setReleaseCost(double value) {
        this.releaseCost = value;
    }

    /**
     * Gets the value of the totalPages property.
     * 
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the value of the totalPages property.
     * 
     */
    public void setTotalPages(int value) {
        this.totalPages = value;
    }

    /**
     * Gets the value of the totalRequestCost property.
     * 
     */
    public double getTotalRequestCost() {
        return totalRequestCost;
    }

    /**
     * Sets the value of the totalRequestCost property.
     * 
     */
    public void setTotalRequestCost(double value) {
        this.totalRequestCost = value;
    }

    /**
     * Gets the value of the balanceDue property.
     * 
     */
    public double getBalanceDue() {
        return balanceDue;
    }

    /**
     * Sets the value of the balanceDue property.
     * 
     */
    public void setBalanceDue(double value) {
        this.balanceDue = value;
    }

    /**
     * Gets the value of the totalPagesReleased property.
     * 
     */
    public int getTotalPagesReleased() {
        return totalPagesReleased;
    }

    /**
     * Sets the value of the totalPagesReleased property.
     * 
     */
    public void setTotalPagesReleased(int value) {
        this.totalPagesReleased = value;
    }

    /**
     * Gets the value of the salesTaxAmount property.
     * 
     */
    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    /**
     * Sets the value of the salesTaxAmount property.
     * 
     */
    public void setSalesTaxAmount(double value) {
        this.salesTaxAmount = value;
    }

    /**
     * Gets the value of the salesTaxPercentage property.
     * 
     */
    public double getSalesTaxPercentage() {
        return salesTaxPercentage;
    }

    /**
     * Sets the value of the salesTaxPercentage property.
     * 
     */
    public void setSalesTaxPercentage(double value) {
        this.salesTaxPercentage = value;
    }

    /**
     * Gets the value of the billingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingType() {
        return billingType;
    }

    /**
     * Sets the value of the billingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingType(String value) {
        this.billingType = value;
    }

    /**
     * Gets the value of the originalBalance property.
     * 
     */
    public double getOriginalBalance() {
        return originalBalance;
    }

    /**
     * Sets the value of the originalBalance property.
     * 
     */
    public void setOriginalBalance(double value) {
        this.originalBalance = value;
    }

    /**
     * Gets the value of the paymentAmount property.
     * 
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     */
    public void setPaymentAmount(double value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the creditAdjustmentAmount property.
     * 
     */
    public double getCreditAdjustmentAmount() {
        return creditAdjustmentAmount;
    }

    /**
     * Sets the value of the creditAdjustmentAmount property.
     * 
     */
    public void setCreditAdjustmentAmount(double value) {
        this.creditAdjustmentAmount = value;
    }

    /**
     * Gets the value of the debitAdjustmentAmount property.
     * 
     */
    public double getDebitAdjustmentAmount() {
        return debitAdjustmentAmount;
    }

    /**
     * Sets the value of the debitAdjustmentAmount property.
     * 
     */
    public void setDebitAdjustmentAmount(double value) {
        this.debitAdjustmentAmount = value;
    }

    /**
     * Gets the value of the billingLocCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingLocCode() {
        return billingLocCode;
    }

    /**
     * Sets the value of the billingLocCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingLocCode(String value) {
        this.billingLocCode = value;
    }

    /**
     * Gets the value of the billingLocName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingLocName() {
        return billingLocName;
    }

    /**
     * Sets the value of the billingLocName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingLocName(String value) {
        this.billingLocName = value;
    }

    /**
     * Gets the value of the applySalesTax property.
     * 
     */
    public boolean isApplySalesTax() {
        return applySalesTax;
    }

    /**
     * Sets the value of the applySalesTax property.
     * 
     */
    public void setApplySalesTax(boolean value) {
        this.applySalesTax = value;
    }

    /**
     * Gets the value of the displayBillingPaymentInfo property.
     * 
     */
    public boolean isDisplayBillingPaymentInfo() {
        return displayBillingPaymentInfo;
    }

    /**
     * Sets the value of the displayBillingPaymentInfo property.
     * 
     */
    public void setDisplayBillingPaymentInfo(boolean value) {
        this.displayBillingPaymentInfo = value;
    }

    /**
     * Gets the value of the invoiceBaseCharge property.
     * 
     */
    public double getInvoiceBaseCharge() {
        return invoiceBaseCharge;
    }

    /**
     * Sets the value of the invoiceBaseCharge property.
     * 
     */
    public void setInvoiceBaseCharge(double value) {
        this.invoiceBaseCharge = value;
    }

    /**
     * Gets the value of the invoiceAutoAdjustment property.
     * 
     */
    public double getInvoiceAutoAdjustment() {
        return invoiceAutoAdjustment;
    }

    /**
     * Sets the value of the invoiceAutoAdjustment property.
     * 
     */
    public void setInvoiceAutoAdjustment(double value) {
        this.invoiceAutoAdjustment = value;
    }

    /**
     * Gets the value of the released property.
     * 
     */
    public boolean isReleased() {
        return released;
    }

    /**
     * Sets the value of the released property.
     * 
     */
    public void setReleased(boolean value) {
        this.released = value;
    }

    /**
     * Gets the value of the hasUnReleasedInvoices property.
     * 
     */
    public boolean isHasUnReleasedInvoices() {
        return hasUnReleasedInvoices;
    }

    /**
     * Sets the value of the hasUnReleasedInvoices property.
     * 
     */
    public void setHasUnReleasedInvoices(boolean value) {
        this.hasUnReleasedInvoices = value;
    }

    /**
     * Gets the value of the invoicesBalance property.
     * 
     */
    public double getInvoicesBalance() {
        return invoicesBalance;
    }

    /**
     * Sets the value of the invoicesBalance property.
     * 
     */
    public void setInvoicesBalance(double value) {
        this.invoicesBalance = value;
    }

    /**
     * Gets the value of the invoicesSalesTaxAmount property.
     * 
     */
    public double getInvoicesSalesTaxAmount() {
        return invoicesSalesTaxAmount;
    }

    /**
     * Sets the value of the invoicesSalesTaxAmount property.
     * 
     */
    public void setInvoicesSalesTaxAmount(double value) {
        this.invoicesSalesTaxAmount = value;
    }

    /**
     * Gets the value of the unbillable property.
     * 
     */
    public boolean isUnbillable() {
        return unbillable;
    }

    /**
     * Sets the value of the unbillable property.
     * 
     */
    public void setUnbillable(boolean value) {
        this.unbillable = value;
    }

    /**
     * Gets the value of the hasInvoices property.
     * 
     */
    public boolean isHasInvoices() {
        return hasInvoices;
    }

    /**
     * Sets the value of the hasInvoices property.
     * 
     */
    public void setHasInvoices(boolean value) {
        this.hasInvoices = value;
    }

    /**
     * Gets the value of the totalUnappliedPaymentAmount property.
     * 
     */
    public double getTotalUnappliedPaymentAmount() {
        return totalUnappliedPaymentAmount;
    }

    /**
     * Sets the value of the totalUnappliedPaymentAmount property.
     * 
     */
    public void setTotalUnappliedPaymentAmount(double value) {
        this.totalUnappliedPaymentAmount = value;
    }

    /**
     * Gets the value of the totalUnappliedAdjustmentAmount property.
     * 
     */
    public double getTotalUnappliedAdjustmentAmount() {
        return totalUnappliedAdjustmentAmount;
    }

    /**
     * Sets the value of the totalUnappliedAdjustmentAmount property.
     * 
     */
    public void setTotalUnappliedAdjustmentAmount(double value) {
        this.totalUnappliedAdjustmentAmount = value;
    }

    /**
     * Gets the value of the totalUnappliedAmount property.
     * 
     */
    public double getTotalUnappliedAmount() {
        return totalUnappliedAmount;
    }

    /**
     * Sets the value of the totalUnappliedAmount property.
     * 
     */
    public void setTotalUnappliedAmount(double value) {
        this.totalUnappliedAmount = value;
    }

    /**
     * Gets the value of the salesTaxSummary property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxSummary }
     *     
     */
    public SalesTaxSummary getSalesTaxSummary() {
        return salesTaxSummary;
    }

    /**
     * Sets the value of the salesTaxSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxSummary }
     *     
     */
    public void setSalesTaxSummary(SalesTaxSummary value) {
        this.salesTaxSummary = value;
    }

    /**
     * Gets the value of the requestCoreChargesBilling property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCoreChargesBilling }
     *     
     */
    public RequestCoreChargesBilling getRequestCoreChargesBilling() {
        return requestCoreChargesBilling;
    }

    /**
     * Sets the value of the requestCoreChargesBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCoreChargesBilling }
     *     
     */
    public void setRequestCoreChargesBilling(RequestCoreChargesBilling value) {
        this.requestCoreChargesBilling = value;
    }

    /**
     * Gets the value of the requestCoreChargesShipping property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCoreChargesShipping }
     *     
     */
    public RequestCoreChargesShipping getRequestCoreChargesShipping() {
        return requestCoreChargesShipping;
    }

    /**
     * Sets the value of the requestCoreChargesShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCoreChargesShipping }
     *     
     */
    public void setRequestCoreChargesShipping(RequestCoreChargesShipping value) {
        this.requestCoreChargesShipping = value;
    }

}
