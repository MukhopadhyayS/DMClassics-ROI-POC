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
using System.Drawing;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using System.Globalization;
using System.Xml;
using System.Xml.XPath;
using McK.EIG.ROI.Client.Base.View;
using System.Reflection;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// DSR
    /// </summary>
    [Serializable]
    public abstract class BaseRequestItem 
    {
        #region Fields

        private int recordVersionId;
        private BaseRequestItem parent;
        private SortedList<string, BaseRequestItem> children;

        #endregion

        protected BaseRequestItem()
        {

        }

        public override bool Equals(object obj)
        {
            return object.ReferenceEquals(this, obj);
        }

        public override int GetHashCode()
        {
            return this.GetType().GetHashCode();
        }

        /// <summary>
        /// Returns BaseRequestItem type item for the guiven key
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public BaseRequestItem GetChild(string key)
        {
            return (Children.ContainsKey(key)) ? Children[key] : null;
        }

        /// <summary>
        /// Retrives all children for the give key
        /// </summary>
        public IList<BaseRequestItem> GetChildren
        {
            get { return Children.Values; }
        }

        /// <summary>
        /// Adding childrens
        /// </summary>
        /// <param name="requestItem"></param>
         public void AddChild(BaseRequestItem requestItem)
        
         {
            //if (children == null)
            //{
            //    children = new SortedList<string, BaseRequestItem>();
            //}
            if (!Children.ContainsKey(requestItem.Key))
            {
                Children.Add(requestItem.Key, requestItem);
                requestItem.Parent = this;
            }
        }

        /// <summary>
        /// Removing items
        /// </summary>
        /// <param name="requestItem"></param>
        public virtual void RemoveChild(BaseRequestItem child)
        {
            Children.Remove(child.Key);
            if (Children.Count == 0)
            {
                if (Parent != null)
                {
                    Parent.RemoveChild(this);
                }
            }
        }

        public static string CreateElement(string name, string value)
        {
            return CreateElement(name, value, true);
        }

        public static string CreateElement(string name, string value, bool applyCurrentCulture)
        {
            if (applyCurrentCulture)
            {
                return string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.Element, 
                                     name, (value == null) ? string.Empty : ROIViewUtility.ReplaceString(value));
            }
            else
            {
                return string.Format(CultureInfo.InvariantCulture, ROIConstants.Element,
                                     name, (value == null) ? string.Empty : ROIViewUtility.ReplaceString(value));
            }
        }

        public static string GetNodeValue(IXPathNavigable rootNode, string key)
        {
            XmlElement root = null;
            if (typeof(XmlElement).IsAssignableFrom(rootNode.GetType()))
            {
                root = (XmlElement)rootNode;
            }
            XmlNode node = root.SelectSingleNode(key);
            return (node == null) ? null : ROIViewUtility.OriginalString(node.InnerText);
        }
      
        /// <summary>
        /// Set Parent
        /// </summary>
        public BaseRequestItem Parent
        {
            get { return parent; }
            set { parent = value; }
        }

        /// <summary>
        /// Holds the name 
        /// </summary>
        public abstract string Name
        {
            get;
        }

        /// <summary>
        /// Holds the key
        /// </summary>
        public abstract string Key
        {
            get;
        }

        private bool isReleased;
        public virtual bool IsReleased
        {
            get
            {
                return isReleased;
            }
            set 
            {
                isReleased = value;
                if (isReleased)
                {
                    if (Parent != null)
                    {
                        Parent.IsReleased = isReleased;
                    }
                }
            }
        }


        private bool isDeleted;
        public virtual bool IsDeleted
        {
            get
            {
                return isDeleted;
            }
            set
            {
                isDeleted = value;
                if (isDeleted)
                {
                    if (Parent != null)
                    {
                        Parent.IsDeleted = isDeleted;
                    }
                }
            }
        }
        /// <summary>
        /// Checks the released status of a page 
        /// </summary>
        public virtual Nullable<bool> SelectedForRelease
        {
            get
            {
                int trueCount = 0;
                bool hadFalse = false;

                foreach (BaseRequestItem item in Children.Values)
                {
                    if (!item.SelectedForRelease.HasValue) { return null; }                   

                    if (item.SelectedForRelease.Value)
                    {
                        if (hadFalse) { return null; }                        
                        ++trueCount;
                    }
                    else
                    {
                        if (trueCount > 0)
                        {
                            return null;// one of previous item was true and current is false
                        }
                        hadFalse = true;
                    }
                }

                if (Children.Count > 0)
                {
                    return (trueCount == Children.Count);
                }
                else
                {
                    return false;
                }
            }

            set
            {
                foreach (BaseRequestItem item in Children.Values) 
                { 
                    item.SelectedForRelease = value.Value; 
                }

                RequestPatientDetails reqPatientDetails = this as RequestPatientDetails;
                if (reqPatientDetails != null) 
                {
                    reqPatientDetails.NonHpfDocument.SelectedForRelease = (value.HasValue) ? value.Value : value;
                    reqPatientDetails.Attachment.SelectedForRelease = (value.HasValue) ? value.Value : value;
                    reqPatientDetails.GlobalDocument.SelectedForRelease = (value.HasValue) ? value.Value : value;
                }
            }
        }

        /// <summary>
        /// Checks for released page
        /// </summary>
        /// <returns></returns>
        public virtual bool HasReleasedPage()
        {
            foreach (BaseRequestItem item in Children.Values)
            {
                if (item.IsReleased)
                {
                    return true;
                }
            }

            return false;
        }

        public bool PartiallyReleased
        {
            get
            {
                int trueCount = 0;
                foreach (BaseRequestItem item in Children.Values)
                {
                    if (item.GetChildren.Count > 0)
                    {
                        if (item.PartiallyReleased) { return true; }
                    }

                    if (item.IsReleased)
                    {   
                        ++trueCount;
                    }
                }
                if (Children.Count > 0)
                {
                    return (trueCount < Children.Count);
                }
                return !IsReleased;
            }
        }

        /// <summary>
        /// Holds image icon
        /// </summary>
        public virtual Image Icon
        {
            get
            {
                return null;
            }
        }

        /// <summary>
        /// Holds record version
        /// </summary>
        public int RecordVersionId
        {
            get { return recordVersionId; }
            set { recordVersionId = value; }
        }

        /// <summary>
        /// Default Sorter
        /// </summary>
        public virtual IComparer<string> DefaultSorter
        {
            get
            {
                return null;
            }
        }

        private SortedList<string, BaseRequestItem> Children
        {
            get
            {
                if (children == null)
                {
                    children = new SortedList<string, BaseRequestItem>(DefaultSorter);
                }
                return children;
            }
        }

        /// <summary>
        /// Clears all children
        /// </summary>
        public void ClearChildren()
        {
          //  if (children == null) return;

            foreach (BaseRequestItem requestItem in Children.Values)
            {
                requestItem.ClearChildren();
            }
            Children.Clear();
        }
    }
}
