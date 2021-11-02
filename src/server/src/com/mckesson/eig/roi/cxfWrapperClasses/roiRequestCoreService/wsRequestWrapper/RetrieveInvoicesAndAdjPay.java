
package com.mckesson.eig.roi.cxfWrapperClasses.roiRequestCoreService.wsRequestWrapper;

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
 *         &lt;element name="requestCoreId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "requestCoreId"
})
@XmlRootElement(name = "retrieveInvoicesAndAdjPay")
public class RetrieveInvoicesAndAdjPay {

    protected long requestCoreId;

    /**
     * Gets the value of the requestCoreId property.
     * 
     */
    public long getRequestCoreId() {
        return requestCoreId;
    }

    /**
     * Sets the value of the requestCoreId property.
     * 
     */
    public void setRequestCoreId(long value) {
        this.requestCoreId = value;
    }

}
