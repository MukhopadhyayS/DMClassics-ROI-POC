package com.mckesson.eig.roi.muroioutbound.service;

import com.mckesson.eig.roi.request.model.RequestCore;

public interface MUCreateROIOutboundServiceCore {
    
    /**
     * Method used to insert into ROI OUTBOUND STATISTICS TABLE
     * 
     * @param requestCore
     */
    void createROIOutboundStatistics(RequestCore requestCore);
   
}
