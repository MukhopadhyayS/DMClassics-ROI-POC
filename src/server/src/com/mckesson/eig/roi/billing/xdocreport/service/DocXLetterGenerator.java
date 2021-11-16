/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.roi.billing.xdocreport.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.LetterGenerator;
import com.mckesson.eig.roi.billing.letter.model.Charge;
import com.mckesson.eig.roi.billing.letter.model.ChargeItem;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.letter.model.Note;
import com.mckesson.eig.roi.billing.letter.model.ReleaseInfo;
import com.mckesson.eig.roi.billing.letter.model.RequestItem;
import com.mckesson.eig.roi.billing.letter.model.RequestorInfo;
import com.mckesson.eig.roi.billing.letter.model.RequestorTransaction;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocCharge;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocChargeItem;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocInvoiceCharge;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocNote;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocReleaseInfo;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocRequestItem;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocRequestorAccount;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocRequestorAging;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocRequestorInfo;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocRequestorTransaction;
import com.mckesson.eig.roi.billing.xdocreport.model.XDocShippingInfo;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.utility.util.CollectionUtilities;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.ITemplateEngine;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.velocity.internal.VelocityTemplateEngine;

/**
 * This class is used to merge .docx template dynamically and 
 * also used to convert .docx files to .pdf file 
 * @author Karthik Eswaran (OFS)
 * @author Nattarshah Mohammed
 */
