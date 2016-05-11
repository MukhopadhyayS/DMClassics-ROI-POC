package com.mckesson.eig.roi.utils;

/**
 *
 * This class is used to generate the PassPhrase based on the MINLENGTH 
 * and supplied goodChar.
 * 
 * The MINLENGTH and the set of character that need to be generated  can be configured.
 *
 */
public final class  PassPhrase {
    
    private PassPhrase() { }
   
    /** Minimum length for a decent password&nb sp;*/ 
   public static final int  MIN_LENGTH = 8;
   
   /** Password Type is PASSWORD, In future it may add PIN **/
   public static enum TYPE { 
       PASSWORD("password");
       
       private final String _type;

       private TYPE(String type) { _type = type; }

       @Override
       public String toString() { return _type; }

   }; 

    /** The random number generator. */
   private static java.security.SecureRandom _rnd = new java.security.SecureRandom();

   /*
     * Set of characters that is valid. Must be printable, memorable, and "won't 
     * break HTML" (i.e., not ' <', '>', '&', '=', ...). or break shell commands 
     * (i.e., not ' <', '>', '$', '!', ...). I, L and O are good to leave out, 
     * as are numeric zero and one.
    */
   private static char [] _goodChar = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
       'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
       'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
       'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
       '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '@', '%', '$', '{', '}', '#'};

    /**
     *  Generate a Password object with a random password.
     *  @return String - generated password. 
     */   
    public static String generate(PassPhrase.TYPE passwordType, int length) {

        // Set default value for length and passwordType.
        if (length == 0) {
            length = MIN_LENGTH;
        }
        
        if (null == passwordType) {
            passwordType = PassPhrase.TYPE.PASSWORD;
        }

        StringBuffer sb = new StringBuffer();
        if (passwordType == PassPhrase.TYPE.PASSWORD) {
            
            for (int i = 0; i < length; i++) {
                sb.append(_goodChar[_rnd.nextInt(_goodChar.length)]);
            }
        }
        return sb.toString();
    }
 }
