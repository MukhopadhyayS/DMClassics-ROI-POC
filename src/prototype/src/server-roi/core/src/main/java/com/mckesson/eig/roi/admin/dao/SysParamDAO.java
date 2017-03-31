/**
 * 
 */
package com.mckesson.eig.roi.admin.dao;

import java.util.List;

import com.mckesson.eig.roi.admin.model.SysParam;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.hpf.model.User;

/**
 * This class is used to perform the operation for System related configuration.  
 * @author Shah Mohamed.N
 */
public interface SysParamDAO extends ROIDAO {

    /**
     * This method is used to retrieve the System parameters related to ROI application
     * @return List<SysParam> - return configuration params.
     */
    List<SysParam> retrieveROISysParams();
    /**
     * Method to retrieve Configured days Status
     * @param
     * @return List<SysParam>
     */
    List<SysParam> retrieveConfigureDaysStatus();
    /**
     * Method to Retrieve Number of WeekEnd Days
     * @param
     * @return int(Number of WeekEnd Days)
     */
    int retrieveWeekendDays();
    /**
     * Method to get DayStatus Obj
     * @param dayName
     * @return SysParam Obj
     */
    SysParam getDayStatusObj(String dayName);

    /**
     * Method to Update Days Status
     * @param SysParam
     *            Obj
     * @param User
     *            Obj
     * @return SysParam Obj
     */
    SysParam updateDaysStatus(SysParam sysParam, User user);
    
    /** 
     * This method is used to update the Unbillable RequestFlag in SysParms_Global table
     * 
     * @param checked
     * 
     */
    void updateUnbillableRequestFlag(boolean checked);
    
    /** 
     * This method is used to retrieve the Unbillable RequestFlag in SysParms_Global table
     * 
     * @return checked
     */
    boolean retrieveUnbillableRequestFlag();

}
