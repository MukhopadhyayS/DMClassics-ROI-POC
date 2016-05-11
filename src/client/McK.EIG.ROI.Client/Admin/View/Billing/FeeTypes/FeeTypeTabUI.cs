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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.FeeTypes
{
    /// <summary>
    /// Display the FeeTypeODP UI.
    /// </summary>
    public partial class FeeTypeTabUI : ROIBaseUI, IAdminBaseTabUI
    {       
        #region Fields

        private const int DefaultAmount = 0;
        private EventHandler dirtyDataHandler;
        private bool enableSave;
        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize UserInterface component of FeeTypeODP
        /// </summary>
        public FeeTypeTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
            amountTextBox.KeyPress +=new KeyPressEventHandler(amountTextBox_KeyPress);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            nameTextBox.Text        = string.Empty;
            descriptionTextBox.Text = string.Empty;
            amountTextBox.Text      = Convert.ToString(DefaultAmount, System.Threading.Thread.CurrentThread.CurrentCulture);
            salesTaxCheckBox.Checked = false;
            nameTextBox.Focus();
        }

        /// <summary>
        /// Gets the FeeTypeDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            FeeTypeDetails feeType = (appendTo == null) ? new FeeTypeDetails()
                                                        : appendTo as FeeTypeDetails;
            feeType.Name        = nameTextBox.Text.Trim();
            feeType.Description = descriptionTextBox.Text.Trim();

            try
            {
                feeType.Amount = (amountTextBox.Text.Trim().Length == 0) ? 0 : Convert.ToDouble(amountTextBox.Text, System.Threading.Thread.CurrentThread.CurrentCulture);
            }
            catch (FormatException fex)
            {
                throw new ROIException(ROIErrorCodes.FeeTypeAmountIsNotValid, fex);
            }

            feeType.SalesTax = (salesTaxCheckBox.Checked) ? ROIConstants.Yes : ROIConstants.No;            
           
            return feeType;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            FeeTypeDetails feeTypeDetails = data as FeeTypeDetails;

            if (feeTypeDetails != null)
            {
                nameTextBox.Text         = feeTypeDetails.Name;
                descriptionTextBox.Text  = feeTypeDetails.Description;
                amountTextBox.Text       = feeTypeDetails.Amount.ToString("F", System.Threading.Thread.CurrentThread.CurrentCulture);
                nameTextBox.ReadOnly     = feeTypeDetails.Id < 0;
                salesTaxCheckBox.Checked = !(string.IsNullOrEmpty(feeTypeDetails.SalesTax)) 
                                           ? string.Compare(feeTypeDetails.SalesTax, "yes", true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0 : false;
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
                case ROIErrorCodes.FeeTypeNameAlreadyExist  : return nameTextBox;
                case ROIErrorCodes.FeeTypeNameEmpty         : return nameTextBox;
                case ROIErrorCodes.FeeTypeIsAssociated      : return nameTextBox;
                case ROIErrorCodes.FeeTypeNameMaxLength     : return nameTextBox;
                case ROIErrorCodes.FeeTypeAmountIsNotValid  : return amountTextBox;
            }

            return null;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the FeeTypeODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged         += dirtyDataHandler;
            descriptionTextBox.TextChanged  += dirtyDataHandler;
            amountTextBox.TextChanged       += dirtyDataHandler;
            salesTaxCheckBox.CheckedChanged += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the FeeTypeODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged         -= dirtyDataHandler;
            descriptionTextBox.TextChanged  -= dirtyDataHandler;
            amountTextBox.TextChanged       -= dirtyDataHandler;
            salesTaxCheckBox.CheckedChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when user changes FeeName or Description / Amount.
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
            if (control == nameLabel || control == amountLabel || control == salesTaxLabel)
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
            SetLabel(rm, dollarLabel);
            SetLabel(rm, amountLabel);
            SetLabel(rm, salesTaxLabel);
        }

        /// <summary>
        /// This event occurs after the KeyDown event and can be used to prevent
        /// characters from entering the control.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void amountTextBox_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
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
