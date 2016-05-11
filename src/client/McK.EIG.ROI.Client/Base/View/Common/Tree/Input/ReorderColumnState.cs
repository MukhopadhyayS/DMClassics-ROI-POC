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
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.Input
{
	internal class ReorderColumnState : ColumnState
	{
		#region Properties

		private Point _location;
		public Point Location
		{
			get { return _location; }
		}

		private Bitmap _ghostImage;
		public Bitmap GhostImage
		{
			get { return _ghostImage; }
		}

		private TreeColumn _dropColumn;
		public TreeColumn DropColumn
		{
			get { return _dropColumn; }
		}

		private int _dragOffset;
		public int DragOffset
		{
			get { return _dragOffset; }
		}

		#endregion

		public ReorderColumnState(TreeViewAdv tree, TreeColumn column, Point initialMouseLocation)
			: base(tree, column)
		{
			_location = new Point(initialMouseLocation.X + Tree.OffsetX, 0);
			_dragOffset = tree.GetColumnX(column) - initialMouseLocation.X;
			_ghostImage = column.CreateGhostImage(new Rectangle(0, 0, column.Width, tree.ColumnHeaderHeight), tree.Font);
		}

		public override void KeyDown(KeyEventArgs args)
		{
			args.Handled = true;
			if (args.KeyCode == Keys.Escape)
				FinishResize();
		}

		public override void MouseDown(TreeNodeAdvMouseEventArgs args)
		{
		}

		public override void MouseUp(TreeNodeAdvMouseEventArgs args)
		{
			FinishResize();
		}

		public override bool MouseMove(MouseEventArgs args)
		{
			_dropColumn = null;
			_location = new Point(args.X + Tree.OffsetX, 0);
			int x = 0;
			foreach (TreeColumn c in Tree.Columns)
			{
				if (c.IsVisible)
				{
					if (_location.X < x + c.Width / 2)
					{
						_dropColumn = c;
						break;
					}
					x += c.Width;
				}
			}
			Tree.UpdateHeaders();
			return true;
		}

		private void FinishResize()
		{
			Tree.ChangeInput();
			if (Column == DropColumn)
				Tree.UpdateView();
			else
			{
				Tree.Columns.Remove(Column);
				if (DropColumn == null)
					Tree.Columns.Add(Column);
				else
					Tree.Columns.Insert(Tree.Columns.IndexOf(DropColumn), Column);

				Tree.OnColumnReordered(Column);
			}
		}
	}
}
