/**
 * 
 */
package com.mckesson.eig.wsfw.cxf;

import java.util.Iterator;

import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * @author sahuly
 * @author Shah Mohamed.N
 * 
 * @date   Mar 31, 2010
 * This interceptor is used to remove the SOAPHeader Interceptor from the phase chain.
 */
public class RemoveHeaderBindInterceptor extends AbstractPhaseInterceptor<Message> {

	public RemoveHeaderBindInterceptor() {
		
		super(Phase.UNMARSHAL);
		addAfter(SoapHeaderRemovalInterceptor.class.getName());
		
	}

	/* (non-Javadoc)
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	public void handleMessage(Message message) {
		removeHeaderBindInterceptors(message);
	}
	
    /**
     * remove the SoapHeaderInterceptor from the incoming interceptor chain.
     * 
     * @param message
     */
    public static void removeHeaderBindInterceptors(Message message) {

        InterceptorChain chain = message.getInterceptorChain();
        for (Iterator<Interceptor< ? extends Message>> itr = chain.iterator(); itr.hasNext();) {

            Interceptor< ? extends Message> i = itr.next();
            if (i instanceof SoapHeaderInterceptor) {

                chain.remove(i);
                break;
            } 
        }
    }

}
