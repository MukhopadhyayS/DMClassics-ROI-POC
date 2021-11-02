
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

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
 *         &lt;element name="hasLetterTemplate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "hasLetterTemplate"
})
@XmlRootElement(name = "hasLetterTemplateResponse")
public class HasLetterTemplateResponse {

    protected boolean hasLetterTemplate;

    /**
     * Gets the value of the hasLetterTemplate property.
     * 
     */
    public boolean isHasLetterTemplate() {
        return hasLetterTemplate;
    }

    /**
     * Sets the value of the hasLetterTemplate property.
     * 
     */
    public void setHasLetterTemplate(boolean value) {
        this.hasLetterTemplate = value;
    }

}
