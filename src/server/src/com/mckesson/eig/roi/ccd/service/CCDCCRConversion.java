package com.mckesson.eig.roi.ccd.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.dom4j.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.io.FileLoader;

public class CCDCCRConversion {

	private static final String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
	private static final String FEATURE1 = "http://xml.org/sax/features/external-general-entities";
	private static final String FEATURE2 = "http://xml.org/sax/features/external-parameter-entities";
	private static final String FEATURE3 = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final String CCD_CONTEXT = "org.hl7.sdtc";
	private static final String CCR_CONTEXT = "org.astm.ccr";
	private static final String CCD_XSLT_FILE = "ccd.xslt.file";
	private static final String CCR_XSLT_FILE = "ccr.xslt.file";
	private static final String CCR = "ccr";
	private static final String CCD = "ccd";

	private JAXBContext _ccdContext;
	private JAXBContext _ccrContext;
	private FopFactory _fopFactory = FopFactory.newInstance(new File(".").toURI());
	private FOUserAgent _foUserAgent = _fopFactory.newFOUserAgent();

	private String _ccrXsltSrc = null;
	private String _ccdXsltSrc = null;

	private synchronized JAXBContext getCCDContext() throws JAXBException {

		if (_ccdContext == null) {
			_ccdContext = JAXBContext.newInstance(CCD_CONTEXT);
		}
		return _ccdContext;
	}

	private synchronized JAXBContext getCCRContext() throws JAXBException {

		if (_ccrContext == null) {
			_ccrContext = JAXBContext.newInstance(CCR_CONTEXT);
		}
		return _ccrContext;
	}

	public Object unmarshalCCD(InputStream is) throws Exception {

		JAXBContext context = getCCDContext();
		return unmarshal(context, is);
	}

	public Object unmarshalCCR(InputStream is) throws Exception {

		JAXBContext context = getCCRContext();
		return unmarshal(context, is);
	}