public class DocXLetterGenerator 
implements LetterGenerator {


    /**
     * @see com.mckesson.eig.roi.base.service.LetterGenerator#generateLetter(java.lang.Object, java.lang.Object, java.lang.String)
     */
    @Override
    public void generateLetter(Object data, Object template, String ouputFile) {
        
        try {
            
            //gets the input stream of the template
            LetterTemplateDocument doc = (LetterTemplateDocument) template;
            InputStream templateStream = new ByteArrayInputStream(doc.getDocument());
            
            //Loading the XdocReport through velocity template engine
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(templateStream, TemplateEngineKind.Velocity );
            
            Properties properties = new Properties();
            properties.setProperty("resource.loader", "class");
            properties.setProperty(
                    "class.resource.loader.class",               "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            ITemplateEngine templateEngine = new VelocityTemplateEngine(properties);
            report.setTemplateEngine(templateEngine);
            
            IContext context = report.createContext();
            FieldsMetadata fieldsMetadata = mapDataToContext(context, data);
            fieldsMetadata.setTemplateEngineKind("Velocity");
            report.setFieldsMetadata(fieldsMetadata);
            
            final String mergedDocxFile = ouputFile.substring(0, ouputFile.lastIndexOf(".")) + ".docx";
            OutputStream out = AccessFileLoader.getFileOutputStream(mergedDocxFile);
            report.process(context, out);
            
            convertToPDF(ouputFile, mergedDocxFile);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ROIException(e, ROIClientErrorCodes.UNABLE_TO_GENERATE_DOCX);
        }
    }

    /**
     * converts the given file to PDF format and copy the file to given location 
     * @param ouputFile
     * @param mergedDocxFile
     */
    public static void convertToPDF(String ouputFile, final String mergedDocxFile) {
        
        try {
            
            //Converting the changed .docx file to the pdf file using iText and and xdocReport
            FileInputStream mergedFileStream = AccessFileLoader.getFileInputStream(AccessFileLoader.getFile(mergedDocxFile));
            XWPFDocument document = new XWPFDocument(mergedFileStream);            
            OutputStream outStream = AccessFileLoader.getFileOutputStream(ouputFile);
            PdfOptions options = PdfOptions.create().fontEncoding( "windows-1250" );
            PdfConverter.getInstance().convert( document, outStream, options );
            
        } catch (Exception e) {
            tryAspose(mergedDocxFile, ouputFile);
        } catch (Throwable e) {
            tryAspose(mergedDocxFile, ouputFile);
        }
    }
    
    private static void tryAspose(String mergedDocxFile, String ouputFile) {
        try {
            com.aspose.words.License license = new  com.aspose.words.License();
            license.setLicense("Aspose.Total.Java.lic");
            Document doc = new Document(mergedDocxFile);
            doc.save(ouputFile, SaveFormat.PDF);
        } catch (Exception e) {
            throw new ROIException(ROIClientErrorCodes.UNABLE_TO_CONVERT_TO_PDF);
        }
    }

    /**
     * map the given letterData model to the report context and returns the fields metadata
     * @param context
     * @param data
     */
    private FieldsMetadata mapDataToContext(IContext ctx, Object data) {

        ctx.put("letter", data);
        LetterData letter = (LetterData) data;         

        FieldsMetadata metadata = new FieldsMetadata();
        
        RequestorInfo letterRequestor = letter.getRequestor();
        ctx.put("letterRequestor", new XDocRequestorInfo(letterRequestor));
        ctx.put("letterRequestorAging", getRequestorAgingBalance(letterRequestor));
        ctx.put("letterRequestorAccount", getRequestorAccountBalance(letterRequestor));
        ctx.put("letterInvoiceCharge", new XDocInvoiceCharge(letter.getInvoiceCharge()));
        ctx.put("letterShippingInfo", new XDocShippingInfo(letter.getBillingInfo() == null
                                        ? null : letter.getBillingInfo().getShippingInfo()));
        
        ctx.put("letterRequestorTxns", getRequestorTransactions(letterRequestor));
        metadata.addFieldAsList("letterRequestorTxns");
        
        ctx.put("letterReleases", getReleaseInfo(letter));
        metadata.addFieldAsList("letterReleases");
        
        ctx.put("letterDocChargeDetails", getXDocChargeItems(letter.getDocChargeDetails()));
        metadata.addFieldAsList("letterDocChargeDetails");
        
        ctx.put("letterFeeChargeDetails", getXDocChargeItems(letter.getFeeChargeDetails()));
        metadata.addFieldAsList("letterFeeChargeDetails");
        
        ctx.put("letterShipCharges", getXDocCharges(letter.getShipCharges()));
        metadata.addFieldAsList("letterShipCharges");
        
        ctx.put("letterTxnCharges", getXDocCharges(letter.getTxnCharges()));
        metadata.addFieldAsList("letterTxnCharges");
        
        ctx.put("letterTxns", getXDocChargeItems(letter.getTxns()));
        metadata.addFieldAsList("letterTxns");

        ctx.put("letterPayment", getXDocChargeItems(letter.getPayment()));
        metadata.addFieldAsList("letterPayment");
        
        ctx.put("letterCreditAdjustment", getXDocChargeItems(letter.getCreditAdjustment()));
        metadata.addFieldAsList("letterCreditAdjustment");

        ctx.put("letterDebitAdjustment", getXDocChargeItems(letter.getDebitAdjustment()));
        metadata.addFieldAsList("letterDebitAdjustment");
        
        ctx.put("letterDocCharges", getXDocCharges(letter.getDocCharges()));
        metadata.addFieldAsList("letterDocCharges");
        
        ctx.put("letterFeeCharges", getXDocCharges(letter.getFeeCharges()));
        metadata.addFieldAsList("letterFeeCharges");
        
        ctx.put("letterPatients", getPatients(letter));
        metadata.addFieldAsList("letterPatients");
        
        ctx.put("letterNotes", getXDocNotes(letter.getNotes()));
        metadata.addFieldAsList("letterNotes");
        
        ctx.put("letterReasons", getXDocNotes(letter.getReasons()));
        metadata.addFieldAsList("letterReasons");
        
        return metadata;
    }
    
    /**
     * constructs the requestor model for the given requestor info
     * @param requestor
     * @return
     */
    private List<XDocRequestorTransaction> getRequestorTransactions(RequestorInfo requestor) {
        
        List<XDocRequestorTransaction> requestorList = new ArrayList<XDocRequestorTransaction>();
        if (requestor != null && !CollectionUtilities.isEmpty(requestor.getTransactions())) {
            
            for (RequestorTransaction transaction : requestor.getTransactions()) {
                requestorList.add(new XDocRequestorTransaction(transaction));
            }
            
        } else {
            requestorList.add(new XDocRequestorTransaction(null));
        }
        return requestorList;
    }

    /**
     * constructs the requestor model for the given requestor info
     * @param requestor
     * @return
     */
    private XDocRequestorAging getRequestorAgingBalance(RequestorInfo requestor) {
        
        XDocRequestorAging xdocAging;
        if (requestor != null && null != requestor.getAging()) {
            xdocAging = new XDocRequestorAging(requestor.getAging());
        } else {
            xdocAging = new XDocRequestorAging(null);
        }
        return xdocAging;
    }

    /**
     * constructs the requestor model for the given requestor info
     * @param requestor
     * @return
     */
    private XDocRequestorAccount getRequestorAccountBalance(RequestorInfo requestor) {
        
        XDocRequestorAccount xdocAccount;
        if (requestor != null && null != requestor.getAging()) {
            xdocAccount = new XDocRequestorAccount(requestor.getAccount());
        } else {
            xdocAccount = new XDocRequestorAccount(null);
        }
        return xdocAccount;
    }
    
    /**
     * get the xdocreleaseInfo model for the given letterdata model
     * @param letter
     * @return
     */
    private List<XDocReleaseInfo> getReleaseInfo(LetterData letter) {
        
        List<XDocReleaseInfo> releaseInformation = new ArrayList<XDocReleaseInfo>();
        if (CollectionUtilities.isEmpty(letter.getReleases())) {
            releaseInformation.add(new XDocReleaseInfo(null));
            return releaseInformation;
        }
        for (ReleaseInfo releaseInfo : letter.getReleases()) {
            releaseInformation.add(new XDocReleaseInfo(releaseInfo));
        }
        return releaseInformation;
    }
    
    /**
     * gets the list of XDOc charge items for the given charge items 
     * @param letter
     * @return
     */
    private List<XDocChargeItem> getXDocChargeItems(List<ChargeItem> chargeItems) {
        
        List<XDocChargeItem> xdocChargeItems = new ArrayList<XDocChargeItem>();
        if (CollectionUtilities.isEmpty(chargeItems)) {
            xdocChargeItems.add(new XDocChargeItem(null));
            return xdocChargeItems;
        }
        
        for (ChargeItem chargeItem : chargeItems) {
            xdocChargeItems.add(new XDocChargeItem(chargeItem));
        }
        return xdocChargeItems;        
    }
    
    /**
     * gets the XDoc charges for the fiven charges model
     * @param charges
     * @return
     */
    private List<XDocCharge> getXDocCharges(List<Charge> charges) {

        List<XDocCharge> xdocCharges = new ArrayList<XDocCharge>();
        if (CollectionUtilities.isEmpty(charges)) {
            xdocCharges.add(new XDocCharge());
            return xdocCharges;
        }
        
        for (Charge charge : charges) {
            xdocCharges.add(new XDocCharge(charge));
        }
        return xdocCharges; 
    }
    
    /**
     * gets the XDOC Patients model for the given letterdata patients
     * @param letter
     * @return
     */
    private List<XDocRequestItem> getPatients(LetterData letter) {
        
        List<XDocRequestItem> patients = new ArrayList<XDocRequestItem>();
        if (CollectionUtilities.isEmpty(letter.getPatients())) {
            patients.add(new XDocRequestItem(null));
            return patients;
        }
        for (RequestItem patient : letter.getPatients()) {
            patients.add(new XDocRequestItem(patient));
        }
        return patients;   
        
    }
    
    /**
     * gets the XDOc Notes model for the given Note model
     * @param notes
     * @return
     */
    private List<XDocNote> getXDocNotes(List<Note> notes) {
        
        List<XDocNote> xdocNotes = new ArrayList<XDocNote>();
        if (CollectionUtilities.isEmpty(notes)) {
            xdocNotes.add(new XDocNote(null));
            return xdocNotes;
        }
        for (Note note : notes) {
            xdocNotes.add(new XDocNote(note));
        }
        return xdocNotes;   
        
    }
}
