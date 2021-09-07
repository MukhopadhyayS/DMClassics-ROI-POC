/**
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.log;

import com.mckesson.dm.core.common.logging.OCLogger;

public interface LoggedRuntimeException {
    
    RuntimeException log();
    
    RuntimeException log(Object message);

    RuntimeException log(OCLogger logImpl, Object message);

    boolean wasLogged();
}
