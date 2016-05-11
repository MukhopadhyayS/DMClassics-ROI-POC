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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;

using McK.EIG.Common.FileTransfer.Controller.Upload;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.SalesTaxPerFacility
{
    /// <summary>
    /// Holds the UI of Sales Tax Per Facility List Pane
    /// </summary>
    public class SalesTaxPerFacilityMCP : AdminMCP
    {
        #region Methods

        /// <summary>
        /// Subscribe the event
        /// </summary>
        protected override void Subscribe()
        {
            base.Subscribe();           
        }

        /// <summary>
        /// Populates configured tax rates per facility
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);                
                Collection<TaxPerFacilityDetails> taxPerFacilityDetails = BillingAdminController.Instance.RetrieveAllTaxPerFacilities();                
                if (taxPerFacilityDetails != null)
                {
                    ComparableCollection<TaxPerFacilityDetails> list = new ComparableCollection<TaxPerFacilityDetails>(taxPerFacilityDetails, new TaxPerFacilityComparer());
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(TaxPerFacilityDetails))["FacilityName"], ListSortDirection.Ascending);
                    adminListUI.SetData(list);
                }
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
        /// Returns the view of SalesTaxPerFacilityMCP.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get 
            {
                return (adminListUI == null) ? new SalesTaxPerFacilityListUI() : adminListUI;
            }
        }

        #endregion
    }
}
