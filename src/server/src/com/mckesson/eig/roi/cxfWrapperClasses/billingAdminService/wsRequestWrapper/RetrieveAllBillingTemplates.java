
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsRequestWrapper;

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
 *         &lt;element name="getAssociation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "getAssociation"
})
@XmlRootElement(name = "retrieveAllBillingTemplates")
public class RetrieveAllBillingTemplates {

    protected boolean getAssociation;

    /**
     * Gets the value of the getAssociation property.
     * 
     */
    public boolean isGetAssociation() {
        return getAssociation;
    }

    /**
     * Sets the value of the getAssociation property.
     * 
     */
    public void setGetAssociation(boolean value) {
        this.getAssociation = value;
    }

}
