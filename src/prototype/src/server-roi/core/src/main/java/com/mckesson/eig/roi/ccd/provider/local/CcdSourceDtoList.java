package com.mckesson.eig.roi.ccd.provider.local;

import java.util.List;

public class CcdSourceDtoList {
    private List<CcdSourceDto> _ccdSourceDtos;

    public CcdSourceDtoList() {
    }

    public CcdSourceDtoList(List<CcdSourceDto> externalNames) {
        setCcdSourceDtos(externalNames);
    };

    public List<CcdSourceDto> getCcdSourceDtos() {
        return _ccdSourceDtos;
    }

    public void setCcdSourceDtos(List<CcdSourceDto> externalNames) {
        _ccdSourceDtos = externalNames;
    }

}
