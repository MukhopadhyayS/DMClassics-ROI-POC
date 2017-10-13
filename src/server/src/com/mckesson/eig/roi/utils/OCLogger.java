package com.mckesson.eig.roi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.mckesson.eig.utility.util.StringUtilities;

public class OCLogger {
	private static Logger LOGGER;
	private static final SqlEncoderAdvanced ENCODER_ADVANCED = new SqlEncoderAdvanced();

	public OCLogger(Class<?> clazz) {
		LOGGER = LoggerFactory.getLogger(clazz);
	}

	public String getName() {
		return LOGGER.getName();
	}

	public boolean isTraceEnabled() {
		return LOGGER.isTraceEnabled();
	}

	public void trace(String msg) {
		LOGGER.trace(encodeLogString(msg));
	}

	public void trace(String format, Object arg) {
		LOGGER.trace(encodeLogString(format), encodeLogString(arg != null ? arg.toString() : ""));
	}

	public void trace(String format, Object arg1, Object arg2) {
		LOGGER.trace(encodeLogString(format), encodeLogString(arg1 != null ? arg1.toString() : ""),	encodeLogString(arg2 != null ? arg2.toString() : ""));
	}

	public void trace(String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.trace(encodeLogString(format), encodedArguments);
	}

	public void trace(String msg, Throwable t) {
		LOGGER.trace(encodeLogString(msg), t);
	}

	public boolean isTraceEnabled(Marker marker) {
		return LOGGER.isTraceEnabled(marker);
	}

	public void trace(Marker marker, String msg) {
		LOGGER.trace(marker, encodeLogString(msg));
	}

