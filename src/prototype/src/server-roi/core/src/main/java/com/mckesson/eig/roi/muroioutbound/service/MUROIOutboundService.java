package com.mckesson.eig.roi.muroioutbound.service;

import java.util.Date;
import java.util.List;

public interface MUROIOutboundService {

    /**
     * Method to get the details for Details Screen for Report Generation
     * 
     * @param fromDate
     * @param toDate
     * @param facList
     * @param mudoctype
     * @return
     */
    public List<Object[]> retriveMURequestDetailsForReport(Date fromDate,
            Date toDate, String[] facList, String mudoctype);

    /**
     * Method to get the details for TotalsPerFacility Screen with GrandTotal
     * for Report Generation
     * 
     * @param fromDate
     * @param toDate
     * @param facList
     * @param mudoctype
     * @return
     */
    public List<Object[]> grandTotalsPerFacility(Date fromDate, Date toDate,
            String[] facList, String mudoctype);

}
