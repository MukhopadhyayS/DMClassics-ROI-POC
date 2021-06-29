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
using System.ComponentModel;
using System.Resources;
using System.Drawing;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.Common.Audit.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Admin.View.Configuration.ExternalSources
{
    public partial class ExternalSourcesTabUI : ROIBaseUI
    {
        private EventHandler dirtyDataHandler;
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ExternalSourcesTabUI));
        private ExternalSource externalSource;
        private ExternalSource externalSourceCopy;
        private Collection<ExternalSource> externalsources;
        private Hashtable hPropertyValues;
        private Hashtable hPropertyNames;
        private Hashtable savedvalues;
        private ExternalSourcesListUI listUI;
        private ExternalSource defaultSelection;

        private int propertyCount;
        private string savename;
        //CR#368718, Keane, 09-May-2012
        private string saveprovider;
        private string savedescription;
        private string plugin;
        internal static string auditextsourcename;
        private int sourceId;
        private static int img1Vertical;
        private static int img1Hzntl;
        private static int img2Vertical;
        private static int img2Hzntl;
        private static int img0Vertical;
        private static int img0Hzntl;
        private int bottom;
        private static Point saveBtnLocation;
        private static Point undoBtnLocation;
        private static Point testBtnLocation;
        private static bool showProviderControls;
        
        internal static bool providerInfo;
        private const string TestConnectionSuccessMeassage = "TestConnectionDialog.SuccessMessageText";
        private const string TestConnectionFailureMeassage = "TestConnectionDialog.FailureMessageText";
        private bool enableSave;
        
        #endregion

        #region Constructor
        
        public ExternalSourcesTabUI()
        {
            InitializeComponent();
            hPropertyValues = new Hashtable();
            hPropertyNames=new Hashtable();
            savedvalues = new Hashtable();
            providerInfo = false;
            testpropertiesButton.Left = undopropertiesButton.Right + 400;
            SaveLocations();
            SetLocations();
            testpropertiesButton.Visible = true;
            testpropertiesButton.Enabled = false;
            ExternalSourcesMCP extsourceMCP = new ExternalSourcesMCP();
            listUI = (ExternalSourcesListUI)extsourceMCP.View;
            extsourceDescriptionTextBox.Visible = false;
            extsourceNameTextBox.Visible = false;
            providerComboBox.Enabled = false;
            defaultSelection = new ExternalSource();
            defaultSelection.SourceId = 0;
            defaultSelection.ProviderName = "Please select provider";
            PopulateProviders();

            dirtyDataHandler = new EventHandler(MarkDirty);
        }
        
        #endregion

        private void MarkDirty(object sender, EventArgs e)
        {
            bool isEmpty = false;
            for (int i = 0; i < propertyCount; i++)
            {
                string name = "t" + i.ToString();
                isEmpty = (String.IsNullOrEmpty(hPropertyValues[name].ToString()));
                if (isEmpty)
                    break;
            }

            if (isEmpty || String.IsNullOrEmpty(extsourceNameTextBox.Text.ToString())
                || String.IsNullOrEmpty(extsourceDescriptionTextBox.Text.ToString()))
            {
                enableSave = false;
            }
            else
            {
                enableSave = true;
            }
            

            object obj = (Pane as ExternalSourcesODP);
            
            if(obj != null)
            {
                ExternalSourcesODP extMCP = (ExternalSourcesODP)obj;
                extMCP.OnDataDirty(sender, e);
            }            
        }

        #region Methods


        public void EnableEvents()
        {
            DisableEvents();            
            extsourceDescriptionTextBox.TextChanged += dirtyDataHandler;
            extsourceNameTextBox.TextChanged += dirtyDataHandler;            
            providerComboBox.SelectedIndexChanged += dirtyDataHandler;
        }

        public void DisableEvents()
        {
            extsourceDescriptionTextBox.TextChanged -= dirtyDataHandler;
            extsourceNameTextBox.TextChanged -= dirtyDataHandler;
            providerComboBox.SelectedIndexChanged -= dirtyDataHandler;
        }

        /// <summary>
        ///  Set Culture text to the controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, connectionproperties_titleLabel);
            SetLabel(rm, externalsource_requiredLabel);
            SetLabel(rm, extsourceDescriptionlabel);
            SetLabel(rm, extsourceNameLabel);
            SetLabel(rm, providerLabel);
            SetLabel(rm, savepropertiesButton);
            SetLabel(rm, testpropertiesButton);
            SetLabel(rm, undopropertiesButton);
            ResourceManager rt = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            System.Windows.Forms.ToolTip tooltip = new ToolTip();
            SetTooltip(rt, tooltip, savepropertiesButton);
            SetTooltip(rt, tooltip, testpropertiesButton);
            SetTooltip(rt, tooltip, undopropertiesButton);
        }
       
        /// <summary>
       /// Sets the data into view
       /// </summary>
       /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();            
            if (data != null)
            {
                int i;
                int count = 0;
                int labelRight = 0;
                int bottomFirst = 0;
                List<int> positionLeft = new List<int>();
                List<int> positionTop = new List<int>();
                Collection<TextBox> propertyTextBoxes = new Collection<TextBox>();
                propertiespanel.Controls.Clear();
                SetLocations();
                this.Refresh();
                if (!((data as ExternalSource).Equals(defaultSelection)))
                {

                    bottom = extsourceDescriptionlabel.Bottom;
                    externalSource = new ExternalSource();
                    externalSource = data as ExternalSource;
                    externalSourceCopy = new ExternalSource();
                    externalSourceCopy = externalSource.Clone();
                    plugin = externalSource.ProviderName;
                    sourceId = externalSource.SourceId;
                    propertyCount = externalSource.ConnectionProperties.Count;
                    savename = externalSource.SourceName;
                    //CR#368718, Keane, 09-May-2012
                    saveprovider = externalSource.ProviderName;
                    extsourceNameTextBox.Text = externalSource.SourceName;
                    extsourceDescriptionTextBox.Text = externalSource.Description;
                    savedescription = externalSource.Description;
                    int imgleft = extsourceNameTextBox.Right + 25;
                    int tabIndex = extsourceDescriptionTextBox.TabIndex + 1;
                    for (i = 0; i < propertyCount; i++)
                    {
                        Label propertylabel = new Label();
                        TextBox propertytextbox = new TextBox();
                        if (count == 0)
                        {
                            bottom = providerComboBox.Top - 15;
                            bottomFirst = bottom;
                            count++;
                        }

                        propertylabel.Name = "l" + i;
                        propertylabel.Text = externalSource.ConnectionProperties[i].ConfigLabel;
                        propertylabel.Location = new Point(imgleft + 15, bottom + 15);
                        propertylabel.Width = extsourceDescriptionlabel.Width;
                        propertylabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
                        propertylabel.AutoSize = true;
                        propertiespanel.Controls.Add(propertylabel);


                        propertytextbox.Text = string.Empty;
                        if ((externalSource.ConnectionProperties[i].ConfigKey).Contains(".masked"))
                            propertytextbox.PasswordChar = '*';
                        propertytextbox.Text = Convert.ToString(externalSource.ConnectionProperties[i].ConfigValue);
                        propertytextbox.Name = "t" + i;
                        propertytextbox.TabStop = false;
                        savedvalues[propertytextbox.Name.ToString()] = propertytextbox.Text;
                        hPropertyValues[propertytextbox.Name.ToString()] = propertytextbox.Text;
                        hPropertyNames[propertylabel.Name.ToString()] = propertylabel.Text;
                        propertyTextBoxes.Add(propertytextbox);
                        propertytextbox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
                        propertytextbox.MaxLength = 50;
                        positionLeft.Add(propertylabel.Right + 15);
                        int right = propertiespanel.Width - propertylabel.Right;
                        positionTop.Add(bottom + 15);
                        int maxValue = 0;
                        int top = 0;
                        if (i == propertyCount - 1)
                        {
                            for (int index = 0; index < positionLeft.Count; index++)
                            {
                                maxValue = maxValue < positionLeft[index] ? positionLeft[index] : maxValue;
                            }
                            foreach (TextBox textBox in propertyTextBoxes)
                            {
                                textBox.Location = new Point(maxValue, positionTop[top]);
                                propertiespanel.Controls.Add(textBox);
                                top++;
                            }
                            this.Refresh();
                        }

                        labelRight = propertylabel.Right;
                        propertytextbox.Width = extsourceDescriptionTextBox.Width;
                        propertytextbox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
                        propertytextbox.BorderStyle = BorderStyle.Fixed3D;
                        propertytextbox.TextChanged += new EventHandler(propertytextbox_TextChanged);
                        propertytextbox.TextChanged += dirtyDataHandler;            
                        propertytextbox.TabIndex = tabIndex++;
                        propertytextbox.TabStop = true;

                        PictureBox img = new PictureBox();
                        img.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
                        img.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
                        img.Location = new System.Drawing.Point(imgleft, bottom + 15);
                        img.Name = "Inputimg " + i;
                        img.Size = new System.Drawing.Size(9, 10);
                        img.TabIndex = 11;
                        img.TabStop = false;
                        propertiespanel.Controls.Add(img);
                        bottom = propertylabel.Bottom;

                    }
                    propertiespanel.Controls.Add(externalsourcelabelimg0);
                    externalsourcelabelimg0.Visible = true;
                    propertiespanel.Controls.Add(externalsourcelabelimg1);
                    externalsourcelabelimg1.Visible = true;
                    propertiespanel.Controls.Add(externalsourcelabelimg2);
                    externalsourcelabelimg2.Visible = true;
                    propertiespanel.Controls.Add(extsourceNameLabel);
                    extsourceNameLabel.Visible = true;
                    propertiespanel.Controls.Add(extsourceNameTextBox);
                    extsourceNameTextBox.Visible = true;
                    propertiespanel.Controls.Add(extsourceDescriptionlabel);
                    extsourceDescriptionlabel.Visible = true;
                    propertiespanel.Controls.Add(extsourceDescriptionTextBox);
                    extsourceDescriptionTextBox.Visible = true;
                    propertiespanel.Controls.Add(externalsource_requiredImage);
                    propertiespanel.Controls.Add(externalsource_requiredLabel);                    
                    EnableSaveButton(false);
                    EnableUndoButton(false);
                    testpropertiesButton.Enabled = true;
                    propertiespanel.Controls.Add(savepropertiesButton);
                    propertiespanel.Controls.Add(undopropertiesButton);
                    propertiespanel.Controls.Add(testpropertiesButton);
                    savepropertiesButton.TabIndex = tabIndex++;
                    undopropertiesButton.TabIndex = tabIndex++;
                    testpropertiesButton.TabIndex = tabIndex++;

                    propertiespanel.Controls.Add(providerLabel);
                    providerLabel.Visible = true;
                    ExternalSource ext1 = (ExternalSource)providerComboBox.SelectedItem;
                    if (providerInfo)
                    {
                        providerInfo = false;
                        propertiespanel.Controls.Add(providerComboBox);
                        providerComboBox.Visible = true;
                        providerComboBox.SelectedItem = ext1;
                        providerInfo = true;
                    }
                    else
                    {
                        propertiespanel.Controls.Add(providerComboBox);
                        providerComboBox.Visible = true;
                        for (int l = 0; l < providerComboBox.Items.Count; l++)
                        {
                            ExternalSource source = (ExternalSource)providerComboBox.Items[l];
                            if (source.ProviderName.Equals(plugin))
                            {
                                providerComboBox.SelectedIndex = l;
                                break;
                            }
                        }
                        providerComboBox.Enabled = false;
                    }
                }
                else
                    DisplayDefaultControls();
            }
            else
            {
                PopulateProviders();
                providerComboBox.Enabled = true;
                providerComboBox.SelectedIndex = 0;
                providerInfo = true;
                providerComboBox_SelectedIndexChanged(this, null);
            }
            providerInfo = true;
            SetButtonPositions();
            EnableEvents();
        }

        private void PopulateProviders()
        {
            externalsources = ROIAdminController.Instance.RetrieveProviders();//.DisplayExternalSources();
            externalsources.Insert(0, defaultSelection);
            showProviderControls = false;
            providerComboBox.DataSource = externalsources;
            providerComboBox.DisplayMember = "ProviderName";
            providerComboBox.ValueMember = "ProviderName";
            showProviderControls = true;
            if (providerComboBox.Items.Count > 0)
                providerComboBox.SelectedIndex = 0;
            providerComboBox.Focus();
            providerComboBox.Enabled = true;
        }

        /// <summary>
        ///  Invoked when text changed in the propertytextbox(created at runtime).
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void propertytextbox_TextChanged(object sender, EventArgs e)
        {
            hPropertyValues[((TextBox)sender).Name.ToString()] = ((TextBox)sender).Text;
        }

        /// <summary>
        /// Enables or Disables the Save button.
        /// </summary>
        /// <param name="enableSave"></param>
        public void EnableSaveButton(bool enableSave)
        {            
                savepropertiesButton.Enabled = enableSave;
                if (providerComboBox.SelectedIndex == 0)
                    savepropertiesButton.Enabled = false;

        }

        /// <summary>
        /// Enables or Disables the Undo button.
        /// </summary>
        /// <param name="enableUndo"></param>
        public void EnableUndoButton(bool enableUndo)
        {
            undopropertiesButton.Enabled = enableUndo;
        }

        public bool Save()
        {
            log.EnterFunction();
            string name;
            bool success = false;
            bool response = false;
            bool updated = false;
            int propertyModified = 0;
            bool newSourceCreation=true;
            bool result=false;
            ROIViewUtility.MarkBusy(true);
            ExternalSource updatedsource = new ExternalSource();
            (Pane as ExternalSourcesODP).IsDirty = false;
            if (externalSource.SourceId != 0)
            {
                if (!((savename.Equals(extsourceNameTextBox.Text.Trim())) && (savedescription.Equals(extsourceDescriptionTextBox.Text.Trim()))))
                {
                    externalSource.SourceName = extsourceNameTextBox.Text.Trim();
                    externalSource.Description = extsourceDescriptionTextBox.Text.Trim();
                    try
                    {
                        response = ROIAdminController.Instance.updateExternalSource(externalSource);
                        updated = response;
                    }
                    catch (ROIException cause)
                    {
                        log.FunctionFailure(cause);
                        ROIViewUtility.Handle(Context, cause);
                        return false;
                    }
                    finally
                    {
                        if (!updated)
                        {
							//CR#368718, Keane, 09-May-2012
						//	externalSource.ProviderName = savename;
                            externalSource.SourceName = savename;
                            externalSource.ProviderName = saveprovider;
                            externalSource.Description = savedescription;
                        }
                        else
                        {
                            savename = externalSource.ProviderName;
                            savedescription = externalSource.Description;
                        }

                        listUI.Refresh();                      
                    }
                }
                for (int i = 0; i < propertyCount; i++)
                {
                    name = "t" + i.ToString();
                    if (!(savedvalues[name].ToString().Equals(hPropertyValues[name].ToString())))
                        propertyModified = 1;
                }
                if (propertyModified == 1)
                {
                    for (int i = 0; i < propertyCount; i++)
                    {
                        name = "t" + i.ToString();
                        externalSource.ConnectionProperties[i].ConfigValue = hPropertyValues[name].ToString();
                        externalSource.ConnectionProperties[i].ConfigKey = externalSource.ConnectionProperties[i].ConfigKey;
                    }
                    try
                    {
                        success = ROIAdminController.Instance.updateConfigProperties(externalSource);
                        if (!success)
                            updated = success;
                    }
                    catch (ROIException cause)
                    {
                        log.FunctionFailure(cause);
                        ROIViewUtility.Handle(Context, cause);
                        return false;
                    }

                    updated = success;
                    if (!updated)
                    {
                        for (int i = 0; i < propertyCount; i++)
                        {
                            name = "t" + i.ToString();
                            externalSource.ConnectionProperties[i].ConfigValue = savedvalues[name].ToString();
                            externalSource.ConnectionProperties[i].ConfigKey = externalSource.ConnectionProperties[i].ConfigKey;
                        }
                    }
                    else
                    {
                        //  savename = externalSource.ProviderName;
                        // savedescription = externalSource.Description;
                    }
                }
                if (updated)
                {                    
                    EnableSaveButton(false);
                    EnableUndoButton(false);

                    
                    listUI.UpdateRow(externalSource);
                    listUI.EnableButton();
                    listUI.SelectPrevoiusUnSelected();
                    for (int i = 0; i < propertyCount; i++)
                    {
                        name = "t" + i.ToString();
                        savedvalues[name] = hPropertyValues[name].ToString();
                    }
                    auditextsourcename = externalSource.SourceName;
                    AuditConfigChanges("updated");
                }
                else
                {
                    this.SetData(externalSourceCopy);
                }
            }
            else
            {
                newSourceCreation = CreateExternalSource();

               
            }
            //providersComboBox.Enabled = false;
            ROIViewUtility.MarkBusy(false);
            log.ExitFunction();
            return result= newSourceCreation==false?false:true;
            
        }

        /// <summary>
        /// Occurs when user click Save button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void savebutton_Click(object sender, EventArgs e)
        {
            Save();
        }
        
        /// <summary>
        /// Creates a new clinical source connection
        /// </summary>
        private bool CreateExternalSource()
        {
            bool sourceCreated = false;
            try
            {
                ROIViewUtility.MarkBusy(true);
                string name;
                ExternalSource extSrc = (ExternalSource)providerComboBox.SelectedItem;
                externalSource.ProviderName = extSrc.ProviderName;
                externalSource.SourceName = extsourceNameTextBox.Text.Trim();
                externalSource.Description = extsourceDescriptionTextBox.Text.Trim();
                for (int i = 0; i < propertyCount; i++)
                {
                    name = "t" + i.ToString();
                    externalSource.ConnectionProperties[i].ConfigValue = hPropertyValues[name].ToString();
                    externalSource.ConnectionProperties[i].ConfigKey = externalSource.ConnectionProperties[i].ConfigKey;
                }
                int sourceId = ROIAdminController.Instance.createExternalSource(externalSource);
                if (sourceId != 0)
                {
                    sourceCreated = true;
                    externalSource.SourceId = sourceId;
                    auditextsourcename = externalSource.SourceName;
                    AuditConfigChanges("created");
                    listUI.AddRow(externalSource);
                    listUI.EnableButton();
                }
                
                EnableSaveButton(false);
                EnableUndoButton(false);
                ROIViewUtility.MarkBusy(false);
            }
            catch (ROIException cause)
            {
                sourceCreated=false;
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            return sourceCreated;
        }

        public void CancelSelection()
        {
            (Pane as ExternalSourcesODP).IsDirty = false;
            this.SetData(externalSource);
            EnableSaveButton(false);
            EnableUndoButton(false);
            AdminEvents.OnCancelModify(null, null);
        }

        public void CancelExternalSource()
        {
            (Pane as ExternalSourcesODP).IsDirty = false;
            this.SetData(externalSource);
            EnableSaveButton(false);
            EnableUndoButton(false);
            if (providerInfo)
               listUI.SelectPrevoiusUnSelected();
            AdminEvents.OnCancelModify(null, null);
        }


        /// <summary>
        /// Occurs when user click Undo button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void undobutton_Click(object sender, EventArgs e)
        {
            CancelExternalSource();
        }
              
        /// <summary>
        /// Occurs when user click Test button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void testpropertiesButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            string name;
            ExternalSource extSource = new ExternalSource();
            ROIDialog InfoDialog = new ROIDialog(ROIDialogIcon.Alert);
            InfoDialog.DialogTitle = "Configure Clinical Systems";
            InfoDialog.MessageHeaderText = "Configure Clinical Systems";
            InfoDialog.OkButtonText = "OK";
            InfoDialog.CancelButton.Visible = false;
            InfoDialog.OkButtonToolTip = "";
            InfoDialog.IgnoreButton.Visible = false;
            ResourceManager rm  = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            extSource.SourceId = sourceId;
            extSource.SourceName = extsourceNameTextBox.Text.Trim();
            extSource.ProviderName = plugin;
            extSource.Description = extsourceDescriptionTextBox.Text.Trim();
            ConnectionProperty property;
            for (int i = 0; i < propertyCount; i++)
            {
                property = new ConnectionProperty();
                name = "l" + i.ToString();
                property.ConfigLabel = hPropertyNames[name].ToString();
                name = "t" + i.ToString();
                property.ConfigValue = hPropertyValues[name].ToString();
                property.ConfigKey = externalSource.ConnectionProperties[i].ConfigKey;
                extSource.ConnectionProperties.Add(property);
            }
            try
            {
                ROIViewUtility.MarkBusy(true);
                bool response = ROIAdminController.Instance.TestConnection(extSource);

                if (!response)
                {
                    InfoDialog.DisplayMessageText = rm.GetString(TestConnectionFailureMeassage);
                }
                else
                    InfoDialog.DisplayMessageText = rm.GetString(TestConnectionSuccessMeassage);
                DialogResult result = InfoDialog.Display();
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        private void providerComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            
            if (providerInfo)
            {
                ExternalSource extSrc = (ExternalSource)providerComboBox.SelectedItem;
                extsourceDescriptionTextBox.Visible = true;
                extsourceNameTextBox.Visible = true;
                
                foreach (ExternalSource extSource in externalsources)
                {

                    if (extSource.ProviderName.ToString().Equals(extSrc.ProviderName.ToString()))
                    {
                        providerInfo = true;
                        SetData(extSource);
                        
                    }
                }
                EnableUndoButton(true);
            }
        }
       
        public void DisplayDefaultControls()
        {
            propertiespanel.Controls.Add(externalsourcelabelimg0);
            externalsourcelabelimg0.Visible = true;
            propertiespanel.Controls.Add(providerLabel);
            providerLabel.Visible = true;
            propertiespanel.Controls.Add(providerComboBox);
            providerComboBox.Visible = true;
            if (providerInfo)
                providerInfo = false;
            providerComboBox.SelectedItem = defaultSelection;
            providerInfo = true;
        }

        private void propertiespanel_Resize(object sender, EventArgs e)
        {
            if ((propertiespanel.Height - savepropertiesButton.Bottom) > 10)
            {
                int buttonsBottomPtn = (propertiespanel.Height - savepropertiesButton.Height);
                savepropertiesButton.Location = new Point(extsourceDescriptionTextBox.Right - 70, buttonsBottomPtn);
                undopropertiesButton.Location = new Point(savepropertiesButton.Right + 15, buttonsBottomPtn);
                testpropertiesButton.Location = new Point(undopropertiesButton.Right + 400, buttonsBottomPtn);
                externalsource_requiredImage.Location = new Point(propertiespanel.Location.X+10, buttonsBottomPtn);
                externalsource_requiredLabel.Location = new Point(propertiespanel.Location.X + 25, buttonsBottomPtn);
                propertiespanel.AutoScroll = true;
            }
                       
        }

        /// <summary>
        /// Audit clinical source connection update/create/delete.
        /// </summary>
        /// <param name="auditEvent"></param>
        public void AuditConfigChanges(string auditEvent)
        {
            AuditEvent auditEvnt = new AuditEvent();
            auditEvnt.ActionCode = ROIConstants.ConfigurationChangeActionCode;
            auditEvnt.UserId = UserData.Instance.UserInstanceId;
            auditEvnt.EventStart = System.DateTime.Now;
            auditEvnt.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvnt.EventId = 1;
            auditEvnt.Facility = "E_P_R_S";
            auditEvnt.Mrn = null;
            auditEvnt.Encounter = null;
            auditEvnt.Comment = "Clinical Source " + auditextsourcename + " was " + auditEvent;
            Application.DoEvents();
            ROIController.Instance.CreateAuditEntry(auditEvnt);
        }

        /// <summary>
        /// Save the control positions.
        /// </summary>
        public void SaveLocations()
        {
            img0Vertical = externalsourcelabelimg0.Location.Y - propertiespanel.Location.Y;
            img0Hzntl = externalsourcelabelimg0.Location.X - propertiespanel.Location.X;
            img1Vertical = externalsourcelabelimg1.Location.Y - propertiespanel.Location.Y;
            img1Hzntl = providerLabel.Location.X - propertiespanel.Location.X;
            img2Vertical = externalsourcelabelimg2.Location.Y - propertiespanel.Location.Y;
            img2Hzntl = providerComboBox.Location.X - propertiespanel.Location.X;
        }

        /// <summary>
        /// Set the location of controls,after clearing all the controls in properties panel.
        /// </summary>
        public void SetLocations()
        {
            int img0VerticalLocation = propertiespanel.Location.Y + img0Vertical;
            int img1VerticalLocation = propertiespanel.Location.Y + img1Vertical;
            int img2VerticalLocation = propertiespanel.Location.Y + img2Vertical;

            int img0HzntlLocation = propertiespanel.Location.X + img0Hzntl;
            int img1HzntlLocation = propertiespanel.Location.X + img1Hzntl;
            int img2HzntlLocation = propertiespanel.Location.X + img2Hzntl;
            externalsourcelabelimg0.Top = providerLabel.Top = providerComboBox.Top = img0VerticalLocation;
            externalsourcelabelimg0.Left = externalsourcelabelimg1.Left = externalsourcelabelimg2.Left = img0HzntlLocation;
            externalsourcelabelimg1.Top = extsourceNameLabel.Top = extsourceNameTextBox.Top = img1VerticalLocation;
            externalsourcelabelimg2.Top = extsourceDescriptionlabel.Top = extsourceDescriptionTextBox.Top = img2VerticalLocation;
            providerComboBox.Left = extsourceNameTextBox.Left = extsourceDescriptionTextBox.Left = img2HzntlLocation;
            providerLabel.Left = extsourceNameLabel.Left = extsourceDescriptionlabel.Left = img1HzntlLocation;
            savepropertiesButton.Left = extsourceDescriptionTextBox.Right - 70;
            undopropertiesButton.Left = savepropertiesButton.Right + 15;
            testpropertiesButton.Left = undopropertiesButton.Right + 400;
        }

        public void SetButtonPositions()
        {
            int buttonsBottomPtn;
            buttonsBottomPtn = propertiespanel.Height - savepropertiesButton.Height;
            if (buttonsBottomPtn < (bottom + 50))
                buttonsBottomPtn = bottom + 50;
            savepropertiesButton.Location = new Point(extsourceDescriptionTextBox.Right - 70, buttonsBottomPtn);
            undopropertiesButton.Location = new Point(savepropertiesButton.Right + 15, buttonsBottomPtn);
            testpropertiesButton.Location = new Point(undopropertiesButton.Right + 400, buttonsBottomPtn);
            externalsource_requiredImage.Location = new Point(propertiespanel.Location.X+10, buttonsBottomPtn);
            externalsource_requiredLabel.Location = new Point(propertiespanel.Location.X + 25, buttonsBottomPtn);
            propertiespanel.AutoScroll = true;

            if (providerComboBox.SelectedIndex == 0)
            {
                if (!propertiespanel.Controls.Contains(savepropertiesButton) &&
                    !propertiespanel.Controls.Contains(undopropertiesButton))
                {
                    propertiespanel.Controls.Add(savepropertiesButton);
                    propertiespanel.Controls.Add(undopropertiesButton);
                }
            }
        }

        public void ClearControls()
        {
            propertiespanel.Controls.Clear();
            propertiespanel.Controls.Add(savepropertiesButton);
            propertiespanel.Controls.Add(undopropertiesButton);
            propertiespanel.Controls.Add(testpropertiesButton);
            testpropertiesButton.Enabled = false;
        }

        #endregion

        #region Properites

        public bool EnableSave
        {
            get { return enableSave; }
        }

        #endregion   

    }
}
