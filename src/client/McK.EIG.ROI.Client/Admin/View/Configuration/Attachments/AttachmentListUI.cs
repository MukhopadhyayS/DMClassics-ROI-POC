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
using System.Text.RegularExpressions;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Admin.View.Configuration.Attachments
{
    /// <summary>
    /// Class display the UI controls of PageWeight
    /// </summary>
    public partial class AttachmentListUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(AttachmentListUI));

        #region Fields
               
        private EventHandler dirtyDataHandler;
        private AttachmentLocation attachmentLocation;

        #endregion

        #region Constructor
        /// <summary>
        /// Initialize the UI controls.
        /// </summary>
        public AttachmentListUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        #endregion

        #region Methods

        /// <summary>
        ///  This method is used to enable(subscribe)the AttachmentMCPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            attachmentLocationTextBox.TextChanged += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscripe)the AttachmentMCPUI local events
        /// </summary>
        public void DisableEvents()
        {
            attachmentLocationTextBox.TextChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when the user changes the PageWeight text.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if(string.IsNullOrEmpty(attachmentLocationTextBox.Text.Trim()))
            {
                saveButton.Enabled = false;
            }
            else
            {
                saveButton.Enabled = true;
            }            
             cancelButton.Enabled = true;
            (Pane as AttachmentMCP).OnDataDirty(sender, e);
        }

        /// <summary>
        /// Set the Attachment location.
        /// </summary>
        /// <param name="data">Attachment location</param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            attachmentLocation = data as AttachmentLocation;
            attachmentLocationTextBox.Text = attachmentLocation.Location;
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            EnableEvents();
            attachmentLocationTextBox.SelectionStart = 0;           
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = saveButton;
            }
        }

        /// <summary>
        /// Gets the Attachment location object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public AttachmentLocation GetData(object appendTo)
        {
            AttachmentLocation attachment = appendTo as AttachmentLocation;
            if (attachment == null)
            {
                attachment = new AttachmentLocation();
            }

            if (!Regex.IsMatch(attachmentLocationTextBox.Text, ROIConstants.UNCPathValidation))
            {
                throw new ROIException(ROIErrorCodes.UNCPathFormat);
            }       
            attachment.Location = attachmentLocationTextBox.Text.Trim();
           
            return attachment;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearControls()
        {
            attachmentLocationTextBox.Text = String.Empty;            
            errorProvider.Clear();
        }

        /// <summary>
        /// Apply culture and set the tooltip.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, attachmentLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);

            //rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            //SetTooltip(rm, toolTip, saveButton);
            //SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Return the key for the control.
        /// </summary>
        /// <param name="control">Control</param>
        /// <param name="toolTip">ToolTip</param>
        /// <returns>Key for localization</returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Return the control for the error code specified
        /// </summary>
        /// <param name="errorCode">ErrorCode</param>
        /// <returns>Control</returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.UNCPathFormat: return attachmentLocationTextBox;
                default: return null;
            }
        }

        public bool Save()
        {
            errorProvider.Clear();
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            AttachmentLocation oldAttachLocation = null;
            try
            {
                AttachmentLocation attachLocation = ROIViewUtility.DeepClone(attachmentLocation) as AttachmentLocation;
                oldAttachLocation = attachLocation;
                attachLocation = GetData(attachmentLocation);
                ROIAdminController.Instance.UpdateAttachmentLocation(attachLocation);
                (Pane as AttachmentMCP).IsDirty = false;

                attachmentLocation = attachLocation;

                saveButton.Enabled = false;
                cancelButton.Enabled = false;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                    attachmentLocation = oldAttachLocation;
                }
                return false;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
            log.ExitFunction();
            return true;
        }

        /// <summary>
        /// Save Button Click in AttachmentMCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnSave_Click(object sender, EventArgs e)
        {
            Save();
        }

        /// <summary>
        /// Cancel button click in AttachmentMCP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnCancel_Click(object sender, EventArgs e)
        {
            CancelAttachment();
        }

        public void CancelAttachment()
        {
            (Pane as AttachmentMCP).IsDirty = false;
            errorProvider.Clear();
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            SetData(attachmentLocation);
        }
       
        #endregion     
    }
}
