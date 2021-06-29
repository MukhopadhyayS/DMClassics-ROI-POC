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
using System.ComponentModel;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.Comments
{
    /// <summary>
    /// Display the Event List UI.
    /// </summary>
    public partial class EventHistoryListUI : ROIBaseUI
    { 
        #region Fields

        private EventHandler EventSelectionChanged;
        private EventHandler OriginatorSelectionChanged;
        private EventHandler PresetDateChanged;

        private const string EventHistoryDateTime    = "datetime";
        private const string EventHistoryEvent       = "event";
        private const string EventHistoryOriginator  = "originator";
        private const string EventHistoryRemarks     = "eventremarks";

        #endregion

        #region Constructor

        public EventHistoryListUI()
        {
            InitializeComponent();
            InitGrid();
            presetDateRange.fromNullableDTP.MaxDate = DateTime.Today.Date;
            presetDateRange.toNullableDTP.MaxDate = new DateTime(DateTime.Now.Year, 
                                                                 DateTime.Now.Month, 
                                                                 DateTime.Now.Day, 23, 59, 59);
            EnableEvents();
        }
        
        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, false))
            {
                this.Enabled = false;
            }
        }

        #endregion

        #region Methods

        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            presetDateRange.SetExecutionContext(Context); 
            presetDateRange.SetPane(Pane);
        }

        /// <summary>
        /// Initializes the grid.
        /// </summary>
        private void InitGrid()
        {   
            DataGridViewTextBoxColumn dgvEventHistoryDateTimeColumn = grid.AddTextBoxColumn(EventHistoryDateTime, "Date Time", "EventDate", 150);
            dgvEventHistoryDateTimeColumn.DefaultCellStyle.Format = System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.ShortDatePattern + ' ' +
                                                                    System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.LongTimePattern.Replace(":ss", "");
            grid.AddTextBoxColumn(EventHistoryEvent, "Event", "RequestEvent", 150);
            grid.AddTextBoxColumn(EventHistoryOriginator, "Originator", "Originator", 150);
            DataGridViewTextBoxColumn eventRemarkColumn = grid.AddTextBoxColumn(EventHistoryRemarks, "EventRemarks", "EventRemarks", 150);
            eventRemarkColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;         
        }

        /// <summary>
        /// Enable the events.
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();

            EventSelectionChanged       = new EventHandler(Process_EventSelectionChanged);
            OriginatorSelectionChanged  = new EventHandler(Process_OriginatorSelectionChanged);
            PresetDateChanged           = new EventHandler(Process_PresetDateChanged);

            eventComboBox.SelectedIndexChanged      += EventSelectionChanged;
            originatorComboBox.SelectedIndexChanged += OriginatorSelectionChanged;
            presetDateRange.PresetDateRangeHandler  += PresetDateChanged;
        }

        /// <summary>
        /// Disable the events.
        /// </summary>
        private void DisableEvents()
        {
            eventComboBox.SelectedIndexChanged      -= EventSelectionChanged;
            originatorComboBox.SelectedIndexChanged -= OriginatorSelectionChanged;
            presetDateRange.PresetDateRangeHandler  -= PresetDateChanged;
        }


        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, presetDateRange.dateLabel);
            //UI Text
            SetLabel(rm, eventLabel);
            SetLabel(rm, originatorLabel);

            presetDateRange.Localize();   

            SetLabel(grid, rm, EventHistoryDateTime);
            SetLabel(grid, rm, EventHistoryEvent);
            SetLabel(grid, rm, EventHistoryOriginator);
            SetLabel(grid, rm, EventHistoryRemarks);
        }

        /// <summary>
        /// Prepopulate the Events for the Selected Request.
        /// </summary>
        /// <param name="collection"></param>
        public void PrePopulate(object data,string[] requestEvents)
        {
            DisableEvents();
            PrePopulateOriginator(data);
            PrePopulateEvent(requestEvents);
            grid.SetItems((IFunctionCollection)data);
            UpdateEventCount();
            EnableEvents();
            eventComboBox.Focus();
        }

        /// <summary>
        /// Prepopulate Originator combobox.
        /// </summary>
        /// <param name="data"></param>
        private void PrePopulateOriginator(object data)
        {
            originatorComboBox.Items.Add(ROIConstants.AllOption);
            foreach (RequestEventHistoryDetails requestEventHistory in data as ComparableCollection<RequestEventHistoryDetails>)
            {
                if (!originatorComboBox.Items.Contains(requestEventHistory.Originator))
                {
                    originatorComboBox.Items.Add(requestEventHistory.Originator);
                }
            }

            if (originatorComboBox.Items.Count > 0)
            {
                originatorComboBox.SelectedIndex = 0;
            }
        }


        /// <summary>
        /// Prepopulate Event combobox.
        /// </summary>
        /// <param name="data"></param>
        private void PrePopulateEvent(string[] requestEvents)
        {
            eventComboBox.Items.Add(ROIConstants.AllEvents);

            foreach (string requestEvent in requestEvents)
            {
                eventComboBox.Items.Add(requestEvent);
            }

            if (eventComboBox.Items.Count > 0)
            {
                eventComboBox.SelectedIndex = 0;
            }
        }

        /// <summary>
        /// Update the Event Count.
        /// </summary>
        private void UpdateEventCount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            if (grid.Rows.Count == 1)
            {
                eventCountLabel.Text = rm.GetString("eventcountlabel.event");
            }
            else
            {
                string message = rm.GetString("eventcountlabel.events");
                eventCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, message, grid.Rows.Count);
            }
        }

        /// <summary>
        /// Invoked when Originator Combobox selected index changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_EventSelectionChanged(object sender, EventArgs e)
        {
            ApplyFilter();
        }

        /// <summary>
        /// Invoked when Originator Combobox selected index changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_OriginatorSelectionChanged(object sender, EventArgs e)
        {
            ApplyFilter();
        }

        /// <summary>
        /// Invoked wher Preset date changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_PresetDateChanged(object sender, EventArgs e)
        {
            ApplyFilter();
        }

        /// <summary>
        /// Apply filter to grid.
        /// </summary>
        private void ApplyFilter()
        {
            if (eventComboBox.SelectedIndex == 0 && originatorComboBox.SelectedIndex == 0 && !presetDateRange.FromDate.HasValue && !presetDateRange.ToDate.HasValue)
            {
                grid.RemoveFilter();
                UpdateEventCount();
                grid.Enabled = grid.RowCount > 0;
                return;
            }
            
            string expression = string.Empty;

            if (eventComboBox.SelectedIndex != 0)
            {
                expression = "RequestEvent = '" + eventComboBox.Text + "' AND ";
            }

            if (originatorComboBox.SelectedIndex != 0)
            {
                expression += "Originator = '" + originatorComboBox.Text + "' AND ";
            }

            if (presetDateRange.FromDate.HasValue && presetDateRange.ToDate.HasValue)
            {
                expression += "EventDate >='" + presetDateRange.FromDate.Value.ToString() + "' AND EventDate <='" + presetDateRange.ToDate.Value.ToString() + "'";
            }
            else
            {
                expression = expression.Substring(0, expression.Length - 4).Trim();
            }
            grid.Filter = expression;
            UpdateEventCount();
            grid.Enabled = grid.RowCount > 0;            
        }

        #endregion
    }
}
