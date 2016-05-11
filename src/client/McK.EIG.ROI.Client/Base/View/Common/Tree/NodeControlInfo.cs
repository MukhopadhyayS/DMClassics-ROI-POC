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

using McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	public struct NodeControlInfo
	{
		public static readonly NodeControlInfo Empty = new NodeControlInfo(null, Rectangle.Empty, null);

		private NodeControl _control;
		public NodeControl Control
		{
			get { return _control; }
		}

		private Rectangle _bounds;
		public Rectangle Bounds
		{
			get { return _bounds; }
		}

		private TreeNodeAdv _node;
		public TreeNodeAdv Node
		{
			get { return _node; }
		}

		public NodeControlInfo(NodeControl control, Rectangle bounds, TreeNodeAdv node)
		{
			_control = control;
			_bounds = bounds;
			_node = node;
		}

        public override bool Equals(Object obj)
        {
            return obj is NodeControlInfo && this == (NodeControlInfo)obj;
        }

        public override int GetHashCode()
        {
            return _node.GetHashCode() ^ _bounds.GetHashCode() ^ _control.GetHashCode();
        }

        public static bool operator ==(NodeControlInfo nodeControlInfoX, NodeControlInfo nodeControlInfoY)
        {
            return nodeControlInfoX._node == nodeControlInfoY._node && nodeControlInfoX._bounds == nodeControlInfoY._bounds && nodeControlInfoX._control== nodeControlInfoY._control;
        }

        public static bool operator !=(NodeControlInfo nodeControlInfoX, NodeControlInfo nodeControlInfoY)
        {
            return !(nodeControlInfoX == nodeControlInfoY);
        }

	}
}
