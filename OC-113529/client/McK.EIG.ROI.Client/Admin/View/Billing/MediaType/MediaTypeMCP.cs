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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Billing.MediaType
{
    /// <summary>
    /// Holds the list UI
    /// </summary>
    public class MediaTypeMCP : AdminMCP
    {

        #region Methods

        /// <summary>
        /// Gets the media type collection from datastore and populates the 
        /// collection on MCP list.
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<MediaTypeDetails> mediaTypes = BillingAdminController.Instance.RetrieveAllMediaTypes();
                if (mediaTypes != null)
                {
                    ComparableCollection<MediaTypeDetails> list = new ComparableCollection<MediaTypeDetails>(mediaTypes, new ImageIdComparer());
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(MediaTypeDetails))["Name"], ListSortDirection.Ascending);
                    adminListUI.SetData(list);
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
        /// Returns the view of MediaTypeMCP.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get 
            {
                return (adminListUI == null) ? new MediaTypeListUI() : adminListUI;
            }
        }

        #endregion

    }
}
