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
    /// Model contains the request patient details
    /// </summary>
    [Serializable]
    public class DeleteList
    {
        #region Fields

        private List<long> deletedPatients; // Delete HPF patient from the DSR Tree
        private List<long> deletedEncounters;
        private List<long> deletedDocuments;
        private List<long> deletedVersions;
        private List<long> deletedPages;
        private List<long> deletedPatientList; //Delete HPF patient from the patient list grid.

        private List<long> deleteDARSupplementalPatients;//Delete HPF patient from the patient list grid.
        private List<long> deletesupplementalPatients; // Delete non HPF patient from the DSR Tree

        private List<long> deletesupplementalDocuments; // Delete non HPF patients's non HPF documents.
        private List<long> deleteSupplementaryDocuments; // Delete HPF patients's non HPF documents.
        private List<long> deleteSupplementalAttachments; // Delete non HPF patient's non HPF attachments.        
        private List<long> deleteSupplementaryAttachments; // Delete HPF patient's non HPF attachments

        #endregion

        #region Constructor

        public DeleteList() { }
        
        #endregion

        #region Methods

        public void Clear()
        {
            try
            {
                DeletedPatients.Clear();
                DeletedEncounters.Clear();
                DeletedDocuments.Clear();
                DeletedVersions.Clear();
                DeletedPages.Clear();
                DeletedPatientList.Clear();
                if(deleteDARSupplementalPatients!=null)
                    deleteDARSupplementalPatients.Clear();

                DeletesupplementalPatients.Clear();
                DeleteSupplementalAttachments.Clear();
                DeletesupplementalDocuments.Clear();
                DeleteSupplementaryAttachments.Clear();
                DeleteSupplementaryDocuments.Clear();
            }
            catch(Exception ex)
            {

            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the request deleted patients.
        /// </summary>
        public List<long> DeletedPatients
        {
            get
            {
                if (deletedPatients == null)
                {
                    deletedPatients = new List<long>();
                }
                return deletedPatients;
            }
        }

        /// <summary>
        /// Gets or sets the request deleted patients in the patient list grid.
        /// </summary>
        public List<long> DeletedPatientList
        {
            get
            {
                if (deletedPatientList == null)
                {
                    deletedPatientList = new List<long>();
                }
                return deletedPatientList;
            }
        }

        /// <summary>
        /// Gets or sets the request deleted encounters.
        /// </summary>
        public List<long> DeletedEncounters
        {
            get
            {
                if (deletedEncounters == null)
                {
                    deletedEncounters = new List<long>();
                }

                return deletedEncounters;
            }
        }

        /// <summary>
        /// Gets or sets the request deleted documents.
        /// </summary>
        public List<long> DeletedDocuments
        {
            get
            {
                if (deletedDocuments == null)
                {
                    deletedDocuments = new List<long>();
                }
                return deletedDocuments;
            }
        }

        /// <summary>
        /// Gets or sets the request deleted versions.
        /// </summary>
        public List<long> DeletedVersions
        {
            get
            {
                if (deletedVersions == null)
                {
                    deletedVersions = new List<long>();
                }
                return deletedVersions;
            }
        }

        /// <summary>
        /// Gets or sets the request deleted pages.
        /// </summary>
        public List<long> DeletedPages
        {
            get
            {
                if (deletedPages == null)
                {
                    deletedPages = new List<long>();
                }
                return deletedPages;
            }
        }

        /// <summary>
        /// Gets or sets the request DAR Supplemental Patients
        /// </summary>
        public List<long> DeleteDARSupplementalPatients
        {
            get
            {
                if (deleteDARSupplementalPatients == null)
                {
                    deleteDARSupplementalPatients = new List<long>();
                }
                return deleteDARSupplementalPatients;
            }
        }

        /// <summary>
        /// Gets or sets the request DSR Supplemental Patients
        /// </summary>
        public List<long> DeletesupplementalPatients
        {
            get
            {
                if (deletesupplementalPatients == null)
                {
                    deletesupplementalPatients = new List<long>();
                }
                return deletesupplementalPatients;
            }
        }

        /// <summary>
        /// Gets or sets the request DSR Supplemental Attachments
        /// </summary>
        public List<long> DeleteSupplementalAttachments
        {
            get
            {
                if (deleteSupplementalAttachments == null)
                {
                    deleteSupplementalAttachments = new List<long>();
                }
                return deleteSupplementalAttachments;
            }
        }

        /// <summary>
        /// Gets or sets the request DSR Supplemental Documents
        /// </summary>
        public List<long> DeletesupplementalDocuments
        {
            get
            {
                if (deletesupplementalDocuments == null)
                {
                    deletesupplementalDocuments = new List<long>();
                }
                return deletesupplementalDocuments;
            }
        }

        /// <summary>
        /// Gets or sets the request DSR Supplemental Documents for HPF Patient
        /// </summary>
        public List<long> DeleteSupplementaryDocuments
        {
            get
            {
                if (deleteSupplementaryDocuments == null)
                {
                    deleteSupplementaryDocuments = new List<long>();
                }
                return deleteSupplementaryDocuments;
            }
        }

        /// <summary>
        /// Gets or sets the request DSR Supplemental Attachments for HPF Patient
        /// </summary>
        public List<long> DeleteSupplementaryAttachments
        {
            get
            {
                if (deleteSupplementaryAttachments == null)
                {
                    deleteSupplementaryAttachments = new List<long>();
                }
                return deleteSupplementaryAttachments;
            }
        }

        #endregion
    }
}
