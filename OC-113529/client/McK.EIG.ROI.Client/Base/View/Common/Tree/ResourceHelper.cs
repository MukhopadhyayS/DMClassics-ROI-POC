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
using System.IO;
using System.Reflection;
using System.Windows.Forms;
using System.Collections.Generic;
using System.Text;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
    public static class ResourceHelper
    {
        // VSpilt Cursor with Innerline (symbolisize hidden column)
        private static Cursor _dVSplitCursor = GetCursor(McK.EIG.ROI.Client.Resources.images.DVSplit);
        public static Cursor DVSplitCursor
        {
            get { return _dVSplitCursor; }
        }

        private static GifDecoder _loadingIcon = GetGifDecoder(McK.EIG.ROI.Client.Resources.images.loading_icon);
		public static GifDecoder LoadingIcon
		{
			get { return _loadingIcon; }
		}

        /// <summary>
        /// Help function to convert byte[] from resource into Cursor Type 
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        private static Cursor GetCursor(byte[] data)
        {
            using (MemoryStream s = new MemoryStream(data))
                return new Cursor(s);
        }

		/// <summary>
		/// Help function to convert byte[] from resource into GifDecoder Type 
		/// </summary>
		/// <param name="data"></param>
		/// <returns></returns>
		private static GifDecoder GetGifDecoder(byte[] data)
		{
			using(MemoryStream ms = new MemoryStream(data))
				return new GifDecoder(ms, true);
		}

    }
}
