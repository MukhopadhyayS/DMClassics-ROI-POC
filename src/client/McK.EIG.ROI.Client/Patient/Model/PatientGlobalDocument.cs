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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Patient.Model
{
    [Serializable]
    public class PatientGlobalDocument : BaseRecordItem
    {
        #region Fields

        private SortedList<DocumentType, DocumentDetails> documents;

        #endregion

        #region Constructor

        public PatientGlobalDocument()
        {
        }

        #endregion

        #region Methods

        public void AddDocument(DocumentDetails doc)
        {
            if (!Documents.ContainsKey(doc.DocumentType))
                this.Documents.Add(doc.DocumentType, doc);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Documents
        /// </summary>
        public SortedList<DocumentType, DocumentDetails> Documents
        {
            get
            {
                if (documents == null)
                    documents = new SortedList<DocumentType, DocumentDetails>(DocumentType.Sorter);
                return documents;
            }
        }

        /// <summary>
        /// Icon
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get
            {
                return ROIImages.GlobalDocIcon;
            }
        }

        /// <summary>
        /// Name
        /// </summary>
        public override string Name
        {
            get { return ROIConstants.GlobalDocument; }
        }

        /// <summary>
        /// Key
        /// </summary>
        public override string Key
        {
            get { return null; }
        }

        /// <summary>
        /// CompareProperty
        /// </summary>
        public override IComparable CompareProperty
        {
            get { return null; }
        }

        #endregion
    }
}
