/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Karthik Easwaran
 * @date   Oct 19, 2012
 * @since  Oct 19, 2012
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingPaymentInfo", propOrder = {
    "_paymentMethods",
    "_billingTemplates",
    "_feeTypes",
    "_deliveryMethods",
    "_weight",
    "_reasons",
    "_requestorType",
    "_countries"
})
public class BillingPaymentInfo
implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement(name="countries")
    private List<Country> _countries;
    @XmlElement(name="weight", required = true)
    private Weight _weight;
    @XmlElement(name="billingTemplateList", required = true)
    private BillingTemplatesList _billingTemplates;
    @XmlElement(name="feeTypeList", required = true)
    private FeeTypesList _feeTypes;
    @XmlElement(name="deliveryMethodList", required = true)
    private DeliveryMethodList _deliveryMethods;
    @XmlElement(name="paymentMethodList", required = true)
    private PaymentMethodList _paymentMethods;
    @XmlElement(name="requestorType", required = true)
    private RequestorType _requestorType;
    @XmlElement(name="reasonsList", required = true)
    private ReasonsList _reasons;

    public List<Country> getCountries() { return _countries; }
    public void setCountries(List<Country> countries) { _countries = countries; }

    public Weight getWeight() { return _weight; }
    public void setWeight(Weight weight) { _weight = weight; }

    public BillingTemplatesList getBillingTemplates() { return _billingTemplates; }
    public void setBillingTemplates(BillingTemplatesList billingTemplates) {
        _billingTemplates = billingTemplates;
    }

    public FeeTypesList getFeeTypes() { return _feeTypes; }
    public void setFeeTypes(FeeTypesList feeTypes) { _feeTypes = feeTypes; }

    public DeliveryMethodList getDeliveryMethods() { return _deliveryMethods; }
    public void setDeliveryMethods(DeliveryMethodList deliveryMethods) {
        _deliveryMethods = deliveryMethods;
    }

    public PaymentMethodList getPaymentMethods() { return _paymentMethods; }
    public void setPaymentMethods(PaymentMethodList paymentMethods) {
        _paymentMethods = paymentMethods;
    }
    public RequestorType getRequestorType() { return _requestorType; }
    public void setRequestorType(RequestorType requestorType) {
        _requestorType = requestorType;
    }

    public void setReasons(ReasonsList reasons) { _reasons = reasons; }
    public ReasonsList getReasons() { return _reasons; }

}
