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
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View.Common.Tree;

using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    /// <summary>
    /// This class contains mthods related to NonHpfDocument Document
    /// </summary>
    public partial class PatientRecordsViewUI
    {
        #region Fields

        
        private EventHandler createEditHandler;
        private EventHandler deleteHandler;
        private CancelEventHandler cancelHandler;

        private NonHpfDocumentDialog nonHpfDocUI;
        private DeficiencyDialog deficiencyDialogUI;

        #endregion

        #region Methods

        private NonHpfDocumentDialog CreateNonHpfUI(Mode mode)
        {           
            createEditHandler = new EventHandler(Process_CreateEditNonHpfDocuments);
            deleteHandler = new EventHandler(Process_DeletedNonHpfDocuments);
            cancelHandler = new CancelEventHandler(Process_CancelDialog);

            nonHpfDocUI = new NonHpfDocumentDialog(createEditHandler, deleteHandler, cancelHandler);
            nonHpfDocUI.DocumentMode = mode;

            nonHpfDocUI.SetPane(Pane);
            nonHpfDocUI.SetExecutionContext(Context);
            nonHpfDocUI.PrePopulate();  

            nonHpfDocUI.Header = CreateDialogHeader();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            nonHpfDocUI.Header.Title = rm.GetString("nonhpfdocument." + mode.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) + ".header.title");
            nonHpfDocUI.Header.Information = rm.GetString("nonhpfdocument." + mode.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) + ".header.info");
            nonHpfDocUI.DocumentMode = mode;
            nonHpfDocUI.Localize();
            return nonHpfDocUI;
        }

        public HeaderUI CreateDialogHeader()
        {
            HeaderUI headerUI = new HeaderUI();
            PatientHeaderUI patientHeaderUI = new PatientHeaderUI();
            patientHeaderUI.PopulatePatientInformation(patTreeModel.PatientInfo);
            patientHeaderUI.Localize(Context);
            headerUI.HeaderExtension = patientHeaderUI;
            return headerUI;
        }

        private void Process_CancelDialog(object sender, CancelEventArgs e)
        {
            Form dialogForm = (sender as Form);

            if (e == null)
            {
                dialogForm.Close();
            }

            if (typeof(PatientRecordsMCPView).IsAssignableFrom(Pane.View.GetType()))
            {
                ((PatientRecordsMCPView)this.Pane.View).IsDirty = false;
            }
        }

        private void Process_CreateEditNonHpfDocuments(object sender, EventArgs e)
        {
            NonHpfDocumentDetails nonHpfDocument = sender as NonHpfDocumentDetails;

            if (nonHpfDocument.Id == 0)
            {
                nonHpfDocument.Mode = Mode.Create;
            }
            else
            {
                nonHpfDocument.Mode = Mode.Edit;
            }

            modifiedNonHpfDocument = nonHpfDocument;

            nonHpfDocument.PatientId       = patTreeModel.PatientInfo.Id;
            nonHpfDocument.PatientFacility = patTreeModel.PatientInfo.FacilityCode;
            nonHpfDocument.PatientMrn      = patTreeModel.PatientInfo.MRN;
            nonHpfDocument.PatientKey      = patTreeModel.PatientInfo.Key;

            CheckPatientInUse();
            if (nonHpfDocument.PatientId != 0 && nonHpfDocument.Mode == Mode.Create)
            {
                nonHpfDocument.Id = PatientController.Instance.CreateSupplementalDocument(nonHpfDocument);
            }
            else if (nonHpfDocument.PatientId == 0 && nonHpfDocument.Mode == Mode.Create)
            {
                nonHpfDocument.Id = PatientController.Instance.CreateSupplementarityDocument(nonHpfDocument);
            }
            else if (nonHpfDocument.Mode == Mode.Edit && nonHpfDocument.PatientId > 0)
            {
                PatientController.Instance.UpdateSupplementalDocument(nonHpfDocument);
            }
            else
            {
                PatientController.Instance.UpdateSupplementarityDocument(nonHpfDocument);
            }
            //patTreeModel.PatientInfo.NonHpfDocuments.AddChild(nonHpfDocument.Key, nonHpfDocument);
            patTreeModel.PatientInfo.NonHpfDocuments.AddDocument(nonHpfDocument);
            patTreeModel.Refresh();
            Save();
            expandButton.Enabled = collapseButton.Enabled = addButton.Enabled = addAllButton.Enabled = tree.RowCount > 0;
            encounterDetailsButton.Enabled = tree.SelectedNodes.Count > 0;
        }

        private void Process_DeletedNonHpfDocuments(object sender, EventArgs e)
        {
            NonHpfDocumentDetails nonHpfDocument = sender as NonHpfDocumentDetails;
            nonHpfDocument.Mode = Mode.Delete;
            modifiedNonHpfDocument = nonHpfDocument;
            bool check = false;
            CheckPatientInUse();
            long patientSeq=0;
            foreach (RequestPatientDetails patient in Patients)
            {
                if (patTreeModel.PatientInfo.MRN == patient.MRN)
                    patientSeq = patient.PatientSeq;
            }
            try
            {
                if (nonHpfDocument.PatientId != 0)
                {
                    try
                    {
                        PatientController.Instance.DeleteSupplementalDocument(nonHpfDocument.Id, ReqId);
                        PatientDetailsCache.RemoveKey(nonHpfDocument.PatientMrn + nonHpfDocument.PatientFacility);
                    }
                    catch (Exception Ex)
                    {
                        check = true;
                        throw Ex;
                    }
                }
                else
                {
                    try
                    {
                        PatientController.Instance.DeleteSupplementarityDocument(nonHpfDocument.Id, ReqId, patientSeq);
                        PatientDetailsCache.RemoveKey(nonHpfDocument.PatientMrn + nonHpfDocument.PatientFacility);
                    }
                    catch (Exception Ex)
                    {
                        check = true;
                        throw Ex;
                    }
                }
            }
            catch (Exception Ex)
            {
                if (check)
                    throw new ROIException("ROI.13.3.23");
            }
            patTreeModel.PatientInfo.NonHpfDocuments.GetChild(nonHpfDocument.Parent.Key).RemoveChild(nonHpfDocument);
            if (patTreeModel.PatientInfo.NonHpfDocuments.GetChildren.Count == 0)
            {
                editDocumentButton.Enabled = false;
            }
            patTreeModel.Refresh();
            Save();
            expandButton.Enabled = collapseButton.Enabled = tree.RowCount > 0;
        }

        private void CheckPatientInUse()
        {
            
            int recordversion = patTreeModel.PatientInfo.RecordVersionId;

            PatientDetails patient = new PatientDetails();
            patient.Id = patTreeModel.PatientInfo.Id;
            patient.IsHpf = patTreeModel.PatientInfo.IsHpf;
            patient.MRN = patTreeModel.PatientInfo.MRN;
            patient.FacilityCode = patTreeModel.PatientInfo.FacilityCode;

            if (patient.IsHpf && patient.Id == 0)
            {
                patient = PatientController.Instance.RetrieveSuppmentarityDocuments(patient);

                if (recordversion != patient.RecordVersionId || patient.Id > 0)
                {
                    throw new ROIException(ROIErrorCodes.PatientInUse);
                }
            }
        }

        private bool CheckPatientHasFacility()
        {
            if (patTreeModel.PatientInfo == null)
            {
                return false;
            }

            if (patTreeModel.PatientInfo.FacilityCode == null)
            {
                return false;
            }

            return (patTreeModel.PatientInfo.FacilityCode.Length > 0);
        }

        private void Save()
        {
            if (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
            {
                ((PatientRecordsMCPView)Pane.View).SaveSupplemental();
            }
            else
            {
                ((RequestPatientInfoUI)Pane.View).SaveSupplemental();
            }
        }

        private void newDocumentButton_Click(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);
            NonHpfDocumentDialog nonHpfDocUI = CreateNonHpfUI(Mode.Create);
            
            nonHpfDocUI.SetData(null);

            Form dialog = ROIViewUtility.ConvertToForm(cancelHandler, nonHpfDocUI.Header.Title, nonHpfDocUI);
            ROIViewUtility.MarkBusy(false);
            dialog.ShowDialog(this);
        }

        private void editDocumentButton_Click(object sender, EventArgs e)
        {
            TreeNodeAdv selectedNode = PatientRecordsTreeView.SelectedNode;
            if (selectedNode != null && typeof(NonHpfDocumentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
            {
                ShowEditDocumentDialog((NonHpfDocumentDetails)selectedNode.Tag);
            }
        }

        private void ShowEditDocumentDialog(NonHpfDocumentDetails document)
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds)) return;
            
            ROIViewUtility.MarkBusy(true);
            
            NonHpfDocumentDialog nonHpfDocUI = CreateNonHpfUI(Mode.Edit);
            nonHpfDocUI.Localize();
            nonHpfDocUI.SetData(document);
            Form dialog = ROIViewUtility.ConvertToForm(cancelHandler, nonHpfDocUI.Header.Title, nonHpfDocUI);
            dialog.ShowDialog(this);
            
            ROIViewUtility.MarkBusy(false);
        }

        

        #endregion
    }
}
