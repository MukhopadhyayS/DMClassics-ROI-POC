/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.reports.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mckesson.eig.roi.admin.dao.SysParamDAO;
import com.mckesson.eig.roi.admin.model.SysParam;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
*
* @author OFS
* @date   Oct 14, 2008
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
public final class ROIReportUtil {

    private static final Log LOG = LogFactory.getLogger(ROIReportUtil.class);

    public static final String DATEFORMAT = "MM/dd/yyyy";
    public static final String DATETIMEFORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat(DATEFORMAT);
    public static final String PROCEDURE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static int ONE_DAY = 1000 * 60 * 60 * 24;

    private ROIReportUtil() { }

    /**
     * Method to get the string value for the given key
     * from the HashMap
     *
     * @param params
     * @param key
     * @return
     */
	@SuppressWarnings("rawtypes")
    public static String getStringParam(Map params, String key) {

	    Object object = params.get(key);
	    if (null == object) {
	        return null;
	    }

        return ((String[]) object)[0];
    }

    /**
     * Method to get the Integer value for the given key
     * from the HashMap
     *
     * @param params
     * @param key
     * @return
     */
	@SuppressWarnings("rawtypes")
    protected static Integer getIntegerParam(Map params, String key) {

	    Object object = params.get(key);
        if (null == object) {
            return null;
        }
        return new Integer(((String[]) object)[0]);
    }

    /**
     * Method to get the String value in the given position
     * from the object array
     *
     * @param values
     * @param position
     * @return
     */
    public static String getStringValue(Object[] values, int position) {
        return (String) values[position];
    }

    /**
     * Method to get the Integer value in the given position
     * from the object array
     *
     * @param values
     * @param position
     * @return
     */
    public static Integer getIntegerValue(Object[] values, int position) {
        return (Integer) values[position];
    }

    /**
     * Method to get the Double/Decimal value in the given position
     * from the object array
     *
     * @param values
     * @param position
     * @return
     */
    public static Double getDecimalValue(Object[] values, int position) {
        return (Double) values[position];
    }

    /**
     * Method to get the Decimal value in the given position
     * from the object array
     *
     * @param values
     * @param position
     * @return
     */
    public static BigDecimal getBigDecimalValue(Object[] values, int position) {
        return (BigDecimal) values[position];
    }

    /**
     * Method to get the Date value in the given position
     * from the object array
     *
     * @param values
     * @param position
     * @return
     */
    public static Date getDateValue(Object[] values, int position) {
        return (Date) values[position];
    }

    /**
     * This method converts the date in the string format to the date object.
     *
     * @param stringDate
     *          Date as String in the default DATEFORMAT.
     *
     * @return date
     *          Respective date object.
     */
    public static Date parseDate(String stringDate) throws IOException {

        Date date = null;
        try {
            date = DATEFORMATTER.parse(stringDate);
        } catch (ParseException e) {
            LOG.error("Invalid Date");
            throw new IOException("Invalid Date");
        }
        return date;
    }

    /**
     * This method is used to convert the end date which is in string format to date format. The
     * created date object will contain the time set to 23:59 in that particular date.
     *
     * @param endDateString
     *          String value of end date.
     *
     * @return endDate
     *          Date object of end date.
     */
    public static Date convertToEndDate(String endDateString) throws IOException {

        final int hourOfDay = 23;
        final int minute = 59;
        final int second = 59;

        Date endDate = parseDate(endDateString);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(endDate);

        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        return cal.getTime();
    }

    /**
     * This method is used to convert the start date which is in string format to date format. The
     * created date object will contain the time set to 00:00 in that particular date.
     *
     * @param startDateString
     *          String value of start date.
     *
     * @return startDate
     *          Date object of start date.
     */
    public static Date convertToStartDate(String startDateString) throws IOException {
        return parseDate(startDateString);
    }

