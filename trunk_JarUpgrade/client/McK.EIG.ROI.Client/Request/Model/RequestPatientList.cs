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
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Globalization;
using System.Text;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{

    /// <summary>
    /// Model contains the list of request patient details
    /// </summary>
    [Serializable]
    public class UpdateRequestPatients
    {
        #region Fields

        private List<RequestPatientDetails> requestPatients;      

        private List<RequestEncounterDetails> requestEncounters;
        private List<RequestDocumentDetails> requestDocuments;
        private List<RequestVersionDetails> requestVersions;
        private List<RequestPageDetails> requestPages;
        private List<RequestNonHpfEncounterDetails> requestNonHPFEncounters;
        private List<RequestAttachmentEncounterDetails> requestAttachmentEncounterDetails;
    
        private DeleteList deleteList;

        #endregion

        #region Constructor

        public UpdateRequestPatients() {}
        
        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the list of request patients.
        /// </summary>
        public List<RequestPatientDetails> RequestPatients
        {
            get 
            {
                if (requestPatients == null)
                {
                    requestPatients = new List<RequestPatientDetails>();
                }
                return requestPatients;
            }
        }

        /// <summary>
        /// Gets or sets the deleted request patients.
        /// </summary>
        public DeleteList DeleteList
        {
            get { return deleteList; }
            set { deleteList = value; }             
        }

        /// <summary>
        /// Gets or sets the list of encounters belongs to particular patient.
        /// </summary>
        public List<RequestEncounterDetails> RequestEncounters
        {
            get
            {
                if (requestEncounters == null)
                {
                    requestEncounters = new List<RequestEncounterDetails>();
                }
                return requestEncounters;
            }
        }

        /// <summary>
        /// Gets or sets the list of documents belongs to particular encounter.
        /// </summary>
        public List<RequestDocumentDetails> RequestDocuments
        {
            get
            {
                if (requestDocuments == null)
                {
                    requestDocuments = new List<RequestDocumentDetails>();
                }
                return requestDocuments;
            }
        }

        /// <summary>
        /// Gets or sets the list of versions belongs to particular document.
        /// </summary>
        public List<RequestVersionDetails> RequestVersions
        {
            get
            {
                if (requestVersions == null)
                {
                    requestVersions = new List<RequestVersionDetails>();
                }
                return requestVersions;
            }
        }

        /// <summary>
        /// Gets or sets the list of pages belongs to particular version.
        /// </summary>
        public List<RequestPageDetails> RequestPages
        {
            get
            {
                if (requestPages == null)
                {
                    requestPages = new List<RequestPageDetails>();
                }
                return requestPages;
            }
        }
		
		/// <summary>
        /// Gets or sets the list of NonHPFEncounters.
        /// </summary>
        public List<RequestNonHpfEncounterDetails> RequestNonHPFEncounters
        {
            get
            {
                if (requestNonHPFEncounters == null)
                {
                    requestNonHPFEncounters = new List<RequestNonHpfEncounterDetails>();
                }
                return requestNonHPFEncounters;
            }
        }

		/// <summary>
        /// Gets or sets the list of attachments.
        /// </summary>
        public List<RequestAttachmentEncounterDetails> RequestAttachmentEncounterDetails
        {
            get
            {
                if (requestAttachmentEncounterDetails == null)
                {
                    requestAttachmentEncounterDetails = new List<RequestAttachmentEncounterDetails>();
                }
                return requestAttachmentEncounterDetails;
            }
        } 

        #endregion
    }
        
}
