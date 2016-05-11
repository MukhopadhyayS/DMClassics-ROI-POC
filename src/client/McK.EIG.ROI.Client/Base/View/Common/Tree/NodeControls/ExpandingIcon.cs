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
using System.Threading;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls
{
	/// <summary>
	/// Displays an animated icon for those nodes, who are in expanding state. 
	/// Parent TreeView must have AsyncExpanding property set to true.
	/// </summary>
	public class ExpandingIcon: NodeControl
	{
		private static GifDecoder _gif = ResourceHelper.LoadingIcon;
        private static int _index;
		private static Thread _animatingThread;

		public override Size MeasureSize(TreeNodeAdv node, DrawContext context)
		{
			return ResourceHelper.LoadingIcon.FrameSize;
		}

		protected override void OnIsVisibleValueNeeded(NodeControlValueEventArgs args)
		{
			args.Value = args.Node.IsExpandingNow;
			base.OnIsVisibleValueNeeded(args);
		}

		public override void Draw(TreeNodeAdv node, DrawContext context)
		{
			Rectangle rect = GetBounds(node, context);
			Image img = _gif.GetFrame(_index).Image;
			context.Graphics.DrawImage(img, rect.Location);
		}

		public static void Start()
		{
			_index = 0;
			if (_animatingThread == null)
			{
				_animatingThread = new Thread(new ThreadStart(IterateIcons));
				_animatingThread.IsBackground = true;
				_animatingThread.Priority = ThreadPriority.Lowest;
				_animatingThread.Start();
			}
		}

		private static void IterateIcons()
		{
			while (true)
			{
				if (_index < _gif.FrameCount - 1)
					_index++;
				else
					_index = 0;

				if (IconChanged != null)
					IconChanged(null, EventArgs.Empty);

				int delay = _gif.GetFrame(_index).Delay;
				Thread.Sleep(delay);
			}
		}

		public static event EventHandler IconChanged;
	}
}
