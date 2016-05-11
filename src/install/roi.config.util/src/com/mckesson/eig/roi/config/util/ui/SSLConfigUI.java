///* 
//BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
//
//* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
//* Use of this software and related documentation is governed by a license agreement. 
//* This material contains confidential, proprietary and trade secret information of 
//* McKesson Information Solutions and is protected under United States
//* and international copyright and other intellectual property laws. 
//* Use, disclosure, reproduction, modification, distribution, or storage
//* in a retrieval system in any form or by any means is prohibited without the 
//* prior express written permission of McKesson Information Solutions.
//
//END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
//*/
//
//package com.mckesson.eig.roi.config.util.ui;
//
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//import java.awt.event.FocusEvent;
//import java.awt.event.FocusListener;
//import java.util.Map;
//
//import javax.swing.ButtonGroup;
//import javax.swing.JRadioButton;
//
//import org.apache.log4j.Logger;
//
//import com.mckesson.eig.roi.config.util.api.ConfigProps;
//import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
//import com.mckesson.eig.roi.config.util.controller.SSLConfigControllerImpl;
//
//
///**
// *
// * @author rethinamt
// * @date   Oct 06, 2011
// * @since  HPF 15.2 [ROI]; Oct 06, 2011
// */
//public class SSLConfigUI 
//	extends AbstractConfigUI implements FocusListener {
//
//    private JRadioButton _http;
//    private JRadioButton _https;
//    private ButtonGroup _protocolGroup = new ButtonGroup();
//    public static boolean CONFIGPARAMS = true;
//    
//    private static final Logger LOG = Logger.getLogger(SSLConfigUI.class);
//
//    @Override
//    public void constructObjectDetailsPanel() {
//
//        final String logSM = "constructObjectDetailsPanel()";
//        LOG.debug(logSM + ">>Start:");
//
//        getObjectDetailsPanel().setBorder(createTitledBorder(ConfigUtilMessages.
//                                                             getMessage("ssl.configuration")));
//
//        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.insets = new Insets(ConfigProps.TOP_MARGIN,
//                              ConfigProps.LEFT_MARGIN,
//                              ConfigProps.COMMON_MARGIN,
//                              ConfigProps.COMMON_MARGIN);
//
//        c.gridx = 1;
//        c.gridy = 0;
//        getObjDetailsInnerPanel().add(createFormattedLabelLeft(ConfigUtilMessages.
//                                                           getMessage("http.protocol.name")), c);
//
//        c.gridx = 0;
//        c.gridy = 0;
//        _http = createFormattedRadioButton(ConfigUtilMessages.getMessage("http.protocol.name"), !ConfigProps.SSL_CONFIGURED );
//        getObjDetailsInnerPanel().add(_http, c);
//        _http.setActionCommand(ConfigUtilMessages.getMessage("http.protocol.name"));
//        _protocolGroup.add(_http);
//        c.gridx = 1;
//        c.gridy = 1;
//        getObjDetailsInnerPanel().add(createFormattedLabelLeft(ConfigUtilMessages.
//                                                           getMessage("https.protocol.name")), c);
//
//        c.gridx = 0;
//        c.gridy = 1;
//        _https = createFormattedRadioButton(ConfigUtilMessages.getMessage("https.protocol.name"), ConfigProps.SSL_CONFIGURED);
//        getObjDetailsInnerPanel().add(_https, c);
//        _https.setActionCommand(ConfigUtilMessages.getMessage("https.protocol.name"));
//        _protocolGroup.add(_https);
//        getObjectDetailsPanel().add(getObjDetailsInnerPanel());
//
//        LOG.debug(logSM + "<<End:");
//    }
//
//    @Override
//    public void addListeners() {
//
//        super.addListeners();
//        
//        (_http).addFocusListener(this);
//        (_https).addFocusListener(this);
//        	
//     }
//        
//    /**
//     * This method is generates the map with the UI field values
//     * @return Map<String, String>
//     */
//
//    /**
//     * This method is to generate the string with output authentication details
//     * @param configParams output authentication config details
//     * @return output authentication Configuration details
//     */
//    public String configDetails(Map<String, String> configParams) {
//
//        return new StringBuffer().append("SSL Configuration : ")
//                                 .append(configParams.get(ConfigProps.SSL_CONFIGURATION))
//                                 .toString();
//
//    }
//
//    @Override
//    public void initialize() {
//        setController(new SSLConfigControllerImpl());
//    }
//
//   
//
//    public static void main(String[] args) {
//        new SSLConfigUI().constructAndShowUI();
//    }
//
//	@Override
//	public Object mapConfigParams() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void prepopulate() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void focusGained(FocusEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void focusLost(FocusEvent e) {
//		
//		if (ConfigUtilMessages.getMessage("http.protocol.name").equals(_protocolGroup.getSelection().getActionCommand())) {
//			CONFIGPARAMS = false;
//		} else {
//			CONFIGPARAMS = true;
//		}
//	}
//}
