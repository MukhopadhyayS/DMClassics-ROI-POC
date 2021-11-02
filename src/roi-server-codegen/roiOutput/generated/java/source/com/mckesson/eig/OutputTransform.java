
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutputTransform complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputTransform">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transformName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transformType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputTransform", propOrder = {
    "properties",
    "transformName",
    "transformType"
})
public class OutputTransform {

    protected List<MapModel> properties;
    @XmlElement(required = true)
    protected String transformName;
    @XmlElement(required = true)
    protected String transformType;

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MapModel }
     * 
     * 
     */
    public List<MapModel> getProperties() {
        if (properties == null) {
            properties = new ArrayList<MapModel>();
        }
        return this.properties;
    }

    /**
     * Gets the value of the transformName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformName() {
        return transformName;
    }

    /**
     * Sets the value of the transformName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformName(String value) {
        this.transformName = value;
    }

    /**
     * Gets the value of the transformType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformType() {
        return transformType;
    }

    /**
     * Sets the value of the transformType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformType(String value) {
        this.transformType = value;
    }

}
