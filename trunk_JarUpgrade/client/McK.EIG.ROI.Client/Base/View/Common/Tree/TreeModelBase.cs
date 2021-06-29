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
	public abstract class TreeModelBase: ITreeModel
	{
		public abstract System.Collections.IEnumerable GetChildren(TreePath treePath);
		public abstract bool IsLeaf(TreePath treePath);


		public event EventHandler<TreeModelEventArgs> NodesChanged;
		protected void OnNodesChanged(TreeModelEventArgs args)
		{
			if (NodesChanged != null)
				NodesChanged(this, args);
		}

		public event EventHandler<TreePathEventArgs> StructureChanged;
		protected void OnStructureChanged(TreePathEventArgs args)
		{
			if (StructureChanged != null)
				StructureChanged(this, args);
		}

		public event EventHandler<TreeModelEventArgs> NodesInserted;
		protected void OnNodesInserted(TreeModelEventArgs args)
		{
			if (NodesInserted != null)
				NodesInserted(this, args);
		}

		public event EventHandler<TreeModelEventArgs> NodesRemoved;
		protected void OnNodesRemoved(TreeModelEventArgs args)
		{
			if (NodesRemoved != null)
				NodesRemoved(this, args);
		}
	}
}
