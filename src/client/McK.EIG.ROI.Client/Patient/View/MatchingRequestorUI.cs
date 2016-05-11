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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Patient.View
{
    public partial class MatchingRequestorUI : ROIBaseUI
    {
        #region Fields

        private const string RequestorNameColumn = "requestorName";
        private const string AddressColumn       = "address";
        private const string PhoneColumn         = "phone";
        private const string FaxColumn           = "fax";
        private const string DOBColumn           = "dob";
        private const string SSNColumn           = "ssn";
        private const string EPNColumn           = "epn";
        private const string FacilityColumn      = "facility";
        private const string MRNColumn           = "mrn";

        private HeaderUI header;
        private CreateRequestInfo createRequestInformation;

        #endregion

        #region Constructor
        /// <summary>
        /// It initializes the UI and grid.
        /// </summary>
        public MatchingRequestorUI()
        {
            InitializeComponent();
            InitGrid();
            
        }

        /// <summary>
        /// Constrctor asssigns context and parent pane.
        /// </summary>
        /// <param name="showNewRequestorsHandler"></param>
        /// <param name="pane"></param>
        public MatchingRequestorUI(IPane pane)
            : this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            header = InitHeaderUI();
            Localize();
        }

        #endregion

        #region Methods

        /// <summary>
        /// It initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            grid.AddTextBoxColumn(RequestorNameColumn, "Requestor Name", "name", 130);
            grid.AddTextBoxColumn(AddressColumn, "Address", "MainAddress", 150);
            grid.AddTextBoxColumn(PhoneColumn, "Phone", "Phone", 90);
            grid.AddTextBoxColumn(FaxColumn, "Fax", "fax", 90);
            grid.AddTextBoxColumn(DOBColumn, "DOB", "PatientDob", 80);
            grid.AddTextBoxColumn(SSNColumn, "SSN", "MaskedPatientSSN", 80);
            grid.AddTextBoxColumn(FacilityColumn, "Facility", "PatientFacilityCode", 70);
            DataGridViewTextBoxColumn mrnColumn = grid.AddTextBoxColumn(MRNColumn, "Mrn", "PatientMrn", 80);
            DataGridViewTextBoxColumn epnColumn = grid.AddTextBoxColumn(EPNColumn, "EPN", "PatientEpn", 100);
            epnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            if (!UserData.Instance.EpnEnabled)
            {
                epnColumn.Visible = false;
                mrnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            }
            grid.DoubleClick += new EventHandler(grid_DoubleClick);
        }

        private void grid_DoubleClick(object sender, EventArgs e)
        {
            this.selectRequestorButton.PerformClick();
        }

        /// <summary>
        /// It sets all requestors to a grid.
        /// </summary>
        /// <param name="encounters"></param>
        public void SetData(CreateRequestInfo createRequestInformation, Collection<RequestorDetails> requestors)
        {
            ComparableCollection<RequestorDetails> list = new ComparableCollection<RequestorDetails>(requestors);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestorDetails))["Name"], ListSortDirection.Ascending);
            grid.SetItems(list);

            this.createRequestInformation = createRequestInformation;

            countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countLabel.Text, grid.Rows.Count);

            selectRequestorButton.Enabled = grid.Rows.Count > 0;
        }
        
        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(grid, rm, RequestorNameColumn);
            SetLabel(grid, rm, AddressColumn);
            SetLabel(grid, rm, PhoneColumn);
            SetLabel(grid, rm, FaxColumn);
            SetLabel(grid, rm, DOBColumn);
            SetLabel(grid, rm, SSNColumn);
            SetLabel(grid, rm, EPNColumn);
            SetLabel(grid, rm, FacilityColumn);
            SetLabel(grid, rm, MRNColumn);

            SetLabel(rm, selectRequestorButton);
            SetLabel(rm, newRequestorButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, matchingRequstorInfoLabel);

            header.Title = rm.GetString("matchingRequestorUI.header.title");
            header.Information = rm.GetString("matchingRequestorUI.header.info");

            countLabel.Text = rm.GetString("countLabel.matchingRequestors");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, selectRequestorButton);
            SetTooltip(rm, toolTip, newRequestorButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Creates HeaderUI
        /// </summary>
        private HeaderUI InitHeaderUI()
        {
            header      = new HeaderUI();
            header.Dock = DockStyle.Fill;

            topPanel.Controls.Add(header);
            return header;
        }

        /// <summary>
        /// Get Localize key.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }


        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = selectRequestorButton;
        }
       
        #endregion

        #region Properties

        public CreateRequestInfo CreateRequestInfo
        {
            get 
            {
                createRequestInformation.Request.Requestor = (RequestorDetails)grid.SelectedRows[0].DataBoundItem;
                return createRequestInformation; 
            }
        }

        #endregion
    }
}
