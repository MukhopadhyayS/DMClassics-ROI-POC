/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.base.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.billing.dao.OverDueInvoiceCoreDAO;
import com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAO;
import com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Aug 24, 2008
 * @since  HPF 13.1 [ROI]; Nov 19, 2008
 */
public abstract class BaseLetterDataRetriever
extends ROIDAOImpl
implements LetterDataRetriever {

    private static final OCLogger LOG = new OCLogger(BaseLetterDataRetriever.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    public static final String DATE_FORMAT = "MM/dd/yyyy";

    protected RequestCorePatientDAO rcPatientDAO;
    protected RequestCoreDAO requestCoreDAO;
    protected RequestCoreChargesDAO rcChargesDAO;
    protected RequestCoreDeliveryDAO rcDeliveryDAO;

    /**
     * This method retrieves the Letter Template
     * @param letterData - contains the template id
     * @returns LetterTemplateDocument
     */
    @Override
    public LetterTemplateDocument retrieveLetterTemplate(long templateId) {

        final String logSM = "retrieveLetterTemplate(templateId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        LetterTemplateDocument doc = (LetterTemplateDocument) get(LetterTemplateDocument.class,
                                                                  templateId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Document Name : " + doc.getName());
        }

        return doc;
    }

    @Override
    public Object retrieveLetterData(long invoiceId, long requestCoreId) {
        return null;
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    protected RequestCoreDAO getRequestCoreDAO() {
        return (requestCoreDAO = (RequestCoreDAO) getDAO("RequestCoreDAO"));
    }

    protected RequestCoreDeliveryDAO getRequestCoreDeliveryDAO() {
        return rcDeliveryDAO = (RequestCoreDeliveryDAO) getDAO("RequestCoreDeliveryDAO");
    }

    protected RequestCorePatientDAO getRequestCorePatientDAO() {
        return rcPatientDAO = (RequestCorePatientDAO) getDAO("RequestCorePatientDAO");
    }

    public void setRcPatientDAO(RequestCorePatientDAO rcPatientDAO) {
        this.rcPatientDAO = rcPatientDAO;
    }
    public void setRequestCoreDAO(RequestCoreDAO requestCoreDAO) {
        this.requestCoreDAO = requestCoreDAO;
    }
    public void setRcDeliveryDAO(RequestCoreDeliveryDAO rcDeliveryDAO) {
        this.rcDeliveryDAO = rcDeliveryDAO;
    }
    public RequestCoreChargesDAO getRequestCoreChargesDAO() {
        return rcChargesDAO = (RequestCoreChargesDAO) getDAO("RequestCoreChargesDAO");
    }
    public void setRcChargesDAO(RequestCoreChargesDAO rcChargesDAO) {
        this.rcChargesDAO = rcChargesDAO;
    }

    public RequestorStatementDAO getRequestorStatementDAO() {
        return (RequestorStatementDAO) getDAO("RequestorStatementDAO");
    }

    public OverDueInvoiceCoreDAO getOverDueInvoiceDAO() {
        return (OverDueInvoiceCoreDAO) getDAO("OverDueInvoiceCoreDAO");
    }

    /**
     * format the given Amount to the currency.
     * @param amount
     * @return String - formatted String
     */
    protected String formatToCurrency(Double amount) {

        double number;
        if (null == amount) {
            number = 0.00;
        } else {
            number = amount.doubleValue();
        }

        return ROIConstants.CURRENCY_FORMAT.format(number);

    }

    /**
     * parse the given currency to double.
     * @param amount
     * @return String - formatted String
     */
    protected Number parseCurrency(String currency) {

        if (null == currency) {
           return 0.00;
        }

        Number amount = 0.00;
        try {

            amount = ROIConstants.CURRENCY_FORMAT.parse(currency);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return amount;
    }

    /**
     * Changes the given amount sign to negative
     * @param amount
     * @return -negative signed number
     */
    protected Double toNegative(Double amount) {

        if (amount == null
                || amount.doubleValue() == 0.00) {
            return new Double(0.00);
        }
        return (-Math.abs(amount));
    }

    /**
     * Changes the given amount sign to negative
     * @param amount
     * @return -negative signed number
     */
    protected Double toPositive(Double amount) {

        if (amount == null) {
            return new Double(0.00);
        }
        return Math.abs(amount);
    }

}
