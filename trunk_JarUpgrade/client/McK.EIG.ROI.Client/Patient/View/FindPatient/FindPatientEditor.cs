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
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Patient.View.FindPatient
{
    /// <summary>
    ///  Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class FindPatientEditor : ROIEditor
    {
        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();            
            base.hOuterSplitContainer.SplitterDistance = 42;            
            
            //Apply securtiy rights
            ((Control)View).Enabled = ROIViewUtility.IsAllowed(ROISecurityRights.MasterPatientIndexSearch);
        }
        
        public override void Cleanup()
        {
            // do nothing since this instance need to reused.
        }

        /// <summary>
        /// PrePopulate the facility in PatientSearchUI.
        /// </summary>
        public override void PrePopulate()
        {
            try
            {
                ROIController.Instance.RetrieveFreeformFacilities();
                ((PatientSearchUI)MCP.View).PrePopulate();
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the Find Patient.
        /// </summary>
        protected override string TitleText
        {
            get { return "findPatient.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the Find Patient.
        /// </summary>
        protected override string InfoText
        {
            get { return "findPatient.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(FindPatientMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        protected override Type ODPType
        {
            get { return typeof(FindPatientODP); }
        }

        public PatientDetails SelectedPatientInfo
        {
            get { return (ODP.View as PatientListUI).SelectedPatientInfo; }
        }

        public FindPatientCriteria SearchCriteria
        {
            get { return (MCP.View as PatientSearchUI).SearchCriteria; } 
        }

        #endregion
    }
}