	public void trace(Marker marker, String format, Object arg) {
		LOGGER.trace(marker, encodeLogString(format), encodeLogString(arg != null ? arg.toString() : ""));
	}

	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		LOGGER.trace(marker, encodeLogString(format), encodeLogString(arg1 != null ? arg1.toString() : ""),	encodeLogString(arg2 != null ? arg2.toString() : ""));
	}

	public void trace(Marker marker, String format, Object... argArray) {
		Object[] encodedArguments = encodeObjectArray(argArray);	
		LOGGER.trace(marker, encodeLogString(format), encodedArguments);
	}

	public void trace(Marker marker, String msg, Throwable t) {
		LOGGER.trace(marker, encodeLogString(msg));
	}

	public boolean isDebugEnabled() {
		return LOGGER.isDebugEnabled();
	}

	public void debug(String msg) {
		LOGGER.debug(encodeLogString(msg));
	}

	public void debug(String format, Object arg) {
		LOGGER.debug(encodeLogString(format), encodeLogString(arg != null ? arg != null ? arg.toString() : "" : ""));
	}

	public void debug(String format, Object arg1, Object arg2) {
		LOGGER.debug(encodeLogString(format), encodeLogString(arg1 != null ? arg1 != null ? arg1.toString() : "" : ""),	encodeLogString(arg1 != null ? arg1 != null ? arg1.toString() : "" : ""));
	}

	public void debug(String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.debug(encodeLogString(format), encodedArguments);
	}

	public void debug(String msg, Throwable t) {
		LOGGER.debug(encodeLogString(msg), t);
	}

	public boolean isDebugEnabled(Marker marker) {
		return LOGGER.isDebugEnabled(marker);
	}

	public void debug(Marker marker, String msg) {
		LOGGER.debug(marker, encodeLogString(msg));
	}

	public void debug(Marker marker, String format, Object arg) {
		LOGGER.debug(marker, encodeLogString(format), encodeLogString(arg != null ? arg != null ? arg.toString() : "" : ""));
	}

	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		LOGGER.debug(marker, encodeLogString(format), encodeLogString(arg1 != null ? arg1.toString() : ""), encodeLogString(arg2 != null ? arg2.toString() : ""));
	}

	public void debug(Marker marker, String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.debug(marker, encodeLogString(format), encodedArguments);
	}

	public void debug(Marker marker, String msg, Throwable t) {
		LOGGER.debug(marker, encodeLogString(msg), t);
	}

	public boolean isInfoEnabled() {
		return LOGGER.isInfoEnabled();
	}

	public void info(String msg) {
		LOGGER.info(encodeLogString(msg));
	}

	public void info(String format, Object arg) {
		LOGGER.info(encodeLogString(format), encodeLogString(arg != null ? arg != null ? arg.toString() : "" : ""));
	}

	public void info(String format, Object arg1, Object arg2) {
		LOGGER.info(encodeLogString(format), encodeLogString(arg1 != null ? arg1 != null ? arg1.toString() : "" : ""), encodeLogString(arg2 != null ? arg2 != null ? arg2.toString() : "" : ""));
	}

	public void info(String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.info(encodeLogString(format), encodedArguments);
	}

	public void info(String msg, Throwable t) {
		LOGGER.info(encodeLogString(msg), t);
	}

	public boolean isInfoEnabled(Marker marker) {
		return LOGGER.isInfoEnabled(marker);
	}

	public void info(Marker marker, String msg) {
		LOGGER.info(marker, encodeLogString(msg));
	}

	public void info(Marker marker, String format, Object arg) {
		LOGGER.info(marker, encodeLogString(format), encodeLogString(arg != null ? arg != null ? arg.toString() : "" : ""));
	}

	public void info(Marker marker, String format, Object arg1, Object arg2) {
		LOGGER.info(marker, encodeLogString(format), encodeLogString(arg1 != null ? arg1 != null ? arg1.toString() : "" : ""), encodeLogString(arg2 != null ? arg2 != null ? arg2.toString() : "" : ""));
	}

	public void info(Marker marker, String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.info(marker, encodeLogString(format), encodedArguments);
	}

	public void info(Marker marker, String msg, Throwable t) {
		LOGGER.info(marker, encodeLogString(msg), t);
	}

	public boolean isWarnEnabled() {
		return LOGGER.isWarnEnabled();
	}

	public void warn(String msg) {
		LOGGER.warn(encodeLogString(msg));
	}

	public void warn(String format, Object arg) {
		LOGGER.warn(encodeLogString(format), encodeLogString(arg != null ? arg != null ? arg.toString() : "" : ""));
	}

	public void warn(String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.warn(encodeLogString(format), encodedArguments);
	}

	public void warn(String format, Object arg1, Object arg2) {
		LOGGER.warn(encodeLogString(format), encodeLogString(arg1 != null ? arg1 != null ? arg1.toString() : "" : ""), encodeLogString(arg2 != null ? arg2 != null ? arg2.toString() : "" : ""));
	}

	public void warn(String msg, Throwable t) {
		LOGGER.warn(encodeLogString(msg), t);
	}

	public boolean isWarnEnabled(Marker marker) {
		return LOGGER.isWarnEnabled(marker);
	}

	public void warn(Marker marker, String msg) {
		LOGGER.warn(marker, encodeLogString(msg));
	}

	public void warn(Marker marker, String format, Object arg) {
		LOGGER.warn(marker, encodeLogString(format), encodeLogString(arg != null ? arg != null ? arg.toString() : "" : ""));
	}

	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		LOGGER.warn(marker, encodeLogString(format), encodeLogString(arg1 != null ? arg1 != null ? arg1.toString() : "" : ""), encodeLogString(arg2 != null ? arg2 != null ? arg2.toString() : "" : ""));
	}

	public void warn(Marker marker, String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.warn(marker, encodeLogString(format), encodedArguments);
	}

	public void warn(Marker marker, String msg, Throwable t) {
		LOGGER.warn(marker, encodeLogString(msg), t);
	}

	public boolean isErrorEnabled() {

		return LOGGER.isErrorEnabled();
	}

	public void error(String msg) {
		LOGGER.error(encodeLogString(msg));
	}

	public void error(String format, Object arg) {
		LOGGER.error(encodeLogString(format), encodeLogString(arg != null ? arg != null ? arg.toString() : "" : ""));
	}

	public void error(String format, Object arg1, Object arg2) {
		LOGGER.error(encodeLogString(format), encodeLogString(arg1 != null ? arg1.toString() : ""), encodeLogString(arg2 != null ? arg2.toString() : ""));
	}

	public void error(String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.error(encodeLogString(format), encodedArguments);
	}

	public void error(String msg, Throwable t) {
		LOGGER.error(encodeLogString(msg), t);
	}

	public boolean isErrorEnabled(Marker marker) {
		return LOGGER.isErrorEnabled(marker);
	}

	public void error(Marker marker, String msg) {
		LOGGER.trace(marker, encodeLogString(msg));
	}

	public void error(Marker marker, String format, Object arg) {
		LOGGER.error(marker, encodeLogString(format), encodeLogString(arg != null ? arg.toString() : ""));
	}

	public void error(Marker marker, String format, Object arg1, Object arg2) {
		LOGGER.error(marker, encodeLogString(format), encodeLogString(arg1 != null ? arg1.toString() : ""),	encodeLogString(arg2 != null ? arg2.toString() : ""));
	}

	public void error(Marker marker, String format, Object... arguments) {
		Object[] encodedArguments = encodeObjectArray(arguments);
		LOGGER.error(marker, encodeLogString(format), encodedArguments);
	}

	public void error(Marker marker, String msg, Throwable t) {
		LOGGER.error(marker, encodeLogString(msg), t);
	}
	
	public Object[] encodeObjectArray(Object... arguments) {
		Object[] encodedArguments = new Object[arguments != null ? arguments.length : 0];
		for (int i = 0; i < arguments.length; i++){
			if (arguments[i] != null) {
				encodedArguments[i] = encodeLogString(arguments[i] != null ? arguments[i].toString() : "");				
			}
		}
		return encodedArguments;
	}
	
	private String encodeLogString(String str) {
		String encodedString = "";
		if (StringUtilities.hasContent(str)) {
			encodedString = SecurityUtilities.cleanEncodeForLogs(str);
			encodedString = ENCODER_ADVANCED.encodeForSql(encodedString);			
		}
		return encodedString;
	}
	
	public String JunitInfo(String msg) {
		return encodeLogString(msg);
	}
	
}
