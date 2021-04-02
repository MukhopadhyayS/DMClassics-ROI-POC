/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.roi.docx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import word._Application;
import word._Document;

import com.mckesson.eig.roi.docx.dao.ConversionDao;
import com.mckesson.eig.roi.docx.model.LetterTemplate;
import com.mckesson.eig.roi.docx.model.NameValuePair;
import com.mckesson.eig.roi.docx.utils.StringUtil;
import com4j.Variant;

/**
 * @author Eric yu
 *
 */
public class ConversionProcessor {
	private static final String SRC_FOLDER = "rtf";
	private static final String MERGE_FOLDER = "merge";
	private static final String TARGET_FOLDER = "docx";
	private ConversionDao _dao = null;
	private static final Logger logger = Logger.getLogger(ConversionProcessor.class);
	
	public ConversionProcessor(ConversionDao dao) {
		_dao = dao;
    	String srcFolder = new File("template").getAbsolutePath() + File.separator + SRC_FOLDER;
    	deleteFolderIfNeeded(srcFolder);
    	String mergeFolder = new File("template").getAbsolutePath() + File.separator + MERGE_FOLDER;
    	deleteFolderIfNeeded(mergeFolder);
    	String targetFolder = new File("template").getAbsolutePath() + File.separator + TARGET_FOLDER;
    	deleteFolderIfNeeded(targetFolder);
	}
	
	public void displayFolderLocation (boolean review) {
    	String srcFolder = new File("template").getAbsolutePath() + File.separator + SRC_FOLDER;
    	String mergeFolder = new File("template").getAbsolutePath() + File.separator + MERGE_FOLDER;
     	String targetFolder = new File("template").getAbsolutePath() + File.separator + TARGET_FOLDER;
     	try{
	     	logger.info("\nThe original rtf files from database are downloaded under " + new File(srcFolder).getCanonicalPath() + " folder.");
	    	logger.info("The updated merge fields rtf files are under " + new File(mergeFolder).getCanonicalPath() + " folder.");
	    	logger.info("The final docx files are under " + new File(targetFolder).getCanonicalPath() + " folder.\n");
	    	
	    	if (review) {
		    	logger.info("Please review the docx files and make any change if necessary\n");
		    	logger.info("Please type 'Y' when it is ready to upload to database.\n");
	    	}
     	} catch(Exception e) {
     		
     	}
	}
    
    public boolean convert(long id, boolean review) { 
    	try {
	    	// Retreive file from datebase and save it under src
	    	String srcFolder = new File("template").getAbsolutePath() + File.separator + SRC_FOLDER;
	    	createFolderIfNeeded(srcFolder);
	    	String mergeFolder = new File("template").getAbsolutePath() + File.separator + MERGE_FOLDER;
	    	createFolderIfNeeded(mergeFolder);
	    	String targetFolder = new File("template").getAbsolutePath() + File.separator + TARGET_FOLDER;
	    	createFolderIfNeeded(targetFolder);
	    	
	    	String src = srcFolder + File.separator + id;
	    	retrieveFileFromdb(id, src);
	    	String merge = mergeFolder + File.separator + id;
	    	replaceStrings(src, merge);
	    	String target = targetFolder + File.separator + id;
	    	convertDocx(merge, target);
	    	if(!review) {
	    		updateContentFileTodb(id, target);
	    	}
	    	return true;
    	} catch(Exception e) {
    		return false;
    	}
    }
    
