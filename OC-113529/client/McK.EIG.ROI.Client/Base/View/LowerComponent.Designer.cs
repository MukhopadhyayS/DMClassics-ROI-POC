namespace McK.EIG.ROI.Client.Base.View
{
    partial class LowerComponent
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
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.odpPanel = new System.Windows.Forms.Panel();
            this.bottomPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // bottomPanel
            // 
            this.bottomPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.bottomPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(145)))), ((int)(((byte)(167)))), ((int)(((byte)(180)))));
            this.bottomPanel.Controls.Add(this.footerPanel);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.bottomPanel.Location = new System.Drawing.Point(0, 173);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Padding = new System.Windows.Forms.Padding(0, 1, 0, 0);
            this.bottomPanel.Size = new System.Drawing.Size(560, 50);
            this.bottomPanel.TabIndex = 1;
            // 
            // footerPanel
            // 
            this.footerPanel.BackColor = System.Drawing.SystemColors.Control;
            this.footerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.footerPanel.Location = new System.Drawing.Point(0, 1);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(560, 49);
            this.footerPanel.TabIndex = 0;
            // 
            // odpPanel
            // 
            this.odpPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.odpPanel.BackColor = System.Drawing.SystemColors.Control;
            this.odpPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.odpPanel.Location = new System.Drawing.Point(0, 0);
            this.odpPanel.Name = "odpPanel";
            this.odpPanel.Size = new System.Drawing.Size(560, 173);
            this.odpPanel.TabIndex = 0;
            // 
            // LowerComponent
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.BackColor = System.Drawing.Color.Red;
            this.Controls.Add(this.odpPanel);
            this.Controls.Add(this.bottomPanel);
            this.DoubleBuffered = true;
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "LowerComponent";
            this.Size = new System.Drawing.Size(560, 223);
            this.bottomPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.Panel odpPanel;
        private System.Windows.Forms.Panel footerPanel;


    }
}
