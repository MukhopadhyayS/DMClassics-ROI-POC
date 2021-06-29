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
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View
{
    public partial class OutputPropertiesDialog : ROIBaseUI
    {  
        #region Fields

        private const string OutputDialog = "OutputDialog";
        private DestinationType outputDestinationType;
        private Collection<PropertyDefinition> propertyDefinitions;
        private bool isDisc;

        #endregion

        #region Constructor

        public OutputPropertiesDialog()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputPropertiesDialog(IPane pane): this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
        }
        
        #endregion

        #region Methods

        /// <summary>
        /// Localize UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            headerLabel.Text     = rm.GetString(GetType().Name + "." + OutputDestinationType.ToString() + "." + headerLabel.Name);
            headerInfoLabel.Text = rm.GetString(GetType().Name + "." + OutputDestinationType.ToString() + "." + headerInfoLabel.Name);

            okButton.Text     = rm.GetString("okButton.DialogUI");
            cancelButton.Text = rm.GetString("cancelButton.DialogUI");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, okButton);
            SetTooltip(rm, toolTip, cancelButton);
            
        }

        /// <summary>
        /// Localize the UI Tooltip.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// PrePoulate the properties
        /// </summary>
        /// <param name="propertyDefinitions"></param>
        public void PrePopulate(Collection<PropertyDefinition> propertyDefinitions, Hashtable properties)
        {
            if (isDisc)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                string okButtonToolTip = rm.GetString("okButton.DiscUI");
                string cancelButtonToolTip = rm.GetString("cancelButton.DiscUI");
                toolTip.SetToolTip(okButton, okButtonToolTip);
                toolTip.SetToolTip(cancelButton, cancelButtonToolTip);
                
            }
            Dialog dialog;            
            foreach (PropertyDefinition propertyDefinition in propertyDefinitions)
            {
                if (!string.IsNullOrEmpty(propertyDefinition.Label))
                {
                    if (isDisc)
                    {
                        if (("Printers").Equals(propertyDefinition.Label))
                        {
                            continue;
                        }
                    }
                    dialog = new Dialog();
                    dialog.Label = propertyDefinition.Label;
                    dialog.PropertyType = (PropertyType)Enum.Parse(typeof(PropertyType), propertyDefinition.DataType, true);
                    dialog.DefaultValue = propertyDefinition.DefaultValue;
                    if (properties != null)
                    {
                        if (properties.Contains(propertyDefinition.PropertyName))
                        {
                            dialog.DefaultValue = (string)properties[propertyDefinition.PropertyName];
                        }
                    }
                    dialog.Description = propertyDefinition.Description;
                    dialog.PropertyName = propertyDefinition.PropertyName;

                    if (dialog.PropertyType == PropertyType.String)
                    {
                        if (propertyDefinition.PossibleValues.Count > 0)
                        {
                            dialog.PropertyType = PropertyType.List;
                            dialog.PrePopulate(propertyDefinition.PossibleValues);
                        }
                        else
                        {
                            dialog.PropertyType = PropertyType.String;
                        }
                    }
                    else if (dialog.PropertyType == PropertyType.Boolean)
                    {
                        dialog.PropertyType = PropertyType.Boolean;
                        dialog.PrePopulate(propertyDefinition.PossibleValues);
                    }
                    propertyFlowLayoutPanel.Controls.Add(dialog);

                    if (propertyFlowLayoutPanel.Controls.Count % 2 != 0)
                    {
                        dialog.DialogBackColor = Color.FromArgb(237, 239, 246);
                    }
                    dialog.Height = 30;
                    dialog.Width = propertyFlowLayoutPanel.Width - 8;
                }
            }

            isDisc = false;
        }
      
        /// <summary>
        /// Occurs when user clicks on "Ok" button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void okButton_Click(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);
            propertyDefinitions = new Collection<PropertyDefinition>();
            PropertyDefinition propertyDefinition;
            foreach (Dialog dialog in propertyFlowLayoutPanel.Controls)
            {
                dialog.GetData();
                propertyDefinition = new PropertyDefinition(dialog.PropertyName,dialog.DefaultValue);                
                propertyDefinitions.Add(propertyDefinition);
            }
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the user presses the Enter key.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = okButton;
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to gets or sets the Letter Types
        /// </summary>
        public DestinationType OutputDestinationType
        {
            get { return outputDestinationType; }            
            set { outputDestinationType = value; }            
        }

        /// <summary>
        /// This property is used to gets or sets the property definitions
        /// </summary>
        public Collection<PropertyDefinition> PropertyDefinitions
        {
            get
            {
                if (propertyDefinitions == null)
                {
                    propertyDefinitions = new Collection<PropertyDefinition>();
                }
                return propertyDefinitions; 
            }
        }

        public bool IsDisc
        {
            get { return isDisc; }
            set { isDisc = value; }
        }

        #endregion
    }
}
