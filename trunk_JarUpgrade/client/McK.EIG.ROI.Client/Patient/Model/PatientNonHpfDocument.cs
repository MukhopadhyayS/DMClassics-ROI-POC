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
using System.Collections.ObjectModel;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using System.Text;

namespace McK.EIG.ROI.Client.Patient.Model
{
    [Serializable]
    public class PatientNonHpfDocument : BaseRecordItem
    {
        #region Fields

        #endregion

        #region Constructor 
        
        public PatientNonHpfDocument() { }
        public PatientNonHpfDocument(PatientDetails patient)
        {
             Parent = patient;
        }

        #endregion

        #region Methods

        public override void AddChild(string key, BaseRecordItem child)
        {
            //string childKey = GetChildKey(key, new List<string>(ChildrenKeys));
            //if (childKey != null)
            //{
            //    RemoveChild(childKey);
            //}
            base.AddChild(key, child);
        }

        public void AddDocument(NonHpfDocumentDetails document)
        {
            NonHpfEncounterDetails encounter = NonHpfEncounterDetails.CreateNonHpfEncounter(document);

            if (document.Parent != null)
            {
                string exParentKey = document.Parent.Key;
                if (exParentKey != encounter.Key && ChildrenKeys.Contains(exParentKey))
                {   
                    GetChild(exParentKey).RemoveChild(document);
                }
            }

            BaseRecordItem child = GetChild(encounter.Key);
            if (child == null)
            {
                encounter.AddChild(document.Key, document);
                AddChild(encounter.Key, encounter);
                encounter.PageCount += document.PageCount;
            }
            else
            {
                child.AddChild(document.Key, document);
                ((NonHpfEncounterDetails)child).PageCount += document.PageCount;
                AddChild(child.Key, child);
            }

        }

        public static string PrepareAuditMessage(NonHpfDocumentDetails nonHpfDocument, BaseRecordItem item)
        {
            StringBuilder sb = new StringBuilder();
            
            if (nonHpfDocument.Mode == Mode.Create)
            {
                sb.Append("The ").Append(UserData.Instance.UserId).
                   Append(" created new non-HPF document ").
                   Append(nonHpfDocument.Name).Append(".");
            }
            else if (nonHpfDocument.Mode == Mode.Edit)
            {
                NonHpfDocumentDetails oldDocument = null;
                foreach (NonHpfEncounterDetails encounter in item.GetChildren)
                {
                    if (encounter.ChildrenKeys.Contains(nonHpfDocument.Key))
                    {
                        oldDocument = encounter.GetChild(nonHpfDocument.Key) as NonHpfDocumentDetails;
                        break;
                    }
                }

                if (oldDocument != null)
                {
                    sb.Append("The ").Append(UserData.Instance.UserId).Append(" modified the non-HPF document ").
                       Append(oldDocument.Name).Append("-").
                       Append(oldDocument.FacilityCode).Append("-").
                       Append(oldDocument.Department).Append("-").
                       Append(oldDocument.DateOfService.Value.ToShortDateString()).Append("-").
                       Append(oldDocument.Comment).Append(" to ").
                       Append(nonHpfDocument.Name).Append("-").
                       Append(nonHpfDocument.FacilityCode).Append("-").
                       Append(nonHpfDocument.Department).Append("-").
                       Append(nonHpfDocument.DateOfService.Value.ToShortDateString()).Append("-").
                       Append(nonHpfDocument.Comment).Append(".");
                }
            }
            else if(nonHpfDocument.Mode == Mode.Delete)
            {
                sb.Append("The ").
                   Append(UserData.Instance.UserId).
                   Append(" deleted the non-HPF document ").
                   Append(nonHpfDocument.Name);
            }

            return sb.ToString();
        }

        #endregion

        #region Properties

        public override System.Drawing.Image Icon
        {
            get { return ROIImages.NonHpfDocIcon; }
        }

        public override string Name
        {
            get { return ROIConstants.NonHpfDocument; }
        }

        public override string Key
        {
            get { return ROIConstants.NonHpfDocument; }
        }

        public override IComparable CompareProperty
        {
            get { return null; }
        }

        public override IComparer<string> DefaultSorter
        {
            get { return NonHpfEncounterDetails.EncounterSorter; }
        }

        #endregion

    }
}
