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

import java.util.LinkedList;

import com.mckesson.eig.roi.config.util.api.ConfigProps;


/**
 *
 * @author OFS
 * @date   Oct 13, 2009
 * @since  HPF 13.1 [ROI]; Mar 11, 2009
 */
public final class ScreenOrderUtility {

    private static ScreenOrderUtility _instance = null;
    private LinkedList<AbstractConfigUI> _screenOrder = new LinkedList<AbstractConfigUI>();
    private int _currentScreenIndex = 0;

    private ScreenOrderUtility() { }

    public static ScreenOrderUtility getInstance() {

        if (_instance == null) {
            _instance = new ScreenOrderUtility();
            _instance.initialize();
        }
        return _instance;
    }

    public AbstractConfigUI getFirstScreen() {
        return _screenOrder.get(0);
    }

    public AbstractConfigUI getNextScreen() {
        _currentScreenIndex++;
        return _screenOrder.get(_currentScreenIndex);
    }

    public AbstractConfigUI getPreviousScreen() {
        _currentScreenIndex--;
        return _screenOrder.get(_currentScreenIndex);
    }

    public void initialize() {

        int i = 0;

        if (ConfigProps.ROI_SERVER_INSTALLED) {
            _screenOrder.add(i++, new JBossServerConfigUI());
        }

        if (ConfigProps.OUTPUT_SERVER_INSTALLED) {
            _screenOrder.add(i++, new OutputJbossServerConfigUI());
        }

        if (ConfigProps.ROI_SERVER_INSTALLED || ConfigProps.OUTPUT_SERVER_INSTALLED
        			|| ConfigProps.FAX_SERVER_INSTALLED) {

            _screenOrder.add(i++, new DBConnectionConfigUI());
        }

        if (ConfigProps.ROI_SERVER_INSTALLED) {
            _screenOrder.add(i++, new ROIDatabaseUserCredentialsUI());
        }

        if (ConfigProps.OUTPUT_SERVER_INSTALLED || ConfigProps.FAX_SERVER_INSTALLED) {
            _screenOrder.add(i++, new OutputDatabaseUserCredentialsUI());
        }

        if (ConfigProps.ROI_CLIENT_INSTALLER_INSTALLED) {
            _screenOrder.add(i++, new ClientInstallerConfigUI());
            _screenOrder.add(i++, new OutputServerConfigUI());
        }

        if (ConfigProps.ROI_SERVER_INSTALLED || ConfigProps.OUTPUT_SERVER_INSTALLED) {
        	_screenOrder.add(i++, new HPFWDBConfigurationUI());
        }

        if (ConfigProps.FAX_SERVER_INSTALLED) {
            _screenOrder.add(i++, new FaxServiceConfigUI());
        }

        _screenOrder.get(0).setFirstScreen(true);
        _screenOrder.get(_screenOrder.size() - 1).setLastScreen(true);
    }

}
