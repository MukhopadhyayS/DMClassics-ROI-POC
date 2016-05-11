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
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.Common.Utility.View;
using System.Resources;
using McK.EIG.ROI.Client.Admin.Model;
using System.Collections.ObjectModel;
using System.Globalization;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    public partial class ModifyBillingTierUI : ROIBaseUI
    {
        #region Constructor

        public ModifyBillingTierUI()
        {
            InitializeComponent();
        }

        public ModifyBillingTierUI(IPane pane) : this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, modifyBillingTierLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
        }

        /// <summary>
        /// Gets the localize key of the control for displaying control's text with current culture
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        public void PrePopulate(Collection<BillingTierDetails> billingTiers)
        {
         
            billingTierComboBox.DisplayMember = "Name";
            billingTierComboBox.ValueMember = "Id";
            billingTierComboBox.DataSource = billingTiers;
            billingTierComboBox.SelectedIndex = 0;
        }

        /// <summary>
        /// Sets the billing tiers in combo box
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            if (data != null)
            {
                billingTierComboBox.SelectedValue = data;
            }

            saveButton.Enabled = false;
        }

        private void billingTierComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            saveButton.Enabled = billingTierComboBox.SelectedIndex > 0;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets modified billing tier
        /// </summary>
        public long SelectedBillingTier
        {
            get  { return Convert.ToInt64(billingTierComboBox.SelectedValue, 
                                          System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }

        #endregion
        
    }
}
