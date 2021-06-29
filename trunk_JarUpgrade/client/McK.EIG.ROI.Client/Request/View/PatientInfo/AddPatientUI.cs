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
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.View.FindPatient;
using McK.EIG.ROI.Client.Patient.View.PatientInfo;
using McK.EIG.ROI.Client.Patient.View;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    /// <summary>
    /// Maintains requestor profile
    /// </summary>
    public partial class AddPatientUI : ROIBaseUI
    {
        #region Fields

        private const string VipLockedPermissionDialogTitle           = "PatientVipLockedPermissionDialog.Title";
        private const string VipLockedPermissionDialogMessage         = "PatientVipLockedPermissionDialog.Message";
        private const string VipLockedPermissionDialogOkButton        = "PatientVipLockedPermissionDialog.OkButton";
        private const string VipLockedPermissionDialogOkButtonToolTip = "VipLockedPermissionDialog.OkButton";

        private HeaderUI header;
        private FindPatientEditor findPatientEditor;
        private PatientInfoEditor patientInfoEditor;
        
        private EventHandler cancelHandler;
        private EventHandler addPatientHandler;

        private EIGDataGrid.RowSelectionHandler rowSelectChangeHandler;
        private EventHandler resetSearchHandler;
        private EventHandler patientSearched;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the controls
        /// </summary>
        public AddPatientUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Initialize the action handler
        /// </summary>
        /// <param name="selectRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public AddPatientUI(EventHandler addPatientHandler, EventHandler cancelHandler, IPane pane, bool isPatientRequestor)
            : this()
        {
            this.addPatientHandler = addPatientHandler;
            this.cancelHandler = cancelHandler;

            SetPane(pane);
            SetExecutionContext(pane.Context);
            CreateDialogHeader();
            Localize();
            InitFindPatientTabPage();
            CreateActionPanel(isPatientRequestor);
            patientProfileTabControl.TabPages[1].Enabled = false;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, addPatientContinueButton);
            SetLabel(rm, addPatientCloseButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, findPatientTabPage);
            SetLabel(rm, patientInfoTabPage);
            SetLabel(rm, successfulLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, addPatientCloseButton);
            SetTooltip(rm, toolTip, addPatientContinueButton);
            SetTooltip(rm, toolTip, cancelButton);
        }


        /// <summary>
        /// Gets the localized key of UI controls
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

        /// <summary>
        /// Initialize the Find Patient tabpage
        /// </summary>
        public void InitFindPatientTabPage()
        {
            findPatientEditor = new FindPatientEditor();

            findPatientEditor.Init(Context);
            findPatientEditor.Localize();
            findPatientEditor.PrePopulate();
            findPatientEditor.hOuterSplitContainer.SplitterDistance = 50;

            HeaderUI header = (HeaderUI)findPatientEditor.HeaderPane.View;
            header.Title = string.Empty;

            PatientSearchUI searchUI = (PatientSearchUI)findPatientEditor.MCP.View;
            searchUI.NewPatientHandler = new EventHandler(NewPatientHandler);
            
            PatientListUI listUI = (PatientListUI)findPatientEditor.ODP.View;
            listUI.ViewEditHandler = new EventHandler(ViewPatientInfoHandler);
            listUI.HideCreateRequestButton = true;
            listUI.HidePatientRequestButton = true;
            listUI.FooterPanelBackColor = Color.White;

            EnableEvents();
            SetPatientTab(findPatientTabPage, findPatientEditor.View as Control);
            addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = false; 
        }

        private void EnableEvents()
        {

            rowSelectChangeHandler = new EIGDataGrid.RowSelectionHandler(list_SelectionChanged);
            resetSearchHandler     = new EventHandler(Process_ResetSearchResult);

            patientSearched = new EventHandler(Process_PatientSearched);
            PatientEvents.PatientSearched += patientSearched;
            ((PatientListUI)findPatientEditor.ODP.View).RowSelectionChangeHandler += rowSelectChangeHandler;
            PatientEvents.ResetSearch += resetSearchHandler;
        }

        public void CleanUp()
        {
            findPatientEditor.ODP.Cleanup();
            if (patientInfoEditor != null)
            {
                patientInfoEditor.MCP.Cleanup();
            }
        }

        private void DisableEvents()
        {
            ((PatientListUI)findPatientEditor.ODP.View).RowSelectionChangeHandler -= rowSelectChangeHandler;
            PatientEvents.PatientSearched -= patientSearched;
            PatientEvents.ResetSearch -= resetSearchHandler;
        }

        private void Process_PatientSearched(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            int searchResult = (ae.Info as FindPatientResult).PatientSearchResult.Count;
            addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = (searchResult > 0); 
        }

        private void Process_ResetSearchResult(object sender, EventArgs e)
        {
            addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = false;
            successfulLabel.Visible = false;
        }

        /// <summary>
        /// Event occurs when the user select the row in the grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void list_SelectionChanged(DataGridViewRow row)
        {
            addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = true; 
        }

        private void CreateActionPanel(bool isPatientRequestor)
        {
            if (UserData.Instance.EpnEnabled && isPatientRequestor)
            {
                addPatientContinueButton.Visible = false;
            }
        }


        /// <summary>
        /// Handler is used to show the patient's information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ViewPatientInfoHandler(object sender, EventArgs e)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                PatientDetails patientInfo = findPatientEditor.SelectedPatientInfo;
                if (patientInfo.IsHpf)
                {
                    if (!HasVipLockedPermission(patientInfo)) return;
                    patientInfo = PatientController.Instance.RetrieveHpfPatient(patientInfo.MRN, patientInfo.FacilityCode);
                }
                else
                {
//                    patientInfo = PatientController.Instance.RetrieveSupplementalInfo(patientInfo.Id);
                }

                InitPatientInfoTabPage(patientInfo);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        /// <summary>
        /// Display VIP/Locked encounter dialog if the patieng does not have access permission
        /// </summary>
        /// <param name="patient"></param>
        /// <returns></returns>
        private bool HasVipLockedPermission(PatientDetails patient)
        {
            if (patient.IsVip || patient.EncounterLocked)
            {
                if (!IsAllowed(ROISecurityRights.ROIVipStatus) ||
                    !IsAllowed(ROISecurityRights.AccessLockedRecords))
                {
                    if (!ShowVipLockedAlert())
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        /// <summary>
        /// Display VIP/Locked encounter dialog if the patieng does not have access permission
        /// </summary>
        /// <returns></returns>
        private bool ShowVipLockedAlert()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(VipLockedPermissionDialogMessage);
            string titleText = rm.GetString(VipLockedPermissionDialogTitle);
            string okButtonText = rm.GetString(VipLockedPermissionDialogOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(VipLockedPermissionDialogOkButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Hanndler is used to initialize the new patient  
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void NewPatientHandler(object sender, EventArgs e)
        {
            PatientEvents.OnCreatePatient(findPatientEditor.ODP, new ApplicationEventArgs(null, this));
            InitPatientInfoTabPage(null);
        }

        /// <summary>
        /// Initialize the patient info tabpage
        /// </summary>
        /// <param name="requestorInfo"></param>
        public void InitPatientInfoTabPage(PatientDetails patient)
        {
            addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = (patient != null); 
            patientProfileTabControl.TabPages[1].Enabled = true;

            patientInfoEditor = new PatientInfoEditor();
            
            patientInfoEditor.Patient = patient;
            patientInfoEditor.Init(Context);
            patientInfoEditor.Localize();
            patientInfoEditor.PrePopulate();
            patientInfoEditor.SetData(patient);

            HeaderUI header = (HeaderUI)patientInfoEditor.HeaderPane.View;
            header.Title = string.Empty;

            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            infoUI.SavePatientHandler = new EventHandler(SavePatientHandler);
            infoUI.CancelPatientHandler = new EventHandler(CancelPatientHandler);
            HideFooterButtons(infoUI);
            infoUI.FooterPanelBackColor = Color.White;

            SetPatientTab(patientInfoTabPage, patientInfoEditor.View as Control);
            patientProfileTabControl.SelectedIndex = 1;
        }

        /// <summary>
        /// Handler is used to save the patient's information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void SavePatientHandler(object sender, EventArgs e)
        {
            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            infoUI.Patient = infoUI.SavePatient();
            if (infoUI.Patient == null)
            {
                addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = false;
                return;
            } 

            PatientSearchUI searchUI = (PatientSearchUI)findPatientEditor.MCP.View;
            searchUI.AddFacility(infoUI.Patient.FacilityCode);

            infoUI.SetData(infoUI.Patient);
            HideFooterButtons(infoUI);

            addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = true; 
        }

        private static void HideFooterButtons(PatientInfoUI infoUI)
        {
            infoUI.HideDeleteNonHpfPatientButton = true;
            infoUI.HideCreateRequestButton = true;
            infoUI.HidePatientRequestButton = true;
        }

        /// <summary>
        /// Handler is used to save the patient's information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CancelPatientHandler(object sender, EventArgs e)
        {
            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            if (infoUI.Patient == null)
            {
                infoUI.IsDirty             = false;
                patientInfoTabPage.Enabled = false;
                patientProfileTabControl.SelectedTab = findPatientTabPage;
            }
            else
            {
                infoUI.RevertPatientInfo();
                HideFooterButtons(infoUI);
            }
        }

        /// <summary>
        /// Creates a Dialog header
        /// </summary>
        public void CreateDialogHeader()
        {
            Header = new HeaderUI();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Header.Title = rm.GetString("addpatient.header.title");
            Header.Information = rm.GetString("addpatient.header.info");
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

        /// <summary>
        /// Method that adds the selected patient into request object
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void addPatientContinueButton_Click(object sender, EventArgs e)
        {
            AddSelectedPatientsToRequest();
            successfulLabel.Visible = true;
        }

        private void addPatientCloseButton_Click(object sender, EventArgs e)
        {
            DisableEvents();
            AddSelectedPatientsToRequest();
            cancelHandler(this, null);
        }

        private void AddSelectedPatientsToRequest()
        {
            if (patientProfileTabControl.SelectedIndex == 0)
            {
                PatientListUI listUI = (PatientListUI)findPatientEditor.ODP.View;
                addPatientHandler(this, new BaseEventArgs(listUI.Patients));
            }
            else
            {
                PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
                addPatientHandler(this, new BaseEventArgs(infoUI.Patients));
                patientProfileTabControl.SelectedIndex = 0;
                addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = false; 
            }
        }
       
        private static void SetPatientTab(TabPage tabPage, Control editor)
        {
            editor.Dock = DockStyle.Fill;
            tabPage.Controls.Clear();
            tabPage.Controls.Add(editor);
        }

        private void patientProfileTabControl_Selecting(object sender, TabControlCancelEventArgs e)
        {
            if (!e.TabPage.Enabled || ((e.TabPage == findPatientTabPage) && !((PatientInfoMCP)patientInfoEditor.MCP).Approve(null)))
            {
                e.Cancel = true;
            }
        }

        private void patientProfileTabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (patientProfileTabControl.SelectedIndex == 0)
            {
                patientInfoEditor.MCP.Cleanup();
                patientInfoTabPage.Enabled = false;
                //CR# 368490 Suppressed the invalid error messgage when create new patient in Request Info screen
                PatientListUI listUI = (PatientListUI)findPatientEditor.ODP.View;
                addPatientContinueButton.Enabled = addPatientCloseButton.Enabled = listUI.Grid.RowCount > 0;
            }
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

        #endregion

        

    }
}
