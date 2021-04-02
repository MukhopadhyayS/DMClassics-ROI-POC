package com.mckesson.eig.roi.requestor.model;

import java.util.List;


public class RequestorHistoryList {
    
    public RequestorHistoryList() { };
    public RequestorHistoryList(List<RequestorHistory> list) { setRequestorHistory(list); };

    private List<RequestorHistory> _requestorHistory;

    public List<RequestorHistory> getRequestorHistory() { return _requestorHistory; }
    public void setRequestorHistory(List<RequestorHistory> list) { _requestorHistory = list; }

}


