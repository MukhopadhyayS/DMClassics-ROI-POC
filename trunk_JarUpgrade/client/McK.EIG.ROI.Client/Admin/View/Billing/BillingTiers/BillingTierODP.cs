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
using System.Collections.ObjectModel;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{
    /// <summary>
    /// Holds the UI of BillingTierTabPane
    /// </summary>
    public class BillingTierODP : AdminBaseODP
    {
         #region Methods

        /// <summary>
        /// Create BillingTier.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object CreateEntity(ROIModel model)
        {
            return BillingAdminController.Instance.CreateBillingTier(model as BillingTierDetails) as ROIModel ;
        }

        /// <summary>
        /// Update BillingTier.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object UpdateEntity(ROIModel model)
        {
            return BillingAdminController.Instance.UpdateBillingTier(model as BillingTierDetails);
        }

        /// <summary>
        /// Returns BillingTier ODP UI.
        /// </summary>
        protected override IAdminBaseTabUI CreateEntityUI()
        {
            return new BillingTierTabUI();
        }

        /// <summary>
        /// PrePopulate Media Types
        /// </summary>
        /// <param name="data"></param>
        public override void PrePopulate(object data)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<MediaTypeDetails> mediaTypes = data as Collection<MediaTypeDetails>;

                if (mediaTypes != null)
                {
                    ((View as AdminBaseObjectDetailsUI).EntityTabUI as BillingTierTabUI).PopulateMediaType(mediaTypes);
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

