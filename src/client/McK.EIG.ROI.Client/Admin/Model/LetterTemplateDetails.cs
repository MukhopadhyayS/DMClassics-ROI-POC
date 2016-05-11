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
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.IO;

using McK.EIG.Common.FileTransfer.Model;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// Letter types
    /// </summary>
    public enum LetterType
    {
        [Description("Please Select")]
        None = 0,
        [Description("Cover Letter")]
        CoverLetter = 1,
        [Description("Invoice")]
        Invoice = 2,
        [Description("Pre-Bill")]
        PreBill = 3,
        [Description("Other")]
        Other = 4,
        [Description("Requestor Statement")]
        RequestorStatement = 5,
        [Description("Overdue Invoice") ]
        OverdueInvoice= 6,
    }

    /// <summary>
    /// Class represent the LetterTemplate Details
    /// </summary>
    [Serializable]
    public class LetterTemplateDetails : ROIModel
    {
        [Serializable]
        private class LetterTemplateSorter : IComparer<LetterTemplateDetails>
        {
            #region IComparer<BillingTierDetails> Members

            public int Compare(LetterTemplateDetails x, LetterTemplateDetails y)
            {
                return x.Name.CompareTo(y.Name);
            }

            #endregion
        }

        #region Fields

        private long id;
        private string name;
        private string desc;
        private bool isDefault;
        private LetterType type;
        private string fileName;
        private string filePath;
        private bool doUpload;
        private bool uploadSuccess;
        private bool hasNotes;
        private long documentId;
        private Image image;
        private string letterTypeName;
        private static LetterTemplateSorter sorter;
        
        #endregion

        #region Methods
        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            name = name.Trim();
            desc = Description.Trim();
            if (FilePath != null)
            {
                filePath = FilePath.Trim();
                if (File.Exists(filePath))
                {
                    FileInfo fileInfo = new FileInfo(filePath);
                    fileName = fileInfo.Name;
                }
            }
            else
            {
                filePath = string.Empty;
            }
        }        

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the LetterTemplate Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets the LetterTemplate Name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// This property is used to get or sets the LetterTemplate Description.
        /// </summary>
        public string Description
        {
            get { return CarriageReturn(desc ?? string.Empty); }
            set { desc = value; }
        }

        /// <summary>
        /// This property is used to get or sets isDefault.
        /// </summary>
        public bool IsDefault
        {
            get { return isDefault; }
            set
            {
                isDefault = value;
                image = (isDefault) ? ROIImages.DefaultImage : null;
            }
        }

        /// <summary>
        /// This property is used to get or sets the Letter Types.
        /// </summary>
        public LetterType LetterType
        {
            get { return type; }
            set
            {
                type = value;
                switch (type)
                {
                    case LetterType.CoverLetter: letterTypeName   = EnumUtilities.GetDescription(LetterType.CoverLetter); break;
                    case LetterType.Invoice: letterTypeName = EnumUtilities.GetDescription(LetterType.Invoice); break;
                    case LetterType.PreBill: letterTypeName = EnumUtilities.GetDescription(LetterType.PreBill); break;
                    case LetterType.Other: letterTypeName   = EnumUtilities.GetDescription(LetterType.Other); break;
                    case LetterType.RequestorStatement: letterTypeName = EnumUtilities.GetDescription(LetterType.RequestorStatement); break;
                }
            }
        }

        /// <summary>
        /// This property is used to get or sets the Letter Template FileName
        /// </summary>
        public string FileName
        {
            get { return fileName; }
            set { fileName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Letter Template FilePath
        /// </summary>
        public string FilePath
        {
            get { return filePath; }
            set { filePath = value; }
        }

        /// <summary>
        /// This property is used to get or sets DoUpload.
        /// </summary>
        public bool DoUpload
        {
            get { return doUpload; }
            set { doUpload = value; }
        }

        /// <summary>
        /// This property is used to get or sets IsUploadSuccess.
        /// </summary>
        public bool IsUploadSuccess
        {
            get { return uploadSuccess; }
            set { uploadSuccess = value; }
        }

        /// <summary>
        /// This property is used to get or sets the LetterTemplate DocumentId.
        /// </summary>
        public long DocumentId
        {
            get { return documentId; }
            set { documentId = value; }
        }


        /// <summary>
        /// This property is used to get or sets IsDefault.
        /// </summary>
        public Image Image
        {
            get { return image; }
            set { image = value; }
        }

        /// <summary>
        /// This property is used to get or set the LetterType name in grid.
        /// </summary>
        public string LetterTypeName
        {
            get { return letterTypeName; }
        }

        /// <summary>
        /// User for sorting.
        /// </summary>
        public string CompositeKey
        {
            get { return LetterTypeName + "." + Name + "." + Id.ToString(System.Threading.Thread.CurrentThread.CurrentCulture); }
        }

        /// <summary>
        /// This property is used to get or sets HasNotes.
        /// </summary>
        public bool HasNotes
        {
            get { return hasNotes; }
            set { hasNotes = value; }
        }

        public static IComparer<LetterTemplateDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new LetterTemplateSorter();
                }
                return sorter;
            }
        }

        #endregion
    }
}

