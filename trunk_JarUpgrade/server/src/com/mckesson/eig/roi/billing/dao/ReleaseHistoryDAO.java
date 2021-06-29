package com.mckesson.eig.roi.billing.dao;

import java.util.List;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryItem;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryPatient;

public interface ReleaseHistoryDAO extends ROIDAO {
    
    String REQUEST_ID = "requestId";
    
    List<ReleaseHistoryItem> retrieveReleaseHistory(long requestId);
    
    List<ReleaseHistoryPatient> retrievePatients(long requestId);
    
}
