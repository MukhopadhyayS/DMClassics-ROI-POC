
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
 *         &lt;element name="letterTemplates" type="{urn:eig.mckesson.com}LetterTemplateList"/>
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
    "letterTemplates"
})
@XmlRootElement(name = "retrieveAllLetterTemplatesResponse")
public class RetrieveAllLetterTemplatesResponse {

    @XmlElement(required = true)
    protected LetterTemplateList letterTemplates;

    /**
     * Gets the value of the letterTemplates property.
     * 
     * @return
     *     possible object is
     *     {@link LetterTemplateList }
     *     
     */
    public LetterTemplateList getLetterTemplates() {
        return letterTemplates;
    }

    /**
     * Sets the value of the letterTemplates property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterTemplateList }
     *     
     */
    public void setLetterTemplates(LetterTemplateList value) {
        this.letterTemplates = value;
    }

}
