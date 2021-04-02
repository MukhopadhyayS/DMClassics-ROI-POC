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

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.InvoiceDue
{
    /// <summary>
    /// Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class InvoiceDueEditor : ROIEditor
    {
        #region Methods

        public override void PrePopulate()
        {
            InvoiceDueMCP invoiceDueMCP = (InvoiceDueMCP)SubPanes[1];
            invoiceDueMCP.PrePopulate();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the InvoiceDue Editor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "invoicedue.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the InvoiceDue Editor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "invoicedue.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(InvoiceDueMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return null; }
        }

        #endregion

    }
}
