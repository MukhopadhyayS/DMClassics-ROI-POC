
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeletePatientList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeletePatientList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="encounterSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="docSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="versionSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attachmentSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="documentSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementalAttachmentSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementalDocumentSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementalPatientSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="darPatientSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="darSuppPatientSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePatientList", propOrder = {
    "pageSeq",
    "encounterSeq",
    "patientSeq",
    "docSeq",
    "versionSeq",
    "attachmentSeq",
    "documentSeq",
    "supplementalAttachmentSeq",
    "supplementalDocumentSeq",
    "supplementalPatientSeq",
    "darPatientSeq",
    "darSuppPatientSeq"
})
public class DeletePatientList {

    @XmlElement(type = Long.class)
    protected List<Long> pageSeq;
    @XmlElement(type = Long.class)
    protected List<Long> encounterSeq;
    @XmlElement(type = Long.class)
    protected List<Long> patientSeq;
    @XmlElement(type = Long.class)
    protected List<Long> docSeq;
    @XmlElement(type = Long.class)
    protected List<Long> versionSeq;
    @XmlElement(type = Long.class)
    protected List<Long> attachmentSeq;
    @XmlElement(type = Long.class)
    protected List<Long> documentSeq;
    @XmlElement(type = Long.class)
    protected List<Long> supplementalAttachmentSeq;
    @XmlElement(type = Long.class)
    protected List<Long> supplementalDocumentSeq;
    @XmlElement(type = Long.class)
    protected List<Long> supplementalPatientSeq;
    @XmlElement(type = Long.class)
    protected List<Long> darPatientSeq;
    @XmlElement(type = Long.class)
    protected List<Long> darSuppPatientSeq;

    /**
     * Gets the value of the pageSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getPageSeq() {
        if (pageSeq == null) {
            pageSeq = new ArrayList<Long>();
        }
        return this.pageSeq;
    }

    /**
     * Gets the value of the encounterSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the encounterSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEncounterSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getEncounterSeq() {
        if (encounterSeq == null) {
            encounterSeq = new ArrayList<Long>();
        }
        return this.encounterSeq;
    }

    /**
     * Gets the value of the patientSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the patientSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatientSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getPatientSeq() {
        if (patientSeq == null) {
            patientSeq = new ArrayList<Long>();
        }
        return this.patientSeq;
    }

    /**
     * Gets the value of the docSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the docSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getDocSeq() {
        if (docSeq == null) {
            docSeq = new ArrayList<Long>();
        }
        return this.docSeq;
    }

    /**
     * Gets the value of the versionSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the versionSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVersionSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getVersionSeq() {
        if (versionSeq == null) {
            versionSeq = new ArrayList<Long>();
        }
        return this.versionSeq;
    }

    /**
     * Gets the value of the attachmentSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachmentSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachmentSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getAttachmentSeq() {
        if (attachmentSeq == null) {
            attachmentSeq = new ArrayList<Long>();
        }
        return this.attachmentSeq;
    }

    /**
     * Gets the value of the documentSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getDocumentSeq() {
        if (documentSeq == null) {
            documentSeq = new ArrayList<Long>();
        }
        return this.documentSeq;
    }

    /**
     * Gets the value of the supplementalAttachmentSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplementalAttachmentSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplementalAttachmentSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getSupplementalAttachmentSeq() {
        if (supplementalAttachmentSeq == null) {
            supplementalAttachmentSeq = new ArrayList<Long>();
        }
        return this.supplementalAttachmentSeq;
    }

    /**
     * Gets the value of the supplementalDocumentSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplementalDocumentSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplementalDocumentSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getSupplementalDocumentSeq() {
        if (supplementalDocumentSeq == null) {
            supplementalDocumentSeq = new ArrayList<Long>();
        }
        return this.supplementalDocumentSeq;
    }

    /**
     * Gets the value of the supplementalPatientSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplementalPatientSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplementalPatientSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getSupplementalPatientSeq() {
        if (supplementalPatientSeq == null) {
            supplementalPatientSeq = new ArrayList<Long>();
        }
        return this.supplementalPatientSeq;
    }

    /**
     * Gets the value of the darPatientSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the darPatientSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDarPatientSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getDarPatientSeq() {
        if (darPatientSeq == null) {
            darPatientSeq = new ArrayList<Long>();
        }
        return this.darPatientSeq;
    }

    /**
     * Gets the value of the darSuppPatientSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the darSuppPatientSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDarSuppPatientSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getDarSuppPatientSeq() {
        if (darSuppPatientSeq == null) {
            darSuppPatientSeq = new ArrayList<Long>();
        }
        return this.darSuppPatientSeq;
    }

}
