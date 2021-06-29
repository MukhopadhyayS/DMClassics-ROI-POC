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

using McK.EIG.ROI.Client.Request.Model;
using System.Xml;
using System.Globalization;
using McK.EIG.ROI.Client.Base.Model;
using System.Xml.XPath;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Patient.Model
{
    [Serializable]
    public abstract class BaseRecordItem
    {

        #region Fields

        private BaseRecordItem parent;
        private int recordVersionId;
        private SortedList<string, BaseRecordItem> children;

        #endregion

        #region Methods

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
        public BaseRecordItem GetChild(string key)
        {
            return (Children.ContainsKey(key)) ? Children[key] : null;
        }

        private SortedList<string, BaseRecordItem> Children
        {
            get
            {
                if (children == null)
                {
                    children = new SortedList<string, BaseRecordItem>(DefaultSorter);
                }
                return children;
            }
        }

        /// <summary>
        /// Retrives all Children for the give key
        /// </summary>
        public IList<BaseRecordItem> GetChildren
        {
            get { return Children.Values; }
        }

        /// <summary>
        /// Retrives all the keys of children
        /// </summary>
        public IList<string> ChildrenKeys
        {
            get { return Children.Keys; }
        }

        public virtual void AddChild(string key, BaseRecordItem child)
        {
            if (!Children.ContainsKey(key))
            {
                Children.Add(key, child);
                child.parent = this;
            }
            else
            {
                Children[key] = child;
            }
        }

        /// <summary>
        /// Removing items
        /// </summary>
        /// <param name="requestItem"></param>
        public void RemoveChild(BaseRecordItem child)
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

        public void RemoveChild(string key)
        {
            Children.Remove(key);
        }

        public void RemoveAll()
        {
            Children.Clear();
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

        #endregion

        #region Properties

        public BaseRecordItem Parent
        {
            get { return parent; }
            set { parent = value; }
        }

        /// <summary>
        /// Holds the record version id
        /// </summary>
        public int RecordVersionId
        {
            get { return recordVersionId; }
            set { recordVersionId = value; }
        }

        public virtual Image Icon
        {
            get { return null; }
        }

        public abstract string Key
        {
            get;
        }

        public abstract string Name
        {
            get;
        }

        public abstract IComparable CompareProperty
        {
            get;
        }

        public virtual IComparer<string> DefaultSorter
        {
            get { return null; }
        }

        #endregion

    }
}
