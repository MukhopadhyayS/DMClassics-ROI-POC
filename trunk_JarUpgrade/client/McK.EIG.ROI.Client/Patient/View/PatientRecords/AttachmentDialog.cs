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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;
using System.Drawing;
using System.Reflection;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Patient.View.PatientRecords.Attachments;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    /// <summary>
    /// This modal dialog will be displayed when the user has 
    /// selected the New/Edit Attachment Button from the Patient Records screen.
    /// </summary>
    public partial class AttachmentDialog : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(AttachmentDialog));
        private  static Control attachmentUIControl;
        public static Point cancelButtonPosition;

        #region Fields

        private const string SelectOption = "  /  /    ";

        private const string DeleteAttachmentDialogMessageKey = "A4002";
        private const string DeleteReleasedAttachmentDialogMessageKey = "A4003";
        private const string DeleteAttachmentDialogTitle = "DeleteAttachmentDialog.Title";
        private const string DeleteAttachmentDialogOkButton = "deleteButton";
        private const string DeleteAttachmentDialogCancelButton = "cancelButton";
        private const string DeleteAttachmentDialogOkButtonToolTip = "DeleteAttachmentDialog.OkButton";
        private const string DeleteAttachmentDialogCancelButtonToolTip = "DeleteAttachmentDialog.CancelButton";
        private const string DateTime24hrFormat = "MM/dd/yyyy HH:mm:ss";
        private int bPrevIndex;

        private EventHandler dirtyDataHandler;

        private EventHandler createEditHandler;
        private EventHandler deleteHandler;
        private CancelEventHandler cancelHandler;

        private bool isDirty;
        private AttachmentMode mode;
        private AttachmentDetails attachment;
        private HeaderUI headerUI;
        private IAttachmentDetailUI attachmentDetailUI;

        private ROIProgressBar fileTransferProgress;
        private int attachmentUploadProgress;
        internal static EventHandler ProgressHandler;

        #endregion

        #region Constructor

        public AttachmentDialog(EventHandler createEditHandler, EventHandler deleteHandler, CancelEventHandler closeHandler)
            : this()
        {
            this.createEditHandler = createEditHandler;
            this.deleteHandler = deleteHandler;
            this.cancelHandler = closeHandler;
            attachmentUploadProgress = 0;
        }

        public AttachmentDialog()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
            InitProgress();
            ProgressHandler = new EventHandler(Process_NotifyProgress);
            attachmentUploadProgress = 0;
            cancelButtonPosition = cancelButton.Location;
            bPrevIndex = -1;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            this.ShowProgress((FileTransferEventArgs)e);
        }

        /// <summary>
        /// Method initilize the progress bar.
        /// </summary>
        private void InitProgress()
        {
            fileTransferProgress = new ROIProgressBar();
            fileTransferProgress.Location = new Point(((this.Width / 2) - 50) - fileTransferProgress.Width / 2,
                                                     (this.Height / 2) - fileTransferProgress.Height / 2);
            this.Controls.Add(fileTransferProgress);
            fileTransferProgress.BringToFront();
            fileTransferProgress.MessageText.BringToFront();
            fileTransferProgress.PercentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
        }

        
        /// <summary>
        /// Method call the progress bar to display.
        /// 
        /// </summary>
        /// <param name="e"></param>
        private void ShowProgress(int progressIndicator, bool finish)
        {
            fileTransferProgress.Visible = true;
            FileTransferEventArgs tmpEvent = new FileTransferEventArgs((int)FileTransferEventArgs.Status.InProgress);
            tmpEvent.FileSize = 100;
            tmpEvent.TransferredSize = progressIndicator;
            fileTransferProgress.ShowProgress(tmpEvent);
            if (finish)
            {
                fileTransferProgress.Visible = false;
            }
        }



        /// <summary>
        /// Method call the progress bar to display.
        /// </summary>
        /// <param name="e"></param>
        public void ShowProgress(FileTransferEventArgs e)
        {
            switch (e.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    fileTransferProgress.Visible = true;
                    e.TransferredSize = attachmentUploadProgress;
                    e.FileSize = 100;
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.InProgress:
                    e.TransferredSize = (long)(e.TransferredSize * (attachmentUploadProgress / 100.0));
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.Finish:
                    e.TransferredSize = (long)(e.TransferredSize * (attachmentUploadProgress / 100.0));
                    fileTransferProgress.ShowProgress(e);
                    break;
            }
        }

        internal void MarkDirty(object sender, EventArgs e)
        {
            IsDirty = true;

            if (!(((attchmentLocationComboBox.Text.Trim()).Equals((AttachmentHelper.GetEnumDescription(AttachmentHelper.AttachmentLocation.ExternalSourceAttachment)).ToString()))&& mode==AttachmentMode.Create))
            {
                if (string.IsNullOrEmpty(attchmentLocationComboBox.Text.Trim()) ||
                   string.IsNullOrEmpty(facilityComboBox.Text.Trim()) ||
                   dosTextBox.Text.Trim() == SelectOption.Trim())
                {
                    saveButton.Enabled = false;
                }
                else
                {
                       if (facilityComboBox.SelectedIndex != 0)
                        {
                            saveButton.Enabled = true;
                            cancelButton.Enabled = true;

                            if (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
                            {
                                if (attchmentLocationComboBox.SelectedIndex != 0 &&
                                facilityComboBox.SelectedIndex != 0)
                                {
                                    saveButton.Enabled = true;
                                    cancelButton.Enabled = true;
                                }

                            }

                            if (mode == AttachmentMode.Edit)
                            {
                                saveButton.Enabled = saveButton.Enabled &&
                                                         filePageCountTextBox.Text.Trim().Length > 0;
                            }
                        }
                    
                    
                      else
                      {
                        saveButton.Enabled = false;
                      }
                }

                if (this.Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
                {
                    ((PatientRecordsMCPView)this.Pane.View).MarkDirty(sender, e);
                }

                //enable save button if attachment details ui data is set also
                saveButton.Enabled = saveButton.Enabled && attachmentDetailUI.EnableSaveButton();
            }
        }

        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = saveButton;
        }


        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            errorProvider.Clear();
            encounterTextBox.Text = string.Empty;
            subTitleTextBox.Text = string.Empty;
            commentTextBox.Text = string.Empty;
            filePageCountTextBox.Text = string.Empty;
            fileNameTextBox.Text = string.Empty;
            fileUploadDateTextBox.Text = string.Empty;

            deleteButton.Enabled = attchmentLocationComboBox.Enabled = dosTextBox.Enabled =  subTitleTextBox.Enabled = encounterTextBox.Enabled = true;

            SetDOBNullValue();
        }

        /// <summary>
        /// Show controls
        /// </summary>
        public void ShowControls()
        {
            errorProvider.Clear();
            encounterTextBox.Visible = true;
            encounterLabel.Visible = true;
            commentTextBox.Visible = true;
            commentLabel.Visible = true;
            facilityComboBox.Visible = true;
            facilityLabel.Visible = true;
            dateOfServiceLabel.Visible = true;
            subTitleTextBox.Visible = true;
            attachmentTitleLabel.Visible = true;
            datePictureBox.Visible = true;
            facilityPictureBox.Visible = true;
            dosTextBox.Visible = true;
            attachmentDetailPanel.Visible = true;

            if (mode == AttachmentMode.Edit)
            {
                fileNameTextBox.Visible = true;
                fileNameTextBox.ReadOnly = true;
                fileNameTextBox.Enabled = true;

                attachmentFileNameLabel.Visible = true;
                fileUploadDateTextBox.Visible = true;
                fileUploadDateTextBox.Enabled = false;
                attachmentDateUploadedLabel.Visible = true;
                filePageCountTextBox.Visible = true;
                pageCountPictureBox.Visible = true;
                filePageCountLabel.Visible = true;
            }
            else
            {
                fileNameTextBox.Visible = false;
                attachmentFileNameLabel.Visible = false;
                fileUploadDateTextBox.Visible = false;
                attachmentDateUploadedLabel.Visible = false;
                filePageCountTextBox.Visible = false;
                pageCountPictureBox.Visible = false;
                filePageCountLabel.Visible = false;
            }
            deleteButton.Enabled = dosTextBox.Enabled = subTitleTextBox.Enabled = encounterTextBox.Enabled = true;

            if (attachment != null)
            {
                if (attachment.IsDeleted)
                {
                    deleteButton.Enabled = false;
                }
            }

            SetDOBNullValue();
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void HideControls()
        {
            errorProvider.Clear();
            SetDOBNullValue();

            encounterTextBox.Visible = false;
            encounterLabel.Visible = false;
            commentTextBox.Visible = false;
            commentLabel.Visible = false;
            facilityComboBox.Visible = false;
            facilityLabel.Visible = false;
            dateOfServiceLabel.Visible = false;
            subTitleTextBox.Visible = false;
            attachmentTitleLabel.Visible = false;
            datePictureBox.Visible = false;
            dosTextBox.Visible = false;
            attachmentDetailPanel.Visible = false;
            fileNameTextBox.Visible = false;
            attachmentFileNameLabel.Visible = false;
            filePageCountTextBox.Visible = false;
            pageCountPictureBox.Visible = false;
            facilityPictureBox.Visible = false;
            filePageCountLabel.Visible = false;
            fileUploadDateTextBox.Visible = false;
            attachmentDateUploadedLabel.Visible = false;

            deleteButton.Enabled =  dosTextBox.Enabled =  subTitleTextBox.Enabled = encounterTextBox.Enabled = false;

        }
        /// <summary>
        /// GetData
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            AttachmentDetails tmpAttachment = appendTo == null ? new AttachmentDetails() : appendTo as AttachmentDetails;

            tmpAttachment.Encounter = encounterTextBox.Text;
            tmpAttachment.Subtitle = subTitleTextBox.Text;
            if (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
            {
                tmpAttachment.AttachmentType = (attchmentLocationComboBox.SelectedIndex == 0) ? string.Empty : attchmentLocationComboBox.Text;
            }
            else
                tmpAttachment.AttachmentType = attchmentLocationComboBox.Text;

            AddFacility(facilityComboBox.Text);
            tmpAttachment.FacilityCode = (facilityComboBox.SelectedIndex == 0) ? string.Empty : ((FacilityDetails)(facilityComboBox.SelectedItem)).Code;
            tmpAttachment.FacilityType = (facilityComboBox.SelectedIndex == 0) ? FacilityType.NonHpf : ((FacilityDetails)(facilityComboBox.SelectedItem)).Type;

            tmpAttachment.Comment = commentTextBox.Text;
            if (dosTextBox.Text.Trim() != SelectOption.Trim() && dosTextBox.IsValidDate)
            {
                tmpAttachment.DateOfService = Convert.ToDateTime(dosTextBox.Text, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            if (attachmentDetailUI != null)
            {
                tmpAttachment = (AttachmentDetails)attachmentDetailUI.GetData(tmpAttachment);
            }

            if (mode == AttachmentMode.Edit)
            {
                int pageCount = 0;
                if (!int.TryParse(filePageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
                {
                    throw new ROIException(ROIErrorCodes.AttachmentInvalidPageCount);
                }
                tmpAttachment.FileDetails.PageCount = pageCount;
                tmpAttachment.PageCount = pageCount;
            }
            return tmpAttachment;
        }

        public void AddFacility(string newFacility)
        {
            if (string.IsNullOrEmpty(newFacility)) return;

            if (facilityComboBox.SelectedItem == null)
            {
                int selectedIndex = facilityComboBox.FindStringExact(newFacility);

                if (selectedIndex != -1)
                {
                    facilityComboBox.SelectedItem = facilityComboBox.Items[selectedIndex];
                    return;
                }

                FacilityDetails facility = new FacilityDetails(newFacility, newFacility, FacilityType.NonHpf);
                facilityComboBox.Items.Add(facility);
                facilityComboBox.SelectedItem = facility;
            }
        }

        /// <summary>
        /// SetData
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            attachment = data as AttachmentDetails;

            ClearControls();

            saveButton.Enabled = false;

            DisableEvents();

            if (attachment != null)
            {
                if ((attachment.AttachmentType.Trim()).Equals((AttachmentHelper.GetEnumDescription(AttachmentHelper.AttachmentType.CCRCCD)).ToString()))
                    attachment.AttachmentType = (AttachmentHelper.GetEnumDescription(AttachmentHelper.AttachmentLocation.LocalFileAttachment)).ToString();
                attchmentLocationComboBox.SelectedIndex = ((int) EnumUtilities.EnumValueOf(attachment.AttachmentType.Trim(), 
                                                                                        typeof(AttachmentHelper.AttachmentLocation)));
                //if edit mode - set data
                if (mode == AttachmentMode.Edit)
                {
                    
                    if (attachment != null)
                    {
                        attachmentDetailUI.SetData(attachment);
                    }
                }

                encounterTextBox.Text = attachment.Encounter;
                subTitleTextBox.Text = attachment.Subtitle;
                filePageCountTextBox.Text = Convert.ToString(attachment.FileDetails.PageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                fileNameTextBox.Text = attachment.FileDetails.FileName + "." + attachment.FileDetails.Extension;
                fileNameTextBox.Enabled = true;

                if (attachment.AttachmentDate.HasValue)
                {
                    fileUploadDateTextBox.Text = attachment.AttachmentDate.Value.ToString(ROIConstants.DateTimeFormat, CultureInfo.InvariantCulture);
                }
                if (attachment.DateOfService.HasValue)
                {
                    dosTextBox.Text = attachment.DateOfService.Value.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                }

                if (String.IsNullOrEmpty(attachment.FacilityCode))
                {
                    facilityComboBox.SelectedIndex = 0;
                }
                else
                {
                    FacilityDetails facility = FacilityDetails.GetFacility(attachment.FacilityCode, attachment.FacilityType);

                    if (facility != null)
                    {
                        if (facilityComboBox.Items.Contains(facility))
                        {
                            facilityComboBox.SelectedItem = facility;
                        }
                    }
                }
                commentTextBox.Text = attachment.Comment;

                if (attachment.IsReleased)
                {
                    attchmentLocationComboBox.Enabled = dosTextBox.Enabled = facilityComboBox.Enabled = 
                        subTitleTextBox.Enabled = encounterTextBox.Enabled = 
                        filePageCountTextBox.Enabled =  fileNameTextBox.Enabled = 
                        fileUploadDateTextBox.Enabled  = false;
                    fileNameTextBox.Enabled = true;
                }
                //disable docType selectorg
                attchmentLocationComboBox.Enabled = false;
                
                //set filename as a tooltip so user is able to view complete filename if size is too large
                toolTip.SetToolTip(fileNameTextBox, attachment.FileDetails.FileName + "." + attachment.FileDetails.Extension);
                fileNameTextBox.Enabled = true;
            }
            if (((AttachmentHelper.AttachmentLocation)attchmentLocationComboBox.SelectedIndex).Equals(AttachmentHelper.AttachmentLocation.ExternalSourceAttachment))
            {
                ExternalSourceAttachment.SetAttachmentDetails(attachment);
            }
            if (((AttachmentHelper.AttachmentLocation)attchmentLocationComboBox.SelectedValue).Equals(AttachmentHelper.AttachmentLocation.ExternalSourceAttachment))
               cancelButton.Text = "Close";
            isDirty = false;
            EnableEvents();
        }

        /// <summary>
        /// FormatAttachmentNode
        /// </summary>
        /// <param name="node"></param>
        /// <param name="tmpAttachment"></param>
        public static void FormatAttachmentNode(TreeNode node, AttachmentDetails tmpAttachment)
        {
            string attachmentName = tmpAttachment.AttachmentType;
            node.Text = String.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0,-35} | {1,-35} | {2,-35} | {3,-15} | {4,-15}", attachmentName, tmpAttachment.FacilityCode, "N/A", tmpAttachment.DateOfService, tmpAttachment.Encounter);
        }

        /// <summary>
        /// Trim the node text width into 25 characters 
        /// </summary>
        /// <param name="nodeText"></param>
        /// <returns></returns>
        public static string TrimNode(string nodeText)
        {
            if (nodeText.Length == 25)
            {
                return nodeText;
            }
            else if (nodeText.Length > 25)
            {
                return nodeText.Substring(0, 22).PadRight(25, ' ');
            }
            else if (nodeText.Length < 25)
            {
                return nodeText.PadRight(25, ' ');
            }
            return nodeText;
        }

        /// <summary>
        /// EnableEvents
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            attchmentLocationComboBox.SelectedIndexChanged += dirtyDataHandler;
            facilityComboBox.SelectedIndexChanged += dirtyDataHandler;

            facilityComboBox.TextChanged += dirtyDataHandler;
            encounterTextBox.TextChanged += dirtyDataHandler;
            commentTextBox.TextChanged += dirtyDataHandler;
            subTitleTextBox.TextChanged += dirtyDataHandler;
            filePageCountTextBox.TextChanged += dirtyDataHandler;

            dosTextBox.TextChanged += dirtyDataHandler;
            if (attachmentDetailUI != null)
            {
                attachmentDetailUI.EnableEvents(dirtyDataHandler);
            }
        }

        /// <summary>
        /// DisableEvents
        /// </summary>
        public void DisableEvents()
        {
            attchmentLocationComboBox.SelectedIndexChanged -= dirtyDataHandler;
            facilityComboBox.SelectedIndexChanged -= dirtyDataHandler;

            facilityComboBox.TextChanged -= dirtyDataHandler;
            encounterTextBox.TextChanged -= dirtyDataHandler;
            commentTextBox.TextChanged -= dirtyDataHandler;
            subTitleTextBox.TextChanged -= dirtyDataHandler;
            filePageCountTextBox.TextChanged -= dirtyDataHandler;

            dosTextBox.TextChanged -= dirtyDataHandler;
            if (attachmentDetailUI != null)
            {
                attachmentDetailUI.DisableEvents(dirtyDataHandler);
            }
        }

        //CR# 368487 Page count error message is invalid.
        /// <summary>
        /// Occurs when user has changed the page count.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void filePageCountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back)
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// PrePopulate
        /// </summary>
        public void PrePopulate()
        {
            DisableEvents();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            PopulateFacilities(rm.GetString("selectFacility"));
            PopulateLocation();
            EnableEvents();
        }
              
        private void PopulateLocation()
        {
            IList attachmentLocations = EnumUtilities.ToList(typeof(AttachmentHelper.AttachmentLocation));
            if (AttachMode == AttachmentMode.Create)
            {
                if (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
                {
                    attachmentLocations.Remove(AttachmentHelper.GetEnum(AttachmentHelper.AttachmentLocation.ExternalSourceAttachment));
                }
                else
                    attachmentLocations.Remove(AttachmentHelper.GetEnum(AttachmentHelper.AttachmentLocation.None));
            }
            attchmentLocationComboBox.DisplayMember = "value";
            attchmentLocationComboBox.ValueMember = "key";
            attchmentLocationComboBox.DataSource = attachmentLocations;
            attchmentLocationComboBox.SelectedIndex = (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView))) ? 0 : 1;
            attchmentLocationComboBox.Focus();
            
        }

        private void PopulateFacilities(string defaultValue)
        {
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                facilityComboBox.Items.Add(fac);
            }

            facilityComboBox.Items.Insert(0, new FacilityDetails(defaultValue, defaultValue, FacilityType.NonHpf));
            facilityComboBox.DisplayMember = "Name";
            facilityComboBox.ValueMember = "Code";

            if (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
            {
                
                facilityComboBox.SelectedText = ((PatientRecordsEditor)Pane.ParentPane).Patient.FacilityCode;
            }
            else
            {
                facilityComboBox.SelectedText = ((RequestPatientInfoUI)Pane.View).Patient.FacilityCode;
            }

            facilityComboBox.Enabled = false;
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control != deleteButton)
            {
                return control.Name + "." + mode.ToString() + "." + GetType().Name;
            }
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, encounterLabel);
            SetLabel(rm, attachmentLocationlabel);
            SetLabel(rm, dateOfServiceLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, commentLabel);
            SetLabel(rm, attachmentFileNameLabel);
            SetLabel(rm, attachmentDateUploadedLabel);

            SetLabel(rm, attachmentTitleLabel);

            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, deleteButton);
            SetLabel(rm, filePageCountLabel);

            //Progress Bar.
            fileTransferProgress.MessageText.Text = rm.GetString("Attachment.ProgressMessage");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, encounterTextBox);
            SetTooltip(rm, toolTip, dosTextBox);
            SetTooltip(rm, toolTip, facilityComboBox);
            SetTooltip(rm, toolTip, commentTextBox);
            SetTooltip(rm, toolTip, filePageCountTextBox);

            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, cancelButton);
            SetTooltip(rm, toolTip, deleteButton);
            SetDOBNullValue();
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
                case ROIErrorCodes.AttachmentTypeEmpty: return attchmentLocationComboBox;
                case ROIErrorCodes.AttachmentFacilityEmpty: return facilityComboBox;
                case ROIErrorCodes.AttachmentDateOfServiceEmpty: return dosTextBox;
                case ROIErrorCodes.AttachmentInvalidTitle: return subTitleTextBox;
                case ROIErrorCodes.AttachmentInvalidEncounter: return encounterTextBox;
                case ROIErrorCodes.InvalidFacility: return facilityComboBox;
                case ROIErrorCodes.IncorrectDate: return dosTextBox;
                case ROIErrorCodes.InvalidDate: return dosTextBox;
                case ROIErrorCodes.InvalidDateValue: return dosTextBox;
                case ROIErrorCodes.InvalidDateOfService: return dosTextBox;
            }

            if (mode == AttachmentMode.Create)
            {
                if (attachmentDetailUI != null)
                {
                    return attachmentDetailUI.GetErrorControl(error);
                }
            }

            if (mode == AttachmentMode.Edit)
            {
                switch (error.ErrorCode)
                {
                    case ROIErrorCodes.AttachmentInvalidPageCount: return filePageCountTextBox ;
                }

            }

            return null;
        }

        private void validatePrimaryFields()
        {
            if (!Validator.Validate(subTitleTextBox.Text, ROIConstants.NameValidationWithSlash))
            {
                throw new ROIException(ROIErrorCodes.AttachmentInvalidTitle);
            }

            if (!ROIViewUtility.IsValidFormat(dosTextBox.Text))
            {
                throw new ROIException(ROIErrorCodes.InvalidDate);
            }

            if (!ROIViewUtility.IsValidDate(dosTextBox.Text))
            {
                throw new ROIException(ROIErrorCodes.InvalidDate);
            }

            DateTime tmpDate = Convert.ToDateTime(dosTextBox.Text, System.Threading.Thread.CurrentThread.CurrentUICulture);
            if (tmpDate != null && tmpDate > DateTime.Now.Date)
            {
                throw new ROIException(ROIErrorCodes.InvalidDateOfService);
            }

            if (!Validator.Validate(encounterTextBox.Text, ROIConstants.EncounterValidation))
            {
                throw new ROIException(ROIErrorCodes.AttachmentInvalidEncounter);
            } 
            
            if (attachmentDetailUI != null)
            {
                attachmentDetailUI.ValidatePrimaryFields();
            }

            if (mode == AttachmentMode.Edit)
            {
                int pageCount = 0;
                if (!int.TryParse(filePageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
                {
                    throw new ROIException(ROIErrorCodes.AttachmentInvalidPageCount);
                }

            }
        }


        private void saveButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
          
            errorProvider.Clear();
            attachmentUploadProgress = 0;
            try
            {
                ROIViewUtility.MarkBusy(true);

                //validate input fields
                validatePrimaryFields();
                AttachmentDetails tmpAttachment = (AttachmentDetails)GetData(ROIViewUtility.DeepClone(this.attachment));

                if (mode == AttachmentMode.Edit)
                {
                    createEditHandler(tmpAttachment, e);
                }
                else
                {
                    attachmentUploadProgress = 25;  //25% work done
                    ShowProgress(25, false);
                    //upload attachment = 50% of work should be completed during this process - progress bar indicator
                    attachmentUploadProgress = 50;
                    //create new attachment
                    //upload attachment
                    List<object> fileDetailList = attachmentDetailUI.UploadAttachment(ProgressHandler);

                    //process each attachment
                    //the upload attachment should add each uploaded attachment to the uploadattachments list
                    //for each uploaded attachment, create new attachment entry
                    tmpAttachment.AttachmentDate = DateTime.Now;
                    int progressIndicator = 50;  //progress bar should be set to 50% at this point.
                    attachmentUploadProgress = 95; //95% of work done at the end of the next segment
                    foreach (object tmpFileDetail in fileDetailList)
                    {
                        progressIndicator += 45/fileDetailList.Count;
                        ShowProgress(progressIndicator, false);
                        tmpAttachment.Id = 0;
                        tmpAttachment.FileDetails = (AttachmentFileDetails)tmpFileDetail;
                        tmpAttachment.PageCount = tmpAttachment.FileDetails.PageCount;
                        PatientValidator validator = new PatientValidator();
                        if (!validator.Validate(tmpAttachment) || !SetErrorForInvalidDate())
                        {
                            throw validator.ClientException;
                        }
                        createEditHandler(tmpAttachment, e);

                    }

                    ShowProgress(100, false);
                    ShowProgress(100, false);
                    ShowProgress(100, false);
                    ShowProgress(100, true);

                }

               if (!string.IsNullOrEmpty(tmpAttachment.FacilityCode))
               {
                   FacilityDetails facility = FacilityDetails.GetFacility(tmpAttachment.FacilityCode, tmpAttachment.FacilityType);
                   if (facility == null)
                   {
                       UserData.Instance.Facilities.Add(new FacilityDetails(tmpAttachment.FacilityCode, tmpAttachment.FacilityCode, FacilityType.NonHpf));
                   }
               }            

                IsDirty = false;
                cancelHandler(this.ParentForm, null);
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                fileTransferProgress.Visible = false;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }


        private bool SetErrorForInvalidDate()
        {
            if (!ROIViewUtility.IsValidFormat(dosTextBox.Text))
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(dosTextBox, rm.GetString(ROIErrorCodes.InvalidDate));
                return false;
            }
            if (!ROIViewUtility.IsValidDate(dosTextBox.Text))
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(dosTextBox, rm.GetString(ROIErrorCodes.InvalidDateValue));
                return false;
            }
            return true;
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            IsDirty = false;
            cancelHandler(this.ParentForm, null);
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            try
            {
                ROIViewUtility.MarkBusy(true);

                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                string messageText = rm.GetString(DeleteAttachmentDialogMessageKey);
                if (attachment.IsReleased)
                {
                    messageText = rm.GetString(DeleteReleasedAttachmentDialogMessageKey);
                }

                rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                string titleText = rm.GetString(DeleteAttachmentDialogTitle);
                string okButtonText = rm.GetString(DeleteAttachmentDialogOkButton);
                string cancelButtonText = rm.GetString(DeleteAttachmentDialogCancelButton);

                rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                string okButtonToolTip = rm.GetString(DeleteAttachmentDialogOkButtonToolTip);
                string cancelButtonToolTip = rm.GetString(DeleteAttachmentDialogCancelButtonToolTip);

                if (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert))
                {
                    deleteHandler(attachment, e);
                    IsDirty = false;
                    cancelHandler(this.ParentForm, null);
                }
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        private void attchmentLocationComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
			//CR#368898 Ntt Data 12 - June - 2012
            if (attchmentLocationComboBox.SelectedIndex != bPrevIndex)
            {
                bPrevIndex = attchmentLocationComboBox.SelectedIndex;
                if (attachmentDetailUI != null)
                {
                    attachmentDetailUI.DisableEvents(dirtyDataHandler);
                }
                ClearControls();
                attachmentDetailPanel.Controls.Clear();
                HideControls();
                bool isReleased = false;

                if (attachment != null
                    && mode == AttachmentMode.Edit)
                {
                    isReleased = attachment.IsReleased;
                }
                attachmentDetailUI = AttachmentHelper.GetAttachmentDetailUI(
                                     (AttachmentHelper.AttachmentLocation)attchmentLocationComboBox.SelectedValue,
                                      mode == AttachmentMode.Edit,
                                     isReleased);
                ROIBaseUI attachmentBaseUI = (ROIBaseUI)attachmentDetailUI;
                if (attachmentDetailUI != null)
                    attachmentDetailUI.PrePopulate();

                if (((AttachmentHelper.AttachmentLocation)attchmentLocationComboBox.SelectedValue).Equals(AttachmentHelper.AttachmentLocation.ExternalSourceAttachment))
                {
                    if (!(mode.Equals(AttachmentMode.Edit)))
                    {
                        if (attachmentDetailUI != null)
                        {
                            attachmentBaseUI.SetExecutionContext(Context);
                            if (Pane.View.GetType().IsAssignableFrom(typeof(PatientRecordsMCPView)))
                            {

                                attachmentDetailUI.SetData(((PatientRecordsEditor)Pane.ParentPane).Patient);
                                ExternalSourceAttachment.requestId = null;
                            }
                            else
                            {
                                attachmentDetailUI.SetData(((RequestPatientInfoUI)Pane.View).Patient);
                                ExternalSourceAttachment.requestId = (((RequestPatientInfoEditor)Pane.ParentPane).Request.Id).ToString();
                                ExternalSourceAttachment.reqType = (((RequestPatientInfoEditor)Pane.ParentPane).Request.Requestor.TypeName).ToString();
                                ExternalSourceAttachment.reqReceiptDt = ((((RequestPatientInfoEditor)Pane.ParentPane).Request.ReceiptDate).Value.ToString(DateTime24hrFormat, CultureInfo.InvariantCulture));
                            }
                            attachmentUIControl = (Control)attachmentDetailUI;
                            attachmentBaseUI.Localize();
                            bottomPanel.Controls.Remove(upperPanel);
                            bottomPanel.Controls.Remove(lowerPanel);
                            bottomPanel.Controls.Add(attachmentUIControl);
                            saveButton.Visible = false;
                            cancelButton.Location = new Point(cancelButtonPosition.X - 50, cancelButtonPosition.Y);
                            cancelButton.Text = "Close";
                            this.Refresh();

                        }
                    }
                    else
                    {
                        attachmentDetailUI = AttachmentHelper.GetAttachmentDetailUI(
                               AttachmentHelper.AttachmentLocation.LocalFileAttachment,
                               mode == AttachmentMode.Edit,
                               isReleased);
                        attachmentBaseUI = (ROIBaseUI)attachmentDetailUI;
                        attachmentDetailUI.PrePopulate();
                        bottomPanel.Refresh();
                        bottomPanel.Controls.Remove(attachmentUIControl);
                        bottomPanel.Controls.Add(upperPanel);
                        bottomPanel.Controls.Add(lowerPanel);
                        saveButton.Visible = true;
                        cancelButton.Text = "Cancel";
                        ResetCancelButtonPosition();

                        if (attachmentDetailUI != null)
                        {
                            ShowControls();
                            attachmentBaseUI.SetPane(Pane);
                            attachmentBaseUI.SetExecutionContext(Context);
                            attachmentBaseUI.Localize();
                            //if edit mode - set data
                            if (mode == AttachmentMode.Edit)
                            {
                                attachmentDetailUI.ShowControls();
                                if (attachment != null)
                                {
                                    attachmentDetailUI.SetData(attachment);
                                }
                            }
                            attachmentDetailUI.EnableEvents(dirtyDataHandler);
                            attachmentUIControl = (Control)attachmentDetailUI;
                            attachmentUIControl.TabIndex = 2;
                            attachmentDetailPanel.Controls.Clear();
                            attachmentDetailPanel.Controls.Add(attachmentUIControl);
                        }

                    }
                }
                else
                {
                    bottomPanel.Refresh();
                    bottomPanel.Controls.Remove(attachmentUIControl);
                    bottomPanel.Controls.Add(upperPanel);
                    bottomPanel.Controls.Add(lowerPanel);
                    saveButton.Visible = true;
                    cancelButton.Text = "Cancel";
                    ResetCancelButtonPosition();

                    if (attachmentDetailUI != null)
                    {
                        ShowControls();
                        attachmentBaseUI.SetPane(Pane);
                        attachmentBaseUI.SetExecutionContext(Context);
                        attachmentBaseUI.Localize();
                        //if edit mode - set data
                        if (mode == AttachmentMode.Edit)
                        {
                            attachmentDetailUI.ShowControls();
                            if (attachment != null)
                            {
                                attachmentDetailUI.SetData(attachment);
                            }
                        }
                        attachmentDetailUI.EnableEvents(dirtyDataHandler);
                        attachmentUIControl = (Control)attachmentDetailUI;
                        attachmentUIControl.TabIndex = 2;
                        attachmentDetailPanel.Controls.Clear();
                        attachmentDetailPanel.Controls.Add(attachmentUIControl);
                    }

                }
            }
         }

        /// <summary>
        /// Set From Date value
        /// </summary>
        private void SetDOBNullValue()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            dosTextBox.Text = rm.GetString(ROIConstants.DefaultDate);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets the Header for NonHpfDialogUI.
        /// </summary>
        public HeaderUI Header
        {
            get { return headerUI; }
            set
            {
                headerUI = value;
                headerUI.Dock = DockStyle.Fill;
                this.topPanel.Controls.Add(headerUI);
            }
        }

        /// <summary>
        /// Sets the Mode whether create/edit.
        /// </summary>
        public AttachmentMode AttachMode
        {
            set
            {
                mode = value;
                deleteButton.Visible = (mode == AttachmentMode.Edit);
            }
            get { return mode; }
        }
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

        #endregion
            
        public void ResetCancelButtonPosition()
        {
            cancelButton.Location = cancelButtonPosition;
        }
        public void SetCancelBtnCaption(string caption)
        {
            cancelButton.Text = caption;
        }
        
        public  Component View
        {
            get { return this; }

        }
    }
}
