using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
     [Serializable]
    public class ConfigureUnbillableRequestDetails : ROIModel    
    {
        #region Fields

        private long id;
        private bool isUnbillableRequest;

        #endregion

        #region Properties
         //<summary>
         //This property is used to get or sets ID.
         //</summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }
      
        /// <summary>
        /// This property is used to get or sets UnbillableRequest detail.
        /// </summary>
        public bool IsUnbillableRequest
        {
            get { return isUnbillableRequest; }
            set { isUnbillableRequest = value; }
        }

        #endregion
    }
}
