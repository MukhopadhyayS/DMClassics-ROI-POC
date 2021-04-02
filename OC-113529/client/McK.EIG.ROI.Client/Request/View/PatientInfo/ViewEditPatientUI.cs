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

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{

    /// <summary>
    /// Maintains requestor profile
    /// </summary>
    public partial class ViewEditPatientUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;
        private PatientInfoEditor patientInfoEditor;
        
        private EventHandler cancelHandler;
        private EventHandler savePatientHandler;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the controls
        /// </summary>
        public ViewEditPatientUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Initialize the action handler
        /// </summary>
        /// <param name="selectRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public ViewEditPatientUI(EventHandler savePatientHandler, EventHandler cancelHandler, IPane pane)
            : this()
        {
            this.savePatientHandler = savePatientHandler;
            this.cancelHandler = cancelHandler;

            SetPane(pane);
            SetExecutionContext(pane.Context);
            CreateDialogHeader();
            Localize();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, requiredLabel);
            SetLabel(rm, revertButton);
            SetLabel(rm, saveButton);
            SetLabel(rm, closeButton);
            SetLabel(rm, patientInfoTabPage);
            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, revertButton);
            SetTooltip(rm, toolTip, closeButton);
        }


        /// <summary>
        /// Gets the localized key of UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control is TabPage || control is Label)
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
        
        private void saveButton_Click(object sender, EventArgs e)
        {
            
            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            try
            {
                ROIViewUtility.MarkBusy(true);
                infoUI.errorProvider.Clear();
                PatientDetails patient = infoUI.SavePatient();
                if (patient != null)
                {
                    infoUI.Patient = patient;
                    savePatientHandler(this, new BaseEventArgs(patient));
                }
                EnableButtons(false);
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!infoUI.HandleClientError(rm, cause, infoUI.errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        private void MarkDirty(object sender, EventArgs e)
        {
            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            infoUI.IsDirty = true;
            EnableButtons(true);
        }
        
        private void revertButton_Click(object sender, EventArgs e)
        {
            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            infoUI.RevertPatientInfo();
            infoUI.IsDirty = false;
            EnableButtons(false);
        }

        private void EnableButtons(bool enable)
        {
            revertButton.Enabled = saveButton.Enabled = enable;
        }

        /// <summary>
        /// Initialize the patient info tabpage
        /// </summary>
        /// <param name="requestorInfo"></param>
        public void InitPatientInfoTabPage(PatientDetails patient)
        {
            patientInfoEditor = new PatientInfoEditor();

            patientInfoEditor.Patient = RetrievePatientInfo(patient);
            patientInfoEditor.Init(Context);
            patientInfoEditor.Localize();
            patientInfoEditor.PrePopulate();
            patientInfoEditor.SetData(patientInfoEditor.Patient);

            HeaderUI header = (HeaderUI)patientInfoEditor.HeaderPane.View;
            header.Title = string.Empty;

            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            infoUI.DirtyDataHandler = new EventHandler(MarkDirty);
            infoUI.EnableEvents();

            infoUI.FooterPanelBackColor = Color.White;
            infoUI.HideFooter = true;
            EnableButtons(false);
            
            SetPatientTab(patientInfoTabPage, patientInfoEditor.View as Control);
            patientProfileTabControl.SelectedIndex = 1;
            ApplySecurityRights();
        }

        private PatientDetails RetrievePatientInfo(PatientDetails patientInfo)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                if (patientInfo.IsHpf)
                {
                    patientInfo = PatientController.Instance.RetrieveHpfPatient(patientInfo.MRN, patientInfo.FacilityCode);
                }
                else
                {
 //                   patientInfo = PatientController.Instance.RetrieveSupplementalInfo(patientInfo.Id);
                    patientInfo = PatientController.Instance.RetrieveSupplementalPatient(patientInfo.Id, false);

                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return patientInfo;
        }

        /// <summary>
        /// 
        /// Creates a Dialog header
        /// </summary>
        public void CreateDialogHeader()
        {
            Header = new HeaderUI();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Header.Title = rm.GetString("vieweditpatient.header.title");
            Header.Information = rm.GetString("vieweditpatient.header.info");
        }

        /// <summary>
        /// Handler is used to close the dialog
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            PatientInfoUI infoUI = (PatientInfoUI)patientInfoEditor.MCP.View;
            if (infoUI.IsDirty && !ROIViewUtility.ConfirmDiscardChanges(Context))
            {
                return;
            }
            cancelHandler(this, null);
        }

        private static void SetPatientTab(TabPage tabPage, Control editor)
        {
            editor.Dock = DockStyle.Fill;
            tabPage.Controls.Clear();
            tabPage.Controls.Add(editor);
        }

        public void CleanUp()
        {
            patientInfoEditor.MCP.Cleanup();
        }

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        private void ApplySecurityRights()
        {
            ((Control)patientInfoEditor.View).Enabled = IsAllowed(ROISecurityRights.ROIModifyRequest);
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
