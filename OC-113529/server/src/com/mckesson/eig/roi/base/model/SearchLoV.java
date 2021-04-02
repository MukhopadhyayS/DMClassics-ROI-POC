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

package com.mckesson.eig.roi.base.model;

import java.io.Serializable;



/**
 * @author OFS
 * @date   Sep 30, 2008
 * @since  HPF 13.1 [ROI]; Sep 18, 2008
 */
public class SearchLoV
extends ROILoV
implements Serializable {

    private String _dataType;
    private String _condition;
    private String _valueTo;
    public enum CONDITION {

        Equal       ("="),
        GreaterThan (">"),
        LessThan    ("<"),
        Like        ("like"),
        In          ("in"),
        Between     ("between"),
        AtLeast     (">="),
        AtMost      ("<=");

        private final String _condition;

        private CONDITION(String condition) { _condition = condition; }

        @Override
        public String toString() { return _condition; }
    }

    public enum DATATYPE {

        Integer     ("int"),
        Double      ("money"),
        Boolean     ("bit"),
        String      ("varchar"),
        Date        ("datetime");

        private final String _dataType;

        private DATATYPE(String dataType) { _dataType = dataType; }

        @Override
        public String toString() { return _dataType; }
    }

    public String getDataType() { return _dataType; }
    public void setDataType(String type) {

        if (type == null) {
            _dataType = DATATYPE.String.toString();
        } else {
            _dataType =  Enum.valueOf(DATATYPE.class, type).toString();
        }
    }

    public String getCondition() { return _condition; }
    public void setCondition(String condition) {

        if (condition == null) {
            _condition = CONDITION.Like.toString();
        } else {
            _condition = Enum.valueOf(CONDITION.class, condition).toString();
        }
    }

    public String getValueTo() { return _valueTo; }
    public void setValueTo(String to) { _valueTo = to; }

}
