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
using System.IO;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// PendingAgingSummary
    /// </summary>
    public partial class ROIBillingUI : ReportBaseCriteriaUI, IReportCriteria
    {
        #region Fields
        private static string subTitle;
        #endregion

        #region Constructor

        public ROIBillingUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        #region IReportCriteria Members

        /// <summary>
        /// Populates facilities and date types.
        /// </summary>
        public void PrePopulate()
        {
            //PopulateFacilities();
            DateRangeUI.Populate();
            ThirdCheckedListUI.Visible = false;
            SecondCheckedListUI.Visible = false;
            FirstCheckedListUI.Visible = false;
            //FirstCheckedListUI.Size = new System.Drawing.Size(285, 148);
        }

        public bool ValidatePrimaryFields()
        {
            bool returnVal = DateRangeUI.IsValidDateRange;
            return returnVal;
        }

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            //FirstCheckedListUI.TitleKey = "reportListBox.BillingLocation";
            //FirstCheckedListUI.SetExecutionContext(Context);
            //FirstCheckedListUI.Localize();
        }
        
        /// <summary>
        /// Gets all facilities and display in checked list box.
        /// </summary>
        //private void PopulateFacilities()
        //{
        //    FirstCheckedListUI.DisableEvents();
        //    FirstCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.SortedFacilities;   //RetrieveHPFFacilities();
        //    FirstCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
        //    FirstCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
        //    FirstCheckedListUI.EnableEvents();
        //}
     
        /// <summary>
        /// Gets data.
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public object GetData(object parameters)
        {
            Hashtable criteriaHt                  = (Hashtable)parameters;
            //StringBuilder checkedFirstListValues = GetFacilityCodes();
            //criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            DateRangeUI.GetData(criteriaHt);
            StringBuilder content = new StringBuilder();
            //char separator = '"';
            content.AppendLine("Date Range");
            if (!(criteriaHt[ROIConstants.ReportStartDate] == null && criteriaHt[ROIConstants.ReportEndDate] == null))
            content.AppendLine(criteriaHt[ROIConstants.ReportStartDate].ToString() + " - " + criteriaHt[ROIConstants.ReportEndDate].ToString());
            subTitle = content.ToString();

            return criteriaHt;
        }

        public void CreateSubtitleFile(string filePath)
        {
            try
            {
                if (File.Exists(filePath))
                {
                    File.Delete(filePath);
                }
                File.AppendAllText(filePath, subTitle);
            }
            catch (Exception e)
            {
                throw new ROIException(ROIErrorCodes.SubtitleDatasourceFailed);
            }
        }
        //private StringBuilder GetFacilityCodes()
        //{  
        //    StringBuilder checkedItems = new StringBuilder();
        //    for (int index = 0; index < FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
        //    {
        //        if (index != 0)
        //        {
        //            checkedItems.Append(",");
        //        }
        //        checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Code));
        //    }

        //    return checkedItems;

        //}

        #endregion

        #endregion
    }
}
