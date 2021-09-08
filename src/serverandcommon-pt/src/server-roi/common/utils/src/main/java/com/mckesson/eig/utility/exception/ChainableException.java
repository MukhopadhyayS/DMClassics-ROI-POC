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

package com.mckesson.eig.utility.exception;

import java.util.List;

/**
 * Often in programming it is convenient to wrap exceptions in a package level
 * exception. The ChainableException interface represents a class that wraps or
 * cascades exceptions so you don't ever lose the lower level exception that was
 * thrown yet only have to check for a package level exception at runtime. This
 * functionality exists in JDK1.4 exceptions so when JDK1.4 becomes our base
 * level of support we will end up deprecating this interface. It is currently
 * needed for chaining exceptions in JDK1.3
 */
public interface ChainableException {

    Throwable getExtendedCause();

    Throwable getNestedCause();

    List<Throwable> getAllNestedCauses();

    Throwable fillInAllStackTraces();
}
