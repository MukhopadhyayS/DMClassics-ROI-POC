/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.base.api;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.ExtendedProperties;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * This class contains application level constants
 *
 * @author manikandans
 * @date   Aug 31, 2009
 * @since  HPF 13.1 [ROI]; Apr 8, 2008
 */
public final class ROIConstants {

    private ROIConstants() { }

    private static final Log LOG =  LogFactory.getLogger(ROIConstants.class);

    private static ExtendedProperties _props;

    // defaults to be used in case the property is not configured
    private static final String AUDIT_ACTION_CODE_I = "I";
    private static final String DEFAULT_AUDIT_FACILITY = "E_P_R_S";
    private static final int DEFAULT_DESC_LENGTH = 256;
    public static final int FEE_TYPE_AMOUNT_PREFIX = 3;
    public static final int FEE_TYPE_AMOUNT_SUFFIX = 2;
    public static final int BILLING_TIER_BASE_CHARGE_PREFIX = 3;
    public static final int BILLING_TIER_BASE_CHARGE_SUFFIX = 2;
    public static final int BILLING_TIER_PAGE_CHARGE_PREFIX = 2;
    public static final int BILLING_TIER_PAGE_CHARGE_SUFFIX = 2;
    private static final String NORMAL_AUDIT_ACTION_CODE = "2";
    public static final int REQUESTOR_NAME_MIN_LENGTH = 2;
    public static final int EPN_MIN_LENGTH = 3;
    public static final int SSN_MIN_LENGTH = 3;
    public static final int MRN_MIN_LENGTH = 3;
    public static final int FAC_MIN_LENGTH = 3;
    public static final int RECENT_REQUESTOR_NO_OF_DAYS = -90;
    public static final int REQUEST_SEARCH_FIELD_MIN_LENGTH = 2;
    public static final String DEFAULT_ROI_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DEFAULT_ROI_DATETIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final String DEFAULT_ROI_REFERENCE_DATETIME_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String DEFAULT_SQL_DATETIME_FORMAT = "MMM dd yyyy HH:mma";

    // General
    public static final int DESC_MAX_LENGTH;
    public static final String DEFAULT_FACILITY;
    public static final String ROI_DATE_FORMAT;
    public static final String CSV_DELIMITER = ",";
    public static final String PAYMENT_DELIMITER = "~!~";
    public static final String QUERY_LIKE = "%";
    public static final String DOLLAR = "$";
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final char Y = 'Y';
    public static final char N = 'N';
    public static final char LY = 'y';
    public static final char LN = 'n';
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    public static final String ROI_ADMINISTRATION_SECURITY = "ROI Administration";
    public static final String ROI_DATETIME_FORMAT;
    public static final String ROI_REFERENCE_DATETIME_FORMAT;

    //MU ROI OUTBOUND
    public static final String REPORT_SNAME;
    public static final String DEFAULT_REPORT_SNAME = "CCD_pat_info";

    public static final String REPORT_CPI_SEQ;
    public static final String DEFAULT_REPORT_CPI_SEQ = "3rd Party EHR";

    public static final String REPORT_PAT_SEQ;
    public static final String DEFAULT_REPORT_PAT_SEQ = "HPF";


    public static final String REPORT_DIGITAL_DOCUMENT_TYPE = "Digital";
    public static final String SAVE_AS_FILE = "SaveAsFile";
    public static final String PRINT_OUTPUT = "Print";
    public static final int DAYS_FOR_MINIMUM_COMPLIANCE = 3;
    public static final int ONE_DAY = 1000 * 60 * 60 * 24;

    public static final String CLINICAL_SUMMARY = "Clinical Summary";
    public static final String DISCHARGE_SUMMARY = "Discharge Summary";
    public static final String DISCHARGE_INSTRUCTIONS = "Discharge Instructions";

    public static final String REQUESTED_STATUS = "Requested";
    public static final String CANCELED_STATUS = "Canceled";
    public static final String DENIED_STATUS = "Denied";
    public static final String FULFILLED_STATUS = "Fulfilled";
    public static final String NEW_STATUS = "New";
    public static final String DELETED_STATUS = "Deleted";
    public static final String COMPLETED_STATUS = "Completed";

    public static final String WEEKENDDAY = "WEEKEND DAY";

    public static final String HPF_PATIENT_TYPE = "hpf";

    public static final String ATTACHMENT_FILE_TYPE_LOCAL = "Local File";

    public static final String ATTACHMENT_FILE_TYPE_EXTERNAL = "Query Clinical System";



    // Admin
    public static final String ADMIN_AUDIT_CODE;

    // Payment Method

    public static final int PAY_METHOD_NAME_MAX_LENGTH = 20;
    // DeliveryMethod

    public static final int DELIVERY_METHOD_NAME_MAX_LENGTH = 20;
    public static final int DELIVERY_METHOD_URL_MAX_LENGTH = 256;

    //Output Server Property
    public static final int OUTPUT_SERVER_NAME_MAX_LENGTH = 255;
    public static final int OUTPUT_SERVER_PORT_MAX_LENGTH = 4;
    public static final int OUTPUT_SERVER_PROTOCOL_MAX_LENGTH = 10;

