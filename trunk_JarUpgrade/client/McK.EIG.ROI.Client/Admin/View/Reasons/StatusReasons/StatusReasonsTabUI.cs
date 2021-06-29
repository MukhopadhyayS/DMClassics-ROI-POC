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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Reasons.StatusReasons
{
    /// <summary>
    /// Display the StatusReasons ODP UI
    /// </summary>
    public partial class StatusReasonsTabUI : ROIBaseUI, IAdminBaseTabUI
    {                
        #region Fields

        private EventHandler dirtyDataHandler;
        private bool enableSave;

        #endregion

        #region Constructor
        /// <summary>
        /// Initialize UserInterface component of StatusReasonODP
        /// </summary>
        public StatusReasonsTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }
        
        #endregion
        
        #region Methods
     
        /// <summary>
        /// Populates request status combobox
        /// </summary>
        /// <param name="requestStatus"></param>
        public void PopulateRequestStatus(IList requestStatus)
        {
            requestStatus.RemoveAt(1);
            requestStatus.RemoveAt(7);
            requestStatus.RemoveAt(requestStatus.IndexOf(new KeyValuePair<Enum, string>(RequestStatus.Unknown,
                                                                                          EnumUtilities.GetDescription(RequestStatus.Unknown))));
            statusReasonComboBox.DataSource = requestStatus;
            statusReasonComboBox.DisplayMember = "value";
            statusReasonComboBox.ValueMember = "key";
            statusReasonComboBox.SelectedIndex = 0;
            statusReasonComboBox.Refresh();
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            nameTextBox.Text                   = string.Empty;
            diplayTextTextBox.Text             = string.Empty;
            statusReasonComboBox.SelectedIndex = 0;
            statusReasonComboBox.Focus();
        }

        /// <summary>
        /// Gets the ReasonDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            ReasonDetails statusReasons = (appendTo == null) ? new ReasonDetails()
                                           : appendTo as ReasonDetails;
           
            statusReasons.Name          = nameTextBox.Text.Trim();
            statusReasons.DisplayText   = diplayTextTextBox.Text.Trim();
            statusReasons.RequestStatus = (RequestStatus)statusReasonComboBox.SelectedValue;
            statusReasons.Type          = ReasonType.Status;

            return statusReasons;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            ReasonDetails statusReasonsDetails = data as ReasonDetails;

            if (statusReasonsDetails != null)
            {
                nameTextBox.Text                   = statusReasonsDetails.Name;
                diplayTextTextBox.Text             = statusReasonsDetails.DisplayText;
                statusReasonComboBox.SelectedValue = statusReasonsDetails.RequestStatus;
                nameTextBox.ReadOnly               = statusReasonsDetails.Id < 0;
            }
            else
            {
                nameTextBox.ReadOnly = false;
            }

            EnableEvents();
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
                case ROIErrorCodes.StatusReasonNameAlreadyExist       : return nameTextBox;                                
                case ROIErrorCodes.ReasonNameMaxLength                : return nameTextBox;
                case ROIErrorCodes.ReasonNameEmpty                    : return nameTextBox;
                case ROIErrorCodes.StatusReasonStatusEmpty            : return statusReasonComboBox;
                case ROIErrorCodes.StatusReasonDisplayTextAlreadyExist: return diplayTextTextBox;
                case ROIErrorCodes.ReasonDisplayTextMaxLength         : return diplayTextTextBox;
                case ROIErrorCodes.ReasonDisplayTextEmpty             : return diplayTextTextBox;
            }
            return null;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the StatusReasonODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged                   += dirtyDataHandler;
            diplayTextTextBox.TextChanged             += dirtyDataHandler;
            statusReasonComboBox.SelectedIndexChanged += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the StatusReasonODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged                   -= dirtyDataHandler;
            diplayTextTextBox.TextChanged             -= dirtyDataHandler;
            statusReasonComboBox.SelectedIndexChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when user changes reason name, status or display text.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim())
                ||statusReasonComboBox.SelectedIndex == 0
                ||String.IsNullOrEmpty(diplayTextTextBox.Text.Trim()))
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
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == nameLabel)
            {              
                return control.Name; 
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, nameLabel);
            SetLabel(rm, requestStatusLabel);
            SetLabel(rm, displayTextLabel);
        }

        #endregion   
    
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
