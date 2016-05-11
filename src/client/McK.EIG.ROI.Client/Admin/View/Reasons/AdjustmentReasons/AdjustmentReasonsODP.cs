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

namespace McK.EIG.ROI.Client.Admin.View.Reasons.AdjustmentReasons
{
    /// <summary>
    /// Holds the UI of AdjustmentReasonTabPane
    /// </summary>
    public class AdjustmentReasonsODP : AdminBaseODP
    {
        #region Methods

        /// <summary>
        /// Create Adjustment Reason.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object CreateEntity(ROIModel model)
        {
            model.Id = ROIAdminController.Instance.CreateReason(model as ReasonDetails);
            return model;
        }
        
        /// <summary>
        /// Update Adjustment Reason.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object UpdateEntity(ROIModel model)
        {
            return ROIAdminController.Instance.UpdateReason(model as ReasonDetails);
        }
             
        /// <summary>
        /// Returns AdjustmentReason ODP UI.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        protected override IAdminBaseTabUI CreateEntityUI()
        {
            return new AdjustmentReasonsTabUI();
        }

        #endregion
    }
}
