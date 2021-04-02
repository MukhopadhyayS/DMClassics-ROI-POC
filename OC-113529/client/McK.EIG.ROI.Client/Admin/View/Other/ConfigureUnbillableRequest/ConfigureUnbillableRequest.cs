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

namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureUnbillableRequest
{
    /// <summary>
    /// This class holds the ConfigureUnbillableRequestUI
    /// </summary>
    public partial class ConfigureUnbillableRequestUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(ConfigureUnbillableRequestUI));

        #region Fields

        private EventHandler dirtyDataHandler;
        private ConfigureUnbillableRequestDetails configureunbillablerequestdetails;

        #endregion

        #region constructor

        public ConfigureUnbillableRequestUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        #endregion

        #region Methods

        /// <summary>
        ///  This method is used to enable(subscribe)the ConfigureUnbillableRequest UI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            billableButton.CheckedChanged += dirtyDataHandler;
            unbillableButton.CheckedChanged += dirtyDataHandler;

            //configureunbillablerequestCheckBox.Click += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscripe)the ConfigureUnbillableRequest UI local events
        /// </summary>
        public void DisableEvents()
        {
            billableButton.CheckedChanged -= dirtyDataHandler;
            unbillableButton.CheckedChanged -= dirtyDataHandler;
            //configureunbillablerequestCheckBox.Click -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when the user changes the ConfigureUnbillableRequest UI .
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            saveButton.Enabled = true;
            cancelButton.Enabled = true;
            (Pane as ConfigureUnbillableRequestMCP).OnDataDirty(sender, e);
        }

        /// <summary>
        /// Set the UnbillableRequest.
        /// </summary>
        /// <param name="data">UnbillableRequestDetails</param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            configureunbillablerequestdetails = (ConfigureUnbillableRequestDetails)data;
            //configureunbillablerequestCheckBox.Checked = configureunbillablerequestdetails.IsUnbillableRequest;
            if (configureunbillablerequestdetails.IsUnbillableRequest == true)
            {
                unbillableButton.Checked = true;
            }
            else
            {
                billableButton.Checked = true;
            }
            cancelButton.Enabled = false;
            saveButton.Enabled = false;            
            EnableEvents();
            //configureunbillablerequestCheckBox.Focus();
        }

        /// <summary>
        /// Gets the UnbillableRequestDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            ConfigureUnbillableRequestDetails requestDetail = (appendTo == null) ? new ConfigureUnbillableRequestDetails() : (ConfigureUnbillableRequestDetails)appendTo;
            //requestDetail.IsUnbillableRequest = configureunbillablerequestCheckBox.Checked;
            if (unbillableButton.Checked == true)
            {
                requestDetail.IsUnbillableRequest = true;
            }
            else
            {
                requestDetail.IsUnbillableRequest = false;
            }
            return requestDetail;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearControls()
        {
            //configureunbillablerequestCheckBox.Checked = false;
            unbillableButton.Checked = false;
        }

        /// <summary>
        /// Apply culture and set the tooltip.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            //SetLabel(rm, configuredefaultunbillablerequestLabel);
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

                ConfigureUnbillableRequestDetails requestDetails = GetData(configureunbillablerequestdetails) as ConfigureUnbillableRequestDetails;
                ROIAdminController.Instance.UpdateConfigureUnbillableRequest(requestDetails.IsUnbillableRequest);
                UserData.Instance.IsUnbillableRequest = requestDetails.IsUnbillableRequest;

                (Pane as ConfigureUnbillableRequestMCP).IsDirty = false;

                configureunbillablerequestdetails = requestDetails;
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
        public void CancelConfigureUnbillableRequest()
        {
            (Pane as ConfigureUnbillableRequestMCP).IsDirty = false;
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            SetData(configureunbillablerequestdetails);
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            CancelConfigureUnbillableRequest();
        }

        #endregion  

        
    }
}
