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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Windows.Forms;
using System.Drawing;

using McK.EIG.ROI.Client.Base.View.Common.Tree;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    
    public class PatientRecordViewTreeModel : TreeModelBase
    {
        #region Fields

        private bool showVersion;
        private SortBy sortorder;
        private PatientDetails patientInfo;

        private SortedList<EncounterDetails, EncounterDetails> encounterWise;
        private SortedList<DocumentType, SortedList<EncounterDetails, EncounterDetails>> documentWise;

        #endregion

        #region Constructor

        public PatientRecordViewTreeModel() { }

        public PatientRecordViewTreeModel(PatientDetails patInfo)
        {
            patientInfo = patInfo;
            BuildTaxonomy();
        }

        #endregion

        #region Methods

        private void BuildTaxonomy()
        {
            
            encounterWise = new SortedList<EncounterDetails, EncounterDetails>(EncounterDetails.Sorter);
            documentWise = new SortedList<DocumentType, SortedList<EncounterDetails, EncounterDetails>>(DocumentType.Sorter);
            if (selectedEncounters == null) selectedEncounters = new List<string>();

            SortedList<EncounterDetails, EncounterDetails> docTypeEncounters = null;

            foreach (EncounterDetails encounter in patientInfo.Encounters)
            {
                if (encounterWise.ContainsKey(encounter)) continue;
                
                encounterWise.Add(encounter, encounter);
                
                foreach (DocumentType docType in encounter.Documents.Keys)
                {
                    if (documentWise.ContainsKey(docType))
                    {
                        docTypeEncounters = documentWise[docType];
                    }
                    else
                    {
                        docTypeEncounters = new SortedList<EncounterDetails, EncounterDetails>(EncounterDetails.Sorter);
                        documentWise.Add(docType, docTypeEncounters);
                    }
                    if (!docTypeEncounters.ContainsKey(encounter))
                    {
                        docTypeEncounters.Add(encounter, encounter);
                    }
                }
            }
        }
		//Compare DocTypes without case sensitive
        private bool hasDocType(Collection<string> docTypes, string docType)
        {
            bool isFound = false;
            for (int index = 0; index <= docTypes.Count - 1; index++)
            {
                if (string.Compare(docTypes[index].Trim(), docType.Trim(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                {
                    isFound = true;
                    break;
                }
            }
            return isFound;
        }
        private IList<DocumentType> GetDocuments(IList<DocumentType> documents)
        {
            List<DocumentType> docs = new List<DocumentType>();
            
            bool hasDoc = false;

            foreach (DocumentType docType in documents)
            {
                hasDoc = false;

                if (RecordView != null)
                {
                    if (!hasDocType(RecordView.DocTypes, docType.Name))
                    {
                        continue;
                    }
                }

                if (IsDisclosure)
                {
                    if (!docType.IsDisclosure)
                    {
                        continue;
                    }
                }

                foreach (EncounterDetails enc in documentWise[docType].Values)
                {
                    if (selectedEncounters.Count > 0 && !selectedEncounters.Contains(enc.EncounterId + ROIConstants.Delimiter + enc.Facility)) continue;
                    if (enc.Documents.Count > 0)
                    {
                        hasDoc = true;
                    }
                }
                
                if (!hasDoc) continue;

                docs.Add(docType);
            }
            return docs;
        }

        //private IList<DocumentType> GetGlobalDocuments(IList<DocumentType> documents)
        //{
        //    List<DocumentType> docs = new List<DocumentType>();

        //    foreach (DocumentType docType in documents)
        //    {
            
        //        if (!RecordView.DocTypes.Contains(docType.Name))
        //        {
        //            continue;
        //        }

        //        if (IsDisclosure)
        //        {
        //            if (!docType.IsDisclosure)
        //            {
        //                continue;
        //            }
        //        }

        //        docs.Add(docType);
        //    }
        //    return docs;
        //}

        private IList<EncounterDetails> GetEncounters(IList<EncounterDetails> encounters)
        {
            List<EncounterDetails> filteredEncounters = new List<EncounterDetails>();

            string encounterKey;
            foreach (EncounterDetails enc in encounters)
            {
                encounterKey = enc.EncounterId + ROIConstants.Delimiter + enc.Facility;
                if (selectedEncounters.Count > 0 && !selectedEncounters.Contains(encounterKey)) continue;
                filteredEncounters.Add(enc);

            }

            return filteredEncounters;
        }

        public override IEnumerable GetChildren(TreePath treePath)
        {
            List<object> items = new List<object>();

            if (treePath.IsEmpty()) // for root node
            {
                if (recordView == null)
                {
                    goto NoAccessToRecordView;
                }

                if (patientInfo.GlobalDocument.Documents.Count > 0)
                {
                    items.Add(patientInfo.GlobalDocument);
                }
                
                if (sortorder == SortBy.Encounter)
                {
                    foreach (EncounterDetails enc in GetEncounters(encounterWise.Values))
                    {
                        items.Add(enc);
                    }
                }
                else if (sortorder == SortBy.Document)
                {
                    foreach (DocumentType docType in GetDocuments(documentWise.Keys))
                    {
                        items.Add(docType);
                    }
                }

                NoAccessToRecordView:
                if (patientInfo.NonHpfDocuments.GetChildren.Count > 0)
                {
                    items.Add(patientInfo.NonHpfDocuments);
                }
                if (patientInfo.Attachments.GetChildren.Count > 0)
                {
                    items.Add(patientInfo.Attachments);
                }
            }
            else
            {
                object node = treePath.LastNode;
                
                if (node == null) return null;

                if (typeof(EncounterDetails).IsAssignableFrom(node.GetType()))
                {
                    EncounterDetails enc = node as EncounterDetails;

                    if (sortorder == SortBy.Encounter)
                    {
                        return GetDocuments(encounterWise[enc].Documents.Keys);
                    }
                    else if (sortorder == SortBy.Document)
                    {
                        DocumentType documentType = treePath.FirstNode as DocumentType;
                        if (ShowVersion && encounterWise[enc].Documents[documentType].Versions.Count > 1)
                        {
                            return GetVersions(encounterWise[enc].Documents[documentType]);
                            //return encounterWise[enc].Documents[documentType].Versions.Values;
                        }
                        else
                        {
                            IList<VersionDetails> versions = encounterWise[enc].Documents[documentType].Versions.Values;
                            //For CR310959
                            if (versions.Count > 0)
                            {
                                return versions[0].Pages.Values;
                            }
                        }
                    }
                }
                else if (typeof(DocumentType).IsAssignableFrom(node.GetType()))
                {
                    DocumentType docType = node as DocumentType;
                    if (sortorder == SortBy.Encounter)
                    {
                        EncounterDetails enc = treePath.FirstNode as EncounterDetails;
                        if (enc != null)
                        {
                            if (ShowVersion && encounterWise[enc].Documents[docType].Versions.Count > 1)
                            {
                                return GetVersions(encounterWise[enc].Documents[docType]);
                                //return encounterWise[enc].Documents[docType].Versions.Values;
                            }
                            else
                            {
                                IList<VersionDetails> versions = encounterWise[enc].Documents[docType].Versions.Values;
                                if (versions.Count > 0)
                                {
                                    return versions[0].Pages.Values;
                                }
                            }
                        }
                        else
                        {
                            if (ShowVersion && patientInfo.GlobalDocument.Documents[docType].Versions.Count > 1)
                            {
                                return GetVersions(patientInfo.GlobalDocument.Documents[docType]);
                                //return patientInfo.GlobalDocument.Documents[docType].Versions.Values;
                            }
                            else
                            {
                                IList<VersionDetails> versions = patientInfo.GlobalDocument.Documents[docType].Versions.Values;
                                if (versions.Count > 0)
                                {
                                    return versions[0].Pages.Values;
                                }
                            }
                        }
                    }
                    else if (sortorder == SortBy.Document)
                    {
                        if (typeof(PatientGlobalDocument).IsAssignableFrom(treePath.FirstNode.GetType()))
                        {
                            if (ShowVersion && patientInfo.GlobalDocument.Documents[docType].Versions.Count > 1)
                            {
                                return GetVersions(patientInfo.GlobalDocument.Documents[docType]);
                                //return patientInfo.GlobalDocument.Documents[docType].Versions.Values;
                            }
                            else
                            {
                                IList<VersionDetails> versions = patientInfo.GlobalDocument.Documents[docType].Versions.Values;
                                if (versions.Count > 0)
                                {
                                    return versions[0].Pages.Values;
                                }
                            }
                        }
                        else
                        {
                            return GetEncounters(documentWise[docType].Values);
                        }
                    }
                }
                else if (typeof(VersionDetails).IsAssignableFrom(node.GetType()))
                {
                    VersionDetails version = node as VersionDetails;
                    return version.Pages.Values;
                }
                else if (typeof(PatientGlobalDocument).IsAssignableFrom(node.GetType()))
                {
                    PatientGlobalDocument globalDoc = node as PatientGlobalDocument;
                    return globalDoc.Documents.Keys;
                }
                else if (typeof(PatientNonHpfDocument).IsAssignableFrom(node.GetType()))
                {
                    PatientNonHpfDocument nonHpfDoc = node as PatientNonHpfDocument;
                    return nonHpfDoc.GetChildren;
                }
                else if (typeof(PatientAttachment).IsAssignableFrom(node.GetType()))
                {
                    PatientAttachment tmpAttachment = node as PatientAttachment;
                    return tmpAttachment.GetChildren;
                }
                else if (typeof(NonHpfEncounterDetails).IsAssignableFrom(node.GetType()))
                {
                    NonHpfEncounterDetails nonHpfEncounter = node as NonHpfEncounterDetails;
                    return nonHpfEncounter.GetChildren;
                }
                else if (typeof(NonHpfDocumentDetails).IsAssignableFrom(node.GetType()))
                {
                    NonHpfDocumentDetails nonHpfDocument = node as NonHpfDocumentDetails;
                    if(!string.IsNullOrEmpty(nonHpfDocument.Subtitle))
                    {
                        DocumentSubtitle subTitle = new DocumentSubtitle();
                        subTitle.Name = nonHpfDocument.Subtitle;
                        items.Add(subTitle);
                        return items;
                    }
                }
                else if (typeof(AttachmentEncounterDetails).IsAssignableFrom(node.GetType()))
                {
                    AttachmentEncounterDetails attachmentEncounter = node as AttachmentEncounterDetails;
                    return attachmentEncounter.GetChildren;
                }
                else if (typeof(AttachmentDetails).IsAssignableFrom(node.GetType()))
                {
                    AttachmentDetails tmpAttachment = node as AttachmentDetails;
                    if (!string.IsNullOrEmpty(tmpAttachment.Subtitle))
                    {
                        DocumentSubtitle subTitle = new DocumentSubtitle();
                        subTitle.Name = tmpAttachment.Subtitle;
                        if (tmpAttachment.IsDeleted)
                        {
                            subTitle.TextColor = Color.Gray;
                        }
                        items.Add(subTitle);
                        return items;
                    }
                }
            }
            return items;
        }

        private static IEnumerable GetVersions(DocumentDetails documentDetails)
        {
            List<object> items = new List<object>();
            int index=0;
            foreach (VersionDetails version in documentDetails.Versions.Values)
            {
                if (index == 0)
                {
                    foreach (PageDetails page in version.Pages.Values)
                    {
                        items.Add(page);
                    }
                    index++;
                }
                else
                {
                    items.Add(version);
                }
            }
            return items;
        }

        public void Refresh()
        {
            OnStructureChanged(new TreePathEventArgs());
        }

        public override bool IsLeaf(TreePath treePath)
        {
            return (treePath.LastNode is PageDetails);
        }

        #endregion

        #region Properties

        public PatientDetails PatientInfo
        {
            get
            {
                return patientInfo;
            }
            set
            {
                patientInfo = value;
                BuildTaxonomy();
            }
        }

        public bool ShowVersion
        {
            get
            {
                return showVersion;
            }
            set
            {
                showVersion = value;
                OnStructureChanged(new TreePathEventArgs());
            }
        }

        public SortBy SortBy
        {
            get
            {
                return sortorder;
            }
            set
            {
                sortorder = value;
                OnStructureChanged(new TreePathEventArgs());
            }
        }

        private bool isDisclosure;
        public bool IsDisclosure
        {
            get
            {
                return isDisclosure;
            }
            set
            {
                isDisclosure = value;
                OnStructureChanged(new TreePathEventArgs());
            }
        }

        private RecordViewDetails recordView;
        public RecordViewDetails RecordView
        {
            get
            {
                return recordView;
            }
            set
            {
                recordView = value;
                OnStructureChanged(new TreePathEventArgs());
            }
        }

        private IList<string> selectedEncounters;
        public IList<string> SelectedEncounters
        {
            get
            {
                return selectedEncounters;
            }
            set
            {
                selectedEncounters = value;
                OnStructureChanged(new TreePathEventArgs());
            }
        }

        #endregion

    }
}
