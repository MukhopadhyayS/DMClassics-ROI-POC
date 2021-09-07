/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidationParams {

    boolean isMandatory() default false;
    ROIClientErrorCodes isMandatoryErrCode()
                           default ROIClientErrorCodes.MANDATORY_FIELD_IS_BLANK;

    String pattern() default ROIConstants.ALLOW_ALL;
    ROIClientErrorCodes misMatchErrCode()
                            default ROIClientErrorCodes.INVALID_SPECIAL_CHARACTERS_FOUND;

    int maxLength() default 0;
    ROIClientErrorCodes maxLenErrCode()
                            default ROIClientErrorCodes.FIELD_LENGTH_EXCEED_LIMIT;
}
