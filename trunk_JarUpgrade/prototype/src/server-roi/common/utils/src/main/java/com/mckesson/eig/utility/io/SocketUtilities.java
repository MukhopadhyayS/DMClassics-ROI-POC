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
import java.net.ServerSocket;
import java.net.Socket;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.UtilitiesException;

/**
 * Establish and close Socket connections.
 * 
 */
public final class SocketUtilities {

    /**
     * Logging error messages.
     */
    private static final Log LOG = LogFactory.getLogger(SocketUtilities.class);

    /**
     * sole constructor.
     */
    private SocketUtilities() {
    }

    /**
     * Creates a stream socket and connects it to the specified port number on
     * the named host but allows only for the specified retries.
     * 
     * @param host
     *            the host name, or <code>null</code> for the loopback
     *            address.
     * @param port
     *            the port number.
     * @param retries
     *            number of retries.
     * @return connected <code>Socket</code> object.
     */
    public static Socket connect(String host, int port, int retries) {
        for (int i = 0; i < retries; i++) {
            try {
                return doConnect(host, port);
            } catch (Exception e) {
                // Ignore exception and retry
                LOG.error("Error connecting to socket", e);
            }
        }
        return connect(host, port);
    }

    /**
     * Creates a stream socket and connects it to the specified port number on
     * the named host.
     * 
     * @param host
     *            the host name, or <code>null</code> for the loopback
     *            address.
     * @param port
     *            the port number.
     * @return connected <code>Socket</code> object.
     * 
     */
    public static Socket connect(String host, int port) {
        try {
            return doConnect(host, port);
        } catch (Exception e) {
            throw new UtilitiesException(e);
        }
    }

    /**
     * Creates a stream socket and connects it to the specified port number on
     * the named host.
     * 
     * @param host
     *            the host name, or <code>null</code> for the loopback
     *            address.
     * @param port
     *            the port number.
     * @return <code>Socket</code> object.
     * @throws IOException
     *             if an I/O error occurs when creating the socket.
     */
    private static Socket doConnect(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    /**
     * Creates a server socket, bound to the specified port. A port of
     * <code>0</code> creates a socket on any free port.
     * <p>
     * The maximum queue length for incoming connection indications (a request
     * to connect) is set to <code>50</code>. If a connection indication
     * arrives when the queue is full, the connection is refused.
     * <p>
     * 
     * @param port
     *            the port number.
     * @return <code>ServerSocket</code> object
     */
    public static ServerSocket createServerSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (Exception e) {
            throw new UtilitiesException(e);
        }
    }

    /**
     * Closes this socket.
     * 
     * @param socket
     *            to be closed.
     */
    public static void close(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Throwable t) {
            LOG.error("Error closing socket", t);
        }
    }
    /**
     * Closes this socket.
     * 
     * @param socket
     *            to be closed.
     */

    public static void close(ServerSocket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Throwable t) {
            LOG.error("Error closing server socket", t);
        }
    }
}
