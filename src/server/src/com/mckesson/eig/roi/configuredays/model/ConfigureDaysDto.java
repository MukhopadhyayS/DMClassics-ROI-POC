package com.mckesson.eig.roi.configuredays.model;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigureDaysDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigureDaysDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dayName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dayStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigureDaysDto", propOrder = {
    "dayName",
    "dayStatus"
})
public class ConfigureDaysDto {
    
    
    private static final long serialVersionUID = 1L;
    private String dayName;
    private String dayStatus;
    public String getDayName() {
        return dayName;
    }
    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
    public String getDayStatus() {
        return dayStatus;
    }
    public void setDayStatus(String dayStatus) {
        this.dayStatus = dayStatus;
    }


}
