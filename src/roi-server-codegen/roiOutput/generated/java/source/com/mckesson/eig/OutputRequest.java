
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutputRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestParts" type="{urn:eig.mckesson.com}RequestPart" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="destId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="destName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="labels" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submitInfo" type="{urn:eig.mckesson.com}SubmitInfo"/>
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
@XmlType(name = "OutputRequest", propOrder = {
    "requestParts",
    "destId",
    "destName",
    "destType",
    "properties",
    "labels",
    "requestId",
    "submitInfo",
    "outputTransforms"
})
public class OutputRequest {

    protected List<RequestPart> requestParts;
    protected int destId;
    @XmlElement(required = true)
    protected String destName;
    @XmlElement(required = true)
    protected String destType;
    protected List<MapModel> properties;
    protected List<MapModel> labels;
    @XmlElement(required = true)
    protected String requestId;
    @XmlElement(required = true)
    protected SubmitInfo submitInfo;
    protected List<OutputTransform> outputTransforms;

    /**
     * Gets the value of the requestParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestPart }
     * 
     * 
     */
    public List<RequestPart> getRequestParts() {
        if (requestParts == null) {
            requestParts = new ArrayList<RequestPart>();
        }
        return this.requestParts;
    }

    /**
     * Gets the value of the destId property.
     * 
     */
    public int getDestId() {
        return destId;
    }

    /**
     * Sets the value of the destId property.
     * 
     */
    public void setDestId(int value) {
        this.destId = value;
    }

    /**
     * Gets the value of the destName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestName() {
        return destName;
    }

    /**
     * Sets the value of the destName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestName(String value) {
        this.destName = value;
    }

    /**
     * Gets the value of the destType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestType() {
        return destType;
    }

    /**
     * Sets the value of the destType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestType(String value) {
        this.destType = value;
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
     * Gets the value of the labels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the labels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MapModel }
     * 
     * 
     */
    public List<MapModel> getLabels() {
        if (labels == null) {
            labels = new ArrayList<MapModel>();
        }
        return this.labels;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the submitInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SubmitInfo }
     *     
     */
    public SubmitInfo getSubmitInfo() {
        return submitInfo;
    }

    /**
     * Sets the value of the submitInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmitInfo }
     *     
     */
    public void setSubmitInfo(SubmitInfo value) {
        this.submitInfo = value;
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
