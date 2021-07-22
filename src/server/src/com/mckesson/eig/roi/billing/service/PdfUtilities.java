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

package com.mckesson.eig.roi.billing.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.utils.AccessFileLoader;



/**
 * @author OFS
 * @date   May 20, 2009
 * @since  HPF 13.1 [ROI]; Feb 20, 2009
 */
public final class PdfUtilities {

    private PdfUtilities() {
    }

    @SuppressWarnings("unchecked") // Not supported by 3rdParty API
    public static void concatenate(List<String> files, String outputFile) {

        try {
            int pageOffset = 0;

            @SuppressWarnings("rawtypes")
            ArrayList bookmarkMasterList = new ArrayList();

            int f = 0;
            Document document = null;
            PdfCopy writer = null;
            while (f < files.size()) {
                PdfReader reader = new PdfReader(files.get(f));
                
                if (reader.isEncrypted()) {
                    reader = new PdfReader(files.get(f), 
                                           ROIConstants.PDF_ENCRYPTION_PD.getBytes());
                }
                reader.consolidateNamedDestinations();
                int n = reader.getNumberOfPages();

                @SuppressWarnings("rawtypes")
                List bookmarks = SimpleBookmark.getBookmark(reader);
                if (bookmarks != null) {
                    if (pageOffset != 0) {
                        SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset,
                                null);
                    }
                    bookmarkMasterList.addAll(bookmarks);
                }
                pageOffset += n;

                if (f == 0) {
                    document = new Document(reader.getPageSizeWithRotation(1));
                    writer = new PdfCopy(document, AccessFileLoader.getFileOutputStream(outputFile));
                    document.open();
                }
                PdfImportedPage page;
                for (int i = 0; i < n;) {
                    ++i;
                    page = writer.getImportedPage(reader, i);
                    writer.addPage(page);
                }
                reader.close();
                f++;
            }
            if (!bookmarkMasterList.isEmpty()) {
                writer.setOutlines(bookmarkMasterList);
            }
            document.close();
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.UNABLE_TO_CONCATENATE_PDF);
        }
    }

    public static void encrypt(String fileName) {

        try {

            String tmpName = fileName.substring(0, fileName.lastIndexOf(".")) + ".temp.pdf";
            File inFile = AccessFileLoader.getFile(fileName);
            File tmpFile = AccessFileLoader.getFile(tmpName);

            PdfReader reader = new PdfReader(fileName);
            FileOutputStream os = AccessFileLoader.getFileOutputStream(tmpFile);

            PdfEncryptor.encrypt(reader,
                                 os,
                                 false,
                                 null,
                                 ROIConstants.PDF_ENCRYPTION_PD,
                                 PdfWriter.ALLOW_SCREENREADERS);

            reader.setViewerPreferences(PdfWriter.HideMenubar);
            reader.setViewerPreferences(PdfWriter.HideToolbar);
            reader.close();
            inFile.delete();
            tmpFile.renameTo(inFile);

        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.UNABLE_TO_ENCRYPT_PDF);
        }
    }

}
