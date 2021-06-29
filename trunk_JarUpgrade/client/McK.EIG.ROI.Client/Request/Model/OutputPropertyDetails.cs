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

namespace McK.EIG.ROI.Client.Request.Model
{
    public enum DestinationType
    {
        Print,
        Fax,
        File,
        Email,
        Disc
    }

    public class OutputPropertyDetails
    {  
        #region Fields

        private Collection<OutputDestinationDetails> outputDestinationDetails;
        private OutputViewDetails outputViewDetails;
        private string mediaType;

        public const string HeaderKey        = "Header";
        public const string FooterKey        = "Footer";
        public const string WatermarkKey     = "WATERMARK";
        public const string PageSeparatorKey = "PAGE_SEPARATOR";

        public const string HpfContentSource = "MPF";
        public const string ROIContentSource = "ROI";
        public const string ResendNonMpfAttachmentContentSource = "Resend_NonMPFAttachment";
        public const string ResendMPFAttachmentContentSource = "Resend_MPFAttachment";
        public const string ResendMPFContentSource = "Resend_MPF";
        public const string ReleaseContentSource = "RELEASE";
        public const string AttachmentContentSource = "ATTACHMENT";
        public const string RequestContentSource = "Request";

        public const string HeaderFooterPatientName = "hfpn";
        public const string HeaderFooterMrn = "hfm";
        public const string HeaderFooterEncounter = "hfe";

        //CR#354133	The ROI admin should be able to configure each of the fields
        //( MRN, Patient Name and Encounter) on either header or footer

        public const string HeaderChecked = "hc";
        public const string FooterChecked = "fc";

        #endregion

        #region Properties

        public Collection<OutputDestinationDetails> OutputDestinationDetails
        {
            get
            {
                if (outputDestinationDetails == null)
                {
                    outputDestinationDetails = new Collection<OutputDestinationDetails>();                         
                }
                return outputDestinationDetails;
            }
        }

        public OutputViewDetails OutputViewDetails
        {
            get { return outputViewDetails; }
            set { outputViewDetails = value; }
        }

        public string MediaType
        {
            get { return mediaType; }
            set { mediaType = value; }
        }

        #endregion
    }
}
