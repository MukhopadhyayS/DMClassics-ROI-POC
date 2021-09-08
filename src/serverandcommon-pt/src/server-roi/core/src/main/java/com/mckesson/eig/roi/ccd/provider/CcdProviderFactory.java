package com.mckesson.eig.roi.ccd.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.UUID;

import com.itextpdf.text.pdf.PdfReader;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.ccd.model.CcdConvertValue;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdFileType;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.RetrieveParameter;
import com.mckesson.eig.roi.ccd.provider.dao.CcdProviderDAO;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocument;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceConfigModel;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceModel;
import com.mckesson.eig.roi.ccd.service.CcdConversionService;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO;
import com.mckesson.eig.roi.utils.DirectoryUtil;
import com.mckesson.eig.roi.utils.FileUtilities;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.ConversionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

public class CcdProviderFactory {
    private static final OCLogger LOG = new OCLogger(CcdProviderFactory.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private List<CcdProvider> _availableProviders = null;
    private HashMap<CcdSourceModel, CcdProvider> _sources = null;
    private static final String DEFAULT_VALUE = "";
    private static final String OUTBOUND_SUCCESSFUL_AUDIT_COMMENT = "Outbound has been performed to {0} ; {1}";
    private static final String OUTBOUND_CANCELLED_AUDIT_COMMENT = "MU Request Cancellation notification has been sent to {0} ; {1}";
    private static final String SUCCESSFUL = "Successful";
    private static final String FAILED = "Failed";

    /**
     * Method to retrieve the Ccd information
     * 
     * @param data
     * @return CcdDocument
     */
    public List<CcdDocument> retrieveCcd(Map<RetrieveParameter, String> data) {
        final String logSM = "retrieveCcd(data)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + data);
        }
        String facility = data.get(RetrieveParameter.FACILITY);
        String extFacility = getExternalFacility(facility);
        if (extFacility != null) {
            data.put(RetrieveParameter.ASSIGNING_AUTHORITY, extFacility.trim());
        }
        CcdProvider provider = getSourceByFacility(facility);
        if (provider == null) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_PROVIDERNOTEXISTS);
        }
        ExternalSourceDocument document = createCdDocument(provider, data);

        List<CcdDocument> result = new ArrayList<CcdDocument>();
        if (document != null) {
            try {
                provider.retrieveCcd(document);
                for (CcdDocument ccdDocument : document.getCcdDocuments()) {
                    ccdDocument.closeOutputStream();
                    setPageNumber(ccdDocument);
                    if (ccdDocument.getPageNumber() == 0) {
                        deleteFile(ccdDocument);
                    } else {
                        result.add(ccdDocument);
                    }
                }
            } catch (ROIException e) {
                throw e;
            } catch (Exception e) {
                throw new ROIException(e,
                        ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
            }
        }
        if (result.size() == 0) {
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_RETRIEVECCD,
                    "Retrieval Failed");
        }
        processPeristanceExternalDocument(provider, document, result);
        return result;
    }

    private void processPeristanceExternalDocument(CcdProvider provider,
            ExternalSourceDocument doc, List<CcdDocument> result) {
        final String logSM = "processPeristanceExternalDocument()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        if (provider.needPeristanceExternalSourceDocument()) {
            getDao().createExternalSourceDocument(doc);
            handleConvertPdf(provider, doc, result);
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }

    private void handleConvertPdf(CcdProvider provider,
            ExternalSourceDocument doc, List<CcdDocument> result) {
        final String logSM = "handleConvertPdf()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        Set<CcdFileType> s = provider.getCcdFileType();
        if (s.size() == 1 && s.contains(CcdFileType.XML)) {
            CcdConversionService service = getCcdConversionService();
            try {
                String style = provider.getCcdConvertStyleSheet();
                CcdConvertValue convertDoc = null;
                if (StringUtilities.isEmpty(style)) {
                    convertDoc = service.ccdConvert(doc.getAttId());
                } else {
                    convertDoc = service.ccdConvert(style, doc.getAttId());
                }
                ExternalSourceDocument copyDoc = ExternalSourceDocument
                        .copy(doc);
                copyDoc.setAttId(convertDoc.getFileName());
                CcdDocument d = new CcdDocument();
                d.setDocumentType(provider.getCcdType().toString());
                d.setFileName(convertDoc.getFileName());
                d.setType(CcdFileType.PDF.toString());
                d.setPageNumber(convertDoc.getPageNumber());
                d.setReceivedDate(new Date());
                getDao().createExternalSourceDocument(copyDoc);
                result.add(d);
            } catch (Exception e) {
                LOG.warn(logSM + "<<WARN: Unable to convert to pdf: "
                        + e.toString());
            }

        }
    }

    /**
     * Method to initiate the process of retrieval
     * 
     * @param
     * @return
     */
    private synchronized void initProviders() {
        final String logSM = "initProviders()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        _availableProviders = new ArrayList<CcdProvider>();
        ServiceLoader<CcdProvider> loader = ServiceLoader
                .load(CcdProvider.class);
        Iterator<CcdProvider> providers = loader.iterator();
        if (providers != null) {
            while (providers.hasNext()) {
                try {
                    CcdProvider d = (CcdProvider) providers.next();
                    _availableProviders.add(d);
                } catch (ServiceConfigurationError ee) {
                    LOG.error("CcdProviderFactory:init - " + ee.toString());
                }
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        }
    }

    private void loadSources() {
        _sources = new HashMap<CcdSourceModel, CcdProvider>();
        List<CcdSourceModel> models = getSourceModelsFromDB();
        if (models != null) {
            for (CcdSourceModel model : models) {
                String providerName = model.getProviderName();
                CcdProvider provider = getProvider(providerName, true);
                if (provider != null) {
                    _sources.put(model, provider);
                    Map<String, String> configs = getSourceConfig(provider,
                            model.getId());
                    provider.setConfigurationValues(configs);
                }
            }
        }
    }

    /**
     * Method to get the Provider Config information
     * 
     * @param providerName
     *            ,applyToProvider
     * @return Map<String, String>
     */
    public Map<String, String> getSourceConfig(CcdProvider provider,
            int sourceId) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        if (provider.getNbrConfigurationItems() > 0) {
            List<String> keys = provider.getAllConfigurationKeys();
            List<CcdSourceConfigModel> configModels = loadConfigureModelFromDb(sourceId);
            for (String key : keys) {
                String value = null;
                if (!CollectionUtilities.isEmpty(configModels)) {
                    value = getConfigValue(configModels, key);
                }
                if (value == null) {
                    value = DEFAULT_VALUE;
                    getDao()
                            .createSourceConfig(sourceId, key, value, getUser());
                }
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * Method to get the Config Value information
     * 
     * @param models
     *            ,key
     * @return String
     */
    private String getConfigValue(List<CcdSourceConfigModel> models, String key) {
        for (CcdSourceConfigModel model : models) {
            if (model.getConfigKey().equals(key)) {
                return model.getConfigValue();
            }
        }
        return null;
    }

    /**
     * Method to get the Configure Model information
     * 
     * @param providerName
     * @return List<CcdProviderConfigModel>
     */
    private List<CcdSourceConfigModel> loadConfigureModelFromDb(int sourceId) {
        final String logSM = "loadConfigureModelFromDb(sourceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + sourceId);
        }
        if (sourceId > 0) {
            return getDao().loadSourceConfigFromDB(sourceId);
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + sourceId);
        }
        return null;
    }

    /**
     * Method to get the Provider Models
     * 
     * @return List<CcdProviderModel>
     */
    public List<CcdSourceModel> getSourceModelsFromDB() {
        return getDao().loadSourceFromDB();
    }

    /**
     * Method to get the Dao information
     * 
     * @return CcdProviderDAOImpl
     */
    private CcdProviderDAO getDao() {
        return (CcdProviderDAO) SpringUtil
                .getObjectFromSpring("CcdProviderDAO");
    }

    private User getUser() {
        return getCcdConversionService().getUser();
    }

    /**
     * Method to Test the Configuration details
     * 
     * @param providerName
     * @param configValues
     * @return boolean
     * @throws ROIException
     */
    public boolean testConfiguration(String providerName,
            Map<String, String> configValues) throws ROIException {
        final String logSM = "testConfiguration(" + providerName
                + ", configValues)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + providerName + configValues);
        }
        CcdProvider d = getProvider(providerName, false);
        if (d == null) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_PROVIDERNOTEXISTS);
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + d);
        }
        return d.testConfiguration(configValues);

    }

    public List<CcdProvider> getAvailableProviders() {
        if (_availableProviders == null) {
            initProviders();
        }
        return _availableProviders;
    }

    public CcdProvider getProvider(String providerName) {
        return getProvider(providerName, false);
    }

    private CcdProvider getProvider(String providerName, boolean createNew) {
        List<CcdProvider> providers = getAvailableProviders();
        for (CcdProvider p : providers) {
            if (p.getProviderName().equalsIgnoreCase(providerName)) {
                if (createNew) {
                    return p.newProvider();
                } else {
                    return p;
                }
            }
        }
        return null;
    }

    private int getSourceIdByFacility(String facility) {
        return getDao().getExternalSourceIdForFacility(facility);
    }

    private String getExternalFacility(String facility) {
        String result = getDao().getExternalFacilityFromAuthority(facility);
        if (result != null) {
            return result.trim();
        }
        return null;
    }

    public Set<CcdSourceModel> getSourceModels() {
        HashMap<CcdSourceModel, CcdProvider> sources = getSources();
        return sources.keySet();
    }

    private HashMap<CcdSourceModel, CcdProvider> getSources() {
        if (_sources == null) {
            loadSources();
        }
        return _sources;
    }

    private CcdSourceModel getSourceById(int id) {
        Set<CcdSourceModel> models = getSourceModels();
        for (CcdSourceModel m : models) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    private CcdProvider getProviderBySourceId(int id) {
        HashMap<CcdSourceModel, CcdProvider> sources = getSources();
        for (CcdSourceModel m : sources.keySet()) {
            if (m.getId() == id) {
                return sources.get(m);
            }
        }
        return null;
    }

    private CcdProvider getSourceByFacility(String facilityCode) {
        int sourceId = getSourceIdByFacility(facilityCode);
        if (sourceId <= 0) {
            return null;
        }
        HashMap<CcdSourceModel, CcdProvider> sources = getSources();
        for (CcdSourceModel model : sources.keySet()) {
            if (model.getId() == sourceId) {
                return sources.get(model);
            }
        }
        return null;
    }

    private ExternalSourceDocument createCdDocument(CcdProvider d,
            Map<RetrieveParameter, String> data) {
        Set<CcdFileType> types = d.getCcdFileType();
        if (types == null) {
            return null;
        }
        ExternalSourceDocument document = new ExternalSourceDocument(data);
        for (CcdFileType t : types) {
            String targetUuid = UUID.randomUUID().toString();
            OutputStream outputStream = null;
            try {
                outputStream = createOutputStream(targetUuid);
            } catch (Exception e) {
                throw new ROIException(e,
                        ROIClientErrorCodes.CCD_CCR_CREATEOUTPUTSTREAM);
            }

            CcdDocument c = createCcdDocument(targetUuid, t.toString(),
                    outputStream, d.getCcdType().toString());
            c.setReceivedDate(new Date());
            document.addCcdDocument(c);
        }
        return document;

    }

    private CcdDocument createCcdDocument(String target, String filetype,
            OutputStream outputStream, String ccdType) {
        return new CcdDocument(target, outputStream, filetype, ccdType);
    }

    /**
     * Method to get the create the output stream
     * 
     * @param uuid
     * @return OutputStream
     */
    private OutputStream createOutputStream(String uuid) throws Exception {
        String dir = DirectoryUtil.getCacheDirectory();
        File f = new File(dir + File.separator + uuid);
        return new FileOutputStream(f);
    }

    private void setPageNumber(CcdDocument d) throws FileNotFoundException {
        String dir = DirectoryUtil.getCacheDirectory();
        File f = new File(dir + File.separator + d.getFileName());
        if (isEmptyFile(f)) {
            d.setPageNumber(0);
        } else if (CcdFileType.PDF.toString().equalsIgnoreCase(d.getType())) {
            int pageNumber = getPageCount(f);
            d.setPageNumber(pageNumber);
        } else {
            d.setPageNumber(1);
        }
    }

    private boolean isEmptyFile(File f) {
        if (f.exists() && f.isFile() && f.length() > 0) {
            return false;
        }
        return true;
    }

    public int getPageCount(File f) {
        final String logSM = "getPageCount(f)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + f);
        }
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
                    // TODO: handle exception
                }
            }
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + pageCount);
        }
        return pageCount;
    }

    private void deleteFile(CcdDocument d) {
        try {
            String dir = DirectoryUtil.getCacheDirectory();
            File f = new File(dir + File.separator + d.getFileName());
            FileUtilities.delete(f);
        } catch (Exception e) {
            LOG.warn("Cannot delete temp file: " + d.getFileName());
        }
    }

    public List<ExternalSourceDocument> getExternalSourceDocuments(int requestId) {
        return getDao().getExternalSourceDocumentByReqId(requestId);
    }

    public List<MUROIOutboundStatistics> getOutboundStatistics(int requestId) {
        return getDao().getOutboundStatistics(requestId);
    }

    /**
     * Method to send the statistics
     * 
     * @param name
     *            ,data
     * @return
     */
    public void sendCompletedStatistics(int requestId, Date d)
            throws ROIException {
        String outputType = getRequestCoreChargesDao().retrieveOutputType((long)requestId);
        boolean resultForUpdateStatistics = sendStatistics(requestId, false, d);
        sendExternalSourceDocuments(requestId, false, d, outputType,
                resultForUpdateStatistics, null);
    }

    private void addToStatisticMap(
            HashMap<CcdProvider, List<MUROIOutboundStatistics>> map,
            MUROIOutboundStatistics s) {
        if (!s.isOutbounded()) {
            String facility = s.getFacility();
            CcdProvider source = getSourceByFacility(facility);
            if (source != null) {
                List<MUROIOutboundStatistics> statistics = map.get(source);
                if (statistics == null) {
                    statistics = new ArrayList<MUROIOutboundStatistics>();
                    map.put(source, statistics);
                }
                statistics.add(s);
            }
        }
    }

    private void addToExtDocumnentMap(
            HashMap<CcdProvider, List<ExternalSourceDocument>> map,
            ExternalSourceDocument s) {
        if (!s.isOutbounded()) {
            String facility = s.getFacility();
            String extFacility = getExternalFacility(facility);
            s.setExtFacility(extFacility);
            CcdProvider source = getSourceByFacility(facility);
            if (source != null) {
                List<ExternalSourceDocument> docs = map.get(source);
                if (docs == null) {
                    docs = new ArrayList<ExternalSourceDocument>();
                    map.put(source, docs);
                }
                boolean isExist = false;
                for (ExternalSourceDocument d : docs) {
                    if (d.getReferenceId().equals(s.getReferenceId())) {
                        isExist = true;
                        String status = d.getReqStatus();
                        if ((status == null)
                                || (!status
                                        .equalsIgnoreCase(ROIConstants.REQUESTED_STATUS))) {
                            d.setReqStatus(s.getReqStatus());
                        }
                        if(!ROIConstants.NEW_STATUS.equalsIgnoreCase(s.getReqStatus()))
                        s.setOutbound(ConversionUtilities.toYesNoFlag(true));
                        getDao().updateExternalSourceDocument(s);
                    }
                }
                if (!isExist) {
                    docs.add(s);
                }
            }
        }
    }

    /**
     * Method to send the statistics
     * 
     * @param name,data
     *           
     * @return
     */
    public void sendCancelledStatistics(int requestId, Date t,
            String requestStatus) throws ROIException {
        boolean resultForUpdateStatistics = sendStatistics(requestId, true, t);
        sendExternalSourceDocuments(requestId, true, t, null,
                resultForUpdateStatistics, requestStatus);
    }

    /**
     * Method to send the statistics
     * 
     * @param name
     *            ,data
     * @return
     */
    public boolean sendStatistics(int requestId, boolean isCancelled,
            Date fulfillDate) throws ROIException {
        final String logSM = "sendStatistics(requestId)";
        boolean result = false;
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        List<MUROIOutboundStatistics> statistics = getDao()
                .getOutboundStatistics(requestId);
        if (statistics != null) {
            HashMap<CcdProvider, List<MUROIOutboundStatistics>> map = new HashMap<CcdProvider, List<MUROIOutboundStatistics>>();
            for (MUROIOutboundStatistics s : statistics) {
                if (!s.isOutbounded()) {
                    addToStatisticMap(map, s);
                }
            }
            if (map.size() > 0) {
                for (CcdProvider d : map.keySet()) {
                    if (DO_DEBUG) {
                        LOG.debug(logSM + "<<End:" + requestId);
                    }
                    List<MUROIOutboundStatistics> statistic = map.get(d);
                    for (MUROIOutboundStatistics s : statistic) {
                        d.sendStatisticsNotice(s);
                    }
                    result = getDao().setStatisticsOutbounded(statistics, true);
                }
            }
        }
        return result;
    }

    /**
     * Method to send the statistics
     * 
     * @param name
     *            ,data
     * @return
     */
    public void sendExternalSourceDocuments(int requestId, boolean isCancelled,
            Date fulfillDate, String outputType,
            boolean resultForUpdateStatistics, String requestStatus)
            throws ROIException {
        final String logSM = "sendExternalSourceDocuments(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        List<ExternalSourceDocument> documents = getExternalSourceDocuments(requestId);

        if (documents != null && documents.size() > 0) {
            updateDocuments(isCancelled, fulfillDate, outputType, documents);
            HashMap<CcdProvider, List<ExternalSourceDocument>> map = new HashMap<CcdProvider, List<ExternalSourceDocument>>();
            for (ExternalSourceDocument s : documents) {
                if (!s.isOutbounded()) {
                    addToExtDocumnentMap(map, s);
                }
            }
            if (map.size() > 0) {
                for (CcdProvider provider : map.keySet()) {
                    if (DO_DEBUG) {
                        LOG.debug(logSM + "<<End:" + requestId);
                    }
                    List<ExternalSourceDocument> docList = map.get(provider);
                    for (ExternalSourceDocument d : docList) {
                        try {
                            provider.sendExternalDocumentNotice(d);
                            if(!ROIConstants.NEW_STATUS.equalsIgnoreCase(d.getReqStatus()))
                            d
                                    .setOutbound(ConversionUtilities
                                            .toYesNoFlag(true)); 
                            provider.sendExternalDocumentNotice(d);
                                doAudit(d, provider, true, requestStatus);
                        } catch (Exception e) {
                            LOG.warn("Failed to sendExternalSourceDocuments - "
                                    + d.getId() + ":" + e.getMessage());
                            doAudit(d, provider, false, requestStatus);
                            d.setRetry(ConversionUtilities.toYesNoFlag(true));
                        }
                        getDao().updateExternalSourceDocument(d);
                    }
                }
            }
        } 
    }

    private void updateDocuments(boolean isCancelled, Date fulfillDate,
            String outputType, List<ExternalSourceDocument> docList) {
        if (!CollectionUtilities.isEmpty(docList)) {
            for (ExternalSourceDocument doc : docList) {
                doc.setFulfillDate(getDateTimeToStr(fulfillDate));
                doc.setOutputType(outputType);
                if (isCancelled) {
                    doc.setReqStatus(ROIConstants.CANCELED_STATUS);
                } else {
                    String status = doc.getReqStatus();
                    if (StringUtilities.isEmpty(status)
                            || !status.equals(ROIConstants.REQUESTED_STATUS)) {
                        List<ExternalSourceDocument> extDocList = getDao().getExternalSourceDocumentsByReqIdAndMrnAndEncounterStatusRequested(doc.getReqID(),doc.getMrn(),doc.getEncounter());
                        if(extDocList != null && extDocList.size() > 0)
                        {
                           if(status.equalsIgnoreCase(ROIConstants.NEW_STATUS))
                           doc.setReqStatus(ROIConstants.REQUESTED_STATUS);
                           getDao().updateExternalSourceDocument(doc);    
                        }
                        else
                        {
                        doc.setReqStatus(ROIConstants.CANCELED_STATUS);
                        getDao().updateExternalSourceDocument(doc);
                        }
                    }
                }
            }
        }
    }

    private String getDateTimeToStr(Date d) {
        SimpleDateFormat df = new SimpleDateFormat(
                ROIConstants.DEFAULT_ROI_DATETIME_FORMAT);
        try {
            return df.format(d);
        } catch (Exception e) {
            return df.format(new Date());
        }
    }

    /**
     * Method to update the provider information
     * 
     * @param name
     *            ,displayName
     * @return boolean
     */
    public boolean updateProviderToDB(int sourceId, String sourceName,
            String description) throws ROIException {

        if (StringUtilities.isEmpty(sourceName)) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_PROVIDERSOURCENAME);
        } else if (StringUtilities.isEmpty(description)) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_PROVIDERDESCRIPTION);
        } else if(isSourceExists(sourceName)) {
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_SOURCEEXISTS);
        }
        List<CcdSourceModel> models = getSourceModelsFromDB();
        if (models != null) {
            for (CcdSourceModel model : models) {
                if (sourceId == model.getId()) {
                    model.setSourceName(sourceName);
                    model.setDescription(description);
                    model.setExtType("CCD");
                    model.setModifiedBy(getUser().getInstanceId());
                    getDao().updateSource(model);
                    updateProvider(model);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to update the provider config information
     * 
     * @param name
     *            ,values
     * @return boolean
     */
    public boolean updateProviderConfig(int sourceId, Map<String, String> values)
            throws ROIException {
        final String logSM = "updateProvider(sourceId,values)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + sourceId + values);
        }
        List<CcdSourceConfigModel> models = loadConfigureModelFromDb(sourceId);
        CcdProvider provider = getProviderBySourceId(sourceId);
        List<String> keys = provider.getAllConfigurationKeys();
        CcdSourceConfigModel model = null;
        for (String key : values.keySet()) {
            if (keys.contains(key)) {
                String value = values.get(key);
                model = getConfigModels(models, key);
                if (model == null) {
                    model = getDao().createSourceConfig(sourceId, key, value,
                            getUser());
                } else {
                    if (!value.equals(model.getConfigValue())) {
                        model.setConfigValue(value);
                        model.setModifiedBy(getUser().getInstanceId());
                        model = getDao().updateSourceConfig(model);
                    }
                }
            }
        }
        provider.setConfigurationValues(values);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return true;
    }

    /**
     * Method to get the Config Models information
     * 
     * @param models
     *            ,key
     * @return CcdProviderConfigModel
     */
    private CcdSourceConfigModel getConfigModels(
            List<CcdSourceConfigModel> models, String key) {
        if (models != null && models.size() != 0) {
            for (CcdSourceConfigModel model : models) {
                if (model.getConfigKey().equals(key)) {
                    return model;
                }
            }
        }
        return null;
    }

    private void updateProvider(CcdSourceModel d) {
        CcdSourceModel c = getSourceById(d.getId());
        c.setSourceName(d.getSourceName());
        c.setDescription(d.getDescription());
    }

    /**
     * Method to create the model information
     * 
     * @param CcdProvider
     * @return CcdProviderModel
     */
    private CcdSourceModel createSourceFromDb(CcdProvider p, String source,
            String description) {
        return getDao().createSource(p, source, description, getUser());
    }

    public int createSource(String provider, String source, String description) {
        CcdProvider p = getProvider(provider, true);
        if (p == null) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_PROVIDERNOTEXISTS);
        }
        if (isSourceExists(source)) {
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_SOURCEEXISTS);
        }
        CcdSourceModel model = createSourceFromDb(p, source, description);
        getSources().put(model, p);
        return model.getId();
    }

    private boolean isSourceExists(String sourceName) {
        return getDao().isSourceExists(sourceName);
    }

    private boolean isSourceMapped(int sourceId) {
        return getDao().isSourceMapped(sourceId);
    }

    public boolean deleteSource(int sourceId) {
        CcdSourceModel model = getSourceById(sourceId);
        if (model == null) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_PROVIDERNOTEXISTS);
        }
        boolean b = isSourceMapped(sourceId);
        if (b) {
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_SOURCEMAPPINGS);
        }
        getDao().deleteSourceConfig(sourceId);
        getDao().deleteSource(sourceId);
        getSources().remove(model);
        return true;
    }

    /**
     * Method to get the Dao information
     * 
     * @return CcdProviderDAOImpl
     */
    private CcdConversionService getCcdConversionService() {
        return (CcdConversionService) SpringUtil
                .getObjectFromSpring("com.mckesson.eig.roi.ccd.service.CcdConversionServiceImpl");
    }

    public void retry() {
        List<ExternalSourceDocument> documents = getDao()
                .getExternalSourceDocumentsForRetry();
        if (!CollectionUtilities.isEmpty(documents)) {
            for (ExternalSourceDocument d : documents) {
                String facility = d.getFacility();
                String extFacility = getExternalFacility(facility);
                d.setExtFacility(extFacility);
                CcdProvider provider = getSourceByFacility(facility);
                if (provider != null) {
                    try {
                        provider.sendExternalDocumentNotice(d);
                        d.setOutbound(true);
                        d.setRetry(false);
                        getDao().updateExternalSourceDocument(d);
                    } catch (Exception e) {
                        LOG
                                .warn("Failed to retry sending ExternalSourceDocuments - "
                                        + d.getId() + ":" + e.getMessage());
                    }
                }
            }
        }
    }

    private void doAudit(ExternalSourceDocument document, CcdProvider provider,
            boolean isSucessful, String requestStatus) {

        Timestamp ts = getDao().getDate();
        MUROIOutboundStatistics statistics = null;
        if(ROIConstants.REQUESTED_STATUS.equalsIgnoreCase(document.getReqStatus()) || ROIConstants.CANCELED_STATUS.equalsIgnoreCase(document.getReqStatus())){
        String auditCode = getAuditCode(document, statistics, requestStatus);
        long userInstanceId = getUser().getInstanceId();
        String facility = document.getFacility();
        String encounter = document.getEncounter();
        String mrn = document.getMrn();
        String comment = getAuditComment(document, statistics, provider,
                isSucessful);
        AuditEvent ae = new AuditEvent();
        ae.setMrn(mrn);
        ae.setEncounter(encounter);
        ae.setActionCode(auditCode);
        ae.setFacility(facility);
        ae.setEventStart(ts);
        ae.setComment(comment);
        if (isSucessful) {
            ae.setEventStatus(AuditEvent.SUCCESS);
        } else {
            ae.setEventStatus(AuditEvent.FAIL);
        }
        ae.setUserId(userInstanceId);
        getCcdConversionService().audit(ae);
        }
    }

    private String getAuditComment(ExternalSourceDocument document,
            MUROIOutboundStatistics statistics, CcdProvider provider,
            boolean isSucessful) {
        String comment = "";
        String action = "Status = ";
        String status = null;
        if (document != null) {
            status = document.getReqStatus();
        } else {
            status = statistics.getReqStatus();
        }
        if ((!StringUtilities.isEmpty(status))
                && (status.equalsIgnoreCase(ROIConstants.CANCELED_STATUS))) {
            comment = OUTBOUND_CANCELLED_AUDIT_COMMENT;
        } else {
            comment = OUTBOUND_SUCCESSFUL_AUDIT_COMMENT;
        }
        if (isSucessful) {
            action += SUCCESSFUL;
        } else {
            action += FAILED;
        }
        if (provider != null) {
            Object[] args = {provider.getProviderName(), action};
            MessageFormat mf = new MessageFormat(comment);
            String s = mf.format(args);
            return s;
        } else {
            Object[] args = {statistics.getExternalSource(), action};
            MessageFormat mf = new MessageFormat(comment);
            String s = mf.format(args);
            return s;

        }
    }

    private String getAuditCode(ExternalSourceDocument document,
            MUROIOutboundStatistics statistics, String requestStatus) {
        String status = null;
        String output = null;
        if (document != null) {
            status = document.getReqStatus();
            output = document.getOutputType();
        } else {
            status = statistics.getReqStatus();
            output = statistics.getDocumentType();
        }
        if ((!StringUtilities.isEmpty(status) && (!StringUtilities
                .isEmpty(requestStatus)))
                && (status.equalsIgnoreCase(ROIConstants.CANCELED_STATUS))) {
            if (requestStatus.equalsIgnoreCase(ROIConstants.CANCELED_STATUS)) {
                return ROIConstants.REQUEST_AUDIT_ACTION_CODE_CANCEL;
            }
            else if (requestStatus.equalsIgnoreCase(ROIConstants.DENIED_STATUS)) {
                return ROIConstants.REQUEST_AUDIT_ACTION_CODE_DENY;
            } else {
                return ROIConstants.REQUEST_AUDIT_ACTION_CODE_DELETE;
            }
        } else {
            if ((!StringUtilities.isEmpty(output))
                    && (ROIConstants.SAVE_AS_FILE.equalsIgnoreCase(output) || ROIConstants.REPORT_DIGITAL_DOCUMENT_TYPE
                            .equalsIgnoreCase(output))) {
                return ROIConstants.AUDIT_ACTION_CODE_DIGITAL;
            } else if ((!StringUtilities.isEmpty(output))
                    && (ROIConstants.PRINT_OUTPUT.equalsIgnoreCase(output))) {
                return ROIConstants.AUDIT_ACTION_CODE_PRINTED;
            } else {
                return ROIConstants.AUDIT_ACTION_CODE_FAXED;
            }
        }
    }
    
    /**
     * Method to send cancellation statistics for delete attachment 
     * @param ext
     */
     public void sendStatisticsForDeleteAttachment(ExternalSourceDocument ext)
     {
        HashMap<CcdProvider, List<ExternalSourceDocument>> map = new HashMap<CcdProvider, List<ExternalSourceDocument>>();
        if(ext != null)
        {
           ext.setReqStatus(ROIConstants.CANCELED_STATUS);
           getDao().updateExternalSourceDocument(ext);
           List<ExternalSourceDocument> externalSourceDocumentListValue = getDao().getExternalSourceDocumentByReqId(ext.getReqID());
           for(ExternalSourceDocument extValue : externalSourceDocumentListValue)
           {
               if(ext.getMrn().equalsIgnoreCase(extValue.getMrn()) && ext.getEncounter().equalsIgnoreCase(extValue.getEncounter()) && (ROIConstants.REQUESTED_STATUS.equalsIgnoreCase(extValue.getReqStatus()) || ROIConstants.NEW_STATUS.equalsIgnoreCase(extValue.getReqStatus())))
               {
                  extValue.setReqStatus(ROIConstants.CANCELED_STATUS);
                  extValue.setOutbound(true);
                  getDao().updateExternalSourceDocument(extValue);
               }
           }
                addToExtDocumnentMap(map,ext);
                if (map.size() > 0) {
                    for (CcdProvider provider : map.keySet()) {
                         ext.setOutbound(ConversionUtilities.toYesNoFlag(true)); 
                         provider.sendExternalDocumentNotice(ext);
                         doAudit(ext, provider, true, ROIConstants.CANCELED_STATUS);
                    }
                }
        }
     }
     
     /**
      * Method to get the Request Core Charges Dao information
      * 
      * @return RequestCoreChargesDaoImpl
      */
     private RequestCoreChargesDAO getRequestCoreChargesDao() {
         return (RequestCoreChargesDAO) SpringUtil
                 .getObjectFromSpring("RequestCoreChargesDAO");
     }
}