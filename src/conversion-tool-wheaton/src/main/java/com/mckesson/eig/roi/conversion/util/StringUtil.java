/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.roi.conversion.util;

/**
 * @author bhanu
 *
 */
public class StringUtil {

	/**
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}
	
	/**
	 * This method will check string array is empty (or) not.
	 * @param stringArr
	 * @return
	 */
	public static boolean isEmpty(String[] stringArr) {
		return (stringArr == null || stringArr.length == 0);
	}
	
	/**
     * Method to get the String value in the given position
     * from the object array and return it as a String value to append
     * in text file
     *
     * @param values
     * @param position
     * @return
     */
	public static String getStringTxt(Object[] values, int position) {

        String value = getStringValueByIndex(values, position);

        if (value == null) { return ""; }
        
        String delimeter = "\"";
        int index = value.indexOf(delimeter);
        String data =  index == -1 ? value
                                   : (index > 0) ? value
                                                  : value.substring(index + 1,
                                                		  value.lastIndexOf(delimeter));
        return data.trim();
    }
	
	/**
     * Method to get the String value in the given position
     * from the object array
     *
     * @param values
     * @param position
     * @return
     */
	public static String getStringValueByIndex(Object[] values, int position) {
        return (String) values[position];
	}
}
