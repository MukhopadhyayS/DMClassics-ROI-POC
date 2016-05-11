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
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Reflection;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Display the AdminBaseListUI.
    /// </summary>
    public partial class AdminBaseListUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(AdminBaseListUI));

        #region Constructor
        /// <summary>
        /// Initilize the ROIListUI
        /// </summary>
        public AdminBaseListUI()
        {
            InitializeComponent();
            InitGrid();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the events
        /// </summary>
        protected virtual void InitEvents()
        {
            grid.ChangeValidator = new EIGDataGrid.RowChangeValidator(RowChangeValidator);
            grid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(RowSelectionHandler);
        }

        private bool RowChangeValidator(DataGridViewRow row)
        {
            ApplicationEventArgs ae = new ApplicationEventArgs(row.DataBoundItem, this);
            //CR# 366286 Enable/Disable the delete button based on row selection in grid
            bool b = TransientDataValidator.Validate(ae);
            deleteButton.Enabled = (grid.SelectedRows.Count > 0) && b;
            return b;
        }

         /// <summary>
        /// Initializes the datagrid
        /// </summary>
        protected virtual void InitGrid()
        {
        }

        protected virtual bool CanDelete(object data)
        {
            return true;
        }

        private void RowSelectionHandler(DataGridViewRow row)
        {
            log.EnterFunction();
            try
            {
                ROIViewUtility.MarkBusy(true);
                object data = RefreshEntityData(row.DataBoundItem);
                UpdateRow(data);
                createButton.Enabled = true;
                if (CanDelete(data))
                {
                    EnableDelete();
                }
                else
                {
                    DisableDelete();
                }

                ApplicationEventArgs ae = new ApplicationEventArgs(data, null);
                if (createButton.Enabled == true)
                {
                    AdminEvents.OnMCPEntitySelected(Pane, ae);
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        /// <summary>
        /// Sets the data into datagrid's DataSource object
        /// </summary>
        /// <param name="data"></param>
        public virtual void SetData(object data)
        {
            grid.SetItems((IFunctionCollection)data);
            InitEvents();
            if (grid.Items.Count == 0)
            {
                EnableCreate();
                DisableDelete();
                AdminEvents.OnMCPEmptyListing(null, null);
                createButton.Focus();
            }
            else
            {
                grid.SelectRow(0);
                grid.Rows[0].Selected = true;
            }
        }

        /// <summary>
        /// Adds the row in datagrid
        /// </summary>
        /// <param name="data"></param>
        public virtual void AddRow(object data)
        {
            grid.ConfirmSelection = false;
            int index = grid.AddItem(data);
            grid.SelectRow(index, false);
            EnableCreate();
            EnableDelete();
        }

        /// <summary>
        /// Update the row details in datagrid
        /// </summary>
        /// <param name="data"></param>
        public virtual void UpdateRow(object data)
        {
            grid.ConfirmSelection = false;
            grid.UpdateItem(data);
            EnableCreate();
            EnableDelete();
        }

        /// <summary>
        /// Selects the first row in datagridview
        /// </summary>
        protected virtual void SelectFirstRow()
        {
            if (grid.Rows.Count > 0)
            {
                grid.SelectRow(0);
            }
            else
            {
                EnableCreate();
                DisableDelete();
                AdminEvents.OnMCPEmptyListing(null, null);
            }
        }

        /// <summary>
        /// Enable Create New button
        /// </summary>
        public virtual void EnableCreate()
        {
            createButton.Enabled = true;
        }

        /// <summary>
        /// Disable Create New button
        /// </summary
        public virtual void DisableCreate()
        {
            createButton.Enabled = false;
        }

        /// <summary>
        /// Enable Delete button
        /// </summary
        public virtual void EnableDelete()
        {
            deleteButton.Enabled = (grid.SelectedRows.Count > 0) &&
                                    CanDelete(grid.SelectedRows[0].DataBoundItem);
        }

        /// <summary>
        /// Disable Delete button
        /// </summary
        public void DisableDelete()
        {
            deleteButton.Enabled = false;
        }

        /// <summary>
        /// Occurs when the Create Button is Clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnCreate_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            try
            {
                grid.ConfirmSelection = true;
                grid.ClearSelection();
                DisableDelete();
                DisableCreate();
                AdminEvents.OnCreateNewButtonClick(null, null);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            log.ExitFunction();
        }


        /// <summary>
        /// Occurs when delete button is clicked, In order to delete the selected row or rows form the DataGridView.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnDelete_Click(object sender, EventArgs e)
        {
            log.EnterFunction();

            try
            {
                if (ROIViewUtility.ConfirmDelete(Context, DeleteMessageKey, Pane.View.GetType().Name))
                {
                    object data = grid.SelectedRows[0].DataBoundItem;
                    ROIViewUtility.MarkBusy(true);
                    DeleteEntity(data);
                    grid.DeleteItem(data);
                    SelectFirstRow();
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }

            if (grid.Items.Count == 0) createButton.Focus();
        }

        protected virtual object RefreshEntityData(object data)
        {
            throw new NotImplementedException();
        }

        protected virtual void DeleteEntity(object data)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Occurs after a new row is added to the DataGridView, in order to update the count.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void listGrid_RowsAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            UpdateRowCount();
        }

        /// <summary>
        /// Occurs when a row or rows are deleted from the DataGridView, in order to update the count.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void listGrid_RowsRemoved(object sender, DataGridViewRowsRemovedEventArgs e)
        {
            UpdateRowCount();
        }

        internal void UpdateRowCount()
        {
            countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentCulture, (string)countLabel.Tag, new object[] { grid.Rows.Count });
        }

        /// <summary>
        ///  Set Culture text to the controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, deleteButton);
            SetLabel(rm, createButton);

            foreach (DataGridViewColumn col in grid.Columns)
            {
                if (col is DataGridViewTextBoxColumn)
                {
                    col.HeaderText = rm.GetString(Convert.ToString(col.Tag, System.Threading.Thread.CurrentThread.CurrentCulture));
                }
            }
        }

        internal void CancelCreate(object sender, EventArgs e)
        {
            grid.ConfirmSelection = false;
            if (grid.Rows.Count == 0)
            {
                EnableCreate();
                DisableDelete();
                AdminEvents.OnMCPEmptyListing(null, null);
                createButton.Focus();
                return;
            }

            ApplicationEventArgs ae = e as ApplicationEventArgs;
            // dont do previous selection if the cancel creation is triggered by another row selection
            if (ae == null || ae.SourceEvent != this)
            {
                grid.SelectPreviousUnselected();
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// This method calculate the width of the last column in the DataGridView.
        /// </summary>
        /// <returns>Integer that represent the width of the column in DataGridView.</returns>
        public int ColumnWidth
        {
            get
            {
                int sum = 0;
                foreach (DataGridViewColumn column in grid.Columns)
                {
                    sum += column.Width;
                }
                sum = grid.Width - sum;
                sum = grid.Columns[grid.Columns.Count - 1].Width + sum - 3;
                return sum;
            }
        }

        public virtual string DeleteMessageKey
        {
            get { return null; }
        }
        
        public Panel ButtonBar
        {
            get { return mcpActions; }
            set 
            {
                if ((mcpActions != value) && (value != null))
                {
                    mcpActions = value as FlowLayoutPanel;
                }
            }
        }

        #endregion

        /// <summary>
        /// Occurs when the contents of a cell need to be formatted for display
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            if (e.ColumnIndex == 0)
            {
                ROIModel roiModel = (ROIModel)grid.Rows[e.RowIndex].DataBoundItem;
                DataGridViewCell cell = grid.Rows[e.RowIndex].Cells[e.ColumnIndex];

                if (roiModel == null)
                {
                    return;
                }

                if (roiModel.Id < 0)
                {
                    cell.ToolTipText = rm.GetString("systemimage");
                }
                else
                {
                    cell.ToolTipText = rm.GetString("userimage");
                }
            }
        }
    }
}
