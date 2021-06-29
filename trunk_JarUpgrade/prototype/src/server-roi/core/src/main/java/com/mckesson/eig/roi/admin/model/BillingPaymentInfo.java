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

/**
 * @author Karthik Easwaran
 * @date   Oct 19, 2012
 * @since  Oct 19, 2012
 */
public class BillingPaymentInfo
implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Country> _countries;
    private Weight _weight;
    private BillingTemplatesList _billingTemplates;
    private FeeTypesList _feeTypes;
    private DeliveryMethodList _deliveryMethods;
    private PaymentMethodList _paymentMethods;
    private RequestorType _requestorType;
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
