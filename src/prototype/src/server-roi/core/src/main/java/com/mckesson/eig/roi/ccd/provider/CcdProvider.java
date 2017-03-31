package com.mckesson.eig.roi.ccd.provider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdFileType;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdType;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;

public interface CcdProvider {
    String getProviderName();

    CcdType getCcdType();

    Set<CcdFileType> getCcdFileType();

    void retrieveCcd(ExternalSourceDocument document);

    void sendStatisticsNotice(MUROIOutboundStatistics statistic);

    boolean needPeristanceExternalSourceDocument();

    void sendExternalDocumentNotice(ExternalSourceDocument document);

    int getNbrConfigurationItems();

    List<String> getAllConfigurationKeys();

    String getConfigurationLabel(String key);

    void setConfigurationValues(Map<String, String> values);

    boolean testConfiguration(Map<String, String> values);

    CcdProvider newProvider();
    String getCcdConvertStyleSheet();


}
