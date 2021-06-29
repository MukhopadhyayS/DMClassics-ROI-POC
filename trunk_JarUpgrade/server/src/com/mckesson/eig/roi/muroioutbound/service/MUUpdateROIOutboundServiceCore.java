package com.mckesson.eig.roi.muroioutbound.service;

import java.util.List;

import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;

public interface MUUpdateROIOutboundServiceCore {
    
    /**
     * Method to Update ROIOUTBOUND STATISTICS table on release of documents
     */
    public List<MUROIOutboundStatistics> updateROIOutboundStatistics(ReleaseCore release);

}
