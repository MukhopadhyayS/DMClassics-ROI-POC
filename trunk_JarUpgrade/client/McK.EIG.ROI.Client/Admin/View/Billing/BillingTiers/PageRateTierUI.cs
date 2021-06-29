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
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Text.RegularExpressions;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{
    /// <summary>
    /// Page level tier user control
    /// </summary>

    public partial class PageRateTierUI : ROIBaseUI
    {
        #region Fields

        private const string DeleteRateTierTitle = "DeleteRateTierDialog.Title";
        private const string DeleteRateTierMessage = "DeleteRateTierDialog.Message";
        private const string DeleteRateTierOkButton = "DeleteRateTierDialog.OkButton";
        private const string DeleteRateTierCancelButton = "DeleteRateTierDialog.CancelButton";
        private const string DeleteRateTierOkButtonToolTip = "DeleteRateTierDialog.OkButton";
        private const string DeleteRateTierCancelButtonToolTip = "DeleteRateTierDialog.CancelButton";

        private Log log = LogFactory.GetLogger(typeof(PageRateTierUI));

        private static string dollar = "= $";
        private ROIBaseUI parent;
       
        internal EventHandler deleteHandler;
        internal EventHandler newRateTierHandler;
        internal EventHandler dirtyDataHandler;       
        
        #endregion

        #region Constructor

        public PageRateTierUI()
        {
            InitializeComponent();
            this.Dock = DockStyle.Top;
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        public PageRateTierUI(bool isDefaultPageTier) : this()
        {
            if (isDefaultPageTier)
            {
                PageLevelTierDetails pageLevelTier = new PageLevelTierDetails();
                pageLevelTier.StartPage = 1;
                SetData(pageLevelTier);
            }
            IsDeletedIconVisible(!isDefaultPageTier);
            IsRequiredIconVisible(isDefaultPageTier);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Deletes the selected Page level tier
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>

        private void DeleteImg_Click(object sender, EventArgs e)
        {
            if (ShowConfirmDeletePageRateTierDialog())
            {
                deleteHandler(this, e);
            }
        }

        /// <summary>
        /// Shows delete confirmation dialog
        /// </summary>
        /// <returns></returns>
        private bool ShowConfirmDeletePageRateTierDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(DeleteRateTierMessage);
            string titleText = rm.GetString(DeleteRateTierTitle);
            string okButtonText = rm.GetString(DeleteRateTierOkButton);
            string cancelButtonText = rm.GetString(DeleteRateTierCancelButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(DeleteRateTierOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(DeleteRateTierCancelButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// It raises when user adds a new rate tier.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="e"></param>
        private void OnNewRateTierHandler(object sender, EventArgs e)
        {
            if (newRateTierHandler != null)
            {
                newRateTierHandler(sender, e);
            }
        }

        /// <summary>
        /// It will restrict user to enter other characters apart from Numerical, and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>

        private void OnKeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back)
            {
                e.Handled = true;
            }
        }      

        /// <summary>
        /// It will restrict user to enter other characters apart from Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pricePerPageTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }       

        /// <summary>
        /// Occurs when user changes BillingTierName or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            ((PageRateTierGroupUI)parent).MarkDirty(sender, e);
            OnNewRateTierHandler(null, null);
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the BillingTierODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            fromPageTextBox.TextChanged     += dirtyDataHandler;
            toPageTextBox.TextChanged       += dirtyDataHandler;
            pricePerPageTextBox.TextChanged += dirtyDataHandler;
            deleteImg.Click                 += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the BillingTierODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            fromPageTextBox.TextChanged     -= dirtyDataHandler;
            toPageTextBox.TextChanged       -= dirtyDataHandler;
            pricePerPageTextBox.TextChanged -= dirtyDataHandler;
            deleteImg.Click                 -= dirtyDataHandler;
        }

        /// <summary>
        /// It collects the all PageLevel Tier detials
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            PageLevelTierDetails pageLevelTier = data as PageLevelTierDetails;

            if (pageLevelTier == null)
            {
                pageLevelTier = new PageLevelTierDetails();
            }

            pageLevelTier.StartPage     = Convert.ToInt32(GetValue(fromPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);
            pageLevelTier.EndPage       = Convert.ToInt32(GetValue(toPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);
            pageLevelTier.PricePerPage  = Convert.ToDouble(GetValue(pricePerPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);
        
           
            return pageLevelTier;
        }

        /// <summary>
        /// It validates page rate tier fields and if fields are invalid it shows online error.
        /// </summary>
        /// <param name="errors"></param>
        /// <param name="index"></param>
        /// <returns></returns>

        public Collection<ExceptionData> CheckErrors(Collection<ExceptionData> errors, int index)
        {
            try
            {
                Convert.ToInt32(GetValue(fromPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);
                if (string.IsNullOrEmpty(fromPageTextBox.Text))
                {
                    ExceptionData error = new ExceptionData(ROIErrorCodes.PageTierPageNotValid, "fromPage." + index);
                    errors.Add(error);   
                }
                
            }
            catch (FormatException cause)
            {
                log.FunctionFailure(cause);
                ExceptionData error = new ExceptionData(ROIErrorCodes.PageTierPageNotValid, "fromPage." + index);
                errors.Add(error);   
            }  
            try
            {
                Convert.ToInt32(GetValue(toPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);
                if (string.IsNullOrEmpty(toPageTextBox.Text))
                {
                    ExceptionData error = new ExceptionData(ROIErrorCodes.PageTierEmptyOrInvalid, "toPage." + index);
                    errors.Add(error);   
                }
            }
            catch (FormatException cause)
            {
                log.FunctionFailure(cause);
                ExceptionData error = new ExceptionData(ROIErrorCodes.PageTierEmptyOrInvalid, "toPage." + index);
                errors.Add(error);   
            }

            try
            {
                Convert.ToDouble(GetValue(pricePerPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);
            }
            catch (FormatException cause)
            {
                log.FunctionFailure(cause);
                ExceptionData error = new ExceptionData(ROIErrorCodes.PageTierInvalidPriceFormat, "pricePerPage." + index);
                errors.Add(error);  
            }

            return errors;
        }

        /// <summary>
        /// It returns zero if the value is empty.
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        private static string GetValue(string value)
        {
            return value.Trim().Length == 0 ? "0" : value;
        }

        /// <summary>
        /// It sets the page level tier data in to PageLevelTierUI user control
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            PageLevelTierDetails pageLevelTier = data as PageLevelTierDetails;
            
            fromPageTextBox.Text = Convert.ToString(pageLevelTier.StartPage, System.Threading.Thread.CurrentThread.CurrentCulture);
            toPageTextBox.Text   = Convert.ToString(pageLevelTier.EndPage, System.Threading.Thread.CurrentThread.CurrentCulture) == "0"
                                   ? string.Empty : Convert.ToString(pageLevelTier.EndPage, 
                                                                            System.Threading.Thread.CurrentThread.CurrentCulture);

            if(pageLevelTier.Id != 0)
            {
                pricePerPageTextBox.Text = String.Format(System.Threading.Thread.CurrentThread.CurrentCulture, "{0:F}", new object[] { pageLevelTier.PricePerPage });
            }
          
            if (pageLevelTier.StartPage == 1)
            {
                Dock = DockStyle.Top;
                IsRequiredIconVisible(true);
                IsDeletedIconVisible(false);
                fromPageTextBox.Enabled = false;
            }
            else
            {
                IsRequiredIconVisible(false);
                IsDeletedIconVisible(true);
                Dock = DockStyle.Bottom;
            }
            
            EnableEvents();
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + this.GetType().Name;
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, pageLabel);
            SetLabel(rm, perPageLabel);
            SetLabel(rm, throughPageLabel);
            rm  = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, fromPageTextBox);
            SetTooltip(rm, toolTip, toPageTextBox);
            SetTooltip(rm, toolTip, pricePerPageTextBox);
            SetTooltip(rm, toolTip, deleteImg);

            dollarLabel.Text = dollar;
        }

        /// <summary>
        /// Apply Localization
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// It handles whether Required image can be visible or not for a Page level tier.
        /// </summary>
        public bool IsRequiredIconVisible(bool visible)
        {
            return requiredImg.Visible = visible;
        }

        /// <summary>
        /// It handles whether Delete image can be visible or not for a Page level tier.
        /// </summary>
        public bool IsDeletedIconVisible(bool visible)
        {
            return deleteImg.Visible = visible;
        }

        /// <summary>
        /// Gets Control name
        /// </summary>
        /// <param name="controlName"></param>
        /// <returns></returns>
        internal Control GetControl(string controlName)
        {
            return this.Controls[controlName];
        }

        /// <summary>
        /// Checks all fields in page rate tier is filled or not.
        /// </summary>
        /// <returns></returns>
        public bool CheckPageTiersFilled()
        {
            errorProvider.Clear();
            if (!Validator.Validate(toPageTextBox.Text, ROIConstants.Numeric))
            {   
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());                
                errorProvider.SetError(toPageTextBox, rm.GetString(ROIErrorCodes.PageTierEmptyOrInvalid));
                return false;
            }
            if (!Validator.Validate(fromPageTextBox.Text, ROIConstants.Numeric))
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(fromPageTextBox, rm.GetString(ROIErrorCodes.PageTierPageNotValid));
                return false;
            }
            else
            {
                int fromPage = Convert.ToInt32(GetValue(fromPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);
                int toPage = Convert.ToInt32(GetValue(toPageTextBox.Text), System.Threading.Thread.CurrentThread.CurrentCulture);

                return (fromPageTextBox.Text.Length > 0 && toPageTextBox.Text.Length > 0 && pricePerPageTextBox.Text.Length > 0
                        && fromPage > 0 && toPage > 0 && fromPage <= toPage);            
            }         
        }
       
        #endregion                   

        #region Properties

        public ROIBaseUI Parent
        {
            get { return parent;  }
            set { parent = value;  }
        }

        #endregion
    }
}

