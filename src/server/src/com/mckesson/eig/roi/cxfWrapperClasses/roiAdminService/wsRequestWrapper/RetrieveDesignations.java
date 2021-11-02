
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsRequestWrapper;

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
 *         &lt;element name="codeSetId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "codeSetId"
})
@XmlRootElement(name = "retrieveDesignations")
public class RetrieveDesignations {

    protected long codeSetId;

    /**
     * Gets the value of the codeSetId property.
     * 
     */
    public long getCodeSetId() {
        return codeSetId;
    }

    /**
     * Sets the value of the codeSetId property.
     * 
     */
    public void setCodeSetId(long value) {
        this.codeSetId = value;
    }

}
