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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.View.Billing.DeliveryMethod
{
    /// <summary>
    /// Holds the UI of DeliveryMethodPane
    /// </summary>
    public class DeliveryMethodODP : AdminBaseODP
    {
        #region Methods

        /// <summary>
        /// Create new DeliveryMethod
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object CreateEntity(ROIModel model)
        {
            model.Id = ROIAdminController.Instance.CreateDeliveryMethod(model as DeliveryMethodDetails);
            return model;
        }

        /// <summary>
        /// Update the details of selected DeliveryMethod
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object UpdateEntity(ROIModel model)
        {
            return ROIAdminController.Instance.UpdateDeliveryMethod(model as DeliveryMethodDetails);
        }

        /// <summary>
        /// Returns DeliveryMethod ODP UI
        /// </summary>
        protected override IAdminBaseTabUI CreateEntityUI()
        {
            return new DeliveryMethodTabUI();
        }

        #endregion
    }
}
