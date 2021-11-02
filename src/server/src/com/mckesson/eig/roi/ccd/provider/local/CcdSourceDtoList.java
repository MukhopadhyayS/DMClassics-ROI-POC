package com.mckesson.eig.roi.ccd.provider.local;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for CcdSourceDtoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CcdSourceDtoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ccdSourceDto" type="{urn:eig.mckesson.com}CcdSourceDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcdSourceDtoList", propOrder = {
    "_ccdSourceDtos"
})
public class CcdSourceDtoList {
    
    @XmlElement(name="ccdSourceDto")
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
