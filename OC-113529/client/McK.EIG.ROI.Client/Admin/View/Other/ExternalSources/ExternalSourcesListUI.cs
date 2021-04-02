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
using System.Resources;
using System.Windows.Forms;
using System.ComponentModel;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.Common.Utility.View;


namespace McK.EIG.ROI.Client.Admin.View.Configuration.ExternalSources
{
    public partial class ExternalSourcesListUI : ROIBaseUI
    {
        #region Fields
        private Log log = LogFactory.GetLogger(typeof(ExternalSourcesListUI));
        private const string ExternalSourceSourceName = "displayname";
        private const string ExternalSourceName = "sourcename";
        private const string Description = "description";
        private const string PropertyName = "property";
        private const string PropertyValue = "propertyvalue";
        private int previousRow=0;
        private ExternalSourcesTabUI tabUI;

        private const string confirmDeleteDialogMessage = "A130";
        public const string confirmDeleteDialogTitle = "ClinicalSystemDeleteDialog.Title";
        private const string confirmDeleteDialogOkButton = "ClinicalSystemDeleteDialog.okButton";
        private const string confirmDeleteDialogCancelButton = "ClinicalSystemDeleteDialog.cancelButton";
        private const string confirmDeleteDialogOkButtonToolTip = "ClinicalSystemDeleteDialog.okButton";
        private const string confirmDeleteDialogCancelButtonToolTip = "ClinicalSystemDeleteDialog.cancelButton";
         
        #endregion
        
        #region Constructor
        public ExternalSourcesListUI()
        {
            
            InitializeComponent();
            
            //Add columns to gridview.            

            grid.AddTextBoxColumn(ExternalSourceName, "SourceName", "SourceName", 210);
            grid.AddTextBoxColumn(ExternalSourceSourceName, "providerDisplayName", "ProviderName", 210);
            DataGridViewTextBoxColumn dgvDescColumn = grid.AddTextBoxColumn(Description,
                                                                                             "Description",
                
                                                                                "Description",
                                                                                ColumnWidth);
                       
            dgvDescColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            grid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(grid_RowSelected);
            grid.ChangeValidator = new EIGDataGrid.RowChangeValidator(RowChangeValidator);

            deleteButton.Enabled = false;
        }
        #endregion

        #region methods

        private bool RowChangeValidator(DataGridViewRow row)
        {
            ApplicationEventArgs ae = new ApplicationEventArgs(row.DataBoundItem, this);
            return TransientDataValidator.Validate(ae);
        }

        /// <summary>
        ///  Set Culture text to the controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            grid.Columns[ExternalSourceName].HeaderText = "Clinical System Instance";
            grid.Columns[ExternalSourceSourceName].HeaderText = "System Provider";//rm.GetString("externalsources.displayname");
            grid.Columns[Description].HeaderText = rm.GetString("externalsources.description");
            SetLabel(rm, createNewButton);
            SetLabel(rm, deleteButton);
        }

        /// <summary>
        /// To fill the last column width.
        /// </summary>
        public int ColumnWidth
        {
            get
            {
                int sum = 0;
                foreach (DataGridViewColumn column in grid.Columns)
                {
                    sum += column.Width;
                }
                sum = grid.Width - sum;
                sum = grid.Columns[grid.Columns.Count - 1].Width + sum - 3;
                return sum;
            }
        }

        /// <summary>
        /// Sets the data into the grid
        /// </summary>
        /// <param name="data"></param>
        public void SetData(Collection<ExternalSource> data)
        {
           ComparableCollection<ExternalSource> listext = new ComparableCollection<ExternalSource>(data);
           grid.SetItems((IFunctionCollection)listext);
           UpdateCount(grid.Rows.Count);
        }

        public void UpdateCount(int count)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            countLabel.Tag = rm.GetString("countLabel." + GetType().Name);
            countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "   {0} {1}", count, countLabel.Tag);
    
        }



        /// <summary>
        /// Row selection handler
        /// </summary>
        /// <param name="row"></param>
        private void grid_RowSelected(DataGridViewRow row)
        {
                ROIViewUtility.MarkBusy(true);
                deleteButton.Enabled = true;
                ExternalSource extsource = (ExternalSource)row.DataBoundItem;
                grid.Rows[row.Index].Selected = true;
                ExternalSourcesODP extsourceODP = new ExternalSourcesODP();
                tabUI = (ExternalSourcesTabUI)extsourceODP.View;
                ExternalSourcesTabUI.providerInfo = false;
                tabUI.SetData(extsource);
                previousRow = row.Index;
                EnableButton();
                ROIViewUtility.MarkBusy(false);
                      
        }
        
        /// <summary>
        /// update the row contents
        /// </summary>
        /// <param name="data"></param>
        public void UpdateRow(object data)
        {
            grid.UpdateItem(data);
        }
        #endregion

        private void createNewButton_Click(object sender, EventArgs e)
        {
            grid.ConfirmSelection = true;   
            if (grid.Rows.Count >0)
                grid.Rows[previousRow].Selected = false;
            if (tabUI == null)
            {
                ExternalSourcesODP extsourceODP = new ExternalSourcesODP();
                tabUI = (ExternalSourcesTabUI)extsourceODP.View;
            }
            tabUI.SetData(null);
            createNewButton.Enabled = false;
            deleteButton.Enabled = false;
                        
        }
        
        private void deleteButton_Click(object sender, EventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString(confirmDeleteDialogMessage);
            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString(confirmDeleteDialogTitle);
            string okButtonText = rm.GetString(confirmDeleteDialogOkButton);
            string cancelButtonText = rm.GetString(confirmDeleteDialogCancelButton);
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(confirmDeleteDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(confirmDeleteDialogCancelButtonToolTip);
            if (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert))
            {
                DeleteConnection();  
            }
        }

        private void DeleteConnection()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                ExternalSource deleteSource=new ExternalSource();
                for (int i = 0; i < grid.SelectedRows.Count; i++)
                {

                    deleteSource = (ExternalSource)grid.SelectedRows[i].DataBoundItem;
                    int index = grid.SelectedRows[i].Index;
                    bool deleted = ROIAdminController.Instance.deleteExternalSource(deleteSource.SourceId);
                    if (deleted)
                    {
                        grid.DeleteItem(deleteSource);
                        if (grid.RowCount >= 1)
                        {                           
                           if(grid.RowCount==index)
                               grid.Rows[grid.RowCount - 1].Selected = true;
                            else
                           grid.Rows[previousRow].Selected = true;

                        }
                        else
                        {
                            tabUI.ClearControls();
                            deleteButton.Enabled = false;
                        }

                    }
                }
                ExternalSourcesTabUI.auditextsourcename = deleteSource.SourceName;
                tabUI.AuditConfigChanges("deleted");
                UpdateCount(grid.Rows.Count);
                ROIViewUtility.MarkBusy(false);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
        }

        public void AddRow(object data)
        {
            ExternalSource externalSrcData = (ExternalSource)data;
            grid.AddItem(externalSrcData);
            if (grid.RowCount>=1)
                grid.Rows[grid.RowCount - 1].Selected = true;
            UpdateCount(grid.Rows.Count);
        }

        public void SelectPrevoiusUnSelected()
        {
            if (grid.Rows.Count > 0)
                grid.SelectRow(previousRow);
         }

        public void EnableButton()
        {
            createNewButton.Enabled = true;
            deleteButton.Enabled = true;
        }

        public void DisableButton()
        {
            createNewButton.Enabled = false;
            deleteButton.Enabled = false;
        }                
    }
}
