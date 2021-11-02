
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="searchResults" type="{urn:eig.mckesson.com}Requestor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="maxCountExceeded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorSearchResult", propOrder = {
    "searchResults",
    "requestorType",
    "maxCountExceeded"
})
public class RequestorSearchResult {

    protected List<Requestor> searchResults;
    protected long requestorType;
    protected boolean maxCountExceeded;

    /**
     * Gets the value of the searchResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Requestor }
     * 
     * 
     */
    public List<Requestor> getSearchResults() {
        if (searchResults == null) {
            searchResults = new ArrayList<Requestor>();
        }
        return this.searchResults;
    }

    /**
     * Gets the value of the requestorType property.
     * 
     */
    public long getRequestorType() {
        return requestorType;
    }

    /**
     * Sets the value of the requestorType property.
     * 
     */
    public void setRequestorType(long value) {
        this.requestorType = value;
    }

    /**
     * Gets the value of the maxCountExceeded property.
     * 
     */
    public boolean isMaxCountExceeded() {
        return maxCountExceeded;
    }

    /**
     * Sets the value of the maxCountExceeded property.
     * 
     */
    public void setMaxCountExceeded(boolean value) {
        this.maxCountExceeded = value;
    }

}
