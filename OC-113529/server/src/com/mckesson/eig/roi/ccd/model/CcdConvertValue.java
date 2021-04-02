package com.mckesson.eig.roi.ccd.model;

public class CcdConvertValue {
    private String _fileName;
    private String _type;
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
