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
using System.Globalization;
using System.IO;
using System.Reflection;
using System.Xml;
using System.Threading;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.ResourceUtility.Generator
{
    public class ResourceGenerator
    {
        XmlElement enResourceElement;
        XmlElement frResourceElement;

        XmlDocument frResourceDocument;

        public ResourceGenerator()
        {
        }

        public void GenerateResourceFile()
        {
            LoadDoc();
            UpdateFrenchResource();
        }

        private void LoadDoc()
        {
            XmlDocument doc = new XmlDocument();

            string enResourceFilePath = GetValue("roi.en.resource");
            doc.Load(enResourceFilePath);
            enResourceElement = doc.DocumentElement;

            string frResourceFilePath = GetValue("roi.fr.resource");
            frResourceDocument = new XmlDocument();
            frResourceDocument.Load(frResourceFilePath);
            frResourceElement = frResourceDocument.DocumentElement;
        }

        private string GetValue(string key)
        {
            return ConfigurationManager.AppSettings[key];
        }

        private void UpdateFrenchResource()
        {
            bool forceRemove = Convert.ToBoolean(GetValue("force.remove"));
            Console.WriteLine("Start " + DateTime.Now.ToString());
            Console.WriteLine("English resource count :" + enResourceElement.SelectNodes("data").Count);
            foreach (XmlElement data in enResourceElement.SelectNodes("data"))
            {
                UpdateElement(data, forceRemove);
            }
            string name;
            foreach (XmlElement data in frResourceElement.SelectNodes("data"))
            {
                name = data.Attributes["name"].Value;
                XmlElement resourceElement = (XmlElement)enResourceElement.SelectSingleNode("data[@name='" + name + "']");
                if (resourceElement == null)
                {
                    Console.WriteLine("Missing resource " + name);
                }
            }
            Console.WriteLine("French resource count :" + frResourceElement.SelectNodes("data").Count);
            frResourceDocument.Save(GetValue("roi.fr.resource"));
            Console.WriteLine("End " + DateTime.Now.ToString());
        }

        /// <summary>
        /// Update element in french resource
        /// </summary>
        /// <param name="data"></param>
        /// <param name="forceRemove">if true remove the element and add new one</param>
        private void UpdateElement(XmlElement data, bool forceRemove)
        {
            string name = data.Attributes["name"].Value;
            XmlElement resourceElement = (XmlElement)frResourceElement.SelectSingleNode("data[@name='" + name + "']");
            if (resourceElement != null)
            {
                if (forceRemove)
                    frResourceElement.RemoveChild(resourceElement);
                else
                    return;
            }
            GetTranslatedValue(data);
            frResourceElement.AppendChild(frResourceDocument.ImportNode(data, true));
        }
        
        /// <summary>
        /// Method gets the translated value
        /// </summary>
        /// <param name="data"></param>
        private void GetTranslatedValue(XmlElement data)
        {
            //data.SelectSingleNode("value").InnerText = translator.Translate( Language.EnglishTOFrench, data.SelectSingleNode("value").InnerText);
            data.SelectSingleNode("value").InnerText = data.SelectSingleNode("value").InnerText.TrimEnd('.') + ".fr";
        }
    }
}
