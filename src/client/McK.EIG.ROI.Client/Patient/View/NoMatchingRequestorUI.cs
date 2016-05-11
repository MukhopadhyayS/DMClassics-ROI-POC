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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Patient.View
{
    /// <summary>
    /// NoMatchingRequestorDialog
    /// </summary>
    public partial class NoMatchingRequestorUI : ROIBaseUI
    {
        #region Fields

        private const string PatientNameColumn  = "patientName";
        private const string DOBColumn          = "dob";
        private const string SSNColumn          = "ssn";
        private const string FacilityColumn     = "facility";
        private const string MRNColumn          = "mrn";
        private const string EPNColumn          = "epn";

        private HeaderUI header;
        private CreateRequestInfo createRequestInformation;
  
        #endregion

        #region Constructor

        /// <summary>
        /// It initializes the UI and grid.
        /// </summary>
        private NoMatchingRequestorUI()
        {
            InitializeComponent();
            InitGrid();
        }

        /// <summary>
        /// Constructor assigns context and parent pane.
        /// </summary>
        /// <param name="pane"></param>
        public NoMatchingRequestorUI(IPane pane): this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
        }

        #endregion

        #region Methods
        /// <summary>
        /// It initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            grid.AddTextBoxColumn(PatientNameColumn, "Patient Name", "name", 200);
            DataGridViewTextBoxColumn dgvDOBColumn = grid.AddTextBoxColumn(DOBColumn, "Date of Birth", "DOB", 125);
            dgvDOBColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;
            grid.AddTextBoxColumn(SSNColumn, "SSN", "MaskedSSN", 125);
            grid.AddTextBoxColumn(FacilityColumn, "Facility", "FacilityCode", 100);
            DataGridViewTextBoxColumn dgvMrnColumn = grid.AddTextBoxColumn(MRNColumn, "MRN", "MRN", 125);
            DataGridViewTextBoxColumn dgvEpnColumn = grid.AddTextBoxColumn(EPNColumn, "EPN", "EPn", 125);
            dgvEpnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            if (!UserData.Instance.EpnEnabled)
            {
                dgvEpnColumn.Visible = false;
                dgvMrnColumn.AutoSizeMode= DataGridViewAutoSizeColumnMode.Fill;
            }
            grid.DoubleClick += new EventHandler(grid_DoubleClick);
        }

        private void grid_DoubleClick(object sender, EventArgs e)
        {
            this.selectPatientAsRequestorButton.PerformClick();
        }
        
        /// <summary>
        /// It sets all patients to a grid.
        /// </summary>
        /// <param name="encounters"></param>
        public void SetData(CreateRequestInfo createRequestInformation, Collection<PatientDetails> patients)
        {
            ComparableCollection<PatientDetails> list = new ComparableCollection<PatientDetails>(patients);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(PatientDetails))["Name"], ListSortDirection.Ascending);
            grid.SetItems(list);

            this.createRequestInformation = createRequestInformation;
            countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countLabel.Text, grid.Rows.Count);
        }
        
        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(grid, rm, PatientNameColumn);
            SetLabel(grid, rm, DOBColumn);
            SetLabel(grid, rm, SSNColumn);
            SetLabel(grid, rm, FacilityColumn);
            SetLabel(grid, rm, MRNColumn);

            SetLabel(rm, selectPatientAsRequestorButton);
            SetLabel(rm, cancelButton);

            countLabel.Text = rm.GetString("countLabel.SelectedPatients");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, selectPatientAsRequestorButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Get Localize key.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Creates a requestor object using patient object.
        /// </summary>
        /// <param name="patient"></param>
        /// <returns></returns>
        private static RequestorDetails ConvertToRequestor(PatientDetails patient)
        {   
            RequestorDetails requestor = new RequestorDetails();

            requestor.Name       = patient.FullName;
            requestor.FirstName  = patient.FirstName;
            requestor.LastName   = patient.LastName;
            requestor.Type       = RequestorDetails.PatientRequestorType;
            requestor.PatientEPN = patient.EPN;
            requestor.PatientSSN = patient.SSN;
            requestor.HomePhone  = patient.HomePhone;
            requestor.WorkPhone  = patient.WorkPhone;
            requestor.PatientDob = patient.DOB;
            requestor.PatientMRN = patient.MRN;
            requestor.PatientFacilityCode = patient.FacilityCode;
            requestor.IsFreeformFacility = patient.IsFreeformFacility;
            requestor.IsActive   = true;

            if (patient.Address != null)
            {
                requestor.MainAddress = new AddressDetails();
                requestor.MainAddress.Address1   = patient.Address.Address1;
                requestor.MainAddress.Address2   = patient.Address.Address2;
                requestor.MainAddress.Address3   = patient.Address.Address3;
                requestor.MainAddress.City       = patient.Address.City;
                requestor.MainAddress.State      = patient.Address.State;
                requestor.MainAddress.PostalCode = patient.Address.PostalCode;
                requestor.MainAddress.CountryCode = patient.Address.CountryCode;
                requestor.MainAddress.CountryName = patient.Address.CountryName;
           
            }

            return requestor;
        }

        private PatientDetails GetPatientDetails(PatientDetails patient)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                if (patient.IsHpf)
                {
                    patient = PatientController.Instance.RetrieveHpfPatient(patient.MRN, patient.FacilityCode);
                }
                else
                {
                    patient = PatientController.Instance.RetrieveSupplementalPatient(patient.Id, false);
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

            return patient;
        }

        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = selectPatientAsRequestorButton;
        }


        #endregion

        #region Properties

        public CreateRequestInfo CreateRequestInfo
        {
            get 
            {
                PatientDetails patient = GetPatientDetails((PatientDetails)grid.SelectedRows[0].DataBoundItem);
                createRequestInformation.Request.Requestor = ConvertToRequestor(patient);
                createRequestInformation.Request.AddPatient(patient);
                return createRequestInformation; 
            }
        }

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

        public Label InfoLabel
        {
            get { return noMatchingRequestorInfoLabel; }
        }

        #endregion
    }
}
