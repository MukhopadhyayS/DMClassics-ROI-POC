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

package com.mckesson.eig.utility.values;

import java.io.Serializable;

/**
 * This class consist information about Sex.
 */
public class Sex implements Serializable {

	private static final long serialVersionUID = -8069267302168283048L;

	private String _code;

    private String _name;

    /**
     * Passed as an argument of type <code>String</code> and store values in
     * <code>String</code> object.
     *
     * @param code
     *            Passed as an argument of Type<code> String </code>Object.
     * @param name
     *            Passed as an argument of Type <code>String</code> Object.
     */
    public Sex(String code, String name) {
        _code = code;
        _name = name;
    }

    /**
     * Calling Bean.
     *
     * @return object of Type <code>Sex</code>.
     */
    public String getCode() {
        return _code;
    }

    /**
     * Calling Bean.
     *
     * @return object of Type <code>Sex</code>.
     */
    public String getName() {
        return _name;
    }

    /**
     * Calling Bean with one argument of type <code>String</code>.
     *
     * @param code
     *            Passed as an argument of Type <code>String</code>.
     */
    public void setCode(String code) {
        _code = code;
    }

    /**
     * Calling Bean with one argument of type <code>String</code>.
     *
     * @param name
     *            Passed as an argument of Type <code>String</code>.
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Passed as an argument of type <code>Object</code> and cast it to type
     * <code>Sex</code>. Then implicit calling Bean and return
     * <code>boolean</code> value.
     *
     * @param obj
     *            Passed as an argument of Type <code>Object</code>.
     * @return object Type of Sex.
     */
    @Override
	public boolean equals(Object obj) {
        Sex sex = (Sex) obj;
        return getCode().equalsIgnoreCase(sex.getCode())
                && getName().equalsIgnoreCase(sex.getName());
    }

    /**
     * @return <code>hash</code> code.
     */
    @Override
	public int hashCode() {
        int hash = 0;
        if (getCode() != null) {
            hash += getCode().hashCode();
        }
        if (getName() != null) {
            hash += getName().hashCode();
        }
        return hash;
    }

}
