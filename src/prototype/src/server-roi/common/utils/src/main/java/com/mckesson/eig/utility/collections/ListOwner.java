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

package com.mckesson.eig.utility.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.mckesson.eig.utility.util.BeanUtilities;
import com.mckesson.eig.utility.util.ObjectUtilities;

/**
 * If a serializable class does not explicitly declare a serialVersionUID, then
 * the serialization runtime will calculate a default serialVersionUID value for
 * that class based on various aspects of the class.
 */
public class ListOwner<O, T> implements Serializable {
	private static final long serialVersionUID = -3724499502683831375L;

	/**
     * object for association.
     */
    private O _owner;

    /**
     * Name associated with this object.
     */
    private String _association;

    /**
     * list to be manipulated.
     */
    private final List<T> _list;

    /**
     * constructor for manipulating the owner and list objects.
     * Ensures both owner and list objects are not <code>null</code>.
     *
     * @param owner
     *            object.
     *
     * @param list
     *            List object.
     */
    public ListOwner(O owner, List<T> list) {
        ObjectUtilities.verifyNotNull(owner);
        ObjectUtilities.verifyNotNull(list);
        _owner = owner;
        _list = list;
    }

    /**
     * constructs a command capable of manipulating the list. Sets the list if
     * its not <code>null</code>
     *
     * @param list
     *            object which has to be manipulated.
     */
    public ListOwner(List<T> list) {
        ObjectUtilities.verifyNotNull(list);
        _list = list;
    }

    /**
     * Sets the Nested property for a <code>Collection</code>.It iterates
     * through the collection and each Bean object is passed as the input
     * parameter to the method <code>own</code>.
     *
     * @param c
     *            collection object whose property has to be set. *
     * @see com.mckesson.eig.utility.util.BeanUtilities
     */
    public void ownAll(Collection<? extends T> c) {
        if (c != null && _owner != null) {
            for (T toOwn : c) {
                own(toOwn);
            }
        }
    }

    /**
     * Sets the nested value as <code>null</code> for all objects in the
     * collection. It iterates through the collection and makes an implicit call
     * to method <code>free</code>for every object to set the nested value as
     * null.
     *
     * @param c
     *            collection object which has to be unassociated.
     */
    public void freeAll(Collection<? extends T> c) {
        if (c != null && _owner != null) {
        	for (T toFree : c) {
                free(toFree);
            }
        }
    }

    /**
     * Sets the Nested property.Bean object is passed as the input parameter.
     * The associated <code>String</code> is the property to be set and the
     * <code>Owner</code>object is the value.
     *
     * @param object
     *            Bean Object.
     * @see com.mckesson.eig.utility.util.BeanUtilities
     */
    public void own(T object) {
        String name = getAssociation(object);
        if (name != null && _owner != null) {
            BeanUtilities.setProperty(object, name, _owner);
        }
    }

    /**
     * Sets the nested value as <code>null</code>.
     *
     * @param object
     *            object which has to be unassociated.
     */
    public void free(T object) {
        String name = getAssociation(object);
        if (name != null) {
            BeanUtilities.setProperty(object, name, null);
        }
    }

    /**
     * Makes an implicit call to the method <code>own</code> to associate and
     * to set nested property.
     *
     * @param object
     *            which has to be associated.
     */
    public void associate(T object) {
        own(object);
    }

    /**
     * @param object
     *            object which has to be unassociated.
     */
    public void unassociate(T object) {
        if (!_list.contains(object)) {
            free(object);
        }
    }

    /**
     * Returns the <code>owner</code> object.
     *
     * @return object.
     */
    public O getOwner() {
        return _owner;
    }

    /**
     * Returns the name associated with this object.
     *
     * @param from
     *            object which has to be associated.
     * @return <code>null</code> if the <code> owner </code>object is null.
     *         otherwise returns the name associated with the object
     *         <code>from</code>.
     */
    protected String buildAssociationName(T from) {
        if (_owner == null) {
            return null;
        }
        return ObjectUtilities.getAssociationName(from, _owner);
    }

    /**
     * Returns the name associated with this object. Makes an implicit call to
     * the method <code>buildAssociationName</code>.
     *
     * @param from
     *            object which has to be associated.
     * @return returns the name associated with the object <code>from</code>.
     */
    protected String getAssociation(T from) {
        if (_association == null) {
            _association = buildAssociationName(from);
        }
        return _association;
    }
}
