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

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// ROIImages
    /// </summary>
    public static class ROIImages
    {
        internal static Image UserDataImage = global::McK.EIG.ROI.Client.Resources.images.sample_user;
        internal static Image SeedDataImage = global::McK.EIG.ROI.Client.Resources.images.system_seed;
        internal static Image DefaultImage  = global::McK.EIG.ROI.Client.Resources.images.Default;

        internal static Image AlertIcon         = global::McK.EIG.ROI.Client.Resources.images.Alert;
        internal static Image ErrorIcon         = global::McK.EIG.ROI.Client.Resources.images.Error;
        internal static Image InfomationIcon    = global::McK.EIG.ROI.Client.Resources.images.Info;
        internal static Image InfoIcon          = global::McK.EIG.ROI.Client.Resources.images.info_icon;
        internal static Image DeleteButtonIcon  = global::McK.EIG.ROI.Client.Resources.images.deletePatient;

        //Find Patient
        internal static Image MoreInfoIcon        = global::McK.EIG.ROI.Client.Resources.images.more_info;
        internal static Image LockedPatientIcon   = global::McK.EIG.ROI.Client.Resources.images.locked_patient_icon;
        internal static Image VipIcon             = global::McK.EIG.ROI.Client.Resources.images.vip_patient_icon;
        internal static Image BillableIcon        = global::McK.EIG.ROI.Client.Resources.images.billable_icon;
        internal static Image SelectedBillableIcon = global::McK.EIG.ROI.Client.Resources.images.checkmark_white;
        

        //Patient Record Tree 
        internal static Image DeficiencyIcon     = global::McK.EIG.ROI.Client.Resources.images.deficiency_icon;
        internal static Image GlobalDocIcon      = global::McK.EIG.ROI.Client.Resources.images.global_docs_icon;
        internal static Image EncounterIcon      = global::McK.EIG.ROI.Client.Resources.images.encounter_icon;
        internal static Image NonHpfDocIcon      = global::McK.EIG.ROI.Client.Resources.images.supplementary_docs_icon;
        internal static Image DocumentIcon       = global::McK.EIG.ROI.Client.Resources.images.document_icon;
        internal static Image DocumentLockedIcon = global::McK.EIG.ROI.Client.Resources.images.doc_locked_icon;
        internal static Image DocumentVipIcon = global::McK.EIG.ROI.Client.Resources.images.doc_vip_icon;

        //Request
        internal static Image InUseIcon = global::McK.EIG.ROI.Client.Resources.images.in_use_icon;
        internal static Image LockedIcon = global::McK.EIG.ROI.Client.Resources.images.locked_icon;

        //Logon
        internal static Image LogonImage = global::McK.EIG.ROI.Client.Resources.images.logon_icon;
        internal static Image RightIcon = global::McK.EIG.ROI.Client.Resources.images.right_icon;

        internal static Icon DeleteIcon = global::McK.EIG.ROI.Client.Resources.images.del;
    }
}
