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
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.OverDueInvoice.Model;
using McK.EIG.ROI.Client.OverDueInvoice.Controller;

namespace McK.EIG.ROI.Client.OverDueInvoice.View.FindOverDueInvoice
{
    /// <summary>
    /// FindInvoiceByFacilitySearchUI
    /// </summary>
    public partial class FindOverDueInvoiceSearchUI : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(FindOverDueInvoiceSearchUI));

        private OverDueInvoiceSearchCriteria searchCriteria;
        private OverDueInvoiceSearchResult searchResult;
        private OverDueInvoiceValidator overDueInvoiceValidator;
        private Collection<RequestorTypeDetails> requestorTypes;

        //Event Declaration.
        private EventHandler keyPressHandler;

        //To set the default billing location value
        private int defaultBillingLocationindex;

        #endregion

        #region Constructor
        
        public FindOverDueInvoiceSearchUI()
        {
            InitializeComponent();
            keyPressHandler = new EventHandler(Process_KeyPressedHandler);
            overDueInvoiceValidator = new OverDueInvoiceValidator();
            requestorTypes = new Collection<RequestorTypeDetails>();
            EnableEvents();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Prepopulats the billing locations and requestor types.
        /// </summary>
        public void PrePopulate()
        {
            DisableEvents();

            List<FacilityDetails> facilityDetailsList = new List<FacilityDetails>(UserData.Instance.SortedFacilities);

            facilityCheckedListBox.DataSource = facilityDetailsList;
            facilityCheckedListBox.DisplayMember = "Name";            

            if (facilityDetailsList.Count > 0)
            {
                //CR# 359164
                facilityCheckedListBox.SelectedIndex = 0;
                defaultBillingLocationindex = facilityDetailsList.FindIndex(delegate(FacilityDetails facility) { return facility.IsDefault; });

                //CR# 359123
                if (defaultBillingLocationindex >= 0)
                {
                    facilityCheckedListBox.SetItemChecked(defaultBillingLocationindex, true);
                }
            }

            requestorTypes = ROIAdminController.Instance.RetrieveAllRequestorTypes(false);

            requestorCheckedListBox.DataSource = requestorTypes;
            requestorCheckedListBox.DisplayMember = "Name";
            requestorCheckedListBox.ValueMember = "Id";

            EnableEvents();

            if (requestorTypes.Count > 0)
            {
                ToggleSelection(requestorCheckedListBox, true);
                findInvoiceButton.Enabled = overDueInvoiceValidator.ValidateOverDueInvoiceSearch(GetData());
            }
        }

        /// <summary>
        /// Register events.
        /// </summary>
        private void EnableEvents()
        {
            selectAllFacilityButton.Click += keyPressHandler;
            clearAllFacilityButton.Click  += keyPressHandler;
            selectAllReqButton.Click += keyPressHandler;
            clearAllReqButton.Click += keyPressHandler;
            facilityCheckedListBox.SelectedIndexChanged += keyPressHandler;
            facilityCheckedListBox.DoubleClick += keyPressHandler;
            requestorCheckedListBox.DoubleClick += keyPressHandler;
            requestorCheckedListBox.SelectedIndexChanged += keyPressHandler;
            requestorNameTextBox.TextChanged += keyPressHandler;
        }

        /// <summary>
        /// Unregister events.
        /// </summary>
        private void DisableEvents()
        {
            selectAllFacilityButton.Click -= keyPressHandler;
            clearAllFacilityButton.Click  -= keyPressHandler;
            selectAllReqButton.Click -= keyPressHandler;
            clearAllReqButton.Click -= keyPressHandler;
            facilityCheckedListBox.SelectedIndexChanged -= keyPressHandler;
            requestorCheckedListBox.SelectedIndexChanged -= keyPressHandler;            
            requestorNameTextBox.TextChanged -= keyPressHandler;
            facilityCheckedListBox.DoubleClick -= keyPressHandler;
            requestorCheckedListBox.DoubleClick -= keyPressHandler;
        }

        /// <summary>
        /// Enable search button based on the input search criteria.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_KeyPressedHandler(object sender, EventArgs e)
        {
            DisableEvents();
            OverDueInvoiceSearchCriteria criteria = GetData();            
            findInvoiceButton.Enabled = overDueInvoiceValidator.ValidateOverDueInvoiceSearch(criteria);
            EnableEvents();
        }

        /// <summary>
        /// Sets the default select button.
        /// </summary>
        public override void SetAcceptButton()
        {
            if (this.ParentForm != null && this.ParentForm.AcceptButton != findInvoiceButton)
            {
                this.ParentForm.AcceptButton = findInvoiceButton;
                ClearSearchCriteria();
            }
        }

        /// <summary>
        /// Clears the past due invoice search criteria.
        /// </summary>
        public void ClearSearchCriteria()
        {
            if (searchResult != null)
            {
                if (searchResult.SearchResult.Count == 0)
                {
                    resetButton.PerformClick();
                }
            }
        }

        /// <summary>
        /// Localize all UI labels.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control.Name.StartsWith("selectAll"))
            {
                return "selectAllButton";
            }
            if (control.Name.StartsWith("clearAll"))
            {
                return "clearAllButton";
            }
            else
            {
                return base.GetLocalizeKey(control);
            }
        }

        /// <summary>
        /// Localize all UI labels.
        /// </summary>
        public override void Localize()
        {   
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, billingLocationLabel);
            SetLabel(rm, overDueLabel);
            SetLabel(rm, requestorNameLabel);
            SetLabel(rm, requestorTypeLabel);
            SetLabel(rm, requiredLabel);
            
            SetLabel(rm, selectAllFacilityButton);
            SetLabel(rm, clearAllFacilityButton);
            
            SetLabel(rm, selectAllReqButton);
            SetLabel(rm, clearAllReqButton);
            SetLabel(rm, findInvoiceButton);
            SetLabel(rm, resetButton);
        }

        /// <summary>
        /// Retrieves the search criteria.
        /// </summary>
        /// <returns></returns>
        private OverDueInvoiceSearchCriteria GetData()
        {
            searchCriteria = new OverDueInvoiceSearchCriteria();
            searchCriteria.FacilityCode = new List<string>();

            if (facilityCheckedListBox.CheckedItems.Count > 0)
            {
                foreach (FacilityDetails facility in facilityCheckedListBox.CheckedItems)
                {
                    searchCriteria.FacilityCode.Add(facility.Code);
                }
            }

            searchCriteria.RequestorName = requestorNameTextBox.Text;

            searchCriteria.RequestorType = new List<long>();
            searchCriteria.RequestorTypeName = new List<string>();

            if (requestorCheckedListBox.CheckedItems.Count > 0)
            {
                foreach (RequestorTypeDetails requestor in requestorCheckedListBox.CheckedItems)
                {
                    searchCriteria.RequestorType.Add(requestor.Id);
                    searchCriteria.RequestorTypeName.Add(requestor.Name);
                }
            }

            searchCriteria.From = (!string.IsNullOrEmpty(overDueDaysRange.MinTextBox.Text.Trim())) ? 
                                    Convert.ToInt32(overDueDaysRange.MinTextBox.Text.Trim(), System.Threading.Thread.CurrentThread.CurrentUICulture) : 0;

            if (overDueDaysRange.MaxTextBox.Text.Trim().IndexOf('+') > 0)
            {
                searchCriteria.To = Convert.ToInt32(overDueDaysRange.MaxTextBox.Text.Trim().TrimEnd(new char[] { '+' }), System.Threading.Thread.CurrentThread.CurrentUICulture) + 1;
            }
            else
            {
                searchCriteria.To = (!string.IsNullOrEmpty(overDueDaysRange.MaxTextBox.Text.Trim())) ?
                                    Convert.ToInt32(overDueDaysRange.MaxTextBox.Text.Trim(), System.Threading.Thread.CurrentThread.CurrentUICulture) : 0;
            }
            
            searchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            return searchCriteria;
        }

        /// <summary>
        /// Method to display the icon with inline error.
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.InvalidRequestorName: return requestorNameTextBox;               
            }
            return null;
        }

        /// <summary>
        /// Occurs when user clicks on reset button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void resetButton_Click(object sender, EventArgs e)
        {
            ClearControls();
            findInvoiceButton.Enabled = overDueInvoiceValidator.ValidateOverDueInvoiceSearch(GetData());
            ApplicationEventArgs ae = new ApplicationEventArgs(null, this);
            OverDueInvoiceEvents.OnSearchReset(Pane, ae);
        }

        /// <summary>
        /// Clear all controls values.
        /// </summary>
        private void ClearControls()
        {            
            // CR#365249 - Check the facilitycount while click reset button
			if (facilityCheckedListBox.Items.Count > 0)
            {
                for (int index = 0; index <= facilityCheckedListBox.Items.Count - 1; index++)
                {
                    facilityCheckedListBox.SetItemChecked(index, false);
                }
                //To select the admin configured default billing location
                //CR# 359123
                if (defaultBillingLocationindex >= 0)
                {
                    facilityCheckedListBox.SetItemChecked(defaultBillingLocationindex, true);
                }
            }

            if (requestorTypes.Count > 0)
            {
                ToggleSelection(requestorCheckedListBox, true);
                findInvoiceButton.Enabled = overDueInvoiceValidator.ValidateOverDueInvoiceSearch(GetData());
            }
            requestorNameTextBox.Text = string.Empty;
            overDueDaysRange.RangeMinimum = 0;
            overDueDaysRange.RangeMaximum = 0;
            overDueDaysRange.MinTextBox.Text = overDueDaysRange.RangeMinimum.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            overDueDaysRange.MaxTextBox.Text = overDueDaysRange.RangeMaximum.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);           
            errorProvider.Clear();
        }
        
        /// <summary>
        /// Occurs when user clicks on search button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void findInvoiceButton_Click(object sender, EventArgs e)
        {
            findInvoiceButton.Focus();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            errorProvider.Clear();
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            try
            {
                searchResult = OverDueInvoiceController.Instance.FindOverDueInvoices(GetData());
                ApplicationEventArgs ae = new ApplicationEventArgs(searchResult, this);
                OverDueInvoiceEvents.OnInvoiceSearched(Pane, ae);
            }
            catch (ROIException cause)
            {
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }
       
        /// <summary>
        /// Occurs when user clicks on select all button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void selectAllFacilityButton_Click(object sender, EventArgs e)
        {
             ToggleSelection(facilityCheckedListBox, true);
        }

        /// <summary>
        /// Occurs when user clicks on clear all button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void clearAllFacilityButton_Click(object sender, EventArgs e)
        {
            ToggleSelection(facilityCheckedListBox, false); 
        }

        /// <summary>
        ///  Occurs when user clicks on select all button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void selectAllReqButton_Click(object sender, EventArgs e)
        {
            ToggleSelection(requestorCheckedListBox, true); 
        }

        /// <summary>
        /// Occurs when user clicks on clear all button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ClearAllReqButton_Click(object sender, EventArgs e)
        {
            ToggleSelection(requestorCheckedListBox, false);
        }

        private static void ToggleSelection(CheckedListBox checkedListBox, bool selectAll)
        {
            for (int i = 0; i < checkedListBox.Items.Count; i++)
            {
                checkedListBox.SetItemChecked(i, selectAll);
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the overdue search criteria.
        /// </summary>
        public OverDueInvoiceSearchCriteria SearchCriteria
        {
            get { return searchCriteria; }
        }

        #endregion
    }
}
