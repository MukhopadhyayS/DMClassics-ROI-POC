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
using System.Drawing;
using System.Globalization;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.View
{

    public enum PropertyType
    {
        List,
        Number,
        Boolean,
        String,
		Collection
    }

    public partial class Dialog : ROIBaseUI
    {
       
        #region Fields

        private RadioButton onRadioButton;
        private RadioButton OffRadioButton;
        private ComboBox comboBox;
        private NumericUpDown numericUpDown;
        private TextBox textBox;
        private PropertyType propertyType;
        private string defaultValue;
        private string description;
        private string propertyName;

        #endregion

        #region Constructor

        public Dialog()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// PrePopulate the property data.
        /// </summary>
        /// <param name="data"></param>
        public void PrePopulate(object data)
        {
            ArrayList possibleValues = data as ArrayList;           

            if (propertyType == PropertyType.List)
            {
                comboBox = new ComboBox();
                comboBox.DropDownStyle = ComboBoxStyle.DropDownList;
                flowLayoutPanel.Controls.Add(comboBox);
                comboBox.DisplayMember = "key";
                comboBox.ValueMember = "value";
                comboBox.DataSource = possibleValues;   
                comboBox.SelectedItem = defaultValue;
            }
            else if (propertyType == PropertyType.Boolean)
            {
                onRadioButton = new RadioButton();
                onRadioButton.Text = possibleValues[0].ToString();
                onRadioButton.Width = 50;
                OffRadioButton = new RadioButton();
                OffRadioButton.Text = possibleValues[1].ToString();
                flowLayoutPanel.Controls.Add(onRadioButton);
                flowLayoutPanel.Controls.Add(OffRadioButton);
                //onRadioButton.Checked = (defaultValue == possibleValues[0].ToString());
                //OffRadioButton.Checked = (defaultValue == possibleValues[1].ToString());
                if (!string.IsNullOrEmpty(defaultValue))
                {
                    if (defaultValue == "Yes" || defaultValue == "On" || defaultValue == "true")
                    {
                        onRadioButton.Checked = true;
                    }
                    else if(defaultValue == "No" || defaultValue == "Off" || defaultValue == "false")
                    {
                        OffRadioButton.Checked = true;
                    }
               }               
            }
        }

        /// <summary>
        /// Occurs when dialog loads.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void dialog_Load(object sender, EventArgs e)
        {
            propertyLabel.Text = Label;

            if (propertyType == PropertyType.String)
            {
                textBox = new TextBox();
                textBox.Text = defaultValue;
                flowLayoutPanel.Controls.Add(textBox);
            }
            else if (propertyType == PropertyType.Number)
            {
                numericUpDown = new NumericUpDown();
                numericUpDown.Maximum = 256;
                numericUpDown.Value = Convert.ToInt16(defaultValue, System.Threading.Thread.CurrentThread.CurrentUICulture);
                numericUpDown.Size = new Size(57, 20);
                numericUpDown.TextAlign = HorizontalAlignment.Center;
                numericUpDown.ReadOnly = true;
                flowLayoutPanel.Controls.Add(numericUpDown);
            }
        }

        /// <summary>
        /// Retrieves the data from UI.
        /// </summary>
        public void GetData()
        {
            if (propertyType == PropertyType.List)
            {
                defaultValue = comboBox.SelectedValue.ToString();
            }
            else if (propertyType == PropertyType.Boolean)
            {
                if (onRadioButton.Checked)
                {
                    defaultValue = "true";                  
                }
                else
                {
                    defaultValue = "false";
                }
            }
            else if (propertyType == PropertyType.Number)
            {
                defaultValue = numericUpDown.Value.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            else if (propertyType == PropertyType.String)
            {
                defaultValue = textBox.Text;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the property label.
        /// </summary>
        public string Label
        {
            get { return propertyLabel.Text; }
            set { propertyLabel.Text = value; }            
        }

        /// <summary>
        /// Gets or sets the property type.
        /// </summary>
        public PropertyType PropertyType
        {
            get { return propertyType; }
            set { propertyType = value; }           
        }

        /// <summary>
        /// Gets or sets the property name.
        /// </summary>
        public string PropertyName
        {
            get { return propertyName; }
            set { propertyName = value; }
        }

        /// <summary>
        /// Gets or sets the property default value.
        /// </summary>
        public string DefaultValue
        {
            get { return defaultValue; }
            set { defaultValue = value; } 
        }

        /// <summary>
        /// Gets or sets the property description.
        /// </summary>
        public string Description
        {
             get { return description; }
             set { description = value; }
        }

        /// <summary>
        /// Gets or sets the dialg back color.
        /// </summary>
        public Color DialogBackColor
        {
            get { return tableLayoutPanel.BackColor; }
            set { tableLayoutPanel.BackColor = value; } 
        }

        #endregion
    }
}
