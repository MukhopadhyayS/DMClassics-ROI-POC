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
    /// RequestNonHpfDocuments
    /// </summary>
    [Serializable]
    public class RequestNonHpfDocuments : BaseRequestItem
    {
        #region Constructor

        public RequestNonHpfDocuments() {}
        public RequestNonHpfDocuments(RequestPatientDetails patient) 
        {
            base.Parent = patient;
        }

        #endregion

        public void AddDocument(RequestNonHpfDocumentDetails document)
        {
            RequestNonHpfEncounterDetails encounter = RequestNonHpfEncounterDetails.CreateNonHpfEncounter(document);

            if (document.Parent != null)
            {
                string exParentKey = document.Parent.Key;
                if (exParentKey != encounter.Key && GetChild(exParentKey) != null)
                {
                    GetChild(exParentKey).RemoveChild(document);
                }
            }

            BaseRequestItem child = GetChild(encounter.Key);
            if (child == null)
            {
                encounter.AddChild(document);
                AddChild(encounter);
                encounter.PageCount += document.PageCount;
            }
            else
            {
                child.AddChild(document);
                ((RequestNonHpfEncounterDetails)child).PageCount += document.PageCount;
                AddChild(child);
            }

        }


        #region Properties

        /// <summary>
        /// Holds name of RequestNonHpfDocuments
        /// </summary>
        public override string Name
        {
            get { return ROIConstants.NonHpfDocument; }
        }

        /// <summary>
        /// Holds Key of RequestNonHpfDocuments
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
