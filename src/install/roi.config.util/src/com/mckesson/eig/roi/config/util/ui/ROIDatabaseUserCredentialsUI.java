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
import com.mckesson.eig.roi.config.util.controller.ROIDBCredentialControllerImpl;


/**
 * @author OFS
 * @date   Feb 13, 2013
 */
public class ROIDatabaseUserCredentialsUI
extends AbstractConfigUI {

    private JTextField _userId;
    private JTextField _password;

    private static final Logger LOG = Logger.getLogger(ROIDatabaseUserCredentialsUI.class);

	@Override
	public void constructObjectDetailsPanel() {

	    final String logSM = "constructObjectDetailsPanel()";
        LOG.debug(logSM + ">>Start:");

        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.
                                                         getMessage("roi.database.configuration")));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(ConfigProps.TOP_MARGIN,
                              ConfigProps.LEFT_MARGIN,
                              ConfigProps.COMMON_MARGIN,
                              ConfigProps.COMMON_MARGIN);

        c.gridx = 0;
        c.gridy = 1;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("user.id")), c);

        c.gridx = 1;
        c.gridy = 1;
        _userId = createFormattedTextField(ConfigUtilMessages.getMessage("db.user"));
        getObjDetailsInnerPanel().add(_userId, c);

        c.gridx = 0;
        c.gridy = ConfigProps.GRID_BAG_CONSTRAINTS;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("password")), c);

        c.gridx = 1;
        c.gridy = ConfigProps.GRID_BAG_CONSTRAINTS;
        _password = createFormattedPasswordField(ConfigUtilMessages.getMessage("db.password"));
        getObjDetailsInnerPanel().add(_password, c);

        getObjectDetailsPanel().add(getObjDetailsInnerPanel());

        LOG.debug(logSM + "<<End:");
	}

    public static void main(String[] args) {
    	new ROIDatabaseUserCredentialsUI().constructAndShowUI();
    }

    @Override
    public void prepopulate() {

        final String logSM = "prepopulate()";
        LOG.debug(logSM + ">>Start:");

        try {

            @SuppressWarnings("unchecked") //Not supported by 3rd party API
            Map<String, String> configParams = (Map<String, String>) getController()
                                                                        .loadConfigParams();

            _userId.setText(configParams.get(ConfigProps.USER_ID));
            _password.setText(configParams.get(ConfigProps.PASSWORD));

            LOG.debug(logSM + "<<End:Existing DB Config Details: " + configDetails(configParams));

        } catch (ConfigUtilException e) {

            LOG.debug(e);
            JOptionPane.showMessageDialog(getParent(), e.getMessage());
        }
    }

    /**
     * This method is generates the map with the UI field values
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> mapConfigParams() {

        final String logSM = "mapDBConfigParams()";
        LOG.debug(logSM + ">>Start:");

        Map<String, String> configParams = new HashMap<String, String>();

        try {

            configParams.put(ConfigProps.USER_ID, _userId.getText());
            configParams.put(ConfigProps.PASSWORD, _password.getText());

            LOG.debug(logSM + "<<End: New DB Config Details: " + configDetails(configParams));

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
        return configParams;
    }

    @Override
    public void initialize() {
        setController(new ROIDBCredentialControllerImpl());
    }

    /**
     * This method is to generate the string with db configuration details
     * @param configParams db config details
     * @return db Configuration details
     */
    public String configDetails(Map<String, String> configParams) {

        return new StringBuffer().append(", User ID : ")
                                 .append(configParams.get(ConfigProps.USER_ID))
                                 .append(", Password : ")
                                 .append(configParams.get(ConfigProps.PASSWORD))
                                 .toString();

       }
}
