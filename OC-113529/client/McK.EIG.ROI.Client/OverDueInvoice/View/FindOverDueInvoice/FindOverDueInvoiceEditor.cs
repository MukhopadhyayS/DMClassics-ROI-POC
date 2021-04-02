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
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

using McK.EIG.ROI.Client.OverDueInvoice.Model;

namespace McK.EIG.ROI.Client.OverDueInvoice.View.FindOverDueInvoice
{
    /// <summary>
    ///  Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class FindOverDueInvoiceEditor : ROIEditor
    {
        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();            
            base.hOuterSplitContainer.SplitterDistance = 50;            
        }

        /// <summary>
        /// PrePopulate the billing locations.
        /// </summary>
        public override void PrePopulate()
        {
            try
            {
                ((FindOverDueInvoiceSearchUI)MCP.View).PrePopulate();
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the title text of the Find Past Due Invoice.
        /// </summary>
        protected override string TitleText
        {
            get { return "findOverDueInvoice.header.title"; }
        }

        /// <summary>
        /// Gets the Information text of the Find Past Due Invoice.
        /// </summary>
        protected override string InfoText
        {
            get { return "findOverDueInvoice.header.info"; }
        }

        /// <summary>
        /// Gets the type of the MCP.
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(FindOverDueInvoiceMCP); }
        }

        /// <summary>
        /// Gets the type of the ODP.
        /// </summary>
        protected override Type ODPType
        {
            get { return typeof(FindOverDueInvoiceODP); }
        }
        
        /// <summary>
        /// Gets the past due invoice search criteria.
        /// </summary>
        public OverDueInvoiceSearchCriteria SearchCriteria
        {
            get { return (MCP.View as FindOverDueInvoiceSearchUI).SearchCriteria; } 
        }

        #endregion
    }
}
