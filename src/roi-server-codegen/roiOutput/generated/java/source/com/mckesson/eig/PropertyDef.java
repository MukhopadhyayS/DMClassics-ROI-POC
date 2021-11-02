
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="propertyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bindingProperty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="possibleValues" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pairPropertyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rowCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contextMenu" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="jsAction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxLength" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyDef", propOrder = {
    "propertyName",
    "label",
    "description",
    "dataType",
    "defaultValue",
    "required",
    "bindingProperty",
    "possibleValues",
    "index",
    "pairPropertyName",
    "rowCount",
    "contextMenu",
    "jsAction",
    "size",
    "maxLength"
})
public class PropertyDef {

    @XmlElement(required = true)
    protected String propertyName;
    @XmlElement(required = true)
    protected String label;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String dataType;
    @XmlElement(required = true)
    protected String defaultValue;
    @XmlElement(required = true)
    protected String required;
    @XmlElement(required = true)
    protected String bindingProperty;
    protected List<String> possibleValues;
    @XmlElement(required = true)
    protected String index;
    @XmlElement(required = true)
    protected String pairPropertyName;
    @XmlElement(required = true)
    protected String rowCount;
    protected List<String> contextMenu;
    @XmlElement(required = true)
    protected String jsAction;
    @XmlElement(required = true)
    protected String size;
    @XmlElement(required = true)
    protected String maxLength;

    /**
     * Gets the value of the propertyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the value of the propertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyName(String value) {
        this.propertyName = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the required property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequired(String value) {
        this.required = value;
    }

    /**
     * Gets the value of the bindingProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingProperty() {
        return bindingProperty;
    }

    /**
     * Sets the value of the bindingProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingProperty(String value) {
        this.bindingProperty = value;
    }

    /**
     * Gets the value of the possibleValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the possibleValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPossibleValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPossibleValues() {
        if (possibleValues == null) {
            possibleValues = new ArrayList<String>();
        }
        return this.possibleValues;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndex(String value) {
        this.index = value;
    }

    /**
     * Gets the value of the pairPropertyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPairPropertyName() {
        return pairPropertyName;
    }

    /**
     * Sets the value of the pairPropertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPairPropertyName(String value) {
        this.pairPropertyName = value;
    }

    /**
     * Gets the value of the rowCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowCount() {
        return rowCount;
    }

    /**
     * Sets the value of the rowCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowCount(String value) {
        this.rowCount = value;
    }

    /**
     * Gets the value of the contextMenu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contextMenu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContextMenu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getContextMenu() {
        if (contextMenu == null) {
            contextMenu = new ArrayList<String>();
        }
        return this.contextMenu;
    }

    /**
     * Gets the value of the jsAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJsAction() {
        return jsAction;
    }

    /**
     * Sets the value of the jsAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJsAction(String value) {
        this.jsAction = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxLength(String value) {
        this.maxLength = value;
    }

}
