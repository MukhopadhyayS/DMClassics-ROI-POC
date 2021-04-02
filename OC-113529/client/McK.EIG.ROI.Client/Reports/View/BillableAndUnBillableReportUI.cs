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
using McK.EIG.ROI.Client.Reports.Model;
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Reports.View
{
    public partial class BillableAndUnBillableReportUI : ReportBaseCriteriaUI, IReportCriteria
    {
        #region Constructor

        public BillableAndUnBillableReportUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Populates the data.
        /// </summary>
        public void PrePopulate()
        {
            PopulateRequestorTypes();
            PopulateBillableType();
            PopulateFacilities();
            DateRangeUI.Populate();
            base.SearchCriteriaPanel.AutoScroll = true;
            FirstCheckedListUI.Size = new System.Drawing.Size(285, 120);
            ThirdCheckedListUI.Size = new System.Drawing.Size(285,100);
        }

        public override void Localize()
        {
            base.Localize();
            FirstCheckedListUI.TitleKey = "reportListBox.Facility";
            SecondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            ThirdCheckedListUI.TitleKey = "reportListBox.BillableAndUnBillable";

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
            StringBuilder checkedFirstListValues = GetFacilityCodes("Code");
            StringBuilder checkedSecondListValues = SecondCheckedListUI.GetData();
            StringBuilder checkedThirdValues = GetData();
            
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportFacilityNames, GetFacilityCodes("Name"));
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedSecondListValues);
            criteriaHt.Add(ROIConstants.ReportRequestType, checkedThirdValues);
            DateRangeUI.GetData(criteriaHt);
            return criteriaHt;
        }

        private StringBuilder GetData()
        {
            bool hasBillableCheck = false;
            bool hasUnBillableCheck = false;
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < ThirdCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                if ("Both".Contains(ThirdCheckedListUI.CriteriaCheckedListBox.GetItemText(
                                    ThirdCheckedListUI.CriteriaCheckedListBox.CheckedItems[index])))
                {
                    checkedItems.Clear();
                    checkedItems.Append("Both");
                    return checkedItems;
                }
                else if ("Unbillable".Contains(ThirdCheckedListUI.CriteriaCheckedListBox.GetItemText(
                                               ThirdCheckedListUI.CriteriaCheckedListBox.CheckedItems[index])))
                {
                    checkedItems.Append("Un-Billable");
                    hasUnBillableCheck = true;
                }
                else
                {
                    checkedItems.Append(ThirdCheckedListUI.CriteriaCheckedListBox.GetItemText(
                                        ThirdCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]));
                    hasBillableCheck = true;
                }
            }
            if (hasBillableCheck && hasUnBillableCheck)
            {
                checkedItems.Clear();
                checkedItems.Append("Both");
            }
            return checkedItems;
        }
 
        /// <summary>
        /// Gets all requestor types
        /// </summary>
        private void PopulateRequestorTypes()
        {
            SecondCheckedListUI.DisableEvents();
            SecondCheckedListUI.CriteriaCheckedListBox.DataSource = RetrieveRequestorTypes();
            SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            SecondCheckedListUI.CriteriaCheckedListBox.ValueMember = "id";
            SecondCheckedListUI.EnableEvents();
        }

        private void PopulateBillableType()
        {
            ThirdCheckedListUI.DisableEvents();
            IList billableTypes = EnumUtilities.ToList(typeof(BillableAndUnBillable));
            ThirdCheckedListUI.CriteriaCheckedListBox.DataSource = billableTypes;
            ThirdCheckedListUI.CriteriaCheckedListBox.DisplayMember = "value";
            ThirdCheckedListUI.CriteriaCheckedListBox.ValueMember = "key";
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
