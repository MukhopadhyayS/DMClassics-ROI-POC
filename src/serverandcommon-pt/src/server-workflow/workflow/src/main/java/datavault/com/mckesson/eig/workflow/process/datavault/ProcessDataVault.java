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
package com.mckesson.eig.workflow.process.datavault;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.workflow.datavault.DVLogger;
import com.mckesson.eig.workflow.datavault.DVUtil;
import com.mckesson.eig.workflow.process.datavault.dao.ProcessDVDAO;
import com.mckesson.eig.workflow.process.datavault.model.ProcessAssignDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessAttributeDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessOwnerDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessVersionDVInfo;

/**
 * @author OFS
 *
 * @date Apr 2, 2009
 * @since HECM 1.0.3; Apr 2, 2009
 */
public class ProcessDataVault {

    private static Log _log;
    private static Log _console;

    /**
     *
     * @param args fileName as args[0]
     */
    public static void main(String[] args) {

    	DOMConfigurator.configure(ProcessDataVault.class.getResource(
    				"/com/mckesson/eig/workflow/process/datavault/logging.xml"));
    	_log = LogFactory.getLogger(ProcessDataVault.class);
    	_console = LogFactory.getLogger("sop");

        if (DVUtil.isValidArugument(args)) {
            new ProcessDataVault().loadExcelData(args[0]);
        }
        System.exit(0);
    }

    /**
     * @param fileName String
     */
    public void loadExcelData(String fileName) {

        try {

        	String startMessage = "Please wait, the data is being processed....\n\n";
        	_log.debug(startMessage);
        	_console.debug(startMessage);

            POIFSFileSystem poiFS = new POIFSFileSystem(
                                    new FileInputStream(
                                    new File(fileName).getCanonicalPath()));
            HSSFSheet xlsSheet = DVUtil.getXLSSheet(
                                          poiFS, ProcessDVHelper.SHEET_NUMBER_ZERO);
            List<ProcessDVInfo> list = getValuesAsList(xlsSheet,
                                                      ProcessDVHelper.SHEET_NUMBER_ZERO,
                                                      ProcessDVHelper.COLUMN_LENGTH_SIXTEEN);

            ProcessDVDAO processDLDAO = new ProcessDVDAO();
            processDLDAO.insertProcessVersion(list);
            processDLDAO.deployProcess(list);

            xlsSheet = DVUtil.getXLSSheet(poiFS, ProcessDVHelper.SHEET_NUMBER_ONE);
            list     = getValuesAsList(xlsSheet,
                                            ProcessDVHelper.SHEET_NUMBER_ONE,
                                            ProcessDVHelper.COLUMN_LENGTH_FIVE);
            processDLDAO.insertProcessAttribute(list);

            xlsSheet = DVUtil.getXLSSheet(poiFS, ProcessDVHelper.SHEET_NUMBER_TWO);
            list     = getValuesAsList(xlsSheet,
                                            ProcessDVHelper.SHEET_NUMBER_TWO,
                                            ProcessDVHelper.COLUMN_LENGTH_FOUR);
            processDLDAO.insertProcessAssign(list);

            String finishMessage = "\n\nProcess DataValut Finished...";
            _log.debug(finishMessage);
            _console.debug(finishMessage);
        } catch (FileNotFoundException e) {
        	_log.error(" Unable to locate the file : '" + fileName + "'" + e);
        } catch (SQLException e) {
        	_log.error("Process DataValut have Connection Problem : " + e);
        } catch (IOException e) {
        	_log.error(" Process DataValut have Input File Reading Problem : " + e);
        }
    }

