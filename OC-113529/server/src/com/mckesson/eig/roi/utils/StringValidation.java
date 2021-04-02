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

package com.mckesson.eig.roi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidation {
    
    public static final String  BALANCE_DUE_OPERATOR_VALIDATOR= "\\<|>|=";
    public static final String  BALANCE_DUE_VALIDATOR= "^-?[0-9]+.?[0-9]+$";
    
    
    public static boolean validateBalanceDuePerator(String arg){
        return validate(arg, BALANCE_DUE_OPERATOR_VALIDATOR);
    } 
    
    public static boolean isNumeric(String arg){
        
        return validate(arg, BALANCE_DUE_VALIDATOR);        
        
    }
    
    private static boolean validate(String arg, String regex){
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(arg); 
        return matcher.matches();
      
    } 


}
