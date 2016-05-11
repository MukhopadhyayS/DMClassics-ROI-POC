/**
 *
 */
package com.mckesson.eig.roi.billing.service;

import com.mckesson.eig.roi.admin.dao.LetterTemplateDAO;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.service.BaseROIValidator;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.RegeneratedInvoiceInfo;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;

/**
 * @author ais
 *
 */
public class BillingCoreServiceValidator
extends BaseROIValidator {

	public boolean validateReleaseCore(ReleaseCore releaseCore) {

		if (releaseCore == null || releaseCore.getRequestId() <= 0) {
            addError(ROIClientErrorCodes.INVALID_RELEASE);
        }
        return hasNoErrors();
	}

	public boolean validateInvoiceInfo(RegeneratedInvoiceInfo invInfo) {

	    if (invInfo == null) {
	        addError(ROIClientErrorCodes.INVOICES_AND_LETTER_INFO_IS_NULL);
	    }
	    return hasNoErrors();
	}

	/**
     * This method validates the letter details
     * @param letterTempId Letter template id
     * @param ltDAO LetterTemplateDAO details
     * @param item Item details
     * @return
     */
    public boolean validateLetterDetails(long letterTempId, LetterTemplateDAO ltDAO) {

        validateLetterTemplateDocId(letterTempId, ltDAO);
        return hasNoErrors();
    }

	/**
     * This method validates the letter template id
     * @param id letterTemplate id
     * @param ltDAO LetterTemplateDAO details
     * @return validation result true/ false
     */
    public boolean validateLetterTemplateDocId(long id, LetterTemplateDAO ltDAO) {

        if (id == 0) {
            addError(ROIClientErrorCodes.LETTER_TEMPLATE_ID_EQUAL_TO_ZERO);
            return false;
        }

        ltDAO.retrieveLetterTemplateFile(id);
        return hasNoErrors();
    }

    /**
     * This method validates the request id
     * @param id RequestId
     * @return validation result true/ false
     */
    public boolean validateRequestId(long id) {

        if (id <= 0) {
            addError(ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
        return hasNoErrors();
    }

    public boolean validateInvoiceId(long id) {

        if (id == 0) {
            addError(ROIClientErrorCodes.INVALID_INVOICE_ID_OR_NOT_INVOICED);
        }
        return hasNoErrors();
    }

    /**
     * This method validates the OutputProperties
     * @param outputProperties
     * @return validation result true/ false
     */
    public boolean validateOutputProperties(InvoiceAndLetterOutputProperties outputProperties) {

        if (outputProperties != null) {

            if (outputProperties.isForInvoice()
                    && outputProperties.getInvoiceId() <= 0) {
                addError(ROIClientErrorCodes.INVALID_INVOICE_ID);
            }

            if (outputProperties.isForLetter()
                    && outputProperties.getLetterId() <= 0) {
                addError(ROIClientErrorCodes.INVALID_LETTER_ID);
            }

        }
        return hasNoErrors();
    }

    /**
     * validates the invoice and letterinfo
     * @param InvAndPrebillInfo
     * @return
     */
    public boolean validateInvAndPrebillInfo(InvoiceOrPrebillAndPreviewInfo invAndPrebillInfo) {

        if (invAndPrebillInfo == null
                || invAndPrebillInfo.getLetterTemplateFileId() < 0) {

            addError(ROIClientErrorCodes.INVOICES_AND_LETTER_INFO_IS_NULL);
        }

        return hasNoErrors();
    }

    /**
     * This method validates the payment details
     * @param paymentInfo payment details
     * @return
     */
    public boolean validatePaymentDetails(RequestorPaymentList paymentInfo,
            boolean isPaymentCreate) {

        if (paymentInfo == null) {
            addError(ROIClientErrorCodes.REQUESTOR_PAYMENT_IS_NULL);
            return false;
        }

        if (isPaymentCreate) {
            if (paymentInfo.getRequestorId() == 0) {
                addError(ROIClientErrorCodes.INVALID_REQUESTOR_ID);
                return false;
            }
        } else {

            if (paymentInfo.getPaymentList() == null
                    || paymentInfo.getPaymentList().isEmpty()) {

                addError(ROIClientErrorCodes.REQUESTOR_PAYMENT_IS_NULL);
                return false;
            }


            for (RequestorPayment payment : paymentInfo.getPaymentList()) {
                if (payment.getPaymentId() == 0) {
                    addError(ROIClientErrorCodes.INVALID_REQUESTOR_PAYMENT_ID);
                    return false;
                }
            }
        }

        return hasNoErrors();
    }
    
    /**
     * validates whether the given arguments are valid
     * @param requestId
     * @param invoiceId
     * @return where the arguments are valid or Not
     */
    public boolean validateAutoApplyInvoice(long requestId, long invoiceId) {
        
        if (0 == invoiceId) {
            addError(ROIClientErrorCodes.INVOICE_ID_IS_BLANK);
        }
        
        if (0 == requestId) {
            addError(ROIClientErrorCodes.INVALID_REQUEST_ID); 
        }
        return hasNoErrors();
    }

}