	protected static void createFolderIfNeeded(String file) {
		if (!StringUtil.isEmpty(file)) {
			File f = new File(file);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
	}
	
	protected static void deleteFolderIfNeeded(String file) {
		if (!StringUtil.isEmpty(file)) {
			File f = new File(file);
			if (f.exists()) {
				try {
					FileUtils.deleteDirectory(f);
				} catch (IOException e) {
					logger.error("Cannot delete folder: " + f.getAbsolutePath());
				}
			}
		}
	}
	
	protected void retrieveFileFromdb(long id, String src) throws Exception {
		LetterTemplate letterTemplate = _dao.getLetterTempplate(id);
		if (letterTemplate == null) {
			logger.error("Cannot retrieve data from database. id:" + id);
		}
		File target = new File(src);
		byte[] source = letterTemplate.getSource();
		if (source == null) {
			logger.error("No letter template is not in database. id:" + id);
			throw new Exception("No letter template is not in database. id:" + id);
		}
		try {
			FileUtils.writeByteArrayToFile(target, source);
		} catch (IOException e) {
			logger.error("Cannot save data to file. Id:" + id + " File: " + target.getPath() + " - " + e.toString());
		}
	}

	public void updateContentFileTodb(long id) {
    	String targetFolder = new File("template").getAbsolutePath() + File.separator + TARGET_FOLDER;
    	createFolderIfNeeded(targetFolder);
    	String target = targetFolder + File.separator + id  + ".docx";
    	File f = new File(target);
    	if(f.exists()) {
			logger.info("Upload file " + target + " to database.");
    		updateContentFileTodb(id, target);
    	}
	}
	
	public void updateContentFileTodb(long id, String target) {
		try {
			FileInputStream file = new FileInputStream(target);
			_dao.updateLetterTempplate(id, file);
		} catch (IOException e) {
			logger.error("Cannot upload date to database. Id:" + id + " File: " + target + " - " + e.toString());
		}
	}
	
	protected List<NameValuePair> getReplacePairs() {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new NameValuePair("$letter.ShipCharges.ChargeTotal", "$letterShipCharges.Charges"));
        pairs.add(new NameValuePair("$letter.Requestor.Account.", "$letterRequestorAccount."));
        pairs.add(new NameValuePair("$letter.Requestor.Aging.", "$letterRequestorAging."));
        pairs.add(new NameValuePair("$letter.RequestorTxns.", "$letterRequestorTxns."));
        pairs.add(new NameValuePair("$letter.Requestor.", "$letterRequestor."));
        pairs.add(new NameValuePair("$letter.ShippingInfo.", "$letterShippingInfo."));
        pairs.add(new NameValuePair("$letter.InvoiceCharge.", "$letterInvoiceCharge."));
        pairs.add(new NameValuePair("$letter.Releases.", "$letterReleases."));
        pairs.add(new NameValuePair("$letter.InvoiceCharge.", "$letterInvoiceCharge."));
        pairs.add(new NameValuePair("$letter.DocChargeDetails.", "$letterDocChargeDetails."));
        pairs.add(new NameValuePair("$letter.FeeChargeDetails.", "$letterFeeChargeDetails."));
        pairs.add(new NameValuePair("$letter.ShipCharges.", "$letterShipCharges."));
        pairs.add(new NameValuePair("$letter.TxnCharges.", "$letterTxnCharges."));
        pairs.add(new NameValuePair("$letter.Txns.", "$letterTxns."));
        pairs.add(new NameValuePair("$letter.Payment.", "$letterPayment."));
        pairs.add(new NameValuePair("$letter.CreditAdjustment.", "$letterCreditAdjustment."));
        pairs.add(new NameValuePair("$letter.DebitAdjustment.", "$letterDebitAdjustment."));
        pairs.add(new NameValuePair("$letter.DocCharges.", "$letterDocCharges."));
        pairs.add(new NameValuePair("$letter.Patients.", "$letterPatients."));
        pairs.add(new NameValuePair("$letter.Notes.", "$letterNotes."));
        pairs.add(new NameValuePair("$letter.Reasons.", "$letterReasons."));
        pairs.add(new NameValuePair("$letterShipCharges.ChargeTotal", "$letterShipCharges.Charges"));
       return pairs;
    }

	protected void convertDocx(String src, String target) throws Exception {
        _Application app = word.ClassFactory.createApplication();
        String version = app.version();
		logger.info("Word Version: " + version);
        app.visible(false);
        // to open a file
        Variant v = null;
        try {
            v = Variant.getMissing();
            _Document d = app.documents().open(src, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v);

            Variant fileFormat = new Variant();
            fileFormat.set(16);
            Float versionNumber = 12.0f;
            try {
            	versionNumber = Float.valueOf(version);
            } catch(Exception e) {
        		logger.error("Failed to get the Word Version. Use default 2007.");
            }
            if(versionNumber <= 12.0f) {
            	d.saveAs(target, fileFormat, v, v, v, v, v, v, v, v, v, v, v, v, v, v);
            } else {
            	d.saveAs2(target, fileFormat, v, v, v, v, v, v, v, v, v, v, v, v, v, v, 12);
            }
//            app.quit(v, v, v);
        } catch (Exception e) {
        	logger.error("Error: cannot convert " + src + " to docx file format: " + e.getMessage());
        	throw new Exception(e);
        } finally {
        	if (v != null) {
        		app.quit(v, v, v);
        	}
            app = null;
        }
    }
    
    protected void replaceStrings(String srcFile, String targetFile) {
        try {
        	List<NameValuePair> replacePairs = getReplacePairs();
            String content = FileUtils.readFileToString(new File(srcFile), "UTF-8");
            for(NameValuePair replacePair : replacePairs) {
                int idx = content.indexOf(replacePair.getName());
                if(idx > 0) {
                    content = content.replace(replacePair.getName(), replacePair.getValue());
                }
            }
            File tempFile = new File(targetFile);
            FileUtils.writeStringToFile(tempFile, content, "UTF-8");
        } catch (IOException e) {
        	logger.error("Error: cannot replace strings of " + srcFile + ": " + e.getMessage());
           // Simple exception handling, replace with what's necessary for your use case!
            throw new RuntimeException("Generating file failed", e);
        }
    }
}
