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
using System.Collections.Generic;
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;


namespace McK.EIG.ROI.Client.Request.Model
{
    [Serializable]
    public class RequestAttachmentEncounterDetails : BaseRequestItem
    {
        #region Fields

        private string encounter;
        private Nullable<DateTime> dateOfService;
        private string facility;
        private bool? selectedForRelease;

        private int pageCount;
        private string uuid;
        private string volume;
        private string path;

        #endregion

        #region Constructors

        public RequestAttachmentEncounterDetails()
        {
            selectedForRelease = true;
        }

        public RequestAttachmentEncounterDetails(AttachmentEncounterDetails attachmentEncounter)
        {
            encounter = attachmentEncounter.Encounter;
            facility = attachmentEncounter.Facility;
            dateOfService = attachmentEncounter.DateOfService;

            selectedForRelease = true;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Methods is used to create an AttachmentEncounterDetails object by using Attachment 
        /// </summary>
        /// <param name="doc"></param>
        /// <returns></returns>
        public static RequestAttachmentEncounterDetails CreateEncounter(RequestAttachmentDetails tmpAttachment)
        {
            RequestAttachmentEncounterDetails encounter = new RequestAttachmentEncounterDetails();
            encounter.Encounter = tmpAttachment.Encounter;
            if (tmpAttachment.DateOfService.HasValue)
                encounter.DateOfService = tmpAttachment.DateOfService.Value;
            encounter.Facility = tmpAttachment.Facility;
            encounter.Uuid = tmpAttachment.Uuid;
            encounter.Volume = tmpAttachment.Volume;
            encounter.Path = tmpAttachment.Path;
            

            return encounter;
        }

        public override void RemoveChild(BaseRequestItem child)
        {
            if (typeof(RequestAttachmentDetails).IsAssignableFrom(child.GetType()))
            {
                RequestAttachmentDetails tmpAttachment = (RequestAttachmentDetails)child;
                ((RequestAttachmentEncounterDetails)tmpAttachment.Parent).PageCount -= tmpAttachment.PageCount;
                base.RemoveChild(child);
            }
        }

        private RequestAttachmentEncounterDetails Clone()
        {
            RequestAttachmentEncounterDetails encounter = new RequestAttachmentEncounterDetails();
            encounter.Encounter = this.encounter;
            encounter.Facility = facility;
            encounter.DateOfService = dateOfService;
            encounter.SelectedForRelease = SelectedForRelease;

            return encounter;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds encounter name of Non-HPF document.
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = (value == null) ? string.Empty : value; ; }
        }

        /// <summary>
        /// Holds data of service 
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

        /// <summary>
        /// /// <summary>
        /// Holds facility 
        /// </summary>
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = (value == null) ? string.Empty : value; }
        }


        /// <summary>
        /// Gets or Sets the page count 
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Holds name 
        /// </summary>
        public override string Name
        {
            get
            {
                if (string.IsNullOrEmpty(encounter))
                {
                    return AttachmentEncounterDetails.NotApplicable;
                }
                return encounter;
            }
        }

        /// <summary>
        /// Holds unique key representaion 
        /// </summary>
        public override string Key
        {
            get
            {
                return ((dateOfService.HasValue)
                        ? dateOfService.Value.ToString(ROIConstants.SorterDateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture)
                        : string.Empty) + "." +
                        Name + "." + facility;
            }
        }

        /// <summary>
        /// Gets the Icon
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get { return ROIImages.EncounterIcon; }
        }

        /// <summary>
        /// Returns only relased patient
        /// </summary>
        public RequestAttachmentEncounterDetails ReleasedItems
        {
            get
            {

                List<BaseRequestItem> attachmentList = new List<BaseRequestItem>(GetChildren);
                attachmentList = attachmentList.FindAll(delegate(BaseRequestItem tmpAttachment)
                {
                    if (!tmpAttachment.IsReleased && tmpAttachment.SelectedForRelease.HasValue)
                    {
                        tmpAttachment.IsReleased = tmpAttachment.SelectedForRelease.Value;
                    }

                    return (!tmpAttachment.SelectedForRelease.HasValue || tmpAttachment.SelectedForRelease.Value);
                });



                RequestAttachmentEncounterDetails encounter = Clone();
                foreach (RequestAttachmentDetails attachment in attachmentList)
                {
                    encounter.AddChild(attachment);
                }

                return encounter;
            }
        }

        public string Uuid
        {
            get { return uuid; }
            set { uuid = value; }
        }

        public string Volume
        {
            get { return volume; }
            set { volume = value; }
        }

        public string Path
        {
            get { return path; }
            set { path = value; }
        }

        #endregion
    }
}
