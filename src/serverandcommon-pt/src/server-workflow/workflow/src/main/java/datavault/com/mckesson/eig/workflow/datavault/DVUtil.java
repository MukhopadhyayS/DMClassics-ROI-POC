/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.datavault;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.process.datavault.ProcessDVHelper;

/**
 * @author OFS
 *
 * @date Apr 8, 2009
 * @since HECM 1.0.3; Apr 8, 2009
 */
public final class DVUtil {

    private DVUtil() {
    }

    private static final Log CONSOLE = LogFactory.getLogger("sop");

    public static final String MSG_COMMAND_LINE_ARG_REQ
                                = " Command line argument is missing ";
    public static final String MSG_COMMAND_LINE_ARG_EXCEED
                                = " Command line should have only one argument as input ";

    /**
     * @param row HSSFRow
     * @param column int
     *
     * @return String
     */
    public static String getCellValue(HSSFRow row, int column, String defaultValue) {

        HSSFCell cell = row.getCell((short) column);
        return ObjectUtilities.isEmptyAsString(cell) ? defaultValue : cell.toString();
    }

    /**
     * @param defContent String
     *
     * @return String
     */
    public static String getXMLFileContent(HSSFRow row, int col) {

        String defContent = null;
        try {

            defContent = getCellValue(row, col, null);
            if (StringUtilities.isEmpty(defContent)) {
                return defContent;
            }

            InputStream is = new FileInputStream(new File(defContent).getCanonicalPath());
            byte[] fileContent = new byte[is.available()];
            is.read(fileContent);
            defContent = new String(fileContent);
            is.close();
        } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return defContent;
    }

    /**
     * @param poiFS POIFSFileSystem
     * @param sheetNumber int
     *
     * @return HSSFSheet
     */
    public static HSSFSheet getXLSSheet(POIFSFileSystem poiFS, int sheetNumber) {

        try {
            return new HSSFWorkbook(poiFS).getSheetAt(sheetNumber);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read excel sheet ..", e);
        }
    }

    /**
     * @param cellValue String
     *
     * @return boolean
     */
     public static boolean isValidNumber(String cellValue) {

       boolean isValid = false;
       try {

            Double.parseDouble(cellValue);
            isValid = true;
        } catch (NumberFormatException ne) {
             isValid = false;
        }
        return isValid;
     }

     /**
      * @param row HSSFRow
      * @param columnLenth int
      *
      * @return boolean
      */
     public static boolean isEmptyRow(HSSFRow row, int columnLenth) {

         String cellValue = null;
         StringBuffer content = new StringBuffer();
         for (int i = 0; i <= columnLenth; i++) {

             cellValue = getCellValue(row, i, null);
             if (!StringUtilities.isEmpty(cellValue)) {
                 content.append(cellValue);
             }
         }
         if (StringUtilities.isEmpty(content.toString())) {
             return false;
         }
         return true;
     }

     /**
      * @param commandLineArgs String[]
      */
     public static boolean isValidArugument(String[] commandLineArgs) {

         if (commandLineArgs.length == 0) {
        	 CONSOLE.debug(MSG_COMMAND_LINE_ARG_REQ);
         } else if (commandLineArgs.length > 1) {
        	 CONSOLE.debug(MSG_COMMAND_LINE_ARG_EXCEED);
         } else {
              return true;
         }
         return false;
     }

     /**
      * @param expireDate String
      *
      * @return boolean
      */
     public static boolean isValidDate(String expireDate) {

         SimpleDateFormat dbDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
         try {
             return dbDateFormatter.parse(expireDate).after(new Date());
         } catch (ParseException e) {
             return false;
         }
     }

     /**
      * @param date java.util.Date
      *
      * @return java.sql.Date
      */
     public static java.sql.Date convertUtilToSQLDate(Date date) {

         if (date == null) {
             return null;
         }
         return new java.sql.Date(date.getTime());
     }

     /**
      * @param dateString String
      *
      * @return java.util.Date
      */
     public static Date parseDate(String dateString) {

         if (dateString == null) {
             return null;
         }
         Date date = new Date();
         try {
             date = ProcessDVHelper.SIMPLE_DATE_FORMAT.parse(dateString);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return date;
     }

     /**
      * @param date java.util.Date
      *
      * @return String
      */
     public static String formatDate(Date date) {

         if (date == null) {
             return null;
         }
         String dateString = null;
         try {
             dateString = ProcessDVHelper.SIMPLE_DATE_FORMAT.format(date);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return dateString;
     }
}
