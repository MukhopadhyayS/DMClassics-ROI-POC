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
using System.Collections.Specialized;
using System.Collections.ObjectModel;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTemplate
{
    /// <summary>
    /// Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class BillingTemplateEditor : AdminEditor
    {
        #region Properties

        /// <summary>
        /// Used to get the Title text of the BillingTemplate Editor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "billingtemplate.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the BillingTemplate Editor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "billingtemplate.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(BillingTemplateMCP); }
        }

        protected override Type ODPType
        {
            get { return typeof(BillingTemplateODP); }
        }

        #endregion

        #region Methods

        public override void PrePopulate()
        {
            
            Collection<FeeTypeDetails> feeTypeList = BillingAdminController.Instance.RetrieveAllFeeTypes(false);
            ListDictionary feeTypes = new ListDictionary();
            foreach (FeeTypeDetails feeType in feeTypeList)
            {
                feeTypes.Add(feeType.Id, feeType);
            }

            BillingTemplateODP odp = (BillingTemplateODP)SubPanes[2];
            odp.PrePopulate(feeTypes);

            BillingTemplateMCP mcp = (BillingTemplateMCP)SubPanes[1];
            mcp.PrePopulate(feeTypes);
        }

        #endregion

    }
}
