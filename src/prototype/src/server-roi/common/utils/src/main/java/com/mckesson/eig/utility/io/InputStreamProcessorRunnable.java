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

import java.io.InputStream;

/**
 * <p>
 * Class that performs the job of reading in data from an input stream until it
 * is exhausted. This will be used primarily when exec'ing a process and the
 * stdout and stderr streams MUST be read completely to avoid deadlock.
 * </p>
 * <p>
 * This class design utilizes a number of overridable "hooks" that allow
 * subclasses to change the default behavior.
 * </p>
 * 
 * @author Michael Macaluso
 * @version 13.5.2
 * @since 13.5.2
 */
public class InputStreamProcessorRunnable implements Runnable {
	/**
	 * Source input stream
	 * 
	 * @since 13.5.2
	 */
	private final InputStream _InputStream;

	/**
	 * @param inputStream
	 * @since 13.5.2
	 */
	public InputStreamProcessorRunnable(InputStream inputStream) {
		_InputStream = inputStream;
	}

	public void run() {
		onStart();

		byte[] buffer = null;
		try {
			buffer = doCreateBuffer();

			int lengthRead;
			while (-1 != (lengthRead = _InputStream.read(buffer, 0,
					buffer.length))) {
				onDataRead(buffer, lengthRead);
			}

			onEnd();
		} catch (Throwable t) {
			onThrowable(t);
		} finally {
			IOUtilities.close(_InputStream);
			doDestroyBuffer(buffer);

			onFinally();
		}
	}

	/**
	 * Called before anything else commences
	 * 
	 * @since 13.5.2
	 */
	protected void onStart() {
		// by default do nothing
	}

	/**
	 * @param buffer
	 * @param lengthRead
	 * @since 13.5.2
	 */
	protected void onDataRead(byte[] buffer, int lengthRead) {
		// by default do nothing
	}

	/**
	 * Called after successful processing has completed
	 * 
	 * @since 13.5.2
	 */
	protected void onEnd() {
		// by default do nothing
	}

	/**
	 * Called after processing has raised exception
	 * 
	 * @param t
	 * @since 13.5.2
	 */
	protected void onThrowable(Throwable t) {
		// by default do nothing
	}

	/**
	 * Called after processing finished regardless of success or failure
	 * 
	 * @since 13.5.2
	 */
	protected void onFinally() {
		// by default do nothing
	}

	/**
	 * @return byte[]
	 * @since 13.5.2
	 */
	protected byte[] doCreateBuffer() {
		byte[] buffer = new byte[1024];
		return buffer;
	}

	/**
	 * @param buffer
	 * @since 13.5.2
	 */
	protected void doDestroyBuffer(byte[] buffer) {
		// by default do nothing
	}
}