    //PageWeight
    public static final int WEIGHT_MIN_NO_PAGES = 0;
    public static final int WEIGHT_MAX_NO_PAGES = 99;
    public static final int WEIGHT_MAX_LENGTH = 2;

    //InvoiceDue Days
    public static final int INVOICE_DUE_MIN_NO_DAYS = 0;
    public static final int INVOICE_DUE_MAX_NO_DAYS = 999;
    public static final int INVOICE_DUE_MAX_LENGTH = 3;

    //BillingTemplate
    public static final int BILLING_TEMPLATE_NAME_MAX_LENGTH = 256;

    //FeeType
    public static final int FEE_TYPE_NAME_MAX_LENGTH = 256;

    //BillingTier
    public static final int BILLING_TIER_NAME_MAX_LENGTH = 256;
    public static final int BILLING_TIER_MAX_CHARGE = 6;
    //MediaType
    public static final String MEDIA_TYPE_NAME = "Non-MPF";
    public static final int MEDIA_TYPE_NAME_MAX_LENGTH = 30;

    //Reason
    public static final int REASON_NAME_DEF_MAX_LENGTH = 256;
    public static final int REASON_DISPLAY_TEXT_MAX_LENGTH = 250;
    public static final String STATUS_REASON = "Status";
    public static final String REQUEST_REASON = "Request";
    public static final String ADJUSTMENT_REASON = "Adjustment";

    //RequestorType
    public static final int REQUESTOR_NAME_MAX_LEN = 256;
    public static final String ELECTRONIC_MEDIATYPE = "Electronic";

    //LetterTemplate
    public static final int LETTER_TEMPLATE_NAME_MAX_LENGTH = 50;
    public static final String LETTER_TEMPLATE_DOC_DEFAULT_TYPE = ".docx";
    public static final String LETTER_TEMPLATE_DOC_TYPE;
    public static final String LETTER_TYPE = "other";

    //SalesTax Per Facility

    public static final String FACILITY_CODE =
        "^[ a-zA-Z0-9~`@#$%^&*()_+=}{\\[\\]|:;\"'?/>.<!\\-\\\\]+$";
    public static final String SALES_TAX = "\\d{0,3}([\\.]\\d{0,2}){0,1}";
    public static final int FACILITY_CODE_MAX_LENGTH = 10;
    public static final int SALESTAX_MAX_PERCENTAGE = 6;

    //NonHpfPatient

    public static final String NORMAL_AUDIT_CODE;

    public static final int DEFAULT_FIELD_LENGTH = 256;

    //Requestor
    public static final String MAIN_ADDR_TYPE = "Main";
    public static final String ALT_ADDR_TYPE = "Alternate";
    public static final String HM_PHONE = "Home";
    public static final String WK_PHONE = "Work";
    public static final String CELL_PHONE = "Cell";
    public static final String PRI_EMAIL = "PrimaryEmail";
    public static final String FAX_TYPE = "MainFax";
    public static final String DEFAULT_CONTACT = "Default";
    public static final String EPN_TYPE = "EPN";
    public static final String SSN_TYPE = "SSN";
    public static final String DOB_TYPE = "DOB";
    public static final String MRN_TYPE = "MRN";
    public static final String FAC_CODE = "FACILITY";
    public static final String IS_FREEE_FORM_FAC = "FREEFORM_FACILITY";
    public static final String DB_TYPE_VARCHAR = "varchar";
    public static final String DB_TYPE_DATETIME = "datetime";
    public static final String REQUESTOR_TYPE_PATIENT = "Patient";
    public static final String SYSTEM_HPF = "hpf";
    public static final String SYSTEM_DB_HIS = "his";
    public static final String SYSTEM_TABLE_PATIENTS = "patients";
    public static final String REQUESTOR_DETAIL_REASON = "Patient Requestor Details";
    public static final int REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH = 256;

    public static final int REQUESTOR_MAX_COUNT;
    public static final int DEFAULT_REQUESTOR_MAX_COUNT = 500;
    public static final int REQUESTOR_DOB_ADDED_HOURS;
    public static final int DEFAULT_REQUESTOR_DOB_ADDED_HOURS = 2;

    //Request
    public static final int STATUS_AUTH_RECEIVED_ID;
    public static final int DEFAULT_STATUS_AUTH_RECEIVED_ID = 1;

    public static final int STATUS_LOGGED_ID;
    public static final int DEFAULT_STATUS_LOGGED_ID = 2;

    public static final int STATUS_COMPLETED_ID;
    public static final int DEFAULT_STATUS_COMPLETED_ID = 3;

    public static final int STATUS_DENIED_ID;
    public static final int DEFAULT_STATUS_DENIED_ID = 4;

    public static final int STATUS_CANCELED_ID;
    public static final int DEFAULT_STATUS_CANCELED_ID = 5;

    public static final int STATUS_PENDED_ID;
    public static final int DEFAULT_STATUS_PENDED_ID = 6;

