/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.common.filetransfer.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.wsfw.security.encryption.AESStrategy;

/**
 * @author Sree Sabesh Rajkumar
 * @date Aug 24, 2009
 * @since WSClient 1.0
 */

public class FileUploadHandler {

	public static final String AND_SYMBOL   = "&";
    public static final String EQUAL_SYMBOL = "=";
    public static final String CHECKIN_ID   = "checkin_id";
    public static final String SESSION_ID   = "SESSION_ID";
    private static final int CHUNCK_SIZE    = 5;

	private boolean _isCanceled;

	/**
     * Method used to upload content file.
     *
     * @return fileID
     * @throws Exception
     */
    public String fileUpload(String filePath, String downloadURL) {

        String fileID = null;
        String sessionId = null;
        URLConnection connection;

        try {

            BufferedInputStream bis  = new BufferedInputStream(new FileInputStream(filePath));
            int totalCount = bis.available();
            int chunkSize = CHUNCK_SIZE;

     	    connection = getURLConnection(fileID, sessionId, downloadURL, totalCount, chunkSize);
     	    BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());

            int i;
            int count = 0;

            while ((i = bis.read()) != -1) {

               if (_isCanceled) {
            	   throw new Exception("File upload cancled");
               }
               count++;
               totalCount--;
               bos.write(i);
               if (count == chunkSize) {

            	   count = 0;
            	   bos.close();
            	   fileID = connection.getHeaderField(CHECKIN_ID);
            	   sessionId = connection.getHeaderField(SESSION_ID);

            	   connection =
            	   	   getURLConnection(fileID, sessionId, downloadURL, totalCount, chunkSize);
            	   bos = new BufferedOutputStream(connection.getOutputStream());
               }
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return connection.getHeaderField(CHECKIN_ID);
    }

    private URLConnection getURLConnection(String fileID,
			                               String sessionId,
			                               String downloadURL,
			                               int totalCount,
			                               int chunkSize) {

		return (totalCount > chunkSize)
			? getConnection(downloadURL, false, fileID, sessionId)
					: getConnection(downloadURL, true, fileID, sessionId);
	}

	/**
	* Method to create Url and connection
	* @param urlString
	* @param isFinal
	* @param fileId
	* @param sessionId
	* @return
	*/
	private URLConnection getConnection(String urlString,
				                        boolean isFinal,
				                        String fileId,
				                        String sessionId) {

		try {

			StringBuffer urlStringBuffer = new StringBuffer().append(urlString);

			if (fileId != null) {

			    urlStringBuffer.append(AND_SYMBOL)
			                   .append(CHECKIN_ID)
			                   .append(EQUAL_SYMBOL)
			                   .append(fileId);
			}

			if (sessionId != null) {

			    urlStringBuffer.append(AND_SYMBOL)
			                   .append(SESSION_ID)
			                   .append(EQUAL_SYMBOL)
			                   .append(sessionId);
			}

			urlStringBuffer.append(AND_SYMBOL)
			               .append("TransactionID")
			               .append(EQUAL_SYMBOL)
			               .append("294e3b44-c04d-4860-9e5e-03f69389fd3d")
			               .append(System.currentTimeMillis()).toString();

			URL url;
			if (isFinal) {
			    url = new URL(urlStringBuffer.append(AND_SYMBOL)
			                                 .append("FINALCHUNK=True").toString());
			} else {
			    url = new URL(urlStringBuffer.append(AND_SYMBOL)
			                                 .append("FINALCHUNK=False").toString());
			}

			URLConnection connection = url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setDefaultUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/octet-stream");

			return connection;
		} catch (Exception e) {
		    throw new ApplicationException(e);
		}
	}

	/**
	* Method used to construct URL.
	*
	* @param  userInfo
	* @return String
	*/
	public String createParameters(String userName, String password, String serverURL) {

	    return new StringBuffer().append(serverURL)
                                 .append("?")
                                 .append("CHUNKENABLED=True")
                                 .append(AND_SYMBOL)
                                 .append("OBJ_CRC" + EQUAL_SYMBOL +  AND_SYMBOL)
                                 .append("USERNAME" + EQUAL_SYMBOL)
                                 .append(userName + AND_SYMBOL)
                                 .append("PASSWORD" + EQUAL_SYMBOL)
                                 .append(getEncryptedPassword(userName, password) + AND_SYMBOL)
                                 .append("TIMESTAMP" + EQUAL_SYMBOL)
                                 .append("TIME_STAMP").toString();
	}

	/**
	* Method used to encrypt password.
	*
	* @param  userInfo
	* @return String
	*/
	protected String getEncryptedPassword(String userName, String password) {

		AESStrategy aesStrategy = new AESStrategy();
		return aesStrategy.encryptPassword(userName, 
		                                   password, 
		                                   String.valueOf(System.currentTimeMillis()));
	}

	public void cancelFileUpload() {
		_isCanceled = Boolean.TRUE;
	}
}
