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

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Request.View;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    public class AccountManagementMCP : ROIBasePane
    {
        #region Fields

        private AccountManagementUI accountManagementUI;
        private EventHandler accountManagementGridReferesh;        

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the view of AccountManagementUI
        /// </summary>
        protected override void InitView()
        {
            accountManagementUI = new AccountManagementUI();
            //requestorLetterHistoryUI.ApplySecurityRights();
        }

        /// <summary>
        /// Prepopulates the data.
        /// </summary>
        /// <param name="requestorDetails"></param>
        public void PrePopulate(RequestorDetails requestor)
        {
            ComparableCollection<RequestInvoiceDetail> list = RetrieveRequestorInvoices(requestor.Id);

            if (list.Count > 0)
            {
                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                accountManagementUI.SetData(list, requestor);
            }
            else
            {
                accountManagementUI.SetData(null, requestor);
            }
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            Unsubscribe();
            accountManagementGridReferesh = new EventHandler(Process_AccountManagementGridRefresh);
            RequestEvents.AccountManagementGridReferesh += accountManagementGridReferesh;  
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestEvents.AccountManagementGridReferesh -= accountManagementGridReferesh;
        }

        /// <summary>
        /// Event occurs when request info created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_AccountManagementGridRefresh(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                RequestorDetails requestorInfo = ((AccountManagementEditor)ParentPane).RequestorInfo;
                RequestorDetails requestRequestorInfo = (RequestorDetails)ae.Info;
                if (requestorInfo.Id == requestRequestorInfo.Id)
                {
                    PrePopulate((RequestorDetails)ae.Info);
                }
            }
        }

        /// <summary>
        /// Retrieves the requestor invioces.
        /// </summary>
        /// <param name="requestorId"></param>
        private ComparableCollection<RequestInvoiceDetail> RetrieveRequestorInvoices(long requestorId)
        {
            Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(requestorId);
            ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(reqInvoices);
            return list;
        }
    
        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of AccountManagementUI
        /// </summary>
        public override Component View
        {
            get { return accountManagementUI; }
        }

        #endregion
    }
}
