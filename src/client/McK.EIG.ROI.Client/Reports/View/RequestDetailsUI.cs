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
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Reports.View
{
    public partial class RequestDetailsUI : ReportBaseCriteriaUI, IReportCriteria
    {
        #region Constructor

        public RequestDetailsUI()
        {
            InitializeComponent();
            FirstCheckedListUI.Size = new System.Drawing.Size(285, 115);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Populates the data.
        /// </summary>
        public void PrePopulate()
        {
            PopulateRequestStatus();
            PopulateRequestorTypes();
            PopulateFacilities();
            DateRangeUI.Populate();
            base.SearchCriteriaPanel.AutoScroll = true;
        }

        public override void Localize()
        {
            base.Localize();
            //FirstCheckedListUI.TitleKey  = "reportListBox.RequestStatus";
            //SecondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            //ThirdCheckedListUI.TitleKey = "reportListBox.Facility";

            FirstCheckedListUI.TitleKey = "reportListBox.Facility";
            SecondCheckedListUI.TitleKey = "reportListBox.RequestStatus";
            ThirdCheckedListUI.TitleKey = "reportListBox.RequestorType";
            
            FirstCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.SetExecutionContext(Context);
            ThirdCheckedListUI.SetExecutionContext(Context);

            FirstCheckedListUI.Localize();
            SecondCheckedListUI.Localize();
            ThirdCheckedListUI.Localize();
            
        }

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public object GetData(object parameters)
        {
            Hashtable criteriaHt = (Hashtable)parameters;
            //StringBuilder checkedFirstListValues = FirstCheckedListUI.GetData();
            //StringBuilder checkedSecondValues    = SecondCheckedListUI.GetData();
            //StringBuilder checkedFirstListValues = SecondCheckedListUI.GetData();
            //StringBuilder checkedSecondValues = ThirdCheckedListUI.GetData();
            StringBuilder checkedFirstListValues = GetFacilityCodes("Code");
            StringBuilder checkedSecondListValues = SecondCheckedListUI.GetData();
            StringBuilder checkedThirdValues = ThirdCheckedListUI.GetData();
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportFacilityNames, GetFacilityCodes("Name"));
            criteriaHt.Add(ROIConstants.ReportRequestStatus, checkedSecondListValues.Replace("Pre-Billed", "PreBilled"));
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedThirdValues);
            DateRangeUI.GetData(criteriaHt);
            return criteriaHt;
        }


        /// <summary>
        /// Gets all requestor types
        /// </summary>
        private void PopulateRequestorTypes()
        {
            ThirdCheckedListUI.DisableEvents();
            ThirdCheckedListUI.CriteriaCheckedListBox.DataSource = RetrieveRequestorTypes();
            ThirdCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            ThirdCheckedListUI.CriteriaCheckedListBox.ValueMember = "id";
            ThirdCheckedListUI.EnableEvents();
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
        /// Populates request status
        /// </summary>
        private void PopulateRequestStatus()
        {
            SecondCheckedListUI.DisableEvents();
            IList requestStatus = EnumUtilities.ToList(typeof(RequestStatus));

            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.None));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.AuthReceived));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.AuthReceivedDenied));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Unknown));
            
            if (this.GetType() == typeof(RequestStatusSummaryUI))
            {
                requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Canceled));
                requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Denied));
            }

            SecondCheckedListUI.CriteriaCheckedListBox.DataSource = requestStatus;
            SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "value";
            SecondCheckedListUI.CriteriaCheckedListBox.ValueMember = "key";
            SecondCheckedListUI.EnableEvents();
        }

        private StringBuilder GetFacilityCodes(string type)
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                if (type.ToUpper().Equals("CODE"))
                {
                    checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Code));
                }
                else
                {
                    checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Name));
                }
            }
            return checkedItems;
        }

        /// <summary>
        /// Validates primary search criteria.
        /// </summary>
        /// <returns></returns>
        public override bool ValidatePrimaryFields()
        {
            return FirstCheckedListUI.HasCheckedItems() && SecondCheckedListUI.HasCheckedItems() && ThirdCheckedListUI.HasCheckedItems();
        }



        #endregion
    }
}
