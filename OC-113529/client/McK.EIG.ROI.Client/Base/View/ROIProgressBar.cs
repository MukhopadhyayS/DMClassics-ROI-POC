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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.FileTransfer.Controller.Upload;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class ROIProgressBar : UserControl
    {
        #region Constructor

        public ROIProgressBar()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Progress will be invoked
        /// </summary>
        /// <param name="e"></param>
        public void ShowProgress(FileTransferEventArgs e)
        {
            long completion = 0;
            switch (e.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    fileTransferProgressBar.Value = Convert.ToInt32(completion);
                    break;
                case FileTransferEventArgs.Status.InProgress:
                    completion = (e.TransferredSize * 100) / e.FileSize;
                    fileTransferProgressBar.Value += Convert.ToInt32(completion) - fileTransferProgressBar.Value;
                    break;
                case FileTransferEventArgs.Status.Finish:
                    completion = (e.TransferredSize * 100) / e.FileSize;
                    fileTransferProgressBar.Value += Convert.ToInt32(completion) - fileTransferProgressBar.Value;
                    break;
            }
            percentageLabel.Text = completion.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + "%";
            this.Refresh();
        }

        /// <summary>
        /// Message that displayed above the progress bar.
        /// </summary>
        public Label MessageText
        {
            get { return messageLabel; }
            set { messageLabel = value; }
        }

        /// <summary>
        /// Percentage that displayed within the progress bar.
        /// </summary>
        public Label PercentageLabel
        {
            get { return percentageLabel; }
            set { percentageLabel = value; }
        }

        #endregion
    }
}
