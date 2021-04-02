namespace McK.EIG.ROI.Client.Admin.View
{
    partial class AdminBaseObjectDetailsUI
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
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.odpOuterPanel = new System.Windows.Forms.Panel();
            this.innerPanel = new System.Windows.Forms.Panel();
            this.headerPanel = new System.Windows.Forms.Panel();
            this.titleLabel = new System.Windows.Forms.Label();
            this.controlsPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.requiredLabel = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.odpOuterPanel.SuspendLayout();
            this.headerPanel.SuspendLayout();
            this.controlsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            this.SuspendLayout();
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkRate = 0;
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // odpOuterPanel
            // 
            this.odpOuterPanel.AutoSize = true;
            this.odpOuterPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.odpOuterPanel.BackColor = System.Drawing.Color.White;
            this.odpOuterPanel.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.odpOuterPanel.Controls.Add(this.innerPanel);
            this.odpOuterPanel.Controls.Add(this.headerPanel);
            this.odpOuterPanel.Controls.Add(this.controlsPanel);
            this.odpOuterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.odpOuterPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.odpOuterPanel.Location = new System.Drawing.Point(0, 0);
            this.odpOuterPanel.Margin = new System.Windows.Forms.Padding(0);
            this.odpOuterPanel.Name = "odpOuterPanel";
            this.odpOuterPanel.Padding = new System.Windows.Forms.Padding(3, 5, 5, 5);
            this.odpOuterPanel.Size = new System.Drawing.Size(690, 201);
            this.odpOuterPanel.TabIndex = 2;
            // 
            // innerPanel
            // 
            this.innerPanel.BackColor = System.Drawing.Color.White;
            this.innerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.innerPanel.Location = new System.Drawing.Point(3, 25);
            this.innerPanel.Name = "innerPanel";
            this.innerPanel.Size = new System.Drawing.Size(682, 132);
            this.innerPanel.TabIndex = 4;
            // 
            // headerPanel
            // 
            this.headerPanel.Controls.Add(this.titleLabel);
            this.headerPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.headerPanel.Location = new System.Drawing.Point(3, 5);
            this.headerPanel.Name = "headerPanel";
            this.headerPanel.Size = new System.Drawing.Size(682, 20);
            this.headerPanel.TabIndex = 16;
            // 
            // titleLabel
            // 
            this.titleLabel.AutoSize = true;
            this.titleLabel.Font = new System.Drawing.Font("Arial", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.titleLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(67)))), ((int)(((byte)(100)))), ((int)(((byte)(145)))));
            this.titleLabel.Location = new System.Drawing.Point(5, 3);
            this.titleLabel.Name = "titleLabel";
            this.titleLabel.Size = new System.Drawing.Size(0, 16);
            this.titleLabel.TabIndex = 1;
            // 
            // controlsPanel
            // 
            this.controlsPanel.Controls.Add(this.cancelButton);
            this.controlsPanel.Controls.Add(this.saveButton);
            this.controlsPanel.Controls.Add(this.requiredImage);
            this.controlsPanel.Controls.Add(this.requiredLabel);
            this.controlsPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.controlsPanel.Location = new System.Drawing.Point(3, 157);
            this.controlsPanel.Name = "controlsPanel";
            this.controlsPanel.Size = new System.Drawing.Size(682, 39);
            this.controlsPanel.TabIndex = 15;
            // 
            // cancelButton
            // 
            this.cancelButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(343, 9);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(78, 23);
            this.cancelButton.TabIndex = 6;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // saveButton
            // 
            this.saveButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(261, 9);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(78, 23);
            this.saveButton.TabIndex = 5;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // requiredImage
            // 
            this.requiredImage.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImage.Location = new System.Drawing.Point(5, 11);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(9, 10);
            this.requiredImage.TabIndex = 16;
            this.requiredImage.TabStop = false;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(18, 9);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 15;
            // 
            // AdminBaseObjectDetailsUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.Controls.Add(this.odpOuterPanel);
            this.DoubleBuffered = true;
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "AdminBaseObjectDetailsUI";
            this.Size = new System.Drawing.Size(690, 201);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.odpOuterPanel.ResumeLayout(false);
            this.headerPanel.ResumeLayout(false);
            this.headerPanel.PerformLayout();
            this.controlsPanel.ResumeLayout(false);
            this.controlsPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel odpOuterPanel;
        private System.Windows.Forms.Panel controlsPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.Panel innerPanel;
        private System.Windows.Forms.Panel headerPanel;
        private System.Windows.Forms.Label titleLabel;
    }
}
