
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargeHistoryList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargeHistoryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chargeHistories" type="{urn:eig.mckesson.com}ChargeHistory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeHistoryList", propOrder = {
    "chargeHistories"
})
public class ChargeHistoryList {

    protected List<ChargeHistory> chargeHistories;

    /**
     * Gets the value of the chargeHistories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chargeHistories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChargeHistories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChargeHistory }
     * 
     * 
     */
    public List<ChargeHistory> getChargeHistories() {
        if (chargeHistories == null) {
            chargeHistories = new ArrayList<ChargeHistory>();
        }
        return this.chargeHistories;
    }

}
