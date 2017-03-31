/*
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
package com.mckesson.eig.utility.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.MockServer;

/**
 * Test Case to test the class SocketUtilities.
 */
public class TestSocketUtilities extends UnitTest {

    /**
     * Private final variable.
     */
    private static final int MOCK_SERVER = 969;

    /**
     * Private final variable of type MockServer.
     */
    private MockServer _server;

    /**
     * Private variable for the number of retries.
     */
    private static final int RETRIES = 0;

    /**
     * Set up the test. Creates an instance of the class that needs to be
     * tested.
     * 
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _server = new MockServer(MOCK_SERVER);
        _server.start();
    }

    /**
     * This will remove all the data associated with the test.
     * 
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        _server.close();
        super.tearDown();
    }

    /**
     * Tests the method ConnectAndClose().
     * 
     * @throws Exception
     *             Throws exception if problem in getInputStream()
     */
    public void testConnectAndClose() throws Exception {
		Socket socket = SocketUtilities.connect("127.0.0.1", MOCK_SERVER);
        assertNotNull(socket.getInputStream());
        SocketUtilities.close(socket);
        try {
            socket.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the method ConnectAndCloseWithRetries().
     * 
     * @throws Exception
     *             Throws exception if problem in getInputStream()
     */
    public void testConnectAndCloseWithRetries() throws Exception {
		Socket socket = SocketUtilities.connect("127.0.0.1", MOCK_SERVER,
                RETRIES);
        assertNotNull(socket.getInputStream());
        SocketUtilities.close(socket);
        try {
            socket.getInputStream();
            fail();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Tests the message.
     * 
     * @throws Exception
     *             Exception if problem in _server.getResult()
     */
    public void testMessage() throws Exception {
		sendMessage("127.0.0.1", "Received socket request.");
        assertEquals("Received socket request.", _server.getResult());
    }

    /**
     * Tests connect method with retries.
     */
    public void testFailedConnectWithRetries() {
        try {
            SocketUtilities.connect("J.Lo's Computer", MOCK_SERVER, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests testCloseWithInvalidSocket().
     * 
     * @throws Exception
     *             Exception thrown while closing a socket.
     */
    public void testCloseWithInvalidSocket() throws Exception {
        SocketUtilities.close((Socket) null);
        SocketUtilities.close((ServerSocket) null);
        SocketUtilities.close(new FailOnCloseSocket());
        SocketUtilities.close(new ServerSocket());
    }

    /**
     * 
     * @param host
     *            Host String
     * @param message
     *            Message to be sent
     * @throws Exception
     *             Exception in sending a message.
     */
    private void sendMessage(String host, String message) throws Exception {
        Socket socket = null;
        try {
            socket = SocketUtilities.connect(host, MOCK_SERVER, 2);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(message);
            writer.flush();
            writer.close();
        } finally {
            SocketUtilities.close(socket);
        }
    }

    /**
     * Inner class extends Socket.
     */
    protected static class FailOnCloseSocket extends Socket {
        /**
         * Overrides the method close().
         * 
         * @see java.net.Socket#close()
         * @throws IOException
         *             when problem occurs in closing a socket.
         */
        public void close() throws IOException {
            throw new IOException();
        }
    }
}
