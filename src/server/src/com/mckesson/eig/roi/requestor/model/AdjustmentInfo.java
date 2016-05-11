package com.mckesson.eig.roi.requestor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mckesson.eig.roi.admin.model.ReasonsList;

public class AdjustmentInfo {

    private ReasonsList _reasonsList;
    private RequestorAdjustment _requestorAdjustment;
    private RequestorInvoicesList _requestorInvoicesList;
    private List<AdjustmentType> _adjustmentTypes;

    public ReasonsList getReasonsList() { return _reasonsList; }
    public void setReasonsList(ReasonsList reasonsList) { _reasonsList = reasonsList; }

    public RequestorAdjustment getRequestorAdjustment() { return _requestorAdjustment; }
    public void setRequestorAdjustment(RequestorAdjustment requestorAdjustment) {
        _requestorAdjustment = requestorAdjustment;
    }

    public RequestorInvoicesList getRequestorInvoicesList() { return _requestorInvoicesList; }
    public void setRequestorInvoicesList(RequestorInvoicesList requestorInvoicesList) {
        _requestorInvoicesList = requestorInvoicesList;
    }

    public List<String> getAdjustmentTypes() {

        List<AdjustmentType> asList = Arrays.asList(AdjustmentType.values());
        List<String> typeAsString = new ArrayList<String>();
        for (AdjustmentType type : asList) {
            typeAsString.add(type.toString());
        }
        return typeAsString;
    }
    public void setAdjustmentTypes(List<AdjustmentType> adjustmentTypes) {
        _adjustmentTypes = adjustmentTypes;
    }

}
