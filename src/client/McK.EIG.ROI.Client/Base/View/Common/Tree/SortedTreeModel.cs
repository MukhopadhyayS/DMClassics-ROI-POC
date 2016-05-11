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
using System.Collections;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	public class SortedTreeModel: TreeModelBase
	{
		private ITreeModel _innerModel;
		public ITreeModel InnerModel
		{
			get { return _innerModel; }
		}

		private IComparer _comparer;
		public IComparer Comparer
		{
			get { return _comparer; }
			set 
			{ 
				_comparer = value;
				OnStructureChanged(new TreePathEventArgs(TreePath.Empty));
			}
		}

		public SortedTreeModel(ITreeModel innerModel)
		{
			_innerModel = innerModel;
			_innerModel.NodesChanged += new EventHandler<TreeModelEventArgs>(_innerModel_NodesChanged);
			_innerModel.NodesInserted += new EventHandler<TreeModelEventArgs>(_innerModel_NodesInserted);
			_innerModel.NodesRemoved += new EventHandler<TreeModelEventArgs>(_innerModel_NodesRemoved);
			_innerModel.StructureChanged += new EventHandler<TreePathEventArgs>(_innerModel_StructureChanged);
		}

		void _innerModel_StructureChanged(object sender, TreePathEventArgs e)
		{
			OnStructureChanged(e);
		}

		void _innerModel_NodesRemoved(object sender, TreeModelEventArgs e)
		{
			OnStructureChanged(new TreePathEventArgs(e.Path));
		}

		void _innerModel_NodesInserted(object sender, TreeModelEventArgs e)
		{
			OnStructureChanged(new TreePathEventArgs(e.Path));
		}

		void _innerModel_NodesChanged(object sender, TreeModelEventArgs e)
		{
			OnStructureChanged(new TreePathEventArgs(e.Path));
		}

		public override IEnumerable GetChildren(TreePath treePath)
		{
			if (Comparer != null)
			{
				ArrayList list = new ArrayList();
				IEnumerable res = InnerModel.GetChildren(treePath);
				if (res != null)
				{
					foreach (object obj in res)
						list.Add(obj);
					list.Sort(Comparer);
					return list;
				}
				else
					return null;
			}
			else
				return InnerModel.GetChildren(treePath);
		}

		public override bool IsLeaf(TreePath treePath)
		{
			return InnerModel.IsLeaf(treePath);
		}
	}
}
