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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;

namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    public class PatientInfoEditor : ROIEditor
    {
        #region Fields
        
        private PatientDetails patient;
        
        #endregion

        #region Constructor

        public PatientInfoEditor() {}

        #endregion

        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();
            ((UpperComponent)hOuterSplitContainer.Panel1.Controls[0]).MCPPanel.AutoScroll = false;
            PatientInfoUI patientInfoUI = (PatientInfoUI)SubPanes[1].View;
            patientInfoUI.AddPersonalInfoPanel(patient != null && patient.IsHpf);
        }

        public override void PrePopulate()
        {
            PatientInfoMCP patientInfoMCP = (PatientInfoMCP)MCP;
            patientInfoMCP.PrePopulate();
        }

        /// <summary>
        /// Sets the data into view.
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            MCP.SetData(data);
        }

        public override void Cleanup()
        {
            base.Cleanup();
            base.View.Dispose();
            // nothing to unsubscribe and view will be destroyed when Editor is recreated
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the Patient Information Editor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "patientInfo.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the Patient Information Editor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "patientInfo.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(PatientInfoMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return null; }
        }

        public PatientDetails Patient
        {
            get { return patient; }
            set { patient = value; }
        }

        #endregion
    }
}
