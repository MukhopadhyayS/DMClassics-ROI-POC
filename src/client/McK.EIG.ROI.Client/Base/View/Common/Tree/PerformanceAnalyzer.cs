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
using System.Globalization;
using System.Text;
using System.Diagnostics;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{

		public class PerformanceInfo
		{
			private string _name;
			public string Name
			{
				get { return _name; }
			}

            private int _count;
			public int Count
			{
				get { return _count; }
				set { _count = value; }
			}

            private double _totalTime;
			public double TotalTime
			{
				get { return _totalTime; }
				set { _totalTime = value; }
			}

			private Int64 _start;
			public Int64 Start
			{
				get { return _start; }
				set { _start = value; }
			}

			public PerformanceInfo(string name)
			{
				_name = name;
			}
		}

        /// <summary>
        /// Is used to analyze code performance
        /// </summary>
        public static class PerformanceAnalyzer
        {

		    private static Dictionary<string, PerformanceInfo> _performances = new Dictionary<string, PerformanceInfo>();

		    public static IEnumerable<PerformanceInfo> Performances
		    {
			    get
			    {
				    return _performances.Values;
			    }
		    }

		    [Conditional("DEBUG")]
		    public static void Start(string pieceOfCode)
		    {
			    PerformanceInfo info = null;
			    lock(_performances)
			    {
				    if (_performances.ContainsKey(pieceOfCode))
					    info = _performances[pieceOfCode];
				    else
				    {
					    info = new PerformanceInfo(pieceOfCode);
					    _performances.Add(pieceOfCode, info);
				    }

				    info.Count++;
				    info.Start = TimeCounter.RetrieveStartValue();
			    }
		    }

		    [Conditional("DEBUG")]
		    public static void Finish(string pieceOfCode)
		    {
			    lock (_performances)
			    {
				    if (_performances.ContainsKey(pieceOfCode))
				    {
					    PerformanceInfo info = _performances[pieceOfCode];
					    info.Count++;
					    info.TotalTime += TimeCounter.Finish(info.Start);
				    }
			    }
		    }

		    public static void Reset()
		    {
			    _performances.Clear();
		    }

		    public static string GenerateReport()
		    {
			    return GenerateReport(0);
		    }

		    public static string GenerateReport(string mainPieceOfCode)
		    {
			    if (_performances.ContainsKey(mainPieceOfCode))
				    return GenerateReport(_performances[mainPieceOfCode].TotalTime);
			    else
				    return GenerateReport(0);
		    }

		    public static string GenerateReport(double totalTime)
		    {
			    StringBuilder sb = new StringBuilder();
			    int len = 0;
			    foreach (PerformanceInfo info in Performances)
				    len = Math.Max(info.Name.Length, len);

			    sb.AppendLine("Name".PadRight(len) + " Count              Time, ms           Percentage, %");
			    sb.AppendLine("---------------------------------------------------------------------------------");
			    foreach (PerformanceInfo info in Performances)
			    {
				    sb.Append(info.Name.PadRight(len));
				    double p = 0;
				    if (totalTime != 0)
					    p = info.TotalTime / totalTime;
				    string c = info.Count.ToString("0,0",System.Threading.Thread.CurrentThread.CurrentUICulture).PadRight(20);
				    string t = (info.TotalTime * 1000).ToString("0,0.00",System.Threading.Thread.CurrentThread.CurrentUICulture).PadRight(20);
				    string sp = (p * 100).ToString("###",System.Threading.Thread.CurrentThread.CurrentUICulture).PadRight(20);
				    sb.AppendFormat(" " + c + t + sp + "\n");
			    }
			    return sb.ToString();
		    }
	}
}
