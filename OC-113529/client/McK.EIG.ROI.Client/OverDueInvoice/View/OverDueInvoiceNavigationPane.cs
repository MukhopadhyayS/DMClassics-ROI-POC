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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.OverDueInvoice.View
{
    /// <summary>
    /// PastDueInvoiceNavigationPane
    /// </summary>
    public class OverDueInvoiceNavigationPane : NavigationPane
    {
        #region Fields

        //private bool canAccessRequest;

        #endregion

        #region Methods

        protected override void InitView()
        {
            base.InitView();

            //navigationLinks.EnableLink(ROIConstants.PatientsInformation, ROIViewUtility.IsAllowed(ROISecurityRights.MasterPatientIndexSearch));

            //canAccessRequest = (ROIViewUtility.IsAllowed(ROISecurityRights.ROIViewRequest) ||
            //                    ROIViewUtility.IsAllowed(ROISecurityRights.ROICreateRequest) ||
            //                    ROIViewUtility.IsAllowed(ROISecurityRights.ROIModifyRequest));

            //EnableLinks(false);
        }

        protected override void ProcessNavigation(string selected, ApplicationEventArgs ae)
        {
            if (selected == ROIConstants.FindOverDueInvoice)
            {
                OverDueInvoiceEvents.OnNavigatePastDueInvoiceByFacility(this, ae);
            }
        }

        #endregion

        #region Properties

        protected override Collection<string> LinkKeys
        {
            get
            {
                Collection<string> links = new Collection<string>();
                links.Add(ROIConstants.FindOverDueInvoice);
                return links;
            }
        }

        public override string Title
        {
            get { return ROIConstants.OverDueInvoiceModuleName; }
        }

        #endregion
    }
}