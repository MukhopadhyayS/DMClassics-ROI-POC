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
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Request.View
{
    public partial class RequestPatientHeaderUI : UserControl
    {
        #region Fields

        private const string RequestPrefix       = ".RequestHeaderUI";        
      
        private ExecutionContext context;

        #endregion

        #region Constructor

        public RequestPatientHeaderUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        private void SetLabel(ResourceManager rm, Control control)
        {
            if (control != requestorLabel)
            {
                control.Text = rm.GetString(control.Name + RequestPrefix);
            }
            else
            {
                control.Text = rm.GetString(control.Name);
            }
        }

        /// <summary>
        /// Localize
        /// </summary>
        /// <param name="context"></param>
        public void Localize(ExecutionContext context)
        {
            this.context = context;
            if (context != null)
            {
                ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
                SetLabel(rm, requestorLabel);
                SetLabel(rm, requestLabel); 
            }
        }

        public void PopulateRequestorInfo(RequestDetails requestInfo)
        {  
            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
            RequestorDetails requestorDetails = requestInfo.Requestor;           
            StringBuilder patientInfo;

            requestorText.Text = requestorDetails.TypeName + ", " + requestorDetails.Name;

            patientInfo = new StringBuilder();
            patientInfo.Append(rm.GetString("genderLabel"));
            //patientInfo.Append(patientDetails.Gender.ToString());            
            patientInfo.Append(' ', 3);
            patientInfo.Append(rm.GetString("dobShortLabel"));
            if (requestorDetails.PatientDob.HasValue)
            {
                patientInfo.Append(requestorDetails.PatientDob.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture));
            }
            patientInfo.Append(' ', 3);
            patientInfo.Append(rm.GetString("ssnLabel"));
            patientInfo.Append(ROIViewUtility.ApplyMask(requestorDetails.PatientSSN));
            patientInfoText.Text = patientInfo.ToString();

            if (requestorDetails.MainAddress != null)
            {
                patientInfo = new StringBuilder();
                patientInfo.Append((requestorDetails.MainAddress.Address1 == null) 
                                    ? string.Empty 
                                    : requestorDetails.MainAddress.Address1);
                if (!string.IsNullOrEmpty(requestorDetails.MainAddress.City))
                {
                    patientInfo.Append(", ");
                }
                patientInfo.Append((requestorDetails.MainAddress.City == null) 
                                    ? string.Empty
                                    : requestorDetails.MainAddress.City);
                if (!string.IsNullOrEmpty(requestorDetails.MainAddress.State))
                {
                    patientInfo.Append(", ");
                }                
                patientInfo.Append((requestorDetails.MainAddress.State == null) 
                                    ? string.Empty 
                                    : requestorDetails.MainAddress.State);
                addressText.Text = patientInfo.ToString();
            }

            patientInfo = new StringBuilder();
            patientInfo.Append(rm.GetString("homePhoneLabel"));
            patientInfo.Append(" ");
            patientInfo.Append((requestInfo.RequestorHomePhone != null) ? requestInfo.RequestorHomePhone : string.Empty);
            phoneText.Text = patientInfo.ToString();

            patientInfo = new StringBuilder();
            patientInfo.Append((requestInfo.Id == 0)
                                   ? string.Empty
                                   : requestInfo.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + ", ");
            patientInfo.Append((requestInfo.Status == 0)
                                ? string.Empty
                                : requestInfo.Status.ToString() + " ");            
            patientInfo.Append(rm.GetString("status.columnHeader") + ", ");                        
            patientInfo.Append(rm.GetString("receiptDate" + RequestPrefix));
            patientInfo.Append(" ");
            patientInfo.Append((requestInfo.ReceiptDate.HasValue) ? requestInfo.ReceiptDate.Value.ToString(ROIConstants.DateTimeAMPMDesignateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) : string.Empty);
            requestText.Text = patientInfo.ToString();

            string requiredMessage = string.Empty;

            if (requestorDetails.PrepaymentRequired && requestorDetails.LetterRequired)
            {
                requiredMessage = rm.GetString("bothLetterRequired");
            }
            else
            {
                if (requestorDetails.PrepaymentRequired)
                {
                    requiredMessage = rm.GetString("paymentRequiredCheckBox");
                }
                if (requestorDetails.LetterRequired)
                {
                    requiredMessage = rm.GetString("letterRequiredCheckBox");
                }
            }
            requiredText.Text = requiredMessage;
        }

        #endregion
    }
}
