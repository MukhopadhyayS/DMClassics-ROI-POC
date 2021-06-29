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

namespace McK.EIG.ROI.Client.Admin.View.Billing.DeliveryMethod
{
    public partial class DeliveryMethodTabUI : ROIBaseUI, IAdminBaseTabUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;
        private bool enableSave;

        #endregion

        /// <summary>
        /// Initialize UserInterface component of DeliveryMethodODP
        /// </summary>
        public DeliveryMethodTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        #region Methods

        /// <summary>
        /// Clear control values.
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();

            nameTextBox.Text        = string.Empty;
            descriptionTextBox.Text = string.Empty;
            urlTextBox.Text         = string.Empty;
            defaultCheckBox.Checked = false;

            nameTextBox.Focus();
        }

        /// <summary>
        /// Get the DeliveryMethodDetailsObject.
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            DeliveryMethodDetails deliveryMethod = (appendTo == null) ? new DeliveryMethodDetails()
                                                                      : appendTo as DeliveryMethodDetails; 

            deliveryMethod.IsDefault   = defaultCheckBox.Checked;
            deliveryMethod.Name        = nameTextBox.Text.Trim();
            deliveryMethod.Description = descriptionTextBox.Text.Trim();
            try
            {
                deliveryMethod.Url = (urlTextBox.Text.Trim().Length > 0) ? new Uri(urlTextBox.Text.ToLower(System.Threading.Thread.CurrentThread.CurrentCulture).Trim()) : null;
          
            }
            catch(UriFormatException e) 
            {
                throw new ROIException(ROIErrorCodes.DeliveryMethodInvalidUrl, e);
            }

            return deliveryMethod;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            DeliveryMethodDetails deliveryMethodDetails = data as DeliveryMethodDetails;
            if (deliveryMethodDetails != null)
            {
                nameTextBox.Text        = deliveryMethodDetails.Name;
                descriptionTextBox.Text = deliveryMethodDetails.Description;
                urlTextBox.Text         = Convert.ToString(deliveryMethodDetails.Url, System.Threading.Thread.CurrentThread.CurrentCulture);
                defaultCheckBox.Checked = deliveryMethodDetails.IsDefault;
                nameTextBox.ReadOnly    = deliveryMethodDetails.Id < 0;
            }
            else
            {
                nameTextBox.ReadOnly = false;
            }

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
                case ROIErrorCodes.DeliveryMethodNameAlreadyExist : return nameTextBox;
                case ROIErrorCodes.DeliveryMethodNameEmpty        : return nameTextBox;
                case ROIErrorCodes.DeliveryMethodNameMaxLength    : return nameTextBox;
                case ROIErrorCodes.DeliveryMethodUrlMaxLength     : return urlTextBox;
                case ROIErrorCodes.DeliveryMethodInvalidUrl       : return urlTextBox;
            }

            return null;
        }

        /// <summary>
        /// This method is used to enable(subscripe)the DeliveryMethodODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged        += dirtyDataHandler;
            descriptionTextBox.TextChanged += dirtyDataHandler;
            urlTextBox.TextChanged         += dirtyDataHandler;
            defaultCheckBox.CheckedChanged += dirtyDataHandler;
        }

        /// <summary>
        /// This method is used to disable(unsubscripe)the DeliveryMethodODPUI local events
        /// </summary>

        public void DisableEvents()
        {
            nameTextBox.TextChanged        -= dirtyDataHandler;
            descriptionTextBox.TextChanged -= dirtyDataHandler;
            urlTextBox.TextChanged         -= dirtyDataHandler;
            defaultCheckBox.CheckedChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when the user changes the DeliveryMethod Name text.
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
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, defaultCheckBox);
            SetLabel(rm, nameLabel);
            SetLabel(rm, descriptionLabel);
            SetLabel(rm, urlLabel);
        }

        # endregion

        #region Properties

        public bool EnableSave
        {
            get { return enableSave; }
        }

        #endregion
    }
}
