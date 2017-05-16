package com.mckesson.eig.roi.conversion.billinglocation;


public class BillingLocation {
	private String _name;
	private String _code;
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		if (name != null) {
			_name = name.trim();
		}
	}
	
	public String getCode() {
		return _code;
	}
	
	public void setCode(String code) {
		if (code != null) {
			_code = code.trim();
		}
	}
}
