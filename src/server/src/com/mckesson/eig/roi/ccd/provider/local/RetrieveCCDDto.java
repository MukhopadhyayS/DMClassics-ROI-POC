package com.mckesson.eig.roi.ccd.provider.local;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for RetrieveCCDDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveCCDDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retrieveCCDKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="retrieveCCDValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveCCDDto", propOrder = {
    "_retrieveCCDKey",
    "_retrieveCCDValue"
})
public class RetrieveCCDDto {

    @XmlElement(name="retrieveCCDKey", required = true)
    private String _retrieveCCDKey;
    
    @XmlElement(name="retrieveCCDValue", required = true)
    private String _retrieveCCDValue;
    
    public String getRetrieveCCDKey() {
        return _retrieveCCDKey;
    }
    public void setRetrieveCCDKey(String key) {
        _retrieveCCDKey = key;
    }
    public String getRetrieveCCDValue() {
        return _retrieveCCDValue;
    }
    public void setRetrieveCCDValue(String value) {
        _retrieveCCDValue = value;
    }

}
