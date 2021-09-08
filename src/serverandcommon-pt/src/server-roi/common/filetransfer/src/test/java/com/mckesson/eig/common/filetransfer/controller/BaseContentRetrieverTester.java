/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.common.filetransfer.controller;

import java.io.File;
import java.util.HashMap;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.dm.core.common.logging.OCLogger;

public class BaseContentRetrieverTester extends BaseContentRetriever {
    private static final OCLogger LOG = new OCLogger(BaseContentRetrieverTester.class);

    public static final String SMALL_FILE = "file10b";
    private static final String SMALL_FILE_NAME =
        "com/mckesson/eig/common/filetransfer/controller/file10b.txt";
    public static final String MED_FILE = "file20kb";
    private static final String MED_FILE_NAME =
        "com/mckesson/eig/common/filetransfer/controller/SWE_Levels.xls";
    public static final String LARGE_FILE = "file12mb";
    private static final String LARGE_FILE_NAME =
        "com/mckesson/eig/common/filetransfer/controller/EAUserGuide.pdf";
    public static final String NOT_EXIST_FILE = "doesNotExist";
    private static final String NOT_EXIST_FILE_NAME =
        "com/mckesson/eig/common/filetransfer/controller/doesNotExist.txt";
    public static final String ZERO_BYTE_FILE = "zeroByteFile";
    private static final String ZERO_BYTE_FILE_NAME =
        "com/mckesson/eig/common/filetransfer/controller/zeroByteFile.txt";
    public static final String FILE_TYPE_ASCII = "FILE_TYPE_ASCII";
    private static final String FILE_TYPE_ASCII_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_ASCII.txt";
    public static final String FILE_TYPE_BMP = "FILE_TYPE_BMP";
    private static final String FILE_TYPE_BMP_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_BMP.bmp";
    public static final String FILE_TYPE_EXCEL = "FILE_TYPE_EXCEL";
    private static final String FILE_TYPE_EXCEL_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_EXCEL.xls";
    public static final String FILE_TYPE_GIF = "FILE_TYPE_GIF";
    private static final String FILE_TYPE_GIF_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_GIF.gif";
    public static final String FILE_TYPE_HTML = "FILE_TYPE_HTML";
    private static final String FILE_TYPE_HTML_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_HTML.html";
    public static final String FILE_TYPE_JPEG = "FILE_TYPE_JPEG";
    private static final String FILE_TYPE_JPEG_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_JPEG.jpg";
    public static final String FILE_TYPE_PDF = "FILE_TYPE_PDF";
    private static final String FILE_TYPE_PDF_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PDF.pdf";
    public static final String FILE_TYPE_PNG = "FILE_TYPE_PNG";
    private static final String FILE_TYPE_PNG_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PNG.png";
    public static final String FILE_TYPE_POWERPOINT = "FILE_TYPE_POWERPOINT";
    private static final String FILE_TYPE_POWERPOINT_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_POWERPOINT.ppt";
    public static final String FILE_TYPE_PROJECT = "FILE_TYPE_PROJECT";
    private static final String FILE_TYPE_PROJECT_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PROJECT.mpp";
    public static final String FILE_TYPE_RTF = "FILE_TYPE_RTF";
    private static final String FILE_TYPE_RTF_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_RTF.rtf";
    public static final String FILE_TYPE_TIFF = "FILE_TYPE_TIFF";
    private static final String FILE_TYPE_TIFF_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_TIFF.tif";
    public static final String FILE_TYPE_UNICODE = "FILE_TYPE_UNICODE";
    private static final String FILE_TYPE_UNICODE_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_UNICODE.txt";
    public static final String FILE_TYPE_VISIO = "FILE_TYPE_VISIO";
    private static final String FILE_TYPE_VISIO_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_VISIO.vsd";
    public static final String FILE_TYPE_WORD = "FILE_TYPE_WORD";
    private static final String FILE_TYPE_WORD_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_WORD.doc";

