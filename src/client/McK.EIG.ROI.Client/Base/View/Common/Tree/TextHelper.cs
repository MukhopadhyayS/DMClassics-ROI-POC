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

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	public static class TextHelper
	{
		public static StringAlignment TranslateAlignment(HorizontalAlignment alignment)
		{
			if (alignment == HorizontalAlignment.Left)
				return StringAlignment.Near;
			else if (alignment == HorizontalAlignment.Right)
				return StringAlignment.Far;
			else
				return StringAlignment.Center;
		}

        public static TextFormatFlags TranslateAlignmentToFlag(HorizontalAlignment alignment)
        {
            if (alignment == HorizontalAlignment.Left)
                return TextFormatFlags.Left;
            else if (alignment == HorizontalAlignment.Right)
                return TextFormatFlags.Right;
            else
                return TextFormatFlags.HorizontalCenter;
        }

		public static TextFormatFlags TranslateTrimmingToFlag(StringTrimming trimming)
		{
			if (trimming == StringTrimming.EllipsisCharacter)
				return TextFormatFlags.EndEllipsis;
			else if (trimming == StringTrimming.EllipsisPath)
				return TextFormatFlags.PathEllipsis;
			if (trimming == StringTrimming.EllipsisWord)
				return TextFormatFlags.WordEllipsis;
			if (trimming == StringTrimming.Word)
				return TextFormatFlags.WordBreak;
			else
				return TextFormatFlags.Default;
		}
	}
}