    public static final int STATUS_PREBILLED_ID;
    public static final int DEFAULT_STATUS_PREBILLED_ID = 7;

    public static final String STATUS_AUTH_RECEIVED;
    public static final String DEFAULT_STATUS_AUTH_RECEIVED = "Auth Received";

    public static final String STATUS_LOGGED;
    public static final String DEFAULT_STATUS_LOGGED = "Logged";

    public static final String STATUS_COMPLETED;
    public static final String DEFAULT_STATUS_COMPLETED = "Completed";

    public static final String STATUS_DENIED;
    public static final String DEFAULT_STATUS_DENIED = "Denied";

    public static final String STATUS_CANCELED;
    public static final String DEFAULT_STATUS_CANCELED = "Canceled";

    public static final String STATUS_PENDED;
    public static final String DEFAULT_STATUS_PENDED = "Pended";

    public static final String STATUS_PRE_BILLED;
    public static final String DEFAULT_STATUS_PREBILLED = "Pre-billed";

    public static final String CHANGE_OF_STATUS = "Change of Status";
    public static final String CHANGE_OF_STATUS_REASONS = "Status Reason Added";

    public static final int STATUS_NOT_APPLICABLE_ID = 0;

    public static final Map<Integer, String> STATUS_MAP = new HashMap<Integer, String>();

    public static final String REQUEST_AUDIT_ACTION_CODE_CREATE = "1";
    public static final String REQUEST_AUDIT_ACTION_CODE_UPDATE = "2";
    public static final String REQUEST_AUDIT_ACTION_CODE_DELETE = "3";
    public static final String REQUEST_AUDIT_ACTION_CODE_DENY   = "4";
    public static final String REQUEST_AUDIT_ACTION_CODE_CANCEL = "5";
    public static final String REQUEST_AUDIT_ACTION_CODE_PENDED = "6";
    public static final String REQUEST_AUDIT_ACTION_CODE_VIEW   = "VD";
    public static final String REQUEST_EVENT_AUDIT_ACTION_CODE  = "7";

    public static final String FIELD_DELIMITER = "~@~";
    public static final String FIELD_SEPERATOR = "~";
    public static final String STATUS_DESC = "Status updated to ";
    public static final String STATUS_REASON_DESC = "Status: ";

    // Added for CR# 346,503
    public static final String KEY_SYSPARAM_REQUEST_PD_TYPE = "roi.request.password.type";
    public static final String KEY_SYSPARAM_REQUEST_PD_LENGTH = "roi.request.password.length";
    public static final String KEY_PD = "password";
    // -----------------

    //Notes
    public static final int NOTE_NAME_DEFAULT_LENGTH = 30;

    //SsnMask
    public static final long SSN_MASK_SEQ;
    public static final long DEFAULT_SSN_MASK_SEQ = -1;

    //RequestLoV Parent sequence
    public static final long ROI_REQUEST_MAIN_ID = 1;
    public static final long ROI_REQUEST_DELIVERY_ID = 2;
    public static final long ROI_SUPPLEMENTAL_ID = 3;
    public static final int ORG_ID;
    public static final int DEFAULT_ORG_ID = 1;

    public static final String ROI_REQUEST_DOMAIN_SRC  = "roi";
    public static final String ROI_REQUEST_DOMAIN_TYPE = "request";
    public static final String ROI_REQUEST_ID_KEY      = "request.id";

    public static final String REQUESTOR_TYPE_KEY = "requestor.type";
    public static final String REQUESTOR_NAME_KEY = "requestor.name";
    public static final String LETTER_KEY          = "request.letter";
    public static final String SUPPLEMENTAL_DOCUMENT_KEY = "supplementaldocument.released";

    public static final String REQUESTOR_NAME_ATTR_KEY = "requestorname";
    public static final String REQUESTOR_PHONE_ATTR_KEY = "requestorphone";
    public static final String REQUESTOR_TYPE_NAME_ATTR_KEY = "requestortypename";
    public static final String REQUESTOR_ID_ATTR_KEY = "requestorid";


    // Invoice LoV parent sequence
    public static final String INVOICE_ID_KEY           = "request.invoice.id";
    public static final String INVOICE_DUE_DATE_KEY     = "request.invoice.due.date";
    public static final String REQUEST_FACILITY_KEY     = "request.facility";
    public static final String INVOICE_BALANCEDUE_KEY   = "request.invoice.balance.due";
    public static final String INVOICE_FACILITY_KEY     = "request.invoice.facility";

    // Invoice XML Attributes Key
    public static final String INVOICE_DUE_DATE_ATTR_KEY      = "invoice-due-date";
    public static final String INVOICE_BILLING_LOC_CODE_ATTR_KEY   = "inv-billing-loc-code";
    public static final String INVOICE_BILLING_LOC_NAME_ATTR_KEY   = "inv-billing-loc-name";
    public static final String INVOICE_ID_ATTR_KEY            = "inv-id";
    public static final String INVOICE_DATE_ATTR_KEY          = "inv-date";
    public static final String INVOICE_BASE_CHARGE_ATTR_KEY   = "base-charge";
    public static final String INVOICE_PAID_AMOUNT_ATTR_KEY   = "amount-paid";
    public static final String INVOICE_BALANCE_DUE_ATTR_KEY   = "inv-balance-due";
    public static final String INVOICE_SALES_TAX_ATTR_KEY     = "inv-sales-tax";
    public static final String INVOICE_OVERDUE_DAYS_ATTR_KEY  = "inv-overdue-days";

