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

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	/// <summary>
	/// Provides a simple ready to use implementation of ITreeModel. Warning: this class is not optimized 
	/// to work with big amount of data. In this case create you own implementation of ITreeModel, and pay attention
	/// on GetChildren and IsLeaf methods.
	/// </summary>
	public class TreeModel : ITreeModel
	{
		private Node _root;
		public Node Root
		{
			get { return _root; }
		}

		public Collection<Node> Nodes
		{
			get { return _root.Nodes; }
		}

		public TreeModel()
		{
			_root = new Node();
			_root.Model = this;
		}

		public TreePath GetPath(Node node)
		{
			if (node == _root)
				return TreePath.Empty;
			else
			{
				Stack<object> stack = new Stack<object>();
				while (node != _root)
				{
					stack.Push(node);
					node = node.Parent;
				}
				return new TreePath(stack.ToArray());
			}
		}

		public Node FindNode(TreePath path)
		{
			if (path.IsEmpty())
				return _root;
			else
				return FindNode(_root, path, 0);
		}

		private Node FindNode(Node root, TreePath path, int level)
		{
			foreach (Node node in root.Nodes)
				if (node == path.FullPath()[level])
				{
					if (level == path.FullPath().Length - 1)
						return node;
					else
						return FindNode(node, path, level + 1);
				}
			return null;
		}

		#region ITreeModel Members

		public System.Collections.IEnumerable GetChildren(TreePath treePath)
		{
			Node node = FindNode(treePath);
			if (node != null)
				foreach (Node n in node.Nodes)
					yield return n;
			else
				yield break;
		}

		public bool IsLeaf(TreePath treePath)
		{
			Node node = FindNode(treePath);
			if (node != null)
				return node.IsLeaf;
			else
				throw new ArgumentException("treePath");
		}

		public event EventHandler<TreeModelEventArgs> NodesChanged;
		internal void OnNodesChanged(TreeModelEventArgs args)
		{
			if (NodesChanged != null)
				NodesChanged(this, args);
		}

		public event EventHandler<TreePathEventArgs> StructureChanged;
		public void OnStructureChanged(TreePathEventArgs args)
		{
			if (StructureChanged != null)
				StructureChanged(this, args);
		}

		public event EventHandler<TreeModelEventArgs> NodesInserted;
		internal void OnNodeInserted(Node parent, int index, Node node)
		{
			if (NodesInserted != null)
			{
				TreeModelEventArgs args = new TreeModelEventArgs(GetPath(parent), new int[] { index }, new object[] { node });
				NodesInserted(this, args);
			}

		}

		public event EventHandler<TreeModelEventArgs> NodesRemoved;
		internal void OnNodeRemoved(Node parent, int index, Node node)
		{
			if (NodesRemoved != null)
			{
				TreeModelEventArgs args = new TreeModelEventArgs(GetPath(parent), new int[] { index }, new object[] { node });
				NodesRemoved(this, args);
			}
		}

		#endregion
	}
}
