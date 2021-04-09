/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.letter.model;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.utils.SecureStringAccessor;

/**
 * @author OFS
 * @date   Oct 6, 2011
 * @since  Oct 6, 2011
 */
public class RequestorLetters {

    private List<InvoiceCharge> _invoiceCharges;
    private RequestorInfo _requestor;
    private ShippingInfo _shippingInfo;
    private SecureStringAccessor _queuePassword;
    private String _outputMethod;
    private String _templateName;
    private String _templateFileId;
    private String _resentDt;
    private List<String> _notesList;
    private List<Note> _notes;
    private String _requestorGroupingKey;

    public RequestorLetters() { }

    public RequestorLetters(List<InvoiceCharge> invCharges, RequestorInfo rInfo,
                            ShippingInfo shippingInfo, List<String> notes) {

        setInvoices(invCharges);
        setRequestor(rInfo);
        setShippingInfo(shippingInfo);
        setNotesList(notes);
    }

    public List<InvoiceCharge> getInvoices() { return _invoiceCharges; }
    public void setInvoices(List<InvoiceCharge> invCharges) { _invoiceCharges = invCharges; }

    public RequestorInfo getRequestor() { return _requestor; }
    public void setRequestor(RequestorInfo requestor) { _requestor = requestor; }

    public ShippingInfo getShippingInfo() { return _shippingInfo; }
    public void setShippingInfo(ShippingInfo shippingInfo) { _shippingInfo = shippingInfo; }

    public String getQueuePassword() { 
        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }

    public void setQueuePassword(String queuePassword) {    
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
     }

    public String getResentDt() { return (null == _resentDt) ? "" : _resentDt; }
    public void setResentDt(String resentDt) { _resentDt = resentDt; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String templateName) { _templateName = templateName; }

    public List<String> getNotesList() { return _notesList; }
    public void setNotesList(List<String> notesList) { _notesList = notesList; }

    public void setNotes(List<Note> notes) {  _notes = notes; }
    public List<Note> getNotes() {

        if (null != _notes) {
            return _notes;
        }

        List<Note> noteList = new ArrayList<Note>();
        if (null == _notesList) {

            noteList.add(new Note());
            return noteList;
        }

        Note note;
        for (String noteDesc : _notesList) {

            note = new Note();
            note.setDescription(noteDesc);
            noteList.add(note);
        }

        if (noteList.isEmpty()) {
            noteList.add(new Note());
        }
        setNotes(noteList);
        return noteList;
    }

    public String getRequestorGroupingKey() { return _requestorGroupingKey; }
    public void setRequestorGroupingKey(String requestorGroupingKey) {
        _requestorGroupingKey = requestorGroupingKey;
    }

    public String getTemplateFileId() { return _templateFileId; }
    public void setTemplateFileId(String templateFileId) { _templateFileId = templateFileId; }

}
