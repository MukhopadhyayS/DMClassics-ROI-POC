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
using System.Collections;
using System.Collections.ObjectModel;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.RequestorType
{
    /// <summary>
    /// Holds the requestor type object detail UI
    /// </summary>
    public class RequestorTypeODP : AdminBaseODP
    {
        #region Methods

        /// <summary>
        /// Create Requestor Type.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object CreateEntity(ROIModel model)
        {
           return ROIAdminController.Instance.CreateRequestorType(model as RequestorTypeDetails);
           
        }

        /// <summary>
        /// Update Requestor Type.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object UpdateEntity(ROIModel model)
        {
            return ROIAdminController.Instance.UpdateRequestorType(model as RequestorTypeDetails);
        }

        /// <summary>
        /// Returns RequestorType ODP UI.
        /// </summary>
        /// <returns></returns>
        protected override IAdminBaseTabUI CreateEntityUI()
        {
            return new RequestorTypeTabUI();
        }


        /// <summary>
        /// Prepopulating Record Views, Billing Tiers, Billing Templates Value
        /// </summary>
        /// <param name="recordViews">Record View List</param>
        /// <param name="billingTiers">Billing Tier List</param>
        /// <param name="billingTemplates">Billing Template List</param>
        public void PrePopulate(Collection<RecordViewDetails> recordViews, 
                                Collection<BillingTierDetails> billingTiers,
                                Collection<BillingTemplateDetails> billingTemplates)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                RequestorTypeTabUI requestorTypeTabUI = (View as AdminBaseObjectDetailsUI).EntityTabUI as RequestorTypeTabUI;

                if (recordViews != null)
                {
                    requestorTypeTabUI.PopulateRecordViews(recordViews);
                }
                if (billingTiers != null)
                {
                    requestorTypeTabUI.PopulateBillingTiers(billingTiers);
                }         
                if (billingTemplates != null)
                {
                    requestorTypeTabUI.PopulateBillingTemplates(billingTemplates);
                }              
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        #endregion
    }
}
