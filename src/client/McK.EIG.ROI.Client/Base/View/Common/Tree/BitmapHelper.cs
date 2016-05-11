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
using System.Runtime.InteropServices;
using System.Drawing.Imaging;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	public static class BitmapHelper
	{
		[StructLayout(LayoutKind.Sequential)]
		private struct PixelData
		{
			public byte B;
			public byte G;
			public byte R;
			public byte A;
		}

		public static void SetAlphaChannelValue(Bitmap image)
		{
			if (image == null)
				throw new ArgumentNullException(string.Empty);
			if (image.PixelFormat != PixelFormat.Format32bppArgb)
                throw new ArgumentException(string.Empty);

			BitmapData bitmapData = image.LockBits(new Rectangle(0, 0, image.Width, image.Height),
									 ImageLockMode.ReadWrite, PixelFormat.Format32bppArgb);
            //unsafe
            //{
            //    PixelData* pPixel = (PixelData*)bitmapData.Scan0;
            //    for (int i = 0; i < bitmapData.Height; i++)
            //    {
            //        for (int j = 0; j < bitmapData.Width; j++)
            //        {
            //            pPixel->A = value;
            //            pPixel++;
            //        }
            //        pPixel += bitmapData.Stride - (bitmapData.Width * 4);
            //    }
            //}
			image.UnlockBits(bitmapData);
		}
	}
}
