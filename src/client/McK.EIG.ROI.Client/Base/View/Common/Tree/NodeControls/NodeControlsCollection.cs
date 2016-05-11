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
using System.ComponentModel.Design;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Drawing.Design;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls
{
	internal class NodeControlsCollection : Collection<NodeControl>
	{
		private TreeViewAdv _tree;

		public NodeControlsCollection(TreeViewAdv tree)
		{
			_tree = tree;
		}

		protected override void ClearItems()
		{
			_tree.BeginUpdate();
			try
			{
				while (this.Count != 0)
					this.RemoveAt(this.Count - 1);
			}
			finally
			{
				_tree.EndUpdate();
			}
		}

		protected override void InsertItem(int index, NodeControl item)
		{
			if (item == null)
				throw new ArgumentNullException("item");

			if (item.Parent != _tree)
			{
				if (item.Parent != null)
				{
					item.Parent.NodeControls.Remove(item);
				}
				base.InsertItem(index, item);
				item.AssignParent(_tree);
				_tree.FullUpdate();
			}
		}

		protected override void RemoveItem(int index)
		{
			NodeControl value = this[index];
			value.AssignParent(null);
			base.RemoveItem(index);
			_tree.FullUpdate();
		}

		protected override void SetItem(int index, NodeControl item)
		{
			if (item == null)
				throw new ArgumentNullException("item");

			_tree.BeginUpdate();
			try
			{
				RemoveAt(index);
				InsertItem(index, item);
			}
			finally
			{
				_tree.EndUpdate();
			}
		}
	}

	internal class NodeControlCollectionEditor : CollectionEditor
	{
		private Type[] _types;

		public NodeControlCollectionEditor(Type type)
			: base(type)
		{
			_types = new Type[] { typeof(NodeTextBox), typeof(NodeIntegerTextBox), typeof(NodeDecimalTextBox), 
				typeof(NodeComboBox), typeof(NodeCheckBox),
				typeof(NodeStateIcon), typeof(NodeIcon), typeof(NodeNumericUpDown), typeof(ExpandingIcon)  };
		}

		protected override System.Type[] CreateNewItemTypes()
		{
			return _types;
		}
	}
}
