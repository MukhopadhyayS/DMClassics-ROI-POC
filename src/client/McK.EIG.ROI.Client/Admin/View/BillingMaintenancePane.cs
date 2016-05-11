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

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Used to group collection of Navigation links.
    /// </summary>
    public class BillingMaintenancePane : NavigationPane
    {
        #region Methods

        protected override void ProcessNavigation(string selected, ApplicationEventArgs ae)
        {
            if (selected == ROIConstants.AdminBillingMediaType)
            {
                AdminEvents.OnNavigateMediaType(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingFeeType)
            {
                AdminEvents.OnNavigateFeeType(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingTier)
            {
                AdminEvents.OnNavigateBillingTier(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingRequestorType)
            {
                AdminEvents.OnNavigateRequestorType(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingDeliveryMethod)
            {
                AdminEvents.OnNavigateDeliveryMethod(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingPaymentMethod)
            {
                AdminEvents.OnNavigatePaymentMethod(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingPageWeight)
            {
                AdminEvents.OnNavigatePageWeight(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingInvoiceDue)
            {
                AdminEvents.OnNavigateInvoiceDue(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingSalesTaxPerFacility)
            {
                AdminEvents.OnNavigateSalesTaxPerFacility(this, ae);
            }
            else if (selected == ROIConstants.AdminBillingTemplate)
            {
                AdminEvents.OnNavigateBillingTemplate(this, ae);
            }
        }

        #endregion

        #region Properties

        protected override Collection<string> LinkKeys
        {
            get
            {
                Collection<string> links = new Collection<string>();
                links.Add(ROIConstants.AdminBillingMediaType);
                links.Add(ROIConstants.AdminBillingTier);
                links.Add(ROIConstants.AdminBillingFeeType);
                links.Add(ROIConstants.AdminBillingTemplate);
                links.Add(ROIConstants.AdminBillingRequestorType);
                links.Add(ROIConstants.AdminBillingPageWeight);
                links.Add(ROIConstants.AdminBillingDeliveryMethod);
                links.Add(ROIConstants.AdminBillingPaymentMethod);
                links.Add(ROIConstants.AdminBillingInvoiceDue);
                links.Add(ROIConstants.AdminBillingSalesTaxPerFacility);

                return links;
            }
        }

        public override string Title
        {
            get { return ROIConstants.AdminBilling; }
        }

        #endregion
    }
}
