
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="versionSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="documentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="versionNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="globalDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="roiPages" type="{urn:eig.mckesson.com}RequestPage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestVersion", propOrder = {
    "versionSeq",
    "documentSeq",
    "docId",
    "versionNumber",
    "globalDocument",
    "roiPages"
})
public class RequestVersion {

    protected long versionSeq;
    protected long documentSeq;
    protected long docId;
    protected long versionNumber;
    protected boolean globalDocument;
    protected List<RequestPage> roiPages;

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
     * Gets the value of the documentSeq property.
     * 
     */
    public long getDocumentSeq() {
        return documentSeq;
    }

    /**
     * Sets the value of the documentSeq property.
     * 
     */
    public void setDocumentSeq(long value) {
        this.documentSeq = value;
    }

    /**
     * Gets the value of the docId property.
     * 
     */
    public long getDocId() {
        return docId;
    }

    /**
     * Sets the value of the docId property.
     * 
     */
    public void setDocId(long value) {
        this.docId = value;
    }

    /**
     * Gets the value of the versionNumber property.
     * 
     */
    public long getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     * 
     */
    public void setVersionNumber(long value) {
        this.versionNumber = value;
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
     * Gets the value of the roiPages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roiPages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoiPages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestPage }
     * 
     * 
     */
    public List<RequestPage> getRoiPages() {
        if (roiPages == null) {
            roiPages = new ArrayList<RequestPage>();
        }
        return this.roiPages;
    }

}
