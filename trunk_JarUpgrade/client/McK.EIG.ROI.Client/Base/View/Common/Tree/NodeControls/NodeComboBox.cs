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
	public class NodeComboBox : BaseTextControl
	{
		#region Properties

		private int _editorWidth = 100;
		[DefaultValue(100)]
		public int EditorWidth
		{
			get { return _editorWidth; }
			set { _editorWidth = value; }
		}

		private IList<object> _dropDownItems;
		[Editor(typeof(StringCollectionEditor), typeof(UITypeEditor)), DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
		public IList<object> DropDownItems
		{
			get { return _dropDownItems; }
		}

		#endregion

		public NodeComboBox()
		{
			_dropDownItems = new List<object>();
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
			ComboBox comboBox = new ComboBox();
            if (DropDownItems != null)
            {
                object[] objArray = null;
                DropDownItems.CopyTo(objArray, 0);
                comboBox.Items.AddRange(objArray);
            }
			comboBox.SelectedItem = GetValue(node);
			comboBox.DropDownStyle = ComboBoxStyle.DropDownList;
			comboBox.DropDownClosed += new EventHandler(EditorDropDownClosed);
			SetEditControlProperties(comboBox, node);
			return comboBox;
		}

		void EditorDropDownClosed(object sender, EventArgs e)
		{
			EndEdit(true);
		}

		public override void UpdateEditor(Control control)
		{
			(control as ComboBox).DroppedDown = true;
		}

		protected override void DoApplyChanges(TreeNodeAdv node, Control editor)
		{
			SetValue(node, (editor as ComboBox).SelectedItem);
		}

		public override void MouseUp(TreeNodeAdvMouseEventArgs args)
		{
			if (args.Node != null && args.Node.IsSelected) //Workaround of specific ComboBox control behaviour
				base.MouseUp(args);
		}
	}
}
