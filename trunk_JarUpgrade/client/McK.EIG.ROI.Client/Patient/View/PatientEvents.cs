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

namespace McK.EIG.ROI.Client.Patient.View
{
    /// <summary>
    /// Placeholder to hold all events related to patients module.
    /// </summary>
    public static class PatientEvents
    {
        #region Fields

        //LSP Events
        public static event EventHandler NavigateFindPatient;
        public static event EventHandler NavigatePatientInfo;
        public static event EventHandler NavigatePatientRecords;
        public static event EventHandler NavigateRequestHistory;
        
        //MCP Events
        public static event EventHandler CreatePatient;
        public static event EventHandler ResetSearch;
        public static event EventHandler PatientSearched;

        //Odp Event
        public static event EventHandler PatientSelected;
        public static event EventHandler PatientDeleted;
        public static event EventHandler PatientCreated;
        public static event EventHandler PatientUpdated;

        //From Menu pane
        public static event EventHandler PatientRequest;
        public static event EventHandler CreateRequest;

        #endregion

        #region Methods
        
        /// <summary>
        /// Occurs when Find Patient Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateFindPatient(object sender, EventArgs e)
        {
            if (NavigateFindPatient != null)
            {
                // Occurs when Find patient navigation link is clicked
                NavigateFindPatient(sender, e);
            }
        }
        
        /// <summary>
        /// Occurs when PatientInfo Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigatePatientInfo(object sender, EventArgs e)
        {
            if (NavigatePatientInfo != null)
            {
                // Occurs when patient info navigation link is clicked
                NavigatePatientInfo(sender, e);
            }
        }

        /// <summary>
        /// Occurs when PatientRecords Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigatePatientRecords(object sender, EventArgs e)
        {
            if (NavigatePatientRecords != null)
            {
                // Occurs when patient records navigation link is clicked
                NavigatePatientRecords(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Request History Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateRequestHistory(object sender, EventArgs e)
        {
            if (NavigateRequestHistory != null)
            {
                // Occurs when patient records navigation link is clicked
                NavigateRequestHistory(sender, e);
            }
        }

        /// <summary>
        /// Occurs When NewPatient Button is Clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnCreatePatient(object sender, EventArgs e)
        {
            if (CreatePatient != null)
            {
                CreatePatient(sender, e);
            }
        }

        /// <summary>
        /// Occurs When Reset Button is Clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnSearchReset(object sender, EventArgs e)
        {
            if (ResetSearch != null)
            {
                ResetSearch(sender,e);
            }
        }

        /// <summary>
        /// Occurs when FindPatient Button is invoked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnPatientSearched(object sender, EventArgs e)
        {
            if (PatientSearched != null)
            {
                PatientSearched(sender, e);                
            }
        }

        /// <summary>
        /// Occurs when FindPatient ODP Grid selection changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnPatientSelected(object sender, EventArgs e)
        {
            if (PatientSelected != null)
            {
                PatientSelected(sender, e);                
            }
        }

        /// <summary>
        /// Occurs when NonHpfPatient Deleted.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnPatientDeleted(object sender, EventArgs e)
        {
            if (PatientDeleted != null)
            {
                PatientDeleted(sender, e);
            }
        }


        /// <summary>
        /// Occurs when NonHpfPatient Created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnPatientCreated(object sender, EventArgs e)
        {
            if (PatientCreated != null)
            {
                PatientCreated(sender, e);
            }
        }

        /// <summary>
        /// Occurs when NonHpfPatient updated.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnPatientUpdated(object sender, EventArgs e)
        {
            if (PatientUpdated != null)
            {
                PatientUpdated(sender, e);
            }
        }

        /// <summary>
        /// Occurs when CreateRequest menu item selected.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnCreateRequest(object sender, EventArgs e)
        {
            if (CreateRequest != null)
            {
                CreateRequest(sender, e);
            }
        }

        /// <summary>
        /// Occurs when CreateRequest menu item selected.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnPatientRequesting(object sender, EventArgs e)
        {
            if (PatientRequest != null)
            {
                PatientRequest(sender, e);
            }
        }

        #endregion
    }
}
