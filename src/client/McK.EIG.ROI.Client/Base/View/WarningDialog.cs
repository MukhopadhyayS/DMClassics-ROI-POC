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
using System.ComponentModel;
using System.Drawing;
using System.Resources;
using System.Globalization;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class WarningDialog : Form
    {
        private Log log = LogFactory.GetLogger(typeof(ROIDialog));

        #region Fields

        private int warningSeconds;
        private string dialogTitle;
        private string displayMessage;
        private string okButtonText;

        private ROIDialogIcon dialogIcon;
        private Timer warningTimer;
        private bool warningEnabled;

        #endregion

        #region Constructor

        /// <summary>
        /// Initializes the warning timer.
        /// </summary>
        /// <param name="dialogIcon"></param>
        public WarningDialog(ROIDialogIcon dialogIcon)
        {
            InitializeComponent();
            this.dialogIcon = dialogIcon;
            warningSeconds = ROIConstants.WarningSeconds;
            warningTimer = new Timer();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Occurs whenever loads the warning dialog.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void WarningDialog_Load(object sender, EventArgs e)
        {
            warningTimer.Tick += new EventHandler(warningTimer_Tick);
        }

        /// <summary>
        /// Displayes the warning dialog.
        /// </summary>
        /// <returns></returns>
        public new DialogResult Display()
        {
            Localize();
            warningTimer.Interval = 1000;
            if (WarningEnabled)
            {
                warningTimer.Start();
            }
            messageLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, DisplayMessageText, warningSeconds.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
            DialogResult result = ShowDialog();
            Close();
            return result;
        }

        /// <summary>
        /// Occurs when the specified timer interval has elapsed and the timer is enabled.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void warningTimer_Tick(object sender, EventArgs e)
        {
            if (warningSeconds < 1)
            {
                warningTimer.Enabled = false;
                warningTimer.Stop();
                this.Dispose();
                Application.Restart();
            }
            else
            {
                messageLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, DisplayMessageText, warningSeconds.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
                warningSeconds--;
            }        
        }

        private void okButton_Click(object sender, EventArgs e)
        {
            warningTimer.Enabled = false;
            this.Close();
        }

        private void WarningDialog_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (DialogResult == DialogResult.Cancel)
            {
                warningTimer.Enabled = false;
            }
        }

        /// <summary>
        /// Localizes the UI.
        /// </summary>
        private void Localize()
        {
            log.EnterFunction();
            try
            {
                this.Text = dialogTitle;
                okButton.Text = okButtonText;                         
                errorImage.Image = (Image)GetImage();

                messageLabel.Text = displayMessage;

                log.ExitFunction();
            }
            catch (MissingManifestResourceException ex)
            {
                log.FunctionFailure(ex);
                MessageBox.Show(ex.Message,
                                System.Configuration.ConfigurationManager.AppSettings["Error"],
                                MessageBoxButtons.OK,
                                MessageBoxIcon.Error,
                                MessageBoxDefaultButton.Button1,
                                0);
            }
        }

        /// <summary>
        /// Retrieve images.
        /// </summary>
        /// <returns></returns>
        private Image GetImage()
        {
            switch (dialogIcon)
            {
                case ROIDialogIcon.Alert: return ROIImages.AlertIcon;
                case ROIDialogIcon.Error: return ROIImages.ErrorIcon;
                case ROIDialogIcon.Info: return ROIImages.InfomationIcon;
            }
            return null;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets display message text.
        /// </summary>
        public string DisplayMessageText
        {
            get { return displayMessage; }
            set { displayMessage = value; }
        }

        /// <summary>
        /// Gets or sets the dialog title.
        /// </summary>
        public string DialogTitle
        {
            get { return dialogTitle; }
            set { dialogTitle = value; }
        }

        /// <summary>
        /// Gets or sets Ok button text
        /// </summary>
        public string OkButtonText
        {
            get { return okButtonText; }
            set { okButtonText = value; }
        }

        public bool WarningEnabled
        {
            get { return warningEnabled; }
            set { warningEnabled = value; }
        }

        #endregion       
    }
}
