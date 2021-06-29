namespace McK.EIG.ROI.Client.Base.View
{
    partial class GotoPage
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
            this.label1 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.GotoPagebtn = new System.Windows.Forms.Button();
            this.cnclbtn = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(22, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(116, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Enter the Page number";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(160, 18);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(100, 20);
            this.textBox1.TabIndex = 1;
            // 
            // GotoPagebtn
            // 
            this.GotoPagebtn.Location = new System.Drawing.Point(38, 72);
            this.GotoPagebtn.Name = "GotoPagebtn";
            this.GotoPagebtn.Size = new System.Drawing.Size(75, 23);
            this.GotoPagebtn.TabIndex = 2;
            this.GotoPagebtn.Text = "OK";
            this.GotoPagebtn.UseVisualStyleBackColor = true;
            this.GotoPagebtn.Click += new System.EventHandler(this.GotoPagebtn_Click);
            // 
            // cnclbtn
            // 
            this.cnclbtn.Location = new System.Drawing.Point(160, 72);
            this.cnclbtn.Name = "cnclbtn";
            this.cnclbtn.Size = new System.Drawing.Size(75, 23);
            this.cnclbtn.TabIndex = 3;
            this.cnclbtn.Text = "Cancel";
            this.cnclbtn.UseVisualStyleBackColor = true;
            this.cnclbtn.Click += new System.EventHandler(this.cnclbtn_Click);
            // 
            // GotoPage
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(299, 126);
            this.Controls.Add(this.cnclbtn);
            this.Controls.Add(this.GotoPagebtn);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.label1);
            this.Name = "GotoPage";
            this.Text = "GotoPage";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Button GotoPagebtn;
        private System.Windows.Forms.Button cnclbtn;
    }
}