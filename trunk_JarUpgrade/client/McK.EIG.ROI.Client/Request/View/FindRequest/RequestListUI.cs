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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;

namespace McK.EIG.ROI.Client.Request.View.FindRequest
{
    /// <summary>
    /// Display the Find Request List UI.
    /// </summary>
    public partial class RequestListUI : ROIBaseUI
    {
        #region Fields
        
        private RequestsListUI listUI;        
        
        #endregion

        #region Constructor

        public RequestListUI()
        {
            InitializeComponent();        
        }

        #endregion

        #region Methods

        public void AddListPanel()
        {
            listPanel.Controls.Clear();
            listUI = new FindRequestListUI();
            listUI.Dock = DockStyle.Fill;
            listPanel.Controls.Add((Control)listUI);
        }

        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);

            listUI.SetExecutionContext(Context);
            listUI.SetPane(Pane);
        }

        /// <summary>
        /// Localise the UI text
        /// </summary>
        public override void Localize()
        {
            //UI Text
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, messageRequestLabel);

            listUI.Localize();
        }

        /// <summary>
        /// Sets the data into datagrid's DataSource object
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            FindRequestResult searchResult = data as FindRequestResult;
            listUI.SetData(searchResult);

            if (((RequestRSP)this.Pane.ParentPane.ParentPane).InfoEditor != null)
            {
                RequestDetails request = ((RequestRSP)this.Pane.ParentPane.ParentPane).InfoEditor.Request;
                if (listUI.Grid.Items.Contains(request))
                {
                    listUI.Grid.SelectItem(request);
                }
            }
        }

        public void ClearList()
        {
            listUI.ClearList();
        }

        #endregion
    }
}
