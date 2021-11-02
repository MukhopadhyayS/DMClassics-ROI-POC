package com.mckesson.eig.roi.configuredays.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigureDaysDtoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigureDaysDtoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configureDaysList" type="{urn:eig.mckesson.com}ConfigureDaysDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigureDaysDtoList", propOrder = {
    "_configureDaysDtoList"
})
public class ConfigureDaysDtoList {
    
    @XmlElement(name="configureDaysList")
    private List<ConfigureDaysDto> _configureDaysDtoList;

    public ConfigureDaysDtoList() {
    }

    public List<ConfigureDaysDto> getConfigureDaysDtoList() {
        return _configureDaysDtoList;
    }

    public void setConfigureDaysDtoList(List<ConfigureDaysDto> daysDtoList) {
        _configureDaysDtoList = daysDtoList;
    }
    
    public ConfigureDaysDtoList(List<ConfigureDaysDto> status) {
        setConfigureDaysDtoList(status);
}

  

    
    

}
