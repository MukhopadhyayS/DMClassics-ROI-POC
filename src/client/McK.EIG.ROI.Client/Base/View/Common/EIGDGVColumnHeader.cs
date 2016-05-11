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
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Windows.Forms.VisualStyles;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    /// <summary>
    /// columnheader with Checkbox for DataGrid
    /// </summary>
    public class EIGCheckedColumnHeader : DataGridViewColumnHeaderCell
    {
        private Rectangle CheckBoxRegion;
        private bool checkAll;

        protected override void Paint(Graphics graphics,
            Rectangle clipBounds, Rectangle cellBounds, int rowIndex,
            DataGridViewElementStates dataGridViewElementState,
            object value, object formattedValue, string errorText,
            DataGridViewCellStyle cellStyle, DataGridViewAdvancedBorderStyle advancedBorderStyle,
            DataGridViewPaintParts paintParts)
        {

            base.Paint(graphics, clipBounds, cellBounds, rowIndex, dataGridViewElementState, value,
                formattedValue, errorText, cellStyle, advancedBorderStyle, paintParts);

            graphics.FillRectangle(new SolidBrush(cellStyle.BackColor), cellBounds);

            CheckBoxRegion = new Rectangle(
                cellBounds.Location.X + 4,
                cellBounds.Location.Y + 2,
                15, cellBounds.Size.Height - 4);


            if (this.checkAll)
                ControlPaint.DrawCheckBox(graphics, CheckBoxRegion, ButtonState.Checked);
            else
                ControlPaint.DrawCheckBox(graphics, CheckBoxRegion, ButtonState.Normal);

            Rectangle normalRegion =
                new Rectangle(
                cellBounds.Location.X + 1 + 25,
                cellBounds.Location.Y,
                cellBounds.Size.Width - 26,
                cellBounds.Size.Height);

            graphics.DrawString(value.ToString(), cellStyle.Font, new SolidBrush(cellStyle.ForeColor), normalRegion);
        }

        protected override void OnMouseClick(DataGridViewCellMouseEventArgs e)
        {
            //Convert the CheckBoxRegion 
            Rectangle rec = new Rectangle(new Point(0, 0), this.CheckBoxRegion.Size);
            this.checkAll = !this.checkAll;
            if (rec.Contains(e.Location))
            {
                this.DataGridView.Invalidate();
            }
            base.OnMouseClick(e);
        }

        public bool CheckAll
        {
            get { return this.checkAll; }
            set { this.checkAll = value; }
        }

    }

    /// <summary>
    //  Below classes are used to visually disable the checkbox in the grid.
    /// </summary>
    public class DataGridViewDisableCheckBoxColumn : DataGridViewCheckBoxColumn
    {
        public DataGridViewDisableCheckBoxColumn()
        {
            this.CellTemplate = new DataGridViewDisableCheckBoxCell();
        }
    }

    public class DataGridViewDisableCheckBoxCell : DataGridViewCheckBoxCell
    {
        protected override void Paint(System.Drawing.Graphics graphics, System.Drawing.Rectangle clipBounds,
                                      System.Drawing.Rectangle cellBounds, int rowIndex, DataGridViewElementStates elementState,
                                      object value, object formattedValue, string errorText, DataGridViewCellStyle cellStyle,
                                      DataGridViewAdvancedBorderStyle advancedBorderStyle, DataGridViewPaintParts paintParts)
        {
            base.Paint(graphics, clipBounds, cellBounds, rowIndex, elementState, value, formattedValue, errorText,
                       cellStyle, advancedBorderStyle, paintParts);

            bool gridTagValue = Tag != null ? Boolean.Parse(Tag.ToString()) : true;

            if (!gridTagValue)
            {
                CheckBoxRenderer.DrawCheckBox(graphics,
                    new Point(cellBounds.X + 58, cellBounds.Y + 3),
                    new Rectangle(cellBounds.X + 2, cellBounds.Y + 4, cellBounds.Width, cellBounds.Height - (4 * 2)),
                    string.Empty,
                    OwningColumn.InheritedStyle.Font,
                    TextFormatFlags.Left,
                    false,
                    System.Windows.Forms.VisualStyles.CheckBoxState.UncheckedDisabled);
            }
        }
    }
}
