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

import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.LetterGenerator;
import com.mckesson.eig.roi.utils.AccessFileLoader;

import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

/**
 * This method is used to merge .odt template file dynamically and 
 * also it converts thedynamically changed .odt file to pdf file
 * @author Karthik Eswaran (OFS)
 * @author Nattarshah Mohammed
 */
public class ODTLetterGenerator 
implements LetterGenerator {


    /**
     * @see com.mckesson.eig.roi.base.service.LetterGenerator#generateLetter(java.lang.Object, java.lang.Object, java.lang.String)
     */
    @Override
    public void generateLetter(Object data, Object template, String ouputFile) {

        try {
            
            final String mergedOdtFile = ouputFile.substring(0, ouputFile.lastIndexOf(".")) + ".odt";
            
            //gets the input stream of the template
            LetterTemplateDocument doc = (LetterTemplateDocument) template;
            InputStream templateStream = new ByteArrayInputStream(doc.getDocument());
            
            //Loading the XdocReport through velocity template engine
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(templateStream, TemplateEngineKind.Velocity );
            IContext context = report.createContext();
            context.put("letter", data );
            
            OutputStream out = AccessFileLoader.getFileOutputStream(mergedOdtFile);
            report.process(context, out);
            
            //Converting the changed .odt file to the .pdf file using iText and and xdocReport
            FileInputStream mergedFileStream = AccessFileLoader.getFileInputStream(mergedOdtFile);
            OdfTextDocument document = OdfTextDocument.loadDocument(mergedFileStream);
            
            OutputStream outStream = AccessFileLoader.getFileOutputStream(ouputFile);
            PdfOptions options = PdfOptions.create().fontEncoding("windows-1250");
            PdfConverter.getInstance().convert(document, outStream, options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ROIException(e, ROIClientErrorCodes.UNABLE_TO_GENERATE_ODT);
        }
    }

}
