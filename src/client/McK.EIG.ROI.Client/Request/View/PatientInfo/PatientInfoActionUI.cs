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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    public partial class PatientInfoActionUI : ROIBaseUI 
    {
        public PatientInfoActionUI()
        {
            InitializeComponent();
        }

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, revertButton);
            SetLabel(rm, saveButton);
            SetLabel(rm, saveBillButton);
            SetLabel(rm, viewAuthRequestButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, revertButton);
            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, saveBillButton);
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


        public void LocalizeUIForNewRequest()
        {
             ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
             revertButton.Text = rm.GetString("cancelButton");

             rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
             toolTip.SetToolTip(revertButton, rm.GetString("cancelButton.RequestPatientInfoMCP"));
        }

        #region Properties

        public Button SaveButton
        {
            get { return saveButton; }
        }

        public Button RevertButton
        {
            get { return revertButton; }
        }

        public Button SaveBillButton
        {
            get { return saveBillButton; }
        }

        public Button ViewAuthRequestButton
        {
            get { return viewAuthRequestButton; }
        }

        public PictureBox RequestLockedImage
        {
            get { return requestLockedImg; }
        }

        #endregion
      
    }
}
