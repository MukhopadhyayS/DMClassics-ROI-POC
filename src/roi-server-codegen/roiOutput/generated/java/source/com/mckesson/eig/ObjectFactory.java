
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
     * Create an instance of {@link GetServicePropertiesResponse }
     * 
     */
    public GetServicePropertiesResponse createGetServicePropertiesResponse() {
        return new GetServicePropertiesResponse();
    }

    /**
     * Create an instance of {@link OutputFeature }
     * 
     */
    public OutputFeature createOutputFeature() {
        return new OutputFeature();
    }

    /**
     * Create an instance of {@link GetServiceProperties }
     * 
     */
    public GetServiceProperties createGetServiceProperties() {
        return new GetServiceProperties();
    }

    /**
     * Create an instance of {@link QueryOutputJob }
     * 
     */
    public QueryOutputJob createQueryOutputJob() {
        return new QueryOutputJob();
    }

    /**
     * Create an instance of {@link SubmitOutputRequest }
     * 
     */
    public SubmitOutputRequest createSubmitOutputRequest() {
        return new SubmitOutputRequest();
    }

    /**
     * Create an instance of {@link OutputRequest }
     * 
     */
    public OutputRequest createOutputRequest() {
        return new OutputRequest();
    }

    /**
     * Create an instance of {@link GetDestinationsResponse }
     * 
     */
    public GetDestinationsResponse createGetDestinationsResponse() {
        return new GetDestinationsResponse();
    }

    /**
     * Create an instance of {@link DestInfoList }
     * 
     */
    public DestInfoList createDestInfoList() {
        return new DestInfoList();
    }

    /**
     * Create an instance of {@link SubmitOutputRequestResponse }
     * 
     */
    public SubmitOutputRequestResponse createSubmitOutputRequestResponse() {
        return new SubmitOutputRequestResponse();
    }

    /**
     * Create an instance of {@link QueryOutputJobResponse }
     * 
     */
    public QueryOutputJobResponse createQueryOutputJobResponse() {
        return new QueryOutputJobResponse();
    }

    /**
     * Create an instance of {@link GetDestinations }
     * 
     */
    public GetDestinations createGetDestinations() {
        return new GetDestinations();
    }

    /**
     * Create an instance of {@link PropertyDef }
     * 
     */
    public PropertyDef createPropertyDef() {
        return new PropertyDef();
    }

    /**
     * Create an instance of {@link SubmitInfo }
     * 
     */
    public SubmitInfo createSubmitInfo() {
        return new SubmitInfo();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link RequestPart }
     * 
     */
    public RequestPart createRequestPart() {
        return new RequestPart();
    }

    /**
     * Create an instance of {@link DestInfo }
     * 
     */
    public DestInfo createDestInfo() {
        return new DestInfo();
    }

    /**
     * Create an instance of {@link MapModel }
     * 
     */
    public MapModel createMapModel() {
        return new MapModel();
    }

    /**
     * Create an instance of {@link OutputTransform }
     * 
     */
    public OutputTransform createOutputTransform() {
        return new OutputTransform();
    }

    /**
     * Create an instance of {@link PropertyMap }
     * 
     */
    public PropertyMap createPropertyMap() {
        return new PropertyMap();
    }

    /**
     * Create an instance of {@link Map }
     * 
     */
    public Map createMap() {
        return new Map();
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
