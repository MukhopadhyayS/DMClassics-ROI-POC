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

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.controller.OutputDestTypeConfigControllerImpl;
import com.mckesson.eig.roi.config.util.model.DestinationType;
import com.mckesson.eig.roi.config.util.model.EndPointTypeList;
import com.mckesson.eig.roi.config.util.model.SourceType;

/**
 *
 * @author OFS
 * @date   Apr 7, 2009
 * @since  HPF 13.1 [ROI]; Mar 2, 2009
 */
public class OutputDestinationTypesUI
extends AbstractConfigUI {

    private List<Component> _sourceTypeChecks = new ArrayList<Component>();
    private List<Component> _destTypeChecks = new ArrayList<Component>();
    private List<SourceType> _sourceTypes;
    private List<DestinationType> _destinationTypes;
    private EndPointTypeList _types;

    private static final Logger LOG = Logger.getLogger(OutputDestinationTypesUI.class);

    /**
     * This method is to create JCheckBox for source type
     * @param checkBoxName name of the JCheckBox
     * @return JCheckBox
     */
    public JCheckBox createSourceTypeCheckBox(String checkBoxName, Boolean isEnabled) {

        JCheckBox checkBox = new JCheckBox(checkBoxName, isEnabled);
        checkBox.setFont(new Font("Arial", 0, ConfigProps.TEXT_SIZE));
        checkBox.setToolTipText(ConfigUtilMessages.getMessage("source.type.check.box"));
        _sourceTypeChecks.add(checkBox);
        return checkBox;
    }

    /**
     * This method is to create JCheckBox for destination type
     * @param checkBoxName name of the JCheckBox
     * @return JCheckBox
     */
    public JCheckBox createDestinationTypeCheckBox(String checkBoxName, Boolean isEnabled) {

        JCheckBox checkBox = new JCheckBox(checkBoxName, isEnabled);
        checkBox.setFont(new Font("Arial", 0, ConfigProps.TEXT_SIZE));
        checkBox.setToolTipText(ConfigUtilMessages.getMessage("dest.type.check.box"));
        _destTypeChecks.add(checkBox);
        return checkBox;
    }

    @Override
    public void constructObjectDetailsPanel() {

        final String logSM = "constructObjectDetailsPanel()";
        LOG.debug(logSM + ">>Start:");

        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.
                                                             getMessage("output.destination")));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(ConfigProps.TOP_MARGIN,
                              ConfigProps.LEFT_MARGIN,
                              ConfigProps.COMMON_MARGIN,
                              ConfigProps.COMMON_MARGIN);

        c.gridx = 0;
        c.gridy = 0;
        getObjDetailsInnerPanel().add(createFormattedBoldLabel(ConfigUtilMessages.
                                                   getMessage("source.types")), c);

        c.gridx = 0;
        c.gridy = 1;
        JPanel sourcePanel = new JPanel();
        sourcePanel.setLayout(new BoxLayout(sourcePanel, BoxLayout.Y_AXIS));

        try {
            _types = (EndPointTypeList) getController().loadConfigParams();
        } catch (ConfigUtilException cue) {
            LOG.debug(cue);
            JOptionPane.showMessageDialog(getParent(), cue.getMessage());
        }

        for (SourceType source : _types.getSourceTypes()) {
            sourcePanel.add(createSourceTypeCheckBox(source.getType(), source.getEnabled()));
        }
        getObjDetailsInnerPanel().add(sourcePanel, c);

        c.gridx = 1;
        c.gridy = 0;
        getObjDetailsInnerPanel().add(createFormattedBoldLabel(ConfigUtilMessages.
                                                               getMessage("destination.types")), c);

        c.gridx = 1;
        c.gridy = 1;
        JPanel destinationPanel = new JPanel();
        destinationPanel.setLayout(new BoxLayout(destinationPanel, BoxLayout.Y_AXIS));

        for (DestinationType destination : _types.getDestinationTypes()) {
            destinationPanel.add(createDestinationTypeCheckBox(destination.getType(),
                                                               destination.getEnabled()));
        }
        getObjDetailsInnerPanel().add(destinationPanel, c);

        getObjectDetailsPanel().add(getObjDetailsInnerPanel());

        LOG.debug(logSM + "<<End:");
    }

    @Override
    public void initialize() {
        setController(new OutputDestTypeConfigControllerImpl());
    }

    @Override
    public void prepopulate() {
    }

    /**
     * This method is to get the source type details
     * @return
     */
    public List<SourceType> getSourceTypes() {

        _sourceTypes = new  ArrayList<SourceType>();

        for (Component comp : _sourceTypeChecks) {

            JCheckBox box = (JCheckBox) comp;
            SourceType type = new SourceType();
            type.setType(box.getText());
            type.setEnabled(box.isSelected());
            _sourceTypes.add(type);
        }
        return _sourceTypes;
    }

    /**
     * This method is to get the destination type details
     * @return
     */
    public List<DestinationType> getDestinationTypes() {

        _destinationTypes = new ArrayList <DestinationType>();

        for (Component comp : _destTypeChecks) {

            JCheckBox box = (JCheckBox) comp;
            DestinationType type = new DestinationType();
            type.setType(box.getText());
            type.setEnabled(box.isSelected());
            _destinationTypes.add(type);
        }
        return _destinationTypes;
    }

    public static void main(String[] args) {
        new OutputDestinationTypesUI().constructAndShowUI();
    }

    @Override
    public Object mapConfigParams() {

        final String logSM = "mapConfigParams()";
        LOG.debug(logSM + ">>Start:");

        EndPointTypeList endPointTypeList = new EndPointTypeList();
        try {

            endPointTypeList.setSourceTypes(getSourceTypes());
            endPointTypeList.setDestinationTypes(getDestinationTypes());

            LOG.debug(logSM + "<<End: ");

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
        return endPointTypeList;
    }

}
