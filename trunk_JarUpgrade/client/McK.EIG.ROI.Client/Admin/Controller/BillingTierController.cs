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
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Handles Billing tier service invocations
    /// </summary>
    public partial class BillingAdminController
    {
        #region Methods

        #region Service Methods
        /// <summary>
        /// This method will creates a new BillingTier
        /// </summary>
        /// <param name="BillingTierDetails"> BillingTier details which has to be created</param>
        /// <returns>The Newly Created BillingTier Id</returns> 
        public BillingTierDetails CreateBillingTier(BillingTierDetails billingTierDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();

            if (!validator.ValidateCreate(billingTierDetails))
            {
                throw validator.ClientException;
            }
            
            BillingTier serverBillingTier = MapModel(billingTierDetails);
            object[] requestParams = new object[] { serverBillingTier };
            ROIHelper.Invoke(billingAdminService, "createBillingTier", requestParams);
            BillingTierDetails clientBillingTierDetails = MapModel((BillingTier)requestParams[0]);
            BillingCache.AddData(clientBillingTierDetails.Id, clientBillingTierDetails);
            return clientBillingTierDetails;
        }

        /// <summary>
        /// This method will update an existing BillingTier
        /// </summary>
        /// <param name="BillingTierDetails">BillingTier details which has to be updated.</param>
        /// <returns>Returns updated BillingTier details</returns>
        public BillingTierDetails UpdateBillingTier(BillingTierDetails billingTierDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();

            if (!validator.ValidateUpdate(billingTierDetails))
            {
                throw validator.ClientException;
            }
            
            BillingTier serverBillingTier = MapModel(billingTierDetails);
            object[] requestParams = new object[] { serverBillingTier };
            ROIHelper.Invoke(billingAdminService, "updateBillingTier", requestParams);
            billingTierDetails = MapModel((BillingTier)requestParams[0]);
            BillingCache.AddData(billingTierDetails.Id, billingTierDetails);

            return billingTierDetails;
        }

        /// <summary>
        /// Deletes the specified BillingTier.
        /// </summary>
        /// <param name="BillingTierIds">BillingTier id which has to be deleted</param>
        public void DeleteBillingTier(long billingTierId)
        {
            object[] requestParams = new object[] { billingTierId };
            ROIHelper.Invoke(billingAdminService, "deleteBillingTier", requestParams);
            BillingCache.RemoveKey(billingTierId);
        }

        /// <summary>
        /// Returns a list of BillingTiers.
        /// </summary>
        /// <returns>List of BillingTiers</returns>
        public Collection<BillingTierDetails> RetrieveAllBillingTiers(bool loadAssociation)
        {
            object response = ROIHelper.Invoke(billingAdminService, "retrieveAllBillingTiers", new object[] { loadAssociation });
            Collection<BillingTierDetails> billingTiers = MapModel((BillingTier[])response);
            return billingTiers;
        }

        public Collection<BillingTierDetails> RetrieveAllBillingTiers()
        {
            return RetrieveAllBillingTiers(true);
        }

        /// <summary>
        /// Returns BillingTier details for the given BillingTierId.
        /// </summary>
        /// <param name="BillingTierId">The id of the BillingTier which has to be retrived</param>
        /// <returns>Returns a BillingTier details </returns>
        public BillingTierDetails GetBillingTier(long billingTierId)
        {
            if (BillingCache.IsKeyExist(billingTierId))
            {
                return BillingCache.GetPatDetails(billingTierId);

            }
            else
            {
                object[] requestParams = new object[] { billingTierId };
                object response = ROIHelper.Invoke(billingAdminService, "retrieveBillingTier", requestParams);
                BillingTierDetails clientBillingTierDetails = MapModel((BillingTier)response);
                BillingCache.AddData(billingTierId, clientBillingTierDetails);
                return clientBillingTierDetails;
            }
        }

        #endregion

        #region Model Mapping

        #region BillingTier

        /// <summary>
        /// Convert server BillingTier to client BillingTier
        /// </summary>
        /// <param name="serverBillingTier"></param>
        /// <returns></returns>
        public static BillingTierDetails MapModel(BillingTier serverBillingTier)
        {
            if (serverBillingTier == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            BillingTierDetails clientBillingTier = new BillingTierDetails();
            clientBillingTier.Id                = serverBillingTier.billingTierId;
            clientBillingTier.Name              = serverBillingTier.name;
            clientBillingTier.Description       = serverBillingTier.description;
            if (!string.IsNullOrEmpty(serverBillingTier.salesTax))
            {
                clientBillingTier.SalesTax = string.Compare(serverBillingTier.salesTax, "Y", true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0 ? ROIConstants.Yes : ROIConstants.No;
            }
            else
            {
                clientBillingTier.SalesTax = ROIConstants.No;
            }
            clientBillingTier.RecordVersionId   = serverBillingTier.recordVersion;
            clientBillingTier.BaseCharge        = serverBillingTier.baseCharge;
            clientBillingTier.OtherPageCharge   = serverBillingTier.defaultPageCharge;
            clientBillingTier.IsAssociated      = serverBillingTier.associated;

            if (serverBillingTier.mediaType != null)
            {
                clientBillingTier.MediaType = MapModel(serverBillingTier.mediaType);
            }
            else
            {
                clientBillingTier.MediaType = new MediaTypeDetails();
                clientBillingTier.MediaType.Id   = serverBillingTier.mediaTypeId;
                clientBillingTier.MediaType.Name = serverBillingTier.mediaTypeName;
            }

            if (serverBillingTier.pageLevelTier != null)
            {
                MapModel(serverBillingTier.pageLevelTier, clientBillingTier.PageTiers);
            }
            
            return clientBillingTier;
        }

        /// <summary>
        /// Convert client BillingTier to server BillingTier
        /// </summary>
        /// <param name="clientBillingTier"></param>
        /// <returns></returns>
        public static BillingTier MapModel(BillingTierDetails clientBillingTier)
        {
            if (clientBillingTier == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            BillingTier serverBillingTier = new BillingTier();

            serverBillingTier.billingTierId     = clientBillingTier.Id; 
            serverBillingTier.name              = clientBillingTier.Name;
            serverBillingTier.description       = clientBillingTier.Description;
            serverBillingTier.salesTax          = string.Compare(clientBillingTier.SalesTax, "yes", true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0 ? "Y" : "N";
            serverBillingTier.recordVersion     =  clientBillingTier.RecordVersionId;
            serverBillingTier.baseCharge        = (float) clientBillingTier.BaseCharge;
            serverBillingTier.defaultPageCharge = (float) clientBillingTier.OtherPageCharge;

            serverBillingTier.mediaType         = MapModel(clientBillingTier.MediaType);
            
            serverBillingTier.pageLevelTier     = MapModel(clientBillingTier.PageTiers);

            return serverBillingTier;
        }


        /// <summary>
        /// Convert server BillingTierlist to client BillingTierlist
        /// </summary>
        /// <param name="serverBillingTiers"></param>
        /// <returns></returns>
        public static Collection<BillingTierDetails> MapModel(BillingTier[] serverBillingTiers)
        {
            if (serverBillingTiers == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<BillingTierDetails> clientBillingTiers = new Collection<BillingTierDetails>();
            BillingTierDetails clientBillingTier;
            foreach (BillingTier serverBillingTier in serverBillingTiers)
            {
                clientBillingTier = MapModel(serverBillingTier);
                clientBillingTiers.Add(clientBillingTier);
            }

            return clientBillingTiers;
        }

        #endregion

        #region PageLevelTier

        /// <summary>
        /// Convert server PageLevelTier to client PageLevelTier
        /// </summary>
        /// <param name="serverPageLevelTier"></param>
        /// <returns></returns>
        public static PageLevelTierDetails MapModel(PageLevelTier serverPageLevelTier)
        {
            if (serverPageLevelTier == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            PageLevelTierDetails clientPageLevelTier = new PageLevelTierDetails();

            clientPageLevelTier.Id                   = serverPageLevelTier.pageLevelTierId;
            clientPageLevelTier.PricePerPage         =  serverPageLevelTier.pageCharge;
            clientPageLevelTier.EndPage              = serverPageLevelTier.page;
            clientPageLevelTier.RecordVersionId      = serverPageLevelTier.recordVersion;

            return clientPageLevelTier;
        }



        /// <summary>
        /// Convert server PageLevelTier to client PageLevelTier
        /// </summary>
        /// <param name="serverPageLevelTier"></param>
        /// <returns></returns>
        public static PageLevelTier MapModel(PageLevelTierDetails clientPageLevelTier)
        {
            if (clientPageLevelTier == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            PageLevelTier serverPageLevelTier = new PageLevelTier();

            serverPageLevelTier.pageLevelTierId = clientPageLevelTier.Id;
            serverPageLevelTier.recordVersion   = clientPageLevelTier.RecordVersionId; 
            serverPageLevelTier.pageCharge      = (float) clientPageLevelTier.PricePerPage;
            serverPageLevelTier.page            = clientPageLevelTier.EndPage;
            return serverPageLevelTier;
        }

        /// <summary>
        /// Convert server PageLevelTierlist to client PageLevelTierlist
        /// </summary>
        /// <param name="serverPageLevelTiers"></param>
        /// <returns></returns>
        public static void MapModel(PageLevelTier[] serverPageLevelTiers, Collection<PageLevelTierDetails> appendTo)
        {
            if (serverPageLevelTiers == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            SortedList sorterPageTiers = new SortedList();

            foreach (PageLevelTier serverPageLevelTier in serverPageLevelTiers)
            {
               sorterPageTiers.Add(serverPageLevelTier.page, serverPageLevelTier);
            }
            
            PageLevelTierDetails clientPageLevelTier;
            int prevEndPage = 0;

            foreach (PageLevelTier serverPageLevelTier in sorterPageTiers.Values)
            {
                clientPageLevelTier = MapModel((PageLevelTier)serverPageLevelTier);
                clientPageLevelTier.StartPage = prevEndPage + 1;
                prevEndPage                   = clientPageLevelTier.EndPage;

                appendTo.Add(clientPageLevelTier);
            }
        }


        /// <summary>
        /// Converts Client PageLevelTierlist to Server PageLevelTierlist
        /// </summary>
        /// <param name="serverPageLevelTiers"></param>
        /// <returns></returns>
        public static PageLevelTier[] MapModel(Collection<PageLevelTierDetails> clientPageLevelTierDetails)
        {
            if (clientPageLevelTierDetails == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            PageLevelTier[] serverPageLevelTier = new PageLevelTier[clientPageLevelTierDetails.Count];
            for (int index=0; index<=clientPageLevelTierDetails.Count-1; index++)
            {
                serverPageLevelTier[index] = MapModel(clientPageLevelTierDetails[index]);
            }

            return serverPageLevelTier;
        }


        #endregion
        
        #endregion

        #endregion
    }
}
