
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingPaymentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillingPaymentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymentMethodList" type="{urn:eig.mckesson.com}PaymentMethodList"/>
 *         &lt;element name="billingTemplateList" type="{urn:eig.mckesson.com}BillingTemplateList"/>
 *         &lt;element name="feeTypeList" type="{urn:eig.mckesson.com}FeeTypeList"/>
 *         &lt;element name="deliveryMethodList" type="{urn:eig.mckesson.com}DeliveryMethodList"/>
 *         &lt;element name="weight" type="{urn:eig.mckesson.com}Weight"/>
 *         &lt;element name="reasonsList" type="{urn:eig.mckesson.com}ReasonsList"/>
 *         &lt;element name="requestorType" type="{urn:eig.mckesson.com}RequestorType"/>
 *         &lt;element name="countries" type="{urn:eig.mckesson.com}Country" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingPaymentInfo", propOrder = {
    "paymentMethodList",
    "billingTemplateList",
    "feeTypeList",
    "deliveryMethodList",
    "weight",
    "reasonsList",
    "requestorType",
    "countries"
})
public class BillingPaymentInfo {

    @XmlElement(required = true)
    protected PaymentMethodList paymentMethodList;
    @XmlElement(required = true)
    protected BillingTemplateList billingTemplateList;
    @XmlElement(required = true)
    protected FeeTypeList feeTypeList;
    @XmlElement(required = true)
    protected DeliveryMethodList deliveryMethodList;
    @XmlElement(required = true)
    protected Weight weight;
    @XmlElement(required = true)
    protected ReasonsList reasonsList;
    @XmlElement(required = true)
    protected RequestorType requestorType;
    protected List<Country> countries;

    /**
     * Gets the value of the paymentMethodList property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMethodList }
     *     
     */
    public PaymentMethodList getPaymentMethodList() {
        return paymentMethodList;
    }

    /**
     * Sets the value of the paymentMethodList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMethodList }
     *     
     */
    public void setPaymentMethodList(PaymentMethodList value) {
        this.paymentMethodList = value;
    }

    /**
     * Gets the value of the billingTemplateList property.
     * 
     * @return
     *     possible object is
     *     {@link BillingTemplateList }
     *     
     */
    public BillingTemplateList getBillingTemplateList() {
        return billingTemplateList;
    }

    /**
     * Sets the value of the billingTemplateList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingTemplateList }
     *     
     */
    public void setBillingTemplateList(BillingTemplateList value) {
        this.billingTemplateList = value;
    }

    /**
     * Gets the value of the feeTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link FeeTypeList }
     *     
     */
    public FeeTypeList getFeeTypeList() {
        return feeTypeList;
    }

    /**
     * Sets the value of the feeTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeeTypeList }
     *     
     */
    public void setFeeTypeList(FeeTypeList value) {
        this.feeTypeList = value;
    }

    /**
     * Gets the value of the deliveryMethodList property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryMethodList }
     *     
     */
    public DeliveryMethodList getDeliveryMethodList() {
        return deliveryMethodList;
    }

    /**
     * Sets the value of the deliveryMethodList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryMethodList }
     *     
     */
    public void setDeliveryMethodList(DeliveryMethodList value) {
        this.deliveryMethodList = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setWeight(Weight value) {
        this.weight = value;
    }

    /**
     * Gets the value of the reasonsList property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonsList }
     *     
     */
    public ReasonsList getReasonsList() {
        return reasonsList;
    }

    /**
     * Sets the value of the reasonsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonsList }
     *     
     */
    public void setReasonsList(ReasonsList value) {
        this.reasonsList = value;
    }

    /**
     * Gets the value of the requestorType property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorType }
     *     
     */
    public RequestorType getRequestorType() {
        return requestorType;
    }

    /**
     * Sets the value of the requestorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorType }
     *     
     */
    public void setRequestorType(RequestorType value) {
        this.requestorType = value;
    }

    /**
     * Gets the value of the countries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the countries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCountries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Country }
     * 
     * 
     */
    public List<Country> getCountries() {
        if (countries == null) {
            countries = new ArrayList<Country>();
        }
        return this.countries;
    }

}
