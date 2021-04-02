using System;
using System.ComponentModel;
using System.Collections.ObjectModel;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;
using System.Globalization;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Web_References.BillingCoreWS;




namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    public partial class UnappliedAdjustmentUI : ROIBaseUI
    {

        private InvoiceOrPrebillPreviewInfo invoiceinfo;
        private double appliedAmount;
        private double totalAmount;
       
        public UnappliedAdjustmentUI()
        {
            InitializeComponent();
            //Localize();
        }

         /// <param name="cancelHandler"></param>
        public UnappliedAdjustmentUI(IPane pane)
            : this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
        }

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, textlabel1);
            SetLabel(rm, UnapplyAdjlabel);
            SetLabel(rm, Unapplypaylabel);
            SetLabel(rm, AmtApplylabel);
            SetLabel(rm, Cancelbutton);
            SetLabel(rm, adjTobeAppliedLabel);
            SetLabel(rm, payToBeAppliedLabel);
            SetLabel(rm, SaveandContinuebutton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip1, Cancelbutton);
            SetTooltip(rm, toolTip1, SaveandContinuebutton);
        }

      

        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        
        public void Setdata(double unApldPay, double unAplAdj)
        {

            double appliedAmt;
            double balance;
            double adjAmt;
            double payAmt;
            balance=invoiceinfo.InvoiceBalanceDue;
            totalAmount = unAplAdj + unApldPay;
            adjAmt = unAplAdj;
            payAmt = unApldPay;
            if (balance != 0)
            {
                if (totalAmount == balance)
                    appliedAmt = balance;
                else if (totalAmount < balance)
                {
                    //lblUnAppAdjAmt.Text = adjAmt.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    //lblUnAppAmt.Text = payAmt.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    //lblAppAmt.Text = totalAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    //totalAmount = balance;
                }
                else if (totalAmount > balance)
                {
                    if (balance <= unAplAdj)
                    {
                        adjAmt = balance;
                        balance = 0;
                        payAmt = 0;
                    }
                    else
                    {
                        adjAmt = unAplAdj;
                        balance = balance - unAplAdj;
                        payAmt = balance;
                    }
                    totalAmount = adjAmt + payAmt;
                }
            }
            else
            {
                adjAmt = 0;
                payAmt = 0;
                totalAmount = 0;
            }
            lblUnAppAdj.Text = unAplAdj.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            lblUnAppPay.Text = unApldPay.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            lblAppAmt.Text = totalAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            adjTobeAppliedValueLabel.Text = adjAmt.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            payToBeAppliedValueLabel.Text = payAmt.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
        }

        public InvoiceOrPrebillPreviewInfo Invoiceinfo
        {
            get { return invoiceinfo; }
            set { invoiceinfo = value; }
        }

        public double AppliedAmount
        {
            get { return appliedAmount; }
            set { appliedAmount = value; }
        }

        private void SaveandContinuebutton_Click(object sender, EventArgs e)        
        {
            if (radioButtonYes.Checked)
            {
                ((Form)(this.Parent)).DialogResult = DialogResult.OK;
            }
            else
                if (radioButtonNo.Checked)
                {
                    appliedAmount = 0;
                    ((Form)(this.Parent)).DialogResult = DialogResult.Ignore;
                }

        }

         private void radioButtonNo_CheckedChanged(object sender, EventArgs e)
         {
             if (radioButtonNo.Checked == true)
             {
                 appliedAmount = 0; 
                 //((Form)(this.Parent)).DialogResult = DialogResult.Ignore;
             }
         }

         

    }
}
