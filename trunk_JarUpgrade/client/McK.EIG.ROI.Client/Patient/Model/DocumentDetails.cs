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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Patient.Model
{
        /// <summary>
        /// Represents document details.
        /// </summary>
        [Serializable]
        public class DocumentDetails : BaseRecordItem
        {
            #region Fields

            private long documentId;
            private DocumentType docType;
            private SortedList<int, VersionDetails> versions;
            private Nullable<DateTime> documentDateTime;

            #endregion

            #region Constructor

            public DocumentDetails()
            {
            }

            #endregion

            #region Properties

            public override IComparable CompareProperty
            {
                get { return docType.Name; }
            }

            /// <summary>
            /// Holds collection of versions under a document.
            /// </summary>
            public SortedList<int, VersionDetails> Versions
            {
                get
                {
                    if (versions == null)
                    {
                        versions = new SortedList<int, VersionDetails>(VersionDetails.CustomSorter);
                    }

                    return versions;
                }
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
            /// Holds document id.
            /// </summary>
            public long DocumentId
            {
                get { return documentId; }
                set { documentId = value; }
            }

            /// <summary>
            /// Holds Documenttype for a document
            /// </summary>
            public DocumentType DocumentType
            {
                get { return docType; }
                set { docType = value; }
            }

            public override System.Drawing.Image Icon
            {
                get
                {
                    return ROIImages.DocumentIcon;
                }
            }

            public override string Name
            {
                get
                {
                    return docType.Name;
                }
            }

            public override string Key
            {
                get { return docType.Key; }
            }

            #endregion
        }
  }
