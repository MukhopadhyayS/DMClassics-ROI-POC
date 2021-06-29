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

namespace McK.EIG.ROI.Client.Admin.Model
{

    public enum DocumentDesignationType
    {
        Disclosure,
        Authorize,
        mu,
        Both,
        None
    }

    /// <summary>
    /// This Class is used to hold DocumentType info
    /// </summary>
    [Serializable]
    public class DocumentTypesDetails : ROIModel
    {
        #region Fields

        private long id;
        private string name;
        private DocumentDesignationType type;
        private long codeSetId;
        private bool isdisclosure;
        private bool isAuthorization;
        private bool isMUDocumentType;
        private string MUDocumenttype;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new DocTypesDetails instance
        /// </summary>
        public DocumentTypesDetails()
        {

        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the DocTypes Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to gets the name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// This property is used to gets the type
        /// </summary>
        public DocumentDesignationType Type
        {
            get { return type; }
            set { type = value; }
        }

        /// <summary>
        /// This Property is used to get whether the document is Isdisclosure.
        /// </summary>
        public bool IsDisclosure
        {
            get { return isdisclosure; }
            set { isdisclosure = value; }
        }

        /// <summary>
        /// This Property is used to get whether the document is  IsAuthorization.
        /// </summary>
        public bool IsAuthorization
        {
            get { return isAuthorization; }
            set { isAuthorization = value;  }
        }


        /// <summary>
        /// This property is used to get or set,whether the document is designated as MUDocumentType
        /// </summary>
        public bool IsMUDocumentType
        {
            get { return isMUDocumentType; }
            set { isMUDocumentType = value; }
        }
        
        /// <summary>
        /// This property is used to get or set the MU documenttype name.
        /// </summary>
        public string MUDocumentType
        {
            get { return MUDocumenttype; }
            set { MUDocumenttype = value; }
        }

        /// <summary>
        /// This property is used to gets the CodeSet.
        /// </summary>
        
        public long CodeSetId
        {
            get { return codeSetId; }
            set { codeSetId = value; }
        }
       
        #endregion

        #region Methods

        public DocumentTypesDetails Clone()
        {
            return (DocumentTypesDetails)this.MemberwiseClone();
        }

        #endregion
    }
}