    /**
     * This method formats a date object in to US date format and returns the date as a
     * Formatted String
     *
     * @param date
     *          Date to be converted as String
     *
     * @param dateFormat
     *          Format in which the date has to be formatted.
     *
     * @return formated date
     *          Formatted date is returned as a String.
     */
    public static String formatDate(Date date, String format) {

        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Method to get the String value in the given position
     * from the object array and return it as a String value to append
     * in csv
     *
     * @param values
     * @param position
     * @return
     */
    public static String getStringCsv(Object[] values, int position) {
        String value = getStringValue(values, position);
        if (value == null) {
            return "";
        }

        String delimeter = "\"";
        String csvString = StringUtilities.getCSV(value);

        // Suppress doublequotes when the CSV data contains
        // comma . Ex ""Test, ROI"" it return as Test,ROI.
        int index = csvString.indexOf(delimeter);
        String data =  index == -1 ? csvString
                                   : (index > 0) ? csvString
                                                  : csvString.substring(index + 1,
                                                                csvString.lastIndexOf(delimeter));

        // Adding double quotes for all the string CSV value.
        return "\"" + data + "\"";
    }

    /**
     * This method is used to get the resolved unit CSV string
     * If the number has the "," then it will be enclosed with double quotes.
     * Ex. "10.00" = 10.00 or "$10,000" = "$10,000"
     * @param values input object
     * @param positions position
     * @return csv string
     */
    public static String getUnitResolvedCsv(Object[] values, int positions) {

        String value = getUnQuotedStringCsv(values, positions);
        return (value.contains(",")) ? "\"" + value + "\"" : value;
    }

    /**
     * This method is used to strip the double quotes in string.
     * @param values
     * @param positions
     * @return
     */
    public static String getUnQuotedStringCsv(Object[] values, int positions) {
        String value = getStringCsv(values, positions);
        return ("".equals(value)) ? ""
                                  :  value.substring(value.indexOf("\"") + 1,
                                                     value.lastIndexOf("\""));
    }

    /**
     * Method to get the Date value in the given position
     * from the object array and return it as a String value to append
     * in csv
     * @param values
     * @param position
     * @return
     */
    public static String getDateCsv(Object[] values, int position) {
        Date value = getDateValue(values, position);
        return (null == value) ? "" : StringUtilities.getCSV(formatDate(value, DATEFORMAT));
    }

    /**
     * Method to get the Date value in the given position
     * from the object array and return it as a String value to append
     * in csv
     * @param values
     * @param position
     * @return
     */
    public static String getDateTimeCsv(Object[] values, int position) {
        Date value = getDateValue(values, position);
        return (null == value) ? "" : StringUtilities.getCSV(formatDate(value, DATETIMEFORMAT));
    }

    /**
     * Method to get the Integer value in the given position
     * from the object array and return it as a String value to append
     * in csv
     *
     * @param values
     * @param position
     * @return
     */
    public static String getIntegerCsv(Object[] values, int position) {
        Integer value = getIntegerValue(values, position);
        if (value == null) {
            return "";
        }
        return StringUtilities.getCSV(value.toString());
    }

    /**
     * Method to get the Double/Decimal value in the given position
     * from the object array and return it as a String value to append
     * in csv
     *
     * @param values
     * @param position
     * @return
     */
    public static String getDecimalCsv(Object[] values, int position) {
        Double value = getDecimalValue(values, position);
        if (value == null) {
            return "0.00";
        }
        return StringUtilities.getCSV(Double.toString(value));
    }

    /**
     * Method to get the Decimal value in the given position
     * from the object array and return it as a String value to append
     * in csv
     *
     * @param values
     * @param position
     * @return
     */
    public static String getBigDecimalCsv(Object[] values, int position) {

        BigDecimal value = getBigDecimalValue(values, position);
        if (value == null) {
            return "0.00";
        }
        return StringUtilities.getCSV(value.toString());
    }

    /**
     * Method to get the Integer value in the given position
     * from the object array and return it as a String value to append
     * in csv
     *
     * @param values
     * @param position
     * @return
     */
    public static String getBalanceCsv(Object[] values, int position) {
        String value = getStringValue(values, position);
        if (value == null) {
            return "$0.00";
        }
        return StringUtilities.getCSV(value);
    }

    /**
     * Method to construct XML from the given csv string
     * to be passed as parameter for Stored Procedures
     *
     * @param values
     * @param position
     * @return
     */
    public static String constructListContentXML(String csv, String tagName) {

        if (StringUtilities.isEmpty(csv)) {
            return "";
        }

        String[] values = csv.split(",");
        return constructListContentXML(tagName, values);
    }

    /**
     * CR# 375059 - Fix
     * @param tagName
     * @param values
     * @return
     */
    public static String constructListContentXML(String tagName, String[] values) {

        String xml = "<ListContent>\n";
        for (String value : values) {
            xml = xml + "<" + tagName + " value=\"" + value + "\"/>\n";
        }
        xml = xml + "</ListContent>";
        return xml;
    }

    public static Date getCurrentDate() {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setCalendar(cal);

        try {
            return convertToEndDate(dateFormat.format(cal.getTime()));
        } catch (IOException e) {

            LOG.error(e);
            throw new ROIException();
        }
    }

    /**
     * Method to get the Number of WeekEndDays
     *
     * @param reqDate
     * @param fullFilledDate
     * @return
     */
    public static int getNoOfWeekEndDays(Date reqDate, Date fullFilledDate) {
        int count = 0;

        List<Date> dates = getDates(reqDate, fullFilledDate);
        for (int i = 0; i < dates.size(); i++) {
            Date date = dates.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String day = cal.getDisplayName(Calendar.DAY_OF_WEEK,
                    Calendar.LONG, Locale.getDefault());
            SysParamDAO daoSysparam = (SysParamDAO) getDAO("SysParamDAO");
            SysParam sysParam = daoSysparam.getDayStatusObj(day);
            if (ROIConstants.WEEKENDDAY.equalsIgnoreCase(sysParam.getGlobalVariant())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Method to get the list of dates between requested and fulfilled dates
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    private static List<Date> getDates(Date fromDate, Date toDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fromDate);
        while (calendar.getTime().before(toDate)) {
            Date resultado = calendar.getTime();
            dates.add(resultado);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * Method to calculate the Number of Business Days
     *
     * @param reqDate
     * @param fullFilledDate
     * @return
     */
    public static double calculateBusDays(Date reqDate, Date fullFilledDate) {
        double diffInDays = ((double)(fullFilledDate.getTime() - reqDate.getTime()) / (ONE_DAY));
        int numberOfWeekEndDays = getNoOfWeekEndDays(reqDate, fullFilledDate);
        double businessDays;
        if (diffInDays > numberOfWeekEndDays) {
            businessDays = diffInDays - numberOfWeekEndDays;
        } else {
            businessDays = 0;
        }
        return businessDays;
    }

    protected static ROIDAO getDAO(String daoName) {
        return (ROIDAO) SpringUtilities.getInstance().getBeanFactory().
                                                          getBean(daoName);
    }

    /**
     * Method to construct List from the given csv string
     * to be passed as parameter for Named query
     *
     * @param values
     * @return list object
     */
    public static List<String> constructListContent(String csv) {

        if (StringUtilities.isEmpty(csv)) {
            return null;
        }

        String[] values = csv.split(",");
        List<String> list = new ArrayList<String>();
        for (String value : values) {
            list.add(value);
        }
        return list;
    }
    
    /**
     * This method encloses the passed string within quotes and returns it.
     */
    public static String addDoubleQuotes(String value) {
        return value == "" ? "" : "\"" + value + "\"";
    }
    
    /**
     * This method will convert from ASCCI value to respective text in the given string.
     * @param str
     * @return String 
     */
    public static String convertASCIIToNormalText(String str) {

        if (StringUtilities.isEmpty(str))
            return str;
        
        String text;
        text = str.replaceAll("&amp;", "&");
        text = text.replaceAll("&quot;", "\"");
        text = text.replaceAll("&#60;", "<");
        text = text.replaceAll("&#62;", ">");

        return text;
    }
    
    /**
     * This method will convert from ASCCI value to respective text in the given list of elements.
     * @param elementList
     * @return String[] 
     */
    public static String[] convertASCIIValues(String[] elementList) {
        
        if (null == elementList || 0 == elementList.length)
            return elementList;
        
        String[] elements = new String[elementList.length];
        for (int i=0; i<elementList.length; i++) {
            elements[i] = ROIReportUtil.convertASCIIToNormalText(elementList[i]);
        }
        
        return elements;
    }
}
