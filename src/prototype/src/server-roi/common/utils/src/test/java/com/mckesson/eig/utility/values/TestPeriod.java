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

package com.mckesson.eig.utility.values;

import java.util.Calendar;

import com.mckesson.eig.utility.exception.InvalidStateException;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestPeriod extends UnitTest {

    private Period _period;
    private Period _other;
    private boolean _wasAddToCalled;

    public TestPeriod(String name) {
        super(name);
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        _period = null;
        _other = null;
        _wasAddToCalled = false;
    }

    @Override
	protected void tearDown() throws Exception {
        _period = null;
        _other = null;
        super.tearDown();
    }

    public void testNullCount() {
        try {
            _period = new TestablePeriod(null);
            fail();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void testCount() {
        _period = new TestablePeriod(THREE);
        assertEquals(THREE, _period.getCount());
    }

    public void testToInteger() {
        _period = new TestablePeriod(FOUR);
        assertEquals(new Integer(FOUR), _period.toInteger());
    }

    public void testInverse() {
        _period = new TestablePeriod(TWO);
        _other = _period.inverse();
        assertEquals(NEG_TWO, _other.getCount());
        assertNotSame(_period, _other);
        assertFalse(_period.equals(_other));
    }

    public void testToString() {
        _period = new TestablePeriod(FOUR);
        assertEquals("4 testable periods", _period.toString());

        _period = new TestablePeriod(1);
        assertEquals("1 testable period", _period.toString());
    }

    public void testEquals() {
        _period = new TestablePeriod(THREE);
        _other = new TestablePeriod(THREE);

        assertTrue(_period.equals(_other));
        assertTrue(_other.equals(_period));

        _period = new TestablePeriod(FIVE);
        _other = new TestablePeriod(SIX);

        assertFalse(_period.equals(_other));
        assertFalse(_other.equals(_period));

        assertFalse(_period.equals(null));
        assertFalse(_period.equals("foo"));
    }

    public void testCompareTo() {
        _period = new TestablePeriod(THREE);
        _other = new TestablePeriod(THREE);

        assertEquals(0, _period.compareTo(_other));
        assertEquals(0, _other.compareTo(_period));

        _period = new TestablePeriod(FIVE);
        _other = new TestablePeriod(SIX);

        assertEquals(-1, _period.compareTo(_other));
        assertEquals(1, _other.compareTo(_period));
    }

    public void testHashCode() {
        _period = new TestablePeriod(THREE);
        _other = new TestablePeriod(THREE);
        assertEquals(_period.hashCode(), _other.hashCode());

        _period = new TestablePeriod(SEVEN);
        _other = new TestablePeriod(EIGHT);
        assertTrue(_period.hashCode() != _other.hashCode());
    }

    public void testAddTo() {
        _period = new TestablePeriod(THREE);
        DateTime start = new DateTime("11/15/02 3:06 pm");
        DateTime end = new DateTime("11/15/02 6:06 pm");
        assertNotSame(start, _period.addTo(start));
        assertEquals(end, _period.addTo(start));
        assertTrue(_wasAddToCalled);
    }

    public void testSubtractFrom() {
        _period = new TestablePeriod(THREE);
        DateTime start = new DateTime("11/15/02 3:06 pm");
        DateTime end = new DateTime("11/15/02 12:06 pm");
        assertNotSame(start, _period.subtractFrom(start));
        assertEquals(end, _period.subtractFrom(start));
        assertTrue(_wasAddToCalled);
    }

    public void testClone() {
        _period = new TestablePeriod(TEN);
        Period clone = (Period) _period.clone();
        Period copy = _period.copy();
        assertNotSame(_period, clone);
        assertNotSame(_period, copy);
        assertNotSame(clone, copy);
        assertEquals(_period, clone);
        assertEquals(_period, copy);
        assertEquals(copy, clone);

        try {
            new TestablePeriod(2) {
				private static final long serialVersionUID = 1L;

				@Override
				protected Object defaultClone()
                        throws CloneNotSupportedException {
                    throw new CloneNotSupportedException();
                }
            } .clone();
            fail();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    public void testToMillis() {
        _period = new TestablePeriod(THREE);
        final int millisec = 10800000;
        assertEquals(millisec, _period.toMillis());
    }

    private class TestablePeriod extends Period {

		private static final long serialVersionUID = 1L;

		protected TestablePeriod(int count) {
            super(count);
        }

        protected TestablePeriod(Integer count) {
            super(count);
        }

        @Override
		public void addTo(Calendar c) {
            _wasAddToCalled = true;
            c.add(Calendar.HOUR, toIntValue());
        }

        @Override
		protected String getName() {
            return "testable period";
        }
    }
}
