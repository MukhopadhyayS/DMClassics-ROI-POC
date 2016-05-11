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

package com.mckesson.eig.roi.config.util.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.controller.HPFWDatabaseConfigurationControllerImpl;


/**
 *
 * @author OFS
 * @date   May 05, 2009
 * @since  HPF 13.1 [ROI]; Mar 2, 2009
 */
public class HPFWDBConfigurationUI
extends AbstractConfigUI {

    private JTextField _dbName;
    private JTextField _dbUserId;
    private JTextField _dbUserPassword;
    private JTextField _userId;
    private JTextField _password;

    private static final Logger LOG = Logger.getLogger(HPFWDBConfigurationUI.class);

    @Override
    public void constructObjectDetailsPanel() {

        final String logSM = "constructObjectDetailsPanel()";
        LOG.debug(logSM + ">>Start:");

        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.
                                                             getMessage("hpfw.db.config")));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(ConfigProps.TOP_MARGIN,
                              ConfigProps.LEFT_MARGIN,
                              ConfigProps.COMMON_MARGIN,
                              ConfigProps.COMMON_MARGIN);

        c.gridx = 0;
        c.gridy = 0;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("mpfw.db.name")), c);

        c.gridx = 1;
        c.gridy = 0;
        _dbName = createFormattedTextField(ConfigUtilMessages.getMessage("mpfw.db.name"));
        getObjDetailsInnerPanel().add(_dbName, c);

        c.gridx = 0;
        c.gridy = 2;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
        		                                           getMessage("mpfw.db.user")), c);

        c.gridx = 1;
        c.gridy = 2;
        _dbUserId = createFormattedTextField(ConfigUtilMessages.getMessage("mpfw.db.user"));
        getObjDetailsInnerPanel().add(_dbUserId, c);

        c.gridx = 0;
        c.gridy = 3;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
        													getMessage("mpfw.db.password")), c);

        c.gridx = 1;
        c.gridy = 3;
        _dbUserPassword = createFormattedPasswordField(ConfigUtilMessages.getMessage("mpfw.db.password"));
        getObjDetailsInnerPanel().add(_dbUserPassword, c);
        
        c.gridx = 0;
        c.gridy = 4;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
        		                                           getMessage("mpfw.smb.user")), c);

        c.gridx = 1;
        c.gridy = 4;
        _userId = createFormattedTextField(ConfigUtilMessages.getMessage("mpfw.smb.user"));
        getObjDetailsInnerPanel().add(_userId, c);

        c.gridx = 0;
        c.gridy = 5;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
        													getMessage("mpf.smb.password")), c);

        c.gridx = 1;
        c.gridy = 5;
        _password = createFormattedPasswordField(ConfigUtilMessages.getMessage("mpf.smb.password"));
        getObjDetailsInnerPanel().add(_password, c);


        getObjectDetailsPanel().add(getObjDetailsInnerPanel());

        LOG.debug(logSM + "<<End:");
    }

    @Override
    public void addListeners() {
        super.addListeners();
    }


    /**
     * This method is generates the map with the UI field values
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> mapConfigParams() {

        final String logSM = "mapOutputConfigParams()";
        LOG.debug(logSM + ">>Start:");

        Map<String, String> configParams = new HashMap<String, String>();
        try {

            configParams.put(ConfigProps.DB_NAME, _dbName.getText());
            configParams.put(ConfigProps.USER_ID, _dbUserId.getText());
            configParams.put(ConfigProps.PASSWORD, _dbUserPassword.getText());
            configParams.put(ConfigProps.SMB_USER_ID, _userId.getText());
            configParams.put(ConfigProps.SMB_PASSWORD, _password.getText());

            LOG.debug(logSM + "<<End:New Output Config Details: " + configDetails(configParams));

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
        return configParams;
    }

    /**
     * This method is to generate the string with output authentication details
     * @param configParams output authentication config details
     * @return output authentication Configuration details
     */
    public String configDetails(Map<String, String> configParams) {

        return new StringBuffer().append("DB Name : ")
                                 .append(configParams.get(ConfigProps.DB_NAME))
                                 .append(", User ID : ")
                                 .append(configParams.get(ConfigProps.USER_ID))
                                 .append(", User Password : ")
                                 .append(configParams.get(ConfigProps.PASSWORD))
                                 .append(", SMB UserID : ")
                                 .append(configParams.get(ConfigProps.SMB_USER_ID))
                                 .append(", User SMB Password : ")
                                 .append(configParams.get(ConfigProps.SMB_PASSWORD))
                                 .toString();

    }

    @Override
    public void initialize() {
        setController(new HPFWDatabaseConfigurationControllerImpl());
    }

    @Override
    public void prepopulate() {

        final String logSM = "prepopulate()";
        LOG.debug(logSM + ">>Start: ");

        try {

            @SuppressWarnings("unchecked") //Not supported by 3rd party API
            Map<String, String> configParams = (Map<String, String>) getController()
                                                                        .loadConfigParams();

            _dbName.setText(configParams.get(ConfigProps.DB_NAME));
            _dbUserId.setText(configParams.get(ConfigProps.USER_ID));
            _dbUserPassword.setText(configParams.get(ConfigProps.PASSWORD));
            _userId.setText(configParams.get(ConfigProps.SMB_USER_ID));
            _password.setText(configParams.get(ConfigProps.SMB_PASSWORD));

            LOG.debug(logSM + "<<End: Existing Output Authentication Config Details :"
                            + configDetails(configParams));

        } catch (ConfigUtilException e) {

            LOG.debug(e);
            JOptionPane.showMessageDialog(getParent(), e.getMessage());
        }
    }

    public static void main(String[] args) {
        new HPFWDBConfigurationUI().constructAndShowUI();
    }

}
