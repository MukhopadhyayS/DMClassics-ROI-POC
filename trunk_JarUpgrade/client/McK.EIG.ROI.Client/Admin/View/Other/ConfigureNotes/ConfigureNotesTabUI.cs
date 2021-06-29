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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureNotes
{
    /// <summary>
    /// ConfigureNotesTabUI.
    /// </summary>
    public partial class ConfigureNotesTabUI : ROIBaseUI, IAdminBaseTabUI
    {

        #region Fields

        private EventHandler dirtyDataHandler;
        private bool enableSave;

        #endregion


        #region Constructor

        public ConfigureNotesTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty); 
        }
        
        #endregion

        #region Methods
        
        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            nameTextBox.Text = string.Empty;
            displayTextBox.Text = string.Empty;
            nameTextBox.Focus();
        }
        
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged     += dirtyDataHandler;
            displayTextBox.TextChanged  += dirtyDataHandler;
        }

        public void DisableEvents()
        {
            nameTextBox.TextChanged     -= dirtyDataHandler;
            displayTextBox.TextChanged  -= dirtyDataHandler;
        }

        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            if (data != null)
            {
                NotesDetails notesDetails = data as NotesDetails;
                nameTextBox.Text    = notesDetails.Name;
                displayTextBox.Text = notesDetails.DisplayText;
            }

            EnableEvents();
        }

        public object GetData(object appendTo)
        {
            NotesDetails notesDetails = (appendTo == null) ? new NotesDetails()
                                                                : appendTo as NotesDetails;

            notesDetails.Name = nameTextBox.Text.Trim();
            notesDetails.DisplayText = displayTextBox.Text.Trim();
            return notesDetails;
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
                case ROIErrorCodes.ConfigureNotesNameAlreadyExist       : return nameTextBox;
                case ROIErrorCodes.ConfigureNotesNameEmpty              : return nameTextBox;
                case ROIErrorCodes.ConfigureNotesDisplayTextEmpty       : return displayTextBox;
                case ROIErrorCodes.ConfigureNotesDisplayTextMaxLength   : return displayTextBox;
                case ROIErrorCodes.ConfigureNotesNameMaxLength          : return nameTextBox;                

            }
            return null;
        }

        /// <summary>
        /// Occurs when user changes configure notes name or display text.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim())
                || String.IsNullOrEmpty(displayTextBox.Text.Trim()))
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
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, configureNameLabel);
            SetLabel(rm, displayTextLabel);
        }
        #endregion

        #region Properites

        public bool EnableSave
        {
            get { return enableSave; }
        }

        #endregion
    }
}
