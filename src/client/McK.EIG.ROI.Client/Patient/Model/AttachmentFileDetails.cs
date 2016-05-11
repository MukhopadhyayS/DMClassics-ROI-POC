using System;
using System.Globalization;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace McK.EIG.ROI.Client.Patient.Model
{
    [Serializable]
    public class AttachmentFileDetails : BaseRecordItem
    {
        #region fields

        private const string UuidKey = "uuid";
        private const string PageCountKey = "page-count";
        private const string ExtensionKey = "file-ext";
        private const string FileNameKey = "file-name";
        private const string FileTypeKey = "file-type";
        private const string PrintableFlagKey = "printable-flag";
        private const string DateTime24hrFormat = "yyyy/MM/dd HH:mm:ss";
        private const string DateReceivedKey = "date-received";

        private String uuid;
        private int pageCount;
        private String fileName;
        private String extension;
        private String fileType;
        private bool printableFlag;
        private Nullable<DateTime> serviceDate;
        private Nullable<DateTime> dateReceived;

        #endregion


        #region method
        public AttachmentFileDetails()
        {
            extension = "";
            uuid = "";
            fileName = "";
            printableFlag = true;
        }

        private class AttachmentFileDetailsSorter : IComparer<AttachmentFileDetails>
        {
            #region IComparer<AttachmentFileDetails> Members

            public int Compare(AttachmentFileDetails x, AttachmentFileDetails y)
            {
                return x.uuid.CompareTo(y.uuid);
            }

            #endregion
        }

        private static AttachmentFileDetailsSorter sorter;

        public static IComparer<AttachmentFileDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new AttachmentFileDetailsSorter();
                }
                return sorter;
            }
        }

        public override bool Equals(object obj)
        {
            if (object.ReferenceEquals(this, obj)) return true;

            return (this.GetType() == obj.GetType()) &&
                   (Key == ((AttachmentFileDetails)obj).Key);
        }

        public override int GetHashCode()
        {
            return this.GetType().GetHashCode() + Key.GetHashCode();
        }

        public override string Name
        {
            get { return uuid; }
        }

        public override IComparable CompareProperty
        {
            get { return null; }
        }

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            uuid = uuid.Trim();
            extension = extension.Trim();
            fileName = fileName.Trim();
            if (fileType != null)
            {
                fileType = fileType.Trim();
            }
            else
            {
                fileType = string.Empty;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the uuid of a file Attachment
        /// </summary>
        public String Uuid
        {
            get { return uuid; }
            set { uuid = value; }
        }

        /// <summary>
        /// Gets or sets the page count of a file Attachment
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Gets or sets the extension of a file Attachment
        /// </summary>
        public String Extension
        {
            get { return extension; }
            set { extension = value; }
        }

        /// <summary>
        /// Gets or sets the original fileName
        /// </summary>
        public String FileName
        {
            get { return fileName; }
            set { fileName = value; }
        }

        /// <summary>
        /// Gets or sets the fileType
        /// </summary>
        public String FileType
        {
            get { return fileType; }
            set { fileType = value; }
        }


        public override string Key
        {
            get
            {
                return uuid;
            }
        }

        /// <summary>
        /// Gets or sets the printableFlag
        /// </summary>
        public bool Printable
        {
            get { return printableFlag ; }
            set { printableFlag = value; }
        }

        public Nullable<DateTime> ServiceDate
        {
            get { return serviceDate; }
            set { serviceDate = value; }
        }

        public Nullable<DateTime> DateReceived
        {
            get { return dateReceived; }
            set { dateReceived = value; }
        }
        #endregion
    }
}
