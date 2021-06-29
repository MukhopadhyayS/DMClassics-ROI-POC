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
using System.Drawing;
using System.ComponentModel;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Used to hold an Editor.
    /// </summary>
    public abstract class ROIRightSidePane : ROIBaseContainerPane
    {
        #region Fields

        internal ROIEditor currentEditor;

        #endregion

        #region Methods
        /// <summary>
        ///  Returns all the Children type of RSP.
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            return new Type[0];
        }

        /// <summary>
        /// Initilizes the RSP.
        /// </summary>
        protected override void InitComponent()
        {
        }

        public override void Localize()
        {
            if (currentEditor != null)
            {
                currentEditor.Localize();
            }
        }

        /// <summary>
        /// Sets the current editor.
        /// </summary>
        /// <param name="editor"></param>
        protected virtual void SetCurrentEditor(ROIEditor editor)
        {
            SetCurrentEditor(editor, true);
        }

        protected virtual void ClearCurrentEditor(BasePane currentEditor, BasePane newEditor)
        {
            if (currentEditor != null)
            {
                currentEditor.Cleanup();
                currentEditor = null;
            }
        }

        protected virtual void SetCurrentEditor(ROIEditor editor, bool init)
        {
            //if (currentEditor != null)
            //{
            //    currentEditor.Cleanup();
            //    currentEditor = null;
            //}

            ClearCurrentEditor(currentEditor, editor);
            
            if (init)
            {
                editor.ParentPane = this;
                editor.Init(Context);
                editor.Localize();
            }

            currentEditor = editor;
            ApplicationEventArgs ae = new ApplicationEventArgs(currentEditor, this);
            ROIEvents.OnEditorChange(this, ae);

            if (init)
            {
                currentEditor.PrePopulate();
            }

            SetFocus();
        }


        public void SetFocus()
        {
            if (currentEditor.GetType().BaseType == typeof(AdminEditor))
            {
                ((ROIBaseUI)(currentEditor.ODP.View)).Focus();
            }
            else
            {
                ((ROIBaseUI)(currentEditor.MCP.View)).Focus();
            }
        }

        

        #endregion

        #region Properties

        /// <summary>
        /// Gets the view of RSP.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get
            {
                if (currentEditor == null)
                {
                    return new Panel();
                }
                else
                {
                    return currentEditor.View;
                }
            }
        }

        #endregion
    }
}
