package com.mckesson.eig.utils;


import com.mckesson.dm.core.common.logging.OCLogger;

import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.dm.core.common.util.sanitize.codecs.HtmlCustomCodec;

public class SecurityUtilities {

	private static final String CRLF_REGEX = "[\\n\\r]+";
	private static final String NAME_REGEX = "([a-zA-Z_\\-\\'\\:\\s0-9\\.\\,]+)";
	private static final String FILE_PATH_REGEX = "(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.\\\\\\$]+)";
	private static final String FILE_NAME_REGEX = "^(?!^(PRN|AUX|CLOCK\\$|NUL|CON|COM\\d|LPT\\d|\\..*)(\\..+)?$)[^\\x00-\\x1f\\\\?*:\\\"\\;|/]+$";
	private static HtmlCustomCodec htmlCustomCodec = new HtmlCustomCodec();
	
    final static OCLogger Log = new OCLogger(SecurityUtilities.class);
    
	/**
	 * This method used to remove or replace the CRLF for a given string with replaceString
	 * @param str
	 * @param remove
	 * @param replaceString
	 * @return
	 */
	public static String cleanCRLF(String str, boolean remove, String replaceString) {
		String sanitizedString = str;
		if (StringUtilities.hasContent(str)) {
			if (remove) {
				sanitizedString = str.replaceAll(CRLF_REGEX, "");
			} else {
				sanitizedString = str.replaceAll(CRLF_REGEX, replaceString);
			}
		}
		return sanitizedString;
	}
	/**
	 * This method replaces CRLF for a given string with replaceString
	 * @param str
	 * @param remove
	 * @return
	 */
	public static String cleanCRLF(String str, String replaceString){
		return cleanCRLF(str, false, replaceString);
	}
	
	/**
	 * This method removes the CRLF for a given string
	 * @param str
	 * @return
	 */
	public static String removeCRLF(String str){
		return cleanCRLF(str, true, "");
	}
	
	/**
	 * This method encodes the given string
	 * @param str
	 * @return
	 */
	public static String encodeForHTML(String str){
		String sanitizedString;
		sanitizedString =  htmlCustomCodec.encode(str);
		return sanitizedString;
	}
    
	//Method to sanitize and encode log to protect against CRLF injection 
	/**
	 * This method replaces CRLF of the given string with "_" and then encodes
	 * @param str
	 * @return
	 */
	public static String cleanEncodeForLogs(String str) {
		String sanitizedString;
		String replaceString = "_";
		sanitizedString = cleanCRLF(str, replaceString);
		sanitizedString = encodeForHTML(sanitizedString);
		return sanitizedString;
	}
	
}
