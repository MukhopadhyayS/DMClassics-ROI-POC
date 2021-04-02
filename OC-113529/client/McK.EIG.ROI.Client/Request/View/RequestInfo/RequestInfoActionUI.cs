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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    /// <summary>
    /// RequestInfoActionUI
    /// </summary>
    public partial class RequestInfoActionUI : ROIBaseUI
    {
        #region Constructor

        public RequestInfoActionUI()
        {
            InitializeComponent();
            deleteRequestButton.Image = ROIImages.DeleteButtonIcon;
        }

        #endregion

        #region Methods

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, requiredLabel);
            SetLabel(rm, deleteRequestButton);
        }

        #endregion

        #region Properties

        public Button SaveButton
        {
            get { return saveButton; }
        }

        public Button RevertButton
        {
            get { return revertButton; }
        }

        public Button DeleteButton
        {
            get { return  deleteRequestButton; }
        }

        public PictureBox RequestLockedImage
        {
            get { return requestLockedImg; }
        }

        public Button ViewAuthRequestButton
        {
            get { return viewAuthRequestButton; }
        }

        #endregion
    }
}
