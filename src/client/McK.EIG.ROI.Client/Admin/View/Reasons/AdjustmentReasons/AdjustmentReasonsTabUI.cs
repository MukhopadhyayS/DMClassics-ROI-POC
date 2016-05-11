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

namespace McK.EIG.ROI.Client.Admin.View.Reasons.AdjustmentReasons
{
    public partial class AdjustmentReasonsTabUI : ROIBaseUI, IAdminBaseTabUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;
        private bool enableSave;

        #endregion

        #region Constructor

        public AdjustmentReasonsTabUI()
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
            nameTextBox.Text    = string.Empty;
            displayTextBox.Text = string.Empty;
            nameTextBox.Focus();
        }

        /// <summary>
        /// Gets the MediaTypeDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            ReasonDetails adjustmentReason = (appendTo == null) ? new ReasonDetails()
                                                                : appendTo as ReasonDetails;

            adjustmentReason.Name        = nameTextBox.Text.Trim();
            adjustmentReason.DisplayText = displayTextBox.Text.Trim();
            adjustmentReason.Type        = ReasonType.Adjustment;

            return adjustmentReason;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            if (data != null)
            {
                ReasonDetails adjustmentReasonDetails = data as ReasonDetails;
                nameTextBox.Text = adjustmentReasonDetails.Name;
                displayTextBox.Text = adjustmentReasonDetails.DisplayText;
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
                case ROIErrorCodes.AdjustmentReasonNameAlreadyExist : return nameTextBox;
                case ROIErrorCodes.ReasonNameEmpty                  : return nameTextBox;
                case ROIErrorCodes.ReasonNameMaxLength              : return nameTextBox;
                case ROIErrorCodes.ReasonDisplayTextEmpty           : return displayTextBox;
                case ROIErrorCodes.ReasonDisplayTextAlreadyExist    : return displayTextBox;
                case ROIErrorCodes.ReasonDisplayTextMaxLength       : return displayTextBox;                
            }
            return null;
        }
        
        /// <summary>
        ///  This method is used to enable(subscribe)the AdjustmentReasonODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged    += dirtyDataHandler;
            displayTextBox.TextChanged += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the AdjustmentReasonODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged    -= dirtyDataHandler;
            displayTextBox.TextChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when user changes MediaName or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim())
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
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, nameLabel);
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
