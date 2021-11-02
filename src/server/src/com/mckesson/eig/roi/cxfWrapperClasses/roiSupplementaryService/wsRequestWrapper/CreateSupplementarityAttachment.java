
package com.mckesson.eig.roi.cxfWrapperClasses.roiSupplementaryService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;


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
 *         &lt;element name="supplementarityAttachment" type="{urn:eig.mckesson.com}SupplementarityAttachment"/>
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
    "supplementarityAttachment"
})
@XmlRootElement(name = "createSupplementarityAttachment")
public class CreateSupplementarityAttachment {

    @XmlElement(required = true)
    protected ROISupplementarityAttachment supplementarityAttachment;

    /**
     * Gets the value of the supplementarityAttachment property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementarityAttachment }
     *     
     */
    public ROISupplementarityAttachment getSupplementarityAttachment() {
        return supplementarityAttachment;
    }

    /**
     * Sets the value of the supplementarityAttachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementarityAttachment }
     *     
     */
    public void setSupplementarityAttachment(ROISupplementarityAttachment value) {
        this.supplementarityAttachment = value;
    }

}
