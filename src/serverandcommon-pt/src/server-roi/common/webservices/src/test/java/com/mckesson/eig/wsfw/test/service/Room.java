/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.wsfw.test.service;

/**
 * This class represents a simple java bean that can be passed to a web service
 * or returned.
 */
public class Room {
    
    /**
     * Room Name.
     */
    private String _name;
    
    /**
     * Length of the room.
     */
    private int _length;
    
    /**
     * Width of the room.
     * 
     */
    private int _width;
    
    /**
     * Returns this length.
     * 
     * @return _length.
     */
    public int getLength() {
        return _length;
    }
   
    /**
     * Sets this length.
     * 
     * @param length
     *            The length to set.
     */
    public void setLength(int length) {
        _length = length;
    }
   
    /**
     * Returns this name.
     * 
     * @return _name.
     */
    public String getName() {
        return _name;
    }
   
    /**
     * Sets this name.
     * 
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        _name = name;
    }
   
    /**
     * Returns this width.
     * 
     * @return _width.
     */
    public int getWidth() {
        return _width;
    }
    
    /**
     * Sets this width.
     * 
     * @param width
     *            The width to set.
     */
    public void setWidth(int width) {
        _width = width;
    }
}
