
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
 *         &lt;element name="letterInfo" type="{urn:eig.mckesson.com}LetterInfo"/>
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
    "letterInfo"
})
@XmlRootElement(name = "createLetterAndPreview")
public class CreateLetterAndPreview {

    @XmlElement(required = true)
    protected LetterInfo letterInfo;

    /**
     * Gets the value of the letterInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LetterInfo }
     *     
     */
    public LetterInfo getLetterInfo() {
        return letterInfo;
    }

    /**
     * Sets the value of the letterInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterInfo }
     *     
     */
    public void setLetterInfo(LetterInfo value) {
        this.letterInfo = value;
    }

}
