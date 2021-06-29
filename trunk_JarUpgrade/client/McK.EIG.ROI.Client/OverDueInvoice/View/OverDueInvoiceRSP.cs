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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.OverDueInvoice.View.FindOverDueInvoice;

namespace McK.EIG.ROI.Client.OverDueInvoice.View
{
    /// <summary>
    /// Used to hold an Editor.
    /// </summary>
    public class OverDueInvoiceRSP : ROIRightSidePane
    {
        #region Fields

        private EventHandler navigateFindOverDueInvoiceHandler;
        private FindOverDueInvoiceEditor findOverDueInvoiceEditor;

        #endregion

        #region Methods

        /// <summary>
        ///  Event subscription.
        /// </summary>
        protected override void Subscribe()
        {
            navigateFindOverDueInvoiceHandler     = new EventHandler(Process_NavigateFindOverDueInvoice);
            
            OverDueInvoiceEvents.NavigatePastDueInvoiceByFacility  += navigateFindOverDueInvoiceHandler;
        }

        /// <summary>
        ///  Event unsubscription.
        /// </summary>
        protected override void Unsubscribe()
        {
            OverDueInvoiceEvents.NavigatePastDueInvoiceByFacility  -= navigateFindOverDueInvoiceHandler;
        }

        /// <summary>
        /// Process the event of FindInvoiceByFacility click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateFindOverDueInvoice(object sender, EventArgs e)
        {
            SetCurrentEditor(new FindOverDueInvoiceEditor());
        }

        #endregion
        
        #region Properties

        public FindOverDueInvoiceEditor FindInvoiceByFacilityEditor
        {
            get { return findOverDueInvoiceEditor;  }
        }

        #endregion
    }
}
