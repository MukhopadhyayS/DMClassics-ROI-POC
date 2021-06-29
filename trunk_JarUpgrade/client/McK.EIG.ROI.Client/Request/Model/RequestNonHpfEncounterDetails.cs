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
    public class RequestNonHpfEncounterDetails : BaseRequestItem
    {
        #region Fields

        private const string NonHpfEncounterElement = "<{0}>";
        
        private string encounter;
        private Nullable<DateTime> dateOfService;
        private string facility;
        private string department;
        private bool? selectedForRelease;
        
        private int pageCount;

        #endregion

        #region Constructors

        public RequestNonHpfEncounterDetails()
        {
            selectedForRelease = true;
        }

        public RequestNonHpfEncounterDetails(NonHpfEncounterDetails nonHpfEncounter)
        {
            encounter     = nonHpfEncounter.Encounter;
            facility      = nonHpfEncounter.Facility;
            department    = nonHpfEncounter.Department;
            dateOfService = nonHpfEncounter.DateOfService;

            selectedForRelease = true;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Methods is used to create a NonHpfEncounterDetails object by using NonHpf Document 
        /// </summary>
        /// <param name="doc"></param>
        /// <returns></returns>
        public static RequestNonHpfEncounterDetails CreateNonHpfEncounter(RequestNonHpfDocumentDetails doc)
        {
            RequestNonHpfEncounterDetails encounter = new RequestNonHpfEncounterDetails();
            encounter.Encounter = doc.Encounter;
            encounter.DateOfService = doc.DateOfService.Value;
            encounter.Facility = doc.Facility;
            encounter.Department = doc.Department;

            return encounter;
        }

        public override void RemoveChild(BaseRequestItem child)
        {
            if(typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(child.GetType()))
            {
                RequestNonHpfDocumentDetails nonHpfDoc = (RequestNonHpfDocumentDetails)child;
                ((RequestNonHpfEncounterDetails)nonHpfDoc.Parent).PageCount -= nonHpfDoc.PageCount;
                base.RemoveChild(child);
            }
        }

        private RequestNonHpfEncounterDetails Clone()
        {
            RequestNonHpfEncounterDetails encounter = new RequestNonHpfEncounterDetails();
            encounter.Encounter = this.encounter;
            encounter.Department = department;
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
        /// Holds data of service of Non-HPF document.
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

        /// <summary>
        /// /// <summary>
        /// Holds facility of Non-HPF document.
        /// </summary>
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds department of Non-HPF document.
        /// </summary>
        public string Department
        {
            get { return department; }
            set { department = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Gets or Sets the page count of Non-HPF document.
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Holds name of Non-HPF document.
        /// </summary>
        public override string Name
        {
            get 
            {
                if (string.IsNullOrEmpty(encounter))
                {
                    return NonHpfEncounterDetails.NotApplicable;
                }
                return encounter; 
            }
        }

        /// <summary>
        /// Holds unique key representaion of Non-HPF document.
        /// </summary>
        public override string Key
        {
            get
            {
                return ((dateOfService.HasValue) 
                        ?dateOfService.Value.ToString(ROIConstants.SorterDateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) 
                        : string.Empty) + "." + 
                        Name + "." + facility + "." + department; }
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
        public RequestNonHpfEncounterDetails ReleasedItems
        {
            get
            {
              
                List<BaseRequestItem> nonHpfDocuments = new List<BaseRequestItem>(GetChildren);
                nonHpfDocuments = nonHpfDocuments.FindAll(delegate(BaseRequestItem nonHpfDocument)
                                                          {
                                                              if (!nonHpfDocument.IsReleased && nonHpfDocument.SelectedForRelease.HasValue)
                                                              {
                                                                  nonHpfDocument.IsReleased = nonHpfDocument.SelectedForRelease.Value;
                                                              }

                                                              return (!nonHpfDocument.SelectedForRelease.HasValue || nonHpfDocument.SelectedForRelease.Value);
                                                          });



                RequestNonHpfEncounterDetails encounter = Clone();
                foreach (RequestNonHpfDocumentDetails document in nonHpfDocuments)
                {
                    encounter.AddChild(document);
                }

                return encounter;
            }
        }

        #endregion
    }
}
