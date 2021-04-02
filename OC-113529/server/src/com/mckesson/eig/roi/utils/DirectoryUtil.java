package com.mckesson.eig.roi.utils;

import java.io.File;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.utility.util.SpringUtilities;

public final class DirectoryUtil {

    private DirectoryUtil() { }

    public static String getCacheDirectory() {

        String cacheDir = System.getProperty("java.io.tmpdir") + File.separatorChar + "FileCache";

        File cache = new File(cacheDir);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cacheDir;
    }

    /**
     * retrieves the temp directory location
     * @return
     */
    public static String getTempDirectory() {

        ROIDAO dao = (ROIDAO) SpringUtilities.getInstance().getBeanFactory().getBean("SysParamDAO");
        String location = dao
                .retrieveEIWDATAConfiguration(ROIConstants.TEMP_DIRECTORY_LOCATION_KEY);

        if (location == null) {
            throw new ROIException(ROIClientErrorCodes.TEMP_DIRECTORY_CONFIG_MISSING);
        }

        //FileUtilities.checkDirectoryAccess(location);

        File cache = new File(location);
        if (!cache.exists()) {
            cache.mkdirs();
        }

        return cache.getAbsolutePath();
    }
}
