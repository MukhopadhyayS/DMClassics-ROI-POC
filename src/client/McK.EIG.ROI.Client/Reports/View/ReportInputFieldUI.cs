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
using System.ComponentModel;
using System.Resources;
using System.Text;
using System.Globalization;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Reports.Model;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportDateRangeUI
    /// </summary>
    public partial class ReportInputFieldUI : ROIBaseUI
    {

        #region Fields

        //Panel reportInputFieldPanel= new Panel();
        private static bool isValidFieldValue = true;
        private const int RequestorNameMaxLength = 256;        

        #endregion

        #region Constructor
        /// <summary>
        /// Initializes the UI
        /// </summary>
        public ReportInputFieldUI()
        {
            isValidFieldValue = true;
            InitializeComponent();
        }

        #endregion

        #region Methods
        /// <summary>
        /// Populated date options.
        /// </summary>
        public void Populate()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            field_Label.Text = rm.GetString("requestorName.SalesTaxReport");            
            field_Value.Text = "";
        }
        public string RetrieveFieldValue()
        {
            field_Value.Text = field_Value.Text.ToString().Trim();
            return field_Value.Text.ToString();
        }
        #endregion

        #region Properties

        

        #endregion
        public bool IsValidField()
        {
            return isValidFieldValue;
        }
        private void inputField_TextChanged(object sender, EventArgs e)
        {
            isValidFieldValue = true;
            ShowInlineError(field_Value, string.Empty);
            if (!Validator.Validate(field_Value.Text, ROIConstants.NameValidation))
            {
                ShowInlineError(field_Value, RetrieveMessage(ROIErrorCodes.RequestorNameInReport));
                isValidFieldValue= false;

            }
            if (field_Value.Text.Length > RequestorNameMaxLength)
            {
                ShowInlineError(field_Value, RetrieveMessage(ROIErrorCodes.RequestorNameMaxLength));
                isValidFieldValue = false;
            }
            ReportEvents.OnInputFieldChanged(Pane, e);
        }
        private string RetrieveMessage(string key)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            return rm.GetString(key);
        }
        private void ShowInlineError(Control ctrl, string message)
        {
            errorProvider.SetError(ctrl, message);
        }
    }
}
