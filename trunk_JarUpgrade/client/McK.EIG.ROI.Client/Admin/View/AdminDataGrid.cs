using System;
using System.Collections.Generic;
using System.ComponentModel;
using McK.EIG.ROI.Client.Base.View.Common;
using System.Globalization;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// AdminDataGrid
    /// </summary>
    public partial class AdminDataGrid : EIGDataGrid
    {
        private bool alreadySorted;

        public AdminDataGrid()
        {
            InitializeComponent();
        }

        public AdminDataGrid(IContainer container)
        {
            container.Add(this);

            InitializeComponent();
        }

        public override void Sort(System.Windows.Forms.DataGridViewColumn dataGridViewColumn, ListSortDirection direction)
        {
            if (dataGridViewColumn.DataPropertyName == "Amount" && !alreadySorted)
            {
                if (Convert.ToString(dataGridViewColumn.Tag, System.Threading.Thread.CurrentThread.CurrentCulture) == "FeeType")
                {
                    alreadySorted = true;
                    base.Sort(dataGridViewColumn, ListSortDirection.Descending);
                }
            }
            else
            {
                base.Sort(dataGridViewColumn, direction);
            }
        }
    }
}
