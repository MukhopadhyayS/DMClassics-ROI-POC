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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.controller.OutputServerConfigControllerImpl;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 *
 * @author OFS
 * @date   Apr 7, 2009
 * @since  HPF 13.1 [ROI]; Mar 2, 2009
 */
public class OutputServerConfigUI
extends AbstractConfigUI {

    private JTextField _ipAddress;
    private JTextField _portNo;
    private JComboBox _comboBox;

    private static final Logger LOG = Logger.getLogger(OutputServerConfigUI.class);

    @Override
    public void constructObjectDetailsPanel() {

        final String logSM = "constructObjectDetailsPanel()";
        LOG.debug(logSM + ">>Start:");

        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.
                                                             getMessage("output.service")));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(ConfigProps.TOP_MARGIN,
                              ConfigProps.LEFT_MARGIN,
                              ConfigProps.COMMON_MARGIN,
                              ConfigProps.COMMON_MARGIN);

        c.gridx = 0;
        c.gridy = 0;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("output.servername.ip")), c);

        c.gridx = 1;
        c.gridy = 0;
        _ipAddress = createFormattedTextField(ConfigUtilMessages.getMessage("output.server"));
        getObjDetailsInnerPanel().add(_ipAddress, c);

        c.gridx = 0;
        c.gridy = 1;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("port.no")), c);

        c.gridx = 1;
        c.gridy = 1;
        _portNo = createFormattedTextField(ConfigUtilMessages.getMessage("output.port"));
        getObjDetailsInnerPanel().add(_portNo, c);

        c.gridx = 0;
        c.gridy = 2;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
        		getMessage("client.protocol.name")), c);

        c.gridx = 1;
        c.gridy = 2;
        _comboBox = createFormattedComboBox(ConfigUtilMessages.getMessage("client.protocol.name"),
        									getProtocolComboBoxValues());

        getObjDetailsInnerPanel().add(_comboBox, c);

        getObjectDetailsPanel().add(getObjDetailsInnerPanel());

        LOG.debug(logSM + "<<End:");
    }

    @Override
    public void addListeners() {

        super.addListeners();

        //add KeyListener to portNo JTextField
        _portNo.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                textFieldValidation(e);
            }
        });

    }

    public static void main(String[] args) {
        new OutputServerConfigUI().constructAndShowUI();
    }

    @Override
    public void prepopulate() {

        final String logSM = "prepopulate()";
        LOG.debug(logSM + ">>Start:");

        try {

            @SuppressWarnings("unchecked") //Not supported by 3rd party API
            Map<String, String> configParams = (Map<String, String>) getController()
                                                                        .loadConfigParams();

            _ipAddress.setText(configParams.get(ConfigProps.IP_ADDRESS));
            _portNo.setText(configParams.get(ConfigProps.PORT_NO));

            String protocol = configParams.get(ConfigProps.PROTOCOL);
            if (StringUtilities.hasContent(protocol)) {
            	_comboBox.setSelectedItem(protocol);
            }

            LOG.debug(logSM + "<<End:Existing Output Server Config Details: "
                      + configDetails(configParams));

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

        final String logSM = "mapOutpurServerConfigParams()";
        LOG.debug(logSM + ">>Start:");

        Map<String, String> configParams = new HashMap<String, String>();
        try {

            configParams.put(ConfigProps.PORT_NO, _portNo.getText());
            configParams.put(ConfigProps.IP_ADDRESS, _ipAddress.getText());
            configParams.put(ConfigProps.PROTOCOL, ObjectUtilities.toString(_comboBox.getSelectedItem()));

            LOG.debug(logSM + "<<End:New Ouput Server Config Details: "
                      + configDetails(configParams));

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
        return configParams;
    }

    @Override
    public void initialize() {
        setController(new OutputServerConfigControllerImpl());
    }

    protected Object[] getProtocolComboBoxValues() {

		String protocolCount = ConfigUtilMessages.getMessage("client.protocol.count");
		int count = 0;
		if (StringUtilities.hasContent(protocolCount)) {
			count = Integer.parseInt(protocolCount.trim());
		}

		List<String> values = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			values.add(ConfigUtilMessages.getMessage("client.protocol.value" + (i + 1)));
		}

		return values.toArray();
	}


    /**
     * This method is to generate the string with client configuration details
     * @param configParams client config details
     * @return client Configuration details
     */
    public String configDetails(Map<String, String> configParams) {

        return new StringBuffer().append("Port No : ")
                                 .append(configParams.get(ConfigProps.PORT_NO))
                                 .append(", Ip Address : ")
                                 .append(configParams.get(ConfigProps.IP_ADDRESS))
                                 .toString();

    }

}
