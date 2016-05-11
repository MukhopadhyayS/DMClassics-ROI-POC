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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.SSNMasking
{
    /// <summary>
    /// This class holds the masking UI
    /// </summary>
    public partial class SSNMaskUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(SSNMaskUI));

        #region Fields

        private EventHandler dirtyDataHandler;
        private SSNMaskDetails ssnMaskDetails;

        #endregion

        #region constructor

        public SSNMaskUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        #endregion

        #region Methods

        /// <summary>
        ///  This method is used to enable(subscribe)the SSN Masking UI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            ssnMaskCheckBox.Click += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscripe)the SSN Masking UI local events
        /// </summary>
        public void DisableEvents()
        {
            ssnMaskCheckBox.Click -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when the user changes the SSN Masking UI .
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            saveButton.Enabled = true;
            cancelButton.Enabled = true;
            (Pane as SSNMaskMCP).OnDataDirty(sender, e);
        }

        /// <summary>
        /// Set the mask.
        /// </summary>
        /// <param name="data">MaskDetails</param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            ssnMaskDetails = (SSNMaskDetails)data;
            ssnMaskCheckBox.Checked = ssnMaskDetails.IsMasking;
            cancelButton.Enabled = false;
            saveButton.Enabled = false;            
            EnableEvents();
            ssnMaskCheckBox.Focus();
        }

        /// <summary>
        /// Gets the SSNMaskingDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            SSNMaskDetails maskingDetail = (appendTo == null) ? new SSNMaskDetails() : (SSNMaskDetails)appendTo;
            maskingDetail.IsMasking = ssnMaskCheckBox.Checked;
            return maskingDetail;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearControls()
        {
            ssnMaskCheckBox.Checked = false;            
        }

        /// <summary>
        /// Apply culture and set the tooltip.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, maskingLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
        }

        /// <summary>
        /// Return the key for the control.
        /// </summary>
        /// <param name="control">Control</param>        
        /// <returns>Key for localization</returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Save
        /// </summary>
        public bool Save()
        {
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            try
            {
                SSNMaskDetails maskDetails = GetData(ssnMaskDetails) as SSNMaskDetails;
                maskDetails = ROIAdminController.Instance.UpdateSsnMask(maskDetails);
                //Set mask peroperty
                UserData.Instance.IsSSNMasked = maskDetails.IsMasking;
                
                (Pane as SSNMaskMCP).IsDirty = false;

                ssnMaskDetails = maskDetails;
                saveButton.Enabled = false;
                cancelButton.Enabled = false;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
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
        /// Save Button Click.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnSave_Click(object sender, EventArgs e)
        {
            Save();
        }

        /// <summary>
        /// Cancel button click
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void CancelSSNMask()
        {
            (Pane as SSNMaskMCP).IsDirty = false;
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            SetData(ssnMaskDetails);
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            CancelSSNMask();
        }

        #endregion  
    }
}
