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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.controller.FaxServiceConfigControllerImpl;


/**
 *
 * @author OFS
 * @date   May 25, 2009
 * @since  HPF 13.1 [ROI]; Mar 2, 2009
 */
public class FaxServiceConfigUI
extends AbstractConfigUI implements ActionListener{

    private JTextField _faxServerName;
    private JTextField _faxQueueName;
    private JTextField _faxPassword;
    private JRadioButton _rightFaxRadio;
    private JRadioButton _zetaFaxRadio;

    private static final Logger LOG = Logger.getLogger(FaxServiceConfigUI.class);

    @Override
    public void constructObjectDetailsPanel() {

        final String logSM = "constructObjectDetailsPanel()";
        LOG.debug(logSM + ">>Start:");

        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.
                                                             getMessage("fax.service")));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(ConfigProps.TOP_MARGIN,
                              ConfigProps.LEFT_MARGIN,
                              ConfigProps.COMMON_MARGIN,
                              ConfigProps.COMMON_MARGIN);

        c.gridx = 0;
        c.gridy = 0;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("fax.server.type")), c);
        
        
        c.gridx = 0;
        c.gridy = 1;
        Color col = new Color(ConfigProps.RED_VALUE, ConfigProps.GREEN_VALUE, ConfigProps.BLUE_VALUE);
        JPanel jPanel = new JPanel();
        
        _rightFaxRadio = createFormattedRadioButton(ConfigProps.RIGHT_FAX, ConfigUtilMessages.getMessage("fax.servertype"), col);
        _rightFaxRadio.addActionListener(this);
        jPanel.add(_rightFaxRadio, c);
        
        _zetaFaxRadio = createFormattedRadioButton(ConfigProps.ZETA_FAX, ConfigUtilMessages.getMessage("fax.servertype"), col);
        _zetaFaxRadio.addActionListener(this);
        jPanel.add(_zetaFaxRadio, c);
        
        jPanel.setBackground(col);
        getObjDetailsInnerPanel().add(jPanel);
        
        c.gridx = 0;
        c.gridy = 1;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("fax.server.name")), c);

        c.gridx = 1;
        c.gridy = 1;
        _faxServerName = createFormattedTextField(ConfigUtilMessages.getMessage("fax.server"));
        getObjDetailsInnerPanel().add(_faxServerName, c);

        c.gridx = 0;
        c.gridy = 2;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("fax.queue.name")), c);

        c.gridx = 1;
        c.gridy = 2;
        _faxQueueName = createFormattedTextField(ConfigUtilMessages.getMessage("fax.user"));
        getObjDetailsInnerPanel().add(_faxQueueName, c);

        c.gridx = 0;
        c.gridy = ConfigProps.GRID_BAG_CONSTRAINTS;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("fax.password")), c);

        c.gridx = 1;
        c.gridy = ConfigProps.GRID_BAG_CONSTRAINTS;
        _faxPassword = createFormattedPasswordField(ConfigUtilMessages.getMessage("fax.pword"));
        getObjDetailsInnerPanel().add(_faxPassword, c);

        getObjectDetailsPanel().add(getObjDetailsInnerPanel());

        LOG.debug(logSM + "<<End:");

    }

    public void actionPerformed(ActionEvent event){
    	AbstractButton button = (AbstractButton)event.getSource();
        String buttonText = button.getText();
        if(buttonText.equalsIgnoreCase(ConfigProps.RIGHT_FAX)){
        	_rightFaxRadio.setSelected(true);
        	_zetaFaxRadio.setSelected(false);
            return;
        }
        else if(buttonText.equalsIgnoreCase(ConfigProps.ZETA_FAX)){
        	_rightFaxRadio.setSelected(false);
        	_zetaFaxRadio.setSelected(true);
            return;
        }
    }
    
    @Override
    public void prepopulate() {

        final String logSM = "prepopulate()";
        LOG.debug(logSM + ">>Start: ");

        try {

            @SuppressWarnings("unchecked") //Not supported by 3rd party API
            Map<String, String> configParams = (Map<String, String>) getController()
                                                                        .loadConfigParams();

            _faxServerName.setText(configParams.get(ConfigProps.FAX_SERVER_NAME));
            _faxQueueName.setText(configParams.get(ConfigProps.FAX_QUEUE_NAME));
            _faxPassword.setText(configParams.get(ConfigProps.FAX_PASSWORD));

            if(ConfigProps.RIGHT_FAX.equalsIgnoreCase(configParams.get(ConfigProps.FAX_SERVER_TYPE))){
            	_rightFaxRadio.setSelected(true);
            	_zetaFaxRadio.setSelected(false);
            }
            else if(ConfigProps.ZETA_FAX.equalsIgnoreCase(configParams.get(ConfigProps.FAX_SERVER_TYPE))){
            	_rightFaxRadio.setSelected(false);
            	_zetaFaxRadio.setSelected(true);
            }
            LOG.debug(logSM + "<<End: Existing Fax Service Config Details :"
                            + faxServiceconfigDetails(configParams));

        } catch (ConfigUtilException e) {

            LOG.debug(e);
            JOptionPane.showMessageDialog(getParent(), e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FaxServiceConfigUI().constructAndShowUI();
    }

    @Override
    public void initialize() {
        setController(new FaxServiceConfigControllerImpl());
    }

    /**
     * This method is generates the map with the UI field values
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> mapConfigParams() {

        final String logSM = "mapConfigParams()";
        LOG.debug(logSM + ">>Start:");

        Map<String, String> configParams = new HashMap<String, String>();
        try {

            configParams.put(ConfigProps.FAX_SERVER_NAME, _faxServerName.getText());
            configParams.put(ConfigProps.FAX_QUEUE_NAME, _faxQueueName.getText());
            configParams.put(ConfigProps.FAX_PASSWORD, _faxPassword.getText());
            
            if(_rightFaxRadio.isSelected() && !_zetaFaxRadio.isSelected())
            	configParams.put(ConfigProps.FAX_SERVER_TYPE, _rightFaxRadio.getText());
            else if(_zetaFaxRadio.isSelected() && !_rightFaxRadio.isSelected())
            	configParams.put(ConfigProps.FAX_SERVER_TYPE, _zetaFaxRadio.getText());
            LOG.debug(logSM + "<<End: New Fax Service Config Details : "
                      + faxServiceconfigDetails(configParams));

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
        return configParams;
    }

    /**
     * This method is to generate the string with fax service configuration details
     * @param configParams fax service config details
     * @return fax service Configuration details
     */
    private String faxServiceconfigDetails(Map<String, String> configParams) {

        return new StringBuffer().append(", Fax ServerName : ")
                                 .append(configParams.get(ConfigProps.FAX_SERVER_NAME))
                                 .append(", Fax QueueName : ")
                                 .append(configParams.get(ConfigProps.FAX_QUEUE_NAME))
                                 .append(", Fax Password : ")
                                 .append(configParams.get(ConfigProps.FAX_PASSWORD))
                                 .append(", Fax Server Type : ")
                                 .append(configParams.get(ConfigProps.FAX_SERVER_TYPE))
                                 .toString();

    }
}
