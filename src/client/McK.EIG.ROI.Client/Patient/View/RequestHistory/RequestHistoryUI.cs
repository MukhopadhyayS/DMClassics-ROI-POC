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
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Patient.View.RequestHistory
{
    public partial class RequestHistoryUI : ROIBaseUI, IPatientPageContext, IFooterProvider
    {
        #region Fields

        private PatientsFooterUI patientsFooterUI;
        private EncounterFilterDialog encounterFilterDialogUI;

        private PatientDetails patient;
        
        private SortedList<string, string> encounters;
        private List<string> selectedEncounters;
        
        //private RequestHistoryListUI listUI;        
        private RequestsListUI listUI;        
        
        #endregion

        #region Constructor
        
        public RequestHistoryUI()
        {
            InitializeComponent();
            patientsFooterUI = new PatientsFooterUI(this);
        }

        public void AddListPanel()
        {
            listPanel.Controls.Clear();
            //listUI = new RequestHistoryListUI(RequestHistoryListUI.Patient);
            listUI = new PatientRequestListUI();
            listUI.Dock = DockStyle.Fill;
            listPanel.Controls.Add((Control)listUI);
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return patientsFooterUI;
        }

        #endregion

        #region Methods

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, filterByEncounterLabel);
            SetLabel(rm, filterLabel);
            SetLabel(rm, setUpdateButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, setUpdateButton);            

            listUI.SetExecutionContext(Context);
            listUI.SetPane(Pane);
            listUI.Localize();
            patientsFooterUI.Localize();
            SetTooltip(rm, patientsFooterUI.ToolTip, patientsFooterUI.CreateRequestButton);
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control.Name != setUpdateButton.Name)
            {
                return GetType().Name + "." + patientsFooterUI.CreateRequestButton.Name;
            }
            return control.Name;
        }

        /// <summary>
        /// Sets the Data
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            patient = data as PatientDetails;
            FindPatientCriteria searchCriteria = ((PatientRSP)Pane.ParentPane.ParentPane).FindEditor.SearchCriteria;
            encounters = new SortedList<string, string>();
            selectedEncounters = new List<string>();
            Filter filter = Filter.Off;
            filterLabel.Text = filter.ToString();
            setUpdateButton.Enabled = patient.IsHpf;
            
            bool isEncounterSearched = !string.IsNullOrEmpty(searchCriteria.Encounter);
            
            if (isEncounterSearched)
            {
                filter = Filter.On;
                filterLabel.Text = filter.ToString();
            }

            string encounterKey;
            foreach (EncounterDetails encounter in patient.Encounters)
            {
                encounterKey = encounter.EncounterId + ROIConstants.Delimiter + encounter.Facility;
                if (isEncounterSearched)
                {
                    if (searchCriteria.Encounter.Contains(encounter.EncounterId))
                    {
                        encounters.Add(encounterKey, encounter.Facility);
                        selectedEncounters.Add(encounterKey);
                    }
                }
                else
                {
                    encounters.Add(encounterKey, encounter.Facility);
                    selectedEncounters.Add(encounterKey);
                }
            }

            FindRequestCriteria findRequestCriteria = new FindRequestCriteria();
            findRequestCriteria.IsSearch = false;
     
            if (patient.IsHpf)
            {
                if(UserData.Instance.EpnEnabled && !string.IsNullOrEmpty(patient.EPN))
                {
                    findRequestCriteria.EPN =  patient.EPN;
                }
                else
                {
                    findRequestCriteria.MRN = patient.MRN;
                    findRequestCriteria.Facility = patient.FacilityCode;
                }
            }
            else
            {
                if (UserData.Instance.EpnEnabled && !string.IsNullOrEmpty(patient.EPN))
                {
                    findRequestCriteria.EPN = patient.EPN;
                }
                else
                {
                    findRequestCriteria.NonHpfPatientId = patient.Id;
                }
                //findRequestCriteria.Facility  = patient.FacilityCode;

                //int index = patient.FullName.IndexOf(',');
                //findRequestCriteria.PatientFirstName = (index != -1) ? patient.FullName.Substring(index + 1) : patient.FullName;
                //findRequestCriteria.PatientLastName  = (index != -1) ? patient.FullName.Substring(0, index) : string.Empty;
                
            }

            findRequestCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);

            listUI.PopulateRequestHistory(findRequestCriteria, encounters, filter.ToString());
            patientsFooterUI.PatientRequestButton.Enabled = true;
            patientsFooterUI.CreateRequestButton.Enabled = true;            
            EnableButton();
        }

        private void EnableButton()
        {
            listUI.ViewEditRequestButton.Enabled = (listUI.Grid.Rows.Count > 0);
            listUI.UpdateRowCount();
        }

        /// <summary>
        /// SetPane
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);

            patientsFooterUI.SetExecutionContext(Context);
            patientsFooterUI.SetPane(Pane);
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
                                                                selectedEncounters);

            encounterFilterDialogUI.SetExecutionContext(Context);
            encounterFilterDialogUI.SetPane(Pane);
            encounterFilterDialogUI.Localize();
            encounterFilterDialogUI.Header = CreateDialogHeader();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            encounterFilterDialogUI.Header.Title = rm.GetString("encounterFilter.header.title");
            encounterFilterDialogUI.Header.Information = rm.GetString("encounterFilter.header.info");
        }

        private void Process_CancelDialog(object sender, CancelEventArgs e)
        {
            Form dialogForm = sender as Form;
            if (dialogForm != null) dialogForm.Dispose();
        }

        public HeaderUI CreateDialogHeader()
        {
            HeaderUI headerUI = new HeaderUI();
            PatientHeaderUI patientHeaderUI = new PatientHeaderUI();
            patientHeaderUI.PopulatePatientInformation(patient);
            patientHeaderUI.Localize(Context);
            headerUI.HeaderExtension = patientHeaderUI;
            return headerUI;
        }

        private void Process_FilterEncounters(object sender, EventArgs e)
        {
            selectedEncounters = new List<string>(sender as List<string>);
            SortedList<string, string> filterEncounters = new SortedList<string, string>();
            foreach (string encounter in selectedEncounters)
            {
                if (encounters.ContainsKey(encounter))
                {
                    filterEncounters.Add(encounter, encounters[encounter]);
                }
            }

            Filter filter = selectedEncounters.Count > 0 ? Filter.On : Filter.Off;
            filterLabel.Text = filter.ToString();

            listUI.FilterByEncounter(filterEncounters, filter.ToString());
            EnableButton();
        }

        private Collection<PatientDetails> RetrievePatients()
        {
            Collection<PatientDetails> selectedPatients = new Collection<PatientDetails>();

            selectedPatients.Add(patient);

            return selectedPatients;
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.PatientsRequestHistory, e));
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
                this.Enabled = false;
            }

           patientsFooterUI.PatientRequestButton.Visible =
           patientsFooterUI.CreateRequestButton.Visible = IsAllowed(ROISecurityRights.ROICreateRequest);
        }

        /// <summary>
        /// Add the new request into grid
        /// </summary>
        /// <param name="data"></param>
        public void AddRow()
        {
            //RequestDetails updatedRequest = (RequestDetails)data;

            //if (updatedRequest.Patients.ContainsKey(patient.Key))
            //{
            //    listUI.AddRow(data);
            //    EnableListUI();
            //} 
            SetData(patient);
        }

        /// <summary>
        /// Remove the deleted patient from the search result.
        /// </summary>
        /// <param name="p"></param>
        public void DeleteRow()
        {
            //listUI.DeleteRow(data);
            //EnableListUI();
            listUI.Grid.RemoveFilter();
            listUI.Grid.Rows.Clear();
            SetData(patient);
        }

        /// <summary>
        /// Update the patient info
        /// </summary>
        /// <param name="data"></param>
        public void UpdateRow()
        {
            //RequestDetails updatedRequest = (RequestDetails)data;
            //updatedRequest.LastUpdated = Convert.ToDateTime(updatedRequest.LastUpdated.ToString(), CultureInfo.InvariantCulture).Date;

            //if(updatedRequest.Patients.ContainsKey(patient.Key))
            //{
            //    if (listUI.Grid.Items == null || !listUI.Grid.Items.Contains(updatedRequest))
            //    {
            //        listUI.AddRow(updatedRequest);                     
            //    }
            //    else
            //    {
            //        listUI.UpdateRow(data);
            //    }
            //}   
            //else
            //{
            //   listUI.DeleteRow(data);
            //}

            //EnableListUI();
            listUI.Grid.RemoveFilter();
            listUI.Grid.Rows.Clear();
            SetData(patient);
        }

        private void EnableListUI()
        {
            listUI.Enabled = true;
            listUI.ViewEditRequestButton.Enabled = listUI.Grid.Rows.Count > 0;
            listUI.ViewEditRequestButton.Focus();
            if (listUI.Grid.Rows.Count > 0)
            {
                listUI.Grid.SelectRow(0);
            }
        }

        #endregion

        #region IPatientPageContext Members

        public Collection<PatientDetails> Patients
        {
            get { return RetrievePatients(); }
        }

        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        #endregion

        #region Properties

        public PatientsFooterUI Footer
        {
            get { return patientsFooterUI; }
        }

        #endregion
    }
}
