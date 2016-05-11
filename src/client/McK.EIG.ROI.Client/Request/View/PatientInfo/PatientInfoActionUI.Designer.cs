namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    partial class PatientInfoActionUI
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
            this.infoActionPanel = new System.Windows.Forms.TableLayoutPanel();
            this.leftPanel = new System.Windows.Forms.Panel();
            this.requestLockedImg = new System.Windows.Forms.PictureBox();
            this.rightPanel = new System.Windows.Forms.Panel();
            this.viewAuthRequestButton = new System.Windows.Forms.Button();
            this.revertButton = new System.Windows.Forms.Button();
            this.saveBillButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.infoActionPanel.SuspendLayout();
            this.leftPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requestLockedImg)).BeginInit();
            this.rightPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // infoActionPanel
            // 
            this.infoActionPanel.AutoScroll = true;
            this.infoActionPanel.AutoScrollMinSize = new System.Drawing.Size(700, 0);
            this.infoActionPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.infoActionPanel.ColumnCount = 2;
            this.infoActionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 31.72758F));
            this.infoActionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 68.27242F));
            this.infoActionPanel.Controls.Add(this.leftPanel, 0, 0);
            this.infoActionPanel.Controls.Add(this.rightPanel, 1, 0);
            this.infoActionPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.infoActionPanel.Location = new System.Drawing.Point(0, 0);
            this.infoActionPanel.Name = "infoActionPanel";
            this.infoActionPanel.RowCount = 1;
            this.infoActionPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.infoActionPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.infoActionPanel.Size = new System.Drawing.Size(702, 46);
            this.infoActionPanel.TabIndex = 1;
            // 
            // leftPanel
            // 
            this.leftPanel.Controls.Add(this.requestLockedImg);
            this.leftPanel.Location = new System.Drawing.Point(3, 3);
            this.leftPanel.Name = "leftPanel";
            this.leftPanel.Size = new System.Drawing.Size(145, 39);
            this.leftPanel.TabIndex = 0;
            // 
            // requestLockedImg
            // 
            this.requestLockedImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requestLockedImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requestLockedImg.Image = global::McK.EIG.ROI.Client.Resources.images.in_use_icon;
            this.requestLockedImg.Location = new System.Drawing.Point(8, 11);
            this.requestLockedImg.Name = "requestLockedImg";
            this.requestLockedImg.Size = new System.Drawing.Size(17, 18);
            this.requestLockedImg.TabIndex = 4;
            this.requestLockedImg.TabStop = false;
            this.requestLockedImg.Visible = false;
            // 
            // rightPanel
            // 
            this.rightPanel.Controls.Add(this.viewAuthRequestButton);
            this.rightPanel.Controls.Add(this.revertButton);
            this.rightPanel.Controls.Add(this.saveBillButton);
            this.rightPanel.Controls.Add(this.saveButton);
            this.rightPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rightPanel.Location = new System.Drawing.Point(225, 3);
            this.rightPanel.Name = "rightPanel";
            this.rightPanel.Size = new System.Drawing.Size(474, 40);
            this.rightPanel.TabIndex = 1;
            // 
            // viewAuthRequestButton
            // 
            this.viewAuthRequestButton.Enabled = false;
            this.viewAuthRequestButton.Location = new System.Drawing.Point(3, 2);
            this.viewAuthRequestButton.Name = "viewAuthRequestButton";
            this.viewAuthRequestButton.Size = new System.Drawing.Size(170, 27);
            this.viewAuthRequestButton.TabIndex = 0;
            this.viewAuthRequestButton.UseVisualStyleBackColor = true;
            // 
            // revertButton
            // 
            this.revertButton.Enabled = false;
            this.revertButton.Location = new System.Drawing.Point(359, 2);
            this.revertButton.Name = "revertButton";
            this.revertButton.Size = new System.Drawing.Size(84, 27);
            this.revertButton.TabIndex = 3;
            this.revertButton.UseVisualStyleBackColor = true;
            // 
            // saveBillButton
            // 
            this.saveBillButton.Enabled = false;
            this.saveBillButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveBillButton.Location = new System.Drawing.Point(269, 2);
            this.saveBillButton.Name = "saveBillButton";
            this.saveBillButton.Size = new System.Drawing.Size(84, 27);
            this.saveBillButton.TabIndex = 2;
            this.saveBillButton.UseVisualStyleBackColor = true;
            // 
            // saveButton
            // 
            this.saveButton.Enabled = false;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(179, 2);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(84, 27);
            this.saveButton.TabIndex = 1;
            this.saveButton.UseVisualStyleBackColor = true;
            // 
            // PatientInfoActionUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.infoActionPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "PatientInfoActionUI";
            this.Size = new System.Drawing.Size(702, 46);
            this.infoActionPanel.ResumeLayout(false);
            this.leftPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.requestLockedImg)).EndInit();
            this.rightPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel infoActionPanel;
        private System.Windows.Forms.Panel leftPanel;
        private System.Windows.Forms.Panel rightPanel;
        private System.Windows.Forms.Button revertButton;
        private System.Windows.Forms.Button saveBillButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.PictureBox requestLockedImg;
        private System.Windows.Forms.Button viewAuthRequestButton;

    }
}
