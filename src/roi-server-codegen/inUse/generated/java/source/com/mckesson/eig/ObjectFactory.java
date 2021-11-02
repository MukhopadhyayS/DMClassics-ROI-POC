
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
    private final static QName _RetrieveInUseRecordResponseInUseRecord_QNAME = new QName("urn:eig.mckesson.com", "inUseRecord");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mckesson.eig
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TouchInUseRecord }
     * 
     */
    public TouchInUseRecord createTouchInUseRecord() {
        return new TouchInUseRecord();
    }

    /**
     * Create an instance of {@link TouchInUseRecordResponse }
     * 
     */
    public TouchInUseRecordResponse createTouchInUseRecordResponse() {
        return new TouchInUseRecordResponse();
    }

    /**
     * Create an instance of {@link InUseRecord }
     * 
     */
    public InUseRecord createInUseRecord() {
        return new InUseRecord();
    }

    /**
     * Create an instance of {@link RetrieveInUseRecordResponse }
     * 
     */
    public RetrieveInUseRecordResponse createRetrieveInUseRecordResponse() {
        return new RetrieveInUseRecordResponse();
    }

    /**
     * Create an instance of {@link ReleaseInUseRecordResponse }
     * 
     */
    public ReleaseInUseRecordResponse createReleaseInUseRecordResponse() {
        return new ReleaseInUseRecordResponse();
    }

    /**
     * Create an instance of {@link CreateInUseRecord }
     * 
     */
    public CreateInUseRecord createCreateInUseRecord() {
        return new CreateInUseRecord();
    }

    /**
     * Create an instance of {@link RetrieveInUseRecord }
     * 
     */
    public RetrieveInUseRecord createRetrieveInUseRecord() {
        return new RetrieveInUseRecord();
    }

    /**
     * Create an instance of {@link RetrieveAllInUseRecords }
     * 
     */
    public RetrieveAllInUseRecords createRetrieveAllInUseRecords() {
        return new RetrieveAllInUseRecords();
    }

    /**
     * Create an instance of {@link RetrieveInUseRecordsByType }
     * 
     */
    public RetrieveInUseRecordsByType createRetrieveInUseRecordsByType() {
        return new RetrieveInUseRecordsByType();
    }

    /**
     * Create an instance of {@link RetrieveInUseRecordsByTypeResponse }
     * 
     */
    public RetrieveInUseRecordsByTypeResponse createRetrieveInUseRecordsByTypeResponse() {
        return new RetrieveInUseRecordsByTypeResponse();
    }

    /**
     * Create an instance of {@link InUseRecordList }
     * 
     */
    public InUseRecordList createInUseRecordList() {
        return new InUseRecordList();
    }

    /**
     * Create an instance of {@link RetrieveAllInUseRecordsResponse }
     * 
     */
    public RetrieveAllInUseRecordsResponse createRetrieveAllInUseRecordsResponse() {
        return new RetrieveAllInUseRecordsResponse();
    }

    /**
     * Create an instance of {@link CreateInUseRecordResponse }
     * 
     */
    public CreateInUseRecordResponse createCreateInUseRecordResponse() {
        return new CreateInUseRecordResponse();
    }

    /**
     * Create an instance of {@link RetrieveInUseRecordsByIDs }
     * 
     */
    public RetrieveInUseRecordsByIDs createRetrieveInUseRecordsByIDs() {
        return new RetrieveInUseRecordsByIDs();
    }

    /**
     * Create an instance of {@link ReleaseInUseRecord }
     * 
     */
    public ReleaseInUseRecord createReleaseInUseRecord() {
        return new ReleaseInUseRecord();
    }

    /**
     * Create an instance of {@link RetrieveInUseRecordsByIDsResponse }
     * 
     */
    public RetrieveInUseRecordsByIDsResponse createRetrieveInUseRecordsByIDsResponse() {
        return new RetrieveInUseRecordsByIDsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "transactionId")
    public JAXBElement<Long> createTransactionId(Long value) {
        return new JAXBElement<Long>(_TransactionId_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InUseRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "inUseRecord", scope = RetrieveInUseRecordResponse.class)
    public JAXBElement<InUseRecord> createRetrieveInUseRecordResponseInUseRecord(InUseRecord value) {
        return new JAXBElement<InUseRecord>(_RetrieveInUseRecordResponseInUseRecord_QNAME, InUseRecord.class, RetrieveInUseRecordResponse.class, value);
    }

}
