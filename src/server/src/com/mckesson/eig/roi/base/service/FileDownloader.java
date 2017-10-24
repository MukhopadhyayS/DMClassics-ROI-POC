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

package com.mckesson.eig.roi.base.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.roi.admin.dao.FileTransferHelper;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.service.BillingCoreService;
import com.mckesson.eig.roi.billing.service.OverDueInvoiceCoreService;
import com.mckesson.eig.roi.billing.service.PdfUtilities;
import com.mckesson.eig.roi.supplementary.service.ROISupplementaryService;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.roi.utils.DirectoryUtil;
import com.mckesson.eig.roi.utils.FileUtilities;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 *
 * @author OFS
 * @date   Mar 31, 2009
 * @since  HPF 13.1 [ROI]; Jan 5, 2009
 */
public class FileDownloader
extends BaseROIService
implements FileTransferHelper {

    private static final OCLogger LOG = new OCLogger(FileDownloader.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String FILENAME_DATE_FORMAT = "yyyy.MM.dd.hh.mm.ss.SSS";

    protected static final String OVERDUE_SERVICE =
        "com.mckesson.eig.roi.billing.service.OverDueInvoiceServiceImpl";

    protected static final String OVERDUE_CORE_SERVICE =
            "com.mckesson.eig.roi.billing.service.OverDueInvoiceCoreServiceImpl";

    protected static final String BILLING_CORE_SERVICE =
        "com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl";

    protected static final String SUPPLEMENTARY_SERVICE =
        "com.mckesson.eig.roi.supplementary.service.ROISupplementaryServiceImpl";
    
    private static OverDueInvoiceCoreService _overDueCoreService;
    private static BillingCoreService _billingCoreService;

    private static final String FILE_NAME = "FILE_NAME";
    private static final String SOURCE = "SOURCE";
    private static final String CHUNK_ENABLED = "CHUNKENABLED";
    private static final String FILE_IDS = "FILE_IDS";
    private static final String ATTACHMENT = "Attachment";

    /**
     * This method fetches the document for the specified docId and writes the document
     * in the cache location configured in roi.properties.
     * @see com.mckesson.eig.roi.admin.dao.FileTransferHelper#retrieve(
     * com.mckesson.eig.common.filetransfer.services.BaseFileTransferData)
     */
    @Override
    public String retrieve(BaseFileTransferData serverData) {

        final String logSM = "retrieve(serverData)";
        final String contentType = "application/octet-stream";

        String fileName = serverData.getRequestProperty(FILE_NAME);
        String source = serverData.getRequestProperty(SOURCE);
        boolean doChunk = serverData.getBooleanRequestProperty(CHUNK_ENABLED);
        String fileIds = serverData.getRequestProperty(FILE_IDS);

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start FileName: " + fileName + ",doChunk: " + doChunk);
        }

        String path = null;
        OutputStream out = null;
        String filePath = null;

        HttpServletResponse response = serverData.getResponse();
        try {

            filePath = getFilePath(fileName, source, fileIds);
            FileInputStream fis = AccessFileLoader.getFileInputStream(filePath);
            byte[] content = new byte[fis.available()];
            fis.read(content);
            fis.close();
            if (doChunk) {
                path = getCacheFileName(fileName, ROIConstants.CHUNK_CACHE_DIR);
                out = AccessFileLoader.getFileOutputStream(path);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentLength(content.length);
                response.setContentType(contentType);
                response.setHeader("FILE_SIZE", content.length + "");
                out = response.getOutputStream();
            }

            writeToStream(content, out);
            deleteFile(filePath);
        } catch (Throwable e) {

            String msg = constructErrorResponse(serverData, e.getMessage()).toString();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentLength(msg.getBytes().length);
            response.setContentType(contentType);
            LOG.error("Retrieval of document failed :" + msg, e);
            try {
                writeToStream(msg.getBytes(), response.getOutputStream());
            } catch (Throwable t) {
                LOG.error("Retrieval of document failed :" + msg, t);
            }
        } finally {
            if (doChunk && (out != null)) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOG.error("Closing the stream in method " + logSM + " failed", e);
                }
            }
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:File Path" + path);
        }
        return path;
    }

    /**
     * @param fileName
     * @param source
     * @param fileIds
     * @return
     * @throws FileNotFoundException
     */
    private String getFilePath(String fileName, String source, String fileIds)
            throws FileNotFoundException {

        String filePath;
        if ("Output".equalsIgnoreCase(source)) {
            filePath = regenerateLetters(fileIds);
        } else if (ATTACHMENT.equalsIgnoreCase(source)) {
            filePath = getAttachment(fileName);
        } else {
            filePath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
        }
        return filePath;
    }

    private String getAttachment(String filename) throws FileNotFoundException {

        ROISupplementaryService service = (ROISupplementaryService) getService(ServiceName.ROI_SUPPLEMENTARY_SERVICE);

        String path = service.retrieveAttachmentPath(filename);
        File src = null;
        File destFile = null;
        String tempfile = null;
        try {
            src = AccessFileLoader.getFile(path);

            tempfile = DirectoryUtil.getCacheDirectory() + File.separator
                    + UUID.randomUUID().toString();
            destFile = AccessFileLoader.getFile(tempfile);
            FileUtilities.copyFile(src, destFile);
        } catch (IOException e) {
            LOG.error("Exception in getAttachment");
        }
        return tempfile;
    }

    public String regenerateLetters(String fileIds) {

        _billingCoreService = (BillingCoreService) getService(ServiceName.BILLING_CORE_SERVICE);
        _overDueCoreService =
                (OverDueInvoiceCoreService) getService(ServiceName.OVERDUE_INVOICE_CORE_SERVICE);

        StringBuffer fName = new StringBuffer("OutputFile");

        if (fileIds != null) {

            String[] ids = fileIds.split(",");
            List<String> fileNames = new ArrayList<String>();

            generateFile(ids, fileNames);

            if (fileNames.size() > 1) {

                String date = formatDate(new Date(), FILENAME_DATE_FORMAT);
                PdfUtilities.concatenate(fileNames,
                                         getCacheFileName(fName.append(date)
                                                               .append(ROIConstants.OUTPUT_FILE_EXT)
                                                               .toString(),
                                                          ROIConstants.OUTPUT_CACHE_DIR));

                deleteFiles(fileNames);
            } else {
                return fileNames.get(0);
            }
        }
        return getCacheFileName(fName.toString(), ROIConstants.OUTPUT_CACHE_DIR);
    }

    /**
     * @param fName
     * @param ids
     * @param fileNames
     * @return
     */
    private String generateFile(String[] ids, List<String> fileNames) {

        final String logSM = "generateFile(ids, fileNames)";
        StringBuffer fName = new StringBuffer();
        for (String fname : ids) {
            String[] type = fname.split("\\.");

            if (DO_DEBUG) {
                LOG.debug(logSM + "<< File Type:" + type[0] + "; File Id:" + type[1]);
            }

            if (ROIConstants.LETTER_FILE.equalsIgnoreCase(type[0])) {

                String fileName = _billingCoreService.regenerateLetter(Long.parseLong(type[1]),
                                                                   ROIConstants.TEMPLATE_FILE_TYPE,
                                                                   ROIConstants.LETTER);

                String filepath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
                fileNames.add(filepath);

                fName = fName.append(ROIConstants.LETTER_FILE).append(".").append(type[1]);

            }

            if (ROIConstants.INVOICE_FILE.equalsIgnoreCase(type[0])) {

                String fileName = _billingCoreService.regenerateLetter(Long.parseLong(type[1]),
                                                                   ROIConstants.TEMPLATE_FILE_TYPE,
                                                                   ROIConstants.INVOICE);

                String filepath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
                fileNames.add(filepath);

                fName = fName.append(ROIConstants.INVOICE_FILE).append(".").append(type[1]);
            }

            if (ROIConstants.PREBILL_FILE.equalsIgnoreCase(type[0])) {

                String fileName = _billingCoreService.regenerateLetter(Long.parseLong(type[1]),
                                                                   ROIConstants.TEMPLATE_FILE_TYPE,
                                                                   ROIConstants.PREBILL);

                String filepath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
                fileNames.add(filepath);

                fName = fName.append(ROIConstants.PREBILL_FILE).append(".").append(type[1]);
            }

            if (ROIConstants.REQUESTOR_LETTER_FILE.equalsIgnoreCase(type[0])) {


                String fileName = _overDueCoreService.regenerateLetter(Long.parseLong(type[1]),
                                                                      ROIConstants.TEMPLATE_FILE_TYPE,
                                                                      ROIConstants.REQUESTORLETTER);

                String filepath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
                fileNames.add(filepath);

                fName = fName.append(ROIConstants.REQUESTOR_LETTER_FILE).append(".")
                        .append(type[1]);
            }
            if (ROIConstants.OVERDUE_INVOICE_FILE.equalsIgnoreCase(type[0])) {

                String fileName = _billingCoreService.regenerateLetter(Long.parseLong(type[1]),
                                             ROIConstants.TEMPLATE_FILE_TYPE,
                                             ROIConstants.INVOICE);

                String filepath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
                fileNames.add(filepath);

                fName = fName.append(ROIConstants.OVERDUE_INVOICE_FILE)
                        .append(".").append(type[1]);
            }
            if (ROIConstants.PAST_INVOICE_FILE.equalsIgnoreCase(type[0])
                    || ROIConstants.REGENERATED_INVOICE_FILE.equalsIgnoreCase(type[0])) {

                String fileName = _billingCoreService.regenerateLetter(Long.parseLong(type[1]),
                                             ROIConstants.TEMPLATE_FILE_TYPE,
                                             ROIConstants.INVOICE);

                String filepath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
                fileNames.add(filepath);

                fName = fName.append(ROIConstants.PAST_INVOICE_FILE)
                             .append(".")
                             .append(type[1]);
            }

            if (ROIConstants.REQUESTOR_REFUND_FILE.equalsIgnoreCase(type[0])) {

                String fileName = _overDueCoreService.regenerateLetter(Long.parseLong(type[1]),
                                                                     ROIConstants.TEMPLATE_FILE_TYPE,
                                                                     ROIConstants.REQUESTORREFUND);

                String filepath = getCacheFileName(fileName, ROIConstants.OUTPUT_CACHE_DIR);
                fileNames.add(filepath);

                fName = fName.append(ROIConstants.REQUESTOR_REFUND_FILE)
                        .append(".")
                        .append(type[1]);
            }
        }

        return fName.toString();
    }

    public void deleteFile(String filePath) throws IOException {

        File file = AccessFileLoader.getFile(filePath);
        file.delete();
    }

    /**
     * This method deletes the given list of files
     * @param fileNames
     * @throws IOException 
     */
    private void deleteFiles(List<String> fileNames) {
        try {
            if ((fileNames != null) && (fileNames.size() > 0)) {
                for (String name : fileNames) {
                    File f = AccessFileLoader.getFile(name);
                    f.delete();
                }
            }
        } catch(IOException e) {
                LOG.error("Exception in deleteFiles");
        }
    }

    /**
     * This method used to retrieves the file from the cache
     * @param fileName name of the filename
     * @return
     */
    private String getCacheFileName(String fileName, String subDirName) {

        final String logSM = "getCacheFileName(fileName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:FileName : " + fileName);
        }
        
        ROIDAO dao = (ROIDAO) getDAO(DAOName.ATTACHMENT_DAO);
        String cacheDir = dao.retrieveEIWDATAConfiguration(ROIConstants.TEMP_DIRECTORY_LOCATION_KEY)
                                                    + File.separatorChar + subDirName;        
        boolean dirCreated = false;
        try {
            File f = AccessFileLoader.getFile(cacheDir);
            dirCreated = f.mkdir();
        } catch (IOException e) {
                 LOG.error("Exception in getCacheFileName");
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Cache dir created " + dirCreated);
        }
        return cacheDir + File.separator + fileName;
    }

    /**
     * This method writes the uploaded document into outputStream
     * @param doc document to write in outputStream
     * @param os outputStream
     * @param serverData BaseFileTransferData
     * @throws IOException
     */
    private void writeToStream(byte[] doc, OutputStream os)
    throws IOException {

        final String logSM = "writeToStream(doc, os, serverData)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        try {

            BufferedOutputStream output = new BufferedOutputStream(os);
            output.write(doc);
            output.flush();
        } catch (IOException e) {
            if (DO_DEBUG) {
                LOG.error("Writing to stream in method " + logSM + " failed", e);
            }
            throw e;
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }
    }

    /**
     * This method construct the errorcode
     * @param serverData BaseFileTransferData
     * @return error code in string buffer
     */
    private StringBuffer constructErrorResponse(BaseFileTransferData serverData, String errMsg) {

        final String logSM = "constructErrorResponse(serverData)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        return new StringBuffer("<FileDownloadResponse><command>BaseFileDownloader</command>")
                                 .append("<returncode>400</returncode>")
                                 .append("<returnmessage>")
                                 .append(errMsg)
                                 .append("</returnmessage><SESSION_ID>")
                                 .append(serverData.getRequest().getSession())
                                 .append("</SESSION_ID></FileDownloadResponse>");

    }

    @Override
    public boolean isValidFileName(String fileName) {

        return (fileName != null) && fileName.endsWith(ROIConstants.LETTER_TEMPLATE_DOC_TYPE);
    }


    @Override
    public void completeUpload(BaseFileTransferData serverData,
                               InputStream ins,
                               int available,
                               StringBuffer responsexml) {

        throw new UnsupportedOperationException("FileDownloader.completeUpload()");
    }

    private String formatDate(Date date, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

}
