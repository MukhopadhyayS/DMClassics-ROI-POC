using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Request.View
{
    public sealed partial class SplashScreen : Form
    {
       // private volatile static SplashScreen singleTonObject;
       //private static SplashScreen _instance;

        public SplashScreen()
        {   
            InitializeComponent();
        }
        
        /*public static SplashScreen Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new SplashScreen();
                }

                return _instance;
            }
        }
         
        
         
        //public static SplashScreen InstanceCreation()
        //{
        //    object lockingObject = new object();
        //    if (singleTonObject == null)
        //    {
        //        lock (lockingObject)
        //        {
        //            if (singleTonObject == null)
        //            {
        //                singleTonObject = new SplashScreen();
        //            }
        //        }
        //    }
        //    return singleTonObject;
        //}
        */
    }
}
