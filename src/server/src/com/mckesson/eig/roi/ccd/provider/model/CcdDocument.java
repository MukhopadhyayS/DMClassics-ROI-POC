package com.mckesson.eig.roi.ccd.provider.model;

import java.io.OutputStream;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CcdDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CcdDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="documentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="receivedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcdDocument", propOrder = {
    "_fileName",
    "_pageNumber",
    "_type",
    "_documentType",
    "_serviceDate",
    "_receivedDate"
})
public class CcdDocument {
    
    @XmlElement(name="fileName",required = true)
    private String _fileName;
    
    @XmlElement(name="type", required = true)
    private String _type;
    
    @XmlElement(name="pageNumber")
    private int _pageNumber;
    
    private transient OutputStream _outputStream;
    
    @XmlElement(name="documentType", required = true)
    private String _documentType;
    
    @XmlElement(name="serviceDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    private Date _serviceDate;
    
    @XmlElement(name="receivedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    private Date _receivedDate;

    public CcdDocument() {
    }

    public CcdDocument(String fileName, OutputStream outputStream, String type, String ccdType) {
        _fileName = fileName;
        _outputStream = outputStream;
        _type = type;
        _documentType = ccdType;
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
    
    public String getDocumentType() {
        return _documentType;
    }

    public void setDocumentType(String documentType) {
        _documentType = documentType;
    }

    public Date getServiceDate() {
        return _serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        _serviceDate = serviceDate;
    }

    public OutputStream getOutputStream() {
        return _outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        _outputStream = outputStream;
    }
    
    public void closeOutputStream() {
        if (_outputStream != null) {
            try {
                _outputStream.close();
                _outputStream = null;
            } catch (Exception ee) {
            }
        }
    }
    public Date getReceivedDate() {
        return _receivedDate;
    }

    public void setReceivedDate(Date date) {
        _receivedDate = date;
    }
}
