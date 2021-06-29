namespace McK.EIG.ROI.Client.Base.View.Common
{
    partial class NumericTextBoxClass
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
            this.numberTextbox = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // numberTextbox
            // 
            this.numberTextbox.Location = new System.Drawing.Point(9, 0);
            this.numberTextbox.MaxLength = 3;
            this.numberTextbox.Name = "numberTextbox";
            this.numberTextbox.Size = new System.Drawing.Size(40, 20);
            this.numberTextbox.TabIndex = 0;
            this.numberTextbox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.numberTextbox_KeyPress);
            this.numberTextbox.Enter += new System.EventHandler(this.numberTextbox_Enter);
            // 
            // NumericTextBoxClass
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.numberTextbox);
            this.Margin = new System.Windows.Forms.Padding(1);
            this.Name = "NumericTextBoxClass";
            this.Size = new System.Drawing.Size(42, 22);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox numberTextbox;
    }
}