    // Invoice Template RequestLevel attribute
    public static final String REQUEST_ORIGINAL_BALANCE_ATTR_KEY    = "request-original-balance";
    public static final String REQUEST_PAYMENT_AMOUNT_ATTR_KEY      = "request-payment-amount";
    public static final String REQUEST_BALANCE_DUE_ATTR_KEY         = "request-balance-due";
    public static final String REQUEST_SALES_TAX_ATTR_KEY           = "request-sales-tax";
    public static final String REQUEST_CREDIT_AMOUNT_ATTR_KEY       = "request-credit-amount";
    public static final String REQUEST_DEBIT_AMOUNT_ATTR_KEY        = "request-debit-amount";
    public static final String DOCUMENT_CHARGE_SALES_TAX_ATTR_KEY
                                                               = "document-charge-salestax-total";
    public static final String FEE_CHARGE_SALES_TAX_ATTR_KEY   = "fee-charge-salestax-total";
    public static final String CUSTOMFEE_CHARGE_SALES_TAX_ATTR_KEY
                                                               = "customfee-charge-salestax-total";

    // Request XML attributes Xpath
    public static final String ROI_REQUEST_ORIGINAL_BALANCE_XPATH   = "/request/original-balance";
    public static final String ROI_REQUEST_PAYMENT_AMOUNT_XPATH     = "/request/payment-amout";
    public static final String ROI_REQUEST_CREDIT_AMOUNT_XPATH      = "/request/credit-amount";
    public static final String ROI_REQUEST_DEBIT_AMOUNT_XPATH       = "/request/debit-amount";
    public static final String ROI_REQUEST_TAX_AMOUNT_XPATH    = "/request/salestax-amount";
    public static final String ROI_REQUEST_BALANCE_AMOUNT_XPATH     = "/request/balance-due";



    //Summary Letter Attributes Key
    public static final String SUMMARY_CREATED_DATE_ATTRIBUTE = "summary-created-date";

    // Reports
    public static final String REPORTS_AUDIT_CODE;
    private static final String REPORTS_AUDIT_ACTION_CODE = "9";

    // DocumentTypes
    public static final String AUTHROIZE_DOCUMENT_TYPE = "authorize";

    // Billing & Shipping
    public static final String RELEASE_ITEM_TAG = "release-item";
    public static final String ATTRIBUTE_TAG = "attribute";
    public static final String TYPE_ATTRIBUTE = "type";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String RELEASED_DATE_TAG = "release-date";
    public static final String LETTER_TEMPLATE_FILE_ID_TAG = "lettertemplate-fileid";

    public static final String LETTER_TEMPLATE_ID_TAG = "lettertemplate-id";
    public static final String LETTER_TEMPLATE_NAME_KEY = "lettertemplate-name";
    public static final String REQUESTOR_LETTER_TEMPLATE_ID_KEY = "requestorlettertemplate-id";
    public static final String REQUESTOR_LETTER_TEMPLATE_NAME_KEY = "requestorlettertemplate-name";
    public static final String REQUESTOR_LETTER_TEMPLATE_FILE_ID_KEY
                                                        = "reqlettertemplate-file-id";
    public static final String CHARGE_ATTRIBUTE = "charge";
    public static final String RELEASE_DATE_TYPE = "ReleaseDate";
    public static final String KEY_NAME = "type";

    // Prebill and invoice
    public static final String CHARGES_TYPE         = "charges";
    public static final String BILLING_INFO_TYPE    = "billing-info";
    public static final String REQUESTOR_INFO_TYPE  = "requestorInfo";
    public static final String SHIPPING_INFO_TYPE   = "shipping-info";
    public static final String BILLING_DOC_TYPE     = "billing-doc";
    public static final String RELEASE_INFO_TYPE    = "release-info";
    public static final String REQUEST_ITEM_TYPE    = "requestItem";
    public static final String INVOICE_INFO_TYPE    = "invoice-info";
    public static final String REQUESTOR_LETTER_TYPE  = "requestor-letter";
    public static final String INVOICES_TYPE        = "invoices";
    public static final String NOTES_TYPE           = "notes";
    public static final String DOCUMENT_CHARGE_TYPE = "DocumentCharge";

    public static final String INVOICEPREBILL_ID_ATTRIBUTE = "invoice-prebill-id";
    public static final String INVOICEPREBILL_DT_ATTRIBUTE = "invoice-prebill-date";
    public static final String RELEASE_COST_ATT = "release-cost";
    public static final String REQUEST_TRANSACTION_TYPE = "request-transaction";
    public static final String LETTER_TEMPLATE_NAME = "lettertemplate-name";
    //hardcoded password veracode fix changing Password varibale to PD
    public static final String QUEUE_PD = "queue-password";
    public static final String REQUEST_PD = "request-password";
    public static final String RESEND_DATE = "resend-dt";
    public static final String OUTPUT_METHOD = "output-method";
    public static final String INVOICE_DUE_DATE = "invoice-due-date";
    public static final String OVERWRITE_DUE_DATE = "overwrite-due-date";
    public static final int PASTDUE_INVOICES_MAX_COUNT = 500;
    public static final String EMPTY_STRING = "";