   /**
    * @param sheet HSSFSheet
    * @param sheetNumber int
    * @param coumnLength int
    *
    * @return List<Process>
    *
    * @throws SQLException
    */
   private List<ProcessDVInfo> getValuesAsList(HSSFSheet sheet, 
                                               int sheetNumber, 
                                               int coumnLength)
   throws SQLException {

       List<ProcessDVInfo> list   = new ArrayList<ProcessDVInfo>();
       Iterator<HSSFRow> iterator = sheet.rowIterator();

       // Skip the header row
       HSSFRow row = iterator.next();

       int currentProcessId = 0;
       boolean isCurrentPId = false;
       ProcessDVDAO processDLDAO = new ProcessDVDAO();

       if (sheetNumber == 0) {

           currentProcessId = processDLDAO.getCurrentProcessId();
           isCurrentPId     = true;
       }

       int processId      = 0;
       String processName = null;
       ProcessDVInfo process;

       while (iterator.hasNext()) {

           row = (HSSFRow) iterator.next();
           if (!DVUtil.isEmptyRow(row, coumnLength)) {
               continue; // Skip empty rows
           }
           process = ProcessDVHelper.getProcessDetail(row, sheetNumber);
           if (process == null) {
               continue;
           }

           processId = 0;

           if (isCurrentPId) {
               processId = ++currentProcessId;
           }
           processName = getProcessName(process);

           processId = processName != null && !isCurrentPId 
           			? processDLDAO.getProcessId(processName, 0, false) 
        		    : isCurrentPId && processName == null ? 0 : processId;

           if (processName == null && processId == 0) {

        	   String sheetName = null;
        	   if (sheetNumber == 1) {
        		   sheetName = ProcessDVHelper.SHEET_NAME_ONE;
        	   } else if (sheetNumber == 2) {
        		   sheetName = ProcessDVHelper.SHEET_NAME_TWO;
        	   }
        	   if (sheetName != null) {

        		   DVLogger.log(sheetName, 
        				   		ProcessDVHelper.ERROR_PROCESS_NOT_EXIST, 
        				   		row.getRowNum());
        	   }
        	   currentProcessId--;
           }

           if (processId != 0) {

               process.setProcessId(processId);
               list.add(process);
           }
       }
       return list;
   }

   /**
    * @param process ProcessDVInfo
    * 
    * @return String
    * 
    * @throws SQLException
    */
   private String getProcessName(ProcessDVInfo process) 
   throws SQLException {
       
       String processName = null;
       ProcessDVDAO processDLDAO = new ProcessDVDAO();

       if (process instanceof ProcessAttributeDVInfo) {
           processName = ((ProcessAttributeDVInfo) process).getName();
       } else if (process instanceof ProcessAssignDVInfo) {

           ProcessAssignDVInfo assignDV = (ProcessAssignDVInfo) process;
           processName = assignDV.getName();

           int entityType = assignDV.getEntityType();
           int actorId = processDLDAO.getEntityId(assignDV.getActorName(), entityType);
           if (actorId == 0) {

               DVLogger.log(ProcessDVHelper.SHEET_NAME_TWO + " For the Process " + processName, 
                            ProcessDVHelper.ERROR_ACTOR_NAME, -1);
               return null;
           }
           ((ProcessAssignDVInfo) process).setActorId(actorId);
       } else if (process instanceof ProcessVersionDVInfo) {

    	   ProcessVersionDVInfo versionDV = (ProcessVersionDVInfo) process;
           ProcessOwnerDVInfo ownerDV = versionDV.getProcessOwner();
           if (ownerDV == null) {
        	   return null;
           }
           processName = ownerDV.getName();
           int ownerActorId = processDLDAO.getEntityId(ownerDV.getOwnerName(), 
                                            ProcessDVHelper.DOMAIN_ENTITY_TYPE);
           if (ownerActorId == 0) {

               DVLogger.log(ProcessDVHelper.SHEET_NAME_ZERO + " For the Process " + processName, 
            		   		ProcessDVHelper.ERROR_OWNER_ACTOR_NAME, -1);
               return null;
           }
           ownerDV.setOwnerId(ownerActorId);
           ((ProcessVersionDVInfo) process).setProcessOwner(ownerDV);
       }

       return processName;
   }
}
