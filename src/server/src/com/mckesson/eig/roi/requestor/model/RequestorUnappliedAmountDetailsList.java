package com.mckesson.eig.roi.requestor.model;

import java.util.List;

public class RequestorUnappliedAmountDetailsList {
    
    private List<RequestorUnappliedAmountDetails> _requestorUnappliedAmountDetails;

    public List<RequestorUnappliedAmountDetails> getRequestorUnappliedAmountDetails() {
        return _requestorUnappliedAmountDetails;
    }
    public void setRequestorUnappliedAmountDetails(
            List<RequestorUnappliedAmountDetails> reqUnappliedAmountList) {
        this._requestorUnappliedAmountDetails = reqUnappliedAmountList;
    }

}