    //logon
    public static final String MASK_BY;
    public static final String DEFAULT_MASK_BY = "*";

    public static final String SEPERATOR = ".";

    //Request Event

    public static final String ADJUSTMENT_TYPE1 = "Debit";
    public static final String ADJUSTMENT_TYPE2 = "Credit";
    public static final String PAYMENT_TYPE = "Payment";
    public static final String AUTO_ADJ_TYPE = "AutoAdjustment";

    public static final String NAME = "[A-Za-z0-9- ,._]+";
    public static final String ALLOW_ALL = "[\\W\\w]*";
    public static final String URL = "[\\W\\w]*";
    public static final String CHARGE = "[0-9.]*";
    public static final String PAGE = "[0-9]*";
    public static final String CITY = "[a-zA-Z0-9. ]*";
    public static final String PHONE = "[\\W\\w]*";
    public static final String ZIP = "(^[abceghjklmnprstvxyABCEGHJKLMNPRSTVXY]{1}\\d{1}"
                                   + "[a-zA-Z]{1} *\\d{1}[a-zA-Z]{1}\\d{1}$)|(^\\d{5}(-\\d{4})?$)";
    public static final String MAIL = "[a-zA-Z0-9.@_-]*";
    public static final String SUPPLEMENTAL_DETAILS = "[a-zA-Z0-9-]*";
    public static final String INVOICE_DUE = "[0-9]*";

    public static final String PORT = "[0-9]*";
    public static final String HOST_NAME =  "(\\d{1,3}\\.){3,3}(\\d{1,3})|[a-zA-Z0-9]*";
    public static final String PROTOCOL = "(http)|(https)";

    public static final String ROI_REQUESTOR_TYPE_DOMAIN_TYPE = "requestortype";
    public static final String HPF_BILLINGTIER_KEY = "hpf.billingtier";
    public static final String NON_HPF_BILLINGTIER_KEY = "nonhpf.billingtier";

    public static final long ROI_REQUESTOR_TYPE_ID = 4;

    public static final String PDF_ENCRYPTION_PD;
    public static final String DEFAULT_PDF_ENCRYPTION_PD = "roi";

    public static final String LETTER_FILE = "letter";
    public static final String INVOICE_FILE = "invoice";
    public static final String PREBILL_FILE = "prebill";
    public static final String REQUESTOR_LETTER_FILE = "requestorletter";
    public static final String OVERDUE_INVOICE_FILE = "overDueInvoice";
    public static final String PAST_INVOICE_FILE = "pastInvoice";
    public static final String REQUESTOR_REFUND_FILE = "RequestorRefund";
    public static final String VOID_LETTER_FILE = "voidLetter";

    public static final String LETTER = "ROILetters";
    public static final String INVOICE = "Invoice";
    public static final String PREBILL = "PreBill";
    public static final String REQUESTORLETTER = "RequestorLetter";
    public static final String OVERDUEINVOICE  = "OverDueInvoice";
    public static final String REQUESTORREFUND = "RequestorRefund";
    public static final String VOIDLETTER     = "VoidLetter";

    public static final String FILE_TYPE_RTF = "RTF";
    public static final String FILE_TYPE_DOCX = "DOCX";
    public static final String FILE_TYPE_ODT = "ODT";
    
    // File type added for docx template  
    public static final String TEMPLATE_FILE_TYPE = FILE_TYPE_DOCX;

    public static final String OUTPUT_CACHE_DIR = "Output";
    public static final String OUTPUT_FILE_EXT = ".pdf";
    public static final String CHUNK_CACHE_DIR = "FileCache";

    //Patient Details
    public static final String PATIENT_ID_KEY        = "id";
    public static final String PATIENT_NAME_KEY      = "name";
    public static final String PATIENT_MRN_KEY       = "mrn";
    public static final String PATIENT_GENDER_KEY    = "gender";
    public static final String PATIENT_EPN_KEY       = "epn";
    public static final String PATIENT_SSN_KEY       = "ssn";
    public static final String PATIENT_DOB_KEY      = "dob";
    public static final String PATIENT_FACILITY_KEY  = "facility";
    public static final String PATIENT_FACILITY_TYPE_KEY = "is-freeformfacility";
    public static final String PATIENT_FREEFORM_FACILITY_KEY = "freeform.facility";
    public static final String PATIENT_FIRST_NAME_KEY = "firstname";
    public static final String PATIENT_LAST_NAME_KEY  = "lastname";
    public static final String PATIENT_IS_VIP_KEY     = "is-vip";

    public static final String PATIENT_NON_HPF_DOCUMENT_ID_KEY = "nonhpf-document-recent-seq";
    public static final String PATIENT_ATTACHMENT_ID_KEY = "attachment-recent-seq";

