/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.components.model;

import junit.framework.TestCase;

/**
 * @author Sahul Hameed Y
 * @date   Apr 14, 2008
 * @since  HECM 1.0; Apr 14, 2008
 */
public class TestComponentInfo extends TestCase {

    private static ComponentInfo _componentInfo;

    private static final String COMPONENT_ID = "componentID";

    private static final String COMPONENT_DISPLAY_NAME     = "componentDisplayName";

    @Override
    protected void setUp()
    throws Exception {

        _componentInfo = new ComponentInfo();
    }

    public void testComponentInfo() {

        _componentInfo.setComponentID(COMPONENT_ID);
        _componentInfo.setComponentDisplayName(COMPONENT_DISPLAY_NAME);
    }
}
