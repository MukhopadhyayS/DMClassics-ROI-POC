package com.mckesson.eig.roi.ccd.provider.local;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for RetrieveCCDDtoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveCCDDtoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retrieveccddto" type="{urn:eig.mckesson.com}RetrieveCCDDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveCCDDtoList", propOrder = {
    "_retrieveParameters"
})
public class RetrieveCCDDtoList {

    @XmlElement(name="retrieveccddto")
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
