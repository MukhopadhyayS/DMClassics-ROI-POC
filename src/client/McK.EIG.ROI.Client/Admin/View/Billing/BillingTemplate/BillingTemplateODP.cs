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
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Collections.ObjectModel;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTemplate
{
    /// <summary>
    /// Holds the UI of BillingTemplateTabPane
    /// </summary>
    public class BillingTemplateODP : AdminBaseODP
    {
        #region Methods

        /// <summary>
        /// Create BillingTemplate.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object CreateEntity(ROIModel model)
        {
            model.Id = BillingAdminController.Instance.CreateBillingTemplate(model as BillingTemplateDetails);
            return model;
        }

        /// <summary>
        /// Update BillingTemplate.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object UpdateEntity(ROIModel model)
        {
            return BillingAdminController.Instance.UpdateBillingTemplate(model as BillingTemplateDetails);
        }

        /// <summary>
        /// Returns BillingTemplate ODP UI.
        /// </summary>
        protected override IAdminBaseTabUI CreateEntityUI()
        {
            return new BillingTemplateTabUI();
        }

        /// <summary>
        /// Gets the FeeType collection.
        /// </summary>
        public override void PrePopulate(object data)
        {
            ListDictionary feeTypes = data as ListDictionary;
            try
            {
                if (feeTypes != null)
                {
                    ((View as AdminBaseObjectDetailsUI).EntityTabUI as BillingTemplateTabUI).PopulateFeeTypes(feeTypes);
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
