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
using System.Drawing;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{
    /// <summary>
    /// Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class BillingTierEditor : AdminEditor
    {
        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();
            base.hOuterSplitContainer.SplitterDistance = 63;            
        }

        /// <summary>
        ///  Prepopulate the Billing Tier details.
        /// </summary>
        public override void PrePopulate()
        {
            Collection<MediaTypeDetails> mediaTypes = BillingAdminController.Instance.RetrieveAllMediaTypes(false);

            BillingTierODP odp = (BillingTierODP)SubPanes[2];
            odp.PrePopulate(mediaTypes);

            BillingTierMCP mcp = (BillingTierMCP)SubPanes[1];
            mcp.PrePopulate(mediaTypes);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the BillingTierEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "billingtier.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the BillingTierEditor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "billingtier.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(BillingTierMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        protected override Type ODPType
        {
           get { return typeof(BillingTierODP); }
        }

        protected override Nullable<Size> MCPAutoScrollMargin
        {
            get
            {
                return new Size(300, 145);
            }
        }

        #endregion
    }
}
