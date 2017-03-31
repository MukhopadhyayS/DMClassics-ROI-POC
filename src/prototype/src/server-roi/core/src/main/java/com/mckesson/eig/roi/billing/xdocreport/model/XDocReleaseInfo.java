/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2014 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.billing.xdocreport.model;

import com.mckesson.eig.roi.billing.letter.model.ReleaseInfo;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocReleaseInfo {    

    private String _totPages = StringUtilities.EMPTYSTRING;
    private String _releaseCost = StringUtilities.EMPTYSTRING;
    private String _releaseDt = StringUtilities.EMPTYSTRING;
    private String _previouslyReleasedCost = StringUtilities.EMPTYSTRING;
    private String _balanceDue = StringUtilities.EMPTYSTRING;
    private String _totalCost = StringUtilities.EMPTYSTRING;
    private String _totalPagesReleased = StringUtilities.EMPTYSTRING;

    public XDocReleaseInfo(ReleaseInfo releaseInfo) {
        
        if (null == releaseInfo) {
            return;
        }
        
        setTotPages(StringUtilities.safe(releaseInfo.getTotPages()));
        setReleaseCost(StringUtilities.safe(releaseInfo.getReleaseCost()));
        setReleaseDt(StringUtilities.safe(releaseInfo.getReleaseDt()));
        setPreviouslyReleasedCost(StringUtilities.safe(releaseInfo.getPreviouslyReleasedCost()));
        setBalanceDue(StringUtilities.safe(releaseInfo.getBalanceDue()));
        setTotalCost(StringUtilities.safe(releaseInfo.getTotalCost()));
        setTotalPagesReleased(StringUtilities.safe(releaseInfo.getTotalPagesReleased()));
    }
    
    public String getTotPages() { return _totPages; }
    public void setTotPages(String pages) { _totPages = pages; }

    public String getReleaseCost() { return _releaseCost; }
    public void setReleaseCost(String cost) { _releaseCost = cost; }

    public String getReleaseDt() { return _releaseDt; }
    public void setReleaseDt(String dt) { _releaseDt = dt; }

    public String getPreviouslyReleasedCost() { return _previouslyReleasedCost; }
    public void setPreviouslyReleasedCost(String releasedCost) {
        _previouslyReleasedCost = releasedCost;
    }

    public String getBalanceDue() { return _balanceDue; }
    public void setBalanceDue(String due) { _balanceDue = due; }

    public String getTotalCost() { return _totalCost; }
    public void setTotalCost(String requestCost) { _totalCost = requestCost; }

    public String getTotalPagesReleased() { return _totalPagesReleased; }
    public void setTotalPagesReleased(String pagesReleased) { _totalPagesReleased = pagesReleased; }
}
