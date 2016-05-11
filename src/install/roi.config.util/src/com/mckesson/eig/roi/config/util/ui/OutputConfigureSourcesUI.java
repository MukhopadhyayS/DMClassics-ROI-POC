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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.controller.OutputConfigureSourceControllerImpl;
import com.mckesson.eig.roi.config.util.model.EndPointDefList;
import com.mckesson.eig.roi.config.util.model.Properties;
import com.mckesson.eig.roi.config.util.model.SourceDef;


/**
 *
 * @author OFS
 * @date   Apr 7, 2009
 * @since  HPF 13.1 [ROI]; Mar 3, 2009
 */
public class OutputConfigureSourcesUI
extends AbstractConfigUI {

    private static final Logger LOG = Logger.getLogger(OutputConfigureSourcesUI.class);

    private JButton _hidden = new JButton();

    private List<JTextField> _fields = new ArrayList<JTextField>();
    private JComboBox _sourceTypes;

    private EndPointDefList _defList;
    private int _currentIndex;

    @Override
    public void constructObjectDetailsPanel() {

        final String logSM = "constructObjectDetailsPanel()";
        LOG.debug(logSM + ">>Start:");

        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.
                                                             getMessage("output.config.sources")));
        try {
            _defList = (EndPointDefList) getController().loadConfigParams();
        } catch (ConfigUtilException cue) {
            LOG.debug(cue);
            JOptionPane.showMessageDialog(getParent(), cue.getMessage());
        }

        constructObjectDetailsInnerPanel(_defList.getSourceDefs().get(0));

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        comboBoxPanel.add(createSourceDefComboBox(_defList.getSourceDefs()));

        getObjectDetailsPanel().add(comboBoxPanel);

        getObjectDetailsPanel().add(getObjDetailsInnerPanel());

        LOG.debug(logSM + "<<End:");
    }

    /**
     * This method is to construct object details inner panel
     */
    public void constructObjectDetailsInnerPanel() {

        SourceDef def = _defList.getSourceDefs().get(0);
        constructObjectDetailsInnerPanel(def);
    }

    /**
     * This method is to construct the components to be added on the object details inner panel
     * @param def
     */
    public void constructObjectDetailsInnerPanel(SourceDef def) {

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(ConfigProps.TOP_MARGIN,
                              ConfigProps.LEFT_MARGIN,
                              ConfigProps.COMMON_MARGIN,
                              ConfigProps.COMMON_MARGIN);

        setObjDetailsInnerPanel(new JPanel());
        getObjDetailsInnerPanel().setName("ObjectDetailsInnerPanel");
        getObjDetailsInnerPanel().setLayout(new GridBagLayout());
        getObjDetailsInnerPanel().setBackground(new Color(ConfigProps.RED_VALUE,
                                                          ConfigProps.GREEN_VALUE,
                                                          ConfigProps.BLUE_VALUE));

        Map<String, String> props = def.getProperties().getProperty();
        JTextField field;
        int i = 1;
        for (String name : props.keySet()) {

            c.gridx = 0;
            c.gridy = i;
            getObjDetailsInnerPanel().add(createFormattedLabel(name), c);

            c.gridx = 1;
            c.gridy = i;
            if (!"password".equalsIgnoreCase(name)) {
                field = createFormattedTextField(null);
                field.setName(name);
            } else {
                field = createFormattedPasswordField(null);
                field.setName(name);
            }
            _fields.add(field);

            getObjDetailsInnerPanel().add(field, c);
            i++;
        }
    }

    @Override
    public void constructButtonPanel() {

        super.constructButtonPanel();

        _hidden = new JButton("initial");
        _hidden.setVisible(false);
        getButtonPanel().add(_hidden);

    }

    @Override
    public void addListeners() {

        super.addListeners();

        //add ComboBoxListener to _sourceTypes combo box
        _sourceTypes.addActionListener(new ComboBoxListener(this));

    }

    /**
     * This method is to create comboBox
     */
    public JComboBox createSourceDefComboBox(List<SourceDef> sourceDefs) {

        _sourceTypes = new JComboBox();
        _sourceTypes.setFont(new Font("Arial", 0, ConfigProps.TEXT_SIZE));
        _sourceTypes.setPreferredSize(new Dimension(ConfigProps.COMBO_BOX_WIDTH,
                                                    ConfigProps.COMBO_BOX_HEIGHT));

        for (SourceDef sd : sourceDefs) {
            _sourceTypes.addItem(sd);
        }
        _currentIndex = 0;
        setSourceDefDetails((SourceDef) _sourceTypes.getItemAt(0));
        return _sourceTypes;
    }

    /**
     * This method is to set the existing source details in the OutputConfigure source UI
     */
    private void setSourceDefDetails(SourceDef sd) {

        for (JTextField field : _fields) {
            field.setText(sd.getProperties().getProperty().get(field.getName()));
        }
    }

    /**
     * This method is to create a map having the text field's name and text as key and value
     * @return
     */
    public Properties getSourceDefProperties() {

        Properties properties = new Properties();
        Map<String, String> props = new HashMap<String, String>();

        for (JTextField field : _fields) {
            props.put(field.getName(), field.getText());
        }

        properties.setProperty(props);
        return properties;
    }

    @Override
    public void initialize() {
        setController(new OutputConfigureSourceControllerImpl());
    }

    public JComboBox getSourceTypes() {
        return _sourceTypes;
    }

    public JButton getHiddenButton() {
        return _hidden;
    }

    @Override
    public void prepopulate() {
    }

    public static void main(String[] args) {
        new OutputConfigureSourcesUI().constructAndShowUI();
    }

    public List<JTextField> getFields() {
        return _fields;
    }

    public int getCurrentIndex() {
        return _currentIndex;
    }

    public void setCurrentIndex(int index) {
        _currentIndex = index;
    }

    @Override
    public Object mapConfigParams() {

        final String logSM = "mapConfigParams()";
        LOG.debug(logSM + ">>Start:");

        try {

            ((SourceDef) _sourceTypes.getItemAt(_currentIndex))
            .setProperties(getSourceDefProperties());

            List<SourceDef> defs = new ArrayList<SourceDef>();
            for (int i = 0; i < _sourceTypes.getItemCount(); i++) {
                defs.add((SourceDef) _sourceTypes.getItemAt(i));
            }
            _defList.setSourceDefs(defs);

            LOG.debug(logSM + "<<End: ");

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
        return _defList;
    }
}
