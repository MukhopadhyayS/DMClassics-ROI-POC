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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Requestors.View.RequestorInfo
{
    public partial class RequestorAddressGroupUI : ROIBaseUI
    {

        #region Fields

        private AddressType type;
        private RequestorDetails requestor;
        private bool isDirty;
        private EventHandler dirtyDataHandler;
        private int prevMainValue;
        private int prevAltValue;
        private bool selectedMainCountry;
        private bool selectedAltCountry;
        private bool defaultCountry;

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the UI.
        /// </summary>
        public RequestorAddressGroupUI()
        {
            InitializeComponent();
            defaultCountry = false;
            dirtyDataHandler = new EventHandler(MarkDirty);
            selectedMainCountry = false;
            selectedAltCountry = false;
        }


        /// <summary>
        ///  Gets the LocalizeKey of UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, address1Label);
            SetLabel(rm, address2Label);
            SetLabel(rm, address3Label);
            SetLabel(rm, cityLabel);
            SetLabel(rm, stateLabel);
            SetLabel(rm, zipLabel);
            SetLabel(rm, countrylabel);

            addressGroupBox.Text = rm.GetString(Type.ToString());

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltipCaption(rm, address1TextBox);
            SetTooltipCaption(rm, address2TextBox);
            SetTooltipCaption(rm, address3TextBox);
            SetTooltipCaption(rm, cityTextBox);
            SetTooltipCaption(rm, stateTextBox);
            SetTooltipCaption(rm, zipTextBox);
            
        }

        private void SetTooltipCaption(ResourceManager rm, Control control)
        {
            toolTip.SetToolTip(control, string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                      rm.GetString(GetLocalizeKey(control, toolTip)),
                                                      addressGroupBox.Text.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)));
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
        /// It sets the all Address group detials
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public void SetData(object data)
        {
            ClearData();
            DisableEvents();

            requestor = (data == null) ? new RequestorDetails() : data as RequestorDetails;
          
            AddressDetails address =  (Type == AddressType.AlternateAddress) 
                                       ? requestor.AltAddress : requestor.MainAddress;
            
            if (address != null)
            {
                address1TextBox.Text = address.Address1;
                address2TextBox.Text = address.Address2;
                address3TextBox.Text = address.Address3;
                cityTextBox.Text     = address.City;
                stateTextBox.Text    = address.State;
                if (!string.IsNullOrEmpty(address.CountryName))
                {
                    requestorConfigCountrycomboBox.Text = address.CountryName;
                }
                zipTextBox.Text = address.PostalCode;                               
            }

            EnableEvents();
            IsDirty = false;
        }


        /// <summary>
        /// It collects the all Address group detials
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            requestor = (appendTo == null) ? new RequestorDetails() : appendTo as RequestorDetails;
            AddressDetails address = new AddressDetails();

            address.Address1   = address1TextBox.Text.Trim();
            address.Address2   = address2TextBox.Text.Trim();
            address.Address3   = address3TextBox.Text.Trim();
            address.City       = cityTextBox.Text.Trim();
            address.State      = stateTextBox.Text.Trim();
            address.PostalCode = zipTextBox.Text.Trim();
            address.CountryName = requestorConfigCountrycomboBox.Text;         
       
            if (Type == AddressType.AlternateAddress)
            {
                address.NewCountry = selectedAltCountry;
                requestor.AltAddress = address;
            }
            else
            {
                address.NewCountry = selectedMainCountry;
                requestor.MainAddress = address;
            }

            return requestor;
        }


        /// <summary>
        ///  Invokes the event when user changes data.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            isDirty = true;
            ((RequestorInfoUI)this.Pane.View).DirtyDataHandler(sender, e);
        }


        /// <summary>
        ///  This method is used to enable(subscribe)the Requestor Address group local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            address1TextBox.TextChanged     += dirtyDataHandler;
            address2TextBox.TextChanged     += dirtyDataHandler;
            address3TextBox.TextChanged     += dirtyDataHandler;
            cityTextBox.TextChanged         += dirtyDataHandler;
            stateTextBox.TextChanged        += dirtyDataHandler;
            zipTextBox.TextChanged          += dirtyDataHandler;
            requestorConfigCountrycomboBox.SelectedIndexChanged += dirtyDataHandler;
            

        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the Requestor Address group local events
        /// </summary>
        public void DisableEvents()
        {
            address1TextBox.TextChanged     -= dirtyDataHandler;
            address2TextBox.TextChanged     -= dirtyDataHandler;
            address3TextBox.TextChanged     -= dirtyDataHandler;
            cityTextBox.TextChanged         -= dirtyDataHandler;
            stateTextBox.TextChanged        -= dirtyDataHandler;
            zipTextBox.TextChanged          -= dirtyDataHandler;
            requestorConfigCountrycomboBox.SelectedIndexChanged -= dirtyDataHandler;
            
        }


        /// <summary>
        /// It clears the data.
        /// </summary>
        public void ClearData()
        {
            address1TextBox.Text    = String.Empty;
            address2TextBox.Text    = String.Empty;
            address3TextBox.Text    = String.Empty;
            cityTextBox.Text        = String.Empty;
            stateTextBox.Text       = String.Empty;
            zipTextBox.Text         = String.Empty;
            
        }

        /// <summary>
        /// Returns control.
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            if (error.ErrorCode == ROIErrorCodes.InvalidAltCity || error.ErrorCode == ROIErrorCodes.InvalidMainCity)
            {
                return cityTextBox;
            }
            if (error.ErrorCode == ROIErrorCodes.InvalidAltState || error.ErrorCode == ROIErrorCodes.InvalidMainState)
            {
                return stateTextBox;
            }
         /* if (error.ErrorCode == ROIErrorCodes.InvalidAltZip || error.ErrorCode == ROIErrorCodes.InvalidMainZip)
            {
                return zipTextBox;
            }*/

            return null;
        }
                
        // Added for CR#356633 
        /// <summary>
        /// Event occurs when zip textbox is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void zipTextBox_TextChanged(object sender, EventArgs e)
        {
            
        }
        

        #endregion

        #region Properties

        public AddressType Type
        {
            get { return type; }
            set { type = value; }
        }

        // <summary>
        /// Sets true if dirty data available.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set
            {
                isDirty = value;
                if (!isDirty)
                {
                    EnableEvents();
                }
            }
        }

        public ComboBox RequestorConfigCountrycomboBox
        {
            get { return requestorConfigCountrycomboBox; }
        }

        public string SetReqComboText
        {
            set { requestorConfigCountrycomboBox.Text = value; }
        }

        public bool IsDefaultSel
        {
            set { defaultCountry = value; }
            get { return defaultCountry; }
        }

        
        #endregion

        private void ConfigCountrycomboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!defaultCountry)
            {
                if (requestorConfigCountrycomboBox.SelectedIndex != prevMainValue)
                {

                    if (Type == AddressType.AlternateAddress)
                    {
                        selectedAltCountry = true;
                    }
                    else
                    {
                        selectedMainCountry = true;
                    }
                }
            }
            prevMainValue = requestorConfigCountrycomboBox.SelectedIndex;
        }
        
    }
}
