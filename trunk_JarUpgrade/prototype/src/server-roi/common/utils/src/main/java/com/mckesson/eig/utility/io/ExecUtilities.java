/*
 * Copyright 2010 McKesson Corporation and/or one of its subsidiaries. 
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Michael Macaluso
 * @version 13.5.2
 * @since 13.5.2
 */
public class ExecUtilities {

    /**
     * @param execName
     * @param cmdarray
     * @return int return value from the spawned process
     * @throws IOException
     * @throws InterruptedException
     * @since 13.5.2
     */
    public static int execAndWaitFor(String execName, String[] cmdarray)
            throws IOException, InterruptedException {
        return execAndWaitFor(execName, cmdarray, null);
    }

    /**
     * @param execName
     * @param cmdarray
     * @param envp
     * @return int return value from the spawned process
     * @throws IOException
     * @throws InterruptedException
     * @since 13.5.2
     */
    public static int execAndWaitFor(String execName, String[] cmdarray,
            String[] envp) throws IOException, InterruptedException {
        return execAndWaitFor(execName, cmdarray, null, null);
    }

    /**
     * @param execName
     * @param cmdarray
     * @param envp
     * @param directory
     * @return int return value from the spawned process
     * @throws IOException
     * @throws InterruptedException
     * @since 13.5.2
     */
    public static int execAndWaitFor(String execName, String[] cmdarray,
            String[] envp, File directory) throws IOException,
            InterruptedException {
    	ProcessBuilder builder = new ProcessBuilder(cmdarray);
        builder.directory(directory);
		if(envp != null && envp.length > 0) {
			Map<String, String> envMap = builder.environment();
			for (String env : envp) {
				String split[] = env.split("=");
				if (split.length == 2)
					envMap.put(split[0], split[1]);
			}
		}
        Process exec = builder.start();

        buildInputStreamReaderThread(exec.getInputStream(), execName
                + " Stdin Reader");
        buildInputStreamReaderThread(exec.getErrorStream(), execName
                + " Stderr Reader");

        int returnValue = exec.waitFor();
        return returnValue;
    }

    /**
     * @param execName
     * @param cmdarray
     * @param envp
     * @param directory
     * @return Process return spawned process
     * @throws IOException
     * @throws InterruptedException
     * @since 13.5.2
     */
    public static Process execAndPipeinput(String execName, String[] cmdarray,
            String[] envp, File directory) throws IOException,
            InterruptedException {
    	ProcessBuilder builder = new ProcessBuilder(cmdarray);
        builder.directory(directory);
		if(envp != null && envp.length > 0) {
			Map<String, String> envMap = builder.environment();
			for (String env : envp) {
				String split[] = env.split("=");
				if (split.length == 2)
					envMap.put(split[0], split[1]);
			}
		}
        Process exec = builder.start();

        buildInputStreamReaderThread(exec.getInputStream(), execName
                + " Stdin Reader");
        buildInputStreamReaderThread(exec.getErrorStream(), execName
                + " Stderr Reader");

        return exec;
    }

    /**
     * @param inputStream
     * @param name
     * @since 13.5.2
     */
    private static void buildInputStreamReaderThread(InputStream inputStream,
            String name) {
        Thread stdinReaderThread = new Thread(new InputStreamProcessorRunnable(
                inputStream), name);
        stdinReaderThread.setDaemon(true);
        stdinReaderThread.start();
    }
}
