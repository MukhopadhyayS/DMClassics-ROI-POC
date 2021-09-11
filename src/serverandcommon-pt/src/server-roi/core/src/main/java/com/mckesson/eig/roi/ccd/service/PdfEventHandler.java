package com.mckesson.eig.roi.ccd.service;

import java.io.OutputStream;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.area.AreaTreeHandler;
import org.apache.fop.fo.flow.Character;

import com.mckesson.dm.core.common.logging.OCLogger;

public class PdfEventHandler extends AreaTreeHandler {
    private static final String HTML_P = "<p>";
    private static final String HTML_P_CLOSE = "</p>";
    private static final String HTML_SPAN = "<span>";
    private static final String HTML_SPAN_OPEN = "<span ";
    private static final String HTML_BR = "<br>";
    private static final String HTML_BR_1 = "<br />";
    private static final String HTML_SPAN_CLOSE = "</span>";
    
    private static final OCLogger LOG = new OCLogger(PdfEventHandler.class);
    

    public PdfEventHandler(FOUserAgent foUserAgent, OutputStream stream)
	    throws FOPException {
	super(foUserAgent, "application/pdf", stream);
	// TODO Auto-generated constructor stub
    }

    public void character(Character c) {
	LOG.debug(c.toString());
    }

    public void characters(char[] data, int start, int length) {
	String origStr = new String(data, start, length);
	String newStr = convert(data, start, length);
	if (!origStr.equals(newStr)) {
	    clearData(data, start, length);
	    char[] cArray = newStr.toCharArray();
	    LOG.debug("FOTEXT:" + newStr);
	    LOG.debug("FOTEXT start:" + start);
	    LOG.debug("FOTEXT: length" + length);
	    System.arraycopy(cArray, 0, data, 0, cArray.length > length ? length : cArray.length);
	}
    }

    private void clearData(char[] data, int start, int length) {
	int len = data.length > length ? data.length : length;
	for (int i = start; i < start + len; i++) {
	    data[i] = ' ';
	}
    }

    private String convert(char[] data, int start, int length) {
	String str = new String(data, start, length);
	String newStr = convertSpecialTag(str);
	return convertHtml(newStr);
    }
    
    private String convertSpecialTag(String s) {
	String result = s.replaceAll(HTML_P, " ");
	result = result.replaceAll(HTML_P.toUpperCase(), " ");
	result = result.replaceAll(HTML_P_CLOSE, " ");
	result = result.replaceAll(HTML_P_CLOSE.toUpperCase(), " ");
	result = result.replaceAll(HTML_BR, java.lang.Character.toString('\n'));
	result = result.replaceAll(HTML_BR_1, java.lang.Character.toString('\n'));
	result = result.replaceAll(HTML_BR.toUpperCase(), java.lang.Character.toString('\n'));
	result = result.replaceAll(HTML_BR_1.toUpperCase(), java.lang.Character.toString('\n'));
	result = result.replaceAll(HTML_SPAN_CLOSE, java.lang.Character.toString('\n'));
	result = result.replaceAll(HTML_SPAN, java.lang.Character.toString('\n'));
	result = result.replaceAll(HTML_SPAN_CLOSE.toUpperCase(),
	                           java.lang.Character.toString('\n'));
	result = result.replaceAll(HTML_SPAN.toUpperCase(), java.lang.Character.toString('\n'));
	result = convertSpecialSpan(result);
	return result;
    }
    
    private String convertSpecialSpan(String s) {
	String result = s;
	int idx = result.indexOf(HTML_SPAN_OPEN);
	while (idx >= 0) {
	    int closeIdx = result.indexOf(">", idx);
	    String beginStr = result.substring(0, idx);
	    String endStr = s.substring(closeIdx + 1);
	    result = beginStr + endStr;
	    idx = result.indexOf(HTML_SPAN_OPEN);	
	}
	return result;
    }
    
    private String convertHtml(String s) {
	return StringEscapeUtils.unescapeHtml4(s);
    }
}
