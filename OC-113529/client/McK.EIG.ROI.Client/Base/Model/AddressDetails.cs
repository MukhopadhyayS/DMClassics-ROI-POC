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
using System.ComponentModel;
using System.Text;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Base.Model
{
    //SOGI OC-111171
    //Gender Details will be returned from the server
    //public enum Gender
    //{
    //    [Description("Select Gender")]
    //    None = 0,

    //    [Description("Female")]
    //    Female = 1,

    //    [Description("Male")]
    //    Male = 2,

    //    [Description("Unknown")]
    //    Unknown = 3,

    //     [Description("Others")]
    //    Others = 4
    //}



    [Serializable]
    public class AddressDetails : ROIModel
    {

        #region Fields

        //holds the id
        private long id;

        //holds the address 1
        private string address1;

        //holds the address 2
        private string address2;

        //holds the address 3
        private string address3;

        //holds the city
        private string city;

        //holds the state
        private string state;

        //holds the postal code
        private string postalCode;

        //hold the patient email address
        private string email;

        //hold the country code
        private string countryCode;

        //hold the country name
        private string countryName;

        //to update the selected country
        private bool newCountry;

        //to update the selected country by sequence
        private long countrySeq;


        #endregion

        #region Methods
        
        
        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            address1   = string.IsNullOrEmpty(address1) ? string.Empty : address1.Trim();
            address2   = string.IsNullOrEmpty(address2) ? string.Empty : address2.Trim();
            address3   = string.IsNullOrEmpty(address3) ? string.Empty : address3.Trim();
            city       = string.IsNullOrEmpty(city) ? string.Empty : city.Trim();
            state      = string.IsNullOrEmpty(state) ? string.Empty : state.Trim();
            postalCode = string.IsNullOrEmpty(postalCode) ? string.Empty : postalCode.Trim();
            email      = string.IsNullOrEmpty(email) ? string.Empty : email.Trim();
            countryName = string.IsNullOrEmpty(countryName) ? string.Empty : countryName.Trim();
            countryCode = string.IsNullOrEmpty(countryCode) ? string.Empty : countryCode.Trim();
        }

        public override string ToString()
        {
            StringBuilder address = new StringBuilder();
            if (!string.IsNullOrEmpty(address1))
            {
                address.Append(address1);
            }

            if (!string.IsNullOrEmpty(address2))
            {
                address.Append(", ");
                address.Append(address2);
            }

            if (!string.IsNullOrEmpty(address3))
            {
                address.Append(", ");
                address.Append(address3);
            }

            if (!string.IsNullOrEmpty(city))
            {
                address.Append(", ");
                address.Append(city);
            }

            if (!string.IsNullOrEmpty(state))
            {
                address.Append(", ");
                address.Append(state);
            }

            if (!string.IsNullOrEmpty(postalCode))
            {
                address.Append(", ");
                address.Append(postalCode);
            }

            if (!string.IsNullOrEmpty(email))
            {
                address.Append(", ");
                address.Append(email);
            }

            if (!string.IsNullOrEmpty(countryName))
            {
                address.Append(", ");
                address.Append(countryName);
            }

            if (!string.IsNullOrEmpty(countryCode))
            {
                address.Append(", ");
                address.Append(countryCode);
            }

            return address.ToString().TrimStart(',');
        }

        #endregion
        
        #region Properties
        
        /// <summary>
        /// This property is used to get or sets the patient address id
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient address 1
        /// </summary>
        public string Address1
        {
            get { return address1; }
            set { address1 = value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient address 2
        /// </summary>
        public string Address2
        {
            get { return address2; }
            set { address2 = value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient address 3
        /// </summary>
        public string Address3
        {
            get { return address3; }
            set { address3= value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient city
        /// </summary>
        public string City
        {
            get { return city; }
            set { city = value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient state
        /// </summary>
        public string State
        {
            get { return state; }
            set { state = value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient zip code
        /// </summary>
        public string PostalCode
        {
            get { return postalCode; }
            set { postalCode = value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient zip code
        /// </summary>
        public string Email
        {
            get { return email; }
            set { email = value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient country name
        /// </summary>
        public string CountryName
        {
            get { return countryName; }
            set { countryName = value; }
        }
        /// <summary>
        /// This property is used to get or sets the patient country code
        /// </summary>
        public string CountryCode
        {
            get { return countryCode; }
            set { countryCode = value; }
        }
        /// <summary>
        /// This property is used to update the country selected
        /// </summary>
        public bool NewCountry
        {
            get { return newCountry; }
            set { newCountry = value; }
        }
        /// <summary>
        /// This property is used to update the country by sequence Id
        /// </summary>
        public long CountrySeq
        {
            get { return countrySeq; }
            set { countrySeq = value; }
        }
        #endregion
    }
}
