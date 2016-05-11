package com.mckesson.eig.roi.muroioutbound.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.BeanFactory;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.ccd.provider.CcdProviderFactory;
import com.mckesson.eig.roi.ccd.provider.dao.CcdProviderDAO;
import com.mckesson.eig.roi.muroioutbound.dao.MUROIOutboundDAOImpl;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAO;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.util.SpringUtilities;

public class MUCreateROIOutboundServiceCoreImpl extends BaseROIService implements MUCreateROIOutboundServiceCore {
    
    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();
    private MUROIOutboundDAOImpl getDAO = (MUROIOutboundDAOImpl) BEAN_FACTORY
            .getBean("MUROIOutboundDAO");

    @Override
    public void createROIOutboundStatistics(RequestCore requestCore) {
        List<MUROIOutboundStatistics> muroiOutboundStatisticsList = new ArrayList<MUROIOutboundStatistics>();
        Set<String> muDocNamesSet = new HashSet<String>();
        if (ROIConstants.CANCELED_STATUS
                .equalsIgnoreCase(requestCore.getStatus())
                || ROIConstants.DENIED_STATUS
                        .equalsIgnoreCase(requestCore.getStatus())) {
            updateRequestStatusToCancel((int) requestCore.getId(),requestCore.getStatus());
        } else {
            // To delete the entries in ROI_OUTBOUND_STATISTICS for this
            // requestId and Create new rows for this requestId with
            // latest request Details
            getDAO.deleteROIOutbound((int) requestCore.getId());
       
        RequestCorePatientDAO rcPatientDAO = (RequestCorePatientDAO) getDAO(DAOName.REQUEST_PATIENT_DAO);
        List<MUROIOutboundStatistics> statisticsList = rcPatientDAO.retrieveAllStatisticsDetailsByRequestId(requestCore.getId());
        for(MUROIOutboundStatistics statistics : statisticsList)
        {
            // To check if the set object has the same HPF document type for an encounter and if so, then we 
            // will not add it into the list so that we do not create a duplicate entry for the same document type
            // for an encounter in ROI_Outbound_Statistics table
            if(!muDocNamesSet.contains(statistics.getHpfMuDocumentType() + "," + statistics.getEncounter()) && statistics.isSelectedForRelease())
            {
               MUROIOutboundStatistics muROIOutboundStatistics = new MUROIOutboundStatistics();
               muROIOutboundStatistics.setReqID((int) requestCore.getId());
               muROIOutboundStatistics.setReqDate(requestCore.getReceiptDate());
               muROIOutboundStatistics.setReqStatus(ROIConstants.REQUESTED_STATUS);
               muROIOutboundStatistics.setAvailBy(getUser().getInstanceId());
               muROIOutboundStatistics.setUserName(getUser().getInstanceId());
               muROIOutboundStatistics.setRequestFor(requestCore.getRequestorDetail().getRequestorTypeName());
               muROIOutboundStatistics.setFacility(statistics.getFacility().trim());
               muROIOutboundStatistics.setMrn(statistics.getMrn().trim());
               muROIOutboundStatistics.setPatientDOB(statistics.getPatientDOB());
               String[] patientName = statistics.getPatientName().trim().split(",");
               muROIOutboundStatistics.setPatientLastName(patientName[0]);
               if (patientName.length >= 2) {                   
                   muROIOutboundStatistics.setPatientFirstName(patientName[1]);
               }
               muROIOutboundStatistics.setPatientSex(statistics.getPatientSex());
               muROIOutboundStatistics.setEncounter(statistics.getEncounter());
               muROIOutboundStatistics.setDischargeDate(statistics.getDischargeDate());
               muROIOutboundStatistics.setHpfMuDocumentType(statistics.getHpfMuDocumentType());
               muROIOutboundStatistics.setCpiSeq(ROIConstants.REPORT_CPI_SEQ);
               muROIOutboundStatistics.setPatSeq(ROIConstants.REPORT_PAT_SEQ);
               muROIOutboundStatistics.setReportSname(ROIConstants.REPORT_SNAME);
               muDocNamesSet.add(statistics.getHpfMuDocumentType() + "," + statistics.getEncounter());
               muroiOutboundStatisticsList.add(muROIOutboundStatistics); 
            }      
         }
            getDAO.createROIOutbound(muroiOutboundStatisticsList);
            updateExternalSourceDocument(requestCore.getId(),statisticsList);
        }     
    }
     /**
      * Method to update the external source document table
      *
      * @param id
      * @param statisticsList
      */
    private void updateExternalSourceDocument(long requestId,List<MUROIOutboundStatistics> statisticsList) 
    {
       for(MUROIOutboundStatistics muroiOutboundStatistics : statisticsList)
       {
           if(muroiOutboundStatistics.isSelectedForRelease() && ROIConstants.ATTACHMENT_FILE_TYPE_EXTERNAL.equalsIgnoreCase(muroiOutboundStatistics.getType()))
           {
              List<ExternalSourceDocument> extsourceList = getCcdProviderDAO().getExternalSourceDocumentsByReqIdAndMrnAndEncounter((int)requestId,muroiOutboundStatistics.getMrn(),muroiOutboundStatistics.getEncounter());
              for(ExternalSourceDocument ext : extsourceList)
              {
                  if(!ext.isOutbounded())
                  {
                     ext.setReqStatus(ROIConstants.REQUESTED_STATUS);
                     getCcdProviderDAO().updateExternalSourceDocument(ext);
                  }  
              }
           }
           else if(!muroiOutboundStatistics.isSelectedForRelease() && ROIConstants.ATTACHMENT_FILE_TYPE_EXTERNAL.equalsIgnoreCase(muroiOutboundStatistics.getType()))
           {
               List<ExternalSourceDocument> extsourceList = getCcdProviderDAO().getExternalSourceDocumentsByReqIdAndMrnAndEncounter((int)requestId,muroiOutboundStatistics.getMrn(),muroiOutboundStatistics.getEncounter());
                   for(ExternalSourceDocument ext : extsourceList)
                   {
                       if(!ext.isOutbounded())
                       {
                          ext.setReqStatus(ROIConstants.NEW_STATUS);
                          getCcdProviderDAO().updateExternalSourceDocument(ext);
                       }
                   } 
           }
        }    
     }

    /**
     * Method to update Request Status for canceled and denied requests
     * 
     * @param requestId,requestStatus
     */
    private void updateRequestStatusToCancel(int requestId,String requestStatus) {
        List<MUROIOutboundStatistics> muList = getCcdProviderDAO()
                .getOutboundStatistics(requestId);
        Timestamp t = getDAO.getDate();
        for (MUROIOutboundStatistics muObj : muList) {
            muObj.setReqStatus(ROIConstants.CANCELED_STATUS);
            muObj.setCancelledDate(t);
            getDAO.updateROIOutbound(muObj);
        }
        getCcdProviderFactory().sendCancelledStatistics(requestId, t,requestStatus);
    }
    
    /**
     * Method to get the Dao information
     * 
     * @return CcdProviderDAOImpl
     */
    private CcdProviderDAO getCcdProviderDAO() {
        return (CcdProviderDAO) SpringUtil
                .getObjectFromSpring("CcdProviderDAO");
    }
    
    /**
     * Method to get the CcdProviderFactory instance
     */
    private CcdProviderFactory getCcdProviderFactory() {
        return (CcdProviderFactory) SpringUtil
                .getObjectFromSpring("ccdProviderFactory");
    }
    
}
