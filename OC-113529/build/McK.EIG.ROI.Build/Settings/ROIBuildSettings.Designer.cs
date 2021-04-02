namespace McK.EIG.ROI.Build.Settings
{
    partial class ROIBuildSettings
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.tabProp = new System.Windows.Forms.TabPage();
            this.panel1 = new System.Windows.Forms.Panel();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.label2 = new System.Windows.Forms.Label();
            this.gbDevProp = new System.Windows.Forms.GroupBox();
            this.dgvDevProp = new System.Windows.Forms.DataGridView();
            this.gbBaseProp = new System.Windows.Forms.GroupBox();
            this.dgvBaseProp = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.tabEnv = new System.Windows.Forms.TabPage();
            this.dgvEnvHome = new System.Windows.Forms.DataGridView();
            this.buildTabCtrl = new System.Windows.Forms.TabControl();
            this.btnQuit = new System.Windows.Forms.Button();
            this.btnRun = new System.Windows.Forms.Button();
            this.tabProp.SuspendLayout();
            this.panel1.SuspendLayout();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.gbDevProp.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvDevProp)).BeginInit();
            this.gbBaseProp.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvBaseProp)).BeginInit();
            this.tabEnv.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvEnvHome)).BeginInit();
            this.buildTabCtrl.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabProp
            // 
            this.tabProp.Controls.Add(this.panel1);
            this.tabProp.Location = new System.Drawing.Point(4, 22);
            this.tabProp.Name = "tabProp";
            this.tabProp.Padding = new System.Windows.Forms.Padding(3);
            this.tabProp.Size = new System.Drawing.Size(938, 610);
            this.tabProp.TabIndex = 4;
            this.tabProp.Text = "Prop";
            this.tabProp.UseVisualStyleBackColor = true;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.splitContainer1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(3, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(932, 604);
            this.panel1.TabIndex = 0;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.label2);
            this.splitContainer1.Panel1.Controls.Add(this.gbDevProp);
            this.splitContainer1.Panel1.Controls.Add(this.gbBaseProp);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.label1);
            this.splitContainer1.Size = new System.Drawing.Size(932, 604);
            this.splitContainer1.SplitterDistance = 556;
            this.splitContainer1.TabIndex = 0;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Tahoma", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(244, 296);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(446, 16);
            this.label2.TabIndex = 5;
            this.label2.Text = "Double click the base.properties row to add into the dev.properites";
            this.label2.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // gbDevProp
            // 
            this.gbDevProp.Controls.Add(this.dgvDevProp);
            this.gbDevProp.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.gbDevProp.Location = new System.Drawing.Point(0, 320);
            this.gbDevProp.Name = "gbDevProp";
            this.gbDevProp.Size = new System.Drawing.Size(932, 236);
            this.gbDevProp.TabIndex = 4;
            this.gbDevProp.TabStop = false;
            this.gbDevProp.Text = "DevProperties";
            // 
            // dgvDevProp
            // 
            this.dgvDevProp.AllowUserToAddRows = false;
            this.dgvDevProp.AllowUserToResizeColumns = false;
            this.dgvDevProp.AllowUserToResizeRows = false;
            this.dgvDevProp.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvDevProp.Location = new System.Drawing.Point(3, 18);
            this.dgvDevProp.MultiSelect = false;
            this.dgvDevProp.Name = "dgvDevProp";
            this.dgvDevProp.RowHeadersVisible = false;
            this.dgvDevProp.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvDevProp.Size = new System.Drawing.Size(926, 215);
            this.dgvDevProp.TabIndex = 1;
            this.dgvDevProp.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvDevProp_CellDoubleClick);
            this.dgvDevProp.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.dgvDevProp_RowsAdded);
            // 
            // gbBaseProp
            // 
            this.gbBaseProp.Controls.Add(this.dgvBaseProp);
            this.gbBaseProp.Dock = System.Windows.Forms.DockStyle.Top;
            this.gbBaseProp.Location = new System.Drawing.Point(0, 0);
            this.gbBaseProp.Name = "gbBaseProp";
            this.gbBaseProp.Size = new System.Drawing.Size(932, 283);
            this.gbBaseProp.TabIndex = 1;
            this.gbBaseProp.TabStop = false;
            this.gbBaseProp.Text = "BaseProperties";
            // 
            // dgvBaseProp
            // 
            this.dgvBaseProp.AllowUserToAddRows = false;
            this.dgvBaseProp.AllowUserToResizeColumns = false;
            this.dgvBaseProp.AllowUserToResizeRows = false;
            this.dgvBaseProp.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvBaseProp.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.dgvBaseProp.Location = new System.Drawing.Point(3, 17);
            this.dgvBaseProp.MultiSelect = false;
            this.dgvBaseProp.Name = "dgvBaseProp";
            this.dgvBaseProp.RowHeadersVisible = false;
            this.dgvBaseProp.Size = new System.Drawing.Size(926, 258);
            this.dgvBaseProp.TabIndex = 1;
            this.dgvBaseProp.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvBaseProp_CellDoubleClick);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Tahoma", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(195, 14);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(560, 16);
            this.label1.TabIndex = 29;
            this.label1.Text = "Double click the dev.properties variable to delete the properties from dev.proper" +
                "ites";
            this.label1.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // tabEnv
            // 
            this.tabEnv.Controls.Add(this.dgvEnvHome);
            this.tabEnv.Location = new System.Drawing.Point(4, 22);
            this.tabEnv.Name = "tabEnv";
            this.tabEnv.Padding = new System.Windows.Forms.Padding(3);
            this.tabEnv.Size = new System.Drawing.Size(938, 610);
            this.tabEnv.TabIndex = 2;
            this.tabEnv.Text = "Env";
            this.tabEnv.UseVisualStyleBackColor = true;
            // 
            // dgvEnvHome
            // 
            this.dgvEnvHome.AllowUserToAddRows = false;
            this.dgvEnvHome.AllowUserToResizeColumns = false;
            this.dgvEnvHome.AllowUserToResizeRows = false;
            this.dgvEnvHome.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvEnvHome.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvEnvHome.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnKeystroke;
            this.dgvEnvHome.Location = new System.Drawing.Point(3, 3);
            this.dgvEnvHome.MultiSelect = false;
            this.dgvEnvHome.Name = "dgvEnvHome";
            this.dgvEnvHome.RowHeadersVisible = false;
            this.dgvEnvHome.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvEnvHome.Size = new System.Drawing.Size(932, 604);
            this.dgvEnvHome.TabIndex = 2;
            this.dgvEnvHome.CellEndEdit += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvEnvHome_CellEndEdit);
            // 
            // buildTabCtrl
            // 
            this.buildTabCtrl.Controls.Add(this.tabProp);
            this.buildTabCtrl.Controls.Add(this.tabEnv);
            this.buildTabCtrl.Font = new System.Drawing.Font("Verdana", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buildTabCtrl.Location = new System.Drawing.Point(8, 0);
            this.buildTabCtrl.Name = "buildTabCtrl";
            this.buildTabCtrl.SelectedIndex = 0;
            this.buildTabCtrl.Size = new System.Drawing.Size(946, 636);
            this.buildTabCtrl.TabIndex = 1;
            // 
            // btnQuit
            // 
            this.btnQuit.Location = new System.Drawing.Point(478, 642);
            this.btnQuit.Name = "btnQuit";
            this.btnQuit.Size = new System.Drawing.Size(87, 25);
            this.btnQuit.TabIndex = 30;
            this.btnQuit.Text = "Quit";
            this.btnQuit.UseVisualStyleBackColor = true;
            this.btnQuit.Click += new System.EventHandler(this.btnQuit_Click);
            // 
            // btnRun
            // 
            this.btnRun.Location = new System.Drawing.Point(356, 642);
            this.btnRun.Name = "btnRun";
            this.btnRun.Size = new System.Drawing.Size(87, 25);
            this.btnRun.TabIndex = 29;
            this.btnRun.Text = "Run";
            this.btnRun.UseVisualStyleBackColor = true;
            this.btnRun.Click += new System.EventHandler(this.btnRun_Click);
            // 
            // ROIBuildSettings
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(958, 675);
            this.Controls.Add(this.btnQuit);
            this.Controls.Add(this.btnRun);
            this.Controls.Add(this.buildTabCtrl);
            this.Font = new System.Drawing.Font("Verdana", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.Name = "ROIBuildSettings";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "ROI Developer Build";
            this.tabProp.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.Panel2.PerformLayout();
            this.splitContainer1.ResumeLayout(false);
            this.gbDevProp.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvDevProp)).EndInit();
            this.gbBaseProp.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvBaseProp)).EndInit();
            this.tabEnv.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvEnvHome)).EndInit();
            this.buildTabCtrl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tabProp;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.TabPage tabEnv;
        private System.Windows.Forms.DataGridView dgvEnvHome;
        private System.Windows.Forms.TabControl buildTabCtrl;
        private System.Windows.Forms.GroupBox gbBaseProp;
        private System.Windows.Forms.DataGridView dgvBaseProp;
        private System.Windows.Forms.GroupBox gbDevProp;
        private System.Windows.Forms.DataGridView dgvDevProp;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnQuit;
        private System.Windows.Forms.Button btnRun;
        private System.Windows.Forms.Label label2;

    }
}

