/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.common.filetransfer.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * This class contains support for transporting a file. It allows the the invoker
 * to check in a file if a file has sent with the request.
 */
public class BaseFileUploader extends HttpServlet implements IUploadContent {

    private static final Log LOG = LogFactory.getLogger(BaseFileUploader.class);

    public static final int SUCCESS = 0;
    public static final int FAILED  = 1;
    public static final int RESEND  = 2;
    public static final int SESSION = 3;

    private List<String> _undeletedFiles = new ArrayList<String>();
    private CacheCleaner _cacheCleaner = new CacheCleaner();

    /**
     * Member variable of type String is initialized.
     */
    private static final String CONTENT_TYPE = "application/octet-stream";

    private static final int CHUNK_SIZE   = 8192;

    private static final String RETURN_MESSAGE_KEY = "RETURN_MESSAGE";
    private static final String CHECKIN_ID_KEY = "CHECKIN_ID";
    private static final String SESSION_ID_KEY = "SESSION_ID";

    /**
     * Static member variable .
     */
    private static String _cName = BaseFileUploader.class.getName();

    /**
     * This method calls the BaseFileTransferData constructor to initialize the request
     * parameters and processes the request.
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     *
     * @throws IOException
     *             IOException
     * @throws ServletException
     *             ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * Calls doGet method if the request is of post type.
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *                                            javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * This method used to process the request, for both doGet and doPost type.
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     *
     * @throws IOException
     *             IOException
     * @throws ServletException
     *             ServletException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

        BaseFileTransferData servletData = new BaseFileTransferData(request, response);
        if (request.getParameterMap().size() > 0) {
            if (!isUploadCanceled(servletData)) {
                // Metric on initial file transfer from client
                TimedMetric tm = TimedMetric.start();
                putRemoteFile(servletData);
                tm.logMetric("Servlet File Upload: " + BaseFileUploader.class);
            } else {
                HttpSession session = servletData.getRequest().getSession();
                deleteCacheFile(session, getCacheDirectory(session),
                                servletData.getCheckInID(), servletData.getTransactionID());
            }
        } else {

            LOG.error("Invalid parameters passed to BaseFileUploader servlet");
            sendFailReturn(servletData, "Invalid parameters");

        }
    }

    /**
     * This function puts a file to the cache. This servlet can be called
     * iteratively for large files that may require "chunking". CHECKIN_ID is
     * used to write data. When the invocation occurs without a
     * "checkin_id=P_1234" querystring parameter, a new temporary cache file
     * name is generated and the chunk is written to that file in the Doccache
     * directory.When the invocation occurs with a "checkin_id=P_1234"
     * querystring parameter, the content is simply appended to the existing
     * temporary file(whose checkin_id is provided).
     *
     * @param servletData
     *            BaseFileTransferData
     * @throws ServletException
     *             ServletException
     * @throws IOException
     *             IOException
     */
    public void putRemoteFile(BaseFileTransferData servletData)
    throws IOException {

        LOG.debug(_cName + " putRemoteFile(), Entering : Timestamp["
                                  + new Timestamp(new Date().getTime()) + "]");

        InputStream inputStream = null;
        String strCacheDir = null;
        String strFileName = null;

        HttpSession session = servletData.getRequest().getSession();
        try {

            if (!isValidUser(servletData.getUserName(),
                             servletData.getPd(),
                             servletData.getTimestamp())) {

                sendFailReturn(servletData, "Invalid credentials for user.");
                return;
            }

            strCacheDir = getCacheDirectory(session);

            // Get the check-in ID if there is one.
            // Any multiple pass activity should have one on subsequent passes.
            // A failure will occur if the checkin_id is supplied, but the file
            // does not exist in the exising cache directory.
            String strCheckInID = servletData.getCheckInID();

            if (strCheckInID == null) {
                strFileName = getFileName();
            } else {

                // Fails if file does not exist
                if (!(AccessFileLoader.getFile(strCacheDir, strCheckInID)).exists()) {

                    sendFailReturn(servletData, "File does not exist. Checkin Id ["
                                                + strCheckInID
                                                + "] is invalid.");
                    return;
                }

                strFileName = strCheckInID;
            }
            LOG.debug(_cName
                          + "BaseFileUploader>putRemoteFile(), local "
                          + "cache file name[" + strFileName + ": Timestamp["
                          + new Timestamp(new Date().getTime()) + "]");

            WsSession.setSessionData(session, servletData.getTransactionID(), strFileName);

            // To get the content length.
            int nContentLength = servletData.getRequest().getContentLength();
            LOG.debug(_cName
                         + "Content length of the file "
                         + strFileName + " is " + nContentLength + " : Timestamp["
                         + new Timestamp(new Date().getTime()) + "]");

            inputStream = servletData.getRequest().getInputStream();

            // If we have a checkin ID then the file should exist and the
            // file operation will be an append.
            boolean blnAppend = !(strCheckInID == null);

            int nRC = writeFileBuffered(servletData, inputStream, strCacheDir, strFileName,
                    nContentLength, blnAppend);

            if (nRC == SUCCESS) {
                sendSuccessReturn(servletData, strFileName, session.getId());
            } else {

                deleteCacheFile(session, strCacheDir, strFileName, null);
                sendFailReturn(servletData, "Error writing temporary file "
                                            + strFileName + ". File was removed.");
            }
        } catch (Exception e) {

            deleteCacheFile(session, strCacheDir, strFileName, null);
            sendFailReturn(servletData, "Exception occurred while processing "
                                        + "putCacheFile request." + " [" + e.toString() + "]");
        } finally {

            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    log("closing  InputStream", "putRemoteFile()", ioe);
                }
            }
        }
    }

    /**
     * This method takes the inputStream and writes its contents into the file
     * specified under the given directory. It returns integer value 0, on
     * success.
     *
     * @param servletData
     *            BaseFileTransferData
     * @param inputStream
     *            InputStream
     * @param strDirPath
     *            String
     * @param strFileName
     *            String
     * @param nDataLength
     *            int
     * @param boolAppend
     *            Boolean
     *
     * @throws Exception
     *             Exception
     *
     * @return nReturn
     *
     */
    protected int writeFileBuffered(BaseFileTransferData data, InputStream fis, String strDirPath,
            String strFileName, int nDataLength, boolean boolAppend) throws Exception {

        byte[] buffer = new byte[CHUNK_SIZE];
        int nReturn   = SUCCESS;
        int nRead = 0;
        int nAmountRead = 0;
        long lWritten = 0;
        FileOutputStream fos = null;

        try {
            fos = AccessFileLoader.getFileOutputStream(strDirPath + File.separator + strFileName,
                    boolAppend);

           while (lWritten < nDataLength) {
              // Only read the smallest of the buffer length or the remaining amount
              nRead = Math.min(buffer.length, (int) (nDataLength - lWritten));

              // Read into buffer, getting back count of bytes read
              nAmountRead = fis.read(buffer, 0, nRead);

              // Write bytes and increase our count
              fos.write(buffer, 0, nAmountRead);
              lWritten += nAmountRead;
           }

           if (lWritten < nDataLength) {
               nReturn = RESEND;
           }

           return nReturn;
        } catch (IOException ioe) {
            log("Error writing file", "writeFileBuffered()", ioe);
            throw ioe;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (IOException ioe) {
                    log("Error closing input stream", "writeFileBuffered()", ioe);
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException ioe) {
                    log("Error closing output stream", "writeFileBuffered()", ioe);
                }
            }
        }
     }

    /**
     * This method sends a string in XML format on success with checkin_Id and
     * session_Id code.
     *
     * @param data
     *            DocServletData
     * @param checkinId
     *            String
     * @param sessionId
     *            String
     *
     */
    public void sendSuccessReturn(BaseFileTransferData data,
                                  String checkinId,
                                  String sessionId) {

        HttpServletResponse res = data.getResponse();
        res.setContentType(CONTENT_TYPE);
        res.setStatus(HttpServletResponse.SC_OK);
		String sanitizedCheckinId = StringUtilities.sanitizeHttpHeaderString(checkinId);
		String sanitizedSessionId = StringUtilities.sanitizeHttpHeaderString(sessionId);
        res.setHeader(CHECKIN_ID_KEY, sanitizedCheckinId);
        res.setHeader(SESSION_ID_KEY, sanitizedSessionId);

        if (data.getBooleanRequestProperty(BaseFileTransferData.PARAMETER_FINALCHUNK)) {
            completeUpload(data, checkinId);
        }
     }

    /**
     * To be over-ridden by sub classes to do extended functionalities on completion of upload, like
     * adding or updating entries in database.
     *
     * @param data
     * @param checkinId
     */
    protected void completeUpload(BaseFileTransferData data,
                                  String checkinId) {
       HttpSession session = data.getRequest().getSession();
       WsSession.removeSessionData(session, data.getTransactionID());
    }

    private String encodeMessage(String message) {
        message = ESAPI.encoder().encodeForHTML(message);
        return message;
    }

    /**
     * This method sends a string in XML format with error code.
     *
     * @param servletData
     *            DocServletData
     * @param strMessage
     *            String
     */
    public void sendFailReturn(BaseFileTransferData servletData, String strMessage) {
        HttpServletResponse res = servletData.getResponse();
        res.setContentType(CONTENT_TYPE);
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        if (!StringUtilities.isEmpty(strMessage)) {
            res.setHeader(RETURN_MESSAGE_KEY, encodeMessage(strMessage));
        }
   }

   /** Delete a file from the cache
    *
    *  @param dirPath The directory path
    *  @param fileName The file name
    *  @param transactionID transactionID
    */
   private void deleteCacheFile(HttpSession session, String dirPath, String fileName, String transactionID) {
       try {
       if ((dirPath == null) || (dirPath.length() <= 0)) {

           LOG.debug("deleteCacheFile(), No directory path provided");
           return;
       }
       if ((fileName == null) || (fileName.length() <= 0)) {

           LOG.debug("deleteCacheFile(), No file name provided");
           
           fileName = (String) WsSession.getSessionData(session, transactionID);
           LOG.debug("fetch file name from WsSession : " + fileName);
           deleteFile(dirPath, fileName);
           WsSession.removeSessionData(session, transactionID);
           return;
       }

       deleteFile(dirPath, fileName);
       } catch(IOException e) {
    	       LOG.error("Exception in deleteCacheFile "+e.getLocalizedMessage());
       }
       WsSession.removeSessionData(session, transactionID);
    }

   /**
    * This method is used to delete the file specified by the file name and directory path.
    * @param dirPath
    * @param fileName
 * @throws IOException 
    */
   private void deleteFile(String dirPath, String fileName) throws IOException {

       String filePath = dirPath + File.separator + fileName;
       File file = AccessFileLoader.getFile(filePath);
       if (file.exists()) {

           if (file.delete()) {
               LOG.debug("deleteFile(), File[" + filePath + "] deleted");
           } else {

               LOG.debug("deleteFile(), Failed to delete file["
                       + file.getAbsolutePath() + "]");
               processUndeletedFiles(filePath);
           }
       } else {
           LOG.info("deleteLocalFile(), File[" + filePath + "] does not exist");
       }
   }

   /**
    * This method is used to process undeleted files.The undeleted files are stored and sent to
    * delete to another background process
    *
    * @param filePath
    */
   private void processUndeletedFiles(String filePath) {

       _undeletedFiles.add(filePath);
       LOG.debug("Added file" + filePath + "to clean up cache");

       if (!_cacheCleaner._cleanUpInProgress) {
           new Thread(_cacheCleaner).start();
       }
   }

   /**
    * This runnable class is used to delete the undeleted files which are canceled by the user when
    * the upload is in progress.
    *
    * @author pranavkumara
    */
   protected class CacheCleaner implements Runnable {

       private boolean _cleanUpInProgress;

        public void run() {

            _cleanUpInProgress = true;

            String strCleanUpInterval = System.getProperty("cache.cleanup.interval");

            final int secsInMinute = 60;
            final int millisecsInSec = 1000;
            final int tenMinutes = 10 * secsInMinute * millisecsInSec;

            try {
                Thread.sleep(StringUtilities.isEmpty(strCleanUpInterval)
                                            ? tenMinutes
                                            : Integer.parseInt(strCleanUpInterval));
                deleteFiles();
            } catch (InterruptedException e) {
                LOG.debug("Clean up thread interrupted");
            } catch (IOException e) {
                LOG.debug("Delete Files Failed");
            }
            _cleanUpInProgress = false;
        }

        /**
         * This method actually does the clean up job in the cache.
         * @throws IOException 
         */
        private void deleteFiles() throws IOException {

            ArrayList<String> unDeletedFiles = new ArrayList<String>();

            synchronized (_undeletedFiles) {
                for (Iterator<String> iterator = _undeletedFiles.iterator(); iterator.hasNext();) {

                    String fileName = iterator.next();
                    File file = AccessFileLoader.getFile(fileName);

                    if (file.delete()) {
                        LOG.debug("File " + fileName + " deleted successfully");
                    } else {
                        LOG.debug("File " + fileName + " not deleted");
                        unDeletedFiles.add(fileName);
                        LOG.debug("Added File " + fileName + " to undeleted files");
                    }
                }
                _undeletedFiles.retainAll(unDeletedFiles);
            }
        }
   }

   /**
    * Helper method used to log the error message
    *
    * @param msg
    *          Indicate the error messgae
    * @param methodName
    *          Indicate the method name
    * @param e
    *          Indicate the exception
    */
    private void log(String msg, String methodName, Exception e) {
        LOG.error("class " + _cName + methodName + ", Exception " + e + msg
                           + " : Timestamp["    + new Timestamp(new Date().getTime()) + "]", e);
    }

    /**
     * This methods used to validate the username, password and also timestamp
     *
     * <p>
     *  Must have to override this method the one who extends <code>BaseFileUploader</code>
     * </p>
     *
     * @param user
     *          Indicates the username
     * @param password
     *          Indicates the password
     * @param timestamp
     *          Indicates the timestamp
     * @return true if valid user, otherwise false;
     */
    protected boolean isValidUser(String user, String password, String timestamp) {
        return !(StringUtilities.isEmpty(user) || StringUtilities.isEmpty(timestamp));
    }

    /**
     * Helper method used to get the cache directory
     *
     * <p>
     *  Must have to override this method the one who extends <code>BaseFileUploader</code>
     * </p>
     *
     * @param session
     *          Indicate the <code>HttpSession</code>
     * @return Returns the qualified cache directory
     * @throws IOException 
     */
    protected String getCacheDirectory(HttpSession session) throws IOException {

        String cacheDir = System.getProperty("java.io.tmpdir") + File.separatorChar + "FileCache";

        File cache = AccessFileLoader.getFile(cacheDir);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cacheDir;
    }

    /**
     * Helper method used to check whether the uploading process gets cancelled or not.
     *
     * <p>If the process has been canceled, then this method deletes the temporary file that was
     * stored in the cache directory., Otherwise the process continues..</p>
     *
     * <p>This method needs to be overridden, if any logic has to be performed on the class which
     * implements <code>BaseFileUploader</code> with respect to file uploading cancellation</p>
     *
     * @param servletData
     *          Indicate the <code>BaseFileTransferData</code>, which is an request wrapper.
     *
     * @param session
     *          Indicate the HttpSession of the request.
     *
     * @return true if the uploading process has been cancelled, otherwise false
     */
    protected boolean isUploadCanceled(BaseFileTransferData servletData) {

        String mode = servletData.getRequestProperty(BaseFileTransferData.PARAMETER_MODE);
        return BaseFileTransferData.MODE_CANCEL.equals(mode);
    }

    /**
     * Helper method used to return the name of the file
     *
     * <p>
     * Must have to override this method the one who extends <code>BaseFileUploader</code>
     * </p>
     *
     * @return Returns the name of the file
     */
    protected String getFileName() {
        return "P_" + System.currentTimeMillis();
    }
}
