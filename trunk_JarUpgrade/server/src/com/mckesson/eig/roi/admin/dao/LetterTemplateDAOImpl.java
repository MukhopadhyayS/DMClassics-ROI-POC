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

package com.mckesson.eig.roi.admin.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;

import org.apache.axis.utils.ByteArrayOutputStream;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.hibernate.Session;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.dm.core.common.util.sanitize.EncoderUtilities;
import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.admin.model.LetterTemplateFile;
import com.mckesson.eig.roi.admin.model.LetterTemplateList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.roi.utils.DirectoryUtil;


/**
 * @author OFS
 * @date   Jul 02, 2009
 * @since  HPF 13.1 [ROI]; May 16, 2008
 */
public class LetterTemplateDAOImpl
extends ROIDAOImpl
implements LetterTemplateDAO, FileTransferHelper {

    private static final OCLogger LOG = new OCLogger(LetterTemplateDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String DOC_ID = "DOC_ID";
    private static final String FILE_NAME = "FILE_NAME";
    private static final String CHUNK_ENABLED = "CHUNKENABLED";
    private static final String USER_ID = "USER_ID";
    private static final String NOTES_FIELD = "$letterNotes";
    private static final String CHECKIN_ID_KEY = "CHECKIN_ID";
    private static final String RETURN_MESSAGE = "RETURN_MESSAGE";
    private static final String SESSION_ID =  "SESSION_ID";

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO
     * #createLetterTemplate(com.mckesson.eig.roi.admin.model.LetterTemplate)
     */
    public long createLetterTemplate(LetterTemplate lt) {

        final String logSM = "createLetterTemplate(lt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + lt);
        }

        long id = toPlong((Long) create(lt));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }
        return id;
    }

    /**
     * This method fetches the letterTemplate
     * @param id Id of the letterTemplate
     * @return letterTemplate
     */
    public LetterTemplate retrieveLetterTemplate(long id) {

        final String logSM = "retrieveLetterTemplate(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        LetterTemplate lt = (LetterTemplate) get(LetterTemplate.class, id);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + lt);
        }
        return lt;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO#retrieveAllLetterTemplates()
     */
    public LetterTemplateList retrieveAllLetterTemplates() {

        final String logSM = "retrieveAllLetterTemplates()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        LetterTemplateList ltList = new LetterTemplateList((List<LetterTemplate>) getHibernateTemplate().
                                     findByNamedQuery("retrieveAllLetterTemplates"));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of records" + ltList.getLetterTemplates().size());
        }
        return ltList;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO#deleteLetterTemplate(long)
     */
    public LetterTemplate deleteLetterTemplate(long id) {

        final String logSM = "deleteLetterTemplate()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        LetterTemplate lt = retrieveLetterTemplate(id);
        delete(lt);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return lt;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO#deleteDocument(long)
     */
    public void deleteDocument(long id) {

        final String logSM = "deleteLetterTemplate()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        LetterTemplateFile document = (LetterTemplateFile) get(LetterTemplateFile.class, id);

        /*
         * On validation failure the uploaded document has to be deleted and ROIException is thrown.
         * With the HibernateTemplate's session, the hibernate transaction gets rolled back when an
         * exception is thrown.
         * To commit the delete operation a new session is obtained and transaction is performed.
         */
        Session s = getHibernateTemplate().getSessionFactory().openSession();
        s.delete(document);
        s.close();

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO
     * #updateLetterTemplate(com.mckesson.eig.roi.admin.model.LetterTemplate)
     */
    public LetterTemplate updateLetterTemplate(LetterTemplate lt) {

        final String logSM = "updateLetterTemplate(lt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + lt);
        }

        LetterTemplate lTemplate = (LetterTemplate) merge(lt);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + lt);
        }
        return lTemplate;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO
     * #clearDefault(com.mckesson.eig.roi.admin.model.LetterTemplate)
     */
    public void clearDefault(LetterTemplate lt) {

        final String logSM = "clearDefault(lt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + lt);
        }

        lt.setIsDefault(false);
        merge(lt);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + lt);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO
     * #getLetterTemplateByName(java.lang.String, java.lang.String)
     */
    public LetterTemplate getLetterTemplateByName(String name, String type) {

        final String logSM = "getLetterTemplateByName(name, type)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        Object[] obj = { name, type };

        @SuppressWarnings("unchecked") // not supported by third party API
        List<LetterTemplate> letterTemplates =
            (List<LetterTemplate>) getHibernateTemplate().findByNamedQuery("getLetterTemplateByName", obj);
        LetterTemplate lt = (letterTemplates.size() == 0) ? null : letterTemplates.get(0);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + lt);
        }
        return lt;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO
     * #getDefaultLetterTemplate(java.lang.String)
     */
    public LetterTemplate getDefaultLetterTemplate(String letterType) {

       final String logSM = "getDefaultLetterTemplate()";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + letterType);
       }

       @SuppressWarnings("unchecked") // not supported by third party API
       List<LetterTemplate> letterTemplates = (List<LetterTemplate>) getHibernateTemplate().
                                             findByNamedQuery("getLetterTemplateIsDefault",
                                                              new String(letterType));

       LetterTemplate lt = (letterTemplates.size() == 0) ? null : letterTemplates.get(0);

       if (DO_DEBUG) {
           LOG.debug(logSM + "<<End:" + lt);
       }
       return lt;
    }

    /**
     * (com.mckesson.eig.common.filetransfer.services.BaseFileTransferData, java.lang.String)
     */
    public void completeUpload(BaseFileTransferData serverData,
                               InputStream ins,
                               int contentLen,
                               StringBuffer responseXML) {

        final String logSM = "completeUpload(serverData, ins, contentLen, responseXML)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        long documentId = 0;
        boolean hasExp = false;
        String expMsg = null;
        boolean result = false;
        try {

            documentId = toPlong(Long.parseLong(serverData.getRequestProperty(DOC_ID)));
            String documentName = serverData.getRequestProperty(FILE_NAME);
            byte[] content = getBytesFromStream(ins, contentLen);
            long userId = toPlong(Long.parseLong(serverData.getRequestProperty(USER_ID)));

            LetterTemplateDocument doc = new LetterTemplateDocument();
            doc.setName(documentName);
            doc.setDocument(content);
            doc.setModifiedBy(userId);
            doc.setModifiedDate(getDate());
            doc.setCreatedBy(userId);
            documentId = toPlong((Long) createFile(doc));

            //result = pdfHasField(content, NOTES_FIELD);
            result = docxHasField(content, NOTES_FIELD);
        } catch (ROIException e) {

            hasExp = true;
            expMsg = e.getErrorCode();
        } catch (Throwable t) {

            hasExp = true;
            expMsg = t.getMessage();
        }

        HttpServletResponse res = serverData.getResponse();

        if (hasExp) {
            res.setStatus(Integer.parseInt(EncoderUtilities.decodeForHTML(EncoderUtilities.encodeForHTML(String.valueOf(HttpServletResponse.SC_BAD_REQUEST)))));
            res.setHeader(EncoderUtilities.decodeForHTML(EncoderUtilities.encodeForHTML(RETURN_MESSAGE)), EncoderUtilities.decodeForHTML(EncoderUtilities.encodeForHTML(expMsg)));
        }

        res.setHeader(CHECKIN_ID_KEY, documentId + ROIConstants.FIELD_DELIMITER + result);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }
    }

    private boolean rtfHasField(byte[] in, String field) {

        try {

            StyledEditorKit styledkit = new StyledEditorKit();
            InputStream docStream = new ByteArrayInputStream(in);

            Document doc = new DefaultStyledDocument();
            styledkit.read(docStream, doc, 0);
            String content = doc.getText(0, doc.getLength());
            if ((content != null) && content.contains(field)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ROIException(ROIClientErrorCodes.LETTER_TEMPLATE_PARSING_FAILED);
        }
    }
    
    private boolean docxHasField(byte[] in, String field) {

        try {

            InputStream docStream = new ByteArrayInputStream(in);
            XWPFDocument hdoc=new XWPFDocument(docStream);

            XWPFWordExtractor extractor = new XWPFWordExtractor(hdoc);
            String content = extractor.getText();
            
            if ((content != null) && content.contains(field)) {
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            throw new ROIException(ROIClientErrorCodes.LETTER_TEMPLATE_PARSING_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO#isValidFileName(java.lang.String)
     */
    public boolean isValidFileName(String fileName) {

        return (fileName != null) && fileName.endsWith(ROIConstants.LETTER_TEMPLATE_DOC_TYPE);
    }

    /**
     * This method fetches the document for the specified docId and writes the document
     * in the cache location configured in roi.properties.
     * @see com.mckesson.eig.roi.admin.dao.FileTransferHelper#retrieve(
     * com.mckesson.eig.common.filetransfer.services.BaseFileTransferData)
     */
    public String retrieve(BaseFileTransferData serverData) {

        final String logSM = "retrieve(serverData)";
        final String contentType = "application/octet-stream";

        long documentId = toPlong(Long.parseLong(serverData.getRequestProperty(DOC_ID)));
        boolean doChunk = serverData.getBooleanRequestProperty(CHUNK_ENABLED);

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start docId: " + documentId + "doChunk: " + doChunk);
        }

        String path = null;
        OutputStream out = null;

        HttpServletResponse response = serverData.getResponse();
        try {

            LetterTemplateDocument doc =
                (LetterTemplateDocument) get(LetterTemplateDocument.class, documentId);
            byte[] content = doc.getDocument();

            if (doChunk) {
                path = getCacheFileName(doc.getName());
                out = AccessFileLoader.getFileOutputStream(path);
            } else {
                out = response.getOutputStream();
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentLength(content.length);
                response.setContentType(contentType);
                response.setHeader("FILE_SIZE", content.length + "");
            }

            writeToStream(content, out, serverData);

        } catch (Throwable e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(EncoderUtilities.decodeForHTML(EncoderUtilities.encodeForHTML(contentType)));
            response.setHeader(RETURN_MESSAGE, EncoderUtilities.decodeForHTML(EncoderUtilities.encodeForHTML(e.getMessage().toString())));
            response.setHeader(SESSION_ID, serverData.getRequest().getSession().getId());
            LOG.error("Retrieval of document failed : ", e);
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
            LOG.debug(logSM + "<<End:" + path);
        }
        return path;
    }

    /**
     * This method writes the uploaded document into outputStream
     * @param doc document to write in outputStream
     * @param os outputStream
     * @param serverData BaseFileTransferData
     * @throws IOException
     */
    private void writeToStream(byte[] doc,
                               OutputStream os,
                               BaseFileTransferData serverData) throws IOException {

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
     * This method reads the uploaded document from the inputStream and returns
     * the file contents as an array of bytes.
     * @param inputDoc
     * @return docBytes
     * @throws Exception
     */
    private byte[] getBytesFromStream(InputStream ins, int len) throws Exception {

        final String logSM = "getBytesFromStream(ins, len)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + "InputStreamLength" + len);
        }

        try {

            ByteArrayOutputStream bout = new ByteArrayOutputStream(len);

            BufferedInputStream buf = new BufferedInputStream(ins, len);

            byte[] buffer = new byte[len];
            int read = 0;
            do {
                bout.write(buffer, 0, read);
                read = buf.read(buffer, 0, buffer.length);
            } while (read != -1);

            byte[] b = bout.toByteArray();
            if (b.length != len) {
                throw new Exception("Unable to read entire content. Read " + b.length
                                            + ", expected " + len);
            }

            return b;

        } catch (IOException e) {
            if (DO_DEBUG) {
                LOG.error("Reading from stream in method " + logSM + " failed", e);
            }
            throw e;
        }
    }

    /**
     * This method used to retrieves the file from the cache
     * @param fileName name of the filename
     * @return
     */
    private String getCacheFileName(String fileName) {

        final String logSM = "getFileCachePath(fileName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        String cacheDir = DirectoryUtil.getCacheDirectory() + File.separatorChar + "FileCache";
        File f = null;
        try {
		//DE7315 External Control of File Name or Path
            f = AccessFileLoader.getFile(cacheDir);
        } catch (IOException e) {
                 LOG.error("Exception in getCacheFileName");
        }
        boolean dirCreated = f != null ? f.mkdir() : false;

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Cache dir created " + dirCreated);
        }
        return cacheDir + File.separator + fileName;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO#retrieveLetterTemplateFile(long)
     */
    public LetterTemplateFile retrieveLetterTemplateFile(long id) {

        final String logSM = "retrieveLetterTemplate(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        LetterTemplateFile ltf = (LetterTemplateFile) get(LetterTemplateFile.class, id);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + ltf);
        }
        return ltf;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.LetterTemplateDAO#hasLetterTemplate(java.lang.String)
     */
    public boolean hasLetterTemplate(String letterType) {

        final String logSM = "hasLetterTemplate(letterType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        Object[] obj = {letterType};

        @SuppressWarnings("unchecked") // not supported by third party API
        List<Object> letterTemplates =
            (List<Object>) getHibernateTemplate().findByNamedQuery("getLetterTemplateByType", obj);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }

        return toPboolean(((Long) letterTemplates.get(0) > 0));
    }

    /**
     * This method retrieves the Letter Template
     * @param templateFileId - the template file id
     * @returns LetterTemplateDocument
     */
    public LetterTemplateDocument retrieveLetterTemplateDocument(long templateFileId) {

        final String logSM = "retrieveLetterTemplateDocument(templateFileId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        LetterTemplateDocument doc = (LetterTemplateDocument) get(LetterTemplateDocument.class,
                                                                  templateFileId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Document Name : " + doc.getName());
        }

        return doc;
    }
 }
