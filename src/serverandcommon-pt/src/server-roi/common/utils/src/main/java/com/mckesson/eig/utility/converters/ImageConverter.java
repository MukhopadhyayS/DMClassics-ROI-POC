package com.mckesson.eig.utility.converters;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;

import Snow.Snowbnd;

public class ImageConverter {

    public static final int JPEG = Snow.Defines.JPEG;
    public static final int TIFF = Snow.Defines.TIFF_G4_FAX;
    public static final int PDF = Snow.Defines.PDF;

    public static final int DPI = 200;

    private Snowbnd _snowbound;

    private Snowbnd getSnowbnd() {
        if (_snowbound == null) {
            _snowbound = new Snowbnd();
        }
        return _snowbound;
    }

    public int getPageCount(byte[] fileData) throws Exception {
        Snowbnd snow = getSnowbnd();
    	if (fileData == null) {
    		return 0;
    	}
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                fileData));
        return snow.IMGLOW_get_pages(dis);
    }

    public ArrayList<byte[]> convert(byte[] fileData, int fileType) throws Exception {
        Snowbnd snow = getSnowbnd();
         DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                fileData));
        int origType = getImageFileType(fileData);
        if (origType == PDF) {
        	snow.IMGLOW_set_pdf_input(DPI, 1);
        }
        int numberOfPages = snow.IMGLOW_get_pages(dis);
        ArrayList<byte[]> result = new ArrayList<byte[]>();
        for (int i = 0; i < numberOfPages; i++) {
            snow.IMG_decompress_bitmap(dis, i);
//            if (fileType == TIFF) {
//            	snow.IMG_diffusion_mono();
//            }
            result.add(getByteArrayFromSnowbound(snow, fileType));
        }
        return result;
    }

    private byte[] getByteArrayFromSnowbound(Snowbnd snow, int fileFormat)
            throws Exception {
        snow.setXdpi(DPI);
        snow.setYdpi(DPI);

        Snow.Dib dib = snow.dis_dib;
        Snow.Dib_Head header = dib.header;
        int size = header.biSizeImage;

        byte[] mainImagebuffer = new byte[size * 2];
        int mainImageSize = snow.IMG_save_bitmap(mainImagebuffer, fileFormat);

        if (mainImageSize <= 0) {
            throw new Exception("The size of main image = " + mainImageSize);
        }
        byte[] finalBuffer = new byte[mainImageSize];
        System.arraycopy(mainImagebuffer, 0, finalBuffer, 0, mainImageSize);
        return finalBuffer;
    }

    public int getImageFileType(byte[] fileData) {
        Snowbnd snow = getSnowbnd();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                fileData));
        return snow.IMGLOW_get_filetype(dis);
    }
}
