using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminValidator
    {
        public bool ValidateUpdateDaysStatus(Collection<TurnaroundTimeDay> days)
        {
            foreach (TurnaroundTimeDay day in days)
            {
                ValidateDayFields(day);
            }
            
            return NoErrors;
        }
        public bool  ValidateDayFields(TurnaroundTimeDay day)
        {
            if (day.Day.Trim().Length == 0)
                AddError(ROIErrorCodes.DayNameinvalid);
            if (day.StatusOfDay.Trim().Length == 0)
                AddError(ROIErrorCodes.DayStatusinvalid);
            return NoErrors;
        }

    }
}
