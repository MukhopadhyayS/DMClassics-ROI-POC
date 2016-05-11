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

namespace McK.EIG.ROI.Client.Admin.View.Billing.PageWeight
{
    /// <summary>
    /// Class display the UI controls of PageWeight
    /// </summary>
    public partial class PageWeightListUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(PageWeightListUI));

        #region Fields
               
        private EventHandler dirtyDataHandler;
        private PageWeightDetails pageWeightDetails;

        #endregion

        #region Constructor
        /// <summary>
        /// Initialize the UI controls.
        /// </summary>
        public PageWeightListUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        #endregion

        #region Methods

        /// <summary>
        ///  This method is used to enable(subscribe)the PageWeightMCPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            pageWeightTextBox.TextChanged += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscripe)the PageWeightMCPUI local events
        /// </summary>
        public void DisableEvents()
        {
            pageWeightTextBox.TextChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when the user changes the PageWeight text.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if(string.IsNullOrEmpty(pageWeightTextBox.Text.Trim()))
            {
                saveButton.Enabled = false;
            }
            else
            {
                saveButton.Enabled = true;
            }            
             cancelButton.Enabled = true;
            (Pane as PageWeightMCP).OnDataDirty(sender, e);
        }

        /// <summary>
        /// Set the page weight.
        /// </summary>
        /// <param name="data">PageWeightDetails</param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            pageWeightDetails = data as PageWeightDetails;
            pageWeightTextBox.Text = Convert.ToString(pageWeightDetails.PageWeight, System.Threading.Thread.CurrentThread.CurrentCulture);
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            EnableEvents();
            pageWeightTextBox.Focus();
            pageWeightTextBox.SelectionStart = 0;
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = saveButton;
            }
        }

        /// <summary>
        /// Gets the PageWeightDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public PageWeightDetails GetData(object appendTo)
        {
            PageWeightDetails pageWeight = appendTo as PageWeightDetails;
            if (pageWeight == null)
            {
                pageWeight = new PageWeightDetails();
            }

            try
            {
               pageWeight.PageWeight = Int64.Parse(pageWeightTextBox.Text, System.Threading.Thread.CurrentThread.CurrentCulture);
            }
            catch (FormatException cause)
            {
                throw new ROIException(ROIErrorCodes.PageWeightFormat, cause);
            }
            return pageWeight;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearControls()
        {
            pageWeightTextBox.Text = String.Empty;            
            errorProvider.Clear();
        }

        /// <summary>
        /// Apply culture and set the tooltip.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, pageWeightLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, cancelButton);
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
                case ROIErrorCodes.PageWeightFormat: return pageWeightTextBox;
                default: return null;
            }
        }

        public bool Save()
        {
            errorProvider.Clear();
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            try
            {   
                PageWeightDetails weightDetails = ROIViewUtility.DeepClone(pageWeightDetails) as PageWeightDetails;
                weightDetails = GetData(weightDetails);
                weightDetails = ROIAdminController.Instance.UpdateWeight(weightDetails);
                (Pane as PageWeightMCP).IsDirty = false;

                pageWeightDetails = weightDetails;

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
        /// Save Button Click in PageWeightMCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnSave_Click(object sender, EventArgs e)
        {
            Save();
        }

        /// <summary>
        /// Cancel button click in PageWeightMCP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnCancel_Click(object sender, EventArgs e)
        {
            CancelPageWeight();
        }

        public void CancelPageWeight()
        {
            (Pane as PageWeightMCP).IsDirty = false;
            errorProvider.Clear();
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            SetData(pageWeightDetails);
        }

        /// <summary>
        /// Event occurs when the page weight TextBox text is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pageWeightTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            int KeyCode = (int)e.KeyChar;
            if (!(KeyCode >= 48 && KeyCode <= 57) && KeyCode != 8)
            {
                e.Handled = true;
            }
        }
       
        #endregion     
    }
}
