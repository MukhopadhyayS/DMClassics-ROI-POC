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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	internal class TreeColumnCollection : Collection<TreeColumn>
	{
		private TreeViewAdv _treeView;

		public TreeColumnCollection(TreeViewAdv treeView)
		{
			_treeView = treeView;
		}

		protected override void InsertItem(int index, TreeColumn item)
		{
			base.InsertItem(index, item);
			BindEvents(item);
			_treeView.UpdateColumns();
		}

		protected override void RemoveItem(int index)
		{
			UnbindEvents(this[index]);
			base.RemoveItem(index);
			_treeView.UpdateColumns();
		}

		protected override void SetItem(int index, TreeColumn item)
		{
			UnbindEvents(this[index]);
			base.SetItem(index, item);
			item.Owner = this;
			BindEvents(item);
			_treeView.UpdateColumns();
		}

		protected override void ClearItems()
		{
			foreach (TreeColumn c in Items)
				UnbindEvents(c);
			Items.Clear();
			_treeView.UpdateColumns();
		}

		private void BindEvents(TreeColumn item)
		{
			item.Owner = this;
			item.HeaderChanged += HeaderChanged;
			item.IsVisibleChanged += IsVisibleChanged;
			item.WidthChanged += WidthChanged;
			item.SortOrderChanged += SortOrderChanged;
		}

		private void UnbindEvents(TreeColumn item)
		{
			item.Owner = null;
			item.HeaderChanged -= HeaderChanged;
			item.IsVisibleChanged -= IsVisibleChanged;
			item.WidthChanged -= WidthChanged;
			item.SortOrderChanged -= SortOrderChanged;
		}

		void SortOrderChanged(object sender, EventArgs e)
		{
			TreeColumn changed = sender as TreeColumn;
			//Only one column at a time can have a sort property set
			if (changed.SortOrder != SortOrder.None)
			{
				foreach (TreeColumn col in this)
				{
					if (col != changed)
						col.SortOrder = SortOrder.None;
				}
			}
			_treeView.UpdateHeaders();
		}

		void WidthChanged(object sender, EventArgs e)
		{
			_treeView.ChangeColumnWidth(sender as TreeColumn);
		}

		void IsVisibleChanged(object sender, EventArgs e)
		{
			_treeView.FullUpdate();
		}

		void HeaderChanged(object sender, EventArgs e)
		{
			_treeView.UpdateView();
		}
	}
}
