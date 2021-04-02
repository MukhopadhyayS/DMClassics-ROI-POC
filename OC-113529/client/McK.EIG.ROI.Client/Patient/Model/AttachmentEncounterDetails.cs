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

namespace McK.EIG.ROI.Client.Patient.Model
{
    /// <summary>
    /// AttachmentEncounterDetails
    /// </summary>
    [Serializable]
    public class AttachmentEncounterDetails : BaseRecordItem
    {

        #region Sorter
        [Serializable]
        private class AttachmentEncounterSorter : IComparer<string>
        {
            public virtual int Compare(string x, string y)
            {
                return -1 * x.CompareTo(y);
            }
        }

        #endregion

        #region Fields

        public const string NotApplicable = "N/A";
        private const string AttachmentEncounterElement = "<{0}>";

        private string encounter;
        private Nullable<DateTime> dateOfService;
        private string facility;
        private int pageCount;

        #endregion

        #region Methods

        /// <summary>
        /// Adds AttachmentDetails object as child
        /// </summary>
        /// <param name="key"></param>
        /// <param name="child"></param>
        public override void AddChild(string key, BaseRecordItem child)
        {

            List<string> keys = new List<string>(ChildrenKeys);
            string childKey = keys.Find(delegate(string itemKey)
            {
                return itemKey.Substring(itemKey.IndexOf(ROIConstants.Delimiter) + 1) ==
                       key.Substring(key.IndexOf(ROIConstants.Delimiter) + 1);
            });

            if (childKey != null)
            {
                RemoveChild(childKey);
            }

            base.AddChild(key, child);
        }

        /// <summary>
        /// Methods is used to create a AttachmentEncounterDetails object by using Attachment 
        /// </summary>
        /// <param name="doc"></param>
        /// <returns></returns>
        public static AttachmentEncounterDetails CreateEncounter(AttachmentDetails attachment)
        {
            AttachmentEncounterDetails encounter = new AttachmentEncounterDetails();
            encounter.Encounter = attachment.Encounter;
            encounter.DateOfService = attachment.DateOfService;
            encounter.Facility = attachment.FacilityCode;

            return encounter;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the encounter value of attachment
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

        /// <summary>
        /// Gets or sets the DateOfService value
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

        /// <summary>
        /// Gets or sets the facility 
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }


        /// <summary>
        /// Gets or sets the Page Count 
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        #endregion

        private static IComparer<string> encounterSorter;
        public static IComparer<string> EncounterSorter
        {
            get
            {
                if (encounterSorter == null)
                {
                    encounterSorter = new AttachmentEncounterSorter();
                }
                return encounterSorter;
            }
        }

        public override IComparer<string> DefaultSorter
        {
            get { return AttachmentDetails.Sorter; }
        }

        public override string Key
        {
            get
            {
                return ((dateOfService.HasValue)
                        ? dateOfService.Value.ToString(ROIConstants.SorterDateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture)
                        : string.Empty) +
                        "." + Name + "." + facility ;
            }
        }

        public override string Name
        {
            get
            {
                if (string.IsNullOrEmpty(encounter))
                {
                    return NotApplicable;
                }

                return encounter;
            }
        }

        public override IComparable CompareProperty
        {
            get { return encounter; }
        }

        public override System.Drawing.Image Icon
        {
            get { return ROIImages.EncounterIcon; }
        }
    }
}
