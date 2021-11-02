
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="docInfo" type="{urn:eig.mckesson.com}DocInfo"/>
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
    "docInfo"
})
@XmlRootElement(name = "createLetterAndPreviewResponse")
public class CreateLetterAndPreviewResponse {

    @XmlElement(required = true)
    protected DocInfo docInfo;

    /**
     * Gets the value of the docInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DocInfo }
     *     
     */
    public DocInfo getDocInfo() {
        return docInfo;
    }

    /**
     * Sets the value of the docInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocInfo }
     *     
     */
    public void setDocInfo(DocInfo value) {
        this.docInfo = value;
    }

}
