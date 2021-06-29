package com.mckesson.eig.roi.utils;


/**
 * The ROI - Classics Unit testing framework is not running locally, for unknown reasons
 * I Just wrote the simple little main method class to examine this
 * @author dschmidt
 * 
 * The following class as coded produces this output:
 * 
        foo array after securing is 000 {the act of creating the SecureStringDan goes ahead and zeros out the array for the caller}
        The secure string reported as an char array within the accessor is foo
        Making a string of the array also gives foo
        x-ing out the tempString {this line is from a system.out i put in my class for reference, see line 84 in SecureStringDan
 *
 */
public class SecureStringAccessorTestMain {

    public static void main(String[] args) {
        
        // notice this was never a string
        char[] bar = {'f', 'o', 'o'};
        SecureStringAccessor secureString= new SecureStringAccessor(bar);
      
        // as you can see, once we guard it, it is automatically zeroed out
        System.out.print("foo array after securing is ");
        for (int i = 0; i < bar.length;i++) {
            System.out.print(bar[i]);
        }
      
        // This is how I would expect the string to be used
        secureString.DoHylandAccess(new SecureStringAccessor.HylandAccessor() {
       
             @Override
             public void access(char[] passwdChars, String tempString) {
                 
                 // This is the best way to use it, never a string
                 System.out.print("\nThe secure string reported as an char array" +
                 " within the accessor is ");
                 for (int i = 0; i < passwdChars.length;i++) {
                     System.out.print(passwdChars[i]);
                 }
            
                 // Utilizing the temp string here, it will be auto replaced with XXXXXXXXXXXXXXX after usage
                 // This should help, but does not eliminate heap leakage
                 tempString = new String(passwdChars);
                 System.out.println("\nMaking a string of the array also gives " + tempString);
             }
          });
    } // main
}
