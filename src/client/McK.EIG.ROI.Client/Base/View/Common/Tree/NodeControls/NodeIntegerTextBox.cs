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
using System.Text;
using System.ComponentModel;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls
{

	public class NodeIntegerTextBox : NodeTextBox
	{
		private bool _allowNegativeSign = true;
		[DefaultValue(true)]
		public bool AllowNegativeSign
		{
			get { return _allowNegativeSign; }
			set { _allowNegativeSign = value; }
		}

		public NodeIntegerTextBox()
		{
		}

		protected override TextBox CreateTextBox()
		{
			NumericTextBox textBox = new NumericTextBox();
			textBox.AllowDecimalSeparator = false;
			textBox.AllowNegativeSign = AllowNegativeSign;
			return textBox;
		}

		protected override void DoApplyChanges(TreeNodeAdv node, Control editor)
		{
			SetValue(node, (editor as NumericTextBox).IntValue);
		}
	}
}
