namespace McK.EIG.ROI.Client.Base.View
{
    partial class HeaderUI
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.titleLabel = new System.Windows.Forms.Label();
            this.panel2 = new System.Windows.Forms.Panel();
            this.infomationLabel = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.titleLabel);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Padding = new System.Windows.Forms.Padding(0, 0, 0, 12);
            this.panel1.Size = new System.Drawing.Size(722, 20);
            this.panel1.TabIndex = 2;
            // 
            // titleLabel
            // 
            this.titleLabel.BackColor = System.Drawing.Color.White;
            this.titleLabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.titleLabel.Font = new System.Drawing.Font("Arial", 11.5F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.titleLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(27)))), ((int)(((byte)(81)))), ((int)(((byte)(156)))));
            this.titleLabel.Location = new System.Drawing.Point(0, 0);
            this.titleLabel.Name = "titleLabel";
            this.titleLabel.Size = new System.Drawing.Size(722, 20);
            this.titleLabel.TabIndex = 1;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.infomationLabel);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(0, 20);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(722, 84);
            this.panel2.TabIndex = 3;
            // 
            // infomationLabel
            // 
            this.infomationLabel.BackColor = System.Drawing.Color.White;
            this.infomationLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.infomationLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.infomationLabel.Location = new System.Drawing.Point(0, 0);
            this.infomationLabel.Name = "infomationLabel";
            this.infomationLabel.Size = new System.Drawing.Size(722, 84);
            this.infomationLabel.TabIndex = 3;
            // 
            // HeaderUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.BackColor = System.Drawing.Color.Silver;
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.DoubleBuffered = true;
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "HeaderUI";
            this.Size = new System.Drawing.Size(722, 104);
            this.panel1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label titleLabel;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Label infomationLabel;
    }
}
