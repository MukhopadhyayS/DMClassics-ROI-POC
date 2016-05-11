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
using System.Collections.ObjectModel;
using System.Globalization;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.Model
{
    [Serializable]
    public class DocumentType
    {
        [Serializable]
        private class DocumentTypeSorter : IComparer<DocumentType>
        {
            #region IComparer<DocumentType> Members

            public int Compare(DocumentType x, DocumentType y)
            {
                return new StringLogicalComparer().Compare(x.DefaultSortOrder, y.DefaultSortOrder);
                //return x.DefaultSortOrder.CompareTo(y.DefaultSortOrder);
            }

            #endregion
        }

        #region Fields

        private long id;
        private string name;
        private string subTitle;
        private long docId;
        private string documentdate;
        private Nullable<DateTime> dateOfService;
        private string chartOrder;
        private bool isDisclosure;        

        private static SortedList<string, DocumentType> docTypes;
        private static DocumentTypeSorter sorter;
        #endregion

        #region Methods

        public override bool Equals(object obj)
        {
            return (object.ReferenceEquals(this, obj) || ((DocumentType)obj).Key == Key);
        }

        public override int GetHashCode()
        {
            return Key.GetHashCode();
        }

        /// <summary>
        /// Creates a new document type.
        /// </summary>
        /// <param name="docType"></param>
        /// <returns></returns>
        public static DocumentType CreateDocType(DocumentType docType)
        {
            return GetDocType(docType.Key);
        }


        /// <summary>
        /// This factory method for DocumentType objects.
        /// </summary>
        /// <param name="id"></param>
        /// <param name="name"></param>
        /// <param name="subtitle"></param>
        /// <param name="dateTime"></param>
        /// <param name="chartOrder"></param>
        /// <returns></returns>
        public static DocumentType CreateDocType(long id, string name, string subtitle, string dateTime, string chartOrder, bool isDisclosure, long docId)
        {
            if (docTypes == null)
            {
                docTypes = new SortedList<string, DocumentType>();
            }

            string key = chartOrder + "." + id.ToString(System.Threading.Thread.CurrentThread.CurrentCulture) + "." + name.Trim();
            if (!string.IsNullOrEmpty(subtitle))
            {
                if (subtitle.Trim().Length > 0)
                {
                    key = key + "." + subtitle.Trim();
                }
            }
            if (!string.IsNullOrEmpty(dateTime))
            {
                DateTime date = Convert.ToDateTime(dateTime, System.Threading.Thread.CurrentThread.CurrentCulture);
                if (dateTime.Trim().Length > 0)
                {
                    key = key + "." + date.ToString().Trim();
                }
            }
            key = key + "." + docId.ToString(System.Threading.Thread.CurrentThread.CurrentCulture);

            DocumentType docType = GetDocType(key);

            if (docType == null)
            {
                docType = new DocumentType();

                docType.Id = id;
                docType.DocumentId = docId;
                docType.name = name;
                docType.Subtitle = subtitle;
                docType.ChartOrder = chartOrder;
                docType.IsDisclosure = isDisclosure;

                if (dateTime != null)
                {
                    docType.DocumentDate = dateTime;
                    docType.dateOfService = Convert.ToDateTime(dateTime, System.Threading.Thread.CurrentThread.CurrentCulture);
                }

                docTypes.Add(docType.Key, docType);
            }
            else
            {
                docType.IsDisclosure = isDisclosure;
            }
            return docType;
        }

        /// <summary>
        /// Gets existing doc type from the list
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public static DocumentType GetDocType(string key)
        {
            return docTypes.ContainsKey(key) ? docTypes[key] : null;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds doctype id.
        /// </summary>
        public long Id
        {
            get { return id; }
            set { id = value; }
        }

        public long DocumentId
        {
            get { return docId; }
            set { docId = value; }
        }

        public string DocumentDate
        {
            get { return documentdate; }
            set { documentdate = value; }
        }

        /// <summary>
        /// Holds document name.
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds document sub title.
        /// </summary>
        public string Subtitle
        {
            get { return subTitle; }
            set { subTitle = (value == null) ? string.Empty : value.Trim(); }
        }

        /// <summary>
        /// Holds document date of service
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

        /// <summary>
        /// Holds document chart order id.
        /// </summary>
        public string ChartOrder
        {
            get { return chartOrder; }
            set { chartOrder = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds document Disclosure state.
        /// </summary>
        public bool IsDisclosure
        {
            get { return isDisclosure; }
            set { isDisclosure = value; }
        }

        public string DefaultSortOrder
        {
            get { return Key; }
        }

        public string Key
        {
            get 
            {
                string key = ChartOrder + "." + Id.ToString(System.Threading.Thread.CurrentThread.CurrentCulture) + "." + Name.Trim();
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
                key = key + "." + DocumentId.ToString(System.Threading.Thread.CurrentThread.CurrentCulture);

                return key;
            }
        }

        public static System.Drawing.Image Icon
        {
            get { return ROIImages.DocumentIcon; }
        }

        public static IComparer<DocumentType> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new DocumentTypeSorter();
                }
                return sorter;
            }
        }

        #endregion
    }
}
