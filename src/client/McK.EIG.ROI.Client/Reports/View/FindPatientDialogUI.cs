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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.View.FindPatient;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// FindPatientDialogUI
    /// </summary>
    public partial class FindPatientDialogUI : ROIBaseUI
    {
        #region Fields

        private FindPatientEditor findPatientEditor;
        private PatientSearchUI patientSearchUI;
        private PatientListUI patientListUI;

        private PatientDetails selectedPatient;

        #endregion

        #region Constructors

        public FindPatientDialogUI()
        {
            InitializeComponent();
        }

        public FindPatientDialogUI(IPane pane) : this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            InitFindPatientUI();
       }

        #endregion

        /// <summary>
        /// Initiates the find patient dialog UI.
        /// </summary>
        private void InitFindPatientUI()
        {
            findPatientEditor = new FindPatientEditor();
            findPatientEditor.Init(Context);
            findPatientEditor.Localize();
            findPatientEditor.PrePopulate();
            findPatientEditor.hOuterSplitContainer.SplitterDistance = 50;

            patientSearchUI = (PatientSearchUI)findPatientEditor.MCP.View;
            patientListUI   = (PatientListUI)findPatientEditor.ODP.View;

            patientSearchUI.NewPatientButton.Visible = false;
            patientListUI.HideCreateRequestButton = true;
            patientListUI.HidePatientRequestButton = true;
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString()); 
            patientListUI.ViewEditButton.Text = rm.GetString("okButton.DialogUI");
            patientListUI.GridInfoLabel.Text = rm.GetString("patientGridInfoLabel.AccountingDisclosure");
            ((HeaderUI)(findPatientEditor.HeaderPane.View)).Information = rm.GetString("Reports.findPatient.header.info");
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, patientListUI.ViewEditButton);

            patientListUI.ViewEditButton.DialogResult = DialogResult.OK;
            patientListUI.ViewEditHandler = new EventHandler(Process_SelectedPatientInfo);
            patientListUI.IsMultipleSelect = false;

            patientListUI.Grid.MouseDoubleClick += new MouseEventHandler(Grid_MouseDoubleClick);
            patientListUI.Grid.KeyDown += new KeyEventHandler(Grid_KeyDown);

            findPatientPanel.Controls.Add((Control)findPatientEditor.View);
        }

        private void Grid_KeyDown(object sender, KeyEventArgs e)
        {
            if ((Keys)e.KeyCode == Keys.Enter)
            {
                Process_SelectedPatientInfo();
            }
        }

        private void Grid_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            Process_SelectedPatientInfo();
        }

        private void Process_SelectedPatientInfo()
        {
            selectedPatient = patientListUI.SelectedPatientInfo;
            patientListUI.ViewEditButton.PerformClick();
        }



        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return this.Pane.GetType().Name+ "." + control.Name;
        }

        /// <summary>
        /// Get the selected patient info.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SelectedPatientInfo(object sender, EventArgs e)
        {
            selectedPatient = patientListUI.SelectedPatientInfo;
        }

        public void CleanUp()
        {
            findPatientEditor.ODP.Cleanup();
        }
     
        #region Properties

        /// <summary>
        /// Gets the selected patient.
        /// </summary>
        public PatientDetails SelectedPatient
        {
            get { return selectedPatient; }
        }

        #endregion

    }
}
