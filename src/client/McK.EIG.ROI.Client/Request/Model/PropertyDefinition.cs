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
using System.Collections;

namespace McK.EIG.ROI.Client.Request.Model
{
    public class PropertyDefinition
    {  
        #region Fields

        private string label;
        private string propertyName;
        private string description;
        private string dataType;
        private string defaultValue;
        private string selectedValue;
        private ArrayList possibleValues;

        #endregion

        #region Constructor

        public PropertyDefinition()
        {
        }

        public PropertyDefinition(string propertyName, string defaultValue)
        {
            PropertyName = propertyName;
            DefaultValue = defaultValue;
        }

        #endregion

        #region Properties

        public string Label
        {
            get { return label; }
            set { label = value; }
        }

        public string PropertyName
        {
            get { return propertyName; }
            set { propertyName = value; }
        }

        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public string DataType
        {
            get { return dataType; }
            set { dataType = value; }
        }

        public string DefaultValue
        {
            get { return defaultValue; }
            set { defaultValue = value; }
        }

        public string SelectedValue
        {
            get { return selectedValue; }
            set { selectedValue = value; }
        }

        public ArrayList PossibleValues
        {
            get
            {
                if (possibleValues == null)
                {
                    possibleValues = new ArrayList();
                }
                return possibleValues;
            }            
        }

        public override bool Equals(object obj)
        {

            if (!(obj is PropertyDefinition)) return false;

            return this.PropertyName == ((PropertyDefinition)obj).PropertyName;

        }

        #endregion
    }
}
