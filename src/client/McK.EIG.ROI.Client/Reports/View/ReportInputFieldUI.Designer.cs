using System;
namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ReportInputFieldUI
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
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.field_Value = new System.Windows.Forms.TextBox();
            this.field_Label = new System.Windows.Forms.Label();
            this.reportInputFieldPanel = new System.Windows.Forms.Panel();
            this.tableLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.reportInputFieldPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.ColumnCount = 1;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.Controls.Add(this.reportInputFieldPanel, 0, 1);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 2;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(280, 50);
            this.tableLayoutPanel.TabIndex = 9;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkRate = 0;
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // field_Value
            // 
            this.field_Value.Location = new System.Drawing.Point(3, 20);
            this.field_Value.Name = "field_Value";
            this.field_Value.Size = new System.Drawing.Size(250, 30);
            this.field_Value.TabIndex = 8;
            this.field_Value.TextChanged += new System.EventHandler(this.inputField_TextChanged);
            // 
            // field_Label
            // 
            this.field_Label.AutoSize = true;
            this.field_Label.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.field_Label.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(77)))), ((int)(((byte)(111)))), ((int)(((byte)(161)))));
            this.field_Label.Location = new System.Drawing.Point(1, 1);
            this.field_Label.Name = "field_Label";
            this.field_Label.Size = new System.Drawing.Size(69, 20);
            this.field_Label.TabIndex = 9;

            // 
            // reportInputFieldPanel
            // 
            this.reportInputFieldPanel.Controls.Add(this.field_Label);
            this.reportInputFieldPanel.Controls.Add(this.field_Value);
            this.reportInputFieldPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reportInputFieldPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportInputFieldPanel.Location = new System.Drawing.Point(3, 3);
            this.reportInputFieldPanel.Name = "reportInputFieldPanel";
            this.reportInputFieldPanel.Size = new System.Drawing.Size(274, 44);
            this.reportInputFieldPanel.TabIndex = 0;
            // 
            // ReportInputFieldUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.Controls.Add(this.tableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ReportInputFieldUI";
            this.Size = new System.Drawing.Size(280, 50);
            this.tableLayoutPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.reportInputFieldPanel.ResumeLayout(false);
            this.reportInputFieldPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.Panel reportInputFieldPanel;
        private System.Windows.Forms.Label field_Label;
        private System.Windows.Forms.TextBox field_Value;
    }
}
