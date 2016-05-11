using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using McK.EIG.ROI.Client.Base.View;
using System.Resources;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    public partial class AccountManagementFooterUI : ROIBaseUI
    {
        public AccountManagementFooterUI()
        {
            InitializeComponent();
        }

        #region Methods

        /// <summary>
        /// Localizes all the controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, paymentsButton);
            SetLabel(rm, adjustmentsButton);
            SetLabel(rm, statementsButton);
            SetLabel(rm, refundButton);
            
           // rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            //SetTooltip(rm, toolTip, saveButton);
            //SetTooltip(rm, toolTip, deleteRequestorButton);
        }

        /// <summary>
        /// Gets the localized key for UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets the localized key for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        #endregion

        #region Properties

        public Button PaymentsButton
        {
            get { return paymentsButton; }
        }

        public Button AdjustmentsButton
        {
            get { return adjustmentsButton; }
        }

        public Button StatementsButton
        {
            get { return statementsButton; }
        }

        public Button RefundButton
        {
            get { return refundButton; }
        }

        #endregion

        private void AccountManagementFooterUI_Paint(object sender, PaintEventArgs e)
        {
            //Pen pen = new Pen(Color.FromArgb(255, 0, 0, 0));
            //e.Graphics.DrawLine(pen, 20, 10, 300, 100);
        }
    }
}
