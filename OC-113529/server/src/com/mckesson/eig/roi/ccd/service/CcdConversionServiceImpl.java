package com.mckesson.eig.roi.ccd.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.BeanFactory;

import com.lowagie.text.pdf.PdfReader;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.ccd.model.CcdConvertValue;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.RetrieveParameter;
import com.mckesson.eig.roi.ccd.provider.CcdProviderFactory;
import com.mckesson.eig.roi.ccd.provider.dao.CcdProviderDAOImpl;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceConfigDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDtoList;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDto;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDtoList;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocumentList;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceModel;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.roi.utils.DirectoryUtil;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.util.SpringUtilities;

public class CcdConversionServiceImpl extends BaseROIService
        implements
            CcdConversionService {
    private CCDCCRConversion _ccdccrConversion;
    private static final OCLogger LOG = new OCLogger(CcdConversionServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();

    private CcdProviderFactory ccdproviderFactory = (CcdProviderFactory) BEAN_FACTORY
            .getBean("ccdProviderFactory");

    /**
     * Method to get the Dao information
     * 
     * @return CcdProviderDAOImpl
     */
    private CcdProviderDAOImpl getDao() {
        return (CcdProviderDAOImpl) SpringUtil
                .getObjectFromSpring("CcdProviderDAO");
    }

    public CcdConvertValue ccdConvert(String uuid) throws Exception {
        final String logSM = "ccdConvert(uuid)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + uuid);
        }
        File sourceFile = getCachFile(uuid);
        if (!sourceFile.exists()) {
            throw new FileNotFoundException(sourceFile.getAbsolutePath());
        }

        String targetUuid = UUID.randomUUID().toString();
        File destFile = getCachFile(targetUuid);

        String type = getConverter().convertToPDF(sourceFile, destFile);
        int pageCount = getPageCount(destFile);
        CcdConvertValue value = new CcdConvertValue(targetUuid, pageCount, type);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + value);
        }
        return value;
    }

    public CcdConvertValue ccdConvert(String style, String uuid) throws Exception {
        final String logSM = "ccdConvert(" + style + "," + uuid + ")";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + uuid);
        }
        File sourceFile = getCachFile(uuid);
        if (!sourceFile.exists()) {
            throw new FileNotFoundException(sourceFile.getAbsolutePath());
        }

        String targetUuid = UUID.randomUUID().toString();
        File destFile = getCachFile(targetUuid);

        String type = getConverter().convertToPDF(style, sourceFile, destFile);
        int pageCount = getPageCount(destFile);
        CcdConvertValue value = new CcdConvertValue(targetUuid, pageCount, type);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + value);
        }
        return value;
    }

    private CCDCCRConversion getConverter() {
        if (_ccdccrConversion == null) {
            _ccdccrConversion = new CCDCCRConversion();
        }
        return _ccdccrConversion;
    }

    public File getCachFile(String uuid) {
        final String logSM = "getCachFile(uuid)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + uuid);
        }
        String dir = DirectoryUtil.getCacheDirectory();
        File f = null;
        try {
		//DE7315 External Control of File Name or Path
            f = AccessFileLoader.getFile(dir + File.separator + uuid);
        } catch (IOException e) {
                 LOG.error("Exception in getCachfile "+e.getLocalizedMessage());
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + f);
        }
        return f;
    }

    public int getPageCount(File f) {
        final String logSM = "getPageCount(f)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + f);
        }
        int pageCount = 0;
        InputStream is = null;
        try {
		//DE7315 External Control of File Name or Path
            is = AccessFileLoader.getFileInputStream(f);
            PdfReader pdfReader = new PdfReader(is);
            pageCount += pdfReader.getNumberOfPages();
            pdfReader.close();
        } catch (Exception e) {
            LOG.error(logSM + " Error :" + e);
            throw new ROIException(e,
                    ROIClientErrorCodes.CCD_CCR_INVALID_PAGE_COUNT);
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + pageCount);
        }
        return pageCount;
    }

    /**
     * Method to Retrieve External Sources
     * 
     * @param
     * @return CcdProviderDtoList Object
     */
    public CcdSourceDtoList getAvailableProviders() {
        final String logSM = "getAvailbleProviders()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try {
            List<CcdSourceDto> externalNamesList = new ArrayList<CcdSourceDto>();
            List<CcdProvider> providers = ccdproviderFactory
                    .getAvailableProviders();
            for (CcdProvider p : providers) {
                String providerName = p.getProviderName();
                String description = "";
                String sourceName = "";
                CcdSourceDto ccdProviderDto = new CcdSourceDto();
                ccdProviderDto.setProviderName(providerName);
                ccdProviderDto.setDescription(description);
                ccdProviderDto.setSourceName(sourceName);
                List<String> keys = p.getAllConfigurationKeys();
                List<CcdSourceConfigDto> ccdProviderConfigDtos = new ArrayList<CcdSourceConfigDto>();
                for (String key : keys) {
                    CcdSourceConfigDto ccdProviderConfigDto = new CcdSourceConfigDto();
                    ccdProviderConfigDto.setConfigKey(key);
                    ccdProviderConfigDto.setConfigValue("");
                    ccdProviderConfigDto.setConfigLabel(p
                            .getConfigurationLabel(key));
                    ccdProviderConfigDtos.add(ccdProviderConfigDto);
                }
                ccdProviderDto.setCcdSourceConfigDto(ccdProviderConfigDtos);
                externalNamesList.add(ccdProviderDto);
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + externalNamesList);
            }
            return new CcdSourceDtoList(externalNamesList);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_EXTERNALSOURCES_RETRIEVAL);
        }
    }

    /**
     * Method to Retrieve External Sources
     * 
     * @param
     * @return CcdProviderDtoList Object
     */
    public CcdSourceDtoList getExternalSources() {
        final String logSM = "getExternalSources()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try {
            List<CcdSourceDto> externalNamesList = new ArrayList<CcdSourceDto>();
            Set<CcdSourceModel> models = ccdproviderFactory.getSourceModels();
            for (CcdSourceModel model : models) {
                String providerName = model.getProviderName();
                String providerDescription = model.getDescription();
                String sourceName = model.getSourceName();
                CcdSourceDto ccdProviderDto = new CcdSourceDto();
                ccdProviderDto.setSourceId(model.getId());
                ccdProviderDto.setProviderName(providerName);
                ccdProviderDto.setDescription(providerDescription);
                ccdProviderDto.setSourceName(sourceName);
                CcdProvider p = ccdproviderFactory.getProvider(providerName);
                Map<String, String> configs = ccdproviderFactory
                        .getSourceConfig(p, model.getId());
                List<CcdSourceConfigDto> ccdProviderConfigDtos = new ArrayList<CcdSourceConfigDto>();
                for (String key : configs.keySet()) {
                    String value = configs.get(key);
                    CcdSourceConfigDto ccdProviderConfigDto = new CcdSourceConfigDto();
                    ccdProviderConfigDto.setConfigKey(key);
                    ccdProviderConfigDto.setConfigValue(value);
                    ccdProviderConfigDto.setConfigLabel(p
                            .getConfigurationLabel(key));
                    ccdProviderConfigDtos.add(ccdProviderConfigDto);
                }
                ccdProviderDto.setCcdSourceConfigDto(ccdProviderConfigDtos);
                externalNamesList.add(ccdProviderDto);
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + externalNamesList);
            }
            return new CcdSourceDtoList(externalNamesList);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_EXTERNALSOURCES_RETRIEVAL);
        }
    }

    /**
     * Method to Update Provider Details
     * 
     * @param CcdSourceDto
     *            object
     * @return boolean
     */
    public boolean updateSource(CcdSourceDto dto) {
        final String logSM = "updateProvider(ccdProviderDto)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + dto);
        }
        try {
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return ccdproviderFactory.updateProviderToDB(
                    dto.getSourceId(),
                    dto.getSourceName(),
                    dto.getDescription());
        } catch (ROIException e) {
           throw e;
        } catch (Throwable e) {
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_UPDATE_PROVIDER);
        }
    }
    /**
     * Method to Update Provider Configuration details
     * 
     * @param Provider
     *            Name
     * @param CcdSourceConfigDto
     *            Object
     * @return boolean
     */
    public boolean updateSourceConfig(CcdSourceDto dto) {
        final String logSM = "updateProviderConfig(providerName,ccdProviderConfigDto)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + dto);
        }
        try {
            List<CcdSourceConfigDto> ccdProviderConfigDtos = dto
                    .getCcdSourceConfigDto();
            Map<String, String> configData = getConfigData(ccdProviderConfigDtos);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return ccdproviderFactory.updateProviderConfig(
                    dto.getSourceId(), configData);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_UPDATE_PROVIDERCONFIG);
        }
    }
    /**
     * Method to Retrieve CCD
     * 
     * @param RetrieveCCDDtoList
     * @return CcdDocument
     */

    public CcdDocumentList retrieveCCD(RetrieveCCDDtoList retrieveCCDDtoList) {
        final String logSM = "retrieveCCD(retrieveCCDDtoList)";
        try {
            List<RetrieveCCDDto> retrieveCcdList = retrieveCCDDtoList
                    .getRetrieveParameters();
            Map<RetrieveParameter, String> retrieveData = new HashMap<RetrieveParameter, String>();
            for (RetrieveCCDDto dto : retrieveCcdList) {
                String key = dto.getRetrieveCCDKey();
                String value = dto.getRetrieveCCDValue();
                RetrieveParameter p = getRetrieveParameter(key);
                if (p != null) {
                    retrieveData.put(p, value);
                }
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return new CcdDocumentList(
                    ccdproviderFactory.retrieveCcd(retrieveData));
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + " Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
        }
    }

    private RetrieveParameter getRetrieveParameter(String s) {
        for (RetrieveParameter p : RetrieveParameter.values()) {
            if (p.toString().equalsIgnoreCase(s)) {
                return p;
            }
        }
        return null;
    }

    /**
     * 
     * Method to Test the Configuration Details
     * 
     * @param CcdSourceDto
     * @return boolean
     */
    public boolean testConfiguration(CcdSourceDto dto) {
        final String logSM = "testConfiguration(ccdProviderDto)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + dto);
        }
        try {
            String providerName = dto.getProviderName();
            List<CcdSourceConfigDto> ccdProviderConfigDtos = dto
                    .getCcdSourceConfigDto();
            Map<String, String> configData = getConfigData(ccdProviderConfigDtos);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return ccdproviderFactory.testConfiguration(providerName,
                    configData);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_TESTCONFIGURATION);
        }

    }

    private Map<String, String> getConfigData(
            List<CcdSourceConfigDto> dtos) {
        Map<String, String> configData = new HashMap<String, String>();
        if (dtos != null) {
            for (CcdSourceConfigDto dto : dtos) {
                String configKey = dto.getConfigKey();
                String configValue = dto.getConfigValue();
                configData.put(configKey, configValue);
            }
        }
        return configData;
    }
    /**
     * Method to get the ExternalSourceName for the facility
     * 
     * @param String
     *            facility
     * @return String externalSourceName
     */
    public String getExternalSourceNameForFacility(String facility) {
        final String logSM = "getExternalSourceNameForFacility(facility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + facility);
        }
        try {

            String externalSourceName = getDao()
                    .getExternalSourceNameForFacility(facility);

            if (externalSourceName != null) {
                if (DO_DEBUG) {
                    LOG.debug(logSM + "<<End:" + externalSourceName);
                }
                return externalSourceName;
            } else {
                throw new ROIException(
                        ROIClientErrorCodes.CCD_CCR_GETEXTERNALSOURCENAMEFORFACILITY);
            }

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_GETEXTERNALSOURCENAMEFORFACILITY);
        }

    }

    public int createSource(CcdSourceDto dto) {
        String provider = dto.getProviderName();
        String source = dto.getSourceName();
        String description = dto.getDescription();
        int sourceId = ccdproviderFactory.createSource(provider, source,
                description);
        if (sourceId >= 0) {
            try {
                List<CcdSourceConfigDto> ccdProviderConfigDtos = dto
                        .getCcdSourceConfigDto();
                Map<String, String> configData = getConfigData(ccdProviderConfigDtos);
                ccdproviderFactory.updateProviderConfig(sourceId, configData);
            } catch (ROIException e) {
                throw e;
            } catch (Throwable e) {
                throw new ROIException(
                        ROIClientErrorCodes.CCD_CCR_UPDATE_PROVIDERCONFIG);
            }
        }
        return sourceId;
    }
    
    public boolean deleteSource(int sourceId) {
        return ccdproviderFactory.deleteSource(sourceId);
    }
    
    @Override
    public User getUser() {
	return super.getUser();
    }

    @Override
    public void audit(AuditEvent ae) {
	getROIAuditManager().createAuditEntry(ae);
    }
}
