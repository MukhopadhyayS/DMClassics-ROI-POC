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

using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	internal class IncrementalSearch
	{
		private const int SearchTimeout = 300; //end of incremental search timeot in msec

		private TreeViewAdv _tree;
		private TreeNodeAdv _currentNode;
		private string _searchString = "";
		private DateTime _lastKeyPressed = DateTime.Now;

		public IncrementalSearch(TreeViewAdv tree)
		{
			_tree = tree;
		}

		public void Search(Char value)
		{
			if (!Char.IsControl(value))
			{
				Char ch = Char.ToLowerInvariant(value);
				DateTime dt = DateTime.Now;
				TimeSpan ts = dt - _lastKeyPressed;
				_lastKeyPressed = dt;
				if (ts.TotalMilliseconds < SearchTimeout)
				{
					if (_searchString == value.ToString())
						FirstCharSearch(ch);
					else
						ContinuousSearch(ch);
				}
				else
				{
					FirstCharSearch(ch);
				}
			}
		}

		private void ContinuousSearch(Char value)
		{
			if (value == ' ' && String.IsNullOrEmpty(_searchString))
				return; //Ingnore leading space

			_searchString += value;
			DoContinuousSearch();
		}

		private void FirstCharSearch(Char value)
		{
			if (value == ' ')
				return;

			_searchString = value.ToString();
			TreeNodeAdv node = null;
			if (_tree.SelectedNode != null)
				node = _tree.SelectedNode.NextVisibleNode;
			if (node == null)
				node = _tree.Root;

			foreach (string label in IterateNodeLabels(node))
			{
				if (label.StartsWith(_searchString))
				{
					_tree.SelectedNode = _currentNode;
					return;
				}
			}
		}

		public virtual void EndSearch()
		{
			_currentNode = null;
			_searchString = "";
		}

		protected IEnumerable<string> IterateNodeLabels(TreeNodeAdv start)
		{
			_currentNode = start;
			while(_currentNode != null)
			{
				foreach (string label in GetNodeLabels(_currentNode))
					yield return label;

				_currentNode = _currentNode.NextVisibleNode;
				if (_currentNode == null)
					_currentNode = _tree.Root;

				if (start == _currentNode)
					break;
			} 
		}

		private IEnumerable<string> GetNodeLabels(TreeNodeAdv node)
		{
			foreach (NodeControl nc in _tree.NodeControls)
			{
				BindableControl bc = nc as BindableControl;
				if (bc != null && bc.IncrementalSearchEnabled)
				{
					object obj = bc.GetValue(node);
					if (obj != null)
						yield return obj.ToString().ToLowerInvariant();
				}
			}
		}

		private bool DoContinuousSearch()
		{
			bool found = false;
			if (!String.IsNullOrEmpty(_searchString))
			{
				TreeNodeAdv node = null;
				if (_tree.SelectedNode != null)
					node = _tree.SelectedNode;
				if (node == null)
					node = _tree.Root.NextVisibleNode;

				if (!String.IsNullOrEmpty(_searchString))
				{
					foreach (string label in IterateNodeLabels(node))
					{
						if (label.StartsWith(_searchString))
						{
							found = true;
							_tree.SelectedNode = _currentNode;
							break;
						}
					}
				}
			}
			return found;
		}

	}
}
