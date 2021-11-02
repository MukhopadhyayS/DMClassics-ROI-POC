
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestStatusMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestStatusMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statusMap" type="{urn:eig.mckesson.com}StatusMap" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="loggedStatus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestStatusMap", propOrder = {
    "statusMap",
    "loggedStatus"
})
public class RequestStatusMap {

    protected List<StatusMap> statusMap;
    protected int loggedStatus;

    /**
     * Gets the value of the statusMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statusMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatusMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatusMap }
     * 
     * 
     */
    public List<StatusMap> getStatusMap() {
        if (statusMap == null) {
            statusMap = new ArrayList<StatusMap>();
        }
        return this.statusMap;
    }

    /**
     * Gets the value of the loggedStatus property.
     * 
     */
    public int getLoggedStatus() {
        return loggedStatus;
    }

    /**
     * Sets the value of the loggedStatus property.
     * 
     */
    public void setLoggedStatus(int value) {
        this.loggedStatus = value;
    }

}
