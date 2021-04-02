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

namespace McK.EIG.ROI.Client.Request.Model
{
    public class OutputViewDetails
    {
        #region Fields
    
        private bool isHeader;
        private bool isFooter;
        private bool isWatermark;
        private bool isHeaderEnabled;
        private bool isFooterEnabled;        
        private bool isCollate;
        private bool isPageSeparator;
        private int copies;        
        private string watermark;
        private string where;
        private string comment;
        private bool isMRNMasking;
        private int mrnMaskingValue;
       
        #endregion

        #region Properties
      
        public bool IsHeader
        {
            get { return isHeader; }
            set { isHeader = value; }
        }

        public bool IsFooter
        {
            get { return isFooter; }
            set { isFooter = value; }
        }

        public bool IsWatermark
        {
            get { return isWatermark; }
            set { isWatermark = value; }
        }

        public bool IsHeaderEnabled
        {
            get { return isHeaderEnabled; }
            set { isHeaderEnabled = value; }
        }

        public bool IsFooterEnabled
        {
            get { return isFooterEnabled; }
            set { isFooterEnabled = value; }
        }
     
        public string Watermark
        {
            get { return watermark; }
            set { watermark = value; }
        }

        public bool IsCollate
        {
            get { return isCollate; }
            set { isCollate = value; }
        }

        public bool IsPageSeparator
        {
            get { return isPageSeparator; }
            set { isPageSeparator = value; }
        }
        
        public int Copies
        {
            get { return copies; }
            set { copies = value; }
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

        public bool IsMRNMasking
        {
            get { return isMRNMasking; }
            set { isMRNMasking = value; }
        }

        public int MRNMaskingValue
        {
            get { return mrnMaskingValue; }
            set { mrnMaskingValue = value; }
        }

        #endregion
    }
}
