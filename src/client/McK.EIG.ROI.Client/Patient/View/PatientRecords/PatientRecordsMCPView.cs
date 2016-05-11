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
using System.ComponentModel;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;


namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    /// <summary>
    /// PatientRecordsUI
    /// </summary>
    public partial class PatientRecordsMCPView : ROIBaseUI, IFooterProvider, IPatientPageContext
    {
        private Log log = LogFactory.GetLogger(typeof(PatientRecordsMCPView));

        #region Fields

        private PatientsFooterUI patientsFooterUI;
        private EncounterFilterDialog encounterFilterDialogUI;

        private PatientDetails patient;
        private bool isDirty;

        #endregion

        #region Constructor

        public PatientRecordsMCPView()
        {
            InitializeComponent();
            CreateActionUI();
        }

        #endregion

        #region Methods

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return patientsFooterUI;
        }

        #endregion

        private void CreateActionUI()
        {
            patientsFooterUI = new PatientsFooterUI(this);
        }

        internal void MarkDirty(object sender, EventArgs e)
        {
            IsDirty = true;
        }

        ///// <summary>
        ///// Get's the Data
        ///// </summary>
        ///// <param name="appendTo"></param>
        ///// <returns></returns>
        //public object GetData(object appendTo)
        //{
        //    return patientRecordsTreeUI.GetData(appendTo);
        //}

        /// <summary>
        /// Set's the Data
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            PatientDetails patientDetails = data as PatientDetails;
            patient = (PatientDetails)ROIViewUtility.DeepClone(patientDetails);
           
            FindPatientCriteria searchCriteria = ((PatientRSP)Pane.ParentPane.ParentPane).FindEditor.SearchCriteria;
            patientRecordsTreeUI.SelectedEncounters = new List<string>();
            bool isEncounterSearched = !string.IsNullOrEmpty(searchCriteria.Encounter);
            if (isEncounterSearched)
            {
                filterLabel.Text = Filter.On.ToString();
            }
                
            foreach (EncounterDetails encounter in patient.Encounters)
            {
                if (isEncounterSearched)
                {
                    if (searchCriteria.Encounter.Contains(encounter.EncounterId))
                    {
                        patientRecordsTreeUI.SelectedEncounters.Add(encounter.EncounterId + 
                                                                    ROIConstants.Delimiter + 
                                                                    encounter.Facility);
                    }
                }
                else
                {
                    patientRecordsTreeUI.SelectedEncounters.Add(encounter.EncounterId + 
                                                                ROIConstants.Delimiter + 
                                                                encounter.Facility);
                }
            }

            patientRecordsTreeUI.SetData(patient);
            EnableRequestButtons(true);

            setUpdateButton.Enabled = patient.IsHpf && patientRecordsTreeUI.CanAccessRecordView;
            patientRecordsTreeUI.Focus();
        }

        private void EnableRequestButtons(bool enable)
        {
            patientsFooterUI.CreateRequestButton.Enabled = enable;
            patientsFooterUI.PatientRequestButton.Enabled = enable;
        }

        /// <summary>
        /// EnableEvents
        /// </summary>
        public void EnableEvents()
        {
            patientRecordsTreeUI.EnableEvents();
        }

        /// <summary>
        /// DisableEvents
        /// </summary>
        public void DisableEvents()
        {
            patientRecordsTreeUI.DisableEvents();
        }

        /// <summary>
        /// PrePopulate
        /// </summary>
        public void PrePopulate(object data)
        {
            DisableEvents();
            patientRecordsTreeUI.PopulateRecordViews(data);
            EnableEvents();
        }

        public void UpdatePatient(PatientDetails patientDetails)
        {
            patientRecordsTreeUI.patTreeModel.PatientInfo = patientDetails;
            patientRecordsTreeUI.patTreeModel.Refresh();
        }

        /// <summary>
        /// SetPane
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            patientRecordsTreeUI.SetExecutionContext(Context);
            patientRecordsTreeUI.SetPane(Pane);
            
            patientsFooterUI.SetExecutionContext(Context);
            patientsFooterUI.SetPane(Pane);
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, filterByEncounterLabel);
            SetLabel(rm, filterLabel);
            SetLabel(rm, setUpdateButton);
            SetLabel(rm, patientRecordsGroupBox);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, setUpdateButton);
            
            //treeUI localize
            patientRecordsTreeUI.Localize();
            patientsFooterUI.Localize();
        }

        public bool SaveSupplemental()
        {
            log.EnterFunction();
            PatientRSP rsp = (PatientRSP)Pane.ParentPane.ParentPane;

            try
            {
                ROIViewUtility.MarkBusy(true);
                PatientDetails patient = patientRecordsTreeUI.patTreeModel.PatientInfo;

                patient.AuditMessage = PatientNonHpfDocument.PrepareAuditMessage(patientRecordsTreeUI.ModifiedNonHpfDocument,
                                                                                   ((PatientRecordsEditor)Pane.ParentPane).Patient.NonHpfDocuments);
                patient.ModifiedNonHpfDocument = patientRecordsTreeUI.ModifiedNonHpfDocument;

                if (patient.IsHpf && patient.Id == 0)
                {
                    patient = PatientController.Instance.RetrieveSuppmentarityDocuments(patient);
                }
                else
                {
                   patient = PatientController.Instance.RetrieveSuppmentalDocuments(patient);
                }

                if (patient.ModifiedNonHpfDocument != null &&
                !string.IsNullOrEmpty(patient.ModifiedNonHpfDocument.FacilityCode))
                {
                    FacilityDetails fac = FacilityDetails.GetFacility(patient.ModifiedNonHpfDocument.FacilityCode,
                                                                        patient.ModifiedNonHpfDocument.FacilityType);
                    if (fac == null)
                    {
                        FacilityDetails facility = new FacilityDetails(patient.ModifiedNonHpfDocument.FacilityCode,
                                                                       patient.ModifiedNonHpfDocument.FacilityCode,
                                                                       FacilityType.NonHpf);
                        UserData.Instance.Facilities.Add(facility);
                    }
                }

                
                rsp.InfoEditor.Patient = patient;
                rsp.RecordsEditor.Patient = patient;
                patientRecordsTreeUI.patTreeModel.PatientInfo = patient;

                IsDirty = false;
                
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                return false;
            }
            finally
            {
                SetData(rsp.RecordsEditor.Patient);
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
            return true;
        }

        public bool SaveAttachmentSupplemental()
        {
            log.EnterFunction();
            PatientRSP rsp = (PatientRSP)Pane.ParentPane.ParentPane;

            try
            {
                ROIViewUtility.MarkBusy(true);
                PatientDetails patient = patientRecordsTreeUI.patTreeModel.PatientInfo;

                patient.AuditMessage = PatientAttachment.PrepareAuditMessage(patientRecordsTreeUI.ModifiedAttachment);
                patient.ModifiedAttachment = patientRecordsTreeUI.ModifiedAttachment;

                if (patient.IsHpf && patient.Id == 0)
                {
                    patient = PatientController.Instance.RetrieveSuppmentarityAttachments(patient);
                }
                else
                {
                    patient = PatientController.Instance.RetrieveSuppmentalAttachments(patient);
                }


                rsp.InfoEditor.Patient = patient;
                rsp.RecordsEditor.Patient = patient;
                patientRecordsTreeUI.patTreeModel.PatientInfo = patient;

                IsDirty = false;

            }
            finally
            {
                SetData(rsp.RecordsEditor.Patient);
                ROIViewUtility.MarkBusy(false);
                patient.ModifiedAttachment = null;
                log.ExitFunction();
            }
            return true;
        }
       

        private void setUpdateButton_Click(object sender, EventArgs e)
        {
            CreateFilterEncounterDialogUI();
            encounterFilterDialogUI.SetData(patient.Encounters);

            Form dialog = ROIViewUtility.ConvertToForm(new CancelEventHandler(Process_CancelDialog),
                                                         encounterFilterDialogUI.Header.Title,
                                                         encounterFilterDialogUI);

            dialog.ShowDialog();
        }

        private void CreateFilterEncounterDialogUI()
        {
            encounterFilterDialogUI = new EncounterFilterDialog(new CancelEventHandler(Process_CancelDialog),
                                                                new EventHandler(Process_FilterEncounters),
                                                                patientRecordsTreeUI.SelectedEncounters);

            encounterFilterDialogUI.SetExecutionContext(Context);
            encounterFilterDialogUI.SetPane(Pane);
            encounterFilterDialogUI.Localize();
            encounterFilterDialogUI.Header = patientRecordsTreeUI.CreateDialogHeader();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            encounterFilterDialogUI.Header.Title = rm.GetString("encounterFilter.header.title");
            encounterFilterDialogUI.Header.Information = rm.GetString("encounterFilter.header.info");
        }


        private void Process_CancelDialog(object sender, CancelEventArgs e)
        {
            Form dialogForm = sender as Form;
            if (dialogForm != null) dialogForm.Dispose();
        }

        private void Process_FilterEncounters(object sender, EventArgs e)
        {
            List<string> encounters = new List<string>(sender as IList<string>);
            patientRecordsTreeUI.SelectedEncounters = encounters;
            filterLabel.Text = patientRecordsTreeUI.SelectedEncounters.Count > 0 ? Filter.On.ToString() : Filter.Off.ToString();
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.PatientsRecords, e));
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {

            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, false))
            {
                patientsFooterUI.Enabled = false;
            }

            patientRecordsTreeUI.ApplySecurityRights();

            patientsFooterUI.PatientRequestButton.Visible = 
            patientsFooterUI.CreateRequestButton.Visible = IsAllowed(ROISecurityRights.ROICreateRequest);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets true if dirty data available.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set
            {
                isDirty = value;
                if (!isDirty)
                {
                    EnableEvents();
                }
            }
        }

        public PatientsFooterUI Footer
        {
            get { return patientsFooterUI; }
        }

        #endregion

        #region IPatientPageContext Members

        public Collection<PatientDetails> Patients
        {
            get 
            {
                Collection<PatientDetails> selectedPatients = new Collection<PatientDetails>();
                selectedPatients.Add(patient);
                return selectedPatients;
            }
        }

        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        #endregion
    }

}
