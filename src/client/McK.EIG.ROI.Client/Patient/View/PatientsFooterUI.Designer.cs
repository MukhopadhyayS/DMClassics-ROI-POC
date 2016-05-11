namespace McK.EIG.ROI.Client.Patient.View
{
    partial class PatientsFooterUI
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

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.footerPanel = new System.Windows.Forms.TableLayoutPanel();
            this.rightPanel = new System.Windows.Forms.TableLayoutPanel();
            this.patientRequestButton = new System.Windows.Forms.Button();
            this.createRequestButton = new System.Windows.Forms.Button();
            this.leftPanel = new System.Windows.Forms.Panel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.footerPanel.SuspendLayout();
            this.rightPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // footerPanel
            // 
            this.footerPanel.AutoScroll = true;
            this.footerPanel.AutoSize = true;
            this.footerPanel.ColumnCount = 2;
            this.footerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 66.30137F));
            this.footerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 33.69863F));
            this.footerPanel.Controls.Add(this.rightPanel, 1, 0);
            this.footerPanel.Controls.Add(this.leftPanel, 0, 0);
            this.footerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.footerPanel.Location = new System.Drawing.Point(0, 0);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.RowCount = 1;
            this.footerPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.footerPanel.Size = new System.Drawing.Size(750, 43);
            this.footerPanel.TabIndex = 2;
            this.footerPanel.TabStop = true;
            // 
            // rightPanel
            // 
            this.rightPanel.BackColor = System.Drawing.Color.Transparent;
            this.rightPanel.ColumnCount = 2;
            this.rightPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 53.11203F));
            this.rightPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 46.88797F));
            this.rightPanel.Controls.Add(this.patientRequestButton, 0, 0);
            this.rightPanel.Controls.Add(this.createRequestButton, 1, 0);
            this.rightPanel.Dock = System.Windows.Forms.DockStyle.Right;
            this.rightPanel.Location = new System.Drawing.Point(500, 3);
            this.rightPanel.Name = "rightPanel";
            this.rightPanel.RowCount = 1;
            this.rightPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.rightPanel.Size = new System.Drawing.Size(247, 37);
            this.rightPanel.TabIndex = 6;
            this.rightPanel.TabStop = true;
            // 
            // patientRequestButton
            // 
            this.patientRequestButton.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.patientRequestButton.AutoSize = true;
            this.patientRequestButton.BackColor = System.Drawing.Color.White;
            this.patientRequestButton.Enabled = false;
            this.patientRequestButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientRequestButton.Location = new System.Drawing.Point(3, 6);
            this.patientRequestButton.Name = "patientRequestButton";
            this.patientRequestButton.Size = new System.Drawing.Size(125, 25);
            this.patientRequestButton.TabIndex = 1;
            this.patientRequestButton.UseVisualStyleBackColor = false;
            this.patientRequestButton.Click += new System.EventHandler(this.patientReqButton_Click);
            // 
            // createRequestButton
            // 
            this.createRequestButton.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.createRequestButton.AutoSize = true;
            this.createRequestButton.BackColor = System.Drawing.Color.White;
            this.createRequestButton.Enabled = false;
            this.createRequestButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.createRequestButton.Location = new System.Drawing.Point(134, 6);
            this.createRequestButton.Name = "createRequestButton";
            this.createRequestButton.Size = new System.Drawing.Size(110, 25);
            this.createRequestButton.TabIndex = 2;
            this.createRequestButton.UseVisualStyleBackColor = false;
            this.createRequestButton.Click += new System.EventHandler(this.createRequestButton_Click);
            // 
            // leftPanel
            // 
            this.leftPanel.AutoSize = true;
            this.leftPanel.BackColor = System.Drawing.Color.Transparent;
            this.leftPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.leftPanel.Location = new System.Drawing.Point(3, 3);
            this.leftPanel.Name = "leftPanel";
            this.leftPanel.Size = new System.Drawing.Size(491, 37);
            this.leftPanel.TabIndex = 3;
            this.leftPanel.TabStop = true;
            // 
            // PatientsFooterUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.AutoScrollMinSize = new System.Drawing.Size(750, 30);
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.Controls.Add(this.footerPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "PatientsFooterUI";
            this.Size = new System.Drawing.Size(750, 43);
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            this.rightPanel.ResumeLayout(false);
            this.rightPanel.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel footerPanel;
        private System.Windows.Forms.Panel leftPanel;
        private System.Windows.Forms.TableLayoutPanel rightPanel;
        private System.Windows.Forms.Button patientRequestButton;
        private System.Windows.Forms.Button createRequestButton;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
