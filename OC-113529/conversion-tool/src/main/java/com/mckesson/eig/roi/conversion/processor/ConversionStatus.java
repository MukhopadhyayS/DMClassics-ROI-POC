/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.conversion.processor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author bhanu
 *
 */
public class ConversionStatus {
	private int freeformFacilitiesTotal;
	private int freeformFacilitiesSuccess;
	private int freeformFacilitiesErrored;
	
	private int supplementalsTotal;
	private int supplementalsSuccess;
	private int supplementalsErrored;
	
	private int requestsTotal;
	private int requestsSuccess;
	private int requestsErrored;
	
	private int requestorLettersTotal;
	private int requestorLettersSuccess;
	private int requestorLettersErrored;
	
	private boolean started;
	private boolean pauseRequested;
	private boolean paused;
	private boolean completed;
	private boolean errored;
	
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private static final ConversionStatus _instance = new ConversionStatus();
	
	private ConversionStatus() {
		// singleton
	}
	
	public static ConversionStatus getInstance() {
		return _instance;
	}

	public int getFreeformFacilitiesTotal() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.freeformFacilitiesTotal;
		} finally {
			readLock.unlock();
		}
	}

	public void setFreeformFacilitiesTotal(int freeformFacilitiesTotal) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.freeformFacilitiesTotal = freeformFacilitiesTotal;
		} finally {
			writeLock.unlock();
		}
	}

	public int getFreeformFacilitiesSuccess() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.freeformFacilitiesSuccess;
		} finally {
			readLock.unlock();
		}
	}

	public void setFreeformFacilitiesSuccess(int freeformFacilitiesSuccess) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.freeformFacilitiesSuccess = freeformFacilitiesSuccess;
		} finally {
			writeLock.unlock();
		}
	}

	public int getFreeformFacilitiesErrored() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.freeformFacilitiesErrored;
		} finally {
			readLock.unlock();
		}
	}

	public void setFreeformFacilitiesErrored(int freeformFacilitiesErrored) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.freeformFacilitiesErrored = freeformFacilitiesErrored;
		} finally {
			writeLock.unlock();
		}
	}

	public int getSupplementalsTotal() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.supplementalsTotal;
		} finally {
			readLock.unlock();
		}
	}

	public void setSupplementalsTotal(int supplementalsTotal) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.supplementalsTotal = supplementalsTotal;
		} finally {
			writeLock.unlock();
		}
	}

	public int getSupplementalsSuccess() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.supplementalsSuccess;
		} finally {
			readLock.unlock();
		}
	}

	public void setSupplementalsSuccess(int supplementalsSuccess) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.supplementalsSuccess = supplementalsSuccess;
		} finally {
			writeLock.unlock();
		}
	}
	
	public void incrementSupplementalsSuccess() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.supplementalsSuccess++;
		} finally {
			writeLock.unlock();
		}
	}

	public int getSupplementalsErrored() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.supplementalsErrored;
		} finally {
			readLock.unlock();
		}
	}

	public void setSupplementalsErrored(int supplementalsErrored) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.supplementalsErrored = supplementalsErrored;
		} finally {
			writeLock.unlock();
		}
	}
	
	public void incrementSupplementalsErrored() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.supplementalsErrored++;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean getStarted() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.started;
		} finally {
			readLock.unlock();
		}
	}

	public void setStarted(boolean started) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.started = started;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean getPauseRequested() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.pauseRequested;
		} finally {
			readLock.unlock();
		}
	}

	public void setPauseRequested(boolean pauseRequested) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.pauseRequested = pauseRequested;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean getPaused() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.paused;
		} finally {
			readLock.unlock();
		}
	}

	public void setPaused(boolean paused) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.paused = paused;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean getCompleted() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.completed;
		} finally {
			readLock.unlock();
		}
	}

	public void setCompleted(boolean completed) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.completed = completed;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean getErrored() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.errored;
		} finally {
			readLock.unlock();
		}
	}

	public void setErrored(boolean errored) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.errored = errored;
		} finally {
			writeLock.unlock();
		}
	}

	public int getRequestsTotal() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.requestsTotal;
		} finally {
			readLock.unlock();
		}
	}

	public void setRequestsTotal(int requestsTotal) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestsTotal = requestsTotal;
		} finally {
			writeLock.unlock();
		}
	}

	public int getRequestsSuccess() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.requestsSuccess;
		} finally {
			readLock.unlock();
		}
	}

	public void setRequestsSuccess(int requestsSuccess) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestsSuccess = requestsSuccess;
		} finally {
			writeLock.unlock();
		}
	}
	
	public void incrementRequestsSuccess() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestsSuccess++;
		} finally {
			writeLock.unlock();
		}
	}

	public int getRequestsErrored() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.requestsErrored;
		} finally {
			readLock.unlock();
		}
	}

	public void setRequestsErrored(int requestsErrored) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestsErrored = requestsErrored;
		} finally {
			writeLock.unlock();
		}
	}
	
	public void incrementRequestsErrored() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestsErrored++;
		} finally {
			writeLock.unlock();
		}
	}

	public int getRequestorLettersTotal() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.requestorLettersTotal;
		} finally {
			readLock.unlock();
		}
	}

	public void setRequestorLettersTotal(int requestorLettersTotal) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestorLettersTotal = requestorLettersTotal;
		} finally {
			writeLock.unlock();
		}
	}

	public int getRequestorLettersSuccess() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.requestorLettersSuccess;
		} finally {
			readLock.unlock();
		}
	}

	public void setRequestorLettersSuccess(int requestorLettersSuccess) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestorLettersSuccess = requestorLettersSuccess;
		} finally {
			writeLock.unlock();
		}
	}
	
	public void incrementRequestorLettersSuccess() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestorLettersSuccess++;
		} finally {
			writeLock.unlock();
		}
	}

	public int getRequestorLettersErrored() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			return this.requestorLettersErrored;
		} finally {
			readLock.unlock();
		}
	}

	public void setRequestorLettersErrored(int requestorLettersErrored) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestorLettersErrored = requestorLettersErrored;
		} finally {
			writeLock.unlock();
		}
	}
	
	public void incrementRequestorLettersErrored() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			this.requestorLettersErrored++;
		} finally {
			writeLock.unlock();
		}
	}
}
