package com.mckesson.eig.roi.ccd.provider.local;

import java.util.List;

public class RetrieveCCDDtoList {

    private List<RetrieveCCDDto> _retrieveParameters;

    public RetrieveCCDDtoList() {
    }

    public RetrieveCCDDtoList(List<RetrieveCCDDto> retrieveParameters) {
        setRetrieveParameters(retrieveParameters);
    };

    public List<RetrieveCCDDto> getRetrieveParameters() {
        return _retrieveParameters;
    }

    public void setRetrieveParameters(List<RetrieveCCDDto> retrieveParameters) {
        _retrieveParameters = retrieveParameters;
    }

}
