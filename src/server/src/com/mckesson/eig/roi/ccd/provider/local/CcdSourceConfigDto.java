package com.mckesson.eig.roi.ccd.provider.local;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for CcdSourceConfigDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CcdSourceConfigDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="configValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="configLabel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcdSourceConfigDto", propOrder = {
    "_configKey",
    "_configValue",
    "_configLabel"
})
public class CcdSourceConfigDto {
    @XmlElement(name="configKey", required=true)
    private String _configKey;
    @XmlElement(name="configValue", required=true)
    private String _configValue;
    @XmlElement(name="configLabel", required=true)
    private String _configLabel;
    
    
    public String getConfigKey() {
        return _configKey;
    }
    
    public void setConfigKey(String configKey) {
        _configKey = configKey;
    }
    
    public String getConfigValue() {
        return _configValue;
    }
    
    public void setConfigValue(String configValue) {
        _configValue = configValue;
    }

    public String getConfigLabel() {
        return _configLabel;
    }

    public void setConfigLabel(String configLabel) {
        _configLabel = configLabel;
    }
    
   

}
