package com.mckesson.eig.roi.ccd.provider.dao;


import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.model.AssigningAuthorityModel;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceConfigModel;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceModel;
import com.mckesson.eig.roi.ccd.provider.model.ExtFacilityMappingModel;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.ConversionUtilities;

public class CcdProviderDAOImpl extends ROIDAOImpl implements CcdProviderDAO {
    private static final OCLogger LOG = new OCLogger(CcdProviderDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * Method to update the Provider information
     * 
     * @param ccdProviderModel
     * @return CcdProviderModel
     */
    public CcdSourceModel updateSource(CcdSourceModel ccdProviderModel) {
	ccdProviderModel.setModifiedDate(getDate());
	CcdSourceModel updatedModel = (CcdSourceModel) merge(ccdProviderModel);
	return updatedModel;
    }

    /**
     * Method to create the Provider information
     * 
     * @param provider
     * @return CcdProviderModel
     */
    public CcdSourceModel createSource(CcdProvider p, String source,
	    String description, User user) {
	CcdSourceModel ccdProviderModel = getNewSourceModel(p, source,
		description, user);
	final String logSM = "createProvider(provider)";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + ccdProviderModel);
	}

	int id = toPint((Integer) create(ccdProviderModel));
	ccdProviderModel.setId(id);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + id);
	}
	return ccdProviderModel;
    }

    /**
     * Method to get the Provider information
     * 
     * @param
     * @return List<CcdProviderModel>
     */
    @SuppressWarnings("unchecked")
    public List<CcdSourceModel> loadSourceFromDB() {
	final String logSM = "loadSourceFromDB()";
	List<CcdSourceModel> providers = (List<CcdSourceModel>) getHibernateTemplate()
		.findByNamedQuery("getExternalSources");

	if (providers.size() == 0) {
	    return null;
	}

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:loadSourceFromDB : " + providers.size());
	}
	return providers;
    }

    /**
     * Method to get the new Ccd Provider Model information
     * 
     * @param provider
     * @return CcdSourceModel
     */
    private CcdSourceModel getNewSourceModel(CcdProvider provider,
	    String source, String description, User user) {
	CcdSourceModel model = new CcdSourceModel();
	model.setSourceName(source);
	model.setProviderName(provider.getProviderName());
	model.setDescription(description);
	model.setExtType(provider.getCcdType().toString());
	model.setCreatedDate(getDate());
	model.setCreatedBy(user.getInstanceIdValue());
	model.setModifiedDate(getDate());
	model.setModifiedBy(user.getInstanceIdValue());
	return model;
    }

    /**
     * Method to get the Provider Config information
     * 
     * @param providerId
     * @return List<CcdProviderConfigModel>
     */
    @SuppressWarnings("unchecked")
    public List<CcdSourceConfigModel> loadSourceConfigFromDB(int providerId) {
	final String logSM = "loadSourceConfigFromDB() :" + providerId;
	List<CcdSourceConfigModel> providers = (List<CcdSourceConfigModel>) getHibernateTemplate()
		.findByNamedQuery("getExternalSourceConfig",
			new Integer(providerId));

	if (providers.size() == 0) {
	    return null;
	}

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:loadSourceConfigFromDB : "
		    + providers.size());
	}
	return providers;
    }

    /**
     * Method to create the Provider Config information
     * 
     * @param extId
     *            ,key,value
     * @return CcdProviderConfigModel
     */
    public CcdSourceConfigModel createSourceConfig(int extId, String key,
	    String value, User user) {
	CcdSourceConfigModel model = new CcdSourceConfigModel();
	model.setSourceId(extId);
	model.setConfigKey(key);
	model.setConfigValue(value);
	model.setCreatedDate(getDate());
	model.setCreatedBy(user.getInstanceId());
	model.setModifiedDate(getDate());
	model.setModifiedBy(user.getInstanceId());
	final String logSM = "createSourceConfig()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + model);
	}
	int id = toPint((Integer) create(model));
	model.setId(id);
	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + id);
	}
	return model;
    }

    /**
     * Method to update the Source Config information
     * 
     * @param model
     * @return CcdProviderConfigModel
     */
    public CcdSourceConfigModel updateSourceConfig(CcdSourceConfigModel model) {
	model.setModifiedDate(getDate());
	CcdSourceConfigModel updatedModel = (CcdSourceConfigModel) merge(model);
	return updatedModel;
    }

    /**
     * Method to get ExternalSourceName For Facility
     * 
     * @param facility
     * @return ExternalSourceName
     */
    @SuppressWarnings("unchecked")
    public String getExternalSourceNameForFacility(String facility) {
	final String logSM = "getExternalSourceNameForFacility()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + facility);
	}

	List<String> sources = (List<String>) getHibernateTemplate().findByNamedQuery(
		"getExternalSourceNameByFacility", facility);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + sources);
	}
	if (sources.size() > 0) {
	    return sources.get(0);
	}

	return null;
    }

    /**
     * Method to get ExternalSourceName For Facility
     * 
     * @param facility
     * @return ExternalSourceName
     */
    @SuppressWarnings("unchecked")
    public int getExternalSourceIdForFacility(String facility) {
	final String logSM = "getExternalSourceIdForFacility()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + facility);
	}

	List<Integer> sources = (List<Integer>) getHibernateTemplate().findByNamedQuery(
		"getExternalSourceIdForFacility", facility);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + sources);
	}
	if (sources.size() > 0) {
	    return sources.get(0);
	}

	return -1;
    }

    /**
     * Method to get the External Facility Mapped From Assigning Authority
     * 
     * @param internalFacilty
     * @return externalFacility
     */
    @SuppressWarnings("unchecked")
    public String getExternalFacilityFromAuthority(String facility) {
	final String logSM = "getExternalFacilityFromAuthority()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + facility);
	}

	List<AssigningAuthorityModel> getExternalFacility = (List<AssigningAuthorityModel>) getHibernateTemplate()
		.findByNamedQuery("getExternalFacilityFromAuthority", facility);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + getExternalFacility);
	}
	if (getExternalFacility.size() > 0) {
	    return getExternalFacility.get(0).getHostFacilityCode();
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public List<MUROIOutboundStatistics> getOutboundStatistics(int requestId) {
	final String logSM = "getOutboundStatistics()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + requestId);
	}

	List<MUROIOutboundStatistics> statistics = (List<MUROIOutboundStatistics>) getHibernateTemplate()
		.findByNamedQuery("getOutboundStatisticsByRequestId", requestId);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + statistics.size());
	}
	return statistics;
    }

    public boolean setStatisticsOutbounded(
	    List<MUROIOutboundStatistics> statistics, boolean outbounded) {
	final String logSM = "setStatisticsOutbounded()";
	String ids = getIds(statistics);
    boolean result = false;
	if (!CollectionUtilities.isEmpty(statistics)) {
	    if (DO_DEBUG) {
		LOG.debug(logSM + ">>Start:" + ids);
	    }
	    String updateQuery = "Update MUROIOutboundStatistics statistic "
		    + "set statistic.outbound=\'"
		    + ConversionUtilities.toYesNoFlag(outbounded) + "\' "
		    + "where statistic.id in ( " + ids + ")";
	    try {
		int recordupdated = getHibernateTemplate().bulkUpdate(
			updateQuery);
        result=true;
		
	    } catch (Exception ex) {
		LOG.warn(logSM
			+ "<<WARN: Unable to set MUROIOutboundStatistics outbounded - ids:"
			+ ids);
        result= false;
	    }
	}
    return result;
	
    }

    private String getIds(List<MUROIOutboundStatistics> statistics) {
	String result = "";
	for (int i = 0; i < statistics.size(); i++) {
	    MUROIOutboundStatistics s = statistics.get(i);
	    result += s.getId();
	    if (i < statistics.size() - 1) {
		result += ",";
	    }
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    public boolean isSourceExists(String source) {
	final String logSM = "isSourceExists()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + source);
	}
	List<CcdSourceModel> sources = (List<CcdSourceModel>) getHibernateTemplate().findByNamedQuery(
		"getExternalSourcesByName", source);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + sources);
	}
	if ((sources != null) && (sources.size() > 0)) {
	    return true;
	}
	return false;
    }

    @SuppressWarnings("unchecked")
    public boolean isSourceMapped(int sourceId) {
	final String logSM = "isSourceMapped()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + sourceId);
	}
	List<ExtFacilityMappingModel> mappings = (List<ExtFacilityMappingModel>) getHibernateTemplate()
		.findByNamedQuery("getExternalSourceById", sourceId);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + mappings);
	}
	if ((mappings != null) && (mappings.size() > 0)) {
	    return true;
	}
	return false;
    }

    public int deleteSourceConfig(int sourceId) {
	final String logSM = "deleteSourceConfig()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + sourceId);
	}
	String updateQuery = "DELETE CcdSourceConfigModel modelConfig "
		+ "where modelConfig.sourceId = " + sourceId;
	try {
	    int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
	    return recordupdated;
	} catch (Exception ex) {
	    return 0;
	}
    }

    public int deleteSource(int sourceId) {
	final String logSM = "deleteSource()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + sourceId);
	}
	String updateQuery = "DELETE CcdSourceModel model "
		+ "where model.id = " + sourceId;
	try {
	    int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
	    return recordupdated;
	} catch (Exception ex) {
	    return 0;
	}
    }

    public int createExternalSourceDocument(ExternalSourceDocument s) {
	final String logSM = "createExternalSourceDocument";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + s);
	}

	int id = toPint((Integer) create(s));

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + id);
	}
	return id;
    }

    public boolean updateExternalSourceDocument(ExternalSourceDocument s) {
	final String logSM = "updateExternalSourceDocument";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + s);
	}

	ExternalSourceDocument updateStatistics = (ExternalSourceDocument) merge(s);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:");
	}
	return true;
    }

    @SuppressWarnings("unchecked")
    public List<ExternalSourceDocument> getExternalSourceDocumentByReqId(
	    int requestId) {
	final String logSM = "getExternalSourceDocumentByReqId()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + requestId);
	}

	List<ExternalSourceDocument> externalSourceDocumentList = (List<ExternalSourceDocument>) getHibernateTemplate()
		.findByNamedQuery("getExternalSourceDocumentByReqId", requestId);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + externalSourceDocumentList.size());
	}
	return externalSourceDocumentList;

    }

    @SuppressWarnings("unchecked")
    public List<ExternalSourceDocument> getExternalSourceDocumentByReqIdAndMrn(
	    int reqId, String mrn) {
	final String logSM = "getExternalSourceDocumentByReqIdAndMrn()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + reqId + mrn);
	}
	Object[] values = { reqId, mrn };
	List<ExternalSourceDocument> externalSourceDocumentList = (List<ExternalSourceDocument>) getHibernateTemplate()
		.findByNamedQuery("getExternalSourceDocumentByReqIdAndMrn",
			values);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + externalSourceDocumentList.size());
	}
	return externalSourceDocumentList;
    }

    @SuppressWarnings("unchecked")
    public ExternalSourceDocument getExternalSourceDocumentByReqIdFileNameStatus(
	    int reqID, String fileName, String reqStatus) {
	final String logSM = "getExternalSourceDocumentByReqIdFileNameStatus()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + reqID + fileName + reqStatus);
	}
	Object[] values = { reqID, fileName, reqStatus };

	List<ExternalSourceDocument> externalSourceDocumentList = (List<ExternalSourceDocument>) getHibernateTemplate()
		.findByNamedQuery(
			"getExternalSourceDocumentByReqIdFileNameStatus",
			values);
	ExternalSourceDocument externalSourceDocument = null;
	if (externalSourceDocumentList.size() > 0) {
	    externalSourceDocument = externalSourceDocumentList.get(0);
	}

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + externalSourceDocumentList.size());
	}
	return externalSourceDocument;
    }

    @SuppressWarnings("unchecked")
    public List<ExternalSourceDocument> getExternalSourceDocumentByReqIdStatusRequested(
	    int reqID) {
	final String logSM = "getExternalSourceDocumentByReqIdStatusRequested()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:" + reqID);
	}

	List<ExternalSourceDocument> externalSourceDocumentList = (List<ExternalSourceDocument>) getHibernateTemplate()
		.findByNamedQuery(
			"getExternalSourceDocumentByReqIdStatusRequested",
			reqID);

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + externalSourceDocumentList.size());
	}
	return externalSourceDocumentList;
    }

    @SuppressWarnings("unchecked")
    public List<ExternalSourceDocument> getExternalSourceDocumentsForRetry() {
	final String logSM = "getExternalSourceDocumentsForRetry()";
	if (DO_DEBUG) {
	    LOG.debug(logSM + ">>Start:");
	}

	List<ExternalSourceDocument> documents = (List<ExternalSourceDocument>) getHibernateTemplate()
		.findByNamedQuery(
			"getExternalSourceDocumentsForRetry");

	if (DO_DEBUG) {
	    LOG.debug(logSM + "<<End:" + documents.size());
	}
	return documents;
    }
    
    @SuppressWarnings("unchecked")
    public List<ExternalSourceDocument> getExternalSourceDocumentsByFileName(String fileName) {
    final String logSM = "getExternalSourceDocumentsForRetry()";
    if (DO_DEBUG) {
        LOG.debug(logSM + ">>Start:");
    }

    List<ExternalSourceDocument> documents = (List<ExternalSourceDocument>) getHibernateTemplate()
        .findByNamedQuery(
            "getExternalSourceDocumentsByFileName",fileName);

    if (DO_DEBUG) {
        LOG.debug(logSM + "<<End:" + documents.size());
    }
    return documents;
    }
    
    @SuppressWarnings("unchecked")
    public List<ExternalSourceDocument> getExternalSourceDocumentsByReqIdAndMrnAndEncounterStatusRequested(
        int reqId, String mrn,String encounter) {
    final String logSM = "getExternalSourceDocumentsByReqIdAndMrnAndEncounterStatusRequested()";
    if (DO_DEBUG) {
        LOG.debug(logSM + ">>Start:" + reqId + mrn + encounter);
    }
    Object[] values = { reqId, mrn, encounter, ROIConstants.REQUESTED_STATUS};
    List<ExternalSourceDocument> extList = (List<ExternalSourceDocument>) getHibernateTemplate()
        .findByNamedQuery("getExternalSourceDocumentsByReqIdAndMrnAndEncounterStatusRequested",
            values);

    if (DO_DEBUG) {
        LOG.debug(logSM + "<<End:" + extList.size());
    }
    return extList;
    }
    
    @SuppressWarnings("unchecked")
    public List<ExternalSourceDocument> getExternalSourceDocumentsByReqIdAndMrnAndEncounter(
        int reqId, String mrn,String encounter) {
    final String logSM = "getExternalSourceDocumentsByReqIdAndMrnAndEncounter()";
    if (DO_DEBUG) {
        LOG.debug(logSM + ">>Start:" + reqId + mrn + encounter);
    }
    Object[] values = { reqId, mrn, encounter};
    List<ExternalSourceDocument> extList = (List<ExternalSourceDocument>) getHibernateTemplate()
        .findByNamedQuery("getExternalSourceDocumentsByReqIdAndMrnAndEncounter",
            values);

    if (DO_DEBUG) {
        LOG.debug(logSM + "<<End:" + extList.size());
    }
    return extList;
    }

}

    

