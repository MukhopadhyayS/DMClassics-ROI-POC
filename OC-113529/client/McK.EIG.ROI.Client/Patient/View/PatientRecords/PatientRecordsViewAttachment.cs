#region Copyright © 2008 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
 * Use of this material is governed by a license agreement. 
 * This material contains confidential, proprietary and trade 
 * secret information of McKesson Information Solutions and is 
 * protected under United States and international copyright and 
 * other intellectual property laws. Use, disclosure, 
 * reproduction, modification, distribution, or storage in a 
 * retrieval system in any form or by any means is prohibited 
 * without the prior express written permission of McKesson 
 * Information Solutions.
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
using McK.EIG.ROI.Client.Patient.View.PatientRecords.Attachments;
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    /// <summary>
    /// This class contains mthods related to Attachments
    /// </summary>
    public partial class PatientRecordsViewUI
    {
        #region Fields

        
        private EventHandler createEditAttachmentHandler;
        private EventHandler deleteAttachmentHandler;
        private CancelEventHandler cancelAttachmentHandler;

        private AttachmentDialog attachUI;

        #endregion

        #region Methods

        private AttachmentDialog CreateAttachmentUI(AttachmentMode mode)
        {
            createEditAttachmentHandler = new EventHandler(Process_CreateEditAttachment);
            deleteAttachmentHandler = new EventHandler(Process_DeleteAttachment);
            cancelAttachmentHandler = new CancelEventHandler(Process_CancelAttachmentDialog);

            ExternalSourceAttachment.SetAttachmentEvents(createEditAttachmentHandler);
            attachUI = new AttachmentDialog(createEditAttachmentHandler, deleteAttachmentHandler, cancelAttachmentHandler);
            attachUI.AttachMode = mode;

            attachUI.SetPane(Pane);
            attachUI.SetExecutionContext(Context);
            attachUI.PrePopulate();

            attachUI.Header = CreateAttachmentDialogHeader();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            attachUI.Header.Title = rm.GetString("attachment." + mode.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) + ".header.title");
            attachUI.Header.Information = rm.GetString("attachment." + mode.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) + ".header.info");
            attachUI.AttachMode = mode;
            attachUI.Localize();
            return attachUI;
        }

        public HeaderUI CreateAttachmentDialogHeader()
        {
            HeaderUI headerUI = new HeaderUI();
            PatientHeaderUI patientHeaderUI = new PatientHeaderUI();
            patientHeaderUI.PopulatePatientInformation(patTreeModel.PatientInfo);
            patientHeaderUI.Localize(Context);
            headerUI.HeaderExtension = patientHeaderUI;
            return headerUI;
        }

        private void Process_CancelAttachmentDialog(object sender, CancelEventArgs e)
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

        private void Process_CreateEditAttachment(object sender, EventArgs e)
        {

                AttachmentDetails attachment = sender as AttachmentDetails;

                if (attachment.Id == 0)
                {
                    attachment.Mode = AttachmentMode.Create;
                }
                else
                {
                    attachment.Mode = AttachmentMode.Edit;
                }
                try
                {
                    modifiedAttachment = attachment;

                    attachment.PatientId = patTreeModel.PatientInfo.Id;
                    attachment.PatientFacility = patTreeModel.PatientInfo.FacilityCode;
                    attachment.PatientMrn = patTreeModel.PatientInfo.MRN;
                    attachment.PatientKey = patTreeModel.PatientInfo.Key;

                    CheckPatientInUse();
                    if (attachment.PatientId != 0 && attachment.Mode == AttachmentMode.Create)
                    {
                        attachment = PatientController.Instance.CreateSupplementalAttachment(attachment);
                    }
                    else if(attachment.PatientId == 0 && attachment.Mode == AttachmentMode.Create)
                    {
                        attachment = PatientController.Instance.CreateSupplementarityAttachment(attachment);
                    }
                    else if (attachment.Mode == AttachmentMode.Edit && attachment.PatientId != 0)
                    {
                        attachment = PatientController.Instance.UpdateSupplementalAttachment(attachment);
                    }
                    else
                    {
                        attachment = PatientController.Instance.UpdateSupplementarityAttachment(attachment);
                    }
                    patTreeModel.PatientInfo.Attachments.AddAttachment(attachment);
                    patTreeModel.Refresh();
                    SaveAttachment();
                    expandButton.Enabled = collapseButton.Enabled = addButton.Enabled = addAllButton.Enabled = tree.RowCount > 0;
                    encounterDetailsButton.Enabled = tree.SelectedNodes.Count > 0;
                }
                catch (ROIException excpt)
                {
                    modifiedAttachment = null;
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                    Collection<ExceptionData> errors = excpt.GetErrorMessage(rm);
                    foreach (ExceptionData error in errors)
                    {
                        if (error.ErrorCode.Equals(ROIErrorCodes.PatientInUse) || error.ErrorCode.Equals(ROIErrorCodes.DirectoryAccessDenied))
                        {
                            throw excpt;
                        }
                    }
                    if (attachment.Mode == AttachmentMode.Create)
                    {
                        throw new ROIException("ROI.13.3.18");
                    }
                    else
                    {
                        throw new ROIException("ROI.13.3.19");
                    }

                }
                finally
                {
                    modifiedAttachment = null;
                }
            
        }

        private void Process_DeleteAttachment(object sender, EventArgs e)
        {
            AttachmentDetails tmpAttachment = sender as AttachmentDetails;
            bool check=false;
            try
            {
                CheckPatientInUse();
                long patientSeq=0;
                foreach (RequestPatientDetails patient in Patients)
                {
                    if (patTreeModel.PatientInfo.MRN == patient.MRN)
                        patientSeq = patient.PatientSeq;
                }

                tmpAttachment.IsDeleted = true;

                //if attachment is released - cannot delete however, attachment is marked as deleted and
                //cannot be added to future releases.
                if (tmpAttachment.IsReleased)
                {
                    //update supplementary record but do not delete attachment
                    tmpAttachment.Mode = AttachmentMode.Edit;
                }
                else
                {
                    //update supplementary record and delete attachment
                    tmpAttachment.Mode = AttachmentMode.Delete;
                    if (tmpAttachment.PatientId != 0)
                    {
                        try
                        {
                            PatientController.Instance.DeleteSupplementalAttachment(tmpAttachment.Id, ReqId);
                            PatientDetailsCache.RemoveKey(tmpAttachment.PatientMrn + tmpAttachment.PatientFacility);
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
                            PatientController.Instance.DeleteSupplementarityAttachment(tmpAttachment.Id, ReqId, patientSeq);
                            PatientDetailsCache.RemoveKey(tmpAttachment.PatientMrn + tmpAttachment.PatientFacility);
                        }
                        catch (Exception Ex)
                        {
                            check = true;
                            throw Ex;
                        }
                    }
                    patTreeModel.PatientInfo.Attachments.GetChild(tmpAttachment.Parent.Key).RemoveChild(tmpAttachment);
                }

                modifiedAttachment = tmpAttachment;
                if (patTreeModel.PatientInfo.Attachments.GetChildren.Count == 0)
                {
                    editAttachmentButton.Enabled = false;
                }
                patTreeModel.Refresh();
                SaveAttachment();
                expandButton.Enabled = collapseButton.Enabled = tree.RowCount > 0;
            }
            catch (Exception ex)
            {
                modifiedAttachment = null;

                if (check)
                    throw new ROIException("ROI.13.3.24");
                else
                    throw new ROIException("ROI.13.3.20");
            }
            finally
            {
                modifiedAttachment = null;
            }
        }


        private void SaveAttachment()
        {
            if (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
            {
                ((PatientRecordsMCPView)Pane.View).SaveAttachmentSupplemental();
            }
            else
            {
                ((RequestPatientInfoUI)Pane.View).SaveAttachmentSupplemental();
            }
        }

        private void newAttachmentButton_Click(object sender, EventArgs e)
        {
            try
            {
                if (!CheckPatientHasFacility())
                {
                    throw new ROIException("ROI.13.3.21");
                }

                ROIViewUtility.MarkBusy(true);
                AttachmentDialog attachUI = CreateAttachmentUI(AttachmentMode.Create);

                attachUI.SetData(null);

                Form dialog = ROIViewUtility.ConvertToForm(cancelAttachmentHandler, attachUI.Header.Title, attachUI);
                ROIViewUtility.MarkBusy(false);
                dialog.ShowDialog(this);
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                ROIViewUtility.Handle(Context, cause);
            }

        }

        private void editAttachmentButton_Click(object sender, EventArgs e)
        {
            TreeNodeAdv selectedNode = PatientRecordsTreeView.SelectedNode;
            if (selectedNode != null && typeof(AttachmentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
            {
                ShowEditAttachmentDialog((AttachmentDetails)selectedNode.Tag);
            }
        }

        private void ShowEditAttachmentDialog(AttachmentDetails attachment)
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds)) return;
            
            ROIViewUtility.MarkBusy(true);

            AttachmentDialog attachUI = CreateAttachmentUI(AttachmentMode.Edit);
            attachUI.Localize();
            attachUI.SetData(attachment);
            Form dialog = ROIViewUtility.ConvertToForm(cancelAttachmentHandler, attachUI.Header.Title, attachUI);
            dialog.ShowDialog(this);
            
            ROIViewUtility.MarkBusy(false);
        }

        

        #endregion
    }
}
