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

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;

/**
 * This is a test service that allows us to test the web services framework.
 * 
 */
public class HouseService {

    /**
     * Exception message.
     */
    private static final String MSG = "This exception is always thrown";

    /**
     * Room width & length constants to avoid Checkstyle magic number warnings.
     */
    private static final int TWELVE = 12;
    private static final int FIFTEEN = 15;
    private static final int EIGHTEEN = 18;
    private static final int TWENTY = 20;
    private static final int TWENTY_FIVE = 25;
    private static final int TWENTY_EIGHT = 28;
    private static final int THIRTY = 30;
    private static final int THIRTY_FIVE = 35;
    private static final int FORTY = 40;
    
    /**
     * Throws UserNameTokenException(String and String).
     * 
     * @return House.
     */
    public House throwUsernameTokenException() {
        throw new UsernameTokenException(MSG, 
                ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
    }

    /**
     * Throws an error.
     * 
     * @return House.
     */
    public House throwError() {
        throw new Error("This error is always thrown");
    }

    /**
     * Returns the entire house details.
     * 
     * @return House.
     */
    public House getMansion() {
        House mansion = createHouse("22 Tuxedo Dr.", "Atlanta", "GA", "30000");
        mansion.setRooms(new ArrayList());
        addRoom(mansion, "kitchen", THIRTY, THIRTY_FIVE);
        addRoom(mansion, "living room", FORTY, TWENTY_FIVE);
        addRoom(mansion, "dining room", TWENTY_FIVE, TWENTY_FIVE);
        addRoom(mansion, "master bedroom", TWENTY_FIVE, TWENTY_EIGHT);
        addRoom(mansion, "bedroom 2", TWENTY, EIGHTEEN);
        addRoom(mansion, "bedroom 3", TWENTY, EIGHTEEN);
        addRoom(mansion, "bedroom 4", TWENTY, EIGHTEEN);
        addRoom(mansion, "bedroom 5", TWENTY, EIGHTEEN);
        addRoom(mansion, "bedroom 6", TWENTY, EIGHTEEN);
        addRoom(mansion, "master bath", EIGHTEEN, TWENTY);
        addRoom(mansion, "bathroom 2", TWELVE, FIFTEEN);
        addRoom(mansion, "bathroom 3", TWELVE, FIFTEEN);
        addRoom(mansion, "bathroom 4", TWELVE, FIFTEEN);
        addRoom(mansion, "bathroom 5", TWELVE, FIFTEEN);
        return mansion;
    }

    /**
     * Creates a new house and add the room details to this created house.
     * 
     * @param street
     *            StreetAddress.
     * @param city
     *            City Name.
     * @param state
     *            State Name.
     * @param zip
     *            Zip code.
     * @param room
     *            list.
     * @return House.
     */
    public House buildHouse(String street, String city, String state,
            String zip, Room room) {
        House house = createHouse(street, city, state, zip);
        List rooms = new ArrayList();
        rooms.add(room);
        house.setRooms(rooms);
        return house;
    }

    /**
     * Creates a new house from the given specifications.
     * 
     * @param street
     *            StreetAddress.
     * @param city
     *            City Name.
     * @param state
     *            State Name.
     * @param zip
     *            Zip code.
     * 
     * @return House.
     */
    private House createHouse(String street, String city, String state,
            String zip) {
        House h = new House();
        h.setStreetAddress(street);
        h.setCity(city);
        h.setState(state);
        h.setZip(zip);
        return h;
    }

    /**
     * Adds the room details.
     * 
     * @param house
     *            Created House.
     * @param roomName
     *            Room name.
     * @param length
     *            Length of the room.
     * @param width
     *            Width of the room.
     */
    private void addRoom(House house, String roomName, int length, int width) {
        Room r = new Room();
        r.setName(roomName);
        r.setLength(length);
        r.setWidth(width);
        house.getRooms().add(r);
    }
}
