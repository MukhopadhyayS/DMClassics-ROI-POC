
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CcdSourceDtoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CcdSourceDtoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ccdSourceDto" type="{urn:eig.mckesson.com}CcdSourceDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcdSourceDtoList", propOrder = {
    "ccdSourceDto"
})
public class CcdSourceDtoList {

    protected List<CcdSourceDto> ccdSourceDto;

    /**
     * Gets the value of the ccdSourceDto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ccdSourceDto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCcdSourceDto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CcdSourceDto }
     * 
     * 
     */
    public List<CcdSourceDto> getCcdSourceDto() {
        if (ccdSourceDto == null) {
            ccdSourceDto = new ArrayList<CcdSourceDto>();
        }
        return this.ccdSourceDto;
    }

}
