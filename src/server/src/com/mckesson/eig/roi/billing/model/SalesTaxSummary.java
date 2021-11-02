package com.mckesson.eig.roi.billing.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesTaxSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesTaxSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="test" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="salesTaxReason" type="{urn:eig.mckesson.com}SalesTaxReason" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesTaxSummary", propOrder = {
    "test",
    "_salesTaxReason"
})
public class SalesTaxSummary {
    
    private String test;
    
    @XmlElement(name="salesTaxReason")
    private List<SalesTaxReason> _salesTaxReason;
    

    public List<SalesTaxReason> getSalesTaxReason() {
        return _salesTaxReason;
    }

    public void setSalesTaxReason(List<SalesTaxReason> salesTaxReasonList) {
        this._salesTaxReason = salesTaxReasonList;
    }
    
    public String getTest() {
        return test;
    }
    public void setTest(String value) {
        this.test = value;
    }


}
