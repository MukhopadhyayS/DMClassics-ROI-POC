namespace McK.EIG.ROI.Client.Requestors.View.AccountHistory
{
    partial class AccountHistoryUI
    {
        private System.ComponentModel.IContainer components = null;
                
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            this.panel5 = new System.Windows.Forms.Panel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.panel1 = new System.Windows.Forms.Panel();
            this.prebillGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.panelViewStatement = new System.Windows.Forms.Panel();
            this.buttonViewStatements = new System.Windows.Forms.Button();
            this.labelStatement = new System.Windows.Forms.Label();
            this.panelViewInvoice = new System.Windows.Forms.Panel();
            this.buttonViewInvoice = new System.Windows.Forms.Button();
            this.labelInvoice = new System.Windows.Forms.Label();
            this.labelpreBill = new System.Windows.Forms.Label();
            this.panelViewPreBill = new System.Windows.Forms.Panel();
            this.buttonViewPrebill = new System.Windows.Forms.Button();
            this.panelStatement = new System.Windows.Forms.Panel();
            this.statementGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.panelInvoice = new System.Windows.Forms.Panel();
            this.invoiceGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.configureDaysServiceWse1 = new McK.EIG.ROI.Client.WebReferences.ConfigureDaysWS.ConfigureDaysServiceWse();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.prebillGrid)).BeginInit();
            this.panelViewStatement.SuspendLayout();
            this.panelViewInvoice.SuspendLayout();
            this.panelViewPreBill.SuspendLayout();
            this.panelStatement.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.statementGrid)).BeginInit();
            this.panelInvoice.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.invoiceGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // panel5
            // 
            this.panel5.Location = new System.Drawing.Point(3, 84);
            this.panel5.Name = "panel5";
            this.panel5.Size = new System.Drawing.Size(579, 32);
            this.panel5.TabIndex = 13;
            // 
            // panel1
            // 
            this.panel1.AutoScroll = true;
            this.panel1.Controls.Add(this.prebillGrid);
            this.panel1.Location = new System.Drawing.Point(0, 27);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(850, 101);
            this.panel1.TabIndex = 21;
            // 
            // prebillGrid
            // 
            this.prebillGrid.AllowUserToAddRows = false;
            this.prebillGrid.AllowUserToDeleteRows = false;
            this.prebillGrid.AllowUserToResizeRows = false;
            this.prebillGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.prebillGrid.BackgroundColor = System.Drawing.Color.White;
            this.prebillGrid.ChangeValidator = null;
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.prebillGrid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle1;
            this.prebillGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.prebillGrid.ConfirmSelection = false;
            this.prebillGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.prebillGrid.Location = new System.Drawing.Point(0, 0);
            this.prebillGrid.MultiSelect = false;
            this.prebillGrid.Name = "prebillGrid";
            this.prebillGrid.ReadOnly = true;
            this.prebillGrid.RowHeadersVisible = false;
            this.prebillGrid.SelectionHandler = null;
            this.prebillGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.prebillGrid.ShowCellToolTips = false;
            this.prebillGrid.Size = new System.Drawing.Size(850, 101);
            this.prebillGrid.SortEnabled = false;
            this.prebillGrid.TabIndex = 22;
            this.prebillGrid.CellMouseDoubleClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.prebillGrid_CellMouseDoubleClick);
            // 
            // panelViewStatement
            // 
            this.panelViewStatement.Controls.Add(this.buttonViewStatements);
            this.panelViewStatement.Location = new System.Drawing.Point(0, 408);
            this.panelViewStatement.Name = "panelViewStatement";
            this.panelViewStatement.Size = new System.Drawing.Size(850, 32);
            this.panelViewStatement.TabIndex = 13;
            // 
            // buttonViewStatements
            // 
            this.buttonViewStatements.Location = new System.Drawing.Point(1, 4);
            this.buttonViewStatements.Name = "buttonViewStatements";
            this.buttonViewStatements.Size = new System.Drawing.Size(48, 23);
            this.buttonViewStatements.TabIndex = 11;
            this.buttonViewStatements.Text = "View";
            this.buttonViewStatements.UseVisualStyleBackColor = true;
            this.buttonViewStatements.Click += new System.EventHandler(this.buttonViewStatements_Click);
            // 
            // labelStatement
            // 
            this.labelStatement.AutoSize = true;
            this.labelStatement.Location = new System.Drawing.Point(2, 313);
            this.labelStatement.Name = "labelStatement";
            this.labelStatement.Size = new System.Drawing.Size(63, 13);
            this.labelStatement.TabIndex = 17;
            this.labelStatement.Text = "Statements:";
            // 
            // panelViewInvoice
            // 
            this.panelViewInvoice.Controls.Add(this.buttonViewInvoice);
            this.panelViewInvoice.Location = new System.Drawing.Point(0, 279);
            this.panelViewInvoice.Name = "panelViewInvoice";
            this.panelViewInvoice.Size = new System.Drawing.Size(850, 32);
            this.panelViewInvoice.TabIndex = 12;
            // 
            // buttonViewInvoice
            // 
            this.buttonViewInvoice.Location = new System.Drawing.Point(1, 4);
            this.buttonViewInvoice.Name = "buttonViewInvoice";
            this.buttonViewInvoice.Size = new System.Drawing.Size(48, 23);
            this.buttonViewInvoice.TabIndex = 11;
            this.buttonViewInvoice.Text = "View";
            this.buttonViewInvoice.UseVisualStyleBackColor = true;
            this.buttonViewInvoice.Click += new System.EventHandler(this.buttonViewInvoice_Click);
            // 
            // labelInvoice
            // 
            this.labelInvoice.AutoSize = true;
            this.labelInvoice.Location = new System.Drawing.Point(3, 164);
            this.labelInvoice.Name = "labelInvoice";
            this.labelInvoice.Size = new System.Drawing.Size(50, 13);
            this.labelInvoice.TabIndex = 15;
            this.labelInvoice.Text = "Invoices:";
            // 
            // labelpreBill
            // 
            this.labelpreBill.AutoSize = true;
            this.labelpreBill.Location = new System.Drawing.Point(3, 0);
            this.labelpreBill.Name = "labelpreBill";
            this.labelpreBill.Size = new System.Drawing.Size(42, 13);
            this.labelpreBill.TabIndex = 15;
            this.labelpreBill.Text = "Pre-Bill:";
            // 
            // panelViewPreBill
            // 
            this.panelViewPreBill.Controls.Add(this.buttonViewPrebill);
            this.panelViewPreBill.Location = new System.Drawing.Point(0, 129);
            this.panelViewPreBill.Name = "panelViewPreBill";
            this.panelViewPreBill.Size = new System.Drawing.Size(850, 32);
            this.panelViewPreBill.TabIndex = 3;
            // 
            // buttonViewPrebill
            // 
            this.buttonViewPrebill.Location = new System.Drawing.Point(1, 4);
            this.buttonViewPrebill.Name = "buttonViewPrebill";
            this.buttonViewPrebill.Size = new System.Drawing.Size(48, 23);
            this.buttonViewPrebill.TabIndex = 11;
            this.buttonViewPrebill.Text = "View";
            this.buttonViewPrebill.UseVisualStyleBackColor = true;
            this.buttonViewPrebill.Click += new System.EventHandler(this.buttonViewPrebill_Click);
            // 
            // panelStatement
            // 
            this.panelStatement.Controls.Add(this.statementGrid);
            this.panelStatement.Location = new System.Drawing.Point(1, 329);
            this.panelStatement.Name = "panelStatement";
            this.panelStatement.Size = new System.Drawing.Size(850, 77);
            this.panelStatement.TabIndex = 2;
            // 
            // statementGrid
            // 
            this.statementGrid.AllowUserToAddRows = false;
            this.statementGrid.AllowUserToDeleteRows = false;
            this.statementGrid.AllowUserToResizeRows = false;
            this.statementGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.statementGrid.BackgroundColor = System.Drawing.Color.White;
            this.statementGrid.ChangeValidator = null;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle2.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle2.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.statementGrid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle2;
            this.statementGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.statementGrid.ConfirmSelection = false;
            this.statementGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.statementGrid.Location = new System.Drawing.Point(0, 0);
            this.statementGrid.MultiSelect = false;
            this.statementGrid.Name = "statementGrid";
            this.statementGrid.ReadOnly = true;
            this.statementGrid.RowHeadersVisible = false;
            this.statementGrid.SelectionHandler = null;
            this.statementGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.statementGrid.ShowCellToolTips = false;
            this.statementGrid.Size = new System.Drawing.Size(850, 77);
            this.statementGrid.SortEnabled = false;
            this.statementGrid.TabIndex = 23;
            this.statementGrid.CellMouseDoubleClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.statementGrid_CellMouseDoubleClick);
            // 
            // panelInvoice
            // 
            this.panelInvoice.Controls.Add(this.invoiceGrid);
            this.panelInvoice.Location = new System.Drawing.Point(0, 180);
            this.panelInvoice.Name = "panelInvoice";
            this.panelInvoice.Size = new System.Drawing.Size(850, 98);
            this.panelInvoice.TabIndex = 1;
            // 
            // invoiceGrid
            // 
            this.invoiceGrid.AllowUserToAddRows = false;
            this.invoiceGrid.AllowUserToDeleteRows = false;
            this.invoiceGrid.AllowUserToResizeRows = false;
            this.invoiceGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.invoiceGrid.BackgroundColor = System.Drawing.Color.White;
            this.invoiceGrid.ChangeValidator = null;
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle3.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle3.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle3.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle3.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle3.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.invoiceGrid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle3;
            this.invoiceGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.invoiceGrid.ConfirmSelection = false;
            this.invoiceGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.invoiceGrid.Location = new System.Drawing.Point(0, 0);
            this.invoiceGrid.MultiSelect = false;
            this.invoiceGrid.Name = "invoiceGrid";
            this.invoiceGrid.ReadOnly = true;
            this.invoiceGrid.RowHeadersVisible = false;
            this.invoiceGrid.SelectionHandler = null;
            this.invoiceGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.invoiceGrid.ShowCellToolTips = false;
            this.invoiceGrid.Size = new System.Drawing.Size(850, 98);
            this.invoiceGrid.SortEnabled = false;
            this.invoiceGrid.TabIndex = 21;
            this.invoiceGrid.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.invoiceGrid_CellClick);
            this.invoiceGrid.CellMouseDoubleClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.invoiceGrid_CellMouseDoubleClick);
            // 
            // configureDaysServiceWse1
            // 
            this.configureDaysServiceWse1.Credentials = null;
            this.configureDaysServiceWse1.RequireMtom = false;
            this.configureDaysServiceWse1.Security = null;
            this.configureDaysServiceWse1.transactionId = null;
            this.configureDaysServiceWse1.Url = "http://127.0.0.1:8080/roi/services/ConfigureDaysService";
            this.configureDaysServiceWse1.UseDefaultCredentials = false;
            // 
            // AccountHistoryUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.AutoSize = true;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.panelViewStatement);
            this.Controls.Add(this.labelStatement);
            this.Controls.Add(this.panelViewInvoice);
            this.Controls.Add(this.labelInvoice);
            this.Controls.Add(this.labelpreBill);
            this.Controls.Add(this.panelViewPreBill);
            this.Controls.Add(this.panelStatement);
            this.Controls.Add(this.panelInvoice);
            this.Name = "AccountHistoryUI";
            this.Size = new System.Drawing.Size(854, 443);
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.prebillGrid)).EndInit();
            this.panelViewStatement.ResumeLayout(false);
            this.panelViewInvoice.ResumeLayout(false);
            this.panelViewPreBill.ResumeLayout(false);
            this.panelStatement.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.statementGrid)).EndInit();
            this.panelInvoice.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.invoiceGrid)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonViewPrebill;
        private System.Windows.Forms.Panel panelInvoice;
        private System.Windows.Forms.Panel panelStatement;
        private System.Windows.Forms.Panel panelViewPreBill;
        private System.Windows.Forms.Label labelpreBill;
        private System.Windows.Forms.Panel panel5;
        private System.Windows.Forms.Label labelInvoice;
        private System.Windows.Forms.Panel panelViewInvoice;
        private System.Windows.Forms.Button buttonViewInvoice;
        private System.Windows.Forms.Label labelStatement;
        private System.Windows.Forms.Panel panelViewStatement;
        private System.Windows.Forms.Button buttonViewStatements;
        private System.Windows.Forms.ToolTip toolTip;
        private Base.View.Common.EIGDataGrid statementGrid;
        private Base.View.Common.EIGDataGrid invoiceGrid;
        private Base.View.Common.EIGDataGrid prebillGrid;
        private System.Windows.Forms.Panel panel1;
        private WebReferences.ConfigureDaysWS.ConfigureDaysServiceWse configureDaysServiceWse1;






    }
}
