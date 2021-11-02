
package com.mckesson.eig.roi.cxfWrapperClasses.billingCoreService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.ReleaseCore;


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
 *         &lt;element name="releaseCore" type="{urn:eig.mckesson.com}ReleaseCore"/>
 *         &lt;element name="amtAppliedFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="appliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "releaseCore",
    "amtAppliedFlag",
    "appliedAmount"
})
@XmlRootElement(name = "createReleaseAndPreviewInfo")
public class CreateReleaseAndPreviewInfo {

    @XmlElement(required = true)
    protected ReleaseCore releaseCore;
    protected boolean amtAppliedFlag;
    protected double appliedAmount;

    /**
     * Gets the value of the releaseCore property.
     * 
     * @return
     *     possible object is
     *     {@link ReleaseCore }
     *     
     */
    public ReleaseCore getReleaseCore() {
        return releaseCore;
    }

    /**
     * Sets the value of the releaseCore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReleaseCore }
     *     
     */
    public void setReleaseCore(ReleaseCore value) {
        this.releaseCore = value;
    }

    /**
     * Gets the value of the amtAppliedFlag property.
     * 
     */
    public boolean isAmtAppliedFlag() {
        return amtAppliedFlag;
    }

    /**
     * Sets the value of the amtAppliedFlag property.
     * 
     */
    public void setAmtAppliedFlag(boolean value) {
        this.amtAppliedFlag = value;
    }

    /**
     * Gets the value of the appliedAmount property.
     * 
     */
    public double getAppliedAmount() {
        return appliedAmount;
    }

    /**
     * Sets the value of the appliedAmount property.
     * 
     */
    public void setAppliedAmount(double value) {
        this.appliedAmount = value;
    }

}
