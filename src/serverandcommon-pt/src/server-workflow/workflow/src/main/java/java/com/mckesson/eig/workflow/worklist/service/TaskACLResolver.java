package com.mckesson.eig.workflow.worklist.service;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.api.IDListResult;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.dao.WorklistDAO;

/**
 * @author Ghazni
 * @date   Nov 18, 2008
 * @since  HECM 4.0
 *
 * This class defines the methods to be execute after worklist operations in order to keep the 
 * task acl's updated.
 */
public class TaskACLResolver implements AfterReturningAdvice {

    private static final String CREATE_WORKLIST = "createWorklist";
    private static final String UPDATE_WORKLIST = "updateExistingWorklist";
    private static final String DELETE_WORKLIST = "deleteWorklists";
    private static final String UPDATE_ACLS     = "updateWorklistACLs";
    private static final String CREATE_NEW_WORKLIST = "createNewWorklist";

    public void afterReturning(Object returnValue,
                               Method method,
                               Object[] args,
                               Object target) throws Throwable {

        String methodName = method.getName();
        WorklistDAO dao 
              = (WorklistDAO) SpringUtilities.getInstance().getBeanFactory().getBean("WorklistDAO");

        if (CREATE_WORKLIST.equals(methodName)) {

            long worklistId = ((Long) returnValue);
            if (hasTaskACls(args)) {
                dao.resolveTaskAclsByWorklistID(worklistId);
            }
        } if (CREATE_NEW_WORKLIST.equals(methodName) || UPDATE_WORKLIST.equals(methodName)) {

            long worklistId = ((Worklist) returnValue).getWorklistID();
            if (hasTaskACls(args)) {
                dao.resolveTaskAclsByWorklistID(worklistId);
            }
        } else if (UPDATE_ACLS.equals(methodName)) {
            dao.updateWorklistACLs();
        } 
    }
    
    private boolean hasTaskACls(Object[] param) {
        
        Worklist worklist = (Worklist) param[0];
        return (null != worklist.getAssignedTo() 
                && CollectionUtilities.hasContent(worklist.getAssignedTo().getACLs()));
    }
}
