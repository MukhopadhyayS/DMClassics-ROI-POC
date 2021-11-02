
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
    private final static QName _RequestorSearchCriteriaDob_QNAME = new QName("urn:eig.mckesson.com", "dob");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mckesson.eig
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckDuplicateRequestorName }
     * 
     */
    public CheckDuplicateRequestorName createCheckDuplicateRequestorName() {
        return new CheckDuplicateRequestorName();
    }

    /**
     * Create an instance of {@link CreateRequestorRefundResponse }
     * 
     */
    public CreateRequestorRefundResponse createCreateRequestorRefundResponse() {
        return new CreateRequestorRefundResponse();
    }

    /**
     * Create an instance of {@link DocInfoList }
     * 
     */
    public DocInfoList createDocInfoList() {
        return new DocInfoList();
    }

    /**
     * Create an instance of {@link RetrieveUnappliedAmountDetails }
     * 
     */
    public RetrieveUnappliedAmountDetails createRetrieveUnappliedAmountDetails() {
        return new RetrieveUnappliedAmountDetails();
    }

    /**
     * Create an instance of {@link DeleteRequestorPayment }
     * 
     */
    public DeleteRequestorPayment createDeleteRequestorPayment() {
        return new DeleteRequestorPayment();
    }

    /**
     * Create an instance of {@link CreateRequestor }
     * 
     */
    public CreateRequestor createCreateRequestor() {
        return new CreateRequestor();
    }

    /**
     * Create an instance of {@link Requestor }
     * 
     */
    public Requestor createRequestor() {
        return new Requestor();
    }

    /**
     * Create an instance of {@link ViewRequestorDetailsResponse }
     * 
     */
    public ViewRequestorDetailsResponse createViewRequestorDetailsResponse() {
        return new ViewRequestorDetailsResponse();
    }

    /**
     * Create an instance of {@link DocInfo }
     * 
     */
    public DocInfo createDocInfo() {
        return new DocInfo();
    }

    /**
     * Create an instance of {@link GenerateRequestorStatement }
     * 
     */
    public GenerateRequestorStatement createGenerateRequestorStatement() {
        return new GenerateRequestorStatement();
    }

    /**
     * Create an instance of {@link RequestorStatementCriteria }
     * 
     */
    public RequestorStatementCriteria createRequestorStatementCriteria() {
        return new RequestorStatementCriteria();
    }

    /**
     * Create an instance of {@link FindRequestor }
     * 
     */
    public FindRequestor createFindRequestor() {
        return new FindRequestor();
    }

    /**
     * Create an instance of {@link RequestorSearchCriteria }
     * 
     */
    public RequestorSearchCriteria createRequestorSearchCriteria() {
        return new RequestorSearchCriteria();
    }

    /**
     * Create an instance of {@link SaveAdjustmentInfo }
     * 
     */
    public SaveAdjustmentInfo createSaveAdjustmentInfo() {
        return new SaveAdjustmentInfo();
    }

    /**
     * Create an instance of {@link AdjustmentInfo }
     * 
     */
    public AdjustmentInfo createAdjustmentInfo() {
        return new AdjustmentInfo();
    }

    /**
     * Create an instance of {@link RetrieveRequestorLetterHistoryResponse }
     * 
     */
    public RetrieveRequestorLetterHistoryResponse createRetrieveRequestorLetterHistoryResponse() {
        return new RetrieveRequestorLetterHistoryResponse();
    }

    /**
     * Create an instance of {@link RequestorLetterHistoryList }
     * 
     */
    public RequestorLetterHistoryList createRequestorLetterHistoryList() {
        return new RequestorLetterHistoryList();
    }

    /**
     * Create an instance of {@link RetrieveRequestorInvoicesResponse }
     * 
     */
    public RetrieveRequestorInvoicesResponse createRetrieveRequestorInvoicesResponse() {
        return new RetrieveRequestorInvoicesResponse();
    }

    /**
     * Create an instance of {@link RequestorInvoicesList }
     * 
     */
    public RequestorInvoicesList createRequestorInvoicesList() {
        return new RequestorInvoicesList();
    }

    /**
     * Create an instance of {@link CreateRequestorResponse }
     * 
     */
    public CreateRequestorResponse createCreateRequestorResponse() {
        return new CreateRequestorResponse();
    }

    /**
     * Create an instance of {@link RetrieveRequestor }
     * 
     */
    public RetrieveRequestor createRetrieveRequestor() {
        return new RetrieveRequestor();
    }

    /**
     * Create an instance of {@link ViewRequestorLetterResponse }
     * 
     */
    public ViewRequestorLetterResponse createViewRequestorLetterResponse() {
        return new ViewRequestorLetterResponse();
    }

    /**
     * Create an instance of {@link CreateRequestorRefund }
     * 
     */
    public CreateRequestorRefund createCreateRequestorRefund() {
        return new CreateRequestorRefund();
    }

    /**
     * Create an instance of {@link RequestorRefund }
     * 
     */
    public RequestorRefund createRequestorRefund() {
        return new RequestorRefund();
    }

    /**
     * Create an instance of {@link RetrieveUnappliedAmountDetailsResponse }
     * 
     */
    public RetrieveUnappliedAmountDetailsResponse createRetrieveUnappliedAmountDetailsResponse() {
        return new RetrieveUnappliedAmountDetailsResponse();
    }

    /**
     * Create an instance of {@link RequestorUnappliedAmountDetailsList }
     * 
     */
    public RequestorUnappliedAmountDetailsList createRequestorUnappliedAmountDetailsList() {
        return new RequestorUnappliedAmountDetailsList();
    }

    /**
     * Create an instance of {@link RetrieveRequestorResponse }
     * 
     */
    public RetrieveRequestorResponse createRetrieveRequestorResponse() {
        return new RetrieveRequestorResponse();
    }

    /**
     * Create an instance of {@link CreateRequestorStatement }
     * 
     */
    public CreateRequestorStatement createCreateRequestorStatement() {
        return new CreateRequestorStatement();
    }

    /**
     * Create an instance of {@link DeleteRequestor }
     * 
     */
    public DeleteRequestor createDeleteRequestor() {
        return new DeleteRequestor();
    }

    /**
     * Create an instance of {@link CreateRequestorLetterEntry }
     * 
     */
    public CreateRequestorLetterEntry createCreateRequestorLetterEntry() {
        return new CreateRequestorLetterEntry();
    }

    /**
     * Create an instance of {@link RegeneratedInvoiceInfo }
     * 
     */
    public RegeneratedInvoiceInfo createRegeneratedInvoiceInfo() {
        return new RegeneratedInvoiceInfo();
    }

    /**
     * Create an instance of {@link ViewRequestorRefundResponse }
     * 
     */
    public ViewRequestorRefundResponse createViewRequestorRefundResponse() {
        return new ViewRequestorRefundResponse();
    }

    /**
     * Create an instance of {@link UpdateRequestorPayment }
     * 
     */
    public UpdateRequestorPayment createUpdateRequestorPayment() {
        return new UpdateRequestorPayment();
    }

    /**
     * Create an instance of {@link RequestorPaymentList }
     * 
     */
    public RequestorPaymentList createRequestorPaymentList() {
        return new RequestorPaymentList();
    }

    /**
     * Create an instance of {@link RetrieveAdjustmentInfoByAdjustmentIdResponse }
     * 
     */
    public RetrieveAdjustmentInfoByAdjustmentIdResponse createRetrieveAdjustmentInfoByAdjustmentIdResponse() {
        return new RetrieveAdjustmentInfoByAdjustmentIdResponse();
    }

    /**
     * Create an instance of {@link CreateRequestorPaymentResponse }
     * 
     */
    public CreateRequestorPaymentResponse createCreateRequestorPaymentResponse() {
        return new CreateRequestorPaymentResponse();
    }

    /**
     * Create an instance of {@link RetrieveRequestorPastInvoices }
     * 
     */
    public RetrieveRequestorPastInvoices createRetrieveRequestorPastInvoices() {
        return new RetrieveRequestorPastInvoices();
    }

    /**
     * Create an instance of {@link ViewRequestorLetter }
     * 
     */
    public ViewRequestorLetter createViewRequestorLetter() {
        return new ViewRequestorLetter();
    }

    /**
     * Create an instance of {@link CreateRequestorLetterEntryResponse }
     * 
     */
    public CreateRequestorLetterEntryResponse createCreateRequestorLetterEntryResponse() {
        return new CreateRequestorLetterEntryResponse();
    }

    /**
     * Create an instance of {@link RequestorLetterHistory }
     * 
     */
    public RequestorLetterHistory createRequestorLetterHistory() {
        return new RequestorLetterHistory();
    }

    /**
     * Create an instance of {@link CreateRequestorStatementResponse }
     * 
     */
    public CreateRequestorStatementResponse createCreateRequestorStatementResponse() {
        return new CreateRequestorStatementResponse();
    }

    /**
     * Create an instance of {@link CheckDuplicateRequestorNameResponse }
     * 
     */
    public CheckDuplicateRequestorNameResponse createCheckDuplicateRequestorNameResponse() {
        return new CheckDuplicateRequestorNameResponse();
    }

    /**
     * Create an instance of {@link RetrieveAdjustmentInfo }
     * 
     */
    public RetrieveAdjustmentInfo createRetrieveAdjustmentInfo() {
        return new RetrieveAdjustmentInfo();
    }

    /**
     * Create an instance of {@link UpdateRequestorPaymentResponse }
     * 
     */
    public UpdateRequestorPaymentResponse createUpdateRequestorPaymentResponse() {
        return new UpdateRequestorPaymentResponse();
    }

    /**
     * Create an instance of {@link ViewRequestorDetails }
     * 
     */
    public ViewRequestorDetails createViewRequestorDetails() {
        return new ViewRequestorDetails();
    }

    /**
     * Create an instance of {@link DeleteRequestorResponse }
     * 
     */
    public DeleteRequestorResponse createDeleteRequestorResponse() {
        return new DeleteRequestorResponse();
    }

    /**
     * Create an instance of {@link UpdateRequestor }
     * 
     */
    public UpdateRequestor createUpdateRequestor() {
        return new UpdateRequestor();
    }

    /**
     * Create an instance of {@link CreateRequestorPayment }
     * 
     */
    public CreateRequestorPayment createCreateRequestorPayment() {
        return new CreateRequestorPayment();
    }

    /**
     * Create an instance of {@link SearchMatchingRequestors }
     * 
     */
    public SearchMatchingRequestors createSearchMatchingRequestors() {
        return new SearchMatchingRequestors();
    }

    /**
     * Create an instance of {@link MatchCriteriaList }
     * 
     */
    public MatchCriteriaList createMatchCriteriaList() {
        return new MatchCriteriaList();
    }

    /**
     * Create an instance of {@link RetrieveRequestorInvoices }
     * 
     */
    public RetrieveRequestorInvoices createRetrieveRequestorInvoices() {
        return new RetrieveRequestorInvoices();
    }

    /**
     * Create an instance of {@link RetrieveAdjustmentInfoByAdjustmentId }
     * 
     */
    public RetrieveAdjustmentInfoByAdjustmentId createRetrieveAdjustmentInfoByAdjustmentId() {
        return new RetrieveAdjustmentInfoByAdjustmentId();
    }

    /**
     * Create an instance of {@link RetrieveRequestorLetterHistory }
     * 
     */
    public RetrieveRequestorLetterHistory createRetrieveRequestorLetterHistory() {
        return new RetrieveRequestorLetterHistory();
    }

    /**
     * Create an instance of {@link RetrieveAdjustmentInfoResponse }
     * 
     */
    public RetrieveAdjustmentInfoResponse createRetrieveAdjustmentInfoResponse() {
        return new RetrieveAdjustmentInfoResponse();
    }

    /**
     * Create an instance of {@link SaveAdjustmentInfoResponse }
     * 
     */
    public SaveAdjustmentInfoResponse createSaveAdjustmentInfoResponse() {
        return new SaveAdjustmentInfoResponse();
    }

    /**
     * Create an instance of {@link GenerateRequestorStatementResponse }
     * 
     */
    public GenerateRequestorStatementResponse createGenerateRequestorStatementResponse() {
        return new GenerateRequestorStatementResponse();
    }

    /**
     * Create an instance of {@link FindRequestorResponse }
     * 
     */
    public FindRequestorResponse createFindRequestorResponse() {
        return new FindRequestorResponse();
    }

    /**
     * Create an instance of {@link RequestorSearchResult }
     * 
     */
    public RequestorSearchResult createRequestorSearchResult() {
        return new RequestorSearchResult();
    }

    /**
     * Create an instance of {@link DeleteRequestorPaymentResponse }
     * 
     */
    public DeleteRequestorPaymentResponse createDeleteRequestorPaymentResponse() {
        return new DeleteRequestorPaymentResponse();
    }

    /**
     * Create an instance of {@link RetrieveRequestorPastInvoicesResponse }
     * 
     */
    public RetrieveRequestorPastInvoicesResponse createRetrieveRequestorPastInvoicesResponse() {
        return new RetrieveRequestorPastInvoicesResponse();
    }

    /**
     * Create an instance of {@link PastInvoiceList }
     * 
     */
    public PastInvoiceList createPastInvoiceList() {
        return new PastInvoiceList();
    }

    /**
     * Create an instance of {@link ViewRequestorRefund }
     * 
     */
    public ViewRequestorRefund createViewRequestorRefund() {
        return new ViewRequestorRefund();
    }

    /**
     * Create an instance of {@link SearchMatchingRequestorsResponse }
     * 
     */
    public SearchMatchingRequestorsResponse createSearchMatchingRequestorsResponse() {
        return new SearchMatchingRequestorsResponse();
    }

    /**
     * Create an instance of {@link UpdateRequestorResponse }
     * 
     */
    public UpdateRequestorResponse createUpdateRequestorResponse() {
        return new UpdateRequestorResponse();
    }

    /**
     * Create an instance of {@link RetrieveRequestorSummaries }
     * 
     */
    public RetrieveRequestorSummaries createRetrieveRequestorSummaries() {
        return new RetrieveRequestorSummaries();
    }

    /**
     * Create an instance of {@link RetrieveRequestorSummariesResponse }
     * 
     */
    public RetrieveRequestorSummariesResponse createRetrieveRequestorSummariesResponse() {
        return new RetrieveRequestorSummariesResponse();
    }

    /**
     * Create an instance of {@link RequestorHistoryList }
     * 
     */
    public RequestorHistoryList createRequestorHistoryList() {
        return new RequestorHistoryList();
    }

    /**
     * Create an instance of {@link TaxPerFacility }
     * 
     */
    public TaxPerFacility createTaxPerFacility() {
        return new TaxPerFacility();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Reason }
     * 
     */
    public Reason createReason() {
        return new Reason();
    }

    /**
     * Create an instance of {@link RequestorAdjustmentsPayments }
     * 
     */
    public RequestorAdjustmentsPayments createRequestorAdjustmentsPayments() {
        return new RequestorAdjustmentsPayments();
    }

    /**
     * Create an instance of {@link RequestorAdjustmentsFeeList }
     * 
     */
    public RequestorAdjustmentsFeeList createRequestorAdjustmentsFeeList() {
        return new RequestorAdjustmentsFeeList();
    }

    /**
     * Create an instance of {@link RequestorAdjustmentsFee }
     * 
     */
    public RequestorAdjustmentsFee createRequestorAdjustmentsFee() {
        return new RequestorAdjustmentsFee();
    }

    /**
     * Create an instance of {@link RequestorInvoice }
     * 
     */
    public RequestorInvoice createRequestorInvoice() {
        return new RequestorInvoice();
    }

    /**
     * Create an instance of {@link ReasonsList }
     * 
     */
    public ReasonsList createReasonsList() {
        return new ReasonsList();
    }

    /**
     * Create an instance of {@link MatchCriteria }
     * 
     */
    public MatchCriteria createMatchCriteria() {
        return new MatchCriteria();
    }

    /**
     * Create an instance of {@link RequestorPayment }
     * 
     */
    public RequestorPayment createRequestorPayment() {
        return new RequestorPayment();
    }

    /**
     * Create an instance of {@link AttributesMap }
     * 
     */
    public AttributesMap createAttributesMap() {
        return new AttributesMap();
    }

    /**
     * Create an instance of {@link RequestorHistory }
     * 
     */
    public RequestorHistory createRequestorHistory() {
        return new RequestorHistory();
    }

    /**
     * Create an instance of {@link TaxPerFacilityList }
     * 
     */
    public TaxPerFacilityList createTaxPerFacilityList() {
        return new TaxPerFacilityList();
    }

    /**
     * Create an instance of {@link PastInvoice }
     * 
     */
    public PastInvoice createPastInvoice() {
        return new PastInvoice();
    }

    /**
     * Create an instance of {@link RequestorUnappliedAmountDetails }
     * 
     */
    public RequestorUnappliedAmountDetails createRequestorUnappliedAmountDetails() {
        return new RequestorUnappliedAmountDetails();
    }

    /**
     * Create an instance of {@link RequestorAdjustment }
     * 
     */
    public RequestorAdjustment createRequestorAdjustment() {
        return new RequestorAdjustment();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "dob", scope = RequestorSearchCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createRequestorSearchCriteriaDob(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RequestorSearchCriteriaDob_QNAME, XMLGregorianCalendar.class, RequestorSearchCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "dob", scope = Requestor.class)
    public JAXBElement<XMLGregorianCalendar> createRequestorDob(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RequestorSearchCriteriaDob_QNAME, XMLGregorianCalendar.class, Requestor.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:eig.mckesson.com", name = "dob", scope = MatchCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createMatchCriteriaDob(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RequestorSearchCriteriaDob_QNAME, XMLGregorianCalendar.class, MatchCriteria.class, value);
    }

}
