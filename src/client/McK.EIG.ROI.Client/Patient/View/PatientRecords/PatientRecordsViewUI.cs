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
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Configuration;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Resources;
using System.Windows.Forms;
using Microsoft.Win32;

using McKesson.Capture.Model;
using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common.Tree;
using McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.View.BillingPayment;

using McKesson.EIG.WinDSS.API;
using McKesson.EIG.WinDSS.Controller;
using McKesson.EIG.WinDSS.Model;
using McKesson.EIG.WinDSS.View;
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    public partial class PatientRecordsViewUI : ROIBaseUI
    {
        #region Fields

        private const string NonHpfAttachmentTitle = "NonHpfAttachmentErrorDialog.Title";
        private const string NonHpfAttachmentMessage = "NonHpfAttachmentErrorDialog.Message";
        private const string NonHpfAttachmentsTitle = "NonHpfAttachmentsErrorDialog.Title";
        private const string NonHpfAttachmentsMessage = "NonHpfAttachmentsErrorDialog.Message";
        private const string HpfNonHpfAttachmentsTitle = "HpfNonHpfAttachmentsErrorDialog.Title";
        private const string HpfNonHpfAttachmentsMessage = "HpfNonHpfAttachmentsErrorDialog.Message";
        private const string HpfNonHpfTitle = "HpfNonHpfErrorDialog.Title";
        private const string HpfNonHpfMessage = "HpfNonHpfErrorDialog.Message";
        private const string HpfAttachmentsTitle = "HpfAttachmentsErrorDialog.Title";
        private const string HpfAttachmentsMessage = "HpfAttachmentsErrorDialog.Message";

        private const string ViewDocumentErrorOkButton = "ViewSelectedErrorDialog.OkButton";
        private const string ViewDocumentOkButtonToolTip = "ViewSelectedErrorDialog.OkButton";

        //CR#359317 - Check whether the HPF Workstation is installed
        private const string INSTALLDIRHIVE = "SOFTWARE\\IMNET\\INSTALLEDAPPS";
        private const string INSTALLDIRHIVE64BIT = "SOFTWARE\\Wow6432Node\\IMNET\\INSTALLEDAPPS";
        
        private WinDocumentViewer winDssViewer = null;
        private IList<PageDetails> pages;
        internal PatientRecordViewTreeModel patTreeModel;

        private NonHpfDocumentDetails modifiedNonHpfDocument;
        private AttachmentDetails modifiedAttachment;
        private Hashtable encounterList = new Hashtable();
        // CR#365724
        private OrderedDictionary orderedEncounterList = new OrderedDictionary();		
        private const string globalDocument = "Global";
        private bool isChildExpanded;
        bool showEncounterDialog;

        private bool canAccessRecordView;
        private new List<RequestPatientDetails> patientDetails;
        private long reqId;
        #endregion

        #region Constructor

        public PatientRecordsViewUI()
        {
            InitializeComponent();
            patTreeModel = new PatientRecordViewTreeModel();
            //winDssViewer = new WinDocumentViewer();
            // reset the logfactory, so that it will continue to log to ROI application log
			// CR fix for 347793
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            showVersionCheckBox.Visible = IsAllowed(ROISecurityRights.ViewVersionedDocuments);
        }

        #endregion

        #region Methods

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, recordViewLabel);
            SetLabel(rm, sortByLabel);
            SetLabel(rm, encounterRadioButton);
            SetLabel(rm, documentRadioButton);
            SetLabel(rm, showVersionCheckBox);
            SetLabel(rm, disclosureCheckBox);

            SetLabel(rm, encounterDetailsButton);
            SetLabel(rm, viewSelectedButton);
            SetLabel(rm, viewAllButton);
            SetLabel(rm, newDocumentButton);
            SetLabel(rm, editDocumentButton);
            SetLabel(rm, newAttachmentButton);
            SetLabel(rm, editAttachmentButton);
            SetLabel(rm, expandButton);
            SetLabel(rm, collapseButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, addButton);
            SetTooltip(rm, toolTip, addAllButton);
            SetTooltip(rm, toolTip, recordViewComboBox);
            SetTooltip(rm, toolTip, sortByLabel);
            SetTooltip(rm, toolTip, showVersionCheckBox);
            SetTooltip(rm, toolTip, disclosureCheckBox);

            SetTooltip(rm, toolTip, documentRadioButton);
            SetTooltip(rm, toolTip, encounterRadioButton);

            SetTooltip(rm, toolTip, viewSelectedButton);
            SetTooltip(rm, toolTip, viewAllButton);
            SetTooltip(rm, toolTip, newDocumentButton);
            SetTooltip(rm, toolTip, editDocumentButton);
            SetTooltip(rm, toolTip, newAttachmentButton);
            SetTooltip(rm, toolTip, editAttachmentButton);
            SetTooltip(rm, toolTip, encounterDetailsButton);
            toolTip.SetToolTip(expandButton, rm.GetString("expandButton"));
            toolTip.SetToolTip(collapseButton, rm.GetString("collapseButton"));
        }

        public void PopulateRecordViews(object data)
        {
            
            Collection<RecordViewDetails> recordViews = (data == null) ? new Collection<RecordViewDetails>() : data as Collection<RecordViewDetails>;

            if (recordViews.Count == 0)
            {
                ShowRecordViewDialog();
                return;
            }

            recordViewComboBox.BeginUpdate();
            recordViewComboBox.DisplayMember = "Name";
            recordViewComboBox.ValueMember = "Id";
            recordViewComboBox.DataSource = recordViews;
            if (recordViews.Count > 0)
            {
                recordViewComboBox.SelectedIndex = 0;
            }
            recordViewComboBox.EndUpdate();

            canAccessRecordView = true;
        }

        public void EnableEvents()
        {
            DisableEvents();
            recordViewComboBox.SelectedIndexChanged += new EventHandler(recordViewComboBox_SelectedIndexChanged);
            tree.NodeMouseDoubleClick += new EventHandler<TreeNodeAdvMouseEventArgs>(tree_NodeMouseDoubleClick);
            tree.MouseUp += new MouseEventHandler(tree_NodeMouseUp);
            tree.SelectionChanged += new EventHandler(tree_SelectionChanged);
        }

        public void DisableEvents()
        {
            recordViewComboBox.SelectedIndexChanged -= new EventHandler(recordViewComboBox_SelectedIndexChanged);
            tree.NodeMouseDoubleClick -= new EventHandler<TreeNodeAdvMouseEventArgs>(tree_NodeMouseDoubleClick);
            tree.MouseUp -= new MouseEventHandler(tree_NodeMouseUp);
            tree.SelectionChanged -= new EventHandler(tree_SelectionChanged);
        }

        /// <summary>
        /// Executes when when user changes record view.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void recordViewComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            patTreeModel.RecordView = (RecordViewDetails)recordViewComboBox.SelectedItem;
            EnableViewButtons();
        }

        private void encounterRadioButton_CheckedChanged(object sender, EventArgs e)
        {
            if (encounterRadioButton.Checked)
            {
                patTreeModel.SortBy = SortBy.Encounter;
                patTreeModel.ShowVersion = showVersionCheckBox.Checked;
                EnableViewButtons();
            }
        }

        private void documentRadioButton_CheckedChanged(object sender, EventArgs e)
        {
            if (documentRadioButton.Checked)
            {
                patTreeModel.SortBy = SortBy.Document;
                patTreeModel.ShowVersion = showVersionCheckBox.Checked;
                EnableViewButtons();
            }
        }

        private void EnableViewButtons()
        {
            if (patTreeModel.PatientInfo != null)
            {
                bool enableView = (patTreeModel.PatientInfo.Encounters.Count > 0 || patTreeModel.PatientInfo.GlobalDocument.Documents.Count > 0);
                //may have encounters but no actual documents verify at least one document exists
                bool hasHpfDocuments = false;
                foreach (EncounterDetails encounterNode in patTreeModel.PatientInfo.Encounters)
                {
                    if (encounterNode.Documents.Count > 0)
                    {
                        hasHpfDocuments = true;
                        break;
                    }
                }
 
                viewAllButton.Enabled = (enableView && hasHpfDocuments && tree.Root.Children.Count > 0 && ROIViewUtility.IsAllowed(ROISecurityRights.ViewPatientDocuments));
                viewSelectedButton.Enabled = (tree.Selection.Count == 1) && ROIViewUtility.IsAllowed(ROISecurityRights.ViewPatientDocuments);
            }
            else
            {
                viewAllButton.Enabled = false;
                viewSelectedButton.Enabled = false;
            }
            addAllButton.Enabled = collapseButton.Enabled = expandButton.Enabled = tree.RowCount > 0;
        }

        private bool HasNonHpfDocumentSelection()
        {
            List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
            selectedNodes = selectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) { return (selectedNode.Tag is PatientNonHpfDocument || selectedNode.Tag is NonHpfEncounterDetails || selectedNode.Tag is NonHpfDocumentDetails); });
            return (selectedNodes.Count > 0);
        }

        private bool HasAttachmentSelection()
        {
            List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
            selectedNodes = selectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) { return (selectedNode.Tag is PatientAttachment || selectedNode.Tag is AttachmentEncounterDetails || selectedNode.Tag is AttachmentDetails); });
            return (selectedNodes.Count > 0);
        }

        private bool HasOneAttachmentSelection()
        {
            List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
            selectedNodes = selectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) { return (selectedNode.Tag is AttachmentDetails); });
            return (selectedNodes.Count == 1);
        }

        private bool HasHpfDocumentSelection()
        {
            List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
            selectedNodes = selectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) { return (selectedNode.Tag is PatientDetails || 
                selectedNode.Tag is PageDetails || selectedNode.Tag is PatientGlobalDocument ||
                selectedNode.Tag is DocumentDetails || selectedNode.Tag is EncounterDetails || selectedNode.Tag is DocumentType);
            });
            return (selectedNodes.Count > 0);
        }

        private void disclosureCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            patTreeModel.IsDisclosure = disclosureCheckBox.Checked;
            EnableViewButtons();
        }

        private void showVersionsCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            patTreeModel.ShowVersion = showVersionCheckBox.Checked;
        }

        public void SetData(object data)
        {
            PatientDetails patientInfo = data as PatientDetails;
            patTreeModel.PatientInfo = patientInfo;

            bool isHpf = patientInfo.IsHpf;

            topPanel.Enabled = isHpf && canAccessRecordView;
            
            viewAllButton.Visible = isHpf;
            viewAllButton.Enabled = canAccessRecordView;

            encounterRadioButton.Checked = true;
            if (encounterRadioButton.Checked)
            {
                patTreeModel.SortBy = SortBy.Encounter;
            }
            else
            {
                patTreeModel.SortBy = SortBy.Document;
            }

            patTreeModel.RecordView = (RecordViewDetails)recordViewComboBox.SelectedItem;
            tree.Model = patTreeModel;
            tree.Model.StructureChanged += new EventHandler<TreePathEventArgs>(Model_StructureChanged);

            EnableViewButtons();

            EnableNewDocumentButton = true;
            EnableEditDocumentButton = false;
            EnableNewAttachmentButton = true;
            EnableEditAttachmentButton = false;
            encounterDetailsButton.Enabled = isHpf && patientInfo.Encounters.Count > 0 && canAccessRecordView && tree.SelectedNodes.Count > 0;
            tree.Enabled = tree.RowCount > 0;
            if (tree.CurrentNode != null)
                tree.CurrentNode.IsSelected = true;

            //collapseButton.Enabled = expandButton.Enabled = tree.RowCount > 0;
        }

        private void Model_StructureChanged(object sender, TreePathEventArgs e)
        {
            tree.Enabled = tree.RowCount > 0;
        }

        private void PatientRecordsView_Load(object sender, EventArgs e)
        {
            name.DrawText += new EventHandler<DrawEventArgs>(name_DrawText);
            facility.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(facility_ValueNeeded);
            facility.DrawText += new EventHandler<DrawEventArgs>(facility_DrawText);
            department.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(department_ValueNeeded);
            dateOfService.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(dateOfService_ValueNeeded);
            vipIcon.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(icon_ValueNeeded);
            lockedIcon.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(icon_ValueNeeded);
            deficiencyIcon.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(icon_ValueNeeded);
            encounter.ValueNeeded += new EventHandler<NodeControlValueEventArgs>(encounter_ValueNeeded);
        }

        private void facility_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is EncounterDetails)
            {
                EncounterDetails enc = e.Node.Tag as EncounterDetails;
                e.Value = enc.PatientType ?? string.Empty;
            }
            else if (e.Node.Tag is DocumentType)
            {
                DocumentType docType = e.Node.Tag as DocumentType;
                e.Value = docType.Subtitle ?? string.Empty;
            }
            else if (e.Node.Tag is NonHpfEncounterDetails)
            {
                NonHpfEncounterDetails nonHpfEncounter = e.Node.Tag as NonHpfEncounterDetails;
                e.Value = nonHpfEncounter.Facility ?? string.Empty;
            }
            else if (e.Node.Tag is NonHpfDocumentDetails)
            {
                NonHpfDocumentDetails nonHpfDoc = e.Node.Tag as NonHpfDocumentDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", nonHpfDoc.PageCount);
            }
            else if (e.Node.Tag is AttachmentEncounterDetails)
            {
                AttachmentEncounterDetails attachmentEncounter = e.Node.Tag as AttachmentEncounterDetails;
                e.Value = attachmentEncounter.Facility ?? string.Empty;
            }
            else if (e.Node.Tag is AttachmentDetails)
            {
                AttachmentDetails attachmentDoc = e.Node.Tag as AttachmentDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", attachmentDoc.PageCount);
            }
        }

        private void department_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is EncounterDetails)
            {
                EncounterDetails enc = e.Node.Tag as EncounterDetails;
                e.Value = enc.Facility ?? string.Empty;
            }
            else if (e.Node.Tag is DocumentType)
            {
                DocumentType docType = e.Node.Tag as DocumentType;
                if (docType.DateOfService.HasValue)
                {
                    e.Value = docType.DateOfService.Value.ToString();
                }
            }
            else if (e.Node.Tag is NonHpfEncounterDetails)
            {
                NonHpfEncounterDetails nonHpfEncounter = e.Node.Tag as NonHpfEncounterDetails;
                e.Value = nonHpfEncounter.Department ?? string.Empty;
            }
            else if (e.Node.Tag is AttachmentEncounterDetails)
            {
                AttachmentEncounterDetails attachmentEncounter = e.Node.Tag as AttachmentEncounterDetails;
                e.Value = attachmentEncounter.Facility ?? string.Empty;
            }
        }

        private void dateOfService_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is EncounterDetails)
            {
                EncounterDetails enc = e.Node.Tag as EncounterDetails;
                if (enc.AdmitDate.HasValue)
                {
                    e.Value = enc.AdmitDate.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
            }
            else if (e.Node.Tag is NonHpfEncounterDetails)
            {
                NonHpfEncounterDetails nonHpfEncounter = e.Node.Tag as NonHpfEncounterDetails;
                if (nonHpfEncounter.DateOfService.HasValue)
                {
                    e.Value = nonHpfEncounter.DateOfService.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
            }
            else if (e.Node.Tag is AttachmentEncounterDetails)
            {
                AttachmentEncounterDetails attachmentEncounter = e.Node.Tag as AttachmentEncounterDetails;
                if (attachmentEncounter.DateOfService.HasValue)
                {
                    e.Value = attachmentEncounter.DateOfService.Value.ToShortDateString();
                }
            }
        }

        private void encounter_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            if (e.Node.Tag is NonHpfEncounterDetails)
            {
                NonHpfEncounterDetails nonHpfEncounter = e.Node.Tag as NonHpfEncounterDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", nonHpfEncounter.PageCount);
            }
            if (e.Node.Tag is AttachmentEncounterDetails)
            {
                AttachmentEncounterDetails attachmentEncounter = e.Node.Tag as AttachmentEncounterDetails;
                e.Value = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0} pages", attachmentEncounter.PageCount);
            }
        }

        private void icon_ValueNeeded(object sender, NodeControlValueEventArgs e)
        {
            EncounterDetails enc = e.Node.Tag as EncounterDetails;
            if (enc != null)
            {
                BindableControl control = sender as BindableControl;
                if (enc.IsVip && control.DataPropertyName == "IsVip")
                {
                    e.Value = ROIImages.DocumentVipIcon;
                }
                if (enc.IsLocked && control.DataPropertyName == "IsLocked")
                {
                    e.Value = ROIImages.DocumentLockedIcon;
                }
                if (enc.HasDeficiency && control.DataPropertyName == "IsDeficiency")
                {
                    e.Value = ROIImages.DeficiencyIcon;
                }
            }
        }

        private void name_DrawText(object sender, DrawEventArgs e)
        {
            if (e.Node.Tag is EncounterDetails)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                EncounterDetails enc = e.Node.Tag as EncounterDetails;
                enc.SelfPayEncounterID = enc.EncounterId + rm.GetString("Self Pay Encounter");
            }
            if (e.Node.Tag is DocumentSubtitle)
            {
                DocumentSubtitle tmpNode = e.Node.Tag as DocumentSubtitle;
                e.TextColor = tmpNode.TextColor;
            } 
            if (e.Node.Tag is AttachmentDetails)
            {
                AttachmentDetails tmpNode = e.Node.Tag as AttachmentDetails;
                if (tmpNode.IsDeleted)
                {
                    e.TextColor = Color.Gray;
                }
            }
        }

        private void facility_DrawText(object sender, DrawEventArgs e)
        {


            if (e.Node.Tag is AttachmentDetails)
            {
                AttachmentDetails tmpNode = e.Node.Tag as AttachmentDetails;
                if (tmpNode.IsDeleted)
                {
                        e.TextColor = Color.Gray;
                }
            }
        }

        private void tree_NodeMouseUp(object sender, MouseEventArgs e)
        {
            if (tree.Selection.Count == 1)
            {
                if (!(tree.SelectedNode.Tag is EncounterDetails))
                {
                    viewSelectedButton.Enabled = !(tree.SelectedNode.Tag is PatientNonHpfDocument ||
                                                   tree.SelectedNode.Tag is PatientAttachment ||
                                                   tree.SelectedNode.Tag is NonHpfEncounterDetails ||
                                                   tree.SelectedNode.Tag is NonHpfDocumentDetails ||
                                                   tree.SelectedNode.Tag is AttachmentEncounterDetails ||
                                                   tree.SelectedNode.Tag is DocumentSubtitle ||
                                                   tree.SelectedNode.Tag is EncounterDetails) && ROIViewUtility.IsAllowed(ROISecurityRights.ViewPatientDocuments);
                    EnableEditDocumentButton = (tree.SelectedNode.Tag is NonHpfDocumentDetails);
                    EnableEditAttachmentButton = (tree.SelectedNode.Tag is AttachmentDetails);
                }
                else if (tree.SelectedNode.Tag is EncounterDetails)
                {
                    viewSelectedButton.Enabled = tree.SelectedNode.Nodes.Count > 0 && ROIViewUtility.IsAllowed(ROISecurityRights.ViewPatientDocuments);
                }
            }
            else if (tree.Selection.Count > 1)
            {
                List<TreeNodeAdv> hpfSelectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
                hpfSelectedNodes = hpfSelectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) { return (selectedNode.Tag is NonHpfDocumentDetails); });
                
                List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
                selectedNodes = selectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) { return (selectedNode.Tag is NonHpfDocumentDetails); });
                EnableEditDocumentButton = selectedNodes.Count == 1 && tree.Selection.Count == 1;
                //if multi-select does not contain an Non-Hfp Document 
                //check to see if multi-select contains an Attachment
                if (EnableEditDocumentButton == false)
                {
                    selectedNodes = selectedNodes.FindAll(delegate(TreeNodeAdv selectedNode) { return (selectedNode.Tag is AttachmentDetails); });
                    EnableEditAttachmentButton = selectedNodes.Count == 1 && tree.Selection.Count == 1;
                }

                /* set view selected button
                 * if there are hpf docs selected enable view selected -
                 * if there are no hpf docs but multiple attachments selected - do not enable view selected (can only view one attachment)
                 */
                viewSelectedButton.Enabled = (!HasHpfDocumentSelection() && HasOneAttachmentSelection()) || 
                                                (HasHpfDocumentSelection());
                
            }
        }


        #region Tree click events

        private void tree_NodeMouseClick(object sender, TreeNodeAdvMouseEventArgs e)
        {
            TreeNodeAdv node = tree.SelectedNode;

            if (node == null) return;

            if (node.Tag is NonHpfDocumentDetails)
            {
                EnableEditDocumentButton = true;
                return;
            }
            else if (node.Tag is AttachmentDetails)
            {
                EnableEditAttachmentButton = true;
                return;
            }
            else if (node.Tag is EncounterDetails)
            {
                NodeControlInfo info = tree.GetNodeControlInfoAt(e.Location);
                if (info.Control != null)
                {
                    if (typeof(NodeIcon).IsAssignableFrom(info.Control.GetType()))
                    {
                        NodeIcon icon = info.Control as NodeIcon;
                        if (icon.DataPropertyName == "IsDeficiency")
                        {
                            EncounterDetails selectedNode = e.Node.Tag as EncounterDetails;
                            if (selectedNode.HasDeficiency)
                            {
                                ShowDeficiencyDialog(selectedNode);
                            }
                        }
                    }
                }
            }
            EnableEditDocumentButton = false;
            EnableEditAttachmentButton = false;
        }

        /// <summary>
        /// Event invoked when tree node double clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void tree_NodeMouseDoubleClick(object sender, TreeNodeAdvMouseEventArgs e)
        {
            TreeNodeAdv node = tree.SelectedNode;
            if (node == null) return;

            if (typeof(EncounterDetails).IsAssignableFrom(node.Tag.GetType()))
            {
                EncounterDetails selectedNode = e.Node.Tag as EncounterDetails;
                if (selectedNode.HasDeficiency)
                {
                    ShowDeficiencyDialog(selectedNode);
                }
            }
            else if (typeof(NonHpfDocumentDetails).IsAssignableFrom(node.Tag.GetType()))
            {
                NonHpfDocumentDetails document = (NonHpfDocumentDetails)node.Tag;
                ShowEditDocumentDialog(document);
            }
            else if (typeof(AttachmentDetails).IsAssignableFrom(node.Tag.GetType()))
            {
                AttachmentDetails  document = (AttachmentDetails )node.Tag;
                ShowEditAttachmentDialog(document);
            }
            else if (typeof(DocumentType).IsAssignableFrom(node.Tag.GetType())
                || typeof(VersionDetails).IsAssignableFrom(node.Tag.GetType())
                || typeof(PageDetails).IsAssignableFrom(node.Tag.GetType()))
            {
                viewSelectedButton.PerformClick();
            }
        }

        ///CR#365724
        /// <summary>
        /// Method used to clear the selected child nodes if its corresponding parent node will be selected
        /// </summary>
        /// <param name="node"></param>
        private void ClearSelection(TreeNodeAdv node)
        {
            foreach (TreeNodeAdv childNode in node.Children)
            {
                if (childNode != null)
                {
                    childNode.IsSelected = false;
                    if (childNode.Children != null)
                    {
                        ClearSelection(childNode);
                    }
                }
            }
        }

        ///CR#365724
        /// <summary>
        /// Method used to clear the selected parent node if its corresponding child nodes will be selected
        /// </summary>
        /// <param name="node"></param>
        private void ClearParentSelection(TreeNodeAdv node)
        {
            if (node.Parent != null)
            {
                if (node.Parent.IsSelected)
                {
                    node.Parent.IsSelected = false;
                }
                ClearParentSelection(node.Parent);
            }
        }
		
        // <summary>
        /// Tree selection event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void tree_SelectionChanged(object sender, EventArgs e)
        {
            TreeNodeAdv node = tree.SelectedNode;
            encounterDetailsButton.Enabled = false;
			
            // CR#365724. To clear the selected child nodes if its corresponding parent node is selected
            if (node != null && !(node.Tag is PageDetails))
            {
                DisableEvents();
                ClearSelection(node);
                EnableEvents();
            }

            // CR#365724. To clear the selected parent node if its corresponding child nodes are selected
            if (node != null && node.Parent != null)
            {
                DisableEvents();
                ClearParentSelection(node);
                EnableEvents();
            }			

            if (node == null)
            {
                viewSelectedButton.Enabled = editDocumentButton.Enabled = editAttachmentButton.Enabled = false && ROIViewUtility.IsAllowed(ROISecurityRights.ViewPatientDocuments);
                return;
            }
            EnableEditDocumentButton = (node != null) && (node.Tag is NonHpfDocumentDetails) && (tree.SelectedNodes.Count == 1);
            EnableEditAttachmentButton = (node != null) && (node.Tag is AttachmentDetails ) && (tree.SelectedNodes.Count == 1);

            foreach (TreeNodeAdv treeNode in tree.SelectedNodes)
            {
                if (!(treeNode.Tag is PatientNonHpfDocument) && 
                    !(treeNode.Tag is NonHpfEncounterDetails) &&
                    !(treeNode.Tag is NonHpfDocumentDetails) &&
                    !(treeNode.Tag is DocumentSubtitle) &&
                    !(treeNode.Tag is EncounterDetails)) 
                {
                    viewSelectedButton.Enabled = true && ROIViewUtility.IsAllowed(ROISecurityRights.ViewPatientDocuments);
                    break;
                }
                else if (treeNode.Tag is EncounterDetails && treeNode.Nodes.Count > 0)
                {
                    viewSelectedButton.Enabled = true && ROIViewUtility.IsAllowed(ROISecurityRights.ViewPatientDocuments);
                    break;
                }
                viewSelectedButton.Enabled = false;
            }
            
            showEncounterDialog = false;

            foreach (TreeNodeAdv treeNode in tree.SelectedNodes)
            {
                if (treeNode.Tag is EncounterDetails)
                {
                    encounterDetailsButton.Enabled = true;                    
                }
                if ((treeNode.Tag is PatientNonHpfDocument) ||
                   (treeNode.Tag is NonHpfEncounterDetails) ||
                   (treeNode.Tag is NonHpfDocumentDetails) ||
                   (treeNode.Tag is PatientAttachment) ||
                   (treeNode.Tag is AttachmentEncounterDetails ) ||
                   (treeNode.Tag is AttachmentDetails) ||
                   (treeNode.Tag is DocumentSubtitle) ||
                   (treeNode.Tag is DocumentDetails) || 
                   (treeNode.Tag is PatientGlobalDocument))
                {
                    showEncounterDialog = true;
                }
            }
        }

        #endregion

        #region Deficiency Code

        /// <summary>
        /// Create and Display Deficiency dialog.
        /// </summary>
        private void ShowDeficiencyDialog(EncounterDetails selectedNode)
        {
            CreateDeficiencyDialogUI();

            Collection<DeficiencyDetails> deficiencyList = PatientController.Instance.RetrieveDeficiencyDetails(selectedNode.EncounterId, selectedNode.Facility);
            deficiencyDialogUI.SetData(deficiencyList);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                         rm.GetString("deficiencyDialog.title"),
                                                         deficiencyDialogUI);
            dialog.ShowIcon = false;
            if (dialog.ShowDialog(this) == DialogResult.Cancel)
            {
                dialog.Close();
            }
        }

        /// <summary>
        /// Creates Deficiency dialog.
        /// </summary>
        private void CreateDeficiencyDialogUI()
        {
            deficiencyDialogUI = new DeficiencyDialog();

            deficiencyDialogUI.SetExecutionContext(Context);
            deficiencyDialogUI.SetPane(Pane);
            deficiencyDialogUI.Localize();

            deficiencyDialogUI.Header = CreateDialogHeader();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            deficiencyDialogUI.Header.Title = rm.GetString("deficiencyDialog.header.title");
            deficiencyDialogUI.Header.Information = rm.GetString("deficiencyDialog.header.info");
        }

        #endregion

        #region View button Click

        /// <summary>
        /// Occurs when view selected button click.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void viewSelectedButton_Click(object sender, EventArgs e)
        {
            if(winDssViewer!=null)
            {
                winDssViewer.CloseViewer();
                winDssViewer = null;
            }

            if (winDssViewer == null)
            {
                try
                {
                    //CR#359317 - Check whether the HPF Workstation is installed
                    if (!HasWorkstationInstalled())
                    {
                        //throw new Exception();
                        DialogUI errorDialog = new DialogUI(Context.CultureManager.GetResource(CultureType.Message.ToString(),
                            ROIErrorCodes.WinDSSNotFound), Context, false);
                        errorDialog.ShowDialog();
                        return;
                    }
                    winDssViewer = new WinDocumentViewer();
                }
                catch (Exception err)
                {
                    //DialogUI errorDialog = new DialogUI(Context.CultureManager.GetResource(CultureType.Message.ToString(),
                    //         ROIErrorCodes.WinDSSNotFound), Context, false);
                    //errorDialog.ShowDialog();
                    //return;
                    winDssViewer = new WinDocumentViewer();
                }
               
                
            }
            List<TreeNodeAdv> selectedNodes = new List<TreeNodeAdv>(tree.SelectedNodes);
            pages = new List<PageDetails>();
            bool hpfDocumentsSelected = HasHpfDocumentSelection();
            bool nonHpfDocumentSelected = HasNonHpfDocumentSelection();
            bool AttachmentSelected = HasOneAttachmentSelection();
            bool AttachmentNodesSelected = HasAttachmentSelection();

            ShowViewSelectedErrorDialog(hpfDocumentsSelected,
                                        AttachmentNodesSelected, 
                                        AttachmentSelected, 
                                        nonHpfDocumentSelected);

            if ( ((selectedNodes.Count == 1) &&
                                AttachmentSelected) ||
                    (AttachmentSelected && !hpfDocumentsSelected))
            {
                TreeNodeAdv node = null;

                if (selectedNodes.Count == 1)
                {
                    node = tree.SelectedNode;
                }
                else
                {
                    foreach (TreeNodeAdv treeNode in selectedNodes)
                    {
                        if (treeNode.Tag is AttachmentDetails)
                        {
                            node = treeNode;
                            break;
                        }
                    }                   
                }

                string attachmentID = ((AttachmentDetails)(node.Tag)).FileDetails.Uuid;
                string attachmentExt = ((AttachmentDetails)(node.Tag)).FileDetails.Extension;
                ShowAttachmentViewer(patTreeModel.PatientInfo, (AttachmentDetails)(node.Tag), attachmentID, attachmentExt);

                return;

            }
                                        

            encounterList.Clear();
            // CR#365724
            orderedEncounterList.Clear();			

            foreach (TreeNodeAdv node in tree.SelectedNodes)
            {
                GetPages(node, pages);
            }

            // CR#365724. Below code is used to arrange the pages in the same document category wise if they are selected in shuffled order
            // with other document set. Ensure that orderedEncounterList has count greater than 0. orderedEncounterList contains the
            // unique key value of the selected document set and the number of pages selected in that.
            if (orderedEncounterList.Count > 0)
            {
                List<PageDetails> orderedPages = new List<PageDetails>();
                int orderPageCount;
                bool isPageCountEnded = false;
                int pageCount;
                string key1 = string.Empty;

                //Iterate the orderedEncounterList for each unique key of document set
                foreach (DictionaryEntry pageKey in orderedEncounterList)
                {
                    for (int count1 = 0; count1 < pages.Count; count1++)
                    {
                        //Get the key value of the current iteration page
                        string key = DocumentKeyName(pages[count1]);

                        //If current iteration pages key value and the ordereddictionary key value are same, then the below 
                        //code will execute
                        if (pageKey.Key.ToString().Equals(key))
                        {
                            orderPageCount = (orderedPages.Count == 0) ? 0 : orderedPages.Count;
                            orderedPages.Insert(orderPageCount, pages[count1]);
                            isPageCountEnded = false;
                            //Get the total number of pages in the selected document set
                            pageCount = (int)pageKey.Value;
                            for (int count2 = count1 + 1; count2 < pages.Count; count2++)
                            {
                                key1 = DocumentKeyName(pages[count2]);

                                if (key1.Equals(key))
                                {
                                    orderedPages.Insert(orderedPages.Count, pages[count2]);
                                    pageCount--;
                                }
                                //Break this for loop once the pagecount value is equal to zero
                                if (pageCount - 1 == 0)
                                {
                                    isPageCountEnded = true;
                                    break;
                                }
                            }
                            //Break this for loop once the isPageCountEnded value is equal to true
                            if (isPageCountEnded)
                            {
                                break;
                            }
                        }
                    }
                }
                pages.Clear();
                pages = (IList<PageDetails>)ROIViewUtility.DeepClone(orderedPages);
            }			

            if (pages.Count > 0)
            {
                winDssViewer.ViewPages(pages, patTreeModel.PatientInfo, encounterList);
            }
        }

        /// CR#365724
        /// <summary>
        /// Method used to return the key value of the selected page
        /// </summary>
        /// <param name="page"></param>
        /// <returns></returns>
        private string DocumentKeyName(PageDetails page)
        {
            string encounterName = string.Empty;
            string documentId = string.Empty;
            string versionNumber = string.Empty;

            if (page.Parent.Parent.Parent is EncounterDetails)
            {
                EncounterDetails encounterDetails = (EncounterDetails)page.Parent.Parent.Parent;
                encounterName = encounterDetails.Name;
            }
            else if (page.Parent.Parent.Parent is PatientGlobalDocument)
            {
                encounterName = globalDocument;
            }

            if (page.Parent.Parent is DocumentDetails)
            {
                DocumentDetails documentDetails = (DocumentDetails)page.Parent.Parent;
                documentId = documentDetails.DocumentType.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            if (page.Parent is VersionDetails)
            {
                VersionDetails version = (VersionDetails)page.Parent;
                versionNumber = version.Name;
            }

            string key = encounterName + documentId + versionNumber;
            return key;
        }
		
        /// <summary>
        /// Occurs when view all button click.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void viewAllButton_Click(object sender, EventArgs e)
        {
            if (winDssViewer == null)
            {
                try
                {
                    //CR#359317 - Check whether the HPF Workstation is installed
                    if (!HasWorkstationInstalled())
                    {
                        throw new Exception();
                    }
                    winDssViewer = new WinDocumentViewer();
                }
                catch (Exception err)
                {
                    DialogUI errorDialog = new DialogUI(Context.CultureManager.GetResource(CultureType.Message.ToString(),
                        ROIErrorCodes.WinDSSNotFound), Context, false);
                    errorDialog.ShowDialog();
                    return;
                }
            }
            pages = new List<PageDetails>();

            foreach (TreeNodeAdv node in tree.Root.Children)
            {
                if (!(node.Tag is NonHpfDocumentDetails))
                {
                    GetPages(node, pages);
                }
            }

            if (pages.Count > 0)
            {
                winDssViewer.ViewPages(pages, patTreeModel.PatientInfo, encounterList);
            }
        }

        #endregion

        /// <summary>
        /// CR#359317 - Check whether the HPF Workstation is installed                    
        /// </summary>
        /// <returns></returns>
        private bool HasWorkstationInstalled()
        {
            RegistryKey masterKey = Registry.LocalMachine.OpenSubKey(INSTALLDIRHIVE64BIT);
            if (masterKey == null)
            {
                masterKey = Registry.LocalMachine.OpenSubKey(INSTALLDIRHIVE);
            }
            return (masterKey != null);
        }

        private bool ShowViewSelectedErrorDialog(bool hasHpfDocs,
                                                 bool hasAttachmentDocs,
                                                 bool hasOneAttachmentDoc,
                                                 bool hasNonHpfDocs)
        {
            if (hasHpfDocs && hasAttachmentDocs && hasNonHpfDocs)
            {
                return ShowViewSelectedErrorDialog(HpfNonHpfAttachmentsTitle,HpfNonHpfAttachmentsMessage);
            }
            else if (hasHpfDocs && hasAttachmentDocs)
            {
                return ShowViewSelectedErrorDialog(HpfAttachmentsTitle,HpfAttachmentsMessage);
            }
            else if (hasHpfDocs && hasNonHpfDocs)
            {
                 return ShowViewSelectedErrorDialog(HpfNonHpfTitle, HpfNonHpfMessage);
            }
            else if (hasOneAttachmentDoc && hasNonHpfDocs)
            {
                return ShowViewSelectedErrorDialog(NonHpfAttachmentTitle, NonHpfAttachmentMessage);
            }
            else if (hasAttachmentDocs && hasNonHpfDocs)
            {
                 return ShowViewSelectedErrorDialog(NonHpfAttachmentsTitle, NonHpfAttachmentsMessage);
            }

            return true;
        }

        private bool ShowViewSelectedErrorDialog(String rmTitleText,
                                                 String rmMessageText)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(rmMessageText);
            string titleText = rm.GetString(rmTitleText);
            string okButtonText = rm.GetString(ViewDocumentErrorOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(ViewDocumentOkButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Error);
        }

        private bool ShowRecordViewDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(ROIConstants.RecordViewMessage);
            string titleText = rm.GetString(ROIConstants.RecordViewTitle);
            string okButtonText = rm.GetString(ROIConstants.RecordViewOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(ROIConstants.RecordViewOkButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, 
                                                 okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Retrieve ImnetId's of the selected node.
        /// </summary>
        /// <param name="selectedNode"></param>
        /// <param name="pages"></param>
        private void GetPages(TreeNodeAdv selectedNode, IList<PageDetails> pages)
        {
            if (typeof(PageDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
            {
                if (!pages.Contains(selectedNode.Tag as PageDetails))
                {
                    PageDetails page = selectedNode.Tag as PageDetails;
                    pages.Add(page);
                    string encounterName = string.Empty;
                    string documentId = string.Empty;
                    string versionNumber = string.Empty;

                    if (page.Parent.Parent.Parent is EncounterDetails)
                    {
                        EncounterDetails encounterDetails = (EncounterDetails)page.Parent.Parent.Parent;
                        encounterName = encounterDetails.Name;
                    }
                    else if (page.Parent.Parent.Parent is PatientGlobalDocument)
                    {
                        encounterName = globalDocument;
                    }

                    if (page.Parent.Parent is DocumentDetails)
                    {
                        DocumentDetails documentDetails = (DocumentDetails)page.Parent.Parent;
                        documentId = documentDetails.DocumentType.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    }

                    if (page.Parent is VersionDetails)
                    {
                        VersionDetails version = (VersionDetails)page.Parent;
                        versionNumber = version.Name;
                    }

                    string key = encounterName + documentId + versionNumber;
                    if (!encounterList.ContainsKey(key))
                    {
                        encounterList[key] = 1;
                    }
                    else
                    {
                        encounterList[key] = (int)encounterList[key] + 1;
                    }

                    // CR#365724. store the key value of the each document set in an ordered list
                    orderedEncounterList[key] = (!orderedEncounterList.Contains(key)) ? 1 : (int)orderedEncounterList[key] + 1;					
                }
            }
            foreach (TreeNodeAdv node in selectedNode.Children)
            {
                GetPages(node, pages);
            }
        }

        /// <summary>
        /// Retrieves the attachment content from the ROI Server 
        /// Shows the attachment viewer.
        /// </summary>
        /// <param name="fileName"></param>
        /// <returns></returns>
        public DialogResult ShowAttachmentViewer(PatientDetails patient, AttachmentDetails attachment, string attachmentId, string attachmentExt)
        {
            if (attachmentExt!=null && attachmentExt.ToUpper().Equals("XML"))
            {
                XmlViewerForm xmlViewer = new XmlViewerForm(Pane, string.Empty);
                return xmlViewer.DisplayAttachment(patient, attachment, attachmentId, attachmentExt);
            }
            else
            {
            AttachmentViewer attachmentViewer = new AttachmentViewer(Pane, string.Empty, string.Empty );
            return attachmentViewer.DisplayAttachment(patient, attachment, attachmentId, attachmentExt);
            }
        }
     

        ///// <summary>
        ///// Document view paging.
        ///// </summary>
        //private void DisplayCurrentPage()
        //{
        //    DocumentRequest firstPage = null;
        //    DocumentRequest secondPage = null;

        //    if (pageIndex < 0) pageIndex = 0;

        //    if (pageIndex > pages.Count - 1) pageIndex = pages.Count - 1;

        //    firstPage = GetDocumentRequest(pages[pageIndex].IMNetId);

        //    firstPage.IsFirstPage = (pageIndex == 0);
        //    firstPage.IsLastPage = (pageIndex == pages.Count - 1);
        //    firstPage.PageNumber = (pageIndex + 1);
        //    firstPage.PageIndex = pageIndex;
        //    firstPage.WindowTitle = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "IMNET: {0} Page: {1} of {2}", pages[pageIndex].IMNetId, firstPage.PageNumber, pages.Count);

        //    if (pages.Count - 1 > pageIndex)
        //    {
        //        firstPage.HasMorePages = true;
        //        secondPage = GetDocumentRequest(pages[pageIndex + 1].IMNetId);

        //        secondPage.IsFirstPage = false;
        //        secondPage.IsLastPage = (pageIndex + 1 == pages.Count - 1);
        //        secondPage.HasMorePages = (pages.Count - 2 > pageIndex);
        //        secondPage.PageNumber = (pageIndex + 2);
        //        secondPage.PageIndex = pageIndex + 1;
        //        secondPage.WindowTitle = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "IMNET: {0} Page: {1} of {2}", pages[pageIndex + 1].IMNetId, secondPage.PageNumber, pages.Count);
        //    }

        //    if (documentContainer == null)
        //    {
        //        documentContainer = wapi.DisplayDocument(firstPage, secondPage);

        //        documentContainer.OnNavigateFirstpage += new NavigateFirstpageHandler(documentContainer_OnNavigateFirstpage);
        //        documentContainer.OnNavigateLastpage += new NavigateLastpageHandler(documentContainer_OnNavigateLastpage);
        //        documentContainer.OnNavigateNextpage += new NavigateNextpageHandler(documentContainer_OnNavigateNextpage);
        //        documentContainer.OnNavigatePreviouspage += new NavigatePreviouspageHandler(documentContainer_OnNavigatePreviouspage);
        //        documentContainer.OnNavigateGoBack += new NavigateGoBackHandler(documentContainer_OnNavigateGoBack);
        //        documentContainer.OnClose += new CloseHandler(documentContainer_OnClose);

        //        documentContainer.NavigationEnabled = (pages.Count > 1);
        //    }
        //    else
        //    {
        //        documentContainer.DisplayDocument(firstPage, secondPage);
        //    }
        //}

        ///// <summary>
        ///// Assign imnetid into document request object.
        ///// </summary>
        ///// <param name="imnetid"></param>
        ///// <returns></returns>
        //private DocumentRequest GetDocumentRequest(string imnetid)
        //{
        //    DocumentRequest request = new DocumentRequest();
        //    request.IMNETId = imnetid;
        //    request.ParentWindowHandle = Handle.ToInt32();
        //    return request;
        //}

        ///// <summary>
        ///// Occurs when clicks on first page.
        ///// </summary>
        //private void documentContainer_OnNavigateFirstpage()
        //{
        //    pageIndex = 0;
        //    DisplayCurrentPage();
        //}

        ///// <summary>
        ///// Occurs when clicks on last page.
        ///// </summary>
        //private void documentContainer_OnNavigateLastpage()
        //{
        //    pageIndex = pages.Count - 1;
        //    DisplayCurrentPage();
        //}

        ///// <summary>
        ///// Occurs when clicks on next page.
        ///// </summary>
        //private void documentContainer_OnNavigateNextpage()
        //{
        //    pageIndex++;
        //    DisplayCurrentPage();
        //}

        ///// <summary>
        ///// Occurs when clicks on previous page.
        ///// </summary>
        //private void documentContainer_OnNavigatePreviouspage()
        //{
        //    pageIndex--;
        //    DisplayCurrentPage();
        //}

        ///// <summary>
        ///// Occurs when clicks on go back button.
        ///// </summary>
        ///// <param name="docIndex"></param>
        ///// <param name="pageIndex"></param>
        //private void documentContainer_OnNavigateGoBack(int docIndex, int pageIndex)
        //{
        //    if (pageIndex >= 0 && pageIndex < pages.Count)
        //    {
        //        this.pageIndex = pageIndex;
        //        DisplayCurrentPage();
        //    }
        //}

        //private void documentContainer_OnClose()
        //{
        //    documentContainer = null;
        //}


        /// <summary>
        /// encounterDetailsButton click
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void encounterDetailsButton_Click(object sender, EventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            if (showEncounterDialog)
            {
                string titleText = rm.GetString("EncounterDialog.header.title");
                string messageText = rm.GetString("EncounterDialog.header.info");
                string OkButtonText = rm.GetString("okButton.DialogUI");
                ROIViewUtility.ConfirmChanges(titleText, messageText, OkButtonText, string.Empty, ROIDialogIcon.Info);
            }
            
            EncounterDetailDialogUI encounterDetailDialogUI = new EncounterDetailDialogUI(Pane, patTreeModel.PatientInfo.MRN);
            encounterDetailDialogUI.Header = CreateDialogHeader();
            encounterDetailDialogUI.Header.Title = rm.GetString("EncounterDetailDialogUI.header.title");
            encounterDetailDialogUI.Header.Information = rm.GetString("EncounterDetailDialogUI.header.info");

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("title.EncounterDetailDialogUI"), encounterDetailDialogUI);

            Collection<EncounterDetails> selectedEncounters = new Collection<EncounterDetails>();
            EncounterDetails encounterDetails;
            ArrayList encounterNames = new ArrayList();
            foreach (TreeNodeAdv treeNode in tree.SelectedNodes)
            {
                if(typeof(EncounterDetails) == treeNode.Tag.GetType())
                {            
                    encounterDetails = (EncounterDetails)treeNode.Tag;
                    if (!encounterNames.Contains(encounterDetails.Key))
                    {
                        selectedEncounters.Add(encounterDetails);
                        encounterNames.Add(encounterDetails.Key);
                    }
                }
            }
            encounterDetailDialogUI.SetData(selectedEncounters, patTreeModel.PatientInfo.FacilityCode);
            form.ShowDialog(this);
        }

        #region Expand/Collapse tree

        /// <summary>
        /// Collapse button click event.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
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

        #endregion

        #endregion

        #region Security Rights

        public void ApplySecurityRights()
        {   
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, false))
            {
                topPanel.Enabled = footerPanel.Enabled = false;
            }
        }

        #endregion

        #region Properties

        public TreeViewAdv PatientRecordsTreeView
        {
            get { return tree; }
        }

        public IList<string> SelectedEncounters
        {
            set { patTreeModel.SelectedEncounters = value; }
            get { return patTreeModel.SelectedEncounters; }
        }

        public bool ShowButtons
        {
            get { return addAllButtonsPanel.Visible; }
            set { addAllButtonsPanel.Visible = value; }
        }

        public Button AddButton
        {
            get { return addButton; }
        }

        public Button AddAllButton
        {
            get { return addAllButton; }
        }

        public bool EnableNewDocumentButton
        {
            get { return newDocumentButton.Enabled; }
            set
            {
                newDocumentButton.Enabled = value;
                //if (Pane != null && typeof(PatientRecordsMCP).IsAssignableFrom(Pane.GetType()))
                //{
                //    newDocumentButton.Enabled = value;
                //}
                //else
                //{
                //    newDocumentButton.Enabled = false;
                //}
            }
        }

        public bool EnableEditDocumentButton
        {
            get { return editDocumentButton.Enabled; }
            set
            {
                editDocumentButton.Enabled = value;
                //if (Pane != null && typeof(PatientRecordsMCP).IsAssignableFrom(Pane.GetType()))
                //{
                //    editDocumentButton.Enabled = value;
                //}
                //else
                //{
                //    editDocumentButton.Enabled = false;
                //}
            }
        }

        public bool EnableNewAttachmentButton
        {
            get { return newAttachmentButton.Enabled; }
            set
            {
                newAttachmentButton.Enabled = value;
            }
        }

        public bool EnableEditAttachmentButton
        {
            get { return editAttachmentButton.Enabled; }
            set
            {
                editAttachmentButton.Enabled = value;
            }
        }
      
        public NonHpfDocumentDetails ModifiedNonHpfDocument
        {
            get { return modifiedNonHpfDocument; }
        }

        public AttachmentDetails ModifiedAttachment
        {
            get { return modifiedAttachment; }
        }
        
        public bool CanAccessRecordView
        {
            get { return canAccessRecordView; }
        }
        //Added for CR# 366249
        public ComboBox RecordViewCombobox
        {
            get { return recordViewComboBox; }
        }
        
        public long ReqId
        {
            get { return reqId; }
            set { reqId = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient object model.
        /// </summary>
        public List<RequestPatientDetails> Patients
        {
            get
            {
                if (patientDetails == null)
                {
                    patientDetails = new List<RequestPatientDetails>();
                }
                return patientDetails;
            }
            set { patientDetails = value; }
        }
        #endregion

    }    
}
