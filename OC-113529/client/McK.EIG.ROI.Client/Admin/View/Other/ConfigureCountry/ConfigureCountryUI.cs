using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;


namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureCountry
{
    /// <summary>
    /// This class holds the masking UI
    /// </summary>
    public partial class ConfigureCountryUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(ConfigureCountryUI));

        #region Fields
        private EventHandler dirtyDataHandler;
        private CountryCodeDetails defaultCountryDetails;
        private System.Collections.Generic.List<CountryCodeDetails> CountryDetailsList;
        private string predefinedCountryCode = "US";
        private int defaultCountryIndex;
 
        #endregion

        #region constructor
        public ConfigureCountryUI()
        {
            defaultCountryDetails = new CountryCodeDetails();
            CountryDetailsList = new System.Collections.Generic.List<CountryCodeDetails>();
            defaultCountryIndex = -1;
            InitializeComponent();
            saveButton.Enabled = false;
            cancelButton.Enabled = false;
            dirtyDataHandler = new EventHandler(MarkDirty);
        }
        #endregion


        /// <summary>
        /// Occurs when the user changes the SSN Masking UI .
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            saveButton.Enabled = true;
            cancelButton.Enabled = true;
            (Pane as ConfigureCountryMCP).OnDataDirty(sender, e);
        }
        /// <summary>
        ///  This method is used to enable(subscribe)the Configure country UI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            ConfigureCountryComboBox.Click += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscripe)the Configure country UI local events
        /// </summary>
        public void DisableEvents()
        {
            ConfigureCountryComboBox.Click -= dirtyDataHandler;
        }

        private static int Compare(CountryCodeDetails x, CountryCodeDetails y)
        {
            if (x == null)
            {
                if (y == null)
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                if (y == null)
               {
                    return 1;
                }
                else
                {
                    // ...and y is not null, compare the  
                    // lengths of the two strings. 
                    // 
                    return x.CountryName.CompareTo(y.CountryName);
                }
            }
        }

       public void SetData()
        {
            CountryDetails();
        }

        /// <summary>
        ///  This method is used to load country details in combobox
        /// </summary>
        private void CountryDetails()
        {
            //(Pane as ConfigureCountryMCP).IsDirty = false;
            defaultCountryIndex = -1;
            int predefinedIndex = -1;
            CountryDetailsList = ROIController.Instance.RetrieveCountryList();
            CountryDetailsList.Sort(Compare);
            int i = 0;
            foreach (CountryCodeDetails countryList in CountryDetailsList)
            {   
                ConfigureCountryComboBox.Items.Add(CountryDetailsList[i].CountryName);
                if (CountryDetailsList[i].DefaultCountry)
                {
                    defaultCountryIndex = i;
                }
                if (CountryDetailsList[i].CountryCode == predefinedCountryCode)
                {
                    predefinedIndex = i;
                }
                ++i;
            }
            if (defaultCountryIndex == -1)
            {
                if (predefinedIndex == -1)
                {

                    defaultCountryIndex = 0;
                }
                else
                {
                    defaultCountryIndex = predefinedIndex;
                }

            }
            ConfigureCountryComboBox.SelectedIndex = defaultCountryIndex;
        }

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, defaultCountryLabel);
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
        }
        
        private void ConfigureCountryComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            saveButton.Enabled = ConfigureCountryComboBox.SelectedIndex != defaultCountryIndex;
            cancelButton.Enabled = ConfigureCountryComboBox.SelectedIndex != defaultCountryIndex;
        }

        public bool Save()
        {
            int i = ConfigureCountryComboBox.SelectedIndex;
            defaultCountryDetails.CountryCode = CountryDetailsList[i].CountryCode;
            defaultCountryDetails.CountryName = CountryDetailsList[i].CountryName;
            defaultCountryDetails.DefaultCountry = true;
            defaultCountryIndex = i;
             ROIController.Instance.UpdateCountryCode(defaultCountryDetails);
            (Pane as ConfigureCountryMCP).IsDirty = false;
            saveButton.Enabled = false;
            cancelButton.Enabled = false;
            return true;
       }

        public void Cancel()
        {
            (Pane as ConfigureCountryMCP).IsDirty = false;
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            ConfigureCountryComboBox.SelectedIndex = defaultCountryIndex;
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            Save();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            Cancel();
        }
    }
}
