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
using System.Collections.ObjectModel;
using System.Configuration;
using System.IO;
using System.Reflection;
using System.Xml;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.ResourceUtility.Validator
{
    public class ResourceValidator
    {
        XmlElement rootElement;

        public void ValidateResourceFile()
        {
            LoadDoc();
            Console.WriteLine("Client Error Codes validation");
            ValidateClientErrorCodes();
            Console.WriteLine("Server Error Codes validation");
            ValidateServerErrorCodes();
        }

        private void LoadDoc()
        {
            string resourceFilePath = GetResourceFile();
            XmlDocument doc = new XmlDocument();
            doc.Load(resourceFilePath);
            rootElement = doc.DocumentElement;
        }

        private string GetResourceFile()
        {
            switch (GetValue("current.culture"))
            {
                case "en" : return GetValue("roi.messages.en.resource");
                case "fr" : return GetValue("roi.messages.fr.resource");
                default: return null;
            }
        }

        private string GetValue(string key)
        {
            return ConfigurationManager.AppSettings[key];
        }
 
        private void ValidateClientErrorCodes()
        {
            Type type = typeof(ROIErrorCodes);
            string errorCode;
            foreach (FieldInfo field in type.GetFields())
            {
                errorCode = field.GetValue(field.Name).ToString();
                if (!IsErrorCodeExists(errorCode))
                {
                    Console.WriteLine("Resource message not exist for :" + errorCode);
                }
            }
        }

        private void ValidateServerErrorCodes()
        {
            string serverErrorCodesFilePath = GetValue("server.error.codes");
            FileStream fs = new FileStream(serverErrorCodesFilePath, FileMode.Open, FileAccess.ReadWrite);
            StreamReader sr = new StreamReader(fs);

            string line;
            string errorCode;
            int firstIndex;
            int lastIndex;
            while ((line = sr.ReadLine()) != null)
            {
                if (line.IndexOf("(\"") != -1)
                {
                    firstIndex = line.IndexOf("\"");
                    lastIndex  = line.LastIndexOf("\"");
                    errorCode = line.Substring(firstIndex + 1, (lastIndex - firstIndex) - 1);
                    if(!IsErrorCodeExists(errorCode))
                    {
                        Console.WriteLine("Resource message not exist for :" + errorCode);
                    }
                }
            }
            sr.Close();
            fs.Close();
        }

        private bool IsErrorCodeExists(string errorCode)
        {
            XmlElement resourceElement = (XmlElement)rootElement.SelectSingleNode("data[@name='" + errorCode + "']");
            return (resourceElement == null) ? false : true;
        }
    }
}
