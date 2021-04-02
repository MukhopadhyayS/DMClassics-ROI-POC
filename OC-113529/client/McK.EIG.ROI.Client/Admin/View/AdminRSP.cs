#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
#endregion
using System;

using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Admin.View.Billing.BillingTemplate;
using McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers;
using McK.EIG.ROI.Client.Admin.View.Billing.DeliveryMethod;
using McK.EIG.ROI.Client.Admin.View.Billing.FeeTypes;
using McK.EIG.ROI.Client.Admin.View.Billing.MediaType;
using McK.EIG.ROI.Client.Admin.View.Billing.PaymentMethods;
using McK.EIG.ROI.Client.Admin.View.Billing.PageWeight;
using McK.EIG.ROI.Client.Admin.View.Billing.InvoiceDue;
using McK.EIG.ROI.Client.Admin.View.Billing.SalesTaxPerFacility;
using McK.EIG.ROI.Client.Admin.View.Billing.RequestorType;
using McK.EIG.ROI.Client.Admin.View.Reasons.AdjustmentReasons;
using McK.EIG.ROI.Client.Admin.View.Reasons.StatusReasons;
using McK.EIG.ROI.Client.Admin.View.Reasons.RequestReasons;
using McK.EIG.ROI.Client.Admin.View.Other.DisclosureDocTypes;
using McK.EIG.ROI.Client.Admin.View.Other.LetterTemplate;
using McK.EIG.ROI.Client.Admin.View.Other.ConfigureNotes;
using McK.EIG.ROI.Client.Admin.View.Other.SSNMasking;
using McK.EIG.ROI.Client.Admin.View.Configuration.Attachments;
using McK.EIG.ROI.Client.Admin.View.Configuration.ExternalSources;
using McK.EIG.ROI.Client.Admin.View.Configuration.TurnaroundTimeDays;
using McK.EIG.ROI.Client.Admin.View.Other.ConfigureCountry;
using McK.EIG.ROI.Client.Admin.View.Other.ConfigureUnbillableRequest;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Used to hold an Editor.
    /// </summary>
    public class AdminRSP : ROIRightSidePane
    {
        #region Fields

        private EventHandler navigateMediaTypeHandler;
        private EventHandler navigateFeeTypeHandler;
        private EventHandler navigateBillingTierHandler;
        private EventHandler navigateRequestorTypeHandler;
        private EventHandler navigateDeliveryMethodHandler;
        private EventHandler navigatePaymentMethodHandler;
        private EventHandler navigatePageWeightHandler;
        private EventHandler navigateBillingTemplate;
        private EventHandler navigateRequestReason;
        private EventHandler navigateAdjustmentReason;
        private EventHandler navigateStatusReason;
        private EventHandler navigateDisclosureDocTypesHandler;
        private EventHandler navigateLetterTemplate;
        private EventHandler navigateConfigureNotes;
        private EventHandler navigateSsnMasking;
        private EventHandler navigateAttachment;
        private EventHandler navigateInvoiceDueHandler;
        private EventHandler navigateSalesTaxPerFacilityHandler;
		private EventHandler navigateExternalSource;
        private EventHandler navigateTurnaroundTimeDays;
        private EventHandler navigateConfigureUnbillableRequest;
        //ROI16.0 zipcode
        private EventHandler navigateConfigureCountry;


        #endregion

        #region Methods
      
        /// <summary>
        ///  Event subscription.
        /// </summary>
        protected override void Subscribe()
        {
            navigateMediaTypeHandler          = new EventHandler(Process_NavigateMediaType);
            navigateFeeTypeHandler            = new EventHandler(Process_NavigateFeeType);
            navigateBillingTierHandler        = new EventHandler(Process_NavigateBillingTier);
            navigateRequestorTypeHandler      = new EventHandler(Process_NavigateRequestorType);
            navigateDeliveryMethodHandler     = new EventHandler(Process_NavigateDeliveryMethod);
            navigatePaymentMethodHandler      = new EventHandler(Process_NavigatePaymentMethod);
            navigatePageWeightHandler         = new EventHandler(Process_NavigatePageWeight);
            navigateInvoiceDueHandler         = new EventHandler(Process_NavigateInvoiceDue);
            navigateSalesTaxPerFacilityHandler = new EventHandler(Process_NavigateSalesTaxPerFacility);
            navigateBillingTemplate           = new EventHandler(Process_NavigateBillingTemplate);
            navigateRequestReason             = new EventHandler(Process_NavigateRequestReason);
            navigateAdjustmentReason          = new EventHandler(Process_NavigateAdjustmentReason);
            navigateStatusReason              = new EventHandler(Process_NavigateStatusReason);
            navigateDisclosureDocTypesHandler = new EventHandler(Process_NavigateDisclosureDocTypes);
            navigateLetterTemplate            = new EventHandler(Process_NavigateLetterTemplate);
            navigateConfigureNotes            = new EventHandler(Process_NavigateConfigureNotes);
            navigateSsnMasking                = new EventHandler(Process_NavigateSsnMasking);
            navigateAttachment                = new EventHandler(Process_NavigateAttachment);
            navigateExternalSource            = new EventHandler(Process_NavigateExternalSources);
            navigateConfigureUnbillableRequest = new EventHandler(Process_NavigateConfigureUnbillableRequest);
            navigateTurnaroundTimeDays = new EventHandler(Process_NavigateTurnaroundTimeDays);
            //ROI16.0 zipcode
            navigateConfigureCountry = new EventHandler(Process_NavigateConfigureCountry);

            AdminEvents.NavigateMediaType          += navigateMediaTypeHandler;
            AdminEvents.NavigateFeeType            += navigateFeeTypeHandler;
            AdminEvents.NavigateBillingTier        += navigateBillingTierHandler;
            AdminEvents.NavigateRequestorType      += navigateRequestorTypeHandler;
            AdminEvents.NavigateDeliveryMethod     += navigateDeliveryMethodHandler;
            AdminEvents.NavigatePaymentMethod      += navigatePaymentMethodHandler;
            AdminEvents.NavigatePageWeight         += navigatePageWeightHandler;
            AdminEvents.NavigateInvoiceDue         += navigateInvoiceDueHandler;
            AdminEvents.NavigateSalesTaxPerFacility += navigateSalesTaxPerFacilityHandler;
            AdminEvents.NavigateBillingTemplate    += navigateBillingTemplate;
            AdminEvents.NavigateDisclosureDocTypes += navigateDisclosureDocTypesHandler;
            AdminEvents.NavigateReasonRequest      += navigateRequestReason;
            AdminEvents.NavigateReasonAdjustment   += navigateAdjustmentReason;
            AdminEvents.NavigateReasonStatus       += navigateStatusReason;
            AdminEvents.NavigateLetterTemplate     += navigateLetterTemplate;
            AdminEvents.NavigateConfigureNotes     += navigateConfigureNotes;
            AdminEvents.NavigateSsnMasking         += navigateSsnMasking;
            AdminEvents.NavigateAttachment         += navigateAttachment;
            AdminEvents.NavigateExternalSources    += navigateExternalSource;
            AdminEvents.NavigateTurnaroundTimeDays += navigateTurnaroundTimeDays;
            AdminEvents.NavigateConfigureUnbillableRequest += navigateConfigureUnbillableRequest;
            //ROI16.0 zipcode
            AdminEvents.NavigateConfigureCountry += navigateConfigureCountry;
        }

        /// <summary>
        ///  Event unsubscription.
        /// </summary>
        protected override void Unsubscribe()
        {
            AdminEvents.NavigateMediaType          -= navigateMediaTypeHandler;
            AdminEvents.NavigateFeeType            -= navigateFeeTypeHandler;
            AdminEvents.NavigateBillingTier        -= navigateBillingTierHandler;
            AdminEvents.NavigateRequestorType      -= navigateRequestorTypeHandler;
            AdminEvents.NavigateDeliveryMethod     -= navigateDeliveryMethodHandler;
            AdminEvents.NavigatePaymentMethod      -= navigatePaymentMethodHandler;
            AdminEvents.NavigatePageWeight         -= navigatePageWeightHandler;
            AdminEvents.NavigateInvoiceDue         -= navigateInvoiceDueHandler;
            AdminEvents.NavigateSalesTaxPerFacility -= navigateSalesTaxPerFacilityHandler;
            AdminEvents.NavigateBillingTemplate    -= navigateBillingTemplate;
            AdminEvents.NavigateDisclosureDocTypes -= navigateDisclosureDocTypesHandler;
            AdminEvents.NavigateReasonRequest      -= navigateRequestReason;
            AdminEvents.NavigateReasonAdjustment   -= navigateAdjustmentReason;
            AdminEvents.NavigateReasonStatus       -= navigateStatusReason;
            AdminEvents.NavigateLetterTemplate     -= navigateLetterTemplate;
            AdminEvents.NavigateConfigureNotes     -= navigateConfigureNotes;
            AdminEvents.NavigateSsnMasking         -= navigateSsnMasking;
            AdminEvents.NavigateAttachment         -= navigateAttachment;
            AdminEvents.NavigateExternalSources    -= navigateExternalSource;
            AdminEvents.NavigateTurnaroundTimeDays -= navigateTurnaroundTimeDays;
            AdminEvents.NavigateConfigureUnbillableRequest -= navigateConfigureUnbillableRequest;
            //ROI16.0 zipcode
            AdminEvents.NavigateConfigureCountry -= navigateConfigureCountry;
        }


        /// <summary>
        /// Process the event of MediaType Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateMediaType(object sender, EventArgs ae)
        {
            SetCurrentEditor(new MediaTypeEditor());
        }

        /// <summary>
        /// Process the event of FeeType Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateFeeType(object sender, EventArgs ae)
        {
            SetCurrentEditor(new FeeTypeEditor());
        }

        /// <summary>
        /// Process the event of BillingTier Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateBillingTier(object sender, EventArgs ae)
        {
            SetCurrentEditor(new BillingTierEditor());
        }


        /// <summary>
        /// Process the event of RequestorType Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateRequestorType(object sender, EventArgs ae)
        {
            SetCurrentEditor(new RequestorTypeEditor());
        }


        /// <summary>
        /// Process the event of DeliveryMethod Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateDeliveryMethod(object sender, EventArgs ae)
        {
            SetCurrentEditor(new DeliveryMethodEditor());
        }

        /// <summary>
        /// Process the event of Payment Method Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigatePaymentMethod(object sender, EventArgs ae)
        {
            SetCurrentEditor(new PaymentMethodEditor());
        }
        
        /// <summary>
        /// Process the event of PageWeight Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigatePageWeight(object sender, EventArgs ae)
        {
            SetCurrentEditor(new PageWeightEditor());
        }

        /// <summary>
        /// Process the event of InvoiceDue Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateInvoiceDue(object sender, EventArgs ae)
        {
            SetCurrentEditor(new InvoiceDueEditor());
        }

        /// <summary>
        /// Process the event of Sales Tax Per Facility Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateSalesTaxPerFacility(object sender, EventArgs ae)
        {
            SetCurrentEditor(new SalesTaxPerFacilityEditor());
        }


        private void Process_NavigateAttachment(object sender, EventArgs ae)
        {
            SetCurrentEditor(new AttachmentEditor());
        }
		
		private void Process_NavigateExternalSources(object sender, EventArgs ae)
        {
            SetCurrentEditor(new ExternalSourcesEditor());
        }
        private void Process_NavigateTurnaroundTimeDays(object sender, EventArgs ae)
        {
            SetCurrentEditor(new TurnaroundTimeDaysEditor());
        }
		
        /// <summary>
        /// Process the event of BillingTemplate Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateBillingTemplate(object sender, EventArgs ae)
        {
            SetCurrentEditor(new BillingTemplateEditor());
        }

        /// <summary>
        /// Process the event of Request Reason Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateRequestReason(object sender, EventArgs ae)
        {
            SetCurrentEditor(new RequestReasonsEditor());
        }

        /// <summary>
        /// Process the event of Adjustment Reason Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateAdjustmentReason(object sender, EventArgs ae)
        {
            SetCurrentEditor(new AdjustmentReasonsEditor());
        }

        /// <summary>
        /// Process the event of Status Reason Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateStatusReason(object sender, EventArgs ae)
        {
            SetCurrentEditor(new StatusReasonsEditor());
        }

        /// <summary>
        /// Process the event of DisclosureDocTypes Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateDisclosureDocTypes(object sender, EventArgs ae)
        {
            SetCurrentEditor(new DisclosureDocTypesEditor());
        }

        /// <summary>
        /// Process the event of Letter Template Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateLetterTemplate(object sender, EventArgs ae)
        {
            SetCurrentEditor(new LetterTemplateEditor());
        }

        /// <summary>
        /// Process the event of Configure NotesClick on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateConfigureNotes(object sender, EventArgs ae)
        {
            SetCurrentEditor(new ConfigureNotesEditor());
        }

        /// <summary>
        /// Process the event of ssn masking clicks on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateSsnMasking(object sender, EventArgs ae)
        {
            SetCurrentEditor(new SSNMaskEditor());
        }

        /// <summary>
        /// Process the event of configure Location clicks on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateConfigureCountry(object sender, EventArgs ae)
        {
            SetCurrentEditor(new ConfigureCountryEditor());
        }

        /// <summary>
        /// Process the event of configureunbillablerequest clicks on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateConfigureUnbillableRequest(object sender, EventArgs ae)
        {
           SetCurrentEditor(new ConfigureUnbillableRequestEditor());            
        }

        #endregion

    }
}
