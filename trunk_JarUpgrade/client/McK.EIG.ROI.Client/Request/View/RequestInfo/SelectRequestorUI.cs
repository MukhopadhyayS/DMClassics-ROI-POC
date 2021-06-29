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
using System.ComponentModel;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View.FindRequestor;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Request.Model;
using System.Collections.ObjectModel;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{

    /// <summary>
    /// Maintains requestor profile
    /// </summary>
    public partial class SelectRequestorUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;
        private FindRequestorEditor findRequestorEditor;
        private RequestorInfoEditor requestorInfoEditor;
        
        private EventHandler cancelHandler;
        private EventHandler selectRequestorHandler;

        private EIGDataGrid.RowSelectionHandler rowSelectChangeHandler;
        private EventHandler resetSearchHandler;

        private RequestDetails request;
        private PatientDetails matchedPatient;

        private bool isRequestorSaved;

        private bool matching;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the controls
        /// </summary>
        public SelectRequestorUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Initialize the action handler
        /// </summary>
        /// <param name="selectRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public SelectRequestorUI(EventHandler selectRequestorHandler, EventHandler cancelHandler, IPane pane)
            : this()
        {
            this.selectRequestorHandler = selectRequestorHandler;
            this.cancelHandler = cancelHandler;

            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
            InitFindRequestorTabPage();
            requestorProfileTabControl.TabPages[1].Enabled = false;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, selectRequestorButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, findRequestorTabPage);
            SetLabel(rm, requestorInfoTabPage);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, selectRequestorButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Gets the localized key for UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control is TabPage)
            {
                return base.GetLocalizeKey(control);
            }

            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets the localized key for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        public void SetData(object data)
        {
            if (data != null)
            {
                request = data as RequestDetails;
            }
        }

        /// <summary>
        /// Initialize the Find Requestor tabpage
        /// </summary>
        public void InitFindRequestorTabPage()
        {
            findRequestorEditor = new FindRequestorEditor();

            findRequestorEditor.Init(Context);
            findRequestorEditor.Localize();
            FindRequestorSearchUI searchUI = (FindRequestorSearchUI)findRequestorEditor.MCP.View;
            FindRequestorSearchUI.IsFindRequestor = true;
            findRequestorEditor.PrePopulate();
            findRequestorEditor.hOuterSplitContainer.SplitterDistance = 57;

            HeaderUI header = (HeaderUI)findRequestorEditor.HeaderPane.View;
            header.Title = string.Empty;
            header.TitlePanel.Visible = false;

            
            searchUI.NewRequestorHandler = new EventHandler(NewRequestorHandler);
            
            FindRequestorListUI listUI = (FindRequestorListUI)findRequestorEditor.ODP.View;
            listUI.ViewEditHandler = new EventHandler(ViewRequestorInfoHandler);
            listUI.HideCreateRequestButton = true;
            listUI.FooterPanelBackColor = Color.White;

            EnableEvents();
            SetRequestorTab(findRequestorTabPage, findRequestorEditor.View as Control);
            selectRequestorButton.Enabled = false;
            isRequestorSaved = false;
        }

        private void grid_RowsRemoved(object sender, DataGridViewRowsRemovedEventArgs e)
        {
            FindRequestorListUI listUI = (FindRequestorListUI)findRequestorEditor.ODP.View;
            selectRequestorButton.Enabled = listUI.grid.Items.Count > 0;
        }

        //internal void SetAcceptButton()
        //{
        //    FindRequestorSearchUI searchUI = (FindRequestorSearchUI)findRequestorEditor.MCP.View;
        //    searchUI.SetAcceptButton();
        //    ((Form)(this.Parent)).FormClosing += delegate { findRequestorEditor.ODP.Cleanup(); };
        //}

        public void CleanUp()
        {
            findRequestorEditor.ODP.Cleanup();
            if (requestorInfoEditor != null)
            {
                requestorInfoEditor.MCP.Cleanup();
            }
        }

        private void EnableEvents()
        {
            rowSelectChangeHandler = new EIGDataGrid.RowSelectionHandler(list_SelectionChanged);
            resetSearchHandler     = new EventHandler(Process_ResetSearchResult);          

            ((FindRequestorListUI)findRequestorEditor.ODP.View).RowSelectionChangeHandler += rowSelectChangeHandler;
            ((FindRequestorListUI)findRequestorEditor.ODP.View).grid.RowsRemoved+=new DataGridViewRowsRemovedEventHandler(grid_RowsRemoved);
            
            RequestorEvents.ResetSearch += resetSearchHandler;
        }

        private void DisableEvents()
        {
            ((FindRequestorListUI)findRequestorEditor.ODP.View).RowSelectionChangeHandler -= rowSelectChangeHandler;
            ((FindRequestorListUI)findRequestorEditor.ODP.View).grid.RowsRemoved -= new DataGridViewRowsRemovedEventHandler(grid_RowsRemoved);
            RequestorEvents.ResetSearch -= resetSearchHandler;
        }

        private void list_SelectionChanged(DataGridViewRow row)
        {
            if (row == null) return;
            selectRequestorButton.Enabled = ((RequestorDetails)row.DataBoundItem).IsActive;
        }

        private void Process_ResetSearchResult(object sender, EventArgs e)
        {
            requestorProfileTabControl.TabPages[1].Enabled = false;
            selectRequestorButton.Enabled = false;
        }

        /// <summary>
        /// Handler is used to show the requestor's information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ViewRequestorInfoHandler(object sender, EventArgs e)
        {
            RequestorDetails requestorInfo = findRequestorEditor.SelectedRequestor;
            InitRequestorInfoTabPage(requestorInfo);
            selectRequestorButton.Enabled = requestorInfo.IsActive;
            matching = false;
        }

        /// <summary>
        /// Hanndler is used to initialize the new requestor  
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void NewRequestorHandler(object sender, EventArgs e)
        {
            FindRequestorSearchUI searchUI = (FindRequestorSearchUI)findRequestorEditor.MCP.View;

            FindRequestorCriteria requestorCriteria = searchUI.GetData();
            matching = false;
            if (requestorCriteria.RequestorTypeId == RequestorDetails.PatientRequestorType)
            {
                Collection<PatientDetails> patients = searchUI.RetrieveMatchingPatients(requestorCriteria);
                if (patients != null)
                {
                    RequestorDetails requestor = ShowCreateNewPatientRequestorDialog(patients);
                    if (requestor != null)
                    {
                        InitRequestorInfoTabPage(requestor);
                        matching = true;
                    }
                }
            }
            else
            {
                RequestorEvents.OnCreateRequestor(findRequestorEditor.ODP, new ApplicationEventArgs(this, null));
                InitRequestorInfoTabPage(null);
                matching = false;
            }
        }

        /// <summary>
        /// Show Create New Patient Requestor Dialog
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        private RequestorDetails ShowCreateNewPatientRequestorDialog(Collection<PatientDetails> patients)
        {
            CreatePatientRequestorUI createPatientRequestorUI = new CreatePatientRequestorUI(Pane);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            createPatientRequestorUI.Header = new HeaderUI();
            createPatientRequestorUI.Header.Title = rm.GetString("createPatientRequestor.header.title");
            createPatientRequestorUI.Header.Information = rm.GetString("createPatientRequestor.header.info");

            createPatientRequestorUI.InfoLabel.Text = rm.GetString("createPatientRequestor.info");

            createPatientRequestorUI.SetData(patients);

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("createPatientRequestor.titlebar.title"), createPatientRequestorUI);
            DialogResult result = form.ShowDialog(this);

            RequestorDetails requestor = null;
            if (result == DialogResult.OK)
            {
                matchedPatient = createPatientRequestorUI.SelectedPatient;
                requestor = CreatePatientRequestorUI.ConvertToRequestor(matchedPatient);
            }

            form.Close();
            form.Dispose();

            return requestor;
        }

        /// <summary>
        /// Initialize the requestor info tabpage
        /// </summary>
        /// <param name="requestorInfo"></param>
        public void InitRequestorInfoTabPage(RequestorDetails requestorInfo)
        {
            selectRequestorButton.Enabled = (requestorInfo != null);
            requestorProfileTabControl.TabPages[1].Enabled = true;

            requestorInfoEditor = new RequestorInfoEditor();
            if (requestorInfo != null && requestorInfo.Id > 0)
            {
                requestorInfo = RetrieveRequestorInfo(requestorInfo);
            }
            requestorInfoEditor.RequestorInfo = requestorInfo;
            requestorInfoEditor.Init(Context);
            requestorInfoEditor.Localize();
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            requestorInfoEditor.PrePopulate();

            HeaderUI header = (HeaderUI)requestorInfoEditor.HeaderPane.View;
            header.Title = string.Empty;
            header.TitlePanel.Visible = false;

            requestorInfoEditor.UpperComponent.HeaderPanel.Height = 30;
            requestorInfoEditor.UpperComponent.BottomPanel.Height = 45;
            
            infoUI.SaveRequestorHandler = new EventHandler(SaveRequestorHandler);
            infoUI.CancelRequestorHandler = new EventHandler(CancelRequestorHandler);
            infoUI.DirtyDataHandler += new EventHandler(MarkDirty);
            infoUI.EnableEvents();

            HideFooterButtons(infoUI);
            infoUI.FooterPanelBackColor = Color.White;
            infoUI.AutoScroll = false;

            SetRequestorTab(requestorInfoTabPage, requestorInfoEditor.View as Control);
            requestorProfileTabControl.SelectedIndex = 1;
        }

        /// <summary>
        /// Handler is used to save the requestor's information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void SaveRequestorHandler(object sender, EventArgs e)
        {
            RequestorInfoUI infoUI = requestorInfoEditor.MCP.View as RequestorInfoUI;
            infoUI.RequestorInfo = infoUI.SaveRequestor();
            if (infoUI.RequestorInfo == null) return;
            infoUI.SetData(infoUI.RequestorInfo);
            HideFooterButtons(infoUI);
            isRequestorSaved = true;

            //if inactive requestor, return
            selectRequestorButton.Enabled = infoUI.RequestorInfo.IsActive;
        }

        /// <summary>
        /// Handler is used to cancel the requestor's information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CancelRequestorHandler(object sender, EventArgs e)
        {
            RequestorInfoUI infoUI = requestorInfoEditor.MCP.View as RequestorInfoUI;
            if (infoUI.RequestorInfo == null || infoUI.RequestorInfo.Id == 0)
            {
                infoUI.IsDirty = false;
                requestorProfileTabControl.SelectedTab = findRequestorTabPage;                
                requestorInfoTabPage.Enabled = false;
                matching = false;

            }
            else
            {
                infoUI.RevertRequestorInfo();
                HideFooterButtons(infoUI);
            }
        }

        private void MarkDirty(object sender, EventArgs e)
        {
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            infoUI.IsDirty = true;
            isRequestorSaved = false;
            selectRequestorButton.Enabled = infoUI.IsSaveButtonEnabled;
        }

        private static void HideFooterButtons(RequestorInfoUI infoUI)
        {
            infoUI.HideDeleteRequestorButton = true;
            infoUI.HideCreateRequestButton = true;
        }

        private RequestorDetails RetrieveRequestorInfo(RequestorDetails requestor)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                requestor = RequestorController.Instance.RetrieveRequestor(requestor.Id, false);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return requestor;
        }

        /// <summary>
        /// Handler is used to close the dialog
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            DisableEvents();            
            cancelHandler(this, null);
        }

        private RequestorInfoUI infoUI;        
        /// <summary>
        /// Add the selected requestor to the request object
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void selectRequestorButton_Click(object sender, EventArgs e)
        {
            try
            {
                RequestorDetails requestorInfo;
                errorProvider.Clear();
                if (requestorProfileTabControl.SelectedTab == requestorInfoTabPage)
                {
                    infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
                    infoUI.errorProvider.Clear();                    
                    if (!isRequestorSaved)
                    {
                        requestorInfo = infoUI.SaveRequestor();
                    }
                    else
                    {
                        requestorInfo = infoUI.RequestorInfo;
                    }

                    if (requestorInfo != null)
                    {
                        infoUI.RequestorInfo = requestorInfo;
                        infoUI.DisableControls(requestorInfo.IsActive);
                        if (request.Id > 0 && request.RequestorId == requestorInfo.Id)
                        {
                            doUpdateContactAndAddressInfo = (infoUI.ContactAddressUpdated && 
                                                             RequestInfoUI.ShowOverrideRequestInformationDialog(Context));
                        }
                        else
                        {
                            doUpdateContactAndAddressInfo = true;
                        }
                    }
                }
                else
                {
                    requestorInfo = RequestorController.Instance.RetrieveRequestor(findRequestorEditor.SelectedRequestor.Id, false);
                    if (request.RequestorId != requestorInfo.Id)
                    {
                        doUpdateContactAndAddressInfo = true;
                    }
                }

                if (requestorInfo != null)
                {
                    DisableEvents();
                    selectRequestorHandler(this, new BaseEventArgs(requestorInfo));
                }
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        public override Control GetErrorControl(ExceptionData error)
        {
            if (infoUI != null)
            {
                switch (error.ErrorCode)
                {
                    case ROIErrorCodes.RequestorNameEmpty: // fall through;
                    case ROIErrorCodes.RequestorTypeNameMaxLength:
                    case ROIErrorCodes.RequestorTypeEmpty:
                    case ROIErrorCodes.InvalidSsn:
                    case ROIErrorCodes.InvalidEpn:
                    case ROIErrorCodes.InvalidMrn:
                    case ROIErrorCodes.InvalidFacility:
                    case ROIErrorCodes.InvalidHomePhone:
                    case ROIErrorCodes.InvalidWorkPhone:
                    case ROIErrorCodes.InvalidCellPhone:
                    case ROIErrorCodes.InvalidEmail:
                    case ROIErrorCodes.InvalidContactPhone:
                    case ROIErrorCodes.InvalidFax:
                    case ROIErrorCodes.InvalidContactName:
                    case ROIErrorCodes.InvalidContactEmail:
                    case ROIErrorCodes.InvalidAltState:
                    case ROIErrorCodes.InvalidAltCity:
                    case ROIErrorCodes.InvalidAltZip:
                    case ROIErrorCodes.InvalidMainState:
                    case ROIErrorCodes.InvalidMainCity:
                    case ROIErrorCodes.InvalidDate:
                    case ROIErrorCodes.InvalidSqlDate:
                    case ROIErrorCodes.InvalidDateValue:
                    case ROIErrorCodes.InvalidMainZip: return infoUI.GetErrorControl(error);
                }
            }
            return null;
        }
        
        private static void SetRequestorTab(TabPage tabPage, Control editor)
        {
            editor.Dock = DockStyle.Fill;
            tabPage.Controls.Clear();
            tabPage.Controls.Add(editor);
        }

        private void requestorProfileTabControl_Selecting(object sender, TabControlCancelEventArgs e)
        {            
            if (!e.TabPage.Enabled || ((e.TabPage == findRequestorTabPage) && !((RequestorInfoMCP)requestorInfoEditor.MCP).Approve(null)))
            {
                e.Cancel = true;
            }
        }

        private void requestorProfileTabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (requestorProfileTabControl.SelectedIndex == 0)
            {
                requestorInfoEditor.MCP.Cleanup();
                requestorInfoTabPage.Enabled = false;
            }
        }

        private void findRequestorTabPage_Enter(object sender, EventArgs e)
        {
            selectRequestorButton.Enabled = (findRequestorEditor.SelectedRequestor != null);
        }

        #endregion

        #region Properties

        public HeaderUI Header
        {
            get { return header; }
            set
            {
                header = value;
                header.Dock = DockStyle.Fill;
                topPanel.Controls.Add(header);
            }
        }

        private bool doUpdateContactAndAddressInfo;
        public bool DoUpdateContactAndAddressInfo
        {
            get { return doUpdateContactAndAddressInfo; }
            set { doUpdateContactAndAddressInfo = value; }
        }


        public bool Matching
        {
            get { return matching; }
        }

        public PatientDetails MatchedPatient
        {
            get { return matchedPatient; }
            set { matchedPatient = value; }
        }
        #endregion

       
    }
}
