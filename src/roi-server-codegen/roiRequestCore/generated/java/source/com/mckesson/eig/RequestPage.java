
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestPage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestPage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="versionSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="contentCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="imnetId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSelectedForRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isReleased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="globalDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pageNumberRequested" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestPage", propOrder = {
    "pageSeq",
    "versionSeq",
    "contentCount",
    "imnetId",
    "isSelectedForRelease",
    "isReleased",
    "pageNumber",
    "globalDocument",
    "pageNumberRequested",
    "deleted"
})
public class RequestPage {

    protected long pageSeq;
    protected long versionSeq;
    protected long contentCount;
    @XmlElement(required = true)
    protected String imnetId;
    protected boolean isSelectedForRelease;
    protected boolean isReleased;
    protected int pageNumber;
    protected boolean globalDocument;
    protected int pageNumberRequested;
    protected boolean deleted;

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
     * Gets the value of the versionSeq property.
     * 
     */
    public long getVersionSeq() {
        return versionSeq;
    }

    /**
     * Sets the value of the versionSeq property.
     * 
     */
    public void setVersionSeq(long value) {
        this.versionSeq = value;
    }

    /**
     * Gets the value of the contentCount property.
     * 
     */
    public long getContentCount() {
        return contentCount;
    }

    /**
     * Sets the value of the contentCount property.
     * 
     */
    public void setContentCount(long value) {
        this.contentCount = value;
    }

    /**
     * Gets the value of the imnetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImnetId() {
        return imnetId;
    }

    /**
     * Sets the value of the imnetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImnetId(String value) {
        this.imnetId = value;
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

    /**
     * Gets the value of the isReleased property.
     * 
     */
    public boolean isIsReleased() {
        return isReleased;
    }

    /**
     * Sets the value of the isReleased property.
     * 
     */
    public void setIsReleased(boolean value) {
        this.isReleased = value;
    }

    /**
     * Gets the value of the pageNumber property.
     * 
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     */
    public void setPageNumber(int value) {
        this.pageNumber = value;
    }

    /**
     * Gets the value of the globalDocument property.
     * 
     */
    public boolean isGlobalDocument() {
        return globalDocument;
    }

    /**
     * Sets the value of the globalDocument property.
     * 
     */
    public void setGlobalDocument(boolean value) {
        this.globalDocument = value;
    }

    /**
     * Gets the value of the pageNumberRequested property.
     * 
     */
    public int getPageNumberRequested() {
        return pageNumberRequested;
    }

    /**
     * Sets the value of the pageNumberRequested property.
     * 
     */
    public void setPageNumberRequested(int value) {
        this.pageNumberRequested = value;
    }

    /**
     * Gets the value of the deleted property.
     * 
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the value of the deleted property.
     * 
     */
    public void setDeleted(boolean value) {
        this.deleted = value;
    }

}
