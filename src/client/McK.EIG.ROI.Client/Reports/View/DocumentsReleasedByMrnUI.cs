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
using System.Text;
using System.Collections;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// Documents Released By MRN UI.
    /// </summary>
    public partial class DocumentsReleasedByMrnUI : ReportBaseCriteriaUI, IReportCriteria
    {
        #region Fileds

        #endregion

        #region Constructor

        public DocumentsReleasedByMrnUI()
        {
            InitializeComponent();
            FirstCheckedListUI.Visible = false;
            ThirdCheckedListUI.Visible = false;
            DateRangeUI.Visible        = false;
            MrnInfoPanel.Visible       = true;
            MrnTextBox.TextChanged    += delegate(object sender, EventArgs e)
                                        { ((ReportPane)this.Pane).ValidatePrimaryFields(); };
            SecondCheckedListUI.Size = new System.Drawing.Size(285, 148);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Populates Facilities
        /// </summary>
        private void PopulateRequestFacilities()
        {
            SecondCheckedListUI.DisableEvents();
            SecondCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.HPFFacilities;   //RetrieveHPFFacilities();
            SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            SecondCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
            SecondCheckedListUI.EnableEvents();
        }

        /// <summary>
        /// Localizes the UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            SecondCheckedListUI.TitleKey = "reportListBox.Facility";
            SecondCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.Localize();            
        }


        #region IReportCriteria Members

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public object GetData(object parameters)
        {
            Hashtable criteriaHt = (Hashtable)parameters;
            StringBuilder checkedFacilities = GetFacilityCodes(); 
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFacilities);
            criteriaHt.Add(ROIConstants.ReportMRN, MrnTextBox.Text);
            return criteriaHt;
        }

        /// <summary>
        /// Checks for required search criteria fields are filled.
        /// </summary>
        /// <returns></returns>
        public override bool ValidatePrimaryFields()
        {
            return SecondCheckedListUI.HasCheckedItems() && MrnTextBox.Text.Length > 2 ;
        }

        public void PrePopulate()
        {
            PopulateRequestFacilities();
        }


        private StringBuilder GetFacilityCodes()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Code));
            }

            return checkedItems;

        }

        #endregion

        #endregion
    }
}
