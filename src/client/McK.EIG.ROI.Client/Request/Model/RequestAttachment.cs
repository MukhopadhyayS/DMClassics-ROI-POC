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

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// RequestAttachment
    /// </summary>
    [Serializable]
    public class RequestAttachments : BaseRequestItem
    {
        #region Constructor

        public RequestAttachments() { }
        public RequestAttachments(RequestPatientDetails patient)
        {
            base.Parent = patient;
        }

        #endregion

        public int RetrieveNonPrintableCount()
        {
            int  count = 0;
            foreach (BaseRequestItem item in GetChildren)
            {
                RequestAttachmentEncounterDetails requestAttachmentEncounterDetails = (RequestAttachmentEncounterDetails)item;
                foreach (BaseRequestItem requestAttachmentDetails in requestAttachmentEncounterDetails.GetChildren)
                {
                    RequestAttachmentDetails requestAttachmentDet = (RequestAttachmentDetails)requestAttachmentDetails;
                    if (!requestAttachmentDet.IsPrintable)
                    {
                        if ((bool)requestAttachmentDet.SelectedForRelease)
                        {
                            count++;
                        }
                    }
                }
            }
            return count;
        }

        public void AddDocument(RequestAttachmentDetails attachment)
        {
            RequestAttachmentEncounterDetails encounter = RequestAttachmentEncounterDetails.CreateEncounter(attachment);

            if (attachment.Parent != null)
            {
                string exParentKey = attachment.Parent.Key;
                if (exParentKey != encounter.Key && GetChild(exParentKey) != null)
                {
                    GetChild(exParentKey).RemoveChild(attachment);
                }
            }

            BaseRequestItem child = GetChild(encounter.Key);
            if (child == null)
            {
                encounter.AddChild(attachment);
                AddChild(encounter);
                encounter.PageCount += attachment.PageCount;
            }
            else
            {
                child.AddChild(attachment);
                ((RequestAttachmentEncounterDetails)child).PageCount += attachment.PageCount;
                AddChild(child);
            }

        }


        #region Properties

        /// <summary>
        /// Holds name of RequestAttachment
        /// </summary>
        public override string Name
        {
            get { return ROIConstants.Attachment; }
        }

        /// <summary>
        /// Holds Key of RequestAttachment
        /// </summary>
        public override string Key
        {
            get { return Name; }
        }

        /// <summary>
        /// Gets the Icon
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get { return ROIImages.NonHpfDocIcon; }
        }


        #endregion
    }
}
