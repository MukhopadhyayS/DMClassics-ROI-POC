
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestEvents complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestEvents">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="events" type="{urn:eig.mckesson.com}RequestEvent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="eventsCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestEvents", propOrder = {
    "events",
    "eventsCount"
})
public class RequestEvents {

    protected List<RequestEvent> events;
    protected int eventsCount;

    /**
     * Gets the value of the events property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the events property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEvents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestEvent }
     * 
     * 
     */
    public List<RequestEvent> getEvents() {
        if (events == null) {
            events = new ArrayList<RequestEvent>();
        }
        return this.events;
    }

    /**
     * Gets the value of the eventsCount property.
     * 
     */
    public int getEventsCount() {
        return eventsCount;
    }

    /**
     * Sets the value of the eventsCount property.
     * 
     */
    public void setEventsCount(int value) {
        this.eventsCount = value;
    }

}
