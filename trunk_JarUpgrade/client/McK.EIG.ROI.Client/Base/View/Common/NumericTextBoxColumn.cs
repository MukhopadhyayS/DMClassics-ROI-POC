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
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public class NumericTextBoxColumn : DataGridViewColumn
    {
        private int maxLength;
        public NumericTextBoxColumn(int maxLength): base(new NumericTextBoxCell())
        {
            this.maxLength = maxLength;
        }

        public override DataGridViewCell CellTemplate
        {
            get
            {
                NumericTextBoxCell dgvCell = (base.CellTemplate as NumericTextBoxCell);
                dgvCell.Tag = maxLength;
                return dgvCell;
            }
            set
            {
                if (value != null &&
                    !value.GetType().IsAssignableFrom(typeof(NumericTextBoxCell)))
                {
                    throw new InvalidCastException("Must be a TextBox Cell");
                }
                base.CellTemplate = value;
            }
        }
    }

    public class NumericTextBoxCell : DataGridViewTextBoxCell
    {
        private TextBox numberTextBox;
        public override Type EditType
        {
            get
            {
                return typeof(NumericTextBoxEditingControl);
            }
        }

        public override Type ValueType
        {
            get
            {

                return typeof(String);
            }
            set
            {
                base.ValueType = value;
            }
        }

        public override Type FormattedValueType
        {
            get
            {
                return typeof(string);
            }
        }

        public override void InitializeEditingControl(int rowIndex, object
            initialFormattedValue, DataGridViewCellStyle dataGridViewCellStyle)
        {
            // Set the value of the editing control to the current cell value.
            base.InitializeEditingControl(rowIndex, initialFormattedValue, dataGridViewCellStyle);
            NumericTextBoxClass ctl = DataGridView.EditingControl as NumericTextBoxClass;
            // Use the default row value when Value property is null.
            if (ctl != null)
            {
                numberTextBox = ctl.Controls[0] as TextBox;
                numberTextBox.MaxLength = (int)this.Tag;
                numberTextBox.TextChanged += new EventHandler(numberTextBox_TextChanged);
                numberTextBox.Text = Convert.ToString(initialFormattedValue, System.Threading.Thread.CurrentThread.CurrentUICulture);

            }
        }

        private void numberTextBox_TextChanged(object sender, EventArgs e)
        {
            if (DataGridView != null)
            {
                NumericTextBoxEditingControl editControl = DataGridView.EditingControl as NumericTextBoxEditingControl;
                if (editControl != null)
                {
                    editControl.EditingControlValueChanged = true;
                    editControl.EditingControlDataGridView.NotifyCurrentCellDirty(true);
                }
            }
        }

        protected override object GetFormattedValue(object value, int rowIndex, ref DataGridViewCellStyle cellStyle, TypeConverter valueTypeConverter, TypeConverter formattedValueTypeConverter, DataGridViewDataErrorContexts context)
        {
            return value;
        }

        protected override void Paint(Graphics graphics, Rectangle clipBounds, Rectangle cellBounds, int rowIndex, DataGridViewElementStates cellState, object value, object formattedValue, string errorText, DataGridViewCellStyle cellStyle, DataGridViewAdvancedBorderStyle advancedBorderStyle, DataGridViewPaintParts paintParts)
        {
            base.Paint(graphics, clipBounds, cellBounds, rowIndex, cellState, value, formattedValue, errorText, cellStyle, advancedBorderStyle, paintParts);

            // Draw the cell background, if specified.
            if ((paintParts & DataGridViewPaintParts.Background) == DataGridViewPaintParts.Background)
            {
                Brush cellBackground = new SolidBrush(this.Selected ? cellStyle.SelectionBackColor : cellStyle.BackColor);
                graphics.FillRectangle(cellBackground, cellBounds);
                cellBackground.Dispose();
            }

            PaintBorder(graphics, clipBounds, cellBounds, cellStyle, advancedBorderStyle);

            Font font = new Font("Arial", 9F, FontStyle.Regular);
            Size size = TextRenderer.MeasureText("Arial", font);
            Point center = new Point(cellBounds.X - 4, cellBounds.Y + 3);
            Point tbcenter = new Point(cellBounds.X+11, cellBounds.Y);
            Rectangle rect = new Rectangle(center, size);
            Rectangle tbrect = new Rectangle(tbcenter, new Size(46, 22));
            int number;
            if (formattedValue == null || string.IsNullOrEmpty(formattedValue.ToString()) || (!Int32.TryParse(formattedValue.ToString(), out number)))
            {
                formattedValue = 0;
            }
            if (TextBoxRenderer.IsSupported)
            {
                TextBoxRenderer.DrawTextBox(graphics,
                                            tbrect,
                                            Int32.Parse(formattedValue.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture).ToString(System.Threading.Thread.CurrentThread.CurrentUICulture),
                                            font,
                                            TextFormatFlags.TextBoxControl,
                                            !ReadOnly ? System.Windows.Forms.VisualStyles.TextBoxState.Normal :
                                            System.Windows.Forms.VisualStyles.TextBoxState.Disabled);
            }
            else
            {
                if (ReadOnly)
                {
                    graphics.FillRectangle(new SolidBrush(SystemColors.ActiveBorder), tbrect);
                }
                ControlPaint.DrawBorder3D(graphics, tbrect, Border3DStyle.Sunken);
                TextRenderer.DrawText(graphics,
                                      Int32.Parse(formattedValue.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture).ToString(System.Threading.Thread.CurrentThread.CurrentUICulture),
                                      font,
                                      new Rectangle(tbrect.Left + 3, tbrect.Top, tbrect.Width - 4, tbrect.Height),
                                      !ReadOnly ? SystemColors.ControlText : SystemColors.GrayText,
                                      TextFormatFlags.VerticalCenter);
            }
        }
    }

    public class NumericTextBoxEditingControl : NumericTextBoxClass, IDataGridViewEditingControl
    {
        private DataGridView dataGridView;
        private bool valueChanged;
        private int rowIndex;

        public NumericTextBoxEditingControl()
        {
            this.BorderStyle = BorderStyle.None;
        }

        protected override void OnTextChanged(EventArgs e)
        {
            base.OnTextChanged(e);

            valueChanged = true;
            this.EditingControlDataGridView.NotifyCurrentCellDirty(true);
        }

        public void ApplyCellStyleToEditingControl(DataGridViewCellStyle dataGridViewCellStyle)
        {
            this.Font = dataGridViewCellStyle.Font;
        }

        // Implements the IDataGridViewEditingControl
        // .EditingControlDataGridView property.
        public DataGridView EditingControlDataGridView
        {
            get { return dataGridView; }
            set { dataGridView = value; }
        }

        // Implements the IDataGridViewEditingControl.EditingControlFormattedValue 
        // property.
        public object EditingControlFormattedValue
        {
            get
            {
                return this.Controls[0].Text.ToString();
            }
            set
            {
                if (value is String)
                {
                    this.Controls[0].Text = value.ToString();
                }
            }
        }

        // Implements the IDataGridViewEditingControl.EditingControlRowIndex 
        // property.
        public int EditingControlRowIndex
        {
            get
            {
                return rowIndex;
            }
            set
            {
                rowIndex = value;
            }
        }

        // Implements the IDataGridViewEditingControl
        // .EditingControlValueChanged property.
        public bool EditingControlValueChanged
        {
            get
            {
                return valueChanged;
            }
            set
            {
                valueChanged = value;
            }
        }

        // Implements the IDataGridViewEditingControl.EditingControlWantsInputKey 
        // method.
        public bool EditingControlWantsInputKey(Keys keyData, bool dataGridViewWantsInputKey)
        {
            switch ((keyData & Keys.KeyCode))
            {
                case Keys.Return:
                    if ((((keyData & (Keys.Alt | Keys.Control | Keys.Shift)) == Keys.Shift)))
                    {
                        return true;
                    }
                    break;
                case Keys.Left:
                case Keys.Right:
                case Keys.Up:
                case Keys.Down:
                    return true;
            }

            return !dataGridViewWantsInputKey;
        }

        // Implements the IDataGridViewEditingControl
        // .EditingPanelCursor property.
        public Cursor EditingPanelCursor
        {
            get
            {
                return base.Cursor;
            }
        }

        // Implements the 
        // IDataGridViewEditingControl.GetEditingControlFormattedValue method.
        public object GetEditingControlFormattedValue(
            DataGridViewDataErrorContexts context)
        {
            return EditingControlFormattedValue;
        }


        // Implements the IDataGridViewEditingControl.PrepareEditingControlForEdit 
        // method.
        public void PrepareEditingControlForEdit(bool selectAll)
        {
            // No preparation needs to be done.
        }

        // Implements the IDataGridViewEditingControl
        // .RepositionEditingControlOnValueChange property.
        public bool RepositionEditingControlOnValueChange
        {
            get
            {
                return false;
            }
        }
    }
}
