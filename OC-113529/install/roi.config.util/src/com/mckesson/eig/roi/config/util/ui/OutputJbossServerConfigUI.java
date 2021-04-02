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
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.controller.ConfigurationValidatorImpl;
import com.mckesson.eig.roi.config.util.controller.OutputJBossServerConfigControllerImpl;


/**
 *
 * @author OFS
 * @date   Oct 13, 2009
 * @since  HPF 13.1 [ROI]; Oct 13, 2009
 */
public class OutputJbossServerConfigUI
extends AbstractConfigUI{

    private JTextField _portNo;
    private JTextField _minMemory;
    private JTextField _maxMemory;
    private JTextField _permSize;

    private static final Logger LOG = Logger.getLogger(OutputJbossServerConfigUI.class);

    @Override
    public void constructObjectDetailsPanel() {

        final String logSM = "constructObjectDetailsPanel()";
        LOG.debug(logSM + ">>Start:");

        String title = (ConfigProps.OUTPUT_SERVER_INSTALLED)
                           ? "output.jboss.server"
                           : "roi.jboss.server";

        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.getMessage(title)));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(ConfigProps.TOP_MARGIN,
                              ConfigProps.LEFT_MARGIN,
                              ConfigProps.COMMON_MARGIN,
                              ConfigProps.COMMON_MARGIN);

        c.gridx = 0;
        c.gridy = 0;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("port.no")), c);

        c.gridx = 1;
        c.gridy = 0;
        _portNo = createFormattedTextField(ConfigUtilMessages
                                           .getMessage("output.jboss.serverport"));

        getObjDetailsInnerPanel().add(_portNo, c);

        c.gridx = 0;
        c.gridy = 1;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("min.memory")), c);

        c.gridx = 1;
        c.gridy = 1;
        _minMemory = createFormattedTextField(ConfigUtilMessages.getMessage("vm.min.memory"));
        getObjDetailsInnerPanel().add(_minMemory, c);

        c.gridx = 0;
        c.gridy = 2;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("max.memory")), c);

        c.gridx = 1;
        c.gridy = 2;
        _maxMemory = createFormattedTextField(ConfigUtilMessages.getMessage("vm.max.memory"));
        getObjDetailsInnerPanel().add(_maxMemory, c);

        c.gridx = 0;
        c.gridy = ConfigProps.GRID_BAG_CONSTRAINTS;
        getObjDetailsInnerPanel().add(createFormattedLabel(ConfigUtilMessages.
                                                           getMessage("max.perm.gen.size")), c);

        c.gridx = 1;
        c.gridy = ConfigProps.GRID_BAG_CONSTRAINTS;
        _permSize = createFormattedTextField(ConfigUtilMessages.getMessage("vm.max.perm.gen.size"));
        getObjDetailsInnerPanel().add(_permSize, c);

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

        //add KeyListener to minMemory JTextField
        _minMemory.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                textFieldValidation(e);
            }
        });

        //add KeyListener to maxMemory JTextField
        _maxMemory.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                textFieldValidation(e);
            }
        });

        //add KeyListener to permSize JTextField
        _permSize.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                textFieldValidation(e);
            }
        });
    }

    public static void main(String[] args) {
        new JBossServerConfigUI().constructAndShowUI();
    }

    @Override
    public void prepopulate() {

      final String logSM = "prepopulate()";
      LOG.debug(logSM + ">>Start: ");

      try {

          @SuppressWarnings("unchecked")
          Map<String, String> configParams = (Map<String, String>) getController()
                                                                          .loadConfigParams();

          _portNo.setText(configParams.get(ConfigProps.PORT_NO));
          _minMemory.setText(configParams.get(ConfigProps.MIN_MEMORY));
          _maxMemory.setText(configParams.get(ConfigProps.MAX_MEMORY));
          _permSize.setText(configParams.get(ConfigProps.PERM_SIZE));

          LOG.debug(logSM + "<<End: Existing Output JBoss Config Details :"
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
    public Object mapConfigParams() {

        final String logSM = "mapConfigParams()";
        LOG.debug(logSM + ">>Start:");

        Map<String, String> configParams = new HashMap<String, String>();
        try {

            configParams.put(ConfigProps.PORT_NO, _portNo.getText());
            configParams.put(ConfigProps.MIN_MEMORY, _minMemory.getText());
            configParams.put(ConfigProps.MAX_MEMORY, _maxMemory.getText());
            configParams.put(ConfigProps.PERM_SIZE, _permSize.getText());

            LOG.debug(logSM + "<<End: New Output JBoss Config Details : "
                            + configDetails(configParams));

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
        return configParams;
    }

    @Override
    public void initialize() {

        setController(new OutputJBossServerConfigControllerImpl());
        setValidator(new ConfigurationValidatorImpl());
    }

    /**
     * This method is to generate the string with output Jboss configuration details
     * @param configParams Output jboss config details
     * @return Output jboss Configuration details
     */
    public String configDetails(Map<String, String> configParams) {

        return new StringBuffer().append("Port No : ")
                                 .append(configParams.get(ConfigProps.PORT_NO))
                                 .append(", Min Memory : ")
                                 .append(configParams.get(ConfigProps.MIN_MEMORY))
                                 .append(", Max Memory : ")
                                 .append(configParams.get(ConfigProps.MAX_MEMORY))
                                 .append(", Perm Size : ")
                                 .append(configParams.get(ConfigProps.PERM_SIZE))
                                 .toString();

    }

}
