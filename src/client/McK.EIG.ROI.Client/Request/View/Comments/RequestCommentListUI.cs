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
    /// Display the Comments List UI.
    /// </summary>
    public partial class RequestCommentListUI : ROIBaseUI
    { 
        #region Fields

        private EventHandler AuthorSelectionChanged;
        private EventHandler PresetDateChanged;

        private const string CommentDateTime    = "datetime";
        private const string CommentAuthor      = "author";
        private const string CommentDescription = "comment";

        #endregion

        #region Constructor

        public RequestCommentListUI()
        {
            InitializeComponent();
            InitGrid();
            EnableEvents();
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
        /// Initialize the grid1.
        /// </summary>
        private void InitGrid()
        {
            DataGridViewTextBoxColumn dgvCommentDateTimeColumn = grid1.AddTextBoxColumn(CommentDateTime, "Date Time", "EventDate", 150);
            dgvCommentDateTimeColumn.DefaultCellStyle.Format = System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.ShortDatePattern + ' ' +
                                                               System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.LongTimePattern.Replace(":ss", "");
            grid1.AddTextBoxColumn(CommentAuthor, "Author", "Originator", 125);
            DataGridViewTextBoxColumn descriptionColumn = grid1.AddTextBoxColumn(CommentDescription, "Comment", "EventRemarks", 125);            
            descriptionColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            grid1.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid1_DataBindingComplete);
        }

		//CR# 378382
        /// <summary>
        /// Occurs data bind into the grid
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>        
        private void grid1_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {   
            foreach (DataGridViewRow row in grid1.Rows)
            {
                CommentDetails comment = (CommentDetails)row.DataBoundItem;
                row.Cells[CommentDescription].ToolTipText = row.Cells[CommentDescription].Value.ToString();
            }
        }        

        /// <summary>
        /// Enable the evnents.
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            AuthorSelectionChanged = new EventHandler(Process_AuthorSelectionChanged);
            PresetDateChanged      = new EventHandler(Process_PresetDateChanged);

            authorComboBox.SelectedIndexChanged    += AuthorSelectionChanged;
            presetDateRange.PresetDateRangeHandler += PresetDateChanged;
        }

        /// <summary>
        /// Disable the events.
        /// </summary>
        private void DisableEvents()
        {
            authorComboBox.SelectedIndexChanged    -= AuthorSelectionChanged;
            presetDateRange.PresetDateRangeHandler -= PresetDateChanged;
        }


        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {   
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            //UI Text
            SetLabel(rm, presetDateRange.dateLabel);
            SetLabel(rm, authorLabel);
            presetDateRange.Localize();            

            //grid1
            SetLabel(grid1, rm, CommentDateTime);
            SetLabel(grid1, rm, CommentAuthor);
            SetLabel(grid1, rm, CommentDescription);
        }

        /// <summary>
        /// Prepopulate the comments for the Selected Request.
        /// </summary>
        /// <param name="collection"></param>
        public void PrePopulate(object data)
        {
            DisableEvents();
            PrePopulateAuthor(data);
            grid1.SetItems((IFunctionCollection)data);
            UpdateCommentCount();
            EnableEvents();
            grid1.Enabled = grid1.RowCount > 0;
        }

        /// <summary>
        /// Prepopulate Author combobox.
        /// </summary>
        /// <param name="data"></param>
        private void PrePopulateAuthor(object data)
        {
            authorComboBox.Items.Add(ROIConstants.AllOption);
            foreach (CommentDetails details in data as ComparableCollection<CommentDetails>)
            {
                if (!authorComboBox.Items.Contains(details.Originator))
                {
                    authorComboBox.Items.Add(details.Originator);
                }
            }
            authorComboBox.SelectedIndex = 0;
        }

        /// <summary>
        /// Update the row in the grid1
        /// </summary>
        /// <param name="info"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ResetFilter();

            //Update Author combobox.
            CommentDetails comment = data as CommentDetails;
            if (!authorComboBox.Items.Contains(comment.Originator.Trim()))
            {
                authorComboBox.Items.Add(comment.Originator.Trim());
            }

            //Update the grid1.
            grid1.AddItem(data);
            UpdateCommentCount();
            SortGrid();            
            EnableEvents();
            grid1.Enabled = grid1.RowCount > 0;
            grid1.SelectNextControl(authorComboBox, true, true, true, true);
        }

        /// <summary>
        /// Updte the Comment Count.
        /// </summary>
        private void UpdateCommentCount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            if (grid1.Rows.Count == 1)
            {
                commentCountLabel.Text = rm.GetString("commentCountLabel.comment");
            }
            else
            {
                string message = rm.GetString("commentCountLabel.comments");
                commentCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, message, grid1.Rows.Count);
            }
        }

        /// <summary>
        /// Reset the filter option.
        /// </summary>
        private void ResetFilter()
        {
            authorComboBox.SelectedIndex = 0;
            presetDateRange.Reset();
            grid1.RemoveFilter();
        }

        /// <summary>
        /// Sort the grid1.
        /// </summary>
        private void SortGrid()
        {
            if (grid1.SortedColumn != null)
            {
                ListSortDirection direction = (grid1.SortOrder == SortOrder.Ascending) ? ListSortDirection.Ascending : ListSortDirection.Descending;
                grid1.Sort(grid1.SortedColumn, direction);                
            }
        }

        /// <summary>
        /// Invoked when author Combobox selected index changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_AuthorSelectionChanged(object sender, EventArgs e)
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
        /// Apply filter to grid1.
        /// </summary>
        private void ApplyFilter()
        {
            //If Author= 'All' and Date='All'
            if (authorComboBox.SelectedIndex == 0 && !presetDateRange.FromDate.HasValue && !presetDateRange.ToDate.HasValue)
            {
                grid1.RemoveFilter();
                UpdateCommentCount();
                return;
            }
            
            string expression = string.Empty;
            //Filter query construction
            if (authorComboBox.SelectedIndex != 0)
            {
                expression = "Originator = '" + authorComboBox.Text + "' AND ";
            }

            if (presetDateRange.FromDate.HasValue && presetDateRange.ToDate.HasValue)
            {
                expression += "EventDate >='" + presetDateRange.FromDate.Value.ToString() + "' AND EventDate <='" + presetDateRange.ToDate.Value.ToString() + "'";
            }
            else
            {
                expression = expression.Substring(0, expression.Length - 4).Trim();
            }
            grid1.Filter = expression;
            UpdateCommentCount();
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

        #region Properties

        #endregion
    }
}
