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
using System.Configuration;
using System.Drawing;
using System.Reflection;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// AboutROIHelpDialogUI
    /// </summary>
    public partial class AboutROIHelpDialogUI : ROIBaseUI
    {
        #region Fields

        #endregion

        #region Constructor

        public AboutROIHelpDialogUI(ExecutionContext context)
        {
            InitializeComponent();
            this.Context = context;
            Localize();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localizes UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, headerLabel);
            SetLabel(rm, versionLabel);
            SetLabel(rm, copyrightHeaderLabel);
            SetLabel(rm, copyRightInfoLabel);            
            SetLabel(rm, supportInfoButton);
            SetLabel(rm, closeButton);
        }

        /// <summary>
        /// Sets data.
        /// </summary>
        public void SetData()
        {
            Assembly assembly = Assembly.GetEntryAssembly(); 
            System.Diagnostics.FileVersionInfo fvi = System.Diagnostics.FileVersionInfo.GetVersionInfo(assembly.Location);
            versionValueLabel.Text = fvi.ProductVersion;
        }

        #endregion

        private void supportInfoButton_Click(object sender, EventArgs e)
        {   
            (new ROIPane()).HelpRequested(null, null);             
        }

        /// <summary>
        /// Closes the dialog.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void closeButton_Click(object sender, EventArgs e)
        {
            ((Form)this.Parent).Close();
        }
    }
}
