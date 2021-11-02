
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ROIPages complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ROIPages">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="selfPayEncounter" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ROIPages", propOrder = {
    "pageSeq",
    "selfPayEncounter"
})
public class ROIPages {

    protected long pageSeq;
    protected boolean selfPayEncounter;

    /**
     * Gets the value of the pageSeq property.
     * 
     */
    public long getPageSeq() {
        return pageSeq;
    }

    /**
     * Sets the value of the pageSeq property.
     * 
     */
    public void setPageSeq(long value) {
        this.pageSeq = value;
    }

    /**
     * Gets the value of the selfPayEncounter property.
     * 
     */
    public boolean isSelfPayEncounter() {
        return selfPayEncounter;
    }

    /**
     * Sets the value of the selfPayEncounter property.
     * 
     */
    public void setSelfPayEncounter(boolean value) {
        this.selfPayEncounter = value;
    }

}