    public static final String FILE_TYPE_JBIG = "FILE_TYPE_JBIG";
    private static final String FILE_TYPE_JBIG_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_JBIG";
    public static final String FILE_TYPE_JBIG2 = "FILE_TYPE_JBIG2";
    private static final String FILE_TYPE_JBIG2_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_JBIG2";
    public static final String FILE_TYPE_JPEG2000 = "FILE_TYPE_JPEG2000";
    private static final String FILE_TYPE_JPEG2000_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_JPEG2000";
    public static final String FILE_TYPE_TIFF_G3 = "FILE_TYPE_TIFF_G3";
    private static final String FILE_TYPE_TIFF_G3_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_TIFF_G3";
    public static final String FILE_TYPE_TIFF_G4 = "FILE_TYPE_TIFF_G4";
    private static final String FILE_TYPE_TIFF_G4_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_TIFF_G4";
    public static final String FILE_TYPE_LOTUS_DOS = "FILE_TYPE_LOTUS_DOS";
    private static final String FILE_TYPE_LOTUS_DOS_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_LOTUS_DOS";
    public static final String FILE_TYPE_LOTUS_WINDOWS = "FILE_TYPE_LOTUS_WINDOWS";
    private static final String FILE_TYPE_LOTUS_WINDOWS_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_LOTUS_WINDOWS";
    public static final String FILE_TYPE_SMART_SUITE = "FILE_TYPE_SMART_SUITE";
    private static final String FILE_TYPE_SMART_SUITE_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_SMART_SUITE";
    public static final String FILE_TYPE_PCL_1 = "FILE_TYPE_PCL_1";
    private static final String FILE_TYPE_PCL_1_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PCL_1";
    public static final String FILE_TYPE_PCL_2 = "FILE_TYPE_PCL_2";
    private static final String FILE_TYPE_PCL_2_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PCL_2";
    public static final String FILE_TYPE_PCL_3 = "FILE_TYPE_PCL_3";
    private static final String FILE_TYPE_PCL_3_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PCL_3";
    public static final String FILE_TYPE_PCL_4 = "FILE_TYPE_PCL_4";
    private static final String FILE_TYPE_PCL_4_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PCL_4";
    public static final String FILE_TYPE_PCL_5 = "FILE_TYPE_PCL_5";
    private static final String FILE_TYPE_PCL_5_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PCL_5";
    public static final String FILE_TYPE_PCL_6 = "FILE_TYPE_PCL_6";
    private static final String FILE_TYPE_PCL_6_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PCL_6";
    public static final String FILE_TYPE_PDFA = "FILE_TYPE_PDFA";
    private static final String FILE_TYPE_PDFA_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PDFA";
    public static final String FILE_TYPE_PS1 = "FILE_TYPE_PS1";
    private static final String FILE_TYPE_PS1_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PS1";
    public static final String FILE_TYPE_PS2 = "FILE_TYPE_PS2";
    private static final String FILE_TYPE_PS2_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PS2";
    public static final String FILE_TYPE_PS3 = "FILE_TYPE_PS3";
    private static final String FILE_TYPE_PS3_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_PS3";
    public static final String FILE_TYPE_AVI = "FILE_TYPE_AVI.AVI";
    private static final String FILE_TYPE_AVI_NAME =
        "com/mckesson/eig/common/filetransfer/controller/FILE_TYPE_AVI.avi";

    private HashMap<String, String> _files = new HashMap<String, String>();

