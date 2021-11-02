
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
     * Create an instance of {@link UpdateSourceConfig }
     * 
     */
    public UpdateSourceConfig createUpdateSourceConfig() {
        return new UpdateSourceConfig();
    }

    /**
     * Create an instance of {@link CcdSourceDto }
     * 
     */
    public CcdSourceDto createCcdSourceDto() {
        return new CcdSourceDto();
    }

    /**
     * Create an instance of {@link GetAvailableProviders }
     * 
     */
    public GetAvailableProviders createGetAvailableProviders() {
        return new GetAvailableProviders();
    }

    /**
     * Create an instance of {@link TestConfiguration }
     * 
     */
    public TestConfiguration createTestConfiguration() {
        return new TestConfiguration();
    }

    /**
     * Create an instance of {@link DeleteSourceResponse }
     * 
     */
    public DeleteSourceResponse createDeleteSourceResponse() {
        return new DeleteSourceResponse();
    }

    /**
     * Create an instance of {@link RetrieveCCD }
     * 
     */
    public RetrieveCCD createRetrieveCCD() {
        return new RetrieveCCD();
    }

    /**
     * Create an instance of {@link RetrieveCCDDtoList }
     * 
     */
    public RetrieveCCDDtoList createRetrieveCCDDtoList() {
        return new RetrieveCCDDtoList();
    }

    /**
     * Create an instance of {@link GetExternalSourceNameForFacility }
     * 
     */
    public GetExternalSourceNameForFacility createGetExternalSourceNameForFacility() {
        return new GetExternalSourceNameForFacility();
    }

    /**
     * Create an instance of {@link DeleteSource }
     * 
     */
    public DeleteSource createDeleteSource() {
        return new DeleteSource();
    }

    /**
     * Create an instance of {@link CcdConvert }
     * 
     */
    public CcdConvert createCcdConvert() {
        return new CcdConvert();
    }

    /**
     * Create an instance of {@link CcdConvertResponse }
     * 
     */
    public CcdConvertResponse createCcdConvertResponse() {
        return new CcdConvertResponse();
    }

    /**
     * Create an instance of {@link CcdConvertResult }
     * 
     */
    public CcdConvertResult createCcdConvertResult() {
        return new CcdConvertResult();
    }

    /**
     * Create an instance of {@link RetrieveCCDResponse }
     * 
     */
    public RetrieveCCDResponse createRetrieveCCDResponse() {
        return new RetrieveCCDResponse();
    }

    /**
     * Create an instance of {@link CcdDocuments }
     * 
     */
    public CcdDocuments createCcdDocuments() {
        return new CcdDocuments();
    }

    /**
     * Create an instance of {@link TestConfigurationResponse }
     * 
     */
    public TestConfigurationResponse createTestConfigurationResponse() {
        return new TestConfigurationResponse();
    }

    /**
     * Create an instance of {@link GetExternalSources }
     * 
     */
    public GetExternalSources createGetExternalSources() {
        return new GetExternalSources();
    }

    /**
     * Create an instance of {@link CreateSource }
     * 
     */
    public CreateSource createCreateSource() {
        return new CreateSource();
    }

    /**
     * Create an instance of {@link GetAvailableProvidersResponse }
     * 
     */
    public GetAvailableProvidersResponse createGetAvailableProvidersResponse() {
        return new GetAvailableProvidersResponse();
    }

    /**
     * Create an instance of {@link CcdSourceDtoList }
     * 
     */
    public CcdSourceDtoList createCcdSourceDtoList() {
        return new CcdSourceDtoList();
    }

    /**
     * Create an instance of {@link UpdateSource }
     * 
     */
    public UpdateSource createUpdateSource() {
        return new UpdateSource();
    }

    /**
     * Create an instance of {@link GetExternalSourceNameForFacilityResponse }
     * 
     */
    public GetExternalSourceNameForFacilityResponse createGetExternalSourceNameForFacilityResponse() {
        return new GetExternalSourceNameForFacilityResponse();
    }

    /**
     * Create an instance of {@link UpdateSourceResponse }
     * 
     */
    public UpdateSourceResponse createUpdateSourceResponse() {
        return new UpdateSourceResponse();
    }

    /**
     * Create an instance of {@link CreateSourceResponse }
     * 
     */
    public CreateSourceResponse createCreateSourceResponse() {
        return new CreateSourceResponse();
    }

    /**
     * Create an instance of {@link GetExternalSourcesResponse }
     * 
     */
    public GetExternalSourcesResponse createGetExternalSourcesResponse() {
        return new GetExternalSourcesResponse();
    }

    /**
     * Create an instance of {@link UpdateSourceConfigResponse }
     * 
     */
    public UpdateSourceConfigResponse createUpdateSourceConfigResponse() {
        return new UpdateSourceConfigResponse();
    }

    /**
     * Create an instance of {@link CcdSourceConfigDto }
     * 
     */
    public CcdSourceConfigDto createCcdSourceConfigDto() {
        return new CcdSourceConfigDto();
    }

    /**
     * Create an instance of {@link RetrieveCCDDto }
     * 
     */
    public RetrieveCCDDto createRetrieveCCDDto() {
        return new RetrieveCCDDto();
    }

    /**
     * Create an instance of {@link CcdDocument }
     * 
     */
    public CcdDocument createCcdDocument() {
        return new CcdDocument();
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
