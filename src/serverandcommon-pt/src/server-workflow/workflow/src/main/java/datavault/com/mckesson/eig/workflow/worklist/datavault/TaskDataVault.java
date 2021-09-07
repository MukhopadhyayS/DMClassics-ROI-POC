/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.worklist.datavault;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Services;
import org.jbpm.taskmgmt.exe.TaskInstance;
/**
 * @author sahuly
 * @date Apr 21, 2007
 * @since HECM 1.0; Dec 18, 2007
 */
public class TaskDataVault {
    
    private static JbpmConfiguration _jc;
    private static Session _session;

    public static final String PROCESS_NAME = "eig.worklist";
    private static final String VK_CAN_START_EARLY = "canStartEarly";
    private static final String VK_COMMENTS = "comments";
    private static final String VK_OWNED_BY = "ownedBy";
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final int NINE = 9;
    private static final int TEN = 10;
    private static final int ELEVEN = 11;
    private static final int TWELVE = 12;
    private static final int FOURTEEN = 14;
    private static final int FIFTEEN = 15;
    private static final int COMMENT_MAX_LENGTH = 255;

    private static final String PRIORITY_ID_QUERY = " SELECT priority_id "
            + "FROM wf_task_priority WHERE DISPLAY_NAME = ?";

    private static final String ACCESS_ID_QUERY = " SELECT actor.ENTITY_ID "
            + "FROM staff_login staff, wf_actor actor"
            + " WHERE staff.staff_login_seq = actor.entity_id AND staff.access_id = ?";

    private static final String GROUP_WORKLIST_ID_QUERY = "SELECT wl.worklist_id "
            + "FROM wf_worklist wl "
            + "INNER JOIN wf_worklist_owner own ON wl.worklist_id = own.worklist_id "
            + "INNER JOIN wf_actor wfa ON own.actor_id = wfa.actor_id "
            + "INNER JOIN org_info d ON wfa.entity_id = d.org_id "
            + "WHERE wl.name = ? AND d.org_name = ? AND wfa.entity_type_id = 1";

    private static final String PERSONAL_WORKLIST_ID_QUERY = "SELECT wl.worklist_id "
            + "FROM wf_worklist wl "
            + "INNER JOIN wf_worklist_owner own ON wl.worklist_id = own.worklist_id "
            + "INNER JOIN wf_actor wfa ON own.actor_id = wfa.actor_id "
            + "INNER JOIN staff_login sl ON wfa.entity_id = sl.staff_login_seq "
            + "WHERE sl.access_id = ? AND wfa.entity_type_id = 3";
    
    private static final String TASK_ID_QUERY = "SELECT ti.id_"
        + " FROM jbpm_taskinstance ti "
        + " INNER JOIN jbpm_taskactorpool taskActorPool ON taskActorPool.taskinstance_ = ti.id_"
        + " INNER JOIN jbpm_pooledactor pa ON pa.id_ = taskActorPool.pooledactor_ "
        + " WHERE ti.name_ = ? AND pa.actorid_ = ?";
    
    public static void main(String[] args) {

        try {
            new TaskDataVault().run(args[0]);
        } catch (Throwable t) {
            log(t);
        }
    }

    private void run(String fileName) throws IOException {
        readExcel(fileName);
    }

