namespace McK.EIG.ROI.Client.Requestors.View
{
    partial class RequestorFooterUI
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
            this.leftPanel = new System.Windows.Forms.Panel();
            this.rightPanel = new System.Windows.Forms.Panel();
            this.createRequestButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.footerPanel.SuspendLayout();
            this.rightPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // footerPanel
            // 
            this.footerPanel.AutoScroll = true;
            this.footerPanel.AutoScrollMinSize = new System.Drawing.Size(700, 0);
            this.footerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.footerPanel.ColumnCount = 2;
            this.footerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 79.29374F));
            this.footerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 20.70626F));
            this.footerPanel.Controls.Add(this.leftPanel, 0, 0);
            this.footerPanel.Controls.Add(this.rightPanel, 1, 0);
            this.footerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.footerPanel.Location = new System.Drawing.Point(0, 0);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.RowCount = 1;
            this.footerPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.footerPanel.Size = new System.Drawing.Size(623, 42);
            this.footerPanel.TabIndex = 0;
            // 
            // leftPanel
            // 
            this.leftPanel.BackColor = System.Drawing.Color.Transparent;
            this.leftPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.leftPanel.Location = new System.Drawing.Point(3, 3);
            this.leftPanel.Name = "leftPanel";
            this.leftPanel.Size = new System.Drawing.Size(549, 36);
            this.leftPanel.TabIndex = 0;
            // 
            // rightPanel
            // 
            this.rightPanel.BackColor = System.Drawing.Color.Transparent;
            this.rightPanel.Controls.Add(this.createRequestButton);
            this.rightPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rightPanel.Location = new System.Drawing.Point(558, 3);
            this.rightPanel.Name = "rightPanel";
            this.rightPanel.Size = new System.Drawing.Size(139, 36);
            this.rightPanel.TabIndex = 1;
            // 
            // createRequestButton
            // 
            this.createRequestButton.AutoSize = true;
            this.createRequestButton.BackColor = System.Drawing.Color.White;
            this.createRequestButton.Enabled = false;
            this.createRequestButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.createRequestButton.Location = new System.Drawing.Point(11, 6);
            this.createRequestButton.Name = "createRequestButton";
            this.createRequestButton.Size = new System.Drawing.Size(104, 21);
            this.createRequestButton.TabIndex = 0;
            this.createRequestButton.UseVisualStyleBackColor = false;
            this.createRequestButton.Click += new System.EventHandler(this.createRequestButton_Click);
            // 
            // RequestorFooterUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.Controls.Add(this.footerPanel);
            this.Name = "RequestorFooterUI";
            this.Size = new System.Drawing.Size(623, 42);
            this.footerPanel.ResumeLayout(false);
            this.rightPanel.ResumeLayout(false);
            this.rightPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel footerPanel;
        private System.Windows.Forms.Panel leftPanel;
        private System.Windows.Forms.Panel rightPanel;
        internal System.Windows.Forms.Button createRequestButton;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
