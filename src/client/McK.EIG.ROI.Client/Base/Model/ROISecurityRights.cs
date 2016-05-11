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

namespace McK.EIG.ROI.Client.Base.Model
{
    public class ROISecurityRights
    {
        //Security Ids
        public const string MasterPatientIndexSearch   = "200";
      //public const string RecordViews                = "925";
        public const string AccessLockedRecords        = "205";
        public const string ViewPatientDocuments       = "4140";
        public const string ViewVersionedDocuments     = "4150";
        public const string ROIAdministration          = "6101";
        public const string ROIAccessApplication       = "6102";
        public const string ROICreateRequest           = "6103";
        public const string ROIModifyRequest           = "6104";
        public const string ROIDeleteRequest           = "6105";
        public const string ROIVipStatus               = "6106";
        public const string ROICancelRequest           = "6107";
        public const string ROIPendingRequest          = "6108";
        public const string ROIDenyRequest             = "6109";
        public const string ROIAdjustCharges           = "6110";
        public const string ROIReports                 = "6111";
        public const string ROIPrintFax                = "6112";
        public const string ROIExportToPDF             = "6113";
        public const string ROIPostPayment             = "6114";
        public const string ROIViewRequest             = "6115";
        public const string ROIEmail                   = "6117";
        public const string ROIReleaseToDisc           = "6118";

        public const string EncounterCustomInfo            = "2300";
        public const string EncounterCustomInfoMaintenance = "2301";
        

        public const string DefaultFacility            = "E_P_R_S"; 
    }
}
