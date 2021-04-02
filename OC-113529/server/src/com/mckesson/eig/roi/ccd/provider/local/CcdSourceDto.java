package com.mckesson.eig.roi.ccd.provider.local;


import java.util.List;

public class CcdSourceDto {

    private static final long serialVersionUID = 1L;
    private int _sourceId;
    private String _providerName;
    private String _sourceName;
    private String _description;
    private List<CcdSourceConfigDto> _ccdSourceConfigDto;

    public String getProviderName() {
        return _providerName;
    }
    
    public void setProviderName(String name) {
        _providerName = name;
    }
    
    public String getSourceName() {
        return _sourceName;
    }
    
    public void setSourceName(String displayName) {
        _sourceName = displayName;
    }
    
    public String getDescription() {
        return _description;
    }
    
    public void setDescription(String description) {
        _description = description;
    }

    public List<CcdSourceConfigDto> getCcdSourceConfigDto() {
        return _ccdSourceConfigDto;
    }

    public void setCcdSourceConfigDto(
            List<CcdSourceConfigDto> dto) {
        _ccdSourceConfigDto = dto;
    }

    public int getSourceId() {
        return _sourceId;
    }

    public void setSourceId(int sourceId) {
        _sourceId = sourceId;
    }

}