    public static final String PATIENT_HOME_PHONE_KEY   = "home-phone";
    public static final String PATIENT_WORK_PHONE_KEY   = "work-phone";
    public static final String PATIENT_ADDRESS_TYPE_KEY = "address-type";
    public static final String PATIENT_ADDRESS_KEY      = "address";
    public static final String PATIENT_ADDRESS_1_KEY    = "address1";
    public static final String PATIENT_ADDRESS_2_KEY    = "address2";
    public static final String PATIENT_ADDRESS_3_KEY    = "address3";
    public static final String PATIENT_CITY_KEY         = "city";
    public static final String PATIENT_STATE_KEY        = "state";
    public static final String PATIENT_POSTAL_CODE_KEY  = "postalcode";
    public static final String PATIENT_SHIPPING_URL_KEY = "shipping-url";
    public static final String PATIENT_SHIPPING_METHOD_KEY    = "shipping-method";
    public static final String PATIENT_SHIPPING_METHOD_ID_KEY = "shipping-method-id";
    public static final String PATIENT_TRACKING_NUMBER_KEY    = "tracking-number";
    public static final String PATIENT_REL_SHIPPED_NUMBER_KEY = "will-release-shipped";
    public static final String PATIENT_SHIPPING_WEIGHT_KEY    = "shipping-weight";

    // Adjustment And Payment
    public static final String ADJUSTMENT_TYPE          = "Adjustment";
    public static final String REFUND_TYPE              = "Refund";
    public static final String AMOUNT_ATTR_KEY          = "amount";
    public static final String PAYMENT_MODE_ATTR_KEY    = "payment-mode";
    public static final String DESCRIPTION_ATTR_KEY     = "description";
    public static final String DATE_ATTR_KEY            = "date";
    public static final String TRANSACTION_TYPE_ATTR_KEY = "transaction-type";
    public static final String IS_DEBIT_ATTR_KEY        = "is-debit";
    public static final String PAYMENT_TOTAL_ATTR_KEY   = "payment-total";
    public static final String DEBIT_ADJ_TOT_ATTR_KEY   = "debit-adjustment-total";
    public static final String CREDIT_ADJ_TOT_ATTR_KEY  = "credit-adjustment-total";
    public static final String ADJ_PAY_TOTAL_ATTR_KEY   = "adjustment-payment-total";
    public static final String ADJUSTMENT_TOTAL_ATTR_KEY = "adjustment-total";
    public static final String TAXABLE_AMOUNT_ATTR_KEY  = "taxable-amt";
    public static final String NON_TAXABLE_AMOUNT_ATTR_KEY = "non-tax-amt";
    public static final String TAX_CHARGE_ATTR_KEY         = "tax-charge";
    public static final String AUTOMATIC                   = "automatic";
    public static final String MANUAL                      = "manual";
    public static final String PAYMENT_APPLIED             = "Payment Applied";
    public static final String ADJUSTMENT_APPLIED          = "Adjustment Applied";
    public static final String PAYMENT_RECEIVED            = "Payment Received";
    public static final String ADJUSTMENT_UNAPPLIED        = "Adjustment Unapplied";
    public static final String UNAPPLIED_PAYMENT           = "Unapplied Payment";
    public static final String UNAPPLIED_ADJUSTMENT        = "Unapplied Adjustment";
    // DE1560/CR# 384,396 - Fix
    public static final String UNBILLABLE                  = "UnBillable";

    public static final String INVOICE_DATE_FORMAT;
    public static final String DATETIME_FORMAT;
    public static final SimpleDateFormat DATE_FORMATTER;
    public static final SimpleDateFormat DATETIME_FORMATTER;
    public static final SimpleDateFormat SQL_DATETIME_FORMAT;
    public static final String INVOICE_LOCALE_LANGUAGE;
    public static final String INVOICE_LOCALE_REGION;
    public static final Locale INVOICE_LOCALE;
    public static final NumberFormat CURRENCY_FORMAT;

    //OutputIntegration
    public static final String OUTPUT_SERVER_PROPERTY_KEY = "OutputServerProperties";
    public static final long PORT_NO = 3030;
    public static final String ALL = "ALL";

    public static final String REGENERATED_INVOICE_FILE = "regeneratedinvoice";
	public static final String AUDIT_ACTION_CODE_RELEASED = "R";
    public static final String AUDIT_ACTION_CODE_PRINTED = "RP";
    public static final String AUDIT_ACTION_CODE_FAXED = "RF";
    public static final String AUDIT_ACTION_CODE_DIGITAL = "RD";
    public static final String AUDIT_ACTION_CODE_SEARCH = "SP";
    public static final String AUDIT_ACTION_CODE_REFUND_INVOICE = "RA";
    public static final String AUDIT_ACTION_CODE_INVOICE_VOID = "IV";
    public static final String AUDIT_ACTION_CODE_ROI_POST = "7";
    
