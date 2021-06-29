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
using System.Runtime.InteropServices;
using System.Diagnostics.CodeAnalysis;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	/// <summary>
	/// High resolution timer, used to test performance
	/// </summary>
	public static class TimeCounter
	{
		private static Int64 _start;

		/// <summary>
		/// Start time counting
		/// </summary>
		public static void Start()
		{
			_start = 0;
            NativeMethods.QueryPerformanceCounter(ref _start);
		}

		public static Int64 RetrieveStartValue()
		{
			Int64 t = 0;
            NativeMethods.QueryPerformanceCounter(ref t);
			return t;
		}

		/// <summary>
		/// Finish time counting
		/// </summary>
		/// <returns>time in seconds elapsed from Start till Finish	</returns>
		public static double Finish()
		{
			return Finish(_start);
		}

		public static double Finish(Int64 start)
		{
			Int64 finish = 0;
            NativeMethods.QueryPerformanceCounter(ref finish);

			Int64 freq = 0;
            NativeMethods.QueryPerformanceFrequency(ref freq);
			return (finish - start) / (double)freq;
		}

		
	}

    internal static class NativeMethods
    {
        [DllImport("Kernel32.dll")]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool QueryPerformanceCounter(ref Int64 performanceCount);

		[DllImport("Kernel32.dll")]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool QueryPerformanceFrequency(ref Int64 frequency);
    }
}
