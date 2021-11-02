
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
 *         &lt;element name="docInfoList" type="{urn:eig.mckesson.com}DocInfoList"/>
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
    "docInfoList"
})
@XmlRootElement(name = "generateRequestorStatementResponse")
public class GenerateRequestorStatementResponse {

    @XmlElement(required = true)
    protected DocInfoList docInfoList;

    /**
     * Gets the value of the docInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link DocInfoList }
     *     
     */
    public DocInfoList getDocInfoList() {
        return docInfoList;
    }

    /**
     * Sets the value of the docInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocInfoList }
     *     
     */
    public void setDocInfoList(DocInfoList value) {
        this.docInfoList = value;
    }

}
