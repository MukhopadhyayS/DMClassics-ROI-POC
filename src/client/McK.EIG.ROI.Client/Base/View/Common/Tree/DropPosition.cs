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
	public struct DropPosition
	{
		private TreeNodeAdv _node;
		public TreeNodeAdv Node
		{
			get { return _node; }
			set { _node = value; }
		}

		private NodePosition _position;
		public NodePosition Position
		{
			get { return _position; }
			set { _position = value; }
		}

        public override bool Equals(Object obj)
        {
            return obj is DropPosition && this == (DropPosition)obj;
        }

        public override int GetHashCode()
        {
            return _node.GetHashCode() ^ _position.GetHashCode();
        }

        public static bool operator ==(DropPosition dropPositionX, DropPosition dropPositionY)
        {
            return dropPositionX._node == dropPositionY._node && dropPositionX._position == dropPositionY._position;
        }

        public static bool operator !=(DropPosition dropPositionX, DropPosition dropPositionY)
        {
            return !(dropPositionX == dropPositionY);
        }
	}
}
