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
using McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls;


namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	public struct EditorContext
	{
		private TreeNodeAdv _currentNode;
		public TreeNodeAdv CurrentNode
		{
			get { return _currentNode; }
			set { _currentNode = value; }
		}

		private Control _editor;
		public Control Editor
		{
			get { return _editor; }
			set { _editor = value; }
		}

		private NodeControl _owner;
		public NodeControl Owner
		{
			get { return _owner; }
			set { _owner = value; }
		}

		private Rectangle _bounds;
		public Rectangle Bounds
		{
			get { return _bounds; }
			set { _bounds = value; }
		}

		private DrawContext _drawContext;
		public DrawContext DrawContext
		{
			get { return _drawContext; }
			set { _drawContext = value; }
		}

        public override bool Equals(Object obj)
        {
            return obj is EditorContext && this == (EditorContext)obj;
        }

        public override int GetHashCode()
        {
            return _currentNode.GetHashCode() ^ _editor.GetHashCode() ^ _owner.GetHashCode() ^ _bounds.GetHashCode();
        }

        public static bool operator ==(EditorContext editorContextX, EditorContext editorContextY)
        {
            return editorContextX._currentNode == editorContextY._currentNode && editorContextX._editor == editorContextY._editor && editorContextX._owner == editorContextY._owner && editorContextX._bounds == editorContextY._bounds;
        }

        public static bool operator !=(EditorContext editorContextX, EditorContext editorContextY)
        {
            return !(editorContextX == editorContextY);
        }
	}
}
