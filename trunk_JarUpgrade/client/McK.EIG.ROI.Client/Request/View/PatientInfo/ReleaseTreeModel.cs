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
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View.Common.Tree;

using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    /// <summary>
    /// Holds DsrTree filter options
    /// </summary>
    public enum FilterBy
    { 
        All,
        Released,
        Unreleased
    }

    /// <summary>
    /// DSRTreeModel.
    /// </summary>
    public class ReleaseTreeModel : TreeModelBase
    {
        #region Fields

        private RequestDetails requestDetail;
        private FilterBy filterBy;

        #endregion

        #region Constructor

        public ReleaseTreeModel() {}

        #endregion

        #region Methods

        /// <summary>
        /// Add pages to the DsrTree
        /// </summary>
        /// <param name="pages"></param>
        /// <param name="nonHpfDocs"></param>
        public bool AddPages(IList<PageDetails> pages, IList<BaseRecordItem> nonHpfDocs, IList<BaseRecordItem> attachments, long nonHpfBillingTier)
        {
            bool flag = true;
            foreach (PageDetails page in pages)
            {
                if (!requestDetail.AddPage(page))
                {
                    flag = false;
                }
            }
            if (!requestDetail.AddNonHpfDocument(nonHpfDocs, nonHpfBillingTier))
            {
                flag = false;
            }
            if (!requestDetail.AddAttachment(attachments, nonHpfBillingTier))
            {
                flag = false;
            }
            Refresh();
            return flag;
        }

        /// <summary>
        /// Generates model for tree
        /// </summary>
        /// <param name="treePath"></param>
        /// <returns></returns>
        public override IEnumerable GetChildren(TreePath treePath)
        {
            if (treePath.IsEmpty()) // for root node
            {
                List<RequestPatientDetails> nodes = new List<RequestPatientDetails>(requestDetail.Patients.Count);
                foreach (RequestPatientDetails requestPatient in requestDetail.Patients.Values)
                {
                    if (requestPatient.HasDocuments)
                    {
                        if (Filter == FilterBy.Released && requestPatient.IsReleased)
                            nodes.Add(requestPatient);
                        else if (Filter == FilterBy.Unreleased && (requestPatient.PartiallyReleased || 
                                                                  (requestPatient.GlobalDocument.GetChildren.Count > 0 && requestPatient.GlobalDocument.PartiallyReleased) || 
                                                                  (requestPatient.NonHpfDocument.GetChildren.Count > 0 && requestPatient.NonHpfDocument.PartiallyReleased) || 
                                                                  (requestPatient.Attachment.GetChildren.Count > 0 && requestPatient.Attachment.PartiallyReleased)))
                            nodes.Add(requestPatient);
                        else if (Filter == FilterBy.All)
                            nodes.Add(requestPatient);
                    }
                }

                nodes.Sort(RequestPatientDetails.Sorter);

                return nodes;
            }
            else
            {
                object node = treePath.LastNode;

                List<BaseRequestItem> items = new List<BaseRequestItem>();

                if (typeof(RequestPatientDetails).IsAssignableFrom(node.GetType()))
                {
                    RequestPatientDetails requestPatient = (RequestPatientDetails)node;

                    if (requestPatient.GlobalDocument.GetChildren.Count > 0)
                    {
                        AddItem(items, requestPatient.GlobalDocument);
                    }

                    AddItem(items, requestPatient.GetChildren);

                    if (requestPatient.NonHpfDocument.GetChildren.Count > 0)
                    {
                        AddItem(items, requestPatient.NonHpfDocument);
                    }

                    if (requestPatient.Attachment.GetChildren.Count > 0)
                    {
                        AddItem(items, requestPatient.Attachment);
                    }

                    return items;
                }

                if(typeof(RequestDocumentDetails).IsAssignableFrom(node.GetType()))
                {
                    RequestDocumentDetails document = (RequestDocumentDetails)node;
                    GetVersions(document, items);
                    return items;
                    //if (document.GetChildren.Count == 1)
                    //{
                    //    AddItem(items, document.GetChildren[0].GetChildren);
                    //    return items;
                    //}
                }

                if (typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(node.GetType()))
                {
                    RequestNonHpfDocumentDetails document = (RequestNonHpfDocumentDetails)node;
                    if (!string.IsNullOrEmpty(document.Subtitle))
                    {
                        RequestDocumentSubtitle docSubtitle = new RequestDocumentSubtitle();
                        docSubtitle.Subtitle = document.Subtitle;
                        docSubtitle.Parent = document;

                        AddItem(items, docSubtitle);
                        return items;
                    }
                }

                if (typeof(RequestAttachmentDetails).IsAssignableFrom(node.GetType()))
                {
                    RequestAttachmentDetails tmpAttachment = (RequestAttachmentDetails)node;
                    if (!string.IsNullOrEmpty(tmpAttachment.Subtitle))
                    {
                        RequestDocumentSubtitle docSubtitle = new RequestDocumentSubtitle();
                        docSubtitle.Subtitle = tmpAttachment.Subtitle;
                        docSubtitle.Parent = tmpAttachment;

                        AddItem(items, docSubtitle);
                        return items;
                    }
                }

                AddItem(items, (node as BaseRequestItem).GetChildren);
                
                return items;
            }
        }

        private void GetVersions(RequestDocumentDetails documentDetails, List<BaseRequestItem> items)
        {
            int index = 0;
            foreach (RequestVersionDetails version in documentDetails.GetChildren)
            {
                if (index == 0 && version.VersionNumber == 9999999)
                {
                    AddItem(items, version.GetChildren);
                    index++;
                }
                else
                {
                    AddItem(items, version);
                }
            }
        }


        private void AddItem(List<BaseRequestItem> items, BaseRequestItem item)
        {
            if (Filter == FilterBy.Released && item.IsReleased)
                items.Add(item);
            else if (Filter == FilterBy.Unreleased && item.PartiallyReleased)
                items.Add(item);
            else if (Filter == FilterBy.All)
                items.Add(item);
        }

        private void AddItem(List<BaseRequestItem> items, IList<BaseRequestItem> children)
        {
            foreach (BaseRequestItem item in children)
            {
                AddItem(items, item);
            }
        }

        /// <summary>
        /// Check for lower level child
        /// </summary>
        /// <param name="treePath"></param>
        /// <returns></returns>
        public override bool IsLeaf(TreePath treePath)
        {
            return (treePath.LastNode is PageDetails);
        }

        /// <summary>
        /// Refresh the tree
        /// </summary>
        public void Refresh()
        {
           OnStructureChanged(new TreePathEventArgs());           
        }

        /// <summary>
        /// Remove selected non released documents from dsr tree.
        /// </summary>
        /// <param name="iRequestItem"></param>
        internal void Remove(BaseRequestItem item)
        {
            if (item.Parent != null)
            {
                if (item is RequestNonHpfDocuments)
                {
                    (item.Parent as RequestPatientDetails).NonHpfDocument.ClearChildren();
                }
                else if (item is RequestAttachments)
                {
                    (item.Parent as RequestPatientDetails).Attachment.ClearChildren();
                }
                else if (item is RequestGlobalDocuments)
                {
                    (item.Parent as RequestPatientDetails).GlobalDocument.ClearChildren();
                }
                else
                {
                    item.Parent.RemoveChild(item);
                }
            }
            else
            {
                requestDetail.Patients[item.Key].ClearChildren();
                requestDetail.Patients[item.Key].GlobalDocument.ClearChildren();
                requestDetail.Patients[item.Key].NonHpfDocument.ClearChildren();
                requestDetail.Patients[item.Key].Attachment.ClearChildren();
            }
        }

        /// <summary>
        /// Remove all non released documents from dsr tree.
        /// </summary>
        public void RemoveAll()
        {
            foreach (RequestPatientDetails reqPatient in requestDetail.Patients.Values)
            {
                reqPatient.GlobalDocument.ClearChildren();
                reqPatient.NonHpfDocument.ClearChildren();
                reqPatient.Attachment.ClearChildren();
                reqPatient.ClearChildren();
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds dsrtree model
        /// </summary>
        public RequestDetails Request
        {
            get { return requestDetail; }
            set { requestDetail = value; }
        }

        /// <summary>
        /// Holds selcted Filter option
        /// </summary>
        public FilterBy Filter
        {
            get { return filterBy; }
            set
            {
                filterBy = value;
                Refresh();
            }
        }

        #endregion
    }
}
