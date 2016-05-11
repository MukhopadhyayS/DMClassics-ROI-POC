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
using System.Windows.Forms;
using System.Drawing;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.Input
{
	internal class ClickColumnState : ColumnState
	{
		private Point _location;

		public ClickColumnState(TreeViewAdv tree, TreeColumn column, Point location)
			: base(tree, column)
		{
			_location = location;
		}

		public override void KeyDown(KeyEventArgs args)
		{
		}

		public override void MouseDown(TreeNodeAdvMouseEventArgs args)
		{
		}

		public override bool MouseMove(MouseEventArgs args)
		{
			if (TreeViewAdv.Dist(_location, args.Location) > TreeViewAdv.ItemDragSensivity
				&& Tree.AllowColumnReorder)
			{
				Tree.Input = new ReorderColumnState(Tree, Column, args.Location);
				Tree.UpdateView();
			}
			return true;
		}

		public override void MouseUp(TreeNodeAdvMouseEventArgs args)
		{
			Tree.ChangeInput();
			Tree.UpdateView();
			Tree.OnColumnClicked(Column);
		}
	}
}
