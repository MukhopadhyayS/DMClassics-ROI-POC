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
using System.Drawing;
using System.Collections.Generic;
using System.Collections.ObjectModel;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.RequestorType
{
    /// <summary>
    /// Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class RequestorTypeEditor : AdminEditor
    {

        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();
            hOuterSplitContainer.SplitterDistance = 63;
        }

        /// <summary>
        ///  Prepopulate the RecordViews, BillingTiers, BillingTemplates value
        /// </summary>
        public override void PrePopulate()
        {

            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<RecordViewDetails> recordViewList = UserData.Instance.RecordViews;
                Collection<BillingTierDetails> billingTierList = BillingAdminController.Instance.RetrieveAllBillingTiers(false);
                Collection<BillingTemplateDetails> billingTemplateList = BillingAdminController.Instance.RetrieveAllBillingTemplates(false);
                RequestorTypeODP odp = (RequestorTypeODP)SubPanes[2];
                odp.PrePopulate(recordViewList, billingTierList, billingTemplateList);
                RequestorTypeMCP mcp = (RequestorTypeMCP)SubPanes[1];
                mcp.PrePopulate(billingTierList, billingTemplateList);
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

        #region Properties

        /// <summary>
        /// Used to get the Title text of the RequestorType Editor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "requestortype.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the RequestorTypeEditor Editor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "requestortype.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(RequestorTypeMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return typeof(RequestorTypeODP); }
        }

        /// <summary>
        /// Resize the MCP Panel Auto Scroll margin
        /// </summary>
        protected override Nullable<Size> MCPAutoScrollMargin
        {
            get { return new Size(100, 20); }
        }

        /// <summary>
        /// Resize the MCP Panel Auto Scroll minimum size
        /// </summary>
        protected override Nullable<Size> MCPAutoScrollMinSize
        {
            get { return new Size(100, 20); }
        }

        #endregion
    }
}
