package com.mckesson.eig.roi.utils;

import com.mckesson.eig.utility.util.SpringUtilities;

public final class SpringUtil {
    
    private SpringUtil() { }
    
    public static Object getObjectFromSpring(String beanName) {
        
    	try {
    	    
    	    Object o = SpringUtilities.getInstance().getBeanFactory()
    	                                            .getBean(beanName);
    	    return o;
    	} catch (Exception e) {
    	    return null;
    	}
    }

}