    public static final String AUDIT_ACTION_CODE_ROI_APPLY_PYMT = "10";
    public static final String AUDIT_ACTION_CODE_ROI_MODIFY_PYMT = "11";
    public static final String AUDIT_ACTION_CODE_ROI_REMOVE_PYMT = "12";
    public static final String AUDIT_ACTION_CODE_ROI_APPLY_ADJ = "13";
    public static final String AUDIT_ACTION_CODE_ROI_MODIFY_ADJ = "14";
    public static final String AUDIT_ACTION_CODE_ROI_REMOVE_ADJ = "15";

    //Database Connection for MU plug-in
    public static final int DEFAULT_PLUGIN_MIN_DB_CONNECTION = 2;
    public static final int DEFAULT_PLUGIN_MAX_DB_CONNECTION = 10;
    public static final double NO_AMOUNT = 0;
    public static final int PLUGIN_MIN_DB_CONNECTION;
    public static final int PLUGIN_MAX_DB_CONNECTION;

    // Database sql batch size
    public static final int DEFAULT_DATABASE_SQL_BATCH_SIZE = 100;
    public static final int SQL_BATCH_SIZE;
    public static final int DEFAULT_SQL_PARAMETERS_SIZE = 2000;
    public static final int SQL_PARAMETERS_SIZE;

    public static final int DATABASE_SQL_BATCH_SIZE = -1;

    public static ExtendedProperties getProperties() {
        return _props;
    }

    //roiInvoiceDueDateConfiguration
    public static final String ROI_INVOICE_DUE_DATE = "roi.invoice.duedate";
    
    //Event names
    public static final String APPLY_PAYMENT_EVENT = "Payment Applied";
    public static final String UPDATE_PAYMENT_EVENT = "Payment Modified";
    public static final String APPLY_ADJUSTMENT_EVENT = "Adjustment Applied";
    public static final String UPDATE_ADJUSTMENT_EVENT = "Adjustment Modified";
    public static final String CLOSED_INVOICE_EVENT = "Invoice Closed";
    
    //Adjustment names
    public static final String APPLY_ADJ_TO_INVOICE = "Adjustment applied to invoice";
    public static final String UNAPPLY_ADJ_FROM_INVOICE = "Adjustment unapplied from invoice";
    public static final String REMOVE_UNAPPLIED_ADJUSTMENT = "Delete unapplied adjustment";
    public static final String CREATE_ADJUSTMENT = "Create Adjustment";
    
    public static final String TEMP_DIRECTORY_LOCATION_KEY = "ROI_LETTER_GENERATION_FILE_DIR";
    
    //Secret Key for AES
    public static final String AES_SECRET_KEY = "OUTPUT_AES_KEY";
  //Secret Vector for AES
    public static final String AES_SECRET_IV = "OUTPUT_AES_KEY";

