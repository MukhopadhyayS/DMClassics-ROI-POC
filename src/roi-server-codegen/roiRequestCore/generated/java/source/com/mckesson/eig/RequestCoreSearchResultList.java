
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestCoreSearchResultList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreSearchResultList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreSearchResult" type="{urn:eig.mckesson.com}RequestCoreSearchResult" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "RequestCoreSearchResultList", propOrder = {
    "requestCoreSearchResult",
    "maxCountExceeded"
})
public class RequestCoreSearchResultList {

    protected List<RequestCoreSearchResult> requestCoreSearchResult;
    protected boolean maxCountExceeded;

    /**
     * Gets the value of the requestCoreSearchResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestCoreSearchResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestCoreSearchResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestCoreSearchResult }
     * 
     * 
     */
    public List<RequestCoreSearchResult> getRequestCoreSearchResult() {
        if (requestCoreSearchResult == null) {
            requestCoreSearchResult = new ArrayList<RequestCoreSearchResult>();
        }
        return this.requestCoreSearchResult;
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
