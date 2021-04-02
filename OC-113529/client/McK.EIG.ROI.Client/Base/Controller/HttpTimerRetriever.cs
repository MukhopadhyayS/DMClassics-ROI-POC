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
using System.Net;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.Controller
{
	/// <summary>
	/// Summary description for HttpTimeRetriever.
	/// </summary>
	public static class HttpTimerRetriever 
    {
        #region Fields

        private static Log log = LogFactory.GetLogger(typeof(HPFWHelper));
        
        #endregion

        #region Methods

        public static Uri GetTimeserverUrl(Uri url)
        {
			int colonPos = url.ToString().IndexOf(":", 0);
            Uri timeServerUrl = null;
			if (colonPos >= 0) {
				// plus 3 because after : there is // (http://)
				int firstForwardSlashPos = url.ToString().IndexOf("/", colonPos + 3); 
				timeServerUrl = new Uri(url.ToString().Substring(0, firstForwardSlashPos));
			}
			return timeServerUrl;
		}

		public static string GetGmtString(Uri url)
        {
            try
            {
                HttpWebRequest httpReq = (HttpWebRequest)WebRequest.Create(url);
                HttpWebResponse httpResp = (HttpWebResponse)httpReq.GetResponse();
                string date = httpResp.GetResponseHeader("Date");
                httpResp.Close();
                DateTime dateTime = DateTime.Parse(date, System.Threading.Thread.CurrentThread.CurrentUICulture);
                return dateTime.ToUniversalTime().ToString("yyyyMMddHHmmss", System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            catch (WebException webEx)
            {
                log.FunctionFailure(webEx);
                string errorCode = webEx.Message;
                if (webEx.Status == WebExceptionStatus.NameResolutionFailure
                    || webEx.Status == WebExceptionStatus.ConnectFailure)
                {
                    errorCode = (!(url.ToString().IndexOf("roi") > 0)) ? ROIErrorCodes.HpfConnectFailure : ROIErrorCodes.ConnectFailure;
                    webEx.Source = url.ToString();
                }
                else if (webEx.Status == WebExceptionStatus.Timeout)
                {
                    errorCode = ROIErrorCodes.Timeout;
                }
                throw new ROIException(errorCode, webEx);
            }
            finally
            {
                log.ExitFunction();
            }
        }

        #endregion
    }
}
