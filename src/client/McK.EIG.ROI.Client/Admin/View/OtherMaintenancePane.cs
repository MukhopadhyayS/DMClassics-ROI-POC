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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Used to group collection of Navigation links.
    /// </summary>
    public class OtherMaintenancePane : NavigationPane
    {
        #region Methods

        protected override void ProcessNavigation(string selected, ApplicationEventArgs ae)
        {
            if (selected == ROIConstants.AdminOtherDisclosureDoc)
            {
                AdminEvents.OnNavigateDisclosureDocumentTypes(this, ae);
            }
            else if (selected == ROIConstants.AdminOtherLetterTemplate)
            {
                AdminEvents.OnNavigateLetterTemplate(this, ae);
            }
            else if (selected == ROIConstants.AdminOtherConfigureNotes)
            {
                AdminEvents.OnNavigateConfigureNotes(this, ae);
            }
            else if (selected == ROIConstants.AdminOtherSsnMasking)
            {
                AdminEvents.OnNavigateSsnMasking(this, ae);
            }
            else if (selected == ROIConstants.AdminConfigurationExternalSources)
            {
                AdminEvents.OnNavigateExternalSources(this, ae);
            }
            else if (selected == ROIConstants.AdminConfigurationTurnAroundTimeDays)
            {
                AdminEvents.OnNavigateTurnaroundTimeDays(this, ae);
            }
            else if (selected == ROIConstants.AdminOtherConfigureCountry)
            {
                AdminEvents.OnNavigateLocationCountry(this, ae);
            }
            else if (selected == ROIConstants.AdminOtherConfigureDefaultUnbillableRequest)
            {
                AdminEvents.OnNavigateConfigureUnbillableRequest(this, ae);
            }
        }

        #endregion

        #region Properties

        protected override Collection<string> LinkKeys
        {
            get
            {
                Collection<string> links = new Collection<string>();
                links.Add(ROIConstants.AdminOtherDisclosureDoc);
                links.Add(ROIConstants.AdminOtherLetterTemplate);
                links.Add(ROIConstants.AdminOtherConfigureNotes);
                links.Add(ROIConstants.AdminOtherSsnMasking);
                links.Add(ROIConstants.AdminConfigurationExternalSources);                
                //ROI16.0 zipcode
                links.Add(ROIConstants.AdminOtherConfigureCountry);
                //links.Add(ROIConstants.AdminConfigurationTurnAroundTimeDays);
                links.Add(ROIConstants.AdminOtherConfigureDefaultUnbillableRequest);
                return links;
            }
        }

        public override string Title
        {
            get { return ROIConstants.AdminOthers; }
        }

        #endregion
    }
}
