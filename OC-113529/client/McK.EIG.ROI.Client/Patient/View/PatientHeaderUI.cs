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
using System.Resources;
using System.Globalization;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Patient.View
{
    public partial class PatientHeaderUI : UserControl
    {
        //private bool IsEPNEnabled;

        #region Constructor

        public PatientHeaderUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        private void SetLabel(ResourceManager rm, Control control)
        {
            if (control == patientNameLabel)
            {
                control.Text = rm.GetString(control.Name + "." + GetType().Name);
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
            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, patientNameLabel);
            SetLabel(rm, genderLabel);
            SetLabel(rm, dobShortLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, ssnLabel);
            SetLabel(rm, mrnLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, vipLabel);
        }

        public void PopulatePatientInformation(PatientDetails patientInfo)
        {            
            //IsEPNEnabled = UserData.Instance.EpnEnabled;
            nameText.Text = patientInfo.FullName;
            //genderText.Text = patientInfo.Gender.ToString();
            //SOGI OC-111171
            genderText.Text = patientInfo.GenderDesc;
            if (patientInfo.DOB.HasValue)
            {
                dobText.Text = patientInfo.DOB.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            
            facilityText.Text = ROIViewUtility.ReplaceAmpersandForLabel(patientInfo.FacilityCode);
            ssnText.Text = ROIViewUtility.ApplyMask(patientInfo.SSN);
            mrnText.Text = patientInfo.MRN;
            epnText.Text = patientInfo.EPN;
            vipLabel.Visible = patientInfo.IsVip;
            //Added for CR# 337443
            epnLabel.Visible = UserData.Instance.EpnEnabled;
            epnText.Visible = UserData.Instance.EpnEnabled;            
            //-----------------
            facilityLabel.Visible = true;
            facilityText.Visible = true;
            mrnLabel.Visible = true;
            mrnText.Visible = true;
            SetTooltip(patientInfo);
        }

        /// <summary>
        /// Set the tool tip for the labels.
        /// </summary>
        /// <param name="patientInfo"></param>
        private void SetTooltip(PatientDetails patientInfo)
        {
            toolTip.SetToolTip(nameText, patientInfo.Name);
            //toolTip.SetToolTip(genderText, patientInfo.Gender.ToString());
            //SOGI OC-111171
            toolTip.SetToolTip(genderText, patientInfo.GenderDesc);
            toolTip.SetToolTip(facilityText, patientInfo.FacilityCode);
            toolTip.SetToolTip(ssnText, ROIViewUtility.ApplyMask(patientInfo.SSN));
            toolTip.SetToolTip(mrnText, patientInfo.MRN);
            toolTip.SetToolTip(epnText, patientInfo.EPN);
            if (patientInfo.DOB.HasValue)
            {
                toolTip.SetToolTip(dobText, patientInfo.DOB.Value.ToShortDateString());
            }
        }

        #endregion

    }
}
