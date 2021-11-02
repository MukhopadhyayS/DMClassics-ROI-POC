
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
     * Create an instance of {@link UpdateDaysStatusResponse }
     * 
     */
    public UpdateDaysStatusResponse createUpdateDaysStatusResponse() {
        return new UpdateDaysStatusResponse();
    }

    /**
     * Create an instance of {@link RetrieveWeekendDays }
     * 
     */
    public RetrieveWeekendDays createRetrieveWeekendDays() {
        return new RetrieveWeekendDays();
    }

    /**
     * Create an instance of {@link RetrieveConfigureDaysStatus }
     * 
     */
    public RetrieveConfigureDaysStatus createRetrieveConfigureDaysStatus() {
        return new RetrieveConfigureDaysStatus();
    }

    /**
     * Create an instance of {@link RetrieveConfigureDaysStatusResponse }
     * 
     */
    public RetrieveConfigureDaysStatusResponse createRetrieveConfigureDaysStatusResponse() {
        return new RetrieveConfigureDaysStatusResponse();
    }

    /**
     * Create an instance of {@link ConfigureDaysDtoList }
     * 
     */
    public ConfigureDaysDtoList createConfigureDaysDtoList() {
        return new ConfigureDaysDtoList();
    }

    /**
     * Create an instance of {@link UpdateDaysStatus }
     * 
     */
    public UpdateDaysStatus createUpdateDaysStatus() {
        return new UpdateDaysStatus();
    }

    /**
     * Create an instance of {@link RetrieveWeekendDaysResponse }
     * 
     */
    public RetrieveWeekendDaysResponse createRetrieveWeekendDaysResponse() {
        return new RetrieveWeekendDaysResponse();
    }

    /**
     * Create an instance of {@link ConfigureDaysDto }
     * 
     */
    public ConfigureDaysDto createConfigureDaysDto() {
        return new ConfigureDaysDto();
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
