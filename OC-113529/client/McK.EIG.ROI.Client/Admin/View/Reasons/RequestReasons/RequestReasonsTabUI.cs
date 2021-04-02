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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;



namespace McK.EIG.ROI.Client.Admin.View.Reasons.RequestReasons
{
    public partial class RequestReasonsTabUI : ROIBaseUI, IAdminBaseTabUI
    {

        #region Fields

        private EventHandler dirtyDataHandler;
        private bool enableSave;

        #endregion

        #region Constructor

        public RequestReasonsTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Clear Control Values.
        /// </summary>
        public void ClearControls()
        {
            reasonNameTextBox.Text = string.Empty;

            (Pane.View as AdminBaseObjectDetailsUI).ClearErrors();

            displayTextBox.Text             = string.Empty;
            attributecomboBox.SelectedIndex = 0;
            reasonNameTextBox.Focus();
        }

        /// <summary>
        /// Gets the request reason object.
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            ReasonDetails reasons = (appendTo == null) ? new ReasonDetails()
                                     : appendTo as ReasonDetails;
            
            reasons.Name        = reasonNameTextBox.Text.Trim();
            reasons.DisplayText = displayTextBox.Text.Trim();
            reasons.Attribute   = (RequestAttr)attributecomboBox.SelectedValue;
            reasons.Type        = ReasonType.Request;
            return reasons;
        }

        /// <summary>
        /// Sets the data into view.
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            ReasonDetails reasons = data as ReasonDetails;

            if (reasons != null)
            {
                reasonNameTextBox.Text          = reasons.Name;
                displayTextBox.Text             = reasons.DisplayText;
                attributecomboBox.SelectedValue = reasons.Attribute;
            }

            EnableEvents();
        }

        /// <summary>
        /// This method is used to subscribe the Request Reason ODP local events.
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            reasonNameTextBox.TextChanged          += dirtyDataHandler;
            displayTextBox.TextChanged             += dirtyDataHandler;
            attributecomboBox.SelectedIndexChanged += dirtyDataHandler;
        }

        /// <summary>
        /// This method is used to unsubscribe the Request Reason ODP local events.
        /// </summary>
        public void DisableEvents()
        {
            reasonNameTextBox.TextChanged          -= dirtyDataHandler;
            displayTextBox.TextChanged             -= dirtyDataHandler;
            attributecomboBox.SelectedIndexChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Returns the respective control for the given errorcode.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.RequestReasonNameAlreadyExist : return reasonNameTextBox;
                case ROIErrorCodes.ReasonDisplayTextAlreadyExist : return displayTextBox;
                case ROIErrorCodes.ReasonDisplayTextEmpty        : return displayTextBox;
                case ROIErrorCodes.ReasonNameEmpty               : return reasonNameTextBox;
                case ROIErrorCodes.ReasonDisplayTextMaxLength    : return displayTextBox;
                case ROIErrorCodes.ReasonNameMaxLength           : return reasonNameTextBox;
                case ROIErrorCodes.RequestReasonAttributeEmpty   : return attributecomboBox;
            }
            
            return null;
        }

        /// <summary>
        /// Occurs when user changes reason name or displaytext or attributes.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(reasonNameTextBox.Text.Trim())
                ||String.IsNullOrEmpty(displayTextBox.Text.Trim()))
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
            if (control == attributeLabel)
            {
                return control.Name + "." + GetType().Name;
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
            SetLabel(rm, displayTextLabel);
            SetLabel(rm, attributeLabel);
        }

        /// <summary>
        /// Preporpulate the Request reason attributes
        /// </summary>
        /// <param name="attributes"></param>
        public void PopulateAttribute(IList attributes)
        {        
            attributecomboBox.DataSource    = attributes;
            attributecomboBox.DisplayMember = "value";
            attributecomboBox.ValueMember   = "key";
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
