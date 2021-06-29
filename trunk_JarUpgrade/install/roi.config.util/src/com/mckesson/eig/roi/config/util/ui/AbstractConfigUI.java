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


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.AutoConfigurator;
import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.controller.ConfigurationController;
import com.mckesson.eig.roi.config.util.controller.ConfigurationValidator;
import com.mckesson.eig.roi.config.util.controller.MPFWConfigurationControllerImpl;


/**
 * @author OFS
 * @date   Apr 7, 2008
 * @since  HPF 13.1 [ROI]; Sep 9, 2008
 */
public abstract class AbstractConfigUI
extends JFrame {

    private static final Logger LOG = Logger.getLogger(AbstractConfigUI.class);

    private JPanel _mainPanel = (JPanel) getContentPane();

    private JPanel _objectDetailsPanel;
    private JPanel _objDetailsInnerPanel;

    private JPanel _buttonPanel;

    private JButton _exitButton;
    private JButton _backButton;
    private JButton _saveButton;
    private JButton _finishButton;

    private boolean _lastScreen;
    private boolean _firstScreen;
    private boolean _constructed;

    private ConfigurationController _controller;
    private ConfigurationValidator _validator;

    public AbstractConfigUI() {

        try {

            setLookAndFeel();
            initPanels();
            initialize();
        } catch (Exception e) {
            LOG.debug(e);
        }
    }

    public void setLookAndFeel()
    throws Exception {
        try {

            UIManager.setLookAndFeel(ConfigProps.LOOK_AND_FEEL);
        } catch (Exception e) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
    }

    public void initPanels() {

        _mainPanel.setLayout(new BorderLayout());

        getObjectDetailsPanel().setLayout(new FlowLayout(FlowLayout.CENTER,
                                                         0,
                                                         ConfigProps.VERTICAL_GAP));

        getObjectDetailsPanel().setBackground(new Color(ConfigProps.RED_VALUE,
                                                        ConfigProps.GREEN_VALUE,
                                                        ConfigProps.BLUE_VALUE));

        getObjDetailsInnerPanel().setLayout(new GridBagLayout());
        getObjDetailsInnerPanel().setBackground(new Color(ConfigProps.RED_VALUE,
                                                          ConfigProps.GREEN_VALUE,
                                                          ConfigProps.BLUE_VALUE));

        getButtonPanel().setLayout(new FlowLayout());
        getButtonPanel().setBackground(new Color(ConfigProps.RED_VALUE,
                                                 ConfigProps.GREEN_VALUE,
                                                 ConfigProps.BLUE_VALUE));

    }

    /**
     * This method is to design and show the UI page
     */
    public void constructAndShowUI() {

        try {

            if (!_constructed) {

                constructObjectDetailsPanel();
                constructButtonPanel();
                constructMainPanel();
                prepopulate();
                addListeners();
                _constructed = true;
            }

            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);

        } catch (Exception e) {
            LOG.debug(e);
        }
    }

    /**
     * This method is to design the main panel
     */
    public void constructMainPanel() {

        _mainPanel.add(_objectDetailsPanel, BorderLayout.CENTER);
        _mainPanel.add(_buttonPanel, BorderLayout.SOUTH);

        setTitle(ConfigUtilMessages.getMessage("roi.configuration.utility"));
        setSize(ConfigProps.PANEL_WIDTH, ConfigProps.PANEL_HEIGTH);
        setBackground(new Color(ConfigProps.RED_VALUE,
                                ConfigProps.GREEN_VALUE,
                                ConfigProps.BLUE_VALUE));
    }

    /**
     * This method is to create ObjectDetailpanel
     */
    public abstract void constructObjectDetailsPanel();

    /**
     * This method is to create ButtonPanel
     */
    public  void constructButtonPanel() {

        if (isFirstScreen()) {
            _exitButton = new JButton(ConfigUtilMessages.getMessage("exit.button"));
            _exitButton.setPreferredSize(new Dimension(ConfigProps.BUTTON_WIDTH,
                                                       ConfigProps.BUTTON_HEIGHT));

            getButtonPanel().add(_exitButton);
        } else {
            _backButton = new JButton(ConfigUtilMessages.getMessage("back.button"));
            _backButton.setPreferredSize(new Dimension(ConfigProps.BUTTON_WIDTH,
                                                       ConfigProps.BUTTON_HEIGHT));

            getButtonPanel().add(_backButton);
        }

        if (isLastScreen()) {
            _finishButton = new JButton(ConfigUtilMessages.getMessage("finish.button"));
            _finishButton.setPreferredSize(new Dimension(ConfigProps.SAVE_BUTTON_WIDTH,
                                                         ConfigProps.BUTTON_HEIGHT));

            getButtonPanel().add(_finishButton);
        } else {
            _saveButton = new JButton(ConfigUtilMessages.getMessage("save.continue.button"));
            _saveButton.setPreferredSize(new Dimension(ConfigProps.SAVE_BUTTON_WIDTH,
                                                       ConfigProps.BUTTON_HEIGHT));

            getButtonPanel().add(_saveButton);
        }
    }

    public abstract void prepopulate();

    /**
     * This method is to add listeners to the JComponent
     */
    public void addListeners() {

        // Action Listener for window close button
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                if (!ConfigProps.DEV_ENVIRONMENT && ConfigProps.ROI_CLIENT_INSTALLER_INSTALLED) {
                    getController().updateClickOnceFiles();
                }
                dispose();
                System.exit(0);
            }
        });

        //add ActionListener to backButtton JButton
        if (_backButton != null) {
            _backButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    setVisible(false);
                    ScreenOrderUtility.getInstance().getPreviousScreen().constructAndShowUI();
                }
            });
        }

        //add ActionListener to exitButtton JButton
        if (_exitButton != null) {
            _exitButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    setVisible(false);
                    if (!ConfigProps.DEV_ENVIRONMENT
                        && ConfigProps.ROI_CLIENT_INSTALLER_INSTALLED) {
                        getController().updateClickOnceFiles();
                    }
                    dispose();
                    System.exit(0);
                }
            });
        }

        if (_saveButton != null) {
            _saveButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    try {

                        getController().saveConfigParams(mapConfigParams());
                        setVisible(false);
                        AbstractConfigUI ui = ScreenOrderUtility.getInstance().getNextScreen();
                        ui.constructAndShowUI();
                    } catch (ConfigUtilException ex) {

                        LOG.debug(ex);
                        JOptionPane.showMessageDialog(getParent(), ex.getMessage());
                    }
                }
            });
        }

        if (_finishButton != null) {
            _finishButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    try {

                        getController().saveConfigParams(mapConfigParams());
                        setVisible(false);

                        /**
                         * Export a config file
                         */
                        AutoConfigurator.exportXML();

                        if (!ConfigProps.DEV_ENVIRONMENT
                            && ConfigProps.ROI_CLIENT_INSTALLER_INSTALLED) {
                        	getController().updateClickOnceFiles();
                        }

                        // updates the MPF configuration
						// updates the logging location, encryption class, application Name in MPFW
                        // moved the MPFW configuration Change to Install Shield 
                        //new MPFWConfigurationControllerImpl().save(null);

                        dispose();
                        System.exit(0);
                    } catch (ConfigUtilException ex) {

                        LOG.debug(ex);
                        JOptionPane.showMessageDialog(getParent(), ex.getMessage());
                    }

                }
            });
        }
    }

    public abstract void initialize();

    public abstract Object mapConfigParams();

    /**
     * This method is to create JLabel
     * @param labelName name of the JLabel
     * @return JLabel
     */
    public JLabel createFormattedLabel(String labelName) {

        JLabel label = new JLabel(labelName, JLabel.RIGHT);
        label.setFont(new Font("Arial", 0, ConfigProps.TEXT_SIZE));
        return label;
    }

    /**
     * This method is to create JTextField
     * @return JTextField
     */
    public JTextField createFormattedTextField(String message) {

        JTextField textField = new JTextField(JTextField.RIGHT);
        textField.setPreferredSize(new Dimension(ConfigProps.FIELD_WIDTH,
                                                 ConfigProps.FIELD_HEIGHT));

        textField.setFont(new Font("Arial", 0, ConfigProps.TEXT_SIZE));
        textField.setColumns(ConfigProps.FIELD_WIDTH);
        if (message != null) {
            textField.setToolTipText(message);
        }
        return textField;
    }

    /**
     * This method is to create JTextField
     * @return JTextField
     */
    public JComboBox createFormattedComboBox(String message, Object[] values) {

    	JComboBox comboBox = new JComboBox(values);
    	comboBox.setPreferredSize(new Dimension(ConfigProps.FIELD_WIDTH,
    			ConfigProps.FIELD_HEIGHT));

    	comboBox.setFont(new Font("Arial", 0, ConfigProps.TEXT_SIZE));
    	if (message != null) {
    		comboBox.setToolTipText(message);
    	}
    	return comboBox;
    }

    /**
     * This method is to create JRadioButton
     * @return JRadioButton
     */
    public JRadioButton createFormattedRadioButton(String text, String message, Color col) {


       JRadioButton radioButton = new JRadioButton();

       radioButton.setFont(new Font("Arial", 0, ConfigProps.TEXT_SIZE));
       radioButton.setBackground(col);
        if (message != null) {
        	radioButton.setToolTipText(message);
        }
        if(text != null)
        	radioButton.setText(text);
        return radioButton;

    }

    /**
     * This method is to create JPasswordField
     * @return JPasswordField
     */
    public JPasswordField createFormattedPasswordField(String message) {

        JPasswordField passwordField = new JPasswordField(JPasswordField.RIGHT);
        passwordField.setPreferredSize(new Dimension(ConfigProps.FIELD_WIDTH,
                                                     ConfigProps.FIELD_HEIGHT));

        passwordField.setColumns(ConfigProps.FIELD_HEIGHT);
        if (message != null) {
            passwordField.setToolTipText(message);
        }
        return passwordField;
    }

    /**
     * This method is to create TitledBorder
     * @param title name of the Title
     * @return Border
     */
    public Border createTitledBorder(String title) {

        return new TitledBorder(new EtchedBorder(),
                                title,
                                0,
                                0,
                                new Font("Arial", 1, ConfigProps.TITLE_SIZE),
                                new Color(0,
                                          ConfigProps.TITLE_GREEN_VALUE,
                                          ConfigProps.TITLE_BLUE_VALUE));

    }

    /**
     * This method is to Validate the input for the JTextField
     * which accepts only numeric values
     * @param e event to be performed
     */
    public void textFieldValidation(KeyEvent e) {

        char c = e.getKeyChar();
        if (!((Character.isDigit(c)
            || (c == KeyEvent.VK_BACK_SPACE)
            || (c == KeyEvent.VK_DELETE)))) {
            getToolkit().beep();
            e.consume();
        }
    }

    @Override
    public Insets getInsets() {

        Insets inset;
        inset = new Insets(ConfigProps.TOP_LEFT_MARGIN,
                           ConfigProps.TOP_LEFT_MARGIN,
                           ConfigProps.BOTTOM_RIGHT_MARGIN,
                           ConfigProps.BOTTOM_RIGHT_MARGIN);

        return inset;
    }

    public JPanel getObjectDetailsPanel() {
        if (_objectDetailsPanel == null) {
            _objectDetailsPanel = new JPanel();
            _objectDetailsPanel.setName("ObjectDetails");
        }
        return _objectDetailsPanel;
    }

    public void setObjectDetailsPanel(JPanel detailsPanel) { _objectDetailsPanel = detailsPanel; }


    public JPanel getObjDetailsInnerPanel() {
        if (_objDetailsInnerPanel == null) {
            _objDetailsInnerPanel = new JPanel();
            _objDetailsInnerPanel.setName("ObjectDetailsInnerPanel");
        }
        return _objDetailsInnerPanel;
    }

    public void setObjDetailsInnerPanel(JPanel detailsInnerPanel) {
        _objDetailsInnerPanel = detailsInnerPanel;
    }

    public JPanel getButtonPanel() {
        if (_buttonPanel == null) {
            _buttonPanel = new JPanel();
        }
        return _buttonPanel;
    }
    public void setButtonPanel(JPanel panel) { _buttonPanel = panel; }

    public ConfigurationController getController() { return _controller; }
    public void setController(ConfigurationController controller) { _controller = controller; }

    public ConfigurationValidator getValidator() { return _validator; }
    public void setValidator(ConfigurationValidator validator) { _validator = validator; }

    /**
     * This method is to create JLabel with Bold
     * @param labelName name of the JLabel
     * @return JLabel
     */
    public JLabel createFormattedBoldLabel(String labelName) {

        JLabel label = new JLabel(labelName, JLabel.RIGHT);
        label.setFont(new Font("Arial", 1, ConfigProps.TEXT_SIZE));
        return label;
    }

    public boolean isLastScreen() { return _lastScreen; }
    public void setLastScreen(boolean screen) { _lastScreen = screen; }

    public boolean isFirstScreen() { return _firstScreen; }
    public void setFirstScreen(boolean screen) { _firstScreen = screen; }


    public JButton getExitButton() { return _exitButton; }
    public void setExitButton(JButton button) { _exitButton = button; }

    public JButton getBackButton() { return _backButton; }
    public void setBackButton(JButton button) { _backButton = button; }

    public JButton getSaveButton() { return _saveButton; }
    public void setSaveButton(JButton button) { _saveButton = button; }

    public JButton getFinishButton() { return _finishButton; }
    public void setFinishButton(JButton button) { _finishButton = button; }

}
