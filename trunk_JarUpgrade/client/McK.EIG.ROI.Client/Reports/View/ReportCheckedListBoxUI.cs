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
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportCheckedListUI
    /// </summary>
    public partial class ReportCheckedListUI : ROIBaseUI
    {
        #region Fields

        private string titleKey;
        private bool isChecked;
        private bool isRequired;

        internal EventHandler mandatoryHandler;

        #endregion

        #region Constructor
        /// <summary>
        /// CriteriaUI
        /// </summary>
        public ReportCheckedListUI()
        {	
            InitializeComponent();
            mandatoryHandler = new EventHandler(Process_SelectedIndexChanged);
        }
        public ReportCheckedListUI(bool isRequiredField)
        {
            isRequired = isRequiredField;
            InitializeComponent();
            mandatoryHandler = new EventHandler(Process_SelectedIndexChanged);
        }

        #endregion

        #region Methods
        /// <summary>
        /// Occurs when user clicks on "Clear All" button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void clearAllButton_Click(object sender, EventArgs e)
        {
            CheckAllItems(false);
            mandatoryHandler(null, null);
        }

        /// <summary>
        /// Occurs when user clicks on "Select All" button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void selectAllButton_Click(object sender, EventArgs e)
        {
            CheckAllItems(true);
            mandatoryHandler(null, null);
        }

        /// <summary>
        /// Check or UnCheck checked list box items.
        /// </summary>
        /// <param name="check"></param>
        private void CheckAllItems(bool check)
        {
            for (int index = criteriaCheckedListBox.Items.Count; --index >= 0; )
            {
                criteriaCheckedListBox.SetItemChecked(index, check);
            }
        }

        /// <summary>
        /// Localizes the UI
        /// </summary>
        /// 
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, clearAllButton);
            SetLabel(rm, selectAllButton);
            checkedListBoxTitleLabel.Text = rm.GetString(titleKey);
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, clearAllButton);
            SetTooltip(rm, toolTip, selectAllButton);
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + Pane.GetType().Name;
        }

        /// <summary>
        /// Enable Checked list box events
        /// </summary>
        public void EnableEvents()
        {
            this.criteriaCheckedListBox.SelectedIndexChanged += mandatoryHandler;
            this.criteriaCheckedListBox.KeyUp += delegate { mandatoryHandler(null, null); };
            this.criteriaCheckedListBox.DoubleClick += mandatoryHandler;
        }

        /// <summary>
        /// Disable Checked list box events
        /// </summary>
        public void DisableEvents()
        {
            this.criteriaCheckedListBox.SelectedIndexChanged -= mandatoryHandler;
            this.criteriaCheckedListBox.KeyUp -= delegate { mandatoryHandler(null, null); };
            this.criteriaCheckedListBox.DoubleClick -= mandatoryHandler;
        }

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <returns></returns>
        internal StringBuilder GetData()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < criteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                checkedItems.Append(criteriaCheckedListBox.GetItemText(criteriaCheckedListBox.CheckedItems[index]));
            }

            return checkedItems;
        }

        /// <summary>
        /// Reset the data to default values.
        /// </summary>
        internal void ClearControls()
        {
            CheckAllItems(false);
            criteriaCheckedListBox.SelectedIndex = 0;
        }

        
        //When items checked in Checked list box, it validate all required search criteria fields
        //and enables the run button.
        private void Process_SelectedIndexChanged(object sender, EventArgs e)
        {
            isChecked = criteriaCheckedListBox.CheckedItems.Count > 0;
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        }

        /// <summary>
        /// Checks whether item has checked or not.
        /// </summary>
        /// <returns></returns>
        public bool HasCheckedItems()
        {
            return isChecked || (criteriaCheckedListBox.CheckedItems.Count>0);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets checkedListBoxTitleLabel
        /// </summary>
        public string TitleKey
        {
            set { titleKey = value; }
            get { return titleKey; }
        }

        /// <summary>
        /// Returns CriteriaCheckedListBox control
        /// </summary>
        public CheckedListBox CriteriaCheckedListBox
        {
            get { return criteriaCheckedListBox; }
        }

        #endregion

       
    }
}
