package com.mckesson.eig.audit.local;

import java.io.StringWriter;
import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.mckesson.eig.audit.AuditException;
import com.mckesson.eig.audit.Auditable;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.audit.model.AuditEventList;
//import com.mckesson.eig.utility.log.Log;
//import com.mckesson.eig.utility.log.LogContext;
//import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.transaction.TransactionId;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.EIGConstants;
import com.mckesson.eig.wsfw.jms.MessagePoster;
import com.mckesson.eig.wsfw.session.WsSession;
import com.mckesson.dm.core.common.logging.OCLogger;

@WebService(
name              = "AuditPortType_v1_0",
portName          = "audit_v1_0",
serviceName       = "AuditService_v1_0",
targetNamespace   = "http://eig.mckesson.com/wsdl/audit-v1",
endpointInterface = "com.mckesson.eig.audit.Auditable")
public class AuditLocalService implements Auditable {

    private static final OCLogger LOG = new OCLogger(AuditLocalService.class);
    
    private JAXBContext _jaxbContext;
    
    private static final String SOAP_ENVELOPE_BEGIN = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
            + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
            + "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";
    
    private static final String SOAP_HEADER_BEGIN = "<soap:Header>" 
    		+ "<TransactionId xmlns=\"" + EIGConstants.TYPE_NS_V1 + "\">";
    
    private static final String SOAP_HEADER_USER = "</TransactionId>" 
            + "<Username xmlns=\"" + EIGConstants.TYPE_NS_V1 + "\">";
    
    private static final String SOAP_HEADER_CLIENT_IP = "</Username>"
            + "<ClientIP xmlns=\"" + EIGConstants.TYPE_NS_V1 + "\">";
    
    private static final String SOAP_HEADER_END = "</ClientIP>"
    		 + "</soap:Header><soap:Body>"
             + " <createAuditEntry>";

    private static final String SOAP_ENVELOPE_END
            = "</createAuditEntry></soap:Body></soap:Envelope>";
    
    public AuditLocalService() {
        initJAXBContext();
    }

    public boolean createAuditEntry(AuditEvent auditEvent) {

        try {

            String ipAddress = (String) WsSession.getSessionData(WsSession.CLIENT_IP);
            auditEvent.setLocation(ipAddress);
            writeMessageToQueue(auditEvent);
            return true;
        } catch (Exception e) {

            LOG.error("Server side auditing has failed, please notify "
                    + "you system administrator.", e);
            throw new AuditException(e);
        }
    }
    
    public boolean createAuditEntryList(AuditEventList auditEventList) {

        try {
        	List<AuditEvent> auditEvents = auditEventList.getAuditEvent();
            
			if (null != auditEvents && !auditEvents.isEmpty()) {
			    
			    for (AuditEvent audit : auditEvents) {
			        createAuditEntry(audit);
			    }  
			}
			 return true;
         } catch (Exception e) {

            LOG.error("Server side auditing has failed, please notify "
                    + "you system administrator.", e);
            throw new AuditException(e);
        }
    } 	

    private void writeMessageToQueue(AuditEvent auditEvent) throws Exception {
        getMessagePoster().sendTextMessage(buildSoapEnvelope(auditEvent));
    }

    protected String buildSoapEnvelope(AuditEvent auditEvent) throws Exception {
        
        String transID = ""; //TODO: add MDC to OCLogger.
                // ((TransactionId) LogContext.get("transactionid")).getValue();
        String userName = (String) WsSession.getSessionData(WsSession.USER_NAME);
        
        return SOAP_ENVELOPE_BEGIN + SOAP_HEADER_BEGIN + transID + SOAP_HEADER_USER 
            + userName + SOAP_HEADER_CLIENT_IP + auditEvent.getLocation() + SOAP_HEADER_END 
            + marshallObject(auditEvent) + SOAP_ENVELOPE_END;
    }

    private String marshallObject(AuditEvent auditEvent) throws Exception {
        
        StringWriter stringWriter = new StringWriter();
        Marshaller m = _jaxbContext.createMarshaller();
        m.marshal(auditEvent, stringWriter);
        String message = stringWriter.toString();
        message = stripXMLDecl(message);
        return message;
    }

    private String stripXMLDecl(String message) {

        int startIndex = message.indexOf("?>");

        if (startIndex != -1) {
            message = message.substring(startIndex + 2, message.length());
        }
        return message;
    }

    private MessagePoster getMessagePoster() {

        return (MessagePoster) SpringUtilities.getInstance()
                                              .getBeanFactory()
                                              .getBean("eig_auditq_config");
    }
    
    private void initJAXBContext() {

        try {
            _jaxbContext = JAXBContext.newInstance(AuditEvent.class);
        } catch (JAXBException e) {

            LOG.error("JAXBContext Initialization Failed", e);
            throw new AuditException(e);
        }
    }
}
