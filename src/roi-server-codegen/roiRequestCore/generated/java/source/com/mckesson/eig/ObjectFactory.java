
package com.mckesson.eig;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mckesson.eig package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TransactionId_QNAME = new QName("urn:eig.mckesson.com", "transactionId");
    private final static QName _AuditEventEncounter_QNAME = new QName("urn:eig.mckesson.com", "encounter");
    private final static QName _AuditEventLocation_QNAME = new QName("urn:eig.mckesson.com", "location");
    private final static QName _AuditEventComment_QNAME = new QName("urn:eig.mckesson.com", "comment");
    private final static QName _AuditEventActionCode_QNAME = new QName("urn:eig.mckesson.com", "actionCode");
    private final static QName _AuditEventMrn_QNAME = new QName("urn:eig.mckesson.com", "mrn");
    private final static QName _AuditEventObjectId_QNAME = new QName("urn:eig.mckesson.com", "objectId");
    private final static QName _AuditEventFacility_QNAME = new QName("urn:eig.mckesson.com", "facility");
    private final static QName _AuditEventWorkflowReason_QNAME = new QName("urn:eig.mckesson.com", "workflowReason");
    private final static QName _AuditEventEventEnd_QNAME = new QName("urn:eig.mckesson.com", "eventEnd");
    private final static QName _AuditEventEventStatus_QNAME = new QName("urn:eig.mckesson.com", "eventStatus");
    private final static QName _CommentCreatedDate_QNAME = new QName("urn:eig.mckesson.com", "createdDate");
    private final static QName _RequestEventModifiedDate_QNAME = new QName("urn:eig.mckesson.com", "modifiedDate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mckesson.eig
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchRequest }
     * 
     */
    public SearchRequest createSearchRequest() {
        return new SearchRequest();
    }

    /**
     * Create an instance of {@link RequestCoreSearchCriteria }
     * 
     */
    public RequestCoreSearchCriteria createRequestCoreSearchCriteria() {
        return new RequestCoreSearchCriteria();
    }

    /**
     * Create an instance of {@link RetrieveInvoicesAndAdjPayResponse }
     * 
     */
    public RetrieveInvoicesAndAdjPayResponse createRetrieveInvoicesAndAdjPayResponse() {
        return new RetrieveInvoicesAndAdjPayResponse();
    }

    /**
     * Create an instance of {@link RequestCoreChargesInvoicesList }
     * 
     */
    public RequestCoreChargesInvoicesList createRequestCoreChargesInvoicesList() {
        return new RequestCoreChargesInvoicesList();
    }

    /**
     * Create an instance of {@link CreateRequestResponse }
     * 
     */
    public CreateRequestResponse createCreateRequestResponse() {
        return new CreateRequestResponse();
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link RetrieveInvoicesAndAdjPay }
     * 
     */
    public RetrieveInvoicesAndAdjPay createRetrieveInvoicesAndAdjPay() {
        return new RetrieveInvoicesAndAdjPay();
    }

    /**
     * Create an instance of {@link CreateRequest }
     * 
     */
    public CreateRequest createCreateRequest() {
        return new CreateRequest();
    }

    /**
     * Create an instance of {@link GetEventHistory }
     * 
     */
    public GetEventHistory createGetEventHistory() {
        return new GetEventHistory();
    }

    /**
     * Create an instance of {@link SaveRequestCoreCharges }
     * 
     */
    public SaveRequestCoreCharges createSaveRequestCoreCharges() {
        return new SaveRequestCoreCharges();
    }

    /**
     * Create an instance of {@link RequestCoreCharges }
     * 
     */
    public RequestCoreCharges createRequestCoreCharges() {
        return new RequestCoreCharges();
    }

    /**
     * Create an instance of {@link RetrieveRequestResponse }
     * 
     */
    public RetrieveRequestResponse createRetrieveRequestResponse() {
        return new RetrieveRequestResponse();
    }

    /**
     * Create an instance of {@link SearchRequestResponse }
     * 
     */
    public SearchRequestResponse createSearchRequestResponse() {
        return new SearchRequestResponse();
    }

    /**
     * Create an instance of {@link RequestCoreSearchResultList }
     * 
     */
    public RequestCoreSearchResultList createRequestCoreSearchResultList() {
        return new RequestCoreSearchResultList();
    }

    /**
     * Create an instance of {@link UpdateRequestResponse }
     * 
     */
    public UpdateRequestResponse createUpdateRequestResponse() {
        return new UpdateRequestResponse();
    }

    /**
     * Create an instance of {@link AddEvent }
     * 
     */
    public AddEvent createAddEvent() {
        return new AddEvent();
    }

    /**
     * Create an instance of {@link RequestEvent }
     * 
     */
    public RequestEvent createRequestEvent() {
        return new RequestEvent();
    }

    /**
     * Create an instance of {@link GetGeneratedPassword }
     * 
     */
    public GetGeneratedPassword createGetGeneratedPassword() {
        return new GetGeneratedPassword();
    }

    /**
     * Create an instance of {@link GetGeneratedPasswordResponse }
     * 
     */
    public GetGeneratedPasswordResponse createGetGeneratedPasswordResponse() {
        return new GetGeneratedPasswordResponse();
    }

    /**
     * Create an instance of {@link SaveRequestCoreChargesResponse }
     * 
     */
    public SaveRequestCoreChargesResponse createSaveRequestCoreChargesResponse() {
        return new SaveRequestCoreChargesResponse();
    }

    /**
     * Create an instance of {@link RetrieveComments }
     * 
     */
    public RetrieveComments createRetrieveComments() {
        return new RetrieveComments();
    }

    /**
     * Create an instance of {@link AddAuditAndEvent }
     * 
     */
    public AddAuditAndEvent createAddAuditAndEvent() {
        return new AddAuditAndEvent();
    }

    /**
     * Create an instance of {@link AuditAndEventList }
     * 
     */
    public AuditAndEventList createAuditAndEventList() {
        return new AuditAndEventList();
    }

    /**
     * Create an instance of {@link RetrieveRequest }
     * 
     */
    public RetrieveRequest createRetrieveRequest() {
        return new RetrieveRequest();
    }

    /**
     * Create an instance of {@link GetEventHistoryResponse }
     * 
     */
    public GetEventHistoryResponse createGetEventHistoryResponse() {
        return new GetEventHistoryResponse();
    }

    /**
     * Create an instance of {@link RequestEvents }
     * 
     */
    public RequestEvents createRequestEvents() {
        return new RequestEvents();
    }

    /**
     * Create an instance of {@link RetrieveAllEventTypesResponse }
     * 
     */
    public RetrieveAllEventTypesResponse createRetrieveAllEventTypesResponse() {
        return new RetrieveAllEventTypesResponse();
    }

    /**
     * Create an instance of {@link EventTypes }
     * 
     */
    public EventTypes createEventTypes() {
        return new EventTypes();
    }

    /**
     * Create an instance of {@link RetrieveRequestInvoicesResponse }
     * 
     */
    public RetrieveRequestInvoicesResponse createRetrieveRequestInvoicesResponse() {
        return new RetrieveRequestInvoicesResponse();
    }

    /**
     * Create an instance of {@link RequestorInvoicesList }
     * 
     */
    public RequestorInvoicesList createRequestorInvoicesList() {
        return new RequestorInvoicesList();
    }

    /**
     * Create an instance of {@link RetrieveRequestCoreCharges }
     * 
     */
    public RetrieveRequestCoreCharges createRetrieveRequestCoreCharges() {
        return new RetrieveRequestCoreCharges();
    }

    /**
     * Create an instance of {@link RetrieveReleasedDocumentChargesByBillingTier }
     * 
     */
    public RetrieveReleasedDocumentChargesByBillingTier createRetrieveReleasedDocumentChargesByBillingTier() {
        return new RetrieveReleasedDocumentChargesByBillingTier();
    }

    /**
     * Create an instance of {@link HasSecurityRightsForReleaseResponse }
     * 
     */
    public HasSecurityRightsForReleaseResponse createHasSecurityRightsForReleaseResponse() {
        return new HasSecurityRightsForReleaseResponse();
    }

    /**
     * Create an instance of {@link AddAuditAndEventResponse }
     * 
     */
    public AddAuditAndEventResponse createAddAuditAndEventResponse() {
        return new AddAuditAndEventResponse();
    }

    /**
     * Create an instance of {@link RetrieveReleasedDocumentChargesByBillingTierResponse }
     * 
     */
    public RetrieveReleasedDocumentChargesByBillingTierResponse createRetrieveReleasedDocumentChargesByBillingTierResponse() {
        return new RetrieveReleasedDocumentChargesByBillingTierResponse();
    }

    /**
     * Create an instance of {@link RequestCoreChargesDocument }
     * 
     */
    public RequestCoreChargesDocument createRequestCoreChargesDocument() {
        return new RequestCoreChargesDocument();
    }

    /**
     * Create an instance of {@link RetrieveRequestInvoices }
     * 
     */
    public RetrieveRequestInvoices createRetrieveRequestInvoices() {
        return new RetrieveRequestInvoices();
    }

    /**
     * Create an instance of {@link HasSecurityRightsForRelease }
     * 
     */
    public HasSecurityRightsForRelease createHasSecurityRightsForRelease() {
        return new HasSecurityRightsForRelease();
    }

    /**
     * Create an instance of {@link UpdateRequest }
     * 
     */
    public UpdateRequest createUpdateRequest() {
        return new UpdateRequest();
    }

    /**
     * Create an instance of {@link GetRequestCount }
     * 
     */
    public GetRequestCount createGetRequestCount() {
        return new GetRequestCount();
    }

    /**
     * Create an instance of {@link GetRequestCountResponse }
     * 
     */
    public GetRequestCountResponse createGetRequestCountResponse() {
        return new GetRequestCountResponse();
    }

    /**
     * Create an instance of {@link RetrieveRequestCoreChargesResponse }
     * 
     */
    public RetrieveRequestCoreChargesResponse createRetrieveRequestCoreChargesResponse() {
        return new RetrieveRequestCoreChargesResponse();
    }

    /**
     * Create an instance of {@link RetrieveRequestPatient }
     * 
     */
    public RetrieveRequestPatient createRetrieveRequestPatient() {
        return new RetrieveRequestPatient();
    }

    /**
     * Create an instance of {@link SaveRequestPatientResponse }
     * 
     */
    public SaveRequestPatientResponse createSaveRequestPatientResponse() {
        return new SaveRequestPatientResponse();
    }

    /**
     * Create an instance of {@link RequestPatientsList }
     * 
     */
    public RequestPatientsList createRequestPatientsList() {
        return new RequestPatientsList();
    }

    /**
     * Create an instance of {@link RetrieveRequestPatientResponse }
     * 
     */
    public RetrieveRequestPatientResponse createRetrieveRequestPatientResponse() {
        return new RetrieveRequestPatientResponse();
    }

    /**
     * Create an instance of {@link DeleteRequestResponse }
     * 
     */
    public DeleteRequestResponse createDeleteRequestResponse() {
        return new DeleteRequestResponse();
    }

    /**
     * Create an instance of {@link RetrieveAllEventTypes }
     * 
     */
    public RetrieveAllEventTypes createRetrieveAllEventTypes() {
        return new RetrieveAllEventTypes();
    }

    /**
     * Create an instance of {@link AddEventResponse }
     * 
     */
    public AddEventResponse createAddEventResponse() {
        return new AddEventResponse();
    }

    /**
     * Create an instance of {@link SaveRequestPatient }
     * 
     */
    public SaveRequestPatient createSaveRequestPatient() {
        return new SaveRequestPatient();
    }

    /**
     * Create an instance of {@link SaveRequestPatientList }
     * 
     */
    public SaveRequestPatientList createSaveRequestPatientList() {
        return new SaveRequestPatientList();
    }

    /**
     * Create an instance of {@link DeleteRequest }
     * 
     */
    public DeleteRequest createDeleteRequest() {
        return new DeleteRequest();
    }

    /**
     * Create an instance of {@link RetrieveCommentsResponse }
     * 
     */
    public RetrieveCommentsResponse createRetrieveCommentsResponse() {
        return new RetrieveCommentsResponse();
    }

    /**
     * Create an instance of {@link Comments }
     * 
     */
    public Comments createComments() {
        return new Comments();
    }

    /**
     * Create an instance of {@link PaginationData }
     * 
     */
    public PaginationData createPaginationData() {
        return new PaginationData();
    }

    /**
     * Create an instance of {@link RequestCoreChargesBilling }
     * 
     */
    public RequestCoreChargesBilling createRequestCoreChargesBilling() {
        return new RequestCoreChargesBilling();
    }

    /**
     * Create an instance of {@link RequestCoreChargesShipping }
     * 
     */
    public RequestCoreChargesShipping createRequestCoreChargesShipping() {
        return new RequestCoreChargesShipping();
    }

    /**
     * Create an instance of {@link RequestSupplementalAttachment }
     * 
     */
    public RequestSupplementalAttachment createRequestSupplementalAttachment() {
        return new RequestSupplementalAttachment();
    }

    /**
     * Create an instance of {@link ROILoV }
     * 
     */
    public ROILoV createROILoV() {
        return new ROILoV();
    }

    /**
     * Create an instance of {@link RequestorAdjustmentsPayments }
     * 
     */
    public RequestorAdjustmentsPayments createRequestorAdjustmentsPayments() {
        return new RequestorAdjustmentsPayments();
    }

    /**
     * Create an instance of {@link PageListMap }
     * 
     */
    public PageListMap createPageListMap() {
        return new PageListMap();
    }

    /**
     * Create an instance of {@link DeletePatientList }
     * 
     */
    public DeletePatientList createDeletePatientList() {
        return new DeletePatientList();
    }

    /**
     * Create an instance of {@link RequestorInvoice }
     * 
     */
    public RequestorInvoice createRequestorInvoice() {
        return new RequestorInvoice();
    }

    /**
     * Create an instance of {@link SalesTaxReason }
     * 
     */
    public SalesTaxReason createSalesTaxReason() {
        return new SalesTaxReason();
    }

    /**
     * Create an instance of {@link RequestCoreChargesInvoice }
     * 
     */
    public RequestCoreChargesInvoice createRequestCoreChargesInvoice() {
        return new RequestCoreChargesInvoice();
    }

    /**
     * Create an instance of {@link AuditEvent }
     * 
     */
    public AuditEvent createAuditEvent() {
        return new AuditEvent();
    }

    /**
     * Create an instance of {@link Requestor }
     * 
     */
    public Requestor createRequestor() {
        return new Requestor();
    }

    /**
     * Create an instance of {@link RequestCoreChargesFee }
     * 
     */
    public RequestCoreChargesFee createRequestCoreChargesFee() {
        return new RequestCoreChargesFee();
    }

    /**
     * Create an instance of {@link SalesTaxSummary }
     * 
     */
    public SalesTaxSummary createSalesTaxSummary() {
        return new SalesTaxSummary();
    }

    /**
     * Create an instance of {@link Comment }
     * 
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link RequestSupplementalDocument }
     * 
     */
    public RequestSupplementalDocument createRequestSupplementalDocument() {
        return new RequestSupplementalDocument();
    }

    /**
     * Create an instance of {@link RequestDocument }
     * 
     */
    public RequestDocument createRequestDocument() {
        return new RequestDocument();
    }

    /**
     * Create an instance of {@link RequestPage }
     * 
     */
    public RequestPage createRequestPage() {
        return new RequestPage();
    }

    /**
     * Create an instance of {@link RequestCoreDeliveryChargesAdjustmentPayment }
     * 
     */
    public RequestCoreDeliveryChargesAdjustmentPayment createRequestCoreDeliveryChargesAdjustmentPayment() {
        return new RequestCoreDeliveryChargesAdjustmentPayment();
    }

    /**
     * Create an instance of {@link RequestPatient }
     * 
     */
    public RequestPatient createRequestPatient() {
        return new RequestPatient();
    }

    /**
     * Create an instance of {@link RequestVersion }
     * 
     */
    public RequestVersion createRequestVersion() {
        return new RequestVersion();
    }

    /**
     * Create an instance of {@link RequestEncounter }
     * 
     */
    public RequestEncounter createRequestEncounter() {
        return new RequestEncounter();
    }

    /**
     * Create an instance of {@link RequestCoreSearchResult }
     * 
     */
    public RequestCoreSearchResult createRequestCoreSearchResult() {
        return new RequestCoreSearchResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "transactionId")
    public JAXBElement<Long> createTransactionId(Long value) {
        return new JAXBElement<Long>(_TransactionId_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "encounter", scope = AuditEvent.class)
    public JAXBElement<String> createAuditEventEncounter(String value) {
        return new JAXBElement<String>(_AuditEventEncounter_QNAME, String.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "location", scope = AuditEvent.class)
    public JAXBElement<String> createAuditEventLocation(String value) {
        return new JAXBElement<String>(_AuditEventLocation_QNAME, String.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "comment", scope = AuditEvent.class)
    public JAXBElement<String> createAuditEventComment(String value) {
        return new JAXBElement<String>(_AuditEventComment_QNAME, String.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "actionCode", scope = AuditEvent.class)
    public JAXBElement<String> createAuditEventActionCode(String value) {
        return new JAXBElement<String>(_AuditEventActionCode_QNAME, String.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "mrn", scope = AuditEvent.class)
    public JAXBElement<String> createAuditEventMrn(String value) {
        return new JAXBElement<String>(_AuditEventMrn_QNAME, String.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "objectId", scope = AuditEvent.class)
    public JAXBElement<Long> createAuditEventObjectId(Long value) {
        return new JAXBElement<Long>(_AuditEventObjectId_QNAME, Long.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "facility", scope = AuditEvent.class)
    public JAXBElement<String> createAuditEventFacility(String value) {
        return new JAXBElement<String>(_AuditEventFacility_QNAME, String.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "workflowReason", scope = AuditEvent.class)
    public JAXBElement<String> createAuditEventWorkflowReason(String value) {
        return new JAXBElement<String>(_AuditEventWorkflowReason_QNAME, String.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "eventEnd", scope = AuditEvent.class)
    public JAXBElement<XMLGregorianCalendar> createAuditEventEventEnd(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AuditEventEventEnd_QNAME, XMLGregorianCalendar.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "eventStatus", scope = AuditEvent.class)
    public JAXBElement<Long> createAuditEventEventStatus(Long value) {
        return new JAXBElement<Long>(_AuditEventEventStatus_QNAME, Long.class, AuditEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "createdDate", scope = Comment.class)
    public JAXBElement<XMLGregorianCalendar> createCommentCreatedDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CommentCreatedDate_QNAME, XMLGregorianCalendar.class, Comment.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "modifiedDate", scope = RequestEvent.class)
    public JAXBElement<XMLGregorianCalendar> createRequestEventModifiedDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RequestEventModifiedDate_QNAME, XMLGregorianCalendar.class, RequestEvent.class, value);
    }

}
