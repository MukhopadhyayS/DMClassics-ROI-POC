#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
#endregion
using System;
using System.Collections.ObjectModel;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{   
    /// <summary>
    /// Contains a set of Page rate tiers
    /// </summary>
    public partial class PageRateTierGroupUI : ROIBaseUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;

        private BillingTierDetails billingTierDetail;
        private Collection<PageLevelTierDetails> pageTiers;     

        private const int defaultPrice = 0;

        private TextBox throughPageTextBox;

        private ROIBaseUI parent;
        
        #endregion

        #region Constructor

        public PageRateTierGroupUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
            InitPageTierGroup();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes Page tier group
        /// </summary>
        private static void InitPageTierGroup()
        {
            Panel pageTierGroupPanel = new Panel();
            pageTierGroupPanel.Padding = new Padding(1);
            pageTierGroupPanel.Size = new Size(365, 67);
            pageTierGroupPanel.BackColor = Color.FromArgb(145, 167, 180);
          
           // pageTierGroupPanel.Controls.Add(outerPanel);
           // pageTierMainGroupPanel.Controls.Add(pageTierGroupPanel);
        }

        /// <summary>
        /// Clears all the controls in pageTiersContainerFlowLayoutPanel and creats a default page rate tier.
        /// </summary>
        public void ClearControls()
        {
            DisableEvents();
            otherChargeTextBox.Text = Convert.ToString(defaultPrice, System.Threading.Thread.CurrentThread.CurrentCulture);
            pageTiersContainerFlowLayoutPanel.Controls.Clear();
            if (pageTiers != null)
            {
                pageTiers.Clear();
            }
            pageTiersContainerFlowLayoutPanel.Controls.Add(CreatePageTier(true));
            addNewTierButton.Enabled = false;

            EnableEvents();
        }

        private PageRateTierUI CreatePageTier(bool isDefault)
        {
            PageRateTierUI pageRateTierUI = new PageRateTierUI(isDefault);
            pageRateTierUI.SetExecutionContext(Context);
            pageRateTierUI.SetPane(Pane);
            pageRateTierUI.EnableEvents();
            pageRateTierUI.Localize();
            pageRateTierUI.deleteHandler += new EventHandler(DeleteButton_Click);
            pageRateTierUI.newRateTierHandler += new EventHandler(EnableAddNewRateTierButton);
            pageRateTierUI.Parent = this;
            if (!isDefault)
            {
                pageRateTierUI.Dock = DockStyle.Bottom;
            }
            return pageRateTierUI;
        }

        /// <summary>
        /// It enables Add New Tier Button if all the existing pagetier fields are fillied.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>

        private void EnableAddNewRateTierButton(object sender, EventArgs e)
        {
            foreach (PageRateTierUI rateTierUI in pageTiersContainerFlowLayoutPanel.Controls)
            {
                if (!rateTierUI.CheckPageTiersFilled())
                {
                    addNewTierButton.Enabled = false;
                    return;
                }
            }
            addNewTierButton.Enabled = true;
        }


        /// <summary>
        /// Gets error controls
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            if (error.ErrorCode == ROIErrorCodes.BillingTierOtherPageChargeIsNotValid)
            {
                return otherChargeTextBox;
            }
            
            string[] data = error.ErrorData.ToString().Split(new char[] {'.'});
            string controlName = data[0] + "textBox";
            int index = Convert.ToInt32(data[1], System.Threading.Thread.CurrentThread.CurrentCulture);
            
            PageRateTierUI pageUI = (PageRateTierUI)pageTiersContainerFlowLayoutPanel.Controls[index];
            return pageUI.GetControl(controlName);
        }

        /// <summary>
        /// Add a new PageTier
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AddNewTierBtn_Click(object sender, EventArgs e)
        {
            PageRateTierUI pageRateTierUI = CreatePageTier(false);
            pageTiersContainerFlowLayoutPanel.Controls.Add(pageRateTierUI);
            int index = pageTiersContainerFlowLayoutPanel.Controls.IndexOf(pageRateTierUI);
            int startPage = Convert.ToInt32(((PageRateTierUI)pageTiersContainerFlowLayoutPanel.Controls[index - 1]).Controls["toPageTextBox"].Text, System.Threading.Thread.CurrentThread.CurrentCulture)+1;
            pageRateTierUI.Controls["fromPageTextBox"].Text = startPage.ToString(System.Threading.Thread.CurrentThread.CurrentCulture);
            pageRateTierUI.Controls["toPageTextBox"].Focus();
            addNewTierButton.Enabled = false;
        }

        /// <summary>
        /// Deletes selected Page Tier.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DeleteButton_Click(object sender, EventArgs e)
        {
            PageRateTierUI selectedPageRateTierUI = (PageRateTierUI)sender;
            int index = pageTiersContainerFlowLayoutPanel.Controls.IndexOf(selectedPageRateTierUI);

            if (pageTiers.Count > index)
            {
                pageTiers.RemoveAt(index);
            }

            pageTiersContainerFlowLayoutPanel.Controls.Remove(selectedPageRateTierUI);
        }

         /// <summary>
        /// Occurs when user changes BillingTier Name or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void MarkDirty(object sender, EventArgs e)
        {
            ((BillingTierTabUI)parent).MarkDirty(sender, e);
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the BillingTierODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            otherChargeTextBox.TextChanged += dirtyDataHandler;
            addNewTierButton.Click         += dirtyDataHandler;

            foreach (PageRateTierUI pageRateTierUI in pageTiersContainerFlowLayoutPanel.Controls)
            {
                pageRateTierUI.EnableEvents();
            }
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the BillingTierODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            otherChargeTextBox.TextChanged -= dirtyDataHandler;
            addNewTierButton.Click         -= dirtyDataHandler;

            foreach (PageRateTierUI pageRateTierUI in pageTiersContainerFlowLayoutPanel.Controls)
            {
                pageRateTierUI.DisableEvents();
            }
        }

        /// <summary>
        /// It collcts all the page rate tiers into a PageTiers collection 
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo, Collection<ExceptionData> errors)
        {
            BillingTierDetails tierDetails = appendTo as BillingTierDetails;
            //Collection<ExceptionData> errors = new Collection<ExceptionData>();

            try
            {
                tierDetails.OtherPageCharge = Convert.ToDouble(otherChargeTextBox.Text.Trim(),
                                                                     System.Threading.Thread.CurrentThread.CurrentCulture);
            }
            catch (Exception)
            {
                ExceptionData error = new ExceptionData(ROIErrorCodes.BillingTierOtherPageChargeIsNotValid);
                errors.Add(error);
                //throw new ROIException(ROIErrorCodes.BillingTierOtherPageChargeIsNotValid, ex);
            }

            Collection<PageLevelTierDetails> temp = new Collection<PageLevelTierDetails>();
            foreach (PageLevelTierDetails pageTier in tierDetails.PageTiers)
            {
                temp.Add(pageTier);
            }

            foreach (PageLevelTierDetails pageTier in temp)
            {
                if (!pageTiers.Contains(pageTier)) tierDetails.PageTiers.Remove(pageTier);
            }

            int index = 0;

            foreach (PageRateTierUI rateTierUI in pageTiersContainerFlowLayoutPanel.Controls)
            {
                errors = rateTierUI.CheckErrors(errors, index);
                index++;
            }

            if (errors.Count > 0)
            {
                throw new ROIException(errors);
            }
           
            index = 0;                
            foreach (PageRateTierUI rateTierUI in pageTiersContainerFlowLayoutPanel.Controls)
            {
                if (tierDetails.PageTiers.Count > index)
                {
                    tierDetails.PageTiers[index] =
                                          (PageLevelTierDetails)rateTierUI.GetData(tierDetails.PageTiers[index]);
                }
                else
                {
                    tierDetails.PageTiers.Add((PageLevelTierDetails)rateTierUI.GetData(null));
                }
                index++;
            }


            return tierDetails;
        }
       
        /// <summary>
        /// It sets the page rate tier data
        /// There will be one default page rate tier whose start page 1. 
        /// It will be created at the time of clear controls.
        /// Each page rate tier to page rate tier group and calls setdata.
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            billingTierDetail = data as BillingTierDetails;

            otherChargeTextBox.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentCulture, "{0:F}", new object[] { billingTierDetail.OtherPageCharge });
            
            pageTiers = new Collection<PageLevelTierDetails>();
            foreach (PageLevelTierDetails pageTier in billingTierDetail.PageTiers)
            {
                pageTiers.Add(pageTier);
            }

            PageRateTierUI pageRateTierUI;

            addNewTierButton.Enabled = billingTierDetail.PageTiers.Count > 0;

            foreach (PageLevelTierDetails pageLevelTier in  billingTierDetail.PageTiers)
            {
                if (pageLevelTier.StartPage != 1)
                {
                    pageRateTierUI = CreatePageTier(false);                    
                    pageTiersContainerFlowLayoutPanel.Controls.Add(pageRateTierUI);
                }
                else
                {
                    pageRateTierUI = pageTiersContainerFlowLayoutPanel.Controls[0] as PageRateTierUI;
                    throughPageTextBox = pageRateTierUI.Controls["toPageTextBox"] as TextBox;                    
                }

                pageRateTierUI.SetData(pageLevelTier);
            }
            EnableEvents();
        }

        /// <summary>
        /// Apply Localization for UI controls.
        /// </summary>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == amountLabel )
            {
                return control.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + this.GetType().Name;
        }


        /// <summary>
        /// Apply Localization for UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, amountLabel);
            SetLabel(rm, addNewTierButton);
            SetLabel(rm, perPageLabel);
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            System.Windows.Forms.ToolTip tooltip = new ToolTip();

            SetTooltip(rm, tooltip, otherChargeTextBox);
            SetTooltip(rm, tooltip, addNewTierButton);
        }

        /// <summary>
        /// It will restrict user to enter other characters apart from Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void otherChargeTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }
        #endregion

        #region Properties

        /// <summary>
        /// Returns ThroughPage textbox control of PageRateTierUI.
        /// </summary>
        public TextBox ThroughPageTextBox
        {
            get 
            {              
                return throughPageTextBox;
            }
        }

        /// <summary>
        /// Returns OtherPage textbox control of PageRateTierGroupUI.
        /// </summary>
        public TextBox OtherPageChargeTextBox
        {
            get { return otherChargeTextBox; }
        }

        public ROIBaseUI Parent
        {
            get { return parent; }
            set { parent = value; }
        }


        #endregion  

    }
}
