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
using System.Drawing;
using System.Windows.Forms;
using System.Reflection;
using System.ComponentModel;
using System.Drawing.Design;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls
{
	public class NodeNumericUpDown : BaseTextControl
	{
		#region Properties

		private int _editorWidth = 100;
		[DefaultValue(100)]
		public int EditorWidth
		{
			get { return _editorWidth; }
			set { _editorWidth = value; }
		}

        private int _decimalPlaces;
		[Category("Data"), DefaultValue(0)]
		public int DecimalPlaces
		{
			get
			{
				return this._decimalPlaces;
			}
			set
			{
				this._decimalPlaces = value;
			}
		}

		private decimal _increment = 1;
		[Category("Data"), DefaultValue(1)]
		public decimal Increment
		{
			get
			{
				return this._increment;
			}
			set
			{
				this._increment = value;
			}
		}

		private decimal _minimum = 0;
		[Category("Data"), DefaultValue(0)]
		public decimal Minimum
		{
			get
			{
				return _minimum;
			}
			set
			{
				_minimum = value;
			}
		}

		private decimal _maximum = 100;
		[Category("Data"), DefaultValue(100)]
		public decimal Maximum
		{
			get
			{
				return this._maximum;
			}
			set
			{
				this._maximum = value;
			}
		}

		#endregion

		public NodeNumericUpDown()
		{
		}

		protected override Size CalculateEditorSize(EditorContext context)
		{
			if (Parent.UseColumns)
				return context.Bounds.Size;
			else
				return new Size(EditorWidth, context.Bounds.Height);
		}

		protected override Control CreateEditor(TreeNodeAdv node)
		{
			NumericUpDown num = new NumericUpDown();
			num.Increment = Increment;
			num.DecimalPlaces = DecimalPlaces;
			num.Minimum = Minimum;
			num.Maximum = Maximum;
			num.Value = (decimal)GetValue(node);
			SetEditControlProperties(num, node);
			return num;
		}

		protected override void DoApplyChanges(TreeNodeAdv node, Control editor)
		{
			SetValue(node, (editor as NumericUpDown).Value);
		}
	}
}
