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

package com.mckesson.eig.roi.billing.service;


import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIValidator;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterInfo;
import com.mckesson.eig.roi.billing.model.OverDueRestriction;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;


/**
 *
 * @author Rethinamt
 * @date   Sep 06, 2011
 * @since  HPF 15.2 [ROI];
 */
public class OverDueInvoiceServiceValidator
extends BaseROIValidator {

     public boolean validateOverDueInvoiceSearchCriteria(SearchPastDueInvoiceCriteria criteria) {

        if (null == criteria) {
            throw new ROIException(ROIClientErrorCodes.INVALID_SEARCH_CRITERIA);
        }

        if (null == criteria.getBillingLocations()) {
            addError(ROIClientErrorCodes.FACILITY_CODE_IS_NULL);
        }

        if (criteria.getOverDueFrom() < 0) {
            addError(ROIClientErrorCodes.OVERDUE_DAYS_IS_INVALID);
        }

        if (null == criteria.getOverDueRestriction()) {
            addError(ROIClientErrorCodes.OVERDUE_RESTRICTION_IS_BLANK);
        }

        if (OverDueRestriction.BETWEEN.equals(criteria.getOverDueRestriction())
                && criteria.getOverDueTo() < criteria.getOverDueFrom()) {
            addError(ROIClientErrorCodes.OVERDUE_DAYS_FROM_IS_GREATER_THAN_TO);
        }

        return hasNoErrors();
    }

    public boolean validateInvoiceAndLetterInfo(InvoiceAndLetterInfo invLetterInfo,
                                                boolean forPreview) {

        if (null == invLetterInfo.getInvoices() || invLetterInfo.getInvoices().size() <= 0) {
            addError(ROIClientErrorCodes.REQUESTOR_INVOICES_IS_BLANK);
        }

        if ((!invLetterInfo.isOutputInvoice())
                && invLetterInfo.getRequestorLetterTemplateId() == 0) {

            addError(ROIClientErrorCodes.TEMPLATE_ID_IS_BLANK);
        }

        if (!forPreview
             && (null == invLetterInfo.getProperties()
                     || invLetterInfo.getProperties().isEmpty())) {

            addError(ROIClientErrorCodes.INVALID_PROPERTIES, "Properties should not be null");
        }

        return hasNoErrors();
    }
}
