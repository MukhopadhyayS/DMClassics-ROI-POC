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
using System.Globalization;
using System.Net;
using System.Text;

namespace McK.EIG.ROI.Client.Base.Model
{
    /// <summary>
    /// Log Event.
    /// </summary>
    public class LogEvent
    {

        #region Fields

        private const string AppID = "1";

        private const string TagLogEvent           = "log-event";
        private const string TagAppId             = "app-iD";
        private const string TagIPAddress         = "IPAddress";
        private const string TagTimeStamp         = "timestamp";
        private const string TagAuthUser          = "auth-user";
        private const string TagCode              = "code";
        private const string TagMessage           = "message";
        private const string TagDetails           = "details";
        
        private DateTime timestamp = DateTime.Now;
        private string authUser = string.Empty;
        private string code = string.Empty;
        private string message = string.Empty;
        private string details = string.Empty;
        
        #endregion

        #region Method

        public String ToXml()
        {
            string startTag = "<";
            string slash = "/";
            string endtag = ">";
            
            return new StringBuilder().Append(startTag).Append(LogEvent.TagLogEvent).Append(endtag).
                                       Append(startTag).Append(LogEvent.TagAuthUser).Append(endtag).Append(StripCharacters(AuthUser)).Append(startTag).Append(slash).Append(LogEvent.TagAuthUser).Append(endtag).
                                       Append(startTag).Append(LogEvent.TagDetails).Append(endtag).Append(StripCharacters(Details)).Append(startTag).Append(slash).Append(LogEvent.TagDetails).Append(endtag).
                                       Append(startTag).Append(LogEvent.TagIPAddress).Append(endtag).Append(StripCharacters(CurrentIPAddress())).Append(startTag).Append(slash).Append(LogEvent.TagIPAddress).Append(endtag).
                                       Append(startTag).Append(LogEvent.TagCode).Append(endtag).Append(StripCharacters(Code)).Append(startTag).Append(slash).Append(LogEvent.TagCode).Append(endtag).
                                       Append(startTag).Append(LogEvent.TagMessage).Append(endtag).Append(StripCharacters(Message)).Append(startTag).Append(slash).Append(LogEvent.TagMessage).Append(endtag).
                                       Append(startTag).Append(LogEvent.TagAppId).Append(endtag).Append(StripCharacters(AppID)).Append(startTag).Append(slash).Append(LogEvent.TagAppId).Append(endtag).
                                       Append(startTag).Append(LogEvent.TagTimeStamp).Append(endtag).Append(StripCharacters(Timestamp.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture))).Append(startTag).Append(slash).Append(LogEvent.TagTimeStamp).Append(endtag).
                                       Append(startTag).Append(slash).Append(LogEvent.TagLogEvent).Append(endtag).ToString();

        }



        private static String StripCharacters(String xmlData)
        {
            return xmlData.Replace("&", "&amp;").
                       Replace(">", "&gt;").
                       Replace("<", "&lt;").
                       Replace("'", "&quot;");
        }


        #endregion

        #region Properties

        public static  string CurrentIPAddress()
        {
            IPHostEntry entry = Dns.GetHostEntry(Dns.GetHostName());
            return entry.AddressList[0].ToString();
        }

        public string AuthUser
        {
            get
            {
                return authUser;
            }
            set
            {
                authUser = value;
            }
        }
        public string Code
        {
            get
            {
                return code;
            }
            set
            {
                code = value;
            }
        }
        public string Message
        {
            get
            {
                return message;
            }
            set
            {
                message = value;
            }
        }
        public string Details
        {
            get
            {
                return details;
            }
            set
            {
                details = value;
            }
        }

        public DateTime Timestamp
        {
            get
            {
                return timestamp;
            }
        }


        #endregion

    }
}