    @SuppressWarnings("unchecked")
    private void createTasks(List list) {

        for (int i = 0; i < list.size(); i++) {

            JbpmContext jbpmContext = _jc.createJbpmContext();
            jbpmContext.setActorId("12");

            ProcessInstance eigTask = jbpmContext
                    .newProcessInstanceForUpdate(PROCESS_NAME);
            TaskInstance jbpmTask = eigTask.getTaskMgmtInstance()
                    .createStartTaskInstance();
            Task task = (Task) list.get(i);

            jbpmTask.setPooledActors(new String[] { String.valueOf(task
                    .getWorklistID()) });

            jbpmTask.setName(task.getTaskName());
            jbpmTask.setDescription(task.getTaskDescription());
            jbpmTask.setDueDate(task.getEndDate());

            jbpmTask.setActorId(task.getTaskCreator());
            jbpmTask.setPriority((int) task.getPriorityID());

            jbpmTask.setVariable(VK_OWNED_BY, task.getOwnedBy());
            jbpmTask.setVariable(VK_CAN_START_EARLY, task.isCanStartEarly());
            jbpmTask.setVariable(VK_COMMENTS, task.getComments());
            long jbpmTaskID = jbpmTask.getId();

            // Start task in draft status
            ProcessInstance processInstance = jbpmContext.getTaskInstance(
                    jbpmTaskID).getProcessInstance();
            processInstance.signal();

            // Process status changes if they are necessary
            changeStatus(jbpmContext, task, jbpmTaskID, processInstance, "release");
            changeStatus(jbpmContext, task, jbpmTaskID, processInstance, "own");
            changeStatus(jbpmContext, task, jbpmTaskID, processInstance,
                    "mark as overdue");
            changeStatus(jbpmContext, task, jbpmTaskID, processInstance, "complete");

            jbpmContext.close();

            // update start date
            try {
                updateStartDateForTask(task.getStartDate(), jbpmTaskID);
            } catch (SQLException e) {
                log(e);
                throw new RuntimeException(e);
            }
        }

    }

    private void changeStatus(JbpmContext jbpmContext, Task task,
            long jbpmTaskID, ProcessInstance processInstance, String transition) {

        if (!jbpmContext.getTaskInstance(jbpmTaskID).getToken().getNode()
                .getName().equals(task.getStatusID())) {
            processInstance.signal(transition);
        }
    }

    public void init() {

        _jc = JbpmConfiguration.getInstance();

        String resourceName =
            "/com/mckesson/eig/workflow/datavault/hibernate.cfg.test.local.xml";

        URL hbConfig = getClass().getResource(resourceName);
        DbPersistenceServiceFactory psf = (DbPersistenceServiceFactory) _jc
                .getServiceFactory(Services.SERVICENAME_PERSISTENCE);
        psf.setConfiguration(new Configuration().configure(hbConfig));
        _session = psf.getSessionFactory().openSession();

        JbpmContext jbpmContext = _jc.createJbpmContext();

        try {

            List<Long> ids = truncateTables();

            for (int i = 0; i < ids.size(); i++) {

                TaskInstance ti = jbpmContext.getTaskInstance(ids.get(i));
                jbpmContext.getGraphSession().deleteProcessInstance(
                        ti.getProcessInstance().getId());
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            jbpmContext.close();
        }
    }

    public void readExcel(String fileName) throws IOException {

        TaskDataVault taskDataVault = new TaskDataVault();
        taskDataVault.init();

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);

        log("Starting processing for group tasks.");
        List<Task> list = getTaskSheetValues(sheet, true);
        createTasks(list);
        log("Loaded " + list.size() + " group tasks.");

        HSSFSheet sheet1 = wb.getSheetAt(1);

        log("Starting processing for user tasks.");
        list = getTaskSheetValues(sheet1, false);
        createTasks(list);
        log("Loaded " + list.size() + " user tasks.");
      
        log("Starting processing for user tasks.");
        insertTaskContents(wb.getSheetAt(2), true);
        log("Loaded group tasks contents.");
        
        log("Starting processing for user tasks contents.");
        insertTaskContents(wb.getSheetAt(THREE), false);
        log("Loaded user tasks contents.");
    }

