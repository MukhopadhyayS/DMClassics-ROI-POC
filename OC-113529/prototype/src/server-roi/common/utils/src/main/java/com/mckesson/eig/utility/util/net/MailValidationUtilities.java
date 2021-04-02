package com.mckesson.eig.utility.util.net;
/**
 * Provide web related helper utilities like validating an email address, etc
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mckesson.eig.utility.util.StringUtilities;

public final class MailValidationUtilities {
    private static final Pattern EMAIL_ID_PATTERN = 
        Pattern.compile("^[A-Za-z0-9._%-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");

    /**
     * Constant used to hold the MAX_LOCAL_EMAIL_ID_LENGTH.
     */
    public static final int MAX_EMAIL_SENDER_LENGTH = 80;

    /**
     * Constant used to hold the MAX_LOCAL_EMAIL_ID_LENGTH.
     */
    public static final int MAX_LOCAL_EMAIL_ID_LENGTH = 64;
    /**
     * Constant used to hold the MAX_DOMAIN_EMAIL_ID_LENGTH.
     */
    public static final int MAX_DOMAIN_EMAIL_ID_LENGTH = 255;
    /**
     * Constant used to hold the MAX_EMAIL_ID_LENGTH.
     */
    public static final int MAX_EMAIL_ID_LENGTH = 320;
    
    /**
     * Constant used to hold the MIN_PORT_VALUE.
     */
    public static final int MIN_PORT_VALUE = 0; 
    /**
     * Constant used to hold the MAX_PORT_VALUE.
     */
    public static final int MAX_PORT_VALUE = 65535;
    /**
     * Constant used to hold the MAX_HOST_NAMEIP_LENGTH.
     */
    public static final int MAX_HOST_NAMEIP_LENGTH = 255;
    
    private MailValidationUtilities() { };
    
    public static boolean isValidMailServerName(String serverName) {
        if (StringUtilities.isEmpty(serverName)) {
            return false;
        }
        
        if (serverName.length() > MAX_HOST_NAMEIP_LENGTH) {
            return false;
        }
        return true;
    }
    
    public static boolean isValidMailServerPort(int serverPort) {
        if (serverPort < MIN_PORT_VALUE || serverPort > MAX_PORT_VALUE) {
            return false;
        }
 
        return true;
    }
    /**
     * A Helper method to validate an E-Mail ID.
     * 
     * @param mailId - String - E-mail ID to be validated.
     * 
     * @return A boolean value that specifies the result of the validation
     */
    public static boolean isValidMailId(String mailId) {
        //not empty or containing a space
        if (StringUtilities.isEmpty(mailId) || StringUtilities.exists(mailId, " ")) {
            return false;
        }
        //less than maximum email address size
        if (mailId.length() > MAX_EMAIL_ID_LENGTH) {
            return false;
        }
        
        //contains an @
        int localEndIndex = mailId.indexOf("@");
        if (localEndIndex == -1) {
            return false;
        }
        //only a single @
        if (mailId.indexOf("@", localEndIndex + 1) != -1) {
            return false;           
        }
        
        //LOCAL length is OK
        if (localEndIndex > MAX_LOCAL_EMAIL_ID_LENGTH) {
            return false;           
        }
   
        //DOMAIN length is OK
        if ((mailId.length() - (localEndIndex + 1)) > MAX_DOMAIN_EMAIL_ID_LENGTH) {
            return false;           
        }

        //email address adheres to our pattern
        Matcher matcher = EMAIL_ID_PATTERN.matcher(mailId);
        return matcher.matches();
    }
 }
