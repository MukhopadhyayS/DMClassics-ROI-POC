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
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Resources;

using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class PickAccountUI : ROIBaseUI
    {
        #region Fields

        private SortedList<string,string> userLists;

        #endregion

        #region Constructor
        public PickAccountUI()
        {
            InitializeComponent();
        }       

        public PickAccountUI(ExecutionContext context): this()
        {
            SetExecutionContext(context);
            SetHeader();            
            //EnableEvents();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Sets the header info
        /// </summary>
        private void SetHeader()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            headerLabel.Text = rm.GetString("pickAccountUI.header.info");           
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, okButton);
            SetLabel(rm, cancelButton);
        }

        /// <summary>
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }
        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get the user listbox control.
        /// </summary>
        public ListBox UserListBox
        {
            get { return userListBox; }
        }

        public string SelectedUser
        {
            get
            { 
                return !string.IsNullOrEmpty(UserListBox.SelectedItem.ToString()) ? UserListBox.SelectedItem.ToString(): string.Empty;             
            }
        }

        #endregion      
       
    }
}
