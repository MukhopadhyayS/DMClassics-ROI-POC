package com.mckesson.eig.roi.ccd.service;

import java.io.File;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.base.service.ROIAuditManager;
import com.mckesson.eig.roi.ccd.model.CcdConvertValue;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDtoList;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDtoList;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocumentList;

public interface CcdConversionService {

    public CcdConvertValue ccdConvert(String uuid) throws Exception;
    public CcdConvertValue ccdConvert(String style, String uuid) throws Exception;
    public File getCachFile(String uuid);
    public int getPageCount(File f);
    public CcdSourceDtoList getAvailableProviders();
    // Method to retrieve the external sources like Paragon , Clinicals etc
    public CcdSourceDtoList getExternalSources();
    // Method to create a Provider source
    public int createSource(CcdSourceDto dto);
    // Method to update the Provider details
    public boolean updateSource(CcdSourceDto dto);
    // Method to update the provider configurations
    public boolean updateSourceConfig(CcdSourceDto dto);
    // Method to delete a source configurations
    public boolean deleteSource(int sourceId);
    // Method to retrieve the CCD document from the selected provider.
    public CcdDocumentList retrieveCCD(RetrieveCCDDtoList retrieveCCDDtoList);
    // Method to test the connection parameters
    public boolean testConfiguration(CcdSourceDto dto);
    // Method to get the ExternalSource Name for the facility
    public String getExternalSourceNameForFacility(String facility);
    
    User getUser(); 
    
    void audit(AuditEvent ae);

}













/*package com.mckesson.eig.roi.ccd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import com.lowagie.text.pdf.PdfReader;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.ccd.model.CcdConvertValue;
import com.mckesson.eig.roi.utils.DirectoryUtil;

public class CcdConversionService {
   
    private CCDCCRConversion _ccdccrConversion;

    public CcdConvertValue ccdConvert(String uuid)
	    throws Exception {
        
    	File sourceFile = getCachFile(uuid);
    	if (!sourceFile.exists()) {
    	    throw new FileNotFoundException(sourceFile.getAbsolutePath());
    	}
    
    	String targetUuid = UUID.randomUUID().toString();
    	File destFile = getCachFile(targetUuid);
    
    	String type = getConverter().convertToPDF(sourceFile, destFile);
    	int pageCount = getPageCount(destFile);
    	CcdConvertValue value = new CcdConvertValue(targetUuid, pageCount, type);
    	return value;
    }
    
    private CCDCCRConversion getConverter() {
            
    	if (_ccdccrConversion == null) {
    	    _ccdccrConversion = new CCDCCRConversion();
    	}
    	return _ccdccrConversion;
    }

    public File getCachFile(String uuid) {
        
    	String dir = DirectoryUtil.getCacheDirectory();
    	File f = new File(dir + File.separator + uuid);
    	return f;
    }

    public static int getPageCount(File f) {
            
    	int pageCount = 0;
    	InputStream is = null;
    	try {
    	    is = new FileInputStream(f);
    	    PdfReader pdfReader = new PdfReader(is);
    	    pageCount += pdfReader.getNumberOfPages();
    	    pdfReader.close();
    	} catch (Exception e) {
    	    throw new ROIException(e,
    		    ROIClientErrorCodes.CCD_CCR_INVALID_PAGE_COUNT);
    	} finally {
    	    if (is != null) {
    		try {
    		    is.close();
    		    is = null;
    		} catch (Exception e) {
    		    throw new ROIException();
    		}
    	    }
    	}
    	return pageCount;
    }
}
*/