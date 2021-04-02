using System;
using System.Collections.Generic;
using System.Text;

namespace McK.EIG.ROI.Client.Patient.Model
{
   public class ExternalSourceAttachmentDetails
   {
       #region Fields

        private string encounter;
        private List<object> attachmentFiles;

       #endregion

        #region Properties

        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

        public List<object> AttachmentFiles
        {
            get { return attachmentFiles; }
            set { attachmentFiles = value; }
        }
        #endregion
   }
}
