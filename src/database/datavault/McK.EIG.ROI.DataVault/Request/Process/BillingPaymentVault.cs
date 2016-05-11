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
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.DataVault.Admin;
using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    /// <summary>
    /// Class Release the request or save the Draft.
    /// </summary>
    public partial class BillingPaymentVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(BillingPaymentVault));

        #region Fields

        //Common Fields
        private const string RefId        = "Ref_ID";
        private const string RequestRefId = "ReqRef_ID";
        private const string ReleaseRefId = "RelRef_ID";
        private const string Amount       = "Amount";
        private const string TotalCount   = "TotalCount";

        private const string RequestRelease = "IsRelease";
        
        private RequestDetails requestDeatils;
        private RequestDetails requestDeatilsCloneObject;
        private RequestorDetails requestorDetails;
        private RequestorTypeDetails requestorTypeDetails;
        private BillingTierDetails defaultHpfBillingTierDetails;
        private BillingTierDetails defaultNonHpfBillingTierDetails;
        private VaultMode modeType;

        private Hashtable releaseInfoHT;
        private Hashtable htBillingTiers;

        private int releaseCount;

        private ReleaseDetails releaseDetail;
        private DocumentChargeDetails documentCharge;

        private Collection<BillingTierDetails> billingTiers;

        private double paymentTotal;
        private double adjustmentTotal;
        private double previouslyReleasedCost;
        private double docChargeTotal;
        private double feeChargeTotal;

        #endregion

        #region Constructor

        public BillingPaymentVault()
        {
            releaseInfoHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Billing_PaymentVault Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();

            long recordCount;            

            try
            {
                recordCount = 1;

                while (reader.Read())
                {
                    string reqRefId     = Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture).Trim();
                    string releaseRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();                    

                    long requestID      = Convert.ToInt64(RequestVault.GetEntityObject(DataVaultConstants.RequestInfo,
                                                          reqRefId),
                                                          CultureInfo.CurrentCulture);                    
                    //Get the Request Information selected for release.
                    GetRequestInfo(requestID);
                    GetRequestorInfo();
                    GetRequestorTypeDetails();
                    GetBillingTierDetails();

                    releaseDetail = new ReleaseDetails();
                    releaseDetail.RequestId = requestDeatils.Id;

                    if (requestDeatils.HasDraftRelease)
                    {
                        releaseDetail = GetReleaseInformation(requestDeatils.DraftRelease);
                        releaseDetail.RequestId = requestDeatils.Id;
                    }
                    else
                    {
                        if (requestDeatils.Releases.Count > 0)
                        {
                            releaseDetail = BillingController.Instance.RetrieveLatestReleaseInfo(releaseDetail);
                        }
                    }                  

                    releaseDetail.Details = string.Empty;
                    ReleasedPatientDetails releasePatient;
                    releaseDetail.ReleasedPatients.Clear();
                    
                    Dictionary<long, int> nonHpfBillingTiersIds = new Dictionary<long, int>();
                    foreach (RequestPatientDetails patient in requestDeatils.ReleasedItems.Values)
                    {
                        releasePatient = new ReleasedPatientDetails(patient);
                        releaseDetail.ReleasedPatients.Add(patient.Key, releasePatient);
                        releaseDetail.Details += patient.ToXml();

                        foreach (RequestNonHpfEncounterDetails nonHpfEncounter in releasePatient.NonHpfDocument.GetChildren)
                        {
                            foreach (RequestNonHpfDocumentDetails nonHpfDoc in nonHpfEncounter.GetChildren)
                            {
                                if (nonHpfBillingTiersIds.ContainsKey(nonHpfDoc.BillingTier))
                                {
                                    nonHpfBillingTiersIds[nonHpfDoc.BillingTier] += nonHpfDoc.PageCount;
                                }
                                else
                                {
                                    nonHpfBillingTiersIds.Add(nonHpfDoc.BillingTier, nonHpfDoc.PageCount);
                                }
                            }
                        }
                    }
                    if (releaseDetail.ReleasedPatients.Count > 0)
                    {
                        billingTiers = BillingAdminController.Instance.RetrieveAllBillingTiers();
                        htBillingTiers = new Hashtable(billingTiers.Count);

                        foreach (BillingTierDetails billingTier in billingTiers)
                        {
                            htBillingTiers.Add(billingTier.Id, billingTier);
                        }
                        List<DocumentChargeDetails> previousReleaseDocumentCharges = null;
                        if (requestDeatils.Releases.Count > 0)
                        {
                            previousReleaseDocumentCharges = new List<DocumentChargeDetails>(GetPreviouReleaseDocumentCharges(requestDeatils));
                        }

                        DocumentChargesUpdate(releaseDetail, nonHpfBillingTiersIds,
                                             requestorTypeDetails, htBillingTiers, requestDeatils.IsReleased,
                                             previousReleaseDocumentCharges);                        
                    }
                    else
                    {
                        releaseDetail.DocumentCharges.Clear();
                        releaseDetail.TotalPages = 0;
                    }

                    //Get the Release Information.
                    releaseDetail = GetDocumentChargeDetails(releaseDetail, releaseRefId);
                    releaseDetail = GetFeeChargeDetails(releaseDetail, releaseRefId);
                    releaseDetail = GetShippingDetails(releaseDetail, releaseRefId);
                    releaseDetail = GetAdjustmentChargeDetails(releaseDetail, releaseRefId);
                    releaseDetail = GetPaymentDetails(releaseDetail, releaseRefId);

                    UpdateDocumentCharge();
                    UpdateFeeCharge();                    
                    UpdateAdjPayTotal();
                    UpdatePreviouslyReleasedCost();
                    
                    releaseDetail.ReleaseCost         = PendingReleaseCost;
                    releaseDetail.AdjustmentTotal     = AdjustmentTotal;
                    releaseDetail.PaymentTotal        = PaymentTotal;
                    releaseDetail.BalanceDue          = BalanceDue;
                    releaseDetail.DocumentChargeTotal = DocumentChargeTotal;
                    
                    if (modeType == VaultMode.Create)
                    {
                        //Save the release information.
                        releaseDetail.IsReleased = false;
                        releaseDetail = SaveReleaseInformation(releaseDetail, recordCount);
                    }
                    else
                    {
                        if (requestDeatils.IsReleased)
                        {
                            releaseDetail.IsReleased = false;
                            releaseDetail = SaveReleaseInformation(releaseDetail, recordCount);
                        }
                        else
                        {
                            releaseDetail = UpdateReleaseInformation(releaseDetail, recordCount);
                        }
                    }

                    //Whether the request need to be released.
                    bool isReleased = (reader[RequestRelease].ToString().Length == 0) ? false :
                                       Convert.ToBoolean(reader[RequestRelease], CultureInfo.CurrentCulture);
                    if (isReleased)
                    {
                        if (releaseDetail.ShippingDetails.OutputMethod != OutputMethod.None)
                        {
                            releaseDetail.IsReleased = true;
                            ReleaseRequest(releaseDetail, recordCount);
                        }
                        else
                        {
                            string message = string.Format( CultureInfo.CurrentCulture, DataVaultErrorCodes.OutputMethodNotSpecified, reqRefId);
                            log.Debug(message);
                            throw new VaultException(message);
                        }
                    }
                    if (!releaseInfoHT.ContainsKey(releaseRefId))
                    {
                        releaseInfoHT.Add(releaseRefId, releaseDetail.Id);
                    }

                    recordCount++;
                }
                log.ExitFunction();
                return releaseInfoHT;
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (InvalidCastException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (ArgumentException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (Exception cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message);
            }
            finally
            {
                reader.Close();
            }
        }        

        private static IList<DocumentChargeDetails> GetPreviouReleaseDocumentCharges(RequestDetails request)
        {

            SortedList<long, DocumentChargeDetails> documentCharges = new SortedList<long, DocumentChargeDetails>();
            
            foreach (ReleaseDetails release in request.Releases)
            {
                if (release.DocumentCharges.Count == 0)
                {
                    BillingController.Instance.RetrieveReleaseInfo(release);
                }

                foreach (DocumentChargeDetails documentCharge in release.DocumentCharges)
                {
                    if(!documentCharges.ContainsKey(documentCharge.BillingTierId))
                    {
                        documentCharges.Add(documentCharge.BillingTierId, documentCharge);        
                    }
                }
            }
            return documentCharges.Values;
        }

        private void DocumentChargesUpdate(ReleaseDetails release,
                                           Dictionary<long, int> nonHpfBillingTiersIds,
                                           RequestorTypeDetails requestorType,
                                           Hashtable htBillingTiers, bool isReleased,
                                           List<DocumentChargeDetails> previousReleaseDocumentCharges)
        {
            log.EnterFunction();
            if (previousReleaseDocumentCharges == null)
            {
                previousReleaseDocumentCharges = new List<DocumentChargeDetails>();
            }
            releaseDetail.DocumentCharges.Clear();
            DocumentChargeDetails documentCharge;
            BillingTierDetails billingTier;
            releaseDetail.TotalPages = 0;
            int releasedHpfDocuments = releaseDetail.ReleasedHpfDocuments;
            if (releasedHpfDocuments > 0)
            {
                billingTier = (BillingTierDetails)htBillingTiers[requestorType.HpfBillingTier.Id];
                documentCharge = GetDocumentChargeForBillingTier(previousReleaseDocumentCharges, defaultHpfBillingTierDetails.Id, isReleased);
                documentCharge.BillingTierId = billingTier.Id;
                documentCharge.Pages = releasedHpfDocuments;
                documentCharge.IsElectronic = true;
                documentCharge.BillingTier = billingTier.Name;
                documentCharge.Copies = 1;

                releaseDetail.TotalPages = documentCharge.Pages * documentCharge.Copies;
                AddDocumentCharge(releaseDetail, documentCharge);
            }

            foreach (long billingTierId in nonHpfBillingTiersIds.Keys)
            {
                if (billingTierId != 0)
                {
                    billingTier = (BillingTierDetails)htBillingTiers[billingTierId];
                }
                else
                {
                    billingTier = defaultNonHpfBillingTierDetails;
                }

                documentCharge = GetDocumentChargeForBillingTier(previousReleaseDocumentCharges, billingTier.Id, isReleased);
                documentCharge.BillingTierId = billingTier.Id;
                documentCharge.Pages = nonHpfBillingTiersIds[billingTierId];
                documentCharge.BillingTier = billingTier.Name;
                documentCharge.Copies = 1;

                releaseDetail.TotalPages += documentCharge.Copies * documentCharge.Pages;
                AddDocumentCharge(releaseDetail, documentCharge);
            }
            log.ExitFunction();
        }

        private static DocumentChargeDetails GetDocumentChargeForBillingTier(List<DocumentChargeDetails> documentCharges, long billingTier, bool isReleased)
        {
            DocumentChargeDetails documentCharge = documentCharges.Find(delegate(DocumentChargeDetails docCharge) { return docCharge.BillingTierId == billingTier; });
            if (documentCharge != null && isReleased)
            {
                documentCharge.RemoveBaseCharge = true;
            }
            return (documentCharge == null) ? new DocumentChargeDetails() : (DocumentChargeDetails)ROIViewUtility.DeepClone(documentCharge);
        }

        private static void AddDocumentCharge(ReleaseDetails releaseDetail, DocumentChargeDetails documentCharge)
        {
            if (releaseDetail.DocumentCharges.Count > 0)
            {
                bool isUpdated = false;
                foreach (DocumentChargeDetails docCharge in releaseDetail.DocumentCharges)
                {
                    if (docCharge.BillingTierId == documentCharge.BillingTierId)
                    {
                        docCharge.Pages += documentCharge.Pages;
                        documentCharge.TotalPages += documentCharge.Pages;
                        isUpdated = true;
                        break;
                    }
                }
                if (!isUpdated)
                {
                    releaseDetail.DocumentCharges.Add(documentCharge);
                }
            }
            else
            {
                releaseDetail.DocumentCharges.Add(documentCharge);
            }
        }
          
        /// <summary>
        /// Passes the Release information object to the Billing Controlle, to release the request.
        /// </summary>
        /// <param name="releaseDetail">Release Info.</param>
        /// <param name="recordCount">Record Count.</param>
        private void ReleaseRequest(ReleaseDetails releaseDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                    DataVaultConstants.ProcessStartTag,
                                    recordCount,
                                    DateTime.Now));
                if (requestDeatilsCloneObject.ReleasedItems.Count == 0)
                    return;
                //Call the ReleaseController to release the request.
                BillingController.Instance.Release(releaseDetail);
                requestDeatilsCloneObject.Releases.Add(releaseDetail);
                requestDeatilsCloneObject = RequestController.Instance.UpdateRequest(requestDeatilsCloneObject);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
                log.ExitFunction();                
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        /// <summary>
        /// Passes the Release information object to the Billing Controller, to save the release details.
        /// </summary>
        /// <param name="releaseDetail">Release Info Details.</param>
        /// <param name="recordCount">Record Count</param>
        private ReleaseDetails SaveReleaseInformation(ReleaseDetails releaseDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ProcessStartTag,
                                      recordCount,
                                      DateTime.Now));


                //Call the RequestorController to save the RequestorInformation.
                long id = BillingController.Instance.CreateReleaseItem(releaseDetail);
                requestDeatilsCloneObject.BalanceDue = BalanceDue;
                requestDeatilsCloneObject.Requestor = requestorDetails;
                requestDeatilsCloneObject = RequestController.Instance.UpdateRequest(requestDeatilsCloneObject);

                releaseDetail.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return releaseDetail;
        }

        private ReleaseDetails UpdateReleaseInformation(ReleaseDetails releaseDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ProcessStartTag,
                                      recordCount,
                                      DateTime.Now));


                //Call the RequestorController to save the RequestorInformation.
                releaseDetail = BillingController.Instance.UpdateReleaseItem(releaseDetail);
                requestDeatilsCloneObject.BalanceDue = BalanceDue;
                requestDeatilsCloneObject.Requestor = requestorDetails;
                requestDeatilsCloneObject = RequestController.Instance.UpdateRequest(requestDeatilsCloneObject);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return releaseDetail;
        }


        /// <summary>
        /// Retrive the Release details.
        /// </summary>
        /// <param name="releaseDetail">Release Info Details.</param>
        /// <param name="recordCount">Record Count</param>
        private ReleaseDetails GetReleaseInformation(ReleaseDetails releaseDetail)
        {
            log.EnterFunction();
            try
            {
                return BillingController.Instance.RetrieveReleaseInfo(releaseDetail);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Get the request details for the given requestID.
        /// </summary>
        /// <param name="requestId">Request ID.</param>        
        private void GetRequestInfo(long requestId)
        {
            log.EnterFunction();
            try
            {
                requestDeatils = RequestController.Instance.RetrieveRequest(requestId);
                requestDeatilsCloneObject =(RequestDetails)McK.EIG.ROI.Client.Base.View.ROIViewUtility.DeepClone(requestDeatils);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Get the Requestor Info details for the given requestor Id.
        /// </summary>
        /// <param name="requestorID"></param>
        private void GetRequestorInfo()
        {
            log.EnterFunction();
            try
            {
                requestorDetails = RequestorController.Instance.RetrieveRequestor(requestDeatils.Requestor.Id);
                requestDeatils.Requestor = requestorDetails;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
        }


        /// <summary>
        /// Get the requestor Type details for the Request selected for release.
        /// </summary>
        /// <param name="requestorType"></param>
        private void GetRequestorTypeDetails()
        {
            log.EnterFunction();
            try
            {
                requestorTypeDetails = ROIAdminController.Instance.GetRequestorType(requestorDetails.Type);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Get the Billing Tier detils for the Request selected for release.
        /// </summary>
        private void GetBillingTierDetails()
        {
            log.EnterFunction();
            try
            {
                defaultHpfBillingTierDetails = BillingAdminController.Instance.GetBillingTier(requestorTypeDetails.HpfBillingTier.Id);
                defaultNonHpfBillingTierDetails = BillingAdminController.Instance.GetBillingTier(requestorTypeDetails.NonHpfBillingTier.Id);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
        }


        /// <summary>
        /// Get the charge history details for the request, If the request is already released.
        /// </summary>
        private void UpdatePreviouslyReleasedCost()
        {
            log.EnterFunction();
            try
            {
                previouslyReleasedCost = 0.00;
                if (modeType == VaultMode.Create || !(requestDeatils.IsReleased))
                {
                    return;
                }
                Collection<ChargeHistoryDetails> chargeHistories = BillingController.Instance.RetrieveChargeHistory(requestDeatils.Id);
                foreach (ChargeHistoryDetails chargeHistory in chargeHistories)
                {
                    previouslyReleasedCost += chargeHistory.TotalReleaseCost;
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message, cause);
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Update adjustemtn and Payment toatal.
        /// </summary>
        private void UpdateAdjPayTotal()
        {
            log.EnterFunction();
            paymentTotal = 0.00;
            adjustmentTotal = 0.00;
            foreach (RequestTransaction txn in releaseDetail.RequestTransactions)
            {
                if (txn.TransactionType == TransactionType.Adjustment)
                {
                    adjustmentTotal = (txn.IsDebit) ? adjustmentTotal + txn.Amount : adjustmentTotal - txn.Amount;
                }
                else
                {
                    paymentTotal += txn.Amount;
                }
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Update the document charge details.
        /// </summary>
        private void UpdateDocumentCharge()
        {
            docChargeTotal = 0.00;
            int totalPage = 0;
            foreach (DocumentChargeDetails docCharge in releaseDetail.DocumentCharges)
            {
                documentCharge = docCharge;
                UpdateBillingTierAmount(documentCharge.Pages, documentCharge.Copies, documentCharge.BillingTierId);
                docChargeTotal = docChargeTotal + TierAmount;
                totalPage += docCharge.Pages * docCharge.Copies; 
            }
            releaseDetail.TotalPages = totalPage;
        }

        private void UpdateBillingTierAmount(int pages, int copies, long billingTierId)
        {
            if (pages == 0)
            {
                TierAmount = 0.00;
                return;
            }
            long totalpages = pages * copies;
            long totalPagesForBillingTier = (!requestDeatils.IsReleased) ? totalpages
                                                                           : documentCharge.TotalPages + totalpages;
            if (releaseDetail.IsReleased)
            {
                totalPagesForBillingTier = documentCharge.TotalPages;
            }
            long pageRange = (totalPagesForBillingTier - totalpages + 1);

            BillingTierDetails billingTier = BillingAdminController.Instance.GetBillingTier(billingTierId);

            double price = (documentCharge.RemoveBaseCharge) ? 0 : billingTier.BaseCharge;
            int pageTierCount = billingTier.PageTiers.Count;
            if (pageTierCount > 0)
            {
                foreach (PageLevelTierDetails pageTier in billingTier.PageTiers)
                {
                    if (!(pageRange >= pageTier.StartPage && pageRange <= pageTier.EndPage))
                    {
                        continue;
                    }
                    if (totalPagesForBillingTier > pageTier.EndPage)
                    {
                        price += (pageTier.PricePerPage * (pageTier.EndPage + 1 - pageRange));
                        pageRange = pageTier.EndPage + 1;
                    }
                    else
                    {
                        price += (pageTier.PricePerPage * (totalPagesForBillingTier + 1 - pageRange));
                    }
                    if (totalPagesForBillingTier >= pageTier.StartPage && totalPagesForBillingTier <= pageTier.EndPage)
                    {
                        break;
                    }
                }
                PageLevelTierDetails lastPageTier = billingTier.PageTiers[pageTierCount - 1];
                if (totalPagesForBillingTier > lastPageTier.EndPage)
                {
                    price += (billingTier.OtherPageCharge * (totalPagesForBillingTier + 1 - pageRange));
                }
                TierAmount = price;
            }
        }

        /// <summary>
        /// update fee charge details.
        /// </summary>
        private void UpdateFeeCharge()
        {
            feeChargeTotal = 0.00;
            foreach (FeeChargeDetails feeCharge in releaseDetail.FeeCharges)
            {
                feeChargeTotal = feeChargeTotal + feeCharge.Amount;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Document Charge amount
        /// </summary>
        public double TierAmount
        {
            get { return documentCharge.Amount; }
            set
            {
                documentCharge.Amount = value;
            }
        }

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.BillGeneralInfo; }
        }

        /// <summary>
        /// Return the mode type.
        /// </summary>
        public VaultMode ModeType
        {
            get
            {
                return modeType;
            }
            set
            {
                modeType = value;
            }
        }

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }

        #region ChargeDetails

        /// <summary>
        /// Get the Total Document Charge amount.
        /// </summary>
        public double DocumentChargeTotal
        {
            get
            {               
                return docChargeTotal;
            }
        }


        /// <summary>
        /// Get the total fee charge amount (Fee Charge from admin and Custom FeeCharge.)
        /// </summary>
        public double FeeChargeTotal
        {
            get
            {               
                return feeChargeTotal;
            }
        }

        /// <summary>
        /// Pending release cost
        /// </summary>
        public double PendingReleaseCost
        {
            get
            {                
                return DocumentChargeTotal + FeeChargeTotal + releaseDetail.ShippingDetails.ShippingCharge;
            }
        }

        /// <summary>
        /// Total request cost - sum of pending release cost and previously released cost
        /// </summary>
        public double TotalRequestCost
        {
            get
            {   
                return PendingReleaseCost + previouslyReleasedCost;
            }
        }

        /// <summary>
        /// Adjustment total
        /// </summary>
        public double AdjustmentTotal
        {
            get 
            {
                return adjustmentTotal; 
            }
        }

        /// <summary>
        /// Payment total
        /// </summary>
        public double PaymentTotal
        {
            get 
            {
                return paymentTotal;
            }
        }

        /// <summary>
        /// Adjutement and Payment Total sum.
        /// </summary>
        public double AdjustmentPaymentTotal
        {
            get { return -paymentTotal + adjustmentTotal; }
        }

        /// <summary>
        /// Balance due- ((TotalRequestCost - PaymentTotal) +/- AdjustmentTotal)
        /// </summary>
        public double BalanceDue
        {
            get
            {
                return TotalRequestCost + AdjustmentPaymentTotal;                
            }
        }

        #endregion

        #endregion
    }
}
