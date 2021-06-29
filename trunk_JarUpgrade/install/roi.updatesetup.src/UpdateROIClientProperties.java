/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2014 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author OFS
 * @date   Apr 7, 2008
 * @since  MPF 16.2 [ROI]; Aug 9, 2014
 */

 
public class UpdateROIClientProperties {


 	public static void main(String [] args)
	{
		UpdateROIClientProperties roiUpdate = new UpdateROIClientProperties();
		roiUpdate.updateClickOnceFiles();
	}

	public UpdateROIClientProperties () {
	}
	
	public void updateClickOnceFiles() {
        	try {

             		//Writes an UpdateSetUp.bat file 
             		// Increments the client version.Whenever client configuration has been modified.
             		File file = new File(".", "UpdateSetUp2.bat");
	             	writeUpdateSetUp(file);

             		String args[] = new String[] {"cmd", "/c", "start", "UpdateSetUp2.bat"};
             		Runtime.getRuntime().exec(args, null , new File("."));

        	} catch (Throwable e) {
	     		e.printStackTrace();
        	}
    	}

	/**
	 * This method used to updated the UpdateSetUp.bat file version
	 * @param writer
	 * @param pointer
	 * @return
	 * @throws IOException
	 */
	private long writeUpdateSetUp(File file) {

		String line;
		String version;
		String buildver;
    		long pointer = 0;
    		RandomAccessFile writer = null;

		try {

			writer = new RandomAccessFile(file, "rw");

			while ((line = writer.readLine()) != null) {
				 line = line.trim();

				 if (!line.toLowerCase().startsWith("rem")
						 && line.toLowerCase().contains("set clientversion=")) {

					 version = line.substring(18);
					 buildver = version.substring(version.lastIndexOf(".") + 1);
					 int res = Integer.parseInt(buildver) + 1;
					 writer.seek(pointer);

					 String s = line.substring(0,18) +
							    version.substring(0, version.lastIndexOf(".") + 1) + res;
					 writer.writeBytes(s);
					 break;
				 }
				 pointer = writer.getFilePointer();
			 }
			return pointer;

		} catch (Exception e) {
	           e.printStackTrace();
			
		} finally {
			close(writer);
			return pointer;
		}
	}

	/**
	 * This method is to close the writer
	 * @param writer Closeable
	 */
   	 protected void close(Closeable writer) {

    		if (null == writer) {
    			return;
    		}

		try {
			writer.close();
		} catch (IOException e) {
           		e.printStackTrace();
 
        	}
    	}

 

}
