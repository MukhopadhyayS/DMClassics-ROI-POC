/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.dao.BaseLetterDataRetriever;
import com.mckesson.eig.roi.billing.letter.model.BillingInfo;
import com.mckesson.eig.roi.billing.letter.model.Charge;
import com.mckesson.eig.roi.billing.letter.model.ChargeItem;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.letter.model.Note;
import com.mckesson.eig.roi.billing.letter.model.ReleaseInfo;
import com.mckesson.eig.roi.billing.letter.model.RequestItem;
import com.mckesson.eig.roi.billing.letter.model.RequestorInfo;
import com.mckesson.eig.roi.billing.letter.model.ShippingInfo;
import com.mckesson.eig.roi.billing.model.CoverLetterCore;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 *
 * @author OFS
 * @date   Mar 30, 2009
 * @since  HPF 13.1 [ROI]; Mar 30, 2009
 */
public class ROILetterDataRetriever
extends BaseLetterDataRetriever {
    protected static final String BILL_DATE_FORMAT = "MM/dd/yyyy";

    /**
     * @see com.mckesson.eig.roi.base.dao.BaseLetterDataRetriever#retrieveLetterData(long, long)
     */
    @Override
    public Object retrieveLetterData(long coverLetterId, long requestId) {

        CoverLetterCore coverLetter = retrieveCoverLetterDetails(coverLetterId);
        Object templateData = constructTemplateDataModel(coverLetter);
        return templateData;

    }

    /**
     * retrieves the coverletter for the give coverletterId
     * @param coverLetterId
     * @return
     */
    private CoverLetterCore retrieveCoverLetterDetails(long coverLetterId) {

        rcDeliveryDAO = getRequestCoreDeliveryDAO();
        rcChargesDAO = getRequestCoreChargesDAO();

        CoverLetterCore coverLetter = rcDeliveryDAO.retrieveLetterCore(coverLetterId);
        long requestId = coverLetter.getRequestId();

        // Retrieve data from RequestCoreCharges and Shipping Table
        RequestCoreCharges rcCharges = rcChargesDAO.retrieveRequestCoreBillingPaymentInfo(requestId);
        if (null != rcCharges) {
            coverLetter.setChargesDetails(rcCharges);
            RequestCoreChargesShipping rcChargesShipping =
                rcChargesDAO.retrieveRequestCoreChargesShipping(rcCharges.getId());
            coverLetter.setShippingDetails(rcChargesShipping);
        }
//        rcPatientDAO = getRequestCorePatientDAO();
        // Retrieve all patients for the request
//        List<RequestPatient> patients = rcPatientDAO.retrieveAllPatientForRequest(requestId);

        requestCoreDAO = getRequestCoreDAO();
        // Retrieve the Requestor Information for the Request
        RequestorCore requestor = requestCoreDAO.retrieveRequestor(requestId);
        coverLetter.setRequestor(requestor);

        return coverLetter;

    }

    /**
     * @see com.mckesson.eig.roi.base.dao.LetterDataRetriever
     * #constructTemplateDataModel(java.lang.Object)
     *
     */
    @Override
    public Object constructTemplateDataModel(Object persistentModel) {

        CoverLetterCore coverLetter = (CoverLetterCore) persistentModel;

        //Setting all the values to LetterData Object.
        LetterData letterData = new LetterData();
        List<RequestPatient> hpfPatients = coverLetter.getRequestHpfPatients();
        List<RequestPatient> supplementalPatients = coverLetter.getRequestSupplementalPatients();

        List<RequestPatient> patients = new ArrayList<RequestPatient>();
        if (null != hpfPatients && !hpfPatients.isEmpty()) {
            patients.addAll(hpfPatients);
        }

        if (null != supplementalPatients && !supplementalPatients.isEmpty()) {
            patients.addAll(supplementalPatients);
        }

        List<RequestItem> masterPatientsList = new ArrayList<RequestItem>();
        RequestItem reqItm = new RequestItem();
        List<RequestItem> patientsList = new ArrayList<RequestItem>();
        for(RequestPatient patient : patients){
            RequestItem reqItem = new RequestItem();
            reqItem.setEpn(patient.getEpn());
            reqItem.setFacility(patient.getFacility());
            reqItem.setMrn(patient.getMrn());
            reqItem.setName(patient.getName());
            reqItem.setSsn(patient.getSsn());
            reqItem.setDob(patient.getDob());
            reqItem.setEncounterFacility(patient.getFacility());
            patientsList.add(reqItem);
        }
        reqItm.setRequestItems(patientsList);
        masterPatientsList.add(reqItm);
        letterData.setPatientsList(masterPatientsList);

        RequestorInfo reqInfo = new RequestorInfo();

        RequestorCore requestor = coverLetter.getRequestor();
        if (null != requestor) {

            reqInfo.setId(String.valueOf(requestor.getRequestorSeq()));

            String reqName = requestor.getLastName()
                    + (StringUtilities.isEmpty(requestor.getMiddleName())
                                        ? ""
                                        : " " + requestor.getMiddleName())
                    + (StringUtilities.isEmpty(requestor.getFirstName())
                                        ? ""
                                        : ", " + requestor.getFirstName());
            reqInfo.setName(reqName);
            reqInfo.setPhone(requestor.getWorkPhone());
            reqInfo.setType(requestor.getRequestorTypeName());
            reqInfo.setAddress1(requestor.getMainAddress1());
            reqInfo.setAddress2(requestor.getMainAddress2());
            reqInfo.setAddress3(requestor.getMainAddress3());
            reqInfo.setCity(requestor.getMainCity());
            reqInfo.setState(requestor.getMainState());
            reqInfo.setPostalCode(requestor.getMainPostalCode());
            reqInfo.setCountry(requestor.getMainCountryName());
            reqInfo.setCountryCode(requestor.getMainCountryCode());
        }
        letterData.setRequestor(reqInfo);

        ShippingInfo shippingInfo = new ShippingInfo();
        RequestCoreChargesShipping rcChargesShipping = coverLetter.getShippingDetails();
        if (null != rcChargesShipping) {

            shippingInfo.setAddress1(rcChargesShipping.getAddress1());
            shippingInfo.setAddress2(rcChargesShipping.getAddress2());
            shippingInfo.setAddress3(rcChargesShipping.getAddress3());
            shippingInfo.setAddressType(rcChargesShipping.getAddressType());
            shippingInfo.setCity(rcChargesShipping.getCity());
            shippingInfo.setCountryCode(rcChargesShipping.getCountryCode());
            shippingInfo.setCountryName(rcChargesShipping.getCountryName());
            shippingInfo.setOutputMethod(rcChargesShipping.getOutputMethod());
            shippingInfo.setPostalCode(rcChargesShipping.getPostalCode());
            shippingInfo.setShippingMethod(rcChargesShipping.getShippingMethod());
            shippingInfo.setShippingURL(rcChargesShipping.getShippingUrl());
            shippingInfo.setShippingWt(String.valueOf(rcChargesShipping.getShippingWeight()));
            shippingInfo.setState(rcChargesShipping.getState());
            shippingInfo.setTrackingNumber(rcChargesShipping.getTrackingNumber());
        } else {
            // If request is in logged state, then set the details from requestor object into shippingInfo Object
            if (null != requestor) {
                shippingInfo.setAddressType(requestor.getRequestorTypeName());
                shippingInfo.setAddress1(requestor.getMainAddress1());
                shippingInfo.setAddress2(requestor.getMainAddress2());
                shippingInfo.setAddress3(requestor.getMainAddress3());
                shippingInfo.setCity(requestor.getMainCity());
                shippingInfo.setState(requestor.getMainState());
                shippingInfo.setPostalCode(requestor.getMainPostalCode());
                shippingInfo.setCountryName(requestor.getMainCountryName());
                shippingInfo.setCountryCode(requestor.getMainCountryCode());
            }
        }


        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setShippingInfo(shippingInfo);
        letterData.setTemplateFileId(String.valueOf(coverLetter.getLetterTemplateId()));
        letterData.setInvoicePrebillDate(formatDate(requestCoreDAO.getDate(), BILL_DATE_FORMAT));
        letterData.setInvoiceId(String.valueOf(coverLetter.getRequestCoreChargesId()));
        letterData.setRequestId(String.valueOf(coverLetter.getRequestId()));
        
        RequestCoreCharges chargesDetails = coverLetter.getChargesDetails();
        List<ReleaseInfo> relInfoList = new ArrayList<ReleaseInfo>();
        ReleaseInfo relInfo = new ReleaseInfo();
        if(null != chargesDetails) {
            relInfo.setBalanceDue(formatToCurrency(chargesDetails.getInvoicesBalance()));
            relInfo.setPreviouslyReleasedCost(formatToCurrency(chargesDetails.getPreviouslyReleasedCost()));
            relInfo.setReleaseCost(formatToCurrency(chargesDetails.getReleaseCost()));
            Date releaseDate = chargesDetails.getReleaseDate();
            relInfo.setReleaseDt((null == releaseDate) ? null : String.valueOf(releaseDate));
            relInfo.setTotalPagesReleased(String.valueOf(chargesDetails.getTotalPagesReleased()));
            relInfo.setTotPages(String.valueOf(chargesDetails.getTotalPages()));
            relInfo.setTotalCost(formatToCurrency(chargesDetails.getTotalRequestCost()));
            relInfoList.add(relInfo);
        } 
        List<Charge> chargeList = new ArrayList<Charge>();
        Charge charge = new Charge();
        charge.setReleases(relInfoList);
        
        //Document and Fee
        List<Charge> docChargeList = new ArrayList<Charge>();
        List<Charge> feeChargeList = new ArrayList<Charge>();
        List<ChargeItem> docItemsList = new ArrayList<ChargeItem>();
        Charge docCharge = new Charge();
        RequestCoreChargesBilling requestCoreChargesBilling = coverLetter.getChargesDetails().getRequestCoreChargesBilling();
        
        if (null != requestCoreChargesBilling) {
            Set<RequestCoreChargesDocument> rcdChargesDocument = requestCoreChargesBilling.getRequestCoreChargesDocument();
            if (null != rcdChargesDocument) {
    
                Iterator<RequestCoreChargesDocument> it = rcdChargesDocument.iterator();
                while (it.hasNext()) {
                    RequestCoreChargesDocument rcdcDocument = it.next();
                    ChargeItem dcItem = new ChargeItem();
                    dcItem.setAmount(formatToCurrency(rcdcDocument.getAmount()));
                    dcItem.setCopies(String.valueOf(rcdcDocument.getCopies()));
                    dcItem.setName(rcdcDocument.getBillingTierName());
                    dcItem.setBillingTierName(rcdcDocument.getBillingTierName());
                    dcItem.setPages(String.valueOf(rcdcDocument.getPages()));
                    dcItem.setBillingTierId(rcdcDocument.getBillingtierId());
                    docItemsList.add(dcItem);
                }
            }
            docCharge.setChargeItems(docItemsList);
            docChargeList.add(docCharge);
    
            //Fee
            List<ChargeItem> feeItemsList = new ArrayList<ChargeItem>();
            Charge feeCharge = new Charge();
            Set<RequestCoreChargesFee> rcdChargesFee = requestCoreChargesBilling.getRequestCoreChargesFee();
            if (null != rcdChargesFee) {
    
                Iterator<RequestCoreChargesFee> it = rcdChargesFee.iterator();
                while (it.hasNext()) {
                    RequestCoreChargesFee rcdcFee = it.next();
                    ChargeItem fcItem = new ChargeItem();
                    fcItem.setAmount(formatToCurrency(rcdcFee.getAmount()));
                    fcItem.setName(rcdcFee.getFeeType());
                    fcItem.setFeeType(rcdcFee.getFeeType());
                    feeItemsList.add(fcItem);
                }
            }
            feeCharge.setChargeItems(feeItemsList);
            feeChargeList.add(feeCharge);
        }
        RequestCoreChargesShipping requestCoreChargesShipping = coverLetter.getChargesDetails().getRequestCoreChargesShipping();
        //Ship
        List<Charge> shipChargeList = new ArrayList<Charge>();
        List<ChargeItem> sxnItemsList = new ArrayList<ChargeItem>();
        Charge shipCharge = new Charge();

        ChargeItem scItem = new ChargeItem();
        scItem.setAmount((null == requestCoreChargesShipping) ? "0.00"
                                : formatToCurrency(requestCoreChargesShipping.getShippingCharge()));
        sxnItemsList.add(scItem);
        shipCharge.setCharges((null == requestCoreChargesShipping) ? "0.00"
                                : formatToCurrency(requestCoreChargesShipping.getShippingCharge()));

        shipCharge.setChargeItems(sxnItemsList);

        shipChargeList.add(shipCharge);

        charge.setDocCharge(docChargeList);
        charge.setFeeCharge(feeChargeList);
        charge.setShippingCharge(shipChargeList);
        chargeList.add(charge);
        billingInfo.setCharges(chargeList);
        letterData.setBillingInfo(billingInfo);
        
        String notestring = coverLetter.getNotes();
        if (StringUtilities.hasContent(notestring)) {

            String[] notes = notestring.split(ROIConstants.FIELD_SEPERATOR);
            List<Note> noteList = new ArrayList<Note>();
            for (String noteDesc : notes) {

                Note note = new Note();
                note.setDescription(noteDesc);
                noteList.add(note);
            }
            Note notesTemp = new Note();
            notesTemp.setChildItems(noteList);
            List<Note> notesList = new ArrayList<Note>();
            notesList.add(notesTemp);

            letterData.setNotesList(notesList);
        }
        
        return letterData;
    }

}
