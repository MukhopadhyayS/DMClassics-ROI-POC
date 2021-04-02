using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using System.ComponentModel;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    class AttachmentHelper
    {
        /// <summary>
        /// Defines list of all attachments
        /// </summary>
        public enum AttachmentType
        {
            [Description("--Select Attachment Type--")]
            None = 0,
            [Description("CCR/CCD")]
            CCRCCD = 1,
           // [Description("File")]
           // FileAttachment = 2,

        }

        public enum AttachmentLocation
        {
            [Description("--Select--")]
            None = 0,
            [Description("Local File")]
            LocalFileAttachment = 1,
            [Description("Query Clinical System")]
            ExternalSourceAttachment = 2,

        }
        /// <summary>
        /// Gets the attachment criteria UI for the selected attachment type
        /// </summary>
        /// <param name="attachmentType"></param>
        /// <returns></returns>
        public static IAttachmentDetailUI GetAttachmentDetailUI(AttachmentType attachmentType,
                                                                bool editMode,
                                                                bool isReleased)
        {
            switch (attachmentType)
            {
                //case AttachmentType.FileAttachment: { return new Attachments.FileAttachment(editMode, isReleased); }
                case AttachmentType.CCRCCD: { return new Attachments.CcrCcd(editMode, isReleased); }

                default: return null;
            }
        }

        public static IAttachmentDetailUI GetAttachmentDetailUI(AttachmentLocation attachmentLocation,
                                                                bool editMode,
                                                                bool isReleased)
        {
            switch (attachmentLocation)
            {
                case AttachmentLocation.ExternalSourceAttachment: { return new Attachments.ExternalSourceAttachment(editMode, isReleased); }
                case AttachmentLocation.LocalFileAttachment: { return new Attachments.CcrCcd(editMode, isReleased); }

                default: return null;
            }
        }

        /// <summary>
        /// Gets the attachmentDeatails for the selected attachment object
        /// </summary>
        /// <param name="attachmentDetails"></param>
        /// <returns></returns>
        public static AttachmentDetails GetAttachmentDetails(AttachmentDetails attachment)
        {
            AttachmentType attachType = (AttachmentType)Enum.Parse(typeof(AttachmentType), attachment.AttachmentType, true);

            switch (attachType)
            {
                //case AttachmentType.FileAttachment: { return attachment; }
                case AttachmentType.CCRCCD: { return new CcrCcdDetails(attachment); }

                default: return null;
            }
        }

        public static KeyValuePair<Enum, string> GetEnum(Enum value)
        {
            return new KeyValuePair<Enum, string>(value, McK.EIG.ROI.Client.Base.Model.EnumUtilities.GetDescription(value));
        }

        public static string GetEnumDescription(Enum value)
        {
            FieldInfo fi = value.GetType().GetField(value.ToString());
            DescriptionAttribute[] attributes = (DescriptionAttribute[])fi.GetCustomAttributes(typeof(DescriptionAttribute), false);
            if (attributes != null && attributes.Length > 0)
                return attributes[0].Description;
            else
                return value.ToString();
        }
    }
}
