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
using System.Globalization;
using System.Reflection;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    public enum ROIDialogIcon
    {
        Alert,
        Error,
        Info,
        InfoIcon,
        RightIcon
    }

    public enum DefaultSelectButton
    {
        Yes,
        No,
        None
    }

    public partial class ROIDialog : Form
    {
        private Log log = LogFactory.GetLogger(typeof(ROIDialog));

        #region Fields

        //ROIDialog Title.
        public const string ROIDialogTitle          = "ROIDialog.Title";
        public const string ROIDialogOkButton       = "ROIDialog.OkButton";
        public const string ROIDialogCancelButton   = "ROIDialog.CancelButton";
        public const string ROIDialogIgnoreButton   = "ROIDialog.IgnoreButton";
        public const string OkButtonROIDialog       = "okButton.ROIDialog";
        public const string CancelButtonROIDialog   = "cancelButton.ROIDialog";
        public const string IgnoreButtonROIDialog   = "ignoreButton.ROIDialog";

        private string dialogTitle;
        private string displayHeader;
        private string displayMessage;

        private string okButtonText;
        private string cancelButtonText;
        private string ignoreButtonText;

        private string okButtonToolTip;
        private string cancelButtonToolTip;
        private string ignoreButtonToolTip;

        private ROIDialogIcon dialogIcon;

        #endregion

        #region Constructor

        public ROIDialog()
        {
            InitializeComponent();
        }

        public ROIDialog(ROIDialogIcon dialogIcon):this()
        {   
            this.dialogIcon = dialogIcon;
        }

        #endregion

        #region Methods

        public DialogResult Display()
        {
            log.EnterFunction();
            Localize();
            DialogResult result = ShowDialog();
            Close();
            log.ExitFunction();
            return result;
        }

        private void Localize()
        {
            log.EnterFunction();
            try
            {
                this.Text         = dialogTitle;
                okButton.Text     = okButtonText;
                cancelButton.Text = cancelButtonText;
                ignoreButton.Text = ignoreButtonText;
                errorImage.Image  = (Image)GetImage();

                headerLabel.Text  = displayHeader;
                messageLabel.Text = displayMessage;

                toolTip.SetToolTip(okButton, okButtonToolTip);
                toolTip.SetToolTip(cancelButton, cancelButtonToolTip);
                toolTip.SetToolTip(ignoreButton, ignoreButtonToolTip);

                log.ExitFunction();
            }
            catch (MissingManifestResourceException ex)
            {
                log.FunctionFailure(ex);
                MessageBox.Show(ex.Message,
                                ConfigurationManager.AppSettings["Error"],
                                MessageBoxButtons.OK,
                                MessageBoxIcon.Error,
                                MessageBoxDefaultButton.Button1,
                                0);
            }
        }

        internal Image GetImage()
        {
            switch (dialogIcon)
            {
                case ROIDialogIcon.Alert: return ROIImages.AlertIcon;
                case ROIDialogIcon.Error: return ROIImages.ErrorIcon;
                case ROIDialogIcon.Info:  return ROIImages.InfomationIcon;
                case ROIDialogIcon.InfoIcon: return ROIImages.InfoIcon;
                case ROIDialogIcon.RightIcon: return ROIImages.RightIcon;
            }
            return null;
        }

        private void okButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            DialogResult = DialogResult.OK;
            log.ExitFunction();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            this.DialogResult = DialogResult.Cancel;
            log.ExitFunction();
        }
        #endregion

        #region Properties

        public string DialogTitle
        {
            get { return dialogTitle; }
            set{ dialogTitle = value; }
        }

        public ROIDialogIcon DialogIcon
        {
            get { return dialogIcon; }
            set{ dialogIcon = value;}
        }

        public string OkButtonText
        {
            get { return okButtonText; }
            set{ okButtonText = value; }
        }

        public string CancelButtonText
        {
            get { return cancelButtonText; }
            set{ cancelButtonText = value; }
        }

        public string IgnoreButtonText
        {
            get { return ignoreButtonText; }
            set { ignoreButtonText = value; }
        }

        public string DisplayMessageText
        {
            get { return displayMessage; }
            set { displayMessage = value; }
        }

        public string OkButtonToolTip
        {
            get { return okButtonToolTip; }
            set{ okButtonToolTip = value; }
        }

        public string CancelButtonToolTip
        {
            get { return cancelButtonToolTip; }
            set{ cancelButtonToolTip = value; }
        }

        public string IgnoreButtonToolTip
        {
            get { return ignoreButtonToolTip; }
            set { ignoreButtonToolTip = value; }
        }

        public bool HideCancelButton
        {
            get { return cancelButton.Visible; }
            set { cancelButton.Visible = !value; }
        }

        public Button IgnoreButton
        {
            get { return ignoreButton; }
        }

        public string MessageHeaderText
        {
            get { return displayHeader; }
            set { displayHeader = value; }
        }

        public Button OkButton
        {
            get { return okButton; }
        }

        public Button CancelButton
        {
            get { return cancelButton; }
        }

        #endregion
    }
}
