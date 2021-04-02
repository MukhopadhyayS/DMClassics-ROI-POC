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
using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// PendingAgingSummary
    /// </summary>
    public partial class PendingAgingSummaryUI : ReportBaseCriteriaUI, IReportCriteria
    {
        #region Fields
        #endregion

        #region Constructor

        public PendingAgingSummaryUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        #region IReportCriteria Members

        /// <summary>
        /// Populates facilities, requestor types and date types.
        /// </summary>
        public void PrePopulate()
        {
            PopulateFacilities();
            PopulateRequestorTypes();
            DateRangeUI.Populate();
            ThirdCheckedListUI.Visible = false;
            FirstCheckedListUI.Size = new System.Drawing.Size(285, 148);
            SecondCheckedListUI.Size = new System.Drawing.Size(285, 148);
        }

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            FirstCheckedListUI.TitleKey = "reportListBox.BillingLocation";
            SecondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            FirstCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.SetExecutionContext(Context);
            FirstCheckedListUI.Localize();
            SecondCheckedListUI.Localize();
        }
        
        /// <summary>
        /// Gets all facilities and display in checked list box.
        /// </summary>
        private void PopulateFacilities()
        {
            FirstCheckedListUI.DisableEvents();
            FirstCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.HPFFacilities;   //RetrieveHPFFacilities();
            FirstCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            FirstCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
            FirstCheckedListUI.EnableEvents();
        }
     
        /// <summary>
        /// Gets all requestor types
        /// </summary>
        private void PopulateRequestorTypes()
        {
            SecondCheckedListUI.DisableEvents();
            SecondCheckedListUI.CriteriaCheckedListBox.DataSource    = RetrieveRequestorTypes();
            SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            SecondCheckedListUI.CriteriaCheckedListBox.ValueMember   = "id";
            SecondCheckedListUI.EnableEvents();
        }

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public object GetData(object parameters)
        {
            Hashtable criteriaHt                  = (Hashtable)parameters;
            StringBuilder checkedFirstListValues = GetFacilityCodes();
            StringBuilder checkedSecondListValues = SecondCheckedListUI.GetData();
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedSecondListValues);
            DateRangeUI.GetData(criteriaHt);

            return criteriaHt;
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

        #endregion
    }
}
