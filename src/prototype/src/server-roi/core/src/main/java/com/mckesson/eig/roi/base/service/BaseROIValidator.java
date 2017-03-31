/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.base.service;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.api.ValidationParams;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * This class creates a ROIException  from list of exceptions
 *
 * @author manikandans
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Mar 25, 2008
 */
public class BaseROIValidator {

    private ROIException _exception = null;


    /**
     * This method returns the ROIException
     *
     * @return ROIException with nested cause
     */
    public ROIException getException() {
        return _exception;
    }

    /**
     * This method creates ROIException with error code as nested cause
     *
     * @param errorCode
     */
    protected void addError(ROIClientErrorCodes errorCode) {

        _exception = (_exception == null) ? new ROIException(errorCode)
                                          : new ROIException(_exception, errorCode);
    }

    /**
     * This method creates ROIException with error code , error data and nested cause
     *
     * @param errorCode
     * @param errorData
     */
    protected void addError(ROIClientErrorCodes errorCode, String errorData) {

        _exception = (_exception == null) ? new ROIException(errorCode, errorData)
                                          : new ROIException(_exception,
                                                             errorCode,
                                                             errorData);
    }

    /**
     * This method checks the _exception and return true if the _exception is  empty
     *
     * @return Error status (true/false)
     */
    protected boolean hasNoErrors() {
        return (_exception == null);
    }

    /**
     * This method validates epn value whether it comes along with epn prefix or not
     * @param epnEnabled
     * @param epnPrefix
     * @param epnValue
     */
    protected void validateEPN(boolean epnEnabled, String epnPrefix, String epnValue) {

        if (epnEnabled
           && (StringUtilities.hasContent(epnValue))
           && (StringUtilities.hasContent(epnPrefix))
           && (!epnValue.toLowerCase().startsWith(epnPrefix.toLowerCase()))) {

            addError(ROIClientErrorCodes.EPN_PREFIX_DIFFERS);
        }
    }

    /**
     * This method validates the spl character, mandatory fields and max length
     * @param obj to be validated
     * @return validation result
     */
    protected boolean validateFields(Object obj) {

        try {

            Class< ? > annotatedClass = obj.getClass();
            BeanInfo info = Introspector.getBeanInfo(annotatedClass);
            PropertyDescriptor[] propDescriptors = info.getPropertyDescriptors();

            for (PropertyDescriptor descriptor : propDescriptors) {

                Method method = descriptor.getWriteMethod();
                if (method != null) {

                    ValidationParams vp = method.getAnnotation(ValidationParams.class);
                    if (vp != null) {

                        Object o = descriptor.getReadMethod().invoke(obj);
                        String s = (o != null) ? o.toString() : null;
                        if (vp.isMandatory() && StringUtilities.isEmpty(s)) {
                            addError(vp.isMandatoryErrCode());
                        }
                        if (StringUtilities.hasContent(s)) {

                            validateLength(s, vp.maxLength(), vp.maxLenErrCode());
                            validateSplChar(vp.pattern(), s, vp.misMatchErrCode());

                        }
                    }
                }
            }
        } catch (Throwable e) {
            throw new ROIException();
        }
        return hasNoErrors();
    }

    private void validateSplChar(String matchChar, String s, ROIClientErrorCodes err) {
        Pattern p = Pattern.compile(matchChar);
        Matcher m = p.matcher(s);
        if (!m.matches()) {
            addError(err);
        }
    }

    private void validateLength(String s, int maxLen, ROIClientErrorCodes err) {
        if (!StringUtilities.isValidLength(s, maxLen)) {
            addError(err);
        }
    }
}
