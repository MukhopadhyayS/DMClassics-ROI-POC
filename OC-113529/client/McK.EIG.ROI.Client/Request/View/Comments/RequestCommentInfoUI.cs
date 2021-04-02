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
using System.Collections.ObjectModel;
using System.Windows.Forms;
using System.Resources;

using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Request.View.Comments
{
    /// <summary>
    /// Display the Comments Info UI.
    /// </summary>
    public partial class RequestCommentInfoUI : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(RequestCommentInfoUI));

        private bool isDirty;

        private const string RequestCommentTitle               = "RequestCommentInfoUI.Title";
        private const string RequestCommentMessage             = "RequestCommentInfoUI.Message";
        private const string RequestCommentOkButton            = "RequestCommentInfoUI.OkButton";
        private const string RequestCommentCancelButton        = "RequestCommentInfoUI.CancelButton";
        private const string RequestCommentOkButtonTooltip     = "RequestCommentInfoUI.OkButton";
        private const string RequestCommentCancelButtonTooltip = "RequestCommentInfoUI.CancelButton";        

        #endregion

        #region Constructor

        public RequestCommentInfoUI()
        {
            InitializeComponent();
            EnableButton(false);
        }

        #endregion
     
        #region Methods

        /// <summary>
        /// Enable/Disable the button based on the Condition.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableButton(bool enable)
        {
            commentSaveButton.Enabled = enable;
            cancelButton.Enabled      = enable;
        }

         /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            //UI-Text
            SetLabel(rm, newCommentLabel);
            SetLabel(rm, commentSaveButton);
            SetLabel(rm, cancelButton);
            
            //UI-Tooltip
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, commentSaveButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        ///  Invoked when text changed in the comment text box.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void newCommentTextBox_TextChanged(object sender, EventArgs e)
        {
            if (newCommentTextBox.Text.Length > 0)
            {
                IsDirty = true;
                EnableButton(true);
            }
            else
            {
                IsDirty = false;
                EnableButton(false);
            }
        }

        /// <summary>
        /// Gets the CommentDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>        
        private CommentDetails GetData()
        {
            RequestCommentEditor editor = Pane.ParentPane as RequestCommentEditor;
            CommentDetails details = new CommentDetails();            
            details.RequestId = editor.RequestInfo.Id;
            details.EventType = EventType.CommentAdded;
            details.EventRemarks = newCommentTextBox.Text.Trim();
            return details;
        }

        /// <summary>
        /// Invoked when Cancel button Clicked
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            CancelComment();
        }

        public void CancelComment()
        {
            errorProvider.Clear();
            newCommentTextBox.Text = string.Empty;
        }

        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.RequestCommentEmpty: 
                case ROIErrorCodes.RequestCommentMaxLength: return newCommentTextBox;
            }
            return null;
        }



        public bool Save()
        {
            log.EnterFunction();
            errorProvider.Clear();
            errorProvider.SetIconAlignment(newCommentTextBox, ErrorIconAlignment.TopLeft);
            ROIViewUtility.MarkBusy(true);
            try
            {
                Application.DoEvents();
                CommentDetails commentDetails = RequestController.Instance.CreateComment(GetData());
                newCommentTextBox.Text = string.Empty;
                EnableButton(false);
                ApplicationEventArgs ae = new ApplicationEventArgs(commentDetails, this);
                RequestEvents.OnCommentCreated(Pane, ae);
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                return false;
            }
            
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
            return true;
        }

        /// <summary>
        ///  Invoked when Save Comment button Clicked
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void commentSaveButton_Click(object sender, EventArgs e)
        {
            ResourceManager rm      = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText      = rm.GetString(RequestCommentMessage);
            string titleText        = rm.GetString(RequestCommentTitle);
            string okButtonText     = rm.GetString(RequestCommentOkButton);
            string cancelButtonText = rm.GetString(RequestCommentCancelButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip     = rm.GetString(RequestCommentOkButtonTooltip);
            string cancelButtonToolTip = rm.GetString(RequestCommentCancelButtonTooltip);

            if (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert))
            {
                Save();
            }
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds))
            {
                this.Enabled = false;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets true if dirty data available.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set { isDirty = value; }
        }

        #endregion
    }
}
