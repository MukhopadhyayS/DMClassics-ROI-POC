
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
 *         &lt;element name="loadAssociation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "loadAssociation"
})
@XmlRootElement(name = "retrieveAllBillingTiers")
public class RetrieveAllBillingTiers {

    protected boolean loadAssociation;

    /**
     * Gets the value of the loadAssociation property.
     * 
     */
    public boolean isLoadAssociation() {
        return loadAssociation;
    }

    /**
     * Sets the value of the loadAssociation property.
     * 
     */
    public void setLoadAssociation(boolean value) {
        this.loadAssociation = value;
    }

}
