/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.requestor.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class RequestorStatementCriteria
implements Serializable {

    private static final long serialVersionUID = 1L;

    public static enum DateRange {

        DAYS_30(30),
        DAYS_60(60),
        DAYS_90(90),
        DAYS_120(120),
        YEAR_TO_DATE(-1),
        ALL(0);

        private int _agingDays;
        private DateRange(int dateRange) {

            if (-1 == dateRange) {
                _agingDays = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            } else {
                _agingDays = dateRange;
            }
        }

        public int getAgingDays() { return _agingDays; }

        @Override
        public String toString() {
            return String.valueOf(getAgingDays());
        }

    }

    private long _requestorId;
    private long _templateFileId;
    private String _templateName;
    private String _outputMethod;
    private SecureStringAccessor _queuePassword;
    private DateRange _dateRange;
    private List<String> _notes;
    private List<Long> _pastInvIds;

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public long getTemplateFileId() { return _templateFileId; }
    public void setTemplateFileId(long templateFileId) { _templateFileId = templateFileId; }

    public DateRange getDateRange() { return _dateRange; }
    public void setDateRange(DateRange dateRange) { _dateRange = dateRange; }

    public void setPastInvIds(List<Long> pastInvIds) { _pastInvIds = pastInvIds; }
    public List<Long> getPastInvIds() { return _pastInvIds; }

    public List<String> getNotes() { return _notes; }
    public void setNotes(List<String> notes) {  _notes = notes;  }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String templateName) { _templateName = templateName; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public String getQueuePassword() {
        if (_queuePassword == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) { 
        queuePassword = StringUtilities.safe(queuePassword);
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }


    public void setDateRangeAsString(String dateRange) {
        _dateRange = (null == dateRange) ? null : DateRange.valueOf(dateRange);
    }
    public String getDateRangeAsString() {
        return (null == _dateRange) ? null : _dateRange.toString();
    }

    public String getNotesAsString() {

        if (null == getNotes()) {
            return null;
        }

        StringBuffer noteString = new StringBuffer();
        for (String note : getNotes()) {

            noteString.append(note)
                      .append(ROIConstants.FIELD_SEPERATOR);
        }

        return noteString.substring(0, noteString.length() - 2);
    }
    public void setNotesAsString(String notes) {

        if (StringUtilities.isEmpty(notes)) {
            return;
        }

        String[] split = notes.split(ROIConstants.FIELD_SEPERATOR);
        _notes = Arrays.asList(split);

    }

    @Override
    public String toString() {
        return new StringBuffer()
                        .append("RequestorId: ")
                        .append(_requestorId)
                        .append(", RequestorTemplateFileId: ")
                        .append(_templateFileId)
                        .append(", DateRange: ")
                        .append(_dateRange)
                        .append(", PastInvoiceIds: ")
                        .append(_pastInvIds)
                        .append(", Notes: ")
                        .append(_notes)
                        .toString();
    }


}
