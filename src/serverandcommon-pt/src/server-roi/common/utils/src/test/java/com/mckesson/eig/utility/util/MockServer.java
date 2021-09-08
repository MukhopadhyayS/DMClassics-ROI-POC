/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import com.mckesson.eig.utility.concurrent.ConcurrencyException;
import com.mckesson.eig.utility.concurrent.MockFutureResult;
import com.mckesson.eig.utility.io.IOUtilities;
import com.mckesson.eig.utility.io.SocketUtilities;

public class MockServer extends Thread {

    private final int _port;
    private final ServerSocket _server;
    private final MockFutureResult<String> _result;

    public MockServer(int port) {
        _port = port;
        _server = SocketUtilities.createServerSocket(_port);
        _result = new MockFutureResult<String>();
        setDaemon(true);
    }

    @Override
	public void run() {
        Socket socket = null;
        BufferedReader reader = null;
        try {
            socket = _server.accept();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            _result.set(reader.readLine());
        } catch (Exception e) {
            _result.setException(e);
        } finally {
            IOUtilities.close(reader);
            SocketUtilities.close(socket);
            close();
        }
    }

    public String getResult() {
        try {
            return _result.get(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new ConcurrencyException(e);
        }
    }

    public byte[] getResultAsBytes() {
        return getResult().getBytes();
    }

    public Throwable getException() {
        return _result.getException();
    }

    public void close() {
        if (!_server.isClosed()) {
            SocketUtilities.close(_server);
        }
    }
}
