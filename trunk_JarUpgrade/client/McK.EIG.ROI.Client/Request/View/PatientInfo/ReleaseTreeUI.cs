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
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common.Tree;
using McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Request.Controller;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    /// <summary>
    /// DsrTreeUI
    /// </summary>
    public partial class ReleaseTreeUI : ROIBaseUI
    {
        #region Fields

        private const string PreviouslyReleasedTitle           = "PreviouslyReleasedDialog.Title";
        private const string PreviouslyReleasedMessage         = "PreviouslyReleasedDialog.Message";
        private const string PreviouslyReleasedOkButton        = "PreviouslyReleasedDialog.OkButton";
        private const string PreviouslyReleasedOkButtonToolTip = "PreviouslyReleasedDialog.OkButton";
        
        private ReleaseTreeModel releaseTreeModel;
        private bool isChildExpanded;

        private int releasedPageCount;
        private int unreleasedCount;
        private int deletedCount;
        private bool isMessageDisplayed;
        private OutputMethod outputMethod; // CR#359303
        private DeleteList deleteList;
        //CR#377567
        private bool hasRights = true;
        //private bool isDirty;

        #endregion

        #region Constructor

        public ReleaseTreeUI()
        {
            InitializeComponent();
            releaseTreeModel = new ReleaseTreeModel();
            deleteList = new DeleteList();

            isChecked.ValueNeeded      += new EventHandler<NodeControlValueEventArgs>(isChecked_ValueNeeded);
            isChecked.ValuePushed      += new EventHandler<NodeControlValueEventArgs>(isChecked_ValuePushed);
            facility.ValueNeeded       += new EventHandler<NodeControlValueEventArgs>(facility_ValueNeeded);
            department.ValueNeeded     += new EventHandler<NodeControlValueEventArgs>(department_ValueNeeded);
            dateOfService.ValueNeeded  += new EventHandler<NodeControlValueEventArgs>(dateOfService_ValueNeeded);
            encounter.ValueNeeded      += new EventHandler<NodeControlValueEventArgs>(encounter_ValueNeeded);
            vipIcon.ValueNeeded        += new EventHandler<NodeControlValueEventArgs>(icon_ValueNeeded);
            lockedIcon.ValueNeeded     += new EventHandler<NodeControlValueEventArgs>(icon_ValueNeeded);
            deficiencyIcon.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(icon_ValueNeeded);
            name.DrawText              += new EventHandler<DrawEventArgs>(name_DrawText);
            tree.SelectionChanged      += new EventHandler(tree_SelectionChanged);
            tree.Enabled = false;
        }
    
        #endregion

        #region Methods

        /// <summary>
        /// Localizes the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, documentsGroupBox);
            SetLabel(rm, filterByLabel);
            SetLabel(rm, allRadioButton);
            SetLabel(rm, releasedRadioButton);
            SetLabel(rm, unReleasedRadioButton);
            SetLabel(rm, dsrTreeInfoLabel);
            float screenWidth = Screen.PrimaryScreen.Bounds.Width;
            float screenHeight = Screen.PrimaryScreen.Bounds.Height;
            float baseresolution = 2073600;

            if ((screenWidth * screenHeight) != baseresolution)
            {
                if (screenWidth <= 1024 && screenHeight <= 768)
                {                   
                    treePanel.Size = new System.Drawing.Size(347, 290);
                    dsrTreeInfoLabel.Size = new System.Drawing.Size(347, 40);                    
                    tableLayoutPanel2.Size = new System.Drawing.Size(347, 52);                    
                }
            }           
            
            SetLabel(rm, removeAllButton);
            SetLabel(rm, removeButton);
            SetLabel(rm, modifyBillingTierButton);
            SetLabel(rm, expandButton);
            SetLabel(rm, collapseButton);
            SetLabel(rm, resendButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, removeButton);
            SetTooltip(rm, toolTip, removeAllButton);
            toolTip.SetToolTip(expandButton, rm.GetString("expandButton"));
            toolTip.SetToolTip(collapseButton, rm.GetString("collapseButton"));
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + Pane.GetType().Name;
        }

        /// <summary>
        /// Sets the data
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            releaseTreeModel.Request = (RequestDetails)data;
            //MarkAsFalseForReleasedDocuments();
            tree.Model = releaseTreeModel;
            tree.Model.StructureChanged += new EventHandler<TreePathEventArgs>(Model_StructureChanged);
            releaseTreeModel.Refresh();
            tree.Enabled = tree.RowCount > 0;
            isMessageDisplayed = true;
            PreviouslyReleased();
            isMessageDisplayed = false; 
            //CR377567
            Application.DoEvents();
            hasRights = RequestController.Instance.HasSercuirtyRights();
            resendButton.Enabled = !HasPreviouslyReleased;
            if (hasRights == false)
                resendButton.Enabled = false;
            EnableFilterButtons();
        }

        private void Model_StructureChanged(object sender, TreePathEventArgs e)
        {
            tree.Enabled = tree.RowCount > 0;
        }

        public static void MarkAsFalseForReleasedDocuments(RequestDetails request)
        {
            foreach (RequestPatientDetails patient in request.Patients.Values)
            {
                if (patient.IsHpf)
                {
                    //Hpf docuemnts
                    MarkAsFalseForReleasedHpfDocuments(patient);

                    //Global documents
                    MarkAsFalseForReleasedHpfDocuments(patient.GlobalDocument);
                }
                //Non Hpf documents
                MarkAsFalseForReleasedNonHpfDocuments(patient.NonHpfDocument.GetChildren);
                //Attachments
                MarkAsFalseForReleasedAttachments(patient.Attachment.GetChildren);
            }
        }

        private static void MarkAsFalseForReleasedHpfDocuments(BaseRequestItem item)
        {
            if(typeof(RequestPageDetails).IsAssignableFrom(item.GetType()))
            {
                if (item.IsReleased)
                {
                    item.SelectedForRelease = false;
                }
            }

            foreach (BaseRequestItem child in item.GetChildren)
            {
                MarkAsFalseForReleasedHpfDocuments(child);
            }
        }


        /// <summary>
        /// Mark the checked status as false for released Non Hpf documents.
        /// </summary>
        /// <param name="nonHpfDocuments"></param>
        private static void MarkAsFalseForReleasedNonHpfDocuments(IList<BaseRequestItem> nonHpfEncounters)
        {
            foreach (RequestNonHpfEncounterDetails nonHpfEncounter in nonHpfEncounters)
            {
                foreach (RequestNonHpfDocumentDetails nonHpfDoc in nonHpfEncounter.GetChildren)
                {
                    if (nonHpfDoc.IsReleased)
                    {
                        nonHpfDoc.SelectedForRelease = false;
                    }
                }
            }
        }

        /// <summary>
        /// Mark the checked status as false for released attachments.
        /// </summary>
        /// <param name="nonHpfDocuments"></param>
        private static void MarkAsFalseForReleasedAttachments(IList<BaseRequestItem> attachmentEncounters)
        {
            foreach (RequestAttachmentEncounterDetails attachmentEncounter in attachmentEncounters)
            {
                foreach (RequestAttachmentDetails tmpAttachment in attachmentEncounter.GetChildren)
                {
                    if (tmpAttachment.IsReleased)
                    {
                        tmpAttachment.SelectedForRelease = false;
                    }
                }
            }
        }


        /// <summary>
        /// Tree selection event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void tree_SelectionChanged(object sender, EventArgs e)
        {
            if (releaseTreeModel.Filter != FilterBy.Released)
            {
                List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
                bool allItemsReleased = AreAllItemsReleased(selectedNodes);
                removeButton.Enabled = (selectedNodes.Count > 0 && !allItemsReleased);
                removeAllButton.Enabled = (tree.Root.Children.Count > 0 && !AreAllItemsReleased(new List<TreeNodeAdv>(tree.Root.Children)));

                modifyBillingTierButton.Enabled = (selectedNodes.Count > 0 && 
                                                   !allItemsReleased && 
                                                   IsNonHPFRecordsOnlySelected(selectedNodes));
                PreviouslyReleased();
                resendButton.Enabled = !HasPreviouslyReleased && (outputMethod != OutputMethod.None);
            }
        }

        private static bool AreAllItemsReleased(List<TreeNodeAdv> selectedNodes)
        {
            RequestPatientDetails patient;
           selectedNodes = selectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) 
                                                 { 
                                                     if(!typeof(RequestPatientDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                                                     {
                                                        return (selectedNode.Tag as BaseRequestItem).PartiallyReleased; 
                                                     }
                                                     else
                                                     {
                                                         patient = (RequestPatientDetails)selectedNode.Tag;
                                                         return (patient.PartiallyReleased || (patient.NonHpfDocument.GetChildren.Count > 0 && patient.NonHpfDocument.PartiallyReleased) ||
                                                                 (patient.Attachment.GetChildren.Count > 0 && patient.Attachment.PartiallyReleased) || 
                                                                 (patient.GlobalDocument.GetChildren.Count > 0 && patient.GlobalDocument.PartiallyReleased));
                                                     }
                                                 });
           return (selectedNodes.Count == 0);
        }

        private static bool IsNonHPFRecordsOnlySelected(List<TreeNodeAdv> selectedNodes)
        {
            return selectedNodes.TrueForAll(delegate(TreeNodeAdv selectedNode)
                                                                 {
                                                                     return (selectedNode.Tag is RequestNonHpfDocumentDetails ||
                                                                             selectedNode.Tag is RequestAttachmentDetails);
                                                                 }) ;
        }
        
        private void isChecked_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is BaseRequestItem)
            {
                BaseRequestItem recordNode = e.Node.Tag as BaseRequestItem;

                if (typeof(RequestPatientDetails).IsAssignableFrom(recordNode.GetType()))
                {
                    RequestPatientDetails patient = (RequestPatientDetails)recordNode;
                    e.Value = patient.CheckedState;
                }
                else if (typeof(RequestDocumentSubtitle).IsAssignableFrom(recordNode.GetType()))
                {
                    RequestDocumentSubtitle subtitle = (RequestDocumentSubtitle)recordNode;
                    e.Value = subtitle.Parent.SelectedForRelease;
                }
                else
                {
                    e.Value = recordNode.SelectedForRelease;
                }

                if (e.Value == null)
                {
                    e.Value = CheckState.Indeterminate;
                }
            }
        }

        private void isChecked_ValuePushed(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is BaseRequestItem)
            {
                BaseRequestItem recordNode = e.Node.Tag as BaseRequestItem;
                recordNode.SelectedForRelease = ((CheckState)e.Value == CheckState.Checked);
            }
            ((RequestPatientInfoUI)Pane.View).MarkDirty(sender, e);
        }

        private void name_DrawText(object sender, DrawEventArgs e)
        {
            RequestStatus status = releaseTreeModel.Request.Status;

            BaseRequestItem recordNode = e.Node.Tag as BaseRequestItem;
            if (recordNode != null)
            {
                if (recordNode.IsReleased && !unReleasedRadioButton.Checked && ((int)status == 3))
                {
                    e.TextColor = Color.Green;
                    removeAllButton.Enabled = (!releasedRadioButton.Checked &&
                                       tree.Root.Children.Count > 0 &&
                                       !AreAllItemsReleased(new List<TreeNodeAdv>(tree.Root.Children)));
                }
                if (recordNode.IsDeleted)
                {
                    e.TextColor = Color.Red;
                }
                if (e.Node.IsSelected)
                {
                    e.TextColor = Color.White;
                }
            }
        }

        private void facility_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is RequestEncounterDetails)
            {
                RequestEncounterDetails enc = e.Node.Tag as RequestEncounterDetails;
                e.Value = enc.PatientType;
            }
            else if (e.Node.Tag is RequestDocumentDetails)
            {
                RequestDocumentDetails hpfDoc = e.Node.Tag as RequestDocumentDetails;
                e.Value = hpfDoc.Subtitle;
            }
            else if (e.Node.Tag is RequestNonHpfEncounterDetails)
            {
                RequestNonHpfEncounterDetails nonHpfEncounter = e.Node.Tag as RequestNonHpfEncounterDetails;
                e.Value = nonHpfEncounter.Facility;
            }
            else if (e.Node.Tag is RequestAttachmentEncounterDetails)
            {
                RequestAttachmentEncounterDetails attachmentEncounter = e.Node.Tag as RequestAttachmentEncounterDetails;
                e.Value = attachmentEncounter.Facility;
            }
            else if (e.Node.Tag is RequestNonHpfDocumentDetails)
            {
                RequestNonHpfDocumentDetails nonHpfDoc = e.Node.Tag as RequestNonHpfDocumentDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", nonHpfDoc.PageCount);
            }
            else if (e.Node.Tag is RequestAttachmentDetails)
            {
                RequestAttachmentDetails tmpAttachment = e.Node.Tag as RequestAttachmentDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", tmpAttachment.PageCount);
            }
        }

        private void department_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is RequestEncounterDetails)
            {
                RequestEncounterDetails enc = e.Node.Tag as RequestEncounterDetails;
                e.Value = enc.Facility;
            }
            else if (e.Node.Tag is RequestDocumentDetails)
            {
                RequestDocumentDetails hpfDoc = e.Node.Tag as RequestDocumentDetails;
                if (hpfDoc.DateOfService.HasValue)
                {
                    e.Value = hpfDoc.DateOfService.Value.ToString();
                }
            }
            else if (e.Node.Tag is RequestNonHpfEncounterDetails)
            {
                RequestNonHpfEncounterDetails nonHpfEnc = e.Node.Tag as RequestNonHpfEncounterDetails;
                e.Value = nonHpfEnc.Department;
            }
            else if (e.Node.Tag is RequestAttachmentEncounterDetails)
            {
                RequestAttachmentEncounterDetails attachmentEncounter = e.Node.Tag as RequestAttachmentEncounterDetails;
                if (attachmentEncounter.DateOfService.HasValue)
                    e.Value = attachmentEncounter.DateOfService.Value.ToString();
            }
        }

        private void encounter_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is RequestNonHpfEncounterDetails)
            {
                RequestNonHpfEncounterDetails nonHpfEncounter = e.Node.Tag as RequestNonHpfEncounterDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", nonHpfEncounter.PageCount);
            }
            else if (e.Node.Tag is RequestAttachmentEncounterDetails)
            {
                RequestAttachmentEncounterDetails attachmentEncounter = e.Node.Tag as RequestAttachmentEncounterDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", attachmentEncounter.PageCount);
            }
        }

        private void dateOfService_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is RequestEncounterDetails)
            {
                RequestEncounterDetails enc = e.Node.Tag as RequestEncounterDetails;
                if (enc.AdmitDate.HasValue)
                {
                    e.Value = enc.AdmitDate.Value.ToShortDateString();
                }
            }
            else if (e.Node.Tag is RequestNonHpfEncounterDetails)
            {
                RequestNonHpfEncounterDetails nonHpfEncounter = e.Node.Tag as RequestNonHpfEncounterDetails;
                if (nonHpfEncounter.DateOfService.HasValue)
                {
                    e.Value = nonHpfEncounter.DateOfService.Value.ToShortDateString();
                }
            }
            else if (e.Node.Tag is RequestAttachmentEncounterDetails)
            {
                RequestAttachmentEncounterDetails attachmentEncounter = e.Node.Tag as RequestAttachmentEncounterDetails;
                if (attachmentEncounter.DateOfService.HasValue)
                {
                    e.Value = attachmentEncounter.DateOfService.Value.ToShortDateString();
                }
            }
        }

        private void icon_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            RequestEncounterDetails enc = e.Node.Tag as RequestEncounterDetails;
            if (enc != null)
            {
                BindableControl control = sender as BindableControl;
                if (enc.IsVip && control.DataPropertyName == "IsVip")
                {
                    e.Value = ROIImages.VipIcon;
                }
                if (enc.IsLocked && control.DataPropertyName == "IsLocked")
                {
                    e.Value = ROIImages.LockedPatientIcon;
                }
                if (enc.HasDeficiency && control.DataPropertyName == "IsDeficiency")
                {
                    e.Value = ROIImages.DeficiencyIcon;
                }
            }
        }

        /// <summary>
        /// Get pages for the selected node.
        /// </summary>
        /// <param name="selectedNode"></param>
        /// <param name="pages"></param>
        //private void GetPages(TreeNodeAdv selectedNode, IList<PageDetails> pages)
        //{
        //    if (typeof(PageDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
        //    {
        //        pages.Add(selectedNode.Tag as PageDetails);
        //    }
        //    foreach (TreeNodeAdv node in selectedNode.Children)
        //    {
        //        GetPages(node, pages);
        //    }
        //}


        private void FilterBy_CheckedChanged(object sender, EventArgs e)
        {
            if (allRadioButton.Checked)
            {
                releaseTreeModel.Filter = FilterBy.All;
            }
            else if (releasedRadioButton.Checked)
            {
                releaseTreeModel.Filter = FilterBy.Released;
            }
            else if (unReleasedRadioButton.Checked)
            {
                releaseTreeModel.Filter = FilterBy.Unreleased;
            }
            EnableButtons();
            if (allRadioButton.Checked)
            {
                PreviouslyReleased();
                resendButton.Enabled = !HasPreviouslyReleased && (outputMethod != OutputMethod.None);
                //CR#377567
                if (hasRights == false)
                    resendButton.Enabled = false;
            }
        }

        /// <summary>
        /// Sets enable property to remove and remove all buttons.
        /// </summary>
        /// <param name="enable"></param>
        public void EnableButtons()
        {
            bool releasedRecordSelected = ReleasedRecordSelected(tree.SelectedNodes);
            
            removeButton.Enabled  = (tree.SelectedNodes.Count > 0 && !releasedRecordSelected);

            removeAllButton.Enabled = (!releasedRadioButton.Checked && 
                                       tree.Root.Children.Count > 0 &&
                                       !AreAllItemsReleased(new List<TreeNodeAdv>(tree.Root.Children)));

            modifyBillingTierButton.Enabled = (tree.SelectedNodes.Count > 0 && 
                                               !releasedRecordSelected &&
                                               IsNonHPFRecordsOnlySelected(new List<TreeNodeAdv>(tree.SelectedNodes)));
            collapseButton.Enabled = expandButton.Enabled = tree.RowCount > 0;
        }

        /// <summary>
        /// Remove selected documents from DSR tree.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void removeButton_Click(object sender, EventArgs e)
        {
            if (ReleasedRecordSelected(tree.SelectedNodes))
            {
                ShowPreviouslyReleasedDialog();
            }

            RemoveSelectedDocuments(tree.SelectedNodes);
            RemoveDeletedItems(tree.SelectedNodes);
            releaseTreeModel.Refresh();
            EnableButtons();

            ((RequestPatientInfoUI)Pane.View).MarkDirty(sender, e);
        }

        private static bool ReleasedRecordSelected(ReadOnlyCollection<TreeNodeAdv> nodes)
        {
            List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(nodes);
            return !selectedNodes.TrueForAll(delegate(TreeNodeAdv selectedNode) { return !(selectedNode.Tag as BaseRequestItem).IsReleased; });
        }

        private void RemoveSelectedDocuments(ReadOnlyCollection<TreeNodeAdv> selectedNodes)
        {
            BaseRequestItem recordNode;
            foreach (TreeNodeAdv node in selectedNodes)
            {
                if (typeof(RequestDocumentSubtitle).IsAssignableFrom(node.Tag.GetType())) continue;

                RemoveSelectedDocuments(node.Children);

                recordNode = node.Tag as BaseRequestItem;
                if (!recordNode.IsReleased)
                {
                    releaseTreeModel.Remove(recordNode);
                }
            }
        }

        /// <summary>
        /// Retrieve all the patients, encounters, documents, versions and pages sequences to be deleted.
        /// </summary>
        private void RemoveDeletedItems(ReadOnlyCollection<TreeNodeAdv> selectedNodes)
        {
            long sequence;
            BaseRequestItem recordNode;
            foreach (TreeNodeAdv selectedNode in selectedNodes)
            {
                recordNode = selectedNode.Tag as BaseRequestItem;
                

                if (typeof(RequestPatientDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    RequestPatientDetails requestPatient = (RequestPatientDetails)selectedNode.Tag;

                    if (!recordNode.IsReleased)
                    {
                        if (requestPatient.IsHpf)
                        {
                            if (!deleteList.DeletedPatients.Contains(requestPatient.PatientSeq))
                            {
                                deleteList.DeletedPatients.Add(requestPatient.PatientSeq);
                            }
                        }
                        else
                        {
                            if (!deleteList.DeletesupplementalPatients.Contains(requestPatient.PatientSeq))
                            {
                                deleteList.DeletesupplementalPatients.Add(requestPatient.PatientSeq);
                            }
                        }
                    }
                    continue;
                }

                if (typeof(RequestEncounterDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    sequence = ((RequestEncounterDetails)selectedNode.Tag).EncounterSeq;
                    if (!recordNode.IsReleased)
                    {
                        if (!deleteList.DeletedEncounters.Contains(sequence))
                        {
                            deleteList.DeletedEncounters.Add(sequence);
                        }
                    }
                }
                if (typeof(RequestDocumentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    sequence = ((RequestDocumentDetails)selectedNode.Tag).DocumentSeq;
                    if (!recordNode.IsReleased)
                    {
                        if (!deleteList.DeletedDocuments.Contains(sequence))
                        {
                            deleteList.DeletedDocuments.Add(sequence);
                        }
                    }
                    //If the selected node parent is global documents, need to delete the parent global encounter also.
                    sequence = ((RequestDocumentDetails)selectedNode.Tag).EncounterSeq;
                    if (!recordNode.IsReleased)
                    {
                        if (typeof(RequestGlobalDocuments).IsAssignableFrom(selectedNode.Parent.Tag.GetType()))
                        {
                            deleteList.DeletedEncounters.Add(sequence);
                        }
                    }
                }
                if (typeof(RequestVersionDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    sequence = ((RequestVersionDetails)selectedNode.Tag).VersionSeq;
                    if (!recordNode.IsReleased)
                    {
                        if (!deleteList.DeletedVersions.Contains(sequence))
                        {
                            deleteList.DeletedVersions.Add(sequence);
                        }
                    }
                }
                if (typeof(RequestPageDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    sequence = ((RequestPageDetails)selectedNode.Tag).PageSeq;
                    if (!recordNode.IsReleased)
                    {
                        if (!deleteList.DeletedPages.Contains(sequence))
                        {
                            deleteList.DeletedPages.Add(sequence);
                        }
                    }
                }
                if (typeof(RequestGlobalDocuments).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    RequestGlobalDocuments globalDocuments = (RequestGlobalDocuments)selectedNode.Tag;
                    ReadOnlyCollection<TreeNodeAdv> globalDocumentNodes = selectedNode.Children;
                    int count;
                    if (!recordNode.IsReleased)
                    {
                        for (count = 0; count < globalDocumentNodes.Count; count++)
                        {
                            deleteList.DeletedDocuments.Add(((RequestDocumentDetails)globalDocumentNodes[count].Tag).DocumentSeq);
                        }
                    }
                }
                if (typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    long patientID = ((RequestNonHpfDocumentDetails)selectedNode.Tag).PatientID;
                    sequence = ((RequestNonHpfDocumentDetails)selectedNode.Tag).DocumentSeq;
                    if (!recordNode.IsReleased)
                    {
                        if (patientID != 0)
                        {
                            if (!deleteList.DeletesupplementalDocuments.Contains(sequence))
                            {
                                deleteList.DeletesupplementalDocuments.Add(sequence);
                            }
                        }
                        else
                        {
                            if (!deleteList.DeleteSupplementaryDocuments.Contains(sequence))
                            {
                                deleteList.DeleteSupplementaryDocuments.Add(sequence);
                            }
                        }
                    }
                }
                if (typeof(RequestAttachmentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    sequence = ((RequestAttachmentDetails)selectedNode.Tag).AttachmentSeq;
                    if (!recordNode.IsReleased)
                    {
                        //CR# 375114
                        if (((RequestAttachmentDetails)selectedNode.Tag).IsHPF)
                        {
                            if (!deleteList.DeleteSupplementaryAttachments.Contains(sequence))
                            {
                                deleteList.DeleteSupplementaryAttachments.Add(sequence);
                            }
                        }
                        else
                        {
                            if (!deleteList.DeleteSupplementalAttachments.Contains(sequence))
                            {
                                deleteList.DeleteSupplementalAttachments.Add(sequence);
                            }
                        }
                    }
                }

                if (typeof(RequestAttachments).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    ReadOnlyCollection<TreeNodeAdv> requestAttachmentEncounters = selectedNode.Children;
                    int requestAttachmentEncountersCount = requestAttachmentEncounters.Count;
                    for (int requestAttachmentCount = 0; requestAttachmentCount < requestAttachmentEncountersCount; requestAttachmentCount++)
                    {
                        ReadOnlyCollection<TreeNodeAdv> requestAttachments = requestAttachmentEncounters[requestAttachmentCount].Children;
                        //CR# 375114
                        int count = requestAttachments.Count;
                        int attachmentCount = 0;
                        for (int i = 0; i < count; i++)
                        {
                            RequestAttachmentDetails requestAttachmentDetails = (RequestAttachmentDetails)requestAttachments[attachmentCount].Tag;
                            sequence = requestAttachmentDetails.AttachmentSeq;
                            if (!recordNode.IsReleased)
                            {
                                if (!requestAttachmentDetails.IsHPF)
                                {
                                    if (!deleteList.DeleteSupplementalAttachments.Contains(sequence))
                                    {
                                        deleteList.DeleteSupplementalAttachments.Add(sequence);
                                    }
                                }
                                else
                                {
                                    if (!deleteList.DeleteSupplementaryAttachments.Contains(sequence))
                                    {
                                        deleteList.DeleteSupplementaryAttachments.Add(sequence);
                                    }
                                }
                            }
                            attachmentCount++;
                        }
                    }
                    
                }

                if (typeof(RequestAttachmentEncounterDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    ReadOnlyCollection<TreeNodeAdv> requestAttachments = selectedNode.Children;
                    int attachmentCount = requestAttachments.Count;
                    for (int count = 0; count < attachmentCount; count++)
                    {
                        RequestAttachmentDetails requestAttachmentDetails = (RequestAttachmentDetails)requestAttachments[count].Tag;
                        sequence = requestAttachmentDetails.AttachmentSeq;
                        if (!recordNode.IsReleased)
                        {
                            if (!requestAttachmentDetails.IsHPF)
                            {
                                if (!deleteList.DeleteSupplementalAttachments.Contains(sequence))
                                {
                                    deleteList.DeleteSupplementalAttachments.Add(sequence);
                                }
                            }
                            else
                            {
                                if (!deleteList.DeleteSupplementaryAttachments.Contains(sequence))
                                {
                                    deleteList.DeleteSupplementaryAttachments.Add(sequence);
                                }
                            }
                        }
                    }
                    
                }

                if (typeof(RequestNonHpfDocuments).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    ReadOnlyCollection<TreeNodeAdv> requestNonHPFDocumentEncounters = selectedNode.Children;
                    int nonHPFDocumentCount = requestNonHPFDocumentEncounters.Count;
                    for (int count = 0; count < nonHPFDocumentCount; count++)
                    {
                        ReadOnlyCollection<TreeNodeAdv> requestNonHPFDocuments = requestNonHPFDocumentEncounters[count].Children;
                        int requestNonHPFDcoumentCount = requestNonHPFDocuments.Count;
                        int nonHPFDocCount = 0;
                        for (int i = 0; i < requestNonHPFDcoumentCount; i++)
                        {
                            RequestNonHpfDocumentDetails requestNonHpfDocumentDetails = (RequestNonHpfDocumentDetails)requestNonHPFDocuments[nonHPFDocCount].Tag;
                            sequence = requestNonHpfDocumentDetails.DocumentSeq;

                            if (!recordNode.IsReleased)
                            {
                                if (!requestNonHpfDocumentDetails.IsHPF)
                                {
                                    if (!deleteList.DeletesupplementalDocuments.Contains(sequence))
                                    {
                                        deleteList.DeletesupplementalDocuments.Add(sequence);
                                    }
                                }
                                else
                                {
                                    if (!deleteList.DeleteSupplementaryDocuments.Contains(sequence))
                                    {
                                        deleteList.DeleteSupplementaryDocuments.Add(sequence);
                                    }
                                }
                            }
                            nonHPFDocCount++;
                        }
                        
                    }
                    
                }

                if (typeof(RequestNonHpfEncounterDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (recordNode.IsReleased)
                    {
                        RemoveDeletedItems(selectedNode.Children);
                    }
                    ReadOnlyCollection<TreeNodeAdv> requestNonHPFDocuments = selectedNode.Children;
                    int nonHPFDocumentCount = requestNonHPFDocuments.Count;
                    for (int count = 0; count < nonHPFDocumentCount; count++)
                    {
                        RequestNonHpfDocumentDetails requestNonHpfDocumentDetails = (RequestNonHpfDocumentDetails)requestNonHPFDocuments[count].Tag;
                        //int requestNonHPFDcoumentCount = requestNonHpfDocumentDetails.Count;
                        //int nonHPFDocCount = 0;
                        sequence = requestNonHpfDocumentDetails.DocumentSeq;
                        if (!recordNode.IsReleased)
                        {
                            if (!requestNonHpfDocumentDetails.IsHPF)
                            {
                                if (!deleteList.DeletesupplementalDocuments.Contains(sequence))
                                {
                                    deleteList.DeletesupplementalDocuments.Add(sequence);
                                }
                            }
                            else
                            {
                                if (!deleteList.DeleteSupplementaryDocuments.Contains(sequence))
                                {
                                    deleteList.DeleteSupplementaryDocuments.Add(sequence);
                                }
                            }
                        }
                    }
                    
                }

                if (selectedNode.Parent != null)
                {
                    if (!recordNode.IsReleased)
                    {
                        RemoveParent(selectedNode);
                    }
                }
            }
        }

        /// <summary>
        /// Removes the parent of the deleted node in DSR.
        /// </summary>
        /// <param name="selectedNode"></param>
        private void RemoveParent(TreeNodeAdv selectedNode)
        {
            long sequence;
            if (selectedNode.Parent != null)
            {
                selectedNode = selectedNode.Parent;
                if (typeof(RequestPatientDetails).IsAssignableFrom(selectedNode.Tag.GetType())) return;               

                if (typeof(RequestEncounterDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (((RequestEncounterDetails)selectedNode.Tag).GetChildren.Count == 0)
                    {
                        sequence = ((RequestEncounterDetails)selectedNode.Tag).EncounterSeq;
                        if (!deleteList.DeletedEncounters.Contains(sequence))
                        {
                            deleteList.DeletedEncounters.Add(sequence);
                        }
                    }                   
                }
                if (typeof(RequestDocumentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (((RequestDocumentDetails)selectedNode.Tag).GetChildren.Count == 0)
                    {
                        sequence = ((RequestDocumentDetails)selectedNode.Tag).DocumentSeq;
                        if (!deleteList.DeletedDocuments.Contains(sequence))
                        {
                            deleteList.DeletedDocuments.Add(sequence);
                        }
                        //Remove the parent(Global Encounter) of the global document while removing the global document.
                        //Checking of the children count is not required if it is the global document.
                        sequence = ((RequestDocumentDetails)selectedNode.Tag).EncounterSeq;
                        if(typeof(RequestGlobalDocuments).IsAssignableFrom(selectedNode.Parent.Tag.GetType()))
                        {
                            deleteList.DeletedEncounters.Add(sequence);
                        }
                    }
                }
                if (typeof(RequestVersionDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (((RequestVersionDetails)selectedNode.Tag).GetChildren.Count == 0)
                    {
                        sequence = ((RequestVersionDetails)selectedNode.Tag).VersionSeq;
                        if (!deleteList.DeletedVersions.Contains(sequence))
                        {
                            deleteList.DeletedVersions.Add(sequence);
                        }
                    }
                }
                if (typeof(RequestPageDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (((RequestPageDetails)selectedNode.Tag).GetChildren.Count == 0)
                    {
                        sequence = ((RequestPageDetails)selectedNode.Tag).PageSeq;
                        if (!deleteList.DeletedPages.Contains(sequence))
                        {
                            deleteList.DeletedPages.Add(sequence);
                        }
                    }
                }

                if (typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    if (!((RequestNonHpfDocumentDetails)selectedNode.Tag).IsReleased)
                    {

                        sequence = ((RequestNonHpfDocumentDetails)selectedNode.Tag).DocumentSeq;
                        if (!deleteList.DeletesupplementalDocuments.Contains(sequence))
                        {
                            deleteList.DeletesupplementalDocuments.Add(sequence);
                        }
                    }
                }
                if (typeof(RequestAttachmentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                {
                    sequence = ((RequestAttachmentDetails)selectedNode.Tag).AttachmentSeq;
                    if (!deleteList.DeleteSupplementalAttachments.Contains(sequence))
                    {
                        deleteList.DeleteSupplementalAttachments.Add(sequence);
                    }
                }

                RemoveParent(selectedNode);
            }
        }      

        private bool ShowPreviouslyReleasedDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(PreviouslyReleasedMessage);
            string titleText = rm.GetString(PreviouslyReleasedTitle);
            string okButtonText = rm.GetString(PreviouslyReleasedOkButton);
            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(PreviouslyReleasedOkButtonToolTip);
            
            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Remove all the nodes from DSR tree.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void removeAllButton_Click(object sender, EventArgs e)
        {
            if (ReleasedRecordSelected(tree.Root.Children))
            {
                ShowPreviouslyReleasedDialog();
            }
            RemoveSelectedDocuments(tree.Root.Children);
            RemoveDeletedItems(tree.Root.Children);
            releaseTreeModel.Refresh();
            EnableButtons();
            EnableFilterButtons();
            ((RequestPatientInfoUI)Pane.View).MarkDirty(sender, e);
        }

        private void modifyBillingTierButton_Click(object sender, EventArgs e)
        {  

            ModifyBillingTierUI modifyBillingTierUI = new ModifyBillingTierUI(Pane);
            modifyBillingTierUI.PrePopulate(((RequestPatientInfoUI)Pane.View).BillingTiers);

            if (tree.Selection.Count == 1 && 
                typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(tree.SelectedNode.Tag.GetType()))
            {  
                    modifyBillingTierUI.SetData(((RequestNonHpfDocumentDetails)tree.SelectedNode.Tag).BillingTier);
            }

            if (tree.Selection.Count == 1 &&
                typeof(RequestAttachmentDetails).IsAssignableFrom(tree.SelectedNode.Tag.GetType()))
            {
                modifyBillingTierUI.SetData(((RequestAttachmentDetails)tree.SelectedNode.Tag).BillingTier);
            }
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form form = ROIViewUtility.ConvertToForm(null,
                                                     rm.GetString("modifyBillingTierUI.titlebar.text"),
                                                     modifyBillingTierUI);

            DialogResult result = form.ShowDialog();

            if (result == DialogResult.OK)
            {
                List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
                long selectedBillingTier = modifyBillingTierUI.SelectedBillingTier;

                selectedNodes.ForEach(delegate(TreeNodeAdv selectedNode)
                {
                    if (typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                    {
                        ((RequestNonHpfDocumentDetails)selectedNode.Tag).BillingTier = selectedBillingTier;
                    }
                }); 
                selectedNodes.ForEach(delegate(TreeNodeAdv selectedNode)
                {
                    if (typeof(RequestAttachmentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
                    {
                        ((RequestAttachmentDetails)selectedNode.Tag).BillingTier = selectedBillingTier;
                    }
                });
                ((RequestPatientInfoUI)Pane.View).MarkDirty(sender, e);
            }

            form.Close();
            form.Dispose();
        }

        public void EnableFilterButtons()
        {
            List<RequestPatientDetails> patients = new List<RequestPatientDetails>(releaseTreeModel.Request.Patients.Values);
            patients = patients.FindAll(delegate(RequestPatientDetails patient) { return patient.HasDocuments ; });

            allRadioButton.Enabled = releasedRadioButton.Enabled = unReleasedRadioButton.Enabled = patients.Count > 0;
            collapseButton.Enabled = expandButton.Enabled = tree.RowCount > 0;
        }

        private void expandButton_Click(object sender, EventArgs e)
        {
            foreach (TreeNodeAdv node in tree.Root.Children)
            {
                ExpandTreeNode(node);
            }
            tree.Refresh();

        }

        /// <summary>
        /// Checks whether the node is expanded, if it expanded then it looks for
        /// child node for expansion.
        /// </summary>
        /// <param name="node"></param>
        private static void ExpandTreeNode(TreeNodeAdv node)
        {
            if (node.IsExpanded)
            {
                foreach (TreeNodeAdv childNode in node.Children)
                {
                    if (childNode.IsExpanded)
                    {
                        ExpandTreeNode(childNode);
                    }
                    else
                    {
                        childNode.IsExpanded = childNode.Children.Count > 0;
                    }
                }
            }
            else
            {
                node.IsExpanded = node.Children.Count > 0;
            }
        }

        private void collapseButton_Click(object sender, EventArgs e)
        {
            foreach (TreeNodeAdv node in tree.Root.Children)
            {
                CollapseNode(node);
            }
            tree.Refresh();
            isChildExpanded = false;
        }

        /// <summary>
        /// Checks whether the node is expanded, if it expanded then it looks for
        /// child node for expansion.
        /// </summary>
        /// <param name="node"></param>
        private void CollapseNode(TreeNodeAdv node)
        {
            if (node.IsExpanded)
            {
                isChildExpanded = false;
                bool allCollapsed = true;
                foreach (TreeNodeAdv childNode in node.Children)
                {
                    if (!childNode.IsExpanded)
                    {
                        childNode.Parent.IsExpanded = false;
                        isChildExpanded = true;
                    }
                    else
                    {
                        allCollapsed = false;
                        CollapseNode(childNode);
                    }
                    childNode.Parent.IsExpanded = !allCollapsed;
                }
                if (!isChildExpanded)
                {
                    node.IsExpanded = false;
                }
            }
        }

        private void pagesReleased(BaseRequestItem item)
        {
            if (typeof(RequestPageDetails).IsAssignableFrom(item.GetType())
                || typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(item.GetType())
                || typeof(RequestAttachmentDetails).IsAssignableFrom(item.GetType()))
            {
                if (item.SelectedForRelease == true && item.IsDeleted == true)
                {
                    deletedCount++;
                }
                if (item.SelectedForRelease == true && item.IsReleased == false)
                {
                    unreleasedCount++;
                }
                else if (item.SelectedForRelease == true && item.IsReleased == true)
                {
                    releasedPageCount++;
                }
            }

            foreach (BaseRequestItem child in item.GetChildren)
            {
                pagesReleased(child);
            }
        }

        /// <summary>
        /// Verify the DSR tree by user selected any unrelased pages
        /// </summary>
        public void PreviouslyReleased()
        {
            releasedPageCount = 0;
            unreleasedCount = 0;
            deletedCount = 0;

            foreach (RequestPatientDetails patient in releaseTreeModel.Request.Patients.Values)
            {
                pagesReleased(patient);
                pagesReleased(patient.GlobalDocument);
                pagesReleased(patient.NonHpfDocument);
                pagesReleased(patient.Attachment);
            }

            if ( ( (unreleasedCount > 0 && releasedPageCount > 0)|| (deletedCount > 0) )
                    && !isMessageDisplayed)
            {
                if (deletedCount > 0)
                {
                    ShowRemoveDeletedPagesDialog();
                }
                else
                {
                    ShowResendOrReleaseDialog();
                }
                isMessageDisplayed = true;
            }

            if (unreleasedCount == 0 || releasedPageCount == 0 || deletedCount == 0)
            {
                isMessageDisplayed = false;
            }
        }

        public void ShowResendOrReleaseDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string okButtonText = rm.GetString("okButton.DialogUI");
            string titleText = rm.GetString("resenddialog.title");
            string messageText = rm.GetString("resenddialog.message");
            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, "", ROIDialogIcon.Alert);
        }


        public void ShowRemoveDeletedPagesDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string okButtonText = rm.GetString("okButton.DialogUI");
            string titleText = rm.GetString("removedeletedpagesdialog.title");
            string messageText = rm.GetString("removedeletedpagesdialog.message");
            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, "", ROIDialogIcon.Alert);
        }
        #endregion

        #region Properties

        /// <summary>
        /// Holds DSR tree model.
        /// </summary>
        public ReleaseTreeModel ReleaseTreeModel
        {
            get { return releaseTreeModel; }
        }

        public DeleteList DeleteList
        {
            get { return deleteList; }
        }

        /// <summary>
        /// Returns the resend button control
        /// </summary>
        public Button ResendButton
        {
            get { return resendButton; }
        }

        /// <summary>
        /// Checks the whether the records in DSR are checked.
        /// </summary>
        public bool HasRecordsChecked
        {
            get
            {
                if (tree.ItemCount == 0) return false;

                List<RequestPatientDetails> patients = new List<RequestPatientDetails>(releaseTreeModel.Request.Patients.Values);
                patients = patients.FindAll(delegate(RequestPatientDetails patient) { return (patient.HasDocuments && patient.CheckedState != false); });
                return (patients.Count > 0);

            }
        }

        /// <summary>
        /// Verify the DSR tree by user selected any unrelased pages or deletedPages
        /// and verifty the user has rights to print or save as pdf option
        /// </summary>
        public bool HasPreviouslyReleased
        {
            get
            {
                // CR#359303
                string securityRights = (outputMethod == OutputMethod.Disc) ? ROISecurityRights.ROIReleaseToDisc : ((outputMethod == OutputMethod.SaveAsFile) ? ROISecurityRights.ROIExportToPDF : ROISecurityRights.ROIPrintFax);                

                return (unreleasedCount > 0
                        || unreleasedCount > 0 && releasedPageCount > 0
                        || unreleasedCount == 0 && releasedPageCount == 0 
                        || !IsAllowed(securityRights)
                        || deletedCount > 0);
            }
        }

        /// <summary>
        /// Gets the count of previoulsy releaed pages which are checked in DSR
        /// </summary>
        public int ReleasedPageCount
        {
            get { return releasedPageCount; }
        }

        /// <summary>
        /// Gets the count of new pages which are checked in DSR
        /// </summary>
        public int UnreleasedPageCount
        {
            get { return unreleasedCount; }
        }


        /// <summary>
        /// Gets the count of deleted pages which are checked in DSR
        /// </summary>
        public int DeletedPageCount
        {
            get { return deletedCount; }
        }

        /// <summary>
        /// Gets or sets the output method of the released request
        /// </summary>
        public OutputMethod OutputMethod
        {
            get { return outputMethod; }
            set { outputMethod = value; }
        }

        #endregion                 

    }
}
