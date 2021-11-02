
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReleaseHistoryList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseHistoryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="releasedPatients" type="{urn:eig.mckesson.com}ReleaseHistoryPatient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="history" type="{urn:eig.mckesson.com}ReleaseHistoryItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReleaseHistoryList", propOrder = {
    "releasedPatients",
    "history"
})
public class ReleaseHistoryList {

    protected List<ReleaseHistoryPatient> releasedPatients;
    protected List<ReleaseHistoryItem> history;

    /**
     * Gets the value of the releasedPatients property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the releasedPatients property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReleasedPatients().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReleaseHistoryPatient }
     * 
     * 
     */
    public List<ReleaseHistoryPatient> getReleasedPatients() {
        if (releasedPatients == null) {
            releasedPatients = new ArrayList<ReleaseHistoryPatient>();
        }
        return this.releasedPatients;
    }

    /**
     * Gets the value of the history property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the history property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReleaseHistoryItem }
     * 
     * 
     */
    public List<ReleaseHistoryItem> getHistory() {
        if (history == null) {
            history = new ArrayList<ReleaseHistoryItem>();
        }
        return this.history;
    }

}
