package com.mckesson.eig.roi.ccd.service;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.mckesson.eig.common.filetransfer.services.BaseFileUploader;
import com.mckesson.eig.roi.utils.DirectoryUtil;

public class CcdFileUploadServlet extends BaseFileUploader {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected String getFileName() {
        UUID uid = UUID.randomUUID();
        return uid.toString();
    }

    protected String getCacheDirectory(HttpSession session) {
	return DirectoryUtil.getCacheDirectory();
    }
}
