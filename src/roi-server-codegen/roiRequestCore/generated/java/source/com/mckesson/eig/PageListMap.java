
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageListMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageListMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="isSelectedForRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageListMap", propOrder = {
    "pageSeq",
    "isSelectedForRelease"
})
public class PageListMap {

    protected long pageSeq;
    protected boolean isSelectedForRelease;

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
     * Gets the value of the isSelectedForRelease property.
     * 
     */
    public boolean isIsSelectedForRelease() {
        return isSelectedForRelease;
    }

    /**
     * Sets the value of the isSelectedForRelease property.
     * 
     */
    public void setIsSelectedForRelease(boolean value) {
        this.isSelectedForRelease = value;
    }

}
