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
using System.Collections.ObjectModel;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// PostedPaymentSummaryUI - Follows the same view as 'Outstanding Invoice balances' report type.
    /// </summary>
    public partial class PostedPaymentSummaryUI : ReportBaseCriteriaUI, IReportCriteria
    {
        #region Constructor

        public PostedPaymentSummaryUI()
        {
            InitializeComponent();
            ThirdCheckedListUI.Visible = false;
            FirstCheckedListUI.Size = new System.Drawing.Size(285, 148);
            SecondCheckedListUI.Size = new System.Drawing.Size(285, 148);
        }

        #endregion Constructor
       
        #region Methods

        /// <summary>
        /// Populates the data.
        /// </summary>
        public void PrePopulate()
        {
            PopulateRequestFacilities();
            PopulateRequestorStatus();
            DateRangeUI.Populate();
        }

        /// <summary>
        /// Localizes the UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            FirstCheckedListUI.TitleKey = "reportListBox.BillingLocation";
            SecondCheckedListUI.TitleKey = "reportListBox.RequestStatus";
            FirstCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.SetExecutionContext(Context);
            FirstCheckedListUI.Localize();
            SecondCheckedListUI.Localize();
        }

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public object GetData(object parameters)
        {
            Hashtable criteriaHt = (Hashtable)parameters;
            StringBuilder checkedFirstListValues = GetFacilityCodes(); 
            StringBuilder checkedSecondValues = SecondCheckedListUI.GetData();
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportRequestStatus, checkedSecondValues);
            DateRangeUI.GetData(criteriaHt);
            return criteriaHt;
        }

        /// <summary>
        /// Populates Facilities
        /// </summary>
        private void PopulateRequestFacilities()
        {
            FirstCheckedListUI.DisableEvents();
            FirstCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.SortedFacilities;   //RetrieveFacilities();
            FirstCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            FirstCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
            FirstCheckedListUI.EnableEvents();
        }

        /// <summary>
        /// Populates all Request status.
        /// </summary>
        private void PopulateRequestorStatus()
        {
            SecondCheckedListUI.DisableEvents();
            IList requestStatus = EnumUtilities.ToList(typeof(RequestStatus));

            if (this.GetType() != typeof(PostedPaymentSummaryUI))
            {
                requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Denied));
                requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Canceled));
            }

            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.None));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.AuthReceived));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.AuthReceivedDenied));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.PreBilled));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Unknown));

            SecondCheckedListUI.CriteriaCheckedListBox.DataSource = requestStatus;
            SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "value";
            SecondCheckedListUI.CriteriaCheckedListBox.ValueMember   = "key";
            
            
            SecondCheckedListUI.EnableEvents();
        }

        private StringBuilder GetFacilityCodes()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }                
                checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Code));                
            }

            return checkedItems;

        }

        #endregion 
    }
}
