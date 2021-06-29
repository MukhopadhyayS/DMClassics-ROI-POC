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
using System.Collections.Generic;
using System.Globalization;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;


namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// TurnAroundTimeUI
    /// </summary>
    public partial class TurnaroundTimeUI : ReportBaseCriteriaUI, IReportCriteria
    {
        #region Constructor

        public TurnaroundTimeUI()
        {
            InitializeComponent();
            StatusPanel.Visible = true;
            StatusToPanel.Visible = true;
            ThirdCheckedListUI.Visible = false;
            StatusCombo.SelectedIndexChanged +=new EventHandler(StatusCombo_SelectedIndexChanged);
            StatusToCombo.SelectedIndexChanged += new EventHandler(StatusCombo_SelectedIndexChanged);
            base.SearchCriteriaPanel.AutoScroll = true;
        }

        #endregion

        #region Methods

        #region IReportCriteria Members

        /// <summary>
        /// Populates Requestor Types, Users , status and data rages.
        /// </summary>
        public void PrePopulate()
        {
            PopulateRequestorTypes();
            PopulateStatus();
            PopulateToStatus();
            PopulateFacilities();
            DateRangeUI.Populate();
            FirstCheckedListUI.Size = new System.Drawing.Size(285, 115);
  			//Increase the height of user listbox
        }
        
        /// <summary>
        /// Gets the selected data.
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public object GetData(object parameters)
        {
            string[] statusValues = StatusCombo.Text.Split(new char[]{' '});
            Hashtable criteriaHt = (Hashtable)parameters;
            StringBuilder checkedFirstListValues = GetFacilityCodes("Code");
            //StringBuilder checkedSecondListValues = GetSelectedUsers();
            StringBuilder checkedSecondListValues = SecondCheckedListUI.GetData();
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            //criteriaHt.Add(ROIConstants.ReportFacilityNames, GetFacilityCodes("Name"));
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedSecondListValues);
            //criteriaHt.Add(ROIConstants.ReportRequestorType, checkedThirdValues);
            string fromStatusValues = StatusCombo.SelectedValue.ToString();
            string toStatusValues = StatusToCombo.SelectedValue.ToString();
            criteriaHt.Add(ROIConstants.FromStatus, fromStatusValues);
            criteriaHt.Add(ROIConstants.ToStatus, toStatusValues);
            criteriaHt.Add(ROIConstants.FromStatusValue, fromStatusValues);
            criteriaHt.Add(ROIConstants.ToStatusValue, toStatusValues);          

            DateRangeUI.GetData(criteriaHt);

            return criteriaHt;
        }

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <returns></returns>
        internal StringBuilder GetSelectedUsers()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                checkedItems.Append(((KeyValuePair<int, string>)SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Key);
            }

            return checkedItems;
        }


        #endregion

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            //FirstCheckedListUI.TitleKey  = "reportListBox.user";
            //SecondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            //ThirdCheckedListUI.TitleKey = "reportListBox.Facility";
            //SecondCheckedListUI.TitleKey = "reportListBox.user";
            SecondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            FirstCheckedListUI.TitleKey = "reportListBox.Facility";
            FirstCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.SetExecutionContext(Context);
            //ThirdCheckedListUI.SetExecutionContext(Context);
            FirstCheckedListUI.Localize();
            SecondCheckedListUI.Localize();
            //ThirdCheckedListUI.Localize();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, FromLabel);
            SetLabel(rm, ToLabel);
        }

        /// <summary>
        /// Gets all requestor types and assign to checked list box.
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
        /// Populates User names and IDs.
        /// </summary>
        //private void PopulateUsers()
        //{
        //    try
        //    {
        //        SecondCheckedListUI.DisableEvents();
        //        //Sort the user listbox
        //        SecondCheckedListUI.CriteriaCheckedListBox.Sorted = true;
        //        SecondCheckedListUI.CriteriaCheckedListBox.DataSource = ROIController.Instance.RetrieveUsers(new int[] { Convert.ToInt32(ROISecurityRights.ROIAccessApplication, System.Threading.Thread.CurrentThread.CurrentUICulture), 
        //                                                                                                            Convert.ToInt32(ROISecurityRights.ROIAdministration, System.Threading.Thread.CurrentThread.CurrentUICulture) 
        //                                                                                                          }
        //                                                                                                   );

        //        SecondCheckedListUI.CriteriaCheckedListBox.ValueMember = "key";
        //        SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Value";
        //        SecondCheckedListUI.EnableEvents();
        //    }
        //    catch (ROIException cause) //Handled exception if HPFW server is not connected.
        //    {
        //        ROIViewUtility.Handle(Context, cause);
        //    }
        //}

        /// <summary>
        /// Populates status.
        /// </summary>
        private void PopulateStatus()
        {
            StatusCombo.DisplayMember = "Value";
            StatusCombo.ValueMember   = "key";
            StatusCombo.DataSource = EnumUtilities.ToList(typeof(FromStatus));
        }

        private void PopulateToStatus()
        {
            StatusToCombo.DisplayMember = "Value";
            StatusToCombo.ValueMember = "key";
            StatusToCombo.DataSource = EnumUtilities.ToList(typeof(ToStatus));
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

        private void StatusCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        }

        /// <summary>
        /// Validates primary search criteria.
        /// </summary>
        /// <returns></returns>
        public override bool ValidatePrimaryFields()
        {
            return FirstCheckedListUI.HasCheckedItems() && SecondCheckedListUI.HasCheckedItems() && StatusCombo.SelectedIndex > 0 &&
                   StatusToCombo.SelectedIndex > 0;
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
