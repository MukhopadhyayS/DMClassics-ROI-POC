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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Other.DisclosureDocTypes
{
    /// <summary>
    /// Class to display UI controls of DisclosureDocTypes
    /// </summary>
    public partial class DisclosureDocTypesListUI : ROIBaseUI 
    {
        private Log log = LogFactory.GetLogger(typeof(DisclosureDocTypesListUI));

        #region Fields

        private const string DesignateColumn = "discdoctypes";
        private const string DocumentColumn  = "document";
        private const string AuthorizationColumn = "authorization";
        private const string MUDocTypeColumn = "MUdoctype";
        
        private Collection<DocumentTypesDetails> designatedDocumentTypes;
        private Collection<DocumentTypesDetails> authorizationTypes;
        private Collection<DocumentTypesDetails> muDocTypes;
        private Hashtable muDocTypesConfig;
        private EventHandler dirtyDataHandler;

        private bool isDirty;
        private string count;
        private CodeSetDetails previousSelection;
        private Label docLabel;
        private bool isCodeSetChanged;

        private bool flipped; // for handling grid edit state
        private ArrayList muDocumentNames;
        private Collection<DocTypeAuditDetails> docAuditColl = new Collection<DocTypeAuditDetails>();
        private Collection<DocumentTypesDetails> newdesignatedDocumentTypes = new Collection<DocumentTypesDetails>();
        private Collection<DocumentTypesDetails> newmuDocTypes = new Collection<DocumentTypesDetails>();
        #endregion

        #region Constructor
        /// <summary>
        /// Initialize the UI controls.
        /// </summary>
        public DisclosureDocTypesListUI()
        {
            InitializeComponent();
            muDocumentNames = new ArrayList();
            InitGrid();
            count = "{0}";
            dirtyDataHandler = new EventHandler(MarkDirty);
            muDocTypesConfig = new Hashtable();           
        }

        #endregion

        #region Methods

        private void InitGrid()
        {
            documentTypesGrid.AddCheckBoxColumn(DesignateColumn,
                                                "Designate as Disclosure Document",
                                                "IsDisclosure",
                                                100);
            documentTypesGrid.AddCheckBoxColumn(AuthorizationColumn,
                                                "Authorization Required",
                                                "IsAuthorization",
                                                125);

            DataGridViewComboBoxColumn muTypeColumn = documentTypesGrid.AddComboBoxColumn(MUDocTypeColumn, "MU Document Type", "MUDocumentType", 175);
            muTypeColumn.DataSource = muDocumentNames;
            DataGridViewTextBoxColumn DescColumn = documentTypesGrid.AddTextBoxColumn(DocumentColumn,
                                                                                      "Document",
                                                                                      "Name",
                                                                                      500);
            DescColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            DescColumn.ReadOnly     = true;
            documentTypesGrid.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            documentTypesGrid.EditMode = DataGridViewEditMode.EditOnEnter;
            DescColumn.DisplayIndex = 0;
        }

        /// <summary>
        ///  This method is used to enable the DisclosureDocTypesListUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            documentTypesGrid.CurrentCellDirtyStateChanged += dirtyDataHandler;
            this.codeSetCombo.SelectedIndexChanged         += new System.EventHandler(this.codeSetCombo_SelectedIndexChanged);
        }

        /// <summary>
        ///  This method is used to disable(unsubscripe)the DisclosureDocTypesListUI local events
        /// </summary>
        public void DisableEvents()
        {
           documentTypesGrid.CurrentCellDirtyStateChanged -= dirtyDataHandler;
           this.codeSetCombo.SelectedIndexChanged         -= new System.EventHandler(this.codeSetCombo_SelectedIndexChanged);
        }

        

        /// <summary>
        /// Occurs when the user changes the code set.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            documentTypesGrid.Refresh();
            if (flipped)
            {
                flipped = false;
            }
            else
            {
                flipped = true;
                //To commit and end the current operation of the Grid.
                documentTypesGrid.EndEdit();
            }

            //To skip the second call.
            if (IsDirty) return;

            IsDirty = true;
            
            saveButton.Enabled = true;
            cancelButton.Enabled = true;
            
        }
        
        /// <summary>
        /// Set the Codeset and populate the document types grid for selected codeset.
        /// </summary>
        /// <param name="data">Codesetdetails.</param>
       public void PrePopulate(object data)
        {
            DisableEvents();
            string[] muDocs = ROIAdminController.Instance.GetMUDocNames();
            for (int i = 0; i < muDocs.Length; i++)
            {
                muDocumentNames.Add(muDocs[i]);
            }
            if (data == null)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                codeSetCombo.Items.Insert(0, rm.GetString("noCodeSets." + GetType().Name));
                codeSetCombo.Enabled = false;
            }
            else
            {
                IList list = ((IList)data);
                codeSetCombo.DataSource    = list;
                codeSetCombo.ValueMember   = "Id";
                codeSetCombo.DisplayMember = "Description";
            }

            codeSetCombo.SelectedIndex = 0;
            codeSetCombo_SelectedIndexChanged(null, null);
            SelectFirstRow();
            EnableEvents();
        }

        /// <summary>
        /// Gets the Disclosure document type details object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            Collection<DocumentTypesDetails> docTypeDetails = (Collection<DocumentTypesDetails>)data;
            try
            {
                DocumentTypesDetails docType;
                foreach (DataGridViewRow row in documentTypesGrid.Rows)
                {
                    docType = (DocumentTypesDetails)row.DataBoundItem;

                    if (docType.IsDisclosure)
                    {
                        docTypeDetails.Add(docType);
                        if (!((row.Cells[2].Value.ToString()).Equals(muDocumentNames[0].ToString())))
                        {
                            docType.MUDocumentType = (row.Cells[2].Value).ToString();
                            docType.IsMUDocumentType = true;
                        }
                        else
                        {
                            docType.IsMUDocumentType = false;
                            docType.MUDocumentType = muDocumentNames[0].ToString();//string.Empty;
                        }
                    }
                    else if (docType.IsAuthorization)
                    {
                        docTypeDetails.Add(docType);
                        if (!((row.Cells[2].Value.ToString()).Equals(muDocumentNames[0].ToString())))
                        {
                            docType.MUDocumentType = (row.Cells[2].Value).ToString();
                            docType.IsMUDocumentType = true;
                            
                        }
                        else
                        {
                            docType.IsMUDocumentType = false;
                            docType.MUDocumentType = muDocumentNames[0].ToString();//string.Empty;
                        }
                    }
                    else if(!((row.Cells[2].Value.ToString()).Equals(muDocumentNames[0].ToString())))
                    {
                        docType.MUDocumentType = (row.Cells[2].Value).ToString();
                        docType.IsMUDocumentType = true;
                        docTypeDetails.Add(docType);
                    }
                    
                }

                return docTypeDetails;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
                return null;
            }
        }

        /// <summary>
        /// Apply culture and set the tooltip.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, codeSetLabel);

            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);

            count = rm.GetString("countLabel." + GetType().Name);
            documentTypesGrid.Columns[DesignateColumn].HeaderText = rm.GetString("discDocTypes.columnHeader");
            documentTypesGrid.Columns[DocumentColumn].HeaderText  = rm.GetString("document.columnHeader");
            documentTypesGrid.Columns[AuthorizationColumn].HeaderText = rm.GetString("authorization.columnHeader");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Returns the respective control for the given error code.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.MultipleAuthSelected: return codeSetCombo;
                case ROIErrorCodes.NoAuthRequestSelected: return codeSetCombo;
            }
            return null;
        }

        /// <summary>
        /// Return the key for the control.
        /// </summary>
        /// <param name="control">Control</param>
        /// <param name="toolTip">ToolTip</param>
        /// <returns>Key for localization</returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }
      
        /// <summary>
        /// Method to save document types details.
        /// </summary>
        public bool SaveDocumentTypes()
        {
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            errorProvider.Clear();
            long currentCodeSetId;
            //CR#370979
            string currentCodeSetDesc;
            try
            {
                Collection<DocumentTypesDetails> designatedDocumentTypes = (Collection<DocumentTypesDetails>)GetData(new Collection<DocumentTypesDetails>());
                currentCodeSetId = (isCodeSetChanged) ? previousSelection.Id : ((CodeSetDetails)codeSetCombo.SelectedItem).Id;
                //CR#370979
                currentCodeSetDesc = (isCodeSetChanged) ? previousSelection.Description : ((CodeSetDetails)codeSetCombo.SelectedItem).Description;
                ROIAdminValidator validator = new ROIAdminValidator();
                if (!validator.ValidateAuthorization(designatedDocumentTypes))
                {
                    throw validator.ClientException;
                }
                createAudit(designatedDocumentTypes);
                //CR#370979 Modified
                ROIAdminController.Instance.DesignateDocumentTypes(currentCodeSetId, designatedDocumentTypes,docAuditColl);

                //To Perform Cancel operation after Update
                //Collection<DocumentTypesDetails> docTypes = ROIAdminController.Instance.RetrieveDocumentTypes(currentCodeSetId);
                CacheDocumentTypes(designatedDocumentTypes);

                IsDirty = false;

                docAuditColl.Clear();
                newdesignatedDocumentTypes.Clear();
                newmuDocTypes.Clear();
                saveButton.Enabled = false;
                cancelButton.Enabled = false;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                return false;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }            
            log.ExitFunction();
            return true;
        }

        /// <summary>
        /// Save Document types details.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            SaveDocumentTypes();
        }

        /// <summary>
        /// Function to cancel the changes that has been made in document types.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            ResetData();
        }
        
        /// <summary>
        /// Populate document types in the datagrid.
        /// </summary>
        private void PopulateDocumentType(CodeSetDetails selectedCodeSet)
        {            
            try
            {
                if (selectedCodeSet == null)
                {
                    documentTypesGrid.Enabled = false;
                    return;
                }
                
                ROIViewUtility.MarkBusy(true);
                Collection<DocumentTypesDetails> docTypes;

                docTypes = ROIAdminController.Instance.RetrieveDocumentTypes(selectedCodeSet.Id);
                controlsSplitContainer.Panel2.Controls.Remove(docLabel);
                foreach (DocumentTypesDetails docType in docTypes)
                    docType.CodeSetId = selectedCodeSet.Id;
                if (docTypes.Count != 0)
                {
                    CacheDocumentTypes(docTypes);

                    ComparableCollection<DocumentTypesDetails> list = new ComparableCollection<DocumentTypesDetails>(docTypes);
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(DocumentTypesDetails))["Name"], ListSortDirection.Ascending);
                    SetData(list);
                    SelectFirstRow();
                }
                else
                {
                    documentTypesGrid.DataSource = null;
                    saveButton.Enabled   = false;
                    cancelButton.Enabled = false;
                    DisplayNoDocumentTypes();
                }

                countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, count, new object[] { docTypes.Count == 0 ? 0 : documentTypesGrid.Rows.Count });
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        /// <summary>
        /// Method to display no document types in the grid.
        /// </summary>
        private void DisplayNoDocumentTypes()
        {
            docLabel = new Label();
            int yPosition = documentTypesGrid.Columns[DesignateColumn].HeaderCell.ContentBounds.Bottom + 5;

            docLabel.Location     = new Point(documentTypesGrid.Location.X + 1, yPosition);
            docLabel.Size         = new System.Drawing.Size(documentTypesGrid.Size.Width - 2, 21);
            docLabel.Anchor       = ((AnchorStyles)((AnchorStyles.Left | AnchorStyles.Right)));
            docLabel.BackColor    = Color.White;
            docLabel.AutoEllipsis = true;
            

            docLabel.Text = Context.CultureManager.GetResource(CultureType.UIText.ToString(), "noDocument." + GetType().Name);
            controlsSplitContainer.Panel2.Controls.Add(docLabel);
            documentTypesGrid.SendToBack();
            docLabel.BringToFront();
        }

        

        /// <summary>
        /// Method to store the retrieved document types for the cancel event.
        /// </summary>
        /// <param name="documentTypes"></param>
        private void CacheDocumentTypes(Collection<DocumentTypesDetails> documentTypes)
        {
            designatedDocumentTypes = new Collection<DocumentTypesDetails>();
            authorizationTypes      = new Collection<DocumentTypesDetails>();
            muDocTypes              = new Collection<DocumentTypesDetails>();
            foreach (DocumentTypesDetails docType in documentTypes)
            {
                if (docType.IsDisclosure)
                {
                    designatedDocumentTypes.Add(docType);
                }
                if (docType.IsAuthorization)
                {
                    authorizationTypes.Add(docType);
                }
                if (docType.IsMUDocumentType)
                {
                    muDocTypes.Add(docType);
                    muDocTypesConfig[docType.CodeSetId + ROIConstants.Delimiter + docType.Id] = docType.MUDocumentType;
                }
            }
        }

        /// <summary>
        /// Selects the first row in datagridview
        /// </summary>
        protected virtual void SelectFirstRow()
        {
            if (documentTypesGrid.Rows.Count > 0)
            {
                documentTypesGrid.Rows[0].Selected = true;
            }
        }

        public void ResetData()
        {
            errorProvider.Clear();
            IsDirty = false;
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            DocumentTypesDetails docTypeDetail;
            foreach (DataGridViewRow row in documentTypesGrid.Rows)
            {
                docTypeDetail = row.DataBoundItem as DocumentTypesDetails;
                docTypeDetail.IsDisclosure = designatedDocumentTypes.Contains(docTypeDetail);
                docTypeDetail.IsAuthorization = authorizationTypes.Contains(docTypeDetail);
                docTypeDetail.IsMUDocumentType = muDocTypes.Contains(docTypeDetail);
                if (docTypeDetail.IsMUDocumentType)
                    row.Cells[2].Value = muDocTypesConfig[docTypeDetail.CodeSetId + ROIConstants.Delimiter + docTypeDetail.Id];
                else
                    row.Cells[2].Value = muDocumentNames[0];
            } 
            documentTypesGrid.Refresh();
            SelectFirstRow();
        }

        /// <summary>
        /// Set the document type data.
        /// </summary>
        /// <param name="data"></param>
        private void SetData(object data)
        {
            DisableEvents();
            documentTypesGrid.SetItems((IFunctionCollection)data);
            documentTypesGrid.Refresh();
            documentTypesGrid.ClearSelection();
           int itemIndex;
           for (int i = 0; i < documentTypesGrid.Rows.Count; i++)
           {
               DocumentTypesDetails docTypeDetails = (DocumentTypesDetails)documentTypesGrid.Rows[i].DataBoundItem;
               if (docTypeDetails.IsMUDocumentType)
               {                  
                  itemIndex = muDocumentNames.IndexOf(docTypeDetails.MUDocumentType);
                  documentTypesGrid.Rows[i].Cells[2].Value = muDocumentNames[itemIndex];
                   
               }
               else
                   documentTypesGrid.Rows[i].Cells[2].Value = muDocumentNames[0];
              
           }
               EnableEvents();
        }

        /// <summary>
        /// Method to handle codeset values changed in the combo box.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void codeSetCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            log.EnterFunction();
            try
            {
                object selectedEntity   = codeSetCombo.SelectedItem;
                isCodeSetChanged        = true;
                ApplicationEventArgs ae = new ApplicationEventArgs(selectedEntity, e);
                if (!Approve())
                {
                    ae.Info        = previousSelection;
                    ae.SourceEvent = e;
                    SelectPreviousItem();
                }
                else
                {
                    if ((CodeSetDetails)codeSetCombo.SelectedItem != previousSelection)
                    {
                        ROIViewUtility.MarkBusy(true);
                        PopulateDocumentType((CodeSetDetails)selectedEntity);
                    }
                }
                StorePreviousSelection();
                isCodeSetChanged = false;
            }

            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        /// <summary>
        /// Function to select previous item in the codeset.
        /// </summary>
        private void SelectPreviousItem()
        {
            if (previousSelection != null)
            {
                DisableEvents();
                codeSetCombo.SelectedItem = previousSelection;
                EnableEvents();
                saveButton.Enabled   = true;
                cancelButton.Enabled = true;
            }
            else
            {
                DisableEvents();
                codeSetCombo.SelectedIndex = 0;
                EnableEvents();
            }
        }

        /// <summary>
        /// Method to store previous selection.
        /// </summary>
        private void StorePreviousSelection()
        {
            if (codeSetCombo.SelectedIndex >= 0)
            {
                previousSelection = ((CodeSetDetails)codeSetCombo.SelectedItem);

            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Method to handle dirty data.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set
            {
                isDirty = value;
                if (!isDirty)
                {
                    flipped = false;
                    EnableEvents();
                }
            }
        }
        #endregion

        #region Dirty data handling
        /// <summary>
        /// Check whether the data is modified and display the
        /// confirm changes dialog box for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true if user click OK button in dialog box.</returns>
        public bool Approve()
        {
            if (!IsDirty)
            {
                return true;
            }

            string titleText;
            string messageText;
            string okButtonText;
            string cancelButtonText;
            string okButtonToolTip;
            string cancelButtonToolTip;

            titleText           = Context.CultureManager.GetResource(CultureType.UIText.ToString(), ROIDialog.ROIDialogTitle);
            okButtonText        = Context.CultureManager.GetResource(CultureType.UIText.ToString(), ROIDialog.ROIDialogOkButton);
            cancelButtonText    = Context.CultureManager.GetResource(CultureType.UIText.ToString(), "cancelButton");
            messageText         = Context.CultureManager.GetResource(CultureType.Message.ToString(), ROIErrorCodes.DisclosureConfirmSaveDialogKey);
            okButtonToolTip     = Context.CultureManager.GetResource(CultureType.ToolTip.ToString(), ROIDialog.OkButtonROIDialog);
            cancelButtonToolTip = Context.CultureManager.GetResource(CultureType.ToolTip.ToString(), ROIDialog.CancelButtonROIDialog);

            if (previousSelection != null)
            {
                messageText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, messageText, new object[] { previousSelection.Description });
            }

            if (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Info))
            {
                SaveDocumentTypes();
                return !IsDirty;
            }
            else
            {
                return false;
            }
        }
       
        #endregion

        /// <summary>
        /// Occurs When Window form is resized
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DisclosureDocTypesListUI_Resize(object sender, EventArgs e)
        {
            if (docLabel != null)
            {
                controlsSplitContainer.Panel2.Controls.Remove(docLabel);
                DisplayNoDocumentTypes();
            }
        }

        //CR#370979
        private void documentTypesGrid_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            
            if (documentTypesGrid.SelectedRows.Count != 0)
            {
                DocumentTypesDetails docType;
                docType = (DocumentTypesDetails)documentTypesGrid.SelectedRows[0].DataBoundItem;
                if (e.ColumnIndex == 2)
                    newmuDocTypes.Add(docType);

                if (e.ColumnIndex == 0)
                    newdesignatedDocumentTypes.Add(docType);
            }
        }

        //CR#370979
        private void createAudit(Collection<DocumentTypesDetails> documentTypes)
        {
            string fromName = string.Empty;
            string toName = string.Empty;
            if (newmuDocTypes.Count > 0)
            {
                foreach (DocumentTypesDetails doctypedetail in newmuDocTypes)
                {
                    DocTypeAuditDetails doctypeAudit = new DocTypeAuditDetails();
                    if (muDocTypesConfig[doctypedetail.CodeSetId + ROIConstants.Delimiter + doctypedetail.Id] != null)
                        doctypeAudit.FromValue = muDocTypesConfig[doctypedetail.CodeSetId + ROIConstants.Delimiter + doctypedetail.Id].ToString();
                    else
                        doctypeAudit.FromValue = "None";
                    doctypeAudit.ToValue = doctypedetail.MUDocumentType;
                    doctypeAudit.DocName = doctypedetail.Name;
                    doctypeAudit.CodeSetName = ((CodeSetDetails)codeSetCombo.SelectedItem).Description;
                    doctypeAudit.DocType = "MU";
                    docAuditColl.Add(doctypeAudit);

                }

            }

            List<DocumentTypesDetails> list1 = new List<DocumentTypesDetails>(documentTypes);
            List<DocumentTypesDetails> discList = list1.FindAll(delegate(DocumentTypesDetails item) { return item.IsDisclosure; });
            foreach (DocumentTypesDetails doctypedetail in designatedDocumentTypes)
            {
                fromName += doctypedetail.Name;
                fromName += ",";
            }
            foreach (DocumentTypesDetails newdoctypedetail in discList)
            {
                toName += newdoctypedetail.Name;
                toName += ",";
            }
            fromName = fromName.TrimEnd(',');
            toName = toName.TrimEnd(',');
            if (fromName != toName)
            {
                if (fromName == string.Empty)
                    fromName = "None";
                else if (toName == string.Empty)
                    toName = "None";
                DocTypeAuditDetails doctypeAudit = new DocTypeAuditDetails();
                doctypeAudit.FromValue = fromName;
                doctypeAudit.ToValue = toName;
                doctypeAudit.DocType = "Disclosure";
                doctypeAudit.CodeSetName = ((CodeSetDetails)codeSetCombo.SelectedItem).Description;
                docAuditColl.Add(doctypeAudit);
            }

            List<DocumentTypesDetails> list = new List<DocumentTypesDetails>(documentTypes);
            List<DocumentTypesDetails> authList = list.FindAll(delegate(DocumentTypesDetails item) { return item.IsAuthorization; });
            foreach (DocumentTypesDetails doctypedetail in authorizationTypes)
            {
                fromName = doctypedetail.Name;
            }
            foreach (DocumentTypesDetails newdoctypedetail in authList)
            {
                toName = newdoctypedetail.Name;
            }
            if (fromName != toName)
            {
                DocTypeAuditDetails doctypeAudit = new DocTypeAuditDetails();                
                if (fromName==string.Empty)
                {
                    fromName = "None";
                }
                fromName = fromName.TrimEnd(',');
                doctypeAudit.FromValue = fromName;
                doctypeAudit.ToValue = toName;
                doctypeAudit.DocType = "Authorize";
                doctypeAudit.CodeSetName = ((CodeSetDetails)codeSetCombo.SelectedItem).Description;
                docAuditColl.Add(doctypeAudit);
            }
        }
    
    }
}
