package com.mckesson.eig.roi.ccd.provider.local;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CcdSourceDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CcdSourceDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sourceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ccdSourceConfigDto" type="{urn:eig.mckesson.com}CcdSourceConfigDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcdSourceDto", propOrder = {
    "_sourceId",
    "_sourceName",
    "_providerName",
    "_description",
    "_ccdSourceConfigDto"
})
public class CcdSourceDto {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="sourceId")
    private int _sourceId;
    @XmlElement(name="providerName", required = true)
    private String _providerName;
    @XmlElement(name="sourceName", required = true)
    private String _sourceName;
    @XmlElement(name="description", required = true)
    private String _description;
    @XmlElement(name="ccdSourceConfigDto")
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
