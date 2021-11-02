
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestPart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestPart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contentSourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contentSourceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outputTransforms" type="{urn:eig.mckesson.com}OutputTransform" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestPart", propOrder = {
    "contentId",
    "contentSourceName",
    "contentSourceType",
    "properties",
    "outputTransforms"
})
public class RequestPart {

    @XmlElement(required = true)
    protected String contentId;
    @XmlElement(required = true)
    protected String contentSourceName;
    @XmlElement(required = true)
    protected String contentSourceType;
    protected List<MapModel> properties;
    protected List<OutputTransform> outputTransforms;

    /**
     * Gets the value of the contentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * Sets the value of the contentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentId(String value) {
        this.contentId = value;
    }

    /**
     * Gets the value of the contentSourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentSourceName() {
        return contentSourceName;
    }

    /**
     * Sets the value of the contentSourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentSourceName(String value) {
        this.contentSourceName = value;
    }

    /**
     * Gets the value of the contentSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentSourceType() {
        return contentSourceType;
    }

    /**
     * Sets the value of the contentSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentSourceType(String value) {
        this.contentSourceType = value;
    }

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
     * Gets the value of the outputTransforms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outputTransforms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutputTransforms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputTransform }
     * 
     * 
     */
    public List<OutputTransform> getOutputTransforms() {
        if (outputTransforms == null) {
            outputTransforms = new ArrayList<OutputTransform>();
        }
        return this.outputTransforms;
    }

}
