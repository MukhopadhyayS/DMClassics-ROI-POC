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

using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Placeholder to hold all events related to admin module.
    /// </summary>
    public static class AdminEvents
    {
        #region Fields

        //LSP Events
        public static event EventHandler NavigateMediaType;
        public static event EventHandler NavigateBillingTier;
        public static event EventHandler NavigateFeeType;
        public static event EventHandler NavigateDeliveryMethod;

        public static event EventHandler NavigateBillingTemplate;
        public static event EventHandler NavigateRequestorType;
        public static event EventHandler NavigatePageWeight;
        public static event EventHandler NavigatePaymentMethod;
        public static event EventHandler NavigateDisclosureDocTypes;
        public static event EventHandler NavigateLetterTemplate;
        public static event EventHandler NavigateConfigureNotes;
        public static event EventHandler NavigateSsnMasking;
        public static event EventHandler NavigateInvoiceDue;
        public static event EventHandler NavigateSalesTaxPerFacility;
        public static event EventHandler NavigateConfigureUnbillableRequest;
        //ROI16.0 zipcode
        public static event EventHandler NavigateConfigureCountry;

        public static event EventHandler NavigateAttachment;
        public static event EventHandler NavigateExternalSources;
        public static event EventHandler NavigateTurnaroundTimeDays;

        //MCP Events
        public static event EventHandler MCPEntitySelection;
        public static event EventHandler CreateNewButtonClick;

        //ODP Save Click Events
        public static event EventHandler Create;
        public static event EventHandler Modify;

        //ODP Cancel Click Events
        public static event EventHandler CancelCreate;
        public static event EventHandler CancelModify;

        //ODP Data Change Events
        public static event EventHandler ODPDataChange;

        public static event EventHandler EmptyListing;

        public static event EventHandler NavigateReasonRequest;
        public static event EventHandler NavigateReasonAdjustment;
        public static event EventHandler NavigateReasonStatus;

        #endregion

        #region Methods
        /// <summary>
        /// Occurs when MediaType Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateMediaType(object sender, EventArgs e)
        {
            if (NavigateMediaType != null)
            {
                // Occurs when media type navigation link is clicked
                NavigateMediaType(sender, e);
            }
        }        

        /// <summary>
        /// Occurs when BillingTier Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateBillingTier(object sender, EventArgs e)
        {
            if (NavigateBillingTier != null)
            {
                NavigateBillingTier(sender, e);
            }
        }     

        /// <summary>
        /// Occurs when FeeType Click on LSP.               
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateFeeType(object sender, EventArgs e)
        {
            if (NavigateFeeType != null)
            {
                NavigateFeeType(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Delivery Method Click on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateDeliveryMethod(object sender, EventArgs e)
        {
            if (NavigateDeliveryMethod != null)
            {
                NavigateDeliveryMethod(sender, e);
            }
        }


        
        internal static void OnNavigateBillingTemplate(object sender, EventArgs e)
        {
            if (NavigateBillingTemplate != null)
            {
                NavigateBillingTemplate(sender, e);
            }
        }
        
        internal static void OnNavigateRequestorType(object sender, EventArgs e)
        {
            if (NavigateRequestorType != null)
            {
                NavigateRequestorType(sender, e);
            }
        }

        internal static void OnNavigatePageWeight(object sender, EventArgs e)
        {
            if (NavigatePageWeight != null)
            {
                NavigatePageWeight(sender, e);
            }
        }

        internal static void OnNavigateAttachment(object sender, EventArgs e)
        {
            if (NavigateAttachment != null)
            {
                NavigateAttachment(sender, e);
            }
        }

        internal static void OnNavigateExternalSources(object sender, EventArgs e)
        {
            if (NavigateExternalSources!= null)
            {
                NavigateExternalSources(sender, e);
            }
        }

        internal static void OnNavigateTurnaroundTimeDays(object sender, EventArgs e)
        {
            if (NavigateTurnaroundTimeDays != null)
            {
                NavigateTurnaroundTimeDays(sender, e);
            }
        }
        internal static void OnNavigatePaymentMethod(object sender, EventArgs e)
        {
            if (NavigatePaymentMethod != null)
            {
                NavigatePaymentMethod(sender, e);
            }
        }

        internal static void OnNavigateDisclosureDocumentTypes(object sender, EventArgs e)
        {
            if (NavigateDisclosureDocTypes != null)
            {
                NavigateDisclosureDocTypes(sender, e);
            }
        }

        internal static void OnNavigateInvoiceDue(object sender, EventArgs e)
        {
            if (NavigateInvoiceDue != null)
            {
                NavigateInvoiceDue(sender, e);
            }
        }

        internal static void OnNavigateSalesTaxPerFacility(object sender, EventArgs e)
        {
            if (NavigateSalesTaxPerFacility != null)
            {
                NavigateSalesTaxPerFacility(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Letter Template Click on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateLetterTemplate(object sender, EventArgs e)
        {
            if (NavigateLetterTemplate != null)
            {
                NavigateLetterTemplate(sender, e);
            }
        }

        /// <summary>
        /// Occurs when row select on MediaTypeMCP.                           
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>        
        internal static void OnMCPEntitySelected(object sender, EventArgs e)
        {
            if (MCPEntitySelection != null)
            {
                MCPEntitySelection(sender, e);
            }
        }

        /// <summary>
        /// Occurs when new button is clicked on MediaTypeMCP.  
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>    
        internal static void OnCreateNewButtonClick(object sender, EventArgs e)
        {
            if (CreateNewButtonClick != null)
            {
                CreateNewButtonClick(sender, e);
            }
        }      

        /// <summary>
        /// Occurs when save button is clicked on ODP.           
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>   
        internal static void OnCreate(object sender, EventArgs e)
        {
            if (Create != null)
            {
                Create(sender, e);
            }
        }

        internal static void OnModify(object sender, EventArgs e)
        {
            if (Modify != null)
            {
                Modify(sender, e);
            }
        }

        /// <summary>
        /// Occurs when cancel button is clicked on ODP - During Create New Operation.           
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>   
        internal static void OnCancelCreate(object sender, EventArgs e)
        {
            if (CancelCreate != null)
            {
                CancelCreate(sender, e);
            }
        }

        /// <summary>
        /// Occurs when cancel button is clicked on ODP - During Modify Operation.           
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>   
        internal static void OnCancelModify(object sender, EventArgs e)
        {
            if (CancelModify != null)
            {
                CancelModify(sender, e);
            }
        }

        internal static void OnODPDataChange(object sender, EventArgs e)
        {
            if (ODPDataChange != null)
            {
                ODPDataChange(sender, e);
            }
        }

        /// <summary>
        /// Occurs when MCP list view is empty
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnMCPEmptyListing(object sender, EventArgs e)
        {
            if (EmptyListing != null)
            {
                EmptyListing(sender, e);
            }
        }

        internal static void OnNavigateReasonRequest(object sender, EventArgs e)
        {
            if (NavigateReasonRequest != null)
            {
                NavigateReasonRequest(sender, e);
            }
        }

        internal static void OnNavigateReasonAdjustment(object sender, EventArgs e)
        {
            if (NavigateReasonAdjustment != null)
            {
                NavigateReasonAdjustment(sender, e);
            }
        }

        internal static void OnNavigateReasonStatus(object sender, EventArgs e)
        {
            if (NavigateReasonStatus != null)
            {
                NavigateReasonStatus(sender, e);
            }
        }

        
        /// <summary>
        /// Occurs when Configure Notes Click on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateConfigureNotes(object sender, EventArgs e)
        {
            if (NavigateConfigureNotes != null)
            {
                NavigateConfigureNotes(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Apply ssn masking Click on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateSsnMasking(object sender, EventArgs e)
        {
            if (NavigateSsnMasking != null)
            {
                NavigateSsnMasking(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Apply location Click on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateLocationCountry(object sender, EventArgs e)
        {
            if (NavigateConfigureCountry != null)
            {
                NavigateConfigureCountry(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Default Configure Request Click on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateConfigureUnbillableRequest(object sender, EventArgs e)
        {
            if (NavigateConfigureUnbillableRequest != null)
            {
                NavigateConfigureUnbillableRequest(sender, e);
            }
        }

        #endregion
    }
}
