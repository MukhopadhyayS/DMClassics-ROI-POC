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
using System.Text;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	public class TreeModelEventArgs: TreePathEventArgs
	{
        //private object[] _children;
        //public object[] Children
        //{
        //    get { return _children; }
        //}

        //private int[] _indexes;
        //public int[] Indexes
        //{
        //    get { return _indexes; }
        //}     

        private object[] _children;
        public object[] Children()
        {
             return _children;
        }

        private int[] _indexes;
        public int[] Indexes()
        {
           return _indexes; 
        }  

		/// <summary>
		/// 
		/// </summary>
		/// <param name="parent">Path to a parent node</param>
		/// <param name="children">Child nodes</param>
		public TreeModelEventArgs(TreePath parent, object[] children)
			: this(parent, null, children)
		{
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="parent">Path to a parent node</param>
		/// <param name="indices">Indices of children in parent nodes collection</param>
		/// <param name="children">Child nodes</param>
		public TreeModelEventArgs(TreePath parent, int[] indexes, object[] children)
			: base(parent)
		{
			if (children == null)
				throw new ArgumentNullException(string.Empty);

			if (indexes != null && indexes.Length != children.Length)
				throw new ArgumentException(string.Empty);

			_indexes = indexes;
			_children = children;
		}
	}
}
