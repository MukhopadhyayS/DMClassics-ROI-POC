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
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Globalization;
using System.Reflection;
using System.Text;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    /// <summary>
    /// ComparableCollection
    /// </summary>
    /// <typeparam name="T"></typeparam>
    public class ComparableCollection<T> : IFunctionCollection, IList<T>
    {
        #region Fields

        private IList _innerList;
        private ListSortDescriptionCollection _sortDescriptions;
        private int[] _sortIndices;
        private int[] _filterIndices;
        private DataTable _filterTable;
        private string _currentFilterExpression = string.Empty;
        private PropertyDescriptorCollection _properties;

        private GenericComparer _comparer;

        #endregion

        #region Constructors

        public ComparableCollection(GenericComparer comparer) : this(null, comparer) { }
        public ComparableCollection() : this(new ArrayList(), null) { }
        public ComparableCollection(IList list) : this(list, null) { }

        /// <summary>
        /// Creates a new instance.
        /// </summary>
        /// <param name="list">The <see cref="InnerList"/> to be used.</param>
        public ComparableCollection(IList list, GenericComparer comparer)
        {
            _innerList = (list == null) ? new List<T>() : list;
            
            _comparer = (comparer == null) ? new GenericComparer() : comparer;

            RemoveSort();

            InitializeFiltering();
        }

        #endregion

        #region Public interface

        /// <summary>
        /// Gets the contained <see cref="IList"/> actually
        /// holding the data.
        /// </summary>
        public IList InnerList
        {
            get { return _innerList; }
        }

        /// <summary>
        /// Raises the <see cref="ListChanged"/> event with <see cref="ListChangedType.Reset"/>.
        /// </summary>
        public void SendListChanged()
        {
            OnListChanged(new ListChangedEventArgs(ListChangedType.Reset, 0));
        }

        /// <summary>
        /// Raises the <see cref="ListChanged"/> event with the specified arguments.
        /// </summary>
        /// <param name="args">Event arguments.</param>
        public void SendListChanged(ListChangedEventArgs args)
        {
            OnListChanged(args);
        }

        #endregion

        #region Protected interface

        /// <summary>
        /// Raises the <see cref="ListChanged"/> event.
        /// </summary>
        /// <param name="args">Event arguments.</param>
        protected virtual void OnListChanged(ListChangedEventArgs args)
        {
            if (ListChanged != null)
                ListChanged(this, args);
        }

        #endregion

        #region Privates

        private void InitializeFiltering()
        {
            _properties = ListBindingHelper.GetListItemProperties(typeof(T));
            _filterTable = new DataTable("FilterTable");
            _filterTable.Locale = CultureInfo.InvariantCulture;
            foreach (PropertyDescriptor property in _properties)
            {
                Type colType = property.PropertyType;
                if (colType.IsGenericType && colType.GetGenericTypeDefinition().Equals(typeof(Nullable<>)))
                    colType = colType.GetGenericArguments()[0];
                _filterTable.Columns.Add(property.Name, colType);
            }
        }

        #endregion

        #region IBindingListView Member

        /// <summary>
        /// Sorts the data source based on the given <see cref="ListSortDescriptionCollection"/>.
        /// </summary>
        /// <param name="sorts">
        /// The <see cref="ListSortDescriptionCollection"/> containing 
        /// the sorts to apply to the data source.
        /// </param>
        public void ApplySort(ListSortDescriptionCollection sorts)
        {
            _sortDescriptions = sorts;
            _sortIndices = new int[_innerList.Count];
            object[] items = new object[_innerList.Count];
            for (int i = 0; i < _sortIndices.Length; i++)
            {
                _sortIndices[i] = i;
                items[i] = _innerList[i];
            }
            _comparer.SetSortDescriptions(sorts);
            Array.Sort(items, _sortIndices, _comparer);
            this.Filter = _currentFilterExpression;
        }

        /// <summary>
        /// Gets or sets the filter to be used to exclude items from the collection 
        /// of items returned by the data source.
        /// </summary>
        public string Filter
        {
            get { return _currentFilterExpression; }
            set
            {
                _filterIndices = null;
                _currentFilterExpression = string.Empty;
                object val = DBNull.Value;

                if (!String.IsNullOrEmpty(value))
                {
                    DataFilter dataFilter = new DataFilter(value, _filterTable);
                    List<int> filteredIndices = new List<int>();

                    int propertiesCount = _properties.Count;
                    DataRow row = _filterTable.NewRow();
                    for (int i = 0; i < Count; i++)
                    {
                        object item = this[i];
                        for (int j = 0; j < propertiesCount; j++)
                        {
                            val = _properties[j].GetValue(item);
                            if (val == null)
                                row[j] = DBNull.Value;
                            else
                                row[j] = val;
                        }


                        if (dataFilter.Invoke(row))
                            filteredIndices.Add(i);
                    }

                    _filterIndices = filteredIndices.ToArray();
                    _currentFilterExpression = value;
                }

                OnListChanged(new ListChangedEventArgs(ListChangedType.Reset, 0));
            }
        }

        /// <summary>
        /// Removes the current filter applied to the data source.
        /// </summary>
        public void RemoveFilter()
        {
            this.Filter = string.Empty;
        }

        /// <summary>
        /// Gets the collection of sort descriptions currently applied to the data source.
        /// </summary>
        public ListSortDescriptionCollection SortDescriptions
        {
            get { return _sortDescriptions; }
        }

        /// <summary>
        /// Gets a value indicating whether the data source supports advanced sorting.
        /// </summary>
        public bool SupportsAdvancedSorting
        {
            get { return true; }
        }

        /// <summary>
        /// Gets a value indicating whether the data source supports filtering.
        /// </summary>
        public bool SupportsFiltering
        {
            get { return true; }
        }

        #endregion

        #region IBindingList Member

        /// <summary>
        /// Occurs when the list changes or an item in the list changes.
        /// </summary>
        public event ListChangedEventHandler ListChanged;

        /// <summary>
        /// Gets whether you can update items in the list.
        /// </summary>
        public bool AllowEdit
        {
            get { return true; }
        }

        /// <summary>
        /// Gets whether you can add items to the list using <see cref="AddNew()"/>.
        /// </summary>
        public bool AllowNew
        {
            get { return false; }
        }

        /// <summary>
        /// Gets whether you can remove items from the list, using 
        /// <see cref="Remove"/> or <see cref="RemoveAt"/>.
        /// </summary>
        public bool AllowRemove
        {
            get { return true; }
        }

        /// <summary>
        /// Sorts the list based on a PropertyDescriptor and a ListSortDirection.
        /// </summary>
        /// <param name="property">The <see cref="PropertyDescriptor"/> to sort by.</param>
        /// <param name="direction">One of the <see cref="ListSortDirection"/> values.</param>
        public void ApplySort(PropertyDescriptor property, ListSortDirection direction)
        {
            ListSortDescription listSortDescription = new ListSortDescription(property, direction);
            ListSortDescription[] listSortDescriptions = new ListSortDescription[] { listSortDescription };
            ListSortDescriptionCollection sorts = new ListSortDescriptionCollection(listSortDescriptions);

            ApplySort(sorts);
        }

        public void SetSortedInfo(PropertyDescriptor property, ListSortDirection direction)
        {
            ListSortDescription listSortDescription = new ListSortDescription(property, direction);
            ListSortDescription[] listSortDescriptions = new ListSortDescription[] { listSortDescription };
            ListSortDescriptionCollection sorts = new ListSortDescriptionCollection(listSortDescriptions);

            _sortDescriptions = sorts;
        }

        /// <summary>
        /// Gets whether the items in the list are sorted.
        /// </summary>
        public bool IsSorted
        {
            get { return _sortDescriptions.Count > 0; }
        }

        /// <summary>
        /// Removes any sort applied using <see cref="ApplySort(PropertyDescriptor, ListSortDirection)"/>.
        /// </summary>
        public void RemoveSort()
        {
            _sortDescriptions = new ListSortDescriptionCollection();
            _sortIndices = null;
        }

        /// <summary>
        /// Gets the direction of the sort.
        /// </summary>
        public ListSortDirection SortDirection
        {
            get { return _sortDescriptions.Count == 1 ? _sortDescriptions[0].SortDirection : ListSortDirection.Ascending; ; }
        }

        /// <summary>
        /// Gets the <see cref="PropertyDescriptor"/> that is being used for sorting.
        /// </summary>
        public PropertyDescriptor SortProperty
        {
            get { return _sortDescriptions.Count == 1 ? _sortDescriptions[0].PropertyDescriptor : null; }
        }

        /// <summary>
        /// Gets whether a <see cref="ListChanged"/> event is raised when the 
        /// list changes or an item in the list changes.
        /// </summary>
        public bool SupportsChangeNotification
        {
            get { return true; }
        }

        /// <summary>
        /// Gets whether the list supports searching using the <see cref="Find"/> method.
        /// </summary>
        public bool SupportsSearching
        {
            get { return false; }
        }

        private bool supportsSorting = true;
        /// <summary>
        /// Gets whether the list supports sorting.
        /// </summary>
        public bool SupportsSorting
        {
            get { return supportsSorting; }
            set {
                supportsSorting = value;
            }
        }

        #region Not Implemented

        /// <summary>
        /// Adds the <see cref="PropertyDescriptor"/> to the indexes used for searching.
        /// NOT IMPLEMENTED!
        /// </summary>
        /// <param name="property">
        /// The <see cref="PropertyDescriptor"/> to add to the indexes used for searching.
        /// </param>
        public void AddIndex(PropertyDescriptor property)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Adds a new item to the list.
        /// NOT IMPLEMENTED!
        /// </summary>
        /// <returns>The item added to the list.</returns>
        public object AddNew()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Returns the index of the row that has the given <see cref="PropertyDescriptor"/>.
        /// NOT IMPLEMENTED!
        /// </summary>
        /// <param name="property">The <see cref="PropertyDescriptor"/> to search on.</param>
        /// <param name="key">The value of the property parameter to search for.</param>
        /// <returns>The index of the row that has the given <see cref="PropertyDescriptor"/>.</returns>
        public int Find(PropertyDescriptor property, object key)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Removes the <see cref="PropertyDescriptor"/> from the indexes used for searching.
        /// NOT IMPLEMENTED!
        /// </summary>
        /// <param name="property">
        /// The <see cref="PropertyDescriptor"/> to remove from the indexes used for searching.
        /// </param>
        public void RemoveIndex(PropertyDescriptor property)
        {
            throw new NotImplementedException();
        }

        #endregion

        #endregion

        #region IList Member

        /// <summary>
        /// Adds an item to the <see cref="IList"/>.  
        /// </summary>
        /// <param name="value">The instance to add to the <see cref="IList"/>.</param>
        /// <returns>The position into which the new element was inserted.</returns>
        public int Add(object value)
        {
            if (value != null && !typeof(T).IsAssignableFrom(value.GetType()))
                throw new ArgumentException("Given instance doesn't match needed type.");

            int result = _innerList.Add(value);
            OnListChanged(new ListChangedEventArgs(ListChangedType.ItemAdded, result));
            return result;
        }

        // Strongly typed members given for IList.
        public int Add(T item)
        {
            return ((IList)this).Add(item);
        }

        /// <summary>
        /// Removes all items from the <see cref="IList"/>.  
        /// </summary>
        public void Clear()
        {
            _innerList.Clear();
            OnListChanged(new ListChangedEventArgs(ListChangedType.Reset, 0));
        }

        /// <summary>
        /// Determines whether the <see cref="IList"/> contains a specific value.
        /// </summary>
        /// <param name="value">The instance to locate in the <see cref="IList"/>.</param>
        /// <returns>true if the instance is found in the <see cref="IList"/>; otherwise, false.</returns>
        public bool Contains(object value)
        {
            return _innerList.Contains(value);
        }

        // Strongly typed members given for IList.
        public bool Contains(T item)
        {
            return ((IList)this).Contains(item);
        }

        /// <summary>
        /// Determines the index of a specific item in the <see cref="IList"/>. 
        /// </summary>
        /// <param name="value">The instance to locate in the <see cref="IList"/>.</param>
        /// <returns>The index of value if found in the list; otherwise, -1.</returns>
        public int IndexOf(object value)
        {
            return _innerList.IndexOf(value);
        }

        // Strongly typed members given for IList.
        public int IndexOf(T item)
        {
            return ((IList)this).IndexOf(item);
        }

        public int NormalizedIndexOf(object item)
        {
            if (item == null) return -1;

            int index = _innerList.IndexOf(item);

            if (_sortIndices != null && index < _sortIndices.Length)
            {
                for (int i = 0; i < _sortIndices.Length; i++)
                {
                    if (_sortIndices[i] == index)
                    {
                        index = i;
                        break;
                    }
                }
            }

            if (_filterIndices != null)
            {
                for (int i = 0; i < _filterIndices.Length; i++)
                {
                    if (_filterIndices[i] == index)
                    {
                        return i;
                    }
                }
                index = -1; // value not found in the filtered items
            }

            return index;
        }

        /// <summary>
        /// Inserts an item to the <see cref="IList"/> at the specified index.
        /// </summary>
        /// <param name="index">The zero-based index at which value should be inserted.</param>
        /// <param name="value">The instance to insert into the <see cref="IList"/>.</param>
        public void Insert(int index, object value)
        {
            if (value != null && !typeof(T).IsAssignableFrom(value.GetType()))
                throw new ArgumentException("Given instance doesn't match needed type.");

            _innerList.Insert(index, value);
            OnListChanged(new ListChangedEventArgs(ListChangedType.ItemAdded, index));
        }
        
        // Strongly typed members given for IList.
        public void Insert(int index, T item)
        {
            ((IList)this).Insert(index, item);
        }

        /// <summary>
        /// Gets a value indicating whether the <see cref="IList"/> has a fixed size. 
        /// </summary>
        public bool IsFixedSize
        {
            get { return _innerList.IsFixedSize; }
        }

        /// <summary>
        /// Gets a value indicating whether the <see cref="IList"/> is read-only.
        /// </summary>
        public bool IsReadOnly
        {
            get { return _innerList.IsReadOnly; }
        }

        private void NormalizeIndices()
        {
            if (_sortIndices != null)
            {
                ApplySort(SortDescriptions); // will take care of _filterIndices too
            }
            else if (_filterIndices != null)
            {
                this.Filter = _currentFilterExpression;
            }
        }

        /// <summary>
        /// Removes the first occurrence of a specific object from the <see cref="IList"/>. 
        /// </summary>
        /// <param name="value">The instance to remove from the <see cref="IList"/>.</param>
        public void Remove(object value)
        {
            int index = IndexOf(value);
            _innerList.Remove(value);
            NormalizeIndices();
            OnListChanged(new ListChangedEventArgs(ListChangedType.ItemDeleted, index));
        }

        // Strongly typed members given for IList.
        public void Remove(T item)
        {
            ((IList)this).Remove(item);
        }

        /// <summary>
        /// Removes the <see cref="IList"/> item at the specified index.
        /// </summary>
        /// <param name="index">The zero-based index of the item to remove.</param>
        public void RemoveAt(int index)
        {
            _innerList.RemoveAt(index);
            NormalizeIndices();
            OnListChanged(new ListChangedEventArgs(ListChangedType.ItemDeleted, index));
        }

        /// <summary>
        /// Gets or sets the element at the specified index.
        /// </summary>
        /// <param name="index">The zero-based index of the element to get or set.</param>
        /// <returns>The element at the specified index.</returns>
        object IList.this[int index]
        {
            get
            {
                return this[index];
            }
            set
            {
                this[index] = (T)value;
            }
        }

        public T this[int index]
        {
            get
            {
                if (_filterIndices != null)
                    index = _filterIndices[index];

                if (_sortIndices != null && index < _sortIndices.Length)
                    index = _sortIndices[index];

                return (T)_innerList[index];
            }
            set
            {
                _innerList[index] = value;
                if (_filterIndices != null)
                {
                    index = NormalizedIndexOf(value);
                }
                if (index >= 0)
                {
                    // when the updated item is filtered
                    OnListChanged(new ListChangedEventArgs(ListChangedType.ItemChanged, index));
                }
            }
        }

        #endregion

        #region ICollection Member

        /// <summary>
        /// Copies the elements of the <see cref="ICollection"/> to an <see cref="Array"/>, 
        /// starting at a particular <see cref="Array"/> index.
        /// </summary>
        /// <param name="array">
        /// The one-dimensional <see cref="Array"/> that is the destination of the elements copied from 
        /// <see cref="ICollection"/>. The <see cref="Array"/> must have zero-based indexing.
        /// </param>
        /// <param name="index">The zero-based index in array at which copying begins.</param>
        public void CopyTo(Array array, int index)
        {
            _innerList.CopyTo(array, index);
        }

        public void CopyTo(T[] array, int arrayIndex)
        {
            ((ICollection)this).CopyTo(array, arrayIndex);
        }

        /// <summary>
        /// Gets the number of elements contained in the <see cref="ICollection"/>. 
        /// </summary>
        public int Count
        {
            get { return _filterIndices == null ? _innerList.Count : _filterIndices.Length; }
        }

        /// <summary>
        /// Gets a value indicating whether access to the <see cref="ICollection"/> is 
        /// synchronized (thread safe). 
        /// </summary>
        public bool IsSynchronized
        {
            get { return _innerList.IsSynchronized; }
        }

        /// <summary>
        /// Gets an object that can be used to synchronize access to the <see cref="ICollection"/>. 
        /// </summary>
        public object SyncRoot
        {
            get { return _innerList.SyncRoot; }
        }

        #endregion


        #region IEnumerable Member

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="IEnumerator"/> object that can be used to iterate 
        /// through the collection.
        /// </returns>
        public IEnumerator GetEnumerator()
        {
            for (int i = 0; i < this.Count; i++)
                yield return this[i];
        }

        #endregion

        //#region ITypedList Member

        ///// <summary>
        ///// Returns the <see cref="PropertyDescriptorCollection"/> that represents the 
        ///// properties on each item used to bind data.
        ///// </summary>
        ///// <param name="listAccessors">
        ///// An array of <see cref="PropertyDescriptor"/> objects to find in the collection 
        ///// as bindable. This can be a null reference.
        ///// </param>
        ///// <returns>The <see cref="PropertyDescriptorCollection"/> that represents the 
        ///// properties on each item used to bind data.</returns>
        //public PropertyDescriptorCollection GetItemProperties(PropertyDescriptor[] listAccessors)
        //{
        //    return ListBindingHelper.GetListItemProperties(typeof(T));
        //}

        ///// <summary>
        ///// Returns the name of the list.
        ///// </summary>
        ///// <param name="listAccessors">
        ///// An array of <see cref="PropertyDescriptor"/> objects, for which the list 
        ///// name is returned. This can be a null reference.
        ///// </param>
        ///// <returns>The name of the list.</returns>
        //public string GetListName(PropertyDescriptor[] listAccessors)
        //{
        //    return this.GetType().Name;
        //}

        //#endregion

        #region IDisposable Members

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            if (disposing)
            {
                this.Dispose();
            }
        }

        #endregion      
      
        #region ICollection<T> Members

        void ICollection<T>.Add(T item)
        {
            ((ICollection<T>)this).Add(item);
        }

        bool ICollection<T>.Remove(T item)
        {
            return ((ICollection<T>)this).Remove(item);
        }

        #endregion

        #region IEnumerable<T> Members

        IEnumerator<T> IEnumerable<T>.GetEnumerator()
        {
            return ((IEnumerable<T>)this).GetEnumerator(); ;
        }

        #endregion
    }


    /// <summary>
    /// Public Wrapper for the internal DataExpression class in the .Net framework.
    /// The purpose of this class is to test if single <see cref="DataRow"/>s match
    /// a given filter expression.
    /// </summary>
    internal class DataFilter
    {
        #region Fields

        private static Type _internalDataFilterType = typeof(DataTable).Assembly.GetType("System.Data.DataExpression");
        private static ConstructorInfo _constructorInfo = _internalDataFilterType.GetConstructor(BindingFlags.NonPublic | BindingFlags.Instance, null, CallingConventions.Any, new Type[] { typeof(DataTable), typeof(string) }, null);
        private static MethodInfo _methodInvokeInfo = _internalDataFilterType.GetMethod("Invoke", BindingFlags.Public | BindingFlags.Instance, null, new Type[] { typeof(DataRow), typeof(DataRowVersion) }, null);
        private object _internalDataFilter;

        #endregion

        #region Constructors

        /// <summary>
        /// Creates a new instance.
        /// </summary>
        /// <param name="expression">Filter expression string.</param>
        /// <param name="dataTable"><see cref="DataTable"/> of the rows to be tested.</param>
        public DataFilter(string expression, DataTable dataTable)
        {
            try
            {
                _internalDataFilter = _constructorInfo.Invoke(new object[] { dataTable, expression });
            }
            catch (System.Reflection.TargetInvocationException invocEx)
            {
                throw invocEx.InnerException;
            }
        }

        #endregion

        #region Public interface

        /// <summary>
        /// Tests whether a single <see cref="DataRow"/> matches the filter expression.
        /// </summary>
        /// <param name="row"><see cref="DataRow"/> to be tested.</param>
        /// <returns>True if the row matches the filter expression, otherwise false.</returns>
        public bool Invoke(DataRow row)
        {
            return Invoke(row, DataRowVersion.Default);
        }

        /// <summary>
        /// Tests whether a single <see cref="DataRow"/> matches the filter expression.
        /// </summary>
        /// <param name="row"><see cref="DataRow"/> to be tested.</param>
        /// <param name="version">The row version to use.</param>
        /// <returns>True if the row matches the filter expression, otherwise false.</returns>
        public bool Invoke(DataRow row, DataRowVersion version)
        {
            return (bool)_methodInvokeInfo.Invoke(_internalDataFilter, new object[] { row, version });
        }

        #endregion
    }
}
