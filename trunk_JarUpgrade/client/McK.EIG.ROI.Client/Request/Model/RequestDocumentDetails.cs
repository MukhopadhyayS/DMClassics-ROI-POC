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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.Common.Utility.Logging;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Represents document details.
    /// </summary>
    [Serializable]
    public class RequestDocumentDetails : BaseRequestItem
    {

        [Serializable]
        private class RequestDocumentDetailsSorter : IComparer<string>
        {
            public int Compare(string x, string y)
            {
                return new StringLogicalComparer().Compare(x, y);
            }
        }

        #region Fields

        //document
        private const string TypeKey = "type";
        private const string SubTitleKey = "subtitle";
        private const string ChartOrderKey = "chart-order";
        private const string DocIdKey = "doc-id";
        private const string DocumentIdAttributeKey = "value";
        private const string DateOfServiceKey = "date-of-service";
        private const string DocumentDateTimeKey = "date-time"; //CR#365589
        private const string DocumentElement = "<{0} {1}=\"{2}\">";
        private const string DocIdElement = "<{0} {1}=\"{2}\">{3}</{4}>";

        private string docType;        
        private string subTitle;
        private string chartOrder;
        private string outputKey; //CR#365589
        private Nullable<DateTime> dateOfService;
        //Holds doctype id
        private long docTypeId;
        //Holds document id
        private long documentId;
        private long patientSeq; //To use the patient sequence for global documents.
        private long encounterSeq;
        private long documentSeq;
        private bool isDeficient;
        private bool isGlobalDocument;
        private List<RequestVersionDetails> requestVersionDetails;
        //CR#365589
        private Nullable<DateTime> documentDateTime;
        private string mrn;
        private string encounter;
        private string facility;
        #endregion

        #region Constructor

        public RequestDocumentDetails() { }

        public RequestDocumentDetails(DocumentDetails document)
        {
            docType     = document.DocumentType.Name;
            subTitle    = document.DocumentType.Subtitle;
            docTypeId   = document.DocumentType.Id;
            documentId  = document.DocumentId;   
            chartOrder  = document.DocumentType.ChartOrder;
            documentDateTime = document.DocumentDateTime; //CR#365589
            if (document.DocumentType.DateOfService.HasValue)
            {
                dateOfService = document.DocumentType.DateOfService.Value;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or Sets Doctype value
        /// </summary>
        public string DocType
        {
            get { return docType; }
            set { docType = value; }
        }

        /// <summary>
        /// Gets or Sets DocType Id
        /// </summary>
        public long DocTypeId
        {
            get { return docTypeId; }
            set { docTypeId = value; }
        }

        /// <summary>
        /// Gets or Sets Document Id
        /// </summary>
        public long DocumentId
        {
            get { return documentId; }
            set { documentId = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient sequence of the global documents
        /// </summary>
        public long PatientSeq
        {
            get { return patientSeq; }
            set { patientSeq = value; }
        }

        /// <summary>
        /// Gets or Sets encounter sequenece
        /// </summary>
        public long EncounterSeq
        {
            get { return encounterSeq; }
            set { encounterSeq = value; }
        }

        /// <summary>
        /// Gets or Sets Document Id
        /// </summary>
        public long DocumentSeq
        {
            get { return documentSeq; }
            set { documentSeq = value; }
        }

        /// <summary>
        /// Gets or Sets chart order
        /// </summary>
        public string ChartOrder
        {
            get { return chartOrder; }
            set { chartOrder = value; }
        }

        /// <summary>
        /// Gets or Sets Subtitle
        /// </summary>
        public string Subtitle
        {
            get { return subTitle; }
            set { subTitle = value; }
        }

        /// <summary>
        /// Gets or Sets IsDeficient Value.
        /// </summary>
        public bool IsDeficient
        {
            get { return isDeficient;}
            set { isDeficient = value; }
        }

        /// <summary>
        /// Gets or Sets IsGlobalDocument
        /// </summary>
        public bool IsGlobalDocument
        {
            get { return isGlobalDocument; }
            set { isGlobalDocument = value; }
        }        
        
        /// <summary>
        /// CR#365589 - Holds document created DateTime
        /// </summary>
        public Nullable<DateTime> DocumentDateTime
        {
            get { return documentDateTime; }
            set { documentDateTime = value; }
        }

        /// <summary>
        /// Holds patient admit date.
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

		/// <summary>
        /// Holds patient Mrn.
        /// </summary>
        public string Mrn
        {
            get { return mrn; }
            set { mrn = value; }
        }
		
		/// <summary>
        /// Holds encounter.
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

		/// <summary>
        /// Holds facility.
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }

        /// <summary>
        /// Gets the Icon
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get { return ROIImages.DocumentIcon; }
        }

        /// <summary>
        /// Gets the Name
        /// </summary>
        public override string Name
        {
            get { return docType; }
        }

        /// <summary>
        /// Gets the key.
        /// </summary>
        public override string Key
        {
            get 
            {
                string key = ChartOrder + "." + DocTypeId + "." + DocType;
                if (!string.IsNullOrEmpty(Subtitle))
                {
                    if (Subtitle.Length > 0)
                    {
                        key = key + "." + Subtitle;
                    }
                }
                if (DateOfService.HasValue)
                {
                    key = key + "." + DateOfService.Value;
                }
                key = key + "." + DocumentId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                return key;
            }
        }

        /// <summary>
        /// CR#365588 - Returns the output key
        /// </summary>
        public string OutputKey
        {
            get
            {
                outputKey = Convert.ToString(DocumentId, System.Threading.Thread.CurrentThread.CurrentUICulture) + "-" + Convert.ToString(DocTypeId, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (!string.IsNullOrEmpty(ChartOrder))
                {
                    outputKey += "-" + ChartOrder;
                }
                return outputKey;
            }
            set
            {
                outputKey = value;
            }
        }

        /// <summary>
        /// Returns only released document
        /// </summary>
        public RequestDocumentDetails ReleasedItems
        {
            get
            {
                List<BaseRequestItem> versions = new List<BaseRequestItem>(GetChildren);
                versions = versions.FindAll(delegate(BaseRequestItem version) { return (!version.SelectedForRelease.HasValue || version.SelectedForRelease.Value); });
                //RequestDocumentDetails document = (RequestDocumentDetails)ROIViewUtility.DeepClone(this);
                RequestDocumentDetails document = Clone();
                foreach (RequestVersionDetails version in versions)
                {
                    document.AddChild(version.ReleasedItems);   
                }
                return document;
            }
        }

        private RequestDocumentDetails Clone()
        {
            RequestDocumentDetails document = new RequestDocumentDetails();
            document.DocTypeId              = docTypeId;
            document.DateOfService      = dateOfService;
            document.DocType            = docType;
            document.ChartOrder         = chartOrder;
            document.IsDeficient        = isDeficient;
            document.Subtitle           = subTitle;
            document.DocumentId         = documentId;//CR#365589
            document.OutputKey          = OutputKey;//CR#365589
            document.DocumentDateTime   = documentDateTime;//CR#365589
            document.RecordVersionId    = RecordVersionId;
            document.SelectedForRelease = SelectedForRelease;
            document.IsGlobalDocument   = IsGlobalDocument;
            document.PatientSeq = PatientSeq;           

            return document;
        }

        public override IComparer<string> DefaultSorter
        {
            get
            {
                return RequestVersionDetails.CustomSorter;
            }
        }

        private static IComparer<string> sorter;
        public static IComparer<string> CustomSorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new RequestDocumentDetailsSorter();
                }
                return sorter;
            }
        }

        #endregion
    }
}
