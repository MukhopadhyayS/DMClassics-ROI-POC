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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.SalesTaxPerFacility
{
    /// <summary>
    /// This class represent the Sales Tax Per Facility ODPUI.
    /// </summary>
    public partial class SalesTaxPerFacilityTabUI : ROIBaseUI, IAdminBaseTabUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;       

        private bool enableSave;        
        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize UserInterface component of SalesTaxPerFacilityODP
        /// </summary>
        public SalesTaxPerFacilityTabUI()
        {
            InitializeComponent();
            dirtyDataHandler  = new EventHandler(MarkDirty);          
        }       


        #endregion

        #region Methods

        /// <summary>
        /// Clear control values.
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();            
            taxTextBox.Text                  = string.Empty;            
            descriptionTextBox.Text          = string.Empty;
            defaultCheckBox.Enabled          = true;
            defaultCheckBox.Checked          = false;
            taxFacilitycomboBox.Enabled      = true;
            taxFacilitycomboBox.SelectedIndex = 0;
            taxFacilitycomboBox.Focus();
        }

        /// <summary>
        /// Get the TaxPerFaciityDetails Object.
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            ExceptionData error;
            double taxValue;
            
            Collection<ExceptionData> errors = new Collection<ExceptionData>();
            TaxPerFacilityDetails taxDetails = (appendTo == null) ? new TaxPerFacilityDetails()
                                                                      : appendTo as TaxPerFacilityDetails;
            taxDetails.IsDefault = defaultCheckBox.Checked;
            FacilityDetails selectedFacility = (taxFacilitycomboBox.SelectedIndex == 0) ? 
                                                null : ((FacilityDetails)(taxFacilitycomboBox.SelectedItem));
            if (selectedFacility != null)
            {
                taxDetails.FacilityCode = selectedFacility.Code;
                taxDetails.FacilityName = selectedFacility.Name;
            }
            else
            {
                taxDetails.FacilityCode = null;
                taxDetails.FacilityName = null;
            }
            
            if (!Double.TryParse(taxTextBox.Text.Trim(), out taxValue) && taxTextBox.Text.Trim().Length > 0)
            {
                error = new ExceptionData(ROIErrorCodes.SalesTaxPercentageIsNotValid);
                errors.Add(error);

            }
            else
            {
                taxDetails.TaxPercentage = taxValue;
            }
            //taxDetails.TaxPercentage = (taxTextBox.Text.Trim().Length == 0) ? 0.0 : double.TryParse(taxTextBox.Text.Trim(), taxValue);
            taxDetails.Description = descriptionTextBox.Text.Trim();
            if (errors.Count > 0)
            {
                throw new ROIException(errors);
            }
            return taxDetails;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {            
            DisableEvents();
            ClearControls();

            TaxPerFacilityDetails taxPerFacilityDetails = data as TaxPerFacilityDetails;
            if (taxPerFacilityDetails != null)
            {
                defaultCheckBox.Checked = taxPerFacilityDetails.IsDefault;
                //taxFacilitycomboBox.SelectedValue = taxPerFacilityDetails.FacilityCode;                
                taxTextBox.Text = taxPerFacilityDetails.TaxPercentage.ToString("F", System.Threading.Thread.CurrentThread.CurrentUICulture);                
                descriptionTextBox.Text = taxPerFacilityDetails.Description;                
                FacilityDetails facility = FacilityDetails.GetFacility(taxPerFacilityDetails.FacilityCode, FacilityType.Hpf);
                if (facility != null)
                {
                    if (taxFacilitycomboBox.Items.Contains(facility))
                    {
                        taxFacilitycomboBox.SelectedItem = facility;
                    }
                }
                taxFacilitycomboBox.Enabled = false;
            }            
            EnableEvents();
        }

        /// <summary>
        /// Method to get the error control.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.SalesTaxFacilityAlreadyExist      : return taxFacilitycomboBox;
                case ROIErrorCodes.SalesTaxFacilityEmpty             : return taxFacilitycomboBox;
                case ROIErrorCodes.SalesTaxPercentageEmpty           : return taxTextBox;
                case ROIErrorCodes.SalesTaxPercentageMaxLength       : return taxTextBox;
                case ROIErrorCodes.SalesTaxPercentageIsNotValid      : return taxTextBox;
                case ROIErrorCodes.SalesTaxDescMaxLength             : return descriptionTextBox;                
            }
            return null;
        }

        /// <summary>
        /// This method is used to enable(subscribe)the SalesTaxPerFacilityODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();            
            taxTextBox.TextChanged                   += dirtyDataHandler;
            descriptionTextBox.TextChanged           += dirtyDataHandler;            
            defaultCheckBox.CheckedChanged           += dirtyDataHandler;            
            taxFacilitycomboBox.SelectedIndexChanged += dirtyDataHandler;
        }

        /// <summary>
        /// This method is used to disable(unsubscribe)the SalesTaxPerFacilityODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            taxTextBox.TextChanged                   -= dirtyDataHandler;
            descriptionTextBox.TextChanged           -= dirtyDataHandler;
            defaultCheckBox.CheckedChanged           -= dirtyDataHandler;
            taxFacilitycomboBox.SelectedIndexChanged -= dirtyDataHandler;
        }     


        /// <summary>
        /// Occurs when the user changes the facility or tax percentage details.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if ((taxFacilitycomboBox.SelectedIndex == 0))
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
        /// It will restrict user to enter other characters apart from Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void taxTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }        

        /// <summary>
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == facilityLabel || control == taxLabel)
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
            return control.Name + "." + Pane.GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, defaultCheckBox);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, descriptionLabel);
            SetLabel(rm, taxLabel);            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
           
        }

        /// <summary>
        /// Populate the pre defined facilities in facilityComboBox.
        /// </summary>
        /// <param name=""></param>
        public void PopulateFacilities()
        {
            
            DisableEvents();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string pleaseSelect = rm.GetString(ROIConstants.PleaseSelect);
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                if (fac.Type == FacilityType.Hpf)
                {
                    taxFacilitycomboBox.Items.Add(fac);
                }
            }            
            taxFacilitycomboBox.Items.Insert(0, new FacilityDetails(pleaseSelect, pleaseSelect, FacilityType.NonHpf));
            taxFacilitycomboBox.DisplayMember = "Name";
            taxFacilitycomboBox.ValueMember = "Code";
            taxFacilitycomboBox.SelectedIndex = 0;            
        }
      
        #endregion       
   
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
