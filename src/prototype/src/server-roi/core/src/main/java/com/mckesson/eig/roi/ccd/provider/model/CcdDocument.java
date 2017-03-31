package com.mckesson.eig.roi.ccd.provider.model;

import java.io.OutputStream;
import java.util.Date;

public class CcdDocument {
    private String _fileName;
    private String _type;
    private int _pageNumber;
    private transient OutputStream _outputStream;
    private String _documentType;
    private Date _serviceDate;
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
