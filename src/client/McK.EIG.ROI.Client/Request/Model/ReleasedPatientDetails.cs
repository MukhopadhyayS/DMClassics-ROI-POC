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
using System.Collections.Generic;
using System.Globalization;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Class holds its own released items. 
    /// </summary>
    [Serializable]
    public sealed class ReleasedPatientDetails : RequestPatientDetails 
    {
        private class ReleasedPatientSorter : IComparer<ReleasedPatientDetails>
        {
            #region IComparer<ReleasedPatientDetails> Members

            public int Compare(ReleasedPatientDetails x, ReleasedPatientDetails y)
            {
                return x.SorterKey.CompareTo(y.SorterKey);
            }

            #endregion
        }

        private static ReleasedPatientSorter sorter;

        public new static IComparer<ReleasedPatientDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new ReleasedPatientSorter();
                }
                return sorter;
            }
        }

        #region Fields

        private Collection<ReleasedItemDetails> releaseItems;

        #endregion

        #region Constructor

        public ReleasedPatientDetails() { }

        #endregion

        #region Methods

        /// <summary>
        /// Converts the request patient to relased patient
        /// </summary>
        /// <param name="requestPatient"></param>
        /// <returns></returns>
        public ReleasedPatientDetails AssignRequestToReleasedPatient(RequestPatientDetails requestPatient)
        {
            ReleasedPatientDetails releasedPatient = new ReleasedPatientDetails();

            if (requestPatient.IsHpf)
            {
                releasedPatient.IsHpf = requestPatient.IsHpf;
                releasedPatient.MRN = requestPatient.MRN;
                releasedPatient.FacilityCode = requestPatient.FacilityCode;
                releasedPatient.FullName = requestPatient.FullName;
                releasedPatient.Gender = requestPatient.Gender;
                releasedPatient.EPN = requestPatient.EPN;
                releasedPatient.SSN = requestPatient.SSN;
                releasedPatient.IsVip = requestPatient.IsVip;
                releasedPatient.IsLockedPatient = requestPatient.IsLockedPatient;
                releasedPatient.EncounterLocked = requestPatient.EncounterLocked;
                if (requestPatient.DOB.HasValue)
                {
                    releasedPatient.DOB = requestPatient.DOB;
                }
                releasedPatient.Id = requestPatient.Id;

                foreach (RequestEncounterDetails encounter in requestPatient.GetChildren)
                {
                    releasedPatient.AddChild(encounter);
                }
                releasedPatient.IsReleased = false;
                foreach (RequestDocumentDetails globalDoc in requestPatient.GlobalDocument.GetChildren)
                {
                    releasedPatient.GlobalDocument.AddChild(globalDoc);
                }
                releasedPatient.GlobalDocument.IsReleased = false;
            }
            else
            {
                releasedPatient.IsHpf = requestPatient.IsHpf;
                releasedPatient.MRN = requestPatient.MRN;
                releasedPatient.FacilityCode = requestPatient.FacilityCode;
                releasedPatient.FullName = requestPatient.FullName;
                releasedPatient.Gender = requestPatient.Gender;
                releasedPatient.EPN = requestPatient.EPN;
                releasedPatient.SSN = requestPatient.SSN;
                releasedPatient.IsVip = requestPatient.IsVip;
                releasedPatient.Id = requestPatient.Id;

                if (releasedPatient.DOB.HasValue)
                {
                    releasedPatient.DOB = requestPatient.DOB;
                }
                releasedPatient.IsFreeformFacility = requestPatient.IsFreeformFacility;
            }

            foreach (RequestNonHpfEncounterDetails nonHpfDocumentEncounter in requestPatient.NonHpfDocument.GetChildren)
            {
                foreach (RequestNonHpfDocumentDetails nonHPFDocument in nonHpfDocumentEncounter.GetChildren)
                {
                    releasedPatient.NonHpfDocument.AddChild(nonHPFDocument);
                }
            }
            foreach (RequestAttachmentEncounterDetails requestAttachmentEncounter in requestPatient.Attachment.GetChildren)
            {
                foreach (RequestAttachmentDetails requestAttachment in requestAttachmentEncounter.GetChildren)
                {
                    releasedPatient.Attachment.AddChild(requestAttachment);
                }
            }

            releasedPatient.NonHpfDocument.IsReleased = false;
            releasedPatient.Attachment.IsReleased = false;

            //Set Release Status
            SetReleasedStatus(releasedPatient);
            SetReleasedStatus(releasedPatient.GlobalDocument);
            SetReleasedStatus(releasedPatient.NonHpfDocument);
            SetReleasedStatus(releasedPatient.Attachment);

            return releasedPatient;
        }

        private void SetReleasedStatus(BaseRequestItem item)
        {
            foreach (BaseRequestItem child in item.GetChildren)
            {
                child.IsReleased = child.IsReleased;
                SetReleasedStatus(child);
            }
        }        
        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the patient released item details.
        /// </summary>
        public Collection<ReleasedItemDetails> ReleaseItems
        {
            get 
            {
                if (releaseItems == null)
                {
                    releaseItems = new Collection<ReleasedItemDetails>();
                }
                return releaseItems;
            }           
        }

        #endregion
    }
}
