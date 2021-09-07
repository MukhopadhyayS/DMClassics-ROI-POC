/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.objectpool;

/**
 * Interface to define objects which can be pooled.
 * 
 * @author Ronnie Andrews, Jr.
 */
public interface PoolableObject {
	
	/**
	 * Reset object back to clean state.
	 */
	void resetObject();
}
