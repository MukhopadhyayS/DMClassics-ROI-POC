package com.mckesson.eig.roi.utils;

import java.util.Arrays;

import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.common.security.GuardedString.Accessor;

/**
 * This class will secure try to secure and prevent Heap inspection of sensitive textual information.
 */
public final class SecureStringAccessor {

    /**
     * In this version of SecureString I (Dan) added this "wrapper" accessor to wrap the 
     * accessor of the underlying third-party GuardedString class.
     * This allow us to 
     * 1) automatically zero out the provided plain text array in the constructor instead 
     * of depending on the caller
     * and 2) 
     * Swap out the underlying "GuardedString" implementation entirely without affecting our code.
     */
    public interface HylandAccessor {

        /** Hyland's accessor interface, hides the underlying implementation
         *  
         * @param clearChars returns the underlying character array, in the clear from the underlying implementation
         * @param tempString A utility string that we provide, is automatically overwritten after the call (see the
         *  DoHylandAccess method below)
         * TempString is the ONLY string that should ever be utilized, and even then it should only be used if explicitly necessary.
         * If the caller makes different strings or does other bad things in their accessor, it is out of our control.
         */
        public void access(char[] clearChars, String tempString);
    }

    /**
     * I (dan) changed this object to final. I am not sure why it wouldn't be final
     */
    private final GuardedString secretText;

    
    /**
     * Rakesh's team's constructor. Note this takes in a String, which means whatever you are 
     * trying to make secret is already in the heap. I am not sure we should do this. Leaving it for the sake of comparison
     * @param secretText
     */
//    public SecureString(String secretText) {
//        if (StringUtils.isNotBlank(secretText)) {
//            this.secretText = new GuardedString(secretText.toCharArray());
//        } else {
//            this.secretText = new GuardedString(new char[]{});
//        }
//        this.secretText.makeReadOnly();
//    }

    /**
     * My (Dan Schmidt) constructor
     * 
     * @param clearChars
     *            : Will be zeroed out after construction. The provided array
     *            can only be accessed as a string after this point.
     */
    public SecureStringAccessor(char[] clearChars) {
        // Initialized the GuardedString from the given clear characters.
        this.secretText = new GuardedString(clearChars);
        this.secretText.makeReadOnly();

        // Comments in the underlying, third-party "GuardedString" constructor say
        // "Caller is responsible for zeroing out the array of characters after the call."
        // Lets go ahead and do that for them
        Arrays.fill(clearChars, '0');
    }
    
    public SecureStringAccessor() {
        this.secretText=new GuardedString();
    }



    /**
     * My (Dan) "wrapper" accessor, as you can see it just wraps the GuardedString accessor and provides a throwaway string that 
     * we control.
     * @param hylandAccessor
     */
    public void DoHylandAccess(HylandAccessor hylandAccessor) {       
        secretText.access(new Accessor() { // "Accessor" here is GuardedString's Accessor
            @Override
            public void access(char[] somechars) {
                String tempString = "";
                hylandAccessor.access(somechars, tempString); // here we call OUR accessor (implemented by the client)
                //System.out.println("x-ing out the tempString");
                // here we x null the temp string so the client doesnt have to.
                tempString = null;
            }
        });
    }
    
    /**
     * Disposes secured encrypted string
     */
    public void dispose() {
        secretText.dispose();
    }

    /**
     * Rakesh's team provided this getter. I am not sure we should do this. It allows the user to get at the guarded string directly,
     * Avoiding all the double wrapping and protections I/We have attempted to add.
     * Returns secured string
     * @return GuardedString
     */
//    public GuardedString getSecretText() {
//        return secretText;
//    }

    // The following was the implementation from Rakesh's team
    // This puts protected text back into a string and lets it back onto the heap. And this class has no control over it at that point.
    // Should be (IMHO) eliminated, or only used when necessary
//    /**
//     * Returns clear form of secured text
//     *
//     * @return String
//     */
//    public String getClearText() {
//        //
//        StringBuilder builder = new StringBuilder();
//        this.secretText.access(builder::append);
//        return builder.toString(); // here we leave security
//    }


}
