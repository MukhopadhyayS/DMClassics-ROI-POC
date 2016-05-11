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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.PaymentMethods
{
    /// <summary>
    /// Display the PaymentMethodODP UI.
    /// </summary>
    public partial class PaymentMethodTabUI : ROIBaseUI, IAdminBaseTabUI 
    {
        #region Fields

        private EventHandler dirtyDataHandler;
        private bool enableSave;

        #endregion

        #region Constructor
        /// <summary>
        /// Initialize UserInterface component of PaymentMethodODP
        /// </summary>
        public PaymentMethodTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }
        #endregion

        #region Methods

        /// <summary>
        /// Gets the PaymentMethodDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            PaymentMethodDetails paymentMethod;
            if (appendTo == null)
            {
                paymentMethod = new PaymentMethodDetails();
            }
            else
            {
                paymentMethod = appendTo as PaymentMethodDetails;
            }

            paymentMethod.Name        = nameTextBox.Text.Trim();
            paymentMethod.Description = descriptionTextBox.Text.Trim();
            paymentMethod.IsDisplay   = displayCheckBox.Checked;
            return paymentMethod;
        }

        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.PaymentMethodNameAlreadyExist     : return nameTextBox;
                case ROIErrorCodes.PaymentMethodNameEmpty            : return nameTextBox;
                case ROIErrorCodes.PaymentMethodNameMaxLength        : return nameTextBox;
                case ROIErrorCodes.PaymentMethodDescriptionMaxLength : return descriptionTextBox;
            }
            return null;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged           += dirtyDataHandler;
            descriptionTextBox.TextChanged    += dirtyDataHandler;
            displayCheckBox.CheckStateChanged += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged           -= dirtyDataHandler;
            descriptionTextBox.TextChanged    -= dirtyDataHandler;
            displayCheckBox.CheckStateChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when user changes Payment Name or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim()))
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
            SetLabel(rm, descriptionLabel);
            SetLabel(rm, formFieldLabel);
            SetLabel(rm, displayCheckBox);
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            nameTextBox.Text        = string.Empty;
            descriptionTextBox.Text = string.Empty;
            displayCheckBox.Checked = false;
            nameTextBox.Focus();
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            PaymentMethodDetails paymentMethodDetails = data as PaymentMethodDetails;

            if (paymentMethodDetails != null)
            {
                nameTextBox.Text        = paymentMethodDetails.Name;
                descriptionTextBox.Text = paymentMethodDetails.Description;
                displayCheckBox.Checked = paymentMethodDetails.IsDisplay;
                nameTextBox.Enabled     = paymentMethodDetails.Id >= 0;
            }
            else
            {
                nameTextBox.Enabled = true;
            }
            EnableEvents();
            nameTextBox.Focus();
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
