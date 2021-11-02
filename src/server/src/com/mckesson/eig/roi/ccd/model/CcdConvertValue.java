package com.mckesson.eig.roi.ccd.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ccdConvertResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ccdConvertResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ccdConvertResult", propOrder = {
    "_fileName",
    "_pageNumber",
    "_type"
})
public class CcdConvertValue {
    
    @XmlElement(name="fileName", required=true)
    private String _fileName;
    
    @XmlElement(name="type", required=true)
    private String _type;
    
    @XmlElement(name="pageNumber")
    private int _pageNumber;
    
    public CcdConvertValue() {
    }
 
   public CcdConvertValue(String fileName, int pageNumber, String type) {
	_fileName = fileName;
	_pageNumber = pageNumber;
	_type = type;
    }
    
    public String getFileName() {
        return _fileName;
    }
    
    public void setFileName(String fileName) {
        _fileName = fileName;
    }
    
    public int getPageNumber() {
        return _pageNumber;
    }
    
    public void setPageNumber(int pageNumber) {
        _pageNumber = pageNumber;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }
}
