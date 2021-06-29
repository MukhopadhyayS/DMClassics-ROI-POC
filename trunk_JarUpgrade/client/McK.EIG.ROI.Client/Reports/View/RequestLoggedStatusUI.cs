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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;
using McK.EIG.ROI.Client.Reports.View;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportLoggedStatusUI
    /// </summary>
    public partial class RequestLoggedStatusUI : ReportBaseCriteriaUI,IReportCriteria
    {

        #region Fields

       

        #endregion

        #region Constructor

        public RequestLoggedStatusUI()
        {
            InitializeComponent();
            ThirdCheckedListUI.Visible = false;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Populates the data.
        /// </summary>
        public void PrePopulate()
        {
            PopulateRequestorTypes();
            PopulateFacilities();
            DateRangeUI.Populate();
            FirstCheckedListUI.Size = new System.Drawing.Size(285, 148);
            SecondCheckedListUI.Size = new System.Drawing.Size(285, 148);
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
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportFacilityNames, GetFacilityCodes("Name"));
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedSecondListValues);
            DateRangeUI.GetData(parameters);
            return parameters;
        }

        /// <summary>
        /// Localizes UI
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            //FirstCheckedListUI.TitleKey = "reportListBox.RequestorType";
            //SecondCheckedListUI.TitleKey = "reportListBox.Facility";
            SecondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            FirstCheckedListUI.TitleKey = "reportListBox.Facility";

            FirstCheckedListUI.SetExecutionContext(Context);
            FirstCheckedListUI.Localize();
            SecondCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.Localize();
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

        /// <summary>
        /// Checks for required search criteria fields are filled.
        /// </summary>
        /// <returns></returns>
        public override bool ValidatePrimaryFields()
        {
            return SecondCheckedListUI.HasCheckedItems() && FirstCheckedListUI.HasCheckedItems();
        }

        /// <summary>
        /// Reset the data to the default values.
        /// </summary>
        public override void ClearControls()
        {
            FirstCheckedListUI.ClearControls();
            SecondCheckedListUI.ClearControls();
            DateRangeUI.ClearControls();
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

        #endregion
    }
}
