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
using System.Collections;
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Base.Model
{

    public enum FacilityType
    {
        Hpf,
        NonHpf
    }

    //public class  FacilityComparer : IComparer<string>
    //{
    //    #region IComparer<string> Members

    //    public int Compare(string x, string y)
    //    {
    //        if (UserData.Instance.Facilities.Count <= 1 && x == y) return 0;

    //        Facility obj1 = ((Facility)UserData.Instance.Facilities[x]);
    //        Facility obj2 = ((Facility)UserData.Instance.Facilities[y]);
    //        return obj1.CompareTo(obj2);
    //    }

    //    #endregion
    //}
    [Serializable]
    public class FacilityDetails : ROIModel, IComparable<FacilityDetails> 
    {
        #region Fields

        private string name;
        private string code;
        private FacilityType type;
        private bool isDefault;
        private double taxPercentage;

        #endregion

        #region Constructor

        public FacilityDetails() { }

        public FacilityDetails(string name, string code, FacilityType type)
        {
            this.name = name;
            this.code = code;
            this.type = type;
        }
        
        public FacilityDetails(string code, FacilityType type): this(string.Empty, code, type)
        {
        }

        #endregion

        #region Methods

        public override bool Equals(object obj)
        {
            if (obj.GetType() == typeof(System.DBNull)) return false;
            if (object.ReferenceEquals(this, obj)) return true;

            FacilityDetails fac = (FacilityDetails)obj;

            return (string.Equals(this.Key, fac.Key, StringComparison.CurrentCultureIgnoreCase));
        }

        public override int GetHashCode()
        {
            return this.GetType().GetHashCode();
        }

        //public static Facility GetFacility(string code)
        //{
        //    return UserData.Instance.Facilities.Find(delegate(Facility facility)
        //                                {
        //                                    return string.Equals(facility.code, code, StringComparison.CurrentCultureIgnoreCase);
        //                                });
        //}


        public static FacilityDetails  GetFacility(string code, FacilityType type)
        {
            FacilityDetails facility = new FacilityDetails(code, (FacilityType)Enum.Parse(typeof(FacilityType), type.ToString()));
            int index = UserData.Instance.Facilities.IndexOf(facility);
            if (index != -1)
            {
               return  facility = UserData.Instance.Facilities[index];
            }
            return null;
        }


        #endregion

        #region IComparable<FacilityDetails> Members

        public int CompareTo(FacilityDetails other)
        {
            string key = this.type + "." + this.name;
            return key.CompareTo(other.type + "." + other.name);
        }

        #endregion

        #region Properties


        public string Code
        {
            get { return code; }
            set { code = value; }
        }

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public FacilityType Type
        {
            get { return type; }
            set { type = value; }
        }

        public string Key
        {
            get { return type + "." + code; }
        }

        public bool IsDefault
        {
            get { return isDefault; }
            set { isDefault = value; }
        }

        public double TaxPercentage
        {
            get { return taxPercentage; }
            set { taxPercentage = value; }
        }

        #endregion     
    
        private long id;
        public override long Id
        {
            get
            {
                return id;
            }
            set
            {
                id = value;
            }
        }
    }
}
