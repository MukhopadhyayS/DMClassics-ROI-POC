package com.mckesson.eig.utility.converters;

import java.io.InputStream;
import java.util.ArrayList;

import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestImageConverter extends UnitTest {

    private static final String TEST_PDF = "com/mckesson/eig/utility/converters/data/test.pdf";
    private static final String TEST_TIFF = "com/mckesson/eig/utility/converters/data/test.tiff";

    private InputStream _pdfStream;
    private InputStream _tiffStream;

    private ImageConverter _converter;

    public TestImageConverter(String name) {
        super(name);
        _converter = new ImageConverter();
        _pdfStream = createStream(TEST_PDF);
        _tiffStream = createStream(TEST_TIFF);
    }

    protected void setUp() throws Exception {
        super.setUp();

    }

    protected void tearDown() throws Exception {
        _pdfStream = null;
        _tiffStream = null;
        _converter = null;
        super.tearDown();
    }

    private InputStream createStream(String file) {
        return FileLoader.getResourceAsInputStream(file);
    }

    public void testConvertToPDF() {
        try {
            byte[] theBytes = new byte[_tiffStream.available()];
            _tiffStream.read(theBytes);
            int type = _converter.getImageFileType(theBytes);
            assertEquals(type, ImageConverter.TIFF);
            ArrayList<byte[]> result = _converter.convert(theBytes, ImageConverter.PDF);
            assertEquals(result.size(), 2);
            byte[] resultTiff = result.get(0);
            type = _converter.getImageFileType(resultTiff);
            assertEquals(type, ImageConverter.PDF);

        } catch (Exception e) {
            fail();
        }
    }

    public void testConvertToTIFF() {
        try {
            byte[] theBytes = new byte[_pdfStream.available()];
            _pdfStream.read(theBytes);
            int type = _converter.getImageFileType(theBytes);
            assertEquals(type, ImageConverter.PDF);
            ArrayList<byte[]> result = _converter.convert(theBytes, ImageConverter.TIFF);
            assertEquals(result.size(), 2);
            byte[] resultTiff = result.get(0);
            type = _converter.getImageFileType(resultTiff);
            assertEquals(type, ImageConverter.TIFF);

        } catch (Exception e) {
            fail();
        }
    }

    public void testConvertToJPEG() {
        try {
            byte[] theBytes = new byte[_pdfStream.available()];
            _pdfStream.read(theBytes);
            int type = _converter.getImageFileType(theBytes);
            assertEquals(type, ImageConverter.PDF);
           ArrayList<byte[]> result = _converter.convert(theBytes, ImageConverter.JPEG);
            assertEquals(result.size(), 2);
            byte[] resultTiff = result.get(0);
            type = _converter.getImageFileType(resultTiff);
            assertEquals(type, ImageConverter.JPEG);

        } catch (Exception e) {
            fail();
        }
    }

    public void testPageCount() {
        try {
	        byte[] theBytes = new byte[_pdfStream.available()];
	        _pdfStream.read(theBytes);
	        int count = _converter.getPageCount(theBytes);
	        assertEquals(2, count);
	        theBytes = new byte[_tiffStream.available()];
	        _tiffStream.read(theBytes);
	        count = _converter.getPageCount(theBytes);
	        assertEquals(2, count);
         } catch (Exception e) {
            fail();
        }
    }


};
