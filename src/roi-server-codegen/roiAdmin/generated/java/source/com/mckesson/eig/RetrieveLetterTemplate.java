
package com.mckesson.eig;

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
 *         &lt;element name="letterTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "letterTemplateId"
})
@XmlRootElement(name = "retrieveLetterTemplate")
public class RetrieveLetterTemplate {

    protected long letterTemplateId;

    /**
     * Gets the value of the letterTemplateId property.
     * 
     */
    public long getLetterTemplateId() {
        return letterTemplateId;
    }

    /**
     * Sets the value of the letterTemplateId property.
     * 
     */
    public void setLetterTemplateId(long value) {
        this.letterTemplateId = value;
    }

}
