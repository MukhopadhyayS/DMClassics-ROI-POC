package com.mckesson.eig.roi.ccd.provider.dao;

import java.util.List;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceConfigModel;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceModel;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;

public interface CcdProviderDAO extends ROIDAO {
    CcdSourceModel updateSource(CcdSourceModel ccdProviderModel);
    CcdSourceModel createSource(CcdProvider p, String source,
            String description,User user);
    List<CcdSourceModel> loadSourceFromDB(); 
    List<CcdSourceConfigModel> loadSourceConfigFromDB(int providerId);
    CcdSourceConfigModel createSourceConfig(int extId, String key,
            String value, User user);
    CcdSourceConfigModel updateSourceConfig(CcdSourceConfigModel model);
    String getExternalSourceNameForFacility(String facility);
    int getExternalSourceIdForFacility(String facility);
    String getExternalFacilityFromAuthority(String internalFacility);
    List<MUROIOutboundStatistics> getOutboundStatistics(int requestId);
    boolean setStatisticsOutbounded(List<MUROIOutboundStatistics> statistics,  boolean outbounded);
    boolean isSourceExists(String sourceName);
    boolean isSourceMapped(int sourceId);
    int deleteSourceConfig(int sourceId);
    int deleteSource(int sourceId);
    int createExternalSourceDocument(ExternalSourceDocument s);
    boolean updateExternalSourceDocument(ExternalSourceDocument s);
    List<ExternalSourceDocument> getExternalSourceDocumentByReqId(int reqId);
    List<ExternalSourceDocument> getExternalSourceDocumentByReqIdAndMrn(int reqId,String mrn);
    ExternalSourceDocument getExternalSourceDocumentByReqIdFileNameStatus(int reqID, String fileName,String reqStatus);
    List<ExternalSourceDocument> getExternalSourceDocumentByReqIdStatusRequested(int reqID);
    List<ExternalSourceDocument> getExternalSourceDocumentsForRetry();
    List<ExternalSourceDocument> getExternalSourceDocumentsByFileName(String fileName);
    List<ExternalSourceDocument> getExternalSourceDocumentsByReqIdAndMrnAndEncounterStatusRequested(int reqId,String mrn,String encounter);
    List<ExternalSourceDocument> getExternalSourceDocumentsByReqIdAndMrnAndEncounter(int reqId,String mrn,String encounter);
}
