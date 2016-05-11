///* 
//BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
//
//* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
//* Use of this software and related documentation is governed by a license agreement. 
//* This material contains confidential, proprietary and trade secret information of 
//* McKesson Information Solutions and is protected under United States
//* and international copyright and other intellectual property laws. 
//* Use, disclosure, reproduction, modification, distribution, or storage
//* in a retrieval system in any form or by any means is prohibited without the 
//* prior express written permission of McKesson Information Solutions.
//
//END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
//*/
//
//package com.mckesson.eig.roi.config.util.controller;
//
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import com.mckesson.eig.roi.config.util.ui.SSLConfigUI;
//
///**
// * @author rethinamt
// * @date   Oct 07, 2011
// * @since  HPF 15.2 [ROI]; Oct 07, 2011
// */
//
//public class SSLConfigControllerImpl implements ConfigurationController {
//
//	@Override
//	public Object loadConfigParams() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void saveConfigParams(Object object) {
//		
//		URL resource = SSLConfigControllerImpl.class
//   			.getResource("/config.util.properties");
//		boolean configParams;
//		String strLine;
//		
//		try {
//			
//			configParams = SSLConfigUI.CONFIGPARAMS;
//			FileInputStream fis = new FileInputStream(resource.toURI().getPath());
//            DataInputStream in = new DataInputStream(fis);
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			HashMap<String, String> _maps = new HashMap<String, String>();
//			//Read File Line By Line
//			while ((strLine = br.readLine()) != null)   {
//
//				if (strLine.contains("=") && !(strLine.charAt(0)== '#')) {
//					_maps.put(strLine.substring(0, strLine.indexOf("=")), strLine.substring(strLine.indexOf("=") + 1));
//				}
//			}
//			fis.close();
//			_maps.put("ssl.configured", "" + configParams);
// 			FileWriter writer = new FileWriter(new File(resource.toURI().getPath()));
//			Set propertySet = _maps.entrySet();
//			
//			for (Object o : propertySet) {
//			      Map.Entry entry = (Map.Entry) o;
//			      writer.write(entry.getKey()+ "=" + entry.getValue() + "\n");
//			    }
//			
//			writer.close();
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//		}
//	}
//
//	@Override
//	public void updateClickOnceFiles() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
