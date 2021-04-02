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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    /// <summary>
    /// Grid component for ROI application
    /// </summary>
    public class EIGDataGrid : DataGridView
    {

        private IFunctionCollection items;

        public delegate bool RowChangeValidator(DataGridViewRow row);
        private RowChangeValidator rowChangeValidator;

        public delegate void RowSelectionHandler(DataGridViewRow row);
        private RowSelectionHandler rowSelectionHandler;

        private DataGridViewRowStateChangedEventHandler rowStateChangeHandler;

        private bool broadcast;

        public EIGDataGrid()
        {
            base.AutoGenerateColumns = false;
            broadcast = true;
            //Cell auto resizing needs to be validated through out the application
            base.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            base.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.None;
        }

        protected override void OnCellMouseDown(DataGridViewCellMouseEventArgs e)
        {
            if ((Control.ModifierKeys & Keys.Control) == Keys.Control)
            {
                if (e.RowIndex == this.CurrentCellAddress.Y)
                {
                    return;
                }
            }
			//For adjustment/payment grid's editing control
            if (this != null)
            {
                base.OnCellMouseDown(e);
            }
        }

        #region listeners

        private bool confirmSelection;
        private int unselectedRowIndex;
        private bool handleSelection = true;
        private void EIGDataGrid_RowStateChanged(object sender, DataGridViewRowStateChangedEventArgs e)
        {

            if (!broadcast) return;

            if (e.StateChanged == DataGridViewElementStates.Selected) // matches for both select and de-select
            {
                if (!e.Row.Selected) // first cycle for deselection of a previously selected row
                {
                    if (rowChangeValidator != null)
                    {
                        handleSelection = rowChangeValidator(e.Row);
                    }
                    // will be used to revert the selection as part of second cycle
                    unselectedRowIndex = e.Row.Index;
                }
                else // second cycle for the selection of new row or first cycle if nothing was selected previously
                {
                    int eventIndex = e.Row.Index;
                    object eventItem = e.Row.DataBoundItem;
                    if (confirmSelection && rowChangeValidator != null)
                    {
                        //Occurs when a row is selected, when create operation is dirty
                        // [Row selection would have been cleared when create is clicked.
                        //  so will not have a corresponding deselection in this case.]
                        handleSelection = rowChangeValidator(e.Row);
                    }

                    if (rowSelectionHandler == null) return; // nobody bothers. nothing to do.

                    if (handleSelection)
                    {
                        //During update/save operation the selected index would be changed, inorder to maintain
                        //the row index we need to normalize
                        int newEventIndex = items.NormalizedIndexOf(eventItem);
                        if (newEventIndex != eventIndex)
                        {
                            eventIndex = newEventIndex;
                            broadcast = false;
                            Rows[eventIndex].Selected = true;
                            broadcast = true;
                        }
                        rowSelectionHandler(Rows[eventIndex]);
                    }
                    else
                    {
                        broadcast = false; // to prevent deadlock with this event itself by selection/deselection as following
                        e.Row.Selected = false;
                        if (!confirmSelection)
                        {
                            Rows[unselectedRowIndex].Selected = true;
                        }
                        handleSelection = true;
                        broadcast = true;
                    }
                }
            }
        }

        private void ListChangeHandler(object sender, ListChangedEventArgs e)
        {
            ListChangedType change = e.ListChangedType;

            if (change == ListChangedType.ItemChanged || change == ListChangedType.ItemAdded)
            {
                if (items.IsSorted) Sort(base.SortedColumn, items.SortDirection);
            }
        }
        # endregion

        # region API
        public int IndexOf(object item)
        {
            return items.NormalizedIndexOf(item);
        }

        /// <summary>
        /// Adds new object into list.
        /// </summary>
        /// <param name="data"></param>
        public int AddItem(object item)
        {
            broadcast = false;
            items.Add(item);
            broadcast = true;

            if (base.SelectedRows.Count > 0)
            {
                if (item == base.SelectedRows[0].DataBoundItem)
                {
                    base.SelectedRows[0].Selected = false;
                }
            }

            return items.NormalizedIndexOf(item);
        }

        /// <summary>
        /// Refresh selected row details
        /// </summary>
        /// <param name="index"></param>
        public int UpdateItem(object item)
        {
            int index = items.IndexOf(item);
            if (index < 0) throw new ROIException(ROIErrorCodes.ObjectNotFound);
            return UpdateItem(item, index);
        }

        public int UpdateItem(object item, int index)
        {
            broadcast = false;
            items[index] = item;
            broadcast = true;

            return items.NormalizedIndexOf(item);
        }

        /// <summary>
        /// Delete selected object based on index given
        /// </summary>
        /// <param name="index"></param>
        public void DeleteRow(int index)
        {
            DeleteItem(base.Rows[index].DataBoundItem);
        }

        /// <summary>
        /// Delete selected object from bound list
        /// </summary>
        /// <param name="index"></param>
        public void DeleteItem(object item)
        {
            broadcast = false;
            items.Remove(item);
            broadcast = true;
            if (base.SelectedRows.Count > 0) base.SelectedRows[0].Selected = false;
        }

        public void RemoveFilter()
        {
            Filter = String.Empty;
        }

        public void SelectItem(object item)
        {
            SelectItem(item, true);
        }

        public void SelectItem(object item, bool callback)
        {
            int index = items.NormalizedIndexOf(item);
            if (index < 0) throw new ROIException(ROIErrorCodes.ObjectNotFound);

            SelectRow(index, callback);
        }

        public void SelectRow(int index)
        {
            SelectRow(index, true);
        }

        public void SelectRow(int index, bool callback)
        {

            broadcast = callback;

            if (broadcast && base.Rows[index].Selected && rowSelectionHandler != null)
            {
                rowSelectionHandler(base.Rows[index]);
            }
            else
            {
                base.Rows[index].Selected = true;
            }

            if(RowCount > 0)
                base.FirstDisplayedScrollingRowIndex = index;

            broadcast = true;
        }

        public int SelectPreviousUnselected()
        {
            return SelectPreviousUnselected(true);
        }

        public int SelectPreviousUnselected(bool callback)
        {
            if (unselectedRowIndex != -1)
            {
                broadcast = callback;
                SelectRow(unselectedRowIndex);
                broadcast = true;
            }
            return unselectedRowIndex;
        }

        /// <summary>
        /// Sets the datasource
        /// </summary>
        /// <returns></returns>
        public void SetItems(IFunctionCollection items)
        {
            this.items = items;
            BindingSource binder = new BindingSource();
            binder.DataSource = items;
            base.DataSource = binder;
            if (items != null) items.ListChanged += new ListChangedEventHandler(ListChangeHandler);
        }
        # endregion

        # region overriden methods
        public override void Sort(IComparer comparer)
        {
            object item = RetainSelection1();
           
            base.Sort(comparer);

            if (item == null) return;

            //Need to normalize the unselected index
            object unselectedItem = (unselectedRowIndex != -1) ? Rows[unselectedRowIndex].DataBoundItem : null;

            RetainSelection2(item);
            if (unselectedItem != null)
            {
                unselectedRowIndex = items.NormalizedIndexOf(unselectedItem);
            }
        }

        public override void Sort(DataGridViewColumn dataGridViewColumn, ListSortDirection direction)
        {
            object item = RetainSelection1();
           
            base.Sort(dataGridViewColumn, direction);

            if (item == null) return;

            //Need to normalize the unselected index
            object unselectedItem = (unselectedRowIndex != -1) ? Rows[unselectedRowIndex].DataBoundItem : null;

            RetainSelection2(item);
            if (unselectedItem != null)
            {
                unselectedRowIndex = items.NormalizedIndexOf(unselectedItem);
            }
        }
        # endregion

        # region local logic
        private object RetainSelection1()
        {
            object item = (base.SelectedRows.Count != 0) ? base.SelectedRows[0].DataBoundItem : null;
            broadcast = false; // need not disable if nothing is selected
            return item;
        }

        private void RetainSelection2(object item)
        {

            int index = items.NormalizedIndexOf(item);
            if (index == -1)
            {
                broadcast = true;
                if (item == null)
                {
                    broadcast = false;
                    if (base.SelectedRows.Count > 0)
                    {
                        base.SelectedRows[0].Selected = false;
                    }
                    broadcast = true;
                }
                //else
                //{
                //    SelectRow(0, true); // item will be null while adding a new row
                //}
            }
            else
            {
                ClearSelection();
                SelectRow(index, false);
                broadcast = true;
            }
        }
        # endregion

        # region factory methods to create and add columns
        /// <summary>
        /// Add the sortable checkbox column
        /// </summary>
        /// <param name="property"></param>
        /// <param name="columnName"></param>
        /// <param name="width"></param>
        /// <param name="headerText"></param>
        public DataGridViewCheckBoxColumn AddCheckBoxColumn(string name, string headerText, string dataProperty, int width)
        {
            return AddCheckBoxColumn(name, headerText, dataProperty, width, true);
        }

        public DataGridViewCheckBoxColumn AddCheckBoxColumn(string name, string headerText, string dataProperty, int width, bool enableSort)
        {
            DataGridViewCheckBoxColumn col = new DataGridViewCheckBoxColumn();
            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            if (enableSort)
            {
                col.SortMode = DataGridViewColumnSortMode.Automatic;
            }
            base.Columns.Add(col);

            return col;
        }

        public DataGridViewDisableCheckBoxColumn AddDisableCheckBoxColumn(string name, string headerText, string dataProperty, int width)
        {
            DataGridViewDisableCheckBoxColumn col = new DataGridViewDisableCheckBoxColumn();
            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            base.Columns.Add(col);

            return col;
        }

        /// <summary>
        /// Add the Textbox column
        /// </summary>
        /// <param name="property"></param>
        /// <param name="columnName"></param>
        /// <param name="width"></param>
        /// <param name="headerText"></param>
        public DataGridViewTextBoxColumn AddTextBoxColumn(string name, string headerText, string dataProperty, int width)
        {
            DataGridViewTextBoxColumn col = new DataGridViewTextBoxColumn();
            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            base.Columns.Add(col);
            col.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
            return col;
        }

        /// <summary>
        /// Add the Textbox column
        /// </summary>
        /// <param name="property"></param>
        /// <param name="columnName"></param>
        /// <param name="width"></param>
        /// <param name="headerText"></param>
        public CustomTextBoxColumn AddCustomTextBoxColumn(string name, string headerText, string dataProperty, int width)
        {
            CustomTextBoxColumn col = new CustomTextBoxColumn();

            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            base.Columns.Add(col);
            col.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
            return col;
        }

        /// <summary>
        /// Add the Numeric Textbox column
        /// </summary>
        /// <param name="property"></param>
        /// <param name="columnName"></param>
        /// <param name="width"></param>
        /// <param name="headerText"></param>
        public NumericTextBoxColumn AddNumericTextBoxColumn(string name, string headerText, string dataProperty, int width, int maxLength)
        {
            NumericTextBoxColumn col = new NumericTextBoxColumn(maxLength);

            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            base.Columns.Add(col);
            col.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
            return col;
        }

        public DataGridViewTextBoxColumn AddCustomTextBoxImageColumn(string name, string headerText, string dataProperty, int width)
        {
            CustomTextBoxImageColumn col = new CustomTextBoxImageColumn();
            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            base.Columns.Add(col);
            col.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
            return col;
        }

        /// <summary>
        /// Add the sortable Image column
        /// </summary>
        /// <param name="property"></param>
        /// <param name="columnName"></param>
        /// <param name="width"></param>
        /// <param name="headerText"></param>
        public DataGridViewImageColumn AddImageColumn(string name, string headerText, string dataProperty, int width)
        {
            return AddImageColumn(name, headerText, dataProperty, width, true);
        }

        public DataGridViewImageColumn AddImageColumn(string name, string headerText, string dataProperty, int width, bool enableSort)
        {
            DataGridViewImageColumn col = new DataGridViewImageColumn();
            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            col.Resizable = DataGridViewTriState.False;
            if (enableSort)
            {
                col.SortMode = DataGridViewColumnSortMode.Automatic;
            }
            base.Columns.Add(col);

            return col;
        }
        public DataGridViewComboBoxColumn AddComboBoxColumn(string name, string headerText, string dataProperty, int width)
        {
            DataGridViewComboBoxColumn col = new DataGridViewComboBoxColumn();
            col.Name = name;
            col.HeaderText = headerText;
            col.DataPropertyName = dataProperty;
            col.Width = width;
            col.SortMode = DataGridViewColumnSortMode.Automatic;
            base.Columns.Add(col);
            return col;
        }

        #endregion

        #region Properties

        [DefaultValue("")]
        [DesignerSerializationVisibility(0)]
        [EditorBrowsable(EditorBrowsableState.Never)]
        [Browsable(false)]
        public string Filter
        {
            get { return (items == null) ? string.Empty : items.Filter; }
            set {

                if (items == null) return;

                object item = RetainSelection1();

                items.Filter = value;

                RetainSelection2(item);
            }
        }

        

        public IFunctionCollection Items
        {
            get { return items; }
        }

        public bool SortEnabled
        {
            get { return (items == null) ? false : items.SupportsSorting; }
            set { if (items != null) items.SupportsSorting = value; }
        }

        public RowChangeValidator ChangeValidator
        {
            get { return rowChangeValidator; }
            set { rowChangeValidator = value; }
        }

        public RowSelectionHandler SelectionHandler
        {
            get { return rowSelectionHandler; }
            set
            {
                rowSelectionHandler = value;
                if (rowStateChangeHandler == null) rowStateChangeHandler = new DataGridViewRowStateChangedEventHandler(EIGDataGrid_RowStateChanged);
                RowStateChanged -= rowStateChangeHandler;//Remove the event handler which was previously added;
                RowStateChanged += rowStateChangeHandler;
            }
        }

        public bool ConfirmSelection
        {
            get { return confirmSelection; }
            set { confirmSelection = value; }
        }
        #endregion
    }
}
