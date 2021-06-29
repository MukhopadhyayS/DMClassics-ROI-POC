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
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{
    /// <summary>
    /// Display the BillingTierODPUI.
    /// </summary>
    public partial class BillingTierTabUI : ROIBaseUI, IAdminBaseTabUI
    {
        #region Fields
        
        private const int defaultPrice = 0;       
        
        private EventHandler dirtyDataHandler;
        
        private BillingTierDetails billingTierDetail;
        private MediaTypeDetails supplementaryMediaType;

        private BindingList<MediaTypeDetails> mediaTypes;

        private bool enableSave;       

        #endregion
        
        #region Constructor
        
        /// <summary>
        /// Initialize UserInterface Component of BillingTierODP.
        /// </summary>
        public BillingTierTabUI()
        {
            InitializeComponent();

            mediaTypes       = new BindingList<MediaTypeDetails>();
            dirtyDataHandler = new EventHandler(MarkDirty);
            pageRateTierGroupUI.Parent = this;
        }
       
        #endregion

        #region Methods

        /// <summary>
        /// Clear Control values.
        /// </summary>
        public void ClearControls()
        {
            nameTextBox.Enabled = true;
            nameTextBox.Text = string.Empty;
            
            (Pane.View as AdminBaseObjectDetailsUI).ClearErrors();

            mediaTypes.Remove(supplementaryMediaType);
            mediaTypeComboBox.Enabled = true;

            if (mediaTypeComboBox.Items.Count > 0)
            {
                mediaTypeComboBox.SelectedIndex = (mediaTypeComboBox.Items.Count > 0) ? 0 : -1;
            }

            descriptionTextBox.Text = string.Empty;
            salesTaxCheckBox.Checked = false;
            baseChargetextBox.Text  = string.Empty;
            baseChargetextBox.Text  = Convert.ToString(defaultPrice, System.Threading.Thread.CurrentThread.CurrentCulture);

            pageRateTierGroupUI.ClearControls();
            nameTextBox.Focus();
            
        }

        /// <summary>
        /// Get the BillingtierDetails object.
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            double charge;
            double otherCharge;
            ExceptionData error;

            BillingTierDetails billingTier = (appendTo == null) ? new BillingTierDetails()
                                            : appendTo as BillingTierDetails;

            billingTier.Name        = nameTextBox.Text.Trim();
            billingTier.Description = descriptionTextBox.Text.Trim();
            billingTier.SalesTax = (salesTaxCheckBox.Checked) ? ROIConstants.Yes : ROIConstants.No;            
            billingTier.MediaType   = (MediaTypeDetails)mediaTypeComboBox.SelectedItem;

            Collection<ExceptionData> errors = new Collection<ExceptionData>();

            if (string.IsNullOrEmpty(billingTier.Name))
            {
                error = new ExceptionData(ROIErrorCodes.BillingTierNameEmpty);
                errors.Add(error);
            }

            if (billingTier.MediaType.Id == 0)
            {
                error = new ExceptionData(ROIErrorCodes.MediaTypeNameEmpty);
                errors.Add(error);
            }


            if (!Double.TryParse(baseChargetextBox.Text.Trim(), out charge))
            {
                error = new ExceptionData(ROIErrorCodes.BillingTierBaseChargeIsNotValid);
                errors.Add(error);
            }
            else
            {
                billingTier.BaseCharge = charge;
            }

            if (!Double.TryParse(pageRateTierGroupUI.OtherPageChargeTextBox.Text.Trim(), out otherCharge))
            {
                error = new ExceptionData(ROIErrorCodes.BillingTierOtherPageChargeIsNotValid);
                errors.Add(error);
            }
            else
            {
                billingTier.OtherPageCharge = otherCharge;
            }

            billingTier = (BillingTierDetails)pageRateTierGroupUI.GetData(billingTier, errors);

            if (errors.Count > 0)
            {
                throw new ROIException(errors);
            }
            //billingTier.BaseCharge      = charge;
            //billingTier.OtherPageCharge = otherCharge;
            //billingTier             = (BillingTierDetails)pageRateTierGroupUI.GetData(billingTier);
            
            return billingTier;
        }


        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();
            billingTierDetail = data as BillingTierDetails;

            if (billingTierDetail != null)
            {
                nameTextBox.Text        = billingTierDetail.Name;
                descriptionTextBox.Text = billingTierDetail.Description;
                salesTaxCheckBox.Checked = !(string.IsNullOrEmpty(billingTierDetail.SalesTax))
                                           ? string.Compare(billingTierDetail.SalesTax, "yes", true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0 : false;
                baseChargetextBox.Text  = billingTierDetail.BaseCharge.ToString("F", System.Threading.Thread.CurrentThread.CurrentCulture);
                nameTextBox.Enabled     = billingTierDetail.Id >= 0;

                mediaTypeComboBox.Enabled = billingTierDetail.Id > 0 && 
                                            (billingTierDetail.MediaType.Name != ROIConstants.Electronic ||
                                             !billingTierDetail.IsAssociated);
                
                if (billingTierDetail.Name == ROIConstants.NonHpf)
                {
                    mediaTypes.Add(supplementaryMediaType);
                }

                mediaTypeComboBox.SelectedItem = billingTierDetail.MediaType;
                pageRateTierGroupUI.SetData(billingTierDetail);
            }

            EnableEvents();
        }

        /// <summary>
        /// Returns the respective control for the given error code.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.BillingTierNameAlreadyExist         : //fall through
                case ROIErrorCodes.BillingTierNameEmpty                : //fall through
                case ROIErrorCodes.BillingTierNameMaxLength            : return nameTextBox;

                case ROIErrorCodes.BillingTierBaseChargeLength         : //fall through
                case ROIErrorCodes.BillingTierBaseChargeIsNotValid     : return baseChargetextBox;

                case ROIErrorCodes.MediaTypeNameEmpty                  : //fall through
                case ROIErrorCodes.ElectronicMediaType                 : return mediaTypeComboBox;

                case ROIErrorCodes.PageTiersNotSequential              : //fall through
                case ROIErrorCodes.PageTiersExist                      : //fall through 
                case ROIErrorCodes.PageTierInvalidPriceFormat          : //fall through
                case ROIErrorCodes.BillingTierOtherPageChargeIsNotValid: //fall through
                case ROIErrorCodes.PageTierEmptyOrInvalid              : //fall through
                case ROIErrorCodes.PageTierPageNotValid                : return pageRateTierGroupUI.GetErrorControl(error);
            }

            return null;
        }
        
        /// <summary>
        ///  This method is used to enable(subscribe)the BillingTierODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged                     += dirtyDataHandler;
            descriptionTextBox.TextChanged              += dirtyDataHandler;
            salesTaxCheckBox.CheckedChanged             += dirtyDataHandler;
            mediaTypeComboBox.SelectedIndexChanged      += dirtyDataHandler;
            baseChargetextBox.TextChanged               += dirtyDataHandler;

            if (pageRateTierGroupUI.ThroughPageTextBox != null)
            {
                pageRateTierGroupUI.ThroughPageTextBox.TextChanged     += dirtyDataHandler;
            }

            if (pageRateTierGroupUI.OtherPageChargeTextBox != null)
            {
                pageRateTierGroupUI.OtherPageChargeTextBox.TextChanged += dirtyDataHandler;
            }

            pageRateTierGroupUI.EnableEvents();
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the BillingTierODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged                     -= dirtyDataHandler;
            descriptionTextBox.TextChanged              -= dirtyDataHandler;
            salesTaxCheckBox.CheckedChanged             -= dirtyDataHandler;
            mediaTypeComboBox.SelectedIndexChanged      -= dirtyDataHandler;
            baseChargetextBox.TextChanged               -= dirtyDataHandler;

            if (pageRateTierGroupUI.ThroughPageTextBox != null)
            {
                pageRateTierGroupUI.ThroughPageTextBox.TextChanged     -= dirtyDataHandler;
            }

            if (pageRateTierGroupUI.OtherPageChargeTextBox != null)
            {
                pageRateTierGroupUI.OtherPageChargeTextBox.TextChanged -= dirtyDataHandler;
            }
        }

        /// <summary>
        /// Occurs when user changes MediaName or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim())
               || mediaTypeComboBox.SelectedIndex == 0
               || String.IsNullOrEmpty(baseChargetextBox.Text.Trim())                            
               || String.IsNullOrEmpty(pageRateTierGroupUI.ThroughPageTextBox.Text.Trim())
               || String.IsNullOrEmpty(pageRateTierGroupUI.OtherPageChargeTextBox.Text.Trim()))
            {
                enableSave = false;
            }
            else
            {
                enableSave = true;
            }

            (Pane as AdminBaseODP).MarkDirty(sender, e);
        }

        /// <summary>
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control != dollarLabel)
            {
                return control.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control);
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
        /// Apply Localization for UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, nameLabel);
            SetLabel(rm, mediaTypeLabel);
            SetLabel(rm, descriptionLabel);
            SetLabel(rm, baseChargeLabel);
            SetLabel(rm, ratesLabel);
            SetLabel(rm, dollarLabel);
            SetLabel(rm, salesTaxLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, baseChargetextBox);
            
            pageRateTierGroupUI.Localize();
        }

        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            pageRateTierGroupUI.SetExecutionContext(Context);
            pageRateTierGroupUI.SetPane(Pane);
        }
        
        /// <summary>
        /// Fill Media types in a Combo box.
        /// </summary>
        /// <param name="mediaTypes"></param>
        public void PopulateMediaType(Collection<MediaTypeDetails> mediaTypeDetails)
         {
             ///Do not display the ï¿½supplementaryï¿½ option when the user is creating or 
             ///modifying a billing tier except when the user has elected to view the ï¿½Supplementary Documentsï¿½ billing tier. 

             DisableEvents();

            foreach (MediaTypeDetails mediaTypeDetail in mediaTypeDetails)
            {
                if (mediaTypeDetail.Name == ROIConstants.NonHpf)
                {
                    supplementaryMediaType = mediaTypeDetail;
                }
                else
                {
                    mediaTypes.Add(mediaTypeDetail);
                }
            }
            /// This is for "Please select" item in mediatype combo box
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            MediaTypeDetails forSelect = new MediaTypeDetails();
            forSelect.Id = 0;
            forSelect.Name = rm.GetString("mediaTypeText." + this.GetType().Name);
            mediaTypes.Insert(0, forSelect);
            
            mediaTypeComboBox.DisplayMember = "Name";
            mediaTypeComboBox.ValueMember = "id";
            mediaTypeComboBox.DataSource = mediaTypes;

            //EnableEvents();
        }

        /// <summary>
        /// It will restrict user to enter other characters apart from Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void baseChargetextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// Assign base charge format value.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void baseChargetextBox_Leave(object sender, EventArgs e)
        {
            string baseCharge = baseChargetextBox.Text;
            double amount;
            if ((baseCharge.Length != 0) && (baseCharge.Length < 6) && double.TryParse(baseChargetextBox.Text, out amount))
            {

                baseCharge = string.Format(System.Threading.Thread.CurrentThread.CurrentCulture, "{0:0.00}", amount);
                if (baseCharge.Length <= 6)
                {
                    baseChargetextBox.Text = baseCharge;
                }
            }
        }

        # endregion

        #region Properties

        /// <summary>
        /// Gets enable save value.
        /// </summary>
        public bool EnableSave
        {
            get { return enableSave; }
        }

        #endregion  
    }
}
