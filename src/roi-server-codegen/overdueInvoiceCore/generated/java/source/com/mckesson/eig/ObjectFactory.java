
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
     * Create an instance of {@link SearchOverDueInvoicesResponse }
     * 
     */
    public SearchOverDueInvoicesResponse createSearchOverDueInvoicesResponse() {
        return new SearchOverDueInvoicesResponse();
    }

    /**
     * Create an instance of {@link SearchPastDueInvoiceResult }
     * 
     */
    public SearchPastDueInvoiceResult createSearchPastDueInvoiceResult() {
        return new SearchPastDueInvoiceResult();
    }

    /**
     * Create an instance of {@link RegenerateInvoiceCoreAndLetterResponse }
     * 
     */
    public RegenerateInvoiceCoreAndLetterResponse createRegenerateInvoiceCoreAndLetterResponse() {
        return new RegenerateInvoiceCoreAndLetterResponse();
    }

    /**
     * Create an instance of {@link OverDueDocInfoList }
     * 
     */
    public OverDueDocInfoList createOverDueDocInfoList() {
        return new OverDueDocInfoList();
    }

    /**
     * Create an instance of {@link SearchOverDueInvoices }
     * 
     */
    public SearchOverDueInvoices createSearchOverDueInvoices() {
        return new SearchOverDueInvoices();
    }

    /**
     * Create an instance of {@link SearchPastDueInvoiceCriteria }
     * 
     */
    public SearchPastDueInvoiceCriteria createSearchPastDueInvoiceCriteria() {
        return new SearchPastDueInvoiceCriteria();
    }

    /**
     * Create an instance of {@link RegenerateInvoiceCoreAndLetter }
     * 
     */
    public RegenerateInvoiceCoreAndLetter createRegenerateInvoiceCoreAndLetter() {
        return new RegenerateInvoiceCoreAndLetter();
    }

    /**
     * Create an instance of {@link InvoiceAndLetterInfo }
     * 
     */
    public InvoiceAndLetterInfo createInvoiceAndLetterInfo() {
        return new InvoiceAndLetterInfo();
    }

    /**
     * Create an instance of {@link RequestorInvoices }
     * 
     */
    public RequestorInvoices createRequestorInvoices() {
        return new RequestorInvoices();
    }

    /**
     * Create an instance of {@link RequestorStatementCriteria }
     * 
     */
    public RequestorStatementCriteria createRequestorStatementCriteria() {
        return new RequestorStatementCriteria();
    }

    /**
     * Create an instance of {@link PastDueInvoices }
     * 
     */
    public PastDueInvoices createPastDueInvoices() {
        return new PastDueInvoices();
    }

    /**
     * Create an instance of {@link PastDueInvoice }
     * 
     */
    public PastDueInvoice createPastDueInvoice() {
        return new PastDueInvoice();
    }

    /**
     * Create an instance of {@link PropertiesMap }
     * 
     */
    public PropertiesMap createPropertiesMap() {
        return new PropertiesMap();
    }

    /**
     * Create an instance of {@link OverDueDocInfo }
     * 
     */
    public OverDueDocInfo createOverDueDocInfo() {
        return new OverDueDocInfo();
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
