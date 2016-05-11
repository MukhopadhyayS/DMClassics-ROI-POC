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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    /// <summary>
    /// This modal dialog will be displayed when the user has 
    /// selected the ï¿½New/Edit Documentï¿½ button from the Patient Records screen.
    /// 
    /// CR#285,853 ï¿½ Enhance non-HPF document support.
    /// </summary>
    public partial class NonHpfDocumentDialog : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(NonHpfDocumentDialog));

        #region Fields

        private const string SelectOption = "  /  /    ";

        private const string DeleteNonHpfDocumentDialogMessageKey = "A401";
        private const string DeleteNonHpfDocumentDialogTitle = "DeleteNonHpfDocumentDialog.Title";
        private const string DeleteNonHpfDocumentDialogOkButton = "deleteButton";
        private const string DeleteNonHpfDocumentDialogCancelButton = "cancelButton";
        private const string DeleteNonHpfDocumentDialogOkButtonToolTip = "DeleteNonHpfDocumentDialog.OkButton";
        private const string DeleteNonHpfDocumentDialogCancelButtonToolTip = "DeleteNonHpfDocumentDialog.CancelButton";

        private EventHandler dirtyDataHandler;

        private EventHandler createEditHandler;
        private EventHandler deleteHandler;
        private CancelEventHandler cancelHandler;

        private bool isDirty;
        private Mode mode;
        private NonHpfDocumentDetails nonHpfDocument;
        private HeaderUI headerUI;

        #endregion

        #region Constructor

        public NonHpfDocumentDialog(EventHandler createEditHandler, EventHandler deleteHandler, CancelEventHandler closeHandler)
            : this()
        {
            this.createEditHandler = createEditHandler;
            this.deleteHandler = deleteHandler;
            this.cancelHandler = closeHandler;
        }

        public NonHpfDocumentDialog()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        #endregion

        #region Methods

        internal void MarkDirty(object sender, EventArgs e)
        {
            //if(!typeof(PatientRecordsMCPView).IsAssignableFrom(Pane.View.GetType()))
            //{
            //    return;
            //}

            IsDirty = true;

            if (string.IsNullOrEmpty(docTypeComboBox.Text.Trim()) ||
                string.IsNullOrEmpty(facilityComboBox.Text.Trim()) ||
                dosTextBox.Text.Trim() == SelectOption.Trim() ||
                pageCountTextBox.Text.Trim().Length == 0)
            {
                saveButton.Enabled = false;
            }
            else
            {
                if (docTypeComboBox.SelectedIndex != 0 &&
                    facilityComboBox.SelectedIndex != 0)
                {
                    saveButton.Enabled = true;
                    cancelButton.Enabled = true;
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
            //else
            //{
            //    ((RequestPatientInfoUI)this.Pane.View).MarkDirty(sender, e);
            //}

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
            pageCountTextBox.Text = string.Empty;
            commentTextBox.Text = string.Empty;

            docTypeComboBox.SelectedIndex = 0;
            facilityComboBox.SelectedIndex = 0;
            departmentComboBox.SelectedIndex = 0;

            deleteButton.Enabled = docTypeComboBox.Enabled = dosTextBox.Enabled = departmentComboBox.Enabled
            = facilityComboBox.Enabled = pageCountTextBox.Enabled = subTitleTextBox.Enabled = encounterTextBox.Enabled = true;

            SetDOBNullValue();
        }

        /// <summary>
        /// GetData
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            NonHpfDocumentDetails nonHpfDocument = appendTo == null ? new NonHpfDocumentDetails() : appendTo as NonHpfDocumentDetails;

            int pageCount;

            if (!int.TryParse(pageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
            {
                throw new ROIException(ROIErrorCodes.NonHpfDocumentInvalidPageCount);
            }

            nonHpfDocument.PageCount = pageCount;
            nonHpfDocument.Encounter = encounterTextBox.Text;
            nonHpfDocument.Subtitle = subTitleTextBox.Text;
            nonHpfDocument.Department = (departmentComboBox.SelectedIndex == 0) ? string.Empty : departmentComboBox.Text.Trim();
            nonHpfDocument.DocType = (docTypeComboBox.SelectedIndex == 0) ? string.Empty : docTypeComboBox.Text;

            AddFacility(facilityComboBox.Text);
            nonHpfDocument.FacilityCode = (facilityComboBox.SelectedIndex == 0) ? string.Empty : ((FacilityDetails)(facilityComboBox.SelectedItem)).Code;
            nonHpfDocument.FacilityType = (facilityComboBox.SelectedIndex == 0) ? FacilityType.NonHpf : ((FacilityDetails)(facilityComboBox.SelectedItem)).Type;

            nonHpfDocument.Comment = commentTextBox.Text;
            if (dosTextBox.Text.Trim() != SelectOption.Trim() && dosTextBox.IsValidDate)
            {
                nonHpfDocument.DateOfService = Convert.ToDateTime(dosTextBox.Text, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            return nonHpfDocument;
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
            nonHpfDocument = data as NonHpfDocumentDetails;

            ClearControls();

            saveButton.Enabled = false;

            DisableEvents();

            if (nonHpfDocument != null)
            {
                encounterTextBox.Text = nonHpfDocument.Encounter;
                subTitleTextBox.Text = nonHpfDocument.Subtitle;
                pageCountTextBox.Text = Convert.ToString(nonHpfDocument.PageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);

                docTypeComboBox.SelectedItem = nonHpfDocument.DocType.Trim();

                if (nonHpfDocument.DateOfService.HasValue)
                {
                    dosTextBox.Text = nonHpfDocument.DateOfService.Value.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                }

                if (!string.IsNullOrEmpty(nonHpfDocument.Department) &&
                    departmentComboBox.FindStringExact(nonHpfDocument.Department.Trim()) < 0)
                {
                    departmentComboBox.Items.Add(nonHpfDocument.Department.Trim());
                }

                if (String.IsNullOrEmpty(nonHpfDocument.FacilityCode))
                {
                    facilityComboBox.SelectedIndex = 0;
                }
                else
                {
                    FacilityDetails facility = FacilityDetails.GetFacility(nonHpfDocument.FacilityCode, nonHpfDocument.FacilityType);

                    if (facility != null)
                    {
                        if (facilityComboBox.Items.Contains(facility))
                        {
                            facilityComboBox.SelectedItem = facility;
                        }
                    }
                }
                departmentComboBox.SelectedIndex = (string.IsNullOrEmpty(nonHpfDocument.Department)) ? 0
                                                                                                     : departmentComboBox.FindStringExact(nonHpfDocument.Department.Trim());
                commentTextBox.Text = nonHpfDocument.Comment;

                if (nonHpfDocument.IsReleased)
                {
                    deleteButton.Enabled = docTypeComboBox.Enabled = dosTextBox.Enabled = departmentComboBox.Enabled
                    = facilityComboBox.Enabled = pageCountTextBox.Enabled = subTitleTextBox.Enabled = encounterTextBox.Enabled = false;
                }
            }

            isDirty = false;
            EnableEvents();
        }

        /// <summary>
        /// FormatDocumentNode
        /// </summary>
        /// <param name="node"></param>
        /// <param name="nonHpfDocument"></param>
        public static void FormatDocumentNode(TreeNode node, NonHpfDocumentDetails nonHpfDocument)
        {
            string docName = nonHpfDocument.DocType;
            node.Text = String.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "{0,-35} | {1,-35} | {2,-35} | {3,-15} | {4,-15}", docName, nonHpfDocument.FacilityCode, nonHpfDocument.Department, nonHpfDocument.DateOfService, nonHpfDocument.Encounter);
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
            docTypeComboBox.SelectedIndexChanged    += dirtyDataHandler;
            facilityComboBox.SelectedIndexChanged   += dirtyDataHandler;
            departmentComboBox.SelectedIndexChanged += dirtyDataHandler;

            facilityComboBox.TextChanged   += dirtyDataHandler;
            departmentComboBox.TextChanged += dirtyDataHandler;
            encounterTextBox.TextChanged   += dirtyDataHandler;
            commentTextBox.TextChanged     += dirtyDataHandler;
            pageCountTextBox.TextChanged   += dirtyDataHandler;
            subTitleTextBox.TextChanged    += dirtyDataHandler;

            dosTextBox.TextChanged += dirtyDataHandler;
        }

        /// <summary>
        /// DisableEvents
        /// </summary>
        public void DisableEvents()
        {
            docTypeComboBox.SelectedIndexChanged    -= dirtyDataHandler;
            facilityComboBox.SelectedIndexChanged   -= dirtyDataHandler;
            departmentComboBox.SelectedIndexChanged -= dirtyDataHandler;

            facilityComboBox.TextChanged   -= dirtyDataHandler;
            departmentComboBox.TextChanged -= dirtyDataHandler;
            encounterTextBox.TextChanged   -= dirtyDataHandler;
            commentTextBox.TextChanged     -= dirtyDataHandler;
            pageCountTextBox.TextChanged   -= dirtyDataHandler;
            subTitleTextBox.TextChanged    -= dirtyDataHandler;

            dosTextBox.TextChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// PrePopulate
        /// </summary>
        public void PrePopulate()
        {
            DisableEvents();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            PopulateFacilities(rm.GetString("selectFacility"));
            PopulateDocTypes(rm.GetString("selectDocumentType"));
            PopulateDepartments(rm.GetString("selectDepartment"));
            EnableEvents();
        }

        private void PopulateDepartments(string defaultValue)
        {
            Collection<string> departments = PatientController.Instance.RetrieveAllDepartments();
            departmentComboBox.Items.Insert(0, defaultValue);

            foreach (string department in departments)
            {
                departmentComboBox.Items.Add(department);
            }
            departmentComboBox.SelectedIndex = 0;
        }

        private void PopulateDocTypes(string defaultValue)
        {
            Collection<RecordViewDetails> recordViews = UserData.Instance.RecordViews;

            docTypeComboBox.Items.Insert(0, defaultValue);

            foreach (RecordViewDetails recordViewDetails in recordViews)
            {
                foreach (string docName in recordViewDetails.DocTypes)
                {
                    if (!docTypeComboBox.Items.Contains(docName.Trim()))
                    {
                        docTypeComboBox.Items.Add(docName.Trim());
                    }
                }
            }
            docTypeComboBox.SelectedIndex = 0;
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

            facilityComboBox.SelectedIndex = 0;
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
            SetLabel(rm, docTypeLabel);
            SetLabel(rm, dateOfServiceLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, departmentLabel);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, commentLabel);

            //CR#285,853 ï¿½ Enhance non-HPF document support.
            SetLabel(rm, pageCountLabel);
            SetLabel(rm, subTitleLabel);

            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, deleteButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, encounterTextBox);
            SetTooltip(rm, toolTip, docTypeComboBox);
            SetTooltip(rm, toolTip, dosTextBox);
            SetTooltip(rm, toolTip, facilityComboBox);
            SetTooltip(rm, toolTip, departmentComboBox);
            SetTooltip(rm, toolTip, commentTextBox);

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
                case ROIErrorCodes.NonHpfDocumentNameEmpty: return docTypeComboBox;
                case ROIErrorCodes.NonHpfDocumentFacilityEmpty: return facilityComboBox;
                case ROIErrorCodes.NonHpfDocumentDateOfServiceEmpty: return dosTextBox;
                case ROIErrorCodes.NonHpfDocumentInvalidPageCount: return pageCountTextBox;
                case ROIErrorCodes.InvalidName: return subTitleTextBox;
                case ROIErrorCodes.InvalidEncounter: return encounterTextBox;
                case ROIErrorCodes.InvalidFacility: return facilityComboBox;
                case ROIErrorCodes.IncorrectDate: return dosTextBox;
                case ROIErrorCodes.InvalidDate: return dosTextBox;
                case ROIErrorCodes.InvalidDateValue: return dosTextBox;
                case ROIErrorCodes.InvalidDateOfService: return dosTextBox;                 
            }
            return null;
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            errorProvider.Clear();

            try
            {
                ROIViewUtility.MarkBusy(true);

                PatientValidator validator = new PatientValidator();

                NonHpfDocumentDetails nonHpfDoc = (NonHpfDocumentDetails)GetData(ROIViewUtility.DeepClone(this.nonHpfDocument));

                if (!validator.Validate(nonHpfDoc) || !SetErrorForInvalidDate())
                {
                    throw validator.ClientException;
                }

                createEditHandler(nonHpfDoc, e);

                if (!string.IsNullOrEmpty(nonHpfDoc.FacilityCode))
                {
                    FacilityDetails facility = FacilityDetails.GetFacility(nonHpfDoc.FacilityCode, nonHpfDoc.FacilityType);
                    if (facility == null)
                    {
                        UserData.Instance.Facilities.Add(new FacilityDetails(nonHpfDoc.FacilityCode, nonHpfDoc.FacilityCode, FacilityType.NonHpf));
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
                string messageText = rm.GetString(DeleteNonHpfDocumentDialogMessageKey);

                rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                string titleText = rm.GetString(DeleteNonHpfDocumentDialogTitle);
                string okButtonText = rm.GetString(DeleteNonHpfDocumentDialogOkButton);
                string cancelButtonText = rm.GetString(DeleteNonHpfDocumentDialogCancelButton);

                rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                string okButtonToolTip = rm.GetString(DeleteNonHpfDocumentDialogOkButtonToolTip);
                string cancelButtonToolTip = rm.GetString(DeleteNonHpfDocumentDialogCancelButtonToolTip);

                if (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert))
                {
                    deleteHandler(nonHpfDocument, e);
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

        private void facilityComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (departmentComboBox.Items.Count > 0)
            {
                departmentComboBox.SelectedIndex = 0;
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
        public Mode DocumentMode
        {
            set
            {
                mode = value;
                deleteButton.Visible = (mode == Mode.Edit);
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

    }
}
