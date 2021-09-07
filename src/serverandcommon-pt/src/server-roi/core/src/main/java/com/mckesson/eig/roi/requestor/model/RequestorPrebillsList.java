package com.mckesson.eig.roi.requestor.model;

import java.util.List;

public class RequestorPrebillsList {

    private List<RequestorPrebill> _requestorPrebills = null;

    public RequestorPrebillsList() { }
    public RequestorPrebillsList(List<RequestorPrebill> requestorPrebills) {
        setRequestorPrebills(requestorPrebills);
    }

    public List<RequestorPrebill> getRequestorPrebills() { return _requestorPrebills; }
    public void setRequestorPrebills(List<RequestorPrebill> requestorPrebills) {
        _requestorPrebills = requestorPrebills;
    }


}