    static {

        try {

            InputStream resource = ROIConstants.class.getResourceAsStream("/com/mckesson/eig/roi/roi.properties");
            
            _props = new ExtendedProperties();
            _props.load(resource);
            
            LOG.debug("**** ROI Constants initialized from properties file ");

            // General
            DEFAULT_FACILITY = _props.getString("audit.facility.code", DEFAULT_AUDIT_FACILITY);
            DESC_MAX_LENGTH  = _props.getInteger("description.length.max", DEFAULT_DESC_LENGTH);
            ROI_DATE_FORMAT  =  _props.getString("roi.date.format", DEFAULT_ROI_DATE_FORMAT);
            ROI_DATETIME_FORMAT  =  _props.getString("roi.datetime.format",
                                                     DEFAULT_ROI_DATETIME_FORMAT);

            ROI_REFERENCE_DATETIME_FORMAT  =  _props.getString("roi.refid.datetime.format",
                                                        DEFAULT_ROI_REFERENCE_DATETIME_FORMAT);

            // TAT
            REPORT_SNAME = _props.getString("report.sname", DEFAULT_REPORT_SNAME);
            REPORT_CPI_SEQ = _props.getString("report.cpi.seq", DEFAULT_REPORT_CPI_SEQ);
            REPORT_PAT_SEQ = _props.getString("report.pat.seq", DEFAULT_REPORT_PAT_SEQ);



            // Admin
            ADMIN_AUDIT_CODE = _props.getString("admin.audit.action.code", AUDIT_ACTION_CODE_I);

            NORMAL_AUDIT_CODE = _props.getString("normal.audit.action.code",
                                                 NORMAL_AUDIT_ACTION_CODE);

            //LetterTemplate
            LETTER_TEMPLATE_DOC_TYPE = _props.getString("letter.template.document.type",
                                                         LETTER_TEMPLATE_DOC_DEFAULT_TYPE);

            //Requestor
            REQUESTOR_MAX_COUNT  = _props.getInteger("requestor.search.max.count",
                                                DEFAULT_REQUESTOR_MAX_COUNT);

            REQUESTOR_DOB_ADDED_HOURS  = _props.getInteger("requestor.dob.added.hours",
                                                 DEFAULT_REQUESTOR_DOB_ADDED_HOURS);


            // Request
            STATUS_AUTH_RECEIVED = _props.getString("request.status.authreceived",
                                                    DEFAULT_STATUS_AUTH_RECEIVED);
            STATUS_LOGGED        = _props.getString("request.status.logged",
                                                    DEFAULT_STATUS_LOGGED);
            STATUS_COMPLETED     = _props.getString("request.status.completed",
                                                    DEFAULT_STATUS_COMPLETED);
            STATUS_DENIED        = _props.getString("request.status.denied", DEFAULT_STATUS_DENIED);
            STATUS_CANCELED      = _props.getString("request.status.canceled",
                                                    DEFAULT_STATUS_CANCELED);
            STATUS_PENDED        = _props.getString("request.status.pended", DEFAULT_STATUS_PENDED);
            STATUS_PRE_BILLED    = _props.getString("request.status.prebilled",
                                                    DEFAULT_STATUS_PREBILLED);

            STATUS_AUTH_RECEIVED_ID = _props.getInteger("request.status.authreceived.id",
                                                 DEFAULT_STATUS_AUTH_RECEIVED_ID);
            STATUS_LOGGED_ID        = _props.getInteger("request.status.logged.id",
                                                 DEFAULT_STATUS_LOGGED_ID);
            STATUS_COMPLETED_ID     = _props.getInteger("request.status.completed.id",
                                                 DEFAULT_STATUS_COMPLETED_ID);
            STATUS_DENIED_ID        = _props.getInteger("request.status.denied.id",
                                                 DEFAULT_STATUS_DENIED_ID);
            STATUS_CANCELED_ID      = _props.getInteger("request.status.canceled.id",
                                                 DEFAULT_STATUS_CANCELED_ID);
            STATUS_PENDED_ID        = _props.getInteger("request.status.pended.id",
                                                 DEFAULT_STATUS_PENDED_ID);
            STATUS_PREBILLED_ID     = _props.getInteger("request.status.prebilled.id",
                                                        DEFAULT_STATUS_PREBILLED_ID);

            STATUS_MAP.put(STATUS_AUTH_RECEIVED_ID, STATUS_AUTH_RECEIVED);
            STATUS_MAP.put(STATUS_LOGGED_ID, STATUS_LOGGED);
            STATUS_MAP.put(STATUS_COMPLETED_ID, STATUS_COMPLETED);
            STATUS_MAP.put(STATUS_DENIED_ID, STATUS_DENIED);
            STATUS_MAP.put(STATUS_CANCELED_ID, STATUS_CANCELED);
            STATUS_MAP.put(STATUS_PENDED_ID, STATUS_PENDED);
            STATUS_MAP.put(STATUS_PREBILLED_ID, STATUS_PRE_BILLED);

            SSN_MASK_SEQ = _props.getLong("ssn.mask.seq", DEFAULT_SSN_MASK_SEQ);
            ORG_ID = _props.getInteger("get.id", DEFAULT_ORG_ID);

            //Reports
            REPORTS_AUDIT_CODE = _props.getString("reports.audit.action.code",
                                                  REPORTS_AUDIT_ACTION_CODE);
            //logon
            MASK_BY = _props.getString("logon.mask.by", DEFAULT_MASK_BY);

            PDF_ENCRYPTION_PD = _props.getString("pdf.encryption.password",
                                                        DEFAULT_PDF_ENCRYPTION_PD);

          //OverDue invoices
            INVOICE_DATE_FORMAT = _props.getString("invoice.due.date.format");
            DATETIME_FORMAT = _props.getString("invoice.due.date.time.format");
            DATE_FORMATTER = new SimpleDateFormat(INVOICE_DATE_FORMAT);
            DATETIME_FORMATTER = new SimpleDateFormat(DATETIME_FORMAT);
            SQL_DATETIME_FORMAT  =  new SimpleDateFormat(_props.getString("roi.sql.datetime.format",
                                                                    DEFAULT_SQL_DATETIME_FORMAT));

          //Setting Locale for invoice.
            INVOICE_LOCALE_LANGUAGE = _props.getString("invoice.locale.language");
            INVOICE_LOCALE_REGION = _props.getString("invoice.locale.region");
            INVOICE_LOCALE = new Locale(INVOICE_LOCALE_LANGUAGE, INVOICE_LOCALE_REGION);

            // Currency format
            CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(INVOICE_LOCALE);

            PLUGIN_MIN_DB_CONNECTION     = _props.getInteger("plugin.min.db.connection",
                DEFAULT_PLUGIN_MIN_DB_CONNECTION);

            PLUGIN_MAX_DB_CONNECTION     = _props.getInteger("plugin.min.db.connection", DEFAULT_PLUGIN_MAX_DB_CONNECTION);
            SQL_BATCH_SIZE = _props.getInteger("database.sql.batch.size", DEFAULT_DATABASE_SQL_BATCH_SIZE);

            SQL_PARAMETERS_SIZE = _props.getInteger("database.sql.parameter.execution.size",
                    DEFAULT_SQL_PARAMETERS_SIZE);

        } catch (Exception e) {
            throw new ROIException(ROIClientErrorCodes.READ_PROPERTIES_FAILED, e.getMessage());
        }
    }
    
}
