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
using System.Drawing;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	internal class FixedRowHeightLayout : IRowLayout
	{
		private TreeViewAdv _treeView;

		public FixedRowHeightLayout(TreeViewAdv treeView, int rowHeight)
		{
			_treeView = treeView;
			PreferredRowHeight = rowHeight;
		}

		private int _rowHeight;
		public int PreferredRowHeight
		{
			get { return _rowHeight; }
			set { _rowHeight = value; }
		}

		public Rectangle GetRowBounds(int rowNo)
		{
			return new Rectangle(0, rowNo * _rowHeight, 0, _rowHeight);
		}

		public int PageRowCount
		{
			get
			{
				return Math.Max((_treeView.DisplayRectangle.Height - _treeView.ColumnHeaderHeight) / _rowHeight, 0);
			}
		}

		public int CurrentPageSize
		{
			get
			{
				return PageRowCount;
			}
		}

		public int GetRowAt(Point point)
		{
			point = new Point(point.X, point.Y + (_treeView.FirstVisibleRow * _rowHeight) - _treeView.ColumnHeaderHeight);
			return point.Y / _rowHeight;
		}

		public int GetFirstRow(int lastPageRow)
		{
			return Math.Max(0, lastPageRow - PageRowCount + 1);
		}

		public void ClearCache()
		{
		}
	}
}
