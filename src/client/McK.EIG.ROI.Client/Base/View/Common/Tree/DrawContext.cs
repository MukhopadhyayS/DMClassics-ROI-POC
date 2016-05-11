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
	public struct DrawContext
	{
		private Graphics _graphics;
		public Graphics Graphics
		{
			get { return _graphics; }
			set { _graphics = value; }
		}

		private Rectangle _bounds;
		public Rectangle Bounds
		{
			get { return _bounds; }
			set { _bounds = value; }
		}

		private Font _font;
		public Font Font
		{
			get { return _font; }
			set { _font = value; }
		}

		private DrawSelectionMode _drawSelection;
		public DrawSelectionMode DrawSelection
		{
			get { return _drawSelection; }
			set { _drawSelection = value; }
		}

		private bool _drawFocus;
		public bool DrawFocus
		{
			get { return _drawFocus; }
			set { _drawFocus = value; }
		}

		private NodeControl _currentEditorOwner;
		public NodeControl CurrentEditorOwner
		{
			get { return _currentEditorOwner; }
			set { _currentEditorOwner = value; }
		}

		private bool _enabled;
		public bool Enabled
		{
			get { return _enabled; }
			set { _enabled = value; }
		}

        // Implemented equals and operator equals for FXCop rule CA1815  
        // OverrideEqualsAndOperatorEqualsOnValueTypes
        public override bool Equals(object obj)
        {
            return base.Equals(obj);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public static bool operator ==(DrawContext p1, DrawContext p2)
        {
            return (p1 != null) &&  p1.Equals(p2);
        }
        // The C# compiler and rule OperatorsShouldHaveSymmetricalOverloads require this.
        public static bool operator !=(DrawContext p1, DrawContext p2)
        {
            return !(p1 == p2);
        }

	}
}
