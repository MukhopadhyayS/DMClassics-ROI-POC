using System;
using System.ComponentModel;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// This Class is used to hold TurnaroundTime days configuration info
    /// </summary>
    [Serializable]
    public class TurnaroundTimeDay
    {
        #region Fields

        private string day;
        private string statusOfDay;

        public enum Status
        {
            [Description("Business Day")]
            BusinessDay=0,
            [Description("Weekend Day")]
            WeekendDay=1,
        }
        
        public enum Days
        {
            [Description("SUNDAY")]
            Sunday = 0,
            [Description("MONDAY")]
            Monday = 1,
            [Description("TUESDAY")]
            Tuesday=2,
            [Description("WEDNESDAY")]
            Wednesday=3,
            [Description("THURSDAY")]
            Thursday=4,
            [Description("FRIDAY")]
            Friday=5,
            [Description("SATURDAY")]
            Saturday = 6,        
        
        }
             
        #endregion

        #region Properties

        public string Day
        {
            get { return day; }
            set { day = value; }
        }

        public string StatusOfDay
        {
            get { return statusOfDay; }
            set { statusOfDay = value; }
        }

        #endregion

        #region Methods

        public TurnaroundTimeDay Clone()
        {
            return (TurnaroundTimeDay)this.MemberwiseClone();
        }

        #endregion

    }
}
