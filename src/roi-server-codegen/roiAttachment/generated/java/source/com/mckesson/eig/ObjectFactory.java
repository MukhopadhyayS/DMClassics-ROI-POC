
package com.mckesson.eig;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mckesson.eig package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TransactionId_QNAME = new QName("urn:eig.mckesson.com", "transactionId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mckesson.eig
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteROIAttachmentsResponse }
     * 
     */
    public DeleteROIAttachmentsResponse createDeleteROIAttachmentsResponse() {
        return new DeleteROIAttachmentsResponse();
    }

    /**
     * Create an instance of {@link AttachmentDeleteStatusList }
     * 
     */
    public AttachmentDeleteStatusList createAttachmentDeleteStatusList() {
        return new AttachmentDeleteStatusList();
    }

    /**
     * Create an instance of {@link GetAllAttachments }
     * 
     */
    public GetAllAttachments createGetAllAttachments() {
        return new GetAllAttachments();
    }

    /**
     * Create an instance of {@link GetAllAttachmentsResponse }
     * 
     */
    public GetAllAttachmentsResponse createGetAllAttachmentsResponse() {
        return new GetAllAttachmentsResponse();
    }

    /**
     * Create an instance of {@link AttachmentInfoList }
     * 
     */
    public AttachmentInfoList createAttachmentInfoList() {
        return new AttachmentInfoList();
    }

    /**
     * Create an instance of {@link DeleteROIAttachments }
     * 
     */
    public DeleteROIAttachments createDeleteROIAttachments() {
        return new DeleteROIAttachments();
    }

    /**
     * Create an instance of {@link AttachmentSequenceList }
     * 
     */
    public AttachmentSequenceList createAttachmentSequenceList() {
        return new AttachmentSequenceList();
    }

    /**
     * Create an instance of {@link RetrieveAttachmentsInfoResponse }
     * 
     */
    public RetrieveAttachmentsInfoResponse createRetrieveAttachmentsInfoResponse() {
        return new RetrieveAttachmentsInfoResponse();
    }

    /**
     * Create an instance of {@link RetrieveAttachmentsInfo }
     * 
     */
    public RetrieveAttachmentsInfo createRetrieveAttachmentsInfo() {
        return new RetrieveAttachmentsInfo();
    }

    /**
     * Create an instance of {@link AttachmentDeleteStatus }
     * 
     */
    public AttachmentDeleteStatus createAttachmentDeleteStatus() {
        return new AttachmentDeleteStatus();
    }

    /**
     * Create an instance of {@link ROIAttachment }
     * 
     */
    public ROIAttachment createROIAttachment() {
        return new ROIAttachment();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "transactionId")
    public JAXBElement<Long> createTransactionId(Long value) {
        return new JAXBElement<Long>(_TransactionId_QNAME, Long.class, null, value);
    }

}
