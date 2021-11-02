package com.mckesson.eig.roi.ccd.service;

import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.ObjectFactory;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.base.service.ROIAuditManager;
import com.mckesson.eig.roi.ccd.model.CcdConvertValue;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDtoList;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDtoList;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocumentList;

@WebService(targetNamespace = "urn:eig.mckesson.com", name = "CcdConversionServicePortType")
@XmlSeeAlso({org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.ObjectFactory.class, org.w3._2000._09.xmldsig_.ObjectFactory.class, org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.ObjectFactory.class, ObjectFactory.class})
public interface CcdConversionService {


    @WebMethod(action = "urn:eig.mckesson.com/ccdConvert")
    @RequestWrapper(localName = "ccdConvert", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.CcdConvert")
    @ResponseWrapper(localName = "ccdConvertResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.CcdConvertResponse")
    @WebResult(name = "ccdConvertResult", targetNamespace = "urn:eig.mckesson.com")
    public CcdConvertValue ccdConvert(
        @WebParam(name = "uuid", targetNamespace = "urn:eig.mckesson.com")
       String uuid
    ) throws Exception;
    
    public CcdConvertValue ccdConvert(String style, String uuid) throws Exception;
    
    public File getCachFile(String uuid);
    
    public int getPageCount(File f);
   
    @WebMethod(action = "urn:eig.mckesson.com/getAvailableProviders")
    @RequestWrapper(localName = "getAvailableProviders", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.GetAvailableProviders")
    @ResponseWrapper(localName = "getAvailableProvidersResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.GetAvailableProvidersResponse")
    @WebResult(name = "ccdSourceDtoList", targetNamespace = "urn:eig.mckesson.com")
    public CcdSourceDtoList getAvailableProviders();
    
    // Method to retrieve the external sources like Paragon , Clinicals etc
    @WebMethod(action = "urn:eig.mckesson.com/getExternalSources")
    @RequestWrapper(localName = "getExternalSources", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.GetExternalSources")
    @ResponseWrapper(localName = "getExternalSourcesResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.GetExternalSourcesResponse")
    @WebResult(name = "ccdSourceDtoList", targetNamespace = "urn:eig.mckesson.com")
    public CcdSourceDtoList getExternalSources();
    
    // Method to create a Provider source
    @WebMethod(action = "urn:eig.mckesson.com/createSource")
    @RequestWrapper(localName = "createSource", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.CreateSource")
    @ResponseWrapper(localName = "createSourceResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.CreateSourceResponse")
    @WebResult(name = "sourceId", targetNamespace = "urn:eig.mckesson.com")
    public int createSource(
        @WebParam(name = "ccdSourceDto", targetNamespace = "urn:eig.mckesson.com")
        CcdSourceDto ccdSourceDto
    );
   
    // Method to update the Provider details
    @WebMethod(action = "urn:eig.mckesson.com/updateSource")
    @RequestWrapper(localName = "updateSource", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.UpdateSource")
    @ResponseWrapper(localName = "updateSourceResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.UpdateSourceResponse")
    @WebResult(name = "ccdSourceDtoRes", targetNamespace = "urn:eig.mckesson.com")
    public boolean updateSource(
        @WebParam(name = "ccdSourceDto", targetNamespace = "urn:eig.mckesson.com")
        CcdSourceDto ccdSourceDto
    );
    
    // Method to update the provider configurations
    @WebMethod(action = "urn:eig.mckesson.com/updateSourceConfig")
    @RequestWrapper(localName = "updateSourceConfig", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.UpdateSourceConfig")
    @ResponseWrapper(localName = "updateSourceConfigResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.UpdateSourceConfigResponse")
    @WebResult(name = "ccdSourceConfigDtoRes", targetNamespace = "urn:eig.mckesson.com")
    public boolean updateSourceConfig(
        @WebParam(name = "ccdSourceDto", targetNamespace = "urn:eig.mckesson.com")
        CcdSourceDto ccdSourceDto
    );
    
    // Method to delete a source configurations
    @WebMethod(action = "urn:eig.mckesson.com/deleteSource")
    @RequestWrapper(localName = "deleteSource", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.DeleteSource")
    @ResponseWrapper(localName = "deleteSourceResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.DeleteSourceResponse")
    @WebResult(name = "ccdDeleteRes", targetNamespace = "urn:eig.mckesson.com")
    public boolean deleteSource(
        @WebParam(name = "sourceId", targetNamespace = "urn:eig.mckesson.com")
        int sourceId
    );
    
    // Method to retrieve the CCD document from the selected provider.
    @WebMethod(action = "urn:eig.mckesson.com/retrieveCCD")
    @RequestWrapper(localName = "retrieveCCD", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.RetrieveCCD")
    @ResponseWrapper(localName = "retrieveCCDResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.RetrieveCCDResponse")
    @WebResult(name = "ccdDocuments", targetNamespace = "urn:eig.mckesson.com")
    public CcdDocumentList retrieveCCD(
        @WebParam(name = "retrieveParameters", targetNamespace = "urn:eig.mckesson.com")
        RetrieveCCDDtoList retrieveParameters
    );
    // Method to test the connection parameters
    
    @WebMethod(action = "urn:eig.mckesson.com/testConfiguration")
    @RequestWrapper(localName = "testConfiguration", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.TestConfiguration")
    @ResponseWrapper(localName = "testConfigurationResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.TestConfigurationResponse")
    @WebResult(name = "testConfigurationRes", targetNamespace = "urn:eig.mckesson.com")
    public boolean testConfiguration(
        @WebParam(name = "ccdSourceDto", targetNamespace = "urn:eig.mckesson.com")
        CcdSourceDto ccdSourceDto
    );
    
    // Method to get the ExternalSource Name for the facility
    @WebMethod(action = "urn:eig.mckesson.com/getExternalSourceNameForFacility")
    @RequestWrapper(localName = "getExternalSourceNameForFacility", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsRequestWrapper.GetExternalSourceNameForFacility")
    @ResponseWrapper(localName = "getExternalSourceNameForFacilityResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper.GetExternalSourceNameForFacilityResponse")
    @WebResult(name = "externalsourcename", targetNamespace = "urn:eig.mckesson.com")
    public String getExternalSourceNameForFacility(
        @WebParam(name = "facility", targetNamespace = "urn:eig.mckesson.com")
        String facility
    );
    
    User getUser(); 
    
    void audit(AuditEvent ae);

}