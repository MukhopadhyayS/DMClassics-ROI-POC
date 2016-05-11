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
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Requestors.View
{
    public partial class RequestorHeaderUI : UserControl
    {
        #region Constructor

        public RequestorHeaderUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        private static void SetLabel(ResourceManager rm, Control control)
        {
            control.Text = rm.GetString(control.Name);
        }

        /// <summary>
        /// Localize
        /// </summary>
        /// <param name="context"></param>
        public void Localize(ExecutionContext context)
        {
            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, requestorLabel);
        }

        public void PopulateRequestorInformation(RequestorDetails requestorInfo, ExecutionContext context)
        {
            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            firstLineText.Text  = string.Empty;
            secondLineText.Text = string.Empty;
            thirdLineText.Text  = string.Empty;
            fourthLineText.Text = string.Empty;
            fifthLineText.Text  = string.Empty;

            firstLineText.Text += requestorInfo.TypeName + ", " + requestorInfo.FullName;
            if (requestorInfo.IsPatientRequestor)
            {
                secondLineText.Text += rm.GetString("dobShortLabel") + " ";
                secondLineText.Text += (!requestorInfo.PatientDob.HasValue) ? string.Empty : requestorInfo.PatientDob.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                secondLineText.Text += "      " + rm.GetString("ssnLabel") + " " + ROIViewUtility.ApplyMask(requestorInfo.PatientSSN);

                thirdLineText.Text += requestorInfo.MainAddress.Address1;
                if (!string.IsNullOrEmpty(requestorInfo.MainAddress.City))
                {
                    thirdLineText.Text += ", ";
                }
                thirdLineText.Text += requestorInfo.MainAddress.City;
                if (!string.IsNullOrEmpty(requestorInfo.MainAddress.State))
                {
                    thirdLineText.Text += ", ";
                }
                thirdLineText.Text += requestorInfo.MainAddress.State;

                fourthLineText.Text += rm.GetString("homePhoneLabel") + " ";
                fourthLineText.Text += requestorInfo.HomePhone;
                fourthLineText.ForeColor = thirdLineText.ForeColor;

                if (requestorInfo.LetterRequired && requestorInfo.PrepaymentRequired)
                {
                    fifthLineText.Text = rm.GetString("bothLetterRequired");
                }
                else if (requestorInfo.LetterRequired)
                {
                    fifthLineText.Text += rm.GetString("letterRequiredCheckBox");
                }
                else if (requestorInfo.PrepaymentRequired)
                {
                    fifthLineText.Text += rm.GetString("paymentRequiredCheckBox");
                }
                else
                {
                    fifthLineText.Text += "";
                }
            }
            else
            {
                secondLineText.Text += requestorInfo.MainAddress.Address1;
                if (!string.IsNullOrEmpty(requestorInfo.MainAddress.City))
                {
                    secondLineText.Text += ", ";
                }
                secondLineText.Text += requestorInfo.MainAddress.City;
                if (!string.IsNullOrEmpty(requestorInfo.MainAddress.State))
                {
                    secondLineText.Text += ", ";
                }
                secondLineText.Text += requestorInfo.MainAddress.State;

                thirdLineText.Text += rm.GetString("workPhoneLabel") + " ";
                thirdLineText.Text += requestorInfo.WorkPhone;
                fourthLineText.ForeColor = Color.Red;
                if (requestorInfo.LetterRequired && requestorInfo.PrepaymentRequired)
                {
                    fourthLineText.Text = rm.GetString("bothLetterRequired");
                }
                else if (requestorInfo.LetterRequired)
                {
                    fourthLineText.Text += rm.GetString("letterRequiredCheckBox");
                }
                else if (requestorInfo.PrepaymentRequired)
                {
                    fourthLineText.Text += rm.GetString("paymentRequiredCheckBox");
                }
                else
                {
                    fourthLineText.Text += "";
                }
            }
        }

        #endregion

    }
}
