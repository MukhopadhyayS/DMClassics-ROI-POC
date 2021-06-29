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
using System.ComponentModel;
using System.Reflection;
using System.Text;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public class PropertyComparer<T> : IComparer<T>
    {
        private PropertyDescriptor sortProperty;
        private ListSortDirection sortDirection;

        public PropertyComparer() { }

        #region IComparer<T>

        public virtual int Compare(T x, T y)
        {
            object value1 = GetPropertyValue(x, sortProperty.Name);
            object value2 = GetPropertyValue(y, sortProperty.Name);

            return CompareValues(value1, value2);
        }

        protected int CompareValues(object value1, object value2)
        {
            int reverse = (sortDirection == ListSortDirection.Ascending) ? 1 : -1;

            // for boolean true should come first. by default false will come first
            reverse *= (value1 is bool || value1 is double) ? -1 : 1;

            int result;

            if ((value1 == null) || (value2 == null))
            {
                result = (value1 != null) ? 1 : ((value2 == null) ? 0 : -1);
            }
            else if (typeof(IComparable).IsAssignableFrom(value1.GetType()))
            {
                result = ((IComparable)value1).CompareTo(value2);
            }
            else if (value1.Equals(value2))
            {
                result = 0;
            }
            else
            {
                result = value1.ToString().CompareTo(value2.ToString());
            }

            return (result * reverse);
        }

        public bool Equals(T value1, T value2)
        {
            return value1.Equals(value2);
        }

        public int GetHashCode(T obj)
        {
            return obj.GetHashCode();
        }

        #endregion

        protected virtual object GetPropertyValue(T from, string propertyName)
        {
            PropertyInfo propertyInfo = from.GetType().GetProperty(propertyName);
            return propertyInfo.GetValue(from, null);
        }

        public PropertyDescriptor SortProperty
        {
            get { return sortProperty; }
            set { sortProperty = value; }
        }

        public ListSortDirection SortDirection
        {
            get { return sortDirection; }
            set { sortDirection = value; }
        }
    }
}
