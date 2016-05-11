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
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.model.SourceDef;


/**
 *
 * @author OFS
 * @date   Apr 7, 2009
 * @since  HPF 13.1 [ROI]; Mar 9, 2009
 */
public class ComboBoxListener
implements ActionListener {

    private OutputConfigureSourcesUI _outputSourcesUI;
    private String _temp = "hidden";

    public ComboBoxListener(OutputConfigureSourcesUI ui) {
        _outputSourcesUI = ui;
    }

    public void actionPerformed(ActionEvent e) {

        _temp = _temp.equalsIgnoreCase("hide") ? "hidden" : "hide";
        _outputSourcesUI.getHiddenButton().setText(_temp);

        int selectedIndex = _outputSourcesUI.getSourceTypes().getSelectedIndex();
        ((SourceDef) _outputSourcesUI.getSourceTypes()
                                        .getItemAt(_outputSourcesUI.getCurrentIndex()))
                                        .setProperties(_outputSourcesUI.getSourceDefProperties());

        SourceDef def = (SourceDef) _outputSourcesUI.getSourceTypes().getSelectedItem();

        Map<String, String> props = def.getProperties().getProperty();
        JPanel innerPanel = getJPanel(_outputSourcesUI.getContentPane(), "ObjectDetailsInnerPanel");
        innerPanel.removeAll();
        _outputSourcesUI.getFields().clear();
        JTextField field;
        int i = 1;
        for (String name : props.keySet()) {

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(ConfigProps.TOP_MARGIN,
                                  ConfigProps.LEFT_MARGIN,
                                  ConfigProps.COMMON_MARGIN,
                                  ConfigProps.COMMON_MARGIN);

            c.gridx = 0;
            c.gridy = i;
            innerPanel.add(_outputSourcesUI.createFormattedLabel(name), c);

            c.gridx = 1;
            c.gridy = i;
            if (!"password".equalsIgnoreCase(name)) {
                field = _outputSourcesUI.createFormattedTextField(ConfigUtilMessages
                                                                  .getMessage("hpfw.server"));

                field.setName(name);
                field.setText(props.get(name));
            } else {
                field = _outputSourcesUI.createFormattedPasswordField(ConfigUtilMessages
                                                                      .getMessage("hpfw.server"));

                field.setName(name);
                field.setText(props.get(name));
            }
            _outputSourcesUI.getFields().add(field);
            innerPanel.add(field, c);
            i++;
        }
        _outputSourcesUI.setCurrentIndex(selectedIndex);
        _outputSourcesUI.repaint();
   }

    private JPanel getJPanel(Container container, String name) {

        for (Component component : container.getComponents()) {
            if ((component != null) && !(component instanceof JPanel)) {
                continue;
            }
            JPanel panel = (JPanel) component;
            if ((component.getName() != null) && component.getName().equalsIgnoreCase(name)) {
                return panel;
            }
            if (panel.getComponentCount() > 0) {
                if (getJPanel(panel, name) != null) {
                    return getJPanel(panel, name);
                }
            }
        }
        return null;
    }

}
