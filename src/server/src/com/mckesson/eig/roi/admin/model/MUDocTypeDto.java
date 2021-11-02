package com.mckesson.eig.roi.admin.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MUDocTypeDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MUDocTypeDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="muDocId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="muDocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MUDocTypeDto", propOrder = {
    "muDocId",
    "muDocName"
})
public class MUDocTypeDto {
    
  private  Long muDocId;
  @XmlElement(required = true)
  private  String muDocName;
  
public Long getMuDocId() {
    return muDocId;
}
public void setMuDocId(Long muDocId) {
    this.muDocId = muDocId;
}
public String getMuDocName() {
    return muDocName;
}
public void setMuDocName(String muDocName) {
    this.muDocName = muDocName;
}

}
