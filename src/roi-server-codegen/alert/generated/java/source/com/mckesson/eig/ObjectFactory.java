
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
     * Create an instance of {@link AlertResponse }
     * 
     */
    public AlertResponse createAlertResponse() {
        return new AlertResponse();
    }

    /**
     * Create an instance of {@link Alert }
     * 
     */
    public Alert createAlert() {
        return new Alert();
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
