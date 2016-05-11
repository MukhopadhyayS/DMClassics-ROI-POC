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
using System.Windows.Forms;
using System.Drawing;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Displays the UserControl MediaTypeHeader.
    /// </summary>
    public partial class HeaderUI : UserControl
    {
        #region Fields

        private Control headerExtension;

        #endregion

        #region Constructor
        /// <summary>
        /// Initialize the MediaTypeHeaderUI.
        /// </summary>
        public HeaderUI()
        {
            InitializeComponent();
        }
        #endregion

        #region Properties
        /// <summary>
        /// Sets the Title text.
        /// </summary>
        public string Title
        {
            get
            {
                return titleLabel.Text;
            }
            set
            {
                titleLabel.Text = value;
            }
        }
        
        /// <summary>
        /// Sets the Information text.
        /// </summary>
        public string Information
        {
            get
            {
                return infomationLabel.Text;
            }
            set
            {
                infomationLabel.Text = value;
            }
        }

        public Font FontInformation
        {
            get
            {
                return infomationLabel.Font;
            }
            set
            {
                infomationLabel.Font = value;
            }
        }



        public Control HeaderExtension
        {
            set
            {
                headerExtension = value as Control;
                headerExtension.Name = "HeaderExt";
                headerExtension.Dock = DockStyle.Right;
                if (this.Controls.ContainsKey(headerExtension.Name))
                {
                    this.Controls.RemoveByKey(headerExtension.Name);
                }
                this.Controls.Add(headerExtension);
            }
            get
            {
                return headerExtension;
            }
        }

        public Panel TitlePanel
        {
            get
            {
                return panel1;
            }
        }

        public Panel InformationPanel
        {
            get
            {
                return panel2;
            }
        }

        #endregion
    }
}
