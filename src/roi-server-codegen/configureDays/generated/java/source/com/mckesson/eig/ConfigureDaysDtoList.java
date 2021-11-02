
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigureDaysDtoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigureDaysDtoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configureDaysList" type="{urn:eig.mckesson.com}ConfigureDaysDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigureDaysDtoList", propOrder = {
    "configureDaysList"
})
public class ConfigureDaysDtoList {

    protected List<ConfigureDaysDto> configureDaysList;

    /**
     * Gets the value of the configureDaysList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configureDaysList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigureDaysList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfigureDaysDto }
     * 
     * 
     */
    public List<ConfigureDaysDto> getConfigureDaysList() {
        if (configureDaysList == null) {
            configureDaysList = new ArrayList<ConfigureDaysDto>();
        }
        return this.configureDaysList;
    }

}
