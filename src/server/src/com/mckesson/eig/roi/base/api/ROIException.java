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

package com.mckesson.eig.roi.base.api;


import com.mckesson.eig.utility.exception.ApplicationException;


/**
 *
 * @author ranjithr
 * @date   Jan 29, 2008
 * @since  HPF 13.1 [ROI]; Jan 29, 2008
 */
@SuppressWarnings("serial")
public class ROIException
extends ApplicationException {

    /**
     * Default constructor
     */
    public ROIException() {
        super();
    }

    /**
     * Construct with errorCode
     *
     * @param errorCode
     *          Error Code enum for the exception
     */
    public ROIException(ROIClientErrorCodes errorCode) {
        setErrorCode(errorCode.toString());
    }

     /**
     * Construct with cause and errorCode
     *
     * @param cause
     *          Actual cause of the exception
     * @param errorCode
     *          Error Code enum for the exception
     */
    public ROIException(Throwable cause, ROIClientErrorCodes errorCode) {
        super(cause, errorCode.toString());
    }

    /**
     * Construct with errorCode and errorData
     *
     * @param errorCode
     *          Error Code enum for the exception
     * @param errorData
     *          Error Data causing exception
     */
    public ROIException(ROIClientErrorCodes errorCode, String errorData) {
        super(null, errorCode.toString(), errorData);
    }


    /**
     * Construct with cause,errorCode and errorData
     *
     * @param cause
     *          Actual cause of the exception
     * @param errorCode
     *          Error Code enum for the exception
     * @param errorData
     *          Error Data causing exception
     */
    public ROIException(Throwable cause, ROIClientErrorCodes errorCode, String errorData) {
        super(null, cause, errorCode.toString(), errorData);
    }

    @Override
    public String toString() {

        String s = getClass().getName();
        String message = getLocalizedMessage();
        String errorCode = getErrorCode();
        String extendedCode = getExtendedCode();

        String errorMessage = new StringBuffer()
                                        .append(s)
                                        .append((message != null) ? (": " + message) : "")
                                        .append((errorCode != null)
                                                            ? (": ErrorCode: " + errorCode)
                                                            : "")
                                        .append((extendedCode != null)
                                                            ? (": ExtendedCode: " + extendedCode)
                                                            : "")
                                        .toString();

        return errorMessage;
    }
}
