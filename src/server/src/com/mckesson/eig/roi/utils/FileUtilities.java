package com.mckesson.eig.roi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;

public final class FileUtilities {

    private static final OCLogger LOG = new OCLogger(FileUtilities.class);

    private FileUtilities() { }

    public static void move(File source, File destDir) throws FileNotFoundException {

        if (!source.exists()) {
    	    throw new FileNotFoundException(source.getAbsolutePath());
    	}

		if(destDir.canRead() && destDir.canWrite())
        {
       		 try {

            	FileUtils.moveFileToDirectory(source, destDir, true);

       		 } catch (Exception e) {
           		 throw new ROIException(e, ROIClientErrorCodes.FILE_OPERATION_FAILED);
       		 }
        }
        else
        {
            try {
                FileUtils.moveFileToDirectory(source, destDir, true);
            }catch (Exception e) {
                throw new ROIException(ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
            }
        }

    }

    public static void moveFile(File source, File destDir) throws FileNotFoundException {

        if (!source.exists()) {
    	    throw new FileNotFoundException(source.getAbsolutePath());
    	}
    	try {
    	    FileUtils.moveFile(source, destDir);
    	} catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.FILE_OPERATION_FAILED);
    	}
    }
    public static void copyFile(File source, File destFile) throws FileNotFoundException {

        if (!source.exists()) {
    	    throw new FileNotFoundException(source.getAbsolutePath());
    	}
    	try {
    	    FileUtils.copyFile(source, destFile, true);
    	} catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.FILE_OPERATION_FAILED);
    	}
    }

    /**
     * moves the given file to the cache directory (directory returned by the property 'java.io.tmpdir')
     * @param source
     * @return
     * @throws FileNotFoundException
     */
    public static String moveFileToCache(File source) throws FileNotFoundException {

        if (!source.exists()) {
    	    throw new FileNotFoundException(source.getAbsolutePath());
    	}
     	String tempfile = UUID.randomUUID().toString();
     	File destFile = new File(DirectoryUtil.getCacheDirectory() +  File.separator + tempfile);
    	try {
    	    FileUtils.moveFile(source, destFile);
    	} catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.FILE_OPERATION_FAILED);
    	}
    	return tempfile;
    }

    /**
     * moves the given file to the temp directory(Directory configured in the eiwdata..EIWT_GLOBAL
     * directory under the global name ROI_LETTER_GENERATION_FILE_DIR)
     * @param source
     * @return
     * @throws FileNotFoundException
     */
    public static String moveFileToTemp(File source) throws FileNotFoundException {

        if (!source.exists()) {
            throw new FileNotFoundException(source.getAbsolutePath());
        }
        String tempfile = UUID.randomUUID().toString();
        File destFile = new File(DirectoryUtil.getTempDirectory() +  File.separator + tempfile);
        try {
            FileUtils.moveFile(source, destFile);
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.FILE_OPERATION_FAILED);
        }
        return tempfile;
    }

    public static void delete(File destFileName) throws FileNotFoundException {

        if (!destFileName.exists()) {
    	    throw new FileNotFoundException(destFileName.getAbsolutePath());
    	}
    	try {
    	    FileUtils.deleteQuietly(destFileName);
    	} catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.FILE_OPERATION_FAILED);
    	}
    }

    /**
     * checks the access to the given directory by creating a new empty file and delete it
     * @param directory
     *
     */
    public static void checkDirectoryAccess(String directory) {

        File file = new File(directory);
        if (file.exists()) {

            try {

                File tempFile = new File(file.getAbsolutePath(), "empty.txt");
                tempFile.createNewFile();
                tempFile.delete();
            } catch (IOException ex) {

                String errorMessage = "Access to share Location is denied:" + file.getAbsolutePath();
                LOG.error(errorMessage);
                throw new ROIException(ex, ROIClientErrorCodes.ACCESS_TO_SHARE_DIRECTORY_DENIED, errorMessage);
            }


        } else if (!file.mkdirs()) {

            String errorMessage = "Share Location does not exists/Access to share Location is denied:"
                                    + file.getAbsolutePath();
            LOG.error(errorMessage);
            throw new ROIException(ROIClientErrorCodes.SHARE_DIRECTORY_DOES_NOT_EXIST, errorMessage);
        }
    }
}
