package com.mckesson.eig.roi.utils;

import org.owasp.esapi.codecs.Codec;

public class MSSQLCodec extends Codec {
    
    public String encodeCharacter( char[] immune, Character c ) {
        if ( c.charValue() == '\'' )
            return "\'";
        return ""+c;
    }
}
