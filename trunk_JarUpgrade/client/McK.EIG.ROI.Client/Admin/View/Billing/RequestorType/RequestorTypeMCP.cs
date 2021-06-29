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
using System.ComponentModel;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.RequestorType
{
    /// <summary>
    /// Holds Requestor Type List UI.
    /// </summary>
    public class RequestorTypeMCP : AdminMCP
    {
        #region Methods

       /// <summary>
        /// Gets the requestor type collection from datastore.
        /// </summary>
        public void PrePopulate(Collection<BillingTierDetails> billingTierList, Collection<BillingTemplateDetails> billingTemplateList)                                
        {                                                       
            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<RequestorTypeDetails> requestorTypes = ROIAdminController.Instance.RetrieveAllRequestorTypes(true);
                                                        
                if (requestorTypes != null)
                {
                    RequestorTypeListUI requestorListUI = (adminListUI as RequestorTypeListUI);
                    requestorListUI.CacheBillingTiers(billingTierList);
                    requestorListUI.CacheBillingTemplates(billingTemplateList);

                    ComparableCollection<RequestorTypeDetails> list = new ComparableCollection<RequestorTypeDetails>(requestorTypes, new ImageIdComparer());
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestorTypeDetails))["Name"], ListSortDirection.Ascending);
                    requestorListUI.SetData(list);
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
        /// Returns the view of RequestorTypeMCP.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {  
            get 
            {
                return (adminListUI == null) ? new RequestorTypeListUI() : adminListUI;
            }
        }

        #endregion
    }
}
