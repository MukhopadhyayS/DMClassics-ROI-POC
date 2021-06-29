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

import java.util.List;

/**
 * This class represents a simple java bean that can be passed to a web service
 * or returned.
 * 
 */
public class House {
    
    /**
     * Holds a street address.
     */
    private String _streetAddress;
    
    /**
     * Holds a city name.
     */
    private String _city;
    
    /**
     * Holds a state name.
     */
    private String _state;
    
    /**
     * Holds the value of Zip code.
     */
    private String _zip;
    /**
     * List for adding room details.
     */
    private List _rooms;
   
    /**
     * Returns this city.
     * 
     * @return _city.
     */
    public String getCity() {
        return _city;
    }
    
    /**
     * Sets this city.
     * 
     * @param city
     *            The city to set.
     */
    public void setCity(String city) {
        _city = city;
    }
    
    /**
     * Returns this rooms.
     * 
     * @return _rooms.
     */
    public List getRooms() {
        return _rooms;
    }
    
    /**
     * Sets this room.
     * 
     * @param rooms
     *            The rooms to set.
     */
    public void setRooms(List rooms) {
        _rooms = rooms;
    }
    
    /**
     * Returns this state.
     * 
     * @return _state.
     */
    public String getState() {
        return _state;
    }
   
    /**
     * Sets this state.
     * 
     * @param state
     *            The state to set.
     */
    public void setState(String state) {
        _state = state;
    }
    
    /**
     * Returns this streetAddress.
     * 
     * @return _streetAddress.
     */
    public String getStreetAddress() {
        return _streetAddress;
    }
    
    /**
     * Sets this StreetAddress.
     * 
     * @param streetAddress
     *            The streetAddress to set.
     */
    public void setStreetAddress(String streetAddress) {
        _streetAddress = streetAddress;
    }
   
    /**
     * Returns this zip.
     * 
     * @return _zip.
     */
    public String getZip() {
        return _zip;
    }
    
    /**
     * Sets this zip.
     * 
     * @param zip
     *            The zip to set.
     */
    public void setZip(String zip) {
        _zip = zip;
    }
}
