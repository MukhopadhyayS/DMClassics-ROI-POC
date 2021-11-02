
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageLevelTier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageLevelTier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageLevelTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageCharge" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageLevelTier", propOrder = {
    "pageLevelTierId",
    "page",
    "pageCharge",
    "recordVersion"
})
public class PageLevelTier {

    protected long pageLevelTierId;
    protected int page;
    protected float pageCharge;
    protected int recordVersion;

    /**
     * Gets the value of the pageLevelTierId property.
     * 
     */
    public long getPageLevelTierId() {
        return pageLevelTierId;
    }

    /**
     * Sets the value of the pageLevelTierId property.
     * 
     */
    public void setPageLevelTierId(long value) {
        this.pageLevelTierId = value;
    }

    /**
     * Gets the value of the page property.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Gets the value of the pageCharge property.
     * 
     */
    public float getPageCharge() {
        return pageCharge;
    }

    /**
     * Sets the value of the pageCharge property.
     * 
     */
    public void setPageCharge(float value) {
        this.pageCharge = value;
    }

    /**
     * Gets the value of the recordVersion property.
     * 
     */
    public int getRecordVersion() {
        return recordVersion;
    }

    /**
     * Sets the value of the recordVersion property.
     * 
     */
    public void setRecordVersion(int value) {
        this.recordVersion = value;
    }

}
