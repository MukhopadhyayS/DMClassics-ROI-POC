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
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportBaseCriteriaUI
    /// </summary>
    public partial class ReportBaseCriteriaUI : ROIBaseUI
    {

        #region Constructor

        /// <summary>
        /// ReportBaseCriteriaUI
        /// </summary>
        public ReportBaseCriteriaUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localizes UI
        /// </summary>
        public override void Localize()
        {   
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, reportCriteriaLabel);
            SetLabel(rm, mrnLabel);
            reportDateRangeUI.SetExecutionContext(Context);
            reportInputFieldUI.SetExecutionContext(Context); 
            reportDateRangeUI.Localize();
            reportInputFieldUI.Localize();
            reportFirstCheckedListUI.SetPane(Pane);
            reportSecondCheckedListUI.SetPane(Pane);
            reportThirdCheckedListUI.SetPane(Pane);
            reportDateRangeUI.SetPane(Pane);
            reportInputFieldUI.SetPane(Pane);
        }

        /// <summary>
        /// Retrieves requestor types from the server.
        /// </summary>
        /// <returns></returns>
        public Collection<RequestorTypeDetails> RetrieveRequestorTypes()
        {

            Collection<RequestorTypeDetails> requestorTypes = null;
            try
            {
                ROIViewUtility.MarkBusy(true);
                requestorTypes = ROIAdminController.Instance.RetrieveAllRequestorTypes();
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return requestorTypes;
        }

        /// <summary>
        /// Retrives facilites.
        /// </summary>
        /// <returns></returns>
        //public static ArrayList RetrieveFacilities()
        //{
        //    ArrayList facilities = new ArrayList();
        //    ArrayList sortedFacilities = new ArrayList();
        //    facilities.AddRange(UserData.Instance.Facilities);
        //    facilities.Sort();
        //    sortedFacilities.AddRange(facilities);

        //    facilities.Clear();
        //    facilities.AddRange(UserData.Instance.FreeformFacilities);
        //    facilities.Sort();
        //    sortedFacilities.AddRange(facilities);

        //    return sortedFacilities;
        //}

        /// <summary>
        /// Gets the control for showing error info.
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            return reportDateRangeUI.EndDateTimePicker;
        }

        /// <summary>
        /// Clears the controls
        /// </summary>
        public virtual void ClearControls()
        {
            SecondCheckedListUI.ClearControls();
            FirstCheckedListUI.ClearControls();
            ThirdCheckedListUI.ClearControls();
            DateRangeUI.ClearControls();
        }

        /// <summary>
        /// Validates primary search criteria.
        /// </summary>
        /// <returns></returns>
        public virtual bool ValidatePrimaryFields()
        {
            if (reportInputFieldisRequired)
            {
                return FirstCheckedListUI.HasCheckedItems() && reportDateRangeUI.IsValidDateRange && reportInputFieldUI.IsValidField();//&& SecondCheckedListUI.HasCheckedItems()
            }
            else
            {
                return FirstCheckedListUI.HasCheckedItems() && SecondCheckedListUI.HasCheckedItems() && reportDateRangeUI.IsValidDateRange;
            }
        }

        private void balanceDueTextBox_TextChanged(object sender, EventArgs e)
        {
            ReportEvents.OnInputFieldChanged(Pane, e);
        }

        private void balanceDueTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }


        #endregion
        public static bool reportInputFieldisRequired;
        public static bool isOutstandingReport;
        #region Properties

        /// <summary>
        /// Returns ReportCheckedListUI
        /// </summary>
        protected ReportCheckedListUI FirstCheckedListUI
        {
            get { return reportFirstCheckedListUI; }
        }

        /// <summary>
        /// Returns ReportCheckedListUI
        /// </summary>
        protected ReportCheckedListUI SecondCheckedListUI
        {
            get { return reportSecondCheckedListUI; }
        }

        /// <summary>
        /// Returns ReportCheckedListUI
        /// </summary>
        protected ReportCheckedListUI ThirdCheckedListUI
        {
            get { return reportThirdCheckedListUI; }
        }

        /// <summary>
        /// Returns ReportDateRangeUI
        /// </summary>
        protected ReportDateRangeUI DateRangeUI
        {
            get { return reportDateRangeUI; }
        }

        public ReportInputFieldUI InputFieldUI
        {
            get { return reportInputFieldUI; }
        }
        /// <summary>
        /// SearchCriteriaPanel
        /// </summary>
        protected Panel SearchCriteriaPanel
        {
            get { return searchCriteriaPanel; }
        }

        /// <summary>
        /// Returns mrnInfoPanel
        /// </summary>
        protected Panel MrnInfoPanel
        {
            get { return mrnInfoPanel; }
        }

        /// <summary>
        /// Returns MrnTextBox
        /// </summary>
        public TextBox MrnTextBox
        {
            get { return mrnTextBox; }
        }

        protected Label FromLabel
        {
            get { return fromStatusLabel; }
        }


        protected Label ToLabel
        {
            get { return statusToLabel; }
        }

        protected Label BalanceDueLabel
        {
            get { return balanceDueLabel; }
        }

        protected Label DollarLabel
        {
            get { return dollarLabel; }
        }

        protected Label AgingLabel
        {
            get { return agingLabel; }
        }

        /// <summary>
        /// Returns status combo.
        /// </summary>
        protected ComboBox StatusCombo
        {
            get { return statusComboBox; }
        }

        /// <summary>
        /// Returns status combo.
        /// </summary>
        protected ComboBox StatusToCombo
        {
            get { return statusToComboBox; }
        }

        protected ComboBox BalanceDueComboBox
        {
            get { return balanceDueComboBox; }
        }

        protected TextBox BalanceDueTextBox
        {
            get { return balanceDueTextBox; }
        }

        /// <summary>
        /// Returns status panel.
        /// </summary>
        protected Panel StatusPanel
        {
            get { return statusPanel; }
        }

        /// <summary>
        /// Returns status panel.
        /// </summary>
        protected Panel StatusToPanel
        {
            get { return statusToPanel; }
        }

        /// <summary>
        /// Returns status panel.
        /// </summary>
        protected Panel BalanceDuePanel
        {
            get { return balanceDuePanel; }
        }

        /// <summary>
        /// Returns status panel.
        /// </summary>
        protected Panel RangeBarPanel
        {
            get { return rangeBarPanel; }
        }

        protected McK.EIG.ROI.Client.Base.View.Common.RangeBar RangeBar
        {
            get { return rangeBar; }
        }

        protected ReportDateRangeUI ReportDateRangeUI
        {
            get { return reportDateRangeUI; }
        }

        #endregion
    }
}
