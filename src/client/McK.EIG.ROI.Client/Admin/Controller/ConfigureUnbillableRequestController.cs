using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;


namespace McK.EIG.ROI.Client.Admin.Controller
{    
    
       public partial class ROIAdminController
       {
           #region Methods

           /// <summary>
           /// The method will retrieve UnbillableRequest Details.

            public ConfigureUnbillableRequestDetails RetrieveConfigureUnbillableRequest()
            {
                ConfigureUnbillableRequestDetails requestDetails = new ConfigureUnbillableRequestDetails();
                object response = ROIHelper.Invoke(roiAdminService, "retrieveUnbillableRequestFlag", new object[0]);
                requestDetails.IsUnbillableRequest = (bool)response;
                return requestDetails;
            }

            /// <summary>
            /// The method will update UnbillableRequest Details.
            
            public void UpdateConfigureUnbillableRequest(bool flag) 
            {
                object[] requestParams = new object[] { flag };
                ROIHelper.Invoke(roiAdminService, "updateUnbillableRequestFlag", requestParams);
            
            }
        
            #endregion
       }
}
