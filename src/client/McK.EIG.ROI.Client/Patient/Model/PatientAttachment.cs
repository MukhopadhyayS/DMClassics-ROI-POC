#region Copyright © 2008 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/*
 * Use of this material is governed by a license agreement. 
 * This material contains confidential, proprietary and trade 
 * secret information of McKesson Information Solutions and is 
 * protected under United States and international copyright and 
 * other intellectual property laws. Use, disclosure, 
 * reproduction, modification, distribution, or storage in a 
 * retrieval system in any form or by any means is prohibited 
 * without the prior express written permission of McKesson 
 * Information Solutions.
 */
#endregion

using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Reflection;
using System.ComponentModel;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.View.PatientRecords;
using McK.EIG.ROI.Client.Patient.View.PatientRecords.Attachments;
using System.Text;

namespace McK.EIG.ROI.Client.Patient.Model
{
    [Serializable]
    public class PatientAttachment : BaseRecordItem
    {
        #region Fields

        #endregion

        #region Constructor 
        
        public PatientAttachment() { }
        public PatientAttachment(PatientDetails patient)
        {
             Parent = patient;
        }

        #endregion

        #region Methods

        public override void AddChild(string key, BaseRecordItem child)
        {
            base.AddChild(key, child);
        }

        public void AddAttachment(AttachmentDetails attachment)
        {
            AttachmentEncounterDetails encounter = AttachmentEncounterDetails.CreateEncounter(attachment);

            if (attachment.Parent != null)
            {
                string exParentKey = attachment.Parent.Key;
                if (exParentKey != encounter.Key && ChildrenKeys.Contains(exParentKey))
                {   
                    GetChild(exParentKey).RemoveChild(attachment);
                }
            }

            BaseRecordItem child = GetChild(encounter.Key);
            if (child == null)
            {
                encounter.AddChild(attachment.Key, attachment);
                AddChild(encounter.Key, encounter);
                encounter.PageCount += attachment.PageCount;
            }
            else
            {
                child.AddChild(attachment.Key, attachment);
                ((AttachmentEncounterDetails)child).PageCount += attachment.PageCount;
                AddChild(child.Key, child);
            }

        }

        public static string PrepareAuditMessage(AttachmentDetails tmpAttachment)
        {
            StringBuilder sb = new StringBuilder();
            ExternalSourceAttachment extSrcAttachment=new ExternalSourceAttachment();

            if (tmpAttachment.Mode == AttachmentMode.Create)
            {
                if (!(tmpAttachment.AttachmentType.Equals((GetEnumDescription(AttachmentHelper.AttachmentLocation.ExternalSourceAttachment)).ToString())))
                sb.Append(tmpAttachment.AttachmentType).Append(" attachment - ").
                   Append(tmpAttachment.Subtitle).Append(" added.");
                else
                    sb.Append(tmpAttachment.Subtitle).Append(" acquired from ").Append(extSrcAttachment.getMappedSource(tmpAttachment.FacilityCode));
            }
            else if (tmpAttachment.Mode == AttachmentMode.Edit)
            {
                sb.Append(tmpAttachment.AttachmentType).Append(" attachment - ").
                   Append(tmpAttachment.Subtitle).Append(" metadata updated.");
            }
            else if (tmpAttachment.Mode == AttachmentMode.Delete)
            {
                sb.Append(tmpAttachment.AttachmentType).Append(" attachment - ").
                                   Append(tmpAttachment.Subtitle).Append(" deleted.");
            }

            return sb.ToString();
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

        #endregion

        #region Properties

        public override System.Drawing.Image Icon
        {
            get { return ROIImages.NonHpfDocIcon; }
        }

        public override string Name
        {
            get { return ROIConstants.Attachment; }
        }

        public override string Key
        {
            get { return ROIConstants.Attachment; }
        }

        public override IComparable CompareProperty
        {
            get { return null; }
        }

        public override IComparer<string> DefaultSorter
        {
            get { return AttachmentEncounterDetails.EncounterSorter; }
        }

        #endregion

    }
}
