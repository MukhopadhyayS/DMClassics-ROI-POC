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
using System.ComponentModel;
using System.Windows.Forms;
using System.Resources;

using System.Configuration;
using System.Globalization;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;

namespace McK.EIG.ROI.Client.Requestors.View.RequestHistory
{
    public partial class RequestHistoryUI : ROIBaseUI, IRequestorPageContext, IFooterProvider
    {
        #region Fields

        private RequestorFooterUI requestorFooterUI = new RequestorFooterUI();
        private RequestorDetails requestorDetails;

        private RequestsListUI listUI;

        #endregion

        #region Constructor

        public RequestHistoryUI()
        {
            InitializeComponent();
            requestorFooterUI = new RequestorFooterUI(this);
        }

        public void AddListPanel()
        {
            listPanel.Controls.Clear();
            listUI = new RequestorRequestListUI();
            listUI.Dock = DockStyle.Fill;
            listPanel.Controls.Add((Control)listUI);
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return requestorFooterUI;
        }

        #endregion

        #region Methods

        public override void Localize()
        {
            listUI.SetExecutionContext(Context);
            listUI.SetPane(Pane);
            listUI.Localize();
            requestorFooterUI.Localize();
        }

        /// <summary>
        /// SetPane
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);

            requestorFooterUI.SetExecutionContext(Context);
            requestorFooterUI.SetPane(Pane);
        }

        /// <summary>
        /// Sets the Data
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            requestorDetails = data as RequestorDetails;

            FindRequestCriteria findRequestCriteria = new FindRequestCriteria();
            findRequestCriteria.IsSearch = false;
            findRequestCriteria.RequestorId = requestorDetails.Id;
            
            listUI.PopulateRequestHistory(findRequestCriteria, null, string.Empty);
            requestorFooterUI.CreateRequestButton.Enabled = requestorDetails.IsActive;
            EnableButtons();
            listUI.ViewEditRequestButton.Focus();
        }

        private void EnableButtons()
        {
            listUI.ViewEditRequestButton.Enabled = listUI.Grid.Rows.Count > 0;
        }

        /// <summary>
        /// Adds the row in datagrid
        /// </summary>
        /// <param name="data"></param>
        public void AddRow(object data)
        {
            RequestDetails request = data as RequestDetails;

            if (request.Requestor.Id == requestorDetails.Id)
            {
                listUI.AddRow(data);
            }        
            EnableListUI();
        }

        /// <summary>
        /// Remove the deleted patient from the search result.
        /// </summary>
        /// <param name="p"></param>
        public void DeleteRow(object data)
        {
            listUI.DeleteRow(data);
            EnableListUI();
        }

        /// <summary>
        /// Update the patient info
        /// </summary>
        /// <param name="data"></param>
        public void UpdateRow(object data)
        {
            RequestDetails updatedRequest = (RequestDetails)data;
            if (updatedRequest.Requestor.Id == requestorDetails.Id)
            {
                listUI.UpdateRow(data);
            }
            else
            {
                listUI.DeleteRow(data);
            }

            EnableListUI();
        }

        private void EnableListUI()
        {
            listUI.Enabled = true;
            listUI.ViewEditRequestButton.Enabled = listUI.Grid.Rows.Count > 0;
            listUI.ViewEditRequestButton.Focus();
        }


        #endregion

        #region Security Rights

        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, false))
            {
                requestorFooterUI.Enabled = false;
                this.Enabled = false;
            }

            requestorFooterUI.CreateRequestButton.Visible = IsAllowed(ROISecurityRights.ROICreateRequest);
        }

        #endregion

        #region IRequestorPageContext Members

        public RequestorDetails Requestor
        {
            get { return requestorDetails; }
        }

        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.RequestorRequestHistory, e));
        }

        #endregion

        #region Properties

        public RequestorFooterUI Footer
        {
            get { return requestorFooterUI; }
        }

        #endregion
    }
}
