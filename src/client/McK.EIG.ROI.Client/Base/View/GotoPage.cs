using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class GotoPage : Form
    {
        //DM-6708 added goto page functionality
        private int pageNo;
        public GotoPage()
        {
            InitializeComponent();
        }
        public int GotoPageNo
        {
            get { return pageNo; }
        }

        private void GotoPagebtn_Click(object sender, EventArgs e)
        {
            pageNo = Convert.ToInt32(textBox1.Text);
            this.Close(); 
        }

        private void cnclbtn_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