    public List<Task> getTaskSheetValues(HSSFSheet sheet, boolean group) {

        Task task;
        List<Task> list = new ArrayList<Task>();
        Iterator i = sheet.rowIterator();
        // Skip the header row
        HSSFRow row = (HSSFRow) i.next();

        while (i.hasNext()) {
            row = (HSSFRow) i.next();

            if (isEmptyRow(row)) {
                // Skip empty rows
                continue;
            }

            try {
                
                task = new Task();

                task.setWorklistID(getWorklistId(row, group));
                task.setTaskName(getCellValue(row, THREE));
                task.setTaskDescription(getCellValue(row, FOUR));
                task.setStatusID(getCellValue(row, FIVE).toLowerCase());

                task.setPriorityID((Long) getIDs(PRIORITY_ID_QUERY, getCellValue(row, SIX)).get(0));
                
                task.setTaskCreator((group ? "1.2." : "1.3.") 
                                   + getIDs(ACCESS_ID_QUERY, getCellValue(row, SEVEN)).get(0));

                String owner = getCellValue(row, EIGHT);
                if (owner != null) {
                    
                    task.setOwnedBy((group ? "1.2." : "1.3.") 
                                   + getIDs(ACCESS_ID_QUERY, owner).get(0));
                }

                task.setStartDate(parseDay(getCellValue(row, NINE) + " " + getCellValue(row, TEN)));

                task.setEndDate(parseDay(getCellValue(row, ELEVEN) + " "
                                                                   + getCellValue(row, TWELVE)));

                boolean startEarly = false;
                String startEarlyStr = getCellValue(row, FOURTEEN);
                if ((startEarlyStr != null) && startEarlyStr.equalsIgnoreCase("Y")) {
                    startEarly = true;
                }
                task.setCanStartEarly(startEarly);

                String comment = getCellValue(row, FIFTEEN);
                if (!isValidLength(comment, COMMENT_MAX_LENGTH)) {
                    comment = comment.substring(0, COMMENT_MAX_LENGTH);
                }
                task.setComments(comment);

                list.add(task);
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
        return list;
    }
    
    public static boolean isValidLength(String value, int maxLen) {
        
        if (value != null && value.length() > maxLen) {
            return false;
        }
        return true;
    }
    
    public void insertTaskContents(HSSFSheet sheet, boolean group) {

        TaskContent taskContent;
        Iterator i = sheet.rowIterator();
        // Skip the header row
        HSSFRow row = (HSSFRow) i.next();

        while (i.hasNext()) {
            row = (HSSFRow) i.next();

            if (isEmptyRow(row)) {
                // Skip empty rows
                continue;
            }

            try {
                
                String worklistID  = String.valueOf(getWorklistId(row, group));
                String contentPath = getCellValue(row, FIVE).replace('/', File.separatorChar); 
                contentPath        = new StringBuffer().append(contentPath)
                                                       .append(File.separator)
                                                       .append(getCellValue(row, FOUR))
                                                       .toString();
                
                long parentID  = getContentID(0, getCellValue(row, 0), true);
                long contentID = getContentID(parentID, contentPath, false);
                
                List taskIDs = getIDs(TASK_ID_QUERY, 
                                      new String[] {getCellValue(row, THREE), worklistID});
                
                String doSignStr = getCellValue(row, SEVEN);
                if (doSignStr == null || !doSignStr.equalsIgnoreCase("Y")) {
                    doSignStr = "N";
                }
                
                String version = getCellValue(row, SIX);
                if (version == null) {
                    version = " ";
                }
                
                for (int j = taskIDs.size(); --j >= 0;) {
                    
                    taskContent = new TaskContent();
                    taskContent.setTaskContentID(contentID);
                    taskContent.setTaskID((Long) taskIDs.get(j));
                    taskContent.setVersion(version);
                    taskContent.setDoSign(doSignStr);
                    taskContent.setContentPath(contentPath);
                    if (taskContent.getTaskContentID() != 0) {
                        insertTaskContent(taskContent);
                    } else {
                        log("The Content in the path " + taskContent.getContentPath() 
                                                       + " is not available");
                    }
                }
                
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    private boolean isEmptyRow(HSSFRow row) {
        
        for (int i = 0; i <= FIFTEEN; i++) {
            String val = getCellValue(row, i);
            if ((val != null) && (val.length() > 0)) {
                return false;
            }
        }
        return true;
    }

    private String getCellValue(HSSFRow row, int column) {
        
        HSSFCell cell = row.getCell((short) column);
        if (cell == null) {
            return null;
        }
        return cell.toString();
    }

    public Date parseDay(String date) {

        SimpleDateFormat dbDateFormatter = new SimpleDateFormat(
                "M/dd/yyyy kk:mm");
        if (date == null) {
            return null;
        }
        try {
            return dbDateFormatter.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private long getWorklistId(HSSFRow row, boolean group) throws SQLException {

        long worklistId = 0;
        if (group) {
            // Lookup group work lists by work list name and domain
            String domainName   = getCellValue(row, 0);
            String worklistName = getCellValue(row, 2);
            worklistId          = (Long) getIDs(GROUP_WORKLIST_ID_QUERY, 
                                                new String[] {worklistName, domainName }).get(0);
        } else {
            // Lookup personal work list by access id
            String accessId = getCellValue(row, 1);
            worklistId      = (Long) getIDs(PERSONAL_WORKLIST_ID_QUERY, accessId).get(0);
        }

        return worklistId;
    }

    private List getIDs(String sql, String param) throws SQLException {
        return getIDs(sql, new String[] { param });
    }
    
    private long getContentID(long parentID, String contentPath, boolean root) 
    throws SQLException {
        
        int index = 0;
        String contentName;
        long contentID = 0;
        
        if (root) {
            return getID(parentID, contentPath);
        }
        
        do {
            
            index = contentPath.indexOf(File.separator);
            
            if (index == 0) {
                // if the sep is the first char then skip it
                contentName = contentPath.substring(index + 1);
            } else if (index == -1) {
                contentName = contentPath;
            } else {

                contentName = contentPath.substring(0, index);
                contentPath = contentPath.substring(index + 1);
            }

            // if I get a "." then skip this
            if (!contentName.equals(".")) {
                
                contentID = getID(parentID, contentName);
                if (contentID == parentID) {
                    contentID = 0;
                } else {
                    parentID = contentID;
                }
            }
        } while (index != -1 && contentID != 0);
        
        return contentID;
    }
    
    private long getID(long parentID, String contentName) 
    throws SQLException {
        
        Connection con = _session.connection();

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = new StringBuffer().append(" SELECT objRevInfo.obj_id ")
                                       .append(" FROM   obj_rev_info objRevInfo")
                                       .append(" WHERE  objRevInfo.parent_obj_id = ?")
                                       .append(" AND    UPPER(objRevInfo.obj_name) = ?")
                                       .toString();
        try {

            statement = con.prepareStatement(sql);
            statement.setLong(1, parentID);
            statement.setString(2, contentName.toUpperCase());
            
            resultSet = statement.executeQuery();

            for (; resultSet.next();) {
                parentID = resultSet.getLong(1);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resultSet.close();
            statement.close();
            con.close();
        }
        return parentID;
    }

    private List getIDs(String sql, String[] params) throws SQLException {

        Connection con = _session.connection();

        List<Long> ids = new ArrayList<Long>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i].toString().trim());
            }
            resultSet = statement.executeQuery();

            for (; resultSet.next();) {
                ids.add(resultSet.getLong(1));
            }
            
            if (ids.isEmpty()) {
                ids.add(new Long(0));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resultSet.close();
            statement.close();
            con.close();
        }
        return ids;
    }

    private void updateStartDateForTask(final Date startDate, long jbpmTaskId)
            throws SQLException {

        Connection con = _session.connection();
        String sql = "UPDATE JBPM_TaskInstance SET start_ = ? WHERE id_ = ?";
        PreparedStatement statement = null;

        try {

            statement = con.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setLong(2, jbpmTaskId);
            int rowCnt = statement.executeUpdate();
            assert (rowCnt == 1);
            con.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            statement.close();
            con.close();
        }
    }
    
    private void insertTaskContent(TaskContent taskContent)
    throws SQLException {

        Connection con = _session.connection();
        String sql = new StringBuffer().append(" INSERT INTO WF_TASK_CONTENT ")
                                       .append(" (WF_TASK_CONTENT_SEQ, ")
                                       .append(" TASK_ID, ")
                                       .append(" OBJ_ID, ")
                                       .append(" OBJ_VERSION, ")
                                       .append(" SIGN_FL, ")
                                       .append(" RECORD_VERSION) ")
                                       .append(" VALUES ")
                                       .append(" (WF_TASK_CONTENT_SEQ.nextval, ?, ?, ?, ?, 0)")
                                       .toString();
                
        PreparedStatement statement = null;
        
        try {
        
            statement = con.prepareStatement(sql);
            statement.setLong(1, taskContent.getTaskID());
            statement.setLong(2, taskContent.getTaskContentID());
            statement.setString(THREE, taskContent.getVersion());
            statement.setString(FOUR, taskContent.isDoSign());
            
            int rowCnt = statement.executeUpdate();
            assert (rowCnt == 1);
            con.commit();
        } catch (SQLException e) {
            
            if (e.getErrorCode() == 1) {
                log("The Content with respect to " + taskContent.getContentPath() 
                                                   + " path is not available");
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            
            statement.close();
            con.close();
        }
    }

    private List<Long> truncateTables() throws SQLException {

        String query = new StringBuffer().append(
                "select id_ from JBPM_TaskInstance").toString();
        Connection con = _session.connection();

        Statement statement = null;
        ResultSet resultSet = null;
        List<Long> list = new ArrayList<Long>();

        try {

            statement = con.createStatement();
            resultSet = statement.executeQuery(query);

            for (; resultSet.next();) {
                list.add(resultSet.getLong(1));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

            if (resultSet != null) {
                resultSet.close();
            }
            con.close();
        }
        return list;
    }

    private static void log(Object o) {

        if (o instanceof Throwable) {
            ((Throwable) o).printStackTrace(System.err);
        } else {
            System.out.println(o + "");
        }
    }

    public class Task {

        private boolean _canStartEarly;
        private long _taskID;
        private long _priorityID;
        private long _worklistID;
        private String _statusID;
        private String _taskName;
        private String _taskDescription;
        private String _reassignReason;
        private long[] _contentIDs;
        private Date _startDate;
        private Date _endDate;
        private String _ownedBy;
        private String _taskCreator;
        private String _comments;

        public Task() {
            super();
        }

        public boolean isCanStartEarly() {
            return _canStartEarly;
        }

        public void setCanStartEarly(boolean startEarly) {
            _canStartEarly = startEarly;
        }

        public long getTaskID() {
            return _taskID;
        }

        public void setTaskID(long taskid) {
            _taskID = taskid;
        }

        public long getPriorityID() {
            return _priorityID;
        }

        public void setPriorityID(long priorityid) {
            _priorityID = priorityid;
        }

        public long getWorklistID() {
            return _worklistID;
        }

        public void setWorklistID(long worklistid) {
            _worklistID = worklistid;
        }

        public String getStatusID() {
            return _statusID;
        }

        public void setStatusID(String statusid) {
            _statusID = statusid;
        }

        public String getTaskName() {
            return _taskName;
        }

        public void setTaskName(String name) {
            _taskName = name;
        }

        public String getTaskDescription() {
            return _taskDescription;
        }

        public void setTaskDescription(String description) {
            _taskDescription = description;
        }

        public String getComments() {
            return _comments;
        }

        public void setComments(String comments) {
            this._comments = comments;
        }

        public String getReassignReason() {
            return _reassignReason;
        }

        public void setReassignReason(String reason) {
            _reassignReason = reason;
        }

        public long[] getContentIDs() {
            return _contentIDs;
        }

        public void setContentIDs(long[] ds) {
            _contentIDs = ds;
        }

        public Date getStartDate() {
            return _startDate;
        }

        public void setStartDate(Date date) {
            _startDate = date;
        }

        public Date getEndDate() {
            return _endDate;
        }

        public void setEndDate(Date date) {
            _endDate = date;
        }

        public String getOwnedBy() {
            return _ownedBy;
        }

        public void setOwnedBy(String by) {
            _ownedBy = by;
        }

        public String getTaskCreator() {
            return _taskCreator;
        }

        public void setTaskCreator(String creator) {
            _taskCreator = creator;
        }
    }
    
    public class TaskContent {
        
        private long _taskContentID;
        private long _taskID;
        private long _contentID;
        private String _version;
        private String _doSign;
        private String _contentPath;
        
        public TaskContent() {
            super();
        }

        public long getTaskContentID() {
            return _taskContentID;
        }

        public void setTaskContentID(long contentID) {
            _taskContentID = contentID;
        }

        public long getTaskID() {
            return _taskID;
        }

        public void setTaskID(long taskid) {
            _taskID = taskid;
        }

        public long getContentID() {
            return _contentID;
        }

        public void setContentID(long contentid) {
            _contentID = contentid;
        }

        public String getVersion() {
            return _version;
        }

        public void setVersion(String version) {
            this._version = version;
        }

        public String isDoSign() {
            return _doSign;
        }

        public void setDoSign(String sign) {
            _doSign = sign;
        }

        public String getContentPath() {
            return _contentPath;
        }

        public void setContentPath(String path) {
            _contentPath = path;
        }
    }
}
