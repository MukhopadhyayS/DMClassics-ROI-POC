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

package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.base.api.ROIConstants;


/**
 * @author Nithyanandhan
 * @date   DEC 31, 2009
 * @since  HPF 13.1 [ROI]; Jul 23, 2008
 */
public class MatchCriteriaList implements Serializable {

    private List<MatchCriteria> _matchCriteria;
    public static final String NAME_LIST = "NAMES";
    public static final String FIRST_NAME_LIST = "FIRSTNAMES";
    public static final String SSN_LIST  = "SSNS";
    public static final String EPN_LIST  = "EPNS";
    public static final String DOB_LIST  = "DOBS";
    public static final String MRN_LIST  = "MRNS";
    public static final String FACILITY_LIST  = "FACS";

    public List<MatchCriteria> getMatchCriteria() { return _matchCriteria; }
    public void setMatchCriteria(List<MatchCriteria> mcl) { _matchCriteria = mcl; }

    public Map<String, String[]> getMatchCriteriaValues() {

        StringBuffer lastNames = new StringBuffer();
        StringBuffer firstNames = new StringBuffer();
        StringBuffer ssns  = new StringBuffer();
        StringBuffer epns  = new StringBuffer();
        StringBuffer mrns  = new StringBuffer();
        StringBuffer fac   = new StringBuffer();
        StringBuffer dobs  = new StringBuffer();

        SimpleDateFormat sdf   = new SimpleDateFormat(ROIConstants.ROI_DATE_FORMAT);

        for (MatchCriteria mc : _matchCriteria) {

            if (mc.getLastName() != null) {
                lastNames.append(lastNames.length() > 0 ? ROIConstants.FIELD_DELIMITER
                + mc.getLastName() : mc.getLastName());
            }
            if (mc.getFirstName() != null) {
                firstNames.append(firstNames.length() > 0 ? ROIConstants.FIELD_DELIMITER
                + mc.getFirstName() : mc.getFirstName());
            }
            if (mc.getEpn() != null) {
                epns.append(epns.length() > 0 ? ROIConstants.FIELD_DELIMITER + mc.getEpn()
                                                : mc.getEpn());
            }
            if (mc.getDob() != null) {
                dobs.append(dobs.length() > 0 ? ROIConstants.FIELD_DELIMITER
                                              + sdf.format(mc.getDob())
                                              : sdf.format(mc.getDob()));

            }
            addSSN(mc, ssns);
            addMRNandFACILITY(mc, mrns, fac);
        }

        Map<String, String[]> map = new HashMap <String, String[]>();
        setMapValue(map, NAME_LIST, lastNames.toString());
        setMapValue(map, FIRST_NAME_LIST, firstNames.toString());
        setMapValue(map, SSN_LIST, ssns.toString());
        setMapValue(map, EPN_LIST, epns.toString());
        setMapValue(map, MRN_LIST, mrns.toString());
        setMapValue(map, FACILITY_LIST, fac.toString());
        setMapValue(map, DOB_LIST, dobs.toString());

        return map;
    }

    private void addSSN(MatchCriteria mc, StringBuffer ssns) {

        if (mc.getSsn() != null) {
            ssns.append(ssns.length() > 0 ? ROIConstants.FIELD_DELIMITER + mc.getSsn()
                                            : mc.getSsn());
        }
    }
    private void addMRNandFACILITY(MatchCriteria mc, StringBuffer mrns, StringBuffer fac) {

        if (mc.getMrn() != null) {
            mrns.append(mrns.length() > 0 ? ROIConstants.FIELD_DELIMITER + mc.getMrn()
                                            : mc.getMrn());
        }
        if (mc.getFacility() != null) {
            fac.append(fac.length() > 0 ? ROIConstants.FIELD_DELIMITER + mc.getFacility()
                                            : mc.getFacility());
        }
    }

    private void setMapValue(Map<String, String[]> map, String key, String value) {
        if (value.length() > 0) {
            map.put(key, value.toString().split(ROIConstants.FIELD_DELIMITER));
        }
    }
}
