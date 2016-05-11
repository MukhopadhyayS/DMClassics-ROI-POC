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
using System.Windows.Forms;
using System.Drawing;
using System.ComponentModel;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls
{
	public abstract class EditableControl : InteractiveControl
	{
		private Timer _timer;
		private bool _editFlag;

		#region Properties

		private TreeNodeAdv _editNode;
		protected TreeNodeAdv EditNode
		{
			get { return _editNode; }
		}

		private Control _editor;
		protected Control CurrentEditor
		{
			get { return _editor; }
		}

        private bool _editOnClick;
		[DefaultValue(false)]
		public bool EditOnClick
		{
			get { return _editOnClick; }
			set { _editOnClick = value; }
		}
		
		#endregion

		protected EditableControl()
		{
			_timer = new Timer();
			_timer.Interval = 1000;
			_timer.Tick += new EventHandler(TimerTick);
		}

		private void TimerTick(object sender, EventArgs e)
		{
			_timer.Stop();
			if (_editFlag)
				BeginEditByUser();
			_editFlag = false;
		}

		public void SetEditorBounds(EditorContext context)
		{
			Size size = CalculateEditorSize(context);
			context.Editor.Bounds = new Rectangle(context.Bounds.X, context.Bounds.Y,
				Math.Min(size.Width, context.Bounds.Width), context.Bounds.Height);
		}

		protected abstract Size CalculateEditorSize(EditorContext context);

		protected virtual bool CanEdit(TreeNodeAdv node)
		{
			return (node.Tag != null) && IsEditEnabled(node);
		}

		protected void BeginEditByUser()
		{
			if (EditEnabled)
				BeginEdit();
		}

		public void BeginEdit()
		{
			if (Parent.CurrentNode != null && CanEdit(Parent.CurrentNode))
			{
				CancelEventArgs args = new CancelEventArgs();
				OnEditorShowing(args);
				if (!args.Cancel)
				{
					_editor = CreateEditor(Parent.CurrentNode);
					_editor.Validating += new CancelEventHandler(EditorValidating);
					_editor.KeyDown += new KeyEventHandler(EditorKeyDown);
					_editNode = Parent.CurrentNode;
					Parent.DisplayEditor(_editor, this);
				}
			}
		}

		private void EditorKeyDown(object sender, KeyEventArgs e)
		{
			if (e.KeyCode == Keys.Escape)
				EndEdit(false);
			else if (e.KeyCode == Keys.Enter)
				EndEdit(true);
		}

		private void EditorValidating(object sender, CancelEventArgs e)
		{
			ApplyChanges();
		}

		internal void HideEditor(Control editor)
		{
			editor.Validating -= new CancelEventHandler(EditorValidating);
			editor.Parent = null;
			editor.Dispose();
			_editNode = null;
			OnEditorHided();
		}

		public void EndEdit(bool applyChanges)
		{
			if (!applyChanges)
				_editor.Validating -= new CancelEventHandler(EditorValidating);
			Parent.Focus();
		}

		public virtual void UpdateEditor(Control control)
		{
		}

		public virtual void ApplyChanges()
		{
			try
			{
				DoApplyChanges(_editNode, _editor);
			}
			catch (ArgumentException ex)
			{
				MessageBox.Show(ex.Message, string.Empty, MessageBoxButtons.OK, MessageBoxIcon.Warning,MessageBoxDefaultButton.Button1,0);
			}
		}

		protected abstract void DoApplyChanges(TreeNodeAdv node, Control editor);

		protected abstract Control CreateEditor(TreeNodeAdv node);

		public override void MouseDown(TreeNodeAdvMouseEventArgs args)
		{
			_editFlag = (!EditOnClick && args.Button == MouseButtons.Left 
				&& args.ModifierKeys == Keys.None && args.Node.IsSelected);
		}

		public override void MouseUp(TreeNodeAdvMouseEventArgs args)
		{
			if (EditOnClick && args.Button == MouseButtons.Left && args.ModifierKeys == Keys.None)
			{
				Parent.ItemDragMode = false;
				BeginEdit();
				args.Handled = true;
			}
			else if (_editFlag && args.Node.IsSelected)
				_timer.Start();
		}

		public override void MouseDoubleClick(TreeNodeAdvMouseEventArgs args)
		{
			_editFlag = false;
			_timer.Stop();
		}

		protected override void Dispose(bool disposing)
		{
			base.Dispose(disposing);
			if (disposing)
				_timer.Dispose();
		}

		#region Events

		public event CancelEventHandler EditorShowing;
		protected void OnEditorShowing(CancelEventArgs args)
		{
			if (EditorShowing != null)
				EditorShowing(this, args);
		}

		public event EventHandler EditorHided;
		protected void OnEditorHided()
		{
			if (EditorHided != null)
				EditorHided(this, EventArgs.Empty);
		}

		#endregion
	}
}
