/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2014 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.billing.xdocreport.model;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.billing.letter.model.ShippingInfo;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocShippingInfo {
    
    private String _address1 = StringUtilities.EMPTYSTRING;
    private String _address2 = StringUtilities.EMPTYSTRING;
    private String _address3 = StringUtilities.EMPTYSTRING;
    private String _city = StringUtilities.EMPTYSTRING;
    private String _state = StringUtilities.EMPTYSTRING;
    private String _postalCode = StringUtilities.EMPTYSTRING;
    private String _shippingURL = StringUtilities.EMPTYSTRING;
    private String _trackingNumber = StringUtilities.EMPTYSTRING;
    private String _willReleaseShipped = StringUtilities.EMPTYSTRING;
    private String _shippingWt = StringUtilities.EMPTYSTRING;
    private String _addressType = StringUtilities.EMPTYSTRING;
    private String _shippingMethod = StringUtilities.EMPTYSTRING;
    private String _shippingMethodId = StringUtilities.EMPTYSTRING;
    private String _outputMethod = StringUtilities.EMPTYSTRING;
    
    private String _countryCode = StringUtilities.EMPTYSTRING;
    private String _countryName = StringUtilities.EMPTYSTRING;

    public XDocShippingInfo(ShippingInfo shippingInfo) {
        
        if (null == shippingInfo) {
            return;
        }
        
        setAddress1(StringUtilities.safe(shippingInfo.getAddress1()));
        setAddress2(StringUtilities.safe(shippingInfo.getAddress2()));
        setAddress3(StringUtilities.safe(shippingInfo.getAddress3()));
        setCity(StringUtilities.safe(shippingInfo.getCity()));
        setState(StringUtilities.safe(shippingInfo.getState()));
        setPostalCode(StringUtilities.safe(shippingInfo.getPostalCode()));
        setShippingURL(StringUtilities.safe(shippingInfo.getShippingURL()));
        setTrackingNumber(StringUtilities.safe(shippingInfo.getTrackingNumber()));
        setWillReleaseShipped(StringUtilities.safe(shippingInfo.getWillReleaseShipped()));
        setShippingWt(StringUtilities.safe(shippingInfo.getShippingWt()));
        setAddressType(StringUtilities.safe(shippingInfo.getAddressType()));
        setShippingMethod(StringUtilities.safe(shippingInfo.getShippingMethod()));
        setShippingMethodId(StringUtilities.safe(shippingInfo.getShippingMethodId()));
        setOutputMethod(StringUtilities.safe(shippingInfo.getOutputMethod()));
        setCountryCode(StringUtilities.safe(shippingInfo.getCountryCode()));
        setCountryName(StringUtilities.safe(shippingInfo.getCountryName()));
    }
    
    public String getAddress1() { return _address1; }
    public void setAddress1(String address1) { _address1 = address1; }

    public String getAddress2() { return _address2;  }
    public void setAddress2(String address2) { _address2 = address2; }

    public String getAddress3() { return _address3; }
    public void setAddress3(String address3) { _address3 = address3; }

    public String getCity() { return _city; }
    public void setCity(String city) { _city = city; }

    public String getState() { return _state; }
    public void setState(String state) { _state = state; }

    public String getPostalCode() { return _postalCode;  }
    public void setPostalCode(String code) {  _postalCode = code;  }

    public String getShippingURL() { return _shippingURL; }
    public void setShippingURL(String shippingurl) { _shippingURL = shippingurl; }

    public String getTrackingNumber() { return _trackingNumber; }
    public void setTrackingNumber(String number) { _trackingNumber = number; }

    public String getShippingWt() { return _shippingWt; }
    public void setShippingWt(String wt) { _shippingWt = wt; }

    public String getAddressType() { return _addressType; }
    public void setAddressType(String type) { _addressType = type; }

    public String getShippingMethod() { return _shippingMethod; }
    public void setShippingMethod(String method) { _shippingMethod = method; }

    public String getShippingMethodId() { return _shippingMethodId; }
    public void setShippingMethodId(String methodId) { _shippingMethodId = methodId; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String method) { _outputMethod = method; }

    public String getWillReleaseShipped() { return _willReleaseShipped; }
    public void setWillReleaseShipped(String releaseShipped) {
        _willReleaseShipped = releaseShipped;
    }
    
    public String getCountryCode() { return _countryCode; }
    public void setCountryCode(String countryCode) {_countryCode = countryCode;}
    
    public String getCountryName() {
        return _countryName;
    }
    public String getCountry() {
        return _countryName;
    }
    public void setCountryName(String countryName) {
        _countryName = countryName;
    }
    
    public String getInvoiceItemXml() {
        
        StringBuffer xml = new StringBuffer();
        xml.append("<" + ROIConstants.RELEASE_ITEM_TAG + " " + ROIConstants.TYPE_ATTRIBUTE + "=\"")
            .append(ROIConstants.SHIPPING_INFO_TYPE + "\" > \n");
            
        xml.append(createAttributeElement(ROIConstants.PATIENT_ADDRESS_1_KEY,
                                          checkNull(_address1)))
            .append(createAttributeElement(ROIConstants.PATIENT_ADDRESS_2_KEY, 
                                          checkNull(_address2)))
            .append(createAttributeElement(ROIConstants.PATIENT_ADDRESS_3_KEY,
                                          checkNull(_address3)))
            .append(createAttributeElement(ROIConstants.PATIENT_CITY_KEY,
                                          checkNull(_city)))
            .append(createAttributeElement(ROIConstants.PATIENT_STATE_KEY, 
                                          checkNull(_state)))
            .append(createAttributeElement(ROIConstants.PATIENT_POSTAL_CODE_KEY,
                                          checkNull(_postalCode)))
            .append(createAttributeElement(ROIConstants.PATIENT_SHIPPING_URL_KEY,
                                          checkNull(_shippingURL)))
            .append(createAttributeElement(ROIConstants.PATIENT_TRACKING_NUMBER_KEY, 
                                          checkNull(_trackingNumber)))
            .append(createAttributeElement(ROIConstants.PATIENT_REL_SHIPPED_NUMBER_KEY,
                                          checkNull(_willReleaseShipped)))
            .append(createAttributeElement(ROIConstants.PATIENT_SHIPPING_WEIGHT_KEY,
                                          checkNull(_shippingWt)))
            .append(createAttributeElement(ROIConstants.PATIENT_ADDRESS_TYPE_KEY, 
                                          checkNull(_addressType)))
            .append(createAttributeElement(ROIConstants.PATIENT_SHIPPING_METHOD_KEY,
                                          checkNull(_shippingMethod)))
            .append(createAttributeElement(ROIConstants.PATIENT_SHIPPING_METHOD_ID_KEY,
                                          checkNull(_shippingMethodId)))
            .append(createAttributeElement(ROIConstants.OUTPUT_METHOD,
                                          checkNull(_outputMethod)));
        
        xml.append("</" + ROIConstants.RELEASE_ITEM_TAG + "> \n");
        return xml.toString();
    }
    
    private String checkNull(String value) {
        return (StringUtilities.isEmpty(value)) ? "" : value;
    }
    
    private String createAttributeElement(String key, String value) {
        
        StringBuffer buf = new StringBuffer();
        buf.append("<" + ROIConstants.ATTRIBUTE_TAG + " ");
        buf.append(ROIConstants.NAME_ATTRIBUTE + "=\"" + key + "\" ");
        buf.append(ROIConstants.VALUE_ATTRIBUTE + "=\"" + toASCII(value) + "\" ");
        buf.append("/> \n");
        return buf.toString();
    }
    
    private String toASCII(String str) {

        String ascii;
        ascii = str.replaceAll("&", "&amp;");
        ascii = ascii.replaceAll("\"", "&quot;");
        ascii = ascii.replaceAll("<", "&#60;");
        ascii = ascii.replaceAll(">", "&#62;");

        return ascii;
    }
    
}