    public BaseContentRetrieverTester() {
        _files.put(SMALL_FILE, SMALL_FILE_NAME);
        _files.put(MED_FILE, MED_FILE_NAME);
        _files.put(LARGE_FILE, LARGE_FILE_NAME);
        _files.put(NOT_EXIST_FILE, NOT_EXIST_FILE_NAME);
        _files.put(ZERO_BYTE_FILE, ZERO_BYTE_FILE_NAME);
        _files.put(FILE_TYPE_ASCII, FILE_TYPE_ASCII_NAME);
        _files.put(FILE_TYPE_BMP, FILE_TYPE_BMP_NAME);
        _files.put(FILE_TYPE_EXCEL, FILE_TYPE_EXCEL_NAME);
        _files.put(FILE_TYPE_GIF, FILE_TYPE_GIF_NAME);
        _files.put(FILE_TYPE_HTML, FILE_TYPE_HTML_NAME);
        _files.put(FILE_TYPE_JPEG, FILE_TYPE_JPEG_NAME);
        _files.put(FILE_TYPE_PDF, FILE_TYPE_PDF_NAME);
        _files.put(FILE_TYPE_PNG, FILE_TYPE_PNG_NAME);
        _files.put(FILE_TYPE_POWERPOINT, FILE_TYPE_POWERPOINT_NAME);
        _files.put(FILE_TYPE_PROJECT, FILE_TYPE_PROJECT_NAME);
        _files.put(FILE_TYPE_RTF, FILE_TYPE_RTF_NAME);
        _files.put(FILE_TYPE_TIFF, FILE_TYPE_TIFF_NAME);
        _files.put(FILE_TYPE_UNICODE, FILE_TYPE_UNICODE_NAME);
        _files.put(FILE_TYPE_VISIO, FILE_TYPE_VISIO_NAME);
        _files.put(FILE_TYPE_WORD, FILE_TYPE_WORD_NAME);
        _files.put(FILE_TYPE_JBIG, FILE_TYPE_JBIG_NAME);
        _files.put(FILE_TYPE_JBIG2, FILE_TYPE_JBIG2_NAME);
        _files.put(FILE_TYPE_JPEG2000, FILE_TYPE_JPEG2000_NAME);
        _files.put(FILE_TYPE_TIFF_G3, FILE_TYPE_TIFF_G3_NAME);
        _files.put(FILE_TYPE_TIFF_G4, FILE_TYPE_TIFF_G4_NAME);
        _files.put(FILE_TYPE_LOTUS_DOS, FILE_TYPE_LOTUS_DOS_NAME);
        _files.put(FILE_TYPE_LOTUS_WINDOWS, FILE_TYPE_LOTUS_WINDOWS_NAME);
        _files.put(FILE_TYPE_SMART_SUITE, FILE_TYPE_SMART_SUITE_NAME);
        _files.put(FILE_TYPE_PCL_1, FILE_TYPE_PCL_1_NAME);
        _files.put(FILE_TYPE_PCL_2, FILE_TYPE_PCL_2_NAME);
        _files.put(FILE_TYPE_PCL_3, FILE_TYPE_PCL_3_NAME);
        _files.put(FILE_TYPE_PCL_4, FILE_TYPE_PCL_4_NAME);
        _files.put(FILE_TYPE_PCL_5, FILE_TYPE_PCL_5_NAME);
        _files.put(FILE_TYPE_PCL_6, FILE_TYPE_PCL_6_NAME);
        _files.put(FILE_TYPE_PDFA, FILE_TYPE_PDFA_NAME);
        _files.put(FILE_TYPE_PS1, FILE_TYPE_PS1_NAME);
        _files.put(FILE_TYPE_PS2, FILE_TYPE_PS2_NAME);
        _files.put(FILE_TYPE_PS3, FILE_TYPE_PS3_NAME);
        _files.put(FILE_TYPE_AVI, FILE_TYPE_AVI_NAME);
    }
    @Override
    public boolean isValidUser(String user, String password, String ticket) {
        return StringUtilities.hasContent(user);
    }

    @Override
    public String retrieveContent(BaseFileTransferData data) {

        String contentId = data.getFileID();
        File file = new File(contentId);

        if (file.exists()) {
            return (file.getAbsolutePath());
        }

        if (_files.containsKey(contentId)) {

            LOG.debug(_files.get(contentId));
            file = new File(ClassLoader.getSystemResource(_files.get(contentId)).getFile());
            return (file == null ? "" : file.getAbsolutePath());
        }

        return ("");
    }
}
