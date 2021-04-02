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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.LetterTemplate
{
    /// <summary>
    /// This class represent the LetterTemplate ODPUI.
    /// </summary>
    public partial class LetterTemplateTabUI : ROIBaseUI, IAdminBaseTabUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;
        private EventHandler letterTypeChanged;
        private EventHandler uploadTextBoxChanged;

        private bool isFilePathChanged;
        private bool enableSave;
        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize UserInterface component of AdminLetterTemplateODP
        /// </summary>
        public LetterTemplateTabUI()
        {
            InitializeComponent();
            openFileDialog.Filter = "Word Document (*.docx)|*.docx";
            openFileDialog.FilterIndex = 1;
            dirtyDataHandler  = new EventHandler(MarkDirty);
            letterTypeChanged = new EventHandler(Process_LetterTypeChanged);            
            letterTypecomboBox.SelectionChangeCommitted += letterTypeChanged;
            uploadTextBoxChanged = new EventHandler(Process_UploadTextBoxChanged);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Clear control values.
        /// </summary>
        public void ClearControls()
        {
            isFilePathChanged = false;
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            letterTypecomboBox.SelectedIndex = 0;
            nameTextBox.Text                 = string.Empty;
            uploadFileTextBox.Text           = string.Empty;
            descriptionTextBox.Text          = string.Empty;
            defaultCheckBox.Enabled          = true;
            defaultCheckBox.Checked          = false;
            letterTypecomboBox.Focus();
        }

        /// <summary>
        /// Get the LetterTemplateDetails Object.
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            LetterTemplateDetails letterTemplate = (appendTo == null) ? new LetterTemplateDetails()
                                                                      : appendTo as LetterTemplateDetails;
            letterTemplate.IsDefault   = defaultCheckBox.Checked;
            letterTemplate.LetterType  = (LetterType)letterTypecomboBox.SelectedValue;
            letterTemplate.Name        = nameTextBox.Text.Trim();
            letterTemplate.Description = descriptionTextBox.Text.Trim();
            if (isFilePathChanged)
            {
                letterTemplate.FilePath = uploadFileTextBox.Text.Trim();
                letterTemplate.DoUpload = isFilePathChanged;
            }
            return letterTemplate;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            DisableFileTextBoxChangeEvent();
            ClearControls();

            LetterTemplateDetails letterTemplateDetails = data as LetterTemplateDetails;
            if (letterTemplateDetails != null)
            {
                defaultCheckBox.Checked = letterTemplateDetails.IsDefault;
                letterTypecomboBox.SelectedValue = letterTemplateDetails.LetterType;
                nameTextBox.Text = letterTemplateDetails.Name;
                descriptionTextBox.Text = letterTemplateDetails.Description;
                uploadFileTextBox.Text = letterTemplateDetails.FileName;
                if (letterTemplateDetails.LetterType == LetterType.Other)
                {
                    defaultCheckBox.Enabled = false;
                }
            }
            EnableFileTextBoxChangeEvent();
            EnableEvents();
        }

        /// <summary>
        /// Method to get the error control.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.LetterTemplateNameAlreadyExist    : return nameTextBox;
                case ROIErrorCodes.LetterTemplateNameEmpty           : return nameTextBox;
                case ROIErrorCodes.LetterTemplateNameMaxLength       : return nameTextBox;
                case ROIErrorCodes.LetterTemplateDescriptionMaxLength: return descriptionTextBox;
                case ROIErrorCodes.LetterTemplateInvalidFileFormat   : return uploadFileTextBox;
                case ROIErrorCodes.LetterTemplateInvalidFilePath     : return uploadFileTextBox;
                case ROIErrorCodes.LetterTemplateFileNameMaxLength   : return uploadFileTextBox;
                case ROIErrorCodes.LetterTemplateFilePathEmpty       : return uploadFileTextBox;
                case ROIErrorCodes.LetterTemplateLetterTypeEmpty     : return letterTypecomboBox;
            }
            return null;
        }

        /// <summary>
        /// This method is used to enable(subscribe)the LetterTemplateODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();

            nameTextBox.TextChanged                 += dirtyDataHandler;
            descriptionTextBox.TextChanged          += dirtyDataHandler;
            uploadFileTextBox.TextChanged           += dirtyDataHandler;
            defaultCheckBox.CheckedChanged          += dirtyDataHandler;
            letterTypecomboBox.SelectedIndexChanged += dirtyDataHandler;
        }

        /// <summary>
        /// This method is used to disable(unsubscribe)the LetterTemplateODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged                 -= dirtyDataHandler;
            descriptionTextBox.TextChanged          -= dirtyDataHandler;
            uploadFileTextBox.TextChanged           -= dirtyDataHandler;
            defaultCheckBox.CheckedChanged          -= dirtyDataHandler;
            letterTypecomboBox.SelectedIndexChanged -= dirtyDataHandler;
        }
        
        private void EnableFileTextBoxChangeEvent()
        {
            uploadFileTextBox.TextChanged += uploadTextBoxChanged;
        }

        private void DisableFileTextBoxChangeEvent()
        {
            uploadFileTextBox.TextChanged -= uploadTextBoxChanged;
        }

        /// <summary>
        /// Occurs when the user changes the LetterTemplate details.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if ((letterTypecomboBox.SelectedIndex == 0)
                 || String.IsNullOrEmpty(nameTextBox.Text.Trim())
                 || String.IsNullOrEmpty(uploadFileTextBox.Text.Trim()))
            {
                enableSave = false;
            }
            else
            {
                enableSave = true;
            }
            (Pane as AdminBaseODP).MarkDirty(sender, e);
        }

        /// <summary>
        /// Occurs when the Letter Type change.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_LetterTypeChanged(object sender, EventArgs e)
        {
            if ((LetterType)letterTypecomboBox.SelectedValue == LetterType.Other)
            {
                defaultCheckBox.Enabled = false;
                defaultCheckBox.Checked = false;
            }
            else
            {
                defaultCheckBox.Enabled = true;
            }
        }

        /// <summary>
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == letterTypeLabel || control == nameLabel)
            {
                return control.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control);
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
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, defaultCheckBox);
            SetLabel(rm, letterTypeLabel);
            SetLabel(rm, descriptionLabel);
            SetLabel(rm, nameLabel);
            SetLabel(rm, uploadFileLabel);
            SetLabel(rm, browseButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, browseButton);
        }

        /// <summary>
        /// Populate the pre defined LetterType in letterTypeComboBox.
        /// </summary>
        /// <param name="requestStatus"></param>
        public void PopulateLetterType(IList requestStatus)
        {
            IEnumerator enumerator = requestStatus.GetEnumerator();
            ArrayList requestStatusList = new ArrayList();
            while (enumerator.MoveNext())
            {
                KeyValuePair<Enum, string> pair = (KeyValuePair<Enum, string>)enumerator.Current;
                if (!LetterType.OverdueInvoice.Equals(pair.Key))
                {
                    requestStatusList.Add(pair);
                }
            }
            letterTypecomboBox.DataSource = requestStatusList;
            letterTypecomboBox.ValueMember = "key";
            letterTypecomboBox.DisplayMember = "value";
        }

        /// <summary>
        /// Event raise when browse Button is Clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void browseButton_Click(object sender, EventArgs e)
        {
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                uploadFileTextBox.Text = openFileDialog.FileName;
            }            
        }

        private void Process_UploadTextBoxChanged(object sender, EventArgs e)
        {
            isFilePathChanged = true;
        }
      
        # endregion       
   
        #region Properties

        /// <summary>
        /// Gets enable save value.
        /// </summary>
        public bool EnableSave
        {
            get { return enableSave; }
        }

        #endregion  
    }
}
