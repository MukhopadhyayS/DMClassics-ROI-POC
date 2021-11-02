
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SaveRequestPatientList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SaveRequestPatientList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="updatePatients" type="{urn:eig.mckesson.com}RequestPatient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateEncounters" type="{urn:eig.mckesson.com}RequestEncounter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateDocuments" type="{urn:eig.mckesson.com}RequestDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updatePages" type="{urn:eig.mckesson.com}RequestPage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateVersions" type="{urn:eig.mckesson.com}RequestVersion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deletePatient" type="{urn:eig.mckesson.com}DeletePatientList"/>
 *         &lt;element name="updateSupplementalDocument" type="{urn:eig.mckesson.com}RequestSupplementalDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateSupplementalAttachement" type="{urn:eig.mckesson.com}RequestSupplementalAttachment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaveRequestPatientList", propOrder = {
    "requestId",
    "updatePatients",
    "updateEncounters",
    "updateDocuments",
    "updatePages",
    "updateVersions",
    "deletePatient",
    "updateSupplementalDocument",
    "updateSupplementalAttachement"
})
public class SaveRequestPatientList {

    protected long requestId;
    protected List<RequestPatient> updatePatients;
    protected List<RequestEncounter> updateEncounters;
    protected List<RequestDocument> updateDocuments;
    protected List<RequestPage> updatePages;
    protected List<RequestVersion> updateVersions;
    @XmlElement(required = true)
    protected DeletePatientList deletePatient;
    protected List<RequestSupplementalDocument> updateSupplementalDocument;
    protected List<RequestSupplementalAttachment> updateSupplementalAttachement;

    /**
     * Gets the value of the requestId property.
     * 
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     */
    public void setRequestId(long value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the updatePatients property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updatePatients property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdatePatients().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestPatient }
     * 
     * 
     */
    public List<RequestPatient> getUpdatePatients() {
        if (updatePatients == null) {
            updatePatients = new ArrayList<RequestPatient>();
        }
        return this.updatePatients;
    }

    /**
     * Gets the value of the updateEncounters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateEncounters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateEncounters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestEncounter }
     * 
     * 
     */
    public List<RequestEncounter> getUpdateEncounters() {
        if (updateEncounters == null) {
            updateEncounters = new ArrayList<RequestEncounter>();
        }
        return this.updateEncounters;
    }

    /**
     * Gets the value of the updateDocuments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateDocuments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateDocuments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestDocument }
     * 
     * 
     */
    public List<RequestDocument> getUpdateDocuments() {
        if (updateDocuments == null) {
            updateDocuments = new ArrayList<RequestDocument>();
        }
        return this.updateDocuments;
    }

    /**
     * Gets the value of the updatePages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updatePages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdatePages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestPage }
     * 
     * 
     */
    public List<RequestPage> getUpdatePages() {
        if (updatePages == null) {
            updatePages = new ArrayList<RequestPage>();
        }
        return this.updatePages;
    }

    /**
     * Gets the value of the updateVersions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateVersions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateVersions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestVersion }
     * 
     * 
     */
    public List<RequestVersion> getUpdateVersions() {
        if (updateVersions == null) {
            updateVersions = new ArrayList<RequestVersion>();
        }
        return this.updateVersions;
    }

    /**
     * Gets the value of the deletePatient property.
     * 
     * @return
     *     possible object is
     *     {@link DeletePatientList }
     *     
     */
    public DeletePatientList getDeletePatient() {
        return deletePatient;
    }

    /**
     * Sets the value of the deletePatient property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeletePatientList }
     *     
     */
    public void setDeletePatient(DeletePatientList value) {
        this.deletePatient = value;
    }

    /**
     * Gets the value of the updateSupplementalDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateSupplementalDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateSupplementalDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestSupplementalDocument }
     * 
     * 
     */
    public List<RequestSupplementalDocument> getUpdateSupplementalDocument() {
        if (updateSupplementalDocument == null) {
            updateSupplementalDocument = new ArrayList<RequestSupplementalDocument>();
        }
        return this.updateSupplementalDocument;
    }

    /**
     * Gets the value of the updateSupplementalAttachement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateSupplementalAttachement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateSupplementalAttachement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestSupplementalAttachment }
     * 
     * 
     */
    public List<RequestSupplementalAttachment> getUpdateSupplementalAttachement() {
        if (updateSupplementalAttachement == null) {
            updateSupplementalAttachement = new ArrayList<RequestSupplementalAttachment>();
        }
        return this.updateSupplementalAttachement;
    }

}
