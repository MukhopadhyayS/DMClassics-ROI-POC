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
using System.ComponentModel;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Requestors.View.FindRequestor
{
    /// <summary>
    /// Holds RequestorSearchUI
    /// </summary>
    public class FindRequestorMCP : ROIBasePane
    {
        #region Fields

        private FindRequestorSearchUI searchUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the Requestor Search UI.
        /// </summary>
        protected override void InitView()
        {
            searchUI = new FindRequestorSearchUI();
            searchUI.ApplySecurityRights();
        }

        /// <summary>
        /// Gets the Requestor collection from datastore.
        /// </summary>
        public void PrePopulate(object data)
        {

            ROIViewUtility.MarkBusy(true);
            
            try
            {
                Collection<RequestorTypeDetails> requestorTypes = (data == null) ? new Collection<RequestorTypeDetails>()
                                                                                 : data as Collection<RequestorTypeDetails>;

                if (requestorTypes != null)
                {
                    searchUI.PrePopulate(requestorTypes);   
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of FindRequestorMCP.
        /// </summary>  
        public override Component View
        {
            get { return searchUI; }
        }

        #endregion
    }
}
