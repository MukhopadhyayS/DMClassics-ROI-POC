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

namespace McK.EIG.ROI.Client.Request.Model
{
    [Serializable]
    public class OutputDestinationDetails
    {
        #region Fields

        private int id;
        private string name;
        private string fax;
        private string emailAddr;
        private string media;
        private string Secure;
        private bool isEncryptedSecure;
        private bool SecureRequired;              
        private string status;
        private string type;
        private string where;
        private string comment;
        private string outputNotes;
        private string freeFormNotes;
        private int deviceID;
        private string discType;
        private string templateName;
        private Collection<PropertyDefinition> propertyDefinitions;

        #endregion

        #region Properties

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public string Fax
        {
            get { return fax; }
            set { fax = value; }
        }

        public string EmailAddr
        {
            get { return emailAddr; }
            set { emailAddr = value; }
        }

        public string Media
        {
            get { return media; }
            set { media = value; }
        }

        public string SecuredSecretWord
        {
            get { return Secure; }
            set { Secure = value; }
        }

        public bool IsEncryptedPassword
        {
            get { return isEncryptedSecure; }
            set { isEncryptedSecure = value; }
        }

        public bool PasswordRequired
        {
            get { return SecureRequired; }
            set { SecureRequired = value; }
        }

        public string Status
        {
            get { return status; }
            set { status = value; }
        }

        public string Type
        {
            get { return type; }
            set { type = value; }
        }

        public string Where
        {
            get { return where; }
            set { where = value; }
        }

        public string Comment
        {
            get { return comment; }
            set { comment = value; }
        }

        public Collection<PropertyDefinition> PropertyDefinitions
        {
            get
            {
                if (propertyDefinitions == null)
                {
                    propertyDefinitions = new Collection<PropertyDefinition>();
                }
                return propertyDefinitions;
            }
        }

        public string OutputNotes
        {
            get { return outputNotes; }
            set { outputNotes = value; }
        }

        public int DeviceID
        {
            get { return deviceID; }
            set { deviceID = value; }
        }

        public string DiscType
        {
            get { return discType; }
            set { discType = value; }
        }

        public string TemplateName
        {
            get { return templateName; }
            set { templateName = value; }
        }
        #endregion
    }
}
