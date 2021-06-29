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

namespace McK.EIG.ROI.Client.Base.View.Common
{

    /// <summary>
    /// Implementation of the <see cref="IComparer"/> interface which
    /// compares according to a given <see cref="ListSortDescriptionCollection"/>.
    /// </summary>
    public class GenericComparer : System.Collections.IComparer
    {
        #region Fields

        private ListSortDescriptionCollection _sortDescriptions;

        #endregion

        #region Constructors

        /// <summary>
        /// Creates a new instance.
        /// </summary>
        /// <param name="sortDescriptions">
        /// The <see cref="ListSortDescriptionCollection"/> which should be
        /// used as the bassi for comparison.
        /// </param>
        public GenericComparer() {}

        #endregion

        #region IComparer Member

        /// <summary>
        /// Compares two objects and returns a value indicating whether one is less 
        /// than, equal to, or greater than the other.
        /// </summary>
        /// <param name="x">The first object to compare.</param>
        /// <param name="y">The second object to compare.</param>
        /// <returns></returns>
        public virtual int Compare(object x, object y)
        {

            int result;
            for (int i = 0; i < _sortDescriptions.Count; i++)
            {
                result = CompareProperty(x, y, _sortDescriptions[i]);
                if (result != 0) return result;
            }

            return 0;
        }

        protected virtual int CompareProperty(object value1, object value2, ListSortDescription sortDescription)
        {

            int reverse = (sortDescription.SortDirection == ListSortDirection.Ascending) ? 1 : -1;

            if (IsReverseSort(sortDescription.PropertyDescriptor))
            {
                reverse *= -1;
            }

            object valueX = GetPropertyValue(value1, sortDescription.PropertyDescriptor);
            object valueY = GetPropertyValue(value2, sortDescription.PropertyDescriptor);
            
            bool xIsNull = valueX == DBNull.Value || valueX == null;
            bool yIsNull = valueY == DBNull.Value || valueY == null;

            int result;
            if (xIsNull)
            {
                result = (yIsNull) ? 0 : -1;
            }
            else
            {
                if (yIsNull)
                {
                    result = 1;
                }
                else if (typeof(IComparable).IsAssignableFrom(valueX.GetType()))
                {
                    result = ((IComparable)valueX).CompareTo(valueY);
                }
                else if (valueX.Equals(valueY))
                {
                    result = 0;
                }
                else
                {
                    result = valueX.ToString().CompareTo(valueY.ToString());
                }
            }
            return (result * reverse);
        }

        protected virtual bool IsReverseSort(PropertyDescriptor descriptor)
        {
            // for boolean default sorting needs to be descending - (true) comes first
            return (descriptor.PropertyType == typeof(bool));
        }

        protected virtual object GetPropertyValue(object from, PropertyDescriptor descriptor)
        {
            return descriptor.GetValue(from);
        }
        #endregion

        public void SetSortDescriptions(ListSortDescriptionCollection sortDescriptions)
        {
            _sortDescriptions = sortDescriptions;
        }
    }
}
