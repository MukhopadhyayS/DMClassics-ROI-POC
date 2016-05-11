namespace McK.EIG.ROI.Client.Patient.View.FindPatient
{
    partial class FindPatientActionUI
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
            this.patientViewEditButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // patientViewEditButton
            // 
            this.patientViewEditButton.AutoSize = true;
            this.patientViewEditButton.BackColor = System.Drawing.Color.White;
            this.patientViewEditButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientViewEditButton.Location = new System.Drawing.Point(341, 8);
            this.patientViewEditButton.Name = "patientViewEditButton";
            this.patientViewEditButton.Size = new System.Drawing.Size(126, 24);
            this.patientViewEditButton.TabIndex = 0;
            this.patientViewEditButton.UseVisualStyleBackColor = false;
            // 
            // FindPatientActionUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.patientViewEditButton);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "FindPatientActionUI";
            this.Size = new System.Drawing.Size(530, 40);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button patientViewEditButton;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