	private static SAXParserFactory getSAXParserFactory()
			throws SAXNotSupportedException, SAXNotRecognizedException, ParserConfigurationException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setFeature(FEATURE, true);
		spf.setFeature(FEATURE1, false);
		spf.setFeature(FEATURE2, false);
		spf.setFeature(FEATURE3, false);
		return spf;
	}

	public Object unmarshal(JAXBContext jc, InputStreamReader is) throws Exception {
		SAXParserFactory spf = getSAXParserFactory();
		Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(), new InputSource(is));
		Unmarshaller um = jc.createUnmarshaller();
		Object o = um.unmarshal(xmlSource);
		return o;
	}

	public Object unmarshal(JAXBContext jc, InputStream is) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true);
	    // use the factory to create a documentbuilder
	    DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = (Document) builder.parse(is);
		SAXParserFactory spf = getSAXParserFactory();
		Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(), new InputSource(is));
		Unmarshaller um = jc.createUnmarshaller();
		Object o = um.unmarshal(xmlSource);
		return o;
	}

	private InputStreamReader getInputStreamISO8859Encoding(InputStream in) throws UnsupportedEncodingException {
		return new InputStreamReader(in, "ISO-8859-1");
	}

	/*
	 * public String convertToPDF(File srcFile, File targetFile) throws
	 * Exception {
	 * 
	 * OutputStream out = new FileOutputStream(targetFile); if
	 * (isCCDDocument(srcFile, false)) { InputStream is = new
	 * FileInputStream(srcFile); convertCCD(is, out, false); return CCD; } else
	 * if (isCCRDocument(srcFile, false)) { InputStream is = new
	 * FileInputStream(srcFile); convertCCR(is, out, false); return CCR; } else
	 * if (isCCDDocument(srcFile, true)) { InputStream is = new
	 * FileInputStream(srcFile); convertCCD(is, out, true); return CCD; } else
	 * if (isCCRDocument(srcFile, true)) { InputStream is = new
	 * FileInputStream(srcFile); convertCCR(is, out, true); return CCR; } else {
	 * throw new ROIException( ROIClientErrorCodes.CCD_CCR_INVALID_FILE_FORMAT);
	 * } }
	 */

	public String convertToPDF(File srcFile, File targetFile) throws Exception {
		OutputStream out = new FileOutputStream(targetFile);
		if (isCCDDocument(srcFile, false)) {
			InputStream is = new FileInputStream(srcFile);
			StreamSource xsltSource = getCcdXslt();
			convertCCD(is, out, false, xsltSource);
			return CCD;
		} else if (isCCRDocument(srcFile, false)) {
			StreamSource xsltSource = getCcrXslt();
			InputStream is = new FileInputStream(srcFile);
			convertCCR(is, out, false, xsltSource);
			return CCR;
		} else if (isCCDDocument(srcFile, true)) {
			StreamSource xsltSource = getCcdXslt();
			InputStream is = new FileInputStream(srcFile);
			convertCCD(is, out, true, xsltSource);
			return CCD;
		} else if (isCCRDocument(srcFile, true)) {
			StreamSource xsltSource = getCcrXslt();
			InputStream is = new FileInputStream(srcFile);
			convertCCR(is, out, true, xsltSource);
			return CCR;
		} else {
			throw new ROIException(ROIClientErrorCodes.CCD_CCR_INVALID_FILE_FORMAT);
		}
	}

	public String convertToPDF(String styleFile, File srcFile, File targetFile) throws Exception {
		OutputStream out = new FileOutputStream(targetFile);
		StreamSource xsltSource = getXslt(styleFile);

		if (isCCDDocument(srcFile, false)) {
			InputStream is = new FileInputStream(srcFile);
			convertCCD(is, out, false, xsltSource);
			return CCD;
		} else if (isCCRDocument(srcFile, false)) {
			InputStream is = new FileInputStream(srcFile);
			convertCCR(is, out, false, xsltSource);
			return CCR;
		} else if (isCCDDocument(srcFile, true)) {
			InputStream is = new FileInputStream(srcFile);
			convertCCD(is, out, true, xsltSource);
			return CCD;
		} else if (isCCRDocument(srcFile, true)) {
			InputStream is = new FileInputStream(srcFile);
			convertCCR(is, out, true, xsltSource);
			return CCR;
		} else {
			throw new ROIException(ROIClientErrorCodes.CCD_CCR_INVALID_FILE_FORMAT);
		}
	}

	public boolean isCCDDocument(File src, boolean useISO8859Encoding) throws JAXBException {

		InputStream is = null;
		JAXBContext context = getCCDContext();
		Object b = null;
		try {
			is = new FileInputStream(src);
			if (useISO8859Encoding) {
				InputStreamReader reader = getInputStreamISO8859Encoding(is);
				b = unmarshal(context, reader);
			} else {
				b = unmarshal(context, is);
			}
			return b != null;
		} catch (Exception e) {
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
					throw new ROIException();
				}
			}
		}
	}

	public boolean isCCRDocument(File src, boolean useISO8859Encoding) throws JAXBException {

		InputStream is = null;
		JAXBContext context = getCCRContext();
		Object b = null;
		try {
			is = new FileInputStream(src);
			if (useISO8859Encoding) {
				InputStreamReader reader = getInputStreamISO8859Encoding(is);
				b = unmarshal(context, reader);
			} else {
				b = unmarshal(context, is);
			}
			return b != null;
		} catch (Exception e) {
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
					throw new ROIException();
				}
			}
		}
	}

	private InputStream getInputStream(String file) {
		return FileLoader.getResourceAsInputStream(file);
	}

	private synchronized StreamSource getCcdXslt() {

		if (_ccdXsltSrc == null) {
			_ccdXsltSrc = (String) SpringUtil.getObjectFromSpring(CCD_XSLT_FILE);
			if (_ccdXsltSrc == null) {
				throw new ROIException(ROIClientErrorCodes.CCD_UNABLE_TO_GET_XSLT);
			}
		}
		return new StreamSource(getInputStream(_ccdXsltSrc));
	}

	public void setCcrXslt(String ccrXslt) {
		_ccrXsltSrc = ccrXslt;
	}

	public void setCcdXslt(String ccdXslt) {
		_ccdXsltSrc = ccdXslt;
	}

	private synchronized StreamSource getXslt(String styleFile) {
		String s = (String) SpringUtil.getObjectFromSpring(styleFile);
		if (s == null) {
			throw new ROIException(ROIClientErrorCodes.CCR_UNABLE_TO_GET_XSLT);
		}
		return new StreamSource(getInputStream(s));
	}

	private synchronized StreamSource getCcrXslt() {

		if (_ccrXsltSrc == null) {
			_ccrXsltSrc = (String) SpringUtil.getObjectFromSpring(CCR_XSLT_FILE);
			if (_ccrXsltSrc == null) {
				throw new ROIException(ROIClientErrorCodes.CCR_UNABLE_TO_GET_XSLT);
			}
		}
		return new StreamSource(getInputStream(_ccrXsltSrc));
	}

	public void convertCCD(InputStream src, OutputStream out, boolean convertSO8859, StreamSource xsltSource)
			throws UnsupportedEncodingException {

		try {
			OutputStream o = new BufferedOutputStream(out);
			// StreamSource xsltSource = getCcdXslt();
			StreamSource streamSrc = null;
			if (convertSO8859) {
				InputStreamReader reader = getInputStreamISO8859Encoding(src);
				streamSrc = new StreamSource(reader);
			} else {
				streamSrc = new StreamSource(src);
			}
			convertToPDF(streamSrc, xsltSource, o);
		} finally {
			try {
				src.close();
				out.close();
			} catch (Exception e) {
				throw new ROIException();
			}
		}
	}

	public void convertCCR(InputStream src, OutputStream out, boolean convertSO8859, StreamSource xsltSource)
			throws UnsupportedEncodingException {

		try {
			OutputStream o = new BufferedOutputStream(out);
			// StreamSource xsltSource = getCcrXslt();
			StreamSource streamSrc = null;
			if (convertSO8859) {
				InputStreamReader reader = getInputStreamISO8859Encoding(src);
				streamSrc = new StreamSource(reader);
			} else {
				streamSrc = new StreamSource(src);
			}
			convertToPDF(streamSrc, xsltSource, o);
		} finally {
			try {
				src.close();
				out.close();
			} catch (Exception e) {
				throw new ROIException();
			}
		}
	}

	private void convertToPDF(StreamSource srcStream, StreamSource xsltSource, OutputStream out) {

		try {
			// Construct fop with desired output format
			_foUserAgent.setFOEventHandlerOverride(new PdfEventHandler(_foUserAgent, out));
			Fop fop = _fopFactory.newFop(MimeConstants.MIME_PDF, _foUserAgent, out);
			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(xsltSource);
			transformer.setParameter("versionParam", "2.0");

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(srcStream, res);
		} catch (Exception e) {
			throw new ROIException(ROIClientErrorCodes.CCD_CCR_UNABLE_TO_CONVERT_PDF);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				throw new ROIException();
			}
		}
	}

}
